package br.com.zynger.guesstheclub;

import java.sql.SQLException;
import java.util.Iterator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.guesstheclub.db.DatabaseHelper;
import br.com.zynger.guesstheclub.model.Club;
import br.com.zynger.guesstheclub.model.Name;
import br.com.zynger.guesstheclub.model.Phase;
import br.com.zynger.guesstheclub.model.StatusEnum;
import br.com.zynger.guesstheclub.model.Tip;
import br.com.zynger.guesstheclub.model.User;
import br.com.zynger.guesstheclub.util.ResourceManager;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class AnswerActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	
	public static final String INTENT_CLUB = "CLUB";
	public static final String INTENT_NUMPHASE = "NUMPHASE";
	public static final String INTENT_NUMCLUB = "NUMCLUB";
	
	private Intent intentReturn;
	
	private ImageView iv_badge;
	private EditText et_answer;
	private ImageButton bt_confirm, bt_clear, bt_tip;
	private TextView tv_numphasebadge;
	private LinearLayout ll_tips;
	private ScrollView sv_tips;
	
	private Club ansClub;
	private User user;
	private int ansBadge, numPhase, numClub;
	private String ansName;
	private Phase phase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer);
		
		intentReturn = new Intent();
				
		numPhase = getIntent().getIntExtra(INTENT_NUMPHASE, -1);
		numClub = getIntent().getIntExtra(INTENT_NUMCLUB, -1);
		
		Integer clubId = (Integer) getIntent().getIntExtra(AnswerActivity.INTENT_CLUB, -1);
		try {
			ansClub = getHelper().getClubDao().queryForId(clubId);
			phase = getHelper().getPhaseDao().queryForId(ansClub.getPhase().getId());
			user = getHelper().getUserDao().queryForAll().get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ansBadge = ResourceManager.getInstance().getResourceFromIdentifier(this, ResourceManager.PATH_DRAWABLE, ansClub.getBadge());
		ansName = ansClub.getMainName();
		
		loadViews();
		setData();
		setListeners();
	}

	private void loadViews() {
		iv_badge = (ImageView) findViewById(R.answer.iv_badge);
		et_answer = (EditText) findViewById(R.answer.et_answer);
		bt_confirm = (ImageButton) findViewById(R.answer.bt_confirm);
		bt_clear = (ImageButton) findViewById(R.answer.bt_clear);
		bt_tip = (ImageButton) findViewById(R.answer.bt_tip);
		tv_numphasebadge = (TextView) findViewById(R.answer.tv_numphasebadge);
		ll_tips = (LinearLayout) findViewById(R.answer.ll_tips);
		sv_tips = (ScrollView) findViewById(R.answer.sv_tips);
	}
	
	private void setData(){
		tv_numphasebadge.setText(getString(R.string.phase) + " " + numPhase + ", " + getString(R.string.badge) + " " + numClub);
		
		iv_badge.setImageResource(ansBadge);
		
		if(ansClub.isDiscovered()) et_answer.setText(ansName);
		
		for (Tip tip : ansClub.getOldTips()) {
			ll_tips.addView(getTipTextView(tip.getText()));
		}
		if(ll_tips.getChildCount() > 0) sv_tips.setVisibility(View.VISIBLE);
	}
	
	private TextView getTipTextView(String text){
		TextView tv = new TextView(this);
		tv.setText("· " + text);
		tv.setTextColor(0xFF474747);
		return tv;
	}
	
	private void setListeners(){
		bt_tip.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				if (user.getRemainingTips() > 0) {
					Tip tip = ansClub.getNextTip();
					if (tip != null) {
						ll_tips.addView(getTipTextView(tip.getText()));
						sv_tips.setVisibility(View.VISIBLE);
						user.setRemainingTips(user.getRemainingTips() - 1);
						phase.setTipsUsed(phase.getTipsUsed() + 1);					
						
						try {
							getHelper().getUserDao().update(user);
							getHelper().getClubDao().update(ansClub);
							getHelper().getPhaseDao().update(phase);
							
							setResult(RESULT_OK, intentReturn);
							intentReturn.putExtra(INTENT_CLUB, ansClub.getId());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else{
						Toast.makeText(v.getContext(), getString(R.string.toast_answer_clubwithnomoretips), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(v.getContext(), getString(R.string.toast_answer_userwithnomoretips), Toast.LENGTH_SHORT).show();
				}
			}
		});
				
		bt_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String answer = et_answer.getText().toString();
				
				boolean correctAnswer = testAnswer(answer, ansName);
				
				for (Name name : ansClub.getNames()) {
					correctAnswer = correctAnswer || testAnswer(answer, name.getText());
				}
				
				if(correctAnswer){
					ansClub.setStatus(StatusEnum.RIGHT);
					phase.setDiscoveredLogos(phase.getDiscoveredLogos() + 1);
					try {
						getHelper().getClubDao().update(ansClub);
						getHelper().getPhaseDao().update(phase);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					intentReturn.putExtra(INTENT_CLUB, ansClub.getId());
					setResult(RESULT_OK, intentReturn);
					finish();
				}else{
					//TODO tirar isso né =p
					for (Iterator<Name> it = ansClub.getNames().iterator(); it
							.hasNext();) {
						Name altname = (Name) it.next();
						Log.e("quiz", "altname="+altname.getText());
					}
					//Toast.makeText(v.getContext(), "Errado - Certo eh " + ansName, Toast.LENGTH_SHORT).show();
					Log.e("quiz", "Errado - Certo eh " + ansName);
				}
			}
		});
		
		bt_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et_answer.setText("");
			}
		});
		
		et_answer.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// Se o evento for um toque na tecla "enter"
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          bt_confirm.performClick();
		          return true;
		        }
		        return false;
			}
		});
	}
	
	private boolean testAnswer(String answer, String correct){
		return answer.trim().toLowerCase().equals(correct.trim().toLowerCase());
	}
}
