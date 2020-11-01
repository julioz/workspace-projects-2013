package br.com.zynger.vamosmarcar;

public class Constants {
	
	public final static String TAG = "VamosMarcar";
	
	public static final int PICK_FRIENDS_REQUEST = 1;
	public static final int PICK_LOCATION_REQUEST = 2;
	
	private final static String INTENT_NAMES = "br.com.zynger.vamosmarcar.";
	public static final String INTENT_FRIENDS = INTENT_NAMES + "FriendsIdList";
	public static final String INTENT_FRIENDS_PRESELECTED = INTENT_NAMES + "PreselectedFriendsIdList";
	public static final String INTENT_LOCATION_POSITION = INTENT_NAMES + "LocationAttrsArray";
	public static final String INTENT_LOCATION_ADDRESS = INTENT_NAMES + "LocationAttrsAddress";

}
