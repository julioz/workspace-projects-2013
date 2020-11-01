package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.HomeActivity;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.MatchDetails.MatchFact;

public class MatchDetailsGoalsAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.matchdetailsgoalsrow;
	private ArrayList<MatchFact> homeGoals, awayGoals;
	private int homeResource, awayResource;
	
	public MatchDetailsGoalsAdapter(Context context, ArrayList<MatchFact> homeGoals,
			ArrayList<MatchFact> awayGoals, int homeResource, int awayResource) {
		this.mInflater = LayoutInflater.from(context);
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
		this.homeResource = homeResource;
		this.awayResource = awayResource;
	}
	
	@Override
	public int getCount() {
		int higherCount = homeGoals.size();
		return (higherCount > awayGoals.size() ? higherCount : awayGoals.size());
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MatchDetailsGoalsHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new MatchDetailsGoalsHolder();
            holder.tv_name_home = (TextView) row.findViewById(R.matchdetailsgoalsrow.tv_name_home);
            holder.tv_name_away = (TextView) row.findViewById(R.matchdetailsgoalsrow.tv_name_away);
            holder.tv_time_home = (TextView) row.findViewById(R.matchdetailsgoalsrow.tv_time_home);
            holder.tv_time_away = (TextView) row.findViewById(R.matchdetailsgoalsrow.tv_time_away);
            holder.iv_badge_home = (ImageView) row.findViewById(R.matchdetailsgoalsrow.iv_badge_home);
            holder.iv_badge_away = (ImageView) row.findViewById(R.matchdetailsgoalsrow.iv_badge_away);
            holder.ll_home = (LinearLayout) row.findViewById(R.matchdetailsgoalsrow.ll_home);
            holder.rl_away = (RelativeLayout) row.findViewById(R.matchdetailsgoalsrow.rl_away);
            
            row.setTag(holder);
        }else{
            holder = (MatchDetailsGoalsHolder) row.getTag();
        }
        
        try{        	
        	MatchFact mfHome = homeGoals.get(position);
        	holder.tv_name_home.setText(mfHome.getName());
        	holder.tv_time_home.setText(getAdjustedTimeBasedOnHalf(mfHome));
        	holder.iv_badge_home.setImageResource(homeResource);
        }catch(IndexOutOfBoundsException e){
        	holder.ll_home.setVisibility(View.INVISIBLE);
        }
        
        try{        	
        	MatchFact mfAway = awayGoals.get(position);
        	holder.tv_name_away.setText(mfAway.getName());
        	holder.tv_time_away.setText(getAdjustedTimeBasedOnHalf(mfAway));
        	holder.iv_badge_away.setImageResource(awayResource);
        }catch(IndexOutOfBoundsException e){
        	holder.rl_away.setVisibility(View.INVISIBLE);
        }
        
        return row;
	}
	
	private String getAdjustedTimeBasedOnHalf(MatchFact fact) {
		String time = fact.getTime();
		try{			
			Integer minutes = Integer.valueOf(time.trim().substring(0, time.indexOf("'")));
			int plusTime = fact.getHalf() == MatchFact.HALF_SECOND ? 45 : 0;
			minutes += plusTime;
			time = String.valueOf(minutes) + "'";
		}catch(Exception e){
			Log.e(HomeActivity.TAG, e.toString());
			e.printStackTrace();
		}
    	return time;
	}

}

class MatchDetailsGoalsHolder {
	TextView tv_name_home, tv_name_away;
	TextView tv_time_home, tv_time_away;
	ImageView iv_badge_home, iv_badge_away;
	LinearLayout ll_home;
	RelativeLayout rl_away;
}
