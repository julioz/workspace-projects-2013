package br.com.zynger.brasileirao2012.base;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.adapters.drawerlayout.DrawerItem;
import br.com.zynger.brasileirao2012.adapters.drawerlayout.DrawerListItem;
import br.com.zynger.brasileirao2012.enums.Division;

public abstract class DivisionsActivity<T> extends DrawerActivity<T> {
	
	private Division division;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setDivision(getInitialDivision());
		super.onCreate(savedInstanceState);
		setItemSelected(getDivision());
	}
	
	public Division getDivision() {
		return division;
	}
	
	public void setDivision(Division division) {
		if(division == null){
			division = Division.FIRSTDIVISION;
		}
		this.division = division;
	}
	
	public abstract Division getInitialDivision();

	public List<DrawerItem> getDrawerListViewAdapter(List<DrawerItem> items){
        addDrawerHeaderToList(R.string.divisions);
        for (Division division : Division.values()) {
        	addDrawerItemToList(division.getLogoRes(), division.getNameRes());
		}
        return items;
	}
	
	@Override
	public boolean shouldCloseDrawerAfterItemSelection() {
		return true;
	}
	
	public void onDrawerCloses() {}
	
	public void onDrawerOpens() {}
	
	public abstract void onDivisionChosenFromDrawer(Division division);

	public void onDrawerListItemClick(AdapterView<?> parent, View view,
			int position, long id, DrawerListItem item){
		onDivisionChosenFromDrawer(Division.getDivisionByName(this, item.toString()));
	}
	
	private void setItemSelected(Division division) {
		int selected = 0;
		for (Division d : Division.values()) {
			if(division.equals(d)){
				selected = d.ordinal();
				break;
			}
		}
		super.setItemSelected(selected+1);
	}
}
