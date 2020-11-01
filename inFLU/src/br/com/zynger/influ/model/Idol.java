package br.com.zynger.influ.model;

import java.io.Serializable;

public class Idol  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int generation;
	private String url;
	private String text;
	
	public Idol(int ger, String name, String url, String text){
		setGeneration(ger);
		setName(name);
		setUrl(url);
		setText(text);
	}
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	private void setUrl(String url) {
		this.url = url;
	}
	
	public String getText() {
		return text;
	}
	
	private void setText(String text) {
		this.text = text;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	
}
