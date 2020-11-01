package br.com.zynger.vamosmarcar.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.zynger.vamosmarcar.R;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Event")
public class Event extends ParseObject {

	public enum EventType {
		MEETING(R.string.eventtype_meeting, R.drawable.img_meeting),
		PARTY(R.string.eventtype_party, R.drawable.img_party),
		SPORTS(R.string.eventtype_sports, R.drawable.img_sports),
		OTHER(R.string.eventtype_other, R.drawable.img_bokeh);
		
		private int nameRes;
		private int imageRes;
		
		private EventType(int nameRes, int imageRes) {
			this.nameRes = nameRes;
			this.imageRes = imageRes;
		}
		
		public int getNameRes() {
			return nameRes;
		}
		
		public int getImageRes() {
			return imageRes;
		}
	}
	
	public enum EventAudience {
		PUBLIC(R.string.eventaudience_public, R.drawable.ic_globe),
		PRIVATE(R.string.eventaudience_private, R.drawable.ic_padlock);
		
		private int nameRes;
		private int imageRes;
		
		private EventAudience(int nameRes, int imageRes) {
			this.nameRes = nameRes;
			this.imageRes = imageRes;
		}
		
		public int getNameRes() {
			return nameRes;
		}

		public int getImageRes() {
			return imageRes;
		}
	}

	public static final String KEY = "Event";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_HOST = "host";
	public static final String KEY_AUDIENCE = "audience";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_TYPE = "type";
	public static final String KEY_PARTICIPANTS = "participants";

	public Event() {
		// A default constructor is required.
	}

	public String getTitle() {
		return getString(KEY_TITLE);
	}

	public void setTitle(String title) {
		put(KEY_TITLE, title);
	}
	
	public String getDescription() {
		return getString(KEY_DESCRIPTION);
	}

	public void setDescription(String description) {
		put(KEY_DESCRIPTION, description);
	}

	public ParseUser getHost() {
		return getParseUser(KEY_HOST);
	}

	public void setHost(ParseUser user) {
		put(KEY_HOST, user);
	}
	
	public EventAudience getAudience() {
		return EventAudience.valueOf(getString(KEY_AUDIENCE));
	}
	
	public void setAudience(EventAudience audience) {
		put(KEY_AUDIENCE, audience.toString());
	}

	public void setLatLng(LatLng latLng) {
		setLatitude(latLng.latitude);
		setLongitude(latLng.longitude);
	}
	
	public double getLatitude() {
		return getDouble(KEY_LATITUDE);
	}

	public void setLatitude(double latitude) {
		put(KEY_LATITUDE, latitude);
	}

	public double getLongitude() {
		return getDouble(KEY_LONGITUDE);
	}

	public void setLongitude(double longitude) {
		put(KEY_LONGITUDE, longitude);
	}

	public EventType getType() {
		return EventType.valueOf(getString(KEY_TYPE));
	}

	public void setType(EventType type) {
		put(KEY_TYPE, type.toString());
	}

	public void setParticipants(List<String> participants) {
		JSONArray json = new JSONArray();
		for (String participantId : participants) {
			json.put(participantId);
		}
		put(KEY_PARTICIPANTS, json);
	}
	
	public List<String> getParticipants() throws JSONException {
		JSONArray json = getJSONArray(KEY_PARTICIPANTS);
		List<String> participants = new ArrayList<String>();
		
		for (int i = 0; i < json.length(); i++) {
			String participantId = json.getString(i);
			participants.add(participantId);
		}
		
		return participants;
	}
}
