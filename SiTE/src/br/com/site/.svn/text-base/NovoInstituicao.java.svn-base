package br.com.site;

import br.com.site.model.Instituicao;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NovoInstituicao extends Activity {
	
	public boolean clique1, clique2;
	public EditText etChefeInstituicao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.novo_instituicao);
		
		
		clique1 = clique2 = false;
		etChefeInstituicao = (EditText) findViewById(R.novo_instituicao.etChefeInstituicao);
		final ImageButton salvar = (ImageButton) findViewById(R.novo_instituicao.btCriaInst);
		final RadioGroup rg1 = (RadioGroup) findViewById(R.novo_instituicao.rgInst1);
		final RadioGroup rg2 = (RadioGroup) findViewById(R.novo_instituicao.rgInst2);
		
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				clique1 = true;
				if(!clique2){
					rg2.clearCheck();
					etChefeInstituicao.requestFocus();
				}
				clique1 = false;
			}
		});
		
		rg2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				clique2 = true;
				if(!clique1){
					rg1.clearCheck();
					etChefeInstituicao.requestFocus();
				}
				clique2 = false;
			}
		});
		
		salvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String instit = "", chefe = "";
				if(rg1.getCheckedRadioButtonId()!= -1 && rg2.getCheckedRadioButtonId()== -1){
					RadioButton rb = (RadioButton) findViewById(rg1.getCheckedRadioButtonId());
					instit = rb.getText().toString();
				}else if(rg1.getCheckedRadioButtonId()== -1 && rg2.getCheckedRadioButtonId()!= -1){
					RadioButton rb = (RadioButton) findViewById(rg2.getCheckedRadioButtonId());
					instit = rb.getText().toString();
				}
				
				chefe = etChefeInstituicao.getText().toString();
				
				boolean erro = validacao();
				
				if(!erro){
					if(instit.equals("")){
						MainActivity.exibeToast(NovoInstituicao.this, "Nenhum tipo de instituição selecionado");
					}else{
						Instituicao ins = new Instituicao(instit, chefe);
						MainActivity.listaInstituicoes.add(ins);
		            	finish();
		            	MainActivity.exibeToast(getApplicationContext(), "Instituição pronta para ser associada a uma equipe");
					}
				}
			}
		});
	}
	
	public boolean validacao(){
		if(etChefeInstituicao.getText().toString().trim().equals("")){
			etChefeInstituicao.setError("Deve ser definido um nome\npara o chefe da Instituição");
			return true;
		}
		
		return false;
	}
}
