package br.com.zynger.vamosmarcar;

import android.app.Application;
import br.com.zynger.vamosmarcar.model.Comment;
import br.com.zynger.vamosmarcar.model.Event;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

public class VamosMarcarApp extends Application { 

    @Override
    public void onCreate() { 
        super.onCreate();
        registerParseObjectSubclasses();
        Parse.initialize(this, getString(R.string.parse_applicationid), getString(R.string.parse_clientid));
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
        ParseAnalytics.trackAppOpened(null);
    }

	private void registerParseObjectSubclasses() {
		ParseObject.registerSubclass(Event.class);
		ParseObject.registerSubclass(Comment.class);
	}
}