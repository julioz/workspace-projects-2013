package br.com.zynger.brasileirao2012;

import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import br.com.zynger.brasileirao2012.adapters.pager.AboutPagerAdapter;
import br.com.zynger.brasileirao2012.base.SimpleActivity;

public class AboutActivity extends SimpleActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutactivity);

		ViewPager mPager = (ViewPager) findViewById(R.aboutactivity.viewpager);
		mPager.setOffscreenPageLimit(4);
		mPager.setAdapter(new AboutPagerAdapter(this));
		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.aboutactivity.pagerindicator);
		titleIndicator.setViewPager(mPager);
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_about;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.aboutactivity_title;
	}
}
