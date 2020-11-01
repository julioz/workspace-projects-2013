package br.com.zynger.brasileirao2012.adapters.pager;

import java.util.ArrayList;

import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.fragment.ClubSelectorFragment;
import br.com.zynger.brasileirao2012.model.Club;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ClubSelectorPagerAdapter extends BasePagerAdapter<ClubSelectorFragment> {

	private ArrayList<ArrayList<Club>> divisionsClubs;

	public ClubSelectorPagerAdapter(SherlockFragmentActivity activity) {
		super(activity);
		ClubsDB clubsDB = ClubsDB.getInstance(activity);
		this.divisionsClubs = new ArrayList<ArrayList<Club>>();
		for (Division division : Division.values()) {
			divisionsClubs.add(clubsDB.getClubsList(division));
		}
	}

	@Override
	protected ClubSelectorFragment createFragment(int position) {
		ClubSelectorFragment fragment = new ClubSelectorFragment();
		fragment.setClubs(divisionsClubs.get(position));
		return fragment;
	}

	@Override
	public int getCount() {
		return divisionsClubs.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return getContext().getString(Division.values()[position].getNameRes());
	}
}
