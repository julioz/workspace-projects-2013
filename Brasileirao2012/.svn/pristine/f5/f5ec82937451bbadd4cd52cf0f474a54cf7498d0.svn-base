package br.com.zynger.brasileirao2012;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.viewpagerindicator.TitlePageIndicator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import br.com.zynger.brasileirao2012.adapters.pager.ThirdDivisionPagerAdapter;
import br.com.zynger.brasileirao2012.asynctasks.GetThirdDivisionRankingTask;
import br.com.zynger.brasileirao2012.base.UpdateableActivity;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.FileHandler;
import br.com.zynger.brasileirao2012.util.JsonUtil;

public class ThirdDivisionActivity extends
		UpdateableActivity<SparseArray<ArrayList<Club>>> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		if (!restoreBackup()) {
			setClickToUpdateMessage(getString(R.string.message_updatedatatoshow));
		}
	}

	private void setContentView() {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.thirddivisionactivity, null);
		setContentView(layout);
		setDataUpdateLayout(layout);
	}

	private boolean restoreBackup() {
		Object jsonStringThirdDivRanking = FileHandler.openBackup(this,
				FileHandler.JSON_THIRDDIVISIONRANKING);
		if (null != jsonStringThirdDivRanking) {
			try {
				SparseArray<ArrayList<Club>> array = new SparseArray<ArrayList<Club>>();
				JSONArray jsonRanking = new JSONArray(
						jsonStringThirdDivRanking.toString());
				for (int groupIndex = 0; groupIndex < jsonRanking.length(); groupIndex++) {
					JSONArray group = jsonRanking.getJSONArray(groupIndex);
					ArrayList<Club> alGroup = new ArrayList<Club>();
					for (int clubIndex = 0; clubIndex < group.length(); clubIndex++) {
						JSONArray clubJson = group.getJSONArray(clubIndex);
						Club club = new Club();
						club.setRankingFromJson(clubJson);
						alGroup.add(club);
					}
					array.put((groupIndex == 0 ? Constants.THIRDDIVISION_GROUP_A
							: Constants.THIRDDIVISION_GROUP_B), alGroup);
				}

				setPager(array);
				return true;
			} catch (JSONException je) {
				je.printStackTrace();
			}
		}

		return false;
	}

	private void setPager(SparseArray<ArrayList<Club>> clubsSparseArray) {
		ViewPager mPager = (ViewPager) findViewById(R.thirddivisionactivity.viewpager);
		TitlePageIndicator mPagerIndicator = (TitlePageIndicator) findViewById(R.thirddivisionactivity.pagerindicator);
		mPager.setAdapter(new ThirdDivisionPagerAdapter(this, clubsSparseArray));
		mPagerIndicator.setViewPager(mPager);
	}

	@Override
	public View[] getDataUpdateViewsToToggle(View activityView) {
		return new View[] { activityView
				.findViewById(R.thirddivisionactivity.rl_data) };
	}

	@Override
	public Integer getDataUpdateLayoutId() {
		return R.thirddivisionactivity.dul_loading;
	}

	@Override
	public AsyncTask<Void, Void, SparseArray<ArrayList<Club>>> getAsyncTask() {
		return new GetThirdDivisionRankingTask(this);
	}

	@Override
	public boolean onAsyncSuccess(SparseArray<ArrayList<Club>> clubsSparseArray) {
		FileHandler.saveBackup(this, FileHandler.JSON_THIRDDIVISIONRANKING,
				JsonUtil.createThirdDivisionRankingJson(clubsSparseArray).toString());
		if (clubsSparseArray.size() == 0) {
			setEmptyDataSetMessage(getString(R.string.thirddivisionactivity_emptydataset));
			return false;
		} else {
			setPager(clubsSparseArray);
			return true;
		}
	}

	@Override
	public void onAsyncFail(String message) {
		setErrorMessage(message);
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_thirddivision;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.thirddivisionactivity_title;
	}

	@Override
	public Integer getPullToRefreshViewId() {
		return null;
	}
}
