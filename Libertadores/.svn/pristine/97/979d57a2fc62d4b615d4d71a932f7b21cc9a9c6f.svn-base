package br.com.zynger.libertadores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;
import br.com.zynger.libertadores.data.ClubsDB;
import br.com.zynger.libertadores.model.Club;
import br.com.zynger.libertadores.model.Headquarters;
import br.com.zynger.libertadores.util.ResourceManager;

import com.cyrilmottier.polaris.Annotation;
import com.cyrilmottier.polaris.MapViewUtils;
import com.cyrilmottier.polaris.PolarisMapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;

public class HeadquartersActivity extends MapActivity {
	
	private PolarisMapView mPolarisMapView;
	private ToggleButton satelliteToggle;
	private TreeMap<String, Club> clubs;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.headquartersactivity);
		
		clubs = ClubsDB.getSingletonObject(this).getClubs();
		
		mPolarisMapView = (PolarisMapView) findViewById(R.headquarters.mapview);
		satelliteToggle = (ToggleButton) findViewById(R.headquarters.satellite_toggle);
		
		MapController controller = mPolarisMapView.getController();
		controller.setZoom(4);
		
		satelliteToggle.setChecked(false);
		
		satelliteToggle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPolarisMapView.setSatellite(!mPolarisMapView.isSatellite());
			}
		});
		
		setAnnotations();
	}
	
	private void setAnnotations() {
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		for (Iterator<Club> it = clubs.values().iterator(); it.hasNext();) {
			Club club = (Club) it.next();
			Headquarters hq = club.getHeadquarters();

			String title = club.getName();
			String snippet = hq.getName() + " - " + hq.getCity();
			int latitude = hq.getLatitude().intValue();
			int longitude = hq.getLongitude().intValue();
			GeoPoint point = new GeoPoint(latitude, longitude);
			int badgeRes = ResourceManager.getInstance().getResourceFromIdentifier(this, ResourceManager.PATH_DRAWABLE, club.getIcon());
			Drawable marker = MapViewUtils.boundMarkerCenterBottom(getResources().getDrawable(badgeRes));

			Annotation annotation = new Annotation(point, title, snippet, marker);
			annotations.add(annotation);
		}
		
		mPolarisMapView.setAnnotations(annotations, R.drawable.ic_launcher);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    mPolarisMapView.onStart();
	}

	@Override
	protected void onStop() {
	    super.onStop();
	    mPolarisMapView.onStop();
	} 

}
