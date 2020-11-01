package br.com.zynger.influ.model;

import android.graphics.drawable.Drawable;

public class Player {
	String name;
	Drawable image;
	int position;
	int number;
	String completeName;
	String naturality;
	String born;
	String heightWeight;
	String arrival;
	String lastClub;
	String description;
	
	public Player(String name, Drawable image, String completeName, int position, int number, String naturality, String born, String heightWeight,
			String arrival, String lastClub, String description){
		setName(name);
		setImage(image);
		setCompleteName(completeName);
		setPosition(position);
		setNumber(number);
		setNaturality(naturality);
		setBorn(born);
		setHeightWeight(heightWeight);
		setArrival(arrival);
		setLastClub(lastClub);
		setDescription(description);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public String getPosition() {
		if(position == 0) return "Goleiro";
		else if(position == 1) return "Zagueiro";
		else if(position == 2) return "Lateral";
		else if(position == 3) return "Volante";
		else if(position == 4) return "Meia";
		else if(position == 5) return "Atacante";
		return "Desconhecido";
	}
	
	public String getNumber() {
		return String.valueOf(number);
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getNaturality() {
		return naturality;
	}
	
	public void setNaturality(String naturality) {
		this.naturality = naturality;
	}
	
	public String getBorn() {
		return born;
	}
	
	public void setBorn(String born) {
		this.born = born;
	}
	
	public String getHeightWeight() {
		return heightWeight;
	}
	
	public void setHeightWeight(String heightWeight) {
		this.heightWeight = heightWeight;
	}
	
	public String getArrival() {
		return arrival;
	}
	
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	
	public String getLastClub() {
		return lastClub;
	}
	
	public void setLastClub(String lastClub) {
		this.lastClub = lastClub;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
