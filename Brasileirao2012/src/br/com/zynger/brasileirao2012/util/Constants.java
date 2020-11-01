package br.com.zynger.brasileirao2012.util;

import android.content.Context;
import android.content.Intent;
import br.com.zynger.brasileirao2012.enums.Division;

public class Constants {

	public static final String INTENT_DIVISION = "INTENTDIVISION";
	public static final String INTENT_MYCLUB = "INTENTMYCLUB";
	public static String INTENT_CLUBACRONYM = "INTENTCLUBACRONYM";
	public static String INTENT_MOVES = "INTENTMOVES";
	public static final String INTENT_MATCH = "INTENTMATCH";
	
	public static final String PAGER_POSITION = "PAGER_PAGE";
	
	public static final String SERVICE_PATH = "br.com.zynger.brasileirao2012.service.RealTimeMatchService";
	
	public static final int YEAR = 2013;
	public static final String TIMEZONE = "GMT-3:00";
	public static final String APP_WEBSITE = "https://play.google.com/store/apps/details?id=br.com.zynger.brasileirao2012";
	public static final String FACEBOOK_FANPAGE = "http://www.facebook.com/brasileiraoapp";
	
	public static Intent getIntentForDivision(Context context, Class<?> clazz, Division division) {
		Intent intent = new Intent(context, clazz);
		intent.putExtra(Constants.INTENT_DIVISION, division);
		return intent;
	}
	
	public final static String FONT_HELVETICA = "fonts/helvetica-medium.TTF";

	public final static String TAG = "brasileirao";
	
	public static final Integer THIRDDIVISION_GROUP_A = 0;
	public static final Integer THIRDDIVISION_GROUP_B = 1;
}
