package br.com.zynger.aulasparticulares;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	public static List<Aula> listaAulas;
	private Toast toast;
	private long lastBackPressTime = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listaAulas = new ArrayList<Aula>();
        
        ImageButton adiciona = (ImageButton) findViewById(R.id.adiciona);
        ImageButton remove = (ImageButton) findViewById(R.id.remove);
        
        adiciona.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AdicionarAula.class);
				startActivity(intent);
			}
		});
        
        remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RemoverAula.class);
				startActivity(intent);
			}
		});
        
        try {
			carregaDados();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void onResume() {
    	
    	if (DefineValor.preco == 0){
        	exibeToast(getApplicationContext(), "Você deve definir um valor para o preço da hora/aula");
        	Intent intent = new Intent(this, DefineValor.class);
    		startActivity(intent);
        }
    	
    	int ganhoTotal = 0;
    	
    	TableLayout tl = (TableLayout) findViewById(R.id.myTableLayout);
    	tl.removeAllViews();
    	
    	TableRow tablerowInicial = new TableRow(this);
    	tablerowInicial.setLayoutParams(new LayoutParams(
                       LayoutParams.FILL_PARENT,
                       LayoutParams.WRAP_CONTENT));
        
        TextView textoData = new TextView(this);
        textoData.setText("Data:");
        textoData.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
        
        TextView textoDuracao = new TextView(this);
        textoDuracao.setText("Duração:");
        textoDuracao.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
        
        TextView textoValor = new TextView(this);
        textoValor.setText("Valor:");
        textoValor.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
        
        formataTexto(textoData);
        formataTexto(textoDuracao);
        formataTexto(textoValor);
        
        tablerowInicial.addView(textoData);
        tablerowInicial.addView(textoDuracao);
        tablerowInicial.addView(textoValor);
        
        
        tl.addView(tablerowInicial,new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    	
    	
    	
    	
    	
    	for (Aula aula : listaAulas) {
    		TableRow tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                           LayoutParams.FILL_PARENT,
                           LayoutParams.WRAP_CONTENT));
            
            TextView data = new TextView(this);
            data.setText(aula.toString());
            formataTexto(data);
            data.setLayoutParams(new LayoutParams(
  	                LayoutParams.FILL_PARENT,
  	                LayoutParams.WRAP_CONTENT));
            
            TextView duracao = new TextView(this);
            String minutos;
            if(aula.getMinutos() < 10){
            	minutos = "0" + aula.getMinutos();
            }else{
            	minutos = String.valueOf(aula.getMinutos());
            }
            duracao.setText(aula.getHoras()+":"+minutos);
            formataTexto(duracao);
            duracao.setLayoutParams(new LayoutParams(
  	                LayoutParams.FILL_PARENT,
  	                LayoutParams.WRAP_CONTENT));
            
            if(DefineValor.preco != aula.getPrecoCadastrado()){
            	aula.setPrecoCadastrado(DefineValor.preco);
            }
            
            TextView valor = new TextView(this);
            valor.setText(String.valueOf(aula.getValor()));
            formataTexto(valor);
            valor.setLayoutParams(new LayoutParams(
  	                LayoutParams.FILL_PARENT,
  	                LayoutParams.WRAP_CONTENT));
            
            ganhoTotal += aula.getValor();
            
            /* Add Button to row. */
            tr.addView(data);
            tr.addView(duracao);
            tr.addView(valor);
            
            /* Add row to TableLayout. */
            tl.addView(tr,new TableLayout.LayoutParams(
                      LayoutParams.FILL_PARENT,
                      LayoutParams.WRAP_CONTENT));
		}
    	
    	
    	TableRow tablerowFinal = new TableRow(this);
    	tablerowFinal.setLayoutParams(new LayoutParams(
                       LayoutParams.FILL_PARENT,
                       LayoutParams.WRAP_CONTENT));
        
        TextView texto = new TextView(this);
        texto.setText("Valor a ser pago:");
        texto.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
        
        TextView dummy = new TextView(this);
        dummy.setText("");
        dummy.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
        
        TextView valorTotal = new TextView(this);
        valorTotal.setText(String.valueOf(ganhoTotal));
        valorTotal.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
        
        formataTexto(texto);
        formataTexto(dummy);
        formataTexto(valorTotal);
        
        tablerowFinal.addView(texto);
        tablerowFinal.addView(dummy);
        tablerowFinal.addView(valorTotal);
        
        
        tl.addView(tablerowFinal,new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        
    	super.onResume();
    }
    
    public void formataTexto(TextView tv){
    	tv.setTextColor(Color.BLACK);
        tv.setTextSize(16);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuprinc, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
    	switch(item.getItemId()) {
    	case R.id.mnDefinirValor:
    		intent = new Intent(this, DefineValor.class);
    		startActivity(intent);
    		break;
    	case R.id.mnSalvar:
    		salvarDados(this);
    		break;
    	default:
    		break;
    	}
    	return true;
    }
    
    public static void exibeToast(Context context, String texto){
    	CharSequence textoToast = texto;
    	Toast toast = Toast.makeText(context, textoToast, Toast.LENGTH_SHORT);
    	toast.show();
    }

    public static void salvarDados(Context context){
    	File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/aulasparticulares");
		dir.mkdirs();
		try {
			FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/aulasparticulares/dados.txt", false);
			writer.append("VALOR:"+DefineValor.preco);
			for (Aula aula : listaAulas) {
				int minutos = aula.getMinutos();
				String min = "";
				if (minutos < 10){
					min = "0" + aula.getMinutos();
				}else{
					min = String.valueOf(aula.getMinutos());
				}
				writer.append("\r\n"+aula.toString()+"jjjjj"+aula.getHoras()+"jjjjj"+min);
			}
			writer.close();
			exibeToast(context, "Cadastros salvos com sucesso");
			Log.e("AulasPartic", "Salvei o dados.ser");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void carregaDados() throws IOException{
    	File sdCard = Environment.getExternalStorageDirectory();
		String caminho = sdCard.getAbsolutePath() + "/aulasparticulares/dados.txt";

		BufferedReader br;
		
		br = new BufferedReader(new InputStreamReader(new FileInputStream(caminho)));
		String c = br.readLine();
		
		String valor = "";
		try{
			valor = c.substring(6);
		}catch(NumberFormatException exc){
			valor = c.substring(6,8);
		}
		DefineValor.preco = Integer.valueOf(valor);
		
		String proximalinha = br.readLine();
		while(proximalinha != null){
			int dia = Integer.valueOf(proximalinha.substring(0, 2));
			int mes = Integer.valueOf(proximalinha.substring(3, 5));
			int ano = Integer.valueOf(proximalinha.substring(6, 10));
			int horas = Integer.valueOf(proximalinha.substring(15, 16));
			int minutos = Integer.valueOf(proximalinha.substring(21, 23));
			
			listaAulas.add(new Aula(horas, minutos, dia, mes, ano));
			
			proximalinha = br.readLine();
		}
		
    }
    
    @Override
    public void onBackPressed() {
    	if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
    		toast = Toast.makeText(this, "Toque em voltar novamente para sair da aplicação", 2000);
    		toast.show();
    		this.lastBackPressTime = System.currentTimeMillis();
    	}else{
    		if (toast != null) {
    			toast.cancel();
    		}
    		super.onBackPressed();
    	}
    }
}