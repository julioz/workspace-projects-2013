package br.com.site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Veiculo;
import br.com.site.model.Vitima;
import br.com.site.web.DownloadApk;

public class Home extends Activity {
	
	final String textSource = "http://dcc.ufrj.br/~julioz/sistemadetratamentodeemergencias/siteupdateversion.txt";
	//final String textSource = "http://dl.dropbox.com/u/11231744/SiTE/siteupdateversion.txt";
	public int versionCode;
	public String versionName;
	public String urlDownload;
	
	private final int URLMALFORMADA = -1;
	private final int CONEXAONAOENCONTRADA = -2;
	private final int ERRODESCONHECIDO = -3;
	
	private WiFiBroadcastReceiver wifibroadcastreceiver = new WiFiBroadcastReceiver();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		this.getAppVersion();
		
		ImageButton nova = (ImageButton) findViewById(R.id.bt_nova_emergencia);
		ImageButton carrega = (ImageButton) findViewById(R.id.bt_carregar_emergencia);
		ImageButton atualizar = (ImageButton) findViewById(R.id.bt_atualizar);
		TextView tvNova = (TextView) findViewById(R.id.novaemerg);
		TextView tvCarrega = (TextView) findViewById(R.id.loademerg);
		TextView tvAtualiza = (TextView) findViewById(R.id.atualizar_app);
		
		tvNova.setText("Nova\nEmergência");
		tvCarrega.setText("Carregar\nEmergência");
		tvAtualiza.setText("Atualizar\nAplicativo");
		
		if(checaUpdate()) Toast.makeText(this, "Existe uma atualização disponível", Toast.LENGTH_SHORT).show(); //TODO e quando o server estiver fora do ar?
		
