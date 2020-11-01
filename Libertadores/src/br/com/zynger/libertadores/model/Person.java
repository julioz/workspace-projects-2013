package br.com.zynger.libertadores.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.com.zynger.libertadores.HomeActivity;

public class Person {
	
	private final String JSON_POSITION = "position";
	private final String JSON_NUMBER = "number";
	private final String JSON_NAME = "name";
	private final String JSON_BIRTHDATE_DAY = "birthdate_day";
	private final String JSON_BIRTHDATE_MONTH = "birthdate_month";
	private final String JSON_BIRTHDATE_YEAR = "birthdate_year";
	private final String JSON_NATIONALITY = "nationality";
	private final String JSON_HEIGHT = "height";
	private final String JSON_WEIGHT = "weight";
	
	public final static Integer NUMBER_COACH = -1;
	
	private TeamMember position;
	private Integer number;
	private String name;
	private GregorianCalendar birthdate;
	private String nationality;
	private String height;
	private String weight;
	
	public Person() { }
	
	public Person(JSONObject json) throws JSONException {		
		String strPosition = json.getString(JSON_POSITION);
		for (TeamMember teamMember : TeamMember.values()) {
			if(strPosition.equals(teamMember.toString())){
				setPosition(teamMember);
				break;
			}
		}
		
		if(json.has(JSON_NUMBER)) setNumber(json.getInt(JSON_NUMBER));
		if(json.has(JSON_NAME)) setName(json.getString(JSON_NAME));
		if(json.has(JSON_BIRTHDATE_DAY)){
			setBirthdate(new GregorianCalendar());
			getBirthdate().set(Calendar.DAY_OF_MONTH, json.getInt(JSON_BIRTHDATE_DAY));
			getBirthdate().set(Calendar.MONTH, json.getInt(JSON_BIRTHDATE_MONTH));
			getBirthdate().set(Calendar.YEAR, json.getInt(JSON_BIRTHDATE_YEAR));
		}
		if(json.has(JSON_NATIONALITY)) setNationality(json.getString(JSON_NATIONALITY));
		if(json.has(JSON_HEIGHT)) setHeight(json.getString(JSON_HEIGHT));
		if(json.has(JSON_WEIGHT)) setWeight(json.getString(JSON_WEIGHT));
	}
	
	public TeamMember getPosition() {
		return position;
	}
	
	public void setPosition(TeamMember position) {
		this.position = position;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public GregorianCalendar getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(GregorianCalendar birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getHeight() {
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}

	public JSONObject toJson() {
		try{			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put(JSON_POSITION, getPosition().toString());
			jsonObj.put(JSON_NUMBER, getNumber());
			jsonObj.put(JSON_NAME, getName());
			if(getBirthdate() != null){			
				jsonObj.put(JSON_BIRTHDATE_DAY, getBirthdate().get(Calendar.DAY_OF_MONTH));
				jsonObj.put(JSON_BIRTHDATE_MONTH, getBirthdate().get(Calendar.MONTH));
				jsonObj.put(JSON_BIRTHDATE_YEAR, getBirthdate().get(Calendar.YEAR));
			}else{
				jsonObj.put(JSON_BIRTHDATE_DAY, null);
				jsonObj.put(JSON_BIRTHDATE_MONTH, null);
				jsonObj.put(JSON_BIRTHDATE_YEAR, null);
			}
			jsonObj.put(JSON_NATIONALITY, getNationality());
			jsonObj.put(JSON_HEIGHT, getHeight());
			jsonObj.put(JSON_WEIGHT, getWeight());
			
			return jsonObj;
		}catch(JSONException e){
			Log.e(HomeActivity.TAG, e.toString());
			return null;
		}
	}
	
}
