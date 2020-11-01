package br.com.zynger.brasileirao2012.comparator;

import java.util.Comparator;

import br.com.zynger.brasileirao2012.model.Club;

public class FansComparator implements Comparator<Club> {
	@Override
	public int compare(Club c1, Club c2) {
		return c2.getFans() - c1.getFans();
	}
}