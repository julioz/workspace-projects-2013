package br.com.zynger.libertadores.model;

import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;


public class Striker implements Comparable<Striker> {
	
	private final static int JSON_NAME = 0;
	private final static int JSON_CLUB = 1;
	private final static int JSON_MOVE = 2;
	private final static int JSON_HEADER = 3;
	private final static int JSON_FOUL = 4;
	private final static int JSON_PENALTY = 5;
	private final static int JSON_CLUBNAMEIFDUMMY = 6;
	
	private String name;
	private Club club;
	private String clubNameIfDummy;
	private Integer move = 0;
	private Integer header = 0;
	private Integer foul = 0;
	private Integer penalty = 0;
	
	public Striker(String name, Club club) {
		this.name = name;
		this.club = club;
	}
	
	public Striker(JSONArray data, TreeMap<String, Club> clubs) throws JSONException{
		setFromJson(data, clubs);
	}
	
	public Integer getTotalGoals(){
		return Integer.valueOf(getMove() + getHeader() + getFoul() + getPenalty());
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
	
	public Integer getMove() {
		return move;
	}
	
	public void setMove(Integer move) {
		this.move = move;
	}
	
	public Integer getHeader() {
		return header;
	}
	
	public void setHeader(Integer header) {
		this.header = header;
	}
	
	public Integer getFoul() {
		return foul;
	}
	
	public void setFoul(Integer foul) {
		this.foul = foul;
	}
	
	public Integer getPenalty() {
		return penalty;
	}
	
	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}
	
	public String getClubNameIfDummy() {
		return clubNameIfDummy;
	}
	
	public void setClubNameIfDummy(String clubNameIfDummy) {
		this.clubNameIfDummy = clubNameIfDummy;
	}

	@Override
	public String toString() {
		return "Striker [name=" + name + ", club=" + club.getAcronym() + ", move=" + move
				+ ", header=" + header + ", foul=" + foul + ", penalty="
				+ penalty + "]";
	}

	@Override
	public int compareTo(Striker another) {
		int comparison = another.getTotalGoals() - getTotalGoals();
		if(comparison == 0) comparison = getName().compareTo(another.getName());
		return comparison;
	}
	
	public void setFromJson(JSONArray data, TreeMap<String, Club> clubs) throws JSONException{
		String name = data.getString(JSON_NAME);
		String clubAcronym = data.getString(JSON_CLUB);
		Integer move = data.getInt(JSON_MOVE);
		Integer header = data.getInt(JSON_HEADER);
		Integer foul = data.getInt(JSON_FOUL);
		Integer penalty = data.getInt(JSON_PENALTY);
		String clubNameIfDummy = data.getString(JSON_CLUBNAMEIFDUMMY);
		
		setName(name);
		setClub(clubs.get(clubAcronym));
		setMove(move);
		setHeader(header);
		setFoul(foul);
		setPenalty(penalty);
		setClubNameIfDummy(clubNameIfDummy);
	}
	
	public JSONArray toJson() {
		try{			
			JSONArray data = new JSONArray();
			data.put(JSON_NAME, getName());
			data.put(JSON_CLUB, getClub().getAcronym());
			data.put(JSON_MOVE, getMove());
			data.put(JSON_HEADER, getHeader());
			data.put(JSON_FOUL, getFoul());
			data.put(JSON_PENALTY, getPenalty());
			data.put(JSON_CLUBNAMEIFDUMMY, getClubNameIfDummy());
			
			return data;
		}catch(JSONException e){
			Log.e(HomeActivity.TAG, e.toString());
			return null;
		}
	}

}