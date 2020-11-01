package br.com.zynger.brasileirao2012.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import br.com.zynger.brasileirao2012.R;

import com.actionbarsherlock.app.ActionBar;

public abstract class SelectiveActivity extends BaseActivity implements
		ActionBarSelectiveListener {

	private View selector;
	private ImageButton selectorContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionbar = getSupportActionBar();

		setSelectorView(actionbar);
		loadSelectorContent(actionbar);
		setSelectorClickListener();
		setSelectorImageResource(getSelectorImageResource());
	}

	private void setSelectorView(ActionBar actionbar) {
		selector = LayoutInflater.from(this).inflate(
				R.layout.actionbar_view_roundedimage, null);
		addActionBarRightView(selector);
	}

	private void loadSelectorContent(ActionBar actionbar) {
		selectorContent = (ImageButton) actionbar.getCustomView().findViewById(
				R.actionbar.view_roudedimage);
	}

	private void setSelectorClickListener() {
		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectorPressed(selector);
			}
		};

		selector.setOnClickListener(ocl);
		selectorContent.setOnClickListener(ocl);
	}

	protected void setSelectorImageResource(int resource) {
		if(resource != -1){			
			selectorContent.setImageResource(resource);
		}
	}

	protected void setSelectorImageDrawable(Drawable drawable) {
		selectorContent.setImageDrawable(drawable);
	}

	protected abstract int getSelectorImageResource();
}
