package br.com.zynger.guesstheclub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {
	
	private Button bt_play;
	//private CoolveticaTextView tv_play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		loadViews();
		
		//tv_play.setText(getResources().getString(R.string.tv_home_play).toUpperCase());
		
		bt_play.setText(getResources().getString(R.string.tv_home_play));
		bt_play.setTypeface(Typeface.createFromAsset(getAssets(), "coolvetica.ttf"));
		bt_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), PhaseSelectorActivity.class));
			}
		});
	}

	private void loadViews() {
		bt_play = (Button) findViewById(R.home.bt_play);
		//tv_play = (CoolveticaTextView) findViewById(R.home.tv_play);
	}
}
