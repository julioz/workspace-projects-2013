package br.com.zynger.vamosmarcar.view;

import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.model.User;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class FacebookImageView extends ImageView {

	public enum Size {
		SQUARE, SMALL, LARGE
	}

	public FacebookImageView(Context context) {
		super(context);
	}

	public FacebookImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FacebookImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void loadFacebookId(final String id) {
		setImage(id);
	}
	
	public void loadParseUser(final ParseUser user) {
		setImage(User.getId(user));
	}

	private void setImage(final String id) {
		String url = getImageUrl(id, Size.LARGE);

		int blank = R.drawable.ic_profile_blank;
		Picasso.with(getContext()).load(url).error(blank).placeholder(blank).transform(new Transformation() {
			@Override
			public Bitmap transform(Bitmap source) {
				int size = Math.min(source.getWidth(), source.getHeight());

				int x = (source.getWidth() - size) / 2;
				int y = (source.getHeight() - size) / 2;

				Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size,
						size);
				if (squaredBitmap != source) {
					source.recycle();
				}

				Bitmap output = Bitmap.createBitmap(size, size,
						source.getConfig());

				Canvas canvas = new Canvas(output);
				Paint paint = new Paint();
				BitmapShader shader = new BitmapShader(squaredBitmap,
						BitmapShader.TileMode.CLAMP,
						BitmapShader.TileMode.CLAMP);
				paint.setShader(shader);
				paint.setAntiAlias(true);

				float r = size / 2f;
				canvas.drawCircle(r, r, r, paint);

				squaredBitmap.recycle();
				return output;
			}

			@Override
			public String key() {
				return id + "_circle";
			}
		}).into(this);
	}

	private String getImageUrl(String id, Size size) {
		String url = "http://graph.facebook.com/" + id + "/picture";

		if (size != null) {
			url += "?type=" + size.toString().toLowerCase(Locale.getDefault());
		}

		return url;
	}

}
