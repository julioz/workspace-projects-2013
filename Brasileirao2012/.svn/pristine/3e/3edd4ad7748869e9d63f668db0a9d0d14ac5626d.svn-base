package br.com.zynger.brasileirao2012.adapters;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.comparator.FansComparator;
import br.com.zynger.brasileirao2012.data.ClubsDB;
import br.com.zynger.brasileirao2012.model.Club;

import com.actionbarsherlock.internal.nineoldandroids.animation.ValueAnimator;

public class TorcidometerArrayAdapter extends ArrayAdapter<Club> {
	private final static int FANS_LAYOUT = R.layout.torcidometerrow;

	private final LayoutInflater mInflater;
	private ArrayList<Club> clubsWithFans;
	private int totalFans;

	private int widthBase = 0;
	private View predefinedViewBase;

	public TorcidometerArrayAdapter(Context context, View viewBase) {
		super(context, FANS_LAYOUT);
		this.mInflater = LayoutInflater.from(context);
		this.predefinedViewBase = viewBase;

		this.clubsWithFans = new ArrayList<Club>();
		this.totalFans = 0;
		for (Club club : ClubsDB.getInstance(context).getClubs().values()) {
			int fans = club.getFans().intValue();
			if (fans > 0) {
				clubsWithFans.add(club);
			}
			totalFans += fans;
		}
		Collections.sort(clubsWithFans, new FansComparator());
	}
	
	@Override
	public int getPosition(Club club) {
		return clubsWithFans.indexOf(club);
	}

	@Override
	public int getCount() {
		return clubsWithFans.size();
	}

	@Override
	public Club getItem(int position) {
		return clubsWithFans.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final FansHolder holder;

		if (row == null) {
			row = mInflater.inflate(FANS_LAYOUT, parent, false);

			holder = new FansHolder();
			holder.imgIcon = (ImageView) row
					.findViewById(R.torcidometerrow.iv_badge);
			holder.txtNumber = (TextView) row
					.findViewById(R.torcidometerrow.tv_number);
			holder.bar = row.findViewById(R.torcidometerrow.v_bar);

			row.setTag(holder);
		} else {
			holder = (FansHolder) row.getTag();
		}

		Club club = getItem(position);

		holder.imgIcon.setImageResource(club.getBadgeResource(getContext()));
		holder.txtNumber.setText(String.valueOf(club.getFans()));
		setHolderBar(holder, club);
		
		startBarAnimation(holder.bar);

		return row;
	}
	
	private void startBarAnimation(final View bar) {
		ValueAnimator animator = ValueAnimator.ofInt(0, bar.getLayoutParams().width);
		animator.setDuration(500);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
				bar.getLayoutParams().width = value.intValue();
				bar.requestLayout();
			}
		});
		animator.start();
	}

	private void setHolderBar(final FansHolder holder, final Club club) {
		int height = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 24, getContext().getResources()
						.getDisplayMetrics());

		widthBase = widthBase <= 0 ? predefinedViewBase.getWidth() : widthBase;
		float parentwidthsize = widthBase;

		totalFans = totalFans <= 0 ? 1 : totalFans;
		float factor = (float) club.getFans() / totalFans;
		factor = (float) (factor * 4);
		float width = factor * parentwidthsize;

		if (width > parentwidthsize) {
			width = (float) (parentwidthsize * 0.9);
		} else if (width < 1) {
			width = 1;
		}

		holder.bar.setLayoutParams(new LayoutParams((int) width, height));
		fixBackgroundRepeat(holder.bar);
	}

	/* workaround para bug do android < 4.0 em que o BG fica extendido */
	private static void fixBackgroundRepeat(View view) {
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
			Drawable bg = view.getBackground();
			if (bg != null) {
				if (bg instanceof BitmapDrawable) {
					BitmapDrawable bmp = (BitmapDrawable) bg;
					bmp.mutate(); // make sure that we aren't sharing state anymore
					bmp.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
				}
			}
		}
	}

	private static class FansHolder {
		public ImageView imgIcon;
		public TextView txtNumber;
		public View bar;
	}
}