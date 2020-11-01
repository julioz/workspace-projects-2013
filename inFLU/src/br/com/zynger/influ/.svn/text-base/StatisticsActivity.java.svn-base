package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.StatPlayer;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.StatisticsRow;
import br.com.zynger.influ.web.HTMLManager;

public class StatisticsActivity extends Activity {
	
	private final static int BLUE = 0xFF6495ED;
	private final static int RED = 0xFFFF0000;
	private final static int WHITE = 0xFFFFFFFF;
	
	public final static String ARQUIVO = "statistics";
	
	private ArrayList<StatPlayer> al;
	private TextView name, played, goals, yellow, red;
	private TableLayout tl;
	private int clickCounter = 1;
	private TextView clicked, colored;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);
		
		//TODO colocar essa activity em outro fragment juntamente com a historyactivity
		
		loadViews();
		//updateTheme(); TODO
		
		sortByClick(name);
		sortByClick(played);
		sortByClick(goals);
		sortByClick(yellow);
		sortByClick(red);
		
		al = (ArrayList<StatPlayer>) FileHandler.openBackup(this, ARQUIVO);
	}
	
	@Override
	protected void onResume() {
		colored = null;
		
		if(this.al != null) populateTableLayout();
		else updateArrayList();
		super.onResume();
	}
	
	private void colorHeader(TextView tv){
		if(colored != null) colored.setTextColor(WHITE);
		if(clickCounter == 1) tv.setTextColor(BLUE);
		else tv.setTextColor(RED);
		colored = tv;
	}
	
	@SuppressWarnings("unchecked")
	private void updateArrayList() {
		try {
			al = HTMLManager.getPlayerStats();
			FileHandler.saveBackup(this, ARQUIVO, this.al);
			Log.d("inFLU", "Salvei um backup das estatisticas no arquivo " + ARQUIVO);
			populateTableLayout();
		} catch (IOException e) {
			try{
				al = (ArrayList<StatPlayer>) FileHandler.openBackup(this, ARQUIVO);
				populateTableLayout();
			}catch(Exception ex){ //se nao tiver baixado nenhuma vez o arquivo
				finish();
			}finally{
				Toast.makeText(this, "Conecte-se à internet para atualizar os dados", Toast.LENGTH_SHORT).show();				
			}
			
		}
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ATUALIZAR, 0, "Atualizar").setIcon(R.drawable.ic_atualizar);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			new DownloadFilesTask(this).execute();
			break;
		default:
			break;
		}
		return true;
	}*///TODO quando alterar o layout, inventar uma forma de colocar o botao para atualizar

	private void loadViews(){
		tl = (TableLayout) findViewById(R.statistics.tablelayout);
		name = (TextView) findViewById(R.statistics.name);
		clicked = name;
		played = (TextView) findViewById(R.statistics.played);
		goals = (TextView) findViewById(R.statistics.goals);
		yellow = (TextView) findViewById(R.statistics.yellow);
		red = (TextView) findViewById(R.statistics.red);
	}
	
	private void sortByClick(TextView tv){
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(clicked != null && clicked.equals(arg0)) clickCounter = -1*clickCounter;
				else{
					clicked = (TextView) arg0;
					clickCounter = 1;
				}
				colorHeader((TextView) arg0);
				sortArrayList();
				populateTableLayout();
			}
		});
	}
	
	private void sortArrayList(){
		if(clicked.getText().toString().toLowerCase().equals("nome")){
			Collections.sort(al, new Comparator<StatPlayer>() {
				@Override
				public int compare(StatPlayer s1, StatPlayer s2) {
					return clickCounter*(s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase()));
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("j")){
			Collections.sort(al, new Comparator<StatPlayer>() {
				@Override
				public int compare(StatPlayer s1, StatPlayer s2) {
					if(s1.getPlayed() < s2.getPlayed()) return clickCounter*1;
					else if(s1.getPlayed() > s2.getPlayed()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("g")){
			Collections.sort(al, new Comparator<StatPlayer>() {
				@Override
				public int compare(StatPlayer s1, StatPlayer s2) {
					if(s1.getGoals() < s2.getGoals()) return clickCounter*1;
					else if(s1.getGoals() > s2.getGoals()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("ca")){
			Collections.sort(al, new Comparator<StatPlayer>() {
				@Override
				public int compare(StatPlayer s1, StatPlayer s2) {
					if(s1.getyCards() < s2.getyCards()) return clickCounter*1;
					else if(s1.getyCards() > s2.getyCards()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("cv")){
			Collections.sort(al, new Comparator<StatPlayer>() {
				@Override
				public int compare(StatPlayer s1, StatPlayer s2) {
					if(s1.getrCards() < s2.getrCards()) return clickCounter*1;
					else if(s1.getrCards() > s2.getrCards()) return clickCounter*-1;
					else return 0;
				}
			});
		}
	}
	
	private void populateTableLayout(){
		tl.removeAllViews();
		
		for (StatPlayer player : al) {
			StatisticsRow sr = new StatisticsRow(this, player);
			sr.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			tl.addView(sr.getTablerow());
		}
		
		tl.invalidate();
	}
	
	@SuppressWarnings("unused")
	private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
		Context c;
		ProgressDialog pd;
		
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
			updateArrayList();
			return null;
		}
		
		protected void onPostExecute(Void result) {
			pd.dismiss();
		}
	}
}
