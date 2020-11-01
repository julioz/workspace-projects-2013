package br.com.zynger.libertadores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.libertadores.view.HelveticaTextView;

public class HistoryActivity extends Activity {
	
	private HelveticaTextView tv_title;
	
	private RelativeLayout rl_winnersandrunnerups, rl_finals, rl_bycountry;
	private TextView tv_winnersandrunnerups, tv_finals, tv_bycountry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historyactivity);
		
		loadViews();
		
		rl_winnersandrunnerups.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDataActivity(HistoryDataActivity.INTENT_TYPE_WINNERSANDRUNNERUPS,
						getString(R.string.homedashboard_history));
			}
		});
		
		rl_finals.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDataActivity(HistoryDataActivity.INTENT_TYPE_FINALS,
						getString(R.string.historydata_finals));
			}
		});
		
		rl_bycountry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDataActivity(HistoryDataActivity.INTENT_TYPE_BYCOUNTRY,
						getString(R.string.historydata_bycountry));
			}
		});
	}

	private void loadViews() {
		tv_title = (HelveticaTextView) findViewById(R.historyactivity.titlebar_tvtitle);
		tv_title.setText(getString(R.string.homedashboard_history).toUpperCase());
		
		rl_winnersandrunnerups = (RelativeLayout) findViewById(R.historyactivity.rl_winnersandrunnerups);
		rl_finals = (RelativeLayout) findViewById(R.historyactivity.rl_finals);
		rl_bycountry = (RelativeLayout) findViewById(R.historyactivity.rl_bycountry);
		
		tv_winnersandrunnerups = (TextView) findViewById(R.historyactivity.tv_winnersandrunnerups);
		tv_finals = (TextView) findViewById(R.historyactivity.tv_finals);
		tv_bycountry = (TextView) findViewById(R.historyactivity.tv_bycountry);
		
		tv_winnersandrunnerups.setText(tv_winnersandrunnerups.getText().toString().toUpperCase());
		tv_finals.setText(tv_finals.getText().toString().toUpperCase());
		tv_bycountry.setText(tv_bycountry.getText().toString().toUpperCase());		
	}

	private void startDataActivity(int actType, String actTitle) {
		Intent it = new Intent(this, HistoryDataActivity.class);
		it.putExtra(HistoryDataActivity.INTENT_ACTIVITYTYPE, actType);
		it.putExtra(HistoryDataActivity.INTENT_ACTIVITYTITLE, actTitle);
		startActivity(it);
	}
}