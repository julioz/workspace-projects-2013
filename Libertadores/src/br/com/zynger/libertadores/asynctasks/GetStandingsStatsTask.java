package br.com.zynger.libertadores.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.web.StandingsParser;

public class GetStandingsStatsTask extends AsyncTask<Void, Void, Boolean> {

	private Context context;
	private AsyncTaskListener listener;
	private StandingsParser parser;
	
	public GetStandingsStatsTask(Context context,
			StandingsParser parser, AsyncTaskListener listener){
		this.context = context;
		this.parser = parser;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		return parser.updateStandings();
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload)
				+ "\n\n" + getContext().getString(R.string.error_verifymatches));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
	
}
