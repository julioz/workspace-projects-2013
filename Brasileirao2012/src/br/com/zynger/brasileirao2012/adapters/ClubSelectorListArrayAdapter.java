package br.com.zynger.brasileirao2012.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.Club;

public class ClubSelectorListArrayAdapter extends ArrayAdapter<Club> {

	private static final int LAYOUT_RESOURCE = R.layout.clubselectorrow;

	private LayoutInflater mInflater;
	private ArrayList<Club> clubs;
	
	public ClubSelectorListArrayAdapter(Context context, ArrayList<Club> objects) {
		super(context, LAYOUT_RESOURCE, objects);
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.clubs = objects;
	}
	
	@Override
	public int getCount() {
		return clubs.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ClubSelectorHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUT_RESOURCE, parent, false);
            
            holder = new ClubSelectorHolder();
            holder.ivBadge = (ImageView) row.findViewById(R.clubselectorrow.iv_badge);
            holder.txtName = (TextView) row.findViewById(R.clubselectorrow.tv_name);
            
            row.setTag(holder);
        }else{
            holder = (ClubSelectorHolder) row.getTag();
        }

        Club club = getItem(position);
        holder.ivBadge.setImageResource(club.getBadgeResource(getContext()));
        holder.txtName.setText(club.getName());

        return row;
	}
}

class ClubSelectorHolder {
    ImageView ivBadge;
    TextView txtName;
}
