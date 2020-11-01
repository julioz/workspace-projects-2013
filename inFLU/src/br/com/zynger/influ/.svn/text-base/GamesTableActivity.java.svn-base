package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.FutureGame;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.XMLPullParser;

public class GamesTableActivity extends ListActivity {
	
	public static final String ARQUIVO = "future_games";
	private final static String urlXML = "http://dl.dropbox.com/u/11231744/inFLU/futuregames.xml";
	
	private TreeSet<FutureGame> table;
	private Theme t;
	
	private ImageButton update;
	private LinearLayout ll_ad;
	private RotateAnimation rot;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamestable);
		
		loadViews();
		updateTheme();
		
		table = (TreeSet<FutureGame>) FileHandler.openBackup(this, ARQUIVO);
		
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(rot);
				new DownloadFilesTask(v.getContext()).execute();
			}
		});
	}

	private void loadViews() {
		update = (ImageButton) findViewById(R.gamestable.ib_actionbar_update);
		
		ll_ad = (LinearLayout) findViewById(R.gamestable.ll_ad);
		
		rot = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rot.setInterpolator(new LinearInterpolator());
		rot.setDuration(900L);
		rot.setStartTime(200L);
	}
	
	private void updateTheme() {
		t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.gamestable.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.gamestable.ll_actbg).setBackgroundColor(t.getAct_background());
		getListView().setBackgroundColor(t.getContent_background());
		findViewById(R.gamestable.ll_ad).setBackgroundDrawable(getResources().getDrawable(t.getNewsRowSelector()));
		
		findViewById(R.gamestable.ll_actbg).invalidate();
	}

	@Override
	protected void onResume() {
		if(!SplashScreen.isConnected(this)) ll_ad.setVisibility(View.GONE);
		else ll_ad.setVisibility(View.VISIBLE);
		
		if(this.table != null) setValues(this.table);
		else new DownloadFilesTask(this).execute();;
		super.onResume();
	}
	
	private void setValues(TreeSet<FutureGame> table) {
		if(table != null){
			MyArrayAdapter adapter = new MyArrayAdapter(this, R.layout.gamestable_row, new ArrayList<FutureGame>(table));
			
			setListAdapter(adapter);
		}
	}

	@SuppressWarnings("unchecked")
	private TreeSet<FutureGame> loadContent(){
		table = parseFutureGames();
		if(table == null){ //se nao conseguiu se conectar a internet
			table = (TreeSet<FutureGame>) FileHandler.openBackup(this, ARQUIVO);
			if(table == null){ //erro na hora de abrir os arquivos salvos
				finish();
				runOnUiThread(new Runnable() {
				     public void run() {			
				    	 Toast.makeText(GamesTableActivity.this, "Conecte-se à internet para obter as informações", Toast.LENGTH_SHORT).show();
				    }
				});
			}else{
				runOnUiThread(new Runnable() {
				     public void run() {			
				    	 Toast.makeText(GamesTableActivity.this, "Não foi possível atualizar os dados, tente novamente", Toast.LENGTH_SHORT).show();
				    }
				});
			}
		}else{
			FileHandler.saveBackup(this, ARQUIVO, table);
			Log.d("inFLU", "Salvei um backup do prox. jogo no arquivo " + ARQUIVO);
		}
		return table;
	}

	public static TreeSet<FutureGame> parseFutureGames() {
		XMLPullParser xmlpp;
		try {
			xmlpp = new XMLPullParser(urlXML, XMLPullParser.URL);
			return xmlpp.parseFutureGames();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		}
	}

	private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
		private Context c;
		private TreeSet<FutureGame> ts;
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
			this.ts = loadContent();
			return null;
		}
		
		protected void onPostExecute(Void result) {
			setValues(ts);
			pd.dismiss();
		}

		public Context getContext() {
			return c;
		}

		public void setContext(Context c) {
			this.c = c;
		}
	}

	private class MyArrayAdapter extends ArrayAdapter<FutureGame>{

		@SuppressWarnings("unused")
		private Context mContext;
		private final LayoutInflater mInflater;
		private int layoutResourceId;
		private ArrayList<FutureGame> objects;
		
		public MyArrayAdapter(Context context, int layoutResourceId, ArrayList<FutureGame> objects) {
			super(context, layoutResourceId, objects);
			this.mInflater = LayoutInflater.from(context);
			this.layoutResourceId = layoutResourceId;
			this.objects = objects;
			this.mContext = context;
		}
		
		@Override
		public int getCount() {
			return objects.size();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
	        FutureGameHolder holder = null;
	        
	        if(row == null){
	            row = mInflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new FutureGameHolder();
	            holder.stadium = (TextView) row.findViewById(R.gamestable_row.tv_stadium);
	            holder.teams = (TextView) row.findViewById(R.gamestable_row.tv_teams);
	            holder.date = (TextView) row.findViewById(R.gamestable_row.tv_date);
	            holder.time = (TextView) row.findViewById(R.gamestable_row.tv_time);
	            
	            row.setTag(holder);
	        }else{
	            holder = (FutureGameHolder) row.getTag();
	        }
	        
	        FutureGame f = objects.get(position);
	        
	        if(f.getStadium().equals("") || f.getStadium().equals("-")) holder.stadium.setText("Local indefinido");
	        else holder.stadium.setText(f.getStadium());
	        
	        holder.teams.setText(f.getHome() + " X " + f.getAway());
	        
	        Date date = f.getDate();
	        int day = date.getDate();
	        int month = date.getMonth();
	        int year = date.getYear();
	        if(month < 1){
	        	month = 12;
	        	year--;
	        }
	        int hour = date.getHours();
	        int min = date.getMinutes();
	        
	        String s_day = String.valueOf(day), s_month = String.valueOf(month), s_hour = String.valueOf(hour), s_min = String.valueOf(min);
	        if(day < 10) s_day = "0" + s_day;
	        if(month < 10) s_month = "0" + s_month;
	        if(hour < 10) s_hour = "0" + s_hour;
	        if(min < 10) s_min = "0" + s_min;
	        
	        String s_time = s_hour + ":" + s_min;
	        
	        if(hour == 0) s_time = "Horário indefinido";
	        
	        holder.date.setText(s_day + "/" + s_month + "/" + year);
	        holder.time.setText(s_time);
	        
	        row.setBackgroundDrawable(getResources().getDrawable(t.getNewsRowSelector()));

	        return row;
		
		}
		
		
	}
	
	static class FutureGameHolder
    {
        TextView stadium, teams, date, time;
    }

}
