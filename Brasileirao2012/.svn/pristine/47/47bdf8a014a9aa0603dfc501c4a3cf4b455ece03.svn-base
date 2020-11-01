package br.com.zynger.brasileirao2012.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.zynger.brasileirao2012.actionmode.ActionModeCallback.ActionModeListener;
import br.com.zynger.brasileirao2012.actionmode.MatchCallback;
import br.com.zynger.brasileirao2012.adapters.TableArrayAdapter;
import br.com.zynger.brasileirao2012.adapters.pager.BasePagerAdapter.ActionModeDismissable;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.util.Constants;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;

public class TableFragment extends SherlockFragment implements
		ActionModeDismissable, ActionModeListener<Match> {
	private int mPageNumber;
	private ArrayList<Match> matches;
	private MatchCallback mActionMode;
	private ListView listView;

	public static TableFragment create(int pageNumber) {
		TableFragment fragment = new TableFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.PAGER_POSITION, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public TableFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(Constants.PAGER_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = new ListView(getContext());
		listView.setCacheColorHint(0x00000000);
		listView.setAdapter(new TableArrayAdapter(getContext(), matches));

		mActionMode = new MatchCallback(this, listView);
		
		return listView;
	}

	public void setMatches(ArrayList<Match> matches) {
		this.matches = matches;
		Collections.sort(this.matches);
	}

	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public void dismissActionMode() {
		if (mActionMode != null) {
			mActionMode.dismissActionMode();
		}
	}

	@Override
	public Context getContext() {
		return getSherlockActivity();
	}

	@Override
	public void onPrepareActionMode() {
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		listView.requestLayout();
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public int getMenuResource() {
		return mActionMode.getMenuResource();
	}

	@Override
	public void changeMenuItems(Match match) {
		mActionMode.changeMenuItems(match);
	}

	@Override
	public boolean onActionModeItemClick(Match match, ActionMode mode,
			int itemId) {
		return mActionMode.onItemClicked(getContext(), match, mode, itemId);
	}

	@Override
	public Match getItemAtPosition(int position) {
		return (Match) listView.getItemAtPosition(position);
	}
}
