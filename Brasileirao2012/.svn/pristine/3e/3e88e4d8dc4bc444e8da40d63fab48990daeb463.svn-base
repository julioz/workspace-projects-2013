package br.com.zynger.brasileirao2012.base;

import br.com.zynger.brasileirao2012.R;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public abstract class ZoomableActivity extends BaseActivity implements
		ActionBarMenuItemListener {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlock().getMenuInflater().inflate(R.menu.actionbar_menu_zoom, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.actionbar.menu_zoom) {
			menuItemPressed(item);
			return true;
		}
		return false;
	}
}