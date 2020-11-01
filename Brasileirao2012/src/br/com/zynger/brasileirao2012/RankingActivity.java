package br.com.zynger.brasileirao2012;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.adapters.RankingListAdapter;
import br.com.zynger.brasileirao2012.asynctasks.GetRankingTask;
import br.com.zynger.brasileirao2012.base.DivisionsActivity;
import br.com.zynger.brasileirao2012.comparator.BalanceComparator;
import br.com.zynger.brasileirao2012.comparator.DrawsComparator;
import br.com.zynger.brasileirao2012.comparator.GoalsAgainstComparator;
import br.com.zynger.brasileirao2012.comparator.GoalsProComparator;
import br.com.zynger.brasileirao2012.comparator.LossesComparator;
import br.com.zynger.brasileirao2012.comparator.MatchesPlayedComparator;
import br.com.zynger.brasileirao2012.comparator.PointsComparator;
import br.com.zynger.brasileirao2012.comparator.WinsComparator;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.FileHandler;
import br.com.zynger.brasileirao2012.util.JsonUtil;

public class RankingActivity extends DivisionsActivity<TreeMap<String, Club>> {
	private enum Comparators {
		POINTS(new PointsComparator()),
		PLAYED(new MatchesPlayedComparator()),
		WIN(new WinsComparator()),
		DRAW(new DrawsComparator()),
		LOSE(new LossesComparator()),
		GOALSPRO(new GoalsProComparator()),
		GOALSAGAINST(new GoalsAgainstComparator()),
		BALANCE(new BalanceComparator());
		
		private Comparator<Club> comparator;
		private Comparators(Comparator<Club> comparator) {
			this.comparator = comparator;
		}
		
		public Comparator<Club> getComparator() {
			return comparator;
		}
	}

	private TreeMap<String, Club> clubs;

	private HashMap<View, Comparators> headers;
	private ListView lvContent;
	private RelativeLayout rlData;
	
	@Override
	public void loadViews(View activityView) {
		lvContent = (ListView) activityView.findViewById(R.rankingactivity.lv_content);
		rlData = (RelativeLayout) activityView.findViewById(R.rankingactivity.rl_data);
	}

	private void setHeadersMap(View view) {
		headers = new HashMap<View, Comparators>();
		headers.put(view.findViewById(R.rankingactivity.tv_points), Comparators.POINTS);
		headers.put(view.findViewById(R.rankingactivity.tv_played), Comparators.PLAYED);
		headers.put(view.findViewById(R.rankingactivity.tv_win), Comparators.WIN);
		headers.put(view.findViewById(R.rankingactivity.tv_draw), Comparators.DRAW);
		headers.put(view.findViewById(R.rankingactivity.tv_lose), Comparators.LOSE);
		headers.put(view.findViewById(R.rankingactivity.tv_goalspro), Comparators.GOALSPRO);
		headers.put(view.findViewById(R.rankingactivity.tv_goalsagainst), Comparators.GOALSAGAINST);
		headers.put(view.findViewById(R.rankingactivity.tv_balance), Comparators.BALANCE);
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
	
	private RankingListAdapter getListAdapter() {
		return (RankingListAdapter) lvContent.getAdapter();
	}
	
	private void setListAdapter(RankingListAdapter adapter) {
		lvContent.setAdapter(adapter);
	}
	
	private void clearHeadersBackground() {
		for (View header : headers.keySet()) {
			header.setBackgroundDrawable(null);
		}
	}

	@Override
	public Division getInitialDivision() {
		return (Division) getIntent().getSerializableExtra(Constants.INTENT_DIVISION);
	}

	@Override
	public void onDivisionChosenFromDrawer(Division division) {
		if(getDivision() != division){			
			finish();
			startActivity(Constants.getIntentForDivision(this, RankingActivity.class, division));
		}
	}

	@Override
	public View[] getDataUpdateViewsToToggle(View activityView) {
		return new View[]{ rlData };
	}

	@Override
	public Integer getDataUpdateLayoutId() {
		return R.rankingactivity.dul_loading;
	}

	@Override
	public AsyncTask<Void, Void, TreeMap<String, Club>> getAsyncTask() {
		return new GetRankingTask(this, getDivision());
	}

	@Override
	public boolean onAsyncSuccess(TreeMap<String, Club> clubs) {
		FileHandler.saveBackup(this, FileHandler.JSON_RANKING, JsonUtil.createRankingJson(clubs).toString());
		for (Club c : this.clubs.values()) {
			if(c.getDivision() == getDivision().getOppositeDivision()){
				clubs.put(c.getAcronym(), c);
			}
		}
		this.clubs = clubs;
		
		showOrderedByPoints(null);
		return true;
	}

	private void showOrderedByPoints(ArrayList<Club> list) {
		if(list == null){
			list = ClubsDB.getInstance(this).getClubsList(getDivision());
		}
		setListAdapter(new RankingListAdapter(this, list));
		getListAdapter().order(Comparators.POINTS.getComparator());
		clearHeadersBackground();
	}

	@Override
	public void onAsyncFail(String message) { }

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_ranking;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.rankingactivity_title;
	}
	
	@Override
	public boolean shouldCloseDrawerAfterItemSelection() {
		return true;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.rankingactivity;
	}

	@Override
	public void didNotAutoUpdate() { }

	@Override
	public void initializeActivityEnvironment(View activityView) {
		clubs = ClubsDB.getInstance(this).getClubs();

		setHeadersMap(activityView);
		setHeadersOnClickListener();

		if(getDivision() == Division.SECONDDIVISION) {
			((TextView) activityView.findViewById(R.rankingactivity.tv_footer_left)).setText(R.string.rankingactivity_footer_access);
		}
		
		showOrderedByPoints(null);
	}

	@Override
	public Integer getPullToRefreshViewId() {
		return R.rankingactivity.lv_content;
	}
}
