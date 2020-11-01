package br.com.zynger.brasileirao2012.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.Striker;

public class StrikersListAdapter extends ArrayAdapter<Striker>{
	private final static int LAYOUT_RESOURCE = R.layout.striker_row;
	
	private boolean ascending;
	private final LayoutInflater mInflater;
	private ArrayList<Striker> strikers;
	
	public StrikersListAdapter(Context context, ArrayList<Striker> strikers) {
		super(context, 0, strikers);
		this.mInflater = LayoutInflater.from(context);
		this.strikers = strikers;
		this.ascending = true;
	}
	
	@Override
	public int getCount() {
		return strikers.size();
	}
	
	public void order(Comparator<Striker> comparator){
		Collections.sort(strikers, comparator);
		ascending = true;
		notifyDataSetChanged();
	}
	
	public boolean isAscending() {
		return ascending;
	}
	
	public void reverse() {
		Collections.reverse(strikers);
		ascending = !ascending;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		StrikerHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUT_RESOURCE, parent, false);
            
            holder = new StrikerHolder();
            holder.badge = (ImageView) row.findViewById(R.strikerrow.badge);
            holder.name = (TextView) row.findViewById(R.strikerrow.name);
            holder.goals = (TextView) row.findViewById(R.strikerrow.goals);
            holder.team = (TextView) row.findViewById(R.strikerrow.team);
            
            row.setTag(holder);
        }else{
            holder = (StrikerHolder) row.getTag();
        }

        Striker striker = getItem(position);

        holder.badge.setImageResource(striker.getClub().getBadgeResource(getContext())); //TODO pode vir nulo para jogadores sem time
        holder.name.setText(striker.getName());
        holder.goals.setText(String.valueOf(striker.getGoals()));
        holder.team.setText(striker.getClub().getName());

        return row;
	}
}

class StrikerHolder {
	ImageView badge;
	TextView name;
	TextView goals;
	TextView team;
}