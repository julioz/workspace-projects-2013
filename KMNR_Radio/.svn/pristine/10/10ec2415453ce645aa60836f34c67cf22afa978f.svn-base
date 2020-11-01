package edu.mst.kmnrradio.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public abstract class ConnectionHelper {
	
	public static boolean isConnected(Context c){
		try{
			ConnectivityManager conMan = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
			
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