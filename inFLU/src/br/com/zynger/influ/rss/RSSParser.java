package br.com.zynger.influ.rss;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;
import br.com.zynger.influ.NewsTabActivity;

public class RSSParser {
	
	private int domain;
	private String site, edited;
	private int numArt;
	private final static int MAX_ART = 18;
	
	public RSSParser(int domain, String url, String encoding) throws XmlPullParserException, IOException{
		setDomain(domain);
		setSiteString(parseWebsiteIntoString(url, encoding));
		numArt = 0;
	}
	
	private void setSiteString(String s) {
		this.site = s;
	}
	
	private void setDomain(int dom) {
		this.domain = dom;
	}

	private String parseWebsiteIntoString(String website, String encoding) throws IOException{
		URL url = new URL(website);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        //String encoding = con.getContentEncoding();
        if(encoding == null) encoding = "UTF-8";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
		return new String(baos.toByteArray(), encoding);
	}

	public ArrayList<Article> parse(){
		this.edited = this.site;
		ArrayList<Article> al = new ArrayList<Article>();
		try {
			if(this.domain != NewsTabActivity.LANCE){				
				while(true){
					int pos = edited.indexOf("<item>", "<item>".length()+1);
					if(pos != -1 && numArt < MAX_ART){
						try{
							edited = edited.substring(pos);
						}catch(StringIndexOutOfBoundsException e){
							break;
						}
						Article art = parseArticle();
						al.add(art);
						numArt++;
					}else break;
				}
			}else al = parseLance();
			
		} catch (MalformedURLException e) {
			Log.e("inFLU", "RSSParser -> " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("inFLU", "RSSParser -> " + e.toString());
			e.printStackTrace();
		}
		return al;
	}

	private Article parseArticle() throws MalformedURLException{
		switch (this.domain) {
		case NewsTabActivity.GLOBOESPORTE:
			return parseGlobo();
		case NewsTabActivity.NETFLU:
			return parseNetflu();
		case NewsTabActivity.TERRA:
			return parseTerra();
		case NewsTabActivity.UOL:
			return parseUol();
		case NewsTabActivity.GOOGLE:
			return parseGoogle();
		default:
			return null;
		}
	}

	private ArrayList<Article> parseLance() throws IOException {
		ArrayList<Article> al = new ArrayList<Article>();
		
		Document doc = Jsoup.connect("http://www.lancenet.com.br/minutoL.html?sectionId=67").get();
		
		Elements bb_mu_list = doc.getElementsByClass("bb-mu");

		int cont_art = 0;
		
		for (Element el : bb_mu_list) {
			if(cont_art == MAX_ART) break;
			
			Article art = new Article();
			
			Elements els_span = el.getElementsByTag("span");
			if(els_span.size()>0){
				art.setPubDate(els_span.get(0).text().substring(0, 5));
			}
			
			Elements els_a = el.getElementsByTag("a");
			for (Element element : els_a) {
				String url = element.attr("href");
				if(url.substring(0, 1).equals("/")){
					url = "http://www.lancenet.com.br" + url;
					art.setUrl(new URL(url));
					art.setTitle(element.text());
					new parseImgUrlTask(art, this.domain).execute();
					al.add(art);
					cont_art++;
				}
			}
			
		}
		
		return al;
	}

	private Article parseGlobo() throws MalformedURLException {
		Article art = new Article();
		
		this.edited = this.edited.substring(this.edited.indexOf("<title>") + "<title>".length());
		art.setTitle(this.edited.substring(0, this.edited.indexOf("</title>")));
		
		this.edited = this.edited.substring(this.edited.indexOf("<link>") + "<link>".length());
		art.setUrl(new URL(this.edited.substring(0, this.edited.indexOf("</link>"))));
		
		this.edited = this.edited.substring(this.edited.indexOf("src='") + "src='".length());
		String imgurl = this.edited.substring(0, this.edited.indexOf("'"));
		imgurl = imgurl.replaceFirst("[0-9]+x[0-9]+", "original");
		if(imgurl.equals("http://globoesporte.globo.com")) imgurl = null;
		art.setImgUrl(imgurl);
		
		this.edited = this.edited.substring(this.edited.indexOf("<pubDate>") + "<pubDate>".length());
		art.setPubDate(this.edited.substring(0, this.edited.indexOf("</pubDate>")));
		
		return art;
	}

	private Article parseNetflu() throws MalformedURLException {
		Article art = new Article();
		
		this.edited = this.edited.substring(this.edited.indexOf("<title>") + "<title>".length());
		art.setTitle(this.edited.substring(0, this.edited.indexOf("</title>")));
		
		this.edited = this.edited.substring(this.edited.indexOf("<link>") + "<link>".length());
		art.setUrl(new URL(this.edited.substring(0, this.edited.indexOf("</link>"))));	
		
		new parseImgUrlTask(art, this.domain).execute();
		
		this.edited = this.edited.substring(this.edited.indexOf("<pubDate>") + "<pubDate>".length());
		art.setPubDate(this.edited.substring(0, this.edited.indexOf("</pubDate>")));
		
		return art;
	}
	
	private Article parseTerra() throws MalformedURLException {
		Article art = new Article();
		
		this.edited = this.edited.substring(this.edited.indexOf("<title>") + "<title>".length());
		art.setTitle(this.edited.substring(this.edited.indexOf("<![CDATA[")+"<![CDATA[".length(), this.edited.indexOf("]]></title>")));
		
		this.edited = this.edited.substring(this.edited.indexOf("<pubDate>") + "<pubDate>".length());
		art.setPubDate(this.edited.substring(0, this.edited.indexOf("</pubDate>")));

		this.edited = this.edited.substring(this.edited.indexOf("<link>") + "<link>".length());
		art.setUrl(new URL(this.edited.substring(0, this.edited.indexOf("</link>"))));
		
		new parseImgUrlTask(art, this.domain).execute();
		
		return art;
	}

	private Article parseUol() throws MalformedURLException {
		Article art = new Article();
		
		this.edited = this.edited.substring(this.edited.indexOf("<title>") + "<title>".length());
		String tt = this.edited.substring(this.edited.indexOf("<![CDATA[")+"<![CDATA[".length(), this.edited.indexOf(" ("));
		String pd = this.edited.substring(this.edited.indexOf(" (")+" (".length(), this.edited.indexOf(") ]]>"));
		art.setTitle(tt);
		art.setPubDate(pd);
		
		this.edited = this.edited.substring(this.edited.indexOf("<link>") + "<link>".length());
		art.setUrl(new URL(this.edited.substring(this.edited.indexOf("<![CDATA[")+"<![CDATA[".length(), this.edited.indexOf("]]>"))));
		
		new parseImgUrlTask(art, this.domain).execute();
		
		return art;
	}
	
	private class parseImgUrlTask extends AsyncTask<Void, Void, String> {
		private Article art;
		private int domain;
		
		public parseImgUrlTask(Article art, int domain){
			this.art = art;
			this.domain = domain;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			if(this.domain == NewsTabActivity.LANCE){				
				try{
					Document document = Jsoup.connect(art.getUrl().toString()).get();
					String url = "http://www.lancenet.com.br"+ document.getElementById("news_image").getElementsByTag("img").attr("src");
					return url;
				}catch(IOException e){
					return null;
				}catch(NullPointerException e){
					return null;
				}
			}else if(this.domain == NewsTabActivity.NETFLU){
				try{
					Document document = Jsoup.connect(art.getUrl().toString()).get();
					Elements els = new Elements();
					for (Element el : document.getAllElements()) {
						if(el.tagName().equals("img")) els.add(el);
					}
					if(els.size() == 1) return null;
					else{
						return els.get(0).attr("src");
					}
				}catch(IOException e){
					return null;
				}
			}else if(this.domain == NewsTabActivity.TERRA){
				try{
					Document document = Jsoup.connect(art.getUrl().toString()).get();
					
					Element elem = null;
					for (Element el : document.getAllElements()) {
						if(el.tagName().equals("img")){
							if(el.hasAttr("width")){
								try{									
									int width = Integer.parseInt(el.attr("width"));
									if(width > 200){
										elem = el;
									}
								}catch (NumberFormatException e) {
									continue;
								}
								
							}
						}
					}
					
					if(elem != null){
						String url_orig = elem.attr("src");
						String url_mod = url_orig.substring(0, url_orig.indexOf("cf/") + "cf/".length()) + "90/68/" + url_orig.substring(url_orig.indexOf("img.terra"));
						return url_mod;
					}
					else return null;
				}catch(IOException e){
					return null;
				}
			}else if(this.domain == NewsTabActivity.UOL){
				try{
					Document document = Jsoup.connect(art.getUrl().toString()).get();
					Elements els = new Elements();
					for (Element el : document.getAllElements()) {
						if(el.tagName().equals("link")) els.add(el);
					}
					for (Element el : els) {
						if(el.attr("rel", "image_src") != null){
							String url = el.attr("href");
							if(url.substring(url.length()-4, url.length()).equals(".jpg")) return url;
							else return null;
						}
						else return null;
					}
				}catch(IOException e){
					return null;
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			art.setImgUrl(result);
			super.onPostExecute(result);
		}
		
	}
	
	private Article parseGoogle() throws MalformedURLException {
		Article art = new Article();
		
		this.edited = this.edited.substring(this.edited.indexOf("<title>") + "<title>".length());
		art.setTitle(this.edited.substring(0, this.edited.indexOf("</title>")));
		
		this.edited = this.edited.substring(this.edited.indexOf("<link>") + "<link>".length());
		art.setUrl(new URL(this.edited.substring(this.edited.indexOf("url=")+"url=".length(), this.edited.indexOf("</link>"))));
		
		this.edited = this.edited.substring(this.edited.indexOf("<pubDate>") + "<pubDate>".length());
		art.setPubDate(this.edited.substring(0, this.edited.indexOf("</pubDate>")));

		return art;
	}
	

}