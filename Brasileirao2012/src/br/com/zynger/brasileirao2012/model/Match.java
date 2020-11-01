package br.com.zynger.brasileirao2012.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.zynger.brasileirao2012.util.Constants;

public class Match implements Comparable<Match> {
	public final static Integer JSON_HOME = 0;
	private final static Integer JSON_AWAY = 1;
	private final static Integer JSON_STADIUM = 2;
	private final static Integer JSON_SCOREHOME = 3;
	private final static Integer JSON_SCOREAWAY = 4;
	private final static Integer JSON_DAYOFMONTH = 5;
	private final static Integer JSON_MONTH = 6;
	private final static Integer JSON_YEAR = 7;
	private final static Integer JSON_HOUR = 8;
	private final static Integer JSON_MINUTE = 9;
	private final static Integer JSON_TIMEZONE = 10;
	
	public final static Integer SCORE_NULL = -1;
	
	private Club home, away;
	private String stadium;
	private Integer scr_h, scr_a;
	private GregorianCalendar date;
	
	public Match() { }
	
	public Match(Club home, Club away, String stadium,
			int scr_h, int scr_a, GregorianCalendar date) {
		setHome(home);
		setAway(away);
		setStadium(stadium);
		setScore(scr_h, scr_a);
		setDate(date);
	}
	
	public Match(JSONArray jArr, TreeMap<String, Club> clubs) {
		restoreMatchData(jArr, clubs);
	}

	public Club getHome() {
		return home;
	}
	
	public void setHome(Club home) {
		this.home = home;
	}
	
	public Club getAway() {
		return away;
	}
	
	public void setAway(Club away) {
		this.away = away;
	}
	
	public String getStadium() {
		return stadium;
	}
	
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}
	
	public int getScoreHome() {
		return scr_h;
	}
	
	public void setScoreHome(int scr_h) {
		this.scr_h = scr_h;
	}
	
	public int getScoreAway() {
		return scr_a;
	}
	
	public void setScore(int home, int away){
		setScoreHome(home);
		setScoreAway(away);		
	}
	
	public void setScoreAway(int scr_a) {
		this.scr_a = scr_a;
	}
	
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
	public GregorianCalendar getDate(){
		return date;
	}
	
	public boolean isRivalry() {
		return getHome().isRival(getAway());
	}

	public Boolean isDone() {
		return getScoreHome() > -1 && getScoreAway() > -1;
	}
	
	public Boolean isScoreNull() {
		return getScoreHome() == Match.SCORE_NULL || getScoreAway() == Match.SCORE_NULL;
	}
	
	public JSONArray toJson(){
		try{
			JSONArray jArr = new JSONArray();
			jArr.put(JSON_HOME, getHome().getAcronym());
			jArr.put(JSON_AWAY, getAway().getAcronym());
			jArr.put(JSON_STADIUM, getStadium());
			jArr.put(JSON_SCOREHOME, String.valueOf(getScoreHome()));
			jArr.put(JSON_SCOREAWAY, String.valueOf(getScoreAway()));
			putJsonDateFields(jArr);
			
			return jArr;
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}

	private void putJsonDateFields(JSONArray jArr) throws JSONException {
		String year = null, month = null, dayOfMonth = null;
		String hourOfDay = null, minute = null, timezoneId = null;
		if(getDate() != null){			
			year = String.valueOf(getDate().get(Calendar.YEAR));
			month = String.valueOf(getDate().get(Calendar.MONTH));
			dayOfMonth = String.valueOf(getDate().get(Calendar.DAY_OF_MONTH));
			hourOfDay = String.valueOf(getDate().get(Calendar.HOUR_OF_DAY));
			minute = String.valueOf(getDate().get(Calendar.MINUTE));
			timezoneId = getDate().getTimeZone().getID();
		}
		
		jArr.put(JSON_YEAR, year);
		jArr.put(JSON_MONTH, month);
		jArr.put(JSON_DAYOFMONTH, dayOfMonth);
		jArr.put(JSON_HOUR, hourOfDay);
		jArr.put(JSON_MINUTE, minute);
		jArr.put(JSON_TIMEZONE, timezoneId);
	}
	
	public void restoreMatchData(JSONArray jArr, TreeMap<String, Club> clubs) {
		try{			
			setHome(clubs.get(jArr.getString(JSON_HOME)));
			setAway(clubs.get(jArr.getString(JSON_AWAY)));
			setStadium(jArr.getString(JSON_STADIUM));
			setScoreHome(jArr.getInt(JSON_SCOREHOME));
			setScoreAway(jArr.getInt(JSON_SCOREAWAY));
			setDate(restoreJsonCalendar(jArr));
		}catch(JSONException je){
			je.printStackTrace();
		}
	}

	private GregorianCalendar restoreJsonCalendar(JSONArray jArr)
			throws JSONException {
		String timezone = Constants.TIMEZONE;
		if(!jArr.isNull(JSON_TIMEZONE)){				
			timezone = jArr.getString(JSON_TIMEZONE);
		}
		
		if(jArr.isNull(JSON_YEAR) && jArr.isNull(JSON_MONTH)
				&& jArr.isNull(JSON_DAYOFMONTH)
				&& jArr.isNull(JSON_HOUR) && jArr.isNull(JSON_MINUTE)){
			return null;
		}
		
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone(timezone));
		cal.set(jArr.getInt(JSON_YEAR), jArr.getInt(JSON_MONTH), jArr.getInt(JSON_DAYOFMONTH),
				jArr.getInt(JSON_HOUR), jArr.getInt(JSON_MINUTE));
		return cal;
	}

	@Override
	public int compareTo(Match another) {
		if(another == null) return -1;
		else{
			GregorianCalendar calendar = getDate();
			GregorianCalendar anotherCalendar = another.getDate();
			if(calendar == null && anotherCalendar != null) return 1;
			else if(calendar != null && anotherCalendar == null) return -1;
			else if(calendar == null && anotherCalendar == null) return 0;
			else return calendar.compareTo(anotherCalendar);
		}
	}	
}