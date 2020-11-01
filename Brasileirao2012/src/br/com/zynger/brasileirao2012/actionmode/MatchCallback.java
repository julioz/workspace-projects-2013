package br.com.zynger.brasileirao2012.actionmode;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.StadiumsMapActivity;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.movetomove.MoveToMoveActivity;
import br.com.zynger.brasileirao2012.util.CalendarHelper;
import br.com.zynger.brasileirao2012.util.Constants;

import com.actionbarsherlock.view.ActionMode;

public class MatchCallback extends ActionModeCallback<Match> {

	public MatchCallback(ActionModeListener<Match> listener, ListView listView) {
		super(listener, listView);
	}

	public int getMenuResource() {
		return R.menu.contextual_menu_table;
	}

	public void changeMenuItems(Match match) {
		setItemVisibility(R.contextual_menu_table.menu_movetomove, match.getDate() != null);
		setItemVisibility(R.contextual_menu_table.menu_stadium, match.getStadium() != null);
	}

	public boolean onItemClicked(Context context, Match match, ActionMode mode,
			int itemId) {
		switch (itemId) {
		case R.contextual_menu_table.menu_calendar:
			addMatchToCalendar(context);
			return true;
		case R.contextual_menu_table.menu_stadium:
			Intent it = new Intent(context, StadiumsMapActivity.class);
			it.putExtra(StadiumsMapActivity.LIMITATION, match.getStadium());
			context.startActivity(it);
			return true;
		case R.contextual_menu_table.menu_movetomove:
			it = new Intent(context, MoveToMoveActivity.class);
			it.putExtra(Constants.INTENT_MATCH, match.toJson().toString());
			context.startActivity(it);
			return true;
		default:
			return false;
		}
	}

	private void addMatchToCalendar(Context context) {
		try {
			Match match = getModel();
			long beginTime = match.getDate().getTimeInMillis();
			long endTime = beginTime + (105 * 60 * 1000);
			String title = match.getHome().getName() + " x "
					+ match.getAway().getName();
			String location = context.getResources().getString(
					R.string.views_matchviewlayout_stadium)
					+ " " + match.getStadium();

			context.startActivity(CalendarHelper.getEventIntent(beginTime,
					endTime, title, location));
		} catch (Exception e) {
			Toast.makeText(context,
					R.string.views_matchviewlayout_erroraddingcalendar,
					Toast.LENGTH_SHORT).show();
		}
	}
}
