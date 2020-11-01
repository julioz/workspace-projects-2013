package br.com.zynger.influ;

import oauth.signpost.OAuth;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.web.BitlyShortener;
import br.com.zynger.influ.web.TwitterUtils;

public class TwitterDialog extends Activity {

	private final static String BITLY_USER = "julioz92";
	private final static String BITLY_APIKEY = "R_9ee37ae44cd0b11dc15b3d9a65710c63";
	
	private SharedPreferences prefs;
	private final Handler mTwitterHandler = new Handler();
	private TextView loginStatus, characterCounter;
	private EditText et_tweet;
	private Button tweet, clearCredentials;
	private String longUrl;

	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(), "Tweet enviado!", Toast.LENGTH_SHORT).show();
			TwitterDialog.this.finish();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tweet_dialog);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		loadViews();
		updateTheme();
		
		Intent it = this.getIntent();
		longUrl = it.getStringExtra("url");
		
		if(longUrl != null){
			String shortUrl = getShortUrl(longUrl);
			if(shortUrl != null) et_tweet.setText(shortUrl + " ");
			else et_tweet.setText(longUrl + " ");
		}

		tweet.setOnClickListener(new View.OnClickListener() {
			/**
			 * Send a tweet. If the user hasn't authenticated to Tweeter yet, he'll be redirected via a browser
			 * to the twitter login page. Once the user authenticated, he'll authorize the Android application to send
			 * tweets on the users behalf.
			 */
			public void onClick(View v) {
				if(SplashScreen.isConnected(v.getContext())){
					if (TwitterUtils.isAuthenticated(prefs)) {
						sendTweet();
					}else{
						Intent i = new Intent(v.getContext(), PrepareRequestTokenActivity.class);
						i.putExtra("tweet_msg", getTweetMsg());
						startActivity(i);
						Toast.makeText(v.getContext(), "Tweet enviado!", Toast.LENGTH_SHORT).show();
						finish();
					}
				}else{
					Toast.makeText(v.getContext(), "Conecte-se à internet para enviar o tweet", Toast.LENGTH_SHORT).show();
				}
			}
		});

		clearCredentials.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clearCredentials();
				updateLoginStatus();
			}
		});
		
		characterCounter.setText("Restam " + String.valueOf(140-et_tweet.getText().toString().length()) + " caracteres");
		
		et_tweet.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				int n_chars = 140 - s.length();
				characterCounter.setText("Restam " + String.valueOf(n_chars) + " caracteres");
				if(n_chars<25) characterCounter.setTextColor(0xFFB3002A);
				else characterCounter.setTextColor(0xFFFFFFFF);
			}
		});
	}

	private void loadViews() {
		loginStatus = (TextView) findViewById(R.twitter_dialog.login_status);
		characterCounter = (TextView) findViewById(R.twitter_dialog.characted_counter);
		et_tweet = (EditText) findViewById(R.twitter_dialog.et_text);
		tweet = (Button) findViewById(R.twitter_dialog.btn_tweet);
		clearCredentials = (Button) findViewById(R.twitter_dialog.btn_clear_credentials);
	}

	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		findViewById(R.twitter_dialog.ll_actbg).setBackgroundColor(t.getAct_background());
		findViewById(R.twitter_dialog.ll_actbg).invalidate();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateLoginStatus();
	}

	public void updateLoginStatus() {
		String status;
		if(TwitterUtils.isAuthenticated(prefs)) status = "Ligado";
		else status = "Desligado";
		loginStatus.setText(Html.fromHtml("Status:<br />" + status));
	}

	private String getShortUrl(String longUrl){
		try {
			BitlyShortener bitly = new BitlyShortener(BITLY_USER, BITLY_APIKEY);
			String shortUrl = bitly.getShortUrl(longUrl);
			return shortUrl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getTweetMsg() {
		String tweet = et_tweet.getText().toString();
		
		return tweet;
	}	

	public void sendTweet() {
		Thread t = new Thread() {
			public void run() {
				try {
					TwitterUtils.sendTweet(prefs, getTweetMsg());
					mTwitterHandler.post(mUpdateTwitterNotification);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
		t.start();
	}

	private void clearCredentials() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Editor edit = prefs.edit();
		edit.remove(OAuth.OAUTH_TOKEN);
		edit.remove(OAuth.OAUTH_TOKEN_SECRET);
		edit.commit();
	}
}