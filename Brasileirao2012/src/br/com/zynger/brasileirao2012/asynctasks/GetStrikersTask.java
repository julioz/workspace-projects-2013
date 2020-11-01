package br.com.zynger.brasileirao2012.asynctasks;

import java.util.ArrayList;
import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Striker;
import br.com.zynger.brasileirao2012.rest.StrikersREST;

public class GetStrikersTask extends AsyncTask<Void, Void, ArrayList<Striker>> {
	private Division division;
	private TreeMap<String, Club> clubs;
	private AsyncTaskListener<ArrayList<Striker>> listener;

	public GetStrikersTask(AsyncTaskListener<ArrayList<Striker>> listener,
			Division division) {
		this.division = division;
		this.clubs = ClubsDB.getInstance(listener.getContext()).getClubs();
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Striker> doInBackground(Void... params) {
		try {
			StrikersREST cliREST = new StrikersREST(clubs);
			return cliREST.getStrikers(division);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(ArrayList<Striker> result) {
		if (result != null) {
			listener.onComplete(result);
		} else {
			listener.onFail(listener.getContext().getString(
					R.string.message_errorupdatefail));
		}
		super.onPostExecute(result);
	}
}