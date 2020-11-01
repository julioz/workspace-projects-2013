package br.com.zynger.vamosmarcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

public class GoogleMapImageView extends ImageView {
	
	private final static String URL = "http://maps.googleapis.com/maps/api/staticmap?zoom=<ZOOM>"
			+ "&size=<WIDTH>x<HEIGHT>&markers=<LATITUDE>,<LONGITUDE>&sensor=false&scale=2";
	
	public GoogleMapImageView(Context context) {
		super(context);
	}
	
	public GoogleMapImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public GoogleMapImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void loadLocation(LatLng location) {
		loadLocation(location, null, null, null);
	}
	
	public void loadLocation(LatLng location, Integer zoom, Integer width, Integer height) {
		String url = getImageUrl(location, zoom, width, height);
		
		Picasso.with(getContext()).load(url).into(this);
	}
	
	private String getImageUrl(LatLng location, Integer zoom, Integer width, Integer height) {
		String url = URL.replace("<LATITUDE>", String.valueOf(location.latitude));
		url = url.replace("<LONGITUDE>", String.valueOf(location.longitude));
		
		if (zoom == null) zoom = 17;
		url = url.replace("<ZOOM>", String.valueOf(zoom));
		
		if (width == null || height == null) {
			width = 400;
			height = 200;
		}
		url = url.replace("<WIDTH>", String.valueOf(width));
		url = url.replace("<HEIGHT>", String.valueOf(height));
		
		return url;
	}
}
