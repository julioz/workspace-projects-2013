package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Tweet;
import br.com.zynger.libertadores.web.TwitterCentralParser;

public class GetTwitterCentralTask extends AsyncTask<Void, Void, ArrayList<Tweet>> {

	private Context context;
	private AsyncTaskListener listener;
	private TwitterCentralParser parser;
	
	public GetTwitterCentralTask(TwitterCentralParser parser, AsyncTaskListener listener){
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
	protected ArrayList<Tweet> doInBackground(Void... params) {
		return parser.getTweets();
	}

	@Override
	protected void onPostExecute(ArrayList<Tweet> result) {
		if(null != result) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
}
