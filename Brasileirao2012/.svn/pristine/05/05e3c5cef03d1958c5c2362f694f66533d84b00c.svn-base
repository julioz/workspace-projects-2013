package br.com.zynger.brasileirao2012.comparator;

import java.util.Comparator;

import br.com.zynger.brasileirao2012.model.Striker;

public class TeamComparator implements Comparator<Striker> {
	private FullNameComparator comparator;
	
	public TeamComparator() {
		comparator = new FullNameComparator();
	}
	
	@Override
	public int compare(Striker s1, Striker s2) {
		return comparator.compare(s1.getClub(), s2.getClub());
	}
}
