package br.com.zynger.brasileirao2012;

import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.asynctasks.AsyncTaskListener;
import br.com.zynger.brasileirao2012.asynctasks.DownloadImageTask;
import br.com.zynger.brasileirao2012.asynctasks.DownloadImageTask.ImageDownloadable;
import br.com.zynger.brasileirao2012.asynctasks.ReadNewsFromDomain;
import br.com.zynger.brasileirao2012.asynctasks.ReadNewsTask;
import br.com.zynger.brasileirao2012.base.ZoomableActivity;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.model.Article;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.PreferenceEditor;
import br.com.zynger.brasileirao2012.view.DataUpdateLayout;

import com.actionbarsherlock.view.MenuItem;

public class NewsDetailsActivity extends ZoomableActivity implements
		AsyncTaskListener<Object>, ImageDownloadable {

	public final static String INTENT_ARTICLE = "ARTICLE";

	private Club club;
	private boolean shouldDownloadImages;

	private String newsUrl, articleImageUrl;
	private float[] textSizes = { 9, 12, 15, 19, 24 };
	private int curTextSizeIndex = 2;
	private NewsDomain domain;

	private TextView tvArticleTitle, tvArticleText;
	private ImageView ivImage;
	private DataUpdateLayout dulLoading;
	private AsyncTask<Void, Void, Bitmap> downloadImageTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetailsactivity);
		setClubFromIntent();
		loadViews();
		setArticleFromIntent();

		shouldDownloadImages = PreferenceEditor.shouldDownloadImages(this);
		downloadImageTask = new DownloadImageTask(this, articleImageUrl,
				ivImage);
		startAsyncTask();
	}

	private void startAsyncTask() {
		if (domain == NewsDomain.UOL) {
			new ReadNewsFromDomain(this, newsUrl, domain).execute();
		} else {
			if (domain != NewsDomain.NETFLU) {
				downloadImage();
			}
			new ReadNewsTask(newsUrl, domain, this).execute();
		}
	}

	private void downloadImage() {
		if (shouldDownloadImages
				&& downloadImageTask.getStatus() == AsyncTask.Status.PENDING) {
			downloadImageTask.execute();
		}
	}

	private void setArticleFromIntent() {
		Article article = (Article) getIntent().getSerializableExtra(
				INTENT_ARTICLE);
		tvArticleTitle.setText(article.getTitle());
		newsUrl = article.getUrl().toString();
		articleImageUrl = article.getImgUrl();
		domain = NewsDomain.valueOf(article.getDomain().toString());
	}

	private void setClubFromIntent() {
		club = ClubsDB.getInstance(this).getClubs()
				.get(getIntent().getStringExtra(Constants.INTENT_CLUBACRONYM));

		setTitle(club.getName().toUpperCase(Locale.getDefault()));
		setIcon(club.getBadgeResource(this));
	}

	private void loadViews() {
		tvArticleTitle = (TextView) findViewById(R.newsdetailsactivity.tv_title);
		tvArticleText = (TextView) findViewById(R.newsdetailsactivity.tv_articletext);
		ivImage = (ImageView) findViewById(R.newsdetailsactivity.iv_image);
		dulLoading = (DataUpdateLayout) findViewById(R.newsdetailsactivity.dul_loading);

		ivImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showExpandedImage();
			}
		});

		dulLoading.setMessage(getString(R.string.newsdetailsactivity_reading));
		dulLoading
				.addViewToToggle((ScrollView) findViewById(R.newsdetailsactivity.sv_tv_text));
	}

	private void showExpandedImage() {
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_image);
		((ImageView) dialog.findViewById(R.dialog_image.iv))
				.setImageDrawable(ivImage.getDrawable());
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@Override
	protected void onResume() {
		if (ivImage.getVisibility() == View.GONE) {
			downloadImage();
		}
		super.onResume();
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void preExecution() {
		dulLoading.setMessage(R.string.updating);
		dulLoading.show();
	}

	@Override
	public void onComplete(Object result) {
		String news = new String();
		if (result instanceof String) {
			news = (String) result;
		} else if (result instanceof String[]) {
			String[] newsArray = (String[]) result;

			String imgsrcServer = newsArray[0];
			news = newsArray[1];
			if (shouldDownloadImages && imgsrcServer != null
					&& domain != NewsDomain.NETFLU) {
				downloadImageTask = new DownloadImageTask(this, imgsrcServer,
						ivImage);
				downloadImage();
			}
		}

		tvArticleText.setText(Html.fromHtml(news));
		tvArticleText.setMovementMethod(LinkMovementMethod.getInstance());
		dulLoading.hide();
	}

	@Override
	public void onFail(String message) {
		dulLoading.setButtonMessage(message,
				getString(R.string.newsviewflowadapter_openpage),
				getResources().getDrawable(R.drawable.ic_menu_website),
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse(newsUrl)));
					}
				});
	}

	@Override
	public void menuItemPressed(MenuItem menuItem) {
		curTextSizeIndex = (curTextSizeIndex == textSizes.length - 1) ? 0
				: curTextSizeIndex + 1;
		tvArticleText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
				textSizes[curTextSizeIndex]);
	}

	@Override
	protected int getActionBarIcon() {
		return R.drawable.ic_news;
	}

	@Override
	protected int getActionBarTitle() {
		return R.string.newsviewflowactivity_title;
	}

	@Override
	public void imageDownloaded(Bitmap bitmap) {
		// After showing the image, there is nothing else to be done
	}
}