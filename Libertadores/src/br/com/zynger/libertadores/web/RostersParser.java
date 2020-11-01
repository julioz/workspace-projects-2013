package br.com.zynger.libertadores.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Person;
import br.com.zynger.libertadores.model.TeamMember;
import br.com.zynger.libertadores.util.WebDatabaseMapper;

public class RostersParser {
	
	private final String CONTENT_URL = "http://statistics.ficfiles.com/conmebol/libertadores/planteles.html";
	
	private Context context;
	private TreeMap<String, Club> clubs;
	private WebDatabaseMapper webDatabaseMapper;
	private SimpleDateFormat sdf;
	
	public RostersParser(Context context, TreeMap<String, Club> clubs) {
		setContext(context);
		setClubs(clubs);
		webDatabaseMapper = new WebDatabaseMapper(getClubs(), WebDatabaseMapper.SOURCE_ROSTERS);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public HashMap<String, ArrayList<Person>> getRosters() throws IOException {
		Document doc = Jsoup.connect(CONTENT_URL).get();
		
		HashMap<String, Club> idsMap = buildIdMap(doc);
		HashMap<String, ArrayList<Person>> rosters = new HashMap<String, ArrayList<Person>>();
		
		for (String id : idsMap.keySet()) {
			ArrayList<Person> array = getRoster(doc.getElementById("e_" + id));
			rosters.put(idsMap.get(id).getAcronym(), array);
		}
		
		return rosters;
	}
	
	private ArrayList<Person> getRoster(Element div) {
		ArrayList<Person> roster = new ArrayList<Person>();
		
		Element tableBody = div.getElementsByTag("tbody").get(0);
		Elements trs = tableBody.getElementsByTag("tr");
		
		if(trs.get(0).classNames().contains("partido")) trs.remove(0);
		
		if(trs.get(0).classNames().contains("dt")){
			Element trCoach = trs.remove(0);
			Person coach = parseCoach(trCoach);
			roster.add(coach);
		}
		
		int counterForPosition = 0;
		for (int i = 0; i < trs.size(); i++) {
			Element tr = trs.get(i);
			if(tr.classNames().contains("cabezal")){
				counterForPosition++;
				continue;
			}
			
			Person player = parsePlayer(tr, counterForPosition);
			roster.add(player);
		}
		return roster;
	}
	
	private void parsePerson(Person player, Element tr) {
		String name = tr.getElementsByClass("nombre").get(0).text().trim();
		player.setName(name);
		
		String country = tr.getElementsByClass("pais").get(0).text().trim();
		player.setNationality(country);
		
		Elements data = tr.getElementsByClass("dato");
		Element tdBirthdate = data.get(0);
		Element tdHeight = data.get(1);
		Element tdWeigth = data.get(2);

		try{
			String strDate = tdBirthdate.text().trim();
			strDate = strDate.substring(0, strDate.indexOf(" "));
			Date date = sdf.parse(strDate);
			player.setBirthdate(new GregorianCalendar());
			player.getBirthdate().setTime(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String height = tdHeight.text().trim();
		if(height.length() > 0) player.setHeight(height);
		
		String weight = tdWeigth.text().trim();
		if(weight.length() > 0) player.setWeight(weight);
	}
	
	private Person parsePlayer(Element trPlayer, int counterForPosition){
		Person player = new Person();
		player.setPosition(TeamMember.values()[counterForPosition]);
		try{				
			String number = trPlayer.getElementsByClass("numero").get(0).text().trim();
			if(number.length() > 0) player.setNumber(Integer.valueOf(number));
		}catch(Exception e){
			e.printStackTrace();
			player.setNumber(null);
		}
		parsePerson(player, trPlayer);
		return player;
	}

	private Person parseCoach(Element trCoach){
		Person coach = new Person();
		coach.setNumber(Person.NUMBER_COACH);
		coach.setPosition(TeamMember.COACH);
		
		parsePerson(coach, trCoach);
		return coach;
	}

	private HashMap<String, Club> buildIdMap(Document doc) {
		Elements classEquipo = doc.getElementsByClass("equipo");
		List<Element> tdsTeamBadges = new ArrayList<Element>();
		for (Element element : classEquipo) {
			if(element.classNames().contains("nav3")) tdsTeamBadges.add(element);
		}
		
		HashMap<String, Club> idsMap = new HashMap<String, Club>();
		for (Element td : tdsTeamBadges) {
			String id = td.getElementsByTag("a").get(0).className();
			String imgAlt = td.getElementsByTag("img").get(0).attr("alt");
			Club club = webDatabaseMapper.getClubByName(imgAlt);
			idsMap.put(id, club);
		}
		
		return idsMap;
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
