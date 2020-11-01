package br.com.zynger.brasileirao2012.asynctasks;

import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class GetRankingTask extends AsyncTask<Void, Void, TreeMap<String, Club>> {
	private TreeMap<String, Club> clubs;
	private Division division;
	private AsyncTaskListener<TreeMap<String, Club>> listener;

	public GetRankingTask(AsyncTaskListener<TreeMap<String, Club>> listener, Division division){
		this.listener = listener;
		this.clubs = ClubsDB.getInstance(listener.getContext()).getClubs();
		this.division = division;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected TreeMap<String, Club> doInBackground(Void... arg0) {
		try{
			return HTMLManager.getRanking(clubs, division);
		}catch(Exception e){
			return null;
		}catch(OutOfMemoryError e){
			return null;
		}
	}

	@Override
	protected void onPostExecute(TreeMap<String, Club> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(listener.getContext().getString(R.string.message_errorupdatefail));		
		super.onPostExecute(result);
	}
}