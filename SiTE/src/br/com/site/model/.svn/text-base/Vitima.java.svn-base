package br.com.site.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import br.com.site.R;

import com.google.android.maps.GeoPoint;

public class Vitima extends PDI {

	private int icone = R.drawable.victim_icon;
	private int tipo = 6;
	private Set<Estrutura> estrutura;
	private Set<Equipe> equipe;
	private Set<Perigo> perigo;
	private String identificacao;
	private int numPosVit, qtdViv, qtdMor, qtdVivRes, qtdMorRem;
	
	public Vitima(int id, String latitude, String longitude, String titulo,
			Set<Estrutura> estrutura, Set<Equipe> equipe, Set<Perigo> perigo,
			String identificacao, int numPossiveisVitimas, int qtdVivos, int qtdMortos, int qtdVivosResgatados, int qtdMortosRemovidos) {
		super(id, latitude, longitude, titulo);
		this.setAtributos(estrutura, equipe, perigo, identificacao, numPossiveisVitimas, qtdVivos, qtdMortos, qtdVivosResgatados, qtdMortosRemovidos);
	}
	
	public Vitima(int id, GeoPoint p, String titulo,
			Set<Estrutura> estrutura, Set<Equipe> equipe, Set<Perigo> perigo,
			String identificacao, int numPossiveisVitimas, int qtdVivos, int qtdMortos, int qtdVivosResgatados, int qtdMortosRemovidos) {
		super(id, p, titulo);
		this.setAtributos(estrutura, equipe, perigo, identificacao, numPossiveisVitimas, qtdVivos, qtdMortos, qtdVivosResgatados, qtdMortosRemovidos);		
	}
	
	private void setAtributos(Set<Estrutura> estrutura, Set<Equipe> equipe, Set<Perigo> perigo,
			String identificacao, int numPossiveisVitimas, int qtdVivos, int qtdMortos, int qtdVivosResgatados, int qtdMortosRemovidos){
		this.equipe = equipe;
		this.estrutura = estrutura;
		this.perigo = perigo;
		this.numPosVit = numPossiveisVitimas;
		this.qtdViv = qtdVivos;
		this.qtdMor = qtdMortos;
		this.qtdVivRes = qtdVivosResgatados;
		this.qtdMorRem = qtdMortosRemovidos;
		this.identificacao = identificacao;
		
		this.atualizaTextoBalao();
	}
	
	public void atualizaTextoBalao() {
		String textoBalao = "";
		if(this.getNumeroPossiveisVitimas() >= 0) textoBalao += "Possíveis vítimas: " + this.getNumeroPossiveisVitimas();
		if(this.getQtdVivos() >= 0) textoBalao += ", Vivos confirmados: " + this.getQtdVivos();
		
		if (estrutura != null && !estrutura.isEmpty()){
			for (Iterator<Estrutura> it = this.estrutura.iterator(); it.hasNext();) {
				Estrutura est = (Estrutura) it.next();	
				textoBalao += "\nEstrutura: " + est.toString();
				if(!est.getTamanhoDoAcesso().equals("") || !est.getTamanhoDoAcesso().equals(null)) textoBalao += "\nTamanho do acesso: "+ est.getTamanhoDoAcesso();
				if(!est.getDificuldadeDeEntrada().equals("") || !est.getDificuldadeDeEntrada().equals(null)) textoBalao += ", Dific. de entrada: "+ est.getDificuldadeDeEntrada();
			}
		}
		if (equipe != null && !equipe.isEmpty()){
			for (Iterator<Equipe> it = this.equipe.iterator(); it.hasNext();) {
				Equipe eqp = (Equipe) it.next();				
				textoBalao += "\nEquipe: " + eqp.toString();
				if(!eqp.getChefe().equals("") || !eqp.getChefe().equals(null)) textoBalao += ", Chefe: "+ eqp.getChefe();
			}
		}
		if (perigo != null && !perigo.isEmpty()){
			for (Iterator<Perigo> it = this.perigo.iterator(); it.hasNext();) {
				Perigo per = (Perigo) it.next();				
				textoBalao += "\nPerigo: " + per.toString();
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

	public int getNumeroPossiveisVitimas() {
		return numPosVit;
	}

	public void setNumeroPossiveisVitimas(int numPosVit) {
		this.numPosVit = numPosVit;
	}

	public int getQtdVivos() {
		return qtdViv;
	}

	public void setQtdVivos(int qtdViv) {
		this.qtdViv = qtdViv;
	}

	public int getQtdMortos() {
		return qtdMor;
	}

	public void setQtdMortos(int qtdMor) {
		this.qtdMor = qtdMor;
	}

	public int getQtdVivosResgatados() {
		return qtdVivRes;
	}

	public void setQtdVivosResgatados(int qtdVivRes) {
		this.qtdVivRes = qtdVivRes;
	}

	public int getQtdMortosRemovidos() {
		return qtdMorRem;
	}

	public void setQtdMortosRemovidos(int qtdMorRem) {
		this.qtdMorRem = qtdMorRem;
	}

	public Set<Estrutura> getEstrutura() {
		return estrutura;
	}

	public Set<Equipe> getEquipe() {
		return equipe;
	}

	public Set<Perigo> getPerigo() {
		return perigo;
	}

	public int getIcone() {
		return icone;
	}

	@Override
	public String toString() {
		return identificacao + ", Possíveis vítimas: " + numPosVit + ", Vivos confirmados: " + qtdViv ;
	}

	public void setEstrutura(Set<Estrutura> estrutura) {
		this.estrutura = estrutura;
	}

	public void setEquipe(Set<Equipe> equipe) {
		this.equipe = equipe;
	}

	public void setPerigo(Set<Perigo> perigo) {
		this.perigo = perigo;
	}
	
	@Override
	public int getTipo(){
		return tipo;
	}
	
	public String getXML(){
		LinkedHashMap<String, String> mapa = new LinkedHashMap<String, String>();
		mapa.put("identificacao", this.getIdentificacao());
		mapa.put("numPosVit", String.valueOf(this.getNumeroPossiveisVitimas()));
		mapa.put("qtdVivos", String.valueOf(this.getQtdVivos()));
		mapa.put("qtdMortos", String.valueOf(this.getQtdMortos()));
		mapa.put("qtdVivResgatados", String.valueOf(this.getQtdVivosResgatados()));
		mapa.put("qtdMortRemovidos", String.valueOf(this.getQtdMortosRemovidos()));
		
		return super.getXML("vitima", mapa);
	}
	

}
