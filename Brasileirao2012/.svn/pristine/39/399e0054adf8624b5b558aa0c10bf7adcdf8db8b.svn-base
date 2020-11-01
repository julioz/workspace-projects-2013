package br.com.zynger.brasileirao2012.adapters.pager;

import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class BasePagerAdapter<T extends SherlockFragment> extends
		FragmentStatePagerAdapter {
	
	public interface ActionModeDismissable {
		void dismissActionMode();
	}
	
	private final Context context;
	private SparseArray<T> cache;

	public BasePagerAdapter(SherlockFragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		this.context = activity;
		this.cache = new SparseArray<T>();
	}

	public Context getContext() {
		return context;
	}

	@Override
	public SherlockFragment getItem(int position) {
		if (cache.get(position) != null) {
			return cache.get(position);
		} else {
			T fragment = createFragment(position);
			cache.put(position, fragment);
			return fragment;
		}
	}
	
	public void dismissActionMode() {
		for (int index = 0; index < getCount(); index++) {
			SherlockFragment fragment = getItem(index);
			if(fragment instanceof ActionModeDismissable) {
				((ActionModeDismissable) fragment).dismissActionMode();
			}
		}
	}

	protected abstract T createFragment(int position);
}