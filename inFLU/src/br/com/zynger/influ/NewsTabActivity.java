package br.com.zynger.influ;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

@SuppressWarnings("deprecation")
public class NewsTabActivity extends TabActivity { 
	//TODO trocar por FragmentActivity
	
	public static final int GLOBOESPORTE = 1;
	public static final int LANCE = 2;
	public static final int NETFLU = 3;
	public static final int TERRA = 4;
	public static final int UOL = 5;
	public static final int GOOGLE = 6;
	
	private Theme theme;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_tabs);
		
		updateTheme();
		
		/** TabHost will have Tabs */
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.getTabWidget().setDividerDrawable(theme.getTabsDivider());

		/** TabSpec used to create a new tab.
		 * By using TabSpec only we can able to setContent to the tab.
		 * By using TabSpec setIndicator() we can set name to tab. */

		/** tid1 is firstTabSpec Id. Its used to access outside. */
		TabSpec firstTab = tabHost.newTabSpec("tid1");
		TabSpec secondTab = tabHost.newTabSpec("tid2");
		TabSpec thirdTab = tabHost.newTabSpec("tid3");
		TabSpec fourthTab = tabHost.newTabSpec("tid4");
		TabSpec fifthTab = tabHost.newTabSpec("tid5");
		TabSpec sixthTab = tabHost.newTabSpec("tid6");
		
		
		/** TabSpec setIndicator() is used to set name for the tab. */
		/** TabSpec setContent() is used to set content for a particular tab. */
		firstTab.setIndicator(createTabView("Globo")).setContent(getIntent(GLOBOESPORTE));
		secondTab.setIndicator(createTabView("Lance")).setContent(getIntent(LANCE));
		thirdTab.setIndicator(createTabView("Netflu")).setContent(getIntent(NETFLU));
		fourthTab.setIndicator(createTabView("Terra")).setContent(getIntent(TERRA));
		fifthTab.setIndicator(createTabView("UOL")).setContent(getIntent(UOL));
		sixthTab.setIndicator(createTabView("Google")).setContent(getIntent(GOOGLE));
		

		/** Add tabSpec to the TabHost to display. */
		tabHost.addTab(firstTab);
		tabHost.addTab(secondTab);
		tabHost.addTab(thirdTab);
		tabHost.addTab(fourthTab);
		tabHost.addTab(fifthTab);
		tabHost.addTab(sixthTab);

	}
	
	private void updateTheme() {
		theme = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		
		this.getTabHost().setBackgroundColor(theme.getContent_background());		
		this.getTabHost().invalidate();
	}
	
	private View createTabView(String text) {
		View view = LayoutInflater.from(this).inflate(theme.getTabsBG(), null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	private Intent getIntent(int site){
		Intent it = new Intent(this, NewsActivity.class);
	    
	    if(site == GLOBOESPORTE){	    	
	    	it.putExtra("encoding", "utf-8");
	    	it.putExtra("url", "http://globoesporte.globo.com/dynamo/futebol/times/fluminense/rss2.xml");//"http://globoesporte.globo.com/Esportes/Rss/0,,AS0-9866,00.xml"); //OK
	    }else if(site == LANCE){
	    	it.putExtra("encoding", "utf-8");
		    it.putExtra("url", "http://www.lancenet.com.br/minutoL.html?sectionId=67"); //OK
	    }else if(site == NETFLU){
	    	it.putExtra("encoding", "iso-8859-1");
		    it.putExtra("url", "http://www.netflu.com.br/noticias_rss.php"); //OK
	    }else if(site == UOL){
	    	it.putExtra("encoding", "iso-8859-1");
	    	it.putExtra("url", "http://rss.esporte.uol.com.br/futebol/clubes/fluminense.xml"); //OK
	    }else if(site == TERRA){
	    	it.putExtra("encoding", "iso-8859-1");
		    it.putExtra("url", "http://rss.terra.com.br/0,,EI19377,00.xml"); //"http://rss.terra.com.br/0,,EI17410,00.xml"); //OK
	    }else if(site == GOOGLE){
	    	it.putExtra("encoding", "utf-8");
	    	it.putExtra("url", "http://news.google.com.br/news?q=fluminense&um=1&hl=pt-BR&bav=on.2,or.r_gc.r_pw.r_cp.,cf.osb&biw=1680&bih=925&ie=UTF-8&output=rss"); //OK
	    }
	    
	    it.putExtra("domain", site);
	    return it;
	}
}