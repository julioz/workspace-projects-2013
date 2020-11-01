package br.com.zynger.vamosmarcar.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.R;

public class FeedTitleAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	private ArrayList<String> items;

	public FeedTitleAdapter(Context context) {
		super(context, 0);
		this.mInflater = LayoutInflater.from(context);
		
		this.items = new ArrayList<String>();
		addItem(R.string.feedactivity_sort_newest);
		addItem(R.string.feedactivity_sort_type);
	}
	
	private void addItem(int res) {
		this.items.add(getContext().getString(res));
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public String getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.feedactivity_actionbar_title, null);
		
		TextView tvSubtitle = (TextView) view.findViewById(R.feedactivity_actionbar.action_bar_subtitle);
		tvSubtitle.setText(getItem(position));
		
		return view;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.feedactivity_actionbar_dropdown, null);
		
		TextView tvTitle = (TextView) view.findViewById(R.feedactivity_actionbar.action_bar_dropdown);
		tvTitle.setText(getItem(position));

		return view;
	}
	
}
