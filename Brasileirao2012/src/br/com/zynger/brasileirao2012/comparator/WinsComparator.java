package br.com.zynger.brasileirao2012.comparator;

import java.util.Comparator;

import br.com.zynger.brasileirao2012.model.Club;

 public class WinsComparator implements Comparator<Club> {
	@Override
	public int compare(Club c1, Club c2) {
		if(c1.getWin() < c2.getWin()) return 1;
		else if(c1.getWin() > c2.getWin()) return -1;
		else return 0;
	}
}