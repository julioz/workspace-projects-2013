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
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Instituicao;
import br.com.site.model.PDI;
import br.com.site.model.Veiculo;
import br.com.site.view.TablerowPDIRelacionado;

import com.google.android.maps.GeoPoint;

public class NovoVeiculo extends NovoPDI{
	
	private List<Equipamento> listaEquipamentos;
	private List<Equipe> listaEquipes;
	private List<Instituicao> listaInstituicoes;
	
	private TableLayout tlVeiDelete;
	private TableRow trSubtipoVeiculo;
	
	private EditText etVeiIdentificacao;
	private Spinner spVeiTipoDeVeiculo, spVeiSubtipo, spVeiEquipamento, spVeiEquipe, spVeiInst;
	private ImageButton spVeiEquipamentoMais, spVeiEquipeMais;
	private ImageButton salvar;
	private TextView tvTit;
	
	private ArrayList<TablerowPDIRelacionado> ltrEquip = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrTeam = new ArrayList<TablerowPDIRelacionado>();
	
	private ArrayAdapter<CharSequence> adtVeiSubAmb, adtVeiSubAer, adtVeiSubTer, adtVeiSubAqu;
	private ArrayAdapter<CharSequence> adtVeiTipo;
	private ArrayAdapter<CharSequence> adtVeiEquipamento, adtVeiEquipe, adtVeiInst;
	
	private Veiculo v;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setContentView(R.layout.novo_veiculo);
		
		carregaViews();
		
		tlVeiDelete.setVisibility(View.GONE);
		trSubtipoVeiculo.setVisibility(View.GONE);
		
		setPDIArrayAdapter(adtVeiEquipamento, spVeiEquipamento, listaEquipamentos);
		setPDIArrayAdapter(adtVeiEquipe, spVeiEquipe, listaEquipes);
		setPDIArrayAdapter(adtVeiInst, spVeiInst, listaInstituicoes);
		
		setMultiTableRow(spVeiEquipamentoMais, "Equipamento:", listaEquipamentos, R.novo_veiculo.tlEquipamento, ltrEquip);
		setMultiTableRow(spVeiEquipeMais, "Equipe:", listaEquipes, R.novo_veiculo.tlEquipe, ltrTeam);
        
