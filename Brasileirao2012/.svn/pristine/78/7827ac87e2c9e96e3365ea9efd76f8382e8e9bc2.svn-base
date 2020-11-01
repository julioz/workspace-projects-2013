package br.com.zynger.brasileirao2012;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.asynctasks.DownloadImageTask;
import br.com.zynger.brasileirao2012.asynctasks.DownloadImageTask.ImageDownloadable;
import br.com.zynger.brasileirao2012.base.SimpleActivity;
import br.com.zynger.brasileirao2012.util.ConnectionHelper;
import br.com.zynger.brasileirao2012.util.PreferenceEditor;

public class StadiumsActivity extends SimpleActivity implements
		ImageDownloadable {
	public final static String INTENT_NAME = "ST_NAME";
	public final static String INTENT_NICK = "ST_NICK";
	public final static String INTENT_CITY = "ST_CITY";
	public final static String INTENT_CAPACITY = "ST_CAPACITY";
	public final static String INTENT_FOUNDATION = "ST_FOUNDATION";
	public final static String INTENT_WIDTH = "ST_WIDTH";
	public final static String INTENT_HEIGHT = "ST_HEIGHT";
	public final static String INTENT_DESCRIPTION = "ST_DESCRIPTION";
	public final static String INTENT_URL = "ST_URL";

	private ImageView iv_stadium;
	private LinearLayout ll_data;
	private OnClickListener oclExpandImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stadiumsactivity);
		setTitleFromIntent();
		loadViews();

		hideStadiumImage();
		setDataFromIntent();
		downloadImage(getIntent().getStringExtra(INTENT_URL));
	}

	private void loadViews() {
		iv_stadium = (ImageView) findViewById(R.stadiumsactivity.iv_image);
		ll_data = (LinearLayout) findViewById(R.stadiumsactivity.ll_data);

		oclExpandImageView = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog d = new Dialog(v.getContext());
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.dialog_image);
				ImageView iv = (ImageView) d.findViewById(R.dialog_image.iv);
				iv.setImageDrawable(((ImageView) v).getDrawable());
				d.setCanceledOnTouchOutside(true);
				d.show();
			}
		};
	}

	private void downloadImage(String url) {
		if (PreferenceEditor.shouldDownloadImages(this)
				&& ConnectionHelper.isConnected(this)) {
			new DownloadImageTask(this, url, iv_stadium).execute();
		}
	}

	private void setTitleFromIntent() {
		String nick = getIntent().getStringExtra(INTENT_NICK);
		if (nick != null) {
			setTitle(nick);
		}
	}

	private void setDataFromIntent() {
		setTextWithTitle(R.stadiumsactivity.tv_name,
				R.string.stadiumsactivity_name, INTENT_NAME);
		setTextWithTitle(R.stadiumsactivity.tv_nick,
				R.string.stadiumsactivity_nick, INTENT_NICK);
		setTextWithTitle(R.stadiumsactivity.tv_city,
				R.string.stadiumsactivity_city, INTENT_CITY);
		setTextWithTitle(R.stadiumsactivity.tv_capacity,
				R.string.stadiumsactivity_capacity, INTENT_CAPACITY);
		((TextView) findViewById(R.stadiumsactivity.tv_size)).setText(Html
				.fromHtml("<b>" + getString(R.string.stadiumsactivity_measures)
						+ ":</b> " + getIntent().getStringExtra(INTENT_WIDTH)
						+ "m x " + getIntent().getStringExtra(INTENT_HEIGHT)
						+ "m"));
		setTextWithTitle(R.stadiumsactivity.tv_foundation,
				R.string.stadiumsactivity_foundation, INTENT_FOUNDATION);
		((TextView) findViewById(R.stadiumsactivity.tv_description))
				.setText(getIntent().getStringExtra(INTENT_DESCRIPTION));
	}

	private void setTextWithTitle(int textViewId, int titleRes, String intentKey) {
		TextView tv = (TextView) findViewById(textViewId);
		tv.setText(Html.fromHtml("<b>" + getString(titleRes) + ":</b> "
				+ getIntent().getStringExtra(intentKey)));
	}

	private void hideStadiumImage() {
		iv_stadium.setVisibility(View.GONE);
		iv_stadium.setOnClickListener(null);
		ll_data.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_stadium;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.stadiumsactivity_title;
	}

	@Override
	public void imageDownloaded(Bitmap bitmap) {
		iv_stadium.setOnClickListener(oclExpandImageView);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
		lp.setMargins(15, 0, 0, 0);
		ll_data.setLayoutParams(lp);
	}
}
