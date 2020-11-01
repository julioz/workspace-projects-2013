package br.com.zynger.brasileirao2012.asynctasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.RealTimeMatch;
import br.com.zynger.brasileirao2012.model.RealTimePlayer;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class GetRealTimeSquadTask extends AsyncTask<Void, Void, LinkedHashMap<String, ArrayList<RealTimePlayer>>> {
	private AsyncTaskListener<LinkedHashMap<String, ArrayList<RealTimePlayer>>> listener;
	private RealTimeMatch realTimeMatch;
	
	public GetRealTimeSquadTask(AsyncTaskListener<LinkedHashMap<String, ArrayList<RealTimePlayer>>> listener, RealTimeMatch realTimeMatch){
		this.listener = listener;
		this.realTimeMatch = realTimeMatch;
	}
	
	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}
	
	@Override
	protected LinkedHashMap<String, ArrayList<RealTimePlayer>> doInBackground(Void... params) {
		try{
			return HTMLManager.getRealTimeSquad(listener.getContext(), realTimeMatch);
		}catch(OutOfMemoryError e){
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(LinkedHashMap<String, ArrayList<RealTimePlayer>> result) {
		if(result != null) listener.onComplete(result);
		else listener.onFail(listener.getContext().getString(R.string.getrealtimesquadtask_fail));
		super.onPostExecute(result);
	}
}