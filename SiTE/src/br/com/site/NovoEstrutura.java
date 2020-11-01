package br.com.site;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Vitima;
import br.com.site.view.TablerowPDIRelacionado;

import com.google.android.maps.GeoPoint;

public class NovoEstrutura extends NovoPDI{
	
	private boolean outroTipo;
	
	private List<Equipe> listaEquipes;
	private List<Perigo> listaPerigos; 
	private List<Vitima> listaVitimas; 
	
	private TableLayout tlEstDelete;
	private TableRow trEstruturaTipo;
	
	private EditText etEstNome, etEstTipo, etEstAfluencia, etEstMaterial, etNumAndares, etNumSubterraneos, etTipoSubterraneo, etTempoEstimadoAcesso, etResistenciaPiso, etQtdPessoasPiso;
	private Spinner spEstTipo, spEstEstabilidade, spEstTamanhoAcesso, spEstDifAcesso, spEstEvolucao, spEstRevisao, spEstEquipe, spEstPerigo, spEstVitima;
	private ImageButton spEstEquipeMais, spEstPerigoMais, spEstVitimaMais;
	private ImageButton salvar;
	private TextView tvTit;
	
	private ArrayList<TablerowPDIRelacionado> ltrTeam = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrPer = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrVit = new ArrayList<TablerowPDIRelacionado>();
	
	ArrayAdapter<CharSequence> adtEstTipo, adtRevisao, adtEstabilidade, adtTmnAcesso, adtDifAcesso, adtEvolucao, adtEstEquipe, adtEstPerigo, adtEstVitima;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setContentView(R.layout.novo_estrutura);
		
		carregaViews();
		
        outroTipo = false;
        
        setPDIArrayAdapter(adtEstEquipe, spEstEquipe, listaEquipes);
        setPDIArrayAdapter(adtEstPerigo, spEstPerigo, listaPerigos);
        setPDIArrayAdapter(adtEstVitima, spEstVitima, listaVitimas);
        
        spEstTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg0.getSelectedItem().toString().equals("Outro...")){
					trEstruturaTipo.setVisibility(View.VISIBLE);
					outroTipo = true;
				}else{
					trEstruturaTipo.setVisibility(View.GONE);
					outroTipo = false;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
        
		setMultiTableRow(spEstEquipeMais, "Equipe:", listaEquipes, R.novo_estrutura.tlEquipe, ltrTeam);
		setMultiTableRow(spEstPerigoMais, "Perigo:", listaPerigos, R.novo_estrutura.tlPerigo, ltrPer);
		setMultiTableRow(spEstVitimaMais, "Vítima:", listaVitimas, R.novo_estrutura.tlVitima, ltrVit);
        
        if(!Global.editarPDI) novoPDI();
        else editarPDI();
	}
	
	private void carregaViews(){
		listaEquipes = br.com.site.MainActivity.listaEquipes;
		listaPerigos = br.com.site.MainActivity.listaPerigos;
		listaVitimas = br.com.site.MainActivity.listaVitimas;
		
		tlEstDelete = (TableLayout) findViewById(R.novo_estrutura.tlEstDelete);
		tlEstDelete.setVisibility(View.GONE);
		
		etEstNome = (EditText) findViewById(R.novo_estrutura.etEstNome);
    	etEstTipo = (EditText) findViewById(R.novo_estrutura.etEstruturaTipoDeEstrutura);
		spEstTipo = (Spinner) findViewById(R.novo_estrutura.spEstTipo);
    	etEstAfluencia = (EditText) findViewById(R.novo_estrutura.etEstAfluencia);
    	etEstMaterial = (EditText) findViewById(R.novo_estrutura.etEstMaterial);
    	etNumAndares = (EditText) findViewById(R.novo_estrutura.etNumAndares);
    	etTipoSubterraneo = (EditText) findViewById(R.novo_estrutura.etTipoSubterraneo);
    	etNumSubterraneos = (EditText) findViewById(R.novo_estrutura.etNumSubterraneos);
    	etTempoEstimadoAcesso = (EditText) findViewById(R.novo_estrutura.etTempoEstimadoAcesso);
    	etResistenciaPiso = (EditText) findViewById(R.novo_estrutura.etResistenciaPiso);
    	etQtdPessoasPiso = (EditText) findViewById(R.novo_estrutura.etQtdPessoasPiso);
    	
		spEstEstabilidade = (Spinner) findViewById(R.novo_estrutura.spEstEstabilidade);
		spEstTamanhoAcesso = (Spinner) findViewById(R.novo_estrutura.spEstTamanhoAcesso);
		spEstDifAcesso = (Spinner) findViewById(R.novo_estrutura.spEstDificuldadeAcesso);
		spEstEvolucao = (Spinner) findViewById(R.novo_estrutura.spEstEvolucao);
		spEstRevisao = (Spinner) findViewById(R.novo_estrutura.spEstRevisao);
    	
		spEstEquipe = (Spinner) findViewById(R.novo_estrutura.spEstEquipe);
		ltrTeam.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_estrutura.trInicialEquipe), "Equipe:", spEstEquipe, spEstEquipeMais));
		spEstPerigo = (Spinner) findViewById(R.novo_estrutura.spEstPerigo);
		ltrPer.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_estrutura.trInicialPerigo), "Perigo:", spEstPerigo, spEstPerigoMais));
		spEstVitima = (Spinner) findViewById(R.novo_estrutura.spEstVitima);
		ltrVit.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_estrutura.trInicialVitima), "Vítima:", spEstVitima, spEstVitimaMais));
		
		spEstEquipeMais = (ImageButton) findViewById(R.novo_estrutura.spEstEquipeMais);
		spEstPerigoMais = (ImageButton) findViewById(R.novo_estrutura.spEstPerigoMais);
		spEstVitimaMais = (ImageButton) findViewById(R.novo_estrutura.spEstVitimaMais);
		
		salvar = (ImageButton) findViewById(R.novo_estrutura.estSalvar);
        tvTit = (TextView) findViewById(R.novo_estrutura.tvEstruturaTit);
        
        trEstruturaTipo = (TableRow) findViewById(R.novo_estrutura.trEstruturaTipo);
        
        adtEstTipo = ArrayAdapter.createFromResource(this, R.array.estruturaTipo_array, R.layout.textviewmodelo);
		adtEstTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstTipo.setAdapter(adtEstTipo);
		
		adtRevisao = ArrayAdapter.createFromResource(
        		this, R.array.estruturaRevisao_array, R.layout.textviewmodelo);
        adtRevisao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstRevisao.setAdapter(adtRevisao);
        
        adtEstabilidade = ArrayAdapter.createFromResource(this,
        		R.array.estruturaEstabilidade_array, R.layout.textviewmodelo);
        adtEstabilidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstEstabilidade.setAdapter(adtEstabilidade);
        
        adtTmnAcesso = ArrayAdapter.createFromResource(
        		this, R.array.estruturaTamanhoAcesso_array, R.layout.textviewmodelo);
        adtTmnAcesso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstTamanhoAcesso.setAdapter(adtTmnAcesso);
        
        adtDifAcesso = ArrayAdapter.createFromResource(
        		this, R.array.estruturaDificuldadeAcesso_array, R.layout.textviewmodelo);
        adtDifAcesso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstDifAcesso.setAdapter(adtDifAcesso);
        
        adtEvolucao = ArrayAdapter.createFromResource(
        		this, R.array.estruturaEvolucao_array, R.layout.textviewmodelo);
        adtEvolucao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstEvolucao.setAdapter(adtEvolucao);
        
        adtEstEquipe = new ArrayAdapter<CharSequence>(this,
        		R.layout.textviewmodelo);
        adtEstEquipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        adtEstPerigo = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtEstPerigo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        adtEstVitima = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtEstVitima.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	private void novoPDI(){
		salvar.setOnClickListener(new OnClickListener() {  
    		public void onClick(View v) {
    			if(!isEditTextEmpty(etEstNome)){
    				salvar.setImageResource(R.drawable.bt_aplicar);
    				GeoPoint temp = new GeoPoint(0, 0);
    				
    				
    				int estadoDaBusca;
    				if (spEstRevisao.getSelectedItem().equals("Não revisado")){
    					estadoDaBusca = 1;
    				} else if (spEstRevisao.getSelectedItem().equals("Em revisão")){
    					estadoDaBusca = 2;
    				} else if (spEstRevisao.getSelectedItem().equals("Revisado")){
    					estadoDaBusca = 3;
    				}else{
    					estadoDaBusca = 0;
    				}
    				
    				int estabilidade;
    				if (spEstEstabilidade.getSelectedItem().equals("Estável")){
    					estabilidade = 1;
    				} else if (spEstEstabilidade.getSelectedItem().equals("Instável")){
    					estabilidade = 2;
    				} else if (spEstEstabilidade.getSelectedItem().equals("Completamente Instável")){
    					estabilidade = 3;
    				}else{
    					estabilidade = 0;
    				}
    				
    				String tamanhoAcesso;
    				if (spEstTamanhoAcesso.getSelectedItem().equals("Grande")){
    					tamanhoAcesso = "Grande";
    				}else if (spEstTamanhoAcesso.getSelectedItem().equals("Pequeno")){
    					tamanhoAcesso = "Pequeno";
    				}else{
    					tamanhoAcesso = "Desconhecido";
    				}
    				
    				int difEntrada;
    				if (spEstDifAcesso.getSelectedItem().equals("Leve")){
    					difEntrada = 1;
    				} else if (spEstDifAcesso.getSelectedItem().equals("Média")){
    					difEntrada = 2;
    				} else if (spEstDifAcesso.getSelectedItem().equals("Difícil")){
    					difEntrada = 3;
    				} else if(spEstDifAcesso.getSelectedItem().equals("Muito Difícil")){
    					difEntrada = 4;
    				}else{
    					difEntrada = 0;
    				}
    				
    				int evolTrab;
    				if (spEstEvolucao.getSelectedItem().equals("Não começou")){
    					evolTrab = 1;
    				} else if (spEstEvolucao.getSelectedItem().equals("Em andamento")){
    					evolTrab = 2;
    				} else if (spEstEvolucao.getSelectedItem().equals("Finalizado")){
    					evolTrab = 3;
    				} else if(spEstEvolucao.getSelectedItem().equals("Em pausa")){
    					evolTrab = 4;
    				}else{
    					evolTrab = 0;
    				}
    				
    				String tituloBalao = etEstNome.getText().toString();
    				
    				String tipoDeEstrutura = "Desconhecido";
    				if(!spEstTipo.getSelectedItem().equals("Desconhecido") && !outroTipo){
    					tipoDeEstrutura = spEstTipo.getSelectedItem().toString();
    				}
    				
    				if(outroTipo){
    					tipoDeEstrutura = etEstTipo.getText().toString();
    				}
    				
    				String tipoDeMaterial = etEstMaterial.getText().toString();
    				if (tipoDeMaterial.equals("")) tipoDeMaterial = "Desconhecido";
    				
    				String tipoDeSubsolo = etTipoSubterraneo.getText().toString();
    				if (tipoDeSubsolo.equals("")) tipoDeSubsolo = "Desconhecido";
    				
    				int afluenciaPublico = 0;
    				try{
    					afluenciaPublico = Integer.parseInt(etEstAfluencia.getText().toString());
    				}catch(NumberFormatException nfe){
    					afluenciaPublico = -1;
    				}
    				
    				int numAndares = 0;
    				try{
    					numAndares = Integer.parseInt(etNumAndares.getText().toString());
    				}catch(NumberFormatException nfe){
    					numAndares = -1;
    				}
    				
    				int numSubsolos = 0;
    				try{
    					numSubsolos = Integer.parseInt(etNumSubterraneos.getText().toString());
    				}catch(NumberFormatException nfe){
    					numSubsolos = -1;
    				}
    				
    				int qtdPessoasPiso;
    				try{
    					qtdPessoasPiso = Integer.parseInt(etQtdPessoasPiso.getText().toString());
    				}catch(NumberFormatException nfe){
    					qtdPessoasPiso = -1;
    				}
    				
    				int resistenciaPiso = 0;
    				try{
    					resistenciaPiso = Integer.parseInt(etResistenciaPiso.getText().toString());
    				}catch(NumberFormatException nfe){
    					resistenciaPiso = -1;
    				}
    				
    				int tempoAcesso;
    				try{
    					tempoAcesso = Integer.parseInt(etTempoEstimadoAcesso.getText().toString());
    				}catch(NumberFormatException nfe){
    					tempoAcesso = -1;
    				}
    				
    				Set<Equipe> setEquipe = new TreeSet<Equipe>();
    				for (TablerowPDIRelacionado tr : ltrTeam) {
    					String s = tr.getSpinner().getSelectedItem().toString();
    					if(s.equals("Desconhecido")) continue;
    					else{
    						for (Equipe equipe : listaEquipes) {
    							if (s.equals(equipe.toString())) setEquipe.add(equipe);
    							else continue;
    						}
    					}
    				}
    				
    				Set<Perigo> setPerigo = new TreeSet<Perigo>();
    				for (TablerowPDIRelacionado tr : ltrPer) {
    					String s = tr.getSpinner().getSelectedItem().toString();
    					if(s.equals("Desconhecido")) continue;
    					else{
    						for (Perigo perigo : listaPerigos) {
    							if (s.equals(perigo.toString())) setPerigo.add(perigo);
    							else continue;
    						}
    					}
    				}
    				
    				Set<Vitima> setVitima = new TreeSet<Vitima>();
    				for (TablerowPDIRelacionado tr : ltrVit) {
    					String s = tr.getSpinner().getSelectedItem().toString();
    					if(s.equals("Desconhecido")) continue;
    					else{
    						for (Vitima vitima : listaVitimas) {
    							if (s.equals(vitima.toString())) setVitima.add(vitima);
    							else continue;
    						}
    					}
    				}
    				
    				Estrutura est = new Estrutura(Global.getNumPDI(), temp, tituloBalao,
    						setEquipe, setPerigo, setVitima, tituloBalao, tipoDeEstrutura, afluenciaPublico, estadoDaBusca, estabilidade,
    						tipoDeMaterial, tamanhoAcesso, difEntrada, numAndares, numSubsolos, tipoDeSubsolo, evolTrab, qtdPessoasPiso, resistenciaPiso, tempoAcesso);
    				
    				if(setEquipe != null && !setEquipe.isEmpty()){
    					for (Iterator<Equipe> it = setEquipe.iterator(); it.hasNext();) {
    						Equipe equipe = (Equipe) it.next();
    						equipe.getEstrutura().add(est);
    					}
    				}
    				
    				if(setVitima != null && !setVitima.isEmpty()){
    					for (Iterator<Vitima> it = setVitima.iterator(); it.hasNext();) {
    						Vitima vitima = (Vitima) it.next();
    						vitima.getEstrutura().add(est);
    					}
    				}
    				
    				br.com.site.MainActivity.objetoPassado = est;
    				Global.novoPdi = true;
    				finish();
    				br.com.site.MainActivity.exibeToast(getApplicationContext(), "Toque na tela para posicionar a estrutura.");
    			}
    		}  
    	});
	}
	
	private void editarPDI(){
		salvar.setImageResource(R.drawable.bt_edit);
		tvTit.setText("Editar Estrutura");
		tlEstDelete.setVisibility(View.VISIBLE);

		final TextView tvDel = (TextView) findViewById(R.novo_estrutura.tvDelEst);
		final ImageView ivDel = (ImageView) findViewById(R.novo_estrutura.ivDelEst);

		final Estrutura e = (Estrutura) Global.pdiEdicao;

		tvDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoEstrutura.this, e, e.getXML());
			}
		});

		ivDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoEstrutura.this, e, e.getXML());
			}
		});


		etEstNome.setText(e.getTituloBalao());

		setNumEditText(e.getAfluenciaPublico(), etEstAfluencia);
		etEstMaterial.setText(e.getTipoDeMaterial());
		etEstTipo.setText(e.getTipoDeEstrutura());
		setNumEditText(e.getNumAndares(), etNumAndares);
		setNumEditText(e.getQtdPessoasPiso(), etQtdPessoasPiso);
		setNumEditText(e.getResistenciaPiso(), etResistenciaPiso);
		etTipoSubterraneo.setText(e.getTipoDeSubsolo());
		setNumEditText(e.getNumSubsolos(), etNumSubterraneos);
		setNumEditText(e.getTempoAcesso(), etTempoEstimadoAcesso);

		setAdapterPositionFromString(spEstTipo,e.getTipoDeEstrutura());
		setAdapterPositionFromString(spEstRevisao,e.getEstadoDaRevisao());
		setAdapterPositionFromString(spEstEstabilidade,e.getEstabilidade());
		setAdapterPositionFromString(spEstTamanhoAcesso,e.getTamanhoDoAcesso());
		setAdapterPositionFromString(spEstDifAcesso,e.getDificuldadeDeEntrada());
		setAdapterPositionFromString(spEstEvolucao,e.getEvolucaoDoTrabalho());


		try{
    		setAdapterPositionFromSet(spEstEquipe, e.getEquipe());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spEstEquipe, "Desconhecido");
		}try{
			setAdapterPositionFromSet(spEstPerigo, e.getPerigo());
		}catch (NullPointerException ex) {
			setAdapterPositionFromString(spEstPerigo, "Desconhecido");
		}try{
			setAdapterPositionFromSet(spEstVitima, e.getVitima());
		}catch (NullPointerException ex) {
			setAdapterPositionFromString(spEstVitima, "Desconhecido");
		}


		salvar.setOnClickListener(new OnClickListener() {  
			public void onClick(View v) {
				if(!isEditTextEmpty(etEstNome)){
					MainActivity.listaEstruturas.remove(e);
					
					
					int estadoDaBusca;
					if (spEstRevisao.getSelectedItem().equals("Não revisado")){
						estadoDaBusca = 1;
					} else if (spEstRevisao.getSelectedItem().equals("Em revisão")){
						estadoDaBusca = 2;
					} else if (spEstRevisao.getSelectedItem().equals("Revisado")){
						estadoDaBusca = 3;
					}else{
						estadoDaBusca = 0;
					}
					e.setEstadoDaRevisao(estadoDaBusca);
					
					int estabilidade;
					if (spEstEstabilidade.getSelectedItem().equals("Estável")){
						estabilidade = 1;
					} else if (spEstEstabilidade.getSelectedItem().equals("Instável")){
						estabilidade = 2;
					} else if (spEstEstabilidade.getSelectedItem().equals("Completamente Instável")){
						estabilidade = 3;
					}else{
						estabilidade = 0;
					}
					e.setEstabilidade(estabilidade);
					
					if (spEstTamanhoAcesso.getSelectedItem().equals("Grande")){
						e.setTamanhoDoAcesso("Grande");
					}else if (spEstTamanhoAcesso.getSelectedItem().equals("Pequeno")){
						e.setTamanhoDoAcesso("Pequeno");
					}else{
						e.setTamanhoDoAcesso("Desconhecido");
					}
					
					int difEntrada;
					if (spEstDifAcesso.getSelectedItem().equals("Leve")){
						difEntrada = 1;
					} else if (spEstDifAcesso.getSelectedItem().equals("Média")){
						difEntrada = 2;
					} else if (spEstDifAcesso.getSelectedItem().equals("Difícil")){
						difEntrada = 3;
					} else if(spEstDifAcesso.getSelectedItem().equals("Muito Difícil")){
						difEntrada = 4;
					}else{
						difEntrada = 0;
					}
					e.setDificuldadeDeEntrada(difEntrada);
					
					int evolTrab;
					if (spEstEvolucao.getSelectedItem().equals("Não começou")){
						evolTrab = 1;
					} else if (spEstEvolucao.getSelectedItem().equals("Em andamento")){
						evolTrab = 2;
					} else if (spEstEvolucao.getSelectedItem().equals("Finalizado")){
						evolTrab = 3;
					} else if(spEstEvolucao.getSelectedItem().equals("Em pausa")){
						evolTrab = 4;
					}else{
						evolTrab = 0;
					}
					e.setEvolucaoDoTrabalho(evolTrab);
					
					String tituloBalao = etEstNome.getText().toString();
					e.setTituloBalao(tituloBalao);
					e.setIdentificacao(tituloBalao);
					
					String tipoDeEstrutura = "Desconhecido";
					if(!spEstTipo.getSelectedItem().equals("Desconhecido") && !outroTipo){
						tipoDeEstrutura = spEstTipo.getSelectedItem().toString();
					}
					if(outroTipo){
						tipoDeEstrutura = etEstTipo.getText().toString();
					}
					
					e.setTipoDeEstrutura(tipoDeEstrutura);
					
					e.setTipoDeMaterial(setStringEditText(etEstMaterial));
					
					e.setTipoDeSubsolo(setStringEditText(etTipoSubterraneo));
					
					int afluenciaPublico = 0;
					try{
						afluenciaPublico = Integer.parseInt(etEstAfluencia.getText().toString());
					}catch(NumberFormatException nfe){
						afluenciaPublico = -1;
					}
					e.setAfluenciaPublico(afluenciaPublico);
					
					int numAndares = 0;
					try{
						numAndares = Integer.parseInt(etNumAndares.getText().toString());
					}catch(NumberFormatException nfe){
						numAndares = -1;
					}
					e.setNumAndares(numAndares);
					
					int numSubsolos = 0;
					try{
						numSubsolos = Integer.parseInt(etNumSubterraneos.getText().toString());
					}catch(NumberFormatException nfe){
						numSubsolos = -1;
					}
					e.setNumSubsolos(numSubsolos);
					
					int qtdPessoasPiso;
					try{
						qtdPessoasPiso = Integer.parseInt(etQtdPessoasPiso.getText().toString());
					}catch(NumberFormatException nfe){
						qtdPessoasPiso = -1;
					}
					e.setQtdPessoasPiso(qtdPessoasPiso);
					
					int resistenciaPiso = 0;
					try{
						resistenciaPiso = Integer.parseInt(etResistenciaPiso.getText().toString());
					}catch(NumberFormatException nfe){
						resistenciaPiso = -1;
					}
					e.setResistenciaPiso(resistenciaPiso);
					
					int tempoAcesso;
					try{
						tempoAcesso = Integer.parseInt(etTempoEstimadoAcesso.getText().toString());
					}catch(NumberFormatException nfe){
						tempoAcesso = -1;
					}
					e.setTempoAcesso(tempoAcesso);
					
					
					Set<Equipe> setEquipe = new TreeSet<Equipe>();
					for (TablerowPDIRelacionado tr : ltrTeam) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Equipe equipe : listaEquipes) {
								if (s.equals(equipe.toString())) setEquipe.add(equipe);
								else continue;
							}
						}
					}
					
					Set<Perigo> setPerigo = new TreeSet<Perigo>();
					for (TablerowPDIRelacionado tr : ltrPer) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Perigo perigo : listaPerigos) {
								if (s.equals(perigo.toString())) setPerigo.add(perigo);
								else continue;
							}
						}
					}
					
					Set<Vitima> setVitima = new TreeSet<Vitima>();
					for (TablerowPDIRelacionado tr : ltrVit) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Vitima vitima : listaVitimas) {
								if (s.equals(vitima.toString())) setVitima.add(vitima);
								else continue;
							}
						}
					}
					
					for (Equipe team : e.getEquipe()) {
						if(setEquipe.contains(team)) continue;
						else team.getEstrutura().remove(e);
					}
					
					for (Vitima vit : e.getVitima()) {
						if(setVitima.contains(vit)) continue;
						else vit.getEstrutura().remove(e);
					}
					
					e.setEquipe(setEquipe);
					e.setPerigo(setPerigo);
					e.setVitima(setVitima);
					
					
					if(setEquipe != null && !setEquipe.isEmpty()){
						for (Iterator<Equipe> it = setEquipe.iterator(); it.hasNext();) {
							Equipe equipe = (Equipe) it.next();
							equipe.getEstrutura().add(e);
						}
					}
					
					if(setVitima != null && !setVitima.isEmpty()){
						for (Iterator<Vitima> it = setVitima.iterator(); it.hasNext();) {
							Vitima vitima = (Vitima) it.next();
							vitima.getEstrutura().add(e);
						}
					}
					
					e.atualizaTextoBalao();
					EditarPDI.atualizarPDI(e);
					MainActivity.estruturaOverlays.hideBalloon();
					finish();
					Global.editarPDI=false;
					MainActivity.exibeToast(getApplicationContext(), "Dados atualizados");
					Global.editarPDI(e.getXML());
				}
			}  
		});
	}
	
	@Override
	protected void setAdapterPositionFromSet(Spinner inicial, Set<? extends PDI> set){
		if(set.size() == 1){
			setAdapterPositionFromString(inicial, set.iterator().next().toString());
		}else if (set.size() > 1){
			Iterator<? extends PDI> iterator = set.iterator();
			PDI primeiro = iterator.next();
			if(primeiro instanceof Equipe){
				spEstEquipeMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrTeam.get(ltrTeam.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrTeam.size(); i++) ltrTeam.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Perigo){
				spEstPerigoMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrPer.get(ltrPer.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrPer.size(); i++) ltrPer.get(i-1).getSpinner().setSelection(i);
			}
		}
	}
}