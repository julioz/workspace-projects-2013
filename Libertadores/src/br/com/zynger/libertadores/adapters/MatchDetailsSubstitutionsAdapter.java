package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.MatchDetails.MatchSubstitution;

public class MatchDetailsSubstitutionsAdapter extends BaseAdapter {

	private Context context;
	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.matchdetailssubstitutionrow;
	private ArrayList<MatchSubstitution> substitutions;
	private int homeResource, awayResource;
	
	public MatchDetailsSubstitutionsAdapter(Context context, ArrayList<MatchSubstitution> substitutions,
			int homeResource, int awayResource) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.substitutions = substitutions;
		this.homeResource = homeResource;
		this.awayResource = awayResource;
	}
	
	@Override
	public int getCount() {
		return substitutions.size();
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
		MatchDetailsSubstitutionsHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new MatchDetailsSubstitutionsHolder();
            holder.iv_badge = (ImageView) row.findViewById(R.matchdetailssubstitutionsrow.iv_badge);
            holder.tv_time = (TextView) row.findViewById(R.matchdetailssubstitutionsrow.tv_time);
            holder.tv_half = (TextView) row.findViewById(R.matchdetailssubstitutionsrow.tv_half);
            holder.tv_name_in = (TextView) row.findViewById(R.matchdetailssubstitutionsrow.tv_name_in);
            holder.tv_name_out = (TextView) row.findViewById(R.matchdetailssubstitutionsrow.tv_name_out);
            
            row.setTag(holder);
        }else{
            holder = (MatchDetailsSubstitutionsHolder) row.getTag();
        }
        
        MatchSubstitution subst = substitutions.get(position);
        holder.tv_name_in.setText(subst.getNameIn());
        holder.tv_name_out.setText(subst.getNameOut());
        holder.tv_time.setText(subst.getTime());
        String half = subst.getHalf() == MatchSubstitution.HALF_FIRST ?
        		context.getString(R.string.matchdetailsactivity_firsthalf) :
        		context.getString(R.string.matchdetailsactivity_secondhalf);
        holder.tv_half.setText(half);
        holder.iv_badge.setImageResource((subst.getSide() == MatchSubstitution.SIDE_HOME ? homeResource : awayResource));
        
        return row;
	}

}

class MatchDetailsSubstitutionsHolder {
	ImageView iv_badge;
	TextView tv_time, tv_half, tv_name_in, tv_name_out;
}