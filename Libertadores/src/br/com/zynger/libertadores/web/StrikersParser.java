package br.com.zynger.libertadores.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Striker;
import br.com.zynger.libertadores.util.WebDatabaseMapper;

public class StrikersParser {
	private final String STRIKERS_URL = "http://www.pasionlibertadores.com/df/libertadores/nuevo_goleadores.html";
	//private final String STRIKERS_URL = "http://www.pasionlibertadores.com/df/libertadores/goleadores.html";
	private final Integer MIN_GOALS_TO_SHOW = 1;
	
	private Context context;
	private TreeMap<String, Club> clubs;
	private WebDatabaseMapper webDatabaseMapper;
	
	public StrikersParser(Context context, TreeMap<String, Club> clubs) {
		setContext(context);
		setClubs(clubs);
		this.webDatabaseMapper = new WebDatabaseMapper(clubs, WebDatabaseMapper.SOURCE_STRIKERS);
	}
	
	public ArrayList<Striker> getStrikers() {
		try{			
			Document document = Jsoup.connect(STRIKERS_URL).get();
			Elements goleadores = document.getElementsByClass("goleadores");
			if(goleadores.size() < 1) throw new IOException();
			Element divGoleadores = goleadores.get(0);
			
			Elements tableRows = divGoleadores.getElementsByTag("tr");
			
			ArrayList<Striker> strikers = new ArrayList<Striker>();
			for (Element tr : tableRows) {
				if(!tr.className().equals("title")){
					Elements td = tr.getElementsByTag("td");
					String name = td.get(1).text();
					String clubName = td.get(2).text();
					String jugada = td.get(4).text();
					String cabeza = td.get(5).text();
					String tiroLivre = td.get(6).text();
					String penal = td.get(8).text();
					
					Club club = webDatabaseMapper.getClubByName(clubName);
					
					Striker striker = new Striker(name, club);
					striker.setMove(Integer.valueOf(jugada));
					striker.setHeader(Integer.valueOf(cabeza));
					striker.setFoul(Integer.valueOf(tiroLivre));
					striker.setPenalty(Integer.valueOf(penal));
					if(club.getBadge().equals("badge_dummy")) striker.setClubNameIfDummy(clubName);
					
					if(striker.getTotalGoals() < MIN_GOALS_TO_SHOW) break; //TODO adicionar endless list
					
					strikers.add(striker);
				}
			}
			
			return strikers;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
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