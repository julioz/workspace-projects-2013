package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import br.com.zynger.libertadores.adapters.NewsAdapter;
import br.com.zynger.libertadores.asynctasks.AsyncTaskListener;
import br.com.zynger.libertadores.asynctasks.GetNewsTask;
import br.com.zynger.libertadores.model.Article;
import br.com.zynger.libertadores.view.DataUpdateLayout;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.web.NewsParser;

public class NewsActivity extends ListActivity implements AsyncTaskListener {

	private NewsParser newsParser;
	
	private HelveticaTextView tv_title;
	private DataUpdateLayout dul_update;
	
	private SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsactivity);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		loadViews();
		
		String language = Locale.getDefault().getLanguage();
		
		String alternateLang = sharedPrefs.getString("list_newslanguage", null);
		if(alternateLang != null) language = alternateLang;
		
		newsParser = new NewsParser(this, language);
		
		new GetNewsTask(newsParser, this).execute();
	}
	
	private void loadViews() {
		tv_title = (HelveticaTextView) findViewById(R.newsactivity.titlebar_tvtitle);
		dul_update = (DataUpdateLayout) findViewById(R.newsactivity.dul_update);
		dul_update.addViewToToggle(getListView());

		tv_title.setText(getString(R.string.homedashboard_news).toUpperCase());
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Article article = (Article) l.getAdapter().getItem(position);
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(article.getLink()));
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
		ArrayList<Article> articles = (ArrayList<Article>) result;
		
		if(articles.size() > 0){
			setListAdapter(new NewsAdapter(this, articles, sharedPrefs.getBoolean("cb_imgsdownload", true)));
			dul_update.hide();
		}else{
			dul_update.setOnlyText(getString(R.string.error_emptynews));
		}
	}

	@Override
	public void onFail(String message) {
		dul_update.setOnlyText(getString(R.string.error_nonews));
	}
}
