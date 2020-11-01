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
import br.com.zynger.libertadores.model.MatchDetails.MatchPlayer;

public class MatchDetailsFormationAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.matchdetailsformationrow;
	private ArrayList<MatchPlayer> homeFormation, awayFormation;
	
	public MatchDetailsFormationAdapter(Context context, ArrayList<MatchPlayer> homeFormation,
			ArrayList<MatchPlayer> awayFormation) {
		this.mInflater = LayoutInflater.from(context);
		this.homeFormation = homeFormation;
		this.awayFormation = awayFormation;
	}
	
	@Override
	public int getCount() {
		return homeFormation.size();
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
		MatchDetailsFormationHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new MatchDetailsFormationHolder();
            holder.tv_name_home = (TextView) row.findViewById(R.matchdetailsformationrow.tv_name_home);
            holder.tv_name_away = (TextView) row.findViewById(R.matchdetailsformationrow.tv_name_away);
            holder.tv_number_home = (TextView) row.findViewById(R.matchdetailsformationrow.tv_number_home);
            holder.tv_number_away = (TextView) row.findViewById(R.matchdetailsformationrow.tv_number_away);
            holder.iv_card_home = (ImageView) row.findViewById(R.matchdetailsformationrow.iv_card_home);
            holder.iv_card_away = (ImageView) row.findViewById(R.matchdetailsformationrow.iv_card_away);
            
            row.setTag(holder);
        }else{
            holder = (MatchDetailsFormationHolder) row.getTag();
        }

        MatchPlayer mpHome = homeFormation.get(position);
        MatchPlayer mpAway = awayFormation.get(position);
        
        holder.tv_name_home.setText(mpHome.getName());
        holder.tv_name_away.setText(mpAway.getName());
        if(mpHome.getNumber() != null) holder.tv_number_home.setText(String.valueOf(mpHome.getNumber()));
        if(mpAway.getNumber() != null) holder.tv_number_away.setText(String.valueOf(mpAway.getNumber()));
        
        Integer cardHome = mpHome.getCard();
        Integer cardAway = mpAway.getCard();
        
        if(cardHome == MatchPlayer.CARD_YELLOW) holder.iv_card_home.setImageResource(R.drawable.img_squad_cards_yellow);
        else if (cardHome == MatchPlayer.CARD_RED) holder.iv_card_home.setImageResource(R.drawable.img_squad_cards_red);
        else holder.iv_card_home.setVisibility(View.INVISIBLE);
        
        if(cardAway == MatchPlayer.CARD_YELLOW) holder.iv_card_away.setImageResource(R.drawable.img_squad_cards_yellow);
        else if (cardAway == MatchPlayer.CARD_RED) holder.iv_card_away.setImageResource(R.drawable.img_squad_cards_red);
        else holder.iv_card_away.setVisibility(View.INVISIBLE);
        
        return row;
	}

}

class MatchDetailsFormationHolder {
	TextView tv_name_home, tv_name_away;
	TextView tv_number_home, tv_number_away;
	ImageView iv_card_home, iv_card_away;
}
