package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.web.MatchesParser;

public class GetMatchesGroupTask extends AsyncTask<Void, Void, ArrayList<Match>> {

	private Context context;
	private AsyncTaskListener listener;
	private MatchesParser parser;
	private Integer groupNum;
	
	public GetMatchesGroupTask(MatchesParser parser, AsyncTaskListener listener, Integer groupNum){
		this.parser = parser;
		this.listener = listener;
		this.context = parser.getContext();
		this.groupNum = groupNum;
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Match> doInBackground(Void... params) {
		return parser.getMatchesForGroups(groupNum);
	}
	
	@Override
	protected void onPostExecute(ArrayList<Match> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
	
}