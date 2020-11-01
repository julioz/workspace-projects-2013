package br.com.zynger.libertadores.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import br.com.zynger.libertadores.R;

public class UpdateImageView extends ImageView {
	
	private RotateAnimation rot;
	
	public UpdateImageView(Context context) {
		super(context);
		init();
	}
	
	public UpdateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public UpdateImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		setImageResource(R.drawable.ic_update);
		rot = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rot.setInterpolator(new LinearInterpolator());
		rot.setDuration(900L);
		rot.setStartTime(200L);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		RotateOnClickListener ocl = new RotateOnClickListener(this, l);
		super.setOnClickListener(ocl);
	}
	
	private void rotateView(){
		startAnimation(rot);
	}
	
	private class RotateOnClickListener implements OnClickListener {

		private OnClickListener clickListener;
		private UpdateImageView updateImageView;

		public RotateOnClickListener(UpdateImageView updateImageView, OnClickListener l) {
			this.updateImageView = updateImageView;
			this.clickListener = l;
		}

		@Override
		public void onClick(View v) {
			updateImageView.rotateView();
			clickListener.onClick(v);
		}
		
	}
}