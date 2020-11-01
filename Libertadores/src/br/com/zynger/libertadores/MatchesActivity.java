package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

import org.json.JSONArray;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.zynger.libertadores.adapters.MatchesAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetAllMatchesTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.data.GroupsDB;
import br.com.zynger.libertadores.enums.Phase;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.util.GroupEnum;
import br.com.zynger.libertadores.util.InternalStorageHandler;
import br.com.zynger.libertadores.util.JsonUtil;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.view.UpdateImageView;
import br.com.zynger.libertadores.web.MatchesParser;

public class MatchesActivity extends ListActivity implements AsyncTaskListener {

	private ClubsDB clubsDB;
	private GroupsDB groupsDB;
	private TreeMap<String, Club> clubs;
	private InternalStorageHandler internalStorage;
	
	private MatchesParser matchesParser;
	
	private SharedPreferences sharedPrefs;
	
	private DataUpdateLayout dul_update;
	private UpdateImageView uiv_update;
	private ImageView iv_clock;
	private HelveticaTextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matchesactivity);
		
		clubsDB = ClubsDB.getSingletonObject(this);
		internalStorage = new InternalStorageHandler(this);
		groupsDB = GroupsDB.getSingletonObject(clubsDB);
		clubs = clubsDB.getClubs();
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		matchesParser = new MatchesParser(this, clubs);
		
		loadViews();
		
		iv_clock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), MatchesOrderedListActivity.class);
				startActivity(it);
			}
		});
		
		iv_clock.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(v.getContext(),
						getString(R.string.matchesorderedlistactivity_nextmatches), Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
		uiv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetAllMatchesTask(matchesParser, MatchesActivity.this).execute();
			}
		});
		
		setListAdapter(new MatchesAdapter(this, groupsDB.getGroups()));
		
		if(sharedPrefs.getBoolean("cb_actsupdates", false)) uiv_update.performClick();
	}
	
	private void loadViews() {
		dul_update = (DataUpdateLayout) findViewById(R.matchesactivity.dul_update);
		dul_update.addViewToToggle(getListView());
		
		tv_title = (HelveticaTextView) findViewById(R.matchesactivity.titlebar_tvtitle);
		uiv_update = (UpdateImageView) findViewById(R.matchesactivity.titlebar_ivupdate);
		iv_clock = (ImageView) findViewById(R.matchesactivity.titlebar_ivclipboard);
		
		tv_title.setText(getString(R.string.homedashboard_matches).toUpperCase(Locale.getDefault()));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		MatchesAdapter adapter = ((MatchesAdapter) getListAdapter());
		GroupEnum gEnum = adapter.getGroupClicked(position);
		if(null != gEnum){
			Intent intent = new Intent(this, GroupActivity.class);
			intent.putExtra(GroupActivity.INTENT_GROUPNUM, gEnum);
			startActivity(intent);
		}else{
			Intent intent = new Intent(this, MatchesListActivity.class);
			intent.putExtra(MatchesListActivity.INTENT_PHASESTRING, adapter.getPhaseClicked(position).toString());
			intent.putExtra(MatchesListActivity.INTENT_PHASENAME, adapter.getItem(position));
			startActivity(intent);
		}
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	public void preExecution() {
		dul_update.show();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(Object result) {
		HashMap<Integer, ArrayList<Match>> matches = (HashMap<Integer, ArrayList<Match>>) result;
		
		for (Phase phase : Phase.values()) {
			saveMatches(phase.getNumber(), matches.get(phase.getNumber()));
		}
		
		saveMatches(GroupEnum.GROUP_1, matches.get(1));
		saveMatches(GroupEnum.GROUP_2, matches.get(2));
		saveMatches(GroupEnum.GROUP_3, matches.get(3));
		saveMatches(GroupEnum.GROUP_4, matches.get(4));
		saveMatches(GroupEnum.GROUP_5, matches.get(5));
		saveMatches(GroupEnum.GROUP_6, matches.get(6));
		saveMatches(GroupEnum.GROUP_7, matches.get(7));
		saveMatches(GroupEnum.GROUP_8, matches.get(8));
		
		internalStorage.saveBackup(this, InternalStorageHandler.LAST_MATCHES_UPDATE, Calendar.getInstance());
		
		Toast.makeText(this, getString(R.string.success_datadownload), Toast.LENGTH_SHORT).show();
		dul_update.hide();
	}
	
	private void saveMatches(Integer phase, ArrayList<Match> matches) {
		JSONArray json = JsonUtil.createMatchesJson(matches);
		internalStorage.saveMatchesArray(MatchesListActivity.PREFIX_PHASE + phase.toString(), json.toString());
	}
	
	private void saveMatches(GroupEnum gEnum, ArrayList<Match> matches){
		JSONArray json = JsonUtil.createMatchesJson(matches);
		internalStorage.saveMatchesArray(gEnum.toString(), json.toString());
	}

	@Override
	public void onFail(String message) {
		dul_update.hide();
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}