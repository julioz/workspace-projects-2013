package br.com.zynger.libertadores.model;

import java.util.GregorianCalendar;

public class Video {
	private String id;
	private String title;
	private GregorianCalendar uploaded;
	private String url;
	private String thumbnailUrl;
	private String description;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public GregorianCalendar getUploaded() {
		return uploaded;
	}
	
	public void setUploaded(GregorianCalendar uploaded) {
		this.uploaded = uploaded;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}
