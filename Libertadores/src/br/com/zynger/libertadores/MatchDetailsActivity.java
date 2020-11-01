package br.com.zynger.libertadores;

import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

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
import br.com.zynger.libertadores.adapters.MatchDetailsCardsAdapter;
import br.com.zynger.libertadores.adapters.MatchDetailsFormationAdapter;
import br.com.zynger.libertadores.adapters.MatchDetailsGoalsAdapter;
import br.com.zynger.libertadores.adapters.MatchDetailsSubstitutionsAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetMatchDetailsTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Match;
import br.com.zynger.libertadores.model.MatchDetails;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.web.MatchDetailsParser;

public class MatchDetailsActivity extends ListActivity implements AsyncTaskListener {
	
	public final static String INTENT_MATCH = "INTENT_MATCH";
	private final static int TIME_ASYNCTASK_EXECUTION = 90;
	private final static Integer MODE_FORMATION = 0;
	private final static Integer MODE_GOALS = 1;
	private final static Integer MODE_CARDS = 2;
	private final static Integer MODE_SUBSTITUTIONS = 3;
	
	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	private MatchDetailsParser matchDetailsParser;
	private Match match;
	
	private Timer asyncTimer;
	
	private Integer mode;
	
	private HelveticaTextView tv_title;
	private DataUpdateLayout dul_update;
	private TextView tv_source;
	private ImageView iv_clipboard;
	
	private QuickAction quickaction;

	private MatchDetails mDetails;
	
