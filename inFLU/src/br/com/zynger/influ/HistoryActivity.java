package br.com.zynger.influ;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.HistoryRow;

public class HistoryActivity extends Activity {
	
	private ScrollView scrollView;
	private MediaPlayer mp;
	private Dialog dialog_mascots;
	private ImageButton ib_badge, ib_badges_openclose, ib_record1_openclose, ib_record2_openclose, ib_record3_openclose, ib_record4_openclose;
	private TextView tv_stadium, tv_mascot, tv_website;
	private TableRow tr_badges_txt, tr_record1_txt, tr_record2_txt, tr_record3_txt, tr_record4_txt;
	private TableLayout tl_badges_img, tl_record1_data, tl_record2_data, tl_record3_data, tl_record4_data;
	private OnClickListener ocl_record;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		loadViews();
		updateTheme();
		
		loadOnClickViews();

		loadRecordsGoals();
		loadRecordsPlayed();
		loadRecordsGoalkeepers();
		loadRecordsCouchs();
	}

	private void loadOnClickViews() {
		tv_website.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fluminense.com.br/site/futebol/")));
			}
		});
		
		View.OnClickListener badges_ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tl_badges_img.getVisibility() == View.GONE){
					animateIn(v.getContext(), tl_badges_img);
					ib_badges_openclose.setImageResource(R.drawable.ic_arrow_up);
				}else{
					animateOut(v.getContext(), tl_badges_img);
					ib_badges_openclose.setImageResource(R.drawable.ic_arrow_down);
				}
			}
		};
		
		tv_mascot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_mascots.show();
			}
		});
		
		tv_stadium.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), LaranjeirasStadiumActivity.class));
			}
		});
		
		tr_badges_txt.setOnClickListener(badges_ocl);
		ib_badges_openclose.setOnClickListener(badges_ocl);
		
		ib_badge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayerControls(v);
			}
		});
	
		ocl_record = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), IdolsActivity.class);
				it.putExtra("IDOL_NAME", ((HistoryRow) v).getName());
				v.getContext().startActivity(it);
			}
		};
		
		setTableOnClick(tl_record1_data, ib_record1_openclose, tr_record1_txt);
		setTableOnClick(tl_record2_data, ib_record2_openclose, tr_record2_txt);
		setTableOnClick(tl_record3_data, ib_record3_openclose, tr_record3_txt);
		setTableOnClick(tl_record4_data, ib_record4_openclose, tr_record4_txt);
	}
	
	private void setTableOnClick(final TableLayout tl, final ImageButton ib, TableRow tr){
		View.OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tl.getVisibility() == View.GONE){
					tl.setVisibility(View.VISIBLE);
					scrollView.postDelayed(new Runnable() {
				        @Override
				        public void run() {
				        	scrollView.smoothScrollBy(0, tl.getHeight());
				        }
				    }, 2);
					ib.setImageResource(R.drawable.ic_arrow_up);
				}else{
					tl.setVisibility(View.GONE);
					ib.setImageResource(R.drawable.ic_arrow_down);
				}
			}
		};
		
		tr.setOnClickListener(ocl);
		ib.setOnClickListener(ocl);
	}

	private void mediaPlayerControls(final View view){
		if(mp != null){
			if(!mp.isPlaying()){
				Toast.makeText(view.getContext(), "Toque novamente para parar", Toast.LENGTH_SHORT).show();
				mp = MediaPlayer.create(view.getContext(), R.raw.hino);
				mp.start();
			}else{
				mp.stop();
				mp.release();
				mp = null;
			}
		}else{
			Toast.makeText(view.getContext(), "Toque novamente para parar", Toast.LENGTH_SHORT).show();
			mp = MediaPlayer.create(view.getContext(), R.raw.hino);
			mp.start();
		}
		
		if(mp != null){        				
			mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaplayer) {
					mediaplayer.stop();
					mediaplayer.release();
					mp = null;
				}
			});
		}
	}
	
	private void animateIn(Context c, View v){
		v.setVisibility(View.VISIBLE);
		v.startAnimation(AnimationUtils.loadAnimation(c, R.anim.sunset_in));
		//v.setVisibility(View.GONE);
	}
	
	private void animateOut(Context c, View v){
		//v.setVisibility(View.VISIBLE);
		//v.startAnimation(AnimationUtils.loadAnimation(c, R.anim.sunset_out));
		v.setVisibility(View.GONE);
	}

	private void loadViews() {
		scrollView = (ScrollView) findViewById(R.history.scrollview);
		ib_badge = (ImageButton) findViewById(R.history.ib_badge);
		tv_stadium = (TextView) findViewById(R.history.tv_stadium);
		tv_mascot = (TextView) findViewById(R.history.tv_mascot);
		tv_website = (TextView) findViewById(R.history.tv_website);
		
		underlineText(tv_stadium);
		underlineText(tv_mascot);
		underlineText(tv_website);
		
		ib_badges_openclose = (ImageButton) findViewById(R.history.ib_badges_openclose);
		
		tr_badges_txt = (TableRow) findViewById(R.history.tr_badges_txt);
		tl_badges_img = (TableLayout) findViewById(R.history.tl_badges_img);
		
		dialog_mascots = new Dialog(this); 
		dialog_mascots.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_mascots.setContentView(R.layout.history_mascot);
		dialog_mascots.setCanceledOnTouchOutside(true);
		
		tr_record1_txt = (TableRow) findViewById(R.history.tr_record1_txt);
		tl_record1_data = (TableLayout) findViewById(R.history.tl_record1_data);
		ib_record1_openclose = (ImageButton) findViewById(R.history.ib_record1_openclose);
		
		tr_record2_txt = (TableRow) findViewById(R.history.tr_record2_txt);
		tl_record2_data = (TableLayout) findViewById(R.history.tl_record2_data);
		ib_record2_openclose = (ImageButton) findViewById(R.history.ib_record2_openclose);
		
		tr_record3_txt = (TableRow) findViewById(R.history.tr_record3_txt);
		tl_record3_data = (TableLayout) findViewById(R.history.tl_record3_data);
		ib_record3_openclose = (ImageButton) findViewById(R.history.ib_record3_openclose);
		
		tr_record4_txt = (TableRow) findViewById(R.history.tr_record4_txt);
		tl_record4_data = (TableLayout) findViewById(R.history.tl_record4_data);
		ib_record4_openclose = (ImageButton) findViewById(R.history.ib_record4_openclose);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		scrollView.setBackgroundColor(t.getAct_background());
		findViewById(R.history.ll_awards).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_awards2).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_badgeshistory1).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_badgeshistory2).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_clothes).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_clubinfo).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_coaches).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_escudo).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_goalkeepers).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_participations).setBackgroundColor(t.getContent_background());
		findViewById(R.history.ll_strikers).setBackgroundColor(t.getContent_background());
		findViewById(R.history.tl_record1_data).setBackgroundColor(t.getContent_background());
		findViewById(R.history.tl_record2_data).setBackgroundColor(t.getContent_background());
		findViewById(R.history.tl_record3_data).setBackgroundColor(t.getContent_background());
		findViewById(R.history.tl_record4_data).setBackgroundColor(t.getContent_background());
		scrollView.invalidate();
	}
	
	private void loadRecordsGoals(){
		tl_record1_data.addView(new HistoryRow(this, "Waldo", "403", "314", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Orlando Pingo de Ouro", "311", "188", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Telê", "556", "165", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Hércules", "176", "164", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Welfare", "166", "163", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Russo", "249", "150", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Preguinho", "174", "129", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Washington", "301", "124", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Ézio", "237", "119", ocl_record));
		tl_record1_data.addView(new HistoryRow(this, "Magno Alves", "265", "111", ocl_record));
		
		for (int i = 0; i < tl_record1_data.getChildCount(); i++) {
			if(tl_record1_data.getChildAt(i) instanceof TableRow){
				if(i%2 == 0) tl_record1_data.getChildAt(i).setBackgroundColor(0xff036851);
			}
		}
	}
	
	private void loadRecordsPlayed(){
		tl_record2_data.addView(new HistoryRow(this, "Castilho", "696", ocl_record));
		tl_record2_data.addView(new HistoryRow(this, "Pinheiro", "603", ocl_record));
		tl_record2_data.addView(new HistoryRow(this, "Telê", "556", ocl_record));
		tl_record2_data.addView(new HistoryRow(this, "Altair", "549", null));
		tl_record2_data.addView(new HistoryRow(this, "Escurinho", "490", null));
		tl_record2_data.addView(new HistoryRow(this, "Rubens Galaxe", "462", null));
		tl_record2_data.addView(new HistoryRow(this, "Denílson", "433", ocl_record));
		tl_record2_data.addView(new HistoryRow(this, "Assis", "424", null));
		tl_record2_data.addView(new HistoryRow(this, "Waldo", "403", ocl_record));
		tl_record2_data.addView(new HistoryRow(this, "Marcão", "397", ocl_record));
		
		for (int i = 0; i < tl_record2_data.getChildCount(); i++) {
			if(tl_record2_data.getChildAt(i) instanceof TableRow){
				if(i%2 == 0) tl_record2_data.getChildAt(i).setBackgroundColor(0xff036851);
			}
		}
	}
	
	private void loadRecordsGoalkeepers(){
		tl_record3_data.addView(new HistoryRow(this, "Castilho", "255", "696 / 777 / 1,116", ocl_record));
		tl_record3_data.addView(new HistoryRow(this, "Paulo Vítor", "179", "358 / 258 / 0,777", ocl_record));
		tl_record3_data.addView(new HistoryRow(this, "Félix", "136", "319 / 263 / 0,824", ocl_record));
		tl_record3_data.addView(new HistoryRow(this, "Ricardo Pinto", "103", "235 / 215 / 0,915", null));
		tl_record3_data.addView(new HistoryRow(this, "Jorge Vitório", "81", "182 / 160 / 0,879", null));
		tl_record3_data.addView(new HistoryRow(this, "Batatais", "79", "309 / 458 / 1,482", ocl_record));
		tl_record3_data.addView(new HistoryRow(this, "Paulo Goulart", "58", "136 / 132 / 0,971", null));
		tl_record3_data.addView(new HistoryRow(this, "Wendell", "57", "114 / 86 / 0,754", null));
		tl_record3_data.addView(new HistoryRow(this, "Renato", "57", "132 / 105 / 0,795", null));
		tl_record3_data.addView(new HistoryRow(this, "Wellerson", "52", "156 / 170 / 1,089", null));
		
		for (int i = 0; i < tl_record3_data.getChildCount(); i++) {
			if(tl_record3_data.getChildAt(i) instanceof TableRow){
				if(i%2 == 0) tl_record3_data.getChildAt(i).setBackgroundColor(0xff036851);
			}
		}
	}
	
	private void loadRecordsCouchs(){
		tl_record4_data.addView(new HistoryRow(this, "Zezé Moreira", "467", null));
		tl_record4_data.addView(new HistoryRow(this, "Ondino Vieira", "302", null));
		tl_record4_data.addView(new HistoryRow(this, "Renato Gaúcho", "186", ocl_record));
		tl_record4_data.addView(new HistoryRow(this, "Tim", "176", ocl_record));
		tl_record4_data.addView(new HistoryRow(this, "Nelsinho", "155", null));
		tl_record4_data.addView(new HistoryRow(this, "Carlos Alberto Parreira", "146", null));
		tl_record4_data.addView(new HistoryRow(this, "Silvio Pirillo", "138", null));
		tl_record4_data.addView(new HistoryRow(this, "Luiz Vinhaes", "137", null));
		tl_record4_data.addView(new HistoryRow(this, "Paulo Emílio", "126", null));
		tl_record4_data.addView(new HistoryRow(this, "Gentil Cardoso", "124", null));
		
		for (int i = 0; i < tl_record4_data.getChildCount(); i++) {
			if(tl_record4_data.getChildAt(i) instanceof TableRow){
				if(i%2 == 0) tl_record4_data.getChildAt(i).setBackgroundColor(0xff036851);
			}
		}
	}
	
	private void underlineText(TextView tv){
		SpannableString content = new SpannableString(tv.getText().toString());
	    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
	    tv.setText(content);
	}
}
