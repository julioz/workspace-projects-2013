package br.com.zynger.libertadores.model;

public class Headquarters {

	private String name, city;
	private Long latitude, longitude;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public Long getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}
	
	public Long getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Headquarters [name=" + name + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
	
}
