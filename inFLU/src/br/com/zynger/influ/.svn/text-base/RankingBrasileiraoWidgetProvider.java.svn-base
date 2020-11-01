package br.com.zynger.influ;

import java.util.Iterator;
import java.util.TreeMap;

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
import br.com.zynger.influ.model.StatClub;
import br.com.zynger.influ.util.FileHandler;

public class RankingBrasileiraoWidgetProvider extends AppWidgetProvider {
	
	public static final String DEBUG_TAG = "inFLU";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		try {
			updateRankingBrasileiraoWidgetContent(context, appWidgetManager);
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Failed", e);
		}
	}

	public static void updateRankingBrasileiraoWidgetContent(Context context, AppWidgetManager appWidgetManager) {
		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.ranking_bras_widget_layout);
		setWidgetTheme(context, remoteView);
		
		@SuppressWarnings("unchecked")
		TreeMap<Integer, StatClub> tm = (TreeMap<Integer, StatClub>) FileHandler.openBackup(context, RankingTableActivity.ARQUIVO_BRASILEIRAO);
		
		if(tm != null){
			StatClub flu = null;
			for (Iterator<StatClub> it = tm.values().iterator(); it.hasNext();) {
				StatClub sc = (StatClub) it.next();
				if(sc.getName().toLowerCase().equals("fluminense")){
					flu = sc;
					break;
				}
			}
			
			StatClub sc1 = null, sc2 = null, sc3 = null, sc4 = null;
			
			if(flu.getPosition() != 1 && flu.getPosition() != 19 && flu.getPosition() != 20){				
				sc1 = tm.get(flu.getPosition()-1);
				sc2 = flu;
				sc3 = tm.get(flu.getPosition()+1);
				sc4 = tm.get(flu.getPosition()+2);
			}else if(flu.getPosition() == 1){
				sc1 = flu;
				sc2 = tm.get(2);
				sc3 = tm.get(3);
				sc4 = tm.get(4);
			}else if(flu.getPosition() == 19){
				sc1 = tm.get(17);
				sc2 = tm.get(18);
				sc3 = flu;
				sc4 = tm.get(20);
			}else if(flu.getPosition() == 20){
				sc1 = tm.get(17);
				sc2 = tm.get(18);
				sc3 = tm.get(19);
				sc4 = flu;
			}
			
			setRow(sc1, remoteView, R.rankingbraswidget.pos1, R.rankingbraswidget.nm1, R.rankingbraswidget.pts1, R.rankingbraswidget.won1);
			setRow(sc2, remoteView, R.rankingbraswidget.pos2, R.rankingbraswidget.nm2, R.rankingbraswidget.pts2, R.rankingbraswidget.won2);
			setRow(sc3, remoteView, R.rankingbraswidget.pos3, R.rankingbraswidget.nm3, R.rankingbraswidget.pts3, R.rankingbraswidget.won3);
			setRow(sc4, remoteView, R.rankingbraswidget.pos4, R.rankingbraswidget.nm4, R.rankingbraswidget.pts4, R.rankingbraswidget.won4);
		}else{
			remoteView.setTextViewText(R.rankingbraswidget.nm1, "Dados inexistentes.");
		}

		Intent launchAppIntent = new Intent(context, SplashScreen.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.rankingbraswidget.full_widget, launchAppPendingIntent);

		ComponentName rankingWidget = new ComponentName(context, RankingBrasileiraoWidgetProvider.class);
		appWidgetManager.updateAppWidget(rankingWidget, remoteView);
	}
	
	private static void setRow(StatClub sc, RemoteViews remoteView, int pos, int name, int pts, int won){
		remoteView.setTextViewText(pos, String.valueOf(sc.getPosition()));
		remoteView.setTextViewText(name, sc.getName());
		remoteView.setTextViewText(pts, String.valueOf(sc.getPoints()));
		remoteView.setTextViewText(won, String.valueOf(sc.getWin()));
	}
	
	private static void setWidgetTheme(Context context, RemoteViews remoteView) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String widget_theme = sharedPrefs.getString("widget_theme", "Padrão");
		int drawable_res = R.drawable.appwidget_bg_clickable;
		if(widget_theme.equals("Glass")) drawable_res = R.drawable.widget_glass_background;
		remoteView.setInt(R.rankingbraswidget.full_widget, "setBackgroundResource", drawable_res);
	}

}
