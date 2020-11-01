package br.com.zynger.influ.model;

import java.io.Serializable;
import java.util.Date;

public class FutureGame implements Comparable<FutureGame>, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String home, away, score = null, stadium;
	private Date date;
	
	public FutureGame(String home, String away, String stadium, int day, int month, int year, int hour, int minute) {
		setHome(home);
		setAway(away);
		setStadium(stadium);
		setDate(new Date(year, month-1, day, hour, minute));
	}
	
	public FutureGame(String home, String away, String score, String stadium, int day, int month, int year, int hour, int minute) {
		setHome(home);
		setAway(away);
		setScore(score);
		setStadium(stadium);
		setDate(new Date(year, month-1, day, hour, minute));
	}
	
	public FutureGame(String home, String away, String stadium, Date date) {
		setHome(home);
		setAway(away);
		setStadium(stadium);
		setDate(date);
	}
	
	public String getHome() {
		return home;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public String getAway() {
		return away;
	}
	
	public void setAway(String away) {
		this.away = away;
	}
	
	public String getStadium() {
		return stadium;
	}
	
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(FutureGame another) {
		return getDate().compareTo(another.getDate());
	}

	@Override
	public String toString() {
		return "FutureGame [home=" + home + ", away=" + away + ", score="
				+ score + ", stadium=" + stadium + ", date=" + date + "]";
	}

	public String getScore() {
		if(score == null) return "";
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	

}
