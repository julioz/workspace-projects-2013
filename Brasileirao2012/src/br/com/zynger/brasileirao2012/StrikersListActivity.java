package br.com.zynger.brasileirao2012;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import br.com.zynger.brasileirao2012.adapters.StrikersListAdapter;
import br.com.zynger.brasileirao2012.asynctasks.GetStrikersTask;
import br.com.zynger.brasileirao2012.base.DivisionsActivity;
import br.com.zynger.brasileirao2012.comparator.GoalsComparator;
import br.com.zynger.brasileirao2012.comparator.PlayerNameComparator;
import br.com.zynger.brasileirao2012.comparator.TeamComparator;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.data.StrikersDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Striker;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.FileHandler;
import br.com.zynger.brasileirao2012.util.JsonUtil;

public class StrikersListActivity extends DivisionsActivity<ArrayList<Striker>> {
	private enum Comparators {
		GOALS(new GoalsComparator()),
		NAME(new PlayerNameComparator()),
		TEAM(new TeamComparator());
		
		private Comparator<Striker> comparator;
		private Comparators(Comparator<Striker> comparator) {
			this.comparator = comparator;
		}
		
		public Comparator<Striker> getComparator() {
			return comparator;
		}
	}
	
	private StrikersDB strikersDB;
	private Club club;
	
	private ListView lvContent;
	private HashMap<View, Comparators> headers;
	
	@Override
	public void loadViews(View activityView) {
		lvContent = (ListView) activityView.findViewById(R.strikerslistactivity.lv_content);
	}

	private void setHeadersOnClickListener() {
		OnClickListener headerOCL = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int color = R.color.rankingrow_blue;
				if(v.getBackground() == null){
					clearHeadersBackground();
					getListAdapter().order(headers.get(v).getComparator());
				}else{
					getListAdapter().reverse();
					if(!getListAdapter().isAscending()){
						color = R.color.rankingrow_red;
					}
				}
				v.setBackgroundColor(getResources().getColor(color));
			}
		};
		
		for (View header : headers.keySet()) {
			header.setOnClickListener(headerOCL);
		}
	}
	
	private void clearHeadersBackground() {
		for (View header : headers.keySet()) {
			header.setBackgroundDrawable(null);
		}
	}
	
	private void setHeadersMap(View view) {
		headers = new HashMap<View, Comparators>();
		headers.put(view.findViewById(R.strikerslistactivity.tv_goals), Comparators.GOALS);
		headers.put(view.findViewById(R.strikerslistactivity.tv_name), Comparators.NAME);
		headers.put(view.findViewById(R.strikerslistactivity.tv_team), Comparators.TEAM);
	}
	
	private StrikersListAdapter getListAdapter() {
		return (StrikersListAdapter) lvContent.getAdapter();
	}
	
	private void setListAdapter(StrikersListAdapter adapter) {
		lvContent.setAdapter(adapter);
	}
	
	@Override
	public Division getInitialDivision() {
		return (Division) getIntent().getSerializableExtra(Constants.INTENT_DIVISION);
	}

	@Override
	public void onDivisionChosenFromDrawer(Division division) {
		if(getDivision() != division){
			finish();
			Intent it = new Intent(this, StrikersListActivity.class);
			it.putExtra(Constants.INTENT_DIVISION, division);
			it.putExtra(Constants.INTENT_MYCLUB, club != null);
			startActivity(it);
		}		
	}

	@Override
	public int getLayoutResource() {
		return R.layout.strikerslistactivity;
	}

	@Override
	public void initializeActivityEnvironment(View activityView) {
		strikersDB = StrikersDB.getInstance();
		if(getIntent().getBooleanExtra(Constants.INTENT_MYCLUB, false)){
			club = ClubsDB.getInstance(this).getMyClub();
			lockDrawer();
		}
		
		setHeadersMap(activityView);
		setHeadersOnClickListener();
	}

	@Override
	public void didNotAutoUpdate() {
		if(strikersDB.isListNull(getDivision())
				|| strikersDB.isListEmpty(getDivision())){
			if (ConnectionHelper.isConnected(this)) {
				getAsyncTask().execute();
			} else {
				setErrorMessage(getString(R.string.message_verifyconnection));
			}
		}else{
			showOrderedByGoals(strikersDB.getStrikers(getDivision()));
		}
	}
	
	private void showOrderedByGoals(ArrayList<Striker> list) {
		ArrayList<Striker> strikers = club == null ? list : strikersDB.getStrikersFromClub(club);
		setListAdapter(new StrikersListAdapter(this, strikers));
		getListAdapter().order(Comparators.GOALS.getComparator());
		clearHeadersBackground();
	}

	@Override
	public View[] getDataUpdateViewsToToggle(View activityView) {
		return new View[] { activityView.findViewById(R.strikerslistactivity.rl_content) };
	}

	@Override
	public Integer getDataUpdateLayoutId() {
		return R.strikerslistactivity.dul_loading;
	}

	@Override
	public AsyncTask<Void, Void, ArrayList<Striker>> getAsyncTask() {
		return new GetStrikersTask(this, getDivision());
	}

	@Override
	public boolean onAsyncSuccess(ArrayList<Striker> strikers) {
		TreeMap<Division, ArrayList<Striker>> tmStrikers = new TreeMap<Division, ArrayList<Striker>>();
		if(strikersDB.isListNull(getDivision().getOppositeDivision())){
			tmStrikers.put(getDivision().getOppositeDivision(), new ArrayList<Striker>());
		}else{
			tmStrikers.put(getDivision().getOppositeDivision(), strikersDB.getStrikers(getDivision().getOppositeDivision()));
		}
		tmStrikers.put(getDivision(), strikers);
		FileHandler.saveBackup(this, FileHandler.JSON_STRIKERS, JsonUtil.createStrikersJson(tmStrikers).toString());
		strikersDB.setStrikers(getDivision(), strikers);
		
		if(strikers.isEmpty()){
			setEmptyDataSetMessage(getString(R.string.strikerslistactivity_emptydataset));
			return false;
		}else{			
			showOrderedByGoals(strikers);
			return true;
		}
	}

	@Override
	public void onAsyncFail(String message) {
		setErrorMessage(getString(R.string.message_errorupdatefail));
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_striker;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.strikerslistactivity_title;
	}

	@Override
	public Integer getPullToRefreshViewId() {
		return R.strikerslistactivity.lv_content;
	}
}
