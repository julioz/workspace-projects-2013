package br.com.zynger.aulasparticulares;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.ToggleButton;

public class RemoverAula extends Activity {

	public ArrayList<ToggleButton> listaBotoes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removeaula);
		listaBotoes = new ArrayList<ToggleButton>();
	}
	
	@Override
	protected void onResume() {
		TableLayout tl = (TableLayout) findViewById(R.id.TableRemover);
		Button aplicar = (Button) findViewById(R.id.btAplicar);
		
    	tl.removeAllViews();
    	
    	for (Aula aula : MainActivity.listaAulas){
    		TableRow tablerow = new TableRow(this);
        	tablerow.setLayoutParams(new LayoutParams(
                           LayoutParams.FILL_PARENT,
                           LayoutParams.WRAP_CONTENT));
            
            /*TextView textoData = new TextView(this);
            textoData.setText("Data:");
            textoData.setLayoutParams(new LayoutParams(
    	                LayoutParams.FILL_PARENT,
    	                LayoutParams.WRAP_CONTENT));*/
        	
        	ToggleButton tb = new ToggleButton(this);
        	tb.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        	tb.setText(aula.toString());
        	tb.setTextOff(aula.toString());
        	tb.setTextOn(aula.toString());
        	
            tablerow.addView(tb);
            listaBotoes.add(tb);
            
            
            tl.addView(tablerow,new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
    	}
    	
    	aplicar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (ToggleButton tb : listaBotoes) {
					if(tb.isChecked()){
						Aula remover = null;
						for (Aula aula : MainActivity.listaAulas) {
							if (aula.toString().equals(tb.getText().toString())){
								remover = aula;
							}
						}
						if(remover != null){
							MainActivity.listaAulas.remove(remover);
						}
					}
				}
				MainActivity.exibeToast(getApplicationContext(), "Modificações aplicadas");
				finish();
			}
		});
		
		
		super.onResume();
	}
}
