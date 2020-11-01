package br.com.zynger.brasileirao2012.adapters;

import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.view.MatchViewLayout;

public class TableArrayAdapter extends ArrayAdapter<Match> {

	private List<Match> matches;
	private SparseArray<MatchViewLayout> cache;

	public TableArrayAdapter(Context context, List<Match> matches) {
		super(context, 0, matches);
		this.matches = matches;
		this.cache = new SparseArray<MatchViewLayout>();
	}
	
	@Override
	public int getCount() {
		return matches.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Match match = getItem(position);
		if(cache.get(position) == null) {
			MatchViewLayout matchViewLayout = new MatchViewLayout(getContext(), match, null);
			cache.put(position, matchViewLayout);
			return matchViewLayout;
		} else {
			return cache.get(position);
		}
	}
}
