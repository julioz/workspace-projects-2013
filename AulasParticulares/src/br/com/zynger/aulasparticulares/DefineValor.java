package br.com.zynger.aulasparticulares;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DefineValor extends Activity {
	
	public static int preco = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definevalor);
		
		final EditText etValorAula = (EditText) findViewById(R.id.etvalordaaula);
		final Button btSalvar = (Button) findViewById(R.id.btSalvar);
		
		etValorAula.setText(String.valueOf(preco));
		
		btSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valor = etValorAula.getText().toString();
				if (valor.equals("")){
					MainActivity.exibeToast(getApplicationContext(), "O campo não pode ficar vazio");
				}else{
					preco = Integer.valueOf(valor);
					if (preco != 0) MainActivity.exibeToast(getApplicationContext(), "Valor hora/aula definido para: R$ " + preco);
					finish();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.salvar, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
    	case R.id.mnSalvar:
    		MainActivity.salvarDados(this);
    		break;
		}
		return true;
	}
}
