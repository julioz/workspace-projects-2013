package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.FutureGame;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.HTMLManager;

@SuppressWarnings("deprecation")
public class RankingLibertadoresTabActivity extends TabActivity {
	
	public static final int LIBERTADORES = 9;
	
	public static final int LIB_GRP_1 = 1;
	public static final int LIB_GRP_2 = 2;
	public static final int LIB_GRP_3 = 3;
	public static final int LIB_GRP_4 = 4;
	public static final int LIB_GRP_5 = 5;
	public static final int LIB_GRP_6 = 6;
	public static final int LIB_GRP_7 = 7;
	public static final int LIB_GRP_8 = 8;
	
	private Theme theme;
	
	private TextView actionbar_tv;
	private ImageButton plus, update;
	private RotateAnimation rot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking_tabs);
		
		loadViews();
		updateTheme();

		/** TabHost will have Tabs */
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.getTabWidget().setDividerDrawable(theme.getTabsDivider());

		/** TabSpec used to create a new tab.
		 * By using TabSpec only we can able to setContent to the tab.
		 * By using TabSpec setIndicator() we can set name to tab. */

		/** tid1 is firstTabSpec Id. Its used to access outside. */
		TabSpec firstTab = tabHost.newTabSpec("tid1");
		TabSpec secondTab = tabHost.newTabSpec("tid2");
		TabSpec thirdTab = tabHost.newTabSpec("tid3");
		TabSpec fourthTab = tabHost.newTabSpec("tid4");
		TabSpec fifthTab = tabHost.newTabSpec("tid5");
		TabSpec sixthTab = tabHost.newTabSpec("tid6");
		TabSpec seventhTab = tabHost.newTabSpec("tid7");
		TabSpec eightTab = tabHost.newTabSpec("tid8");


		/** TabSpec setIndicator() is used to set name for the tab. */
		/** TabSpec setContent() is used to set content for a particular tab. */
		firstTab.setIndicator(createTabView("Grupo 1")).setContent(getIntent(LIB_GRP_1));
		secondTab.setIndicator(createTabView("Grupo 2")).setContent(getIntent(LIB_GRP_2));
		thirdTab.setIndicator(createTabView("Grupo 3")).setContent(getIntent(LIB_GRP_3));
		fourthTab.setIndicator(createTabView("Grupo 4")).setContent(getIntent(LIB_GRP_4));
		fifthTab.setIndicator(createTabView("Grupo 5")).setContent(getIntent(LIB_GRP_5));
		sixthTab.setIndicator(createTabView("Grupo 6")).setContent(getIntent(LIB_GRP_6));
		seventhTab.setIndicator(createTabView("Grupo 7")).setContent(getIntent(LIB_GRP_7));
		eightTab.setIndicator(createTabView("Grupo 8")).setContent(getIntent(LIB_GRP_8));


		/** Add tabSpec to the TabHost to display. */
		tabHost.addTab(firstTab);
		tabHost.addTab(secondTab);
		tabHost.addTab(thirdTab);
		tabHost.addTab(fourthTab);
		tabHost.addTab(fifthTab);
		tabHost.addTab(sixthTab);
		tabHost.addTab(seventhTab);
		tabHost.addTab(eightTab);
	}
	
	//TODO remover quando for necessario visualizar novamente esta activity
	/*@Override
	protected void onResume() {
		if(!getIntent().getBooleanExtra("SHOW_ACT", false)){			
			finish();
			plus.performClick();
		}
		super.onResume();
	}*/
	
	private void loadViews() {
		actionbar_tv = (TextView) findViewById(R.ranking_tabs.tv_actionbar);
		plus = (ImageButton) findViewById(R.ranking_tabs.ib_actionbar_plus);
		update = (ImageButton) findViewById(R.ranking_tabs.ib_actionbar_update);
		
		actionbar_tv.setText("Libertadores");
		
		rot = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rot.setInterpolator(new LinearInterpolator());
		rot.setDuration(900L);
		rot.setStartTime(200L);

		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(rot);
				new DownloadFilesTask(v.getContext()).execute();
			}
		});
		
		plus.setVisibility(View.GONE); //TODO remover quando puder visualizar as finais
		/*plus.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				new GetFinalsTask(v.getContext()).execute();
			}
		});*/
	}
	
	private void updateTheme() {
		theme = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.ranking_tabs.ll_actbg).setBackgroundColor(theme.getContent_background());
		
		int colors[] = { theme.getAbgradstart() , theme.getAbgradend() };
		findViewById(R.ranking_tabs.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		
		findViewById(R.ranking_tabs.ll_actbg).invalidate();
	}
	
	private View createTabView(String text) {
		View view = LayoutInflater.from(this).inflate(theme.getTabsBG(), null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	private Intent getIntent(int group){
		Intent it = new Intent(this, RankingTableActivity.class);
		it.putExtra("championship", LIBERTADORES);
		it.putExtra("libertagroup", group);
	    
	    return it;
	}
	
	private class GetFinalsTask extends AsyncTask<Void, Void, Void> {
		Context c;
		ProgressDialog pd;
		boolean hasDownload = false, success = false;
		
		public GetFinalsTask(Context c){
			this.c = c;
			this.hasDownload = false;
			this.success = false;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void onPreExecute() {
			if((ArrayList<ArrayList<FutureGame>>) FileHandler.openBackup(c, RankingFinalsActivity.ARQUIVO_LIBERTADORES) == null){
				hasDownload = true;
			}
			
			if(hasDownload){
				pd = new ProgressDialog(c);
				pd.setIndeterminate(true);
				pd.setCancelable(false);
				pd.setTitle("Download");
				pd.setMessage("Atualizando...");
				pd.show();
			}
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			if(hasDownload){				
				ArrayList<ArrayList<FutureGame>> al;
				try {
					al = HTMLManager.getFinals(true);
				} catch (IOException e) {
					al = null;
				}
				if(al != null){
					FileHandler.saveBackup(c, RankingFinalsActivity.ARQUIVO_LIBERTADORES, al);
					success = true;
				}
			}else success = true;
			return null;
		}
		
		protected void onPostExecute(Void result) {
			if(hasDownload) pd.dismiss();
			if(success){
				//TODO remover quando for necessario visualizar novamente esta activity
				//if(getIntent().getBooleanExtra("SHOW_ACT", false)) finish();
				Intent it = new Intent(c, RankingFinalsActivity.class);
				it.putExtra("CHAMPIONSHIP", 0);
				startActivity(it);
				finish();
			}else{
				Toast.makeText(c, "Não foi possível atualizar os dados. Tente mais tarde.", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
		Context c;
		ProgressDialog pd;
		boolean success = false;
		
		public DownloadFilesTask(Context c){
			this.c = c;
		}
		
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(c);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.setTitle("Download");
			pd.setMessage("Atualizando...");
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				FileHandler.saveBackup(c, RankingTableActivity.ARQUIVO_LIBERTADORES, HTMLManager.getRankingLibertadores());
				Log.i("inFLU", "O arquivo " + RankingTableActivity.ARQUIVO_LIBERTADORES + " foi salvo.");
				success = true;
			}catch (IOException io) {
				runOnUiThread(new Runnable() {
				     public void run() {			
				    	 Toast.makeText(c, "Conecte-se à internet para atualizar os dados ou tente mais tarde.", Toast.LENGTH_SHORT).show();
				    	 pd.dismiss();
				    }
				});
			}	
			return null;
		}
		
		protected void onPostExecute(Void result) {
			if(success){				
				runOnUiThread(new Runnable() {
					public void run() {			
						Toast.makeText(c, "Dados atualizados", Toast.LENGTH_SHORT).show();
						pd.dismiss();
					}
				});
			}
		}
	}
}
