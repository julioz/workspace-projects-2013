package br.com.zynger.brasileirao2012.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.adapters.RankingListAdapter;
import br.com.zynger.brasileirao2012.model.Club;

import com.actionbarsherlock.app.SherlockFragment;

public class ThirdDivisionFragment extends SherlockFragment {
	private static final int LAYOUT_RESOURCE = R.layout.newsviewflowadapteritem;
	
	private ArrayList<Club> clubs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Context context = getSherlockActivity();
		View view = inflater.inflate(LAYOUT_RESOURCE, null);
		ListView listView = (ListView) view.findViewById(R.newsviewflowadapteritem.listview);
		listView.setAdapter(new RankingListAdapter(context, clubs));
		return view;
	}
	
	public void setClubs(ArrayList<Club> clubs) {
		this.clubs = clubs;
		Collections.sort(this.clubs, new ThirdDivisionClubComparator());
	}
	
	class ThirdDivisionClubComparator implements Comparator<Club> {
		@Override
		public int compare(Club lhs, Club another) {
			int differ = lhs.getPosition() - another.getPosition();
			if(differ == 0){
				differ = lhs.getPoints() - another.getPoints();
				
				if(differ == 0){
					differ = lhs.getBalance() - another.getBalance();
				}
			}
			return differ;
		}
	}
}