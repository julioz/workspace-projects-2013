package br.com.zynger.brasileirao2012.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.SplashScreen;

public class NotificationHelper {
	public enum Notifications {
		FOLLOW(1,
				R.drawable.ic_notification_soccerball,
				R.string.realtimematchservice_notification_following,
				NO_AUDIO),
		WHISTLE(2,
				R.drawable.ic_notification_whistle,
				R.string.realtimematchservice_notification_matchstarted,
				R.raw.whistle),
		GOAL(3,
				R.drawable.ic_notification_soccerball,
				R.string.realtimematchservice_notification_goal,
				R.raw.goal),
		STARTMATCH(4,
				R.drawable.ic_notification_whistle,
				R.string.realtimematchservice_notification_matchended,
				R.raw.whistle);

		private final int id;
		private final int iconRes;
		private final int prefixRes;
		private final int audioRes;

		private Notifications(int id, int iconRes, int prefixRes, int audioRes) {
			this.id = id;
			this.iconRes = iconRes;
			this.prefixRes = prefixRes;
			this.audioRes = audioRes;
		}

		public int getId() {
			return id;
		}

		public int getIconRes() {
			return iconRes;
		}

		public int getPrefixRes() {
			return prefixRes;
		}

		public int getAudioRes() {
			return audioRes;
		}
	}

	protected final static int NO_AUDIO = -1;

	private final Context context;
	private NotificationManager notificationManager;

	public NotificationHelper(Context context) {
		this.context = context;
		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void showNotification(Notifications notification, String content) {
		showNotification(notification.getId(), notification.getIconRes(),
				notification.getPrefixRes(), notification.getAudioRes(),
				content);
	}

	private void showNotification(int id, int icon, int prefix, int resAudio,
			String content) {
		Notification notification = new Notification();

		setIcon(notification, icon);

		setTickerText(notification, context.getString(prefix), content);

		setWhen(notification);

		setFlags(notification);
		setSound(notification, resAudio);

		setVibration(notification);

		setIntent(notification);
		notificationManager.notify(id, notification);
	}

	private void setIntent(Notification notification) {
		Intent notificationIntent = new Intent(context, SplashScreen.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification
				.setLatestEventInfo(
						context,
						context.getString(R.string.app_name)
								+ " - "
								+ context.getString(R.string.realtimematchesactivity_title),
						notification.tickerText, contentIntent);
	}

	private void setVibration(Notification notification) {
		if (PreferenceEditor.shouldServiceVibrate(context)) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
	}

	private void setSound(Notification notification, int resAudio) {
		if (resAudio != NO_AUDIO
				&& PreferenceEditor.shouldServicePlaySound(context)) {
			notification.sound = Uri.parse("android.resource://"
					+ context.getPackageName() + "/" + resAudio);
		}
	}

	private void setFlags(Notification notification) {
		notification.flags |= Notification.FLAG_AUTO_CANCEL
				| Notification.FLAG_SHOW_LIGHTS;
	}

	private void setWhen(Notification notification) {
		notification.when = System.currentTimeMillis();
	}

	private void setTickerText(Notification notification, String prefix,
			String content) {
		notification.tickerText = prefix + ": " + content;
	}

	private void setIcon(Notification notification, int icon) {
		notification.icon = icon;
	}
}
