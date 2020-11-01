package br.com.zynger.brasileirao2012.data;

import java.util.ArrayList;

import android.content.Context;
import br.com.zynger.brasileirao2012.model.Stadium;
import br.com.zynger.brasileirao2012.xml.StadiumsParser;

public class StadiumsDB {
	private static StadiumsDB ref;
	private ArrayList<Stadium> stadiums;
	
	private StadiumsDB(Context context){
		stadiums = generateStadiums(context);
	}
	
	public static StadiumsDB getInstance(Context context){
		if (ref == null) ref = new StadiumsDB(context);
		return ref;
	}
	
	private ArrayList<Stadium> generateStadiums(Context context) {
		return new StadiumsParser().getStadiums(context);
	}
	
	public ArrayList<Stadium> getStadiums() {
		return stadiums;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
