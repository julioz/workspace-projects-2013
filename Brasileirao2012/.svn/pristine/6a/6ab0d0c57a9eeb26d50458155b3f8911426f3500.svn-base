package br.com.zynger.brasileirao2012.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.util.AnimationHelper;
import br.com.zynger.brasileirao2012.util.ImageManager;

public class DataUpdateLayout extends RelativeLayout {
	private ScrollView svContent;
	private ProgressBar pbUpdating;
	private ImageView ivImage;
	private Button btButton;
	private TextView tvText;
	private ArrayList<View> viewsToToggle;
	private Animation fadeIn;
	private Animation fadeOut;
	
	public DataUpdateLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater  mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.dataupdatelayout, this, true);
        
        if(!isInEditMode()){        	
        	viewsToToggle = new ArrayList<View>();
        	svContent = (ScrollView) findViewById(R.dataupdatelayout.sv_content);
        	pbUpdating = (ProgressBar) findViewById(R.dataupdatelayout.pb_updating);
        	ivImage = (ImageView) findViewById(R.dataupdatelayout.iv_image);
        	btButton = (Button) findViewById(R.dataupdatelayout.bt_button);
        	tvText = (TextView) findViewById(R.dataupdatelayout.tv_text);
        	
        	fadeIn = AnimationHelper.getFadeInAnimation();
        	fadeOut = AnimationHelper.getFadeOutAnimation();
        	
        	hide();
        }
	}
	
	public void hide() {
		startAnimation(fadeOut);
		setVisibility(View.GONE);
		svContent.setVisibility(View.GONE);
		for (View v : getViewsToToggle()) {
			v.startAnimation(fadeIn);
			v.setVisibility(View.VISIBLE);
		}
	}
	
	public void show() {
		pbUpdating.setVisibility(View.VISIBLE);
		ivImage.setVisibility(View.GONE);

		startAnimation(fadeIn);
		setVisibility(View.VISIBLE);
		svContent.setVisibility(View.VISIBLE);
		for (View v : getViewsToToggle()) {
			v.startAnimation(fadeOut);
			v.setVisibility(View.GONE);
		}
	}
	
	public void setMessage(String message) {
		tvText.setText(message);
	}
	
	public void setMessage(int textResource) {
		tvText.setText(getContext().getString(textResource));
	}

	private void setImageResource(int resource) {
		try{
			ivImage.setImageResource(resource);
		}catch(OutOfMemoryError error){			
			ivImage.setImageBitmap(ImageManager.decodeResource(getContext(), resource));
		}
		ivImage.setVisibility(View.VISIBLE);
		pbUpdating.setVisibility(View.GONE);
	}
	
	private void setButton(String buttonText, Drawable leftDrawable,
			OnClickListener onClickListener) {
		btButton.setText(buttonText);
		btButton.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
		btButton.setOnClickListener(onClickListener);
		btButton.setVisibility(View.VISIBLE);
	}
	
	public void setClickToUpdateMessage(String message) {
		setImageResource(R.drawable.img_error_fan);
		setMessage(message);
	}
	
	public void setEmptyDataSetMessage(String message) {
		setImageResource(R.drawable.img_error_offside);
		setMessage(message);
	}
	
	public void setErrorMessage(String message) {
		setImageResource(R.drawable.img_error_goalkeeper);
		setMessage(message);
	}
	
	public void setButtonMessage(String message, String buttonText,
			Drawable leftDrawable, OnClickListener onClickListener) {
		setImageResource(R.drawable.img_error_fan);
		setButton(buttonText, leftDrawable, onClickListener);
		setMessage(message);
	}
	
	public void addViewToToggle(View v) {
		viewsToToggle.add(v);
	}
	
	private ArrayList<View> getViewsToToggle() {
		return viewsToToggle;
	}
}
