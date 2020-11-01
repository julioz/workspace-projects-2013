package br.com.zynger.brasileirao2012.base;

import java.util.Locale;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.util.Constants;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.analytics.tracking.android.EasyTracker;

public abstract class BaseActivity extends SherlockFragmentActivity {

	private TextView title;
	private LinearLayout ll_holder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionbar = getSupportActionBar();
		replaceActionBarByCustomView(actionbar);
		loadActionBarViews(actionbar);
		setTitleTypeface();
		setTitle(getString(getActionBarTitle()));
		setIcon(getActionBarIcon());
	}

	private void loadActionBarViews(ActionBar actionbar) {
		View customView = actionbar.getCustomView();
		title = (TextView) customView.findViewById(R.actionbar.tv_title);
		ll_holder = (LinearLayout) customView.findViewById(R.actionbar.ll_viewholder);
	}

	private void replaceActionBarByCustomView(ActionBar actionbar) {
		View customView = LayoutInflater.from(this).inflate(
				R.layout.actionbar_custom_layout, null);

		actionbar.setCustomView(customView);
		actionbar.setDisplayShowCustomEnabled(true);
	}
	
	public void addActionBarRightView(View view){
		ll_holder.addView(view, 0);
		ll_holder.setVisibility(View.VISIBLE);
	}

	private void setTitleTypeface() {
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/tertre-xbol.otf");
		title.setTypeface(face);
	}

	public void setTitle(String title) {
		if(title != null){			
			this.title.setText(title.toUpperCase(Locale.getDefault()));
		}
	}

	public void setIcon(int resource) {
		getSupportActionBar().setIcon(resource);
	}

	protected abstract int getActionBarIcon();

	protected abstract int getActionBarTitle();
	
	@Override
	  public void onStart() {
	    super.onStart();
	    try {	    	
	    	EasyTracker.getInstance().activityStart(this);
	    	EasyTracker.getTracker().sendView(this.getClass().getName());
	    } catch (Exception e) {
	    	Log.e(Constants.TAG, "Analytics exception " + e.toString());
	    }
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    try {
	    	EasyTracker.getInstance().activityStop(this);
	    } catch (Exception e) {
	    	Log.e(Constants.TAG, "Analytics exception " + e.toString());
	    }
	  }
}
