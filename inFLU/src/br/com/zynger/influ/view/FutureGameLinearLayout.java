package br.com.zynger.influ.view;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.influ.FutureGameDialog;
import br.com.zynger.influ.model.FutureGame;

public class FutureGameLinearLayout extends LinearLayout{

	private Context c;
	private FutureGame game1, game2;
	private TextView tv;
	
	public FutureGameLinearLayout(Context context, FutureGame f) {
		super(context);
		setGame1(f);
		init(context, f);
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(c, FutureGameDialog.class);
				it.putExtra("TYPE", 1);
				it.putExtra("STADIUM1", getGame1().getStadium());
				it.putExtra("HOME1", getGame1().getHome());
				it.putExtra("AWAY1", getGame1().getAway());
				it.putExtra("SCORE1", getGame1().getScore());
				if(getGame1().getDate().getYear() != 2010) it.putExtra("DAY1", getGame1().getDate().getDate());
				it.putExtra("MONTH1", getGame1().getDate().getMonth());
				it.putExtra("YEAR1", getGame1().getDate().getYear());
				if(getGame1().getDate().getMinutes() != 58) it.putExtra("HOUR1", getGame1().getDate().getHours());
				it.putExtra("MINUTE1", getGame1().getDate().getMinutes());
				c.startActivity(it);
			}
		});
	}
	
	public FutureGameLinearLayout(Context context, FutureGame f, FutureGame g) {
		super(context);
		setGame1(f);
		setGame2(g);
		init(context, f);
		this.setText(Html.fromHtml(f.getHome() + "<br />X<br />" + f.getAway()+ "<br />(I/V)"));
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(c, FutureGameDialog.class);
				it.putExtra("TYPE", 2);
				it.putExtra("STADIUM1", getGame1().getStadium());
				it.putExtra("HOME1", getGame1().getHome());
				it.putExtra("AWAY1", getGame1().getAway());
				it.putExtra("SCORE1", getGame1().getScore());
				if(getGame1().getDate().getYear() != 2010) it.putExtra("DAY1", getGame1().getDate().getDate());
				it.putExtra("MONTH1", getGame1().getDate().getMonth());
				it.putExtra("YEAR1", getGame1().getDate().getYear());
				if(getGame1().getDate().getMinutes() != 58) it.putExtra("HOUR1", getGame1().getDate().getHours());
				it.putExtra("MINUTE1", getGame1().getDate().getMinutes());
				
				it.putExtra("STADIUM2", getGame2().getStadium());
				it.putExtra("HOME2", getGame2().getHome());
				it.putExtra("AWAY2", getGame2().getAway());
				it.putExtra("SCORE2", getGame2().getScore());
				if(getGame2().getDate().getYear() != 2010) it.putExtra("DAY2", getGame2().getDate().getDate());
				it.putExtra("MONTH2", getGame2().getDate().getMonth());
				it.putExtra("YEAR2", getGame2().getDate().getYear());
				if(getGame2().getDate().getMinutes() != 58) it.putExtra("HOUR2", getGame2().getDate().getHours());
				it.putExtra("MINUTE2", getGame2().getDate().getMinutes());
				
				c.startActivity(it);
			}
		});
	}
	
	private void init(Context context, FutureGame f){
		c = context;
		this.setGravity(Gravity.CENTER);
		this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f));
		this.setOrientation(LinearLayout.VERTICAL);
		
		tv = new TextView(context);
		tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(0xFFFFFFFF);
		
		this.addView(tv);
		
		this.setText(Html.fromHtml(f.getHome() + "<br />X<br />" + f.getAway()));
		
		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent me) {
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
    				v.setBackgroundColor(0x44000000);
    			} else {
    				v.setBackgroundColor(0x00000000);
    			}
    			return false;
			}
		});
	}

	public void setText(CharSequence txt){
		tv.setText(txt);
	}

	public FutureGame getGame1() {
		return game1;
	}

	public void setGame1(FutureGame game1) {
		this.game1 = game1;
	}

	public FutureGame getGame2() {
		return game2;
	}

	public void setGame2(FutureGame game2) {
		this.game2 = game2;
	}

}
