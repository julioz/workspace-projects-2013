package br.com.zynger.brasileirao2012.asynctasks;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import br.com.zynger.brasileirao2012.util.AppHelper;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.web.HTMLManager;

public class CheckAppStoreForUpdateTask extends AsyncTask<Void, Void, Boolean> {
	private static final int UPDATE_NOTIFICATION = 1;
	
	private Context context;
	private String marketVersion;

	public CheckAppStoreForUpdateTask(Context context){
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		if(ConnectionHelper.isConnected(context)){
			try {
				String market = HTMLManager.getVersionAtMarket();
				String thisVers = AppHelper.getAppVersion(context);
				if(!market.equals(thisVers)){
					this.marketVersion = market;
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
			}
		}else return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if(result == true){
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			Notification newVersionAvailable = new Notification();
			newVersionAvailable.icon = android.R.drawable.stat_notify_sync;
			newVersionAvailable.tickerText = "Versão " + marketVersion + " disponível";
			newVersionAvailable.when = System.currentTimeMillis();
			newVersionAvailable.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;

			Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
			notificationIntent.setData(Uri.parse("market://details?id=br.com.zynger.brasileirao2012"));

			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			newVersionAvailable.setLatestEventInfo(context,
					"Atualização - Brasileirão", newVersionAvailable.tickerText, contentIntent);
			notificationManager.notify(UPDATE_NOTIFICATION, newVersionAvailable);
		}
		super.onPostExecute(result);
	}


}
