package br.com.zynger.libertadores.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.model.Video;
import br.com.zynger.libertadores.util.JsonUtil;

public class VideoCentralParser {
	private final static String TERMS_TO_SEARCH_DEFAULT = "libertadores+2013";
	private final static String NUMBER_VIDEOS = "40";
	private final static String VIDEOS_URL = "https://gdata.youtube.com/feeds/api/videos?q=%22" + TERMS_TO_SEARCH_DEFAULT + "%22";
	private final static String VIDEOS_URL_SUFFIX = "&v=2&alt=jsonc&orderby=published&max-results=" + NUMBER_VIDEOS;
	
	private Context context;
	
	public VideoCentralParser(Context context) {
		this.context = context;
	}
	
	public ArrayList<Video> getVideos(String searchKey){
		try{
			String urlSearchKey = (searchKey == null ? "" : "+" + searchKey.replaceAll(" ", "+"));
			Log.e(HomeActivity.TAG, VIDEOS_URL + urlSearchKey + VIDEOS_URL_SUFFIX);
			JSONObject json = new JSONObject(JsonUtil.readJsonFeed(VIDEOS_URL + urlSearchKey + VIDEOS_URL_SUFFIX));
			JSONArray items = json.getJSONObject("data").getJSONArray("items");
			ArrayList<Video> videos = new ArrayList<Video>();
			for (int i = 0; i < items.length(); i++) {
				JSONObject obj = items.getJSONObject(i);
				Video video = new Video();
				video.setId(obj.getString("id"));
				video.setTitle(obj.getString("title"));
				//2013-02-13T19:31:11.000Z
				try {
					GregorianCalendar cal = new GregorianCalendar();
					String uploaded = obj.getString("uploaded");
					uploaded = uploaded.substring(0, 10) + " " + uploaded.substring(11, 16);
					Date sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(uploaded);
					cal.setGregorianChange(sdf);
				} catch (ParseException e) {
					e.printStackTrace();
					video.setUploaded(null);
				}
				video.setDescription(obj.getString("description"));
				video.setThumbnailUrl(obj.getJSONObject("thumbnail").getString("sqDefault"));
				video.setUrl(obj.getJSONObject("player").getString("default"));
				
				videos.add(video);
			}
			return videos;
		}catch(JSONException je){
			Log.e(HomeActivity.TAG, je.toString());
			je.printStackTrace();
			return null;
		}
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

}
