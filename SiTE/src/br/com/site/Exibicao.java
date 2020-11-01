package br.com.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import br.com.site.MainActivity.CustomItemizedOverlay;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.Instituicao;
import br.com.site.model.Perigo;
import br.com.site.model.Veiculo;

import com.google.android.maps.Overlay;

public class Exibicao extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	
	private Map<CheckBox, String> mapVeicCB, mapEstrutCB, mapEquipeCB, mapPerigoCB;;
	private ArrayList<TableLayout> alTableLayoutCB;
	private List<Overlay> mapOverlays;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exibicao);
		
		mapOverlays = MainActivity.getMapView().getOverlays();
		
		final TableRow trExibicaoVeiculo = (TableRow) findViewById(R.exibicao.trExibicaoVeiculo);
		final TableRow trExibicaoEstrutura = (TableRow) findViewById(R.exibicao.trExibicaoEstrutura);
		final TableRow trExibicaoEquipamento = (TableRow) findViewById(R.exibicao.trExibicaoEquipamento);
		final TableRow trExibicaoEquipe = (TableRow) findViewById(R.exibicao.trExibicaoEquipe);
		final TableRow trExibicaoPerigo = (TableRow) findViewById(R.exibicao.trExibicaoPerigo);
		final TableRow trExibicaoVitima = (TableRow) findViewById(R.exibicao.trExibicaoVitima);
		
		final ImageView marcaVei = (ImageView) findViewById(R.exibicao.marcaCBVei);
		final ImageView marcaEstrut = (ImageView) findViewById(R.exibicao.marcaCBEstrut);
		final ImageView marcaEquipe = (ImageView) findViewById(R.exibicao.marcaCBEquipe);
		final ImageView marcaPerigo = (ImageView) findViewById(R.exibicao.marcaCBPerigo);
		
		final TableLayout tlExibicaoVeiculoCB = (TableLayout) findViewById(R.exibicao.tlExibicaoVeiculoCB);
		final TableLayout tlExibicaoEstruturaCB = (TableLayout) findViewById(R.exibicao.tlExibicaoEstruturaCB);
		final TableLayout tlExibicaoEquipeCB = (TableLayout) findViewById(R.exibicao.tlExibicaoEquipeCB);
		final TableLayout tlExibicaoPerigoCB = (TableLayout) findViewById(R.exibicao.tlExibicaoPerigoCB);
		alTableLayoutCB = new ArrayList<TableLayout>();
		alTableLayoutCB.add(tlExibicaoVeiculoCB);
		alTableLayoutCB.add(tlExibicaoEstruturaCB);
		alTableLayoutCB.add(tlExibicaoEquipeCB);
		alTableLayoutCB.add(tlExibicaoPerigoCB);
		
		final CheckBox cbVeiculo = (CheckBox) findViewById(R.exibicao.cbVeiculo);
		final CheckBox cbEstrutura = (CheckBox) findViewById(R.exibicao.cbEstrutura);
		final CheckBox cbEquipamento = (CheckBox) findViewById(R.exibicao.cbEquipamento);
		final CheckBox cbEquipe = (CheckBox) findViewById(R.exibicao.cbEquipe);
		final CheckBox cbPerigo = (CheckBox) findViewById(R.exibicao.cbPerigo);		
		final CheckBox cbVitima = (CheckBox) findViewById(R.exibicao.cbVitima);
		
		final CheckBox cbVeicAmb = (CheckBox) findViewById(R.exibicao.cbVeiculoAmbulancia);
		final CheckBox cbVeicAereo = (CheckBox) findViewById(R.exibicao.cbVeiculoAereos);
		final CheckBox cbVeicTerrestre = (CheckBox) findViewById(R.exibicao.cbVeiculoTerrestre);
		final CheckBox cbVeicAquatico = (CheckBox) findViewById(R.exibicao.cbVeiculoAquatico);
		mapVeicCB = new HashMap<CheckBox, String>();
		mapVeicCB.put(cbVeicAmb, "Ambulância");
		mapVeicCB.put(cbVeicAereo, "Veículo Aéreo");
		mapVeicCB.put(cbVeicTerrestre, "Veículo de Socorro Terrestre");
		mapVeicCB.put(cbVeicAquatico, "Veículo de Socorro Aquático");
		final Set<CheckBox> checkboxesVei = mapVeicCB.keySet();		
		
		final CheckBox cbEstrutHospital = (CheckBox) findViewById(R.exibicao.cbEstrutHospital);
		final CheckBox cbEstrutEstadio = (CheckBox) findViewById(R.exibicao.cbEstrutEstadio);
		final CheckBox cbEstrutEdificio = (CheckBox) findViewById(R.exibicao.cbEstrutEdificio);
		final CheckBox cbEstrutEdificioComercial = (CheckBox) findViewById(R.exibicao.cbEstrutEdificioComerial);
		final CheckBox cbEstrutShopping = (CheckBox) findViewById(R.exibicao.cbEstrutShopping);
		final CheckBox cbEstrutIndustria = (CheckBox) findViewById(R.exibicao.cbEstrutIndustria);
		final CheckBox cbEstrutCasa = (CheckBox) findViewById(R.exibicao.cbEstrutCasa);
		final CheckBox cbEstrutOutro = (CheckBox) findViewById(R.exibicao.cbEstrutOutro);
		mapEstrutCB = new HashMap<CheckBox, String>();
		mapEstrutCB.put(cbEstrutHospital, "Hospital");
		mapEstrutCB.put(cbEstrutEstadio, "Estádio");
		mapEstrutCB.put(cbEstrutEdificio, "Edifício");
		mapEstrutCB.put(cbEstrutEdificioComercial, "Edifício Comercial");
		mapEstrutCB.put(cbEstrutShopping, "Shopping Center");
		mapEstrutCB.put(cbEstrutIndustria, "Indústria");
		mapEstrutCB.put(cbEstrutCasa, "Casa");
		mapEstrutCB.put(cbEstrutOutro, "...");
		final Set<CheckBox> checkboxesEstrut = mapEstrutCB.keySet();
		
		final CheckBox cbEquipeBombeiros = (CheckBox) findViewById(R.exibicao.cbEquipeBombeiros);
		final CheckBox cbEquipeVoluntarios = (CheckBox) findViewById(R.exibicao.cbEquipeVoluntarios);
		final CheckBox cbEquipePoliciaMilitar = (CheckBox) findViewById(R.exibicao.cbEquipePoliciaMilitar);
		final CheckBox cbEquipePoliciaCivil = (CheckBox) findViewById(R.exibicao.cbEquipePoliciaCivil);
		final CheckBox cbEquipeDefCivMun = (CheckBox) findViewById(R.exibicao.cbEquipeDefCivMun);
		final CheckBox cbEquipeDefCivEstad = (CheckBox) findViewById(R.exibicao.cbEquipeDefCivEstad);
		final CheckBox cbEquipeComlurb = (CheckBox) findViewById(R.exibicao.cbEquipeComlurb);
		final CheckBox cbEquipeCetRio = (CheckBox) findViewById(R.exibicao.cbEquipeCetRio);
		final CheckBox cbEquipeCEG = (CheckBox) findViewById(R.exibicao.cbEquipeCEG);
		final CheckBox cbEquipeLight = (CheckBox) findViewById(R.exibicao.cbEquipeLight);
		final CheckBox cbEquipeCedae = (CheckBox) findViewById(R.exibicao.cbEquipeCedae);
		final CheckBox cbEquipeGeoRio = (CheckBox) findViewById(R.exibicao.cbEquipeGeoRio);
		final CheckBox cbEquipeIML = (CheckBox) findViewById(R.exibicao.cbEquipeIML);
		final CheckBox cbEquipeCNEN = (CheckBox) findViewById(R.exibicao.cbEquipeCNEN);
		final CheckBox cbEquipeINEA = (CheckBox) findViewById(R.exibicao.cbEquipeINEA);
		final CheckBox cbEquipeIBAMA = (CheckBox) findViewById(R.exibicao.cbEquipeIBAMA);
		final CheckBox cbEquipeExercito = (CheckBox) findViewById(R.exibicao.cbEquipeExercito);
		final CheckBox cbEquipeMarinha = (CheckBox) findViewById(R.exibicao.cbEquipeMarinha);
		final CheckBox cbEquipeAeronautica = (CheckBox) findViewById(R.exibicao.cbEquipeAeronautica);
		mapEquipeCB = new HashMap<CheckBox, String>();
		mapEquipeCB.put(cbEquipeBombeiros, "Bombeiros");
		mapEquipeCB.put(cbEquipeVoluntarios, "Voluntários");
		mapEquipeCB.put(cbEquipePoliciaCivil, "Polícia Civil");
		mapEquipeCB.put(cbEquipePoliciaMilitar, "Polícia Militar");
		mapEquipeCB.put(cbEquipeDefCivEstad, "Def. Civil Estad.");
		mapEquipeCB.put(cbEquipeDefCivMun, "Def. Civil Mun.");
		mapEquipeCB.put(cbEquipeComlurb, "Comlurb");
		mapEquipeCB.put(cbEquipeCetRio, "Cet-Rio");
		mapEquipeCB.put(cbEquipeCEG, "CEG");
		mapEquipeCB.put(cbEquipeLight, "Light");
		mapEquipeCB.put(cbEquipeCedae, "CEDAE");
		mapEquipeCB.put(cbEquipeGeoRio, "Geo-Rio");
		mapEquipeCB.put(cbEquipeIML, "IML");
		mapEquipeCB.put(cbEquipeCNEN, "CNEN");
		mapEquipeCB.put(cbEquipeINEA, "INEA");
		mapEquipeCB.put(cbEquipeIBAMA, "IBAMA");
		mapEquipeCB.put(cbEquipeExercito, "Exército");
		mapEquipeCB.put(cbEquipeMarinha, "Marinha");
		mapEquipeCB.put(cbEquipeAeronautica, "Aeronáutica");
		final Set<CheckBox> checkboxesEquipe = mapEquipeCB.keySet();
		
		final CheckBox cbPerigoGases = (CheckBox) findViewById(R.exibicao.cbPerigoGases);
		final CheckBox cbPerigoLiquidos = (CheckBox) findViewById(R.exibicao.cbPerigoLiquidos);
		final CheckBox cbPerigoSubst = (CheckBox) findViewById(R.exibicao.cbPerigoSubst);
		final CheckBox cbPerigoRadioativo = (CheckBox) findViewById(R.exibicao.cbPerigoRadioativa);
		final CheckBox cbPerigoDeslizamento = (CheckBox) findViewById(R.exibicao.cbPerigoDeslizamento);
		final CheckBox cbPerigoColapsoEdificio = (CheckBox) findViewById(R.exibicao.cbPerigoColapsoEdificio);
		final CheckBox cbPerigoRatos = (CheckBox) findViewById(R.exibicao.cbPerigoRatos);
		final CheckBox cbPerigoOutro = (CheckBox) findViewById(R.exibicao.cbPerigoOutro);
		mapPerigoCB = new HashMap<CheckBox, String>();
		mapPerigoCB.put(cbPerigoGases, "Gases Tóxicos");
		mapPerigoCB.put(cbPerigoLiquidos, "Líquidos Tóxicos");
		mapPerigoCB.put(cbPerigoSubst, "Substâncias Tóxicas");
		mapPerigoCB.put(cbPerigoRadioativo, "Exposição Radioativa");
		mapPerigoCB.put(cbPerigoDeslizamento, "Deslizamento de Terra");
		mapPerigoCB.put(cbPerigoColapsoEdificio, "Colapso de Edifício");
		mapPerigoCB.put(cbPerigoRatos, "Ratos");
		mapPerigoCB.put(cbPerigoOutro, "...");
		final Set<CheckBox> checkboxesPerigo = mapPerigoCB.keySet();
		
		tlExibicaoVeiculoCB.setVisibility(View.GONE);
		tlExibicaoEstruturaCB.setVisibility(View.GONE);
		tlExibicaoEquipeCB.setVisibility(View.GONE);
		tlExibicaoPerigoCB.setVisibility(View.GONE);
		
		MainActivity.veiculoOverlays.hideBalloon();
		MainActivity.estruturaOverlays.hideBalloon();
		MainActivity.equipamentoOverlays.hideBalloon();
		MainActivity.equipeOverlays.hideBalloon();
		MainActivity.perigoOverlays.hideBalloon();
		MainActivity.vitimaOverlays.hideBalloon();

		showGroup(trExibicaoVeiculo, tlExibicaoVeiculoCB);
		showGroup(trExibicaoEstrutura, tlExibicaoEstruturaCB);
		showGroup(trExibicaoEquipe, tlExibicaoEquipeCB);
		showGroup(trExibicaoPerigo, tlExibicaoPerigoCB);
		trExibicaoEquipamento.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cbEquipamento.toggle();
			}
		});
		trExibicaoVitima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cbVitima.toggle();
			}
		});
		
		cbListener(cbVeiculo, marcaVei, checkboxesVei);
		cbListener(cbEstrutura, marcaEstrut, checkboxesEstrut);
		cbListener(cbEquipe, marcaEquipe, checkboxesEquipe);
		cbListener(cbPerigo, marcaPerigo, checkboxesPerigo);
		
		///////////////////////////////////////////////////////////////////////		
		/**
		 * atualizar os checkboxes com os overlays que ja estao no mapa
		 * **/
		
		ArrayList<Overlay> veiculosNoMapa = (ArrayList<Overlay>) MainActivity.veiculoOverlays.getL_overlays().clone();
		ArrayList<Overlay> estruturasNoMapa = (ArrayList<Overlay>) MainActivity.estruturaOverlays.getL_overlays().clone();
		ArrayList<Overlay> equipesNoMapa = (ArrayList<Overlay>) MainActivity.equipeOverlays.getL_overlays().clone();
		ArrayList<Overlay> perigosNoMapa = (ArrayList<Overlay>) MainActivity.perigoOverlays.getL_overlays().clone();
		
		////////////////////////////////////////////////////////
		for (Overlay overlay : veiculosNoMapa) {
			Veiculo v = (Veiculo) overlay;
			String t = v.getTipoDeVeiculo();
			
			getStates(t, mapVeicCB);
		}
		
		checkboxTriState(mapVeicCB, checkboxesVei, marcaVei, cbVeiculo);
		////////////////////////////////////////////////////////
		for (Overlay overlay : estruturasNoMapa) {
			Estrutura e = (Estrutura) overlay;
			String t = e.getTipoDeEstrutura();
			
			if(!t.equals("Casa") && !t.equals("Hospital")
								&& !t.equals("Edifício") && !t.equals("Edifício Comercial")
								&& !t.equals("Estádio") && !t.equals("Indústria")
								&& !t.equals("Shopping Center")){
				cbEstrutOutro.setChecked(true);
				continue;
			}
			
			getStates(t, mapEstrutCB);
		}
		
		checkboxTriState(mapEstrutCB, checkboxesEstrut, marcaEstrut, cbEstrutura);
		////////////////////////////////////////////////////////
		if(mapOverlays.contains(MainActivity.equipamentoOverlays) && MainActivity.equipamentoOverlays.size()>0) cbEquipamento.setChecked(true);
		////////////////////////////////////////////////////////		
		for (Overlay overlay : equipesNoMapa) {
			Equipe e = (Equipe) overlay;
			try{
				String t = e.getInst().getTipoDeInstituicao();
				
				getStates(t, mapEquipeCB);
			}catch (NullPointerException exc) {
				continue;
			}
		}
		
		checkboxTriState(mapEquipeCB, checkboxesEquipe, marcaEquipe, cbEquipe);
		////////////////////////////////////////////////////////		
		for (Overlay overlay : perigosNoMapa) {
			Perigo p = (Perigo) overlay;
			String t = p.getTipoDePerigo();
			
			if(!t.equals("Ratos") && !t.equals("Colapso de Edifício")
					&& !t.equals("Deslizamento de Terra") && !t.equals("Exposição Radioativa")
					&& !t.equals("Substâncias Tóxicas") && !t.equals("Líquidos Tóxicos")
					&& !t.equals("Gases Tóxicos")){
				cbPerigoOutro.setChecked(true);
				continue;
			}
			
			getStates(t, mapPerigoCB);
		}
		
		checkboxTriState(mapPerigoCB, checkboxesPerigo, marcaPerigo, cbPerigo);
		////////////////////////////////////////////////////////
		if(mapOverlays.contains(MainActivity.vitimaOverlays) && MainActivity.vitimaOverlays.size()>0) cbVitima.setChecked(true);
		///////////////////////////////////////////
		
		final ImageButton btSalvar = (ImageButton) findViewById(R.exibicao.btnExibicaoAplicar);
		
		btSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.veiculoOverlays.clear();
				MainActivity.estruturaOverlays.clear();
				MainActivity.equipeOverlays.clear();
				MainActivity.perigoOverlays.clear();
				
				for (Iterator<CheckBox> iterator = getChecked(mapVeicCB).iterator(); iterator.hasNext();) {
					CheckBox cb = (CheckBox) iterator.next();
					String tipo = mapVeicCB.get(cb);
					for (Veiculo veic : MainActivity.listaVeiculos) {
						if (veic.getTipoDeVeiculo().equals(tipo)){
							MainActivity.veiculoOverlays.addOverlay(veic.getOverlayItem(), veic);
						}
					}	
				}
				
				for (Iterator<CheckBox> iterator = getChecked(mapEstrutCB).iterator(); iterator.hasNext();) {
					CheckBox cb = (CheckBox) iterator.next();
					String tipo = mapEstrutCB.get(cb);
					for (Estrutura est : MainActivity.listaEstruturas) {
						if (est.getTipoDeEstrutura().equals(tipo)){
							MainActivity.estruturaOverlays.addOverlay(est.getOverlayItem(), est);
						}else if(tipo == "..."){
							int outro = 0;
							for (String s : Global.strEst) {
								if (!est.getTipoDeEstrutura().equals(s)){
									outro += 1;
								}
							}
							if (outro == Global.strEst.length){
								MainActivity.estruturaOverlays.addOverlay(est.getOverlayItem(), est);
							}
						}
					}	
				}
				
				for (Iterator<CheckBox> iterator = getChecked(mapEquipeCB).iterator(); iterator.hasNext();) {
					CheckBox cb = (CheckBox) iterator.next();
					String tipo = mapEquipeCB.get(cb);
					for (Equipe team : MainActivity.listaEquipes) {
						Instituicao in = team.getInst();
						if(in != null){
							if (in.getTipoDeInstituicao().equals(tipo)){
								MainActivity.equipeOverlays.addOverlay(team.getOverlayItem(), team);
							}
						}
					}
				}
				
				for (Iterator<CheckBox> iterator = getChecked(mapPerigoCB).iterator(); iterator.hasNext();) {
					CheckBox cb = (CheckBox) iterator.next();
					String tipo = mapPerigoCB.get(cb);
					for (Perigo per : MainActivity.listaPerigos) {
						if (per.getTipoDePerigo().equals(tipo)){
							MainActivity.perigoOverlays.addOverlay(per.getOverlayItem(), per);
						}else if(tipo == "..."){
							int outro = 0;
							for (String s : Global.strPer) {
								if (!per.getTipoDePerigo().equals(s)){
									outro += 1;
								}
							}
							if (outro == Global.strPer.length){
								MainActivity.perigoOverlays.addOverlay(per.getOverlayItem(), per);
							}
						}
					}	
				}
				
				setOverlays(cbVeiculo, MainActivity.veiculoOverlays, getChecked(mapVeicCB).size());
				setOverlays(cbEstrutura, MainActivity.estruturaOverlays, getChecked(mapEstrutCB).size());
				setOverlays(cbEquipamento, MainActivity.equipamentoOverlays, -1);
				setOverlays(cbEquipe, MainActivity.equipeOverlays, getChecked(mapEquipeCB).size());
				setOverlays(cbPerigo, MainActivity.perigoOverlays, getChecked(mapPerigoCB).size());
				setOverlays(cbVitima, MainActivity.vitimaOverlays, -1);

				finish();
				MainActivity.exibeToast(getApplicationContext(), "Alterações efetuadas com sucesso");
			}
		});
	}
	
	/**
	 * Esta função testa se é necessário adicionar ou remover ao mapa o overlay preterido. Isso é feito devido
	 * à duas condições: ou o checkbox do grupo foi checado, ou existe um numero > 0 de checkboxes checados dentro do grupo.
	 * 
	 * @param cb CheckBox que representa o grupo
	 * @param overlay Overlay que contém os itemizedoverlays
	 * @param checked Numero de checkboxes do grupo checados
	 */
	public void setOverlays(CheckBox cb, CustomItemizedOverlay overlay, int checked){
		if (cb.isChecked() || checked > 0){
			if(!mapOverlays.contains(overlay)) mapOverlays.add(overlay);
		}
		else mapOverlays.remove(overlay);
	}
	
	/**
	 * Função que controlará o funcionamento do checkbox com três estados, isto é, aquele sobre o qual ficará a imagem
	 * @param map Mapa que contém os checkboxes a serem referenciados
	 * @param total Set com todos os checkboxes checados do mapa
	 * @param iv ImageView que vai sobrepor o checkbox TriState
	 * @param cb Checkbox a ser sobreposto
	 */
	public void checkboxTriState(Map<CheckBox, String> map, Set<CheckBox> total, ImageView iv, CheckBox cb){
		int checados = getChecked(map).size(); 
		if(checados == total.size()){
			iv.setVisibility(View.GONE);
			cb.setChecked(true);
		}else if(checados > 0 && checados < total.size()){
			iv.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Esta função retorna um HashSet dos checkboxes checados no map.
	 * 
	 * @param map Mapa que contém o grupo de checkboxes a serem testados
	 * @return HashSet composto somente dos checkboxes checados
	 */
	public HashSet<CheckBox> getChecked(Map<CheckBox, String> map){
		Set<CheckBox> checkboxes = map.keySet();
		HashSet<CheckBox> checados = new HashSet<CheckBox>();
		for (Iterator<CheckBox> iterator = checkboxes.iterator(); iterator.hasNext();) {
			CheckBox checkBox = (CheckBox) iterator.next();
			if(checkBox.isChecked()){
				checados.add(checkBox);
			}
			
		}
		return checados;
	}
	
	/**
	 * Checka/Un-checka todos os checkboxes do Set
	 * @param set Set de checkboxes a terem seus estados alterados
	 * @param bool Check ou uncheck
	 */
	public void checkAll(Set<CheckBox> set, boolean bool){
		for (Iterator<CheckBox> iterator = set.iterator(); iterator.hasNext();) {
			CheckBox cb = (CheckBox) iterator.next();
			cb.setChecked(bool);
		}
	}
	/**
	 * Amplia ou contrai o grupo de tablerows
	 * @param cabeca TableRow cabeça, que será sempre exibido, e servirá como lançador do método quando clicado
	 * @param grupo O TableLayout que contém as TableRows para serem exibidas/escondidas
	 */
	public void showGroup(TableRow cabeca, final TableLayout grupo){
		cabeca.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (TableLayout tl : alTableLayoutCB) { //esconder os outros
					if (!tl.equals(grupo)) tl.setVisibility(View.GONE);
				}
				
				if(grupo.getVisibility() == View.VISIBLE){
					grupo.setVisibility(View.GONE);
				}else{
					grupo.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	/**
	 * Quando o checkbox estiver checado, checará todos os checkboxes do set. Quando uncheckado, idem.
	 * @param cb CheckBox 'mestre' do grupo
	 * @param iv ImageView que sobrepõe o 'mestre', será removido se este for clicado
	 * @param set Set de checkboxes filhos do grupo
	 */
	public void cbListener(CheckBox cb, final ImageView iv, final Set<CheckBox> set){
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				iv.setVisibility(View.GONE);
				if(isChecked && set != null) checkAll(set, true);
				else checkAll(set, false);				
			}
		});
	}
	/**
	 * Checa os checkboxes de acordo com o estado atual do mapa, quais tipos de pdi estao visiveis
	 * @param comparar String do tipo a ser comparada
	 * @param map Mapa de CheckBoxes a serem alterados
	 */
	public void getStates(String comparar, Map<CheckBox, String> map){
		for (Map.Entry<CheckBox, String> entry : map.entrySet()) {
		    CheckBox key = entry.getKey();
		    String s = entry.getValue();
		    
		    if (comparar.equals(s) && key.isChecked() == false){
				key.setChecked(true);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		MainActivity.exibeToast(getApplicationContext(), "Alterações descartadas");
		super.onBackPressed();
	}
}