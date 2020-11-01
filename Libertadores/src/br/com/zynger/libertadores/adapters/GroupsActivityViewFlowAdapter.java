package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.zynger.libertadores.R;

public class GroupsActivityViewFlowAdapter extends BaseAdapter {
	public static final int MATCHESLISTVIEW_INDEX = 0;
	public static final int STANDINGSLISTVIEW_INDEX = 1;
	
	private LayoutInflater mInflater;
	private ArrayList<View> cache;
	private final int[] ids = { R.layout.groupsactivitylistlayout,
			R.layout.groupsactivitylistlayout};

	public GroupsActivityViewFlowAdapter(Context context) {
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
	
	public void setMatchesListViewAdapter(ListAdapter adapter, OnItemClickListener oicl) {
		LinearLayout ll = (LinearLayout) getView(MATCHESLISTVIEW_INDEX, null, null);
		ListView lv = (ListView) ll.findViewById(R.groupsactivitylistlayout.listview);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(oicl);
	}
	
	public void setStandingsListViewAdapter(ListAdapter adapter) {
		LinearLayout ll = (LinearLayout) getView(STANDINGSLISTVIEW_INDEX, null, null);
		ListView lv = (ListView) ll.findViewById(R.groupsactivitylistlayout.listview);
		lv.setAdapter(adapter);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try{			
			View view = cache.get(position);
			return view;
		}catch(Exception e){
			View view = mInflater.inflate((Integer) getItem(position), null);
			
			if(position == STANDINGSLISTVIEW_INDEX){
				view.findViewById(R.groupsactivitylistlayout.tr_toggle_standings).setVisibility(View.VISIBLE);
			}else{
				view.findViewById(R.groupsactivitylistlayout.tr_toggle_standings).setVisibility(View.GONE);
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
