package br.com.zynger.vamosmarcar.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.util.Log;
import br.com.zynger.vamosmarcar.Constants;

public class ConnectionHelper {
	private static final String WEBSITE_TO_PING = "http://www.amazon.com";

	private static boolean isConnectedByHardware(Context context) {
		try {
			ConnectivityManager conMan = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			State mobile = conMan.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState();
			State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.getState();

			if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
				return true;
			} else if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static boolean isConnected(Context context){
		return !isConnectedByHardware(context) ? tryToPingHost() : true;
	}

	private static boolean tryToPingHost() {
		try {
			return new CheckConnectionByPingTask().execute().get(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static class CheckConnectionByPingTask extends AsyncTask<Void, Void, Boolean> {		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				Log.e(Constants.TAG, "Trying ping to well-known host");
	            HttpURLConnection urlc = (HttpURLConnection) (new URL(WEBSITE_TO_PING).openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(500);
	            
	            urlc.connect();
	            return (urlc.getResponseCode() == 200);
	        } catch (IOException e) {
	            Log.e(Constants.TAG, "isConnected: IOException");
	            return false;
	        }
		}
	}
}