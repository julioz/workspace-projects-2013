package br.com.zynger.brasileirao2012.movetomove;

import java.util.ArrayList;
import java.util.TreeMap;

import org.json.JSONArray;

import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.model.Move;

public interface IMoveToMoveFollower {
	
	Match getMatchFromJsonArray(JSONArray json, TreeMap<String, Club> clubs);
	
	void onMovesParsed(ArrayList<Move> moves);
	
	Match getMatch();

}
