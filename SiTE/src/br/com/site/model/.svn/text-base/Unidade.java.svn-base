package br.com.site.model;

public class Unidade {
	
	private String unidade, chefeDaUnidade;
	private Instituicao inst;
	
	public Unidade(String unidade, String chefeDaUnidade){
		this.unidade = unidade;
		this.chefeDaUnidade = chefeDaUnidade;
	}

	public String getUnidade() {
		return unidade;
	}
	
	public void setInstituicao(Instituicao i){
		this.inst = i;
	}
	
	public Instituicao getInstituicao(){
		return inst;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getChefeDaUnidade() {
		return chefeDaUnidade;
	}

	public void setChefeDaUnidade(String chefeDaUnidade) {
		this.chefeDaUnidade = chefeDaUnidade;
	}

	@Override
	public String toString() {
		if(!chefeDaUnidade.equals("")){
			return unidade + ", " + chefeDaUnidade;
		}else{
			return unidade;
		}
	}
	
	

}
