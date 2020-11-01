package br.com.zynger.guesstheclub.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Club implements Serializable, Comparable<Club> {
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private String name;

	@DatabaseField
	private StatusEnum status;
	
	@DatabaseField(defaultValue = "0")
	private int tipNumber;
	
	@DatabaseField(defaultValue = "0")
	private int attemptsNumber;
	
	@DatabaseField
	private String badge;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "phase_id")
	private Phase phase;
	
	@ForeignCollectionField(eager = true)
	private Collection<Name> names;
	
	@ForeignCollectionField(eager = true)
	private Collection<Tip> tips;
	
	public Club(String badge, Collection<Name> names) {
		setBadge(badge);
		for (Iterator<Name> it = names.iterator(); it.hasNext();) {
			Name name = (Name) it.next();
			if(name.isMain()) setMainName(name.getText());
		}
		setNames(names);
		setAttemptsNumber(0);
		setStatus(StatusEnum.WRONG);
		tipNumber = 0;
	}

	public Club() {
		setStatus(StatusEnum.WRONG);
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String resName) {
		this.badge = resName;
	}
	
	public String getMainName() {
		return name;
	}

	public void setMainName(String name) {
		this.name = name;
	}

	public Collection<Name> getNames() {
		return names;
	}

	public void setNames(Collection<Name> names) {
		this.names = names;
	}

	@Override
	public int compareTo(Club another) {
		return getMainName().compareTo(another.getMainName());
	}
	
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public boolean isDiscovered() {
		return (this.status == StatusEnum.ALMOST_RIGHT || this.status == StatusEnum.RIGHT) ;
	}

	public int getAttemptsNumber() {
		return attemptsNumber;
	}

	public void setAttemptsNumber(int attemptsNumber) {
		this.attemptsNumber = attemptsNumber;
	}

	public Tip getNextTip(){
		Tip tip = null;
		int number = isDiscovered() ? getTips().size() : tipNumber;
		if (number < getTips().size()) {
			tip = (Tip) getTips().toArray()[tipNumber];
			tipNumber++;
		}
		return tip;
	}
	public List<Tip> getOldTips(){
		List<Tip> l = new ArrayList<Tip>();
		int size = isDiscovered() ? getTips().size() : tipNumber;
		for (int i = 0; i < size; i++) {
			l.add((Tip) getTips().toArray()[i]);
		}
		return l;
	}
	public Integer getTipsUsed(){
		return tipNumber;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Collection<Tip> getTips() {
		return tips;
	}

	public void setTips(Collection<Tip> tips) {
		this.tips = tips;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
