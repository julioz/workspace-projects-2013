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
import br.com.site.model.Instituicao;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Unidade;
import br.com.site.model.Vitima;
import br.com.site.view.TablerowPDIRelacionado;

import com.google.android.maps.GeoPoint;

public class NovoEquipe extends NovoPDI{
	
	private List<Estrutura> listaEstruturas;
	private List<Equipamento> listaEquipamentos;
	private List<Perigo> listaPerigos;
	private List<Instituicao> listaInstituicoes;
	private List<Unidade> listaUnidades;
	
	private TableLayout tlTeamDelete;
	private ImageButton spTeamEstruturaMais, spTeamEquipamentoMais, spTeamPerigoMais;
	
	private ArrayList<TablerowPDIRelacionado> ltrEstr = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrEquip = new ArrayList<TablerowPDIRelacionado>();
	private ArrayList<TablerowPDIRelacionado> ltrPer = new ArrayList<TablerowPDIRelacionado>();
	
	private EditText etTeamIdentificacao, etTeamChefe, etTeamQuantidadeMembros, etTeamQuantidadeMembrosFeridos, etTeamTarefaAtual;
	private Spinner spTeamFuncao, spTeamInst, spTeamUnid, spTeamEstrut, spTeamEquipamento, spTeamPerigo;
	private ImageButton salvar;
	private TextView tvTit;
	
	private ArrayAdapter<CharSequence> adtTeamFuncao, adtTeamInst, adtTeamUnid;
	private ArrayAdapter<CharSequence> adtTeamEstrut, adtTeamEquipamento, adtTeamPerigo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setContentView(R.layout.novo_equipe);
		
		carregaViews();
		
		tlTeamDelete.setVisibility(View.GONE);
		spTeamFuncao.setAdapter(adtTeamFuncao);
		
		setPDIArrayAdapter(adtTeamInst, spTeamInst, listaInstituicoes);
		setPDIArrayAdapter(adtTeamUnid, spTeamUnid, listaUnidades);
		setPDIArrayAdapter(adtTeamEstrut, spTeamEstrut, listaEstruturas);
		setPDIArrayAdapter(adtTeamEquipamento, spTeamEquipamento, listaEquipamentos);
		setPDIArrayAdapter(adtTeamPerigo, spTeamPerigo, listaPerigos);
		
		setMultiTableRow(spTeamEstruturaMais, "Estrutura:", listaEstruturas, R.novo_equipe.tlEstrutura, ltrEstr);
		setMultiTableRow(spTeamEquipamentoMais, "Equipamento:", listaEquipamentos, R.novo_equipe.tlEquipamento, ltrEquip);
		setMultiTableRow(spTeamPerigoMais, "Perigo:", listaPerigos, R.novo_equipe.tlPerigo, ltrPer);
    	
