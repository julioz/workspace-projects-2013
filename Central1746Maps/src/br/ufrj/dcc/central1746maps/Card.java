package br.ufrj.dcc.central1746maps;

import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.ufrj.dcc.central1746maps.Call.State;
import br.ufrj.dcc.central1746maps.Call.Type;

public class Card {
	private Call call;
	private View view;
	
	public Card(Context context, Call call) {
		this.call = call;
		
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.card, null);
		((TextView) view.findViewById(R.card.tv_address)).setText(getAddress());
		((TextView) view.findViewById(R.card.tv_type)).setText(getType().getTypeName());
		setState((LinearLayout) view.findViewById(R.card.ll_state), (TextView) view.findViewById(R.card.tv_state), getState());
	}
	
	private void setState(LinearLayout layout, TextView textView, State state) {
		int color = -1;
		int textColor = -1;
		
		if (state == State.ABERTO) {
			color = 0xffc0c0c0;
			textColor = 0xff7a7a7a;
		} else if (state == State.ATRASADO) {
			color = 0xffdfca59;
			textColor = 0xffa18311;
		} else if (state == State.FECHADO) {
			color = 0xff54de6a;
			textColor = 0xff10a212;
		} else return;
		
		layout.setBackgroundColor(color);
		textView.setText("Chamado " + state.toString().toLowerCase(Locale.getDefault()));
		textView.setTextColor(textColor);
	}

	public View getView() {
		return view;
	}
	
	public State getState() {
		return call.getState();
	}
	
	public Type getType() {
		return call.getType();
	}
	
	public String getAddress() {
		return call.getAddressPrefix() + " " + call.getAddress();
	}
}
