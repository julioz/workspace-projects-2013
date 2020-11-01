package br.com.zynger.brasileirao2012.model;

import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;


public class Striker {
	
	private final Integer JSON_NAME = 0;
	private final Integer JSON_CLUB = 1;
	private final Integer JSON_GOALS = 2;
	
	private String name;
	private Club club;
	private Integer goals;
	
	public Striker() { }
	
	public Striker(String name, Club club, int goals) {
		setName(name);
		setClub(club);
		setGoals(goals);
	}
	
	public Striker(JSONArray data, TreeMap<String, Club> clubs){
		try{			
			setName(data.getString(JSON_NAME));
			setClub(clubs.get(data.getString(JSON_CLUB)));
			setGoals(data.getInt(JSON_GOALS));
		}catch(JSONException je){
			je.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}
	
	@Override
	public String toString() {
		return getName() + " - " + getClub().getAcronym() + ": " + getGoals();
	}
	
	public JSONArray toJson() {
		try{
			JSONArray array = new JSONArray();
			array.put(JSON_NAME, getName());
			array.put(JSON_CLUB, getClub().getAcronym());
			array.put(JSON_GOALS, getGoals().toString());
			return array;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
}