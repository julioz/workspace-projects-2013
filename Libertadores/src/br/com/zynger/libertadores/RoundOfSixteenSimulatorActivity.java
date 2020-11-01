package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;
import br.com.zynger.libertadores.adapters.RoundOfSixteenSimulatorAdapter;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.util.ClubComparatorByPoints;
import br.com.zynger.libertadores.view.HelveticaTextView;

public class RoundOfSixteenSimulatorActivity extends ListActivity {

	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	
	private HelveticaTextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roundofsixteensimulatoractivity);
		
		clubsDB = ClubsDB.getSingletonObject(this);
		clubs = clubsDB.getClubs();
		
		loadViews();
		
		ArrayList<Match> matches = getRoundOfSixteenMatches();
		setListAdapter(new RoundOfSixteenSimulatorAdapter(this, matches));
	}
	
	private void loadViews() {
		tv_title = (HelveticaTextView) findViewById(R.roundofsixteenactivity.titlebar_tvtitle);
		tv_title.setText(getString(R.string.roundofsixteensimulatoractivity_title).toUpperCase());
	}
	
	private ArrayList<Match> getRoundOfSixteenMatches() {
		ArrayList<Club> firstPlaces = new ArrayList<Club>();
		ArrayList<Club> secondPlaces = new ArrayList<Club>();
		for (Club club : clubs.values()) {
			if(club.getGroupPosition() != null){				
				if(club.getGroupPosition() == 1) firstPlaces.add(club);
				else if(club.getGroupPosition() == 2) secondPlaces.add(club);
			}
		}
		
		if(firstPlaces.size() == 0
				|| secondPlaces.size() == 0){
			Toast.makeText(this, getString(R.string.error_verifymatches), Toast.LENGTH_LONG).show();
		}
		
		Comparator<Club> pointsComparator = new ClubComparatorByPoints();
		Collections.sort(firstPlaces, pointsComparator);
		Collections.sort(secondPlaces, pointsComparator);
		
		ArrayList<Match> matches = new ArrayList<Match>();
		for (int i = 0; i < firstPlaces.size(); i++) {
			Club home = firstPlaces.get(i);
			Club away = secondPlaces.get(secondPlaces.size() - 1 - i);
			Match m = new Match(home, away, null, null, Match.SCORE_NULL, Match.SCORE_NULL);
			matches.add(m);
		}
		
		return matches;
	}
}
