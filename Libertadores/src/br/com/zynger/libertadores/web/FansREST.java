package br.com.zynger.libertadores.web;

import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.util.DeviceUUIDFactory;
import br.com.zynger.libertadores.util.JsonUtil;

public class FansREST {

	private final static String REST_URL = "http://sleepy-cove-7525.herokuapp.com/libertadores/fans";
	
	public static TreeMap<String, Integer> getFans(){
		String url = REST_URL + "/get_statistics";
		try{
			JSONObject json = new JSONObject(JsonUtil.readJsonFeed(url));
			
			TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
			Iterator<?> keys = json.keys();

			while(keys.hasNext()){
				String key = (String) keys.next();
				tm.put(key, json.getInt(key));
			}
			
			return tm;
		}catch(JSONException jse){
			Log.e(HomeActivity.TAG, jse.toString());
			jse.printStackTrace();
			return null;
		}catch(Exception e){
			Log.e(HomeActivity.TAG, e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public static String becomeFan(Context context, String clubAcronym){
		String uuid = new DeviceUUIDFactory(context).getDeviceUuid().toString();
		
		String url = REST_URL + "/add/id=" + uuid + "&ac=" + clubAcronym;
		String response = JsonUtil.readJsonFeed(url);
		
		if(response.equals("")) return null;
		else return response;
	}
	
}
