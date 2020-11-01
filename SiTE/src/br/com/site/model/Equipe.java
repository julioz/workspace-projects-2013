package br.com.site.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import br.com.site.R;

import com.google.android.maps.GeoPoint;

public class Equipe extends PDI {

	private int icone = R.drawable.team_icon;
	private int tipo = 4;
	private Set<Equipamento> equipamento;
	private Set<Estrutura> estrutura;
	private Set<Perigo> perigo;
	private String identificacao, chefe, tipoDeFuncao, tarefaAtual;
	private Instituicao inst;
	private Unidade unid;
	private int qtdMembros;
	private int qtdMembrosFeridos;
	
	public Equipe(int id, String latitude, String longitude, String titulo,
			Set<Estrutura> estrutura, Set<Equipamento> equipamento, Set<Perigo> perigo,
			String identificacaoDaEquipe, String chefeDaEquipe, Instituicao instituicao, Unidade unidade,
			int tipoDeFuncao, int quantidadeDeMembros, int quantidadeMembrosFeridos, String tarefaAtual) {
		super(id, latitude, longitude, titulo);
		this.setAtributos(estrutura, equipamento, perigo, identificacaoDaEquipe, chefeDaEquipe, instituicao, unidade, tipoDeFuncao, quantidadeDeMembros, quantidadeMembrosFeridos, tarefaAtual);
	}
	
	public Equipe(int id, GeoPoint p, String titulo,
			Set<Estrutura> estrutura, Set<Equipamento> equipamento, Set<Perigo> perigo,
			String identificacaoDaEquipe, String chefeDaEquipe, Instituicao instituicao, Unidade unidade,
			int tipoDeFuncao, int quantidadeDeMembros, int quantidadeMembrosFeridos, String tarefaAtual) {
		super(id, p, titulo);
		this.setAtributos(estrutura, equipamento, perigo, identificacaoDaEquipe, chefeDaEquipe, instituicao, unidade, tipoDeFuncao, quantidadeDeMembros, quantidadeMembrosFeridos, tarefaAtual);
		
		
	}
	
	private void setAtributos(Set<Estrutura> estrutura, Set<Equipamento> equipamento, Set<Perigo> perigo,
			String identificacaoDaEquipe, String chefeDaEquipe, Instituicao instituicao, Unidade unidade,
			int tipoDeFuncao, int quantidadeDeMembros, int quantidadeMembrosFeridos, String tarefaAtual){
		this.perigo = perigo;
		this.estrutura = estrutura;
		this.equipamento = equipamento;
		this.identificacao = identificacaoDaEquipe;
		this.tarefaAtual = tarefaAtual;
		this.chefe = chefeDaEquipe;
		this.inst = instituicao;
		this.unid = unidade;
		this.qtdMembros = quantidadeDeMembros;
		this.qtdMembrosFeridos = quantidadeMembrosFeridos;
		
		switch(tipoDeFuncao){
		case 0: this.tipoDeFuncao = "Desconhecido"; break;
		case 1: this.tipoDeFuncao = "Resgate"; break;
		case 2: this.tipoDeFuncao = "Administração"; break;
		case 3: this.tipoDeFuncao = "Médica"; break;
		case 4: this.tipoDeFuncao = "Segurança"; break;
		case 5: this.tipoDeFuncao = "Voluntários"; break;
		case 6: this.tipoDeFuncao = "Serviços Públicos"; break;
		default: this.tipoDeFuncao = ""; break;
		}
		
		this.atualizaTextoBalao();
	}
	
