package br.com.zynger.guesstheclub.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.zynger.guesstheclub.AnswerActivity;
import br.com.zynger.guesstheclub.QuizActivity;
import br.com.zynger.guesstheclub.R;
import br.com.zynger.guesstheclub.model.Club;
import br.com.zynger.guesstheclub.util.ColorFilterGenerator;
import br.com.zynger.guesstheclub.util.ResourceManager;

public class BadgeLayout extends LinearLayout {
	private Club club;
	private int numPhase, numClub_Phase;
	
	private ImageView badge, correct;
	private TextView title, subtitle, numberHints;
	private LinearLayout ll_hint;
	
	public BadgeLayout(Context context, int numPhase, int numClub_Phase, Club club) {
		super(context);
		setClub(club);
		
		this.numPhase = numPhase;
		this.numClub_Phase = numClub_Phase;
		
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.badgelayout, this);
		
		loadViews();
		setData();
	}

	private void loadViews() {
		badge = (ImageView) findViewById(R.badgelayout.iv_badge);
		title = (TextView) findViewById(R.badgelayout.tv_title);
		subtitle = (TextView) findViewById(R.badgelayout.tv_subtitle);
		numberHints = (TextView) findViewById(R.badgelayout.tv_numberhints);
		correct = (ImageView) findViewById(R.badgelayout.iv_correct);
		ll_hint = (LinearLayout) findViewById(R.badgelayout.ll_hint);
	}
	
	private void setData() {
		badge.setImageResource(ResourceManager.getInstance().getResourceFromIdentifier(getContext(), ResourceManager.PATH_DRAWABLE, club.getBadge()));
		
		if(club.isDiscovered()) title.setText(club.getMainName());
		else title.setText("???");
		//subtitle.setText(Html.fromHtml("Descoberto: <b>" + club.isDiscovered() + "</b>")); //TODO
		subtitle.setVisibility(View.GONE); //TODO
		
		if(getClub().isDiscovered()) showCorrect();
		
		int nHints = getClub().getTipsUsed();
		if(nHints > 0){		
			String str_nHints;
			if(nHints == 1) str_nHints = String.valueOf(nHints) + " " + getResources().getString(R.string.str_tip).toLowerCase();
			else str_nHints = String.valueOf(nHints) + " " + getResources().getString(R.string.str_tips).toLowerCase();
			numberHints.setText(str_nHints);
		}else{
			ll_hint.setVisibility(View.GONE);
		}
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(v.getContext(), AnswerActivity.class);
				it.putExtra(AnswerActivity.INTENT_CLUB, club.getId());
				it.putExtra(AnswerActivity.INTENT_NUMPHASE, numPhase);
				it.putExtra(AnswerActivity.INTENT_NUMCLUB, numClub_Phase + 1);
				((Activity) v.getContext()).startActivityForResult(it, QuizActivity.REQUEST_CODE_ANSWER);
			}
		});
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public void showCorrect(){		
		correct.setVisibility(View.VISIBLE);
		badge.setColorFilter(ColorFilterGenerator.adjustSaturation(0));
	}
	
	public void hideCorrect(){
		correct.setVisibility(View.GONE);
		badge.setColorFilter(null);
	}
}