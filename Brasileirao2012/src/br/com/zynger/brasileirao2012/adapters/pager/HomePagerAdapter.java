package br.com.zynger.brasileirao2012.adapters.pager;

import br.com.zynger.brasileirao2012.fragment.HomeFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class HomePagerAdapter extends BasePagerAdapter<HomeFragment> {

    public HomePagerAdapter(SherlockFragmentActivity activity) {
		super(activity);
	}
    
    @Override
	protected HomeFragment createFragment(int position) {
		return HomeFragment.create(position);
	}

    @Override
    public int getCount() {
        return 2;
    }
}
