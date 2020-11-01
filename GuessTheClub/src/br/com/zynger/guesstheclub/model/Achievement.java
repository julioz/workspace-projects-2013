package br.com.zynger.guesstheclub.model;

import java.io.Serializable;

import br.com.zynger.guesstheclub.util.AchievementTypeEnum;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Achievement implements Serializable, Comparable<Achievement>  {
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private Integer points;
	
	@DatabaseField
	private Boolean done;
	
	@DatabaseField
	private String description;
	
	@DatabaseField
	private Phase phase;
	private Integer constantPhase;
	
	@DatabaseField
	private AchievementTypeEnum type;
	
	public Achievement(String name, Integer points, String description, AchievementTypeEnum type) {
		super();
		this.name = name;
		this.points = points;
		this.description = description;
		this.done = false;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Achievement arg0) {
		return this.getPoints().compareTo(arg0.getPoints());
	}
	public Phase getPhase() {
		return phase;
	}
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	public AchievementTypeEnum getType() {
		return type;
	}
	public void setType(AchievementTypeEnum type) {
		this.type = type;
	}
	public Integer getConstantPhase() {
		return constantPhase;
	}
	public void setConstantPhase(Integer constantPhase) {
		this.constantPhase = constantPhase;
	}
}
