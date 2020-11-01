package br.ufrj.dcc.central1746maps;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class CallAdapter extends ArrayAdapter<Call> implements ListAdapter {
//TODO usar viewholder
	private List<Call> items;
	
	public CallAdapter(Context context, List<Call> items) {
		super(context, 0);
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Call getItem(int position) {
		return items.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return new Card(getContext(), getItem(position)).getView();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}
}
