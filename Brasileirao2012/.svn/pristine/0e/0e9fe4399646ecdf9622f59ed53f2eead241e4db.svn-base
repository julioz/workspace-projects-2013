package br.com.zynger.brasileirao2012.model;

import java.io.Serializable;

import br.com.zynger.brasileirao2012.enums.NewsDomain;

public class NewsSource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ENCODING_UTF8 = "utf-8";
	public static final String ENCODING_ISO8859 = "iso-8859-1";
	
	private String name;
	private String url;
	private String encoding;
	private NewsDomain domain;
	
	public NewsSource(String name, String url, String encoding, NewsDomain domain) {
		this.name = name;
		this.url = url;
		this.encoding = encoding;
		this.domain = domain;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public NewsDomain getDomain() {
		return domain;
	}
}
