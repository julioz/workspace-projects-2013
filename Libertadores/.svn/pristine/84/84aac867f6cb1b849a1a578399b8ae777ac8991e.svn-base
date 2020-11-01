package br.com.zynger.libertadores;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideoPlayerActivity extends YouTubeBaseActivity implements OnInitializedListener{

	private static final int RECOVERY_DIALOG_REQUEST = 1;
	public static final String INTENT_VIDEOID = "INTENT_VIDEOID";
	public static final String INTENT_VIDEOURL = "INTENT_VIDEOURL";
	
	private String videoID, videoURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youtubevideoplayeractivity);
		
		videoID = getIntent().getStringExtra(INTENT_VIDEOID);
		videoURL = getIntent().getStringExtra(INTENT_VIDEOURL);

		YouTubePlayerView youtubeView = (YouTubePlayerView) findViewById(R.youtubevideoplayer.youtube_view);
		youtubeView.initialize(getString(R.string.youtube_key), this);
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
		} else {
			Toast.makeText(this, errorReason.toString(), Toast.LENGTH_LONG).show();
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(videoURL));
			startActivity(i);
		}
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(videoID);
		}
	}
	
}
