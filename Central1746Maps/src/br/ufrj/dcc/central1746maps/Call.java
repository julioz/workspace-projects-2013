package br.ufrj.dcc.central1746maps;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Locale;

@SuppressWarnings("serial")
public class Call implements Serializable {

	public enum Type {
		CONSERVAÇÃO_DE_VIAS, PODA_DE_ÁRVORES, ILUMINAÇÃO_PÚBLICA, ESTACIONAMENTO_IRREGULAR;

		public String getTypeName() {
			return toString().replace("_", " ")
					.toUpperCase(Locale.getDefault());
		}

		public int getImageResource() {
			if (this == Type.CONSERVAÇÃO_DE_VIAS) {
				return R.drawable.conservacaodevias;
			} else if (this == Type.ESTACIONAMENTO_IRREGULAR) {
				return R.drawable.estacionamentoirregular;
			} else if (this == Type.ILUMINAÇÃO_PÚBLICA) {
				return R.drawable.iluminacaopublica;
			} else if (this == Type.PODA_DE_ÁRVORES) {
				return R.drawable.podadearvores;
			}
			return 0;
		}
	}

	public enum State {
		ABERTO, FECHADO, ATRASADO;
	}

	private Type type;
	private String address;
	private State state;
	private String addressPrefix;
	private GregorianCalendar dueDate;
	private int id;
	private GregorianCalendar closingDate;
	private GregorianCalendar openingDate;
	private String neighborhood;
	private String number;

	public Call() {
	}

	public Type getType() {
		return type;
	}

	public String getAddress() {
		return address;
	}

	public State getState() {
		return state;
	}

	public String getAddressPrefix() {
		return addressPrefix;
	}

	public GregorianCalendar getDueDate() {
		return dueDate;
	}

	public int getId() {
		return id;
	}

	public GregorianCalendar getClosingDate() {
		return closingDate;
	}

	public GregorianCalendar getOpeningDate() {
		return openingDate;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getNumber() {
		return number;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setAddressPrefix(String addressPrefix) {
		this.addressPrefix = addressPrefix;
	}

	public void setDueDate(GregorianCalendar dueDate) {
		this.dueDate = dueDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClosingDate(GregorianCalendar closingDate) {
		this.closingDate = closingDate;
	}

	public void setOpeningDate(GregorianCalendar openingDate) {
		this.openingDate = openingDate;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
