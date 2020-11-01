package br.com.zynger.vamosmarcar.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.interfaces.FacebookFriendsTouchable;
import br.com.zynger.vamosmarcar.model.FacebookFriend;
import br.com.zynger.vamosmarcar.model.User;
import br.com.zynger.vamosmarcar.view.AnimationHelper;
import br.com.zynger.vamosmarcar.view.FacebookImageView;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class LoginActivity extends BaseActivity implements FacebookFriendsTouchable {

	private Button authButton;
	private FacebookImageView profilePictureView;
	private LinearLayout llProgress;
	private TextView tvProgress;
	private ProgressBar pbProgress;
	private RelativeLayout rlProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginactivity);

		loadViews();
		testByPassLogin();
	}

	private boolean testByPassLogin() {
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			facebook.setUserId(User.getId(currentUser));
			facebook.setUserName(User.getName(currentUser));
			// Finish this and go to the user next activity
			byPassLoginActivity();

			return true;
		}
		return false;
	}

	private void loadViews() {
		authButton = (Button) findViewById(R.loginactivity.bt_auth);
		authButton.setCompoundDrawablesWithIntrinsicBounds(
				R.drawable.com_facebook_inverse_icon, 0, 0, 0);
		authButton
				.setCompoundDrawablePadding(getResources()
						.getDimensionPixelSize(
								R.dimen.com_facebook_loginview_compound_drawable_padding));
		authButton.setPadding(
				getResources().getDimensionPixelSize(
						R.dimen.com_facebook_loginview_padding_left),
				getResources().getDimensionPixelSize(
						R.dimen.com_facebook_loginview_padding_top),
				getResources().getDimensionPixelSize(
						R.dimen.com_facebook_loginview_padding_right),
				getResources().getDimensionPixelSize(
						R.dimen.com_facebook_loginview_padding_bottom));

		llProgress = (LinearLayout) findViewById(R.loginactivity.ll_progress);
		tvProgress = (TextView) findViewById(R.loginactivity.tv_progress);
		pbProgress = (ProgressBar) findViewById(R.loginactivity.pb_progress);
		rlProfile = (RelativeLayout) findViewById(R.loginactivity.rl_profile);

		profilePictureView = (FacebookImageView) findViewById(R.loginactivity.iv_profile_pic);

		authButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View button) {
				beforeMakingRequest();
				makeLoginRequest();
			}
		});
	}

	private void updateUserAtServer(ParseUser user, String id, String name) {
		user.put("facebookId", id);
		user.put("name", name);
		user.saveEventually();
	}

	private void populateFacebookHolderInformationRequest(final ParseUser user) {
		final Session session = ParseFacebookUtils.getSession();

		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser graphUser,
							Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (graphUser != null) {
								facebook.setUserId(graphUser.getId());
								profilePictureView.loadFacebookId(facebook.getUserId());
								facebook.setUserName(graphUser.getName());

								updateUserAtServer(user, graphUser.getId(),
										graphUser.getName());
								
								facebook.getFriends(LoginActivity.this);

								rlProfile.startAnimation(AnimationHelper
										.getFadeInAnimation());
								rlProfile.setVisibility(View.VISIBLE);
							}
						}

						FacebookRequestError error = response.getError();
						if (error != null) {
							if ((error.getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (error.getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(Constants.TAG,
										"The facebook session was invalidated.");
								onLoginError("");
							} else {
								Log.d(Constants.TAG, "Some other error: "
										+ error.getErrorMessage());
								onLoginError(getString(R.string.loginactivity_errornetwork));
							}
						}
					}
				});
		request.executeAsync();
	}

	private void beforeMakingRequest() {
		llProgress.setVisibility(View.VISIBLE);
		authButton.setEnabled(false);
	}

	private void makeLoginRequest() {
		ParseFacebookUtils.logIn(LoginActivity.this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if (user != null) {
					populateFacebookHolderInformationRequest(user);
					if (user.isNew()) {
						Log.d(Constants.TAG,
								"User signed up and logged in through Facebook!");
					} else {
						Log.d(Constants.TAG, "User logged in through Facebook!");
					}
				} else {
					Log.d(Constants.TAG,
							"Uh oh. The user cancelled the Facebook login.");
					Log.d(Constants.TAG, err.getMessage());
					err.printStackTrace();
					onLoginError("");
				}
			}
		});
	}

	private void onLoginError(String message) {
		authButton.setEnabled(true);
		pbProgress.setVisibility(View.INVISIBLE);
		tvProgress.setText(message);
	}

	private void afterRequestCompleted() {
		authButton.setEnabled(true);
		byPassLoginActivity();
	}

	private void byPassLoginActivity() {
		finish();
		startActivity(new Intent(getBaseContext(), FeedActivity.class));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	@Override
	public void beforeFriendsRequest() { }

	@Override
	public void afterFriendRequest(List<FacebookFriend> friends) {
		afterRequestCompleted();
	}

	@Override
	public void onFriendRequestError() {
		// A lista de amigos pode ser populada depois.
		// Mesmo assim quero autorizar o user.
		afterRequestCompleted();
	}
}
