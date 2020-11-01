package br.com.zynger.brasileirao2012.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.R;

import com.actionbarsherlock.view.MenuItem;

/**
 * An action bar item implementing a common pattern: initially a refresh button
 * is shown, and when the button is clicked a background operation begins and
 * the button turns into a progress indicator until the operation ends, at which
 * point the button is restored to its initial state.
 * <p>
 * The progress indicator can be determinate or indeterminate. If the
 * determinate mode is used, it is possible to choose between two styles:
 * "wheel" and "pie".
 * <p>
 * It is also possible to have the refresh button be invisible initially, which
 * makes this action item behave like a replacement for the built-in
 * indeterminate action bar progress indicator (with the benefit that with this
 * action item the progress can be determinate).
 * <p>
 * The action item also supports adding a small badge that indicates that there
 * is new data available.
 */
public class RefreshActionItem extends FrameLayout implements OnClickListener,
		OnLongClickListener {
	private ImageView mRefreshButton;
	private ProgressBar mProgressIndicatorIndeterminate;
	private RefreshActionListener mRefreshButtonListener;
	private MenuItem mMenuItem;
	private boolean mShowingProgress;
	
	public enum ProgressIndicatorType {
	    INDETERMINATE
	}

	public interface RefreshActionListener {
		void onRefreshButtonClick(RefreshActionItem sender);
	}

	public RefreshActionItem(Context context) {
		this(context, null);
	}

	public RefreshActionItem(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.refreshActionItemStyle);
	}

	public RefreshActionItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.refreshactionitem, this);
		mRefreshButton = (ImageView) findViewById(R.refreshactionitem.refresh_button);
		mRefreshButton.setOnClickListener(this);
		mRefreshButton.setOnLongClickListener(this);
		mProgressIndicatorIndeterminate = (ProgressBar) findViewById(R.refreshactionitem.indeterminate_progress_indicator);
		updateChildrenVisibility();

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RefreshActionItem, defStyle,
				R.style.Widget_RefreshActionItem);
		int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.RefreshActionItem_refreshActionItemIcon:
				Drawable refreshButtonIcon = a.getDrawable(attr);
				mRefreshButton.setImageDrawable(refreshButtonIcon);
				break;
			case R.styleable.RefreshActionItem_refreshActionItemBackground:
				Drawable drawable = a.getDrawable(attr);
				mRefreshButton.setBackgroundDrawable(drawable);
				break;
			}
		}
		a.recycle();
	}

	public void setRefreshActionListener(RefreshActionListener listener) {
		this.mRefreshButtonListener = listener;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.mMenuItem = menuItem;
		if (menuItem.getIcon() != null) {
			mRefreshButton.setImageDrawable(mMenuItem.getIcon());
		}
	}
	
	public MenuItem getMenuItem() {
		return this.mMenuItem;
	}

	private void updateChildrenVisibility() {
		if (!mShowingProgress) {
			mRefreshButton.setVisibility(View.VISIBLE);
			mProgressIndicatorIndeterminate.setVisibility(View.GONE);
			return;
		}
		mRefreshButton.setVisibility(View.GONE);
		mProgressIndicatorIndeterminate.setVisibility(View.VISIBLE);
	}

	/**
	 * Changes the state of the action item between the modes
	 * "showing refresh button" and "showing progress"
	 * 
	 * @param show
	 */
	public void showProgress(boolean show) {
		if (show == mShowingProgress) {
			return;
		}
		mShowingProgress = show;
		updateChildrenVisibility();
	}

	public ProgressIndicatorType getProgressIndicatorType() {
		return ProgressIndicatorType.INDETERMINATE;
	}

	@Override
	public void onClick(View v) {
		if (mRefreshButtonListener != null) {
			mRefreshButtonListener.onRefreshButtonClick(this);
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (mMenuItem == null || TextUtils.isEmpty(mMenuItem.getTitle())) {
			return true;
		}
		showToastNearToActionBar(mMenuItem.getTitle().toString());
		return true;
	}
	
	public void showToastNearToActionBar(String message) {
		final int[] screenPos = new int[2];
		final Rect displayFrame = new Rect();
		getLocationOnScreen(screenPos);
		getWindowVisibleDisplayFrame(displayFrame);
		final Context context = getContext();
		final int width = getWidth();
		final int height = getHeight();
		final int midy = screenPos[1] + height / 2;
		final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		Toast cheatSheet = Toast.makeText(context, message,
				Toast.LENGTH_SHORT);
		if (midy < displayFrame.height()) {
			cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT, screenWidth
					- screenPos[0] - width / 2, height);
		} else {
			cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
					0, height);
		}
		cheatSheet.show();
	}
}