package br.com.zynger.brasileirao2012.service;

import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import br.com.zynger.brasileirao2012.asynctasks.GetUpdatedRealTimeMatchTask;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.RealTimeMatch;

public class UpdateDataTicker implements Runnable {
	private static final long MINUTE = 60 * 1000L;
	private static final long HOUR = 60 * MINUTE;
	private static final long INTERVAL = 2 * MINUTE;
	
	private final RealTimeMatchService service;
	private final TreeMap<String, Club> clubs;
	
	public UpdateDataTicker(RealTimeMatchService service, TreeMap<String, Club> clubs) {
		this.service = service;
		this.clubs = clubs;
	}
	
	public void run(){
		if((System.currentTimeMillis() - service.getStartedTime()) <= 2 * HOUR){
			RealTimeMatch realTimeMatch = getUpdatedRealTimeMatch();
			if(realTimeMatch != null && wasMatchUpdated(realTimeMatch)){
				service.setRealTimeMatch(realTimeMatch);
			}
		}else service.stopSelf();
		
		service.scheduleNextRun(getInterval());
	}

	private boolean wasMatchUpdated(RealTimeMatch updatedMatch) {
		if(updatedMatch.isOnGoingMatch()){
			if(service.getRealTimeMatch().hasMatchStarted(updatedMatch)){
				service.showStartMatchNotification(updatedMatch);
				return true;
			}else if(service.getRealTimeMatch().hasScoreChanged(updatedMatch)){
				service.showGoalNotification(updatedMatch);
				return true;
			}
		}else service.matchHasEnded();
		
		return false;
	}
	
	private RealTimeMatch getUpdatedRealTimeMatch(){
		try {
			return new GetUpdatedRealTimeMatchTask(clubs, service.getRealTimeMatch()).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	public long getInterval() {
		return INTERVAL;
	}
}