package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.zynger.libertadores.adapters.RostersExpandableListViewAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetRostersTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Person;
import br.com.zynger.libertadores.util.InternalStorageHandler;
import br.com.zynger.libertadores.util.JsonUtil;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.web.RostersParser;

public class RostersActivity extends Activity implements AsyncTaskListener {

	public final int INTENT_RETURN_CLUBSELECTOR = 42;
	
	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	private InternalStorageHandler internalStorage;
	
	private SharedPreferences sharedPrefs;
	
	private RostersParser parser;
	
	private HashMap<String, ArrayList<Person>> rostersMap;
	private String rosterKey;
	
	private HelveticaTextView tv_title;
	private ImageView iv_badge;
	private DataUpdateLayout dul_update;
	private ExpandableListView elv_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rostersactivity);
		
		clubsDB = ClubsDB.getSingletonObject(this);
		clubs = clubsDB.getClubs();
		internalStorage = new InternalStorageHandler(this);
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		parser = new RostersParser(this, clubs);

		loadViews();

		Club club = clubsDB.getMyClub();
		if(club != null) rosterKey = club.getAcronym();			
		
		rostersMap = restoreBackup();
		if(sharedPrefs.getBoolean("cb_actsupdates", false)) ; //uiv_update.performClick(); TODO
		else{			
			if(null != rostersMap) setAdapterFromRosters();
			else new GetRostersTask(parser, this).execute();
		}
		
	}
	
	private void loadViews() {
		tv_title = (HelveticaTextView) findViewById(R.rostersactivity.titlebar_tvtitle);
		iv_badge = (ImageView) findViewById(R.rostersactivity.titlebar_ivbadge);
		elv_content = (ExpandableListView) findViewById(R.rostersactivity.elv_content);
		dul_update = (DataUpdateLayout) findViewById(R.rostersactivity.dul_update);
		dul_update.addViewToToggle(elv_content);
		
		tv_title.setText(getString(R.string.homedashboard_rosters).toUpperCase(Locale.getDefault()));
		
		iv_badge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openClubSelectorActivity();
			}
		});
	}
	
	private void openClubSelectorActivity() {
		Intent it = new Intent(this, ClubSelectorActivity.class);
		startActivityForResult(it, INTENT_RETURN_CLUBSELECTOR);
	}
	
	private void setAdapterFromRosters() {
		if(rosterKey == null){
			openClubSelectorActivity();
			return;
		}
		
		iv_badge.setImageResource(clubs.get(rosterKey).getBadgeResource(this));
		iv_badge.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(v.getContext(), clubs.get(rosterKey).getName(), Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
		try{			
			RostersExpandableListViewAdapter relva =
					new RostersExpandableListViewAdapter(this, rostersMap.get(rosterKey));
			elv_content.setAdapter(relva);
			
			for (int i = 0; i < relva.getGroupCount(); i++) {
				elv_content.expandGroup(i);
			}
			dul_update.hide();
		}catch(NullPointerException npe){
			dul_update.setOnlyText(R.string.error_datadownload);
			Log.e(HomeActivity.TAG, "RostersActivity -> key = " + rosterKey);
		}
	}

	private HashMap<String, ArrayList<Person>> restoreBackup() {
		String rostersJsonString = (String) internalStorage.openBackup(InternalStorageHandler.ROSTERS_JSON);
		if(null != rostersJsonString){
			try{
				HashMap<String, ArrayList<Person>> map = new HashMap<String, ArrayList<Person>>();
				JSONObject jsonRosters = new JSONObject(rostersJsonString);
				
				Iterator<?> keys = jsonRosters.keys();
				while(keys.hasNext()){
					String key = (String) keys.next();
					if(jsonRosters.get(key) instanceof JSONArray){
						JSONArray data = jsonRosters.getJSONArray(key);
						ArrayList<Person> array = new ArrayList<Person>();
						for (int i = 0; i < data.length(); i++) {
							JSONObject jObj = data.getJSONObject(i);
							Person person = new Person(jObj);
							array.add(person);
						}
						map.put(key, array);
					}
				}
				
				return map;
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
		HashMap<String, ArrayList<Person>> rostersMap =
				(HashMap<String, ArrayList<Person>>) result;
		
		this.rostersMap = rostersMap;
		
		JSONObject json = JsonUtil.createRostersJson(rostersMap);
		internalStorage.saveBackup(this, InternalStorageHandler.ROSTERS_JSON, json.toString());
		setAdapterFromRosters();
		dul_update.hide();
	}

	@Override
	public void onFail(String message) {
		if(rostersMap == null) dul_update.setOnlyText(message);
		else{
			setAdapterFromRosters();
			dul_update.hide();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case (INTENT_RETURN_CLUBSELECTOR):
			if (resultCode == Activity.RESULT_OK) {
				String clubAcronym = data.getStringExtra(ClubSelectorActivity.INTENT_CLUB_RETURNED);
				if(!clubAcronym.equals(ClubSelectorActivity.DUMMY_ACRONYM)
						&& !clubAcronym.equals(rosterKey)){					
					rosterKey = clubAcronym;
					setAdapterFromRosters();
				}
			}
			break;
		default:
			break;
		}
	}
}