package br.com.zynger.brasileirao2012;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.adapters.pager.ClubSelectorPagerAdapter;
import br.com.zynger.brasileirao2012.base.SimpleActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.fragment.ClubSelectorFragment;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;

public class TrophiesSelectorActivity extends SimpleActivity
			implements ClubSelectorFragment.OnClubSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trophiesselectoractivity);

		setPager();
		setMyTeamLayout();
	}

	private void setMyTeamLayout() {
		TextView tvMyTeamTitle = (TextView) findViewById(R.trophiesselectoractivity.tv_myteam);
		ImageButton ibMyTeamBadge = (ImageButton) findViewById(R.trophiesselectoractivity.iv_myteam);

		final Club myClub = ClubsDB.getInstance(this).getMyClub();
		tvMyTeamTitle.setText(myClub.getName());
		ibMyTeamBadge.setImageDrawable(getResources().getDrawable(
				myClub.getBadgeResource(this)));

		ibMyTeamBadge.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent me) {
				if (me.getAction() == MotionEvent.ACTION_DOWN
						|| me.getAction() == MotionEvent.ACTION_MOVE) {
					((ImageButton) v).setColorFilter(
							Color.argb(150, 50, 50, 50),
							PorterDuff.Mode.SRC_ATOP);
				} else {
					((ImageButton) v).setColorFilter(null);
				}
				return false;
			}

		});

		OnClickListener myTeamOCL = new OnClickListener() {
			@Override
			public void onClick(View v) {
				startTrophiesActivity(myClub.getAcronym());
			}
		};
		ibMyTeamBadge.setOnClickListener(myTeamOCL);
		findViewById(R.trophiesselectoractivity.ll_myteam).setOnClickListener(
				myTeamOCL);
	}

	private void setPager() {
		ViewPager mPager = (ViewPager) findViewById(R.trophiesselectoractivity.viewpager);
		ClubSelectorPagerAdapter clubSelectorAdapter = new ClubSelectorPagerAdapter(this);
		mPager.setAdapter(clubSelectorAdapter);
	}

	private void startTrophiesActivity(String clubAcronym) {
		Intent it = new Intent(this, TrophiesActivity.class);
		it.putExtra(Constants.INTENT_CLUBACRONYM, clubAcronym);
		startActivity(it);
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_trophies;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.trophiesselectoractivity_title;
	}

	@Override
	public void onClubSelected(Club club) {
		startTrophiesActivity(club.getAcronym());		
	}
}