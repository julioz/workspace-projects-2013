package br.com.zynger.brasileirao2012;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.asynctasks.AsyncTaskListener;
import br.com.zynger.brasileirao2012.asynctasks.GetRealTimeSquadTask;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.RealTimeMatch;
import br.com.zynger.brasileirao2012.model.RealTimePlayer;
import br.com.zynger.brasileirao2012.view.DataUpdateLayout;
import br.com.zynger.brasileirao2012.view.RealTimePlayerLinearLayout;

/*
 * TODO Refactor this class, make it work
 * and re-enable the references to it from other
 * activities (mainly RealTimeMoveToMoveActivity).
 */

public class RealTimeMatchSquadActivity extends Activity
	implements AsyncTaskListener<LinkedHashMap<String, ArrayList<RealTimePlayer>>> {
	
	public final static String INTENT_REALTIMEMATCH = "realtimematch";
	
	private RealTimeMatch rtm;
	
	private TextView tv_squadl, tv_squadr;
	private LinearLayout ll_squads, ll_rtml, ll_rtmr;
	private DataUpdateLayout dul_loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.realtimematchsquadactivity);
		
		String rtmString = getIntent().getStringExtra(INTENT_REALTIMEMATCH);
		try {
			this.rtm = new RealTimeMatch(new JSONArray(rtmString), ClubsDB.getInstance(this).getClubs());
			
			loadViews();
			
			new GetRealTimeSquadTask(this, rtm).execute();
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, R.string.message_errortryagain, Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	private void loadViews() {
		tv_squadl = (TextView) findViewById(R.realtimematchsquadactivity.tv_squadl);
		tv_squadr = (TextView) findViewById(R.realtimematchsquadactivity.tv_squadr);
		
		ll_rtml = (LinearLayout) findViewById(R.realtimematchsquadactivity.ll_squadl);
		ll_rtmr = (LinearLayout) findViewById(R.realtimematchsquadactivity.ll_squadr);
		ll_squads = (LinearLayout) findViewById(R.realtimematchsquadactivity.ll_squads);
		dul_loading = (DataUpdateLayout) findViewById(R.realtimematchsquadactivity.dul_loading);
		dul_loading.addViewToToggle(ll_squads);
	}

	public void populateLayout(LinkedHashMap<String, ArrayList<RealTimePlayer>> map) {
		ll_rtml.removeAllViews();
		ll_rtmr.removeAllViews();
		int i = 0;
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if(i == 0) tv_squadl.setText(key);
			else tv_squadr.setText(key);
			
			for (RealTimePlayer rtp : map.get(key)) {
				if(i == 0) ll_rtml.addView(new RealTimePlayerLinearLayout(this, rtp));
				else ll_rtmr.addView(new RealTimePlayerLinearLayout(this, rtp));
			}
			
			i++;
		}
	}
	
	@Override
	public Context getContext() {
		return getContext();
	}

	@Override
	public void preExecution() {
		dul_loading.setMessage(R.string.updating);
		dul_loading.show();
	}
	
	@Override
	public void onComplete(LinkedHashMap<String, ArrayList<RealTimePlayer>> result) {
		populateLayout(result);
		dul_loading.hide();
	}


	@Override
	public void onFail(String message) {
		dul_loading.setErrorMessage(message);
	}
}