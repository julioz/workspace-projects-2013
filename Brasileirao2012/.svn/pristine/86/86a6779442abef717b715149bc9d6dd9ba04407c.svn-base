package br.com.zynger.brasileirao2012.adapters.pager;

import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.fragment.AboutFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class AboutPagerAdapter extends BasePagerAdapter<AboutFragment> {
	private int[] titles = { R.string.aboutactivity_viewflow_title_theapp,
					R.string.aboutactivity_viewflow_title_moreinfo,
					R.string.aboutactivity_viewflow_title_thanks };
	
	public AboutPagerAdapter(SherlockFragmentActivity activity) {
		super(activity);
	}

	@Override
	protected AboutFragment createFragment(int position) {
		return AboutFragment.create(position);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return getContext().getString(titles[position % titles.length]);
	}

	@Override
	public int getCount() {
		return 3;
	}
}
