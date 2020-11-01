package br.com.zynger.libertadores.util;

import java.util.Comparator;

import br.com.zynger.libertadores.model.Club;

public class ClubComparatorByPoints implements Comparator<Club> {

	@Override
	public int compare(Club lhs, Club rhs) {
		int comparison = lhs.getPoints() - rhs.getPoints();
		if(comparison == 0){
			comparison = lhs.getBalance() - rhs.getBalance();
			if(comparison == 0){
				comparison = lhs.getGoalsPro() - rhs.getGoalsPro();
				if(comparison == 0){					
					return lhs.getName().compareTo(rhs.getName());
				}
			}
		}
		return -1*comparison;
	}
}