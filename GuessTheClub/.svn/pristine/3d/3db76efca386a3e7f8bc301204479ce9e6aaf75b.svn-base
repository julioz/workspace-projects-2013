package br.com.zynger.guesstheclub.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(defaultValue = "2")
	private int remainingTips;
	
	private int totalTips;
	
	@DatabaseField(defaultValue = "0")
	private int attemptsNumber;
	
	public User(){
		remainingTips = totalTips = 2;
		attemptsNumber = 0;
	}

	public int getRemainingTips() {
		return remainingTips;
	}

	public void setRemainingTips(int remainingTips) {
		this.remainingTips = remainingTips;
	}

	public int getAttemptsNumber() {
		return attemptsNumber;
	}

	public void setAttemptsNumber(int attemptsNumber) {
		this.attemptsNumber = attemptsNumber;
	}

	public int getTotalTips() {
		return totalTips;
	}

	public void setTotalTips(int totalTips) {
		this.totalTips = totalTips;
	}
}
