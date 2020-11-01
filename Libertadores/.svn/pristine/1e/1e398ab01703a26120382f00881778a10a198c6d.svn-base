package br.com.zynger.libertadores.model;

import android.content.Context;
import br.com.zynger.libertadores.util.ResourceManager;

public class HistoricCountry implements Comparable<HistoricCountry> {
	
	public static final int NULL = -1;
	
	private String acronym;
	private String name;
	private Integer won;
	private Integer runnerUp;
	private String flag;
	
	public HistoricCountry(String acronym) {
		setAcronym(acronym);
		setWon(0);
		setRunnerUp(0);
	}
	
	public String getAcronym() {
		return acronym;
	}
	
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getWon() {
		return won;
	}
	
	public void setWon(Integer won) {
		this.won = won;
	}
	
	public Integer getRunnerUp() {
		return runnerUp;
	}
	
	public void setRunnerUp(Integer runnerUp) {
		this.runnerUp = runnerUp;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public int getFlagResource(Context context){
		return ResourceManager.getInstance().getResourceFromIdentifier(context, ResourceManager.PATH_DRAWABLE, getFlag());
	}

	@Override
	public String toString() {
		return name + " => " + won + ", " + runnerUp;
	}

	@Override
	public int compareTo(HistoricCountry another) {
		int comparison = getWon() - another.getWon();
		if(comparison == 0){
			comparison = getRunnerUp() - another.getRunnerUp();
			if(comparison == 0) comparison = another.getName().compareTo(getName());
		}
		return -comparison;
	}

}
