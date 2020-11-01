package br.com.zynger.brasileirao2012.adapters.drawerlayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.adapters.drawerlayout.DrawerLayoutAdapter.DrawerRowType;

public class DrawerHeader extends DrawerItem {
	private Typeface typeface;
	
	private final String name;

	public DrawerHeader(Context context, String name) {
		this.name = name;
		this.typeface = getTypeface(context);
	}

	@Override
	public int getViewType() {
		return DrawerRowType.HEADER_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
		if (convertView == null) {
			view = (View) inflater.inflate(R.layout.drawerlayout_header, null);
		} else {
			view = convertView;
		}

		TextView title = (TextView) view.findViewById(R.drawerlayout_header.tv_title);
		title.setTypeface(typeface);
		title.setText(name);
		
		view.setSelected(getSelected());

		return view;
	}
	
}