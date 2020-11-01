package br.com.site.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.site.R;

public class TablerowPDIRelacionado extends TableRow{

	private Context context;
	private TableRow tablerow;
	private String titulo;
	private TextView textview;
	private Spinner spinner;
	private ImageButton imagebutton;
	
	public TablerowPDIRelacionado(Context c, View v, String tt, List<?> lista, OnClickListener onClickListener) {
		super(c);
		this.context = c;
		this.titulo = tt;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.tablerow = (TableRow) inflater.inflate(R.layout.tablerow_pdi_relacionado, null);
		
		this.textview = (TextView) tablerow.findViewById(R.tablerowpdirelacionado.textview);
		this.spinner = (Spinner) tablerow.findViewById(R.tablerowpdirelacionado.spinner);
		this.imagebutton = (ImageButton) tablerow.findViewById(R.tablerowpdirelacionado.bt_mais);
		if(v instanceof ImageButton) v.setVisibility(View.GONE);
		
		this.textview.setText(this.titulo);
		setPDIArrayAdapter(this.spinner, lista);
		this.imagebutton.setOnClickListener(onClickListener);
	}
	
	public TablerowPDIRelacionado(Context c, TableRow tr, String tt, Spinner sp, ImageButton ib) {
		super(c);
		this.context = c;
		this.titulo = tt;
		this.tablerow = tr;
		this.spinner = sp;
		this.imagebutton = ib;
	}
	
	public TableRow getTableRow() {
		return tablerow;
	}

	public String getTitulo() {
		return titulo;
	}

	public TextView getTextview() {
		return textview;
	}

	public Spinner getSpinner() {
		return spinner;
	}

	public ImageButton getImagebutton() {
		return imagebutton;
	}
	
	private void setPDIArrayAdapter(Spinner sp, List<?> l){
		ArrayAdapter<CharSequence> aAdapter = new ArrayAdapter<CharSequence>(context, R.layout.textviewmodelo);
		aAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
		sp.setAdapter(aAdapter);
        if(l.size() < 1){
        	aAdapter.add("Desconhecido");
        }else{
        	aAdapter.add("Desconhecido");
	        for (int j = 0; j < l.size(); j++) {
	        	aAdapter.add(l.get(j).toString());
			}
    	}
	}	
}