package br.com.zynger.libertadores.model;

public class HistoricFinal implements Comparable<HistoricFinal> {
	
	private String winner;
	private String runnerup;
	private HistoricMatch firstMatch;
	private HistoricMatch secondMatch;
	private HistoricMatch penalties;
	private Integer year;
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public String getRunnerup() {
		return runnerup;
	}
	
	public void setRunnerup(String runnerup) {
		this.runnerup = runnerup;
	}
	
	public HistoricMatch getFirstMatch() {
		return firstMatch;
	}
	
	public void setFirstMatch(HistoricMatch firstMatch) {
		this.firstMatch = firstMatch;
	}
	
	public HistoricMatch getSecondMatch() {
		return secondMatch;
	}
	
	public void setSecondMatch(HistoricMatch secondMatch) {
		this.secondMatch = secondMatch;
	}
	
	public void setPenalties(HistoricMatch penalties) {
		this.penalties = penalties;
	}
	
	public HistoricMatch getPenalties() {
		return penalties;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public int compareTo(HistoricFinal another) {
		return getYear() - another.getYear();
	}
}
