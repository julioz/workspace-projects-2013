package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Move;

public class MoveToMoveAdapter extends ArrayAdapter<Move> {

	private static final int LAYOUTRESOURCEID = R.layout.movetomoverow;
	
	private LayoutInflater layoutInflater;
	private ArrayList<Move> moves;
	
	public MoveToMoveAdapter(Context context, ArrayList<Move> moves) {
		super(context, LAYOUTRESOURCEID, moves);
		this.moves = moves;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return moves.size();
	}
	
	public ArrayList<Move> getMoves() {
		return moves;
	}
	
	public ArrayList<Move> getMovesWithVideos(){
		ArrayList<Move> al_moves = new ArrayList<Move>();
		for (Move move : getMoves()) {
			if(move.getVideoUrl() != null) al_moves.add(move);
		}
		return al_moves;
	}

	@Override
	public Move getItem(int position) {
		return getMoves().get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MoveToMoveHolder holder = null;
        
        if(row == null){
            row = layoutInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new MoveToMoveHolder();
            holder.tv_text = (TextView) row.findViewById(R.movetomoverow.tv_text);
            holder.tv_moment = (TextView) row.findViewById(R.movetomoverow.tv_moment);
            holder.tv_time = (TextView) row.findViewById(R.movetomoverow.tv_time);
            holder.ll_type = (LinearLayout) row.findViewById(R.movetomoverow.ll_type);
            holder.iv_type = (ImageView) row.findViewById(R.movetomoverow.iv_type);
            
            row.setTag(holder);
        }else{
            holder = (MoveToMoveHolder) row.getTag();
        }
		
        Move move = (Move) getItem(position);
        
        holder.tv_text.setText(Html.fromHtml(move.getTexto()));
		
		String momento = move.getMomento();
		if(!momento.equals("")){
			holder.tv_moment.setText(momento + "'");
			holder.tv_moment.setVisibility(View.VISIBLE);
		}else holder.tv_moment.setVisibility(View.GONE);
		
		setType(holder.ll_type, holder.iv_type, move.getTipo(), move.getCartao());
		
		holder.tv_time.setText(move.getPeriodo());
		
		final String video = move.getVideoUrl();
		if(video != null){
			//holder.tv_text.setTextColor(0xFF7171CF);
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video));
					getContext().startActivity(browserIntent);
				}
			});
		}else{
			final Spanned text = Html.fromHtml(move.getTexto());
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(), text, Toast.LENGTH_LONG).show();
				}
			});
		}
		
		return row;
	}

	private void setType(LinearLayout ll_type, ImageView iv_type,
			int tipo, int cartao) {
		ll_type.setVisibility(View.VISIBLE);
		
		switch (tipo) {
		case Move.TYPE_MOVE:
			ll_type.setVisibility(View.GONE);
			break;
		case Move.TYPE_IMPORTANTMOVE:
			iv_type.setImageResource(R.drawable.img_squad_important);
			break;
		case Move.TYPE_GOAL:
			iv_type.setImageResource(R.drawable.img_squad_goal_pro);
			break;
		case Move.TYPE_CARD:
			switch (cartao) {
			case Move.CARD_YELLOW:
				iv_type.setImageResource(R.drawable.img_squad_cards_yellow);
				break;
			case Move.CARD_RED:
				iv_type.setImageResource(R.drawable.img_squad_cards_red);
				break;
			default:
				ll_type.setVisibility(View.GONE);
				break;
			}
			break;
		case Move.TYPE_SUBSTITUTION:
			iv_type.setImageResource(R.drawable.img_squad_subst);
			break;
		default:
			break;
		}
	}
}

class MoveToMoveHolder {
	TextView tv_text;
	TextView tv_moment;
	TextView tv_time;
	LinearLayout ll_type;
	ImageView iv_type;
}
