package br.com.zynger.brasileirao2012;

import java.util.Iterator;
import java.util.TreeMap;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import br.com.zynger.brasileirao2012.adapters.AprovAdapter;
import br.com.zynger.brasileirao2012.asynctasks.UpdateAprovTask;
import br.com.zynger.brasileirao2012.base.DivisionsActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.AprovData;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.FileHandler;
import br.com.zynger.brasileirao2012.util.JsonUtil;

public class AprovActivity extends
		DivisionsActivity<TreeMap<String, AprovData>> {

	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;

	private ListView lvContent;

	private void setTable() {
		lvContent.setAdapter(new AprovAdapter(this, clubsDB
				.getClubsList(getDivision())));
	}

	@Override
	public boolean onAsyncSuccess(TreeMap<String, AprovData> map) {
		FileHandler.saveBackup(this, FileHandler.JSON_APROV, JsonUtil
				.createAprovJson(map).toString());
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String clubAcronym = (String) it.next();
			clubs.get(clubAcronym).setAprov(map.get(clubAcronym));
		}

		setTable();
		return true;
	}

	@Override
	public void onAsyncFail(String message) {
		setTable();
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_aprov;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.aprovactivity_title;
	}

	@Override
	public void onDivisionChosenFromDrawer(Division division) {
		setDivision(division);
		setTable();
	}

	@Override
	public Division getInitialDivision() {
		return (Division) getIntent().getSerializableExtra(
				Constants.INTENT_DIVISION);
	}

	@Override
	public AsyncTask<Void, Void, TreeMap<String, AprovData>> getAsyncTask() {
		return new UpdateAprovTask(this, clubs);
	}

	@Override
	public View[] getDataUpdateViewsToToggle(View activityView) {
		return null;
	}

	@Override
	public Integer getDataUpdateLayoutId() {
		return null;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.aprovactivity;
	}

	@Override
	public void initializeActivityEnvironment(View activityView) {
		clubsDB = ClubsDB.getInstance(this);
		clubs = clubsDB.getClubs();

		setTable();
	}

	@Override
	public void didNotAutoUpdate() {
	}

	@Override
	public void loadViews(View activityView) {
		lvContent = (ListView) activityView
				.findViewById(R.aprovactivity.listview_content);
	}

	@Override
	public Integer getPullToRefreshViewId() {
		return R.aprovactivity.listview_content;
	}
}
