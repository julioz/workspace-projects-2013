package br.com.zynger.influ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.rss.Article;
import br.com.zynger.influ.rss.RSSParser;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.util.ImageManager;
import br.com.zynger.influ.view.ActionItem;
import br.com.zynger.influ.view.EllipsizingTextView;
import br.com.zynger.influ.view.QuickAction;

public class NewsActivity extends ListActivity {

	private SharedPreferences prefs;
	
	private List<Article> articles;
	private WebView webview;
	private LinearLayout ll_ad_rsslist;
	private ProgressBar progressBar, pb_empty;
	private TextView tv_empty;
	private QuickAction quickaction;
	private ActionItem actionItem_webpage, actionItem_twitter, actionItem_share;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.rss_list);

		loadViews();
		updateTheme();
		setWebViewSettings();
		setDividerGradient();
		setActionItem();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		String url = getIntent().getStringExtra("url");
		String encoding = getIntent().getStringExtra("encoding");
		int domain = getIntent().getIntExtra("domain", 0);
		new UpdateNewsTask(this, url, encoding, domain).execute();

	}

	private void loadViews() {
		ll_ad_rsslist = (LinearLayout) findViewById(R.id.ll_ad_rsslist);
		webview = (WebView) findViewById(R.id.webview);
		progressBar = (ProgressBar) findViewById(R.id.webview_progressbar);
		pb_empty = (ProgressBar) findViewById(R.id.pb_rss_empty);
		tv_empty = (TextView) findViewById(R.id.tv_rss_empty);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		getListView().setBackgroundColor(t.getContent_background());
		ll_ad_rsslist.setBackgroundColor(t.getContent_background());
		
		getListView().invalidate();
	}

	private void setWebViewSettings() {
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress)
			{
				progressBar.setProgress(progress);

				if(progress == 100) progressBar.setVisibility(View.GONE);
			}
		});

		this.getListView().setLongClickable(true);

		this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				final int pos = position;
				quickaction = new QuickAction(v);
				quickaction.setAnimStyle(QuickAction.ANIM_REFLECT);

				actionItem_webpage.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(pos).getUrl().toString()));
						quickaction.dismiss();
						startActivity(it);
					}
				});

				actionItem_twitter.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent it = new Intent(v.getContext(), TwitterDialog.class);
						quickaction.dismiss();
						it.putExtra("url", articles.get(pos).getUrl().toString());
						v.getContext().startActivity(it);
					}
				});

				actionItem_share.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_SEND);
						quickaction.dismiss();
						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_TEXT, articles.get(pos).getUrl().toString());
						startActivity(Intent.createChooser(intent, "Compartilhar com..."));
					}
				});

				quickaction.addActionItem(actionItem_webpage);
				quickaction.addActionItem(actionItem_twitter);
				quickaction.addActionItem(actionItem_share);
				quickaction.show();
				return true;
			}
		});
	}

	private void setActionItem(){
		actionItem_webpage = new ActionItem();
		actionItem_webpage.setIcon(getResources().getDrawable(R.drawable.ic_liveview_quickaction));
		actionItem_webpage.setTitle("Ler notícia");

		actionItem_twitter = new ActionItem();
		actionItem_twitter.setIcon(getResources().getDrawable(R.drawable.ic_twitter_quickaction));
		actionItem_twitter.setTitle("Twitter");

		actionItem_share = new ActionItem();
		actionItem_share.setIcon(getResources().getDrawable(R.drawable.ic_share_quickaction));
		actionItem_share.setTitle("Mais");
	}

	@Override
	public void onBackPressed() {
		if(progressBar.getVisibility() == View.VISIBLE) progressBar.setVisibility(View.GONE);
		
		if(webview.getVisibility() == View.VISIBLE) webview.setVisibility(View.GONE);
		else super.onBackPressed();
	}

	private void setDividerGradient(){
		//int[] colors = {0, 0xFFFFFFFF, 0};
		int[] colors = {0x04a07b, 0x04a07b, 0x04a07b};
		this.getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		this.getListView().setDividerHeight(1);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		webview.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		String url = articles.get(position).getUrl().toString();
		webview.loadUrl(url);
	}

	private class UpdateNewsTask extends AsyncTask<Void, Void, List<Article>> {
		private Context c;
		private String url, encoding;
		private int domain;

		public UpdateNewsTask(Context context, String url, String encoding, int domain){
			this.c = context;
			this.url = url;
			this.encoding = encoding;
			this.domain = domain;
		}
		
		@Override
		protected void onPreExecute() {
			if(SplashScreen.isConnected(c)){
				pb_empty.setVisibility(View.VISIBLE);
				tv_empty.setText("Atualizando...");
			}else{
				pb_empty.setVisibility(View.GONE);
				tv_empty.setText(getResources().getString(R.string.loading_articles));
			}
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Article> doInBackground(Void... arg0) {
			try {
				RSSParser rssP = new RSSParser(domain, url, encoding);
				return rssP.parse();
			} catch (XmlPullParserException e) {
				Log.e("inFLU", "XMLPULLPARSEREXC=" + e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("inFLU", "IOEXC=" + e.toString());
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<Article> result) {
			if(result != null){
				articles = result;
				ll_ad_rsslist.setVisibility(View.VISIBLE);
				Theme theme = (Theme) FileHandler.openBackup(c, ThemeChooserActivity.ARQUIVO);
				MyArrayAdapter adapter = new MyArrayAdapter(c, R.layout.news_row, theme.getNewsRowSelector(), result);
				
				setListAdapter(adapter);
			}else{
				ll_ad_rsslist.setVisibility(View.GONE);
				pb_empty.setVisibility(View.GONE);
				tv_empty.setText(getResources().getString(R.string.loading_articles));
			}
			super.onPostExecute(result);
		}
	}

	private class MyArrayAdapter extends ArrayAdapter<Article>{

		@SuppressWarnings("unused")
		private Context mContext;
		private final LayoutInflater mInflater;
		private int layoutResourceId;
		private int newsrowselector;
		private List<Article> objects;
		private HashMap<String, Bitmap> cache;
		
		public MyArrayAdapter(Context context, int layoutResourceId, int newsRowSelector, List<Article> objects) {
			super(context, layoutResourceId, objects);
			this.mInflater = LayoutInflater.from(context);
			this.layoutResourceId = layoutResourceId;
			this.newsrowselector = newsRowSelector;
			this.objects = objects;
			this.mContext = context;
			cache = new HashMap<String, Bitmap>();
		}
		
		@Override
		public int getCount() {
			return objects.size();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = convertView;
	        ArticleHolder holder = null;
	        
	        if(row == null){
	            row = mInflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new ArticleHolder();
	            holder.imgIcon = (ImageView) row.findViewById(R.news_row.imageview);
	            holder.txtTitle = (EllipsizingTextView) row.findViewById(R.news_row.tv_title);
	            holder.txtPubdate = (TextView) row.findViewById(R.news_row.tv_pubdate);
	            
	            row.setTag(holder);
	        }else{
	            holder = (ArticleHolder) row.getTag();
	        }
	        
	        Article art = objects.get(position);
	        
	        String imgUrl = art.getImgUrl();
	        if(imgUrl == null) holder.imgIcon.setVisibility(View.GONE);
	        else{
	        	Bitmap bm = cache.get(art.getTitle());
	        	if(bm != null){
	        		holder.imgIcon.setImageBitmap(bm);
	        		holder.imgIcon.setVisibility(View.VISIBLE);
	        	}else{
	        		if(prefs.getBoolean("cb_imgsdownload", true)) new DownloadImageTask(art, cache, holder.imgIcon, holder.txtTitle).execute();
	        		holder.imgIcon.setVisibility(View.GONE);
	        	}
	        }
	        
	        holder.txtTitle.setText(art.getTitle());
	        holder.txtTitle.setMaxLines(2);
	        holder.txtPubdate.setText(art.getPubCalendar());

	        row.setBackgroundDrawable(getResources().getDrawable(this.newsrowselector));
	        
	        return row;
		
		}
		
		private class DownloadImageTask extends AsyncTask<Void, Void, Void> {
			private HashMap<String, Bitmap> cache;
			private Article art;
			private ImageView iv;
			private EllipsizingTextView tv;
			
			public DownloadImageTask(Article art, HashMap<String, Bitmap> cache, ImageView imgIcon, EllipsizingTextView txtTitle){
				this.art = art;
				this.cache = cache;
				this.iv = imgIcon;
				this.tv = txtTitle;
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				Bitmap bm = ImageManager.loadFromUrl(art.getImgUrl());
				cache.put(art.getTitle(), bm);
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				iv.setImageBitmap(cache.get(art.getTitle()));
				//iv.setVisibility(View.VISIBLE);
				tv.invalidate();
				iv.invalidate();
				super.onPostExecute(result);
			}
			
		}
		
		
	}
	
	static class ArticleHolder
    {
        ImageView imgIcon;
        EllipsizingTextView txtTitle;
        TextView txtPubdate;
    }


}