package br.com.zynger.brasileirao2012.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import br.com.zynger.brasileirao2012.AboutActivity;
import br.com.zynger.brasileirao2012.AprovActivity;
import br.com.zynger.brasileirao2012.MyTeamActivity;
import br.com.zynger.brasileirao2012.NewsPagerActivity;
import br.com.zynger.brasileirao2012.PartnersActivity;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.RankingActivity;
import br.com.zynger.brasileirao2012.RealTimeMatchesActivity;
import br.com.zynger.brasileirao2012.SettingsActivity;
import br.com.zynger.brasileirao2012.StadiumsMapActivity;
import br.com.zynger.brasileirao2012.StrikersListActivity;
import br.com.zynger.brasileirao2012.TablePagerActivity;
import br.com.zynger.brasileirao2012.ThirdDivisionActivity;
import br.com.zynger.brasileirao2012.TorcidometerActivity;
import br.com.zynger.brasileirao2012.TrophiesSelectorActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.ShareHelper;

import com.actionbarsherlock.app.SherlockFragment;

public class HomeFragment extends SherlockFragment {

	private final int[] layouts = { R.layout.home_dashboard,
			R.layout.home_dashboard2 };

	private ShareHelper shareHelper;
	private int mPageNumber;
	private Division myClubDivision;

	private View layout;

	public static HomeFragment create(int pageNumber) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.PAGER_POSITION, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public HomeFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(Constants.PAGER_POSITION);
		shareHelper = new ShareHelper(getSherlockActivity());
	}

	@Override
	public void onResume() {
		myClubDivision = ClubsDB.getInstance(getSherlockActivity()).getMyClub()
				.getDivision();
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = inflater.inflate(layouts[mPageNumber], null);

		loadViews();

		return layout;
	}

	private void loadViews() {
		if (mPageNumber == 0) {
			loadFirstPageViews();
		} else {
			loadSecondPageViews();
		}
	}

	private void loadFirstPageViews() {
		find(R.home.ibRanking)
				.setOnClickListener(
						getStartActivityListenerPassingMyClubDivision(RankingActivity.class));
		find(R.home.ibTable)
				.setOnClickListener(
						getStartActivityListenerPassingMyClubDivision(TablePagerActivity.class));
		find(R.home.ibStrikers)
				.setOnClickListener(
						getStartActivityListenerPassingMyClubDivision(StrikersListActivity.class));
		find(R.home.ibNews).setOnClickListener(
				getStartActivityListener(NewsPagerActivity.class));
		find(R.home.ibTeamGames).setOnClickListener(
				getStartActivityListener(MyTeamActivity.class));
		find(R.home.ibRealTime)
				.setOnClickListener(
						getStartActivityListenerPassingMyClubDivision(RealTimeMatchesActivity.class));
		find(R.home.ibAprov)
				.setOnClickListener(
						getStartActivityListenerPassingMyClubDivision(AprovActivity.class));
		find(R.home.ibTrophies).setOnClickListener(
				getStartActivityListener(TrophiesSelectorActivity.class));
		find(R.home.ibTorcid).setOnClickListener(
				getStartActivityListener(TorcidometerActivity.class));
	}

	private void loadSecondPageViews() {
		find(R.home.ibStadiums).setOnClickListener(
				getStartActivityListener(StadiumsMapActivity.class));
		find(R.home.ibThirdDivision).setOnClickListener(
				getStartActivityListener(ThirdDivisionActivity.class));
		find(R.home.ibSettings).setOnClickListener(
				getStartActivityListener(SettingsActivity.class));
		find(R.home.ibRate).setOnClickListener(
				getStartActivityToViewWebSite(Constants.APP_WEBSITE));
		find(R.home.ibShare).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shareHelper.share(getString(R.string.share_home));
			}
		});
		find(R.home.ibFanpage).setOnClickListener(
				getStartActivityToViewWebSite(Constants.FACEBOOK_FANPAGE));
		find(R.home.ibPartners).setOnClickListener(
				getStartActivityListener(PartnersActivity.class));
		find(R.home.ibAbout).setOnClickListener(
				getStartActivityListener(AboutActivity.class));
	}

	private View find(int id) {
		return layout.findViewById(id);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getSherlockActivity().overridePendingTransition(
				R.anim.slide_righttoleft_in, R.anim.slide_righttoleft_out);
	}

	private View.OnClickListener getStartActivityListener(final Class<?> clazz) {
		return new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent it = new Intent(view.getContext(), clazz);
				startActivity(it);
			}
		};
	}

	private OnClickListener getStartActivityListenerPassingMyClubDivision(
			final Class<?> clazz) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentForDivision = Constants.getIntentForDivision(
						v.getContext(), clazz, myClubDivision);
				startActivity(intentForDivision);
			}
		};
	}

	private View.OnClickListener getStartActivityToViewWebSite(
			final String website) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(website));
				startActivity(intent);
			}
		};
	}
}
