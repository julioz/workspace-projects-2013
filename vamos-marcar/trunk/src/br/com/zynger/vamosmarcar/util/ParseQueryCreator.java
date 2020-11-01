package br.com.zynger.vamosmarcar.util;

import br.com.zynger.vamosmarcar.model.Event;

import com.parse.ParseQuery;


public class ParseQueryCreator {

	private static ParseQueryCreator instance;

	private ParseQueryCreator() {
	}

	public static ParseQueryCreator getInstance() {
		if (instance == null) {
			instance = new ParseQueryCreator();
		}
		return instance;
	}
	
	public ParseQuery<Event> queryEvents() {
		ParseQuery<Event> query = ParseQuery.getQuery(Event.KEY);
		query.include(Event.KEY_HOST);
		return query;
	}

}
