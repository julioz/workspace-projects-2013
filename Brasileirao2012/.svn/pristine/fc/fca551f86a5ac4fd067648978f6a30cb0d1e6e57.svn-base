package br.com.zynger.brasileirao2012.comparator;

import java.util.Comparator;

import br.com.zynger.brasileirao2012.model.Club;

public class PointsComparator implements Comparator<Club> {
	@Override
	public int compare(Club c1, Club c2) {
		if(c1.getPoints() < c2.getPoints()) return 1;
		else if(c1.getPoints() > c2.getPoints()) return -1;
		else if(c1.getPoints() == c2.getPoints()){
			if(c1.getPosition() < c2.getPosition()) return -1;
			else if(c1.getPosition() > c2.getPosition()) return 1;
			else return 0;
		}
		return 1;
	}
}