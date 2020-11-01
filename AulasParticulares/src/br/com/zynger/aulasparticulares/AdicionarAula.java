package br.com.zynger.aulasparticulares;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AdicionarAula extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novaaula);
		
		final DatePicker datePicker = (DatePicker) findViewById(R.id.DatePicker);
		final EditText horas = (EditText) findViewById(R.id.etDuracaoHora);
		final EditText minutos = (EditText) findViewById(R.id.etDuracaoMinutos);
		final Button btSalvarNova = (Button) findViewById(R.id.btSalvarNova);
		
		btSalvarNova.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int dia = datePicker.getDayOfMonth();
				int mes = datePicker.getMonth()+1;
				int ano = datePicker.getYear();
				
				String hour = horas.getText().toString();
				String minute = minutos.getText().toString();
				
				if(hour.equals("") || minute.equals("")){
					MainActivity.exibeToast(getApplicationContext(), "Campos de duração da aula não podem ficar vazios");
				}else{
					int horasDuracao = Integer.valueOf(hour);
					int minutosDuracao = Integer.valueOf(minute);
					
					Aula aula = new Aula(horasDuracao, minutosDuracao, dia, mes, ano);
					MainActivity.listaAulas.add(aula);
					finish();
				}
				
			}
		});
	}
}
