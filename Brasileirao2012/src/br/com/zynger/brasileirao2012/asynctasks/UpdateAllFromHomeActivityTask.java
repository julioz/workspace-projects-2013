package br.com.zynger.brasileirao2012.asynctasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.AprovCalculator;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.data.MatchesDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.AprovData;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Striker;
import br.com.zynger.brasileirao2012.rest.FansREST;
import br.com.zynger.brasileirao2012.rest.StrikersREST;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.FileHandler;
import br.com.zynger.brasileirao2012.util.JsonUtil;
import br.com.zynger.brasileirao2012.util.PreferenceEditor;
import br.com.zynger.brasileirao2012.view.DataUpdateLayout;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class UpdateAllFromHomeActivityTask extends AsyncTask<Void, Integer, Boolean> {
	private final int UPDATE_RANKING = 1;
	private final int UPDATE_TABLE = 2;
	private final int UPDATE_STRIKERS = 3;
	private final int UPDATE_APROV = 4;
	private final int UPDATE_OTHERS = 5;

	private TreeMap<String, Club> clubs;
	private Club myClub;
	private MatchesDB matchesDB;
	
	private Context context;
	private DataUpdateLayout dulLoading;
	private View viewFlow;
	private View updateAllButton;
	private View updateAllLayout;

	public UpdateAllFromHomeActivityTask(Context context,
			View updateAllLayout,
			View viewFlow,
			View updateAllButton){
		ClubsDB clubsDB = ClubsDB.getInstance(context);
		this.clubs = clubsDB.getClubs();
		this.myClub = clubsDB.getMyClub();
		this.matchesDB = MatchesDB.getInstance(context, clubs);
		
		this.context = context;
		this.viewFlow = viewFlow;
		this.updateAllLayout = updateAllLayout;
		this.dulLoading = (DataUpdateLayout) updateAllLayout.findViewById(R.homeactivity.dul_loading);
		this.updateAllButton = updateAllButton;
	}

	@Override
	protected void onPreExecute() {
		dulLoading.show();
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		if(ConnectionHelper.isConnected(context)){
			try{
				publishProgress(UPDATE_RANKING);
				TreeMap<String, Club> ranking1 = HTMLManager.getRanking(clubs, Division.FIRSTDIVISION);
				FileHandler.saveBackup(context, FileHandler.JSON_RANKING,
						JsonUtil.createRankingJson(ranking1).toString());
				Log.d(Constants.TAG, "Ranking A atualizado");

				TreeMap<String, Club> ranking2 = HTMLManager.getRanking(clubs, Division.SECONDDIVISION);
				FileHandler.saveBackup(context, FileHandler.JSON_RANKING,
						JsonUtil.createRankingJson(ranking2).toString());
				Log.d(Constants.TAG, "Ranking B atualizado");

				publishProgress(UPDATE_TABLE);
				JSONObject matches_a = HTMLManager.getTableData(context, matchesDB, clubs, Division.FIRSTDIVISION);
				FileHandler.saveBackup(context, FileHandler.JSON_MATCHES, matches_a.toString(), Division.FIRSTDIVISION);
				Log.d(Constants.TAG, "Matches A atualizado");

				JSONObject matches_b = HTMLManager.getTableData(context, matchesDB, clubs, Division.SECONDDIVISION);
				FileHandler.saveBackup(context, FileHandler.JSON_MATCHES, matches_b.toString(), Division.SECONDDIVISION);
				Log.d(Constants.TAG, "Matches B atualizado");

				publishProgress(UPDATE_STRIKERS);
				TreeMap<Division, ArrayList<Striker>> tmStrikers = new TreeMap<Division, ArrayList<Striker>>();
				StrikersREST strikersREST = new StrikersREST(clubs);
				tmStrikers.put(Division.FIRSTDIVISION, strikersREST.getStrikers(Division.FIRSTDIVISION));
				tmStrikers.put(Division.SECONDDIVISION, strikersREST.getStrikers(Division.SECONDDIVISION));
				FileHandler.saveBackup(context, FileHandler.JSON_STRIKERS, JsonUtil.createStrikersJson(tmStrikers));
				Log.d(Constants.TAG, "Strikers atualizado");

				publishProgress(UPDATE_APROV);
				TreeMap<String, AprovData> tmAprov = new AprovCalculator().getAprov(clubs, matchesDB);
				for (Iterator<String> it = tmAprov.keySet().iterator(); it.hasNext();) {
					String clubAcr = (String) it.next();
					clubs.get(clubAcr).setAprov(tmAprov.get(clubAcr));
				}
				FileHandler.saveBackup(context, FileHandler.JSON_APROV, JsonUtil.createAprovJson(tmAprov).toString());
				Log.d(Constants.TAG, "Aproveitamento atualizado");

				publishProgress(UPDATE_OTHERS);
				FileHandler.saveBackup(context, FileHandler.JSON_FANS,
						JsonUtil.createFansJson(new FansREST().updateFansData(context, clubs, myClub.getAcronym())).toString());
				Log.d(Constants.TAG, "Torcidômetro atualizado");
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}catch(OutOfMemoryError e){
				return false;
			}
		}else return false;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		String message = "Atualizando...";
		switch (values[0]) {
		case UPDATE_RANKING:
			message = "Atualizando classificações...";
			break;
		case UPDATE_TABLE:
			message = "Atualizando tabelas...";
			break;
		case UPDATE_STRIKERS:
			message = "Atualizando artilharias...";
			break;
		case UPDATE_APROV:
			message = "Atualizando aproveitamento...";
			break;
		case UPDATE_OTHERS:
			message = "Completando atualização...";
			break;
		default:
			break;
		}
		
		dulLoading.setMessage(message);
		super.onProgressUpdate(values);
	}

	protected void onPostExecute(Boolean result) {
		viewFlow.setVisibility(View.VISIBLE);
		updateAllButton.setVisibility(View.VISIBLE);
		updateAllLayout.setVisibility(View.GONE);
		dulLoading.hide();

		if(result){
			PreferenceEditor.setLastGeneralUpdate(context, Calendar.getInstance());
			Toast.makeText(context, R.string.message_dataupdated, Toast.LENGTH_SHORT).show();
		}
		else Toast.makeText(context, R.string.message_errorupdatefail, Toast.LENGTH_SHORT).show();
	}
}