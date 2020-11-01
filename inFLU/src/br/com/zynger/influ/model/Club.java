package br.com.zynger.influ.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Club implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String goalkeeper;
	private ArrayList<String> defenders;
	private ArrayList<String> midfielders;
	private ArrayList<String> forwards;
	private String coach;
	
	public Club(String name, String goalkeeper, ArrayList<String> defenders,
			ArrayList<String> midfielders, ArrayList<String> forwards, String coach) {
		setName(name);
		setGoalkeeper(goalkeeper);
		setDefenders(defenders);
		setMidfielders(midfielders);
		setForwards(forwards);
		setCoach(coach);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoalkeeper() {
		return goalkeeper;
	}

	public void setGoalkeeper(String goalkeeper) {
		this.goalkeeper = goalkeeper;
	}

	public ArrayList<String> getDefenders() {
		return defenders;
	}

	public void setDefenders(ArrayList<String> defenders) {
		this.defenders = defenders;
	}

	public ArrayList<String> getMidfielders() {
		return midfielders;
	}

	public void setMidfielders(ArrayList<String> midfielders) {
		this.midfielders = midfielders;
	}

	public ArrayList<String> getForwards() {
		return forwards;
	}

	public void setForwards(ArrayList<String> forwards) {
		this.forwards = forwards;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}
}
