package br.com.zynger.vamosmarcar.model;

import com.google.android.gms.maps.model.LatLng;

public class MapLocation {
	
	private String address;
	private LatLng latLng;
	
	public MapLocation(String address, LatLng latLng) {
		this.address = address;
		this.latLng = latLng;
	}
	
	public String getAddress() {
		return address;
	}
	
	public LatLng getLatLng() {
		return latLng;
	}

	public String getName() {
		return address.substring(address.indexOf("-") + 1).trim();
	}
}
