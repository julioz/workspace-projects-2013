package br.com.zynger.libertadores.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.annotation.SuppressLint;
import android.content.Context;
import br.com.zynger.libertadores.enums.Country;
import br.com.zynger.libertadores.model.HistoricClub;
import br.com.zynger.libertadores.model.HistoricCountry;
import br.com.zynger.libertadores.model.HistoricFinal;
import br.com.zynger.libertadores.model.HistoricMatch;
import br.com.zynger.libertadores.util.Raw;

public class HistoryParser {
	private static final String FILE_WINNERS_RUNNERUPS = "history_winners_runnerups";
	private static final String FILE_FINALS = "history_finals";
	private static final String FILE_COUNTRY = "history_countries";
	
	private static final String PREFIX_BADGE_ICON = "ic_history_badge_";
	private static final String PREFIX_COUNTRY_FLAG = "img_flag_";

	public ArrayList<HistoricClub> getWinnersAndRunnerUps(Context context){
		String xml = Raw.openRaw(context, FILE_WINNERS_RUNNERUPS);
		ArrayList<HistoricClub> clubs = parseWinnersAndRunnerUpsXML(xml);
		return clubs;
	}
	
	public ArrayList<HistoricFinal> getFinals(Context context){
		String xml = Raw.openRaw(context, FILE_FINALS);
		ArrayList<HistoricFinal> finals = parseHistoricFinals(xml);
		return finals;
	}
	
	public ArrayList<HistoricCountry> getByCountries(Context context){
		String xml = Raw.openRaw(context, FILE_COUNTRY);
		ArrayList<HistoricCountry> countries = parseCountriesData(xml);
		return countries;
	}
	
	private ArrayList<HistoricCountry> parseCountriesData(String xml) {		
		ArrayList<HistoricCountry> countries = new ArrayList<HistoricCountry>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		List<Node> nodeCountries = XMLUtils.getChildren(root, "country");
		for (Node node : nodeCountries) {
			String acronym = XMLUtils.getText(node, "acronym");
			String flag = XMLUtils.getText(node, "flag");
			String name = XMLUtils.getText(node, "name");
			Integer won = Integer.parseInt(XMLUtils.getText(node, "won"));
			Integer runnerup = Integer.parseInt(XMLUtils.getText(node, "runnerup"));
			
			HistoricCountry country = new HistoricCountry(acronym);
			country.setName(name);
			country.setFlag(PREFIX_COUNTRY_FLAG + flag);
			country.setWon(won);
			country.setRunnerUp(runnerup);
			countries.add(country);
		}
		return countries;
	}

	@SuppressLint("UseSparseArrays")
	private ArrayList<HistoricFinal> parseHistoricFinals(String xml) {
		ArrayList<HistoricFinal> array = new ArrayList<HistoricFinal>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		List<Node> nodeFinal = XMLUtils.getChildren(root, "final");
		for (Node node : nodeFinal) {
			HistoricFinal hFinal = new HistoricFinal();
			Integer year = Integer.valueOf(XMLUtils.getText(node, "year"));
			hFinal.setYear(year);
			String winner = XMLUtils.getText(node, "winner");
			hFinal.setWinner(winner);
			String badgeWinner = XMLUtils.getText(node, "badgewinner");
			String runnerup = XMLUtils.getText(node, "runnerup");
			String badgeRunnerUp = XMLUtils.getText(node, "badgerunnerup");
			hFinal.setRunnerup(runnerup);
			HistoricClub home = new HistoricClub();
			HistoricClub away = new HistoricClub();
			home.setName(winner);
			home.setBadge(PREFIX_BADGE_ICON + badgeWinner);
			away.setName(runnerup);
			away.setBadge(PREFIX_BADGE_ICON + badgeRunnerUp);
			List<Node> matches = XMLUtils.getChildren(node, "match");
			int countMatches = 0;
			for (Node match : matches) {
				String score = XMLUtils.getText(match, "score");
				String stadium = XMLUtils.getText(match, "venue");
				HistoricMatch hMatch = new HistoricMatch();
				hMatch.setHome(home);
				hMatch.setAway(away);
				hMatch.setScoreHome(Integer.valueOf(score.substring(0, 1)));
				hMatch.setScoreAway(Integer.valueOf(score.substring(2, 3)));
				hMatch.setStadium(stadium);
				if(countMatches == 0){
					hFinal.setFirstMatch(hMatch);
					countMatches++;
				}else hFinal.setSecondMatch(hMatch);
			}
			String penaltyScore = XMLUtils.getText(node, "penalties");
			if(!penaltyScore.equals("")){
				HistoricMatch penalties = new HistoricMatch();
				penalties.setHome(home);
				penalties.setAway(away);
				penalties.setScoreHome(Integer.valueOf(penaltyScore.substring(0, 1)));
				penalties.setScoreAway(Integer.valueOf(penaltyScore.substring(2, 3)));
				hFinal.setPenalties(penalties);
			}
			array.add(hFinal);
		}
		return array;
	}

	private ArrayList<HistoricClub> parseWinnersAndRunnerUpsXML(String xml){
		ArrayList<HistoricClub> clubs = new ArrayList<HistoricClub>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		List<Node> nodeClub = XMLUtils.getChildren(root, "club");
		for (Node node : nodeClub) {
			HistoricClub club = new HistoricClub();
			String name = XMLUtils.getText(node, "name");
			String badge = XMLUtils.getText(node, "badge");
			String country = XMLUtils.getText(node, "country");
			
			Node winner = XMLUtils.getChild(node, "winner");
			ArrayList<String> won = new ArrayList<String>();
			if(null != winner) won = parseTrophies(winner);
			
			Node runnerup = XMLUtils.getChild(node, "runnerup");
			ArrayList<String> runnerUp = new ArrayList<String>();
			if(null != runnerup) runnerUp = parseTrophies(runnerup);
			
			club.setName(name);
			club.setBadge(PREFIX_BADGE_ICON + badge);
			club.setCountry(getCountryConst(country));
			club.setWon(won);
			club.setRunnerUp(runnerUp);
			clubs.add(club);
		}
		return clubs;
	}
	
	private static Country getCountryConst(String country) {
		if(country.equals("ARG")) return Country.ARGENTINA;
		else if(country.equals("BOL")) return Country.BOLIVIA;
		else if(country.equals("BRA")) return Country.BRAZIL;
		else if(country.equals("COL")) return Country.COLOMBIA;
		else if(country.equals("CHI")) return Country.CHILE;
		else if(country.equals("PAR")) return Country.PARAGUAY;
		else if(country.equals("URU")) return Country.URUGUAY;
		else if(country.equals("ECU")) return Country.ECUADOR;
		else if(country.equals("VEN")) return Country.VENEZUELA;
		else if(country.equals("MEX")) return Country.MEXICO;
		else if(country.equals("PER")) return Country.PERU;
		else return null;
	}

	private static ArrayList<String> parseTrophies(Node root) {
		ArrayList<String> al = new ArrayList<String>();
		List<Node> years = XMLUtils.getChildren(root, "year");
		for (Node node : years) {
			String year = node.getTextContent();
			al.add(year);
		}
		return al;
	}
}
