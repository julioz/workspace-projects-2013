package br.com.zynger.libertadores.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.enums.Phase;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.util.JsonUtil;
import br.com.zynger.libertadores.util.WebDatabaseMapper;

public class MatchesParser {
	
	private static final int MATCHES_YEAR = 2013;
	private static final String TIMEZONE_SOURCE = "GMT+00:00";
	private final String MATCHES_URL = "http://statistics.ficfiles.com/conmebol/libertadores/fixture.html";
	private final String DROPBOX_MATCHES_URL = "https://dl.dropboxusercontent.com/u/11231744/libertadores.json";
	private final String PREFIX_MATCHES_DETAILS_URL = "http://statistics.ficfiles.com/conmebol/libertadores/";

	private Context context;
	private TreeMap<String, Club> clubs;
	private WebDatabaseMapper webDatabaseMapper;
	
	public MatchesParser(Context context, TreeMap<String, Club> clubs) {
		setContext(context);
		setClubs(clubs);
		this.webDatabaseMapper = new WebDatabaseMapper(getClubs(), WebDatabaseMapper.SOURCE_MATCHES);
	}
	
	public ArrayList<Match> getMatchesForGroups(Integer groupNum){
		try{
			Document document = Jsoup.connect(MATCHES_URL).get();
			if(groupNum >= 1 && groupNum <= 8) return getGroupMatches(document, groupNum);
			else return null;
		}catch(IOException ioexc){
			Log.e(HomeActivity.TAG, ioexc.toString());
			return null;
		}
	}
	
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, ArrayList<Match>> getAllMatches(){
		try{
			Document document = Jsoup.connect(MATCHES_URL).get();
			HashMap<Integer, ArrayList<Match>> matches = new HashMap<Integer, ArrayList<Match>>();
			ArrayList<Match> prelib = getPhase(document, Phase.PRELIB, "fase_n1");
			
			ArrayList<Match> group1 = getGroupMatches(document, 1);
			ArrayList<Match> group2 = getGroupMatches(document, 2);
			ArrayList<Match> group3 = getGroupMatches(document, 3);
			ArrayList<Match> group4 = getGroupMatches(document, 4);
			ArrayList<Match> group5 = getGroupMatches(document, 5);
			ArrayList<Match> group6 = getGroupMatches(document, 6);
			ArrayList<Match> group7 = getGroupMatches(document, 7);
			ArrayList<Match> group8 = getGroupMatches(document, 8);
			
			ArrayList<Match> roundof16 = getPhase(document, Phase.ROUNDOF16, "fase_n3");
			ArrayList<Match> quarter = getPhase(document, Phase.QUARTERFINAL, "fase_n4");
			ArrayList<Match> semifinal = getDropboxPhase(document, Phase.SEMIFINAL, "fase_n5");
			ArrayList<Match> finals = getDropboxPhase(document, Phase.FINAL, "fase_n6");
			
			matches.put(Phase.PRELIB.getNumber(), prelib);
			matches.put(1, group1);
			matches.put(2, group2);
			matches.put(3, group3);
			matches.put(4, group4);
			matches.put(5, group5);
			matches.put(6, group6);
			matches.put(7, group7);
			matches.put(8, group8);
			matches.put(Phase.ROUNDOF16.getNumber(), roundof16);
			matches.put(Phase.QUARTERFINAL.getNumber(), quarter);
			matches.put(Phase.SEMIFINAL.getNumber(), semifinal);
			matches.put(Phase.FINAL.getNumber(), finals);
			
			return matches;
		}catch(IOException e){
			Log.e(HomeActivity.TAG, e.toString());
			return null;
		}
	}

