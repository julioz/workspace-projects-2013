package br.com.zynger.brasileirao2012.adapters.pager;

import java.util.TreeMap;

import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.data.MatchesDB;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.fragment.TableFragment;
import br.com.zynger.brasileirao2012.model.Fixture;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TablePagerAdapter extends BasePagerAdapter<TableFragment> {
	private TreeMap<Integer, Fixture> matches;

	public TablePagerAdapter(SherlockFragmentActivity activity,
			Division division) {
		super(activity);
		ClubsDB clubsDB = ClubsDB.getInstance(activity);
		matches = MatchesDB.getInstance(activity, clubsDB.getClubs())
				.getMatches(division);
	}

	@Override
	public int getCount() {
		return matches.size();
	}

	@Override
	protected TableFragment createFragment(int position) {
		TableFragment fragment = TableFragment.create(position);
		fragment.setMatches(matches.get(position + 1).getMatches());
		return fragment;
	}
}
