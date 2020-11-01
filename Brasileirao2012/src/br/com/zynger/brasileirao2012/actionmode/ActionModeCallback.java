package br.com.zynger.brasileirao2012.actionmode;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ActionModeCallback<T> implements ActionMode.Callback {
	public interface ActionModeListener<T> {
		Context getContext();
		int getMenuResource();
		void onPrepareActionMode();
		void changeMenuItems(T model);
		boolean onActionModeItemClick(T model, ActionMode mode, int itemId);
		T getItemAtPosition(int position);
	}
	
	private ActionModeListener<T> listener;
	protected ActionMode mActionMode;
	private T model;
	private Menu menu;
	private boolean visible;
	private ListView listView;
	
	public ActionModeCallback(ActionModeListener<T> listener, ListView listView) {
		this.listener = listener;
		this.listView = listView;
		
		setListViewListeners();
	}
	
	private void setListViewListeners() {
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		listView.setOnScrollListener(new OnScrollListener() {
			private int scrollState;

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				this.scrollState = scrollState;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					dismissActionMode();
				}
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				T itemAtPosition = listener.getItemAtPosition(position);
				if(itemAtPosition != null) {
					setModel(itemAtPosition);
					startActionMode();
				}
			}
		});
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setModel(T model) {
		this.model = model;
		listener.changeMenuItems(model);
	}
	
	public T getModel() {
		return model;
	}
	
	public void setItemVisibility(int id, boolean visible) {
		if(menu != null) {
			menu.findItem(id).setVisible(visible);
		}
	}
	
	public void startActionMode() {
		Context context = listener.getContext();
		if(!isVisible()){
			if (context instanceof SherlockActivity) {
				mActionMode = ((SherlockActivity) context)
						.startActionMode(this);
			}else if(context instanceof SherlockFragmentActivity){
				mActionMode = ((SherlockFragmentActivity) context)
						.startActionMode(this);
			}
		}
	}
	
	public void dismissActionMode() {
		if(mActionMode != null && isVisible()){
			mActionMode.finish();
		}
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(listener.getMenuResource(), menu);
		this.menu = menu;
		listener.changeMenuItems(getModel());
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		visible = true;
		listener.onPrepareActionMode();
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		mode.finish();
		return listener.onActionModeItemClick(model, mode, item.getItemId());
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		visible = false;
		listView.clearChoices();
		listView.requestLayout();
	}
}
