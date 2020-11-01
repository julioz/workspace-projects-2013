package br.com.zynger.influ.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

public class HistoryRow extends TableRow {

	private Context c;
	private String[] str;
	private String name;
	private OnClickListener ocl;
	
	public HistoryRow(Context context, String[] str, OnClickListener ocl) {
		super(context);
		this.c = context;
		this.str = str;
		this.name = str[0];
		this.ocl = ocl;
		setValues();
	}

	public HistoryRow(Context context, String s1, String s2, String s3, OnClickListener ocl) {
		super(context);
		String[] str = {s1, s2, s3};
		this.c = context;
		this.str = str;
		this.name = s1;
		this.ocl = ocl;
		setValues();
	}
	
	public HistoryRow(Context context, String s1, String s2, OnClickListener ocl) {
		super(context);
		String[] str = {s1, s2};
		this.c = context;
		this.str = str;
		this.name = s1;
		this.ocl = ocl;
		setValues();
	}
	
	private void setValues(){
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom()+3);
		
		for (String string : str) {
			TextView tv = new TextView(c);
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv.setTextColor(0xFFFFFFFF);
			tv.setPadding(tv.getPaddingLeft(), tv.getPaddingTop()+3, tv.getPaddingRight(), tv.getPaddingBottom()+3);
			tv.setGravity(Gravity.CENTER);
			tv.setText(string);
			
			this.addView(tv);
		}
		
		setOnClickListener(ocl);
	}
	
	public String getName(){
		return name;
	}
}