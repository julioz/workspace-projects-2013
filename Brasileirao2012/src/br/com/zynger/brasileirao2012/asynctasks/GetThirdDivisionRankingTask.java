package br.com.zynger.brasileirao2012.asynctasks;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.SparseArray;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class GetThirdDivisionRankingTask extends AsyncTask<Void, Void, SparseArray<ArrayList<Club>>> {
	private AsyncTaskListener<SparseArray<ArrayList<Club>>> listener;

	public GetThirdDivisionRankingTask(AsyncTaskListener<SparseArray<ArrayList<Club>>> listener){
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected SparseArray<ArrayList<Club>> doInBackground(Void... arg0) {
		try{
			return HTMLManager.getThirdDivisionRanking();
		}catch(Exception e){
			return null;
		}catch(OutOfMemoryError e){
			return null;
		}
	}

	@Override
	protected void onPostExecute(SparseArray<ArrayList<Club>> array) {
		if(array != null) listener.onComplete(array);
		else listener.onFail(listener.getContext().getString(R.string.message_errorupdatefail));		
		super.onPostExecute(array);
	}
}