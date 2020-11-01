package br.com.site.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import br.com.site.R;

import com.google.android.maps.GeoPoint;

public class Veiculo extends PDI{

	private int icone = R.drawable.car_icon;
	private int tipo = 1;
	private Set<Equipamento> equipamento;
	private Set<Equipe> equipe;
	private String identificacao, tipoDeVeiculo, subtipo;
	private Instituicao inst;
	
	
	public Veiculo(int id, String latitude, String longitude, String titulo,
			Set<Equipamento> equipamento, Set<Equipe> equipe,
			String identificacao, String tipoDeVeiculo, String subtipo, Instituicao inst) {
		super(id, latitude, longitude, titulo);
		this.setAtributos(equipamento, equipe, identificacao, tipoDeVeiculo, subtipo, inst);
	}

	public Veiculo(int id, GeoPoint p, String titulo,
			Set<Equipamento> equipamento, Set<Equipe> equipe,
			String identificacao, String tipoDeVeiculo, String subtipo, Instituicao inst) {
		super(id, p, titulo);
		this.setAtributos(equipamento, equipe, identificacao, tipoDeVeiculo, subtipo, inst);
	}
	
	private void setAtributos(Set<Equipamento> equipamento, Set<Equipe> equipe,
			String identificacao, String tipoDeVeiculo, String subtipo, Instituicao inst){
		this.equipamento = equipamento;
		this.equipe = equipe;
		this.identificacao = identificacao;
		this.tipoDeVeiculo = tipoDeVeiculo;
		this.subtipo = subtipo;
		this.inst = inst;
		
		this.atualizaTextoBalao();
	}
	
	public void atualizaTextoBalao(){
		String textoBalao = subtipo;
		if (inst != null) textoBalao += ", " + inst.getTipoDeInstituicao();
		if (equipamento != null && !equipamento.isEmpty()){
			for (Iterator<Equipamento> it = this.equipamento.iterator(); it.hasNext();) {
				Equipamento eqp = (Equipamento) it.next();				
				textoBalao += "\nEquipamento: " + eqp.toString();
			}
		}
		if (equipe != null && !equipe.isEmpty()){
			for (Iterator<Equipe> it = this.equipe.iterator(); it.hasNext();) {
				Equipe eqp = (Equipe) it.next();				
				textoBalao += "\nEquipe: " + eqp.toString();
			}
		}
		
		this.setTextoBalao(textoBalao);
	}
	
	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
		super.setTituloBalao(identificacao);
	}

	public String getTipoDeVeiculo() {
		return tipoDeVeiculo;
	}

	public void setTipoDeVeiculo(String tipoDeVeiculo) {
		this.tipoDeVeiculo = tipoDeVeiculo;
	}
	
	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public Instituicao getInst() {
		return inst;
	}

	public void setInst(Instituicao inst) {
		this.inst = inst;
	}

	public Set<Equipamento> getEquipamento() {
		return equipamento;
	}

	public Set<Equipe> getEquipe() {
		return equipe;
	}

	public int getIcone() {
		return icone;
	}

	@Override
	public String toString() {
		return getIdentificacao() + ", " + getTipoDeVeiculo();
	}

	public void setEquipamento(Set<Equipamento> equipamento) {
		this.equipamento = equipamento;
	}

	public void setEquipe(Set<Equipe> equipe) {
		this.equipe = equipe;
	}
	
	@Override
	public int getTipo(){
		return tipo;
	}
	
	public String getXML(){
		LinkedHashMap<String, String> mapa = new LinkedHashMap<String, String>();
		mapa.put("identificacao", this.getIdentificacao());
		mapa.put("tipoDeVeiculo", this.getTipoDeVeiculo());
		mapa.put("subtipo", this.getSubtipo());
		return super.getXML("veiculo", mapa);
	}
	
}
