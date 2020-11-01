package br.com.zynger.brasileirao2012.util;

import android.content.Intent;

public class CalendarHelper {
	
	public static Intent getEventIntent(long beginTime, long endTime, String title, String location) {
		Intent intent = new Intent(Intent.ACTION_EDIT);
	    intent.setType("vnd.android.cursor.item/event");
	    intent.putExtra("beginTime", beginTime);
	    intent.putExtra("allDay", false);
	    intent.putExtra("transparency", 0);
	    intent.putExtra("eventLocation", location); 
	    intent.putExtra("endTime", endTime);
	    intent.putExtra("title", title);
	    return intent;
	}
	

}
