package br.ufrj.dcc.central1746maps;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private RelativeLayout rlProgress;
	private TextView tvHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rlProgress = (RelativeLayout) findViewById(R.main.rl_progress);
		tvHeader = (TextView) LayoutInflater.from(this).inflate(
				R.layout.mainactivity_headerview, null);
		if (getListView().getHeaderViewsCount() > 0) {
			getListView().removeHeaderView(tvHeader);
		}
		getListView().addHeaderView(tvHeader);

		new GetCallsTask(this).execute();
	}
	
	public class GetCallsTask extends AsyncTask<Void, Void, List<Call>> {
		private Context context;

		public GetCallsTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			rlProgress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected List<Call> doInBackground(Void... arg0) {
			try {
				return ServiceWrapper.getCalls();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<Call> calls) {
			if (calls != null) {
				getListView().setAdapter(new CallAdapter(context, calls));
				getListView().setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterview,
							View listview, int position, long id) {
						Call call = (Call) getListView()
								.getItemAtPosition(position);
						if (call != null) {
							Intent intent = new Intent(
									adapterview.getContext(),
									CallDetailsActivity.class);
							intent.putExtra(CallDetailsActivity.INTENT_CALL,
									call);
							startActivity(intent);
							overridePendingTransition(
									R.anim.slide_righttoleft_in,
									R.anim.slide_righttoleft_out);
						}
					}

				});

				tvHeader.setText(getListView().getAdapter().getCount()
						+ " chamados");
			} else {
				Toast.makeText(context,
						"Um erro ocorreu. Tente novamente mais tarde.",
						Toast.LENGTH_LONG).show();
			}

			rlProgress.setVisibility(View.INVISIBLE);
			super.onPostExecute(calls);
		}
	}
}
