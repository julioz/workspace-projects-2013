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
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.HTMLManager;

@SuppressWarnings("deprecation")
public class StatisticsTabActivity extends TabActivity {
	
	private Theme theme;
	
	private ImageButton update;
	private RotateAnimation rot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statisticstab);
		
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
		
		
		/** TabSpec setIndicator() is used to set name for the tab. */
		/** TabSpec setContent() is used to set content for a particular tab. */
		firstTab.setIndicator(createTabView("Dados")).setContent(new Intent(this, StatisticsDataActivity.class));
		secondTab.setIndicator(createTabView("História")).setContent(new Intent(this, HistoryActivity.class));
		

		/** Add tabSpec to the TabHost to display. */
		tabHost.addTab(firstTab);
		tabHost.addTab(secondTab);
	}
	
	private void loadViews() {
		update = (ImageButton) findViewById(R.statistics_tabs.ib_actionbar_update);
		
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
	}
	
	private void updateTheme() {
		theme = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.statistics_tabs.ll_actbg).setBackgroundColor(theme.getContent_background());
		
		int colors[] = { theme.getAbgradstart() , theme.getAbgradend() };
		findViewById(R.statistics_tabs.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		
		findViewById(R.statistics_tabs.ll_actbg).invalidate();
	}

	private View createTabView(String text) {
		View view = LayoutInflater.from(this).inflate(theme.getTabsBG(), null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	private class DownloadFilesTask extends AsyncTask<Void, Void, ArrayList<String>> {
		Context context;
		ProgressDialog pd;
		
		public DownloadFilesTask(Context c){
			this.context = c;
			pd = new ProgressDialog(context);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.setTitle("Download");
			pd.setMessage("Atualizando...");
		}
		
		@Override
		protected void onPreExecute() {
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			try {
				return HTMLManager.getStatisticsDataInfo();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			pd.dismiss();
			if(result != null){
				FileHandler.saveBackup(this.context, StatisticsDataActivity.ARQUIVO, result);
				Toast.makeText(this.context, "Dados atualizados", Toast.LENGTH_SHORT).show();
			}
			else Toast.makeText(this.context, "Não foi possível atualizar os dados", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
	}
}
