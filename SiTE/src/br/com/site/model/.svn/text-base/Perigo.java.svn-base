package br.com.site.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import br.com.site.R;

import com.google.android.maps.GeoPoint;

public class Perigo extends PDI {

	private int icone = R.drawable.danger_icon;
	private int tipo = 5;
	private Set<Equipe> equipe;
	private Set<Vitima> vitima;
	private String tipoDePerigo, riscoAssociado, devoIr, identificacao;
	
	public Perigo(int id, String latitude, String longitude, String titulo,
			Set<Equipe> equipe, Set<Vitima> vitima,
			String tipoDePerigo, int riscoAssociado, String devoIr, String identificacao) {
		super(id, latitude, longitude, titulo);
		this.setAtributos(equipe, vitima, tipoDePerigo, riscoAssociado, devoIr, identificacao);
	}
	
	public Perigo(int id, GeoPoint p, String titulo,
			Set<Equipe> equipe, Set<Vitima> vitima,
			String tipoDePerigo, int riscoAssociado, String devoIr, String identificacao) {
		super(id, p, titulo);
		this.setAtributos(equipe, vitima, tipoDePerigo, riscoAssociado, devoIr, identificacao);
	}
	
	private void setAtributos(Set<Equipe> equipe, Set<Vitima> vitima,
			String tipoDePerigo, int riscoAssociado, String devoIr, String identificacao){
		this.equipe = equipe;
		this.vitima = vitima;
		this.tipoDePerigo = tipoDePerigo;
		this.identificacao = identificacao;
		this.devoIr = devoIr;
		
		switch(riscoAssociado){
		case 0: this.riscoAssociado="Desconhecido"; break;
		case 1: this.riscoAssociado="Nulo"; break;
		case 2: this.riscoAssociado="Baixo"; break;
		case 3: this.riscoAssociado="Médio"; break;
		case 4: this.riscoAssociado="Alto"; break;
		default: this.riscoAssociado=""; break;
		}
		
		this.atualizaTextoBalao();
	}
	
	public void atualizaTextoBalao(){
		String textoBalao = this.getTipoDePerigo();		
		if(!this.getRiscoAssociado().equals("")) textoBalao += "\nRisco "+this.getRiscoAssociado();
		
		if (equipe != null && !equipe.isEmpty()){
			for (Iterator<Equipe> it = this.equipe.iterator(); it.hasNext();) {
				Equipe eqp = (Equipe) it.next();				
				textoBalao += "\nEquipe: " + eqp.toString();
			}
		}
		if (vitima != null && !vitima.isEmpty()){
			for (Iterator<Vitima> it = this.vitima.iterator(); it.hasNext();) {
				Vitima vit = (Vitima) it.next();				
				textoBalao += "\nVítima: " + vit.toString();
			}
		}
		
		this.setTextoBalao(textoBalao);
	}
	
	public String getTipoDePerigo() {
		return tipoDePerigo;
	}

	public void setTipoDePerigo(String tipoDePerigo) {
		this.tipoDePerigo = tipoDePerigo;
	}

	public String getRiscoAssociado() {
		return riscoAssociado;
	}
	
	public int getRiscoAssociadoInt() {
		if(this.riscoAssociado == "Nulo") return 1;
		else if(this.riscoAssociado == "Baixo") return 2;
		else if(this.riscoAssociado == "Médio") return 3;
		else if(this.riscoAssociado == "Alto") return 4;
		return 0;
	}

	public void setRiscoAssociado(int riscoAssociado) {
		switch(riscoAssociado){
		case 1: this.riscoAssociado = "Nulo"; break;
		case 2: this.riscoAssociado = "Baixo"; break;
		case 3: this.riscoAssociado = "Médio"; break;
		case 4: this.riscoAssociado = "Alto"; break;
		default: this.riscoAssociado = "Desconhecido"; break;
		}
	}

	public String getDevoIr() {
		return devoIr;
	}

	public void setDevoIr(String devoIr) {
		this.devoIr = devoIr;
	}

	public Set<Equipe> getEquipe() {
		return equipe;
	}

	public Set<Vitima> getVitima() {
		return vitima;
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
		return identificacao + ", " + tipoDePerigo + ", Risco " + riscoAssociado;
	}

	public void setEquipe(Set<Equipe> equipe) {
		this.equipe = equipe;
	}

	public void setVitima(Set<Vitima> vitima) {
		this.vitima = vitima;
	}
	
	@Override
	public int getTipo(){
		return tipo;
	}
	
	public String getXML(){
		LinkedHashMap<String, String> mapa = new LinkedHashMap<String, String>();
		mapa.put("identificacao", this.getIdentificacao());
		mapa.put("tipoDePerigo", this.getTipoDePerigo());
		mapa.put("risco", this.getRiscoAssociado());
		mapa.put("devoIr", this.getDevoIr());
		
		return super.getXML("perigo", mapa);
	}
	

}