        if(!Global.editarPDI) novoPDI();
        else editarPDI();
    
	}
	
	private void carregaViews() {
		listaEstruturas = br.com.site.MainActivity.listaEstruturas;
		listaEquipamentos = br.com.site.MainActivity.listaEquipamentos;
		listaPerigos = br.com.site.MainActivity.listaPerigos;
		listaInstituicoes = br.com.site.MainActivity.listaInstituicoes;
		listaUnidades = br.com.site.MainActivity.listaUnidades;
		
		tlTeamDelete = (TableLayout) findViewById(R.novo_equipe.tlTeamDelete);
		
		etTeamIdentificacao = (EditText) findViewById(R.novo_equipe.etTeamIdentificacao);
		etTeamChefe = (EditText) findViewById(R.novo_equipe.etTeamChefe);
		etTeamQuantidadeMembros = (EditText) findViewById(R.novo_equipe.etTeamQuantidadeMembros);
		etTeamQuantidadeMembrosFeridos = (EditText) findViewById(R.novo_equipe.etTeamQtdMembrosFeridos);
		etTeamTarefaAtual = (EditText) findViewById(R.novo_equipe.etTeamTarefaAtual);
    	
		spTeamFuncao = (Spinner) findViewById(R.novo_equipe.spTeamFuncao);
		spTeamInst = (Spinner) findViewById(R.novo_equipe.spTeamInstituicao);
		spTeamUnid = (Spinner) findViewById(R.novo_equipe.spTeamUnidade);
		spTeamEstrut = (Spinner) findViewById(R.novo_equipe.spTeamEstrutura);
		ltrEstr.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_equipe.trInicialEstrutura), "Estrutura:", spTeamEstrut, spTeamEstruturaMais));
		spTeamEquipamento = (Spinner) findViewById(R.novo_equipe.spTeamEquipamento);
		ltrEquip.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_equipe.trInicialEquipamento), "Equipamento:", spTeamEquipamento, spTeamEquipamentoMais));
		spTeamPerigo = (Spinner) findViewById(R.novo_equipe.spTeamPerigo);
		ltrPer.add(new TablerowPDIRelacionado(this, (TableRow) findViewById(R.novo_equipe.trInicialPerigo), "Perigo:", spTeamPerigo, spTeamPerigoMais));
    	
		spTeamEstruturaMais = (ImageButton) findViewById(R.novo_equipe.spTeamEstruturaMais);
		spTeamEquipamentoMais = (ImageButton) findViewById(R.novo_equipe.spTeamEquipamentoMais);
		spTeamPerigoMais = (ImageButton) findViewById(R.novo_equipe.spTeamPerigoMais);
		
    	salvar = (ImageButton) findViewById(R.novo_equipe.teamSalvar);
    	tvTit = (TextView) findViewById(R.novo_equipe.tvTeamTit);
    	
    	adtTeamFuncao = ArrayAdapter.createFromResource(this, R.array.equipeFuncao_array, R.layout.textviewmodelo);
    	adtTeamFuncao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	adtTeamInst = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
    	adtTeamInst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adtTeamUnid = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtTeamUnid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adtTeamEstrut = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtTeamEstrut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adtTeamEquipamento = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtTeamEquipamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adtTeamPerigo = new ArrayAdapter<CharSequence>(this, R.layout.textviewmodelo);
        adtTeamPerigo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
	}
	
	private void novoPDI() {
		salvar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(!isEditTextEmpty(etTeamIdentificacao) && !isEditTextBiggerValue(etTeamQuantidadeMembros, "quantidade de membros", etTeamQuantidadeMembrosFeridos, "quantidade de membros feridos")){
					salvar.setImageResource(R.drawable.bt_aplicar);
					GeoPoint temp = new GeoPoint(0, 0);
					String identif = etTeamIdentificacao.getText().toString();
					String chefe = etTeamChefe.getText().toString();				
					
					int qtdMembros = 0;
					
					try{
						qtdMembros = Integer.parseInt(etTeamQuantidadeMembros.getText().toString());
					}catch(NumberFormatException nfe){
						qtdMembros = -1;
					}
					
					int qtdMembrosFeridos = 0;
					
					try{
						qtdMembrosFeridos = Integer.parseInt(etTeamQuantidadeMembrosFeridos.getText().toString());
					}catch(NumberFormatException nfe){
						qtdMembrosFeridos = -1;
					}
					
					int tipoDeFuncao = 7;
					if (spTeamFuncao.getSelectedItem().equals("Desconhecido")){
						tipoDeFuncao = 0;
					}else if (spTeamFuncao.getSelectedItem().equals("Resgate")){
						tipoDeFuncao = 1;
					}else if (spTeamFuncao.getSelectedItem().equals("Administração")){
						tipoDeFuncao = 2;
					}else if (spTeamFuncao.getSelectedItem().equals("Médica")){
						tipoDeFuncao = 3;
					}else if (spTeamFuncao.getSelectedItem().equals("Segurança")){
						tipoDeFuncao = 4;
					}else if (spTeamFuncao.getSelectedItem().equals("Voluntários")){
						tipoDeFuncao = 5;
					}else if (spTeamFuncao.getSelectedItem().equals("Serviços Públicos")){
						tipoDeFuncao = 6;
					}else{
						tipoDeFuncao = 7;
					}
					
					String tarefaAt = etTeamTarefaAtual.getText().toString();
					if (tarefaAt.equals("")){
						tarefaAt = "Desconhecido";
					}
					
					Instituicao teamInst = null;
					for (Instituicao i : listaInstituicoes) {
						if (spTeamInst.getSelectedItem().equals(i.toString())){
							teamInst = i;
						}else{
							continue;
						}
					}
					
					Unidade teamUnid = null;
					for (Unidade u : listaUnidades) {
						if (spTeamUnid.getSelectedItem().equals(u.toString())){
							teamUnid = u;
						}else{
							continue;
						}
					}
					
					Set<Estrutura> setEstrutura = new TreeSet<Estrutura>();
					for (TablerowPDIRelacionado tr : ltrEstr) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Estrutura est : listaEstruturas) {
								if (s.equals(est.toString())) setEstrutura.add(est);
								else continue;
							}
						}
					}
					
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
					
					Equipe time = new Equipe(Global.getNumPDI(), temp,
							identif, setEstrutura, setEquipamento, setPerigo,
							identif, chefe,
							teamInst, teamUnid,
							tipoDeFuncao, qtdMembros, qtdMembrosFeridos, tarefaAt);
					
					if(setEstrutura != null && !setEstrutura.isEmpty()){
						for (Iterator<Estrutura> it = setEstrutura.iterator(); it.hasNext();) {
							Estrutura est = (Estrutura) it.next();
							est.getEquipe().add(time);
						}
					}
					if(setEquipamento != null && !setEquipamento.isEmpty()){
						for (Iterator<Equipamento> it = setEquipamento.iterator(); it.hasNext();) {
							Equipamento eqp = (Equipamento) it.next();
							eqp.getEquipe().add(time);
						}
					}
					if(setPerigo != null && !setPerigo.isEmpty()){
						for (Iterator<Perigo> it = setPerigo.iterator(); it.hasNext();) {
							Perigo per = (Perigo) it.next();
							per.getEquipe().add(time);
						}
					}
					
					br.com.site.MainActivity.objetoPassado = time;
					Global.novoPdi = true;
					finish();
					br.com.site.MainActivity.exibeToast(getApplicationContext(), "Toque na tela para posicionar a equipe.");
				}
			}
		});		
	}

	private void editarPDI() {
		salvar.setImageResource(R.drawable.bt_edit);
    	tvTit.setText("Editar Equipe");
    	tlTeamDelete.setVisibility(View.VISIBLE);
    	
    	final TextView tvDel = (TextView) findViewById(R.novo_equipe.tvDelTeam);
    	final ImageView ivDel = (ImageView) findViewById(R.novo_equipe.ivDelTeam);
    	
    	final Equipe e = (Equipe) Global.pdiEdicao;
    	
    	tvDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoEquipe.this, e, e.getXML());
			}
		});
    	
    	ivDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditarPDI.promptDeletaObj(NovoEquipe.this, e, e.getXML());
			}
		});

    	etTeamIdentificacao.setText(e.getIdentificacao());
    	etTeamChefe.setText(e.getChefe());
    	setNumEditText(e.getQtdMembros(), etTeamQuantidadeMembros);
    	setNumEditText(e.getQtdMembrosFeridos(), etTeamQuantidadeMembrosFeridos);
    	etTeamTarefaAtual.setText(e.getTarefaAtual());
    	
    	setAdapterPositionFromString(spTeamFuncao, e.getTipoDeFuncao());
    	
    	try{
    		setAdapterPositionFromString(spTeamInst, e.getInst().toString());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spTeamInst, "Desconhecido");
		}try{
    		setAdapterPositionFromString(spTeamUnid, e.getUnid().toString());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spTeamUnid, "Desconhecido");
		}try{
    		setAdapterPositionFromSet(spTeamEstrut, e.getEstrutura());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spTeamEstrut, "Desconhecido");
		}try{
    		setAdapterPositionFromSet(spTeamEquipamento, e.getEquipamento());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spTeamEquipamento, "Desconhecido");
		}try{
    		setAdapterPositionFromSet(spTeamPerigo, e.getPerigo());
    	}catch (NullPointerException ex) {
    		setAdapterPositionFromString(spTeamPerigo, "Desconhecido");
		}
    	
    	salvar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View view) {
				if(!isEditTextEmpty(etTeamIdentificacao) && !isEditTextBiggerValue(etTeamQuantidadeMembros, "quantidade de membros", etTeamQuantidadeMembrosFeridos, "quantidade de membros feridos")){
					MainActivity.listaEquipes.remove(e);
					
					Vitima vitima = null;
					for (Vitima vit : MainActivity.listaVitimas) {
						if(vit.getEquipe().equals(e)) vitima = vit;
					}
					
					e.setIdentificacao(setStringEditText(etTeamIdentificacao));
					e.setChefe(setStringEditText(etTeamChefe));
					
					
					int qtdMembros = 0;
					
					try{
						qtdMembros = Integer.parseInt(etTeamQuantidadeMembros.getText().toString());
					}catch(NumberFormatException nfe){
						qtdMembros = -1;
					}
					e.setQtdMembros(qtdMembros);
					
					int qtdMembrosFeridos = 0;
					
					try{
						qtdMembrosFeridos = Integer.parseInt(etTeamQuantidadeMembrosFeridos.getText().toString());
					}catch(NumberFormatException nfe){
						qtdMembrosFeridos = -1;
					}
					e.setQtdMembrosFeridos(qtdMembrosFeridos);
					
					int tipoDeFuncao = 7;
					if (spTeamFuncao.getSelectedItem().equals("Desconhecido")){
						tipoDeFuncao = 0;
					}else if (spTeamFuncao.getSelectedItem().equals("Resgate")){
						tipoDeFuncao = 1;
					}else if (spTeamFuncao.getSelectedItem().equals("Administração")){
						tipoDeFuncao = 2;
					}else if (spTeamFuncao.getSelectedItem().equals("Médica")){
						tipoDeFuncao = 3;
					}else if (spTeamFuncao.getSelectedItem().equals("Segurança")){
						tipoDeFuncao = 4;
					}else if (spTeamFuncao.getSelectedItem().equals("Voluntários")){
						tipoDeFuncao = 5;
					}else if (spTeamFuncao.getSelectedItem().equals("Serviços Públicos")){
						tipoDeFuncao = 6;
					}else{
						tipoDeFuncao = 7;
					}
					e.setTipoDeFuncao(tipoDeFuncao);
					
					
					String tarefaAt = etTeamTarefaAtual.getText().toString();
					if (tarefaAt.equals("")){
						tarefaAt = "Desconhecido";
					}
					e.setTarefaAtual(tarefaAt);
					
					Instituicao teamInst = null;
					for (Instituicao i : listaInstituicoes) {
						if (spTeamInst.getSelectedItem().equals(i.toString())) teamInst = i;
						else continue;
					}
					
					Unidade teamUnid = null;
					for (Unidade u : listaUnidades) {
						if (spTeamUnid.getSelectedItem().equals(u.toString())){
							teamUnid = u;
						}else{
							continue;
						}
					}
					
					Set<Estrutura> setEstrutura = new TreeSet<Estrutura>();
					for (TablerowPDIRelacionado tr : ltrEstr) {
						String s = tr.getSpinner().getSelectedItem().toString();
						if(s.equals("Desconhecido")) continue;
						else{
							for (Estrutura est : listaEstruturas) {
								if (s.equals(est.toString())) setEstrutura.add(est);
								else continue;
							}
						}
					}
					
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
					
					if(teamUnid != null){
						e.setUnid(teamUnid);
					}
					if(teamInst != null){
						e.setInst(teamInst);
					}
					
					for (Estrutura est : e.getEstrutura()) {
						if(setEstrutura.contains(est)) continue;
						else est.getEquipe().remove(e);
					}
					
					for (Equipamento eqp : e.getEquipamento()) {
						if(setEquipamento.contains(eqp)) continue;
						else eqp.getEquipe().remove(e);
					}
					
					for (Perigo per : e.getPerigo()) {
						if(setPerigo.contains(per)) continue;
						else per.getEquipe().remove(e);
					}
					
					e.setEstrutura(setEstrutura);
					e.setEquipamento(setEquipamento);
					e.setPerigo(setPerigo);
					
					if(vitima != null){
						vitima.getEquipe().add(e);
					}
					
					if(setEstrutura != null && !setEstrutura.isEmpty()){
						for (Iterator<Estrutura> it = setEstrutura.iterator(); it.hasNext();) {
							Estrutura est = (Estrutura) it.next();
							est.getEquipe().add(e);
						}
					}
					if(setEquipamento != null && !setEquipamento.isEmpty()){
						for (Iterator<Equipamento> it = setEquipamento.iterator(); it.hasNext();) {
							Equipamento eqp = (Equipamento) it.next();
							eqp.getEquipe().add(e);
						}
					}
					if(setPerigo != null && !setPerigo.isEmpty()){
						for (Iterator<Perigo> it = setPerigo.iterator(); it.hasNext();) {
							Perigo per = (Perigo) it.next();
							per.getEquipe().add(e);
						}
					}
					
					EditarPDI.atualizarPDI(e);
					MainActivity.equipeOverlays.hideBalloon();
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
			if(primeiro instanceof Estrutura){
				spTeamEstruturaMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrEstr.get(ltrEstr.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrEstr.size(); i++) ltrEstr.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Equipamento){
				spTeamEquipamentoMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrEquip.get(ltrEquip.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrEquip.size(); i++) ltrEquip.get(i-1).getSpinner().setSelection(i);
			}else if(primeiro instanceof Perigo){
				spTeamPerigoMais.performClick();
				for (int i = 0; i < set.size()-1; i++) ltrPer.get(ltrPer.size()-1).getImagebutton().performClick();
				for (int i = 1; i < ltrPer.size(); i++) ltrPer.get(i-1).getSpinner().setSelection(i);
			}			
		}
	}
}