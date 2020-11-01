package br.com.zynger.brasileirao2012.asynctasks;

import java.util.ArrayList;
import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.RealTimeMatch;
import br.com.zynger.brasileirao2012.rest.RealTimeREST;

public class GetUpdatedRealTimeMatchTask extends
		AsyncTask<Void, Void, RealTimeMatch> {
	private String home, away;
	private Division division;
	private RealTimeREST connector;

	public GetUpdatedRealTimeMatchTask(TreeMap<String, Club> clubs,
			RealTimeMatch realTimeMatch) {
		this.home = realTimeMatch.getHome().getAcronym();
		this.away = realTimeMatch.getAway().getAcronym();
		this.division = realTimeMatch.getDivision();
		connector = new RealTimeREST(clubs);
	}

	@Override
	protected RealTimeMatch doInBackground(Void... params) {
		try {
			ArrayList<RealTimeMatch> matches = connector.getRealTimeMatches(
					division, Boolean.valueOf(false));

			if (matches == null)
				return null;

			for (RealTimeMatch realTimeMatch : matches) {
				if (realTimeMatch.getHome().getAcronym().equals(home)
						|| realTimeMatch.getAway().getAcronym().equals(away)) {
					return realTimeMatch;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}
}