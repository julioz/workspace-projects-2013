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
import br.com.site.model.PDI;
import br.com.site.model.Veiculo;
import br.com.site.view.TablerowPDIRelacionado;

import com.google.android.maps.GeoPoint;

public class NovoEquipamento extends NovoPDI{

	private List<Veiculo> listaVeiculos;
	private List<Equipe> listaEquipes;
	
	private TableLayout tlEquipDelete;
	
	private EditText etEquipIdentificacao, etEquipTipoDeEquipamento, etEquipDisponiveis, etEquipQuantidade;
	private Spinner spEquipTeam, spEquipVeiculo;
	private ImageButton spEquipVeiMais, spEquipTeamMais;
	private ImageButton salvar;
	private TextView tvTit;
	
	private ArrayList<TablerowPDIRelacionado> ltrVeic = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrTeam = new ArrayList<TablerowPDIRelacionado>();
	
	private ArrayAdapter<CharSequence> adtEquipTeam, adtEquipVeiculo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setContentView(R.layout.novo_equipamento);
		
		carregaViews();
		
		tlEquipDelete.setVisibility(View.GONE);
		
		setPDIArrayAdapter(adtEquipTeam, spEquipTeam, listaEquipes);
		setPDIArrayAdapter(adtEquipVeiculo, spEquipVeiculo, listaVeiculos);
		
		setMultiTableRow(spEquipVeiMais, "Veículo:", listaVeiculos, R.novo_equipamento.tlVeiculo, ltrVeic);
		setMultiTableRow(spEquipTeamMais, "Equipe:", listaEquipes, R.novo_equipamento.tlTeam, ltrTeam);
        
