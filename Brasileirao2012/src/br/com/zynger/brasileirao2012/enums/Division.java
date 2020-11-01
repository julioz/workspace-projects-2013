package br.com.zynger.brasileirao2012.enums;

import android.content.Context;
import br.com.zynger.brasileirao2012.R;

public enum Division {
	FIRSTDIVISION(R.drawable.ic_firstdivisionlogo, R.string.firstdivision, "A"),
	SECONDDIVISION(R.drawable.ic_seconddivisionlogo, R.string.seconddivision, "B");
	
	private int logoRes;
	private int nameRes;
	private String notation;
	private Division(int logoRes, int nameRes, String notation) {
		this.logoRes = logoRes;
		this.nameRes = nameRes;
		this.notation = notation;
	}
	
	public String getNotation() {
		return notation;
	}
	
	public int getLogoRes() {
		return logoRes;
	}
	
	public int getNameRes() {
		return nameRes;
	}
	
	public static Division getDivisionByName(Context context, String name){
		for (Division division : Division.values()) {			
			if(name.equals(context.getString(division.getNameRes()))){
				return division;
			}
		}
		return null;
	}
	
	public Division getOppositeDivision(){
		if(this == FIRSTDIVISION) return SECONDDIVISION;
		else return FIRSTDIVISION;
	}
}