package br.com.zynger.influ;

import java.io.IOException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.ViewFlinger;
import br.com.zynger.influ.web.HTMLManager;

public class HomeActivity extends Activity {
	
	private static final int UPDATE_NOTIFICATION = 1;
	
	private SharedPreferences prefs;
	
	private ImageButton nextGame, news, ranking, squad, statistics, tickets, phrases, idols, themes, share, rate, settings, about, logo;
	private OnTouchListener otl_greyOverlay;
	private MediaPlayer mp;
	@SuppressWarnings("unused")
	private ViewFlinger flinger;
	
	/*private TableLayout infoBar;
	private ImageButton ibCloseInfoBar, ibUpdateInfoBar;
	private ImageView infoBar_ng_t1badge, infoBar_ng_t2badge, infoBar_lg_t1badge, infoBar_lg_t2badge;
	private TextView infoBar_tv_teamNames, infoBar_tv_championship, infobar_tv_date, infoBar_tv_lastgame_team1score, infoBar_tv_lastgame_team2score;
	private AlphaAnimation anim_fadein, anim_fadeout;*/
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        //TODO refazer TODA activity de estatisticas, e mudar seu layout inclusive.
        
        loadViews();
        
        if(FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO) == null){
        	FileHandler.saveBackup(this, ThemeChooserActivity.ARQUIVO, new Theme("Verde", R.layout.tabs_bg_verde, R.drawable.tab_divider_verde, R.drawable.news_row_background_selector_verde, "#FF5F011A", "#FFA7173D", "#FF00372A", "#FF024234", "#FF10AD85", "#FF0FAD94"));
        }
        
        //flinger.lockCurrentScreen(); //TODO
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        if(prefs.getBoolean("cb_updatecheck", true)) new UpdateAppTask(this).execute();

        nextGame.setOnClickListener(getStartActivityListener(NextGameActivity.class));
        nextGame.setOnTouchListener(otl_greyOverlay);
        news.setOnClickListener(getStartActivityListener(NewsTabActivity.class));
        news.setOnTouchListener(otl_greyOverlay);
        ranking.setOnClickListener(getStartActivityListener(RankingActivity.class));
        ranking.setOnTouchListener(otl_greyOverlay);
        squad.setOnClickListener(getStartActivityListener(SquadActivity.class));
        squad.setOnTouchListener(otl_greyOverlay);
        statistics.setOnClickListener(getStartActivityListener(HistoryActivity.class));//StatisticsTabActivity.class));//StatisticsActivity.class));
        statistics.setOnTouchListener(otl_greyOverlay);
        tickets.setOnClickListener(getStartActivityListener(TicketsActivity.class));
        tickets.setOnTouchListener(otl_greyOverlay);
        phrases.setOnClickListener(getStartActivityListener(PhrasesActivity.class));
        phrases.setOnTouchListener(otl_greyOverlay);
        idols.setOnClickListener(getStartActivityListener(IdolsListActivity.class));
        idols.setOnTouchListener(otl_greyOverlay);
        themes.setOnClickListener(getStartActivityListener(ThemeChooserActivity.class));
        themes.setOnTouchListener(otl_greyOverlay);
        share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_home));
				startActivity(Intent.createChooser(intent, "Compartilhar com..."));
			}
		});
        share.setOnTouchListener(otl_greyOverlay);
        rate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=br.com.zynger.influ"));
				startActivity(intent);
			}
		});
        rate.setOnTouchListener(otl_greyOverlay);
        settings.setOnClickListener(getStartActivityListener(SettingsActivity.class));
        settings.setOnTouchListener(otl_greyOverlay);
        about.setOnClickListener(getStartActivityListener(AboutActivity.class));
        about.setOnTouchListener(otl_greyOverlay);
        
        if(logo != null) setMediaPlayer();

    }

	private void setMediaPlayer() {
		logo.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(mp != null){
					if(!mp.isPlaying()){
						Toast.makeText(view.getContext(), "Toque novamente para parar", Toast.LENGTH_SHORT).show();
						mp = MediaPlayer.create(view.getContext(), R.raw.denorteasul);
						mp.start();
					}else{
						mp.stop();
						mp.release();
						mp = null;
					}
				}else{
					Toast.makeText(view.getContext(), "Toque novamente para parar", Toast.LENGTH_SHORT).show();
					mp = MediaPlayer.create(view.getContext(), R.raw.denorteasul);
					mp.start();
				}
				
				if(mp != null){        				
					mp.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mediaplayer) {
							mediaplayer.stop();
							mediaplayer.release();
							mp = null;
						}
					});
				}
			}
			
		});
	}
    
	private View.OnClickListener getStartActivityListener(final Class<?> clazz){
    	View.OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent it = new Intent(view.getContext(), clazz);
				view.getContext().startActivity(it);
			}
		};
		
		return ocl;
    }

	private void loadViews() {
		flinger = (ViewFlinger) findViewById(R.home.flinger);
		
		nextGame = (ImageButton) findViewById(R.home.ibNextGame);
		news = (ImageButton) findViewById(R.home.ibNews);
		ranking = (ImageButton) findViewById(R.home.ibRanking);
		squad = (ImageButton) findViewById(R.home.ibSquad);
		statistics = (ImageButton) findViewById(R.home.ibStatistics);
		tickets = (ImageButton) findViewById(R.home.ibTickets);
		phrases = (ImageButton) findViewById(R.home.ibPhrases);
		idols = (ImageButton) findViewById(R.home.ibIdols);
		themes = (ImageButton) findViewById(R.home.ibThemes);
		share = (ImageButton) findViewById(R.home.ibShare);
		rate = (ImageButton) findViewById(R.home.ibRate);
		settings = (ImageButton) findViewById(R.home.ibSettings);
		about = (ImageButton) findViewById(R.home.ibAbout);
		
    	try{			
			logo = (ImageButton) findViewById(R.home.ibLogoBG);
		}catch(NullPointerException npe){
			logo = null;
		}
    	
    	otl_greyOverlay = new OnTouchListener() {
    		@Override
    		public boolean onTouch(View v, MotionEvent me) {
    			if (me.getAction() == MotionEvent.ACTION_DOWN) ((ImageButton) v).setColorFilter(Color.argb(150, 50, 50, 50), PorterDuff.Mode.SRC_ATOP);
    			else ((ImageButton) v).setColorFilter(null);
    			
    			return false;
    		}

    	};
	}
	
	private class UpdateAppTask extends AsyncTask<Void, Void, Boolean> {
		private Context c;
		private String marketVersion;
		
		public UpdateAppTask(Context c){
			this.setContext(c);
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			if(SplashScreen.isConnected(getContext())){
        		try {
        			String market = HTMLManager.getVersionAtMarket();
        			String thisVers = SplashScreen.getAppVersion(getContext());
        			if(!market.equals(thisVers)){
        				setMarketVersion(market);
        				return true;
        			}
        			else return false;
        		} catch (IOException e) {
        			return false;
        		} catch (NullPointerException e) {
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

		@Override
		protected void onPostExecute(Boolean result) {
			if(result == true){
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				
				Notification newVersionAvailable = new Notification();
				newVersionAvailable.icon = android.R.drawable.stat_notify_sync;
				newVersionAvailable.tickerText = "Versão " + getMarketVersion() + " disponível";
				newVersionAvailable.when = System.currentTimeMillis();
				
				Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
				notificationIntent.setData(Uri.parse("market://details?id=br.com.zynger.influ"));
				
				PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, 0);
				newVersionAvailable.setLatestEventInfo(getContext(), "Atualização - inFLU", newVersionAvailable.tickerText, contentIntent);
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
	}
}