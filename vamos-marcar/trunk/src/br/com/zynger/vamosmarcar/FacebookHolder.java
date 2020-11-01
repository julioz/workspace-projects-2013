package br.com.zynger.vamosmarcar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import br.com.zynger.vamosmarcar.interfaces.FacebookFriendsTouchable;
import br.com.zynger.vamosmarcar.model.FacebookFriend;
import br.com.zynger.vamosmarcar.parser.FacebookFriendParser;

import com.facebook.Request;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class FacebookHolder {

	private String[] friendRequestFields = { "id", "first_name", "last_name",
			"username", "picture.type(normal)" };
	
	private static FacebookHolder instance;
	
	private String userId;
	private String userName;
	private Map<String, FacebookFriend> friends;
	
	private FacebookHolder() { }
	
	public static FacebookHolder getInstance() {
		if (instance == null) {
			instance = new FacebookHolder();
		}
		return instance;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void getFriends(FacebookFriendsTouchable touchable) {
		touchable.beforeFriendsRequest();
		if (friends == null) {
			Map<String, FacebookFriend> friends = getFriends();
			setFriends(friends);
		}
		
		if (friends == null) {
			touchable.onFriendRequestError();
		} else {
			ArrayList<FacebookFriend> sortedFriends = new ArrayList<FacebookFriend>(friends.values());
			Collections.sort(sortedFriends);
			touchable.afterFriendRequest(sortedFriends);
		}
	}
	
	private Map<String, FacebookFriend> getFriends() {
		Session activeSession = Session.getActiveSession();
		if (activeSession.getState().isOpened()) {
			try {
				return new GetFacebookFriendTask(activeSession).execute().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void setFriends(Map<String, FacebookFriend> friends) {
		this.friends = friends;
	}
	
	public FacebookFriend getFriendById(String id) {
		FacebookFriend friend = null;
		if (friends != null) {
			friend = friends.get(id);
		}
		return friend;
	}

	public void clearInstance() {
		instance = null;
	}
	
	private class GetFacebookFriendTask extends AsyncTask<Void, Void, Map<String, FacebookFriend>> {
		private Session activeSession;

		public GetFacebookFriendTask(Session activeSession) {
			this.activeSession = activeSession;
		}
		
		@Override
		protected Map<String, FacebookFriend> doInBackground(Void... parameters) {
			Request friendRequest = Request.newMyFriendsRequest(activeSession,
					new GraphUserListCallback() {
						@Override
						public void onCompleted(List<GraphUser> users,
								Response response) { } });
			Bundle params = new Bundle();
			params.putString("fields",
					TextUtils.join(", ", friendRequestFields));
			friendRequest.setParameters(params);
			
			Response response = friendRequest.executeAndWait();
			String json = response.toString();
			Map<String, FacebookFriend> friends = FacebookFriendParser
					.parseFriends(json);
			
			return friends;
		}
		
	}
}
