package br.com.zynger.guesstheclub.util;

import java.util.ArrayList;
import java.util.List;

import br.com.zynger.guesstheclub.model.Achievement;
import br.com.zynger.guesstheclub.model.Phase;

public class EventBuffer {
	private List<Achievement> achievements;
	private EventBuffer instance;
	
	public EventBuffer(){
		achievements = new ArrayList<Achievement>();
	}
	
	public EventBuffer getInstance(){
		if (instance == null) {
			instance = new EventBuffer();
		}
		return instance;
	}
	
	public Achievement sendEvent(AchievementTypeEnum type, Phase phase){
		for (Achievement a : achievements) {
			if (a.getType() == type && a.getPhase() == phase) {
				a.setDone(true);
				return a;
			}
		}
		return null;
	}
}
