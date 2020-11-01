package br.com.zynger.libertadores.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.R;

public class DataUpdateLayout extends RelativeLayout {
	
	private RelativeLayout rl_content;
	private LogoProgressBar lpb_updating;
	private TextView tv_text;
	private ArrayList<View> viewsToToggle;
	
	public DataUpdateLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater  mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.dataupdatelayout, this, true);
        
        viewsToToggle = new ArrayList<View>();
        rl_content = (RelativeLayout) findViewById(R.dataupdatelayout.rl_content);
        tv_text = (TextView) findViewById(R.dataupdatelayout.tv_text);
        lpb_updating = (LogoProgressBar) findViewById(R.dataupdatelayout.lpb_updating);
        
        hide();
	}
	
	public void hide() {
		setVisibility(View.GONE);
		rl_content.setVisibility(View.GONE);
		for (View v : getViewsToToggle()) {
			v.setVisibility(View.VISIBLE);
		}
	}
	
	public void show() {
		setVisibility(View.VISIBLE);
		rl_content.setVisibility(View.VISIBLE);
		for (View v : getViewsToToggle()) {
			v.setVisibility(View.GONE);
		}
	}
	
	public void setOnlyText(String text) {
		lpb_updating.setVisibility(View.GONE);
		setText(text);
		tv_text.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT));
		tv_text.setGravity(Gravity.CENTER);
	}
	
	public void setOnlyText(int resourceId){
		setOnlyText(getContext().getString(resourceId));
	}
	
	public void showFullLayout() {
		lpb_updating.setVisibility(View.VISIBLE);
		setText(getContext().getString(R.string.updating));
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		p.addRule(RelativeLayout.BELOW, R.dataupdatelayout.lpb_updating);
		p.addRule(RelativeLayout.CENTER_HORIZONTAL);
		tv_text.setLayoutParams(p);		
		
		show();
	}
	
	public void setText(String text) {
		tv_text.setText(text);
	}
	
	public void addViewToToggle(View v) {
		viewsToToggle.add(v);
	}
	
	private ArrayList<View> getViewsToToggle() {
		return viewsToToggle;
	}
}
