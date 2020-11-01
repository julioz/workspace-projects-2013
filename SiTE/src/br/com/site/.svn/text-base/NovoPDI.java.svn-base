package br.com.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import br.com.site.model.PDI;
import br.com.site.view.TablerowPDIRelacionado;

public abstract class NovoPDI extends Activity{
	
	protected abstract void setAdapterPositionFromSet(Spinner inicial, Set<? extends PDI> set);
	
	protected String getStringEditText(EditText et){
		if (et.getText().toString().equals("")) return "Desconhecido";
		return et.getText().toString();
	}

	@Override
	public void onBackPressed() {
		Global.editarPDI=false;
		super.onBackPressed();
	}
	
	protected void setAdapterPositionFromString(ArrayAdapter<CharSequence> adapter, Spinner spinner, String texto){
		try{
			int pos = adapter.getPosition(texto);
			spinner.setSelection(pos);
		}catch(NullPointerException exc){
			spinner.setSelection(0);
		}
	}
	
	protected void setAdapterPositionFromString(Spinner spinner, String texto){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter<CharSequence> adapter = (ArrayAdapter) spinner.getAdapter();
		try{
			int pos = adapter.getPosition(texto);
			spinner.setSelection(pos);
		}catch(NullPointerException exc){
			spinner.setSelection(0);
		}
	}
	
	/**
	 * Este método aplicará a um spinner um ArrayAdapter populado pelos toStrings de cada PDI contido numa lista de PDIs
	 * @param aAdapter ArrayAdapter que será aplicado no Spinner
	 * @param sp Spinner que receberá os toStrings dos PDIs
	 * @param l Lista de PDIs a serem adicionados ao ArrayAdapter
	 */
	protected void setPDIArrayAdapter(ArrayAdapter<CharSequence> aAdapter, Spinner sp, List<?> l){
		aAdapter = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
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
	
	protected void setMultiTableRow(ImageButton ib, final String title,final List<? extends PDI> list,
			final int tablelayoutid, final ArrayList<TablerowPDIRelacionado> al){
		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				TablerowPDIRelacionado tr = new TablerowPDIRelacionado(v.getContext(), v, title, list, this);
				al.add(tr);
				TableLayout tl = (TableLayout) ((Activity) v.getContext()).findViewById(tablelayoutid);
				tl.addView(tr.getTableRow()); //TODO bug tosco do textview redimensionando
			}
		};
		
		ib.setOnClickListener(ocl);
	}
	
	protected String setStringEditText(EditText et){
		if (et.getText().toString().equals("")){
			return "Desconhecido";
		}
		return et.getText().toString();
	}
	
	protected void setNumEditText(int propriedade, EditText et){
		String s = "";
    	if(propriedade >= 0) s = String.valueOf(propriedade);
    	et.setText(s);
	}
	
	protected boolean isEditTextEmpty(EditText et){
		if(et.getText().toString().trim().length()<1){
			et.setError("Este campo não pode ficar vazio");
			return true;
		}else{
			et.setError(null);
			return false;
		}
	}
	
	protected boolean isEditTextBiggerValue(EditText etMaior, String maiorNome, EditText etMenor, String menorNome){
		String maior = etMaior.getText().toString().trim();
		String menor = etMenor.getText().toString().trim();
		if(!maior.equals("") && !menor.equals("")){			
			if(Integer.valueOf(maior) < Integer.valueOf(menor)){
				etMaior.setError("Este valor não pode ser menor que o valor de \"" + menorNome +"\"");
				etMenor.setError("Este valor não pode ser maior que o valor de \"" + maiorNome + "\"");
				return true;
			}else{
				etMaior.setError(null);
				etMenor.setError(null);
				return false;
			}
		}else{
			return false;
		}
	}
	
	/*protected void popularSet(TreeSet<? extends PDI> set,
			ArrayList<TablerowPDIRelacionado> ltr, List<? extends PDI> list){
		for (TablerowPDIRelacionado tr : ltr) {
			String s = tr.getSpinner().getSelectedItem().toString();
			if(s.equals("Desconhecido")) continue;
			else{
				for (PDI pdi : list) {
					if (s.equals(pdi.toString())) set.add(pdi); //TODO
					else continue;
				}
			}
		}
	}*/
	
}
