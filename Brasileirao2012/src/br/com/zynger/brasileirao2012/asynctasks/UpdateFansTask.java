package br.com.zynger.brasileirao2012.asynctasks;

import java.util.TreeMap;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.rest.FansREST;

public class UpdateFansTask extends AsyncTask<Void, Void, TreeMap<String, Club>> {
	private Context context;
	private AsyncTaskListener<TreeMap<String, Club>> listener;
	private TreeMap<String, Club> clubs;
	private Club myClub;
	
	public UpdateFansTask(AsyncTaskListener<TreeMap<String, Club>> listener){
		this.context = listener.getContext();
		this.listener = listener;
		ClubsDB clubsDB = ClubsDB.getInstance(context);
		this.clubs = clubsDB.getClubs();
		this.myClub = clubsDB.getMyClub();
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}
	
	@Override
	protected TreeMap<String, Club> doInBackground(Void... params) {
		return new FansREST().updateFansData(context, clubs, myClub.getAcronym());
	}

	@Override
	protected void onPostExecute(TreeMap<String, Club> result) {
		if(result != null){
			listener.onComplete(result);
		}else listener.onFail(context.getString(R.string.message_errorupdatefail));
		super.onPostExecute(result);
	}
}
