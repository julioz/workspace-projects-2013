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
import br.com.zynger.libertadores.model.Video;

public class VideoCentralAdapter extends ArrayAdapter<Video> {

	public final static int COLOR_LIGHTGRAY = 0xFFF4F4F4;
	public final static int COLOR_DARGGRAY = 0xFFDFDFDF;
	
	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.videocentralrow;
	private HashMap<String, Bitmap> cache;
	private ArrayList<Video> objects;
	private boolean downloadImages;
	
	public VideoCentralAdapter(Context context, ArrayList<Video> videos, boolean downloadImages) {
		super(context, LAYOUTRESOURCEID);
		this.mInflater = LayoutInflater.from(context);
		cache = new HashMap<String, Bitmap>();
		objects = videos;
		this.downloadImages = downloadImages;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	@Override
	public Video getItem(int position) {
		return objects.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		VideosHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new VideosHolder();
            holder.tv_title = (TextView) row.findViewById(R.videocentralrow.tv_title);
            holder.iv_thumbnail = (ImageView) row.findViewById(R.videocentralrow.iv_thumbnail);
            holder.rl_datethumb = (RelativeLayout) row.findViewById(R.videocentralrow.rl_datethumb);
            
            row.setTag(holder);
        }else{
            holder = (VideosHolder) row.getTag();
            holder.iv_thumbnail.setImageBitmap(null);
            holder.iv_thumbnail.setVisibility(View.VISIBLE);
            holder.rl_datethumb.setVisibility(View.VISIBLE);
        }
        
        int bgColor = position % 2 == 0 ? COLOR_LIGHTGRAY : COLOR_DARGGRAY;
        row.setBackgroundColor(bgColor);

        Video video = getItem(position);
        
        holder.tv_title.setText(video.getTitle());
        
        if(null != video.getThumbnailUrl() && downloadImages){
        	new DownloadImageTask(video.getThumbnailUrl(), holder.iv_thumbnail, cache).execute();
        }else{
        	holder.iv_thumbnail.setVisibility(View.GONE);
        	holder.rl_datethumb.setVisibility(View.GONE);
        }
        
        return row;
	}
}

class VideosHolder {
	TextView tv_title;
	ImageView iv_thumbnail;
	RelativeLayout rl_datethumb;
}