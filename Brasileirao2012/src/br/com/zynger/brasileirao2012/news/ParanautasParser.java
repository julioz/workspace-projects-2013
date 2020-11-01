package br.com.zynger.brasileirao2012.news;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.model.Article;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;

public class ParanautasParser extends RSSParser {
	private final String DATEFORMATPATTERN = "dd/MM/yyyy";
	
	public ParanautasParser() {
		super();
	}
	
	@Override
	public ArrayList<Article> parse(String url, String encoding) throws IOException {
		String website = ConnectionHelper.websiteToString(url, encoding);
		
		ArrayList<Article> articlesList = new ArrayList<Article>();
		website = website.substring(website.indexOf("<table width=\"100%\">"));
		
		while(true){
			int indexStart = website.indexOf("<tr valign=\"top\">");
			if(indexStart == -1 || articlesList.size() == getMaxArticles()) break;
			website = website.substring(indexStart);
			website = website.substring(website.indexOf("<a href=\"") + "<a href=\"".length());
			String articleUrl = "http://paranautas.com/" + website.substring(0, website.indexOf("\">"));
			website = website.substring(website.indexOf(">")+1);
			String pubDate = website.substring(0, website.indexOf(" -"));
			website = website.substring(website.indexOf(" - ") + " - ".length());
			String title = website.substring(0, website.indexOf("<br>"));
			
			articlesList.add(new Article(title, NewsDomain.PARANAUTAS,
					new URL(articleUrl), getCalendarFromPattern(pubDate.trim())));
		}
		
		return articlesList;
	}

	@Override
	public String getDateFormat() {
		return DATEFORMATPATTERN;
	}

}
