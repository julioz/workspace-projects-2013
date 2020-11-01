package br.com.zynger.vamosmarcar.parser;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.model.FacebookFriend;

public class FacebookFriendParser {
	
	public static Map<String, FacebookFriend> parseFriends(String json) {
		try {
			json = json.substring(json.indexOf("state=") + "state=".length());
			JSONArray data = new JSONObject(json).getJSONArray("data");
			Map<String, FacebookFriend> friends = new HashMap<String, FacebookFriend>();
			
			for (int index = 0; index < data.length(); index++) {
				try {
					JSONObject profile = data.getJSONObject(index);
					FacebookFriend parseFriend = parseFriend(profile);
					friends.put(parseFriend.getId(), parseFriend);
				} catch (JSONException je) {
					je.printStackTrace();
					continue;
				}
			}
			
			return friends;
		} catch (JSONException je) {
			je.printStackTrace();
			Log.e(Constants.TAG, je.toString());
		}
		return null;
	}

	private static FacebookFriend parseFriend(JSONObject profile) throws JSONException {
		FacebookFriend friend = new FacebookFriend();
		friend.setId(profile.getString("id"));
		friend.setFirstName(profile.getString("first_name"));
		friend.setLastName(profile.getString("last_name"));
		if (profile.has("username")) {
			friend.setUsername(profile.getString("username"));
		}
		if (profile.has("picture")) {
			JSONObject picture = profile.getJSONObject("picture");
			String pictureUrl = picture.getJSONObject("data").getString("url");
			friend.setPictureUrl(pictureUrl);
		}
		return friend;
	}

}
