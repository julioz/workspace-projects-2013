package br.com.zynger.brasileirao2012.asynctasks;

import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.AprovCalculator;
import br.com.zynger.brasileirao2012.data.MatchesDB;
import br.com.zynger.brasileirao2012.model.AprovData;
import br.com.zynger.brasileirao2012.model.Club;

public class UpdateAprovTask extends
		AsyncTask<Void, Void, TreeMap<String, AprovData>> {
	private TreeMap<String, Club> clubs;
	private AsyncTaskListener<TreeMap<String, AprovData>> listener;

	public UpdateAprovTask(AsyncTaskListener<TreeMap<String, AprovData>> listener,
			TreeMap<String, Club> clubs) {
		this.clubs = clubs;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected TreeMap<String, AprovData> doInBackground(Void... arg0) {
		try {
			AprovCalculator calculator = new AprovCalculator();
			return calculator.getAprov(clubs,
					MatchesDB.getInstance(listener.getContext(), clubs));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(TreeMap<String, AprovData> result) {
		if (result != null) {
			listener.onComplete(result);
		} else {
			listener.onFail(listener.getContext().getString(
					R.string.message_errorupdatefail));
		}
		super.onPostExecute(result);
	}
}