	private TextView tv_stadium, tv_referee, tv_score_home, tv_score_away, tv_coach_home, tv_coach_away;
	private ImageView iv_badge_home, iv_badge_away;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matchdetailsactivity);
		
		clubsDB = ClubsDB.getSingletonObject(this);
		clubs = clubsDB.getClubs();
		
		loadViews();
		setQuickAction();
		
		try{			
			String jsonMatch = getIntent().getStringExtra(INTENT_MATCH);
			match = new Match(new JSONArray(jsonMatch), clubs);

			matchDetailsParser = new MatchDetailsParser(this, match, clubs);

			startAsyncTask();
		}catch(JSONException e){
			dul_update.hide();
		}
	}

	private void loadViews() {
		dul_update = (DataUpdateLayout) findViewById(R.matchdetailsactivity.dul_update);
		dul_update.addViewToToggle(getListView());
		tv_title = (HelveticaTextView) findViewById(R.matchdetailsactivity.titlebar_tvtitle);
		iv_clipboard = (ImageView) findViewById(R.matchdetailsactivity.titlebar_ivclipboard);
		tv_stadium = (TextView) findViewById(R.matchdetailsactivity.tv_stadium);
		tv_referee = (TextView) findViewById(R.matchdetailsactivity.tv_referee);
		iv_badge_home = (ImageView) findViewById(R.matchdetailsactivity.iv_badge_home);
		iv_badge_away = (ImageView) findViewById(R.matchdetailsactivity.iv_badge_away);
		tv_score_home = (TextView) findViewById(R.matchdetailsactivity.tv_score_home);
		tv_score_away = (TextView) findViewById(R.matchdetailsactivity.tv_score_away);
		tv_coach_home = (TextView) findViewById(R.matchdetailsactivity.tv_coach_home);
		tv_coach_away = (TextView) findViewById(R.matchdetailsactivity.tv_coach_away);
		tv_source = (TextView) findViewById(R.matchdetailsactivity.tv_source);
		
		tv_title.setText(getString(R.string.homedashboard_matches).toUpperCase());
		tv_source.setText(getString(R.string.matchdetailsactivity_source) + ": conmebol.com");
	}
	
	private void setQuickAction() {
		quickaction  = new QuickAction(this);
		
		quickaction.addActionItem(new ActionItem(getString(R.string.matchdetailsactivity_quickaction_formation),
				getResources().getDrawable(R.drawable.img_squad_formation)));
		
		quickaction.addActionItem(new ActionItem(getString(R.string.matchdetailsactivity_quickaction_goals),
				getResources().getDrawable(R.drawable.img_squad_goal_pro)));

		quickaction.addActionItem(new ActionItem(getString(R.string.matchdetailsactivity_quickaction_cards),
				getResources().getDrawable(R.drawable.img_squad_cards_yellow)));
		
		quickaction.addActionItem(new ActionItem(getString(R.string.matchdetailsactivity_quickaction_substitutions),
				getResources().getDrawable(R.drawable.img_squad_subst)));
		
		quickaction.addActionItem(new ActionItem(getString(R.string.homedashboard_videocentral),
				getResources().getDrawable(R.drawable.ic_videos)));

		quickaction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				String errorMessage = null;
				
				if (pos == 0) mode = MODE_FORMATION;
				else if (pos == 1){
					int scoreSum = (mDetails.getHomeGoals() != null ? mDetails.getHomeGoals().size() : 0)
							+ (mDetails.getAwayGoals() != null ? mDetails.getAwayGoals().size() : 0);
					if(scoreSum == 0) errorMessage = getString(R.string.matchdetailsactivity_quickaction_toast_nogoals);
					else mode = MODE_GOALS;
				}else if (pos == 2){
					int cardsSum = (mDetails.getYellowCards() != null ? mDetails.getYellowCards().size() : 0)
							+ (mDetails.getRedCards() != null ? mDetails.getRedCards().size() : 0);
					if(cardsSum == 0) errorMessage = getString(R.string.matchdetailsactivity_quickaction_toast_nocards);
					else mode = MODE_CARDS;
				}else if (pos == 3){
					int substNum = (mDetails.getSubstitutions() != null ? mDetails.getSubstitutions().size() : 0);
					if(substNum == 0) errorMessage = getString(R.string.matchdetailsactivity_quickaction_toast_nosubsts);
					else mode = MODE_SUBSTITUTIONS;
				}else if (pos == 4){
					Intent it = new Intent(source.getContext(), VideoCentralActivity.class);
					it.putExtra(VideoCentralActivity.INTENT_SEARCH_KEYWORDS, match.getHome().getName() + " " + match.getAway().getName());
					startActivity(it);
				}

				if(pos != 4){					
					if(errorMessage == null) setData(mDetails, mode);
					else Toast.makeText(source.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		iv_clipboard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quickaction.show(v);
			}
		});
	}
	
	public void startAsyncTask() {
	    final Handler handler = new Handler();
	    asyncTimer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                	new GetMatchDetailsTask(matchDetailsParser, MatchDetailsActivity.this).execute();
	                }
	            });
	        }
	    };
	    asyncTimer.schedule(doAsynchronousTask, 0, TIME_ASYNCTASK_EXECUTION * 1000);
	}
	
	@Override
	protected void onStop() {
		asyncTimer.cancel();
		super.onStop();
	}
	
	private void setData(MatchDetails details, Integer mode) {
		this.mDetails = details;
		
		tv_stadium.setText(details.getStadium());
		tv_referee.setText(details.getReferee());
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
		tv_score_home.setText(String.valueOf(details.getHomeGoals().size()));
		tv_score_away.setText(String.valueOf(details.getAwayGoals().size()));
		tv_coach_home.setText(details.getHomeCoach());
		tv_coach_away.setText(details.getAwayCoach());
		
		if(mode == MODE_GOALS){
			setListAdapter(new MatchDetailsGoalsAdapter(this, mDetails.getHomeGoals(), mDetails.getAwayGoals(),
					match.getHome().getBadgeResource(this), match.getAway().getBadgeResource(this)));
		}else if(mode == MODE_CARDS){
			setListAdapter(new MatchDetailsCardsAdapter(this, mDetails.getYellowCards(), mDetails.getRedCards(),
					match.getHome().getBadgeResource(this), match.getAway().getBadgeResource(this)));
		}else if(mode == MODE_SUBSTITUTIONS){
			setListAdapter(new MatchDetailsSubstitutionsAdapter(this, mDetails.getSubstitutions(),
					match.getHome().getBadgeResource(this), match.getAway().getBadgeResource(this)));
		}else{
			setListAdapter(new MatchDetailsFormationAdapter(this, details.getHomeFormation(), details.getAwayFormation()));
		}
	}

	@Override
	public void preExecution() {
		dul_update.showFullLayout();
	}

	@Override
	public void onComplete(Object result) {
		MatchDetails details = (MatchDetails) result;
		setData(details, mode);
		dul_update.hide();

		iv_clipboard.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFail(String message) {
		dul_update.setOnlyText(message);
		iv_clipboard.setVisibility(View.GONE);
	}
}
