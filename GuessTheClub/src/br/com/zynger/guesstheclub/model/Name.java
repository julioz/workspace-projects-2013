package br.com.zynger.guesstheclub.model;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Name implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(dataType=DataType.BOOLEAN)
	private boolean main;
	
	@DatabaseField
	private String text;
	
	@DatabaseField(canBeNull = false, foreign = true)
	private Club club;
	
	public Club getClub() {
		return club;
	}
	
	public void setClub(Club club) {
		this.club = club;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}
}
