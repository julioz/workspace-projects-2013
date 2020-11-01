package br.com.zynger.libertadores;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import br.com.zynger.libertadores.adapters.TwitterCentralAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetTwitterCentralTask;
import br.com.zynger.libertadores.model.Tweet;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.web.TwitterCentralParser;

public class TwitterCentralActivity extends ListActivity implements AsyncTaskListener {

	private TwitterCentralParser parser;
	
	private HelveticaTextView tv_title;
	private DataUpdateLayout dul_update;
	
	private SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twittercentralactivity);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		loadViews();

		parser = new TwitterCentralParser(this);
		
		new GetTwitterCentralTask(parser, this).execute();
	}
	
	private void loadViews() {
		tv_title = (HelveticaTextView) findViewById(R.twittercentralactivity.titlebar_tvtitle);
		dul_update = (DataUpdateLayout) findViewById(R.twittercentralactivity.dul_update);
		dul_update.addViewToToggle(getListView());

		tv_title.setText(getString(R.string.homedashboard_twittercentral).toUpperCase());
	}

	@Override
	public void preExecution() {
		dul_update.showFullLayout();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(Object result) {
		ArrayList<Tweet> tweets = (ArrayList<Tweet>) result;

		if(tweets.size() > 0){
			setListAdapter(new TwitterCentralAdapter(
					this, tweets, sharedPrefs.getBoolean("cb_imgsdownload", true)));
			dul_update.hide();
		}else{
			dul_update.setOnlyText(getString(R.string.error_emptytweets));
		}
	}

	@Override
	public void onFail(String message) {
		dul_update.setOnlyText(getString(R.string.error_notweets));
	}
}
