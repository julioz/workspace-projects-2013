package br.com.zynger.vamosmarcar.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.model.Event;
import br.com.zynger.vamosmarcar.model.User;
import br.com.zynger.vamosmarcar.util.ParseObjectsPool;
import br.com.zynger.vamosmarcar.util.Util;
import br.com.zynger.vamosmarcar.view.FacebookImageView;
import br.com.zynger.vamosmarcar.view.GoogleMapImageView;
import br.com.zynger.vamosmarcar.view.RobotoTextView;

import com.google.android.gms.maps.model.LatLng;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class EventActivity extends BaseActivity {

	private Event event;
	
	private GoogleMapImageView gmivMap;
	private ImageView ivHeader;
	private FacebookImageView ivHeaderUserImage;
	private TextView tvHeader;
	private RobotoTextView tvDescription;
	private RelativeLayout rlMap;
	private RelativeLayout rlNoMapConnection;
	private RobotoTextView tvMapHeadline;
	private Button btComments;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		FadingActionBarHelper helper = new FadingActionBarHelper()
				.actionBarBackground(R.drawable.ab_background)
				.headerLayout(R.layout.eventactivity_header)
				.contentLayout(R.layout.activity_event);
		setContentView(helper.createView(this));
		helper.initActionBar(this);
		
		event = (Event) ParseObjectsPool.getInstance().getObjectToPass();

		loadViews();
		
		setTitle(event.getTitle());
		ivHeader.setImageResource(event.getType().getImageRes());
		
		ivHeaderUserImage.loadParseUser(event.getHost());
		tvHeader.setText(getString(R.string.eventactivity_openedby) + " " + User.getName(event.getHost()));
		tvDescription.setText(event.getDescription());
		
		if (!showMapLayout(new LatLng(event.getLatitude(), event.getLongitude()))) {
			rlMap.setVisibility(View.GONE);
			rlNoMapConnection.setVisibility(View.VISIBLE);
		}
		
		tvDescription.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View tv) {
				if (tvDescription.isEllipsized()) {
					showTextDialog(tvDescription.getText().toString());
				}
			}
		});
		
		btComments.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View bt) {
				Intent intent = new Intent(bt.getContext(), CommentsActivity.class);
				intent.putExtra(CommentsActivity.INTENT_TITLE, getTitle());
				startActivity(intent);
			}
		});
	}

	private boolean showMapLayout(final LatLng localization) {
		if (!Util.isConnected(this)) return false;
		
		try {
			Geocoder geoCoder = new Geocoder(this);
			List<Address> matches = geoCoder.getFromLocation(localization.latitude, localization.longitude, 1);
			Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
			
			final String addressLine = bestMatch.getAddressLine(0);
			tvMapHeadline.setText(addressLine);
			
			gmivMap.loadLocation(localization, 15, null, null);
			
			rlMap.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(view.getContext(), LocationViewerActivity.class);
					double[] location = new double[2];
					location[0] = localization.latitude;
					location[1] = localization.longitude;
					intent.putExtra(Constants.INTENT_LOCATION_POSITION, location);
					intent.putExtra(Constants.INTENT_LOCATION_ADDRESS, addressLine);
					startActivity(intent);
				}
			});
			
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	private void loadViews() {
		ivHeader = (ImageView) findViewById(R.eventactivity.iv_header);
		ivHeaderUserImage = (FacebookImageView) findViewById(R.eventactivity.iv_user_picture);
		tvHeader = (TextView) findViewById(R.eventactivity.tv_header);
		
		rlMap = (RelativeLayout) findViewById(R.eventactivity.rl_map);
		rlNoMapConnection = (RelativeLayout) findViewById(R.eventactivity.rl_nomapconnection);
		tvMapHeadline = (RobotoTextView) findViewById(R.eventactivity.tv_mapheadline);
		gmivMap = (GoogleMapImageView) findViewById(R.eventactivity.gmiv_map);
		
		tvDescription = (RobotoTextView) findViewById(R.eventactivity.tv_description);
		
		btComments = (Button) findViewById(R.eventactivity.bt_comments);
		int btCommentsColor = getResources().getColor(R.color.holo_blue_dark);
		btComments.setTextColor(btCommentsColor);
		Drawable writeCommentDrawable = getResources().getDrawable(R.drawable.ic_action_write_new);
		writeCommentDrawable.setBounds(0, 0, (int)(writeCommentDrawable.getIntrinsicWidth() * 0.8), 
		                         (int)(writeCommentDrawable.getIntrinsicHeight() * 0.8));
		writeCommentDrawable.setColorFilter(btCommentsColor, PorterDuff.Mode.SRC_ATOP);
		btComments.setCompoundDrawables(writeCommentDrawable, null, null, null);
		btComments.setCompoundDrawablePadding(20);
	}
}
