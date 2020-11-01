package br.com.zynger.influ;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.zynger.influ.model.StatPlayer;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

public class PlayerInformationActivity extends Activity {
	
	private ImageView iv;
	private TextView tvName, tvDesc, tvStatsGoals, tvStatsPlayed, tvStatsYellow, tvStatsRed;
	private LinearLayout llStats, ll_advStats;
	private ScrollView sv_desc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playerinformation);
		
		loadViews();
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		updateTheme(t);
		
		Intent it = this.getIntent();
		String name = it.getStringExtra("NAME").toUpperCase();
		String completeName = it.getStringExtra("COMPLETENAME");
		String arrival = it.getStringExtra("ARRIVAL");
		String born = it.getStringExtra("BORN");
		String hw = it.getStringExtra("HW");
		String lastclub = it.getStringExtra("LASTCLUB");
		String naturality = it.getStringExtra("NATURALITY");
		String number = it.getStringExtra("NUMBER");
		String position = it.getStringExtra("POSITION");
		String description = it.getStringExtra("DESCRIPTION");
		Bitmap image = (Bitmap) this.getIntent().getParcelableExtra("IMAGE");
		
		iv.setImageBitmap(image);
		tvName.setText(name);
		if(description != null){
			sv_desc.setVisibility(View.VISIBLE);
			tvDesc.setText(Html.fromHtml(description));
		}else sv_desc.setVisibility(View.GONE);
		
		llStats.addView(getTextedLinearLayout(t, "Nome:", completeName));
		llStats.addView(getTextedLinearLayout(t, "Número:", number));
		llStats.addView(getTextedLinearLayout(t, "Posição:", position));
		llStats.addView(getTextedLinearLayout(t, "Naturalidade:", naturality));
		llStats.addView(getTextedLinearLayout(t, "Nascido em:", born));
		llStats.addView(getTextedLinearLayout(t, "Altura/Peso:", hw));
		llStats.addView(getTextedLinearLayout(t, "Chegada ao time:", arrival));
		llStats.addView(getTextedLinearLayout(t, "Último clube:", lastclub));
		
		loadAdvStats(name);
	}

	private void loadViews() {
		iv = (ImageView) findViewById(R.playerinformation.image);
		tvName = (TextView) findViewById(R.playerinformation.name);
		sv_desc = (ScrollView) findViewById(R.playerinformation.sv_description);
		tvDesc = (TextView) findViewById(R.playerinformation.description);
		llStats = (LinearLayout) findViewById(R.playerinformation.llStats);
		tvStatsGoals = (TextView) findViewById(R.playerinformation.stats_goals);
		tvStatsPlayed = (TextView) findViewById(R.playerinformation.stats_played);
		tvStatsYellow = (TextView) findViewById(R.playerinformation.stats_yellow);
		tvStatsRed = (TextView) findViewById(R.playerinformation.stats_red);
		ll_advStats = (LinearLayout) findViewById(R.playerinformation.ll_advstats);
	}
	
	private void updateTheme(Theme t) {		
		findViewById(R.playerinformation.ll_actbg).setBackgroundColor(t.getAct_background());
		tvName.setTextColor(t.getSec_text());
		
		findViewById(R.playerinformation.ll_name).setBackgroundColor(t.getContent_background());
		findViewById(R.playerinformation.sv_stats).setBackgroundColor(t.getContent_background());
		findViewById(R.playerinformation.ll_advstats).setBackgroundColor(t.getContent_background());
		findViewById(R.playerinformation.ll_description).setBackgroundColor(t.getContent_background());
		findViewById(R.playerinformation.ll_actbg).invalidate();
	}

	@SuppressWarnings("unchecked")
	private void loadAdvStats(String name) {
		ArrayList<StatPlayer> al = (ArrayList<StatPlayer>) FileHandler.openBackup(this, StatisticsActivity.ARQUIVO);
		if(al != null){
			StatPlayer pl = null;
			for (StatPlayer sp : al) {
				if(sp.getName().regionMatches(true, 0, name, 0, name.length())){
					pl = sp;
					break;
				}
			}
			if(pl != null){
				ll_advStats.setVisibility(View.VISIBLE);
				tvStatsPlayed.setText("Jogos: " + pl.getPlayed());
				tvStatsGoals.setText("Gols: " + pl.getGoals());
				tvStatsYellow.setText("Amarelos: " + pl.getyCards());
				tvStatsRed.setText("Vermelhos: " + pl.getrCards());
			}
			else ll_advStats.setVisibility(View.GONE);
		}
		else ll_advStats.setVisibility(View.GONE);
	}

	private LinearLayout getTextedLinearLayout(Theme t, String tt, String value){
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		ll.setGravity(Gravity.LEFT);
		
			TextView tv = new TextView(this);
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv.setTextColor(t.getSec_text());
			tv.setPadding(tv.getPaddingLeft()+3, tv.getPaddingTop(), tv.getPaddingRight()+3, tv.getPaddingBottom());
			tv.setTextSize(12);
			tv.setText(tt);
			
			TextView vl = new TextView(this);
			vl.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			vl.setTextColor(Color.parseColor("#FFFFFF"));
			vl.setPadding(tv.getPaddingLeft()+3, tv.getPaddingTop(), tv.getPaddingRight()+3, tv.getPaddingBottom());
			vl.setTextSize(12);
			vl.setText(value);
		
		ll.addView(tv);
		ll.addView(vl);
		
		return ll;
	}
}
