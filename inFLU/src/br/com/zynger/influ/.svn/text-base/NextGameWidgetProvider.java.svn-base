package br.com.zynger.influ;

import java.util.Calendar;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import br.com.zynger.influ.model.Game;
import br.com.zynger.influ.util.FileHandler;

public class NextGameWidgetProvider extends AppWidgetProvider {

	public static final String DEBUG_TAG = "inFLU";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		try {
			updateWidgetContent(context, appWidgetManager);
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Failed", e);
		}
	}

	public static void updateWidgetContent(Context context, AppWidgetManager appWidgetManager) {
		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.nextgame_widget_layout);
		setWidgetTheme(context, remoteView);
		
		Game game = (Game) FileHandler.openBackup(context, NextGameActivity.ARQUIVO);
		if(game != null){
			Calendar cal = Calendar.getInstance();
			Date gameDate = game.getDate();
			gameDate.setYear(cal.get(Calendar.YEAR)-1900);
			
			gameDate.setMonth(gameDate.getMonth()-1);
			
			cal.set(Calendar.DAY_OF_MONTH, gameDate.getDate());
			cal.set(Calendar.MONTH, gameDate.getMonth());
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(gameDate);
			cal.set(Calendar.HOUR_OF_DAY, tmp.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, gameDate.getMinutes());
			
			String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			if(day.length() == 1) day = "0" + day;
			String month = String.valueOf(cal.get(Calendar.MONTH)+1);
			if(month.length() == 1) month = "0" + month;
			String minute = String.valueOf(cal.get(Calendar.MINUTE));
			if(minute.length() == 1) minute = "0" + minute;
			
			String week = getWeekDay(cal.get(Calendar.DAY_OF_WEEK));
			
			String game_data = week + day + "/" + month + " - " + cal.get(Calendar.HOUR_OF_DAY) + ":" + minute;
			
			remoteView.setTextViewText(R.nextgamewidget.stadium, game.getStadium());
			remoteView.setTextViewText(R.nextgamewidget.team1, game.getClub1().getName().toUpperCase());
			remoteView.setTextViewText(R.nextgamewidget.team2, game.getClub2().getName().toUpperCase());
			remoteView.setTextViewText(R.nextgamewidget.datetime, game_data);
		}else{
			remoteView.setTextViewText(R.nextgamewidget.stadium, "Dados inexistentes.\nAtualize-os para utilizar o widget.");
		}

		Intent launchAppIntent = new Intent(context, SplashScreen.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.nextgamewidget.full_widget, launchAppPendingIntent);

		ComponentName nextGameWidget = new ComponentName(context, NextGameWidgetProvider.class);
		appWidgetManager.updateAppWidget(nextGameWidget, remoteView);
	}

	private static void setWidgetTheme(Context context, RemoteViews remoteView) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String widget_theme = sharedPrefs.getString("widget_theme", "Padrão");
		int drawable_res = R.drawable.appwidget_bg_clickable;
		if(widget_theme.equals("Glass")) drawable_res = R.drawable.widget_glass_background;
		remoteView.setInt(R.nextgamewidget.full_widget, "setBackgroundResource", drawable_res);
	}
	
	private static String getWeekDay(int calendarDay){
		switch (calendarDay) {
		case Calendar.SUNDAY: return "Dom, ";
		case Calendar.MONDAY: return "Seg, ";
		case Calendar.TUESDAY: return "Ter, ";
		case Calendar.WEDNESDAY: return "Qua, ";
		case Calendar.THURSDAY: return "Qui, ";
		case Calendar.FRIDAY: return "Sex, ";
		case Calendar.SATURDAY: return"Sab, ";
		default: break;
		}
		return "";
	}
}