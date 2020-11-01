package br.com.site.view;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MarcadorPonto extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();

	public MarcadorPonto(Drawable defaultMarker, GeoPoint gp) {
		super(boundCenterBottom(defaultMarker));
		populate();
	}
	
	public void addItem(GeoPoint p, String title, String snippet){
		OverlayItem newItem = new OverlayItem(p, title, snippet);
		overlayItemList.add(newItem);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlayItemList.get(i);
	}

	@Override
	public int size() {
		return overlayItemList.size();
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
	}

}
