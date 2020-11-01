package br.com.zynger.libertadores.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.enums.Country;
import br.com.zynger.libertadores.util.GroupEnum;
import br.com.zynger.libertadores.util.PreLibEnum;
import br.com.zynger.libertadores.util.ResourceManager;

public class Club implements Comparable<Club> {
	
	private final static int JSON_POINTS = 0;
	private final static int JSON_GROUPPOSITION = 1;
	private final static int JSON_VICTORIES = 2;
	private final static int JSON_DRAWS = 3;
	private final static int JSON_LOSSES = 4;
	private final static int JSON_GOALSPRO = 5;
	private final static int JSON_GOALSAGAINST = 6;
	
	private String name, acronym;
	private Country country;
	private String badge, icon;
	private String headerUrl;
	private Integer fans;
	private Headquarters headquarters;
	
	private GroupEnum group;
	private PreLibEnum preLibGroup;
	
	private Integer groupPosition = null;
	private Integer points = 0;
	private Integer won = 0;
	private Integer drawn = 0;
	private Integer lost = 0;
	private Integer goalsFor = 0;
	private Integer goalsAgainst = 0;
	private Integer played = 0;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAcronym() {
		return acronym;
	}
	
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public String getBadge() {
		return badge;
	}
	
	public int getBadgeResource(Context context){
		return ResourceManager.getInstance().getResourceFromIdentifier(context, ResourceManager.PATH_DRAWABLE, getBadge());
	}
	
	public void setBadge(String badge) {
		this.badge = badge;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Headquarters getHeadquarters() {
		return headquarters;
	}
	
	public void setHeadquarters(Headquarters headquarters) {
		this.headquarters = headquarters;
	}
	
	public GroupEnum getGroup() {
		return group;
	}
	
	public void setGroup(GroupEnum group) {
		this.group = group;
	}
	
	public PreLibEnum getPreLibGroup() {
		return preLibGroup;
	}
	
	public void setPreLibGroup(PreLibEnum prelib) {
		this.preLibGroup = prelib;
	}
	
	public Integer getFans() {
		return fans;
	}
	
	public void setFans(Integer fans) {
		this.fans = fans;
	}
	
	public Integer getGroupPosition() {
		return groupPosition;
	}
	
	public void setGroupPosition(Integer groupPosition) {
		this.groupPosition = groupPosition;
	}
	
	public Integer getGamesPlayed() {
		return played;
	}
	
	public void setGamesPlayed(Integer gamesPlayed) {
		this.played = gamesPlayed;
	}
	
	public void initStandingsStats(){
		setGamesPlayed(0);
		setPoints(0);
		setVictories(0);
		setDraws(0);
		setLosses(0);
		setGoalsPro(0);
		setGoalsAgainst(0);
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getVictories() {
		return won;
	}

	public void setVictories(Integer victories) {
		this.won = victories;
	}

	public Integer getDraws() {
		return drawn;
	}

	public void setDraws(Integer draws) {
		this.drawn = draws;
	}

	public Integer getLosses() {
		return lost;
	}

	public void setLosses(Integer losses) {
		this.lost = losses;
	}

	public Integer getGoalsPro() {
		return goalsFor;
	}

	public void setGoalsPro(Integer goalsPro) {
		this.goalsFor = goalsPro;
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
	
	public Integer getBalance() {
		return getGoalsPro() - getGoalsAgainst();
	}

	@Override
	public String toString() {
		return name + ", " + acronym + " - " + headquarters;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	@Override
	public int compareTo(Club another) {
		return getName().compareTo(another.getName());
	}
	
	public JSONArray toJson(){
		try{			
			JSONArray data = new JSONArray();
			data.put(JSON_POINTS, getPoints());
			data.put(JSON_GROUPPOSITION, getGroupPosition());
			data.put(JSON_VICTORIES, getVictories());
			data.put(JSON_DRAWS, getDraws());
			data.put(JSON_LOSSES, getLosses());
			data.put(JSON_GOALSPRO, getGoalsPro());
			data.put(JSON_GOALSAGAINST, getGoalsAgainst());
			
			return data;
		}catch(JSONException e){
			Log.e(HomeActivity.TAG, e.toString());
			return null;
		}
	}
	
	public Boolean setFromJson(JSONObject object){
		try {
			JSONArray data = object.getJSONArray(getAcronym());
			
			setPoints(data.getInt(JSON_POINTS));
			setGroupPosition(data.getInt(JSON_GROUPPOSITION));
			setVictories(data.getInt(JSON_VICTORIES));
			setDraws(data.getInt(JSON_DRAWS));
			setLosses(data.getInt(JSON_LOSSES));
			setGoalsPro(data.getInt(JSON_GOALSPRO));
			setGoalsAgainst(data.getInt(JSON_GOALSAGAINST));
			setGamesPlayed(Integer.valueOf(getVictories() + getDraws() + getLosses()));
			
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
}
