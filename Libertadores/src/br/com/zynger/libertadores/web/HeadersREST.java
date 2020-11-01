package br.com.zynger.libertadores.web;

import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.util.JsonUtil;

public class HeadersREST {

	private final static String REST_URL = "http://sleepy-cove-7525.herokuapp.com/libertadores/headers";
	
	public static TreeMap<String, String> getHeaders(){
		String url = REST_URL + "/getHeaders";
		try{
			JSONObject json = new JSONObject(JsonUtil.readJsonFeed(url));
			
			TreeMap<String, String> tm = new TreeMap<String, String>();
			Iterator<?> keys = json.keys();

			while(keys.hasNext()){
				String key = (String) keys.next();
				String headerUrl = json.getString(key);
				
				headerUrl = headerUrl.replaceAll("_ponto_", ".");
				headerUrl = headerUrl.replaceAll("_barra_", "/");
				headerUrl = headerUrl.replaceAll("_doispontos_", ":");
				
				tm.put(key, "http://" + headerUrl);
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
	
}
