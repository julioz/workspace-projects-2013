package br.com.zynger.brasileirao2012.model;

import java.util.HashMap;
import java.util.TreeMap;

public class MatchesParticipation {
	
	private String clubAcronym;
	private HashMap<Fixture, Match> homeMatches;
	private HashMap<Fixture, Match> awayMatches;
	
	public MatchesParticipation() {
		homeMatches = new HashMap<Fixture, Match>();
		awayMatches = new HashMap<Fixture, Match>();
	}
	
	public void add(Fixture fixture, Match m) {
		HashMap<Fixture, Match> map;
		if(m.getHome().getAcronym().equals(getClubAcronym())) map = homeMatches;
		else map = awayMatches;
		
		map.put(fixture, m);
	}
	
	public Match getMatch(Fixture fixture) {
		Match m = homeMatches.get(fixture);
		if(m != null) return m;
		
		m = awayMatches.get(fixture);
		if(m != null) return m;
		
		return null;
	}
	
	public TreeMap<Fixture, Match> getMixedSets() {
		TreeMap<Fixture, Match> mixedSet = new TreeMap<Fixture, Match>();
		mixedSet.putAll(homeMatches);
		mixedSet.putAll(awayMatches);
		return mixedSet;
	}
	
	public void setClubAcronym(String clubAcronym) {
		this.clubAcronym = clubAcronym;
	}
	
	public String getClubAcronym() {
		return clubAcronym;
	}

}
