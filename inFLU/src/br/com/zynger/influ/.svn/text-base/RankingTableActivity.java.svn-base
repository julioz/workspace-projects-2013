package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.StatClub;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.RankingRow;
import br.com.zynger.influ.web.HTMLManager;

public class RankingTableActivity extends Activity {
	
	//private static final int MENU_ATUALIZAR = 1;
	public final static String ARQUIVO_BRASILEIRAO = "ranking_brasileirao";
	public final static String ARQUIVO_CARIOCA_A = "ranking_carioca_a";
	public final static String ARQUIVO_CARIOCA_B = "ranking_carioca_b";
	public final static String ARQUIVO_LIBERTADORES = "ranking_libertadores";
	private final static int BLUE = 0xFF6495ED;
	private final static int RED = 0xFFFF0000;
	private final static int WHITE = 0xFFFFFFFF;
	
	private Theme theme;
	
	private int championship = 0, libertagroup = RankingLibertadoresTabActivity.LIB_GRP_1;
	
	private TextView tv_points, tv_played, tv_win, tv_draw, tv_lose, tv_goalspro, tv_goalsagainst, tv_balance, /*tv_ycards, tv_rcards,*/ tv_aprov;
	private LinearLayout ll_footer;
	private ArrayList<RankingRow> al;
	private TreeMap<Integer, StatClub> tm;
	private TreeMap<Integer, TreeMap<Integer, StatClub>> treeMapLiberta;
	private TableLayout tl;
	private int clickCounter = 1;
	private TextView clicked, colored;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking_table);
		
		loadViews();
		updateTheme();
		
		sortByClick(tv_points);
		sortByClick(tv_played);
		sortByClick(tv_win);
		sortByClick(tv_draw);
		sortByClick(tv_lose);
		
		championship = getIntent().getIntExtra("championship", 0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		if(championship == RankingCariocaTabActivity.CARIOCA_GRUPO_A){
			this.tm = (TreeMap<Integer, StatClub>) FileHandler.openBackup(this, ARQUIVO_CARIOCA_A);
			ll_footer.setVisibility(View.INVISIBLE);
		}else if(championship == RankingCariocaTabActivity.CARIOCA_GRUPO_B){
			this.tm = (TreeMap<Integer, StatClub>) FileHandler.openBackup(this, ARQUIVO_CARIOCA_B);
			ll_footer.setVisibility(View.INVISIBLE);
		}else if(championship == RankingLibertadoresTabActivity.LIBERTADORES){
			this.treeMapLiberta = (TreeMap<Integer, TreeMap<Integer, StatClub>>) FileHandler.openBackup(this, ARQUIVO_LIBERTADORES);
			libertagroup = getIntent().getIntExtra("libertagroup", RankingLibertadoresTabActivity.LIB_GRP_1);
			if(this.treeMapLiberta != null) this.tm = this.treeMapLiberta.get(libertagroup);
			ll_footer.setVisibility(View.INVISIBLE);
		}else{
			this.tm = (TreeMap<Integer, StatClub>) FileHandler.openBackup(this, ARQUIVO_BRASILEIRAO);
			ll_footer.setVisibility(View.VISIBLE);
		}
		
		colored = null;
		
		try{
			sortByClick(tv_goalspro);
			sortByClick(tv_goalsagainst);
			sortByClick(tv_balance);
			/*sortByClick(tv_ycards);
			sortByClick(tv_rcards);*/
			sortByClick(tv_aprov);
		}catch(NullPointerException npe) {} //se estiver na view portrait
		
		if(this.tm != null && championship != RankingLibertadoresTabActivity.LIBERTADORES) setValues(tm);
		else if(championship == RankingLibertadoresTabActivity.LIBERTADORES && this.treeMapLiberta != null){
			this.tm = this.treeMapLiberta.get(libertagroup);
			setValues(tm);
		}
		else new DownloadFilesTask(this).execute();
		super.onResume();
	}	
	
	private void sortByClick(TextView tv) {
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(clicked != null && clicked.equals(arg0)) clickCounter = -1*clickCounter;
				else{
					clicked = (TextView) arg0;
					clickCounter = 1;
				}
				if(al != null){
					colorHeader((TextView) arg0);
					sortArrayList();
					populateTableLayout();
				}				
			}
		});
	}
	
	private void sortArrayList(){
		if(clicked.getText().toString().toLowerCase().equals("p")){
			Collections.sort(al, new Comparator<RankingRow>(){
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getPoints() < r2.getClub().getPoints()) return clickCounter*1;
					else if(r1.getClub().getPoints() > r2.getClub().getPoints()) return clickCounter*-1;
					else if(r1.getClub().getPoints() == r2.getClub().getPoints()){
						if(r1.getClub().getWin() < r2.getClub().getWin()) return clickCounter*1;
						else if(r1.getClub().getWin() > r2.getClub().getWin()) return clickCounter*-1;
						else return 0;
					}
					return 1;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("j")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getPlayed() < r2.getClub().getPlayed()) return clickCounter*1;
					else if(r1.getClub().getPlayed() > r2.getClub().getPlayed()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("v")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getWin() < r2.getClub().getWin()) return clickCounter*1;
					else if(r1.getClub().getWin() > r2.getClub().getWin()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("e")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getDraw() < r2.getClub().getDraw()) return clickCounter*1;
					else if(r1.getClub().getDraw() > r2.getClub().getDraw()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("d")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getLose() < r2.getClub().getLose()) return clickCounter*1;
					else if(r1.getClub().getLose() > r2.getClub().getLose()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("gp")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getGoalsPro() < r2.getClub().getGoalsPro()) return clickCounter*1;
					else if(r1.getClub().getGoalsPro() > r2.getClub().getGoalsPro()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("gc")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getGoalsAgainst() < r2.getClub().getGoalsAgainst()) return clickCounter*1;
					else if(r1.getClub().getGoalsAgainst() > r2.getClub().getGoalsAgainst()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("s")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getBalance() < r2.getClub().getBalance()) return clickCounter*1;
					else if(r1.getClub().getBalance() > r2.getClub().getBalance()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("ca")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getyCards() < r2.getClub().getyCards()) return clickCounter*1;
					else if(r1.getClub().getyCards() > r2.getClub().getyCards()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("cv")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getrCards() < r2.getClub().getrCards()) return clickCounter*1;
					else if(r1.getClub().getrCards() > r2.getClub().getrCards()) return clickCounter*-1;
					else return 0;
				}
			});
		}else if(clicked.getText().toString().toLowerCase().equals("ap")){
			Collections.sort(al, new Comparator<RankingRow>() {
				@Override
				public int compare(RankingRow r1, RankingRow r2) {
					if(r1.getClub().getAproveit() < r2.getClub().getAproveit()) return clickCounter*1;
					else if(r1.getClub().getAproveit() > r2.getClub().getAproveit()) return clickCounter*-1;
					else if(r1.getClub().getAproveit() == r2.getClub().getAproveit()){
						if(r1.getClub().getPoints() < r2.getClub().getPoints()) return clickCounter*1;
						else if(r1.getClub().getPoints() > r2.getClub().getPoints()) return clickCounter*-1;
						else if(r1.getClub().getPoints() == r2.getClub().getPoints()){
							if(r1.getClub().getWin() < r2.getClub().getWin()) return clickCounter*1;
							else if(r1.getClub().getWin() > r2.getClub().getWin()) return clickCounter*-1;
							else return 0;
						}
					}
					return 1;
				}
			});
		}
	}
	
	private void setValues(TreeMap<Integer, StatClub> tm) {
		al = new ArrayList<RankingRow>();
		
		if(tm != null){			
			boolean quintoNaLiberta = false;
			for (StatClub sc : tm.values()) {
				RankingRow r = new RankingRow(this, sc, championship);
				
				if(championship != RankingCariocaTabActivity.CARIOCA_GRUPO_A && championship != RankingCariocaTabActivity.CARIOCA_GRUPO_B && championship != RankingLibertadoresTabActivity.LIBERTADORES){					
					if(r.isChampion() && r.getClub().getPosition()<5) quintoNaLiberta = true;
					if(r.getClub().getPosition() == 5 && quintoNaLiberta){
						r.getPosition().setTextColor(BLUE);
						quintoNaLiberta = false;
					}
				}				
				al.add(r);
			}
			
			if(championship == RankingCariocaTabActivity.CARIOCA_GRUPO_A || championship == RankingCariocaTabActivity.CARIOCA_GRUPO_B || championship == RankingLibertadoresTabActivity.LIBERTADORES){				
				al.get(0).getPosition().setTextColor(BLUE);
				al.get(1).getPosition().setTextColor(BLUE);
			}
			
			populateTableLayout();
		}
		
	}
	
	private void populateTableLayout(){
		tl.removeAllViews();
		
		int bg = 0;
		for (RankingRow rankingRow : al) {
			TableRow tr = rankingRow.getTablerow();
			
			if(bg%2==0) tr.setBackgroundColor(theme.getAct_background());
			else tr.setBackgroundColor(theme.getContent_background());
			
			if(rankingRow.getName().getText().toString().equals("Fluminense")){
				rankingRow.getName().setTextColor(0xffB3002A);
				rankingRow.getName().setTypeface(null, Typeface.BOLD);
			}
			bg++;
			tl.addView(tr);
		}
		tl.invalidate();
	}

	private void colorHeader(TextView tv){
		if(colored != null) colored.setTextColor(WHITE);
		if(clickCounter == 1) tv.setTextColor(BLUE);
		else tv.setTextColor(RED);
		colored = tv;
	}

	private void loadViews() {
		tl = (TableLayout) findViewById(R.ranking.tablelayout);
		
		tv_points = (TextView) findViewById(R.ranking.tv_points);
		tv_played = (TextView) findViewById(R.ranking.tv_played);
		tv_win = (TextView) findViewById(R.ranking.tv_win);
		tv_draw = (TextView) findViewById(R.ranking.tv_draw);
		tv_lose = (TextView) findViewById(R.ranking.tv_lose);
		tv_goalspro = (TextView) findViewById(R.ranking.tv_goalspro);
		tv_goalsagainst = (TextView) findViewById(R.ranking.tv_goalsagainst);
		tv_balance = (TextView) findViewById(R.ranking.tv_balance);
		/*tv_ycards = (TextView) findViewById(R.ranking.tv_ycards);
		tv_rcards = (TextView) findViewById(R.ranking.tv_rcards);*/
		tv_aprov = (TextView) findViewById(R.ranking.tv_aprov);
		
		ll_footer = (LinearLayout) findViewById(R.ranking.ll_footer);
	}
	
	private void updateTheme() {
		theme = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.ranking.ll_actback).setBackgroundColor(theme.getAct_background());
		int colors[] = { theme.getAbgradstart() , theme.getAbgradend() };
		findViewById(R.ranking.ll_headers).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.ranking.sv_table).setBackgroundColor(theme.getAct_background());
		findViewById(R.ranking.ll_footer).setBackgroundColor(theme.getAct_background());
		findViewById(R.ranking.ll_actback).invalidate();
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
				Toast.makeText(c, "Dados atualizados", Toast.LENGTH_SHORT).show();
				RankingBrasileiraoWidgetProvider.updateRankingBrasileiraoWidgetContent(c, AppWidgetManager.getInstance(c));
				pd.dismiss();
			}
		}
	}
}
