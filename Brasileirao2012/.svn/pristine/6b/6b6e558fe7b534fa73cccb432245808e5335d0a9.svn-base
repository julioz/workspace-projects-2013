package br.com.zynger.brasileirao2012.rest;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.Match;
import br.com.zynger.brasileirao2012.model.Move;
import br.com.zynger.brasileirao2012.model.Move.Video;
import br.com.zynger.brasileirao2012.util.Constants;
import br.com.zynger.brasileirao2012.util.WebDatabaseMapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MoveToMoveREST {

	private static final String MOVETOMOVE_VIDEOS_PREFIX = "http://api.globovideos.com/videos/";
	private static final String MOVETOMOVE_VIDEOS_SUFFIX = "/playlist";
	private static final String MOVETOMOVE_URL_PREFIX = "http://globoesporte.globo.com/temporeal/futebol/";
	private static final String MOVETOMOVE_URL_SUFFIX = "/mensagens.json";
	
	private final String FORMAT_PATTERN = "dd-MM-yyyy";

	private WebDatabaseMapper webDatabaseMapper;
	private SimpleDateFormat sdf;
	private ObjectMapper mapper;
	private JsonFactory factory;
	
	public MoveToMoveREST(TreeMap<String, Club> clubs) {
		this.webDatabaseMapper = new WebDatabaseMapper(clubs, WebDatabaseMapper.SOURCE_MOVETOMOVE);
		this.sdf = new SimpleDateFormat(FORMAT_PATTERN, Locale.getDefault());
		mapper = new ObjectMapper();
		factory = new JsonFactory();
	}
	
	public ArrayList<Move> getMoveToMove(Context context, Match match)
			throws JsonParseException, IOException {
		String url = getRealTimeMatchUrl(match);
		Log.e(Constants.TAG, url);
		ArrayList<Move> moves = new ArrayList<Move>();

		JsonParser jsonParser = factory.createParser(new URL(url));
		jsonParser.nextToken();
		while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
			Move move = mapper.readValue(jsonParser, Move.class);
			
			if (move.getPeriodoId() != null) {
				move.setTexto(move.getTexto().replaceAll("<p>", "")
						.replaceAll("</p>", ""));

				if (move.getOperacao().equals(Move.Operacao.ALTERACAO)) {
					modifyMove(moves, move);
				} else {
					moves.add(0, move);
				}
				
				//FIXME problema com API de videos
				move.setVideoId(null);
			}
		}
		jsonParser.close();
		return moves;
	}
	
	private void modifyMove(ArrayList<Move> moves, Move sourceMove) {
		Move moveToRemove = null;
		for (Move move : moves) {
			if(move.getId().intValue() == sourceMove.getId().intValue()){
				moveToRemove = move;
				break;
			}
		}
		
		if(moveToRemove != null){
			moves.remove(moveToRemove);
			moves.add(sourceMove);
		}
	}

	@SuppressLint("DefaultLocale")
	private String getRealTimeMatchUrl(Match m){
		String home = webDatabaseMapper.getStringForMoveToMove(m.getHome().getName());
		home = home.toLowerCase();
		home = home.replace(" ", "-");
		String away = webDatabaseMapper.getStringForMoveToMove(m.getAway().getName());
		away = away.toLowerCase();
		away = away.replace(" ", "-");
		
		return MOVETOMOVE_URL_PREFIX + sdf.format(m.getDate().getTime()) + "/" + home + "-" + away + MOVETOMOVE_URL_SUFFIX;
	}
	
	public String getMoveVideoUrl(Integer videoId) throws JsonParseException, IOException {
		String url = MOVETOMOVE_VIDEOS_PREFIX + videoId + MOVETOMOVE_VIDEOS_SUFFIX;
		
		JsonParser jsonParser = factory.createParser(new URL(url));
		Video video = mapper.readValue(jsonParser, Video.class);
		return video.getUrl();
	}
}
