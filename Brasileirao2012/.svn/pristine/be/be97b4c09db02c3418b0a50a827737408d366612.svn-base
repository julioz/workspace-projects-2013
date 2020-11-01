package br.com.zynger.brasileirao2012.asynctasks;

import java.util.ArrayList;
import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.RealTimeMatch;
import br.com.zynger.brasileirao2012.rest.RealTimeREST;

public class GetRealTimeMatchesTask extends AsyncTask<Void, Void, ArrayList<RealTimeMatch>> {
	
	private Division division;
	private Boolean showClubsPositions;
	private TreeMap<String, Club> clubs;
	private AsyncTaskListener<ArrayList<RealTimeMatch>> listener;
	
	public GetRealTimeMatchesTask(AsyncTaskListener<ArrayList<RealTimeMatch>> listener, Division division,
			Boolean showClubsPositions){
		this.division = division;
		this.showClubsPositions = showClubsPositions;
		this.listener = listener;
		this.clubs = ClubsDB.getInstance(listener.getContext()).getClubs();
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}
	
	@Override
	protected ArrayList<RealTimeMatch> doInBackground(Void... params) {
		return new RealTimeREST(clubs).getRealTimeMatches(division,
				showClubsPositions.booleanValue());
	}
	
	@Override
	protected void onPostExecute(ArrayList<RealTimeMatch> result) {
		if(result != null){
			listener.onComplete(result);
		}else{
			listener.onFail(listener.getContext().getString(R.string.message_errortryagain));
		}
		super.onPostExecute(result);
	}
}