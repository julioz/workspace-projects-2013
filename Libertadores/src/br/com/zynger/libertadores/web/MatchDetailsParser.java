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
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.model.MatchDetails;
import br.com.zynger.libertadores.model.MatchDetails.MatchFact;
import br.com.zynger.libertadores.model.MatchDetails.MatchPlayer;
import br.com.zynger.libertadores.model.MatchDetails.MatchSubstitution;

public class MatchDetailsParser {

	private Context context;
	private Match match;
	private TreeMap<String, Club> clubs;
	
	public MatchDetailsParser(Context context, Match match, TreeMap<String, Club> clubs) {
		setContext(context);
		setMatch(match);
		setClubs(clubs);
	}

	public MatchDetails getMatchDetails() throws IOException {
		Document doc = Jsoup.connect(getMatch().getDetailsUrl()).get();
		
		MatchDetails matchDetails = new MatchDetails();
		
		Element cont_datos = doc.getElementsByClass("cont_datos").get(0);
		Elements dato = cont_datos.getElementsByClass("dato");
		String referee = dato.get(0).getElementsByClass("right").get(0).text();
		String stadium = dato.get(1).getElementsByClass("right").get(0).text();
		matchDetails.setReferee(referee.replace("Arbitro: ", "").trim());
		matchDetails.setStadium(stadium.replace("Estadio: ", "").trim());
		
		Element contenedorFormacion = doc.getElementsByClass("contenedorFormacion").get(0);
		Element cont_izq = contenedorFormacion.getElementsByClass("cont_izq").get(0);
		Element cont_der = contenedorFormacion.getElementsByClass("cont_der").get(0);
		Element c_barra2_izq = contenedorFormacion.getElementsByClass("c_barra2_izq").get(0);
		Element c_barra2_der = contenedorFormacion.getElementsByClass("c_barra2_der").get(0);
		
		matchDetails.setHomeCoach(cont_izq.getElementsByClass("ultima").get(0).text().replace("DT: ", "").trim());
		matchDetails.setAwayCoach(cont_der.getElementsByClass("ultima").get(0).text().replace("DT: ", "").trim());
		
		ArrayList<MatchPlayer> homeFormation = getPlayers(cont_izq.getElementsByTag("tr"));
		ArrayList<MatchPlayer> awayFormation = getPlayers(cont_der.getElementsByTag("tr"));
		ArrayList<MatchPlayer> homeAlternate = getPlayers(c_barra2_izq.getElementsByTag("tr"));
		ArrayList<MatchPlayer> awayAlternate = getPlayers(c_barra2_der.getElementsByTag("tr"));
		
		matchDetails.setHomeFormation(homeFormation);
		matchDetails.setAwayFormation(awayFormation);
		matchDetails.setHomeAlternate(homeAlternate);
		matchDetails.setAwayAlternate(awayAlternate);
		
		Element goles = doc.getElementById("goles");
		ArrayList<MatchFact> goals = getSimpleTable(goles);
		ArrayList<MatchFact> homeGoals = new ArrayList<MatchDetails.MatchFact>();
		ArrayList<MatchFact> awayGoals = new ArrayList<MatchDetails.MatchFact>();
		for (MatchFact matchFact : goals) {
			if(matchFact.getSide() == MatchFact.SIDE_HOME) homeGoals.add(matchFact);
			else awayGoals.add(matchFact);
		}
		matchDetails.setHomeGoals(homeGoals);
		matchDetails.setAwayGoals(awayGoals);
		
		Element amonestados = doc.getElementById("amonestados");
		ArrayList<MatchFact> yCards = getSimpleTable(amonestados);
		matchDetails.setYellowCards(yCards);
		
		Element expulsados = doc.getElementById("expulsados");
		ArrayList<MatchFact> rCards = getSimpleTable(expulsados);
		matchDetails.setRedCards(rCards);
		
		Element cambios = doc.getElementById("cambios");
		ArrayList<MatchSubstitution> substs = getSubstTable(cambios);
		matchDetails.setSubstitutions(substs);

		return matchDetails;
	}

