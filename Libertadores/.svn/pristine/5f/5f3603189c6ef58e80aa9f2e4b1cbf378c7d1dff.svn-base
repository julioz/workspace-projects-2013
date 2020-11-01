package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.com.zynger.libertadores.adapters.ClubSelectorAdapter;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.util.ColorFilterGenerator;
import br.com.zynger.libertadores.view.HelveticaTextView;

public class ClubSelectorActivity extends ListActivity {

	public static final String INTENT_CLUB_RETURNED = "clubreturned";

	public static final String DUMMY_ACRONYM = "DUMMY";
	
	private ClubsDB clubsDB;
	private Club myClub;
	private TreeMap<String, Club> clubs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clubselectoractivity);
		
		clubsDB = ClubsDB.getSingletonObject(this);
		clubs = clubsDB.getClubs();
		myClub = clubsDB.getMyClub();
		
		LinearLayout ll_contentView = (LinearLayout) findViewById(R.clubselectoractivity.ll_contentview);
		HelveticaTextView tv_title = (HelveticaTextView) findViewById(R.clubselectoractivity.titlebar_tvtitle);
		tv_title.setText(getString(R.string.clubselectoractivity_titlebar_title).toUpperCase());
		
		List<Club> listClubs = new ArrayList<Club>();
		for (Club club : clubs.values()) {
			listClubs.add(club);
		}
		Collections.sort(listClubs);
		
		View header = getNoTeamView();
		ll_contentView.addView(header, 1);
		setListAdapter(new ClubSelectorAdapter(this, listClubs, myClub));
	}

	private View getNoTeamView() {
		View v = LayoutInflater.from(this).inflate(R.layout.clubselectorrow, null);
		ImageView iv_badge = (ImageView) v.findViewById(R.clubselectorrow.iv_badge);
		TextView tv_badge = (TextView) v.findViewById(R.clubselectorrow.tv_name);
		
		iv_badge.setImageResource(R.drawable.badge_dummy);
		iv_badge.setColorFilter(ColorFilterGenerator.adjustSaturation(0));
		tv_badge.setText(getString(R.string.clubselectoractivity_noclub).toUpperCase());
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnAcronym(DUMMY_ACRONYM);
			}
		});
		
		return v;
	}

	private void returnAcronym(String acronym){
		Intent resultIntent = new Intent();
    	resultIntent.putExtra(INTENT_CLUB_RETURNED, acronym);
    	setResult(Activity.RESULT_OK, resultIntent);
    	finish();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Club club = (Club) getListAdapter().getItem(position);
		returnAcronym(club.getAcronym());
		super.onListItemClick(l, v, position, id);
	}
}
