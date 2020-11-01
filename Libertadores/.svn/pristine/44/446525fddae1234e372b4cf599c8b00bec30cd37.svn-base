package br.com.zynger.libertadores.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class HelveticaTextView extends TextView {
	
	public HelveticaTextView(Context context) {
		super(context);
		init();
	}

	public HelveticaTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public HelveticaTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		if(!isInEditMode()){
			// Your custom code that is not letting the Visual Editor draw properly
			// i.e. thread spawning or other things in the constructor
			setTypeface(Typeface.createFromAsset(getContext().getAssets(), "helveticacdhv.ttf"));
		}
	}
}
