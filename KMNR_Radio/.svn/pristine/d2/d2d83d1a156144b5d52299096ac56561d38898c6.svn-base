package edu.mst.kmnrradio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import edu.mst.kmnrradio.model.Station;
import edu.mst.kmnrradio.util.StatefulMediaPlayer;
import edu.mst.kmnrradio.util.StatefulMediaPlayer.MPStates;

/**
 * An extension of android.app.Service class which provides access to a StatefulMediaPlayer.<br>
 * @author rootlicker http://speakingcode.com
 * @see com.speakingcode.android.media.mediaplayer.StatefulMediaPlayer
 */
public class MediaPlayerService extends Service implements OnBufferingUpdateListener,
OnInfoListener, OnPreparedListener, OnErrorListener {

	private StatefulMediaPlayer mMediaPlayer = new StatefulMediaPlayer();
	private final Binder mBinder = new MediaPlayerBinder();

	/**
	 * A class for clients binding to this service. The client will be passed an object of this class
	 * via its onServiceConnected(ComponentName, IBinder) callback.
	 */
	public class MediaPlayerBinder extends Binder {
		/**
		 * Returns the instance of this service for a client to make method calls on it.
		 * @return the instance of this service.
		 */
		public MediaPlayerService getService() {
			return MediaPlayerService.this;
		}

	}

	/**
	 * Returns the contained StatefulMediaPlayer
	 * @return
	 */
	public StatefulMediaPlayer getMediaPlayer() {
		return mMediaPlayer;
	}

	/**
	 * Initializes a StatefulMediaPlayer for streaming playback of the provided StreamStation
	 * @param station The StreamStation representing the station to play
	 */
	public void initializePlayer(Station station) {
		mMediaPlayer = new StatefulMediaPlayer(station);

		mMediaPlayer.setOnBufferingUpdateListener(this);
		mMediaPlayer.setOnInfoListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.prepareAsync();
	}

	/**
	 * Initializes a StatefulMediaPlayer for streaming playback of the provided stream url
	 * @param streamUrl The URL of the stream to play.
	 */
	public void initializePlayer(String streamUrl) {
		mMediaPlayer = new StatefulMediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(streamUrl);
		}
		catch (Exception e) {
			Log.e(MainActivity.TAG, "error setting data source");
			mMediaPlayer.setState(MPStates.ERROR);
		}
		mMediaPlayer.setOnBufferingUpdateListener(this);
		mMediaPlayer.setOnInfoListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.prepareAsync();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer player, int percent) {

	}

	@Override
	public boolean onError(MediaPlayer player, int what, int extra) {
		mMediaPlayer.reset();
		return true;
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer player) {
		startMediaPlayer();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	/**
	 * Pauses the contained StatefulMediaPlayer
	 */
	public void pauseMediaPlayer() {
		Log.d(MainActivity.TAG,"pauseMediaPlayer() called");
		mMediaPlayer.pause();
		stopForeground(true);

	}

	/**
	 * Starts the contained StatefulMediaPlayer and foregrounds the service to support
	 * persisted background playback.
	 */
	public void startMediaPlayer() {
	    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		builder
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(getString(R.string.app_name))
		.setTicker(getString(R.string.service_notificationticker))
		.setContentIntent(pendingIntent)
		.setAutoCancel(true);

		manager.notify(1, builder.build());
		Log.d(MainActivity.TAG,"startMediaPlayer() called");
		mMediaPlayer.start();
	}

	/**
	 * Stops the contained StatefulMediaPlayer.
	 */
	public void stopMediaPlayer() {
		stopForeground(true);
		try{			
			if(mMediaPlayer.isPlaying()){			
				mMediaPlayer.stop();
				mMediaPlayer.release();
			}
		}catch(IllegalStateException ise){ }
	}

	public void resetMediaPlaer() {
		stopForeground(true);
		mMediaPlayer.reset();
	}

}