	private ArrayList<Match> getDropboxPhase(Document document, Phase phase, String uselessTag) {
		try{
			ArrayList<Match> matches = new ArrayList<Match>();
			String json = JsonUtil.readJsonFeed(DROPBOX_MATCHES_URL);
			JSONObject array = new JSONObject(json);
			String tagPhase = (phase == Phase.SEMIFINAL ? "semifinal" : "final");
			JSONArray jsonPhase = array.getJSONArray(tagPhase);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
			for (int i = 0; i < jsonPhase.length(); i++) {
				JSONObject jsonObject = jsonPhase.getJSONObject(i);
				Log.e(HomeActivity.TAG, jsonObject.toString());
				Club home = webDatabaseMapper.getClubByName(translateName(jsonObject.getString("home")));
				Club away = webDatabaseMapper.getClubByName(translateName(jsonObject.getString("away")));
				String stadium = null;
				if(!jsonObject.isNull("stadium")){
					stadium = jsonObject.getString("stadium");
				}
				GregorianCalendar date = new GregorianCalendar(TimeZone.getTimeZone(TIMEZONE_SOURCE));
				date.setTime(sdf.parse(jsonObject.getString("date")));
				Integer scoreHome = Match.SCORE_NULL;
				if(!jsonObject.isNull("score_home")){					
					scoreHome = jsonObject.getInt("score_home");
				}
				Integer scoreAway = Match.SCORE_NULL;
				if(!jsonObject.isNull("score_away")){					
					scoreAway = jsonObject.getInt("score_away");
				}
				
				Match match = new Match(home, away, stadium, date, scoreHome, scoreAway);
				match.setTitle(jsonObject.getString("title"));
				
				if(!jsonObject.isNull("penalties_home")){
					Integer penaltyHome = jsonObject.getInt("penalties_home");
					Integer penaltyAway = jsonObject.getInt("penalties_away");
					match = new Match(home, away, stadium, date, scoreHome, scoreAway, penaltyHome, penaltyAway);
				}
				
				match.setDetailsUrl(""); //TODO remove
				matches.add(match);
			}
			
			return matches;
		}catch(JSONException je){
			je.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Match> getMatches(Phase phase){
		try{
			Document document = Jsoup.connect(MATCHES_URL).get();			
			if(phase == Phase.PRELIB) return getPhase(document, phase, "fase_n1");
			else if(phase == Phase.ROUNDOF16) return getPhase(document, phase, "fase_n3");
			else if(phase == Phase.QUARTERFINAL) return getPhase(document, phase, "fase_n4");
			else if(phase == Phase.SEMIFINAL) return getDropboxPhase(document, phase, "fase_n5");
			else if(phase == Phase.FINAL) return getDropboxPhase(document, phase, "fase_n6");
			else return null;
		}catch(IOException ioexc){
			Log.e(HomeActivity.TAG, ioexc.toString());
			return null;
		}
	}
	
	private ArrayList<Match> getGroupMatches(Document document, Integer groupNum) {
		Element groupDiv = document.getElementById("grupo_nro_" + groupNum);
		return getMatchesFromDiv(groupDiv, null, true);
	}
	
	private ArrayList<Match> getPhase(Document document, Phase phase, String tagPhase) {
		Element fixtureFase = document.getElementById(tagPhase);
		return getMatchesFromDiv(fixtureFase, phase, false);
	}

	private ArrayList<Match> getMatchesFromDiv(Element element, Phase phase, boolean shouldRemoveHeaders) {
		if(element == null) return null;
		
		ArrayList<Match> al = new ArrayList<Match>();
		Elements titles = element.getElementsByClass("tit");
		int counterTitle = 0;
		Elements tablas = element.getElementsByClass("cont_tabla");
		if(shouldRemoveHeaders){			
			tablas.remove(0);
			titles.remove(0);
		}
		for (Element tabla : tablas) {
			String title = titles.get(counterTitle).text();
			counterTitle++;
			title = formatMatchesTitle(title.trim());
			
			Element tr_partidoA = tabla.getElementsByClass("partido_a").get(0);
			Element tr_partidoB = tabla.getElementsByClass("partido_b").get(0);
			
			Match partido_a = parseMatch(tr_partidoA);
			Match partido_b = parseMatch(tr_partidoB);
			
			//TODO remover urgente!
			if(partido_b.getHome().getAcronym().equals("CAM") && partido_b.getAway().getAcronym().equals("TIJ")){
				partido_b.setScoreHome(1);
				partido_b.setScoreAway(1);
				partido_b.setOver(true);
			}
			
			partido_a.setTitle(title);
			partido_b.setTitle(title);
			partido_a.setPhase(phase);
			partido_b.setPhase(phase);
			al.add(partido_a);
			al.add(partido_b);
		}
		return al;
	}

	private String formatMatchesTitle(String title) {
		title = title.replaceAll("Llave", "");
		title = title.replaceAll("Fecha", "");
		return title.trim();
	}

	private Match parseMatch(Element tr_partido) {
		Elements equipos = tr_partido.getElementsByClass("equipo");
		Elements gols = tr_partido.getElementsByClass("gol");
		Elements estado = tr_partido.getElementsByClass("estado");
		Element dia = tr_partido.getElementsByClass("dia").get(0);
		Element hora = tr_partido.getElementsByClass("hora").get(0);
		Elements ficha = tr_partido.getElementsByClass("ficha");
		String homeName = equipos.get(0).text();
		String awayName = equipos.get(1).text();
		Club home = webDatabaseMapper.getClubByName(translateName(homeName));
		Club away = webDatabaseMapper.getClubByName(translateName(awayName));
		String strScoreHome = gols.get(0).text().trim();
		String strScoreAway = gols.get(1).text().trim();
		Boolean over = false;
		if(estado.size() != 0){
			if(estado.get(0).classNames().contains("fin")) over = true;
		}
		
		Integer[] penaltis = getPenaltisData(gols.get(0), gols.get(1));
		
		Integer scoreHome, scoreAway;
		if(penaltis != null && penaltis.length == 2){
			strScoreHome = strScoreHome.replaceFirst(String.valueOf(penaltis[0]), "").trim();
			strScoreAway = strScoreAway.replaceFirst(String.valueOf(penaltis[1]), "").trim();
		}
		try{			
			scoreHome = Integer.valueOf(strScoreHome);
			scoreAway = Integer.valueOf(strScoreAway);
		}catch(NumberFormatException nfe){
			scoreHome = Match.SCORE_NULL;
			scoreAway = Match.SCORE_NULL;
		}

		String stadium = null; //FIXME
		
		GregorianCalendar date = null;
		if(!dia.text().equals("A confirmar")){
			int day = Integer.valueOf(dia.text().substring(0, 2));
			int month = Integer.valueOf(dia.text().substring(3, 5)) - 1;
			int year = MATCHES_YEAR;
			int hour = Integer.valueOf(hora.text().substring(0, 2));
			int minute = Integer.valueOf(hora.text().substring(3, 5));
			
			date = new GregorianCalendar(TimeZone.getTimeZone(TIMEZONE_SOURCE));
			date.set(year, month, day, hour, minute);
		}
		String fichaUrl = null;
		try{
			Element tdFicha = ficha.get(0);
			fichaUrl = tdFicha.getElementsByTag("a").get(0).attr("href");
			fichaUrl = fichaUrl.substring(fichaUrl.indexOf("'") + "'".length());
			fichaUrl = fichaUrl.substring(0, fichaUrl.indexOf("'"));
			fichaUrl = PREFIX_MATCHES_DETAILS_URL + fichaUrl;
		}catch(Exception e){
			Log.e(HomeActivity.TAG, "Parsing ficha: " + e.toString());
			e.printStackTrace();
			fichaUrl = null;
		}
		
		Match match = null;
		
		if(penaltis != null){
			match = new Match(home, away, stadium, date, scoreHome, scoreAway, penaltis[0], penaltis[1]);
		}else match = new Match(home, away, stadium, date, scoreHome, scoreAway);
		match.setDetailsUrl(fichaUrl);
		match.setOver(over);
		return match;
	}
	
	private Integer[] getPenaltisData(Element gols1, Element gols2) {
		String strPenHome = gols1.getElementsByTag("sub").text().trim();
		String strPenAway = gols2.getElementsByTag("sub").text().trim();
		
		if(strPenHome.equals("") || strPenAway.equals("")) return null;
		
		Integer penaltyHome = Match.SCORE_NULL;
		Integer penaltyAway = Match.SCORE_NULL;
		
		try{
			if(strPenHome != null && strPenAway != null){
				penaltyHome = Integer.valueOf(strPenHome);
				penaltyAway = Integer.valueOf(strPenAway);
				
				Integer[] penaltis = new Integer[2];
				penaltis[0] = penaltyHome;
				penaltis[1] = penaltyAway;
				return penaltis;
			}
		}catch(NumberFormatException nfe){
			Log.e(HomeActivity.TAG, "penHome=" + strPenHome + ", penAway=" + strPenAway + " - " + nfe.toString());
		}
		
		return null;
	}

	private String translateName(String name) {
		if(name.contains("Equipo")) name = name.replaceAll("Equipo", getContext().getString(R.string.matchesparser_team));
		else if(name.contains("Gan. Llave")) name = name.replaceAll("Gan. Llave", getContext().getString(R.string.matchesparser_winner));
		else if(name.contains("GAN")) name = name.replaceAll("GAN", getContext().getString(R.string.matchesparser_winner));
		else if(name.contains("Ganador")) name = name.replaceAll("Ganador", getContext().getString(R.string.matchesparser_winner));
		else if(name.trim().equals("")) name = getContext().getString(R.string.matchesparser_undefined);
		return name;
	}

	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public TreeMap<String, Club> getClubs() {
		return clubs;
	}
	
	public void setClubs(TreeMap<String, Club> clubs) {
		this.clubs = clubs;
	}
	
}
