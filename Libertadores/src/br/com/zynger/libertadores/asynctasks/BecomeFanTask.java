package br.com.zynger.libertadores.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.web.FansREST;

public class BecomeFanTask extends AsyncTask<Void, Void, String> {
	private Context context;
	private String clubAcronym;

	public BecomeFanTask(Context context, String clubAcronym){
		setContext(context);
		setClubAcronym(clubAcronym);
	}

	@Override
	protected String doInBackground(Void... params) {
		return FansREST.becomeFan(getContext(), getClubAcronym());
	}

	@Override
	protected void onPostExecute(String result) {
		if(result != null) Toast.makeText(getContext(), getContext().getString(R.string.becomefan_async_success), Toast.LENGTH_SHORT).show();
		else Toast.makeText(getContext(), getContext().getString(R.string.becomefan_async_fail), Toast.LENGTH_SHORT).show();
		super.onPostExecute(result);
	}

	private void setClubAcronym(String myteam) {
		this.clubAcronym = myteam;
	}

	private String getClubAcronym() {
		return this.clubAcronym;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}