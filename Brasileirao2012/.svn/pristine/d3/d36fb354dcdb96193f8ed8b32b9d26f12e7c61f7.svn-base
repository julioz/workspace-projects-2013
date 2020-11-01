package br.com.zynger.brasileirao2012.rest;

import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.DeviceUUIDFactory;
import br.com.zynger.brasileirao2012.util.FileHandler;
import br.com.zynger.brasileirao2012.util.JsonUtil;

public class FansREST {

	private final String FANS_REST_GET_URL = "http://electric-stream-5784.herokuapp.com/users/get_statistics";
	
	private final String FANS_REST_PUT_PREFIX = "http://electric-stream-5784.herokuapp.com/users/set_team?user[uid]=";
	private final String FANS_REST_PUT_SUFFIX = "&user[team]=";

	public TreeMap<String, Club> updateFansData(Context context, TreeMap<String, Club> clubs, String myteam){
		try{
			JSONObject json = new JSONObject(JsonUtil.readJsonFeed(FANS_REST_GET_URL));

			for (Club club : clubs.values()) {
				try{
					int fans = json.getInt(club.getAcronym());
					club.setFans(fans);
				}catch(JSONException e){
					club.setFans(0);
				}
			}

			sendMyTeamToServer(context, myteam);			
			return clubs;
		}catch(JSONException jse){
			Log.e(Constants.TAG, jse.toString());
			jse.printStackTrace();
			return null;
		}catch(Exception e){
			return null;
		}
	}

	public JSONObject sendMyTeamToServer(Context context, String myteam){
		String sentClub = (String) FileHandler.openBackup(context, FileHandler.TORCIDOMETER_SENT_CLUB);

		if(sentClub == null || !sentClub.equals(myteam)){
			try{
				String uuid = new DeviceUUIDFactory(context).getDeviceUuid().toString();
				String url = FANS_REST_PUT_PREFIX + uuid + FANS_REST_PUT_SUFFIX + myteam;

				JSONObject json = new JSONObject(JsonUtil.readJsonFeed(url));
				FileHandler.saveBackup(context, FileHandler.TORCIDOMETER_SENT_CLUB, myteam);
				return json;
			}catch(JSONException jse){
				Log.e(Constants.TAG, jse.toString());
				jse.printStackTrace();
				return null;
			}catch(Exception e){
				return null;
			}catch(OutOfMemoryError e){
				e.printStackTrace();
				return null;
			}
		}else{
			return new JSONObject();
		}
	}

}
