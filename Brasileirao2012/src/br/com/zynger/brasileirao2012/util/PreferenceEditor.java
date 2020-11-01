package br.com.zynger.brasileirao2012.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferenceEditor {
	
	private final static String PREFERENCE_UPDATEAPP = "cb_updatecheck";
	private final static String PREFERENCE_AUTOUPDATE = "cb_actsupdates";
	private final static String PREFERENCE_DOWNLOADIMAGES = "cb_imgsdownload";
	private static final String PREFERENCE_SHOWREALTIMERANKING = "cb_realtimeranking";
	private static final String PREFERENCE_REALTIMEUPDATEINTERVAL = "list_realtimeinterval";
	private static final String PREFERENCE_REALTIMEAUDIO = "cb_realtimeaudio";
	private static final String PREFERENCE_REALTIMEVIBRATION = "cb_realtimevibrate";
	private static final String PREFERENCE_FOLLOWEDREALTIMEMATCH = "str_followedrealtimematch";
	private static final String PREFERENCE_LASTUPDATEALL = "str_lastupdateall";
	
	private static final String PREFERENCE_ONCE_SHOWACTIONLOGOCLICK_HINT = "showactionlogoclick_hint";
	
	public static SharedPreferences getDefaultSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public static boolean shouldUpdateApp(Context context){
		return getBoolean(context, PREFERENCE_UPDATEAPP, true);
	}
	
	public static boolean shouldAutoUpdate(Context context){
		return getBoolean(context, PREFERENCE_AUTOUPDATE, false);
	}
	
	public static boolean shouldDownloadImages(Context context){
		return getBoolean(context, PREFERENCE_DOWNLOADIMAGES, true);
	}

	public static boolean shouldShowRealTimeRanking(Context context) {
		return getBoolean(context, PREFERENCE_SHOWREALTIMERANKING, false);
	}
	
	public static String getRealTimeUpdateInterval(Context context){
		return getDefaultSharedPreferences(context).getString(PREFERENCE_REALTIMEUPDATEINTERVAL, "90000");
	}

	public static boolean shouldServicePlaySound(Context context) {
		return getBoolean(context, PREFERENCE_REALTIMEAUDIO, true);
	}

	public static boolean shouldServiceVibrate(Context context) {
		return getBoolean(context, PREFERENCE_REALTIMEVIBRATION, true);
	}
	
	public static boolean hasNotSeenActionLogoClickHint(Context context) {
		return getBoolean(context, PREFERENCE_ONCE_SHOWACTIONLOGOCLICK_HINT, true);
	}
	
	public static void setSeenActionLogoClickHint(Context context) {
		getDefaultSharedPreferences(context).edit().putBoolean(
				PREFERENCE_ONCE_SHOWACTIONLOGOCLICK_HINT, false).commit();
	}

	public static String getLastGeneralUpdate(Context context) {
		return getDefaultSharedPreferences(context).getString(PREFERENCE_LASTUPDATEALL, null);
	}
	
	public static void setLastGeneralUpdate(Context context, Calendar calendar) {
		String value = SimpleDateFormat.getDateTimeInstance().format(calendar.getTime());
		getDefaultSharedPreferences(context).edit().putString(PREFERENCE_LASTUPDATEALL, value).commit();
	}
	
	public static String getFollowedRealTimeMatchJson(Context context) {
		return getDefaultSharedPreferences(context).getString(PREFERENCE_FOLLOWEDREALTIMEMATCH, null);
	}

	public static void setFollowedRealTimeMatchJson(Context context, String json) {
		getDefaultSharedPreferences(context).edit().putString(PREFERENCE_FOLLOWEDREALTIMEMATCH, json).commit();
	}
	
	private static boolean getBoolean(Context context, String constant, boolean def) {
		return getDefaultSharedPreferences(context).getBoolean(constant, def);
	}
}