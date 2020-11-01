package br.com.zynger.vamosmarcar.model;

import com.parse.ParseUser;

public class User {
	private static final String id = "facebookId";
	private static final String name = "name";
	
	public static String getId(ParseUser user) {
		return user.getString(id);
	}
	
	public static String getName(ParseUser user) {
		return user.getString(name);
	}
}
