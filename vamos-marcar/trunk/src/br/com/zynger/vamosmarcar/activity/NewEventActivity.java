package br.com.zynger.vamosmarcar.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.TreeSet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import br.com.zynger.vamosmarcar.R;
import br.com.zynger.vamosmarcar.activity.BaseActivity.FriendPickable;
import br.com.zynger.vamosmarcar.activity.BaseActivity.LocationPickable;
import br.com.zynger.vamosmarcar.model.Event;
import br.com.zynger.vamosmarcar.model.Event.EventAudience;
import br.com.zynger.vamosmarcar.model.Event.EventType;
import br.com.zynger.vamosmarcar.model.FacebookFriend;
import br.com.zynger.vamosmarcar.util.Util;
import br.com.zynger.vamosmarcar.view.FacebookImageView;
import br.com.zynger.vamosmarcar.view.FlowLayout;
import br.com.zynger.vamosmarcar.view.FlowRadioGroup;
import br.com.zynger.vamosmarcar.view.GoogleMapImageView;
import br.com.zynger.vamosmarcar.view.RobotoTextView;
import br.com.zynger.vamosmarcar.view.ScheduleDateLayout;
import br.com.zynger.vamosmarcar.view.ScheduleDateLayout.ScheduleDateTimeSettable;
import br.com.zynger.vamosmarcar.view.ScheduleTimeLayout;
import br.com.zynger.vamosmarcar.view.ScheduleTimeLayout.ScheduleTimeDeletable;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog.OnDateSetListener;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseUser;

