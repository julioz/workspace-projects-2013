package br.com.zynger.brasileirao2012.util;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;

public class ShareHelper {
	private Context context;
	private String title;

	public ShareHelper(Context context) {
		this.context = context;
		this.title = context.getString(R.string.share_send) + "...";
	}

	public void share(final String message) {
		List<ResolveInfo> activities = getActivitiesList();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);

		final ShareIntentListAdapter adapter = new ShareIntentListAdapter(
				context, activities);

		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int position) {
				String packageName = adapter.getPackageName(position);
				String activityInfoName = adapter.getActivityInfoName(position);
				startIntent(message, packageName, activityInfoName);
			}
		});

		builder.create().show();
	}

	private void startIntent(final String message, String packageName,
			String activityInfoName) {
		if (packageName.toLowerCase(Locale.getDefault()).contains("facebook")) {
			showConfirmationDialog(message);
		} else {
			context.startActivity(getSendIntent(message, packageName,
					activityInfoName));
		}
	}

	private Intent getSendIntent(final String message, String packageName,
			String activityInfoName) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setClassName(packageName, activityInfoName);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, message);
		return intent;
	}

	private List<ResolveInfo> getActivitiesList() {
		Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
		sendIntent.setType("text/plain");
		return context.getPackageManager().queryIntentActivities(sendIntent, 0);
	}

	private void showConfirmationDialog(final String message) {
		final Dialog d = new Dialog(this.context);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.sharehelper_dialog);

		TextView tvTitle = (TextView) d.findViewById(R.sharehelper.tv_title);
		TextView tvMessage = (TextView) d.findViewById(R.sharehelper.tv_message);
		final EditText etInput = (EditText) d.findViewById(R.sharehelper.et_input);
		Button btCancel = (Button) d.findViewById(R.sharehelper.bt_cancel);
		Button btSend = (Button) d.findViewById(R.sharehelper.bt_send);

		tvTitle.setText(title);
		tvMessage.setText(message);

		btCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				d.dismiss();
			}
		});

		btSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = etInput.getText().toString().trim();
				if (!msg.equals("")){
					msg += "\n";
				}
				msg += message;

				Intent it = new Intent(v.getContext(), FacebookConnect.class);
				it.putExtra(FacebookConnect.INTENT_MESSAGE, msg);
				v.getContext().startActivity(it);
				d.dismiss();
			}
		});

		d.show();
	}

	public class ShareIntentListAdapter extends ArrayAdapter<ResolveInfo> {
		private static final int LAYOUT_ID = R.layout.basiclistview;

		private LayoutInflater inflater;
		private PackageManager packageManager;

		public ShareIntentListAdapter(Context context, List<ResolveInfo> items) {
			super(context, 0, items);
			inflater = LayoutInflater.from(context);
			packageManager = context.getPackageManager();
			Collections.sort(items, new ResolveInfo.DisplayNameComparator(packageManager));
		}

		public String getPackageName(int position) {
			return getItem(position).activityInfo.packageName;
		}

		public String getActivityInfoName(int position) {
			return getItem(position).activityInfo.name;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ResolveInfo resolver = getItem(position);
			View row = inflater.inflate(LAYOUT_ID, null);

			TextView label = (TextView) row.findViewById(R.basiclistview.text1);
			ImageView image = (ImageView) row
					.findViewById(R.basiclistview.logo);

			label.setText(resolver.loadLabel(packageManager));
			image.setImageDrawable(resolver.activityInfo.applicationInfo
					.loadIcon(packageManager));

			return row;
		}
	}
}