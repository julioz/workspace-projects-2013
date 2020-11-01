package br.com.zynger.brasileirao2012;

import java.util.Locale;

import android.os.Bundle;
import android.widget.ListView;
import br.com.zynger.brasileirao2012.adapters.TrophiesArrayAdapter;
import br.com.zynger.brasileirao2012.base.SimpleActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;

public class TrophiesActivity extends SimpleActivity {
	private Club club;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trophiesactivity);
		setClubFromIntent();
		setActionBarContent(club);
		setListAdapter();
	}

	private void setClubFromIntent() {
		club = ClubsDB.getInstance(this).getClubs().get(getIntent().getStringExtra(
				Constants.INTENT_CLUBACRONYM));
	}

	private void setActionBarContent(Club club) {
		setTitle(club.getName().toUpperCase(Locale.getDefault()));
		setIcon(club.getBadgeResource(this));
	}

	private void setListAdapter() {
		ListView lvContent = (ListView) findViewById(R.trophiesactivity.lv_content);
		TrophiesArrayAdapter adapter = new TrophiesArrayAdapter(this, club);
		lvContent.setAdapter(adapter);
	}
	
	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_trophies;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.trophiesselectoractivity_title;
	}
}
