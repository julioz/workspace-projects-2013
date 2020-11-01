package br.com.zynger.brasileirao2012.util;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.util.Log;
import br.com.zynger.brasileirao2012.enums.Division;
import br.com.zynger.brasileirao2012.enums.NewsDomain;
import br.com.zynger.brasileirao2012.model.Club;
import br.com.zynger.brasileirao2012.model.NewsSource;

public class WebDatabaseMapper {

	public static final Integer SOURCE_RANKING = 1;
	public final static Integer SOURCE_MATCHES = 2;
	public static final Integer SOURCE_MOVETOMOVE = 3;
	public static final Integer SOURCE_REALTIME = 4;
	public static final Integer SOURCE_STRIKERS = 5;
	
	private TreeMap<String, Club> clubs;
	
	private TreeMap<String, Club> nameMap;
	private TreeMap<String, String> mtmMap;
	
	private Integer source;
	
	public WebDatabaseMapper(TreeMap<String, Club> clubs, Integer source) {
		this.nameMap = new TreeMap<String, Club>();
		this.mtmMap = new TreeMap<String, String>();
		this.source = source;
		this.clubs = clubs;
	}
	
	public Club getClubByName(String clubName){
		Club club = nameMap.get(clubName);
		if(null != club) return club;
		
		if(source == SOURCE_RANKING || source == SOURCE_STRIKERS) populateDatabaseRanking();
		else if(source == SOURCE_MATCHES) populateDatabaseMatches();
		else if(source == SOURCE_STRIKERS) populateDatabaseStrikers();
		else if(source == SOURCE_REALTIME) populateDatabaseRealtime();
		else if(source == SOURCE_MOVETOMOVE) return null;
		
		club = nameMap.get(clubName);
		if(null != club) return club;

		try{
			Club c = clubs.get(clubName.substring(0, 3).toUpperCase(Locale.getDefault()));
			if(null != c){
				nameMap.put(clubName, c);
				return c;
			}
		}catch(StringIndexOutOfBoundsException e){
			Log.e(Constants.TAG, clubName + " - " + e.toString());
		}
		
		Log.d(Constants.TAG, "Não achei correspondência: " + clubName);
		return club;
	}
	
	public String getStringForMoveToMove(String clubName){
		if(source != SOURCE_MOVETOMOVE) return null;
		populateDatabaseMoveToMove();
		
		String name = mtmMap.get(clubName.toLowerCase(Locale.getDefault()));
		return name == null ? clubName : name;
	}

	private void populateDatabaseRealtime() {		
		addToMapIfEqual("são paulo", "SPO");
		addToMapIfEqual("náutico", "NAU");
		addToMapIfEqual("atlético-mg", "CAM");
		addToMapIfEqual("grêmio", "GRE");
		addToMapIfEqual("atlético-go", "ATG");
		addToMapIfEqual("atlético-pr", "CAP");
		addToMapIfEqual("coritiba", "CFC");
		addToMapIfEqual("sport", "SPT");
		
		addToMapIfEqual("asa", "ASA");
		addToMapIfEqual("boa", "BOA");
		addToMapIfEqual("são caetano", "SCA");
		addToMapIfEqual("américa-mg", "AMG");
		addToMapIfEqual("américa-rn", "ARN");
		addToMapIfEqual("guaratinguetá", "GTA");
		addToMapIfEqual("grêmio barueri", "BAR");
		addToMapIfEqual("paysandu-pa", "PAY");
	}
	
	private void populateDatabaseStrikers() {
		addToMapIfEqual("sao-paulo", "SPO");
		addToMapIfEqual("atletico-pr", "CAP");
		addToMapIfEqual("coritiba", "CFC");
		addToMapIfEqual("atletico-mg", "CAM");
		
		addToMapIfEqual("america-rn", "ARN");
		addToMapIfEqual("atletico-go", "ATG");
		addToMapIfEqual("sao-caetano", "SCA");
		addToMapIfEqual("guaratingueta", "GTA");
		addToMapIfEqual("america-mg", "AMG");
		addToMapIfEqual("sport", "SPT");
	}

	private void populateDatabaseRanking() {
		addToMapIfEqual("São Paulo", "SPO");
		addToMapIfEqual("Náutico", "NAU");
		addToMapIfEqual("Atlético-MG", "CAM");
		addToMapIfEqual("Grêmio", "GRE");
		addToMapIfEqual("Atlético-GO", "ATG");
		addToMapIfEqual("Atlético-PR", "CAP");
		addToMapIfEqual("São Caetano", "SCA");
		addToMapIfEqual("América-MG", "AMG");
		addToMapIfEqual("América-RN", "ARN");
		addToMapIfEqual("Guaratinguetá", "GTA");
		addToMapIfEqual("Grêmio Barueri", "BAR");
		addToMapIfEqual("Paysandu-PA", "PAY");
		addToMapIfEqual("Coritiba", "CFC");
		addToMapIfEqual("Sport", "SPT");
	}

