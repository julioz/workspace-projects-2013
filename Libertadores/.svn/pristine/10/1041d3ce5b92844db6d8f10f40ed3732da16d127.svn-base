package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.zynger.libertadores.R;

public class HomeActivityViewFlowAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<View> cache;
	private final int[] ids = { R.layout.homedashboard_layout, R.layout.homedashboard2_layout };

	public HomeActivityViewFlowAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cache = new ArrayList<View>();
	}

	@Override
	public int getCount() {
		return ids.length;
	}

	@Override
	public Object getItem(int position) {
		return ids[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		try{			
			View view = cache.get(position);
			return view;
		}catch(Exception e){
			View view = mInflater.inflate((Integer) getItem(position), null);
			cache.add(position, view);
			return view;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}