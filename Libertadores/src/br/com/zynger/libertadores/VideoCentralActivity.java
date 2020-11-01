package br.com.zynger.libertadores;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.adapters.VideoCentralAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetVideoCentralTask;
import br.com.zynger.libertadores.model.Video;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.web.VideoCentralParser;

public class VideoCentralActivity extends ListActivity implements AsyncTaskListener {

	public final static String INTENT_SEARCH_KEYWORDS = "INTENT_SEARCH_KEYWORDS";
	
	private VideoCentralParser parser;
	
	private HelveticaTextView tv_title;
	private RelativeLayout rl_search;
	private DataUpdateLayout dul_update;
	private EditText et_searchbox;
	private Button bt_searchconfirm;
	
	private SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videocentralactivity);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String searchKeywords = getIntent().getStringExtra(INTENT_SEARCH_KEYWORDS);
		
		loadViews();
		
		if(searchKeywords != null) et_searchbox.setText(searchKeywords);

		parser = new VideoCentralParser(this);
		
		new GetVideoCentralTask(parser, searchKeywords, this).execute();
		
		bt_searchconfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetVideoCentralTask(parser, et_searchbox.getText().toString().trim(),
						VideoCentralActivity.this).execute();
			}
		});

		et_searchbox.setOnKeyListener(new TextView.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)){
					bt_searchconfirm.performClick();
					return true;
				}
				return false;
			}
		});
	}
	
	private void loadViews() {
		tv_title = (HelveticaTextView) findViewById(R.videocentralactivity.titlebar_tvtitle);
		rl_search = (RelativeLayout) findViewById(R.videocentralactivity.rl_search);
		et_searchbox = (EditText) findViewById(R.videocentralactivity.et_searchbox);
		bt_searchconfirm = (Button) findViewById(R.videocentralactivity.bt_searchconfirm);

		dul_update = (DataUpdateLayout) findViewById(R.videocentralactivity.dul_update);
		dul_update.addViewToToggle(getListView());
		dul_update.addViewToToggle(rl_search);

		tv_title.setText(getString(R.string.homedashboard_videocentral).toUpperCase());
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Video video = (Video) l.getAdapter().getItem(position);
		Intent i = new Intent(this, YoutubeVideoPlayerActivity.class);
		i.putExtra(YoutubeVideoPlayerActivity.INTENT_VIDEOID, video.getId());
		i.putExtra(YoutubeVideoPlayerActivity.INTENT_VIDEOURL, video.getUrl());
		v.getContext().startActivity(i);
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void preExecution() {
		dul_update.showFullLayout();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(Object result) {
		ArrayList<Video> videos = (ArrayList<Video>) result;

		if(videos.size() > 0){
			setListAdapter(new VideoCentralAdapter(this, videos, sharedPrefs.getBoolean("cb_imgsdownload", true)));
			dul_update.hide();
		}else{
			dul_update.setOnlyText(getString(R.string.error_emptyvideos));
		}
	}

	@Override
	public void onFail(String message) {
		dul_update.setOnlyText(getString(R.string.error_novideos));
	}
}