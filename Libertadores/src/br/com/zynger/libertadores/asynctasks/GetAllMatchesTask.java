package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.web.MatchesParser;

public class GetAllMatchesTask extends AsyncTask<Void, Void, HashMap<Integer, ArrayList<Match>>> {

	private Context context;
	private AsyncTaskListener listener;
	private MatchesParser parser;
	
	public GetAllMatchesTask(MatchesParser parser, AsyncTaskListener listener){
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
	protected HashMap<Integer, ArrayList<Match>> doInBackground(Void... params) {
		return parser.getAllMatches();
	}
	
	@Override
	protected void onPostExecute(HashMap<Integer, ArrayList<Match>> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
	
}