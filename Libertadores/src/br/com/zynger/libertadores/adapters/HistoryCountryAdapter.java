package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.HistoricCountry;

public class HistoryCountryAdapter extends ArrayAdapter<HistoricCountry> {

	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.history_countryrow;
	private ArrayList<HistoricCountry> objects;
	
	public HistoryCountryAdapter(Context context, ArrayList<HistoricCountry> objects) {
		super(context, LAYOUTRESOURCEID);
		this.mInflater = LayoutInflater.from(context);
		this.objects = objects;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	@Override
	public HistoricCountry getItem(int position) {
		return objects.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		HistoryCountryRowHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new HistoryCountryRowHolder();
            holder.ivFlag = (ImageView) row.findViewById(R.historycountryrow.iv_flag);
            holder.tvName = (TextView) row.findViewById(R.historycountryrow.tv_name);
            holder.tvWon = (TextView) row.findViewById(R.historycountryrow.tv_winner);
            holder.tvRunnerUp = (TextView) row.findViewById(R.historycountryrow.tv_runnerup);
            
            row.setTag(holder);
        }else{
            holder = (HistoryCountryRowHolder) row.getTag();
        }

        final HistoricCountry country = getItem(position);
        holder.ivFlag.setImageResource(country.getFlagResource(getContext()));
        holder.ivFlag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), country.getAcronym(), Toast.LENGTH_SHORT).show();
			}
		});
        holder.tvName.setText(country.getName());
        holder.tvWon.setText(String.valueOf(country.getWon()));
        holder.tvRunnerUp.setText(String.valueOf(country.getRunnerUp()));

        return row;
	}
	
}

class HistoryCountryRowHolder {
	ImageView ivFlag;
	TextView tvName;
    TextView tvWon;
    TextView tvRunnerUp;
}
