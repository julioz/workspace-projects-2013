package edu.mst.kmnrradio.web;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.mst.kmnrradio.model.Post;

public abstract class PostsParser {

	private final static String URL = "http://kmnr897.tumblr.com/rss";

	public static ArrayList<Post> getPosts() {

		String xml = HTTPDownloader.downloadWebpage(URL);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			dbf.setNamespaceAware(false);
			DocumentBuilder builder = dbf.newDocumentBuilder();

			InputSource inStream = new InputSource();
			inStream.setCharacterStream(new StringReader(xml));
			Document dom = builder.parse(inStream);

			//get the root element
			Element channel = dom.getDocumentElement();

			//get a nodelist of elements
			NodeList items = channel.getElementsByTagName("item");
			if(items != null && items.getLength() > 0) {
				ArrayList<Post> posts = new ArrayList<Post>();
				for(int i = 0 ; i < items.getLength();i++) {
					Element item = (Element) items.item(i);
					Post post = parsePost(item);
					posts.add(post);
				}
				
				return posts;
			}
			
			return null;
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	private static Post parsePost(Element item) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

		Post post = new Post();
		
		post.setTitle(getText(item, "title"));
		post.setDescription(getText(item, "description"));
		post.setLink(getText(item, "link"));
	    try {
	        Date pubDate = dateFormat.parse(getText(item, "pubDate"));
	        GregorianCalendar calendar = new GregorianCalendar();
	        calendar.setTime(pubDate);
	        post.setPubdate(calendar);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }

	    return post;
	}
	
	private static String getText(Element ele, String tagName) {
	    String textVal = null;
	    NodeList nl = ele.getElementsByTagName(tagName);
	    if(nl != null && nl.getLength() > 0) {
	        Element el = (Element) nl.item(0);
	        textVal = el.getFirstChild().getNodeValue();
	    }

	    return textVal;
	}

}
