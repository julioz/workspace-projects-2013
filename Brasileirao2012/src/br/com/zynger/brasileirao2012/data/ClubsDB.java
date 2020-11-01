package br.com.zynger.brasileirao2012.data;

import java.util.ArrayList;
import java.util.TreeMap;

import android.content.Context;
import android.preference.PreferenceManager;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.xml.ClubsParser;

public class ClubsDB {
	private static final String MYCLUBKEY = "bras2013_myclub";
	
	private static ClubsDB ref;
	private TreeMap<String, Club> clubs;
	private Club myClub;

	private static Context context;
	
	private ClubsDB(Context context){
		clubs = generateClubs(context);
	}
	
	public static ClubsDB getInstance(Context context){
		ClubsDB.context = context;
		if (ref == null){
			ref = new ClubsDB(context);
		}
		return ref;
	}
	
	private TreeMap<String, Club> generateClubs(Context context) {
		TreeMap<String, Club> tm = new TreeMap<String, Club>();
		ArrayList<Club> clubs = new ClubsParser().getClubs(context);
		for (Club club : clubs) {
			tm.put(club.getAcronym(), club);			
		}
		return tm;
	}
	
	public TreeMap<String, Club> getClubs() {
		return clubs;
	}
	
	public ArrayList<Club> getClubsList(Division division){
		ArrayList<Club> list = new ArrayList<Club>();
		for (Club club : getClubs().values()) {
			if(club.getDivision() == division) list.add(club);
		}
		return list;
	}
	
	public Club getMyClub() {
		if(myClub == null){
			String acronym = PreferenceManager.getDefaultSharedPreferences(context).getString(MYCLUBKEY, null);
			if(acronym == null) return null;
			Club club = getClubs().get(acronym);
			setMyClub(context, club);
		}
		
		return myClub;
	}
	
	public void setMyClub(Context context, Club club) {
		this.myClub = club;
		if(myClub != null){
			PreferenceManager.getDefaultSharedPreferences(context).edit().putString(MYCLUBKEY, club.getAcronym()).commit();
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public boolean hasRivalsOnDivision(Club club) {
		for (Club clubInDivision : getClubsList(club.getDivision())) {
			if(club.isRival(clubInDivision)) return true;
		}
		return false;
	}
	
	public boolean hasClubWithFans() {
		for (Club club : getClubs().values()) {
			if(club.getFans().intValue() > 0){
				return true;
			}
		}
		return false;
	}

}