		tvNova.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {  
        		novaEmerg();
            }
		});
		
        nova.setOnClickListener(new OnClickListener() {  
        	public void onClick(View v) {  
        		novaEmerg();
            }  
        });
        
        carrega.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        tvCarrega.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        tvAtualiza.setOnClickListener(new OnClickListener() {  
        	public void onClick(View v) {  
        		updateApp();
            }  
        });
        
        atualizar.setOnClickListener(new OnClickListener() {  
        	public void onClick(View v) {  
        		updateApp();
            }  
        });
	}
	
	public static void fazerLog(PDI pdi) {
		if(pdi != null){
			if(pdi instanceof Veiculo){
				Veiculo v = (Veiculo) pdi;
				Log.d("SiTE", "------Parse de um Veiculo-----");
				Log.d("SiTE", "id = " + v.getId());
				Log.d("SiTE", "ident = " + v.getIdentificacao());
				Log.d("SiTE", "gp = " + v.getGeoPoint());
				Log.d("SiTE", "tipo = " + v.getTipoDeVeiculo());
				Log.d("SiTE", "subtipo = " + v.getSubtipo());
				Log.d("SiTE", "------------------------------");
			}else if(pdi instanceof Estrutura){
				Estrutura e = (Estrutura) pdi;
				Log.d("SiTE", "------Parse de uma Estrutura------");
				Log.d("SiTE", "id = " + e.getId());
				Log.d("SiTE", "ident = " + e.getIdentificacao());
				Log.d("SiTE", "gp = " + e.getGeoPoint());
				Log.d("SiTE", "tipo = " + e.getTipoDeEstrutura());
				Log.d("SiTE", "afl = " + e.getAfluenciaPublico());
				Log.d("SiTE", "rev = " + e.getEstadoDaRevisao());
				Log.d("SiTE", "instab = " + e.getEstabilidade());
				Log.d("SiTE", "mater = " + e.getTipoDeMaterial());
				Log.d("SiTE", "tamAcesso = " + e.getTamanhoDoAcesso());
				Log.d("SiTE", "difEnt = " + e.getDificuldadeDeEntrada());
				Log.d("SiTE", "numAnd = " + e.getNumAndares());
				Log.d("SiTE", "numSub = " + e.getNumSubsolos());
				Log.d("SiTE", "tipoSub = " + e.getTipoDeSubsolo());
				Log.d("SiTE", "evTrab = " + e.getEvolucaoDoTrabalho());
				Log.d("SiTE", "qtdPes = " + e.getQtdPessoasPiso());
				Log.d("SiTE", "resPis = " + e.getResistenciaPiso());
				Log.d("SiTE", "tempoAc = " + e.getTempoAcesso());
				Log.d("SiTE", "----------------------------------");
			}else if(pdi instanceof Equipe){
				Equipe e = (Equipe) pdi;
				Log.d("SiTE", "------Parse de uma Equipe------");
				Log.d("SiTE", "id = " + e.getId());
				Log.d("SiTE", "ident = " + e.getIdentificacao());
				Log.d("SiTE", "gp = " + e.getGeoPoint());
				Log.d("SiTE", "chefe = " + e.getChefe());
				Log.d("SiTE", "tipoFunc = " + e.getTipoDeFuncao());
				Log.d("SiTE", "qtdMem = " + e.getQtdMembros());
				Log.d("SiTE", "qtdMemFer = " + e.getQtdMembrosFeridos());
				Log.d("SiTE", "tarAt = " + e.getTarefaAtual());
				Log.d("SiTE", "-------------------------------");
			}else if(pdi instanceof Equipamento){
				Equipamento e = (Equipamento) pdi;
				Log.d("SiTE", "------Parse de um Equipamento------");
				Log.d("SiTE", "id = " + e.getId());
				Log.d("SiTE", "ident = " + e.getIdentificacao());
				Log.d("SiTE", "gp = " + e.getGeoPoint());
				Log.d("SiTE", "tipo = " + e.getTipoDeEquipamento());
				Log.d("SiTE", "qtd = " + e.getQuantidade());
				Log.d("SiTE", "disp = " + e.getDisponiveis());
				Log.d("SiTE", "-----------------------------------");
			}else if(pdi instanceof Perigo){
				Perigo p = (Perigo) pdi;
				Log.d("SiTE", "------Parse de um Perigo------");
				Log.d("SiTE", "id = " + p.getId());
				Log.d("SiTE", "ident = " + p.getIdentificacao());
				Log.d("SiTE", "gp = " + p.getGeoPoint());
				Log.d("SiTE", "tipo = " + p.getTipoDePerigo());
				Log.d("SiTE", "risco = " + p.getRiscoAssociado());
				Log.d("SiTE", "devoIr = " + p.getDevoIr());
				Log.d("SiTE", "------------------------------");
			}else if(pdi instanceof Vitima){
				Vitima v = (Vitima) pdi;
				Log.d("SiTE", "------Parse de uma Vitima------");
				Log.d("SiTE", "id = " + v.getId());
				Log.d("SiTE", "ident = " + v.getIdentificacao());
				Log.d("SiTE", "gp = " + v.getGeoPoint());
				Log.d("SiTE", "numPosVit = " + v.getNumeroPossiveisVitimas());
				Log.d("SiTE", "qtdViv = " + v.getQtdVivos());
				Log.d("SiTE", "qtdMort = " + v.getQtdMortos());
				Log.d("SiTE", "qtdVivRes = " + v.getQtdVivosResgatados());
				Log.d("SiTE", "qtdMortRem = " + v.getQtdMortosRemovidos());
				Log.d("SiTE", "-------------------------------");
			}				
		}
	}

	private void novaEmerg(){
		Intent intent = new Intent(Home.this, MainActivity.class);
		startActivity(intent);
	}

	private boolean checaUpdate(){
		URL textUrl;
		int codNovaVersao = 0;
		try{
			String stringBuffer;
			
			textUrl = new URL(textSource);
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
			while ((stringBuffer = bufferReader.readLine()) != null) {
				if(stringBuffer.contains("Version Code: ")){
					codNovaVersao = Integer.valueOf(stringBuffer.replace("Version Code: ", ""));
				}
			}
			bufferReader.close();
		} catch (MalformedURLException e) {
			codNovaVersao = this.URLMALFORMADA;
		} catch (IOException e) {
			codNovaVersao = this.CONEXAONAOENCONTRADA;
		} catch (Exception e) {
			codNovaVersao = this.ERRODESCONHECIDO;
		}
		
		if(codNovaVersao>this.versionCode) return true;
		else return false;
	}
	
	private void updateApp(){
		URL textUrl;
		int codNovaVersao = 0;
		String nomeNovaVersao = null;
		try{
			String stringBuffer;
			
			textUrl = new URL(textSource);
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
			while ((stringBuffer = bufferReader.readLine()) != null) {
				if(stringBuffer.contains("Version Code: ")){
					codNovaVersao = Integer.valueOf(stringBuffer.replace("Version Code: ", ""));
					Log.d("SiTE", "Version Code encontrada: " + codNovaVersao);
				}
				if(stringBuffer.contains("Version Name: ")){
					nomeNovaVersao = stringBuffer.replace("Version Name: ", "");
					Log.d("SiTE", "Version Name encontrada: " + nomeNovaVersao);
				}
				if(stringBuffer.contains("URL: ")){
					this.urlDownload = stringBuffer.replace("URL: ", "");
					Log.d("SiTE", "URL para download: " + this.urlDownload);
				}
			}
			bufferReader.close();
		} catch (MalformedURLException e) {
			Toast.makeText(this, "A URL para atualização não pôde ser encontrada", Toast.LENGTH_SHORT).show();
			codNovaVersao = this.URLMALFORMADA;
		} catch (IOException e) {
			Toast.makeText(this, "Verifique a conexão com a internet", Toast.LENGTH_SHORT).show();
			codNovaVersao = this.CONEXAONAOENCONTRADA;
		}
		
		if(Global.isOnline()){
			if(codNovaVersao>this.versionCode){
				final String novaNome = nomeNovaVersao;
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
				alertDialog.setTitle("Atualização");
				alertDialog.setMessage("Versão atual: " + this.versionName + "\nVersão disponível: " + nomeNovaVersao + "\n\nDeseja fazer o download e instalação da atualização?");
				alertDialog.setIcon(R.drawable.atualizar_app);
				alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						downloadUpdate(novaNome);
					}
				});
				alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				alertDialog.create().show();
				
			}else{
				if(codNovaVersao != this.CONEXAONAOENCONTRADA) Toast.makeText(this, "Não é necessário atualizar o aplicativo", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void downloadUpdate(String nomeNovaVersao){
		ProgressDialog mProgressDialog = new ProgressDialog(Home.this);
		mProgressDialog.setMessage("Fazendo download da versão " + nomeNovaVersao);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		DownloadApk downloadFile = new DownloadApk(Home.this, mProgressDialog, Home.this.urlDownload, nomeNovaVersao);
		downloadFile.execute();
	}
	
	public void instalar(Intent i){
		this.startActivity(i);
	}
	
	private void getAppVersion(){
		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			//String packageName = info.packageName;
			this.versionCode = info.versionCode;
			this.versionName = info.versionName;
			//Log.d("SiTE", "Package Name: " + packageName);
			//Log.d("SiTE", "Version Code: " + this.versionCode);
			//Log.d("SiTE", "Version Name: " + this.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume() {
		registerReceiver(this.wifibroadcastreceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(this.wifibroadcastreceiver);
		super.onPause();
	}
}
