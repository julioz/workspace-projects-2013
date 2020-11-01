package br.com.zynger.brasileirao2012.adapters;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.comparator.FullNameComparator;
import br.com.zynger.brasileirao2012.model.AprovData;
import br.com.zynger.brasileirao2012.model.Club;

public class AprovAdapter extends ArrayAdapter<Club> {
	private final static int LAYOUT_RESOURCE = R.layout.aprovrow;
	
	private final LayoutInflater mInflater;
	private ArrayList<Club> objects;
	
	public AprovAdapter(Context context, ArrayList<Club> objects) {
		super(context, LAYOUT_RESOURCE, objects);
		Collections.sort(objects, new FullNameComparator());
		this.mInflater = LayoutInflater.from(context);
		this.objects = objects;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	public ArrayList<Club> getList() {
		return objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AprovDataHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUT_RESOURCE, parent, false);
            
            holder = new AprovDataHolder();
            holder.iv_bdg = (ImageView) row.findViewById(R.aprovrow.iv_bdg);
            holder.mand_j = (TextView) row.findViewById(R.aprovrow.mand_j);
            holder.mand_v = (TextView) row.findViewById(R.aprovrow.mand_v);
            holder.mand_e = (TextView) row.findViewById(R.aprovrow.mand_e);
            holder.mand_perc = (TextView) row.findViewById(R.aprovrow.mand_perc);
            holder.vis_j = (TextView) row.findViewById(R.aprovrow.vis_j);
            holder.vis_v = (TextView) row.findViewById(R.aprovrow.vis_v);
            holder.vis_e = (TextView) row.findViewById(R.aprovrow.vis_e);
            holder.vis_perc = (TextView) row.findViewById(R.aprovrow.vis_perc);
            
            row.setTag(holder);
        }else{
            holder = (AprovDataHolder) row.getTag();
        }

        final Club club = getItem(position);
        AprovData aprov = club.getAprov();

        holder.iv_bdg.setImageResource(club.getBadgeResource(getContext()));
        holder.iv_bdg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), club.getToastDescription(v.getContext()), Toast.LENGTH_SHORT).show();
			}
		});
        
        holder.mand_j.setText(String.valueOf(aprov.getHomePlayed()));
        holder.mand_v.setText(String.valueOf(aprov.getHomeVictories()));
        holder.mand_e.setText(String.valueOf(aprov.getHomeDrawn()));
        holder.mand_perc.setText(String.valueOf(Math.round(aprov.getHomePerc())));
		
        holder.vis_j.setText(String.valueOf(aprov.getAwayPlayed()));
        holder.vis_v.setText(String.valueOf(aprov.getAwayVictories()));
        holder.vis_e.setText(String.valueOf(aprov.getAwayDrawn()));
        holder.vis_perc.setText(String.valueOf(Math.round(aprov.getAwayPerc())));

        return row;
	}
}

class AprovDataHolder {
	ImageView iv_bdg;
	TextView mand_j;
	TextView mand_v;
	TextView mand_e;
	TextView mand_perc;
	TextView vis_j;
	TextView vis_v;
	TextView vis_e;
	TextView vis_perc;
}