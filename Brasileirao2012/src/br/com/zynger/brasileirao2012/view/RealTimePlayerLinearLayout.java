package br.com.zynger.brasileirao2012.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.model.RealTimePlayer;

public class RealTimePlayerLinearLayout extends LinearLayout {
	
	private RealTimePlayer realTimePlayer;
	private ImageView subst, cards;
	private TextView name, goalsPro, goalsAgainst;
	private FrameLayout fl_pro, fl_against;

	public RealTimePlayerLinearLayout(Context context, RealTimePlayer rtp) {
		super(context);
		setRealTimePlayer(rtp);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.realtimeplayerlayout, this);
		
		loadViews();
		
		name.setText(getRealTimePlayer().getName());
		switch (getRealTimePlayer().getType()) {
		case RealTimePlayer.TIPO_TITULAR:
			name.setTypeface(null, Typeface.BOLD);
			break;
		case RealTimePlayer.TIPO_SUBSTITUICAO:
			if(getRealTimePlayer().getSubst_type() == RealTimePlayer.SUBST_ENTROU){
				name.setTypeface(null, Typeface.BOLD);
				subst.setImageResource(R.drawable.img_squad_subst_in);
			}
			else if(getRealTimePlayer().getSubst_type() == RealTimePlayer.SUBST_SAIU){
				name.setTypeface(null, Typeface.ITALIC);
				subst.setImageResource(R.drawable.img_squad_subst_out);
			}
			break;
		case RealTimePlayer.TIPO_RESERVA:
			name.setTextColor(Color.GRAY);
			break;
		case RealTimePlayer.TIPO_EXPULSO:
			subst.setImageResource(R.drawable.img_squad_subst_redcard);
			name.setTextColor(Color.RED);
			break;
		default:
			break;
		}
		
		int card = getRealTimePlayer().getCard_type();
		if(card == RealTimePlayer.CARTAO_AMAR) cards.setImageResource(R.drawable.img_squad_cards_yellow);
		else if(card == RealTimePlayer.CARTAO_VERM) cards.setImageResource(R.drawable.img_squad_cards_red);
		
		int gp = getRealTimePlayer().getGoalspro();
		int ga = getRealTimePlayer().getGoalsagainst();
		if(gp != RealTimePlayer.NULL) setGoalsPro(gp);
		if(ga != RealTimePlayer.NULL) setGoalsAgainst(ga);
	}

	private void loadViews() {
		name = (TextView) findViewById(R.rtplayout.tv_name);
		subst = (ImageView) findViewById(R.rtplayout.iv_subst);
		cards = (ImageView) findViewById(R.rtplayout.iv_cards);
		goalsPro = (TextView) findViewById(R.rtplayout.tv_gpro);
		goalsAgainst = (TextView) findViewById(R.rtplayout.tv_gagainst);
		
		fl_pro = (FrameLayout) findViewById(R.rtplayout.fl_gpro);
		fl_against = (FrameLayout) findViewById(R.rtplayout.fl_gagainst);
	}
	
	private void setGoalsPro(int num){
		fl_pro.setVisibility(View.VISIBLE);
		goalsPro.setText(String.valueOf(num));
	}
	
	private void setGoalsAgainst(int num){
		fl_against.setVisibility(View.VISIBLE);
		goalsAgainst.setText(String.valueOf(num));
	}

	public RealTimePlayer getRealTimePlayer() {
		return realTimePlayer;
	}

	public void setRealTimePlayer(RealTimePlayer realTimePlayer) {
		this.realTimePlayer = realTimePlayer;
	}

}
