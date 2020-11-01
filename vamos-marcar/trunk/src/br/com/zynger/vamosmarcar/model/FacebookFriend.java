package br.com.zynger.vamosmarcar.model;


public class FacebookFriend implements Comparable<FacebookFriend> {
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String pictureUrl;

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getName() {
		return getFirstName() + " " + getLastName();
	}

	public String getUsername() {
		return username;
	}
	
	public String getPictureUrl() {
		return pictureUrl;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@Override
	public int compareTo(FacebookFriend other) {
		int firstNameComparison = getFirstName().compareTo(other.getFirstName());
		if (firstNameComparison == 0) {
			return getLastName().compareTo(other.getLastName());
		}
		return firstNameComparison;
	}
}
