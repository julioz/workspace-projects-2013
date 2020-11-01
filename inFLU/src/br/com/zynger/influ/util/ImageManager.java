package br.com.zynger.influ.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class ImageManager {

	private final static String PATH = "/data/data/br.com.zynger.influ/files/";
	private final static float width = 110;
	private final static float height = 110;

	public static Drawable DownloadFromUrl(String  imageURL, String  fileName) throws IOException{
		URL url = new URL (imageURL);
		File file = new File(PATH + fileName);
		
		//long startTime = System .currentTimeMillis();
		//Log.d("ImageManager", "download begining");
		//Log.d("ImageManager", "download url:" + url);
		//Log.d("ImageManager", "downloaded file name:" + fileName);
		/* Open a connection to that URL. */
		URLConnection  ucon = url.openConnection();
		
		//Define InputStreams to read from the URLConnection.
		InputStream  is = ucon.getInputStream();
		BufferedInputStream  bis = new BufferedInputStream (is);
		
		//Read bytes to the Buffer until there is nothing more to read(-1).
		ByteArrayBuffer baf = new ByteArrayBuffer(50);
		int current = 0;
		while ((current = bis.read()) != -1) {
			baf.append((byte) current);
		}
		
		/* Convert the Bytes read to a String. */
		FileOutputStream  fos = new FileOutputStream (file);
		fos.write(baf.toByteArray());
		fos.close();
		//Log.d("ImageManager", "download ready in" + ((System .currentTimeMillis() - startTime) / 1000) + " sec");
		
		Drawable drawable = BitmapDrawable.createFromPath(file.getCanonicalPath());
		try{			
			drawable = ImageManager.resize((BitmapDrawable) drawable);
		}catch(NullPointerException e){
			throw new IOException();
		}
		
		return drawable;
	}
	
	public static Bitmap loadFromUrl(String url){
		try{
		    URL ulrn = new URL(url);
		    HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
		    InputStream is = con.getInputStream();
		    BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inDither = false;                     //Disable Dithering mode
		    options.inPurgeable = true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
		    options.inSampleSize = 8;
		    Bitmap bmp = BitmapFactory.decodeStream(is, null, options);
		    if(con != null) con.disconnect();
		    return bmp;
		}catch(Exception e){
			return null;
		}
	}
	

	public static BitmapDrawable resize(BitmapDrawable bdImage) {
		Bitmap bitmapOrig = bdImage.getBitmap();
		float scaleWidth = ((float) width) / bitmapOrig.getWidth();
		float scaleHeight = ((float) height) / bitmapOrig.getHeight();
		if(scaleWidth > 1 || scaleHeight > 1){			
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrig, 0, 0, bitmapOrig.getWidth(), bitmapOrig.getHeight(), matrix, true);
			return new BitmapDrawable(resizedBitmap);
		}
		return new BitmapDrawable(bitmapOrig);
	}
}