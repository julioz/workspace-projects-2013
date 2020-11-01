package br.com.zynger.influ.rss;

import java.net.URL;

public class Article extends Object {
	private long articleId;
	private long feedId;
	private String title;
	private URL url;
	private String pubDate;
	private String imgUrl;
	
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public long getFeedId() {
		return feedId;
	}
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title.contains("&quot;")) title = title.replaceAll("&quot;", "\"");
		if(title.contains("&apos;")) title = title.replaceAll("&apos;", "\'");
		this.title = title;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public String getPubTime() {
		if(pubDate.substring(2, 3).equals("-")) return null;
		return pubDate.substring(17, 22);
	}
	public String getPubCalendar() {
		if(pubDate.substring(2, 3).equals("-"))
			return pubDate.substring(0, 2) + "/" + pubDate.substring(3, 5) + "/" + pubDate.substring(6 , pubDate.length());
		
		else if(pubDate.substring(2, 3).equals("/"))
			return pubDate.replace("-", " ");
		
		else if(pubDate.substring(2, 3).equals(":") && pubDate.length()<6) return pubDate;
		
		String pd = pubDate.substring(0, 16);
		pd = pd.replace("Mon", "Seg");
		pd = pd.replace("Tue", "Ter");
		pd = pd.replace("Wed", "Qua");
		pd = pd.replace("Thu", "Qui");
		pd = pd.replace("Fri", "Sex");
		pd = pd.replace("Sat", "Sab");
		pd = pd.replace("Sun", "Dom");
		
		pd = pd.replace("Feb", "Fev");
		pd = pd.replace("Apr", "Abr");
		pd = pd.replace("May", "Mai");
		pd = pd.replace("Aug", "Ago");
		pd = pd.replace("Sep", "Set");
		pd = pd.replace("Oct", "Out");
		pd = pd.replace("Dec", "Dez");
		
		return pd;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}