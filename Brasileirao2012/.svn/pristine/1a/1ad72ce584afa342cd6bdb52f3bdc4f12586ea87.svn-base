package br.com.zynger.brasileirao2012;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import br.com.zynger.brasileirao2012.adapters.pager.NewsPagerAdapter;
import br.com.zynger.brasileirao2012.base.SelectiveActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.view.DataUpdateLayout;

import com.viewpagerindicator.TitlePageIndicator;

public class NewsPagerActivity extends SelectiveActivity {

	private static final int MAX_NUMBER_OF_NEWSSOURCES = 5;
	private static final int INTENT_SEE_OTHER_CLUB_NEWS = 42;
	private ClubsDB clubsDB;
	private Club club;
	
	private DataUpdateLayout dulLoading;
	private ViewPager mPager;
	private TitlePageIndicator mPagerIndicator;
	private NewsPagerAdapter newsPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		clubsDB = ClubsDB.getInstance(this);
		setClubFromIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsactivity);
		
		loadViews();
		
		if(club != null){
			setTitle(club.getName().toUpperCase(Locale.getDefault()));
			if(ConnectionHelper.isConnected(this)){
				newsPagerAdapter = new NewsPagerAdapter(this, club);
				mPager.setAdapter(newsPagerAdapter);
				mPagerIndicator.setViewPager(mPager);
				dulLoading.hide();
			}else{
				dulLoading.show();
				dulLoading.setErrorMessage(getString(R.string.newsviewflowadapter_interneterrorreading));
			}
		}else{
			dulLoading.show();
			dulLoading.setErrorMessage(getString(R.string.newsviewflowactivity_errorreading));
		}
	}

	private void setClubFromIntent() {
		String clubAcr = getIntent().getStringExtra(Constants.INTENT_CLUBACRONYM);
		if(clubAcr == null){
			club = clubsDB.getMyClub();
		}else{
			club = clubsDB.getClubs().get(clubAcr);
		}
	}

	private void loadViews() {
		mPager = (ViewPager) findViewById(R.newsactivity.viewpager);
		mPagerIndicator = (TitlePageIndicator) findViewById(R.newsactivity.pagerindicator);
		
		mPager.setOffscreenPageLimit(MAX_NUMBER_OF_NEWSSOURCES);
		dulLoading = (DataUpdateLayout) findViewById(R.newsactivity.dul_loading);
		dulLoading.addViewToToggle(mPager);
		dulLoading.addViewToToggle(mPagerIndicator);
		
		mPagerIndicator.setOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if(newsPagerAdapter != null){
					newsPagerAdapter.dismissActionMode();
				}
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
			case (INTENT_SEE_OTHER_CLUB_NEWS):
				if (resultCode == Activity.RESULT_OK) {
					Intent it = new Intent(this, NewsPagerActivity.class);
					it.putExtra(Constants.INTENT_CLUBACRONYM, data.getStringExtra(ClubSelectorActivity.INTENT_RETURN_ACRONYM));
					startActivity(it);
					finish();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void selectorPressed(View selector) {
		Intent it = new Intent(this, ClubSelectorActivity.class);
		it.putExtra(ClubSelectorActivity.INTENT_TITLE,
				getString(R.string.newsviewflowactivity_title).toUpperCase(Locale.getDefault()));
		it.putExtra(ClubSelectorActivity.INTENT_IMAGELOGO, R.drawable.ic_news);
		startActivityForResult(it, INTENT_SEE_OTHER_CLUB_NEWS);
	}

	@Override
	protected int getSelectorImageResource() {
		return club.getBadgeResource(this);
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_news;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.newsviewflowactivity_title;
	}
}