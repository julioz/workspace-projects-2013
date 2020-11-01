package br.com.zynger.influ.model;

import java.io.Serializable;

public class StatPlayer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean goalkeeper = false;
	private int played, goals, yCards, rCards;

	public StatPlayer(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlayed() {
		return played;
	}

	public void setPlayed(int played) {
		this.played = played;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getyCards() {
		return yCards;
	}

	public void setyCards(int yCards) {
		this.yCards = yCards;
	}

	public int getrCards() {
		return rCards;
	}

	public void setrCards(int rCards) {
		this.rCards = rCards;
	}

	@Override
	public String toString() {
		return this.name+", "+this.played+", "+this.goals+", "+this.yCards+", "+this.rCards;
	}

	public void setGoalkeeper(boolean goalkeeper) {
		this.goalkeeper = goalkeeper;
	}

	public boolean isGoalkeeper() {
		return goalkeeper;
	}
}
