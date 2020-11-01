package br.com.zynger.libertadores;

import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import br.com.zynger.libertadores.asynctasks.UpdateAppTask;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.data.GroupsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.util.InternalStorageHandler;

public class SplashScreenActivity extends Activity {

	private InternalStorageHandler internalStorage;
	private ClubsDB clubsDB;
	private TreeMap<String, Club> clubs;
	private SharedPreferences sharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		clubsDB = ClubsDB.getSingletonObject(this);
		GroupsDB.getSingletonObject(clubsDB); //inicializar banco de grupos
		internalStorage = new InternalStorageHandler(this);
		clubs = clubsDB.getClubs();
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		new SplashScreenTask(this).execute();
	}

	private void updateDatabase() {
		String standings = (String) internalStorage.openBackup(InternalStorageHandler.STANDINGS_JSON);
		if(null != standings){
			try{				
				JSONObject jsonStandings = new JSONObject(standings);
				for (Club club : clubs.values()) {
					club.setFromJson(jsonStandings);
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}

	public static String getAppVersion(Context c){
		PackageManager manager = c.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
			//String packageName = info.packageName;
			//return info.versionCode;
			return info.versionName;
			//Log.d(HomeActivity.TAG, "Package Name: " + packageName);
			//Log.d(HomeActivity.TAG, "Version Code: " + this.versionCode);
			//Log.d(HomeActivity.TAG, "Version Name: " + this.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	class SplashScreenTask extends AsyncTask<Void, Void, Boolean> {

		private Context context;
		private String appVersion;

		public SplashScreenTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			setContentView(R.layout.splashscreenactivity);

			TextView tv_version = (TextView) findViewById(R.splashscreenactivity.tv_version);
			appVersion = getAppVersion(context);
			tv_version.setText(appVersion);
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			updateDatabase();
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(sharedPrefs.getBoolean("cb_updatecheck", true)){
				new UpdateAppTask(context, appVersion).execute();
			}
			startActivity(new Intent(context, HomeActivity.class));
			finish();
			super.onPostExecute(result);
		}
	}
}
