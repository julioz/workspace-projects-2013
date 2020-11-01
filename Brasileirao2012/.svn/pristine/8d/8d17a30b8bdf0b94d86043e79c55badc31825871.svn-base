package br.com.zynger.brasileirao2012.adapters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Fixture;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.view.MatchViewLayout;

public class MyTeamListAdapter extends ArrayAdapter<Match> {
	private boolean onlyRivalries;
	private List<MatchViewLayout> matchViewLayouts;
	private TreeMap<Fixture, Match> matchesMap;
	
	public MyTeamListAdapter(Context context) {
		super(context, 0);
		matchViewLayouts = new ArrayList<MatchViewLayout>();
		Club myClub = ClubsDB.getInstance(context).getMyClub();
		matchesMap = myClub.getMatchesParticipation().getMixedSets();
		for (Fixture fixture : matchesMap.keySet()) {
			Match match = matchesMap.get(fixture);
			matchViewLayouts.add(new MatchViewLayout(context, match, fixture.getNumber()));
		}
	}
	
	@Override
	public Match getItem(int position) {
		return matchViewLayouts.get(position).getMatch();
	}
	
	public void setMatchesOnAdapter(boolean onlyRivalries) {
		this.onlyRivalries = onlyRivalries;
		
		matchViewLayouts.clear();
		for (Fixture fixture : matchesMap.keySet()) {
			Match match = matchesMap.get(fixture);
			if(!onlyRivalries || match.isRivalry()){				
				matchViewLayouts.add(new MatchViewLayout(getContext(), match, fixture.getNumber()));
			}
		}
		notifyDataSetChanged();
	}
	
	public boolean isOnlyRivalries() {
		return onlyRivalries;
	}
	
	@Override
	public int getCount() {
		return matchViewLayouts.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        return matchViewLayouts.get(position);
	}

	public int getFirstFixtureWithNoScore() {
		for (Iterator<Fixture> iterator = matchesMap.keySet().iterator(); iterator.hasNext();) {
			Fixture fixture = (Fixture) iterator.next();
			Match match = matchesMap.get(fixture);
			if(!match.isDone()){
				return fixture.getNumber();
			}
		}
		return 1;
	}
}