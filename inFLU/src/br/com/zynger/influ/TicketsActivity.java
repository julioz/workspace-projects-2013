package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.HTMLManager;

public class TicketsActivity extends Activity {
	
	public final static String ARQUIVO = "tickets";
	private ArrayList<String> info;
	private TextView tv;
	private ImageButton update;
	private RotateAnimation rot;
	private boolean success = false;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tickets);
		
		loadViews();
		updateTheme();
		
		this.info = (ArrayList<String>) FileHandler.openBackup(this, ARQUIVO);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.tickets.ll_actbg).setBackgroundColor(t.getAct_background());
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.tickets.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.tickets.sv_info).setBackgroundColor(t.getAct_background());
		findViewById(R.tickets.ll_info).setBackgroundColor(t.getContent_background());
		findViewById(R.tickets.ll_actbg).invalidate();
	}

	@Override
	protected void onResume() {
		if(this.info != null) setValues();
		else new DownloadFilesTask(this).execute();
		super.onResume();
	}
	
	@SuppressWarnings("unchecked")
	private void loadContent(){
		success = false;
		
		try {
			this.info = HTMLManager.getTicketsInfo();
		} catch (IOException e) {
			info = null;
		}
		if(this.info == null){ //se nao conseguiu se conectar a internet
			this.info = (ArrayList<String>) FileHandler.openBackup(this, ARQUIVO);
			if(this.info == null){ //erro na hora de abrir os arquivos salvos
				runOnUiThread(new Runnable() {
				     public void run() {
				    	 Toast.makeText(TicketsActivity.this, "Conecte-se à internet para obter as informações", Toast.LENGTH_SHORT).show();
				    }
				});
				finish();
			}else{
				runOnUiThread(new Runnable() {
				     public void run() {
				    	 setValues();
				    	 Toast.makeText(TicketsActivity.this, "Não foi possível atualizar os dados, tente novamente", Toast.LENGTH_SHORT).show();
				    }
				});
			}
		}else{
			FileHandler.saveBackup(this, ARQUIVO, this.info);
			Log.d("inFLU", "Salvei um backup do prox. jogo no arquivo " + ARQUIVO);
			success = true;
			
			runOnUiThread(new Runnable() {
			     public void run() {
			    	 setValues();			
			    }
			});
		}
	}

	private void setValues() {
		String s = "";
		for (String string : this.info) {
			s += string+"\n\n";
		}
		s = s.substring(0, s.length()-1);
		tv.setText(Html.fromHtml(s));
	}

	private void loadViews() {
		tv = (TextView) findViewById(R.tickets.textview);
		update = (ImageButton) findViewById(R.tickets.ib_actionbar_update);
		
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
	
	private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
		private Context context;
		private ProgressDialog pd;
		
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
		protected Void doInBackground(Void... arg0) {
			loadContent();
			return null;
		}
		
		protected void onPostExecute(Void result) {
			runOnUiThread(new Runnable() {
				public void run() {			
					if(success) Toast.makeText(context, "Dados atualizados", Toast.LENGTH_SHORT).show();
					pd.dismiss();
				}
			});
		}
	}
}
