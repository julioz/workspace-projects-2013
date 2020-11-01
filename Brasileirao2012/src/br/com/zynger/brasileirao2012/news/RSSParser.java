package br.com.zynger.brasileirao2012.news;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import br.com.zynger.brasileirao2012.model.Article;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.util.Constants;

public abstract class RSSParser {
	protected SimpleDateFormat simpleDateFormat;
	
	public RSSParser() {
		simpleDateFormat = new SimpleDateFormat(getDateFormat(), Locale.US);
	}
	
	protected String getSite(String url, String encoding) throws IOException{
		return ConnectionHelper.websiteToString(url, encoding);
	}
	
	protected int getMaxArticles() {
		return 18;
	}
	
	public abstract String getDateFormat();
	
	public abstract ArrayList<Article> parse(String url, String encoding) throws IOException;
	
	protected GregorianCalendar getCalendarFromPattern(String pubDate){
		try{
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(simpleDateFormat.parse(pubDate));
			calendar.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
			return calendar;
		}catch(ParseException pe){
			pe.printStackTrace();
			return null;
		}
	}
}