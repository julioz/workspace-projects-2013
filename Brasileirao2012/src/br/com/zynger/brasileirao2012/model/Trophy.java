package br.com.zynger.brasileirao2012.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.zynger.brasileirao2012.enums.TrophyRegion;

public class Trophy implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String type;
	private TrophyRegion region;
	private ArrayList<Integer> times;
	
	public Trophy(String type, TrophyRegion region, ArrayList<Integer> times){
		setType(type);
		setRegion(region);
		setTimes(times);
	}

	public Trophy() { }

	public ArrayList<Integer> getTimes() {
		return times;
	}

	public void setTimes(ArrayList<Integer> times) {
		this.times = times;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TrophyRegion getRegion() {
		return region;
	}

	public void setRegion(TrophyRegion region) {
		this.region = region;
	}
}