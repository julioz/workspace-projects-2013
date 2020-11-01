package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.zynger.libertadores.R;

public class StandingsActivityViewFlowAdapter extends BaseAdapter {
	private final int EXPANDABLELISTVIEW_INDEX = 0;
	private final int LISTVIEW_INDEX = 1;
	
	private LayoutInflater mInflater;
	private ArrayList<View> cache;
	private final int[] ids = { R.layout.standingsactivity_layout_expandablelistview,
			R.layout.standingsactivity_layout_listview};

	public StandingsActivityViewFlowAdapter(Context context) {
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
	
	public void setListViewAdapter(ListAdapter adapter) {
		ListView lv = (ListView) getView(LISTVIEW_INDEX, null, null);
		lv.setAdapter(adapter);
	}
	
	public void setExpandableListViewAdapter(ExpandableListAdapter adapter) {
		ExpandableListView elv = (ExpandableListView) getView(EXPANDABLELISTVIEW_INDEX, null, null);
		elv.setAdapter(adapter);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try{			
			View view = cache.get(position);
			return view;
		}catch(Exception e){
			View view = mInflater.inflate((Integer) getItem(position), null);
			
			if(view instanceof ExpandableListView){
				((ExpandableListView) view).setIndicatorBounds(15, 35);
			}
			
			cache.add(position, view);
			return view;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}