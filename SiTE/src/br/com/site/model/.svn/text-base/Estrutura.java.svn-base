package br.com.site.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import br.com.site.R;

import com.google.android.maps.GeoPoint;

public class Estrutura extends PDI {

	int icone = R.drawable.casa_icon;
	private int tipo = 2;
	private Set<Equipe> equipe;
	private Set<Perigo> perigo;
	private Set<Vitima> vitima;
	private String identificacao, tipoDeEstrutura, tipoDeMaterial, tipoDeSubsolo, estadoDaRevisao, estabilidade;
	private String tamanhoDoAcesso, dificuldadeDeEntrada, evolucaoDoTrabalho;
	private int afluenciaPublico, numAndares, numSubsolos, tempoAcesso, qtdPessoasPiso, resistenciaPiso;
	
	public Estrutura(int id, String latitude, String longitude, String titulo,
			Set<Equipe> equipe, Set<Perigo> perigo, Set<Vitima> vitima,
			String identificacao, String tipoDeEstrutura, int afluenciaPublico, int estadoDaRevisao, int instabilidade,
			String tipoDeMaterial, String tamanhoDoAcesso, int dificuldadeDeEntrada,
			int numAndares, int numSubsolos, String tipoDeSubsolo, int evolucaoDoTrabalho,
			int qtdPessoasPiso, int resistenciaPiso, int tempoEstimadoAcesso) {
		super(id, latitude, longitude, titulo);
		this.setAtributos(equipe, perigo, vitima, identificacao, tipoDeEstrutura, afluenciaPublico, estadoDaRevisao, instabilidade, tipoDeMaterial, tamanhoDoAcesso, dificuldadeDeEntrada, numAndares, numSubsolos, tipoDeSubsolo, evolucaoDoTrabalho, qtdPessoasPiso, resistenciaPiso, tempoEstimadoAcesso);
	}
	
	public Estrutura(int id, GeoPoint p, String titulo,
			Set<Equipe> equipe, Set<Perigo> perigo, Set<Vitima> vitima,
			String identificacao, String tipoDeEstrutura, int afluenciaPublico, int estadoDaRevisao, int instabilidade,
			String tipoDeMaterial, String tamanhoDoAcesso, int dificuldadeDeEntrada,
			int numAndares, int numSubsolos, String tipoDeSubsolo, int evolucaoDoTrabalho,
			int qtdPessoasPiso, int resistenciaPiso, int tempoEstimadoAcesso) {
		super(id, p, titulo);
		this.setAtributos(equipe, perigo, vitima, identificacao, tipoDeEstrutura, afluenciaPublico, estadoDaRevisao, instabilidade, tipoDeMaterial, tamanhoDoAcesso, dificuldadeDeEntrada, numAndares, numSubsolos, tipoDeSubsolo, evolucaoDoTrabalho, qtdPessoasPiso, resistenciaPiso, tempoEstimadoAcesso);
	}
	
	private void setAtributos(Set<Equipe> equipe, Set<Perigo> perigo, Set<Vitima> vitima,
			String identificacao, String tipoDeEstrutura, int afluenciaPublico, int estadoDaRevisao, int instabilidade,
			String tipoDeMaterial, String tamanhoDoAcesso, int dificuldadeDeEntrada,
			int numAndares, int numSubsolos, String tipoDeSubsolo, int evolucaoDoTrabalho,
			int qtdPessoasPiso, int resistenciaPiso, int tempoEstimadoAcesso){
		this.equipe = equipe;
		this.perigo = perigo;
		this.vitima = vitima;
		this.identificacao = identificacao;
		this.tipoDeEstrutura = tipoDeEstrutura;
		this.tipoDeMaterial = tipoDeMaterial;
		this.tipoDeSubsolo = tipoDeSubsolo;
		this.afluenciaPublico = afluenciaPublico;
		this.numAndares = numAndares;
		this.numSubsolos = numSubsolos;
		this.qtdPessoasPiso = qtdPessoasPiso;
		this.resistenciaPiso = resistenciaPiso;
		this.tempoAcesso = tempoEstimadoAcesso;
		this.tipoDeSubsolo = tipoDeSubsolo;
		this.tamanhoDoAcesso = tamanhoDoAcesso;
		
		switch(estadoDaRevisao){
		case 1: this.estadoDaRevisao = "Não revisado"; break;
		case 2: this.estadoDaRevisao = "Em revisão"; break;
		case 3: this.estadoDaRevisao = "Revisado"; break;
		default: this.estadoDaRevisao = "Desconhecido"; break;
		}
		
		switch(instabilidade){
		case 1: this.estabilidade = "Estável"; break;
		case 2: this.estabilidade = "Instável"; break;
		case 3: this.estabilidade = "Completamente Instável"; break;
		default: this.estabilidade = "Desconhecido"; break;
		}
		
		switch(dificuldadeDeEntrada){
		case 1: this.dificuldadeDeEntrada = "Leve"; break;
		case 2: this.dificuldadeDeEntrada = "Média"; break;
		case 3: this.dificuldadeDeEntrada = "Difícil"; break;
		case 4: this.dificuldadeDeEntrada = "Muito Difícil"; break;
		default: this.dificuldadeDeEntrada = "Desconhecido"; break;
		}
		
		switch(evolucaoDoTrabalho){
		case 1: this.evolucaoDoTrabalho = "Não começou"; break;
		case 2: this.evolucaoDoTrabalho = "Em andamento"; break;
		case 3: this.evolucaoDoTrabalho = "Finalizado"; break;
		case 4: this.evolucaoDoTrabalho = "Em pausa"; break;
		default: this.evolucaoDoTrabalho = "Desconhecido"; break;
		}
		
		this.atualizaTextoBalao();
	}
	
