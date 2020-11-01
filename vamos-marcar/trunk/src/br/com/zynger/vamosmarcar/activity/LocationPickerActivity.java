package br.com.zynger.vamosmarcar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.asynctask.GetLocationFromAddressTask;
import br.com.zynger.vamosmarcar.asynctask.GetLocationFromAddressTask.Geocoddable;
import br.com.zynger.vamosmarcar.model.MapLocation;

import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.view.Window;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationPickerActivity extends BaseActivity implements Geocoddable, OnInfoWindowClickListener, OnMapClickListener {

	private static final int ANIMATION_DURATION = 600;

	private HashMap<Marker, MapLocation> markersMap;
	
	private GoogleMap googleMap;
	private LinearLayout llInput;
	private EditText etAddress;
	private ImageButton ibSearch;
	private ImageButton ibShowSearchBar;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);    
	    setSupportProgressBarIndeterminateVisibility(false);
		setContentView(R.layout.activity_locationpicker);
		
		loadViews();
		setClickListeners();
	}
	
	private void loadViews() {
		llInput = (LinearLayout) findViewById(R.locationpickeractivity.ll_input);
		etAddress = (EditText) findViewById(R.locationpickeractivity.et_address);
		ibSearch = (ImageButton) findViewById(R.locationpickeractivity.ib_search);
		ibShowSearchBar = (ImageButton) findViewById(R.locationpickeractivity.ib_showsearch);
		
		googleMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.locationpickeractivity.mapfragment)).getMap();
	}
	
	private void setClickListeners() {
		ibSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View imagebutton) {
				etAddress.setError(null);
				
				String address = etAddress.getText().toString().trim();
				if (address.length() > 0) {
					new GetLocationFromAddressTask(LocationPickerActivity.this).execute(address);
				} else {
					etAddress.setError(getString(R.string.locationpicker_emptysearchfield));
				}
			}
		});
		
		ibShowSearchBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View imagebutton) {
				etAddress.setError(null);
				ibShowSearchBar.setVisibility(View.GONE);
				llInput.setVisibility(View.VISIBLE);
				ObjectAnimator animSearchBarOnScreen = ObjectAnimator.ofFloat(llInput, "translationX", -1.3f*llInput.getWidth(), 0f);
				animSearchBarOnScreen.setDuration(ANIMATION_DURATION);
				animSearchBarOnScreen.start();
			}
		});
		
		googleMap.setOnMapClickListener(this);
	}
	
	private void finishWithLocation(double latitude, double longitude, String address) {
		double[] location = new double[2];
		location[0] = latitude;
		location[1] = longitude;

		Intent intent = getIntent();
		intent.putExtra(Constants.INTENT_LOCATION_POSITION, location);
		intent.putExtra(Constants.INTENT_LOCATION_ADDRESS, address);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onPreExecution() {
		setSupportProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void onGeocodingComplete(ArrayList<MapLocation> locations) {
		setSupportProgressBarIndeterminateVisibility(false);
		googleMap.clear();
		markersMap = new HashMap<Marker, MapLocation>();
		
		if (locations.size() == 0) {
			showNoLocationsFoundMessage();
		} else if (locations.size() == 1) {
			setSingleLocationMarker(locations.get(0));
		} else {
			showMultipleLocationChooser(locations);
		}
		
		googleMap.setOnInfoWindowClickListener(this);
	}

	private void showNoLocationsFoundMessage() {
		LatLng mapCenter = googleMap.getCameraPosition().target;
		CameraPosition cameraPosition = new CameraPosition.Builder().zoom(1).target(mapCenter).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		
		etAddress.setError(getString(R.string.locationpicker_placenotfound));
	}

	private void setSingleLocationMarker(final MapLocation location) {
		Marker marker = addMarker(location);
		setMapCamera(location.getLatLng());
		marker.showInfoWindow();
	}

	private void showMultipleLocationChooser(ArrayList<MapLocation> locations) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (MapLocation location : locations) {
			Marker marker = addMarker(location);
			builder.include(marker.getPosition());
		}
		
		LatLngBounds bounds = builder.build();
		int padding = 40; // offset from edges of the map in pixels
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
		googleMap.animateCamera(cameraUpdate);
	}
	
	private Marker addMarker(MapLocation mapLocation) {
		Marker marker = googleMap.addMarker(new MarkerOptions().position(mapLocation.getLatLng())
				.title(mapLocation.getName())
				.snippet(getString(R.string.locationpicker_infowindowsnippet_clicktoselect)));
		markersMap.put(marker, mapLocation);
		return marker;
	}
	
	protected void setMapCamera(LatLng target) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(target)
        .zoom(17).build();
		
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	@Override
	public void onGeocodingError() {
		setSupportProgressBarIndeterminateVisibility(false);
		Toast.makeText(this, R.string.locationpicker_geocodingerror, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		MapLocation location = markersMap.get(marker);
		
		if (location != null) {
			LatLng position = location.getLatLng();
			finishWithLocation(position.latitude, position.longitude, location.getAddress());
		}
	}

	@Override
	public void onMapClick(LatLng position) {
		if (llInput.getVisibility() == View.VISIBLE) {
			ObjectAnimator animSearchBarOffScreen = ObjectAnimator.ofFloat(llInput, "translationX", 0f, -1.3f*llInput.getWidth());
			animSearchBarOffScreen.setDuration(ANIMATION_DURATION);
			animSearchBarOffScreen.addListener(new AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {}
				
				@Override
				public void onAnimationRepeat(Animator animation) {}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					llInput.setVisibility(View.GONE);
					ibShowSearchBar.setVisibility(View.VISIBLE);
					ObjectAnimator.ofFloat(ibShowSearchBar, "alpha", 0.0f, 1.0f).start();
				}
				
				@Override
				public void onAnimationCancel(Animator animation) { }
			});
			animSearchBarOffScreen.start();
		} else {
			googleMap.clear();
			markersMap = new HashMap<Marker, MapLocation>();
			
			String address = null;
			try {
				List<Address> matches = new Geocoder(this).getFromLocation(position.latitude, position.longitude, 1);
				if (!matches.isEmpty()) {
					address = matches.get(0).getAddressLine(0);
				}

				setSingleLocationMarker(new MapLocation(address, position));
				googleMap.setOnInfoWindowClickListener(this);
			} catch(Exception e) {
				Toast.makeText(this, R.string.locationpicker_geocodingerror, Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}
}
