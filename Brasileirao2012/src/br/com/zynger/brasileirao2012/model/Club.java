package br.com.zynger.brasileirao2012.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.util.ResourceManager;

public class Club implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Integer JSON_POSITION = 0;
	private final Integer JSON_POINTS = 1;
	private final Integer JSON_PLAYED = 2;
	private final Integer JSON_WIN = 3;
	private final Integer JSON_DRAW = 4;
	private final Integer JSON_LOSE = 5;
	private final Integer JSON_GOALSPRO = 6;
	private final Integer JSON_GOALSAGAINST = 7;
	private final Integer JSON_BALANCE = 8;
	private final Integer JSON_FANS = 9;
	private final Integer JSON_NAME = 10;
	private final Integer JSON_ACRONYM = 11;
	private final Integer JSON_BADGESTRING = 12;
	
	private String name, fullName, wiki, acronym;
	private String badge;
	private ArrayList<Trophy> trophies;
	private HashSet<String> rivals;
	private AprovData aprov;
	private Division division;
	private Integer position = 0;
	private int posDif;
	private Integer fans = 0;
	private Integer points = 0, played = 0, win = 0, draw = 0, lose = 0, goalspro = 0, goalsagainst = 0, balance = 0;
	
	private MatchesParticipation matchesParticipation;
	
	public Club(){}
	
	public Club(String name, String acronym, String badge, Division division, ArrayList<Trophy> trophies, String fullName, String wiki){
		init(name, acronym, badge, division, trophies, fullName, wiki);
	}
	
	private void init(String name, String acronym, String badge, Division division, ArrayList<Trophy> trophies, String fullName, String wiki){
		setName(name);
		setAcronym(acronym);
		setBadge(badge);
		setDivision(division);
		setTrophies(trophies);
		setAprov(new AprovData());
		setFullName(fullName);
		setWiki(wiki);
	}

	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getPlayed() {
		return played;
	}

	public void setPlayed(Integer played) {
		this.played = played;
	}

	public Integer getWin() {
		return win;
	}

	public void setWin(Integer win) {
		this.win = win;
	}
	
	public void setPosDif(int posDif) {
		this.posDif = posDif;
	}
	
	public int getPosDif() {
		return posDif;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getLose() {
		return lose;
	}

	public void setLose(Integer lose) {
		this.lose = lose;
	}

	public Integer getGoalsPro() {
		return goalspro;
	}

	public void setGoalsPro(Integer goalspro) {
		this.goalspro = goalspro;
	}

	public Integer getGoalsAgainst() {
		return goalsagainst;
	}

	public void setGoalsAgainst(Integer goalsagainst) {
		this.goalsagainst = goalsagainst;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}
	
	public int getBadgeResource(Context context){
		return ResourceManager.getInstance().getResourceFromIdentifier(context, ResourceManager.PATH_DRAWABLE, getBadge());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Trophy> getTrophies() {
		return trophies;
	}

	public void setTrophies(ArrayList<Trophy> trophies) {
		this.trophies = trophies;
	}
	
	public HashSet<String> getRivals() {
		return rivals;
	}
	
	public void setRivals(HashSet<String> rivals) {
		this.rivals = rivals;
	}
	
	public boolean isRival(Club other) {
		return rivals.contains(other.getAcronym());
	}
	
	public AprovData getAprov() {
		return aprov;
	}
	
	public void setAprov(AprovData aprov) {
		this.aprov = aprov;
	}
	
	public void clone(Club c){
		setPosition(c.getPosition());
		setPoints(c.getPoints());
		setPlayed(c.getPlayed());
		setWin(c.getWin());
		setDraw(c.getDraw());
		setLose(c.getLose());
		setGoalsPro(c.getGoalsPro());
		setGoalsAgainst(c.getGoalsAgainst());
		setBalance(c.getBalance());
		setPosDif(c.getPosDif());
		setAprov(c.getAprov());
		setFullName(c.getFullName());
		setWiki(c.getWiki());
		
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}
	
	public MatchesParticipation getMatchesParticipation() {
		return matchesParticipation;
	}
	
	public void setMatchesParticipation(
			MatchesParticipation matchesParticipation) {
		this.matchesParticipation = matchesParticipation;
		matchesParticipation.setClubAcronym(getAcronym());
	}
	
	public String getToastDescription(Context context){
		String s = getName();
		int position = getPosition();
		int points = getPoints();
		
		if(position > 0) s += "\n" + position + context.getString(R.string.model_club_place);
		if(points > 0) s += ": " + points + " " + context.getString(R.string.model_club_points);
		
		return s;
	}
	
	@Override
	public String toString() {
		return getAcronym();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other != null){
			if(other instanceof Club){			
				return ((Club) other).getAcronym().equals(getAcronym());
			}
		}
		return false;
	}
	
	public JSONArray toRankingJson() {
		try{
			JSONArray array = new JSONArray();
			array.put(JSON_POSITION, getPosition().toString());
			array.put(JSON_PLAYED, getPlayed().toString());
			array.put(JSON_POINTS, getPoints().toString());
			array.put(JSON_WIN, getWin().toString());
			array.put(JSON_DRAW, getDraw().toString());
			array.put(JSON_LOSE, getLose().toString());
			array.put(JSON_GOALSPRO, getGoalsPro().toString());
			array.put(JSON_GOALSAGAINST, getGoalsAgainst().toString());
			array.put(JSON_BALANCE, getBalance().toString());
			return array;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	public JSONArray toThirdDivisionJson() {
		try{			
			JSONArray array = new JSONArray();
			array.put(JSON_NAME, getName());
			array.put(JSON_ACRONYM, getAcronym());
			array.put(JSON_BADGESTRING, getBadge());
			array.put(JSON_POSITION, getPosition().toString());
			array.put(JSON_PLAYED, getPlayed().toString());
			array.put(JSON_POINTS, getPoints().toString());
			array.put(JSON_WIN, getWin().toString());
			array.put(JSON_DRAW, getDraw().toString());
			array.put(JSON_LOSE, getLose().toString());
			array.put(JSON_GOALSPRO, getGoalsPro().toString());
			array.put(JSON_GOALSAGAINST, getGoalsAgainst().toString());
			array.put(JSON_BALANCE, getBalance().toString());
			return array;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	public void setRankingFromJson(JSONArray array) {
		try{			
			setPosition(array.getInt(JSON_POSITION));
			setPlayed(array.getInt(JSON_PLAYED));
			setPoints(array.getInt(JSON_POINTS));
			setWin(array.getInt(JSON_WIN));
			setDraw(array.getInt(JSON_DRAW));
			setLose(array.getInt(JSON_LOSE));
			setGoalsPro(array.getInt(JSON_GOALSPRO));
			setGoalsAgainst(array.getInt(JSON_GOALSAGAINST));
			setBalance(array.getInt(JSON_BALANCE));
			
			if(!array.isNull(JSON_NAME)) setName(array.getString(JSON_NAME));
			if(!array.isNull(JSON_ACRONYM)) setAcronym(array.getString(JSON_ACRONYM));
			if(!array.isNull(JSON_BADGESTRING)) setBadge(array.getString(JSON_BADGESTRING));
		}catch(JSONException je){
			je.printStackTrace();
		}
	}
	
	public JSONArray toFansJson() {
		try{			
			JSONArray array = new JSONArray();
			array.put(JSON_FANS, getFans().toString());
			return array;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	public void setFansFromJson(JSONArray array) {
		try{			
			setFans(array.getInt(JSON_FANS));
		}catch(JSONException je){
			je.printStackTrace();
		}
	}
}