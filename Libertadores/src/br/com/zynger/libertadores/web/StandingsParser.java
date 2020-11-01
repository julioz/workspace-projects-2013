package br.com.zynger.libertadores.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Group;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.util.ClubComparatorByPoints;
import br.com.zynger.libertadores.util.GroupEnum;
import br.com.zynger.libertadores.util.InternalStorageHandler;

public class StandingsParser {
	private TreeMap<String, Club> clubs;
	private TreeMap<GroupEnum, Group> groups;
	private InternalStorageHandler internalStorage;

	public StandingsParser(TreeMap<String, Club> clubs,
			TreeMap<GroupEnum, Group> groups, InternalStorageHandler internalStorage) {
		setClubs(clubs);
		setGroups(groups);
		this.internalStorage = internalStorage;
	}

	public Boolean updateStandings(){
		for (GroupEnum gEnum : GroupEnum.values()) {
			String matchesJson = (String) internalStorage.openMatchesArray(gEnum.toString());
			if(null == matchesJson) continue;

			Group group = getGroups().get(gEnum);
			for (Club club : group.getClubs()) {
				club.initStandingsStats();				
			}

			if(!readMatchesScores(matchesJson)) return false;

			Collections.sort(group.getClubs(), new ClubComparatorByPoints());
			for (int i = 0; i < group.getClubs().size(); i++) {
				group.getClubs().get(i).setGroupPosition(Integer.valueOf(i+1));
			}
		}
		return true;
	}

	private Boolean readMatchesScores(String matchesJson){
		try{
			ArrayList<Match> matches = new ArrayList<Match>();
			JSONArray jsonMatches = new JSONArray(matchesJson);

			for (int i = 0; i < jsonMatches.length(); i++) {
				JSONArray data = jsonMatches.getJSONArray(i);
				Match match = new Match(data, clubs);
				matches.add(match);
			}

			for (Match match : matches) {
				if(match.getScoreHome() == Match.SCORE_NULL
						|| match.getScoreAway() == Match.SCORE_NULL){
					continue;
				}

				Club home = clubs.get(match.getHome().getAcronym());
				Club away = clubs.get(match.getAway().getAcronym());
				home.setGamesPlayed(home.getGamesPlayed() + 1);
				away.setGamesPlayed(away.getGamesPlayed() + 1);
				home.setGoalsPro(home.getGoalsPro() + match.getScoreHome());
				away.setGoalsPro(away.getGoalsPro() + match.getScoreAway());
				home.setGoalsAgainst(home.getGoalsAgainst() + match.getScoreAway());
				away.setGoalsAgainst(away.getGoalsAgainst() + match.getScoreHome());

				if(match.getScoreHome() > match.getScoreAway()){
					home.setVictories(home.getVictories() + 1);
					away.setLosses(away.getLosses() + 1);

					home.setPoints(home.getPoints() + 3);
				}else if(match.getScoreHome() == match.getScoreAway()){
					home.setDraws(home.getDraws() + 1);
					away.setDraws(away.getDraws() + 1);

					home.setPoints(home.getPoints() + 1);
					away.setPoints(away.getPoints() + 1);
				}else{
					home.setLosses(home.getLosses() + 1);
					away.setVictories(away.getVictories() + 1);

					away.setPoints(away.getPoints() + 3);
				}

			}

			return true;
		}catch(JSONException e){
			e.printStackTrace();
			return false;
		}
	}

	public TreeMap<String, Club> getClubs() {
		return clubs;
	}

	public void setClubs(TreeMap<String, Club> clubs) {
		this.clubs = clubs;
	}

	public void setGroups(TreeMap<GroupEnum, Group> groups) {
		this.groups = groups;
	}

	public TreeMap<GroupEnum, Group> getGroups() {
		return groups;
	}
}