        if(!Global.editarPDI) novoPDI();
        else editarPDI();
        
	}
	
	private void carregaViews(){
		listaEquipamentos = br.com.site.MainActivity.listaEquipamentos;
		listaEquipes = br.com.site.MainActivity.listaEquipes;
		listaInstituicoes = br.com.site.MainActivity.listaInstituicoes;

		tlVeiDelete = (TableLayout) findViewById(R.novo_veiculo.tlVeiDelete);
		trSubtipoVeiculo = (TableRow) findViewById(R.novo_veiculo.trSubTipoVeiculo);

		etVeiIdentificacao = (EditText) findViewById(R.novo_veiculo.etVeiIdentificacao);

		spVeiTipoDeVeiculo = (Spinner) findViewById(R.novo_veiculo.spVeiTipoDeVeiculo);
		spVeiSubtipo = (Spinner) findViewById(R.novo_veiculo.spVeiSubtipo);
		spVeiEquipamento = (Spinner) findViewById(R.novo_veiculo.spVeiEquipamento);
		ltrEquip.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_veiculo.trInicialEquipamento), "Equipamento:", spVeiEquipamento, spVeiEquipamentoMais));
		spVeiEquipe = (Spinner) findViewById(R.novo_veiculo.spVeiEquipe);
		ltrTeam.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_veiculo.trInicialEquipe), "Equipe:", spVeiEquipe, spVeiEquipeMais));
		spVeiInst = (Spinner) findViewById(R.novo_veiculo.spVeiInst);

		spVeiEquipamentoMais = (ImageButton) findViewById(R.novo_veiculo.spVeiEquipamentoMais);
		spVeiEquipeMais = (ImageButton) findViewById(R.novo_veiculo.spVeiEquipeMais);

		salvar = (ImageButton) findViewById(R.novo_veiculo.veiSalvar);

		tvTit = (TextView) findViewById(R.novo_veiculo.tvVeiTit);

		adtVeiSubAmb = ArrayAdapter.createFromResource(this, R.array.veiculoAmbulancia_array, R.layout.textviewmodelo);
		adtVeiSubAmb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adtVeiSubAer = ArrayAdapter.createFromResource(this, R.array.veiculoAereo_array, R.layout.textviewmodelo);
		adtVeiSubAer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adtVeiSubTer = ArrayAdapter.createFromResource(this, R.array.veiculoTerrestre_array, R.layout.textviewmodelo);
		adtVeiSubTer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adtVeiSubAqu = ArrayAdapter.createFromResource(this, R.array.veiculoAquatico_array, R.layout.textviewmodelo);
		adtVeiSubAqu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adtVeiTipo = ArrayAdapter.createFromResource(this, R.array.veiculoTipo_array, R.layout.textviewmodelo);
		adtVeiTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spVeiTipoDeVeiculo.setAdapter(adtVeiTipo);
	}
	
	private void novoPDI(){
		spVeiTipoDeVeiculo.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg0.getSelectedItem().toString().equals("Desconhecido")){
					trSubtipoVeiculo.setVisibility(View.GONE);
				}else if(arg0.getSelectedItem().toString().equals("Ambulância")){
					spVeiSubtipo.setAdapter(adtVeiSubAmb);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
				}else if(arg0.getSelectedItem().toString().equals("Veículo Aéreo")){
					spVeiSubtipo.setAdapter(adtVeiSubAer);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
				}else if(arg0.getSelectedItem().toString().equals("Veículo de Socorro Terrestre")){
					spVeiSubtipo.setAdapter(adtVeiSubTer);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
				}else if(arg0.getSelectedItem().toString().equals("Veículo de Socorro Aquático")){
					spVeiSubtipo.setAdapter(adtVeiSubAqu);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
		});
    	
    	
    	salvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!isEditTextEmpty(etVeiIdentificacao)){
					salvar.setImageResource(R.drawable.bt_aplicar);
					GeoPoint temp = new GeoPoint(0, 0);
					String identif = etVeiIdentificacao.getText().toString();
					String tipo = spVeiTipoDeVeiculo.getSelectedItem().toString();
					String subtipoVei = "Desconhecido";
					if (!spVeiTipoDeVeiculo.getSelectedItem().equals("Desconhecido")){
						subtipoVei = spVeiSubtipo.getSelectedItem().toString();
					}
					
					TreeSet<Equipamento> setEquipamento = new TreeSet<Equipamento>();
					//popularSet(setEquipamento, ltrEquip, listaEquipamentos); //TODO
					for (TablerowPDIRelacionado tr : ltrEquip) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Equipamento equip : listaEquipamentos) {
								if (s.equals(equip.toString())) setEquipamento.add(equip);
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
					
					Instituicao in = null;
					for (Instituicao i : listaInstituicoes) {
						if (spVeiInst.getSelectedItem().equals(i.toString())){
							in = i;
						}else{
							continue;
						}
					}
					
					v = new Veiculo(Global.getNumPDI(), temp,
							identif, setEquipamento, setEquipe,
							identif, tipo, subtipoVei, in);
					
					if(setEquipamento != null && !setEquipamento.isEmpty()){
						for (Iterator<Equipamento> it = setEquipamento.iterator(); it.hasNext();) {
							Equipamento equipamento = (Equipamento) it.next();
							equipamento.getVeiculo().add(v);
						}
					}
					
					MainActivity.objetoPassado = v;
					Global.novoPdi = true;
					finish();
					MainActivity.exibeToast(getApplicationContext(), "Toque na tela para posicionar o veículo");
				}
			}
		});
	}
	
	private void editarPDI(){
		salvar.setImageResource(R.drawable.bt_edit);
    	tvTit.setText("Editar Veículo");
    	tlVeiDelete.setVisibility(View.VISIBLE);
    	
    	final TextView tvDel = (TextView) findViewById(R.novo_veiculo.tvDelVei);
    	final ImageView ivDel = (ImageView) findViewById(R.novo_veiculo.ivDelVei);
    	
    	this.v = (Veiculo) Global.pdiEdicao;
    	
    	tvDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoVeiculo.this, v, v.getXML());
			}
		});
    	
    	ivDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoVeiculo.this, v, v.getXML());
			}
		});

    	
    	spVeiTipoDeVeiculo.setOnItemSelectedListener(new OnItemSelectedListener() {
    		String sub = v.getSubtipo();
    		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg0.getSelectedItem().toString().equals("Desconhecido")){
					trSubtipoVeiculo.setVisibility(View.GONE);
				}else if(arg0.getSelectedItem().toString().equals("Ambulância")){
					spVeiSubtipo.setAdapter(adtVeiSubAmb);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
					setAdapterPositionFromString(adtVeiSubAmb,spVeiSubtipo,sub);
				}else if(arg0.getSelectedItem().toString().equals("Veículo Aéreo")){
					spVeiSubtipo.setAdapter(adtVeiSubAer);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
					setAdapterPositionFromString(adtVeiSubAer,spVeiSubtipo,sub);
				}else if(arg0.getSelectedItem().toString().equals("Veículo de Socorro Terrestre")){
					spVeiSubtipo.setAdapter(adtVeiSubTer);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
					setAdapterPositionFromString(adtVeiSubTer,spVeiSubtipo,sub);
				}else if(arg0.getSelectedItem().toString().equals("Veículo de Socorro Aquático")){
					spVeiSubtipo.setAdapter(adtVeiSubAqu);
					trSubtipoVeiculo.setVisibility(View.VISIBLE);
					setAdapterPositionFromString(adtVeiSubAqu,spVeiSubtipo,sub);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
		});
    	
    	
    	etVeiIdentificacao.setText(v.getIdentificacao());
    	setAdapterPositionFromString(spVeiTipoDeVeiculo,v.getTipoDeVeiculo());
    	
    	try{
    		setAdapterPositionFromString(spVeiInst,v.getInst().toString());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spVeiInst, "Desconhecido");
		}
    	
    	try{
    		setAdapterPositionFromSet(spVeiEquipamento, v.getEquipamento());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spVeiEquipamento, "Desconhecido");
		}
    	try{
    		setAdapterPositionFromSet(spVeiEquipe,v.getEquipe());
    	}catch (NullPointerException e) {
    		setAdapterPositionFromString(spVeiEquipe, "Desconhecido");
		}
    	
    	salvar.setOnClickListener(new OnClickListener() {
    		@Override
			public void onClick(View view) {
    			if(!isEditTextEmpty(etVeiIdentificacao)){
    				MainActivity.listaVeiculos.remove(v);
    				
    				v.setIdentificacao(getStringEditText(etVeiIdentificacao));
    				
    				v.setTipoDeVeiculo(spVeiTipoDeVeiculo.getSelectedItem().toString());
    				
    				String subtipoVei = "Desconhecido";
    				if (!spVeiTipoDeVeiculo.getSelectedItem().equals("Desconhecido")) subtipoVei = spVeiSubtipo.getSelectedItem().toString();
    				v.setSubtipo(subtipoVei);
    				
    				Set<Equipamento> setEquipamento = new TreeSet<Equipamento>();
    				for (TablerowPDIRelacionado tr : ltrEquip) {
    					String s = tr.getSpinner().getSelectedItem().toString();
    					if(s.equals("Desconhecido")) continue;
    					else{
    						for (Equipamento equip : listaEquipamentos) {
    							if (s.equals(equip.toString())) setEquipamento.add(equip);
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
    				
    				Instituicao in = null;
    				for (Instituicao i : listaInstituicoes) {
    					if (spVeiInst.getSelectedItem().equals(i.toString())){
    						in = i;
    					}else{
    						continue;
    					}
    				}
    				
    				for (Equipamento equip : v.getEquipamento()) {
						if(setEquipamento.contains(equip)) continue;
						else equip.getVeiculo().remove(v);
					}
    				
    				v.setEquipamento(setEquipamento);
    				v.setEquipe(setEquipe);
    				v.setInst(in);
    				v.atualizaTextoBalao();
    				
    				if(setEquipamento != null && !setEquipamento.isEmpty()){
    					for (Iterator<Equipamento> it = setEquipamento.iterator(); it.hasNext();) {
    						Equipamento equipamento = (Equipamento) it.next();
    						equipamento.getVeiculo().add(v);
    					}
    				}
    				
    				EditarPDI.atualizarPDI(v);
    				MainActivity.veiculoOverlays.hideBalloon();
    				finish();
    				Global.editarPDI = false;
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
			if(primeiro instanceof Equipamento){
				spVeiEquipamentoMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrEquip.get(ltrEquip.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrEquip.size(); i++) ltrEquip.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Equipe){
				spVeiEquipeMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrTeam.get(ltrTeam.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrTeam.size(); i++) ltrTeam.get(i-1).getSpinner().setSelection(i);
			}
		}
	}	
}