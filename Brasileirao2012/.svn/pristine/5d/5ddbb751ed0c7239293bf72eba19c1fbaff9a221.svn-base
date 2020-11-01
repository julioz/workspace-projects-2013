package br.com.zynger.brasileirao2012.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.model.Article;

public class UOLParser extends RSSParser {

	private final String DATEFORMATPATTERN = "dd/MM/yy-HH:mm";
	
	public UOLParser() {
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
		String title = website.substring(website.indexOf("<![CDATA[")+"<![CDATA[".length(), website.indexOf(" ("));
		String pubDateString = website.substring(website.indexOf(" (")+" (".length(), website.indexOf(") ]]>"));
		GregorianCalendar pubDate = getCalendarFromPattern(pubDateString.replace("h", ":"));
		
		website = website.substring(website.indexOf("<link>") + "<link>".length());
		URL url = new URL(website.substring(website.indexOf("<![CDATA[")+"<![CDATA[".length(), website.indexOf("]]>")));
		
		return new Article(title, NewsDomain.UOL, url, pubDate);
	}

	@Override
	public String getDateFormat() {
		return DATEFORMATPATTERN;
	}
}
