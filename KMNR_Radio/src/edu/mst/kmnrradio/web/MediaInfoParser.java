package edu.mst.kmnrradio.web;

public abstract class MediaInfoParser {
	
	private static final String INFOURL =
			"http://yp.shoutcast.com/Metadata_Info1.php?surl=";
	
	public static String parseInfo(String radioURL){
		String url = INFOURL + radioURL;
		String webpage = HTTPDownloader.downloadWebpage(url);
		
		try{			
			String info = webpage.substring(webpage.indexOf('\'')+1,
					webpage.indexOf('&')-1);
			return info.trim();
		}catch(StringIndexOutOfBoundsException siob){
			return null;
		}
	}

}