	public void atualizaTextoBalao(){
		String textoBalao = "";
    	if (this.getTipoDeMaterial().equals("")){
    		textoBalao += "Estado da busca: " + this.getEstadoDaRevisao() + "\n" + this.getEstabilidade();
    	}else{
    		textoBalao += this.getTipoDeMaterial() + ", Estado da busca: " + this.getEstadoDaRevisao() + "\n" + this.getEstabilidade();
    	}
    	/*if (equipe != null){
    		textoBalao += "\nEquipe: " + equipe.toString();
    	}*/
    	if (equipe != null && !equipe.isEmpty()){
			for (Iterator<Equipe> it = this.equipe.iterator(); it.hasNext();) {
				Equipe eqp = (Equipe) it.next();				
				textoBalao += "\nEquipe: " + eqp.toString();
			}
		}
		/*if (perigo != null){
			textoBalao += "\nPerigo: " + perigo.toString();
		}*/
		if (perigo != null && !perigo.isEmpty()){
			for (Iterator<Perigo> it = this.perigo.iterator(); it.hasNext();) {
				Perigo per = (Perigo) it.next();				
				textoBalao += "\nPerigo: " + per.toString();
			}
		}
		/*if (vitima != null){
			textoBalao += "\nVítima: " + vitima.toString();
		}*/
		if (vitima != null && !vitima.isEmpty()){
			for (Iterator<Vitima> it = this.vitima.iterator(); it.hasNext();) {
				Vitima vitima = (Vitima) it.next();				
				textoBalao += "\nVítima: " + vitima.toString();
			}
		}
		
		this.setTextoBalao(textoBalao);
	}

	public String getEvolucaoDoTrabalho() {
		return evolucaoDoTrabalho;
	}
	
	public int getEvolucaoDoTrabalhoInt() {
		if(this.evolucaoDoTrabalho == "Não começou") return 1;
		else if(this.evolucaoDoTrabalho == "Em andamento") return 2;
		else if(this.evolucaoDoTrabalho == "Finalizado") return 3;
		else if(this.evolucaoDoTrabalho == "Em pausa") return 4;
		return 0;
	}

	public void setEvolucaoDoTrabalho(int evolucaoDoTrabalho) {
		switch(evolucaoDoTrabalho){
		case 1: this.evolucaoDoTrabalho = "Não começou"; break;
		case 2: this.evolucaoDoTrabalho = "Em andamento"; break;
		case 3: this.evolucaoDoTrabalho = "Finalizado"; break;
		case 4: this.evolucaoDoTrabalho = "Em pausa"; break;
		default: this.evolucaoDoTrabalho = "Desconhecido"; break;
		}
	}

	public String getTipoDeEstrutura() {
		return tipoDeEstrutura;
	}

	public void setTipoDeEstrutura(String tipoDeEstrutura) {
		this.tipoDeEstrutura = tipoDeEstrutura;
	}

	public String getTipoDeMaterial() {
		return tipoDeMaterial;
	}

