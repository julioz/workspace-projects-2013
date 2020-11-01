package br.com.zynger.brasileirao2012.asynctasks;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import br.com.zynger.brasileirao2012.util.AnimationHelper;
import br.com.zynger.brasileirao2012.util.ImageManager;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

	public interface ImageDownloadable {
		void imageDownloaded(Bitmap bitmap);
	}

	private String imgurl;
	private final RelativeLayout rlVideoThumb;
	private ImageView imageview;
	private HashMap<String, Bitmap> cache;
	private Bitmap cachedBitmap;
	private final ImageDownloadable listener;

	public DownloadImageTask(ImageDownloadable listener, String imgurl,
			ImageView imageview) {
		this.listener = listener;
		this.imgurl = imgurl;
		this.imageview = imageview;
		this.rlVideoThumb = null;
		this.cache = null;
		this.cachedBitmap = null;
	}

	public DownloadImageTask(ImageDownloadable listener, String imgurl,
			RelativeLayout rlVideoThumb, ImageView imageview,
			HashMap<String, Bitmap> cache) {
		this.listener = listener;
		this.imgurl = imgurl;
		this.rlVideoThumb = rlVideoThumb;
		this.imageview = imageview;
		setVideoThumbVisibility(false);
		this.cache = cache;
		this.cachedBitmap = cache.get(this.imgurl);
	}

	public DownloadImageTask(ImageDownloadable listener, String imgurl,
			RelativeLayout rlVideoThumb, ImageView imageview,
			Drawable placeHolder, HashMap<String, Bitmap> cache) {
		this.listener = listener;
		this.imgurl = imgurl;
		this.rlVideoThumb = rlVideoThumb;
		this.imageview = imageview;
		this.imageview.setImageDrawable(placeHolder);
		setVideoThumbVisibility(true);
		this.cache = cache;
		this.cachedBitmap = cache.get(this.imgurl);
	}

	@Override
	protected Bitmap doInBackground(Void... arg0) {
		if (cachedBitmap != null) {
			return cachedBitmap;
		} else {
			Bitmap bitmap = ImageManager.loadFromUrl(imgurl);
			return bitmap != null ? ImageManager.getRoundedCornerBitmap(bitmap,
					3) : null;
		}
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			imageview.setImageBitmap(result);
			if (cache != null && cachedBitmap == null) {
				cache.put(this.imgurl, result);
			}
			setVideoThumbVisibility(true);
			listener.imageDownloaded(result);
		} else {
			setVideoThumbVisibility(false);
		}
		super.onPostExecute(result);
	}

	private void setVideoThumbVisibility(boolean visible) {
		if (rlVideoThumb != null) {
			rlVideoThumb.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
		if (visible) {
			imageview.setVisibility(View.INVISIBLE);
			imageview.startAnimation(AnimationHelper.getFadeInAnimation());
			imageview.setVisibility(View.VISIBLE);
		}
	}
}