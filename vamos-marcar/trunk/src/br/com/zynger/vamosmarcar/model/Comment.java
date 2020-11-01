package br.com.zynger.vamosmarcar.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {
	
	public String getText() {
		return getString("text");
	}

	public void setText(String text) {
		put("text", text);
	}

	public ParseUser getAuthor() {
		return getParseUser("author");
	}

	public void setAuthor(ParseUser user) {
		put("author", user);
	}
	
	public ParseObject getEvent() {
		return getParseObject("event");
	}
	
	public void setEvent(String eventId) {
		put("event", ParseObject.createWithoutData("Event", eventId));
	}
}
