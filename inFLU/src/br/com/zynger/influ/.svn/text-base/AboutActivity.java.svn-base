package br.com.zynger.influ;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

public class AboutActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		updateTheme();
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.about.ll_actback).setBackgroundColor(t.getAct_background());
		findViewById(R.about.sv_content1).setBackgroundColor(t.getContent_background());
		findViewById(R.about.sv_content2).setBackgroundColor(t.getContent_background());
		((TextView) findViewById(R.about.tv_name)).setTextColor(t.getAbout_text());
		findViewById(R.about.ll_actback).invalidate();
	}
}
