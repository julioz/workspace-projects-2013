package br.com.zynger.libertadores.asynctasks;

import java.util.TreeMap;

import android.content.Context;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.web.FansREST;

public class GetFansTask extends AsyncTask<Void, Void, TreeMap<String, Integer>> {
	
	private Context context;
	private AsyncTaskListener listener;

	public GetFansTask(Context context, AsyncTaskListener listener) {
		this.context = context;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected TreeMap<String, Integer> doInBackground(Void... params) {
		return FansREST.getFans();
	}

	@Override
	protected void onPostExecute(TreeMap<String, Integer> tm) {
		if(null != tm) listener.onComplete(tm);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(tm);
	}
	
	public Context getContext() {
		return context;
	}

}