package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.asynctasks.DownloadImageTask;
import br.com.zynger.libertadores.model.Tweet;

public class TwitterCentralAdapter extends ArrayAdapter<Tweet> {
	
	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.twittercentralrow;
	private HashMap<String, Bitmap> cache;
	private ArrayList<Tweet> objects;
	private boolean downloadImages;
	
	public TwitterCentralAdapter(Context context, ArrayList<Tweet> tweets, boolean downloadImages) {
		super(context, LAYOUTRESOURCEID);
		this.mInflater = LayoutInflater.from(context);
		cache = new HashMap<String, Bitmap>();
		objects = tweets;
		this.downloadImages = downloadImages;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	@Override
	public Tweet getItem(int position) {
		return objects.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TweetsHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new TweetsHolder();
            holder.tv_title = (TextView) row.findViewById(R.twittercentralrow.tv_title);
            holder.iv_thumbnail = (ImageView) row.findViewById(R.twittercentralrow.iv_thumbnail);
            holder.rl_datethumb = (RelativeLayout) row.findViewById(R.twittercentralrow.rl_datethumb);
            holder.tv_author = (TextView) row.findViewById(R.twittercentralrow.tv_author);
            
            row.setTag(holder);
        }else{
            holder = (TweetsHolder) row.getTag();
            holder.iv_thumbnail.setImageBitmap(null);
            holder.iv_thumbnail.setVisibility(View.VISIBLE);
            holder.rl_datethumb.setVisibility(View.VISIBLE);
        }
        
        int bgColor = position % 2 == 0 ? VideoCentralAdapter.COLOR_LIGHTGRAY : VideoCentralAdapter.COLOR_DARGGRAY;
        row.setBackgroundColor(bgColor);

        Tweet tweet = getItem(position);
        
        holder.tv_title.setText(tweet.getText());
        holder.tv_author.setText(tweet.getAuthor());
        
        if(null != tweet.getAuthorImageUrl() && downloadImages){
        	new DownloadImageTask(tweet.getAuthorImageUrl(), holder.iv_thumbnail, cache).execute();
        }else{
        	holder.iv_thumbnail.setVisibility(View.GONE);
        	holder.rl_datethumb.setVisibility(View.GONE);
        }
        
        return row;
	}
}

class TweetsHolder {
	TextView tv_title;
	ImageView iv_thumbnail;
	RelativeLayout rl_datethumb;
	TextView tv_author;
}