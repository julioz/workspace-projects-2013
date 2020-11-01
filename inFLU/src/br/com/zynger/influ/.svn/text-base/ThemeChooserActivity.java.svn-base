package br.com.zynger.influ;

import java.io.InputStream;
import java.util.ArrayList;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

public class ThemeChooserActivity extends Activity {
	
	public final static String ARQUIVO = "influ_theme";
	private ViewFlow viewFlow;
	private TextView tv_name;
	private Button bt_apply;
	private ArrayList<Theme> al_theme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themechooser);
		loadThemes();
		loadViews();
		updateTheme();
	}
	
	private void loadThemes() {
		al_theme = new ArrayList<Theme>();
		
		addTheme("Verde", R.layout.tabs_bg_verde, R.drawable.tab_divider_verde, R.drawable.news_row_background_selector_verde, R.raw.theme_default);
		addTheme("Grená", R.layout.tabs_bg_grena, R.drawable.tab_divider_grena, R.drawable.news_row_background_selector_grena,  R.raw.theme_grena);
		addTheme("Laranja", R.layout.tabs_bg_laranja, R.drawable.tab_divider_laranja, R.drawable.news_row_background_selector_laranja,  R.raw.theme_laranja);
		addTheme("Azul", R.layout.tabs_bg_azul, R.drawable.tab_divider_azul, R.drawable.news_row_background_selector_azul,  R.raw.theme_azul);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.themechooser.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.themechooser.actionbar).invalidate();
	}
	
	private void addTheme(String name, int tabsbg, int tabsdivider, int newsrowselect, int raw) {
		try{			
			String str = readRawResource(raw);
			Theme theme = parseTheme(name, tabsbg, tabsdivider, newsrowselect, str);
			al_theme.add(theme);
		}catch(NullPointerException e){}
	}
	
	private Theme parseTheme(String name, int tabsbg, int tabsdiv, int newsrowselect, String raw){
		try{			
			raw = raw.substring(raw.indexOf("<abgradstart>")+"<abgradstart>".length());
			String abgradstart = raw.substring(0, raw.indexOf("</abgradstart>"));
			
			raw = raw.substring(raw.indexOf("<abgradend>")+"<abgradend>".length());
			String abgradend = raw.substring(0, raw.indexOf("</abgradend>"));
			
			raw = raw.substring(raw.indexOf("<actback>")+"<actback>".length());
			String actback = raw.substring(0, raw.indexOf("</actback>"));
			
			raw = raw.substring(raw.indexOf("<contentback>")+"<contentback>".length());
			String contentback = raw.substring(0, raw.indexOf("</contentback>"));
			
			raw = raw.substring(raw.indexOf("<sectext>")+"<sectext>".length());
			String sectext = raw.substring(0, raw.indexOf("</sectext>"));
			
			raw = raw.substring(raw.indexOf("<abouttext>")+"<abouttext>".length());
			String abouttext = raw.substring(0, raw.indexOf("</abouttext>"));
			
			return new Theme(name, tabsbg, tabsdiv, newsrowselect, abgradstart, abgradend, actback, contentback, sectext, abouttext);
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	private void loadViews(){
		viewFlow = (ViewFlow) findViewById(R.themechooser.viewflow);
		tv_name = (TextView) findViewById(R.themechooser.tv_name);
		bt_apply = (Button) findViewById(R.themechooser.bt_apply);
		viewFlow.setAdapter(new ThemeAdapter(this, al_theme));
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.themechooser.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		
		tv_name.setText("Tema " + ((Theme) viewFlow.getAdapter().getItem(0)).getName());
		
		bt_apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FileHandler.saveBackup(v.getContext(), ARQUIVO, viewFlow.getAdapter().getItem(0));
				updateTheme();
				Toast.makeText(v.getContext(), "Tema "+ ((Theme) viewFlow.getAdapter().getItem(0)).getName().toLowerCase() + " aplicado!", Toast.LENGTH_SHORT).show();
			}
		});
		
		viewFlow.setOnViewSwitchListener(new ViewSwitchListener() {
			@Override
			public void onSwitched(View view, int position) {
				final int pos = position;
				tv_name.setText("Tema " + ((Theme) viewFlow.getAdapter().getItem(position)).getName());
				bt_apply.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FileHandler.saveBackup(v.getContext(), ARQUIVO, viewFlow.getAdapter().getItem(pos));
						updateTheme();
						Toast.makeText(v.getContext(), "Tema "+ ((Theme) viewFlow.getAdapter().getItem(pos)).getName().toLowerCase() + " aplicado!", Toast.LENGTH_SHORT).show();
					}
				});			
			}
		});
	}
	
	private String readRawResource(int res){
		try {
	        InputStream in_s = getResources().openRawResource(res);

	        byte[] b = new byte[in_s.available()];
	        in_s.read(b);
	        return new String(b);
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	public class ThemeAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private ArrayList<Theme> themes;

		public ThemeAdapter(Context context, ArrayList<Theme> al) {
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			themes = al;
		}

		@Override
		public int getCount() {
			return themes.size();
		}

		@Override
		public Object getItem(int position) {
			return themes.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.theme_item, null);
			}
			LinearLayout ll_bg = (LinearLayout) convertView.findViewById(R.theme_item.ll_bg);
			View v2 = (View) convertView.findViewById(R.theme_item.view_actionbar);
			LinearLayout v3 = (LinearLayout) convertView.findViewById(R.theme_item.view_content);
			TextView txt = (TextView) convertView.findViewById(R.theme_item.txt);
			
			Theme t = (Theme) getItem(position);
			
			ll_bg.setBackgroundColor(t.getAct_background());
			int colors[] = { t.getAbgradstart() , t.getAbgradend() };
			v2.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
			v3.setBackgroundColor(t.getContent_background());
			txt.setTextColor(t.getSec_text());
			txt.setTypeface(null, Typeface.BOLD);
			
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}
}