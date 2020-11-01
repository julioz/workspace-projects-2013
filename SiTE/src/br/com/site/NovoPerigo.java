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
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Vitima;
import br.com.site.view.TablerowPDIRelacionado;

import com.google.android.maps.GeoPoint;

public class NovoPerigo extends NovoPDI{

	private boolean outroTipo;
	
	private List<Equipe> listaEquipes;
	private List<Vitima> listaVitimas;
	
	private TableLayout tlPerDelete;
	private TableRow trPerigoTipo;
	
	private EditText etPerigoIdentificacao, etPerigoTipo;
	private Spinner spPerigoTipo, spPerigoRisco, spPerigoDevoIr, spPerigoTeam, spPerigoVitima;
	private ImageButton spPerigoTeamMais, spPerigoVitimaMais;
	private ImageButton salvar;
	private TextView tvTit;
	
	private ArrayList<TablerowPDIRelacionado> ltrTeam = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrVit = new ArrayList<TablerowPDIRelacionado>();
	
	ArrayAdapter<CharSequence> adtPerigoTipo, adtPerigoRisco, adtPerigoDevoIr, adtPerigoEquipe, adtPerigoVitima;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setContentView(R.layout.novo_perigo);
		
		carregaViews();
    	
    	setPDIArrayAdapter(adtPerigoEquipe, spPerigoTeam, listaEquipes);
        setPDIArrayAdapter(adtPerigoVitima, spPerigoVitima, listaVitimas);
        
        outroTipo = false;
        
        setMultiTableRow(spPerigoTeamMais, "Equipe:", listaEquipes, R.novo_perigo.tlEquipe, ltrTeam);
		setMultiTableRow(spPerigoVitimaMais, "Vítima:", listaVitimas, R.novo_perigo.tlVitima, ltrVit);
        
