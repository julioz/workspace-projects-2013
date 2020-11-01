package br.com.zynger.brasileirao2012.model;

import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.zynger.brasileirao2012.enums.Division;

public class RealTimeMatch extends Match {
	private final static Integer JSON_RESULTHOME = 11;
	private final static Integer JSON_RESULTAWAY = 12;
	private final static Integer JSON_URLDETAILS = 13;
	private final static Integer JSON_HOMEPOS = 14;
	private final static Integer JSON_AWAYPOS = 15;
	private final static Integer JSON_HOMEPOSDIF = 16;
	private final static Integer JSON_AWAYPOSDIF = 17;
	private final static Integer JSON_STATE = 18;
	
	public enum State {
		TO_START, IN_PROGRESS, FINISHED;
	}

	private String resultHome, resultAway;
	private String urlDetails;
	private Division division;
	private Integer homePos, awayPos, homePosDif, awayPosDif;
	private State state = State.TO_START;
	
	public RealTimeMatch(){}
	
	public RealTimeMatch(JSONArray jArr, TreeMap<String, Club> clubs){
		super(jArr, clubs);
		try{			
			setResultHome(jArr.getString(JSON_RESULTHOME));
			setResultAway(jArr.getString(JSON_RESULTAWAY));
			setUrlDetails(jArr.getString(JSON_URLDETAILS));
			if(!jArr.isNull(JSON_HOMEPOS)) setHomePos(jArr.getInt(JSON_HOMEPOS));
			if(!jArr.isNull(JSON_AWAYPOS)) setAwayPos(jArr.getInt(JSON_AWAYPOS));
			if(!jArr.isNull(JSON_HOMEPOSDIF)) setHomePosDif(jArr.getInt(JSON_HOMEPOSDIF));
			if(!jArr.isNull(JSON_AWAYPOSDIF)) setAwayPosDif(jArr.getInt(JSON_AWAYPOSDIF));
			if(!jArr.isNull(JSON_STATE)) setState(State.valueOf(jArr.getString(JSON_STATE)));
		}catch(JSONException je){
			je.printStackTrace();
		}
	}
	
	@Override
	public int getScoreHome() {
		return getResultHome().equals("") ? -1 : Integer.valueOf(getResultHome());
	}
	
	@Override
	public int getScoreAway() {
		return getResultAway().equals("") ? -1 : Integer.valueOf(getResultAway());
	}

	public String getUrlDetails() {
		return urlDetails;
	}

	public void setUrlDetails(String urlDetails) {
		this.urlDetails = urlDetails;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		
		if(this.state == State.TO_START){
			setResultHome("");
			setResultAway("");
		}
	}
	
	public Division getDivision() {
		return division;
	}
	
	public void setDivision(Division division) {
		this.division = division;
	}
	
	public String getResultHome() {
		return resultHome;
	}
	
	public void setResultHome(String resultHome) {
		this.resultHome = resultHome;
	}
	
	public String getResultAway() {
		return resultAway;
	}
	
	public void setResultAway(String resultAway) {
		this.resultAway = resultAway;
	}
	
	public Integer getHomePos() {
		return homePos;
	}
	
	public void setHomePos(Integer homePos) {
		this.homePos = homePos;
	}
	
	public Integer getAwayPos() {
		return awayPos;
	}
	
	public void setAwayPos(Integer awayPos) {
		this.awayPos = awayPos;
	}

	public Integer getHomePosDif() {
		return homePosDif;
	}

	public void setHomePosDif(Integer homePosDif) {
		this.homePosDif = homePosDif;
	}

	public Integer getAwayPosDif() {
		return awayPosDif;
	}

	public void setAwayPosDif(Integer awayPosDif) {
		this.awayPosDif = awayPosDif;
	}

	@Override
	public JSONArray toJson() {
		try{			
			JSONArray jArr = super.toJson();
			
			jArr.put(JSON_RESULTHOME, getResultHome());
			jArr.put(JSON_RESULTAWAY, getResultAway());
			jArr.put(JSON_URLDETAILS, getUrlDetails());
			if(getHomePos() != null) jArr.put(JSON_HOMEPOS, String.valueOf(getHomePos()));
			if(getAwayPos() != null) jArr.put(JSON_AWAYPOS, String.valueOf(getAwayPos()));
			if(getHomePosDif() != null) jArr.put(JSON_HOMEPOSDIF, String.valueOf(getHomePosDif()));
			if(getAwayPosDif() != null) jArr.put(JSON_AWAYPOSDIF, String.valueOf(getAwayPosDif()));
			jArr.put(JSON_STATE, getState().toString());
			
			return jArr;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	public boolean hasSquadDetails(){
		return getUrlDetails() != null
				&& getDivision() == Division.FIRSTDIVISION
				&& (getState() == State.IN_PROGRESS
					|| getState() == State.FINISHED);
	}
	
	public boolean hasMatchStarted(RealTimeMatch updated){
		return getState() == State.TO_START
				&& updated.getState() == State.IN_PROGRESS;
	}
	
	public boolean isOnGoingMatch(){
		return getState() != RealTimeMatch.State.FINISHED;
	}
	
	public boolean hasScoreChanged(RealTimeMatch old){
		return !(getResultHome().equals(old.getResultHome())
				&& getResultAway().equals(old.getResultAway()));
	}
	
	@Override
	public String toString() {
		return getHome().getName()
				+ " " + (getResultHome() == null ? "" : getResultHome())
				+ " x " + (getResultAway() == null ? "" : getResultAway())
				+ " " + getAway().getName();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof RealTimeMatch){
			RealTimeMatch other = (RealTimeMatch) o;
			if(getHome().getAcronym().equals(other.getHome().getAcronym())
					&& getAway().getAcronym().equals(other.getAway().getAcronym())){
				return true;
			}
		}
		return false;
	}
	
}