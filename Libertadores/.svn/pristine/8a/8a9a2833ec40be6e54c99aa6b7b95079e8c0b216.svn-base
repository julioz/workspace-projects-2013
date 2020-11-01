package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Match;

public class RoundOfSixteenSimulatorAdapter extends ArrayAdapter<Match> {
	
	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.roundofsixteensimulatorrow;
	private ArrayList<Match> objects;
	
	public RoundOfSixteenSimulatorAdapter(Context context, ArrayList<Match> matches) {
		super(context, LAYOUTRESOURCEID);
		this.mInflater = LayoutInflater.from(context);
		objects = matches;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	@Override
	public Match getItem(int position) {
		return objects.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RoundOfSixteenSimulatorHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new RoundOfSixteenSimulatorHolder();
            holder.tv_date = (TextView) row.findViewById(R.roundofsixteensimulatorrow.tv_date);
            holder.iv_home = (ImageView) row.findViewById(R.roundofsixteensimulatorrow.iv_home);
            holder.iv_away = (ImageView) row.findViewById(R.roundofsixteensimulatorrow.iv_away);
            holder.tv_score_home = (TextView) row.findViewById(R.roundofsixteensimulatorrow.tv_score_home);
            holder.tv_score_away = (TextView) row.findViewById(R.roundofsixteensimulatorrow.tv_score_away);
            holder.tv_home_position = (TextView) row.findViewById(R.roundofsixteensimulatorrow.tv_home_position);
            holder.tv_away_position = (TextView) row.findViewById(R.roundofsixteensimulatorrow.tv_away_position);
            
            row.setTag(holder);
        }else{
            holder = (RoundOfSixteenSimulatorHolder) row.getTag();
        }

        Match match = getItem(position);
        
        final Club home = match.getHome();
        final Club away = match.getAway();
        
        holder.tv_date.setText(getContext().getString(R.string.roundofsixteensimulatoractivity_match) +
        		" " + Integer.valueOf(position + 1));
        
        holder.tv_home_position.setText(getSpannedTextForClubData(position + 1, home));
        holder.tv_home_position.setTextColor(StandingsAdapter.COLOR_GREEN);
        
        holder.tv_away_position.setText(getSpannedTextForClubData(2*objects.size() - position, away));
        holder.tv_away_position.setTextColor(StandingsAdapter.COLOR_BLUE);
        
        holder.iv_home.setImageResource(home.getBadgeResource(getContext()));
        holder.iv_away.setImageResource(away.getBadgeResource(getContext()));
        holder.iv_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), home.getName(), Toast.LENGTH_SHORT).show();
			}
		});
        holder.iv_away.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), away.getName(), Toast.LENGTH_SHORT).show();
			}
		});
        holder.tv_score_home.setText(new String());
        holder.tv_score_away.setText(new String());
        
        return row;
	}
	
	private Spanned getSpannedTextForClubData(int position, Club club){
		return Html.fromHtml("<b>" + adjustNumberOfCharactersPosition(position)
		+ "</b><br />" + getContext().getString(R.string.standingsactivity_header_points) + ": " + club.getPoints());
	}
	
	private String adjustNumberOfCharactersPosition(int position){
		String s = String.valueOf(position);
		if(s.length() == 1) s = "0" + s;
		
		return s;
	}

}

class RoundOfSixteenSimulatorHolder {
	TextView tv_date;
	ImageView iv_home;
	ImageView iv_away;
	TextView tv_score_home;
	TextView tv_score_away;
	TextView tv_home_position;
	TextView tv_away_position;
}
