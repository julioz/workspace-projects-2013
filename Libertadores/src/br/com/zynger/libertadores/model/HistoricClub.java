package br.com.zynger.libertadores.model;

import java.util.ArrayList;

public class HistoricClub extends Club {

	private ArrayList<String> won;
	private ArrayList<String> runnerUp;
	
	public HistoricClub(String badge) {
		setBadge(badge);
		this.won = new ArrayList<String>();
		this.runnerUp = new ArrayList<String>();
	}
	
	public HistoricClub() {
		this.won = new ArrayList<String>();
		this.runnerUp = new ArrayList<String>();
	}
	
	public ArrayList<String> getWon() {
		return won;
	}
	
	public void setWon(ArrayList<String> won) {
		this.won = won;
	}
	
	public ArrayList<String> getRunnerUp() {
		return runnerUp;
	}
	
	public void setRunnerUp(ArrayList<String> runnerUp) {
		this.runnerUp = runnerUp;
	}
}
