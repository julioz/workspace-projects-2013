package br.com.zynger.guesstheclub.model;

import java.io.Serializable;
import java.util.Collection;

import android.content.Context;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Phase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private String title;
	
	@DatabaseField
	private int constantPhase;
	
	@DatabaseField(defaultValue = "0")
	private int numberOfLogos;
	
	@DatabaseField(defaultValue = "0")
	private int discoveredLogos;
	
	@DatabaseField(defaultValue = "0")
	private int tipsUsed;
	
	@ForeignCollectionField(eager = false)
	private Collection<Club> clubs;
	
	public Phase(){};
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getNumberOfLogos() {
		return numberOfLogos;
	}
	public void setNumberOfLogos(int numberOfLogos) {
		this.numberOfLogos = numberOfLogos;
	}

	public int getDiscoveredLogos() {
		return discoveredLogos;
	}

	public void setDiscoveredLogos(int discoveredLogos) {
		this.discoveredLogos = discoveredLogos;
	}

	public int getConstantPhase() {
		return constantPhase;
	}

	public void setConstantPhase(int constantPhase) {
		this.constantPhase = constantPhase;
	}

	public Collection<Club> getClubs() {
		return clubs;
	}

	public void setClubs(Collection<Club> clubs) {
		this.clubs = clubs;
	}

	public int getId() {
		return this.id;
	}

	public int getTipsUsed() {
		return tipsUsed;
	}

	public void setTipsUsed(int tipsUsed) {
		this.tipsUsed = tipsUsed;
	}

}
