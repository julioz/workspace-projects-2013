package br.com.zynger.influ.model;

import java.io.Serializable;

import android.graphics.Color;

public class Theme implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String abgradstart, abgradend, act_background, content_background, sec_text, about_text;
	private int tabsbg, tabsdivider, newsrowselector;
	
	public Theme(String name, int tabsbg_res, int tabsdivider, int newsrowselector, String abgradstart, String abgradend, String act_back, String content_back, String sec_text, String about_text){
		this.setName(name);
		setTabsbg(tabsbg_res);
		setTabsDivider(tabsdivider);
		setNewsRowSelector(newsrowselector);
		setAbgradstart(abgradstart);
		setAbgradend(abgradend);
		this.act_background = act_back;
		this.content_background = content_back;
		this.setSec_text(sec_text);
		this.about_text = about_text;
	}

	public int getAct_background() {
		return Color.parseColor(act_background);
	}

	public void setAct_background(String act_background) {
		this.act_background = act_background;
	}

	public int getContent_background() {
		return Color.parseColor(content_background);
	}

	public void setContent_background(String content_background) {
		this.content_background = content_background;
	}

	public int getAbout_text() {
		return Color.parseColor(about_text);
	}

	public void setAbout_text(String about_text) {
		this.about_text = about_text;
	}

	@Override
	public String toString() {
		return "Name=" + name + ", act_background=" + act_background + ", content_background=" + content_background + ", about_text=" + about_text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAbgradstart() { 
		return Color.parseColor(abgradstart);
	}

	public void setAbgradstart(String abgradstart) {
		this.abgradstart = abgradstart;
	}

	public int getAbgradend() {
		return Color.parseColor(abgradend);
	}

	public void setAbgradend(String abgradend) {
		this.abgradend = abgradend;
	}

	public int getSec_text() {
		return Color.parseColor(sec_text);
	}

	public void setSec_text(String sec_text) {
		this.sec_text = sec_text;
	}

	public int getTabsBG() {
		return tabsbg;
	}

	public void setTabsbg(int tabsbg) {
		this.tabsbg = tabsbg;
	}

	public int getTabsDivider() {
		return tabsdivider;
	}

	public void setTabsDivider(int tabsdivider) {
		this.tabsdivider = tabsdivider;
	}

	public int getNewsRowSelector() {
		return newsrowselector;
	}

	public void setNewsRowSelector(int newsrow) {
		this.newsrowselector = newsrow;
	}
}
