package br.com.zynger.brasileirao2012.asynctasks;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.data.MatchesDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class GetTableTask extends AsyncTask<Void, Void, JSONObject> {
	private AsyncTaskListener<JSONObject> listener;
	private Context context;
	private MatchesDB matchesDB;
	private ClubsDB clubsDB;
	private Division division;

	public GetTableTask(AsyncTaskListener<JSONObject> listener){
		this.context = listener.getContext();
		this.clubsDB = ClubsDB.getInstance(context);
		this.matchesDB = MatchesDB.getInstance(context, clubsDB.getClubs());
		this.division = clubsDB.getMyClub().getDivision();
		this.listener = listener;
	}
	
	public GetTableTask(AsyncTaskListener<JSONObject> listener, Division division){
		this.context = listener.getContext();
		this.clubsDB = ClubsDB.getInstance(context);
		this.matchesDB = MatchesDB.getInstance(context, clubsDB.getClubs());
		this.division = division;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(Void... arg0) {
		try{				
			return HTMLManager.getTableData(context, matchesDB,
					clubsDB.getClubs(), division);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(context.getString(R.string.message_errorupdatefail));
		super.onPostExecute(result);
	}
}