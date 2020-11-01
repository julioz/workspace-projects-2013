package br.com.zynger.vamosmarcar.view;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

public class AnimationHelper {

	private static final long FADE_DURATION = 500;
	
	public static Animation getFadeInAnimation() {
		Animation fadeIn = new AlphaAnimation(0, 1);
    	fadeIn.setInterpolator(new DecelerateInterpolator());
    	fadeIn.setDuration(FADE_DURATION);
    	return fadeIn;
	}
	
	public static Animation getFadeOutAnimation() {
		Animation fadeOut = new AlphaAnimation(1, 0);
    	fadeOut.setInterpolator(new AccelerateInterpolator());
    	fadeOut.setDuration(FADE_DURATION);
    	return fadeOut;
	}
}
