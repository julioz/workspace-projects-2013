package br.com.zynger.libertadores.web;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.model.Tweet;
import br.com.zynger.libertadores.util.JsonUtil;

public class TwitterCentralParser {
	private final static String TERMS_TO_SEARCH_DEFAULT = "%23libertadores"; // %23 = #
	private final static String NUMBER_TWEETS = "45";
	private final static String TWEETS_URL = "http://search.twitter.com/search.json?q=" + TERMS_TO_SEARCH_DEFAULT;
	private final static String TWEETS_URL_SUFFIX = "&count=1&rpp=" + NUMBER_TWEETS;
	
	private Context context;
	
	public TwitterCentralParser(Context context) {
		this.context = context;
	}
	
	public ArrayList<Tweet> getTweets(){
		try{
			Log.d(HomeActivity.TAG, TWEETS_URL + TWEETS_URL_SUFFIX);
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			JSONObject json = new JSONObject(JsonUtil.readJsonFeed(TWEETS_URL + TWEETS_URL_SUFFIX));
			JSONArray results = json.getJSONArray("results");
			
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonTweet = results.getJSONObject(i);
				Tweet tweet = new Tweet();
				
				String author = jsonTweet.getString("from_user");
				Integer id = jsonTweet.getInt("id");
				String authorImageUrl = jsonTweet.getString("profile_image_url");
				String text = jsonTweet.getString("text");
				
				tweet.setAuthor(author);
				tweet.setId(id);
				tweet.setAuthorImageUrl(authorImageUrl);
				tweet.setText(text);
				
				tweets.add(tweet);
			}

			return tweets;
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