        if(!Global.editarPDI) novoPDI();
        else editarPDI();
        
	}
	
	private void carregaViews(){
		listaEquipes = br.com.site.MainActivity.listaEquipes;
		listaVitimas = br.com.site.MainActivity.listaVitimas;
		
		tlPerDelete = (TableLayout) findViewById(R.novo_perigo.tlPerDelete);
		tlPerDelete.setVisibility(View.GONE);
		
		trPerigoTipo = (TableRow) findViewById(R.novo_perigo.trPerigoTipo);
		
		etPerigoIdentificacao = (EditText) findViewById(R.novo_perigo.etPerigoIdentificacao);
		etPerigoTipo = (EditText) findViewById(R.novo_perigo.etPerigoTipoDePerigo);
		spPerigoTipo = (Spinner) findViewById(R.novo_perigo.spPerigoTipo);
		
		spPerigoRisco = (Spinner) findViewById(R.novo_perigo.spPerigoRiscoAssociado);
		spPerigoDevoIr = (Spinner) findViewById(R.novo_perigo.spPerigoDevoIr);
		spPerigoTeam = (Spinner) findViewById(R.novo_perigo.spPerigoTeam);
		ltrTeam.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_perigo.trInicialEquipe), "Equipe:", spPerigoTeam, spPerigoTeamMais));
		spPerigoVitima = (Spinner) findViewById(R.novo_perigo.spPerigoVitima);
		ltrVit.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_perigo.trInicialVitima), "Vítima:", spPerigoVitima, spPerigoVitimaMais));
    	
		spPerigoTeamMais = (ImageButton) findViewById(R.novo_perigo.spPerigoTeamMais);
		spPerigoVitimaMais = (ImageButton) findViewById(R.novo_perigo.spPerigoVitimaMais);
		
    	salvar = (ImageButton) findViewById(R.novo_perigo.perigoSalvar);
    	tvTit = (TextView) findViewById(R.novo_perigo.tvPerTit);
    	
    	adtPerigoTipo = ArrayAdapter.createFromResource(this, R.array.perigoTipo_array, R.layout.textviewmodelo);
    	adtPerigoTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPerigoTipo.setAdapter(adtPerigoTipo);
    	
    	adtPerigoRisco = ArrayAdapter.createFromResource(this, R.array.perigoRisco_array, R.layout.textviewmodelo);
    	adtPerigoRisco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPerigoRisco.setAdapter(adtPerigoRisco);
        
        adtPerigoDevoIr = ArrayAdapter.createFromResource(this, R.array.perigoDevoIr_array, R.layout.textviewmodelo);
        adtPerigoDevoIr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPerigoDevoIr.setAdapter(adtPerigoDevoIr);
        
        adtPerigoEquipe = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtPerigoEquipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        adtPerigoVitima = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtPerigoVitima.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	private void novoPDI(){
		spPerigoTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg0.getSelectedItem().toString() == "Outro..."){
					trPerigoTipo.setVisibility(View.VISIBLE);
					outroTipo = true;
				}else{
					trPerigoTipo.setVisibility(View.GONE);
					outroTipo = false;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
		});        	
    	
    	salvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isEditTextEmpty(etPerigoIdentificacao)){
					salvar.setImageResource(R.drawable.bt_aplicar);
					GeoPoint temp = new GeoPoint(0, 0);
					String identif = etPerigoIdentificacao.getText().toString();
					String tipoPerigo = "Desconhecido";
					
					if(!spPerigoTipo.getSelectedItem().equals("Desconhecido") && !outroTipo){
						tipoPerigo = spPerigoTipo.getSelectedItem().toString();
					}
					
					if(outroTipo){
						tipoPerigo = etPerigoTipo.getText().toString();
					}
					
					int riscoAssociado = 5;
					if (spPerigoRisco.getSelectedItem().equals("Desconhecido")){
						riscoAssociado = 0;
					}else if (spPerigoRisco.getSelectedItem().equals("Nulo")){
						riscoAssociado = 1;
					}else if (spPerigoRisco.getSelectedItem().equals("Baixo")){
						riscoAssociado = 2;
					}else if (spPerigoRisco.getSelectedItem().equals("Médio")){
						riscoAssociado = 3;
					}else if (spPerigoRisco.getSelectedItem().equals("Alto")){
						riscoAssociado = 4;
					}else{
						riscoAssociado = 5;
					}
					
					String devoIr = "Desconhecido";
					if (spPerigoDevoIr.getSelectedItem().equals("Sim")){
						devoIr = "Sim";
					}else if (spPerigoDevoIr.getSelectedItem().equals("Não")){
						devoIr = "Não";
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
					
					Perigo per = new Perigo(Global.getNumPDI(), temp,
							identif, setEquipe, setVitima,
							tipoPerigo, riscoAssociado, devoIr, identif);
					
					if(setEquipe != null && !setEquipe.isEmpty()){
						for (Iterator<Equipe> it = setEquipe.iterator(); it.hasNext();) {
							Equipe equipe = (Equipe) it.next();
							equipe.getPerigo().add(per);
						}
					}
					
					if(setVitima != null && !setVitima.isEmpty()){
						for (Iterator<Vitima> it = setVitima.iterator(); it.hasNext();) {
							Vitima vit = (Vitima) it.next();
							vit.getPerigo().add(per);
						}
					}
					
					br.com.site.MainActivity.objetoPassado = per;
					Global.novoPdi = true;
					finish();
					br.com.site.MainActivity.exibeToast(getApplicationContext(), "Toque na tela para posicionar o perigo.");
				}
			}
		});
	}
	
	private void editarPDI(){
		salvar.setImageResource(R.drawable.bt_edit);
    	tvTit.setText("Editar Perigo");
    	tlPerDelete.setVisibility(View.VISIBLE);
    	
    	final TextView tvDel = (TextView) findViewById(R.novo_perigo.tvDelPer);
    	final ImageView ivDel = (ImageView) findViewById(R.novo_perigo.ivDelPer);
    	
    	final Perigo p = (Perigo) Global.pdiEdicao;
    	
    	tvDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoPerigo.this, p, p.getXML());
			}
		});
    	
    	ivDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoPerigo.this, p, p.getXML());
			}
		});
    	
    	spPerigoTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg0.getSelectedItem().toString().equals("Outro...")){
					trPerigoTipo.setVisibility(View.VISIBLE);
					etPerigoTipo.setText(p.getTipoDePerigo());
					outroTipo = true;
				}else{
					trPerigoTipo.setVisibility(View.GONE);
					outroTipo = false;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});        	
    	
    	etPerigoIdentificacao.setText(p.getIdentificacao());
    	
    	int c = Global.strPer.length;
    	for (String s : Global.strPer) {
			if(p.getTipoDePerigo() != "Desconhecido" && !p.getTipoDePerigo().equals(s)){
				c--;
			}
		}
    	if (c == 0){
    		setAdapterPositionFromString(spPerigoTipo,"Outro...");
    	}else{
    		setAdapterPositionFromString(spPerigoTipo,p.getTipoDePerigo());
    	}
    	
    	setAdapterPositionFromString(spPerigoRisco,p.getRiscoAssociado());
    	setAdapterPositionFromString(spPerigoDevoIr,p.getDevoIr());
    	
    	
    	try{
    		setAdapterPositionFromSet(spPerigoTeam, p.getEquipe());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spPerigoTeam, "Desconhecido");
		}try{
			setAdapterPositionFromSet(spPerigoVitima, p.getVitima());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spPerigoVitima, "Desconhecido");
		}
    	
    	salvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isEditTextEmpty(etPerigoIdentificacao)){
					MainActivity.listaPerigos.remove(p);
					
					p.setIdentificacao(getStringEditText(etPerigoIdentificacao));
					
					String tipoPerigo = "Desconhecido";
					
					if(!spPerigoTipo.getSelectedItem().equals("Desconhecido") && !outroTipo) tipoPerigo = spPerigoTipo.getSelectedItem().toString();
					
					if(outroTipo) tipoPerigo = etPerigoTipo.getText().toString();
					p.setTipoDePerigo(tipoPerigo);
					
					int riscoAssociado = 5;
					if (spPerigoRisco.getSelectedItem().equals("Desconhecido")){
						riscoAssociado = 0;
					}else if (spPerigoRisco.getSelectedItem().equals("Nulo")){
						riscoAssociado = 1;
					}else if (spPerigoRisco.getSelectedItem().equals("Baixo")){
						riscoAssociado = 2;
					}else if (spPerigoRisco.getSelectedItem().equals("Médio")){
						riscoAssociado = 3;
					}else if (spPerigoRisco.getSelectedItem().equals("Alto")){
						riscoAssociado = 4;
					}else{
						riscoAssociado = 5;
					}
					
					p.setRiscoAssociado(riscoAssociado);
					
					String devoIr = "Desconhecido";
					if (spPerigoDevoIr.getSelectedItem().equals("Sim")){
						devoIr = "Sim";
					}else if (spPerigoDevoIr.getSelectedItem().equals("Não")){
						devoIr = "Não";
					}
					p.setDevoIr(devoIr);
					
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
					
					for (Equipe team : p.getEquipe()) {
						if(setEquipe.contains(team)) continue;
						else team.getPerigo().remove(p);
					}
					
					for (Vitima vit : p.getVitima()) {
						if(setVitima.contains(vit)) continue;
						else vit.getPerigo().remove(p);
					}
					
					if (setEquipe != null) p.setEquipe(setEquipe);
					if (setVitima != null) p.setVitima(setVitima);
					
					if(setEquipe != null && !setEquipe.isEmpty()){
						for (Iterator<Equipe> it = setEquipe.iterator(); it.hasNext();) {
							Equipe equipe = (Equipe) it.next();
							equipe.getPerigo().add(p);
						}
					}
					
					if(setVitima != null && !setVitima.isEmpty()){
						for (Iterator<Vitima> it = setVitima.iterator(); it.hasNext();) {
							Vitima vit = (Vitima) it.next();
							vit.getPerigo().add(p);
						}
					}
					
					EditarPDI.atualizarPDI(p);
					MainActivity.perigoOverlays.hideBalloon();
					finish();
					Global.editarPDI=false;
					MainActivity.exibeToast(getApplicationContext(), "Dados atualizados");
					Global.editarPDI(p.getXML());
				}
			}
		});
	}
	
	@Override
	protected void setAdapterPositionFromSet(Spinner inicial, Set<? extends PDI> set){ //FIXME
		if(set.size() == 1){
			setAdapterPositionFromString(inicial, set.iterator().next().toString());
		}else if (set.size() > 1){
			Iterator<? extends PDI> iterator = set.iterator();
			PDI primeiro = iterator.next();
			if(primeiro instanceof Equipe){
				spPerigoTeamMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrTeam.get(ltrTeam.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrTeam.size(); i++) ltrTeam.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Vitima){
				spPerigoVitimaMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrVit.get(ltrVit.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrVit.size(); i++) ltrVit.get(i-1).getSpinner().setSelection(i);				
			}
		}
	}
}