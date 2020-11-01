package br.com.zynger.vamosmarcar.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import br.com.zynger.vamosmarcar.model.MapLocation;

import com.google.android.gms.maps.model.LatLng;

public class GetLocationFromAddressTask extends
		AsyncTask<String, Void, ArrayList<MapLocation>> {

	private Geocoddable geocoddable;

	public interface Geocoddable {
		void onPreExecution();

		void onGeocodingComplete(ArrayList<MapLocation> locations);

		void onGeocodingError();
	}

	public GetLocationFromAddressTask(Geocoddable geocoddable) {
		this.geocoddable = geocoddable;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		geocoddable.onPreExecution();
	}

	@Override
	protected ArrayList<MapLocation> doInBackground(String... params) {
		String address = params[0].replaceAll(" ", "%20");

		String url = "http://maps.google.com/maps/api/geocode/json?address="
				+ address + "&sensor=false";

		try {
			InputStream is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject jsonObject = new JSONObject(jsonText);
			is.close();

			JSONArray results = (JSONArray) jsonObject.get("results");
			ArrayList<MapLocation> locations = new ArrayList<MapLocation>();

			for (int i = 0; i < results.length(); i++) {
				JSONObject item = results.getJSONObject(i);
				String name = item.getString("formatted_address");

				JSONObject location = item.getJSONObject("geometry")
						.getJSONObject("location");

				double lat = location.getDouble("lat");
				double lng = location.getDouble("lng");

				locations.add(new MapLocation(name, new LatLng(lat, lng)));
			}

			return locations;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<MapLocation> locations) {
		if (locations == null) {
			geocoddable.onGeocodingError();
		} else {
			geocoddable.onGeocodingComplete(locations);
		}
		super.onPostExecute(locations);
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
