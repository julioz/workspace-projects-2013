package br.com.zynger.libertadores.asynctasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Person;
import br.com.zynger.libertadores.web.RostersParser;

public class GetRostersTask extends AsyncTask<Void, Void, HashMap<String, ArrayList<Person>>> {

	private Context context;
	private AsyncTaskListener listener;
	private RostersParser parser;
	
	public GetRostersTask(RostersParser parser, AsyncTaskListener listener){
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
	protected HashMap<String, ArrayList<Person>> doInBackground(Void... params) {
		try {
			return parser.getRosters();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(HashMap<String, ArrayList<Person>> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(result);
	}
	
	public Context getContext() {
		return context;
	}
}
