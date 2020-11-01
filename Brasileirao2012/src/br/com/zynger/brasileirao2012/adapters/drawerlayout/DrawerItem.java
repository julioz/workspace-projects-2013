package br.com.zynger.brasileirao2012.adapters.drawerlayout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import br.com.zynger.brasileirao2012.util.Constants;

public abstract class DrawerItem {
	private boolean selected = false;

	public abstract int getViewType();

	public abstract View getView(LayoutInflater inflater, View convertView);

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean getSelected() {
		return selected;
	}
	
	public Typeface getTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(), Constants.FONT_HELVETICA);
	}
}