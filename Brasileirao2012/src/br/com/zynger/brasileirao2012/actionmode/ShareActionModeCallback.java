package br.com.zynger.brasileirao2012.actionmode;

import android.widget.ListView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.util.ShareHelper;

import com.actionbarsherlock.view.ActionMode;

public class ShareActionModeCallback<T> extends ActionModeCallback<T> {

	private ShareHelper shareHelper;

	public ShareActionModeCallback(ActionModeListener<T> listener, ListView listView) {
		super(listener, listView);
		shareHelper = new ShareHelper(listener.getContext());
	}

	public int getMenuResource() {
		return R.menu.contextual_menu_share;
	}

	public boolean onShareClicked(String message, ActionMode mode, int itemId) {
		mode.finish();
		switch (itemId) {
		case R.contextual_menu_share.menu_share:
			if (message != null) {
				shareHelper.share(message);
			}
			return true;
		default:
			return false;
		}
	}
}