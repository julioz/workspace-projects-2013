 package br.com.zynger.influ;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.HTMLManager;

public class RankingActivity extends Activity {
	
	private TableRow carioca, libertadores, brasileirao;
	private ImageButton update;
	private RotateAnimation rot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		
		carioca = (TableRow) findViewById(R.ranking_selector.button1);
		libertadores = (TableRow) findViewById(R.ranking_selector.button2);
		brasileirao = (TableRow) findViewById(R.ranking_selector.button3);
		
		update = (ImageButton) findViewById(R.ranking_selector.ib_actionbar_update);
		
		updateTheme();
		
		carioca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), RankingCariocaTabActivity.class);
				v.getContext().startActivity(it);
			}
		});
		
		libertadores.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), RankingLibertadoresTabActivity.class);
				v.getContext().startActivity(it);
			}
		});
		
		brasileirao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), RankingTableActivity.class);
				v.getContext().startActivity(it);
			}
		});
		
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
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.ranking_selector.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.ranking_selector.ll_actbg).setBackgroundColor(t.getAct_background());
		TextView tv_top = (TextView) findViewById(R.ranking_selector.tv_texttop);
		tv_top.setTextColor(t.getSec_text());
		findViewById(R.ranking_selector.ll_actbg).invalidate();
	}
	
	private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
		private Context c;
		private ProgressDialog pd;
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
				FileHandler.saveBackup(c, RankingTableActivity.ARQUIVO_BRASILEIRAO, HTMLManager.getRankingBrasileirao());
				Log.i("inFLU", "O arquivo " + RankingTableActivity.ARQUIVO_BRASILEIRAO + " foi salvo.");
				FileHandler.saveBackup(c, RankingTableActivity.ARQUIVO_CARIOCA_A, HTMLManager.getRankingCarioca(RankingCariocaTabActivity.CARIOCA_GRUPO_A));
				Log.i("inFLU", "O arquivo " + RankingTableActivity.ARQUIVO_CARIOCA_A + " foi salvo.");
				FileHandler.saveBackup(c, RankingTableActivity.ARQUIVO_CARIOCA_B, HTMLManager.getRankingCarioca(RankingCariocaTabActivity.CARIOCA_GRUPO_B));
				Log.i("inFLU", "O arquivo " + RankingTableActivity.ARQUIVO_CARIOCA_B + " foi salvo.");
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
						RankingBrasileiraoWidgetProvider.updateRankingBrasileiraoWidgetContent(c, AppWidgetManager.getInstance(c));
						pd.dismiss();
					}
				});
			}
		}
	}
}
