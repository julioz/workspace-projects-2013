package br.com.zynger.influ.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.zynger.influ.R;
import br.com.zynger.influ.RankingCariocaTabActivity;
import br.com.zynger.influ.RankingLibertadoresTabActivity;
import br.com.zynger.influ.model.StatClub;

public class RankingRow extends TableRow{
	
	private static final String CAMP_CBRASIL = "Palmeiras";
	private static final String CAMP_LIBERTA = "Corinthians";
	
	private static final int BLUE = 0xFF6495ED;
	private static final int YELLOW = 0xFFFFFF00;
	private static final int RED = 0xFFB3002A;
	
	private Context context;
	private TableRow tablerow;
	private StatClub club;
	private int competition;
	private TextView position, name, points, played, win, draw, lose, goalspro, goalsagainst, balance, /*ycards, rcards,*/ aprov;

	public RankingRow(Context c, StatClub club, int competition) {
		super(c);
		this.context = c;
		this.club = club;
		this.setCompetition(competition);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.tablerow = (TableRow) inflater.inflate(R.layout.ranking_row, null);
		
		this.position = (TextView) tablerow.findViewById(R.rankingrow.position);
		this.name = (TextView) tablerow.findViewById(R.rankingrow.name);
		this.points = (TextView) tablerow.findViewById(R.rankingrow.points);
		this.played = (TextView) tablerow.findViewById(R.rankingrow.played);
		this.win = (TextView) tablerow.findViewById(R.rankingrow.win);
		this.draw = (TextView) tablerow.findViewById(R.rankingrow.draw);
		this.lose = (TextView) tablerow.findViewById(R.rankingrow.lose);
		this.goalspro = (TextView) tablerow.findViewById(R.rankingrow.goalspro);
		this.goalsagainst = (TextView) tablerow.findViewById(R.rankingrow.goalsagainst);
		this.balance = (TextView) tablerow.findViewById(R.rankingrow.balance);
		/*this.ycards = (TextView) tablerow.findViewById(R.rankingrow.ycards);
		this.rcards = (TextView) tablerow.findViewById(R.rankingrow.rcards);*/
		this.aprov = (TextView) tablerow.findViewById(R.rankingrow.aprov);
		
		if(this.club.getPosition()<10) this.position.setText("0" + String.valueOf(this.club.getPosition())); 
		else this.position.setText(String.valueOf(this.club.getPosition()));
		
		if(getCompetition() != RankingCariocaTabActivity.CARIOCA_GRUPO_A && getCompetition() != RankingCariocaTabActivity.CARIOCA_GRUPO_B && getCompetition() != RankingLibertadoresTabActivity.LIBERTADORES){			
			if(this.club.getPosition()<5 || this.club.getName().equals(CAMP_CBRASIL) || this.club.getName().equals(CAMP_LIBERTA)){
				position.setTextColor(BLUE);			
			}else if(this.club.getPosition()>16){
				position.setTextColor(RED);
			}/*else if(this.club.getPosition()>=5 && this.club.getPosition() < 15){
				position.setTextColor(YELLOW);
			}*/
		}
		this.name.setText(this.club.getName());
		this.points.setText(String.valueOf(this.club.getPoints()));
		this.played.setText(String.valueOf(this.club.getPlayed()));
		this.win.setText(String.valueOf(this.club.getWin()));
		this.draw.setText(String.valueOf(this.club.getDraw()));
		this.lose.setText(String.valueOf(this.club.getLose()));
		try{
			this.goalspro.setText(String.valueOf(this.club.getGoalsPro()));
			this.goalsagainst.setText(String.valueOf(this.club.getGoalsAgainst()));
			this.balance.setText(String.valueOf(this.club.getBalance()));
			/*this.ycards.setText(String.valueOf(this.club.getyCards()));
			this.rcards.setText(String.valueOf(this.club.getrCards()));*/
			String aprov = String.valueOf(this.club.getAproveit());
			if(aprov.length()>4) aprov = aprov.substring(0, 4);
			if(aprov.charAt(aprov.length()-1) == '.') aprov = aprov.substring(0, aprov.length()-1);
			if(aprov.substring(aprov.length()-2, aprov.length()).equals(".0")) aprov = aprov.substring(0, aprov.length()-2);
			this.aprov.setText(aprov);
		}catch(NullPointerException npe){} //se estiver na view portrait, estes textviews nao existirao
	}
	
	public boolean isChampion(){
		if(this.getClub().getName().equals(CAMP_CBRASIL) || this.getClub().getName().equals(CAMP_LIBERTA)) return true;
		else return false;
	}

	public TableRow getTablerow() {
		return tablerow;
	}

	public void setTablerow(TableRow tablerow) {
		this.tablerow = tablerow;
	}

	public StatClub getClub() {
		return club;
	}

	public void setClub(StatClub club) {
		this.club = club;
	}

	public TextView getPosition() {
		return position;
	}

	public void setPosition(TextView position) {
		this.position = position;
	}

	public TextView getName() {
		return name;
	}

	public void setName(TextView name) {
		this.name = name;
	}

	public TextView getPoints() {
		return points;
	}

	public void setPoints(TextView points) {
		this.points = points;
	}

	public TextView getPlayed() {
		return played;
	}

	public void setPlayed(TextView played) {
		this.played = played;
	}

	public TextView getWin() {
		return win;
	}

	public void setWin(TextView win) {
		this.win = win;
	}

	public TextView getDraw() {
		return draw;
	}

	public void setDraw(TextView draw) {
		this.draw = draw;
	}

	public TextView getLose() {
		return lose;
	}

	public void setLose(TextView lose) {
		this.lose = lose;
	}

	public TextView getGoalspro() {
		return goalspro;
	}

	public void setGoalspro(TextView goalspro) {
		this.goalspro = goalspro;
	}

	public TextView getGoalsagainst() {
		return goalsagainst;
	}

	public void setGoalsagainst(TextView goalsagainst) {
		this.goalsagainst = goalsagainst;
	}

	public TextView getBalance() {
		return balance;
	}

	public void setBalance(TextView balance) {
		this.balance = balance;
	}

	/*public TextView getYcards() {
		return ycards;
	}

	public void setYcards(TextView ycards) {
		this.ycards = ycards;
	}

	public TextView getRcards() {
		return rcards;
	}

	public void setRcards(TextView rcards) {
		this.rcards = rcards;
	}*/

	public TextView getAprov() {
		return aprov;
	}

	public void setAprov(TextView aprov) {
		this.aprov = aprov;
	}

	public int getCompetition() {
		return competition;
	}

	public void setCompetition(int competition) {
		this.competition = competition;
	}
}
