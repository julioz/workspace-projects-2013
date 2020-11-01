package br.com.zynger.influ.model;

public class Phrase {
	public static final String[] COLUNAS = new String[] { "id", "phrase", "author" };
	private String phrase;
	private String author;
	private long id;
	
	public Phrase() {}
	
	public Phrase(Long id, String phrase, String author){
		setId(id);
		setPhrase(phrase);
		setAuthor(author);
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		if(author == null) this.author = "Autor desconhecido";
		else this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
