package br.com.zynger.brasileirao2012.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;

public class IconedAlertDialog extends AlertDialog {

	private TextView tv_snippet;
	private ImageView iv_icon;
	private Button bt_download, bt_later;
	
	public IconedAlertDialog(Context context, int iconResource, String title, String snippet,
			String downloadButtonUrl, String leftButtonText, String rightButtonText) {
		super(context);
		View view = getLayoutInflater().inflate(R.layout.iconedalertdialog, null);
		setView(view);
		setTitle(title);

		// set the custom dialog components - text, image and button
		tv_snippet = (TextView) view.findViewById(R.iconedalertdialog.tv_snippet);
		iv_icon = (ImageView) view.findViewById(R.iconedalertdialog.iv_icon);
		bt_download = (Button) view.findViewById(R.iconedalertdialog.bt_download);
		bt_later = (Button) view.findViewById(R.iconedalertdialog.bt_later);

		tv_snippet.setText(snippet);
		iv_icon.setImageResource(iconResource);
		setDownloadButtonUrl(downloadButtonUrl);
		bt_download.setText(leftButtonText);
		bt_later.setText(rightButtonText);
		bt_later.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	public void setDownloadButtonUrl(final String url) {
		bt_download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				v.getContext().startActivity(intent);
			}
		});
	}

}
