package br.com.zynger.libertadores.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GooglePlayParser {

	private final static String APP_URL = "https://play.google.com/store/apps/details?id=br.com.zynger.libertadores";
	
	public static String getVersionAtMarket() throws IOException{
		Document document = Jsoup.connect(APP_URL).get();
		
		Elements els = document.getElementsByAttributeValue("itemprop", "softwareVersion");
		
		return els.get(0).text();
	}
	
}