	private void populateDatabaseMoveToMove() {
		addToMoveToMoveMap("atlético-go", "Atletico-GO");
		addToMoveToMoveMap("são paulo", "Sao Paulo");
		addToMoveToMoveMap("náutico", "Nautico");
		addToMoveToMoveMap("atlético-mg", "Atletico-MG");
		addToMoveToMoveMap("grêmio", "Gremio");
		addToMoveToMoveMap("grêmio barueri", "Barueri");
		addToMoveToMoveMap("américa-mg", "America-MG");
		addToMoveToMoveMap("goiás", "Goias");
		addToMoveToMoveMap("ceará", "Ceara");
		addToMoveToMoveMap("américa-rn", "America-RN");
		addToMoveToMoveMap("atlético-pr", "Atletico-PR");
		addToMoveToMoveMap("criciúma", "Criciuma");
		addToMoveToMoveMap("boa esporte", "Boa");
		addToMoveToMoveMap("guaratinguetá", "Guaratingueta");
		addToMoveToMoveMap("são caetano", "Sao Caetano");
		addToMoveToMoveMap("paraná", "Parana");
		addToMoveToMoveMap("vitória", "Vitoria");
		addToMoveToMoveMap("avaí", "Avai");
		addToMoveToMoveMap("asa", "Asa-arapiraca");
	}

	private void addToMoveToMoveMap(String expected, String correctValue) {
		mtmMap.put(expected, correctValue);
	}

	private void populateDatabaseMatches() {
		addToMapIfEqual("São Paulo", "SPO");
		addToMapIfEqual("Náutico", "NAU");
		addToMapIfEqual("Atlético-MG", "CAM");
		addToMapIfEqual("Grêmio", "GRE");
		addToMapIfEqual("Atlético-GO", "ATG");
		addToMapIfEqual("Atlético-PR", "CAP");
		addToMapIfEqual("Coritiba", "CFC");
		
		addToMapIfEqual("São Caetano", "SCA");
		addToMapIfEqual("Guaratinguetá", "GTA");
		addToMapIfEqual("América-MG", "AMG");
		addToMapIfEqual("Sport", "SPT");
		addToMapIfEqual("América-RN", "ARN");
	}

	private void addToMapIfEqual(String expected, String correctValue){
		Club club = clubs.get(correctValue);
		nameMap.put(expected, club);
	}
	
	@SuppressLint("DefaultLocale")
	public ArrayList<NewsSource> getNewsUrls(Club club){
		ArrayList<NewsSource> al = new ArrayList<NewsSource>();
		
		if(club.getDivision() != Division.FIRSTDIVISION
				&& club.getDivision() != Division.SECONDDIVISION){
			al.add(new NewsSource("Terceira Divisão",
				"http://globoesporte.globo.com/servico/semantica/editorias/"
				+ "plantao/futebol/brasileirao-serie-c/feed.rss",
				NewsSource.ENCODING_UTF8, NewsDomain.GLOBOESPORTE));
			return al;
		}
		
		if(club.getAcronym().equals("FLU")){
			NewsSource netfluSource = new NewsSource("NetFLU", "http://netflu.com.br/feed/",
					NewsSource.ENCODING_UTF8, NewsDomain.NETFLU);
			al.add(netfluSource);
		}else if(club.getAcronym().equals("PAR")){
			NewsSource paranautasSource = new NewsSource("Paranautas", "http://paranautas.com/index.php?pg=todas_noticias",
					NewsSource.ENCODING_ISO8859, NewsDomain.PARANAUTAS);
			al.add(paranautasSource);
		}
		
		String name = club.getName().toLowerCase().replaceAll("é", "e").replaceAll("ê", "e");
		name = name.replaceAll("ó", "o").replaceAll("í", "i").replaceAll("ú", "u").replaceAll("á", "a");
		name = name.replaceAll("ã", "a").replaceAll(" ", "-");		
		
		String state = new String();
		if(club.getAcronym().equals("CHA")) state = "/sc";
		else if(club.getAcronym().equals("PAY")) state = "/pa";
		
		String model = "dynamo";
		if(club.getAcronym().equals("PAY")) model = "servico";
		
		NewsSource globoSource = new NewsSource("Globo", "http://globoesporte.globo.com/"
		+ model + "/semantica/editorias/plantao" + state + "/futebol/times/"+name+"/feed.rss",
				NewsSource.ENCODING_UTF8, NewsDomain.GLOBOESPORTE);
		al.add(globoSource);
		name = name.replaceAll("-", "");
		
		if(name.toLowerCase().equals("asa")) name = "asa-al";
		else if(name.toLowerCase().equals("icasa")) name = "icasa-ce";
		
		NewsSource uolSource = new NewsSource("UOL", "http://rss.esporte.uol.com.br/futebol/clubes/"+name+".xml",
				NewsSource.ENCODING_ISO8859, NewsDomain.UOL);
		al.add(uolSource);
		
		NewsSource googleSource = new NewsSource("Google", "http://news.google.com.br/news?q="
		+ name + "&um=1&hl=pt-BR&bav=on.2,or.r_gc.r_pw.r_cp.,cf.osb&biw=1680&bih=925&ie=UTF-8&output=rss",
				NewsSource.ENCODING_UTF8, NewsDomain.GOOGLE);
		al.add(googleSource);
		
		return al;
	}
}