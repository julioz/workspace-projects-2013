package br.com.zynger.vamosmarcar.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.adapter.FeedDrawerAdapter.FeedDrawerItem;

public class FeedDrawerAdapter extends ArrayAdapter<FeedDrawerItem> {

	private LayoutInflater inflater;
	private List<FeedDrawerItem> items;

	public FeedDrawerAdapter(Context context) {
		super(context, 0);
		this.inflater = LayoutInflater.from(context);
		this.items = new ArrayList<FeedDrawerItem>();

		items.add(new FeedDrawerItem(R.string.feed_drawer_newevent, R.drawable.ic_action_newevent));
		items.add(new FeedDrawerItem(R.string.feed_drawer_checkins, R.drawable.ic_action_checkin));
		items.add(new FeedDrawerItem(R.string.feed_drawer_settings, R.drawable.ic_action_settings));
	}

	@Override
	public FeedDrawerItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = (View) inflater.inflate(R.layout.row_feed_drawerlayout, null);
		} else {
			view = convertView;
		}

		FeedDrawerItem item = getItem(position);

		ImageView icon = (ImageView) view
				.findViewById(R.feeddraweradapter.iv_image);
		icon.setImageResource(item.getImageRes());

		TextView title = (TextView) view
				.findViewById(R.feeddraweradapter.tv_title);
		title.setText(getContext().getString(item.getTitleRes()));

		return view;
	}
	
	public class FeedDrawerItem {
		private int titleRes;
		private int imageRes;

		public FeedDrawerItem(int titleRes, int imageRes) {
			this.titleRes = titleRes;
			this.imageRes = imageRes;
		}
		
		public int getTitleRes() {
			return titleRes;
		}
		
		public int getImageRes() {
			return imageRes;
		}
	}
}
