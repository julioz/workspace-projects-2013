package br.com.site.model;

public class Instituicao {
	
	private String tipoDeInstituicao, chefeDaInstituicao;
	
	public Instituicao(String tipo, String chefeDaInstituicao){
		this.tipoDeInstituicao = tipo;
		this.chefeDaInstituicao = chefeDaInstituicao;
	}

	public String getTipoDeInstituicao() {
		return tipoDeInstituicao;
	}

	public void setTipoDeInstituicao(String tipo) {
		this.tipoDeInstituicao = tipo;
	}

	public String getChefeDaInstituicao() {
		return chefeDaInstituicao;
	}

	public void setChefeDaInstituicao(String chefeDaInstituicao) {
		this.chefeDaInstituicao = chefeDaInstituicao;
	}

	@Override
	public String toString() {
		return tipoDeInstituicao + ", " + chefeDaInstituicao;
	}

}
