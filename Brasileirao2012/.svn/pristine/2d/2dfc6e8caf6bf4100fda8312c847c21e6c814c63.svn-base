package br.com.zynger.brasileirao2012.data;

import java.util.ArrayList;

import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Striker;

public class StrikersDB {

	private static StrikersDB ref;
	private ArrayList<Striker> strikersA, strikersB;
	
	private StrikersDB(){ }
	
	public static StrikersDB getInstance(){
		if (ref == null) ref = new StrikersDB();
		return ref;
	}
	
	public ArrayList<Striker> getStrikers(Division division){
		if(division == Division.SECONDDIVISION) return getStrikersB();
		else return getStrikersA();
	}
	
	public void setStrikers(Division division, ArrayList<Striker> strikers){
		if(division == Division.SECONDDIVISION) setStrikersB(strikers);
		else setStrikersA(strikers);
	}
	
	public boolean hasOneNullList() {
		for (Division division : Division.values()) {
			if(isListNull(division)) return true;
		}
		return false;
	}
	
	public boolean isListNull(Division division) {
		return getStrikers(division) == null;
	}
	
	public boolean isListEmpty(Division division) {
		return getStrikers(division).isEmpty();
	}
	
	public boolean hasStrikersFromClub(Club club) {
		if(isListNull(club.getDivision())) return false;
		for (Striker striker : getStrikers(club.getDivision())) {
			if(striker.getClub().getAcronym().equals(club.getAcronym())){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Striker> getStrikersFromClub(Club club) {
		if(isListNull(club.getDivision())) return null;
		
		ArrayList<Striker> strikers = new ArrayList<Striker>();
		for (Striker striker : getStrikers(club.getDivision())) {
			if(striker.getClub().getAcronym().equals(club.getAcronym())){
				strikers.add(striker);
			}
		}
		return strikers;
	}
	
	public ArrayList<Striker> getStrikersA() {
		return strikersA;
	}
	
	public ArrayList<Striker> getStrikersB() {
		return strikersB;
	}
	
	private void setStrikersA(ArrayList<Striker> strikersA) {
		this.strikersA = strikersA;
	}
	
	private void setStrikersB(ArrayList<Striker> strikersB) {
		this.strikersB = strikersB;
	}
}
