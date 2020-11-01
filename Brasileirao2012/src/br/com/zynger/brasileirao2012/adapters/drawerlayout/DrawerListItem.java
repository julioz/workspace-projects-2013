package br.com.zynger.brasileirao2012.adapters.drawerlayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.adapters.drawerlayout.DrawerLayoutAdapter.DrawerRowType;

public class DrawerListItem extends DrawerItem {
	private Typeface typeface;
	
	private int iconRes;
	private String text;

	private final int textRes;

	public DrawerListItem(Context context, int iconRes, int textRes) {
		this.textRes = textRes;
		this.text = context.getString(textRes);
		this.iconRes = iconRes;
		this.typeface = getTypeface(context);
	}

	@Override
	public int getViewType() {
		return DrawerRowType.LIST_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
		if (convertView == null) {
			view = (View) inflater.inflate(R.layout.drawerlayout_item, null);
		} else {
			view = convertView;
		}

		TextView tvText = (TextView) view.findViewById(R.drawerlayout_item.tv_text);
		ImageView ivIcon = (ImageView) view.findViewById(R.drawerlayout_item.iv_icon);
		
		ivIcon.setImageResource(iconRes);
		tvText.setTypeface(typeface);
		tvText.setText(text);
		
		setBackgroundColor(view);

		return view;
	}
	
	public int getIconRes() {
		return iconRes;
	}

	private void setBackgroundColor(View view) {
		int colorRes = R.color.drawer_item_normal;
		view.setSelected(getSelected());
		if(getSelected()){
			colorRes = R.color.drawer_item_selected;
		}
		view.setBackgroundColor(view.getContext().getResources().getColor(colorRes));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrawerListItem other = (DrawerListItem) obj;
		if (iconRes != other.getIconRes())
			return false;
		if (textRes != other.getTextRes())
			return false;
		return true;
	}

	public int getTextRes() {
		return textRes;
	}

	@Override
	public String toString() {
		return text;
	}

}