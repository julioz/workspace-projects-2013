package br.com.zynger.brasileirao2012.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class ImageManager {
	
	public static Bitmap loadFromUrl(String url){
		try{
		    URL ulrn = new URL(url);
		    HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
		    InputStream is = con.getInputStream();
		    BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inDither = false;                     //Disable Dithering mode
		    options.inPurgeable = true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
		    options.inSampleSize = 2;
		    Bitmap bmp = BitmapFactory.decodeStream(is, null, options);
		    if(con != null) con.disconnect();
		    return bmp;
		}catch(Exception e){
			return null;
		}catch(OutOfMemoryError e){
			return null;
		}
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
	public static Bitmap decodeResource(Context context, int resId) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 70;

		// Find the correct scale value. It should be the power of 2.
		int scale = 1;
		while (o.outWidth / scale / 2 >= REQUIRED_SIZE
				&& o.outHeight / scale / 2 >= REQUIRED_SIZE){			
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeResource(context.getResources(), resId, o2);
	}
}