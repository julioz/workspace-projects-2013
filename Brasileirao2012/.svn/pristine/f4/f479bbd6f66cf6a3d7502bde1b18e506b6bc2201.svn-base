package br.com.zynger.brasileirao2012.asynctasks;

import java.io.IOException;
import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Move;
import br.com.zynger.brasileirao2012.rest.MoveToMoveREST;

import com.fasterxml.jackson.core.JsonParseException;

public class GetVideoUrlTask extends AsyncTask<Void, Void, String[]> {
	private final AsyncTaskListener<String[]> listener;
	private final Move move;
	private final MoveToMoveREST moveToMoveRest;

	public GetVideoUrlTask(AsyncTaskListener<String[]> listener, TreeMap<String, Club> clubs, Move move) {
		this.listener = listener;
		this.moveToMoveRest = new MoveToMoveREST(clubs);
		this.move = move;
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}
	
	@Override
	protected String[] doInBackground(Void... params) {
		try {
			String videoUrl = moveToMoveRest.getMoveVideoUrl(move.getVideoId());
			return new String[] { String.valueOf(move.getId()), videoUrl };
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String[] videoUrl) {
		if(videoUrl != null){
			listener.onComplete(videoUrl);
		}else{
			listener.onFail(listener
					.getContext()
					.getString(
							R.string.movetomovevideocentralactivity_novideoscouldbeparsed));
		}
	}

	public Move getMove() {
		return move;
	}
}