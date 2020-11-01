package br.com.zynger.influ.model;

import java.io.Serializable;

public class StatClub implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int position;
	private int points, played, win, draw, lose, goalsPro, goalsAgainst, balance, yCards, rCards;
	private float aproveit;
	
	public StatClub() {}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPlayed() {
		return played;
	}

	public void setPlayed(int played) {
		this.played = played;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getGoalsPro() {
		return goalsPro;
	}

	public void setGoalsPro(int goalsPro) {
		this.goalsPro = goalsPro;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
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

	public float getAproveit() {
		return aproveit;
	}

	public void setAproveit(float aproveit) {
		this.aproveit = aproveit;
	}

	@Override
	public String toString() {
		return "StatClub [name=" + name + ", position=" + position
				+ ", points=" + points + ", played=" + played + ", win=" + win
				+ ", draw=" + draw + ", lose=" + lose + ", goalsPro="
				+ goalsPro + ", goalsAgainst=" + goalsAgainst + ", balance="
				+ balance + ", yCards=" + yCards + ", rCards=" + rCards
				+ ", aproveit=" + aproveit + "]";
	}

	
}
