package br.com.zynger.libertadores.asynctasks;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.AsyncTask;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.web.GooglePlayParser;

public class UpdateAppTask extends AsyncTask<Void, Void, Boolean> {
	private static final int UPDATE_NOTIFICATION = 1;
	private Context c;
	private String appVersion, marketVersion;
	
	public UpdateAppTask(Context c, String appVersion){
		this.setContext(c);
		this.setAppVersion(appVersion);
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		if(isConnected()){
    		try {
    			String market = GooglePlayParser.getVersionAtMarket();
    			String thisVers = this.getAppVersion();
    			if(!market.equals(thisVers)){
    				setMarketVersion(market);
    				return true;
    			}
    			else return false;
    		} catch (IOException e) {
    			return false;
    		} catch (NullPointerException e) {
    			return false;
    		} catch (NumberFormatException e) {
    			return false;
    		} catch (IndexOutOfBoundsException e) {
    			return false;
    		} catch (ExceptionInInitializerError e) {
    			return false;
    		} catch (NoSuchFieldError e) {
    			return false;
    		} catch (OutOfMemoryError e) {
    			return false;
    		}
    	}else return false;
	}
	
	private void setMarketVersion(String market) {
		this.marketVersion = market;
	}
	
	private String getMarketVersion() {
		return this.marketVersion;
	}
	
	private void setAppVersion(String appv) {
		this.appVersion = appv;
	}
	
	private String getAppVersion() {
		return this.appVersion;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if(result == true){
			NotificationManager notificationManager =
					(NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
			
			Notification newVersionAvailable = new Notification();
			newVersionAvailable.icon = android.R.drawable.stat_notify_sync;
			newVersionAvailable.tickerText = getContext().getString(R.string.aboutactivity_version)
					+ " " + getMarketVersion() + " " + getContext().getString(R.string.updateapp_available).toLowerCase();
			newVersionAvailable.when = System.currentTimeMillis();
			newVersionAvailable.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
			
			Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
			notificationIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=br.com.zynger.libertadores"));
			
			PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, 0);
			newVersionAvailable.setLatestEventInfo(getContext(), getContext().getString(R.string.updateapp_update)
					+ " - " + getContext().getString(R.string.app_name),
					newVersionAvailable.tickerText, contentIntent);
			notificationManager.notify(UPDATE_NOTIFICATION, newVersionAvailable);
		}
		super.onPostExecute(result);
	}

	public Context getContext() {
		return c;
	}

	public void setContext(Context c) {
		this.c = c;
	}
	
	private boolean isConnected() {
		try{
			ConnectivityManager conMan = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

			State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();			

			if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
				return true;
			} else if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				return true;
			}else{
				return false;
			}
		}catch(NullPointerException e){
			return false;
		}
	}
	
}