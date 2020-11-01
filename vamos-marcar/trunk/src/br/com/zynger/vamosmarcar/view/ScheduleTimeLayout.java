package br.com.zynger.vamosmarcar.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.zynger.vamosmarcar.R;

public class ScheduleTimeLayout extends RelativeLayout {
	
	public interface ScheduleTimeDeletable {
		void onScheduleTimeDeleted(ScheduleTimeLayout scheduleTimeView);
	}

	private static final String DATE_PATTERN = "HH:mm";
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
	
	private Calendar time;
	private ScheduleTimeDeletable deletableListener;

	public ScheduleTimeLayout(Context context, Calendar time, ScheduleTimeDeletable deletable) {
		super(context);
		init(context, time, deletable);
	}
	
	public ScheduleTimeLayout(Context context, AttributeSet attrs, Calendar time, ScheduleTimeDeletable deletable) {
		super(context, attrs);
		init(context, time, deletable);
	}

	public ScheduleTimeLayout(Context context, AttributeSet attrs, int defStyle, Calendar time, ScheduleTimeDeletable deletable) {
		super(context, attrs, defStyle);
		init(context, time, deletable);
	}
	
	private void init(Context context, Calendar time,
			ScheduleTimeDeletable deletable) {
		this.time = time;
		this.deletableListener = deletable;
		
		LayoutInflater.from(context).inflate(R.layout.view_scheduletime, this);
		
		TextView tvTime = (TextView) findViewById(R.view_scheduletime.tv_time);
		ImageButton ibDelete = (ImageButton) findViewById(R.view_scheduletime.ib_delete);
		ibDelete.getDrawable().setColorFilter(getResources().getColor(R.color.holo_red_dark), Mode.SRC_ATOP);
		
		tvTime.setText(simpleDateFormat.format(time.getTime()));
		
		if (deletableListener != null) {
			ibDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View imageButton) {
					deletableListener.onScheduleTimeDeleted(ScheduleTimeLayout.this);
				}
			});
		} else {
			ibDelete.setVisibility(View.GONE);
		}
	}
	
	public Calendar getTime() {
		return time;
	}

}
