package br.com.zynger.influ;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.ViewFlinger;

public class FutureGameDialog extends Activity {
	
	private ViewFlinger flinger;
	private TextView tv_stadium1, tv_teams1, tv_time1, tv_date1, tv_stadium2, tv_teams2, tv_time2, tv_date2;
	
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.futuregame_dialog);
		
		loadViews();
		updateTheme();
		
		int type = getIntent().getIntExtra("TYPE", 1);
		
		String stadium1 = getIntent().getStringExtra("STADIUM1");
		String home1 = getIntent().getStringExtra("HOME1");
		String away1 = getIntent().getStringExtra("AWAY1");
		String score1 = getIntent().getStringExtra("SCORE1");
		int day1 = getIntent().getIntExtra("DAY1", -1);
		int month1 = getIntent().getIntExtra("MONTH1", 0);
		int year1 = getIntent().getIntExtra("YEAR1", 0);
		int hour1 = getIntent().getIntExtra("HOUR1", -1);
		int minute1 = getIntent().getIntExtra("MINUTE1", 0);
		
		String s_score = "<br />X<br />";
		if(!score1.equals("vs.")) s_score = "<br />" + score1 + "<br />";
        
        String s_day = String.valueOf(day1), s_month = String.valueOf(month1), s_hour = String.valueOf(hour1), s_min = String.valueOf(minute1);
        if(day1 < 10) s_day = "0" + s_day;
        if(month1 < 10) s_month = "0" + s_month;
        if(hour1 < 10) s_hour = "0" + s_hour;
        if(minute1 < 10) s_min = "0" + s_min;
        
        String s_time = s_hour + ":" + s_min;
        
        if(hour1 == 0) s_time = "Finalizado";
        if(hour1 == -1) s_time = "Indisponível";
        
        String s_date = s_day + "/" + s_month;
        if(day1 == -1) s_date = "Indisponível";
		
		
		if(stadium1 == null) tv_stadium1.setVisibility(View.GONE);
		else if(stadium1.equals("") || stadium1.equals("-")) tv_stadium1.setText("Local indefinido");
		else tv_stadium1.setText(stadium1);
		
		tv_teams1.setText(Html.fromHtml(home1 + s_score + away1));
		tv_date1.setText(s_date);
		tv_time1.setText(s_time);
		
		if(type == 2){
			String stadium2 = getIntent().getStringExtra("STADIUM2");
			String home2 = getIntent().getStringExtra("HOME2");
			String away2 = getIntent().getStringExtra("AWAY2");
			String score2 = getIntent().getStringExtra("SCORE2");
			int day2 = getIntent().getIntExtra("DAY2", -1);
			int month2 = getIntent().getIntExtra("MONTH2", 0);
			int year2 = getIntent().getIntExtra("YEAR2", 0);
			int hour2 = getIntent().getIntExtra("HOUR2", -1);
			int minute2 = getIntent().getIntExtra("MINUTE2", 0);
			
			String s_score2 = "<br />X<br />";
			if(!score2.equals("vs.")) s_score2 = "<br />" + score2 + "<br />";
	        
	        String s_day2 = String.valueOf(day2), s_month2 = String.valueOf(month2), s_hour2 = String.valueOf(hour2), s_min2 = String.valueOf(minute2);
	        if(day2 < 10) s_day2 = "0" + s_day2;
	        if(month2 < 10) s_month2 = "0" + s_month2;
	        if(hour2 < 10) s_hour2 = "0" + s_hour2;
	        if(minute2 < 10) s_min2 = "0" + s_min2;
	        
	        String s_time2 = s_hour2 + ":" + s_min2;
	        
	        if(hour2 == 0) s_time2 = "Finalizado";
	        if(hour2 == -1) s_time2 = "Indisponível";
	        
	        String s_date2 = s_day2 + "/" + s_month2;
	        if(day2 == -1) s_date2 = "Indisponível";
			
			
			if(stadium2 == null) tv_stadium2.setVisibility(View.GONE);
			else if(stadium2.equals("") || stadium1.equals("-")) tv_stadium2.setText("Local indefinido");
			else tv_stadium2.setText(stadium1);
			
			tv_teams2.setText(Html.fromHtml(home2 + s_score2 + away2));
			tv_date2.setText(s_date2);
			tv_time2.setText(s_time2);
		}else{
			flinger.removeViewAt(1);
		}
		
	}

	private void loadViews() {
		flinger = (ViewFlinger) findViewById(R.futuregame_dialog.flinger);
		
		tv_stadium1 = (TextView) findViewById(R.futuregame_dialog.tv_stadium1);
		tv_teams1 = (TextView) findViewById(R.futuregame_dialog.tv_teams1);
		tv_date1 = (TextView) findViewById(R.futuregame_dialog.tv_date1);
		tv_time1 = (TextView) findViewById(R.futuregame_dialog.tv_time1);
		
		tv_stadium2 = (TextView) findViewById(R.futuregame_dialog.tv_stadium2);
		tv_teams2 = (TextView) findViewById(R.futuregame_dialog.tv_teams2);
		tv_date2 = (TextView) findViewById(R.futuregame_dialog.tv_date2);
		tv_time2 = (TextView) findViewById(R.futuregame_dialog.tv_time2);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.futuregame_dialog.ll_actbg).setBackgroundColor(t.getAct_background());
		findViewById(R.futuregame_dialog.ll_actbg).invalidate();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    Rect dialogBounds = new Rect();
	    getWindow().getDecorView().getHitRect(dialogBounds);

	    if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY()) && ev.getAction() == MotionEvent.ACTION_DOWN){
	        // Tapped outside so we finish the activity
	        this.finish();
	    }
	    return super.dispatchTouchEvent(ev);
	}
}
