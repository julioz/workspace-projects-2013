package br.com.zynger.brasileirao2012.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.zynger.brasileirao2012.R;
import br.com.zynger.brasileirao2012.adapters.ClubSelectorListArrayAdapter;
import br.com.zynger.brasileirao2012.comparator.FullNameComparator;
import br.com.zynger.brasileirao2012.model.Club;

import com.actionbarsherlock.app.SherlockFragment;

public class ClubSelectorFragment extends SherlockFragment {
	OnClubSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnClubSelectedListener {
        public void onClubSelected(Club club);
    }
	
	private static final int LAYOUT_RESOURCE = R.layout.clubselectorlistviewlayout;
	
	private ArrayList<Club> clubs;

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClubSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnClubSelectedListener");
        }
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Context context = getSherlockActivity();
		ListView listView = (ListView) inflater.inflate(LAYOUT_RESOURCE, null);
		listView.setAdapter(new ClubSelectorListArrayAdapter(context, clubs));
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View v, int position,
					long id) {
				mCallback.onClubSelected((Club) list.getAdapter().getItem(position));
			}
		});
		
		return listView;
	}
	
	public void setClubs(ArrayList<Club> clubs) {
		this.clubs = clubs;
		Collections.sort(this.clubs, new FullNameComparator());
	}
}