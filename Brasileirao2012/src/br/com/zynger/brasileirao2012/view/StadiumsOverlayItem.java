package br.com.zynger.brasileirao2012.view;

import br.com.zynger.brasileirao2012.model.Stadium;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class StadiumsOverlayItem extends OverlayItem {

	private Stadium stadium;

	public StadiumsOverlayItem(GeoPoint point, Stadium stadium) {
		super(point, stadium.getNick(), stadium.getCity());
		setStadium(stadium);
	}

	public String getImageURL() {
		return this.stadium.getImgUrl();
	}

	public void setImageURL(String imageURL) {
		this.stadium.setImgUrl(imageURL);
	}
	
	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
	
}
