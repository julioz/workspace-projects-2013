package br.com.zynger.guesstheclub.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CoolveticaTextView extends TextView{

	public CoolveticaTextView(Context context) {
		super(context);
		init();
	}

	public CoolveticaTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public CoolveticaTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setTypeface(Typeface.createFromAsset(getContext().getAssets(), "coolvetica.ttf"));
	}

}
