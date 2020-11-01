package br.com.zynger.brasileirao2012.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.model.Article;

public class GoogleParser extends RSSParser {
	private final String DATEFORMATPATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
	
	public GoogleParser() {
		super();
	}

	@Override
	public ArrayList<Article> parse(String url, String encoding) throws IOException {
		String site = getSite(url, encoding);
		
		ArrayList<Article> articlesList = new ArrayList<Article>();
		while(true){
			int pos = site.indexOf("<item>", "<item>".length() + 1);
			if(pos != -1 && articlesList.size() < getMaxArticles()){
				try{
					site = site.substring(pos);
					articlesList.add(parseArticle(site));
				}catch(StringIndexOutOfBoundsException e){
					break;
				}
			}else break;
		}
		return articlesList;
	}

	private Article parseArticle(String website) throws MalformedURLException {
		website = website.substring(website.indexOf("<title>") + "<title>".length());
		String title = website.substring(0, website.indexOf("</title>"));
		
		website = website.substring(website.indexOf("<link>") + "<link>".length());
		URL url = new URL(website.substring(website.indexOf("url=")+"url=".length(), website.indexOf("</link>")));
		
		website = website.substring(website.indexOf("<pubDate>") + "<pubDate>".length());
		GregorianCalendar pubDate = getCalendarFromPattern(website.substring(0, website.indexOf("</pubDate>")));

		return new Article(title, NewsDomain.GOOGLE, url, pubDate);
	}

	@Override
	public String getDateFormat() {
		return DATEFORMATPATTERN;
	}
}
