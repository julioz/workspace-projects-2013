package br.com.zynger.libertadores.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import br.com.zynger.libertadores.R;
import br.com.zynger.libertadores.model.Person;
import br.com.zynger.libertadores.model.TeamMember;

public class RostersExpandableListViewAdapter extends BaseExpandableListAdapter {
	
	private final int LAYOUTRESOURCE_GROUP = R.layout.rostersexpandablelistview_grouprow;
	private final int LAYOUTRESOURCE_CHILD = R.layout.rostersexpandablelistview_childrow;

	private Context context;
	private final LayoutInflater mInflater;
	private HashMap<TeamMember, ArrayList<Person>> roster;
	
	public RostersExpandableListViewAdapter(Context context, ArrayList<Person> rosterRaw) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		
		roster = new HashMap<TeamMember, ArrayList<Person>>();
		for (Person person : rosterRaw) {
			if(roster.get(person.getPosition()) == null){
				roster.put(person.getPosition(), new ArrayList<Person>());
			}
			roster.get(person.getPosition()).add(person);
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return roster.get(TeamMember.values()[groupPosition]).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			row = mInflater.inflate(LAYOUTRESOURCE_CHILD, parent, false);
		}

		TextView tvNumber = (TextView) row.findViewById(R.rosterselv_childrow.tv_number);
		TextView tvName = (TextView) row.findViewById(R.rosterselv_childrow.tv_name);
		TextView tvBirthdate = (TextView) row.findViewById(R.rosterselv_childrow.tv_birthdate);
		TextView tvNationality = (TextView) row.findViewById(R.rosterselv_childrow.tv_nationality);
		TextView tvHeigth = (TextView) row.findViewById(R.rosterselv_childrow.tv_height);
		TextView tvWeigth = (TextView) row.findViewById(R.rosterselv_childrow.tv_weigth);
		
		tvNumber.setText("");
		tvName.setText("");
		tvBirthdate.setText("");
		tvNationality.setText("");
		tvHeigth.setText("");
		tvWeigth.setText("");
		
		Person person = (Person) getChild(groupPosition, childPosition);

		if(person.getNumber() == Person.NUMBER_COACH){
			tvNumber.setText(getContext().getString(R.string.rostersactivity_coach_acronym));
		}else{			
			setTextIfNotNull(tvNumber, person.getNumber());
		}
		setTextIfNotNull(tvName, person.getName());
		if(person.getBirthdate() != null){			
			String birthdate = getFormattedDateString(person.getBirthdate());
			tvBirthdate.setText(birthdate);
		}
		String nationality = person.getNationality();
		if(nationality != null && nationality.length() > 3) nationality = nationality.substring(0, 3).toUpperCase();
		setTextIfNotNull(tvNationality, nationality);
		setTextIfNotNull(tvHeigth, person.getHeight());
		setTextIfNotNull(tvWeigth, person.getWeight());

		return row;
	}

	private void setTextIfNotNull(TextView tv, Object obj) {
		String s = new String();
		if(obj != null) s = String.valueOf(obj);
		tv.setText(s);
	}
	
	private String getFormattedDateString(GregorianCalendar date) {
		String lang = Locale.getDefault().getLanguage();
		
		String str = new String();
		int day_of_month = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		
		
		if(lang.contains("en")){
			str = str.concat(addZeroIfNecessary(month));
			str = str.concat("/");
			str = str.concat(addZeroIfNecessary(day_of_month));
		}else{
			str = str.concat(addZeroIfNecessary(day_of_month));
			str = str.concat("/");
			str = str.concat(addZeroIfNecessary(month));			
		}
		str = str.concat("/");
		str = str.concat(String.valueOf(year));
		
		return str;
	}
	
	private String addZeroIfNecessary(int value){
		return value < 10 ? '0' + String.valueOf(value) : String.valueOf(value);
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return roster.get(TeamMember.values()[groupPosition]).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return roster.get(TeamMember.values()[groupPosition]);
	}

	@Override
	public int getGroupCount() {
		return roster.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			v = mInflater.inflate(LAYOUTRESOURCE_GROUP, parent, false);
		}

		TextView groupName = (TextView) v.findViewById(R.rosterselv_grouprow.tv_positionname);

		Person person = (Person) getChild(groupPosition, 0);
		if(person != null) groupName.setText(
				getContext().getString(
						getResourceIdForPositionString(person.getPosition())).toUpperCase());

		return v;
	}

	private int getResourceIdForPositionString(TeamMember position) {
		if(position == TeamMember.COACH) return R.string.rostersactivity_coach;
		else if(position == TeamMember.DEFENDER) return R.string.rostersactivity_defender;
		else if(position == TeamMember.MIDFIELDER) return R.string.rostersactivity_midfielder;
		else if(position == TeamMember.FORWARD) return R.string.rostersactivity_forward;
		else if(position == TeamMember.GOALKEEPER) return R.string.rostersactivity_goalkeeper;
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	public Context getContext() {
		return context;
	}
	
}
