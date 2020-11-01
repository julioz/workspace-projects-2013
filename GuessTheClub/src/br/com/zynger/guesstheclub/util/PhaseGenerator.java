package br.com.zynger.guesstheclub.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import br.com.zynger.guesstheclub.model.Club;
import br.com.zynger.guesstheclub.model.Name;
import br.com.zynger.guesstheclub.model.Tip;

public class PhaseGenerator {
	
	private Context context;
	
	public static List<Club> getClubs(Context context, int tipo) {
		String xml = Raw.openRaw(context, "phase"+tipo);
		List<Club> clubs = parserXML(context, xml);
		return clubs;
	}

	private static List<Club> parserXML(Context context, String xml){
		List<Club> clubs = new ArrayList<Club>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		List<Node> nodeClubs = XMLUtils.getChildren(root, "club");
		for (Node node : nodeClubs) {
			Club c = new Club();
			String resName = XMLUtils.getText(node, "nm_bdg");
			c.setBadge(resName);
			ArrayList<Name> al_names = new ArrayList<Name>();
			String str_mainName = XMLUtils.getText(node, "nm_club");
			Name mainName = new Name();
			mainName.setText(str_mainName);
			mainName.setMain(true);
			c.setMainName(str_mainName);
			al_names.add(mainName);
			Node names = XMLUtils.getChild(node, "alt_names");
			if(names != null){
				for (Node nodeName : XMLUtils.getChildren(names, "name")) {
					Name name = new Name();
					name.setClub(c);
					name.setMain(false);
					name.setText(nodeName.getTextContent());
					al_names.add(name);
				}
			}
			c.setNames(al_names);
			c.setTips(new ArrayList<Tip>());
			for (int i = 1; i <= 3; i++) {
				try{	
					int hintIdentifier = ResourceManager.getInstance().getResourceFromIdentifier(context, ResourceManager.PATH_STRING, "hint_" + c.getBadge() + "_" + i);
					String tipText = context.getResources().getString(hintIdentifier);
					
					Tip tip = new Tip();
					tip.setClub(c);
					tip.setText(tipText);
					c.getTips().add(tip);
				}catch(NotFoundException nfe) {}
			}
			
			clubs.add(c);
		}
		return clubs;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
