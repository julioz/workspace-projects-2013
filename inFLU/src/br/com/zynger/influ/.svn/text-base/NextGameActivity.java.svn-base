package br.com.zynger.influ;

import java.io.File;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Club;
import br.com.zynger.influ.model.Game;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.util.ImageManager;
import br.com.zynger.influ.web.XMLPullParser;

public class NextGameActivity extends Activity{
	
	public final static String ARQUIVO = "nextgame";
	private final static String HOMEBADGE_PATH = "/data/data/br.com.zynger.influ/files/home_badge";
	private final static String AWAYBADGE_PATH = "/data/data/br.com.zynger.influ/files/away_badge";
	private final static String HOMEBADGE_URL = "http://dl.dropbox.com/u/11231744/inFLU/nextgame_home.png";
	private final static String AWAYBADGE_URL = "http://dl.dropbox.com/u/11231744/inFLU/nextgame_away.png";
	private final static String urlXML = "http://dl.dropbox.com/u/11231744/inFLU/nextgame.xml";
	//"http://dcc.ufrj.br/~julioz/inFLU/nextgame.xml";
	private Game game;

	private TextView competition, homeName, awayName, stadium, date, hour, referee, aux1, aux2, club1, club2;
	private TableRow badgesRow;
	private ImageButton plus, update;
	private RotateAnimation rot;
	private ImageView homeBadge, awayBadge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nextgame);

		loadViews();
		updateTheme();
		
		plus.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), GamesTableActivity.class);
				startActivity(it);
			}
		});
		
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(rot);
				new DownloadFilesTask(v.getContext()).execute();
			}
		});
		
		this.game = (Game) FileHandler.openBackup(this, ARQUIVO);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.nextgame.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		
		findViewById(R.nextgame.ll_actback).setBackgroundColor(t.getAct_background());
		findViewById(R.nextgame.tr_comp).setBackgroundColor(t.getContent_background());
		findViewById(R.nextgame.tr_teamnames).setBackgroundColor(t.getContent_background());
		findViewById(R.nextgame.tr_stadium).setBackgroundColor(t.getContent_background());
		findViewById(R.nextgame.tr_ref).setBackgroundColor(t.getContent_background());
		findViewById(R.nextgame.tr_aux).setBackgroundColor(t.getContent_background());
		findViewById(R.nextgame.tr_squads).setBackgroundColor(t.getContent_background());
		badgesRow.setBackgroundColor(t.getContent_background());
		findViewById(R.nextgame.ll_actback).invalidate();
	}

	@Override
	protected void onResume() {
		if(this.game != null) setValues(this.game);
		else new DownloadFilesTask(this).execute();
		super.onResume();
	}
	
	private Game loadContent(){
		this.game = parseXml();
		if(this.game == null){ //se nao conseguiu se conectar a internet
			this.game = (Game) FileHandler.openBackup(this, ARQUIVO);
			if(this.game == null){ //erro na hora de abrir os arquivos salvos
				finish();
				runOnUiThread(new Runnable() {
				     public void run() {			
				    	 Toast.makeText(NextGameActivity.this, "Conecte-se à internet para obter as informações", Toast.LENGTH_SHORT).show();
				    }
				});
			}else{
				try{
					homeBadge.setImageDrawable(ImageManager.resize((BitmapDrawable) BitmapDrawable.createFromPath(getFilesDir().getAbsolutePath()+"/home_badge")));
					awayBadge.setImageDrawable(ImageManager.resize((BitmapDrawable) BitmapDrawable.createFromPath(getFilesDir().getAbsolutePath()+"/away_badge")));
				}catch(Exception e){
					e.printStackTrace();
					runOnUiThread(new Runnable() {
					     public void run() {			
					    	 badgesRow.setVisibility(View.GONE);
					    }
					});
				}
				runOnUiThread(new Runnable() {
				     public void run() {			
				    	 Toast.makeText(NextGameActivity.this, "Não foi possível atualizar os dados, tente novamente", Toast.LENGTH_SHORT).show();
				    }
				});
			}
		}else{
			FileHandler.saveBackup(this, ARQUIVO, this.game);
			Log.d("inFLU", "Salvei um backup do prox. jogo no arquivo " + ARQUIVO);
			
			NextGameWidgetProvider.updateWidgetContent(this, AppWidgetManager.getInstance(this));
			
			try {
				final Drawable imgHomeBadge = ImageManager.DownloadFromUrl(HOMEBADGE_URL, "home_badge");
				final Drawable imgAwayBadge = ImageManager.DownloadFromUrl(AWAYBADGE_URL, "away_badge");
				runOnUiThread(new Runnable() {
				     public void run() {
				    	 homeBadge.setImageDrawable(imgHomeBadge);
				    	 awayBadge.setImageDrawable(imgAwayBadge);			
				    }
				});
				
			} catch (IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
				     public void run() {			
				    	 badgesRow.setVisibility(View.GONE);
				    }
				});
			}
		}
		return game;
	}
	
	private void setValues(Game game){
		if(game != null){			
			try {
				homeBadge.setImageDrawable(ImageManager.resize((BitmapDrawable) BitmapDrawable.createFromPath(new File(HOMEBADGE_PATH).getCanonicalPath())));
				awayBadge.setImageDrawable(ImageManager.resize((BitmapDrawable) BitmapDrawable.createFromPath(new File(AWAYBADGE_PATH).getCanonicalPath())));
				badgesRow.setVisibility(View.VISIBLE);
			} catch (Exception e) {
				badgesRow.setVisibility(View.GONE);
			}
			
			setTextViewData(competition, game.getCompetition());
			setTextViewData(homeName, game.getClub1().getName().toUpperCase());
			setTextViewData(awayName, game.getClub2().getName().toUpperCase());
			setTextViewData(stadium, game.getStadium());			
			date.setText(game.getDate().getDate()+"/"+(game.getDate().getMonth())+"/"+game.getDate().getYear());
			int minutes = game.getDate().getMinutes();
			if(minutes == 0) hour.setText(game.getDate().getHours()+":"+minutes+"0");
			else hour.setText(game.getDate().getHours()+":"+minutes);
			setTextViewData(referee, game.getReferee());
			setTextViewData(aux1, game.getAux1());
			setTextViewData(aux2, game.getAux2());
			setTextViewData(club1, getSquad(game.getClub1()));
			setTextViewData(club2, getSquad(game.getClub2()));
			
		}
	}
	
	private void setTextViewData(TextView tv, String value){
		if(value.equals("")) value = "Não disponível";
		tv.setText(value);
	}

	private String getSquad(Club club) {
		String ret = "";
		if(!club.getGoalkeeper().equals("")) ret += club.getGoalkeeper() + "\n\n";
		for (String s : club.getDefenders()) {
			if(s.equals("")) break;
			ret+=s+"\n";
		}
		if(!ret.equals("")) ret+="\n";
		for (String s : club.getMidfielders()) {
			if(s.equals("")) break;
			ret+=s+"\n";
		}
		if(!ret.equals("")) ret+="\n";
		for (String s : club.getForwards()) {
			if(s.equals("")) break;
			ret+=s+"\n";
		}
		if(!ret.equals("")) ret+="\n";
		
		if(!ret.equals("") && !club.getCoach().equals("")) ret += club.getCoach();

		return ret;
	}

	private void loadViews(){
		plus = (ImageButton) findViewById(R.nextgame.ib_actionbar_plus);
		update = (ImageButton) findViewById(R.nextgame.ib_actionbar_update);
		
		competition = (TextView) findViewById(R.nextgame.competition);
		homeName = (TextView) findViewById(R.nextgame.homeName);
		awayName = (TextView) findViewById(R.nextgame.awayName);
		stadium = (TextView) findViewById(R.nextgame.stadium);
		date = (TextView) findViewById(R.nextgame.date);
		hour = (TextView) findViewById(R.nextgame.hour);
		referee = (TextView) findViewById(R.nextgame.referee);
		aux1 = (TextView) findViewById(R.nextgame.aux1);
		aux2 = (TextView) findViewById(R.nextgame.aux2);
		club1 = (TextView) findViewById(R.nextgame.club1);
		club2 = (TextView) findViewById(R.nextgame.club2);

		badgesRow = (TableRow) findViewById(R.nextgame.badgesRow);

		homeBadge = (ImageView) findViewById(R.nextgame.homeBadge);
		awayBadge = (ImageView) findViewById(R.nextgame.awayBadge);
		
		rot = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rot.setInterpolator(new LinearInterpolator());
		rot.setDuration(900L);
		rot.setStartTime(200L);
	}

	public static Game parseXml(){
		XMLPullParser xmlpp;
		try {
			xmlpp = new XMLPullParser(urlXML, XMLPullParser.URL);
			return xmlpp.parseNextGame();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setGame(Game game) {
		this.game = game;
	}

	private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
		private Context c;
		private Game gm;
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
		protected Void doInBackground(Void... arg0) {
			this.gm = loadContent();
			return null;
		}
		
		protected void onPostExecute(Void result) {
			setValues(gm);
			pd.dismiss();
		}

		public Context getContext() {
			return c;
		}

		public void setContext(Context c) {
			this.c = c;
		}
	}
}
