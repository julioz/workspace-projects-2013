package br.com.zynger.brasileirao2012;

import java.util.Locale;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.util.PreferenceEditor;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

@SuppressWarnings("deprecation")
public class SettingsActivity extends SherlockPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarTitle();
		addPreferencesFromResource(R.xml.preferences);
		setContentView(R.layout.settings);
		setLastUpdateAllFromPreferences();
	}

	private void setLastUpdateAllFromPreferences() {
		String lastUpdate = PreferenceEditor.getLastGeneralUpdate(this);
		if (lastUpdate != null) {
			findPreference("pref_lastupdateall").setSummary(lastUpdate);
		}
	}
	
	private void setActionBarTitle() {
		View customView = LayoutInflater.from(this).inflate(
				R.layout.actionbar_custom_layout, null);

		getSupportActionBar().setCustomView(customView);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/tertre-xbol.otf");
		TextView tvTitle = ((TextView) customView.findViewById(R.actionbar.tv_title));
		tvTitle.setTypeface(face);
		tvTitle.setText(getTitle().toString().toUpperCase(Locale.getDefault()));
	}
}