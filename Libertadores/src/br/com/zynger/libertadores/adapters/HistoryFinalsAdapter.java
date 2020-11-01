package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.HistoricFinal;
import br.com.zynger.libertadores.model.HistoricMatch;
import br.com.zynger.libertadores.view.ExpandAnimation;

public class HistoryFinalsAdapter extends ArrayAdapter<HistoricFinal> implements OnClickListener {

	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.history_finalsrow;
	private ArrayList<HistoricFinal> objects;
	
	public HistoryFinalsAdapter(Context context, ArrayList<HistoricFinal> objects) {
		super(context, LAYOUTRESOURCEID);
		this.mInflater = LayoutInflater.from(context);
		this.objects = objects;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	@Override
	public HistoricFinal getItem(int position) {
		return objects.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        HistoryFinalRowHolder holder = null;
        
        if(row == null){
            row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);
            
            holder = new HistoryFinalRowHolder();
            holder.tvYear = (TextView) row.findViewById(R.historyfinalsrow.tv_year);
            holder.ivWinner = (ImageView) row.findViewById(R.historyfinalsrow.iv_winner);
            holder.ivRunnerUp = (ImageView) row.findViewById(R.historyfinalsrow.iv_runnerup);
            holder.tvScore1 = (TextView) row.findViewById(R.historyfinalsrow.tv_score1);
            holder.tvStadium1 = (TextView) row.findViewById(R.historyfinalsrow.tv_venue1);
            holder.tvScore2 = (TextView) row.findViewById(R.historyfinalsrow.tv_score2);
            holder.tvStadium2 = (TextView) row.findViewById(R.historyfinalsrow.tv_venue2);
            holder.tvPenalties = (TextView) row.findViewById(R.historyfinalsrow.tv_penalties);
            
            holder.toolbar = (LinearLayout) row.findViewById(R.historyfinalsrow.toolbar);
            
            row.setTag(holder);
        }else{
            holder = (HistoryFinalRowHolder) row.getTag();
        }
        
        row.setClickable(true);
        row.setFocusable(true);
        row.setOnClickListener(this);
        
        if(View.VISIBLE == holder.toolbar.getVisibility()) showDetailsToolbar(row);

        HistoricFinal hFinal = getItem(position);
        holder.tvYear.setText(String.valueOf(hFinal.getYear()));
        holder.ivWinner.setImageResource(hFinal.getFirstMatch().getHome().getBadgeResource(getContext()));
        holder.ivRunnerUp.setImageResource(hFinal.getFirstMatch().getAway().getBadgeResource(getContext()));
        setClickOnBadge(holder.ivWinner, hFinal.getWinner());
        setClickOnBadge(holder.ivRunnerUp, hFinal.getRunnerup());
        
        holder.tvScore1.setText(String.valueOf(hFinal.getFirstMatch().getScoreHome()) + " x " + String.valueOf(hFinal.getFirstMatch().getScoreAway()));
        holder.tvStadium1.setText(hFinal.getFirstMatch().getStadium());
        holder.tvScore2.setText(String.valueOf(hFinal.getSecondMatch().getScoreHome()) + " x " + String.valueOf(hFinal.getSecondMatch().getScoreAway()));
        holder.tvStadium2.setText(hFinal.getSecondMatch().getStadium());
        
        HistoricMatch penalties = hFinal.getPenalties();
        if(null != penalties){
        	holder.tvPenalties.setText(getContext().getString(R.string.historyfinalsactivity_penaltyshootout) + ": "
        			+ String.valueOf(hFinal.getPenalties().getScoreHome()) + " x " + String.valueOf(hFinal.getPenalties().getScoreAway()));
        	holder.tvPenalties.setVisibility(View.VISIBLE);
        }else holder.tvPenalties.setVisibility(View.GONE);

        return row;
	}
	
	private void setClickOnBadge(ImageView iv, final String name){
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void showDetailsToolbar(View parent) {
		View toolbar = parent.findViewById(R.historyfinalsrow.toolbar);

        // Criando a animacao de expansao para o item
        ExpandAnimation expandAni = new ExpandAnimation(toolbar, 0);
        
        // Iniciar a animacao
        toolbar.startAnimation(expandAni);
	}
	
	private void showDetailsToolbar(View parent, int duration) {
		View toolbar = parent.findViewById(R.historyfinalsrow.toolbar);

        // Criando a animacao de expansao para o item
        ExpandAnimation expandAni = new ExpandAnimation(toolbar, duration);
        
        // Iniciar a animacao
        toolbar.startAnimation(expandAni);
	}

	@Override
	public void onClick(View v) {
		showDetailsToolbar(v, 350);
	}
}

class HistoryFinalRowHolder {
	TextView tvYear;
	ImageView ivWinner;
    ImageView ivRunnerUp;
    TextView tvScore1;
    TextView tvStadium1;
    TextView tvScore2;
    TextView tvStadium2;
    TextView tvPenalties;
    
    LinearLayout toolbar;
}