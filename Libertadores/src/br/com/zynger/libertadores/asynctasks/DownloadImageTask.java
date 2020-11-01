package br.com.zynger.libertadores.asynctasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import br.com.zynger.libertadores.HomeActivity;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
	
	private String imgurl;
	private ImageView imageview;
	private HashMap<String, Bitmap> cache;
	private Bitmap cachedBitmap;
	
	public DownloadImageTask(String imgurl, ImageView imageview){
		this.imgurl = imgurl;
		this.imageview = imageview;
		this.cache = null;
		this.cachedBitmap = null;
	}
	
	public DownloadImageTask(String imgurl, ImageView imageview, HashMap<String, Bitmap> cache){
		this.imgurl = imgurl;
		this.imageview = imageview;
		this.cache = cache;
		this.cachedBitmap = cache.get(this.imgurl);
	}

	@Override
	protected Bitmap doInBackground(Void... arg0) {
		if(cachedBitmap != null) return cachedBitmap;
		
		try{			
			return BitmapFactory.decodeStream((InputStream) new URL(imgurl).getContent());
		}catch(IOException e){
			Log.e(HomeActivity.TAG, e.toString() + " - " + imgurl);
			return null;
		}catch(NullPointerException npe){
			Log.e(HomeActivity.TAG, npe.toString() + " - " + imgurl);
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if(result != null){
			imageview.setImageBitmap(result);
			if(cache != null && cachedBitmap == null) cache.put(this.imgurl, result);
			imageview.setVisibility(View.VISIBLE);
		}
		else imageview.setVisibility(View.GONE);
		super.onPostExecute(result);
	}
}