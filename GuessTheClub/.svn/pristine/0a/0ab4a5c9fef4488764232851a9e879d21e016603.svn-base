package br.com.zynger.guesstheclub.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageTransformer {

	public static BitmapDrawable rotate(Drawable drawable, float angle){
		Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();
		// Create blank bitmap of equal size
		Bitmap canvasBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
		canvasBitmap.eraseColor(0x00000000);

		// Create canvas
		Canvas canvas = new Canvas(canvasBitmap);

		// Create rotation matrix
		Matrix rotateMatrix = new Matrix();
		rotateMatrix.setRotate(angle, canvas.getWidth()/2, canvas.getHeight()/2);

		// Draw bitmap onto canvas using matrix
		canvas.drawBitmap(originalBitmap, rotateMatrix, null);

		return new BitmapDrawable(canvasBitmap); 
	}
	
	public static BitmapDrawable changeTransparency(Drawable drawable, int transparency){
		Bitmap bmpOriginal = ((BitmapDrawable) drawable).getBitmap();
	    Bitmap bmResult = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
	    Canvas tempCanvas = new Canvas(bmResult);
		
		Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(transparency);
        tempCanvas.drawBitmap(bmpOriginal, 0, 0, alphaPaint);
        
        return new BitmapDrawable(bmResult);
	}
}
