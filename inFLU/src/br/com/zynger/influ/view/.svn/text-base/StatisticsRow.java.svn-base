package br.com.zynger.influ.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.zynger.influ.R;
import br.com.zynger.influ.model.StatPlayer;

public class StatisticsRow extends TableRow{

	private Context context;
	private TableRow tablerow;
	private StatPlayer player;
	private TextView name, played, goals, yellow, red;
	
	public StatisticsRow(Context c, StatPlayer player) {
		super(c);
		this.context = c;
		this.player = player;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.tablerow = (TableRow) inflater.inflate(R.layout.statistics_row, null);
		
		this.name = (TextView) tablerow.findViewById(R.statisticsrow.name);
		this.played = (TextView) tablerow.findViewById(R.statisticsrow.played);
		this.goals = (TextView) tablerow.findViewById(R.statisticsrow.goals);
		this.yellow = (TextView) tablerow.findViewById(R.statisticsrow.yellow);
		this.red = (TextView) tablerow.findViewById(R.statisticsrow.red);
		
		this.name.setText(this.player.getName());
		this.played.setText(String.valueOf(this.player.getPlayed()));
		this.goals.setText(String.valueOf(this.player.getGoals()));
		this.yellow.setText(String.valueOf(this.player.getyCards()));
		this.red.setText(String.valueOf(this.player.getrCards()));
	}

	public TableRow getTablerow() {
		return tablerow;
	}

	public void setTablerow(TableRow tablerow) {
		this.tablerow = tablerow;
	}

	public StatPlayer getPlayer() {
		return player;
	}

	public void setPlayer(StatPlayer player) {
		this.player = player;
	}

	public TextView getName() {
		return name;
	}

	public void setName(TextView name) {
		this.name = name;
	}

	public TextView getPlayed() {
		return played;
	}

	public void setPlayed(TextView played) {
		this.played = played;
	}

	public TextView getGoals() {
		return goals;
	}

	public void setGoals(TextView goals) {
		this.goals = goals;
	}

	public TextView getYellow() {
		return yellow;
	}

	public void setYellow(TextView yellow) {
		this.yellow = yellow;
	}

	public TextView getRed() {
		return red;
	}

	public void setRed(TextView red) {
		this.red = red;
	}
}
