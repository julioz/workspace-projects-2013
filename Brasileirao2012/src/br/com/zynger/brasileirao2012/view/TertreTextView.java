package br.com.zynger.brasileirao2012.view;

import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;

public class TertreTextView extends TextView {
	
	private static final String FONT_SOURCE = "fonts/tertre-xbol.otf";

	public TertreTextView(Context context) {
		super(context);
		init();
	}

	public TertreTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public TertreTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		if(!isInEditMode()){
			// Your custom code that is not letting the Visual Editor draw properly
			// i.e. thread spawning or other things in the constructor
			setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_SOURCE), Typeface.BOLD);
			setTextColor(getResources().getColor(R.color.text_actionbar));
			setText(getText().toString().toUpperCase(Locale.getDefault()));
		}
	}
}
