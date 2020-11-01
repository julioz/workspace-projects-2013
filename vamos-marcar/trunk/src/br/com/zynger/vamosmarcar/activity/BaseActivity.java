package br.com.zynger.vamosmarcar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.FacebookHolder;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.util.ParseObjectsPool;
import br.com.zynger.vamosmarcar.view.RobotoTextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.facebook.Session;
import com.parse.ParseObject;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseUser;

public class BaseActivity extends SherlockFragmentActivity {
	
	public interface FriendPickable {
		void friendsPicked(String[] friendsIds);
	}
	
	public interface LocationPickable {
		void locationPicked(LatLng latlng, String address);
	}
	
	private final Boolean DEBUG = true;

	protected FacebookHolder facebook;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		facebook = FacebookHolder.getInstance();
	}

	protected void showTextDialog(String message) {
		AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
		TextView tvMessage = new RobotoTextView(this,
				RobotoTextView.ROBOTO_REGULAR);
		tvMessage.setText(message);
		tvMessage.setPadding(12, 12, 12, 12);
		popupBuilder.setView(tvMessage);
		AlertDialog alertDialog = popupBuilder.create();
		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
	}

	protected void showLogoutDialog() {
		DialogInterface.OnClickListener logoutDialogListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					logout();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					dialog.dismiss();
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.facebook_logout_dialog_title)
				.setPositiveButton(android.R.string.yes, logoutDialogListener)
				.setNegativeButton(android.R.string.no, logoutDialogListener).show();
	}
	
	protected void startActivityWithParseObject(Class<?> activity, ParseObject parseObject) {
		ParseObjectsPool.getInstance().setObjectToPass(parseObject);
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}
	
	private void logout() {
		facebook.clearInstance();
		Session session = Session.getActiveSession();
		session.closeAndClearTokenInformation();
		ParseUser.logOut();
		finish();
		startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}
	
	protected void startPickFriendsIntent(SparseArray<String> preselectedIds) {
		Intent intent = new Intent(this,
				FriendChooserActivity.class);
		if (preselectedIds != null && preselectedIds.size() > 0) {
			String[] peopleIdsArray = new String[preselectedIds.size()];
			for (int i = 0; i < preselectedIds.size(); i++) {
				peopleIdsArray[i] = preselectedIds.get(i);
			}
			intent.putExtra(Constants.INTENT_FRIENDS_PRESELECTED, peopleIdsArray);
		}
		startActivityForResult(intent, Constants.PICK_FRIENDS_REQUEST);
	}
	
	protected void startPickLocationIntent() {
		Intent intent = new Intent(this,
				LocationPickerActivity.class);
		startActivityForResult(intent, Constants.PICK_LOCATION_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == Constants.PICK_FRIENDS_REQUEST) {
				if (this instanceof FriendPickable) {
					String[] friends = data
							.getStringArrayExtra(Constants.INTENT_FRIENDS);
					((FriendPickable) this).friendsPicked(friends);
				}
			} else if (requestCode == Constants.PICK_LOCATION_REQUEST) {
				if (this instanceof LocationPickable) {
					double[] location = data.getDoubleArrayExtra(Constants.INTENT_LOCATION_POSITION);
					LatLng latlng = new LatLng(location[0], location[1]);
					String address = data.getStringExtra(Constants.INTENT_LOCATION_ADDRESS);
					((LocationPickable) this).locationPicked(latlng, address);
				}
			}
		}
	}
	
	public boolean isDebug() {
		if (DEBUG == true) {
			Toast.makeText(this, "Debugging... will not touch server", Toast.LENGTH_SHORT).show();
		}
		return DEBUG;
	}
}
