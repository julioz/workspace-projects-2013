package br.com.zynger.brasileirao2012.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import android.content.Context;
import android.util.SparseArray;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Fixture;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.xml.MatchesParser;

public class MatchesDB {
	private static MatchesDB ref;
	private TreeMap<Integer, Fixture> matchesA, matchesB;
	
	private MatchesDB(Context context, TreeMap<String, Club> clubs){
		matchesA = genMatches(context, clubs, Division.FIRSTDIVISION);
		matchesB = genMatches(context, clubs, Division.SECONDDIVISION);
	}
	
	public static MatchesDB getInstance(Context context, TreeMap<String, Club> clubs){
		if (ref == null) ref = new MatchesDB(context, clubs);
		return ref;
	}
	
	public TreeMap<Integer, Fixture> getMatches(Division division){
		if(division == Division.SECONDDIVISION) return getMatchesB();
		else return getMatchesA();
	}

	private TreeMap<Integer, Fixture> genMatches(Context context,
			TreeMap<String, Club> clubs, Division division){
		return new MatchesParser().getMatches(context, clubs, division);
	}
	
	public TreeMap<Integer, Fixture> getMatchesA() {
		return matchesA;
	}
	
	public TreeMap<Integer, Fixture> getMatchesB() {
		return matchesB;
	}
	
	public Fixture getFixtureBasedOnCalendar(Division division) {
		GregorianCalendar today = new GregorianCalendar();
		
		Integer fixtureNumber = 1;
		TreeMap<Integer, Fixture> matches = getMatches(division);
		SparseArray<GregorianCalendar> dates = getDates(division);
		for(int keyIndex = 0; keyIndex < dates.size(); keyIndex++) {
			Integer fixture = dates.keyAt(keyIndex);
			fixtureNumber = fixture;
			GregorianCalendar calendar = dates.get(fixture);
			if (!today.after(calendar)) {
				Integer previousFixture = fixture - 1;
				if (previousFixture >= 1
						&& !matches.get(previousFixture).isDone()) {
					fixtureNumber--;
				}
				break;
			}
		}
		return matches.get(fixtureNumber);
	}
	
	public void replaceFixture(Division division, Fixture fixture){
		TreeMap<Integer, Fixture> map = getMatches(division);
		if(map.keySet().contains(fixture.getNumber())){
			map.put(fixture.getNumber(), fixture);
		}
	}
    
	public SparseArray<GregorianCalendar> getDates(Division division){
		SparseArray<GregorianCalendar> sparseArray = new SparseArray<GregorianCalendar>();
		TreeMap<Integer, Fixture> map = getMatches(division);
		
		for (int j = 1; j <= map.size(); j++) {
			try{
				ArrayList<Match> matches = map.get(j).getMatches();
				Collections.sort(matches);
				sparseArray.put(j, matches.get(0).getDate());
			}
			catch(NullPointerException e) {}
			catch(IndexOutOfBoundsException e) {}
		}
		
		return sparseArray;
	}
}