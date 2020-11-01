package br.com.zynger.aulasparticulares;

import java.io.Serializable;

public class Aula implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int horas, minutos, dia, mes, ano, valor, precoCadastrado;

	public Aula(int horas, int minutos, int dia, int mes, int ano){
		this.setHoras(horas);
		this.setMinutos(minutos);
		this.setDia(dia);
		this.setMes(mes);
		this.setAno(ano);
		this.setPrecoCadastrado(DefineValor.preco);
		this.valor = (this.horas*this.precoCadastrado) + ((this.minutos*this.precoCadastrado)/60);
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public int getHoras() {
		return horas;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getDia() {
		return dia;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getMes() {
		return mes;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getAno() {
		return ano;
	}

	public int getValor() {
		return valor;
	}

	public void setPrecoCadastrado(int precoCadastrado) {
		this.precoCadastrado = precoCadastrado;
		this.valor = (this.horas*this.precoCadastrado) + ((this.minutos*this.precoCadastrado)/60);
	}

	public int getPrecoCadastrado() {
		return precoCadastrado;
	}

	@Override
	public String toString() {
		String dia, mes;
		
		if (this.getDia() <10){
			dia = "0" + this.getDia();
		}else{
			dia = String.valueOf(this.getDia());
		}
		if (this.getMes() < 10){
			mes = "0" + this.getMes();
		}else{
			mes = String.valueOf(this.getMes());
		}
		
		
		return dia + "/" + mes + "/" + this.getAno();
	}
	

}
