package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.libertadores.adapters.MoveToMoveAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetMoveToMoveTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.model.Move;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.web.MoveToMoveParser;

public class MoveToMoveActivity extends ListActivity implements AsyncTaskListener {

	public final static String INTENT_MATCH = "INTENT_MATCH";
	private final static int TIME_ASYNCTASK_EXECUTION = 100;

	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	private MoveToMoveParser moveToMoveParser;
	private Match match;
	
	private Timer asyncTimer;
	
	private HelveticaTextView tv_title;
	private DataUpdateLayout dul_update;
	private TextView tv_source;
	private ImageView iv_clipboard;
	private TextView tv_score_home, tv_score_away;
	private ImageView iv_badge_home, iv_badge_away;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movetomoveactivity);

		clubsDB = ClubsDB.getSingletonObject(this);
		clubs = clubsDB.getClubs();

		loadViews();

		try{			
			String jsonMatch = getIntent().getStringExtra(INTENT_MATCH);
			match = new Match(new JSONArray(jsonMatch), clubs);

			setHeader(match);
			
			moveToMoveParser = new MoveToMoveParser(this, match, clubs);
			
			iv_clipboard.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(v.getContext(), MatchDetailsActivity.class);
					i.putExtra(MatchDetailsActivity.INTENT_MATCH, match.toJson().toString());
					v.getContext().startActivity(i);
				}
			});

			startAsyncTask();
		}catch(JSONException e){
			dul_update.setOnlyText(getString(R.string.error_datadownload));
		}
	}

	private void loadViews() {
		dul_update = (DataUpdateLayout) findViewById(R.movetomoveactivity.dul_update);
		dul_update.addViewToToggle(getListView());
		tv_title = (HelveticaTextView) findViewById(R.movetomoveactivity.titlebar_tvtitle);
		iv_clipboard = (ImageView) findViewById(R.movetomoveactivity.titlebar_ivclipboard);
		iv_badge_home = (ImageView) findViewById(R.movetomoveactivity.iv_badge_home);
		iv_badge_away = (ImageView) findViewById(R.movetomoveactivity.iv_badge_away);
		tv_score_home = (TextView) findViewById(R.movetomoveactivity.tv_score_home);
		tv_score_away = (TextView) findViewById(R.movetomoveactivity.tv_score_away);
		tv_source = (TextView) findViewById(R.movetomoveactivity.tv_source);

		tv_title.setText(getString(R.string.homedashboard_matches).toUpperCase());
		tv_source.setText(getString(R.string.matchdetailsactivity_source) + ": globoesporte.com");
	}
	
	@Override
	protected void onResume() {
		startAsyncTask();
		super.onResume();
	}
	
	public void startAsyncTask() {
	    final Handler handler = new Handler();
	    asyncTimer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                	new GetMoveToMoveTask(moveToMoveParser, MoveToMoveActivity.this).execute();
	                }
	            });
	        }
	    };
	    asyncTimer.schedule(doAsynchronousTask, 0, TIME_ASYNCTASK_EXECUTION * 1000);
	}
	
	@Override
	protected void onPause() {
		asyncTimer.cancel();
		super.onPause();
	}
	
	private void setHeader(final Match match) {
		iv_badge_home.setImageResource(match.getHome().getBadgeResource(this));
		iv_badge_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), match.getHome().getName(), Toast.LENGTH_SHORT).show();
			}
		});
		iv_badge_away.setImageResource(match.getAway().getBadgeResource(this));
		iv_badge_away.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), match.getAway().getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
		//TODO: Atualizar com o placar do jogo rolando
		//TODO: Remover gambiarra
		tv_score_home.setText("");
		tv_score_away.setText("");
		/*try{
			tv_score_home.setText(match.getScoreHome() == Match.SCORE_NULL ? "" : String.valueOf(match.getScoreHome()));
		}catch(Exception e){
			e.printStackTrace();
			tv_score_home.setText("");
		}
		
		try{			
			tv_score_away.setText(match.getScoreAway() == Match.SCORE_NULL ? "" : String.valueOf(match.getScoreAway()));
		}catch(Exception e){
			e.printStackTrace();
			tv_score_away.setText("");
		}*/
	}

	@Override
	public void preExecution() {
		dul_update.showFullLayout();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(Object result) {
		ArrayList<Move> moves = (ArrayList<Move>) result;

		if(moves.size() > 0){
			setListAdapter(new MoveToMoveAdapter(this, moves));
			dul_update.hide();
		}else{
			dul_update.setOnlyText(getString(R.string.error_nodatabase));
		}
	}

	@Override
	public void onFail(String message) {
		dul_update.setOnlyText(message);
	}
}
