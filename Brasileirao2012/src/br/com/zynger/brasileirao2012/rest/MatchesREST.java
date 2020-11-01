package br.com.zynger.brasileirao2012.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.util.Log;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Fixture;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.WebDatabaseMapper;

public class MatchesREST {

	private final static String MATCHES_URL_PREFIX = "http://esporte.uol.com.br/futebol/campeonatos/brasileiro/2013/serie-";
	private final static String MATCHES_URL_INFIX = "/tabela-de-jogos/fase-unica/tabela-de-jogos-";
	private final static String MATCHES_URL_SUFFIX = "a-rodada.htm";
	private final static String MATCHES_DIVISION_A_URL = "a";
	private final static String MATCHES_DIVISION_B_URL = "b";

	private final static String DATE_PATTERN = "dd/MM/yyyy";
	
	private WebDatabaseMapper webDatabaseMapper;
	private SimpleDateFormat sdf;
	
	public MatchesREST(TreeMap<String, Club> clubs) {
		this.webDatabaseMapper = new WebDatabaseMapper(clubs, WebDatabaseMapper.SOURCE_MATCHES);
		this.sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
		this.sdf.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
	}
	
	public ArrayList<Match> getMatches(TreeMap<Integer, Fixture> matchesMap, Division division, int rodada) throws IOException {
		String url = MATCHES_URL_PREFIX;
		url += division == Division.SECONDDIVISION ? MATCHES_DIVISION_B_URL : MATCHES_DIVISION_A_URL;
		url += MATCHES_URL_INFIX + rodada + MATCHES_URL_SUFFIX;
		
		Document doc = Jsoup.connect(url).timeout(30*1000).get();
		Element div  = doc.getElementsByClass("tabelajogo").first();
		Elements tables = div.getElementsByTag("table");
		
		ArrayList<Match> matchesList = new ArrayList<Match>();
		for (Element table : tables) {
			matchesList = parseHTMLTable(matchesList, matchesMap.get(rodada), table);
		}
		
		return matchesList;
	}
	
	private ArrayList<Match> parseHTMLTable(ArrayList<Match> matches, Fixture fixture, Element table) {
		GregorianCalendar calendar = null;
		
		Elements tableRows = new Elements();
		for (Element elementTr : table.getElementsByTag("tr")) {
			if(elementTr.className().equals("rodada")){
				try{					
					String spanData = elementTr.getElementsByClass("data").first().text();
					if(!spanData.equals("0/0/0")){
						calendar = new GregorianCalendar(TimeZone.getTimeZone(Constants.TIMEZONE));
						calendar.setTime(sdf.parse(spanData));
					}
				}catch(ParseException pe){
					pe.printStackTrace();
				}
			}else if(!elementTr.className().equals("dados")){
				tableRows.add(elementTr);
			}
		}
		
		return parseHTMLTableRows(matches, fixture, calendar, tableRows);
	}

	private ArrayList<Match> parseHTMLTableRows(ArrayList<Match> matches, Fixture fixture,
			GregorianCalendar calendar, Elements tableRows) {
		for (Element tr : tableRows) {
			String time = tr.getElementsByClass("hora").first().text();
			String teamHome = tr.getElementsByClass("time1").first().text();
			String resultado = tr.getElementsByClass("resultado").first().text();
			String stadium = tr.getElementsByClass("estadio").first().text();
			
			Match match = fixture.getMatchByHomeClub(webDatabaseMapper.getClubByName(teamHome));
			if(match != null){
				// se a partida nao acabou, preciso atualiza-la
				if(!match.isDone()){				
					try{
						setScores(resultado, match);
						setStadium(stadium, match);
						setDate(calendar, time, match);
					}catch(NullPointerException e) {
						Log.e(Constants.TAG, e.toString() + " -> " + teamHome);
						e.printStackTrace();
						continue;
					}
				}
				matches.add(match);
			}
		}
		
		return matches;
	}

	private void setDate(GregorianCalendar calendar, String time, Match match) {
		if(calendar == null){
			match.setDate(null);
		}else{
			try{
				Integer hourOfDay = Integer.parseInt(time.substring(0, time.indexOf("h")));
				Integer minute = Integer.parseInt(time.substring(time.indexOf("h")+1));
				
				// data foi parseada com sucesso, resta atualizar o horario
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);

				match.setDate(new GregorianCalendar(TimeZone.getTimeZone(Constants.TIMEZONE)));
				match.getDate().set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
			}catch(Exception e){
				Log.e(Constants.TAG, e.toString() + " -> " + time);
				if(time.trim().equals("-")){
					match.getDate().set(Calendar.HOUR_OF_DAY, 0);
					match.getDate().set(Calendar.MINUTE, 0);
				}else{
					e.printStackTrace();
				}
			}
		}
	}

	private void setStadium(String stadium, Match match) {
		if(stadium.trim().equals("-")){
			stadium = null;
		}
		match.setStadium(stadium);
	}

	@SuppressLint("DefaultLocale")
	private void setScores(String resultado, Match match) {
		try{					
			int scr_h = Integer.parseInt(resultado.substring(0, resultado.toLowerCase().indexOf("x")-1));
			int scr_a = Integer.parseInt(resultado.substring(resultado.toLowerCase().indexOf("x")+2));
			match.setScoreHome(scr_h);
			match.setScoreAway(scr_a);
		}catch (StringIndexOutOfBoundsException e) {} //se o placar ainda nao estiver preenchido
	}
}