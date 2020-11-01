package br.com.zynger.brasileirao2012.service;

import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.RealTimeMatch;
import br.com.zynger.brasileirao2012.util.NotificationHelper;
import br.com.zynger.brasileirao2012.util.PreferenceEditor;

public class RealTimeMatchService extends Service {
	public static final String INTENT_REALTIMEMATCH_JSON = "REALTIMEMATCH_JSON";

	private TreeMap<String, Club> clubs;

	private long serviceStartedTime;
	private NotificationHelper notificationHelper;
	private UpdateDataTicker updateTicker;
	private Handler serviceHandler = null; //To handle the service

	private RealTimeMatch realTimeMatch;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		serviceStartedTime = System.currentTimeMillis();
		
		clubs = ClubsDB.getInstance(this).getClubs();
		notificationHelper = new NotificationHelper(this);
		
		startTickerTask(getRealTimeMatchFromIntent(intent));

		return super.onStartCommand(intent, flags, startId);
	}
	
	private RealTimeMatch getRealTimeMatchFromIntent(Intent intent) {
		try{
			return new RealTimeMatch(new JSONArray(
					intent.getStringExtra(INTENT_REALTIMEMATCH_JSON)), clubs);
		}catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	private void startTickerTask(RealTimeMatch realTimeMatch) {
		if(realTimeMatch != null){			
			setRealTimeMatch(realTimeMatch);
			showFollowNotification(this.realTimeMatch);

			if(serviceHandler == null) this.serviceHandler = new Handler();
			updateTicker = new UpdateDataTicker(this, clubs);
			serviceHandler.postDelayed(updateTicker, updateTicker.getInterval());		
		}else{
			Toast.makeText(this, R.string.realtimematchservice_error, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public IBinder onBind(Intent intent){
		return null;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if(serviceHandler != null) serviceHandler.removeCallbacks(updateTicker);
	}
	
	public void showFollowNotification(RealTimeMatch realTimeMatch) {
		showNotification(NotificationHelper.Notifications.FOLLOW, realTimeMatch);
	}
	
	public void showStartMatchNotification(RealTimeMatch realTimeMatch) {
		showNotification(NotificationHelper.Notifications.STARTMATCH, realTimeMatch);
	}
	
	public void showGoalNotification(RealTimeMatch realTimeMatch) {
		showNotification(NotificationHelper.Notifications.GOAL, realTimeMatch);
	}
	
	public void showWhistleNotification(RealTimeMatch realTimeMatch){
		showNotification(NotificationHelper.Notifications.WHISTLE, realTimeMatch);
	}
	
	private void showNotification(NotificationHelper.Notifications type, RealTimeMatch realTimeMatch){
		notificationHelper.showNotification(type, realTimeMatch.toString());
	}
	
	public void matchHasEnded(){
		showWhistleNotification(realTimeMatch);
		PreferenceEditor.setFollowedRealTimeMatchJson(this, null);
		stopSelf();
	}
	
	public RealTimeMatch getRealTimeMatch() {
		return realTimeMatch;
	}

	public void setRealTimeMatch(RealTimeMatch realTimeMatch) {
		this.realTimeMatch = realTimeMatch;
		PreferenceEditor.setFollowedRealTimeMatchJson(this, realTimeMatch.toJson().toString());
	}

	public long getStartedTime() {
		return serviceStartedTime;
	}

	public void scheduleNextRun(long interval) {
		serviceHandler.postDelayed(updateTicker, interval);
	}

}