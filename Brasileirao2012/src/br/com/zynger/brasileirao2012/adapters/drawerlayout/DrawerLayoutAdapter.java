package br.com.zynger.brasileirao2012.adapters.drawerlayout;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DrawerLayoutAdapter extends ArrayAdapter<DrawerItem> {
	public enum DrawerRowType {
		LIST_ITEM, HEADER_ITEM
	}

	private LayoutInflater mInflater;
	private List<DrawerItem> items;

	public DrawerLayoutAdapter(Context context, List<DrawerItem> items) {
		super(context, 0, items);
		this.items = items;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getViewTypeCount() {
		return DrawerRowType.values().length;

	}

	@Override
	public int getItemViewType(int position) {
		return items.get(position).getViewType();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return items.get(position).getView(mInflater, convertView);
	}
}
