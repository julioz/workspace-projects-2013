package br.com.zynger.vamosmarcar.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.graphics.PorterDuff.Mode;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.R;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog.OnTimeSetListener;

public class ScheduleDateLayout extends RelativeLayout implements
		OnTimeSetListener {

	public interface ScheduleDateTimeSettable {
		void onScheduleTimeSet(Calendar calendar);
	}

	private static final String DATE_PATTERN = "dd/MM/yyyy";
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			DATE_PATTERN, Locale.getDefault());

	private Calendar time;
	private SherlockFragmentActivity activity;

	public ScheduleDateLayout(SherlockFragmentActivity activity, Calendar time) {
		super(activity);
		init(activity, time);
	}

	public ScheduleDateLayout(SherlockFragmentActivity activity,
			AttributeSet attrs, Calendar time) {
		super(activity, attrs);
		init(activity, time);
	}

	public ScheduleDateLayout(SherlockFragmentActivity activity,
			AttributeSet attrs, int defStyle, Calendar time) {
		super(activity, attrs, defStyle);
		init(activity, time);
	}

	private void init(final SherlockFragmentActivity activity, Calendar time) {
		this.activity = activity;
		this.time = time;

		LayoutInflater.from(activity).inflate(R.layout.view_scheduledate, this);

		TextView tvTime = (TextView) findViewById(R.view_scheduledate.tv_time);
		ImageButton ibNew = (ImageButton) findViewById(R.view_scheduledate.ib_new);
		ibNew.getDrawable()
				.setColorFilter(
						getResources().getColor(R.color.holo_green_dark),
						Mode.SRC_ATOP);

		tvTime.setText(simpleDateFormat.format(time.getTime()));

		ibNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View imageButton) {
				FragmentManager fm = activity.getSupportFragmentManager();
				Calendar now = GregorianCalendar.getInstance();
				RadialTimePickerDialog timePickerDialog = RadialTimePickerDialog
						.newInstance(ScheduleDateLayout.this,
								now.get(Calendar.HOUR_OF_DAY),
								now.get(Calendar.MINUTE),
								DateFormat.is24HourFormat(activity));
				timePickerDialog.show(fm, "fragment_time_picker_name");
			}
		});
	}

	public Calendar getTime() {
		return time;
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(time.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);

		if (activity instanceof ScheduleDateTimeSettable) {
			ScheduleDateTimeSettable listener = (ScheduleDateTimeSettable) activity;
			listener.onScheduleTimeSet(calendar);
		}
	}

}
