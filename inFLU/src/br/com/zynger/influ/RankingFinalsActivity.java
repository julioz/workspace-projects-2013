package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.zynger.influ.model.FutureGame;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.FutureGameLinearLayout;
import br.com.zynger.influ.web.HTMLManager;

public class RankingFinalsActivity extends Activity {
	
	private boolean isLiberta;
	
	private ArrayList<ArrayList<FutureGame>> al;
	public final static String ARQUIVO_CARIOCA = "ranking_finals_carioca";
	public final static String ARQUIVO_LIBERTADORES = "ranking_finals_liberta";
	
	private ImageButton update, plus;
	private RotateAnimation rot;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getIntExtra("CHAMPIONSHIP", 1) == 0) isLiberta = true;
		else isLiberta = false;
		
		if(!isLiberta){
			setContentView(R.layout.ranking_finals_carioca);
			this.al = (ArrayList<ArrayList<FutureGame>>) FileHandler.openBackup(this, ARQUIVO_CARIOCA);
			loadViews(R.ranking_finals_carioca.ib_actionbar_update, R.ranking_finals_carioca.ib_actionbar_plus);
			
			plus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					Intent it = new Intent(v.getContext(), RankingCariocaTabActivity.class);
					//it.putExtra("SHOW_ACT", true);
					v.getContext().startActivity(it);
				}
			});
			
			updateTheme(1);
		}else{
			setContentView(R.layout.ranking_finals_libertadores);
			this.al = (ArrayList<ArrayList<FutureGame>>) FileHandler.openBackup(this, ARQUIVO_LIBERTADORES);
			loadViews(R.ranking_finals_libertadores.ib_actionbar_update, R.ranking_finals_libertadores.ib_actionbar_plus);
			
			plus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					Intent it = new Intent(v.getContext(), RankingLibertadoresTabActivity.class);
					//it.putExtra("SHOW_ACT", true); //TODO remover quando for necessario visualizar novamente esta activity
					v.getContext().startActivity(it);
				}
			});
			
			updateTheme(2);
		}

		if(this.al != null) populateLayouts();
		else{
			try {
				this.al = new DownloadFilesTask(this).execute().get();
			} catch (InterruptedException e) {
				this.al = null;
			} catch (ExecutionException e) {
				this.al = null;
			}
			if(this.al != null)	populateLayouts();
			else{
				finish();
				Toast.makeText(this, "Não foi possível atualizar os dados. Tente mais tarde.", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	private void loadViews(int updateId, int plusId) {
		update = (ImageButton) findViewById(updateId);
		plus = (ImageButton) findViewById(plusId);
		
		rot = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rot.setInterpolator(new LinearInterpolator());
		rot.setDuration(900L);
		rot.setStartTime(200L);
		
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(rot);
				if(SplashScreen.isConnected(v.getContext())){
					new DownloadFilesTask(v.getContext()).execute();					
				}else{
					Toast.makeText(v.getContext(), "Conecte-se à internet para atualizar", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void updateTheme(int i) {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		
		if(i == 1){
			findViewById(R.ranking_finals_carioca.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
			findViewById(R.ranking_finals_carioca.ll_actbg).setBackgroundColor(t.getAct_background());
			findViewById(R.ranking_finals_carioca.ll_actbg).invalidate();
		}else{
			findViewById(R.ranking_finals_libertadores.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
			findViewById(R.ranking_finals_libertadores.ll_actbg).setBackgroundColor(t.getAct_background());
			findViewById(R.ranking_finals_libertadores.ll_actbg).invalidate();
		}
	}
	
	private ArrayList<ArrayList<FutureGame>> loadContent(){
		try {
			ArrayList<ArrayList<FutureGame>> array;
			if(!isLiberta){
				array = HTMLManager.getFinals(isLiberta);
				if(array != null) FileHandler.saveBackup(this, ARQUIVO_CARIOCA, array);
			}else{
				array = HTMLManager.getFinals(isLiberta);
				if(array != null) FileHandler.saveBackup(this, ARQUIVO_LIBERTADORES, array);
			}
			return array;
		} catch (IOException e) {
			return null;
		}
	}
	
	private void populateLayouts(){
		if(!isLiberta){			
			LinearLayout ll_semi = (LinearLayout) findViewById(R.ranking_finals_carioca.ll_semi);
			LinearLayout ll_fin = (LinearLayout) findViewById(R.ranking_finals_carioca.ll_fin);
			LinearLayout ll_dec = (LinearLayout) findViewById(R.ranking_finals_carioca.ll_dec);
			
			ll_semi.removeAllViews();
			ll_fin.removeAllViews();
			ll_dec.removeAllViews();
			
			FutureGameLinearLayout ll_semi1 = new FutureGameLinearLayout(this, al.get(0).get(0));
			FutureGameLinearLayout ll_semi2 = new FutureGameLinearLayout(this, al.get(0).get(1));
			FutureGameLinearLayout ll_semi3 = new FutureGameLinearLayout(this, al.get(2).get(0));
			FutureGameLinearLayout ll_semi4 = new FutureGameLinearLayout(this, al.get(2).get(1));
			
			FutureGameLinearLayout ll_fin1 = new FutureGameLinearLayout(this, al.get(1).get(0));
			FutureGameLinearLayout ll_fin2 = new FutureGameLinearLayout(this, al.get(3).get(0));
			
			FutureGameLinearLayout ll_decision = new FutureGameLinearLayout(this, al.get(4).get(0), al.get(4).get(1));
			
			ll_semi.addView(ll_semi1);
			ll_semi.addView(ll_semi2);
			ll_semi.addView(ll_semi3);
			ll_semi.addView(ll_semi4);
			
			ll_fin.addView(ll_fin1);
			ll_fin.addView(ll_fin2);
			
			ll_dec.addView(ll_decision);
		}else{
			LinearLayout ll_eigths = (LinearLayout) findViewById(R.ranking_finals_libertadores.ll_eigths);
			LinearLayout ll_fours = (LinearLayout) findViewById(R.ranking_finals_libertadores.ll_fours);
			LinearLayout ll_semi = (LinearLayout) findViewById(R.ranking_finals_libertadores.ll_semi);
			LinearLayout ll_fin = (LinearLayout) findViewById(R.ranking_finals_libertadores.ll_final);
			
			ll_eigths.removeAllViews();
			ll_fours.removeAllViews();
			ll_semi.removeAllViews();
			ll_fin.removeAllViews();
			
			FutureGameLinearLayout ll_eights1 = new FutureGameLinearLayout(this, al.get(0).get(0), al.get(0).get(1));
			FutureGameLinearLayout ll_eights2 = new FutureGameLinearLayout(this, al.get(0).get(2), al.get(0).get(3));
			FutureGameLinearLayout ll_eights3 = new FutureGameLinearLayout(this, al.get(0).get(4), al.get(0).get(5));
			FutureGameLinearLayout ll_eights4 = new FutureGameLinearLayout(this, al.get(0).get(6), al.get(0).get(7));
			FutureGameLinearLayout ll_eights5 = new FutureGameLinearLayout(this, al.get(0).get(8), al.get(0).get(9));
			FutureGameLinearLayout ll_eights6 = new FutureGameLinearLayout(this, al.get(0).get(10), al.get(0).get(11));
			FutureGameLinearLayout ll_eights7 = new FutureGameLinearLayout(this, al.get(0).get(12), al.get(0).get(13));
			FutureGameLinearLayout ll_eights8 = new FutureGameLinearLayout(this, al.get(0).get(14), al.get(0).get(15));
			
			FutureGameLinearLayout ll_fours1 = new FutureGameLinearLayout(this, al.get(1).get(0), al.get(1).get(1));
			FutureGameLinearLayout ll_fours2 = new FutureGameLinearLayout(this, al.get(1).get(2), al.get(1).get(3));
			FutureGameLinearLayout ll_fours3 = new FutureGameLinearLayout(this, al.get(1).get(4), al.get(1).get(5));
			FutureGameLinearLayout ll_fours4 = new FutureGameLinearLayout(this, al.get(1).get(6), al.get(1).get(7));
			
			FutureGameLinearLayout ll_semi1 = new FutureGameLinearLayout(this, al.get(2).get(0), al.get(2).get(1));
			FutureGameLinearLayout ll_semi2 = new FutureGameLinearLayout(this, al.get(2).get(2), al.get(2).get(3));
			
			FutureGameLinearLayout ll_final = new FutureGameLinearLayout(this, al.get(3).get(0), al.get(3).get(1));
			
			ll_eigths.addView(ll_eights1);
			ll_eigths.addView(ll_eights2);
			ll_eigths.addView(ll_eights3);
			ll_eigths.addView(ll_eights4);
			ll_eigths.addView(ll_eights5);
			ll_eigths.addView(ll_eights6);
			ll_eigths.addView(ll_eights7);
			ll_eigths.addView(ll_eights8);
			
			ll_fours.addView(ll_fours1);
			ll_fours.addView(ll_fours2);
			ll_fours.addView(ll_fours3);
			ll_fours.addView(ll_fours4);
			
			ll_semi.addView(ll_semi1);
			ll_semi.addView(ll_semi2);
			
			ll_fin.addView(ll_final);
		}
	}
	
	private class DownloadFilesTask extends AsyncTask<Void, Void, ArrayList<ArrayList<FutureGame>>> {
		private Context c;
		private ProgressDialog pd;
		
		public DownloadFilesTask(Context c){
			this.setContext(c);
			pd = new ProgressDialog(this.getContext());
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
		protected ArrayList<ArrayList<FutureGame>> doInBackground(Void... arg0) {
			return loadContent();
		}
		
		@Override
		protected void onPostExecute(ArrayList<ArrayList<FutureGame>> result) {
			al = result;
			pd.dismiss();
			if(al != null){
				populateLayouts();
				Toast.makeText(c, "Dados atualizados", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(c, "Não foi possível atualizar os dados. Tente novamente.", Toast.LENGTH_SHORT).show();
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