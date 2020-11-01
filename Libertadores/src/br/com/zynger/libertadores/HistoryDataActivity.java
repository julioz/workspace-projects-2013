package br.com.zynger.libertadores;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import br.com.zynger.libertadores.adapters.HistoryCountryAdapter;
import br.com.zynger.libertadores.adapters.HistoryFinalsAdapter;
import br.com.zynger.libertadores.adapters.HistoryWinnersAndRunnerUpsAdapter;
import br.com.zynger.libertadores.model.HistoricClub;
import br.com.zynger.libertadores.model.HistoricCountry;
import br.com.zynger.libertadores.model.HistoricFinal;
import br.com.zynger.libertadores.view.HelveticaTextView;
import br.com.zynger.libertadores.xml.HistoryParser;

public class HistoryDataActivity extends ListActivity {

	public static final String INTENT_ACTIVITYTYPE = "historydata_activitytype";
	public static final String INTENT_ACTIVITYTITLE = "historydata_activitytitle";
	public static final int INTENT_TYPE_WINNERSANDRUNNERUPS = 0;
	public static final int INTENT_TYPE_FINALS = 1;
	public static final int INTENT_TYPE_BYCOUNTRY = 2;
	
	private Integer type;
	
	private HelveticaTextView titlebar_tvtitle;
	private LinearLayout ll_header_countries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historydataactivity);
		loadViews();
		
		type = getIntent().getIntExtra(INTENT_ACTIVITYTYPE, INTENT_TYPE_WINNERSANDRUNNERUPS);
		String activityTitle = getIntent().getStringExtra(INTENT_ACTIVITYTITLE);
		titlebar_tvtitle.setText(activityTitle.toUpperCase());
		
		if(type == INTENT_TYPE_WINNERSANDRUNNERUPS){			
			ArrayList<HistoricClub> historyArray = getWinnersAndRunnerUpsDatabase();
			setListAdapter(new HistoryWinnersAndRunnerUpsAdapter(this, historyArray));			
		}else if(type == INTENT_TYPE_FINALS){
			ArrayList<HistoricFinal> finalsArray = getFinalsDatabase();
			setListAdapter(new HistoryFinalsAdapter(this, finalsArray));
		}else if(type == INTENT_TYPE_BYCOUNTRY){
			ArrayList<HistoricCountry> historyArray = getCountriesDatabase();
			ll_header_countries.setVisibility(View.VISIBLE);
			setListAdapter(new HistoryCountryAdapter(this, historyArray));
		}
	}

	private void loadViews() {
		titlebar_tvtitle = (HelveticaTextView) findViewById(R.historydataactivity.titlebar_tvtitle);
		ll_header_countries = (LinearLayout) findViewById(R.historydataactivity.ll_header_countries);
		ll_header_countries.setVisibility(View.GONE);
	}

	private ArrayList<HistoricClub> getWinnersAndRunnerUpsDatabase() {
		ArrayList<HistoricClub> array = new HistoryParser().getWinnersAndRunnerUps(this);
		return array;
	}
	
	private ArrayList<HistoricFinal> getFinalsDatabase() {
		ArrayList<HistoricFinal> array = new HistoryParser().getFinals(this);
		return array;
	}
	
	private ArrayList<HistoricCountry> getCountriesDatabase() {
		ArrayList<HistoricCountry> array = new HistoryParser().getByCountries(this);
		return array;
	}
}