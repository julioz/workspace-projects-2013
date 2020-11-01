package edu.mst.kmnrradio.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.holoeverywhere.ArrayAdapter;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import edu.mst.kmnrradio.R;
import edu.mst.kmnrradio.model.Post;

public class PostsListAdapter extends ArrayAdapter<Post> {

	private final static int LAYOUT_RESOURCE = R.layout.postsrow;
	
	private SimpleDateFormat dateFormat, monthYearFormat;
	
	private final LayoutInflater mInflater;
	
	public PostsListAdapter(Context context, ArrayList<Post> objects) {
		super(context, LAYOUT_RESOURCE, objects);
		this.mInflater = LayoutInflater.from(context);
		dateFormat = new SimpleDateFormat("dd");
		monthYearFormat = new SimpleDateFormat("MMM yy");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		PostHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUT_RESOURCE, parent, false);
            
            holder = new PostHolder();
            holder.pubDateDay = (TextView) row.findViewById(R.postsrow.pubdate_day);
            holder.pubDateMonthYear = (TextView) row.findViewById(R.postsrow.pubdate_monthyear);
            holder.description = (TextView) row.findViewById(R.postsrow.description);
            
            row.setTag(holder);
        }else{
            holder = (PostHolder) row.getTag();
        }

        final Post post = getItem(position);
        
        holder.pubDateDay.setText(dateFormat.format(post.getPubdate().getTime())); //TODO: Trocar por SimpleDateFormat
        holder.pubDateMonthYear.setText(monthYearFormat.format(post.getPubdate().getTime()).toUpperCase());
        holder.description.setText(Html.fromHtml(post.getDescription()));
        
        row.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(post.getLink()));
				v.getContext().startActivity(browserIntent);
			}
		});
        
        return row;
	}

}

class PostHolder {
	TextView pubDateDay;
	TextView pubDateMonthYear;
	TextView description;
}
