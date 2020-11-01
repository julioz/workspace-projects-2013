package br.com.zynger.libertadores.asynctasks;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Move;
import br.com.zynger.libertadores.web.MoveToMoveParser;

public class GetMoveToMoveTask extends AsyncTask<Void, Void, ArrayList<Move>> {

	private Context context;
	private AsyncTaskListener listener;
	private MoveToMoveParser parser;
	
	public GetMoveToMoveTask(MoveToMoveParser parser, AsyncTaskListener listener){
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
	protected ArrayList<Move> doInBackground(Void... params) {
		try {
			return parser.getMoves();
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(HomeActivity.TAG, e.toString());
			return null;
		}
	}

	@Override
	protected void onPostExecute(ArrayList<Move> moves) {
		if(null != moves) listener.onComplete(moves);
		else listener.onFail(getContext().getString(R.string.error_datadownload));
		super.onPostExecute(moves);
	}
	
	public Context getContext() {
		return context;
	}
}
