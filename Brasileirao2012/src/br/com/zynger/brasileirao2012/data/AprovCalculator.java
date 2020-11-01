package br.com.zynger.brasileirao2012.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.AprovData;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Fixture;
import br.com.zynger.brasileirao2012.model.Match;

public class AprovCalculator {

	public TreeMap<String, AprovData> getAprov(TreeMap<String, Club> clubs, MatchesDB matchesDB) throws Exception {
		for (Iterator<Club> it = clubs.values().iterator(); it.hasNext();) {
			Club club = (Club) it.next();
			club.setAprov(new AprovData());
		}
		
		for (Division division : Division.values()) {
			TreeMap<Integer, Fixture> matches = matchesDB.getMatches(division);
			for (int i = 1; i <= 38; i++) {
				Fixture fixture = matches.get(i);
				ArrayList<Match> fixtureMatches = fixture.getMatches();
				for (Match match : fixtureMatches) {
					int scoreHome = match.getScoreHome();
					int scoreAway = match.getScoreAway();
					
					if(scoreHome >= 0 && scoreAway >= 0){
						if(match.getHome() == null || match.getAway() == null) continue;
						
						AprovData homeAprov = match.getHome().getAprov();
						AprovData awayAprov = match.getAway().getAprov();
						homeAprov.setHomePlayed(homeAprov.getHomePlayed() + 1);
						awayAprov.setAwayPlayed(awayAprov.getAwayPlayed() + 1);
						
						if(scoreHome > scoreAway){
							homeAprov.setHomeVictories(homeAprov.getHomeVictories() + 1);
						}else if(scoreHome < scoreAway){
							awayAprov.setAwayVictories(awayAprov.getAwayVictories() + 1);
						}else{
							homeAprov.setHomeDrawn(homeAprov.getHomeDrawn() + 1);
							awayAprov.setAwayDrawn(awayAprov.getAwayDrawn() + 1);
						}
					}
				}
			}
		}
		
		TreeMap<String, AprovData> tm = new TreeMap<String, AprovData>();
		for (Club club : clubs.values()) {
			AprovData aprov = club.getAprov();
			aprov.calcPercs();
			tm.put(club.getAcronym(), club.getAprov());
		}
		
		return tm;
	}
}
