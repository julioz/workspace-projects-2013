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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Vitima;
import br.com.site.view.TablerowPDIRelacionado;

import com.google.android.maps.GeoPoint;

public class NovoVitima extends NovoPDI{

	private List<Estrutura> listaEstruturas;
	private List<Equipe> listaEquipes;
	private List<Perigo> listaPerigos;
	
	private TableLayout tlVitimaDelete;
	
	private EditText etVitimaIdentificacao, etVitimaNumPosVit, etVitimaQtdVivos, etVitimaQtdMortos, etVitimaQtdVivosResgatados, etVitimaQtdMortosRemov;
	private Spinner spVitimaEstrut, spVitimaTeam, spVitimaPerigo;
	private ImageButton spVitimaEstrutMais, spVitimaTeamMais, spVitimaPerigoMais;
	private ImageButton salvar;
	private TextView tvTit;
	
	private ArrayList<TablerowPDIRelacionado> ltrEst = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrTeam = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrPer = new ArrayList<TablerowPDIRelacionado>();
	
	private ArrayAdapter<CharSequence> adtVitimaEstrut, adtVitimaTeam, adtVitimaPerigo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setContentView(R.layout.novo_vitima);
		
		carregaViews();
		
		setPDIArrayAdapter(adtVitimaEstrut, spVitimaEstrut, listaEstruturas);
    	setPDIArrayAdapter(adtVitimaTeam, spVitimaTeam, listaEquipes);
    	setPDIArrayAdapter(adtVitimaPerigo, spVitimaPerigo, listaPerigos);
    	
    	setMultiTableRow(spVitimaEstrutMais, "Estrutura:", listaEstruturas, R.novo_vitima.tlEstrutura, ltrEst);
    	setMultiTableRow(spVitimaTeamMais, "Equipe:", listaEquipes, R.novo_vitima.tlEquipe, ltrTeam);
    	setMultiTableRow(spVitimaPerigoMais, "Perigo:", listaPerigos, R.novo_vitima.tlPerigo, ltrPer);
        
