package br.com.zynger.libertadores;

import java.util.Locale;
import java.util.TreeMap;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.adapters.HomeActivityViewFlowAdapter;
import br.com.zynger.libertadores.asynctasks.BecomeFanTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.util.HomeHeadersManager;
import br.com.zynger.libertadores.util.InternalStorageHandler;
import br.com.zynger.libertadores.view.ResizableImageView;

public class HomeActivity extends Activity {

	private static final String RATING_URL = "https://play.google.com/store/apps/details?id=br.com.zynger.libertadores";
	public final static String TAG = "Libertadores";
	public final int INTENT_RETURN_CLUBSELECTOR = 42;
	
	private ViewFlow viewFlow;

	private ResizableImageView iv_header;
	private ImageView iv_centerbar_badge;
	private TextView titlebar_tvtitle, tv_centerbar_clubname;
	private LinearLayout ll_header;

	private Button standings, matches, strikers, news, map, fans, history, about;
	private Button videoCentral, twitterCentral, rosters, rating;
	private ImageView settings;
	
	private InternalStorageHandler internalStorage;
	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	private HomeHeadersManager headersManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeactivity);
		clubsDB = ClubsDB.getSingletonObject(this);
		internalStorage = new InternalStorageHandler(this);
		clubs = clubsDB.getClubs();

		loadViews();
	}
	
	@Override
	protected void onResume() {
		String myClubAcronym = (String) internalStorage.openBackup(InternalStorageHandler.MYCLUB);
		if(myClubAcronym == null) openClubSelectorActivity();
		else{
			if(myClubAcronym.equals(ClubSelectorActivity.DUMMY_ACRONYM)) setNoClubChosen();
			else{
				setMyClub(myClubAcronym);
			
				headersManager = new HomeHeadersManager(this, internalStorage, clubs, clubsDB.getMyClub());
				headersManager.setHeaderImage(ll_header, iv_header);
			}		
		}
		super.onResume();
	}

	private Club setMyClub(String acronym){
		clubsDB.setMyClub(clubs.get(acronym.toUpperCase(Locale.getDefault())));

		Club myClub = clubsDB.getMyClub();
		tv_centerbar_clubname.setText(myClub.getName().toUpperCase(Locale.getDefault()));
		iv_centerbar_badge.setImageResource(myClub.getBadgeResource(this));
		
		return myClub;
	}
	
	private void setNoClubChosen(){
		clubsDB.setMyClub(null);

		tv_centerbar_clubname.setText(getString(R.string.clubselectoractivity_noclub).toUpperCase(Locale.getDefault()));
		iv_centerbar_badge.setImageResource(R.drawable.badge_dummy);
		
		iv_header.setImageResource(R.drawable.img_headerdefault);
		ll_header.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case (INTENT_RETURN_CLUBSELECTOR):
			if (resultCode == Activity.RESULT_OK) {
				String clubAcronym = data.getStringExtra(ClubSelectorActivity.INTENT_CLUB_RETURNED);
				if(clubAcronym.equals(ClubSelectorActivity.DUMMY_ACRONYM)){
					internalStorage.saveBackup(this, InternalStorageHandler.MYCLUB, clubAcronym);
					setNoClubChosen();
				}else if(clubsDB.getMyClub() == null || !clubsDB.getMyClub().getAcronym().equals(clubAcronym)){
					internalStorage.saveBackup(this, InternalStorageHandler.MYCLUB, clubAcronym);
					new BecomeFanTask(this, clubAcronym).execute();
					setMyClub(clubAcronym);
				}
			}
			break;
		default:
			break;
		}
	}

	private void loadViews() {
		viewFlow = (ViewFlow) findViewById(R.home.viewflow);
		viewFlow.setAdapter(new HomeActivityViewFlowAdapter(this));
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.home.viewflowindicator);
		viewFlow.setFlowIndicator(indic);
		
		titlebar_tvtitle = (TextView) findViewById(R.home.titlebar_tvtitle);
		titlebar_tvtitle.setText(titlebar_tvtitle.getText().toString().toUpperCase(Locale.getDefault()));
		iv_header = (ResizableImageView) findViewById(R.home.iv_header);
		iv_centerbar_badge = (ImageView) findViewById(R.home.iv_centerbar_badge);
		tv_centerbar_clubname = (TextView) findViewById(R.home.tv_centerbar_clubname);
		ll_header = (LinearLayout) findViewById(R.home.ll_header);

		standings = (Button) findViewById(R.home.btn_standings);
		matches = (Button) findViewById(R.home.btn_matches);
		strikers = (Button) findViewById(R.home.btn_strikers);
		news = (Button) findViewById(R.home.btn_news);
		map = (Button) findViewById(R.home.btn_map);
		fans = (Button) findViewById(R.home.btn_fans);
		history = (Button) findViewById(R.home.btn_history);
		about = (Button) findViewById(R.home.btn_about);
		videoCentral = (Button) findViewById(R.home.btn_videos);
		twitterCentral = (Button) findViewById(R.home.btn_twitter);
		rosters = (Button) findViewById(R.home.btn_rosters);
		rating = (Button) findViewById(R.home.btn_rating);
		settings = (ImageView) findViewById(R.home.titlebar_ivsettings);

		setActivityLauncher(standings, StandingsActivity.class);
		setActivityLauncher(matches, MatchesActivity.class);
		setActivityLauncher(strikers, StrikersActivity.class);
		setActivityLauncher(news, NewsActivity.class);
		setActivityLauncher(map, HeadquartersActivity.class);
		setActivityLauncher(fans, TorcidometerActivity.class);
		setActivityLauncher(history, HistoryActivity.class);
		setActivityLauncher(about, AboutActivity.class);
		setActivityLauncher(videoCentral, VideoCentralActivity.class);
		setActivityLauncher(twitterCentral, TwitterCentralActivity.class);
		setActivityLauncher(rosters, RostersActivity.class);
		setActivityLauncher(settings, SettingsActivity.class);
		
		rating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(RATING_URL));
				startActivity(intent);
			}
		});

		iv_centerbar_badge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openClubSelectorActivity();
			}
		});
	}

	private void openClubSelectorActivity() {
		Intent it = new Intent(this, ClubSelectorActivity.class);
		startActivityForResult(it, INTENT_RETURN_CLUBSELECTOR);
	}

	private void setActivityLauncher(View v, final Class<?> clazz) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), clazz);
				startActivity(it);
			}
		});
	}
}