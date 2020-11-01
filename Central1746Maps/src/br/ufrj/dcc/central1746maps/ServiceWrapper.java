package br.ufrj.dcc.central1746maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import br.ufrj.dcc.central1746maps.Call.State;
import br.ufrj.dcc.central1746maps.Call.Type;

public class ServiceWrapper {

	private final static String URL = "http://blink-app.herokuapp.com/ws/";

	@SuppressLint("SimpleDateFormat")
	public static List<Call> getCalls() throws IOException, JSONException {
		JSONArray array = readJsonFromUrl(URL);
		List<Call> calls = new LinkedList<Call>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (int index = 0; index < array.length(); index++) {
			JSONObject jsonObj = array.getJSONObject(index);
			Call call = new Call();
			
			call.setId(jsonObj.getInt("id"));
			call.setNeighborhood(jsonObj.getString("bairro").trim());
			if (!jsonObj.isNull("num")) {
				call.setNumber(jsonObj.getString("num").trim());
			}
			call.setAddressPrefix(jsonObj.getString("rua_p").trim());
			call.setAddress(jsonObj.getString("rua").trim());
			
			String categoryString = jsonObj.getString("categoria");
			if (categoryString.equals("Poda de Árvores")) {
				call.setType(Type.PODA_DE_ÁRVORES);
			} else if (categoryString.equals("Iluminação Pública")) {
				call.setType(Type.ILUMINAÇÃO_PÚBLICA);
			} else if (categoryString.equals("Estacionamento Irregular")) {
				call.setType(Type.ESTACIONAMENTO_IRREGULAR);
			} else if (categoryString.equals("Conservação de Vias")) {
				call.setType(Type.CONSERVAÇÃO_DE_VIAS);
			}
			
			String stateString = jsonObj.getString("state").toLowerCase(Locale.getDefault());
			if (stateString.equals("fechado")) {
				call.setState(State.FECHADO);
			} else if (stateString.equals("aberto")) {
				call.setState(State.ABERTO);
			} else if (stateString.equals("atrasado")) {
				call.setState(State.ATRASADO);
			}
			
			GregorianCalendar closingDate, openingDate, dueDate;
			
			closingDate = new GregorianCalendar();
			openingDate = new GregorianCalendar();
			dueDate = new GregorianCalendar();
			
			try {
				closingDate.setTime(sdf.parse(jsonObj.getString("dt_fechado")));
				openingDate.setTime(sdf.parse(jsonObj.getString("dt_aberto")));
				dueDate.setTime(sdf.parse(jsonObj.getString("prazo")));
				
				call.setClosingDate(closingDate);
				call.setDueDate(dueDate);
				call.setOpeningDate(openingDate);

				calls.add(call);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		return calls;
	}

	public static JSONArray readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