    	if(!Global.editarPDI) novoPDI();
        else editarPDI();
        
	}
	
	private void carregaViews(){
		listaEstruturas = br.com.site.MainActivity.listaEstruturas;
		listaEquipes = br.com.site.MainActivity.listaEquipes;
		listaPerigos = br.com.site.MainActivity.listaPerigos;
		
		tlVitimaDelete = (TableLayout) findViewById(R.novo_vitima.tlVitDelete);
		tlVitimaDelete.setVisibility(View.GONE);
		
		etVitimaIdentificacao = (EditText) findViewById(R.novo_vitima.etVitimaIdentificacao);
		etVitimaNumPosVit = (EditText) findViewById(R.novo_vitima.etVitimaNumPosVit);
		etVitimaQtdVivos = (EditText) findViewById(R.novo_vitima.etVitimaQtdVivos);
		etVitimaQtdMortos = (EditText) findViewById(R.novo_vitima.etVitimaQtdMortos);
		etVitimaQtdVivosResgatados = (EditText) findViewById(R.novo_vitima.etVitimaQtdVivosResgatados);
		etVitimaQtdMortosRemov = (EditText) findViewById(R.novo_vitima.etVitimaQtdMortosRemov);
    	
		spVitimaEstrut = (Spinner) findViewById(R.novo_vitima.spVitimaEstrut);
		ltrEst.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_vitima.trInicialEstrutura), "Estrutura:", spVitimaEstrut, spVitimaEstrutMais));
		spVitimaTeam = (Spinner) findViewById(R.novo_vitima.spVitimaTeam);
		ltrTeam.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_vitima.trInicialEquipe), "Equipe:", spVitimaTeam, spVitimaTeamMais));
		spVitimaPerigo = (Spinner) findViewById(R.novo_vitima.spVitimaPerigo);
		ltrPer.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_vitima.trInicialPerigo), "Perigo:", spVitimaPerigo, spVitimaPerigoMais));
    	
		spVitimaEstrutMais = (ImageButton) findViewById(R.novo_vitima.spVitimaEstrutMais);
		spVitimaTeamMais = (ImageButton) findViewById(R.novo_vitima.spVitimaTeamMais);
		spVitimaPerigoMais = (ImageButton) findViewById(R.novo_vitima.spVitimaPerigoMais);
		salvar = (ImageButton) findViewById(R.novo_vitima.vitimaSalvar);
		tvTit = (TextView) findViewById(R.novo_vitima.tvVitTit);
		
		
		adtVitimaEstrut = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
    	adtVitimaEstrut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	adtVitimaTeam = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtVitimaTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        adtVitimaPerigo = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtVitimaPerigo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
	}
	
	private void novoPDI(){
		salvar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (!isEditTextEmpty(etVitimaIdentificacao) && !isEditTextBiggerValue(etVitimaQtdMortos, "quantidade de mortos", etVitimaQtdMortosRemov, "quantidade de mortos removidos") && !isEditTextBiggerValue(etVitimaQtdVivos, "quantidade de vivos", etVitimaQtdVivosResgatados, "quantidade de vivos resgatados")) {
					salvar.setImageResource(R.drawable.bt_aplicar);
					GeoPoint temp = new GeoPoint(0, 0);
					String identif = etVitimaIdentificacao.getText().toString();
					
					int numPosVit = -1;
					try{
						numPosVit = Integer.parseInt(etVitimaNumPosVit.getText().toString());
					}catch(NumberFormatException nfe){
						numPosVit = -1;
					}
					
					int qtdVivos = -1;
					try{
						qtdVivos = Integer.parseInt(etVitimaQtdVivos.getText().toString());
					}catch(NumberFormatException nfe){
						qtdVivos = -1;
					}
					
					int qtdMortos = -1;
					try{
						qtdMortos = Integer.parseInt(etVitimaQtdMortos.getText().toString());
					}catch(NumberFormatException nfe){
						qtdMortos = -1;
					}
					
					int qtdVivosResg = -1;
					try{
						qtdVivosResg = Integer.parseInt(etVitimaQtdVivosResgatados.getText().toString());
					}catch(NumberFormatException nfe){
						qtdVivosResg = -1;
					}
					
					int qtdMortosRemov = -1;
					try{
						qtdMortosRemov = Integer.parseInt(etVitimaQtdMortosRemov.getText().toString());
					}catch(NumberFormatException nfe){
						qtdMortosRemov = -1;
					}
					
					Set<Estrutura> setEstrutura = new TreeSet<Estrutura>();
					for (TablerowPDIRelacionado tr : ltrEst) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Estrutura est : listaEstruturas) {
								if (s.equals(est.toString())) setEstrutura.add(est);
								else continue;
							}
						}
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
							for (Perigo per : listaPerigos) {
								if (s.equals(per.toString())) setPerigo.add(per);
								else continue;
							}
						}
					}
					
					if(numPosVit<1 && qtdVivos>0 && qtdMortos>0){
						numPosVit = qtdVivos + qtdMortos;
					}else if(numPosVit<1 && qtdVivos>0 && qtdMortos<1){
						numPosVit = qtdVivos;
					}else if(numPosVit<1 && qtdVivos<1 && qtdMortos>0){
						numPosVit = qtdMortos;
					}
					
					Vitima vit = new Vitima(Global.getNumPDI(), temp,
							identif, setEstrutura, setEquipe, setPerigo,
							identif, numPosVit, qtdVivos, qtdMortos, qtdVivosResg, qtdMortosRemov);
					
					if(setEstrutura != null && !setEstrutura.isEmpty()){
						for (Iterator<Estrutura> it = setEstrutura.iterator(); it.hasNext();) {
							Estrutura estrutura = (Estrutura) it.next();
							estrutura.getVitima().add(vit);
						}
					}
					if(setPerigo != null && !setPerigo.isEmpty()){
						for (Iterator<Perigo> it = setPerigo.iterator(); it.hasNext();) {
							Perigo per = (Perigo) it.next();
							per.getVitima().add(vit);
						}
					}
					
					br.com.site.MainActivity.objetoPassado = vit;
					Global.novoPdi = true;
					finish();
					br.com.site.MainActivity.exibeToast(getApplicationContext(), "Toque na tela para posicionar a vítima.");
				}
			}
		});
	}
	
	private void editarPDI(){
		salvar.setImageResource(R.drawable.bt_edit);
    	tvTit.setText("Editar Vítima");
    	tlVitimaDelete.setVisibility(View.VISIBLE);
    	
    	final TextView tvDel = (TextView) findViewById(R.novo_vitima.tvDelVit);
    	final ImageView ivDel = (ImageView) findViewById(R.novo_vitima.ivDelVit);
    	
    	final Vitima v = (Vitima) Global.pdiEdicao;
    	
    	tvDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoVitima.this, v, v.getXML());
			}
		});
    	
    	ivDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoVitima.this, v, v.getXML());
			}
		});

    	etVitimaIdentificacao.setText(v.getIdentificacao());
    	
    	setNumEditText(v.getNumeroPossiveisVitimas(),etVitimaNumPosVit);
    	setNumEditText(v.getQtdVivos(),etVitimaQtdVivos);
    	setNumEditText(v.getQtdMortos(),etVitimaQtdMortos);
    	setNumEditText(v.getQtdVivosResgatados(),etVitimaQtdVivosResgatados);
    	setNumEditText(v.getQtdMortosRemovidos(),etVitimaQtdMortosRemov);
    	
    	try{
    		setAdapterPositionFromSet(spVitimaEstrut, v.getEstrutura());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spVitimaEstrut, "Desconhecido");
		}
    	try{
    		setAdapterPositionFromSet(spVitimaTeam, v.getEquipe());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spVitimaTeam, "Desconhecido");
		}
    	try{
    		setAdapterPositionFromSet(spVitimaPerigo, v.getPerigo());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spVitimaPerigo, "Desconhecido");
		}
    	
    	salvar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View view) {
				if(!isEditTextEmpty(etVitimaIdentificacao) && !isEditTextBiggerValue(etVitimaQtdMortos, "quantidade de mortos", etVitimaQtdMortosRemov, "quantidade de mortos removidos") && !isEditTextBiggerValue(etVitimaQtdVivos, "quantidade de vivos", etVitimaQtdVivosResgatados, "quantidade de vivos resgatados")){
					MainActivity.listaVitimas.remove(v);
					
					v.setIdentificacao(setStringEditText(etVitimaIdentificacao));
					
					int numPosVit = -1;
					try{
						numPosVit = Integer.parseInt(etVitimaNumPosVit.getText().toString());
					}catch(NumberFormatException nfe){}
					v.setNumeroPossiveisVitimas(numPosVit);
					
					int qtdVivos = -1;
					try{
						qtdVivos = Integer.parseInt(etVitimaQtdVivos.getText().toString());
					}catch(NumberFormatException nfe){}
					v.setQtdVivos(qtdVivos);
					
					int qtdMortos = -1;
					try{
						qtdMortos = Integer.parseInt(etVitimaQtdMortos.getText().toString());
					}catch(NumberFormatException nfe){}
					v.setQtdMortos(qtdMortos);
					
					int qtdVivosResg = -1;
					try{
						qtdVivosResg = Integer.parseInt(etVitimaQtdVivosResgatados.getText().toString());
					}catch(NumberFormatException nfe){}
					v.setQtdVivosResgatados(qtdVivosResg);
					
					int qtdMortosRemov = -1;
					try{
						qtdMortosRemov = Integer.parseInt(etVitimaQtdMortosRemov.getText().toString());
					}catch(NumberFormatException nfe){}
					v.setQtdMortosRemovidos(qtdMortosRemov);
					
					Set<Estrutura> setEstrutura = new TreeSet<Estrutura>();
					for (TablerowPDIRelacionado tr : ltrEst) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Estrutura est : listaEstruturas) {
								if (s.equals(est.toString())) setEstrutura.add(est);
								else continue;
							}
						}
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
							for (Perigo per : listaPerigos) {
								if (s.equals(per.toString())) setPerigo.add(per);
								else continue;
							}
						}
					}
					
					for (Estrutura est : v.getEstrutura()) {
						if(setEstrutura.contains(est)) continue;
						else est.getVitima().remove(v);
					}
					
					for (Perigo per : v.getPerigo()) {
						if(setPerigo.contains(per)) continue;
						else per.getVitima().remove(v);
					}
					
					if (setEstrutura != null) v.setEstrutura(setEstrutura);
					if (setEquipe != null) v.setEquipe(setEquipe);
					if (setPerigo != null) v.setPerigo(setPerigo);
					
					if(setEstrutura != null && !setEstrutura.isEmpty()){
						for (Iterator<Estrutura> it = setEstrutura.iterator(); it.hasNext();) {
							Estrutura estrutura = (Estrutura) it.next();
							estrutura.getVitima().add(v);
						}
					}
					if(setPerigo != null && !setPerigo.isEmpty()){
						for (Iterator<Perigo> it = setPerigo.iterator(); it.hasNext();) {
							Perigo per = (Perigo) it.next();
							per.getVitima().add(v);
						}
					}
					
					EditarPDI.atualizarPDI(v);
					MainActivity.vitimaOverlays.hideBalloon();
					finish();
					Global.editarPDI=false;
					MainActivity.exibeToast(getApplicationContext(), "Dados atualizados");
					Global.editarPDI(v.getXML());
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
			if(primeiro instanceof Estrutura){
				spVitimaEstrutMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrEst.get(ltrEst.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrEst.size(); i++) ltrEst.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Equipe){
				spVitimaTeamMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrTeam.get(ltrTeam.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrTeam.size(); i++) ltrTeam.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Perigo){
				spVitimaPerigoMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrPer.get(ltrPer.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrPer.size(); i++) ltrPer.get(i-1).getSpinner().setSelection(i);
			}
		}
	}
}