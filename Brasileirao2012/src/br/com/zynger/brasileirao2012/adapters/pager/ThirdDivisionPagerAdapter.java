package br.com.zynger.brasileirao2012.adapters.pager;

import java.util.ArrayList;

import android.util.SparseArray;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.fragment.ThirdDivisionFragment;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ThirdDivisionPagerAdapter extends BasePagerAdapter<ThirdDivisionFragment> {
	private final SparseArray<ArrayList<Club>> sparseArray;

	public ThirdDivisionPagerAdapter(SherlockFragmentActivity activity,
			SparseArray<ArrayList<Club>> sparseArray) {
		super(activity);
		this.sparseArray = sparseArray;
	}

	@Override
	protected ThirdDivisionFragment createFragment(int position) {
		ThirdDivisionFragment fragment = new ThirdDivisionFragment();
		fragment.setClubs(sparseArray
				.get(position == 0 ? Constants.THIRDDIVISION_GROUP_A
						: Constants.THIRDDIVISION_GROUP_B));
		return fragment;
	}
	
	@Override
	public int getCount() {
		return sparseArray.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return getContext().getString(R.string.thirddivisionviewflowadapter_group)
				+ " " + (position == 0 ? "A" : "B");
	}
}