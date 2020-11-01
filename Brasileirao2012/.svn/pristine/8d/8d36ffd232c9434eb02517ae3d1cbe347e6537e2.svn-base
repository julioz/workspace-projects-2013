package br.com.zynger.brasileirao2012.model;

import org.json.JSONArray;
import org.json.JSONException;

public class AprovData {
	private final Integer JSON_HOMEPLAYED = 0;
	private final Integer JSON_HOMEVICTORIES = 1;
	private final Integer JSON_HOMEDRAWS = 2;
	private final Integer JSON_AWAYPLAYED = 3;
	private final Integer JSON_AWAYVICTORIES = 4;
	private final Integer JSON_AWAYDRAWS = 5;
	
	private Integer homePlayed, homeVictories, homeDrawn;
	private Float homePerc;
	private Integer awayPlayed, awayVictories, awayDrawn;
	private Float awayPerc;
	
	public AprovData() {
		setHomePlayed(0);
		setHomeVictories(0);
		setHomeDrawn(0);
		setHomePerc(0.0f);
		
		setAwayPlayed(0);
		setAwayVictories(0);
		setAwayDrawn(0);
		setAwayPerc(0.0f);
	}
	
	public AprovData(JSONArray array){
		try{			
			setHomePlayed(array.getInt(JSON_HOMEPLAYED));
			setHomeVictories(array.getInt(JSON_HOMEVICTORIES));
			setHomeDrawn(array.getInt(JSON_HOMEDRAWS));
			setAwayPlayed(array.getInt(JSON_AWAYPLAYED));
			setAwayVictories(array.getInt(JSON_AWAYVICTORIES));
			setAwayDrawn(array.getInt(JSON_AWAYDRAWS));
			calcPercs();
		}catch(JSONException je){
			je.printStackTrace();
		}
	}
	
	public Integer getHomePlayed() {
		return homePlayed;
	}
	
	public void setHomePlayed(Integer homePlayed) {
		this.homePlayed = homePlayed;
	}
	
	public Integer getHomeVictories() {
		return homeVictories;
	}
	
	public void setHomeVictories(Integer homeVictories) {
		this.homeVictories = homeVictories;
	}
	
	public Integer getHomeDrawn() {
		return homeDrawn;
	}
	
	public void setHomeDrawn(Integer homeDrawn) {
		this.homeDrawn = homeDrawn;
	}
	
	public Float getHomePerc() {
		return homePerc;
	}
	
	public void setHomePerc(Float homePerc) {
		this.homePerc = homePerc;
	}
	
	public Integer getAwayPlayed() {
		return awayPlayed;
	}
	
	public void setAwayPlayed(Integer awayPlayed) {
		this.awayPlayed = awayPlayed;
	}
	
	public Integer getAwayVictories() {
		return awayVictories;
	}
	
	public void setAwayVictories(Integer awayVictories) {
		this.awayVictories = awayVictories;
	}
	
	public Integer getAwayDrawn() {
		return awayDrawn;
	}
	
	public void setAwayDrawn(Integer awayDrawn) {
		this.awayDrawn = awayDrawn;
	}
	
	public Float getAwayPerc() {
		return awayPerc;
	}
	
	public void setAwayPerc(Float awayPerc) {
		this.awayPerc = awayPerc;
	}
	
	public JSONArray toJson() {
		try{			
			JSONArray array = new JSONArray();
			array.put(JSON_HOMEPLAYED, getHomePlayed().toString());
			array.put(JSON_HOMEVICTORIES, getHomeVictories().toString());
			array.put(JSON_HOMEDRAWS, getHomeDrawn().toString());
			array.put(JSON_AWAYPLAYED, getAwayPlayed().toString());
			array.put(JSON_AWAYVICTORIES, getAwayVictories().toString());
			array.put(JSON_AWAYDRAWS, getAwayDrawn().toString());
			return array;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	public void calcPercs() {
		Integer hVictories = getHomeVictories() * 3;
		Integer aVictories = getAwayVictories() * 3;
		
		Integer hPoints = hVictories + getHomeDrawn();
		Integer aPoints = aVictories + getAwayDrawn();
		
		hPoints *= 100;
		aPoints *= 100;
		
		float homePerc = 0.0f;
		float awayPerc = 0.0f;
		try{			
			homePerc = hPoints / (getHomePlayed() * 3);
			awayPerc = aPoints / (getAwayPlayed() * 3);
		}catch(ArithmeticException ae){ }
		
		setHomePerc(homePerc);
		setAwayPerc(awayPerc);
	}
	
}
