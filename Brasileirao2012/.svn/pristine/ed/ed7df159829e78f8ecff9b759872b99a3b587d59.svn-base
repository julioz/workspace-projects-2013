package br.com.zynger.brasileirao2012.base;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.DefaultHeaderTransformer;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.asynctasks.AsyncTaskListener;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.util.PreferenceEditor;
import br.com.zynger.brasileirao2012.view.DataUpdateLayout;
import br.com.zynger.brasileirao2012.view.RefreshActionItem;
import br.com.zynger.brasileirao2012.view.RefreshActionItem.RefreshActionListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public abstract class UpdateableActivity<T> extends BaseActivity implements
		ActionBarMenuItemListener, RefreshActionListener, AsyncTaskListener<T> {

	private RefreshActionItem mRefreshActionItem;
	private DataUpdateLayout dulLoading;
	private String dulMessage;
	private PullToRefreshAttacher mPullToRefreshAttacher;

	public abstract Integer getPullToRefreshViewId();

	public abstract View[] getDataUpdateViewsToToggle(View activityView);

	public abstract Integer getDataUpdateLayoutId();

	protected boolean showUpdateButton() {
		return true;
	}

	public boolean isMainContentVisible() {
		return dulLoading.getVisibility() != View.VISIBLE;
	}

	public void setUpdateButtonVisibility(boolean visible) {
		mRefreshActionItem.getMenuItem().setVisible(visible);
		setPullToRefreshEnabled(visible);
	}

	public abstract AsyncTask<Void, Void, T> getAsyncTask();

	private void showUpdateLayout() {
		if (dulLoading != null) {
			if(dulMessage == null){
				dulLoading.setMessage(R.string.updating);
			}
			dulLoading.show();
		}
	}

	private void hideUpdateLayout() {
		if (dulLoading != null) {
			dulLoading.hide();
		}
	}

	public void setClickToUpdateMessage(String message) {
		if (dulLoading != null) {
			dulLoading.show();
			dulLoading.setClickToUpdateMessage(message);
		}
	}

	public void setEmptyDataSetMessage(String message) {
		if (dulLoading != null) {
			dulLoading.show();
			dulLoading.setEmptyDataSetMessage(message);
		}
	}

	public void setErrorMessage(String message) {
		if (dulLoading != null) {
			dulLoading.show();
			dulLoading.setErrorMessage(message);
		}
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void preExecution() {
		showUpdateLayout();
		setPullToRefreshEnabled(false);
	}

	private void setPullToRefreshEnabled(boolean enabled) {
		if (mPullToRefreshAttacher != null) {
			mPullToRefreshAttacher.setEnabled(enabled);
		}
	}

	@Override
	public void onComplete(T result) {
		if (onAsyncSuccess(result)) {
			hideUpdateLayout();
			showToastNearToRefreshActionItem(R.string.message_dataupdated);
		}

		setNonRefreshingState();
	}

	private void setNonRefreshingState() {
		setLoopingRefreshActionItem(false);
		if (mPullToRefreshAttacher != null) {
			mPullToRefreshAttacher.setRefreshComplete();
			// Library bug:
			// Once the pull down has refreshed, it will not refresh
			// again until the user has completed a new swipe
			mPullToRefreshAttacher.setEnabled(false);
			mPullToRefreshAttacher.setEnabled(true);
		}
	}

	@Override
	public void onFail(String message) {
		onAsyncFail(message);

		setErrorMessage(message);
		setNonRefreshingState();
	}

	private void setLoopingRefreshActionItem(boolean looping) {
		if (mRefreshActionItem != null && showUpdateButton()) {
			mRefreshActionItem.showProgress(looping);
		}
	}

	public abstract boolean onAsyncSuccess(T result);

	public abstract void onAsyncFail(String message);

	public boolean shouldAutoUpdate() {
		if (PreferenceEditor.shouldAutoUpdate(this)) {
			return executeAsyncTask();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (showUpdateButton()) {
			getSherlock().getMenuInflater().inflate(
					R.menu.actionbar_menu_update, menu);
			MenuItem item = menu.findItem(R.actionbar.menu_update);
			mRefreshActionItem = (RefreshActionItem) item.getActionView();
			mRefreshActionItem.setMenuItem(item);
			mRefreshActionItem.setRefreshActionListener(this);
		}
		return true;
	}

	@Override
	public void onRefreshButtonClick(RefreshActionItem sender) {
		menuItemPressed(sender.getMenuItem());
	}

	@Override
	public void menuItemPressed(MenuItem menuItem) {
		executeAsyncTask();
	}

	private boolean executeAsyncTask() {
		if (ConnectionHelper.isConnected(this)) {
			setLoopingRefreshActionItem(true);
			getAsyncTask().execute();
			return true;
		} else {
			setNonRefreshingState();
			showToastNearToRefreshActionItem(R.string.message_verifyconnection);
			return false;
		}
	}

	public void showToastNearToRefreshActionItem(int messageRes) {
		if (mRefreshActionItem != null && showUpdateButton()) {
			mRefreshActionItem.showToastNearToActionBar(getString(messageRes));
		}
	}

	protected void setPullToRefresh(View activityView) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			Integer refreshViewId = getPullToRefreshViewId();
			if (refreshViewId != null) {
				mPullToRefreshAttacher = new PullToRefreshAttacher(this);
				DefaultHeaderTransformer ht = (DefaultHeaderTransformer) mPullToRefreshAttacher
						.getHeaderTransformer();
				ht.setPullText(getString(R.string.pulltorefresh_pull));
				ht.setRefreshingText(getString(R.string.updating));
				ht.setTextColor(Color.WHITE);
				mPullToRefreshAttacher.setRefreshableView(
						activityView.findViewById(refreshViewId),
						new OnRefreshListener() {
							@Override
							public void onRefreshStarted(View view) {
								executeAsyncTask();
							}
						});
			}
		}
	}

	protected void setDataUpdateLayout(View activityView) {
		Integer dulId = getDataUpdateLayoutId();
		if (dulId == null) {
			return;
		}
		View dul = activityView.findViewById(dulId);
		if (dul instanceof DataUpdateLayout) {
			dulLoading = (DataUpdateLayout) dul;
			for (View view : getDataUpdateViewsToToggle(activityView)) {
				dulLoading.addViewToToggle(view);
			}
		}
	}

	public void setDataUpdateLayoutMessage(String message) {
		if (dulLoading != null) {
			dulMessage = message;
			dulLoading.setMessage(message);
		}
	}

	public void setDataUpdateLayoutMessage(int messageRes) {
		if (dulLoading != null) {
			dulMessage = getString(messageRes);
			dulLoading.setMessage(messageRes);
		}
	}
}
