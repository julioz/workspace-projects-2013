package br.com.zynger.vamosmarcar.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import br.com.zynger.vamosmarcar.R;

import com.viewpagerindicator.TitlePageIndicator;

public class FlowRadioGroup extends LinearLayout {

	private Object[] values;

	private ViewPager viewPager;
	private TitlePageIndicator titleIndicator;

	public FlowRadioGroup(Context context) {
		super(context);
	}

	public FlowRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setValues(Object[] values) {
		this.values = values;
		init();
	}

	private void init() {
		if (!isInEditMode()) {
			View view = LayoutInflater.from(getContext()).inflate(
					R.layout.view_flowradiogroup, this);
			viewPager = (ViewPager) view
					.findViewById(R.flowradiogroup.viewpager);
			titleIndicator = (TitlePageIndicator) view
					.findViewById(R.flowradiogroup.titleindicator);

			FlowRadioGroupPagerAdapter adapter = new FlowRadioGroupPagerAdapter(
					((FragmentActivity) getContext())
							.getSupportFragmentManager(),
					values);
			viewPager.setAdapter(adapter);
			titleIndicator.setViewPager(viewPager);
		}
	}

	public String getSelectedItem() {
		return values[getSelectedItemPosition()].toString();
	}
	
	public int getSelectedItemPosition() {
		return viewPager.getCurrentItem();
	}

	private class FlowRadioGroupPagerAdapter extends FragmentPagerAdapter {
		private Object[] values;
		private SparseArray<TextViewFragment> fragmentsMap;

		public FlowRadioGroupPagerAdapter(FragmentManager fm, Object[] values) {
			super(fm);
			this.values = values;
			this.fragmentsMap = new SparseArray<TextViewFragment>();
		}

		@Override
		public Fragment getItem(int position) {
			TextViewFragment fragment = fragmentsMap.get(position);
			if (fragment == null) {
				fragment = TextViewFragment.create();
				fragmentsMap.put(position, fragment);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return values.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return values[position].toString();
		}
	}

	public static class TextViewFragment extends Fragment {
		public static TextViewFragment create() {
			return new TextViewFragment();
		}

		public TextViewFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return new View(getActivity());
		}
	}

}
