package br.com.zynger.vamosmarcar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import br.com.zynger.vamosmarcar.Constants;
import br.com.zynger.vamosmarcar.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationViewerActivity extends LocationPickerActivity {
	
	private String address;
	private LatLng location;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		address = getIntent().getStringExtra(Constants.INTENT_LOCATION_ADDRESS);
		double[] intentArray = getIntent().getDoubleArrayExtra(Constants.INTENT_LOCATION_POSITION);
		double latitude = intentArray[0];
		double longitude = intentArray[1];
		location = new LatLng(latitude, longitude);
		
		setViewer();
	}
	
	private void setViewer() {
		LinearLayout llInput = (LinearLayout) findViewById(R.locationpickeractivity.ll_input);
		llInput.setVisibility(View.GONE);
		
		GoogleMap googleMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.locationpickeractivity.mapfragment)).getMap();
		
		Marker marker = googleMap.addMarker(new MarkerOptions().position(location).title(address));
		setMapCamera(location);
		marker.showInfoWindow();
	}
	
	@Override
	public void onMapClick(LatLng position) {
	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {
	}
}