		if(!Global.editarPDI) novoPDI();
        else editarPDI();
	}
	
	private void carregaViews() {
		listaVeiculos = br.com.site.MainActivity.listaVeiculos;
		listaEquipes = br.com.site.MainActivity.listaEquipes;
		
		tlEquipDelete = (TableLayout) findViewById(R.novo_equipamento.tlEquipDelete);
		
		etEquipIdentificacao = (EditText) findViewById(R.novo_equipamento.etEquipIdentificacao);
		etEquipTipoDeEquipamento = (EditText) findViewById(R.novo_equipamento.etEquipTipoDeEquipamento);
		etEquipDisponiveis = (EditText) findViewById(R.novo_equipamento.etEquipDisponiveis);
		etEquipQuantidade = (EditText) findViewById(R.novo_equipamento.etEquipQuantidade);
    	
		spEquipVeiculo = (Spinner) findViewById(R.novo_equipamento.spEquipVeiculo);
		ltrVeic.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_equipamento.trInicialVeiculo), "Veículo:", spEquipVeiculo, spEquipVeiMais));
    	spEquipTeam = (Spinner) findViewById(R.novo_equipamento.spEquipTeam);
    	ltrTeam.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_equipamento.trInicialTeam), "Equipe:", spEquipTeam, spEquipTeamMais));
    	
    	spEquipVeiMais = (ImageButton) findViewById(R.novo_equipamento.spEquipVeiculoMais);
		spEquipTeamMais = (ImageButton) findViewById(R.novo_equipamento.spEquipTeamMais);
    	
    	adtEquipTeam = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
    	adtEquipTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	adtEquipVeiculo = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtEquipVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        salvar = (ImageButton) findViewById(R.novo_equipamento.equipSalvar);
        
        tvTit = (TextView) findViewById(R.novo_equipamento.tvEquipTit);
	}
	
	private void novoPDI() {
		salvar.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(!isEditTextEmpty(etEquipIdentificacao) && !isEditTextBiggerValue(etEquipQuantidade, "quantidade", etEquipDisponiveis, "disponíveis")){
					salvar.setImageResource(R.drawable.bt_aplicar);
					GeoPoint temp = new GeoPoint(0, 0);
					String identif = etEquipIdentificacao.getText().toString();
					String tipoEquip = etEquipTipoDeEquipamento.getText().toString();
					
					int qtd = 0;
					try{
						qtd = Integer.parseInt(etEquipQuantidade.getText().toString());
					}catch(NumberFormatException nfe){
						qtd = -1;
					}
					
					int disp = 0;
					try{
						disp = Integer.parseInt(etEquipDisponiveis.getText().toString());
					}catch(NumberFormatException nfe){
						disp = -1;
					}
					
					Set<Veiculo> setVeiculo = new TreeSet<Veiculo>();
					for (TablerowPDIRelacionado tr : ltrVeic) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Veiculo veiculo : listaVeiculos) {
								if (s.equals(veiculo.toString())) setVeiculo.add(veiculo);
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
					
					Equipamento eqp = new Equipamento(Global.getNumPDI(), temp, identif, setVeiculo, setEquipe, identif, tipoEquip, qtd, disp);
					
					if(setVeiculo != null && !setVeiculo.isEmpty()){
						for (Iterator<Veiculo> it = setVeiculo.iterator(); it.hasNext();) {
							Veiculo veiculo = (Veiculo) it.next();
							veiculo.getEquipamento().add(eqp);
						}
					}
					
					if(setEquipe != null && !setEquipe.isEmpty()){
						for (Iterator<Equipe> it = setEquipe.iterator(); it.hasNext();) {
							Equipe equipe = (Equipe) it.next();
							equipe.getEquipamento().add(eqp);
						}
					}
					
					br.com.site.MainActivity.objetoPassado = eqp;
					Global.novoPdi = true;
					finish();
					br.com.site.MainActivity.exibeToast(getApplicationContext(), "Toque na tela para posicionar o equipamento.");
				}
				
			}
		});		
	}
	
	private void editarPDI() {
		salvar.setImageResource(R.drawable.bt_edit);
    	tvTit.setText("Editar Equipamento");
    	tlEquipDelete.setVisibility(View.VISIBLE);
    	
    	final TextView tvDel = (TextView) findViewById(R.novo_equipamento.tvDelEquip);
    	final ImageView ivDel = (ImageView) findViewById(R.novo_equipamento.ivDelEquip);
    	
    	final Equipamento e = (Equipamento) Global.pdiEdicao;
    	
    	tvDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoEquipamento.this, e, e.getXML());
			}
		});
    	
    	ivDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoEquipamento.this, e, e.getXML());
			}
		});

    	etEquipIdentificacao.setText(e.getIdentificacao());
    	etEquipTipoDeEquipamento.setText(e.getTipoDeEquipamento());
    	etEquipQuantidade.setText(String.valueOf(e.getQuantidade()));
    	etEquipDisponiveis.setText(String.valueOf(e.getDisponiveis()));
    	
    	try{
    		setAdapterPositionFromSet(spEquipVeiculo, e.getVeiculo());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spEquipVeiculo, "Desconhecido");
		}try{
    		setAdapterPositionFromSet(spEquipTeam, e.getEquipe());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spEquipTeam, "Desconhecido");
		}
    	
    	salvar.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				if(!isEditTextEmpty(etEquipIdentificacao) && !isEditTextBiggerValue(etEquipQuantidade, "quantidade", etEquipDisponiveis, "disponíveis")){
					MainActivity.listaEquipamentos.remove(e);
					
					e.setIdentificacao(setStringEditText(etEquipIdentificacao));
					e.setTipoDeEquipamento(setStringEditText(etEquipTipoDeEquipamento));
					
					int qtd = -1;
					try{
						qtd = Integer.parseInt(etEquipQuantidade.getText().toString());
					}catch(NumberFormatException exc){}
					
					e.setQuantidade(qtd);
					
					try{
						int disp = Integer.parseInt(etEquipDisponiveis.getText().toString());
						e.setDisponiveis(disp);
					}catch(NumberFormatException exc){
						e.setDisponiveis(-1);
					}
					
					Set<Veiculo> setVeiculo = new TreeSet<Veiculo>();
					for (TablerowPDIRelacionado tr : ltrVeic) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Veiculo veiculo : listaVeiculos) {
								if (s.equals(veiculo.toString())) setVeiculo.add(veiculo);
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
					
					for (Veiculo vei : e.getVeiculo()) {
						if(setVeiculo.contains(vei)) continue;
						else vei.getEquipamento().remove(e);
					}
					
					for (Equipe eqp : e.getEquipe()) {
						if(setEquipe.contains(eqp)) continue;
						else eqp.getEquipamento().remove(e);
					}
					
					if(setVeiculo != null) e.setVeiculo(setVeiculo);
					if(setEquipe != null) e.setEquipe(setEquipe);
					e.atualizaTextoBalao();
					
					if(setVeiculo != null && !setVeiculo.isEmpty()){
						for (Iterator<Veiculo> it = setVeiculo.iterator(); it.hasNext();) {
							Veiculo veiculo = (Veiculo) it.next();
							veiculo.getEquipamento().add(e);
						}
					}
					
					if(setEquipe != null && !setEquipe.isEmpty()){
						for (Iterator<Equipe> it = setEquipe.iterator(); it.hasNext();) {
							Equipe equipe = (Equipe) it.next();
							equipe.getEquipamento().add(e);
						}
					}
					
					EditarPDI.atualizarPDI(e);
					MainActivity.equipamentoOverlays.hideBalloon();
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
			if(primeiro instanceof Veiculo){
				spEquipVeiMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrVeic.get(ltrVeic.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrVeic.size(); i++) ltrVeic.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Equipe){
				spEquipTeamMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrTeam.get(ltrTeam.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrTeam.size(); i++) ltrTeam.get(i-1).getSpinner().setSelection(i);
			}
		}
	}
}