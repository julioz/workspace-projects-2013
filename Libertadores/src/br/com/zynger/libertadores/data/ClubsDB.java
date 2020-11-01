package br.com.zynger.libertadores.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import android.content.Context;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.util.PreLibEnum;
import br.com.zynger.libertadores.xml.ClubsParser;

public class ClubsDB {
	private static ClubsDB ref;
	private TreeMap<String, Club> clubs;
	private HashMap<PreLibEnum, ArrayList<Club>> preLib;
	private Club myClub;

	private ClubsDB(Context context){
		preLib = new HashMap<PreLibEnum, ArrayList<Club>>();
		clubs = generateClubs(context);
	}

	private TreeMap<String, Club> generateClubs(Context context) {
		TreeMap<String, Club> tm = new TreeMap<String, Club>();
		ArrayList<Club> clubs = new ClubsParser().getClubs(context);
		for (Club club : clubs) {
			tm.put(club.getAcronym(), club);
			
			if(null != club.getPreLibGroup()){
				ArrayList<Club> preLibFixture = getPreLib().get(club.getPreLibGroup());
				if(null == preLibFixture) preLibFixture = new ArrayList<Club>();
				preLibFixture.add(club);
				getPreLib().put(club.getPreLibGroup(), preLibFixture);
			}
			
		}
		return tm;
	}

	public static ClubsDB getSingletonObject(Context context){
		if (ref == null) ref = new ClubsDB(context);
		return ref;
	}
	
	public TreeMap<String, Club> getClubs() {
		return clubs;
	}
	
	public Club getMyClub() {
		return myClub;
	}
	
	public void setMyClub(Club myClub) {
		this.myClub = myClub;
	}
	
	public HashMap<PreLibEnum, ArrayList<Club>> getPreLib() {
		return preLib;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
