package br.com.zynger.vamosmarcar.util;

import android.content.Context;

public class Util {

	public static int getPixelsFromDp(Context context, int dps) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int pixels = (int) (dps * scale + 0.5f);
		return pixels;
	}

	public static boolean matchString(String value, String keyword) {
		if (value == null || keyword == null)
			return false;
		if (keyword.length() > value.length())
			return false;

		int valueIndex = 0, keywordIndex = 0;
		do {
			if (keyword.charAt(keywordIndex) == value.charAt(valueIndex)) {
				valueIndex++;
				keywordIndex++;
			} else if (keywordIndex > 0) {
				break;
			} else {
				valueIndex++;
			}
		} while (valueIndex < value.length() && keywordIndex < keyword.length());

		return keywordIndex == keyword.length();
	}
	
	public static boolean isConnected(Context context) {
		return ConnectionHelper.isConnected(context);
	}

}
