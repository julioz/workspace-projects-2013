package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Striker;
import br.com.zynger.libertadores.web.StrikersParser;

public class GetStrikersTask extends AsyncTask<Void, Void, ArrayList<Striker>> {

	private Context context;
	private AsyncTaskListener listener;
	private StrikersParser parser;
	
	public GetStrikersTask(StrikersParser parser, AsyncTaskListener listener){
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
	protected ArrayList<Striker> doInBackground(Void... params) {
		return parser.getStrikers();
	}

	@Override
	protected void onPostExecute(ArrayList<Striker> strikers) {
		if(null != strikers) listener.onComplete(strikers);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(strikers);
	}
	
	public Context getContext() {
		return context;
	}

}