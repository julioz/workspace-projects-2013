package br.com.zynger.brasileirao2012.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import br.com.zynger.brasileirao2012.model.Striker;

public class PlayerNameComparator implements Comparator<Striker> {
	private Collator collator;
	
	public PlayerNameComparator() {
		collator = Collator.getInstance(Locale.getDefault());
        collator.setStrength(Collator.PRIMARY);
        collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
	}
	
	@Override
	public int compare(Striker s1, Striker s2) {
		return collator.compare(s1.getName(), s2.getName());
	}
}
