package br.com.zynger.brasileirao2012.asynctasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.model.Move;
import br.com.zynger.brasileirao2012.rest.MoveToMoveREST;

import com.fasterxml.jackson.core.JsonParseException;

public class GetMoveToMoveTask extends AsyncTask<Void, Void, ArrayList<Move>> {
	private AsyncTaskListener<ArrayList<Move>> listener;
	private TreeMap<String, Club> clubs;
	private Match match;

	public GetMoveToMoveTask(AsyncTaskListener<ArrayList<Move>> listener, Match match) {
		this.listener = listener;
		this.match = match;
		this.clubs = ClubsDB.getInstance(listener.getContext()).getClubs();
	}

	@Override
	protected void onPreExecute() {
		listener.preExecution();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Move> doInBackground(Void... params) {
		try {
			return new MoveToMoveREST(clubs).getMoveToMove(
					listener.getContext(), match);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(ArrayList<Move> result) {
		if (result != null) {
			listener.onComplete(result);
		} else {
			listener.onFail(listener.getContext().getString(
					R.string.message_errormovetomovefail));
		}
		super.onPostExecute(result);
	}
}