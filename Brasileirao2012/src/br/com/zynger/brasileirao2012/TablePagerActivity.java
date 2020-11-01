package br.com.zynger.brasileirao2012;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.adapters.pager.TablePagerAdapter;
import br.com.zynger.brasileirao2012.asynctasks.GetTableTask;
import br.com.zynger.brasileirao2012.base.DivisionsActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.data.MatchesDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.FileHandler;

import com.michaelnovakjr.numberpicker.NumberPickerDialog;
import com.michaelnovakjr.numberpicker.NumberPickerDialog.OnNumberSetListener;

public class TablePagerActivity extends DivisionsActivity<JSONObject> {
    private ViewPager mPager;
    private TablePagerAdapter tablePagerAdapter;
	private int calendarFixture;
	private NumberPickerDialog fixturePicker;
	private TextView tvSelector;
	private View selector;
	protected int currentShowingPagerPage;

	@Override
	public View[] getDataUpdateViewsToToggle(View activityView) {
		return new View[] { mPager } ;
	}

	@Override
	public Integer getDataUpdateLayoutId() {
		return R.tableflowactivity.dul_loading;
	}
	
	@Override
	public Division getInitialDivision() {
		return (Division) getIntent().getSerializableExtra(Constants.INTENT_DIVISION);
	}

	@Override
	public void onDivisionChosenFromDrawer(Division division) {
		if (division != getDivision()) {
			Intent it = new Intent(this, TablePagerActivity.class);
			it.putExtra(Constants.INTENT_DIVISION, division);
			finish();
			startActivity(it);
		}
	}

	@Override
	public int getLayoutResource() {
		return R.layout.tableflowactivity;
	}

	@Override
	public void initializeActivityEnvironment(View activityView) {
		setFixtureNumberBasedOnCalendar();
		
		fixturePicker = new NumberPickerDialog(this,
				getString(R.string.tableflowactivity_selectfixture), 1, 38,
				getString(R.string.tableflowactivity_ok),
				getString(R.string.tableflowactivity_cancel));
		fixturePicker.setTextColor(Color.BLACK);
		fixturePicker.setOnNumberSetListener(new OnNumberSetListener() {
			@Override
			public void onNumberSet(int selectedNumber) {
				setCurrentFixture(selectedNumber);
			}
		});
		selector.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fixturePicker.show(currentShowingPagerPage+1);
			}
		});
		
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	tvSelector.setText(String.valueOf(position+1));
				currentShowingPagerPage = position;
				if (tablePagerAdapter != null) {
					tablePagerAdapter.dismissActionMode();
				}
			}
        });
	}

	private void setFixtureNumberBasedOnCalendar() {
		MatchesDB matchesDB = MatchesDB.getInstance(this, ClubsDB.getInstance(this).getClubs());
		calendarFixture = matchesDB.getFixtureBasedOnCalendar(getDivision()).getNumber();
	}

	@Override
	public void didNotAutoUpdate() {
		setTablePagerAdapter();
	}

	private void setTablePagerAdapter() {
        tablePagerAdapter = new TablePagerAdapter(this, getDivision());
		mPager.setAdapter(tablePagerAdapter);
        setCurrentFixture(calendarFixture);
	}

	private void setCurrentFixture(int number) {
		mPager.setCurrentItem(number-1);
		currentShowingPagerPage = number-1;
	}

	@Override
	public void loadViews(View activityView) {
		mPager = (ViewPager) activityView.findViewById(R.tableflowactivity.viewpager);
		
		setSelectorOnActionBar(activityView);
	}

	private void setSelectorOnActionBar(View activityView) {
		selector = LayoutInflater.from(this).inflate(
				R.layout.actionbar_view_roundedimage, null);
		selector.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showToastNearToRefreshActionItem(R.string.tableflowactivity_selectfixture);
				return true;
			}
		});
		
		tvSelector = (TextView) selector.findViewById(R.actionbar.textview_roudedview);
		selector.findViewById(R.actionbar.view_roudedimage).setVisibility(View.GONE);
		tvSelector.setVisibility(View.VISIBLE);
		tvSelector.setText(getDivision().getNotation());
		
		addActionBarRightView(selector);
	}

	@Override
	public AsyncTask<Void, Void, JSONObject> getAsyncTask() {
		return new GetTableTask(this, getDivision());
	}

	@Override
	public boolean onAsyncSuccess(JSONObject matches) {
		FileHandler.saveBackup(this, FileHandler.JSON_MATCHES,
				matches.toString(), getDivision());
		
		setFixtureNumberBasedOnCalendar();
		setTablePagerAdapter();
		return true;
	}

	@Override
	public void onAsyncFail(String message) {
		setErrorMessage(message);
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_table;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.tableflowactivity_title;
	}

	@Override
	public Integer getPullToRefreshViewId() {
		return null;
	}
}
