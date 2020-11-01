package br.com.zynger.brasileirao2012.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.util.Constants;

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
			setTypeface(Typeface.createFromAsset(getContext().getAssets(), Constants.FONT_HELVETICA), Typeface.NORMAL);
			setTextColor(getResources().getColor(R.color.text_actionbar));
		}
	}
}
