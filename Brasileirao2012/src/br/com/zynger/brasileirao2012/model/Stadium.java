package br.com.zynger.brasileirao2012.model;

import java.util.GregorianCalendar;

import com.google.android.gms.maps.model.LatLng;

public class Stadium {
	private double lat, lng;
	private String name, nick, city, imgUrl, description;
	private GregorianCalendar foundation;
	private int capacity, width, height;
	
	public Stadium() { }
	
	public Stadium(double lat, double lng, String name,
			String nick, String city, int capacity,
			GregorianCalendar foundation, int width, int height,
			String imgUrl, String description){
		setLat(lat);
		setLng(lng);
		setName(name);
		setNick(nick);
		setCity(city);
		setCapacity(capacity);
		setFoundation(foundation);
		setWidth(width);
		setHeight(height);
		setImgUrl(imgUrl);
		setDescription(description);
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GregorianCalendar getFoundation() {
		return foundation;
	}

	public void setFoundation(GregorianCalendar foundation) {
		this.foundation = foundation;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public LatLng getLatLng() {
		return new LatLng(getLat(), getLng());
	}
}