package br.com.site.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import br.com.site.R;

import com.google.android.maps.GeoPoint;

public class Equipamento extends PDI {

	private int icone = R.drawable.equipment_icon;
	private int tipo = 3;
	private Set<Veiculo> veiculo;
	private Set<Equipe> equipe;
	private int quantidade, disponiveis;
	private String tipoDeEquipamento, identificacao;
	
	public Equipamento(int id, String latitude, String longitude, String titulo,
			Set<Veiculo> veiculo, Set<Equipe> equipe,
			String identificacao, String tipoDeEquipamento, int quantidade, int disponiveis) {
		super(id, latitude, longitude, titulo);
		this.setAtributos(veiculo, equipe, identificacao, tipoDeEquipamento, quantidade, disponiveis);
	}
	
	public Equipamento(int id, GeoPoint p, String titulo,
			Set<Veiculo> veiculo, Set<Equipe> equipe,
			String identificacao, String tipoDeEquipamento, int quantidade, int disponiveis) {
		super(id,p, titulo);
		this.setAtributos(veiculo, equipe, identificacao, tipoDeEquipamento, quantidade, disponiveis);
	}
	
	private void setAtributos(Set<Veiculo> veiculo, Set<Equipe> equipe,
			String identificacao, String tipoDeEquipamento, int quantidade, int disponiveis){
		this.veiculo = veiculo;
		this.equipe = equipe;
		this.identificacao = identificacao;
		this.tipoDeEquipamento = tipoDeEquipamento;
		this.quantidade = quantidade;
		this.disponiveis = disponiveis;
		
		this.atualizaTextoBalao();
	}
	
	public void atualizaTextoBalao() {
		String textoBalao = this.getTipoDeEquipamento();
		
		int disp = this.getDisponiveis();
		int qtd = this.getQuantidade();
		
		if(qtd!=1){
			textoBalao += ", " + disp + " disponíveis em " + qtd;
		}else textoBalao = this.getTipoDeEquipamento() + ", " + disp + " disponível em " + qtd;
		
		if (veiculo != null && !veiculo.isEmpty()){
			for (Iterator<Veiculo> it = this.veiculo.iterator(); it.hasNext();) {
				Veiculo vei = (Veiculo) it.next();				
				textoBalao += "\nVeículo: " + vei.toString();
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

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getDisponiveis() {
		return disponiveis;
	}

	public void setDisponiveis(int disponiveis) {
		this.disponiveis = disponiveis;
	}

	public String getTipoDeEquipamento() {
		return tipoDeEquipamento;
	}

	public void setTipoDeEquipamento(String tipoDeEquipamento) {
		this.tipoDeEquipamento = tipoDeEquipamento;
	}

	public Set<Veiculo> getVeiculo() {
		return veiculo;
	}

	public Set<Equipe> getEquipe() {
		return equipe;
	}

	public int getIcone() {
		return icone;
	}
	
	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
		super.setTituloBalao(identificacao);
	}

	@Override
	public String toString() {
		if(this.disponiveis==1) return tipoDeEquipamento + ", " + disponiveis + " disponível em " + quantidade;
		else return tipoDeEquipamento + ", " + disponiveis + " disponíveis em " + quantidade;
	}

	public void setVeiculo(Set<Veiculo> veiculo) {
		this.veiculo = veiculo;
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
		mapa.put("tipoDeEquipamento", this.getTipoDeEquipamento());
		mapa.put("quantidade", String.valueOf(this.getQuantidade()));
		mapa.put("disponiveis", String.valueOf(this.getDisponiveis()));
		
		return super.getXML("equipamento", mapa);
	}
}