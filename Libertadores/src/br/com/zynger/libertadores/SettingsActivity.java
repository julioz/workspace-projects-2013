package br.com.zynger.libertadores;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class SettingsActivity extends PreferenceActivity {
	
	private SharedPreferences prefs;
	
	private String[] ids = { "cb_actsupdates", "cb_updatecheck", "cb_imgsdownload", "list_newslanguage", "cb_showhints" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setContentView(R.layout.settingsactivity);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		TextView actionbar_tt = (TextView) findViewById(R.settingsactivity.titlebar_tvtitle);
		actionbar_tt.setText(getString(R.string.homedashboard_settings).toUpperCase());
		
		String newsLanguage = prefs.getString("list_newslanguage", null);
		if(null != newsLanguage){
			String summaryNewsLanguage = null;
			if(newsLanguage.equals("en")) summaryNewsLanguage = "English";
			else if(newsLanguage.equals("es")) summaryNewsLanguage = "Español";
			else if(newsLanguage.equals("pt")) summaryNewsLanguage = "Português";

			findPreference("list_newslanguage").setSummary(turnTextToBlack(summaryNewsLanguage));
		}
		
		for (String id : ids) {
			setBlackText(id);
		}
	}
	
	private void setBlackText(String id) {
		Preference pref = findPreference(id);
		pref.setTitle(turnTextToBlack(pref.getTitle()));
		if(pref.getSummary() != null) pref.setSummary(turnTextToBlack(pref.getSummary()));
	}
	
	private Spannable turnTextToBlack(CharSequence text){
		Spannable spannable = new SpannableString(text);
		spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannable.length(), 0);
		return spannable;
	}
}