package br.com.zynger.guesstheclub.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import android.content.Context;
import br.com.zynger.guesstheclub.model.Achievement;

public class AchievementGenerator {
	
	private Context context;
	
	public static List<Achievement> getAchievement(Context context) {
		String xml = Raw.openRaw(context, "achievements");
		List<Achievement> achievements = parserXML(context, xml);
		return achievements;
	}

	private static List<Achievement> parserXML(Context context, String xml){
		List<Achievement> achievements = new ArrayList<Achievement>();
		Element root = XMLUtils.getRoot(xml, "UTF-8");
		List<Node> nodeAchieviments = XMLUtils.getChildren(root, "achievement");
		for (Node node : nodeAchieviments) {
			String name = XMLUtils.getText(node, "name");
			Integer points = new Integer(XMLUtils.getText(node, "points"));
			String description = XMLUtils.getText(node, "description");
			String typeEnum = XMLUtils.getText(node, "type");
			Integer constantPhase = new Integer(XMLUtils.getText(node, "phase"));
			
			AchievementTypeEnum type = AchievementTypeEnum.valueOf(typeEnum);
			
			Achievement a = new Achievement(name, points, description, type);
			a.setConstantPhase(constantPhase);
			achievements.add(a);
		}
		return achievements;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}	
}
