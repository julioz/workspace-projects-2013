package br.com.zynger.influ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Idol;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

public class IdolsListActivity extends Activity {

	private ArrayList<Idol> al1902 = new ArrayList<Idol>();
	private ArrayList<Idol> al1931 = new ArrayList<Idol>();
	private ArrayList<Idol> al1961 = new ArrayList<Idol>();
	private ArrayList<Idol> al1991 = new ArrayList<Idol>();
	
	private ExpandableListView elv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.idols_list);
		
		elv = (ExpandableListView) findViewById(R.idols_list.expandablelv);
		updateTheme();
		
		populateArrayLists();
		
		elv.setAdapter(new IdolsExpandableListAdapter(this, createGroupList(), R.layout.squad_group, new String[] {"Decadas"}, new int[] {R.exlvgroup.row_name}, createChildList(), R.layout.idols_list_child, new String[] {"Url", "Nome"}, new int[] {R.idolslistchild.child_name}));
		elv.setOnChildClickListener(getOnChildClickListener());
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		findViewById(R.idols_list.ll_actbg).setBackgroundColor(t.getAct_background());
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.idols_list.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.idols_list.ll_actbg).invalidate();
	}

	private void populateArrayLists() {
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Idol> idols = (LinkedHashMap<String, Idol>) FileHandler.openBackup(this, IdolsActivity.ARQUIVO);
		if(idols == null) idols = IdolsActivity.loadContent();
		
		
		for (Idol id : idols.values()) {
			if(id.getGeneration() == IdolsActivity.PRGER) al1902.add(id);
			else if (id.getGeneration() == IdolsActivity.SGGER) al1931.add(id);
			else if (id.getGeneration() == IdolsActivity.TCGER) al1961.add(id);
			else if (id.getGeneration() == IdolsActivity.QTGER) al1991.add(id);
		}
	}
	
	public OnChildClickListener getOnChildClickListener() {
		OnChildClickListener occl = new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				Idol s = null;
				if(groupPosition == 0) s = al1902.get(childPosition);
				else if(groupPosition == 1) s = al1931.get(childPosition);
				else if(groupPosition == 2) s = al1961.get(childPosition);
				else if(groupPosition == 3) s = al1991.get(childPosition);
				
				if(s != null){			
					Intent it = new Intent(v.getContext(), IdolsActivity.class);
					it.putExtra("IDOL_NAME", s.getName());
					v.getContext().startActivity(it);
					return true;
				}else{
					Toast.makeText(v.getContext(), "Erro IA01", Toast.LENGTH_SHORT).show();
					Log.e("inFLU", "Erro IA01");
					return false;
				}
			}
		};
		return occl;
	}

	private List<HashMap<String, String>> createGroupList() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> hm1902 = new HashMap<String, String>();
		HashMap<String, String> hm1931 = new HashMap<String, String>();
		HashMap<String, String> hm1961 = new HashMap<String, String>();
		HashMap<String, String> hm1991 = new HashMap<String, String>();

		hm1902.put("Decadas", "1902-1930");
		hm1931.put("Decadas", "1931-1960");
		hm1961.put("Decadas", "1961-1990");
		hm1991.put("Decadas", "1991-2010");

		result.add(hm1902);
		result.add(hm1931);
		result.add(hm1961);
		result.add(hm1991);
		return result;
	}

	private List<ArrayList<HashMap<String, String>>> createChildList() {
		ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
		result.add(populateHashMap(al1902));
		result.add(populateHashMap(al1931));
		result.add(populateHashMap(al1961));
		result.add(populateHashMap(al1991));
		return result;
	}
	
	private ArrayList<HashMap<String, String>> populateHashMap(ArrayList<Idol> al){
		ArrayList<HashMap<String, String>> hashmap = new ArrayList<HashMap<String, String>>();		
		for (Idol idol : al) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("Url", idol.getUrl());
			hm.put("Nome", idol.getName());
			hashmap.add(hm);
		}
		return hashmap;
	}
	
	public class IdolsExpandableListAdapter extends SimpleExpandableListAdapter {
		private List<? extends List<? extends Map<String, ?>>> mChildData;
		private String[] mChildFrom;
		private int[] mChildTo;

		public IdolsExpandableListAdapter(Context context, List<? extends Map<String, ?>> groupData, int groupLayout,
				String[] groupFrom, int[] groupTo, List<? extends List<? extends Map<String, ?>>> childData, int childLayout, String[] childFrom, int[] childTo) {
			super(context, groupData, groupLayout, groupFrom, groupTo,
					childData, childLayout, childFrom, childTo);

			mChildData = childData;
			mChildFrom = childFrom;
			mChildTo = childTo;
		}

		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) v = newChildView(isLastChild, parent);
			else v = convertView;
			bindView(v, mChildData.get(groupPosition).get(childPosition), mChildFrom, mChildTo, groupPosition, childPosition);
			return v;
		}

		// This method binds my data to the Views specified in the child xml layout
		private void bindView(View view, Map<String, ?> data, String[] from, int[] to, int groupPosition, int childPosition) {
			TextView v = (TextView) view.findViewById(to[0]);
			if (v != null) v.setText((String) data.get(from[1]));
		}
	}
	
}
