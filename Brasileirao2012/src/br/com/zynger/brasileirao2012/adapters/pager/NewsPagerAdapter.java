package br.com.zynger.brasileirao2012.adapters.pager;

import java.util.ArrayList;

import br.com.zynger.brasileirao2012.fragment.NewsFragment;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.NewsSource;
import br.com.zynger.brasileirao2012.util.WebDatabaseMapper;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class NewsPagerAdapter extends BasePagerAdapter<NewsFragment> {

	private ArrayList<NewsSource> newsSources;
	private final Club club;

	public NewsPagerAdapter(SherlockFragmentActivity activity, Club club) {
		super(activity);
		this.club = club;
		this.newsSources = new WebDatabaseMapper(null, null).getNewsUrls(club);
	}

	@Override
	public int getCount() {
		return newsSources.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return newsSources.get(position).getName();
	}

	@Override
	protected NewsFragment createFragment(int position) {
		return NewsFragment
				.create(newsSources.get(position), club.getAcronym());
	}
}
