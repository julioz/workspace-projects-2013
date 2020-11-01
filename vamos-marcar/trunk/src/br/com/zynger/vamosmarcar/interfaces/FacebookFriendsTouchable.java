package br.com.zynger.vamosmarcar.interfaces;

import java.util.List;

import br.com.zynger.vamosmarcar.model.FacebookFriend;

public interface FacebookFriendsTouchable {
	
	void beforeFriendsRequest();
	void afterFriendRequest(List<FacebookFriend> friends);
	void onFriendRequestError();

}
