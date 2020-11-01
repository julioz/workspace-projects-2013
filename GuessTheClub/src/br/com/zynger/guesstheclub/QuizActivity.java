package br.com.zynger.guesstheclub;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.zynger.guesstheclub.db.DatabaseHelper;
import br.com.zynger.guesstheclub.model.Club;
import br.com.zynger.guesstheclub.model.Phase;
import br.com.zynger.guesstheclub.view.BadgeLayout;
import br.com.zynger.guesstheclub.view.CoolveticaTextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

public class QuizActivity extends OrmLiteBaseListActivity<DatabaseHelper> {
    
	@SuppressWarnings("unused")
	private final static String TAG = "quiz";
	public final static int REQUEST_CODE_ANSWER = 1;
	public final static String INTENT_PHASE = "phase";
	
	private Phase phase;
	private LinkedHashMap<String, Club> clubs;
	
	private TextView tv_actionbar_title;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        
        clubs = new LinkedHashMap<String, Club>();
        int phaseId = getIntent().getIntExtra(INTENT_PHASE, -1);
        
        loadViews();
        
        try {
			phase = getHelper().getPhaseDao().queryForId(phaseId);
			tv_actionbar_title.setText(getResources().getString(R.string.tv_phaseselector_level) + " " + phase.getConstantPhase());
			
			for (Club c : phase.getClubs()) {
				clubs.put(c.getMainName(), c);
			}
	     
	        this.setListAdapter(new QuizListAdapter(this, clubs));
		} catch (SQLException e) {
			e.printStackTrace();
			//Se deu algum problema no SQL, inclusive phaseId não existir no banco ou não conseguir acessar o banco.
		}
    }
		
	private void loadViews() {
		tv_actionbar_title = (CoolveticaTextView) findViewById(R.quiz.tv_actionbar_title);	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			Integer clubId = (Integer) data.getIntExtra(AnswerActivity.INTENT_CLUB, -1);
			Club club;
			try {
				club = getHelper().getClubDao().queryForId(clubId);
				if(clubs.get(club.getMainName()) != null){
					QuizListAdapter qla = (QuizListAdapter) getListAdapter();
					int position = qla.getClubPositionByName(club.getMainName());
					setListAdapter(null);
					qla.updateClub(position, club);
					setListAdapter(qla);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private class QuizListAdapter extends BaseAdapter {
		private Context context;
		private TreeMap<String, BadgeLayout> cache;
		private LinkedHashMap<String, Club> clubs;
		
		public QuizListAdapter(Context context, LinkedHashMap<String, Club> clubs) {
			this.context = context;
			this.clubs = clubs;
			cache = new TreeMap<String, BadgeLayout>();
		}
		
		@Override
		public int getCount() {
			return clubs.size();
		}
		
		public void updateClub(int position, Club c){
			cache.put(c.getMainName(), new BadgeLayout(context, phase.getConstantPhase(), position, c));
			clubs.put(c.getMainName(), c);
		}

		@Override
		public Object getItem(int position) {
			int i = 0;
			for (Iterator<Club> it = clubs.values().iterator(); it.hasNext();) {
				Club club = (Club) it.next();
				if(position == i) return club;
				else i++;
			}
			return null;
		}
		
		public int getClubPositionByName(String name){
			for (int i = 0; i < clubs.size(); i++) {
				if(((Club) getItem(i)).getMainName().equals(name)) return i;
			}
			return -1;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Club club = (Club) getItem(position);
			BadgeLayout bdglt = cache.get(club.getMainName());
			if(bdglt == null){
				bdglt = new BadgeLayout(context, phase.getConstantPhase(), position, club);
				cache.put(club.getMainName(), bdglt);
			}
			return bdglt;
		}
	}
}