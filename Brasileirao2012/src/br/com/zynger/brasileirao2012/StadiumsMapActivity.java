package br.com.zynger.brasileirao2012;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import br.com.zynger.brasileirao2012.data.StadiumsDB;
import br.com.zynger.brasileirao2012.model.Stadium;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twotoasters.clusterkraf.ClusterPoint;
import com.twotoasters.clusterkraf.Clusterkraf;
import com.twotoasters.clusterkraf.InputPoint;
import com.twotoasters.clusterkraf.MarkerOptionsChooser;
import com.twotoasters.clusterkraf.OnInfoWindowClickDownstreamListener;
import com.twotoasters.clusterkraf.OnMarkerClickDownstreamListener;
import com.twotoasters.clusterkraf.Options;
import com.twotoasters.clusterkraf.Options.ClusterClickBehavior;
import com.twotoasters.clusterkraf.Options.SinglePointClickBehavior;

public class StadiumsMapActivity extends FragmentActivity {
	public static final String LIMITATION = "STADIUM_LIMITED";
	
	private StadiumsDB stadiumsDB;
	
	private String stadiumLimited;
	
	private GoogleMap googleMap;
	private Clusterkraf clusterkraf;
	private ToggleButton satelliteToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stadiumsmapactivity);
		stadiumsDB = StadiumsDB.getInstance(this);
		
		googleMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.stadiumsmapactivity.mapfragment)).getMap();
        if (googleMap == null) {
            Toast.makeText(this, "Google Maps not available", 
                Toast.LENGTH_LONG).show();
            finish();
        }
        
		satelliteToggle = (ToggleButton) findViewById(R.stadiumsmapactivity.satellite_toggle);
		
		stadiumLimited = getIntent().getStringExtra(LIMITATION);
		
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		satelliteToggle.setChecked(false);
		
		satelliteToggle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isHybrid = googleMap.getMapType() == GoogleMap.MAP_TYPE_HYBRID;
				googleMap.setMapType(isHybrid ? GoogleMap.MAP_TYPE_NORMAL : GoogleMap.MAP_TYPE_HYBRID);
			}
		});
		
		setCameraPosition();
		
		ArrayList<InputPoint> inputPoints = buildInputPoints(stadiumsDB.getStadiums());
		
		if (inputPoints.size() < 1) {
			Toast.makeText(this, R.string.stadiumsactivity_stadiumnotfound, Toast.LENGTH_SHORT).show();
	    	finish();
		}

		Options options = getClusterKrafOptions();
		this.clusterkraf = new Clusterkraf(googleMap, options, inputPoints);
	}

	private com.twotoasters.clusterkraf.Options getClusterKrafOptions() {
		com.twotoasters.clusterkraf.Options options = new com.twotoasters.clusterkraf.Options();
		options.setSinglePointClickBehavior(SinglePointClickBehavior.SHOW_INFO_WINDOW);
		options.setClusterClickBehavior(ClusterClickBehavior.ZOOM_TO_BOUNDS);
		
		options.setOnMarkerClickDownstreamListener(new OnMarkerClickDownstreamListener() {
			@Override
			public boolean onMarkerClick(Marker marker, ClusterPoint clusterPoint) {
				clusterkraf.showInfoWindow(marker, clusterPoint);
				return true;
			}
		});
		
		options.setOnInfoWindowClickDownstreamListener(new OnInfoWindowClickDownstreamListener() {
			@Override
			public boolean onInfoWindowClick(Marker marker, ClusterPoint clusterPoint) {
				if (clusterPoint.size() == 1) {
					Stadium stadium = (Stadium)clusterPoint.getPointAtOffset(0).getTag();
					startStadiumActivity(stadium);
					return true;
				}
				return false;
			}
		});
		
		options.setMarkerOptionsChooser(new MarkerOptionsChooser() {
			@Override
			public void choose(MarkerOptions markerOptions, ClusterPoint clusterPoint) {
				int clusterSize = clusterPoint.size();
				boolean isCluster = clusterSize > 1;
				String title, snippet;
				BitmapDescriptor icon;
				
				if (!isCluster) {
					Stadium stadium = (Stadium)clusterPoint.getPointAtOffset(0).getTag();

					title = stadium.getNick();
					snippet = stadium.getCity();
					icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_point);
				} else {
					title = clusterSize + " " + getString(R.string.stadiumsactivity_title).toLowerCase(Locale.getDefault());
					int clusterMaxSize = Math.min(clusterSize, 3);
					snippet = new String();
					for (int i = 0; i < clusterMaxSize; i++) {
						Stadium stadium = (Stadium) clusterPoint.getPointAtOffset(i).getTag();
						snippet += stadium.getNick() + (i != clusterMaxSize-1 ? ", " : "");
					}
					if (clusterSize > 3) {
						snippet += "…";
					}
					icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_cluster);
				}
				
				markerOptions.title(title).snippet(snippet).icon(icon);
			}
		});
		
		return options;
	}

	private void startStadiumActivity(Stadium stadium) {
		Intent it = new Intent(this, StadiumsActivity.class);
		
		it.putExtra(StadiumsActivity.INTENT_NAME, stadium.getName());
		it.putExtra(StadiumsActivity.INTENT_NICK, stadium.getNick());
		it.putExtra(StadiumsActivity.INTENT_CITY, stadium.getCity());
		
		String cap = String.valueOf(stadium.getCapacity());
		cap = cap.substring(0, cap.length()-3)+"."+cap.substring(cap.length()-3);
		it.putExtra(StadiumsActivity.INTENT_CAPACITY, cap);
		
		it.putExtra(StadiumsActivity.INTENT_FOUNDATION, stadium.getFoundation().get(Calendar.DATE) + "/" 
		+ stadium.getFoundation().get(Calendar.MONTH) + "/" + stadium.getFoundation().get(Calendar.YEAR));
		it.putExtra(StadiumsActivity.INTENT_WIDTH, String.valueOf(stadium.getWidth()));
		it.putExtra(StadiumsActivity.INTENT_HEIGHT, String.valueOf(stadium.getHeight()));
		it.putExtra(StadiumsActivity.INTENT_DESCRIPTION, stadium.getDescription());
		it.putExtra(StadiumsActivity.INTENT_URL, stadium.getImgUrl());
		startActivity(it);		
	}

	private ArrayList<InputPoint> buildInputPoints(ArrayList<Stadium> stadiums) {
		ArrayList<InputPoint> inputPoints = new ArrayList<InputPoint>(stadiums.size());
        for (Stadium stadium : stadiums) {
        	if (stadiumLimited == null || stadium.getNick().contains(stadiumLimited)
	    			|| stadium.getName().contains(stadiumLimited)) {
				inputPoints.add(new InputPoint(stadium.getLatLng(), stadium));
			}
        }
        return inputPoints;
    }
	
	private void setCameraPosition() {
		CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(-15.783611, -47.899167))
        .zoom(4).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
}