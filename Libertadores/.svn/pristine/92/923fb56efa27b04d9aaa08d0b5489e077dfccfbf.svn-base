package br.com.zynger.libertadores.enums;

public enum Phase {
	PRELIB, ROUNDOF16, QUARTERFINAL, SEMIFINAL, FINAL;
	
	public Integer getNumber() {
		if(this == PRELIB) return 10;
		else if(this == ROUNDOF16) return 11;
		else if(this == QUARTERFINAL) return 12;
		else if(this == SEMIFINAL) return 13;
		else if(this == FINAL) return 14;
		else return -1;
	}

	public Phase getNextPhase() {
	     return this.ordinal() < Phase.values().length - 1
	         ? Phase.values()[this.ordinal() + 1]
	         : null;
	   }
}
