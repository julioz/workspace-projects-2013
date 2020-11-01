package br.com.zynger.guesstheclub;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.guesstheclub.db.DatabaseHelper;
import br.com.zynger.guesstheclub.model.Club;
import br.com.zynger.guesstheclub.model.Phase;
import br.com.zynger.guesstheclub.model.User;
import br.com.zynger.guesstheclub.util.ImageTransformer;
import br.com.zynger.guesstheclub.util.ResourceManager;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

public class PhaseSelectorActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	
	private static final int MINLOGOSTOUNLOCK = 10;
	private static final int LOGOSPERPHASE = 20;
	
	private ViewFlow viewFlow;
	private TextView tv_actionbar_percent, tv_actionbar_numhints;
	
	private List<Phase> phases;
	private User user;
	private Dao<Phase, Integer> phaseDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phaseselector);
		
		loadViews();
		
		try {
			phaseDao = getHelper().getPhaseDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void loadViews() {
		viewFlow = (ViewFlow) findViewById(R.phaseselector.viewflow);
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.phaseselector.viewflowindic);
		indic.setFillColor(getResources().getColor(R.color.gray));
		indic.setStrokeColor(getResources().getColor(R.color.gray));
		viewFlow.setFlowIndicator(indic);
		
		tv_actionbar_percent = (TextView) findViewById(R.phaseselector.tv_actionbar_percent);
		tv_actionbar_numhints = (TextView) findViewById(R.phaseselector.tv_actionbar_numhints);
	}
	
	
	@Override
	protected void onResume() {
		try {
			phases = phaseDao.queryForAll();
			user = getHelper().getUserDao().queryForAll().get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		viewFlow.setAdapter(new PhaseSelectorAdapter(this, phases));
		
		int totalDiscovered = 0;
		for (Phase p : phases) {
			totalDiscovered += p.getDiscoveredLogos();
		}
		int percentagem = totalDiscovered*100/(phases.size()*20);
		tv_actionbar_percent.setText(percentagem + "% " + getString(R.string.tv_phaseselector_discovered).toLowerCase());
		tv_actionbar_numhints.setText(user.getRemainingTips() + " " + getString(R.string.tv_phaseselector_availabletips).toLowerCase());
		super.onResume();
	}



	public class PhaseSelectorAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context c;
		private List<Phase> phases;

		public PhaseSelectorAdapter(Context context, List<Phase> phases) {
			setContext(context);
			this.phases = phases;
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
		}

		@Override
		public int getCount() {
			return phases.size();
		}

		@Override
		public Object getItem(int position) {
			return phases.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.phaseadapteritem, null);
			
			LinearLayout ll_box = (LinearLayout) convertView.findViewById(R.phaseadapteritem.ll_box);
			TextView tv_tt = (TextView) convertView.findViewById(R.phaseadapteritem.tv_title);
			TextView tv_numFoundLogos = (TextView) convertView.findViewById(R.phaseadapteritem.tv_numFoundLogos);
			TextView tv_numTipsUsed = (TextView) convertView.findViewById(R.phaseadapteritem.tv_numTipsUsed);
			ImageView iv_locked = (ImageView) convertView.findViewById(R.phaseadapteritem.iv_locked);
			LinearLayout ll_stars = (LinearLayout) convertView.findViewById(R.phaseadapteritem.ll_stars);
			ImageView iv_guessed1 = (ImageView) convertView.findViewById(R.phaseadapteritem.iv_guessed1);
			ImageView iv_guessed2 = (ImageView) convertView.findViewById(R.phaseadapteritem.iv_guessed2);
			ImageView iv_guessed3 = (ImageView) convertView.findViewById(R.phaseadapteritem.iv_guessed3);
			ImageView iv_guessed4 = (ImageView) convertView.findViewById(R.phaseadapteritem.iv_guessed4);
			
			final Phase phase = phases.get(position);
			
			tv_tt.setText(phase.getTitle());
			tv_numFoundLogos.setText(String.valueOf(phase.getDiscoveredLogos()) + " " + getResources().getString(R.string.tv_phaseselector_discoveredlogos).toLowerCase());
			tv_numTipsUsed.setText(String.valueOf(phase.getTipsUsed()) + " " + getResources().getString(R.string.tv_phaseselector_tipsused).toLowerCase());
			
			boolean unlockedPhase = false;
			if(position > 0){
				Phase lastPhase = phases.get(position-1);
				if(lastPhase.getDiscoveredLogos() > MINLOGOSTOUNLOCK) unlockedPhase = true;
			}else unlockedPhase = true;
			
			if(unlockedPhase){
				iv_locked.setVisibility(View.GONE);
				ll_box.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent it = new Intent(v.getContext(), QuizActivity.class);
						it.putExtra(QuizActivity.INTENT_PHASE, phase.getId());
						startActivity(it);
					}
				});
				
				if(phase.getDiscoveredLogos() == LOGOSPERPHASE) ll_stars.setVisibility(View.VISIBLE);
			
				ImageView[] vet_iv = { iv_guessed1, iv_guessed2, iv_guessed3, iv_guessed4 };
				int i = 0;
				Random rnd = new Random();
				for (Iterator<Club> it = phase.getClubs().iterator(); it.hasNext();) {
					if(i == 4) break;
					Club club = (Club) it.next();
					if(club.isDiscovered()){
						float degrees = -45 + rnd.nextInt(90);
						setImageViewBadgeRotation(vet_iv[i], club.getBadge(), degrees);
						vet_iv[i].setVisibility(View.VISIBLE);
						i++;
					}
				}
			}else{
				iv_locked.setVisibility(View.VISIBLE);
				tv_numFoundLogos.setVisibility(View.GONE);
				tv_numTipsUsed.setVisibility(View.GONE);
				ll_box.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getContext(), getResources().getString(R.string.toast_phaseselector_phaselocked), Toast.LENGTH_SHORT).show();
					}
				});
			}
			return convertView;
		}
		
		private void setImageViewBadgeRotation(ImageView iv, String badge, float degrees){
			Drawable drawable = getResources().getDrawable(ResourceManager.getInstance().getResourceFromIdentifier(getContext(), ResourceManager.PATH_DRAWABLE, badge));
			BitmapDrawable brmdrb = ImageTransformer.rotate(drawable, degrees);
			iv.setImageDrawable(brmdrb);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public Context getContext() {
			return c;
		}

		public void setContext(Context c) {
			this.c = c;
		}

	}
}