	public void setTipoDeMaterial(String tipoDeMaterial) {
		this.tipoDeMaterial = tipoDeMaterial;
	}
	
	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
		super.setTituloBalao(identificacao);
	}

	public String getTipoDeSubsolo() {
		return tipoDeSubsolo;
	}

	public void setTipoDeSubsolo(String tipoDeSubsolo) {
		this.tipoDeSubsolo = tipoDeSubsolo;
	}

	public String getTamanhoDoAcesso() {
		return tamanhoDoAcesso;
	}

	public void setTamanhoDoAcesso(String tamanhoDoAcesso) {
		this.tamanhoDoAcesso = tamanhoDoAcesso;
	}

	public int getAfluenciaPublico() {
		return afluenciaPublico;
	}

	public void setAfluenciaPublico(int afluenciaPublico) {
		this.afluenciaPublico = afluenciaPublico;
	}

	public int getNumAndares() {
		return numAndares;
	}

	public void setNumAndares(int numAndares) {
		this.numAndares = numAndares;
	}

	public int getNumSubsolos() {
		return numSubsolos;
	}

	public void setNumSubsolos(int numSubsolos) {
		this.numSubsolos = numSubsolos;
	}

	public int getTempoAcesso() {
		return tempoAcesso;
	}

	public void setTempoAcesso(int tempoAcesso) {
		this.tempoAcesso = tempoAcesso;
	}

	public int getQtdPessoasPiso() {
		return qtdPessoasPiso;
	}

	public void setQtdPessoasPiso(int qtdPessoasPiso) {
		this.qtdPessoasPiso = qtdPessoasPiso;
	}

	public int getResistenciaPiso() {
		return resistenciaPiso;
	}

	public void setResistenciaPiso(int resistenciaPiso) {
		this.resistenciaPiso = resistenciaPiso;
	}

	public String getDificuldadeDeEntrada() {
		return dificuldadeDeEntrada;
	}
	
	public int getDificuldadeDeEntradaInt(){
		if(this.dificuldadeDeEntrada == "Leve") return 1;
		else if(this.dificuldadeDeEntrada == "Média") return 2;
		else if(this.dificuldadeDeEntrada == "Difícil") return 3;
		else if(this.dificuldadeDeEntrada == "Muito Difícil") return 4;
		else return 0;
	}

	public void setDificuldadeDeEntrada(int dificuldadeDeEntrada) {
		switch(dificuldadeDeEntrada){
		case 1: this.dificuldadeDeEntrada = "Leve"; break;
		case 2: this.dificuldadeDeEntrada = "Média"; break;
		case 3: this.dificuldadeDeEntrada = "Difícil"; break;
		case 4: this.dificuldadeDeEntrada = "Muito Difícil"; break;
		default: this.dificuldadeDeEntrada = "Desconhecido"; break;
		}
	}

	public String getEstabilidade() {
		return estabilidade;
	}
	
	public int getEstabilidadeInt() {
		if(this.estabilidade == "Estável") return 1;
		else if(this.estabilidade == "Instável") return 2;
		else if (this.estabilidade == "Completamente Instável") return 3;
		return 0;
	}

	public void setEstabilidade(int instabilidade) {
		switch(instabilidade){
		case 1: this.estabilidade = "Estável"; break;
		case 2: this.estabilidade = "Instável"; break;
		case 3: this.estabilidade = "Completamente Instável"; break;
		default: this.estabilidade = "Desconhecido"; break;
		}
	}

	public String getEstadoDaRevisao() {
		return estadoDaRevisao;
	}
	
	public int getEstadoDaRevisaoInt() {
		if(this.estadoDaRevisao == "Não revisado") return 1;
		else if(this.estadoDaRevisao == "Em revisão") return 2;
		else if(this.estadoDaRevisao == "Revisado") return 3;
		else return 0;
	}

	public void setEstadoDaRevisao(int estadoDaRevisao) {
		switch(estadoDaRevisao){
		case 1: this.estadoDaRevisao = "Não revisado"; break;
		case 2: this.estadoDaRevisao = "Em revisão"; break;
		case 3: this.estadoDaRevisao = "Revisado"; break;
		default: this.estadoDaRevisao = "Desconhecido"; break;
		}
	}

	public int getIcone() {
		return icone;
	}
	
	public Set<Equipe> getEquipe() {
		return equipe;
	}


	public Set<Perigo> getPerigo() {
		return perigo;
	}


	public Set<Vitima> getVitima() {
		return vitima;
	}


	@Override
	public String toString() {
		String str = tipoDeEstrutura;
		if(!estabilidade.equals("Desconhecido") || !estabilidade.equals("") || !estabilidade.equals(null)){
			str += ", " + estabilidade;
		}
		return str;
	}

	public void setEquipe(Set<Equipe> equipe) {
		this.equipe = equipe;
	}

	public void setPerigo(Set<Perigo> perigo) {
		this.perigo = perigo;
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
		mapa.put("tipoDeEstrutura", this.getTipoDeEstrutura());
		mapa.put("afluenciaDePublico", String.valueOf(this.getAfluenciaPublico()));
		mapa.put("estadoDaRevisao", this.getEstadoDaRevisao());
		mapa.put("instabilidade", this.getEstabilidade());
		mapa.put("tipoDeMaterial", this.getTipoDeMaterial());
		mapa.put("tamanhoDoAcesso", this.getTamanhoDoAcesso());
		mapa.put("dificuldadeDeEntrada", this.getDificuldadeDeEntrada());
		mapa.put("numAndares", String.valueOf(this.getNumAndares()));
		mapa.put("numSubsolos", String.valueOf(this.getNumSubsolos()));
		mapa.put("tipoDeSubsolo", this.getTipoDeSubsolo());
		mapa.put("evolucaoDoTrabalho", this.getEvolucaoDoTrabalho());
		mapa.put("qtdPessoasPiso", String.valueOf(this.getQtdPessoasPiso()));
		mapa.put("resistenciaPiso", String.valueOf(this.getResistenciaPiso()));
		mapa.put("tempoEstimadoAcesso", String.valueOf(this.getTempoAcesso()));
		
		return super.getXML("estrutura", mapa);
	}
	
	
}
