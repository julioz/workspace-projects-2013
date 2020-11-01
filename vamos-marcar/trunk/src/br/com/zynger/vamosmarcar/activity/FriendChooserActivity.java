package br.com.zynger.vamosmarcar.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.adapter.FriendsAdapter;
import br.com.zynger.vamosmarcar.interfaces.FacebookFriendsTouchable;
import br.com.zynger.vamosmarcar.model.FacebookFriend;
import br.com.zynger.vamosmarcar.view.IndexableListView;
import br.com.zynger.vamosmarcar.view.RobotoTextView;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FriendChooserActivity extends BaseActivity implements
		FacebookFriendsTouchable, OnItemClickListener {

	private ActionMode mMode = null;

	private LinearLayout llLoading;
	private ProgressBar pbLoading;
	private RobotoTextView tvLoading;
	private IndexableListView lvFriends;

	private FriendsAdapter friendsAdapter;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_friendchooser);
		loadViews();
		
		facebook.getFriends(this);
	}

	private void loadViews() {
		llLoading = (LinearLayout) findViewById(R.friendchooser_activity.ll_loading);
		pbLoading = (ProgressBar) findViewById(R.friendchooser_activity.pb_loading);
		tvLoading = (RobotoTextView) findViewById(R.friendchooser_activity.tv_loading);
		lvFriends = (IndexableListView) findViewById(R.friendchooser_activity.lv_friends);

		lvFriends.setItemsCanFocus(false);
		lvFriends.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lvFriends.setOnItemClickListener(this);
		lvFriends.setFastScrollEnabled(true);
	}

	@Override
	public void beforeFriendsRequest() {
		showProgressLayout(true, -1);
	}

	@Override
	public void afterFriendRequest(List<FacebookFriend> friends) {
		friendsAdapter = new FriendsAdapter(this, friends);
		lvFriends.setAdapter(friendsAdapter);
		
		String[] preselected = getIntent().getStringArrayExtra(Constants.INTENT_FRIENDS_PRESELECTED);
		if (preselected != null) {
			setPreselectedFriends(preselected);
		}
		
		showProgressLayout(false, -1);
	}

	private void setPreselectedFriends(String[] preselectedIds) {
		for (String id : preselectedIds) {
			int positionFromId = friendsAdapter.getPositionFromId(id);
			if (positionFromId != -1) {
				lvFriends.setItemChecked(positionFromId, true);
			}
		}
	}

	@Override
	public void onFriendRequestError() {
		showProgressLayout(true, R.string.friendchooseractivity_fetchingerror);
		pbLoading.setVisibility(View.GONE);
	}

	private void showProgressLayout(boolean visible, int textRes) {
		llLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
		pbLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
		lvFriends.setVisibility(!visible ? View.VISIBLE : View.GONE);
		if (textRes == -1) {
			textRes = R.string.friendchooseractivity_fetchingfriends;
		}
		tvLoading.setText(getString(textRes));
	}

	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		SparseBooleanArray checked = lvFriends.getCheckedItemPositions();
		boolean hasCheckedElement = false;
		for (int i = 0; i < checked.size() && !hasCheckedElement; i++) {
			hasCheckedElement = checked.valueAt(i);
		}

		if (hasCheckedElement) {
			if (mMode == null) {
				// TODO Api level 11 ?
				mMode = startActionMode(new ModeCallback());
			}
		} else {
			if (mMode != null) {
				mMode.finish();
			}
		}
	}

	private void finishWithFriends(String[] selectedFriends) {
		Intent intent = this.getIntent();
		intent.putExtra(Constants.INTENT_FRIENDS, selectedFriends);
		setResult(RESULT_OK, intent);
		finish();
	}

	private final class ModeCallback implements ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Create the menu from the xml file
			MenuInflater inflater = getSupportMenuInflater();
			inflater.inflate(R.menu.actionmode_friendchooser, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// Here, you can checked selected items to adapt available actions
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// Destroying action mode, let's unselect all items
			for (int i = 0; i < lvFriends.getAdapter().getCount(); i++) {
				lvFriends.setItemChecked(i, false);
			}

			if (mode == mMode) {
				mMode = null;
			}
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			long[] selected = lvFriends.getCheckedItemIds();
			String[] selectedFriends = new String[selected.length];
			if (selected.length > 0) {
				for (int i = 0; i < selected.length; i++) {
					selectedFriends[i] = friendsAdapter.getItem(
							(int) selected[i]).getId();
				}
			}

			mode.finish();
			finishWithFriends(selectedFriends);
			return true;
		}
	};
}
