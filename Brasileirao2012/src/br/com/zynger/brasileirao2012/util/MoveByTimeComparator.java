package br.com.zynger.brasileirao2012.util;

import java.util.Comparator;

import br.com.zynger.brasileirao2012.model.Move;

public class MoveByTimeComparator implements Comparator<Move> {
	@Override
	public int compare(Move lhs, Move rhs) {
		int comparison = lhs.getPeriodoId().compareTo(rhs.getPeriodoId());
		if(comparison == 0){
			try{				
				return lhs.getMomento().compareTo(rhs.getMomento());
			}catch(NullPointerException npe){
				return 0;
			}
		}
		return comparison;
	}

}