package br.com.zynger.libertadores.asynctasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.MatchDetails;
import br.com.zynger.libertadores.web.MatchDetailsParser;

public class GetMatchDetailsTask extends AsyncTask<Void, Void, MatchDetails> {

	private Context context;
	private AsyncTaskListener listener;
	private MatchDetailsParser parser;
	
	public GetMatchDetailsTask(MatchDetailsParser parser, AsyncTaskListener listener){
		this.parser = parser;
		this.listener = listener;
		this.context = parser.getContext();
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected MatchDetails doInBackground(Void... params) {
		try {
			return parser.getMatchDetails();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(MatchDetails moves) {
		if(null != moves) listener.onComplete(moves);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(moves);
	}
	
	public Context getContext() {
		return context;
	}
}
