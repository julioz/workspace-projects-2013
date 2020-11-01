package br.com.zynger.brasileirao2012.xml;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.content.Context;
import br.com.zynger.brasileirao2012.model.Stadium;
import br.com.zynger.brasileirao2012.util.Raw;

public class StadiumsParser {

	private final String STADIUMS_XML = "stadiums";

	public ArrayList<Stadium> getStadiums(Context context){
		String xml = Raw.openRaw(context, STADIUMS_XML);
		ArrayList<Stadium> stadiums = parseXML(xml);
		return stadiums;
	}
	
	private static ArrayList<Stadium> parseXML(String xml){
		ArrayList<Stadium> stadiums = new ArrayList<Stadium>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		List<Node> nodeStadiums = XMLUtils.getChildren(root, "stadium");
		for (Node node : nodeStadiums) {
			Stadium s = new Stadium();
			
			s.setLat(Double.valueOf(XMLUtils.getText(node, "lat")));
			s.setLng(Double.valueOf(XMLUtils.getText(node, "lng")));
			s.setName(XMLUtils.getText(node, "name"));
			s.setNick(XMLUtils.getText(node, "nick"));
			s.setCity(XMLUtils.getText(node, "city"));
			s.setImgUrl(XMLUtils.getText(node, "imgurl"));
			s.setDescription(XMLUtils.getText(node, "description").replaceAll("###", "\n"));
			
			Node foundation = XMLUtils.getChild(node, "foundation");
			String strDate = XMLUtils.getText(foundation, "date");
			String strMonth = XMLUtils.getText(foundation, "month");
			String strYear = XMLUtils.getText(foundation, "year");
			GregorianCalendar gregCal = new GregorianCalendar(Integer.valueOf(strYear),
					Integer.valueOf(strMonth), Integer.valueOf(strDate));
			s.setFoundation(gregCal);
			
			s.setCapacity(Integer.valueOf(XMLUtils.getText(node, "capacity")));
			s.setWidth(Integer.valueOf(XMLUtils.getText(node, "width")));
			s.setHeight(Integer.valueOf(XMLUtils.getText(node, "height")));
			
			stadiums.add(s);
		}
		return stadiums;
	}
}
