package br.com.zynger.libertadores;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutactivity);
		
		TextView tv_version = (TextView) findViewById(R.aboutactivity.tv_version);
		String version = SplashScreenActivity.getAppVersion(this);
		tv_version.setText(getString(R.string.aboutactivity_version) + " " + version);
	}
	
}
