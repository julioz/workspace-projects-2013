package br.com.zynger.brasileirao2012.enums;

import br.com.zynger.brasileirao2012.news.GloboParser;
import br.com.zynger.brasileirao2012.news.GoogleParser;
import br.com.zynger.brasileirao2012.news.NetFLUParser;
import br.com.zynger.brasileirao2012.news.ParanautasParser;
import br.com.zynger.brasileirao2012.news.RSSParser;
import br.com.zynger.brasileirao2012.news.UOLParser;

public enum NewsDomain {
	GLOBOESPORTE, UOL, GOOGLE, NETFLU, PARANAUTAS;
	
	public RSSParser getParser(){
		if(this == GLOBOESPORTE) return new GloboParser();
		else if(this == UOL) return new UOLParser();
		else if(this == GOOGLE) return new GoogleParser();
		else if(this == NETFLU) return new NetFLUParser();
		else if(this == PARANAUTAS) return new ParanautasParser();
		else throw new RuntimeException("There is no parser for news' source " + toString());
	}
}