	public void atualizaTextoBalao() {
		String textoBalao = "";
		if(this.getQtdMembros() >0) textoBalao += this.getQtdMembros() + " membros";
		if(this.getQtdMembrosFeridos()>0) textoBalao += ", " + this.getQtdMembrosFeridos() + " feridos";
		if(this.getTipoDeFuncao().length()>0) textoBalao += ", " + this.getTipoDeFuncao();
		if(this.getInst()!= null) textoBalao += "\nInstituição: " + this.getInst().getTipoDeInstituicao();
		
		if (estrutura != null && !estrutura.isEmpty()){
			for (Iterator<Estrutura> it = this.estrutura.iterator(); it.hasNext();) {
				Estrutura est = (Estrutura) it.next();				
				textoBalao += "\nEstrutura: " + est.toString();
			}
		}
		if (equipamento != null && !equipamento.isEmpty()){
			for (Iterator<Equipamento> it = this.equipamento.iterator(); it.hasNext();) {
				Equipamento eqp = (Equipamento) it.next();				
				textoBalao += "\nEquipamento: " + eqp.toString();
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
	
	public String getTarefaAtual() {
		return tarefaAtual;
	}

	public void setTarefaAtual(String tarefaAtual) {
		this.tarefaAtual = tarefaAtual;
	}

	public String getChefe() {
		return chefe;
	}

	public void setChefe(String chefe) {
		this.chefe = chefe;
	}

	public String getTipoDeFuncao() {
		return tipoDeFuncao;
	}
	
	public int getTipoDeFuncaoInt() {
		if(this.tipoDeFuncao == "Resgate") return 1;
		else if(this.tipoDeFuncao == "Administração") return 2;
		else if(this.tipoDeFuncao == "Médica") return 3;
		else if(this.tipoDeFuncao == "Segurança") return 4;
		else if(this.tipoDeFuncao == "Voluntários") return 5;
		else if(this.tipoDeFuncao == "Serviços Públicos") return 6;
		return 0;
	}

	public void setTipoDeFuncao(int tipoDeFuncao) {
		switch(tipoDeFuncao){
		case 1: this.tipoDeFuncao = "Resgate"; break;
		case 2: this.tipoDeFuncao = "Administração"; break;
		case 3: this.tipoDeFuncao = "Médica"; break;
		case 4: this.tipoDeFuncao = "Segurança"; break;
		case 5: this.tipoDeFuncao = "Voluntários"; break;
		case 6: this.tipoDeFuncao = "Serviços Públicos"; break;
		default: this.tipoDeFuncao = "Desconhecido"; break;
		}
	}

	public Instituicao getInst() {
		return inst;
	}

	public void setInst(Instituicao inst) {
		this.inst = inst;
	}

	public Unidade getUnid() {
		return unid;
	}

	public void setUnid(Unidade unid) {
		this.unid = unid;
	}

	public int getQtdMembros() {
		return qtdMembros;
	}

	public void setQtdMembros(int qtdMembros) {
		this.qtdMembros = qtdMembros;
	}
	

	public int getQtdMembrosFeridos() {
		return qtdMembrosFeridos;
	}

	public void setQtdMembrosFeridos(int qtdMembrosFeridos) {
		this.qtdMembrosFeridos = qtdMembrosFeridos;
	}

	public Set<Equipamento> getEquipamento() {
		return equipamento;
	}

	public Set<Estrutura> getEstrutura() {
		return estrutura;
	}

	public Set<Perigo> getPerigo() {
		return perigo;
	}

	public int getIcone() {
		return icone;
	}

	@Override
	public String toString() {
		String str = identificacao + ", " + tipoDeFuncao;
		if(!getTarefaAtual().equals("") || !getTarefaAtual().equals(null)) str += ", Tarefa atual: "+ getTarefaAtual();
		return str;
	}

	public void setEquipamento(Set<Equipamento> equipamento) {
		this.equipamento = equipamento;
	}

	public void setEstrutura(Set<Estrutura> estrutura) {
		this.estrutura = estrutura;
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
		mapa.put("chefe", this.getChefe());
		mapa.put("tipoDeFuncao", this.getTipoDeFuncao());
		mapa.put("qtdMembros", String.valueOf(this.getQtdMembros()));
		mapa.put("qtdMembrosFeridos", String.valueOf(this.getQtdMembrosFeridos()));
		mapa.put("tarefaAtual", this.getTarefaAtual());
		
		return super.getXML("equipe", mapa);
	}
	
	
}
