package br.ufrj.dcc.central1746maps;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.ufrj.dcc.central1746maps.Call.State;
import br.ufrj.dcc.central1746maps.Call.Type;

public class CallDetailsActivity extends Activity {

	protected static final String INTENT_CALL = "CALL";
	private Call call;
	private TextView tvCallOpening;
	private TextView tvCallDue;
	private TextView tvCallId;
	private TextView tvStreetPrefix;
	private TextView tvStreetName;
	private TextView tvStreetReference;
	private ImageView ivTypeIcon;
	private TextView tvType;
	private TextView tvNeighborhoodName;
	private TextView tvState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_details);

		call = (Call) getIntent().getSerializableExtra(INTENT_CALL);

		loadViews();

		tvStreetName.setText(call.getAddress());
		tvStreetPrefix.setText(call.getAddressPrefix());
		tvCallId.setText("ID " + call.getId());
		if (call.getNumber() != null) {
			tvStreetReference.setText("Próximo ao número " + call.getNumber());
			tvStreetReference.setVisibility(View.VISIBLE);
		}
		tvCallOpening.setText("Aberto: " + formatCalendar(call.getOpeningDate()));
		tvCallDue.setText("Fechado: " + formatCalendar(call.getClosingDate()));
		tvNeighborhoodName.setText(call.getNeighborhood().toUpperCase(Locale.getDefault()));
		setTypeBasedData();
		setStateBasedData();
	}

	private String formatCalendar(GregorianCalendar calendar) {
		return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
	}

	private void setStateBasedData() {
		State state = call.getState();
		int color = -1;
		int textColor = -1;

		if (state == State.ABERTO) {
			color = 0xffc0c0c0;
			textColor = 0xff7a7a7a;
		} else if (state == State.ATRASADO) {
			color = 0xffdfca59;
			textColor = 0xffa18311;
		} else if (state == State.FECHADO) {
			color = 0xff54de6a;
			textColor = 0xff10a212;
		} else
			return;

		tvState.setBackgroundColor(color);
		tvState.setText("Chamado "
				+ state.toString().toLowerCase(Locale.getDefault()));
		tvState.setTextColor(textColor);
	}

	private void setTypeBasedData() {
		Type type = call.getType();
		tvType.setText(type.getTypeName().toUpperCase(Locale.getDefault()));
		ivTypeIcon.setImageResource(type.getImageResource());
	}

	private void loadViews() {
		tvCallOpening = (TextView) findViewById(R.calldetails.tv_call_opening);
		tvCallDue = (TextView) findViewById(R.calldetails.tv_call_due);
		tvCallId = (TextView) findViewById(R.calldetails.tv_call_id);
		tvStreetPrefix = (TextView) findViewById(R.calldetails.tv_street_prefix);
		tvStreetName = (TextView) findViewById(R.calldetails.tv_street_name);
		tvStreetReference = (TextView) findViewById(R.calldetails.tv_street_reference);
		ivTypeIcon = (ImageView) findViewById(R.calldetails.iv_type_icon);
		tvType = (TextView) findViewById(R.calldetails.tv_type);
		tvNeighborhoodName = (TextView) findViewById(R.calldetails.tv_neighborhood_name);
		tvState = (TextView) findViewById(R.calldetails.tv_state);
	}
}
