package br.com.zynger.brasileirao2012.comparator;

import java.util.Comparator;

import br.com.zynger.brasileirao2012.model.Striker;

public class GoalsComparator implements Comparator<Striker> {
	@Override
	public int compare(Striker s1, Striker s2) {
		if(s1.getGoals() < s2.getGoals()) return 1;
		else if(s1.getGoals() > s2.getGoals()) return -1;
		else if(s1.getGoals() == s2.getGoals()){
			return s1.getName().compareTo(s2.getName());
		}
		return 1;
	}
}
