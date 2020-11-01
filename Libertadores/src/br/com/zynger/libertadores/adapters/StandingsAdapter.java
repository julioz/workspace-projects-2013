package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.util.ClubComparatorByPoints;

public class StandingsAdapter extends ArrayAdapter<Club> {

	public final static int COLOR_GREEN = 0xFF088A08;
	public final static int COLOR_BLUE = 0xFF045FB4;

	private final LayoutInflater mInflater;
	private static final int LAYOUTRESOURCEID = R.layout.standingsrow;
	private static final Comparator<Club> pointsComparator = new ClubComparatorByPoints();
	private ArrayList<Club> objects;

	public StandingsAdapter(Context context, ArrayList<Club> objects) {
		super(context, LAYOUTRESOURCEID, objects);
		Collections.sort(objects, pointsComparator);
		this.mInflater = LayoutInflater.from(context);
		this.objects = objects;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Club getItem(int position) {
		return objects.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		StandingsHolder holder = null;

		if(row == null){
			row = mInflater.inflate(LAYOUTRESOURCEID, parent, false);

			holder = new StandingsHolder();
			holder.tvPosition = (TextView) row.findViewById(R.standingsrow.tv_position);
			holder.ivBadge = (ImageView) row.findViewById(R.standingsrow.iv_badge);
			holder.tvPoints = (TextView) row.findViewById(R.standingsrow.tv_points);
			holder.tvPlayed = (TextView) row.findViewById(R.standingsrow.tv_played);
			holder.tvVictories = (TextView) row.findViewById(R.standingsrow.tv_victories);
			holder.tvDraws = (TextView) row.findViewById(R.standingsrow.tv_draws);
			holder.tvLosses = (TextView) row.findViewById(R.standingsrow.tv_losses);
			holder.tvGoalsFor = (TextView) row.findViewById(R.standingsrow.tv_goalsfor);
			holder.tvGoalsAgainst = (TextView) row.findViewById(R.standingsrow.tv_goalsagainst);
			holder.tvBalance = (TextView) row.findViewById(R.standingsrow.tv_balance);

			row.setTag(holder);
		}else{
			holder = (StandingsHolder) row.getTag();
			holder.tvPosition.setTextColor(getContext().getResources().getColor(android.R.color.black));
		}

		Club club = getItem(position);

		String strPosition = String.valueOf(position+1);
		strPosition = strPosition.length() < 2 ? "0" + strPosition : strPosition;

		if(club.getGroupPosition() != null){        	
			if(club.getGroupPosition() == 1) holder.tvPosition.setTextColor(COLOR_GREEN);
			else if(club.getGroupPosition() == 2) holder.tvPosition.setTextColor(COLOR_BLUE);
		}else holder.tvPosition.setTextColor(getContext().getResources().getColor(android.R.color.black));

		holder.tvPosition.setText(strPosition);
		int badgeRes = club.getBadgeResource(getContext());
		holder.ivBadge.setImageResource(badgeRes);
		final String clubName = club.getName();
		holder.ivBadge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), clubName, Toast.LENGTH_SHORT).show();
			}
		});
		holder.tvPoints.setText(String.valueOf(club.getPoints()));
		holder.tvPlayed.setText(String.valueOf(club.getGamesPlayed()));
		holder.tvVictories.setText(String.valueOf(club.getVictories()));
		holder.tvDraws.setText(String.valueOf(club.getDraws()));
		holder.tvLosses.setText(String.valueOf(club.getLosses()));
		holder.tvGoalsFor.setText(String.valueOf(club.getGoalsPro()));
		holder.tvGoalsAgainst.setText(String.valueOf(club.getGoalsAgainst()));
		holder.tvBalance.setText(String.valueOf(club.getBalance()));

		return row;
	}

}

class StandingsHolder {
	TextView tvPosition;
	ImageView ivBadge;
	TextView tvPoints;
	TextView tvPlayed;
	TextView tvVictories;
	TextView tvDraws;
	TextView tvLosses;
	TextView tvGoalsFor;
	TextView tvGoalsAgainst;
	TextView tvBalance;
}