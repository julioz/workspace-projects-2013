package br.com.zynger.influ;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		findPreference("app_theme").setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				preference.getContext().startActivity(new Intent(preference.getContext(), ThemeChooserActivity.class));
				return true;
			}
		});
		
		findPreference("widget_theme").setDefaultValue("Padrão");
		findPreference("widget_theme").setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String widgetTheme = (String) newValue;
				preference.setSummary(widgetTheme);
				prefs.edit().putString("widget_theme", widgetTheme).commit();
				
				AppWidgetManager appwidgetmanager = AppWidgetManager.getInstance(preference.getContext());
				NextGameWidgetProvider.updateWidgetContent(preference.getContext(), appwidgetmanager);
				RankingBrasileiraoWidgetProvider.updateRankingBrasileiraoWidgetContent(preference.getContext(), appwidgetmanager);
				return true;
			}
		});
		findPreference("app_version").setSummary(SplashScreen.getAppVersion(this));
	}
	
	@Override
	protected void onResume() {
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		findPreference("app_theme").setSummary(((Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO)).getName());
		findPreference("widget_theme").setSummary(prefs.getString("widget_theme", ""));
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// example
		/*Preference pref = findPreference(key);
	    if (pref instanceof EditTextPreference) {
	        EditTextPreference etp = (EditTextPreference) pref;
	        pref.setSummary(etp.getText());
	    }*/
		
	}
}