	private ArrayList<MatchPlayer> getPlayers(Elements trs) {
		ArrayList<MatchPlayer> array = new ArrayList<MatchDetails.MatchPlayer>();
		
		for (Element tr : trs) {
			if(tr.className().equals("ultima")) continue;
			
			MatchPlayer mp = new MatchPlayer();
			Integer number = null;
			try{				
				number = Integer.valueOf(tr.getElementsByClass("numero").get(0).text().trim());
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			String name = tr.getElementsByClass("nombre").get(0).text().trim();
			
			Element tarjeta = tr.getElementsByClass("tarjeta").get(0);
			Elements imgs = tarjeta.getElementsByTag("img");
			Integer card = null;
			for (Element img : imgs) {
				String src = img.attr("src");
				if(src.toLowerCase().contains("amarilla")) card = MatchPlayer.CARD_YELLOW;
				else if(src.toLowerCase().contains("roja")){
					card = MatchPlayer.CARD_RED;
					break;
				}
			}
			
			mp.setCard(card);
			mp.setNumber(number);
			mp.setName(name);
			array.add(mp);
		}
		
		return array;
	}

	private ArrayList<MatchFact> getSimpleTable(Element div){
		ArrayList<MatchFact> array = new ArrayList<MatchDetails.MatchFact>();
		try{
			Element leftTable = div.getElementsByClass("c_barra2_izq").get(0).getElementById("tabla_der");
			Element rightTable = div.getElementsByClass("c_barra2_der").get(0).getElementById("tabla_der");
			
			Elements tr = leftTable.getElementsByTag("tr");
			for (Element el : tr) {
				MatchFact mf = new MatchFact();
				mf.setSide(MatchFact.SIDE_HOME);
				mf.setTime(el.getElementsByClass("minuto").get(0).text().trim());
				String tiempo = el.getElementsByClass("tiempo").get(0).text().trim();
				mf.setHalf(tiempo.toLowerCase().contains("pt") ? MatchFact.HALF_FIRST : MatchFact.HALF_SECOND);
				mf.setName(el.getElementsByClass("nombre").get(0).text().trim());
				array.add(mf);	
			}
			
			tr = rightTable.getElementsByTag("tr");
			for (Element el : tr) {
				MatchFact mf = new MatchFact();
				mf.setSide(MatchFact.SIDE_AWAY);
				mf.setTime(el.getElementsByClass("minuto").get(0).text().trim());
				String tiempo = el.getElementsByClass("tiempo").get(0).text().trim();
				mf.setHalf(tiempo.toLowerCase().contains("pt") ? MatchFact.HALF_FIRST : MatchFact.HALF_SECOND);
				mf.setName(el.getElementsByClass("nombre").get(0).text().trim());
				array.add(mf);	
			}
		}catch(NullPointerException npe) {} // if there is no occurency inside this element, just return the empty array
		
		return array;
	}
	
	private ArrayList<MatchSubstitution> getSubstTable(Element div) {
		ArrayList<MatchSubstitution> array = new ArrayList<MatchDetails.MatchSubstitution>();
		
		try{
			Element leftTable = div.getElementsByClass("c_barra2_izq").get(0).getElementById("tabla_der");
			Element rightTable = div.getElementsByClass("c_barra2_der").get(0).getElementById("tabla_der");
			
			Elements tr = leftTable.getElementsByTag("tr");
			int counter = 0;
			MatchSubstitution ms = null;
			for (Element el : tr) {
				if(counter%2 == 0){					
					ms = new MatchSubstitution();
					ms.setSide(MatchFact.SIDE_HOME);
					ms.setTime(el.getElementsByClass("minuto").get(0).text().trim());
					String tiempo = el.getElementsByClass("tiempo").get(0).text().trim();
					ms.setHalf(tiempo.toLowerCase().contains("pt") ? MatchFact.HALF_FIRST : MatchFact.HALF_SECOND);
					ms.setNameIn(el.getElementsByClass("nombre").get(0).text().replace("Entra:", "").trim());
				}else{
					ms.setNameOut(el.getElementsByClass("nombre").get(0).text().replace("Sale:", "").trim());
					array.add(ms);
				}
				counter++;
			}
			
			tr = rightTable.getElementsByTag("tr");
			counter = 0;
			for (Element el : tr) {
				if(counter%2 == 0){					
					ms = new MatchSubstitution();
					ms.setSide(MatchFact.SIDE_AWAY);
					ms.setTime(el.getElementsByClass("minuto").get(0).text().trim());
					String tiempo = el.getElementsByClass("tiempo").get(0).text().trim();
					ms.setHalf(tiempo.toLowerCase().contains("pt") ? MatchFact.HALF_FIRST : MatchFact.HALF_SECOND);
					ms.setNameIn(el.getElementsByClass("nombre").get(0).text().replace("Entra:", "").trim());
				}else{
					ms.setNameOut(el.getElementsByClass("nombre").get(0).text().replace("Sale:", "").trim());
					array.add(ms);
				}
				counter++;
			}
		}catch(NullPointerException npe) {} // if there is no occurency inside this element, just return the empty array
		
		return array;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public Match getMatch() {
		return match;
	}
	
	public void setMatch(Match match) {
		this.match = match;
	}
	
	public TreeMap<String, Club> getClubs() {
		return clubs;
	}
	
	public void setClubs(TreeMap<String, Club> clubs) {
		this.clubs = clubs;
	}
}
