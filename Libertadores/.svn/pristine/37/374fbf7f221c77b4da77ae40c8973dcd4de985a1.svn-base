package br.com.zynger.libertadores.view;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.R;

public class SwipeHintLayout extends LinearLayout {

	public final static int GESTURE_SWIPE = R.drawable.img_swipe;
	
	private LinearLayout ll_content;
	private ImageView iv_gesture;
	private TextView tv_text;
	private View viewToShowWhenHidden;
	
	private String prefKey;
	
	public SwipeHintLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater  mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.swipehintlayout, this, true);
        
        ll_content = (LinearLayout) findViewById(R.swipehintlayout.ll_content);
        tv_text = (TextView) findViewById(R.swipehintlayout.tv_text);
        iv_gesture = (ImageView) findViewById(R.swipehintlayout.iv_gesture);
        
        ll_content.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						PreferenceManager.getDefaultSharedPreferences(
								v.getContext()).edit().putBoolean(getPrefKey(), false).commit();
						hide();
						return true;
					default:
						break;
				}
				return false;
			}
		});
	}
	
	public void hide() {
		setVisibility(View.GONE);
		ll_content.setVisibility(View.GONE);
		if(getViewToShowWhenHidden() != null){			
			getViewToShowWhenHidden().setVisibility(View.VISIBLE);
		}
	}
	
	public void setText(String text) {
		tv_text.setText(text);
	}
	
	public void setImageForGesture(int gesture) {
		iv_gesture.setImageResource(gesture);
	}
	
	public void setViewToShowWhenHidden(View viewToShowWhenHidden, boolean hideNow) {
		this.viewToShowWhenHidden = viewToShowWhenHidden;
		if(hideNow) this.viewToShowWhenHidden.setVisibility(View.GONE);
	}
	
	public View getViewToShowWhenHidden() {
		return viewToShowWhenHidden;
	}
	
	public String getPrefKey() {
		return prefKey;
	}
	
	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
	}
}
