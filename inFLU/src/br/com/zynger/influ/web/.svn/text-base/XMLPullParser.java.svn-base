package br.com.zynger.influ.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import br.com.zynger.influ.model.Club;
import br.com.zynger.influ.model.FutureGame;
import br.com.zynger.influ.model.Game;

public class XMLPullParser{
	
	private String xml;
	private XmlPullParser parser;
	public static final int STRING = 0;
	public static final int URL = 1;
	private boolean modo = false;
	
	private final String TAG = "inFLU";
	
    public XMLPullParser(String xml, int modo) throws IOException, XmlPullParserException{
    	setXmlUrl(xml);
    	
    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
        XmlPullParser parser = factory.newPullParser();
        this.parser = parser;
        
        if(modo == XMLPullParser.STRING) this.modo = false;
        if(modo == XMLPullParser.URL) this.modo = true;
	}
    
    public Game parseNextGame() throws IOException, XmlPullParserException{
    	if(this.modo){
    		URL urlObj = new URL(xml);
    		URLConnection ucon = urlObj.openConnection();
    		InputStream is = ucon.getInputStream();
    		
    		parser.setInput(is, null);
    	}else{
    		parser.setInput(new StringReader(this.xml));
    	}
    	
    	return parseFromUrl();
    }
    
    public TreeSet<FutureGame> parseFutureGames() throws IOException, XmlPullParserException {
    	URL urlObj = new URL(xml);
    	URLConnection ucon = urlObj.openConnection();
    	InputStream is = ucon.getInputStream();
    	parser.setInput(is, null);
    	parser.nextTag();
    	
    	TreeSet<FutureGame> ts = new TreeSet<FutureGame>();
    	
    	while(parser.getEventType() != XmlPullParser.END_DOCUMENT){
    		try{
    			String home = getStringFromTag("home");
    			String away = getStringFromTag("away");
    			String stadium = getStringFromTag("stadium");
    			while(!parser.getName().equals("year")) parser.nextTag();
    			Date date = parseDate();
    			
    			ts.add(new FutureGame(home, away, stadium, date));
    		}catch(XmlPullParserException e){
    			break;
    		}
    	}
    	
    	return ts;
	}
    
    private String getStringFromTag(String s) throws XmlPullParserException, IOException{
    	while(!parser.getName().equals(s)) parser.nextTag();
		return getNextText();
    }
	
	private Game parseFromUrl(){
		Game retorno = null;
		
		try{
			parser.nextTag();
	        if(parser.getName().equals("game")) retorno = parseGame();
		}catch (XmlPullParserException e) {
			Log.d(TAG, "XmlPullParserException");
		}catch (IOException e) {
			Log.d(TAG, "IOException");
		}
		
		return retorno;
	}

	private Game parseGame() throws XmlPullParserException, IOException {
		String competition = parseCompetition();
		
		Club home = parseClub();
		
		Club away = parseClub();
		
		while(!parser.getName().equals("stadium")) parser.nextTag();
		String stadium = getNextText();
		
		while(!parser.getName().equals("year")) parser.nextTag();
		Date date = parseDate();
		parser.nextTag();
		
		String referee = getNextText();
		String aux1 = getNextText();
		String aux2 = getNextText();
		String transmission = getNextText();
		
		return new Game(competition, home, away, stadium, date, referee, aux1, aux2, transmission);
	}
	
	private Date parseDate() throws XmlPullParserException, IOException{
		int year = getNextInt();
		int month = getNextInt();
		int day = getNextInt();
		int hour = getNextInt();
		int minute = getNextInt();
		Date c = new Date(year, month, day, hour, minute);
		return c;
	}

	private Club parseClub() throws XmlPullParserException, IOException{
		parser.nextTag();
		parser.nextTag();
		String name = parser.nextText();
		parser.nextTag();
		String goalkeep = parser.nextText();
		
		ArrayList<String> defenders = new ArrayList<String>();
		ArrayList<String> midfielders = new ArrayList<String>();
		ArrayList<String> forwards = new ArrayList<String>();
		
		parser.nextTag();
		while(!parser.getName().equals("coach")){
			if(parser.getName().equals("defender")) defenders.add(parser.nextText());
			else if(parser.getName().equals("midfielder")) midfielders.add(parser.nextText());
			else if(parser.getName().equals("forward")) forwards.add(parser.nextText());
			parser.nextTag();
		}
		String coach = parser.nextText();
		
		Club c = new Club(name, goalkeep, defenders, midfielders, forwards, coach);
		parser.nextTag();
		return c;
	}
	
	private String parseCompetition() throws XmlPullParserException, IOException{
		parser.nextTag();
		return parser.nextText();
	}
	
	private String getNextText() throws XmlPullParserException, IOException{
		String ret = parser.nextText();
		parser.nextTag();
		return ret;
	}
	
	private int getNextInt() throws XmlPullParserException, IOException{
		int ret = Integer.parseInt(parser.nextText());
		parser.nextTag();
		return ret;
	}

	public void setXmlUrl(String xmlUrl) {
		this.xml = xmlUrl;
	}
}