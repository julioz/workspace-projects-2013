package br.com.zynger.brasileirao2012.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fixture implements Serializable, Comparable<Fixture> {

	private static final String JSON_FIXTURENUMBER = "JSON_FIXTURENUMBER";
	private static final String JSON_MATCHESARRAY = "JSON_MATCHESARRAY";

	private static final long serialVersionUID = 1L;

	private int number;
	private ArrayList<Match> matches;
	private boolean done;

	public Fixture(int number) {
		this.number = number;
		this.matches = new ArrayList<Match>();
		this.done = false;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}

	public void setMatches(ArrayList<Match> matches) {
		this.matches = matches;
	}

	public void addMatch(Match match){
		getMatches().add(match);
	}

	public boolean isDone() {
		checkIfDone();
		return done;
	}
	
	public Match getMatchByHomeClub(Club home) {
		Match match = null;
		if(home != null){			
			for (Match m : getMatches()) {
				if(m.getHome().getAcronym().equals(home.getAcronym())){
					match = m;
					break;
				}
			}
		}
		return match;
	}

	private void checkIfDone() {
		int counter = 0;
		for (Match match : getMatches()) {
			if(match.isDone().booleanValue() == true) counter++;
		}

		setDone(counter == getMatches().size());
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public int compareTo(Fixture another) {
		return getNumber() - another.getNumber();
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		
		try{			
			json.put(JSON_FIXTURENUMBER, getNumber());
			JSONArray matches = new JSONArray();
			for (Match m : getMatches()) {
				JSONArray matchJson = m.toJson();
				if(matchJson != null) matches.put(matchJson);
			}
			json.put(JSON_MATCHESARRAY, matches);
			return json;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}

	public void restoreFixtureData(JSONObject jsonFixture, TreeMap<String, Club> clubs) {
		try{
			JSONArray matches = jsonFixture.getJSONArray(JSON_MATCHESARRAY);
			for (int i = 0; i < matches.length(); i++) {
				JSONArray array = matches.getJSONArray(i);
				for (Match m : getMatches()) {
					if(m.getHome().getAcronym().equals(array.get(Match.JSON_HOME))){
						m.restoreMatchData(array, clubs);
					}
				}
			}
		}catch(JSONException je){
			je.printStackTrace();
		}
	}

}