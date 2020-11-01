package br.com.zynger.brasileirao2012.asynctasks;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.rest.FansREST;

public class UpdateMyTeamWithServerTask extends AsyncTask<Void, Void, JSONObject> {
	private Context context;
	private String myClubAcronym;
	
	public UpdateMyTeamWithServerTask(Context context, String myClubAcronym){
		this.context = context;
		this.myClubAcronym = myClubAcronym;
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		return new FansREST().sendMyTeamToServer(context, myClubAcronym);
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		JSONObject json = result;
		
		String message = context.getString(R.string.sendmyteamtask_fail);
		if(json != null){
			message = context.getString(R.string.sendmyteamtask_success);
		}

		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		super.onPostExecute(result);
	}
}