public class NewEventActivity extends BaseActivity implements FriendPickable, LocationPickable,
				OnDateSetListener, ScheduleTimeDeletable, ScheduleDateTimeSettable {
	
	private int FRIEND_IMAGE_SIZE = 48;
	private int friendImageSize;

	private SparseArray<String> peopleIds;
	private LatLng selectedLocation;
	private TreeSet<Long> timesSet;
	
	private EditText etTitle;
	private EditText etDescription;
	private FlowRadioGroup flowRadioGroupType;
	private RadioGroup rgAudience;
	private FlowLayout flPeople;
	private OnClickListener personClickListener;
	private LinearLayout llPeopleEmpty;
	private Button btSelectPlace;
	private RobotoTextView tvPlace;
	private GoogleMapImageView gmivMap;
	
	private Button btCalendar;
	private LinearLayout llTimes;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		friendImageSize = Util.getPixelsFromDp(this, FRIEND_IMAGE_SIZE);
		
		setContentView(R.layout.activity_newevent);
		peopleIds = new SparseArray<String>();
		timesSet = new TreeSet<Long>();
		
		loadViews();
		setClickListeners();
	}

	private void loadViews() {
		etTitle = (EditText) findViewById(R.neweventactivity.et_title);
		etDescription = (EditText) findViewById(R.neweventactivity.et_description);
		flowRadioGroupType = (FlowRadioGroup) findViewById(R.neweventactivity.frg_typeofevent);
		rgAudience = (RadioGroup) findViewById(R.neweventactivity.rg_audience);
		flPeople = (FlowLayout) findViewById(R.neweventactivity.fl_people);
		llPeopleEmpty = (LinearLayout) findViewById(R.neweventactivity.ll_peopleempty);
		tvPlace = (RobotoTextView) findViewById(R.neweventactivity.tv_place);
		gmivMap = (GoogleMapImageView) findViewById(R.neweventactivity.gmiv_map);
		
		setTypeFlowRadioGroup();
		setAudienceRadioGroup();
		
		btSelectPlace = (Button) findViewById(R.neweventactivity.bt_placeempty);
		Drawable mapDrawable = getResources().getDrawable(R.drawable.ic_action_map);
		mapDrawable.setBounds(0, 0, (int)(mapDrawable.getIntrinsicWidth() * 0.8), 
		                         (int)(mapDrawable.getIntrinsicHeight() * 0.8));
		btSelectPlace.setCompoundDrawables(null, null, mapDrawable, null);
		btSelectPlace.setTextColor(getResources().getColor(R.color.darkgray));
		btSelectPlace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startPickLocationIntent();
			}
		});
		
		initCalendarView();
		
		Button btPeopleEmpty = (Button) findViewById(R.neweventactivity.bt_peopleempty);
		Drawable invitePeopleDrawable = getResources().getDrawable(R.drawable.ic_action_next_item);
		invitePeopleDrawable.setBounds(0, 0, (int)(invitePeopleDrawable.getIntrinsicWidth() * 0.8), 
		                         (int)(invitePeopleDrawable.getIntrinsicHeight() * 0.8));
		btPeopleEmpty.setCompoundDrawables(null, null, invitePeopleDrawable, null);
		btPeopleEmpty.setTextColor(getResources().getColor(R.color.darkgray));
		btPeopleEmpty.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startPickFriendsIntent(peopleIds);
			}
		});
	}

	private void initCalendarView() {
		btCalendar = (Button) findViewById(R.neweventactivity.calendarbutton);
		llTimes = (LinearLayout) findViewById(R.neweventactivity.ll_times);
		
		btCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View button) {
				// TODO Ainda testando como funciona o calendarDatePickerDialog
				FragmentManager fm = getSupportFragmentManager();
                Calendar now = GregorianCalendar.getInstance();
                CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog
                        .newInstance(NewEventActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                calendarDatePickerDialog.show(fm, "fragment_date_picker_name");
			}
		});
	}
	
	private void setTypeFlowRadioGroup() {
		EventType[] eventTypes = EventType.values();
		String[] types = new String[eventTypes.length];
		for (int i = 0; i < EventType.values().length; i++) {
			types[i] = getString(eventTypes[i].getNameRes());
		}
		
		flowRadioGroupType.setValues(types);
	}

	private void setAudienceRadioGroup() {
		rgAudience.removeAllViews();
		for (EventAudience audience : EventAudience.values()) {
			RadioButton radioButton = new RadioButton(this);
			radioButton.setText(audience.getNameRes());
			radioButton.setId(audience.ordinal());
			
			if (audience == EventAudience.PRIVATE) {
				rgAudience.check(radioButton.getId());
				radioButton.setChecked(true);
			}
			
			rgAudience.addView(radioButton);
		}
	}

	private void setClickListeners() {
		personClickListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 0; i < flPeople.getChildCount(); i++) {
					View child = flPeople.getChildAt(i);
					if (view == child) {
						String id = peopleIds.get(i);
						FacebookFriend friend = facebook.getFriendById(id);
						if (friend != null) {
							Toast.makeText(view.getContext(), friend.getName(), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		};
		
		gmivMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startPickLocationIntent();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (flPeople.isEmpty()) {
			llPeopleEmpty.setVisibility(View.VISIBLE);
			flPeople.setVisibility(View.GONE);
		} else {
			llPeopleEmpty.setVisibility(View.GONE);
			flPeople.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.actionitem_newevent, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.actionitem_newevent.action_confirm:
	            confirmNewEvent();
	            return true;
	        case R.actionitem_newevent.action_addpeople:
	        	startPickFriendsIntent(peopleIds);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void addPersonToFlowLayout(String id) {
		FacebookImageView personIv = new FacebookImageView(this);
		personIv.setLayoutParams(new FlowLayout.LayoutParams(friendImageSize , friendImageSize));
		personIv.setOnClickListener(personClickListener);
		flPeople.addView(personIv);
		personIv.loadFacebookId(id);
	}
	
	private void confirmNewEvent() {
		// TODO completar campos
		final String title = etTitle.getText().toString();
		final String description = etDescription.getText().toString();
		final EventType eventType = EventType.values()[flowRadioGroupType.getSelectedItemPosition()];
		final EventAudience eventAudience = EventAudience.values()[rgAudience.getCheckedRadioButtonId()];

		String message = "Title: " + title + "\nDesc.: " + description;
		message = message + "\nType: " + eventType;
		message = message + "\nAudience: " + eventAudience;
		if (selectedLocation != null) {
			message = message + "\nLocation: " + selectedLocation.latitude + ", " + selectedLocation.longitude;
		}
		message = message + "\n" + peopleIds.size() + " friends selected.";
		
		if (isFormValid()) {
			message = message + "\n**** VALIDO ****";
			Event event = new Event();
			event.setHost(ParseUser.getCurrentUser());
			event.setTitle(title);
			event.setDescription(description);
			event.setType(eventType);
			event.setAudience(eventAudience);
			event.setLatitude(selectedLocation.latitude);
			event.setLongitude(selectedLocation.longitude);
			
			List<String> participants = new ArrayList<String>();
			for (int i = 0; i < peopleIds.size(); i++) {
				participants.add(peopleIds.valueAt(i));
			}
			event.setParticipants(participants);
			
			if (!isDebug()) {
				event.saveEventually();
			}
			finish();
		} else {
			message = message + "\n**** INVALIDO ****";
		}
		
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		
		toastTimesQueue();
	}
	
	private boolean isFormValid() {
		return isTitleValid() && isScheduleValid();
	}
	
	private boolean isTitleValid() {
		if (etTitle.getText().toString().trim().length() == 0) {
			etTitle.setError(getString(R.string.neweventactivity_error_emptytitle));
			return false;
		} else if (selectedLocation == null) {
			Toast.makeText(this, R.string.neweventactivity_error_emptylocation, Toast.LENGTH_SHORT).show();
			// TODO: Colocar algum enfoque na escolha da posicao ou usar um crouton vermelho para os erros da activity
			return false;
		} else {
			etTitle.setError(null);
			return true;
		}
	}
	
	private boolean isScheduleValid() {
		// TODO: apos implementar a tabela de horarios
		return true;
	}

	@Override
	public void friendsPicked(String[] friendsIds) {
		flPeople.removeAllViews();
		peopleIds.clear();
		
		for (int index = 0; index < friendsIds.length; index++) {
			String id = friendsIds[index];
			addPersonToFlowLayout(id);
			peopleIds.put(index, id);
		}
	}

	@Override
	public void locationPicked(LatLng latlng, String address) {
		selectedLocation = latlng;
		gmivMap.loadLocation(latlng);
		tvPlace.setText(address);
		
		btSelectPlace.setVisibility(View.GONE);
		tvPlace.setVisibility(View.VISIBLE);
		gmivMap.setVisibility(View.VISIBLE);
	}
	
	private void updateTimesLayout() {
		llTimes.removeAllViews();
		
		for (Long millis : timesSet) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(millis);
			if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
				ScheduleDateLayout schedDateView = new ScheduleDateLayout(this, calendar);
				llTimes.addView(schedDateView);
			} else {
				ScheduleTimeLayout schedTimeView = new ScheduleTimeLayout(this, calendar, this);
				llTimes.addView(schedTimeView);
			}
		}
	}

	@Override
	public void onDateSet(CalendarDatePickerDialog dialog, int year,
			int monthOfYear, int dayOfMonth) {
//		currentSelectedTime = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		GregorianCalendar currentSelectedTime = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		
        Long selectedMillis = Long.valueOf(currentSelectedTime.getTimeInMillis());
        timesSet.add(selectedMillis);
        updateTimesLayout();
	}
	
	// TODO: remove this function - just for debugging
	private void toastTimesQueue() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
		for (Long timeInMillis : timesSet) {
			Calendar gc = GregorianCalendar.getInstance();
			gc.setTimeInMillis(timeInMillis);
			if (gc.get(Calendar.HOUR_OF_DAY) == 0 && gc.get(Calendar.MINUTE) == 0) continue;
			sb.append(sdf.format(gc.getTime()) + "\n");
		}
		Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onScheduleTimeSet(Calendar calendar) {
		// TODO Auto-generated method stub
		timesSet.add(calendar.getTimeInMillis());
		updateTimesLayout();
	}
	
	@Override
	public void onScheduleTimeDeleted(ScheduleTimeLayout scheduleTimeView) {
		llTimes.removeView(scheduleTimeView);
	}
}
