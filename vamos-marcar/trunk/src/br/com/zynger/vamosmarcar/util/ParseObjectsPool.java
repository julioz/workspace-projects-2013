package br.com.zynger.vamosmarcar.util;

import com.parse.ParseObject;


public class ParseObjectsPool {
	
	private static ParseObjectsPool instance;
	
	private ParseObject objectToPass;

	private ParseObjectsPool() {
	}

	/**
	 * Because ParseObject cannot be passed through intents,
	 * as Parcelables or Serializables, this dirty hack is the
	 * way we're sharing those instances. Bad, I know :-(
	 * @return instance of the pool
	 */
	public static ParseObjectsPool getInstance() {
		if (instance == null) {
			instance = new ParseObjectsPool();
		}
		return instance;
	}
	
	public void setObjectToPass(ParseObject object) {
		this.objectToPass = object;
	}
	
	public ParseObject getObjectToPass() {
		return objectToPass;
	}

}
