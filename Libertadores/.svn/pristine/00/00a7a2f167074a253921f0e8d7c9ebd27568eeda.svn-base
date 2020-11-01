package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import br.com.zynger.libertadores.adapters.StrikersAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetStrikersTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Striker;
import br.com.zynger.libertadores.util.InternalStorageHandler;
import br.com.zynger.libertadores.util.JsonUtil;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.view.UpdateImageView;
import br.com.zynger.libertadores.web.StrikersParser;

public class StrikersActivity extends ListActivity implements AsyncTaskListener {
	
	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	private InternalStorageHandler internalStorage;
	
	private StrikersParser strikersParser;
	
	private ArrayList<Striker> strikers;
	
	private SharedPreferences sharedPrefs;
	
	private HelveticaTextView tv_title;
	private DataUpdateLayout dul_update;
	private UpdateImageView uiv_update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.strikersactivity);
		
		clubsDB = ClubsDB.getSingletonObject(this);
		clubs = clubsDB.getClubs();
		internalStorage = new InternalStorageHandler(this);
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		strikersParser = new StrikersParser(this, clubs);
		
		loadViews();
		
		uiv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetStrikersTask(strikersParser, StrikersActivity.this).execute();
			}
		});
		
		strikers = restoreBackup();
		if(sharedPrefs.getBoolean("cb_actsupdates", false)) uiv_update.performClick();
		else{			
			if(null != strikers) setAdapterFromStrikers();
			else new GetStrikersTask(strikersParser, this).execute();
		}
	}

	private void loadViews() {
		dul_update = (DataUpdateLayout) findViewById(R.strikersactivity.dul_update);
		dul_update.addViewToToggle(getListView());
		tv_title = (HelveticaTextView) findViewById(R.strikersactivity.titlebar_tvtitle);
		uiv_update = (UpdateImageView) findViewById(R.strikersactivity.titlebar_ivupdate);
		
		tv_title.setText(getString(R.string.homedashboard_strikers).toUpperCase());
	}
	
	private void setAdapterFromStrikers() {
		Collections.sort(strikers);
		//Collections.sort(strikers, new ClubComparator()); TODO colocar a possibilidade de ordenar por time
		
		setListAdapter(new StrikersAdapter(this, strikers));
	}
	
	private ArrayList<Striker> restoreBackup() {
		String strikersJsonString = (String) internalStorage.openBackup(InternalStorageHandler.STRIKERS_JSON);
		if(null != strikersJsonString){
			try{
				ArrayList<Striker> strikers = new ArrayList<Striker>();
				JSONArray jsonStrikers = new JSONArray(strikersJsonString);
				
				for (int i = 0; i < jsonStrikers.length(); i++) {
					JSONArray data = jsonStrikers.getJSONArray(i);
					Striker striker = new Striker(data, clubs);
					strikers.add(striker);
				}
				
				return strikers;
			}catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		} else return null;
	}

	@Override
	public void preExecution() {
		dul_update.showFullLayout();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(Object result) {
		ArrayList<Striker> strikers = (ArrayList<Striker>) result;
		this.strikers = strikers;
		
		if(strikers.size() > 0){
			JSONArray json = JsonUtil.createStrikersJson(strikers);
			internalStorage.saveBackup(this, InternalStorageHandler.STRIKERS_JSON, json.toString());
			
			setAdapterFromStrikers();
			dul_update.hide();
		}else{
			dul_update.setOnlyText(getString(R.string.error_emptydatabase));
		}
		
	}

	@Override
	public void onFail(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		if(null != strikers && strikers.size() > 0){
			setAdapterFromStrikers();
			dul_update.hide();
		}else{
			dul_update.setOnlyText(getString(R.string.error_nodatabase));
		}
	}
	
	class ClubComparator implements Comparator<Striker> {
		@Override
		public int compare(Striker lhs, Striker rhs) {
			return lhs.getClub().getName().compareTo(rhs.getClub().getName());
		}	
	}

}
