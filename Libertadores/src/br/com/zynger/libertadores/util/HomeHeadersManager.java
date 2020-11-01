package br.com.zynger.libertadores.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.web.HeadersREST;

public class HomeHeadersManager {
	
	private final static String HEADERS_MAP_JSON = "libertadores_headersmap";
	private final static String HEADERS_LAST_DATE_UPDATE = "libertadores_headerslastupdatedate";
	
	private static final String SUFFIXFORHEADER = "_headerImg.jpg";
	private static final int DAYSOFMONTH_TO_ADVANCE_IN_COMPARISON = 4;
	
	private InternalStorageHandler internalStorage;
	private TreeMap<String, Club> clubs;
	private Club myClub;
	private Bitmap myClubOldHeader;
	private Context context;
	private ViewGroup headerLayout;
	private ImageView headersImageView;
	
	public HomeHeadersManager(Context context, InternalStorageHandler internalStorage,
			TreeMap<String, Club> clubs, Club myClub) {
		this.internalStorage = internalStorage;
		this.clubs = clubs;
		this.myClub = myClub;
		this.myClubOldHeader = internalStorage.getBitmapFromInternalStorage(
				myClub.getAcronym().toLowerCase(Locale.getDefault()) + SUFFIXFORHEADER);
		this.context = context;
	}
	
	public void setHeaderImage(ViewGroup headerLayout, ImageView headersImageView) {
		this.headerLayout = headerLayout;
		this.headersImageView = headersImageView;
		
		Long millisFromLastUpdate = (Long) internalStorage.openBackup(HEADERS_LAST_DATE_UPDATE);
		Date lastUpdateDate = null;
		if(millisFromLastUpdate != null) lastUpdateDate = new Date(millisFromLastUpdate);
		
		if(!desserializeHeadersMapIntoDB() || compareLastUpdateDate(lastUpdateDate)){
			new GetHeadersTask().execute();
		}else{
			new UpdateHeaderTask(myClub, false).execute();
		}
	}
	
	private boolean compareLastUpdateDate(Date savedDate) {
		if(savedDate == null) return true;
		else{
			Calendar savedCalendar = new GregorianCalendar();
			savedCalendar.setTime(savedDate);
			savedCalendar.add(Calendar.DAY_OF_MONTH, DAYSOFMONTH_TO_ADVANCE_IN_COMPARISON);
			return Calendar.getInstance().after(savedCalendar);
		}
	}
	
	private boolean desserializeHeadersMapIntoDB() {
		try{			
			Object headersMapSerialized = internalStorage.openBackup(HEADERS_MAP_JSON);
			if(headersMapSerialized == null) return false;
			else return setHeadersToDB(headersMapSerialized.toString());
		}catch(JSONException je){
			je.printStackTrace();
			return false;
		}
	}
	
	private boolean setHeadersToDB(String jsonString) throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		
		for (Iterator<?> it = json.keys(); it.hasNext();) {
			String key = (String) it.next();
			String value = json.getString(key);
			Club club = clubs.get(key);
			if(club != null) club.setHeaderUrl(value);
		}
		
		return true;
	}
	
	private void setHeaderBitmap(Bitmap bitmap){
		if(bitmap != null) headersImageView.setImageBitmap(bitmap);
		else headersImageView.setImageResource(R.drawable.img_headerdefault);
		
		headerLayout.setVisibility(View.VISIBLE);
	}
	
	private class GetHeadersTask extends AsyncTask<Void, Void, TreeMap<String, String>> {		
		@Override
		protected void onPreExecute() {
			setHeaderBitmap(myClubOldHeader);
			super.onPreExecute();
		}
		
		@Override
		protected TreeMap<String, String> doInBackground(Void... params) {
			return HeadersREST.getHeaders();
		}

		@Override
		protected void onPostExecute(TreeMap<String, String> tm) {
			try{				
				if(tm != null) {
					JSONObject json = new JSONObject();
					for (Iterator<String> iter = tm.keySet().iterator(); iter.hasNext();) {
						String key = (String) iter.next();
						String value = tm.get(key);
						json.put(key, value);
					}
					
					internalStorage.saveBackup(context, HEADERS_MAP_JSON, json.toString());
					internalStorage.saveBackup(context, HEADERS_LAST_DATE_UPDATE,
							Long.valueOf(Calendar.getInstance().getTimeInMillis()));
					setHeadersToDB(json.toString());
					
					new UpdateHeaderTask(myClub, true).execute();
				}	
			}catch(JSONException je){
				je.printStackTrace();
			}
			super.onPostExecute(tm);
		}
	}
	
	private class UpdateHeaderTask extends AsyncTask<Void, Void, Bitmap> {
		private String imgUrl;
		private String fileName;
		private boolean wasDownloaded;
		private boolean mustDownload;
		
		public UpdateHeaderTask(Club myClub, boolean mustDownload) {
			setHeaderBitmap(myClubOldHeader);
			
			this.wasDownloaded = false;
			this.imgUrl = myClub.getHeaderUrl();
			this.fileName = myClub.getAcronym().toLowerCase(Locale.getDefault()) + SUFFIXFORHEADER;
			this.mustDownload = mustDownload;
		}

		@Override
		protected void onPreExecute() {
			if(mustDownload) headerLayout.setVisibility(View.GONE);
			super.onPreExecute();
		}
		
		@Override
		protected Bitmap doInBackground(Void... params) {
			Bitmap bitmap = null;
			if(!mustDownload){
				try{					
					bitmap = internalStorage.getBitmapFromInternalStorage(fileName);
				}catch(OutOfMemoryError o){
					o.printStackTrace();
					return null;
				}
			}
			if(bitmap == null || mustDownload){
				try {
					bitmap = BitmapFactory.decodeStream((InputStream) new URL(imgUrl).getContent());
					wasDownloaded = true;
					return bitmap;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}else return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap b) {
			myClubOldHeader = b;
			setHeaderBitmap(b);
			if(b != null && wasDownloaded){
				internalStorage.writeBitmapToInternalStorage(b, fileName);
			}
			super.onPostExecute(b);
		}
	}
}
