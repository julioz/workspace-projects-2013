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
import br.com.zynger.libertadores.model.MatchDetails.MatchFact;

public class MatchDetailsCardsAdapter extends BaseAdapter {

	private Context context;
	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.matchdetailscardsrow;
	private ArrayList<MatchFact> cards;
	private ArrayList<MatchFact> yellows, reds;
	private int homeResource, awayResource;
	
	public MatchDetailsCardsAdapter(Context context, ArrayList<MatchFact> yellowCards,
			ArrayList<MatchFact> redCards, int homeResource, int awayResource) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.cards = new ArrayList<MatchFact>();
		this.yellows = yellowCards;
		this.reds = redCards;
		cards.addAll(this.yellows);
		cards.addAll(this.reds);
		this.homeResource = homeResource;
		this.awayResource = awayResource;
	}
	
	@Override
	public int getCount() {
		return cards.size();
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
		MatchDetailsCardsHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new MatchDetailsCardsHolder();
            holder.tv_name = (TextView) row.findViewById(R.matchdetailscardsrow.tv_name);
            holder.tv_time = (TextView) row.findViewById(R.matchdetailscardsrow.tv_time);
            holder.tv_half = (TextView) row.findViewById(R.matchdetailscardsrow.tv_half);
            holder.iv_badge = (ImageView) row.findViewById(R.matchdetailscardsrow.iv_badge);
            holder.iv_card = (ImageView) row.findViewById(R.matchdetailscardsrow.iv_card);
            
            row.setTag(holder);
        }else{
            holder = (MatchDetailsCardsHolder) row.getTag();
        }
        
        MatchFact card = cards.get(position);
        holder.tv_name.setText(card.getName());
        holder.tv_time.setText(card.getTime());
        String half = card.getHalf() == MatchFact.HALF_FIRST ?
        		context.getString(R.string.matchdetailsactivity_firsthalf) :
        		context.getString(R.string.matchdetailsactivity_secondhalf);
        holder.tv_half.setText(half);
        holder.iv_badge.setImageResource((card.getSide() == MatchFact.SIDE_HOME ? homeResource : awayResource));
        holder.iv_card.setImageResource((reds.contains(card) ?
        		R.drawable.img_squad_cards_red : R.drawable.img_squad_cards_yellow));
        
        return row;
	}

}

class MatchDetailsCardsHolder {
	ImageView iv_card;
	TextView tv_time, tv_name, tv_half;
	ImageView iv_badge;
}