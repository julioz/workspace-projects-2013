package br.com.zynger.libertadores.model;

public class Tweet {
	
	private String author;
	private Integer id;
	private String authorImageUrl;
	private String text;
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAuthorImageUrl() {
		return authorImageUrl;
	}
	
	public void setAuthorImageUrl(String authorImageUrl) {
		this.authorImageUrl = authorImageUrl;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
