package br.com.zynger.guesstheclub.util;

import android.content.Context;

public class ResourceManager {
	private static final String APP_PACKAGE = "br.com.zynger.guesstheclub";
	public static final String PATH_DRAWABLE = "drawable";
	public static final String PATH_STRING = "string";
	private static ResourceManager instance;
	
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	public int getResourceFromIdentifier(Context c, String path, String nm_res) {
		int resID = c.getResources().getIdentifier(nm_res, path, APP_PACKAGE);
		return resID;
	}
}
