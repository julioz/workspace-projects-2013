package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.HTMLManager;

public class StatisticsDataActivity extends Activity {
	
	public final static String ARQUIVO = "statistics_data";
	private ArrayList<String> info;
	
	private TextView tv_carioca, tv_libertadores, tv_brasileiro, tv_total, tv_gols, tv_dados, tv_time;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statisticsdata);
		
		this.info = (ArrayList<String>) FileHandler.openBackup(this, ARQUIVO);
		
		loadViews();
		updateTheme();
	}

	private void loadViews() {
		tv_carioca = (TextView) findViewById(R.statisticsdata.tv_carioca);
		tv_libertadores = (TextView) findViewById(R.statisticsdata.tv_libertadores);
		tv_brasileiro = (TextView) findViewById(R.statisticsdata.tv_brasileiro);
		tv_total = (TextView) findViewById(R.statisticsdata.tv_total);
		tv_gols = (TextView) findViewById(R.statisticsdata.tv_gols);
		tv_dados = (TextView) findViewById(R.statisticsdata.tv_dados);
		tv_time = (TextView) findViewById(R.statisticsdata.tv_time);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.statisticsdata.ll_actbg).setBackgroundColor(t.getAct_background());
		findViewById(R.statisticsdata.ll_content1).setBackgroundColor(t.getAct_background());
		findViewById(R.statisticsdata.ll_actbg).invalidate();
	}

	@Override
	protected void onResume() {
		if(this.info != null) setValues();
		else new DownloadFilesTask(this).execute();
		super.onResume();
	}
	
	@SuppressWarnings("unchecked")
	private Boolean loadContent(){		
		try {
			this.info = HTMLManager.getStatisticsDataInfo();
		} catch (IOException e) {
			info = null;
		}
		if(this.info == null){ //se nao conseguiu se conectar a internet
			this.info = (ArrayList<String>) FileHandler.openBackup(this, ARQUIVO);
			if(this.info == null){ //erro na hora de abrir os arquivos salvos
				runOnUiThread(new Runnable() {
				     public void run() {
				    	 Toast.makeText(StatisticsDataActivity.this, "Conecte-se à internet para obter as informações", Toast.LENGTH_SHORT).show();
				    }
				});
				finish();
				return false;
			}else{
				runOnUiThread(new Runnable() {
				     public void run() {
				    	 setValues();
				    	 Toast.makeText(StatisticsDataActivity.this, "Não foi possível atualizar os dados, tente novamente", Toast.LENGTH_SHORT).show();
				    }
				});
				return false;
			}
		}else{
			FileHandler.saveBackup(this, ARQUIVO, this.info);
			Log.d("inFLU", "Salvei um backup do prox. jogo no arquivo " + ARQUIVO);
			
			runOnUiThread(new Runnable() {
			     public void run() {
			    	 setValues();			
			    }
			});
			return true;
		}
	}

	private void setValues() {
		tv_carioca.setText(Html.fromHtml(info.get(1)));
		tv_libertadores.setText(Html.fromHtml(info.get(2)));
		tv_brasileiro.setText(Html.fromHtml(info.get(3)));
		tv_total.setText(Html.fromHtml(info.get(0)));
		tv_gols.setText(Html.fromHtml(info.get(4)));
		tv_dados.setText(Html.fromHtml(info.get(5)));
		tv_time.setText(Html.fromHtml(info.get(6)));
	}
	
	private class DownloadFilesTask extends AsyncTask<Void, Void, Boolean> {
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
		protected Boolean doInBackground(Void... arg0) {
			return loadContent();
		}
		
		protected void onPostExecute(Boolean result) {
			if(result != false) Toast.makeText(this.context, "Dados atualizados", Toast.LENGTH_SHORT).show();
			pd.dismiss();
		}
	}
	
}
