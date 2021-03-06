package br.com.zynger.influ.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.zynger.influ.RankingCariocaTabActivity;
import br.com.zynger.influ.model.FutureGame;
import br.com.zynger.influ.model.StatClub;
import br.com.zynger.influ.model.StatPlayer;

public abstract class HTMLManager {
	
	public static ArrayList<StatPlayer> getPlayerStats() throws IOException {
		Document document = Jsoup.connect("http://br.esportes.yahoo.com/futebol/fluminense/").get();
		Elements div = document.getElementsByClass("yui-dt-liner");
		
		ArrayList<StatPlayer> al = new ArrayList<StatPlayer>();
		int c = 0, intern = 0;
		boolean goalk = false;
		StatPlayer p = new StatPlayer();
		for (Element element : div) {
			if(element.text().toString().equals("Araujo")) c++;
			if(c==2){
				if(element.text().toString().equals("Diego Cavalieri")) goalk = true;
				if(intern == 0){
					p.setName(element.text().toString());
					intern++;
				}else if(intern == 1){
					p.setPlayed(Integer.valueOf(element.text().toString()));
					intern++;
				}else if(intern == 2 && !goalk){
					intern++;
					p.setGoals(Integer.valueOf(element.text().toString()));
				}else if((intern == 3 && !goalk) || (intern == 2 && goalk)){
					p.setyCards(Integer.valueOf(element.text().toString()));
					intern++;
				}else if((intern == 4 && !goalk) || (intern == 3 && goalk)){
					p.setrCards(Integer.valueOf(element.text().toString()));
					if(goalk){
						p.setGoals(0);
						p.setGoalkeeper(true);
					}
					al.add(p);
					p = new StatPlayer();
					intern = 0;
				}
			}
		}
		
		return al;
	}
	
	public static ArrayList<String> getTicketsInfo() throws IOException {
		Document document = Jsoup.connect("http://www.fluminense.com.br/site/futebol/venda-de-ingressos/").get();
		Elements div = document.getElementsByClass("post-content");
		
		ArrayList<String> al = new ArrayList<String>();
		String html = div.outerHtml();
		html = html.replaceAll("<strong>", "<b>");
		html = html.replaceAll("</strong>", "</b>");
		if(html.contains("<h1>Venda de ingressos</h1>")) html = html.replace("<h1>Venda de ingressos</h1>", "");
		al.add(html);
		
		return al;
	}
	
	public static TreeMap<Integer, StatClub> getRankingBrasileirao() throws IOException{
		TreeMap<Integer, StatClub> clubs = new TreeMap<Integer, StatClub>();
		
		Document document = Jsoup.connect("http://esporte.uol.com.br/futebol/campeonatos/brasileiro/2012/serie-a/classificacao/classificacao.htm").get();
		Element table = document.getElementById("classificationTable");
		Elements tablerows = table.getElementsByTag("tr");
		tablerows.remove(0); //Headers da tabela

		for (Element e : tablerows) {
			StatClub sc = new StatClub();
			
			Elements tabledata = e.getElementsByTag("td");
			
			for (Element element : tabledata) {
				if(element.className().equals("tc-team")){
					if(element.text().charAt(1) == ' ') sc.setPosition(Integer.parseInt(element.text().substring(0, 1)));
					else sc.setPosition(Integer.parseInt(element.text().substring(0, 2)));
					Elements els = element.getElementsByTag("a");
					if(els.size()>0) sc.setName(els.get(0).text());
					else{
						els = element.getElementsByTag("p");
						if(els.size()>0) sc.setName(els.get(0).text());
					}
				}
				else if(element.className().equals("tc-pg")) sc.setPoints(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-j")) sc.setPlayed(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-v")) sc.setWin(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-e")) sc.setDraw(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-d")) sc.setLose(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-gp")) sc.setGoalsPro(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-gc")) sc.setGoalsAgainst(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-sg")) sc.setBalance(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-pc")) sc.setAproveit(Integer.parseInt(element.text()));
			}
			
			while(clubs.containsKey(sc.getPosition())) sc.setPosition(sc.getPosition()+1);
			clubs.put(sc.getPosition(), sc);
		}
		
		return clubs;
	}
	
	/*public static TreeMap<Integer, StatClub> getRankingBrasileirao() throws IOException{
		Document document = Jsoup.connect("http://br.esportes.yahoo.com/futebol/campeonato-brasileiro/estatisticas/").timeout(7*1000).get();
		Elements div = document.getElementsByClass("content");
		
		
		TreeMap<Integer, StatClub> tm = new TreeMap<Integer, StatClub>();
		ArrayList<String> s = new ArrayList<String>();
		for (Element element : div) {
			Elements all = element.getAllElements();

			for (Element el : all) {
				if(el.tagName().equals("tr")) s.add(el.text());
			}
		}
		s.remove(0); //header da tabela
		
		for (String string : s) {
			StatClub sc = new StatClub();
			
			int esp = string.indexOf(" ", 2);
			if((!Character.isDigit(string.toLowerCase().charAt(esp-1)) && !Character.isDigit(string.toLowerCase().charAt(esp+1))))
				string = string.replace(string.substring(2), string.substring(2).replaceFirst(" ", "#"));
			
			String[] vet = string.split(" ");
			sc.setPosition(Integer.valueOf(vet[0]));
			
			if(vet[1].contains("#")) vet[1] = vet[1].replace("#", " ");
			
			sc.setName(vet[1]);
			sc.setPoints(Integer.valueOf(vet[2]));
			sc.setPlayed(Integer.valueOf(vet[3]));
			sc.setWin(Integer.valueOf(vet[4]));
			sc.setDraw(Integer.valueOf(vet[5]));
			sc.setLose(Integer.valueOf(vet[6]));
			sc.setGoalsPro(Integer.valueOf(vet[7]));
			sc.setGoalsAgainst(Integer.valueOf(vet[8]));
			sc.setBalance(Integer.valueOf(vet[9]));
			sc.setyCards(Integer.valueOf(vet[10]));
			sc.setrCards(Integer.valueOf(vet[11]));
			sc.setAproveit(Float.valueOf(vet[12]));
			tm.put(sc.getPosition(), sc);
		}
		
		return tm;
	}*/
	
	public static ArrayList<ArrayList<FutureGame>> getFinals(boolean isLiberta) throws IOException{
		Pattern p = Pattern.compile("[0-9]+/[0-9]+/[0-9]+");
		
		ArrayList<ArrayList<FutureGame>> al = new ArrayList<ArrayList<FutureGame>>();
		
		if(!isLiberta){
			//TODO
			//al.add(parseFinalsCarioca(p, "http://br.esportes.yahoo.com/futebol/estadual-do-rio/;_ylt=Ak3Yi_WINk4gCHA2EbU5BY6FQ6F4;_ylu=X3oDMTJlZ2MwMDk4BG1pdANTUE9SVFMgTGVhZ3VlIFNjaGVkdWxlIEZ1dGVib2wgQ2FyaW9jYQRwb3MDOARzZWMDTWVkaWFNb2R1bGVMZWFndWVTY2hlZHVsZQ--;_ylg=X3oDMTIzazVyYm51BGludGwDYnIEbGFuZwNwdC1icgRwc3RhaWQDBHBzdGNhdANmdXRlYm9sfGNhcmlvY2EEcHQDc2VjdGlvbnMEdGVzdAM-;_ylv=3?t=8&c=1"));
			//al.add(parseFinalsCarioca(p, "http://br.esportes.yahoo.com/futebol/estadual-do-rio/;_ylt=Ak3Yi_WINk4gCHA2EbU5BY6FQ6F4;_ylu=X3oDMTJlZ2MwMDk4BG1pdANTUE9SVFMgTGVhZ3VlIFNjaGVkdWxlIEZ1dGVib2wgQ2FyaW9jYQRwb3MDOARzZWMDTWVkaWFNb2R1bGVMZWFndWVTY2hlZHVsZQ--;_ylg=X3oDMTIzazVyYm51BGludGwDYnIEbGFuZwNwdC1icgRwc3RhaWQDBHBzdGNhdANmdXRlYm9sfGNhcmlvY2EEcHQDc2VjdGlvbnMEdGVzdAM-;_ylv=3?t=9&c=1"));
			ArrayList<FutureGame> al_semi_gua = new ArrayList<FutureGame>();
			al_semi_gua.add(new FutureGame("Vasco", "Flamengo", "2 - 1", "Engenhão", 22, 3, 2012, 00, 00));
			al_semi_gua.add(new FutureGame("Botafogo", "Fluminense", "(3) 1 - 1 (4)", "Engenhão", 23, 3, 2012, 00, 00));
			al.add(al_semi_gua);
			
			ArrayList<FutureGame> al_fin_gua = new ArrayList<FutureGame>();
			al_fin_gua.add(new FutureGame("Vasco", "Fluminense", "1 - 3", "Engenhão", 26, 3, 2012, 00, 00));
			al.add(al_fin_gua);
			
			ArrayList<FutureGame> al_semi_rio = new ArrayList<FutureGame>();
			al_semi_rio.add(new FutureGame("Bangu", "Botafogo", "2 - 4", "Engenhão", 21, 4, 2012, 00, 00));
			al_semi_rio.add(new FutureGame("Flamengo", "Vasco", "2 - 3", "Engenhão", 22, 4, 2012, 00, 00));
			al.add(al_semi_rio);
			
			//al.add(parseFinalsCarioca(p, "http://br.esportes.yahoo.com/futebol/estadual-do-rio/;_ylt=Ak3Yi_WINk4gCHA2EbU5BY6FQ6F4;_ylu=X3oDMTJlZ2MwMDk4BG1pdANTUE9SVFMgTGVhZ3VlIFNjaGVkdWxlIEZ1dGVib2wgQ2FyaW9jYQRwb3MDOARzZWMDTWVkaWFNb2R1bGVMZWFndWVTY2hlZHVsZQ--;_ylg=X3oDMTIzazVyYm51BGludGwDYnIEbGFuZwNwdC1icgRwc3RhaWQDBHBzdGNhdANmdXRlYm9sfGNhcmlvY2EEcHQDc2VjdGlvbnMEdGVzdAM-;_ylv=3?t=19&c=2"));
			ArrayList<FutureGame> al_fin_rio = new ArrayList<FutureGame>();
			al_fin_rio.add(new FutureGame("Botafogo", "Vasco", "3 - 1", "Engenhão", 29, 4, 2012, 00, 00));
			al.add(al_fin_rio);
			
			ArrayList<FutureGame> al_fin_estadual = new ArrayList<FutureGame>();
			al_fin_estadual.add(new FutureGame("Fluminense", "Botafogo", "4 - 1", "Engenhão", 6, 5, 2012, 00, 00));
			al_fin_estadual.add(new FutureGame("Botafogo", "Fluminense", "0 - 1", "Engenhão", 13, 5, 2012, 00, 00));
			//al.add(parseFinalsCarioca(p, "http://br.esportes.yahoo.com/futebol/estadual-do-rio/;_ylt=Ak3Yi_WINk4gCHA2EbU5BY6FQ6F4;_ylu=X3oDMTJlZ2MwMDk4BG1pdANTUE9SVFMgTGVhZ3VlIFNjaGVkdWxlIEZ1dGVib2wgQ2FyaW9jYQRwb3MDOARzZWMDTWVkaWFNb2R1bGVMZWFndWVTY2hlZHVsZQ--;_ylg=X3oDMTIzazVyYm51BGludGwDYnIEbGFuZwNwdC1icgRwc3RhaWQDBHBzdGNhdANmdXRlYm9sfGNhcmlvY2EEcHQDc2VjdGlvbnMEdGVzdAM-;_ylv=3?t=20"));
			al.add(al_fin_estadual);
		}else{
			/*try{
				Document document = Jsoup.connect("http://df1.conmebol.com/libertadores/fixture.html").get(); //TODO
				al.add(parseFinalsLibertadores(document, 0));
				al.add(parseFinalsLibertadores(document, 1));
				al.add(parseFinalsLibertadores(document, 2));
				al.add(parseFinalsLibertadores(document, 3));
			}catch(IOException e){
				return null;
			}*/
			ArrayList<FutureGame> al_oitavas = new ArrayList<FutureGame>();
			al_oitavas.add(new FutureGame("Internacional", "Fluminense", "0 x 0", null, 25, 4+1, 2012, 21, 50));
			al_oitavas.add(new FutureGame("Fluminense", "Internacional", "2 x 1", null, 10, 5+1, 2012, 22, 00));
			al_oitavas.add(new FutureGame("Boca", "U. Española", "2 x 1", null, 2, 5+1, 2012, 19, 30));
			al_oitavas.add(new FutureGame("U. Española", "Boca", "2 x 3", null, 9, 5+1, 2012, 19, 30));
			al_oitavas.add(new FutureGame("Dep. Quito", "U. de Chile", "4 x 1", null, 3, 5+1, 2012, 21, 15));
			al_oitavas.add(new FutureGame("U. de Chile", "Dep. Quito", "6 x 0", null, 10, 5+1, 2012, 22, 00));
			al_oitavas.add(new FutureGame("Cruz Azul", "Libertad", "1 x 1", null, 1, 5+1, 2012, 23, 30));
			al_oitavas.add(new FutureGame("Libertad", "Cruz Azul", "2 x 0", null, 8, 5+1, 2012, 22, 30));
			al_oitavas.add(new FutureGame("Emelec", "Corinthians", "0 x 0", null, 2, 5+1, 2012, 21, 50));
			al_oitavas.add(new FutureGame("Corinthians", "Emelec", "3 x 0", null, 9, 5+1, 2012, 22, 00));
			al_oitavas.add(new FutureGame("Vasco", "Lanús", "2 x 1", null, 2, 5+1, 2012, 21, 50));
			al_oitavas.add(new FutureGame("Lanús", "Vasco", "2 (4) x (5) 1", null, 9, 5+1, 2012, 22, 00));
			al_oitavas.add(new FutureGame("Bolívar", "Santos", "2 x 1", null, 25, 4+1, 2012, 21, 50));
			al_oitavas.add(new FutureGame("Santos", "Bolívar", "8 x 0", null, 10, 5+1, 2012, 19, 30));
			al_oitavas.add(new FutureGame("A. Nacional", "Vélez", "0 x 1", null, 1, 5+1, 2012, 21, 15));
			al_oitavas.add(new FutureGame("Vélez", "A. Nacional", "1 x 1", null, 8, 5+1, 2012, 20, 00));
			al.add(al_oitavas);
			
			ArrayList<FutureGame> al_quartas = new ArrayList<FutureGame>();
			al_quartas.add(new FutureGame("Boca", "Fluminense", "1 x 0", null, 17, 5+1, 2012, 19, 45));
			al_quartas.add(new FutureGame("Fluminense", "Boca", "1 x 1", null, 23, 5+1, 2012, 19, 30));
			al_quartas.add(new FutureGame("Libertad", "U. de Chile", "1 x 1", null, 16, 5+1, 2012, 19, 30));
			al_quartas.add(new FutureGame("U. de Chile", "Libertad", "1 (5) x (3) 1", null, 24, 5+1, 2012, 22, 30));
			al_quartas.add(new FutureGame("Vasco", "Corinthians", "0 x 0", null, 16, 5+1, 2012, 21, 50));
			al_quartas.add(new FutureGame("Corinthians", "Vasco", "1 x 0", null, 23, 5+1, 2012, 22, 00));
			al_quartas.add(new FutureGame("Vélez", "Santos", "1 x 0", null, 17, 5+1, 2012, 22, 00));
			al_quartas.add(new FutureGame("Santos", "Vélez", "1 (4) x (2) 0", null, 24, 5+1, 2012, 20, 00));
			al.add(al_quartas);
			
			ArrayList<FutureGame> al_semi = new ArrayList<FutureGame>();
			al_semi.add(new FutureGame("Boca", "U. de Chile", "2 x 0", null, 14, 6+1, 2012, 20, 15));
			al_semi.add(new FutureGame("U. de Chile", "Boca", "0 x 0", null, 21, 6+1, 2012, 21, 15));
			al_semi.add(new FutureGame("Santos", "Corinthians", "0 x 1", null, 13, 6+1, 2012, 21, 50));
			al_semi.add(new FutureGame("Corinthians", "Corinthians", "1 x 1", null, 20, 6+1, 2012, 21, 50));
			al.add(al_semi);
			
			ArrayList<FutureGame> al_final = new ArrayList<FutureGame>();
			al_final.add(new FutureGame("Boca", "Corinthians", "1 x 1", null, 27, 6+1, 2012, 21, 50));
			al_final.add(new FutureGame("Corinthians", "Boca", "2 x 0", null, 4, 7+1, 2012, 21, 50));
			al.add(al_final);
		}
		
		return al;
	}
	
	private static ArrayList<FutureGame> parseFinalsLibertadores(Document document, int fase) throws IOException{
		ArrayList<FutureGame> al = new ArrayList<FutureGame>();
		
		Elements table = document.getElementsByClass("tabla");
		
		int start = 55, end = 62;
		switch (fase) {
		case 0:
			start = 55;
			end = 62;
			break;
		case 1:
			start = 63;
			end = 66;
			break;
		case 2:
			start = 67;
			end = 68;
			break;
		case 3:
			start = end = 69;
			break;
		default:
			break;
		}
		
		for (int i = start-1; i <= end-1; i++) {
			try{
				Element dupla = table.get(i);
				Element match_a = dupla.getElementsByClass("partido_a").get(0);
				al.add(parseMatchLibertadores(match_a));
				
				Element match_b = dupla.getElementsByClass("partido_b").get(0);
				al.add(parseMatchLibertadores(match_b));
			}catch(Exception e){
				throw new IOException();
			}
		}
		
		return al;
	}
	
	private static FutureGame parseMatchLibertadores(Element match) {
		String home = match.getElementsByClass("local").get(0).text();
		
		Element plac_1 = match.getElementsByClass("local_2").get(0);
		String plac1 = plac_1.ownText();
		Elements pen_loc = plac_1.getElementsByAttributeValue("style", "font-weight:normal");
		if(pen_loc != null && pen_loc.size() > 0) plac1 += " (" + pen_loc.text() + ")";
		
		Element plac_2 = match.getElementsByClass("visitante_2").get(0);
		String plac2 = "";
		Elements pen_vis = plac_2.getElementsByAttributeValue("style", "font-weight:normal");
		if(pen_vis != null && pen_vis.size() > 0) plac2 += "(" + pen_vis.text() + ") ";
		plac2 += plac_2.ownText();
		
		String str_score = " X ";
		if(!plac1.equals("&nbsp;") && !plac2.equals("&nbsp;")) str_score = plac1 + " X " + plac2;
		
		String away = match.getElementsByClass("visitante").get(0).text();
		
		String date = match.getElementsByClass("dia").get(0).text();
		int day = 1, month = 1, year = 2010;
		if(date.equals("A confirmar")) year = 2010;
		else{
			day = Integer.valueOf(date.substring(0, 2));
			month = Integer.valueOf(date.substring(3, 5));
			year = Integer.valueOf(date.substring(6, 10));
		}
		
		String time = match.getElementsByClass("hora").get(0).text();

		int hour = 0, minute = 58;
		if(time.length() < 5 || !time.contains(":") || time.equals("- hs")) minute = 58;
		else{
			hour = Integer.valueOf(time.substring(0, 2));
			minute = Integer.valueOf(time.substring(3, 5));
		}

		home = home.replaceAll("Equipo", "Equipe");
		home = home.replaceAll("Llave", "Chave");
		home = home.replaceAll("Ganador", "Vencedor");
		
		away = away.replaceAll("Equipo", "Equipe");
		away = away.replaceAll("Llave", "Chave");
		away = away.replaceAll("Ganador", "Vencedor");
		
		return new FutureGame(home, away, str_score, null, day, month+1, year, hour-3, minute);
	}

	private static ArrayList<FutureGame> parseFinalsCarioca(Pattern p, String url) throws IOException{
		ArrayList<FutureGame> al = new ArrayList<FutureGame>();
		
		Document document = Jsoup.connect(url).timeout(10*1000).get();
		Elements div = document.getElementsByClass("yui-content");
		
		ArrayList<Element> ul = new ArrayList<Element>();
		for (Element element : div) {
			Elements all = element.getAllElements();

			for (Element el : all) {
				if(el.tagName().equals("ul")) ul.add(el);
			}
		}
		
		String date_bkp = "";
		for (Element el : ul) {
			String home = el.getElementsByClass("team-1").get(0).text();
			String away = el.getElementsByClass("team-2").get(0).text();
			String score = el.getElementsByClass("score").get(0).text();
			String date = "";
			try{
				date = el.getElementsByClass("date").get(0).text();
				date_bkp = date;
			}catch(IndexOutOfBoundsException e){
				date = date_bkp;
			}
			Matcher m = p.matcher(date);
			if (m.find()) date = m.group();
			String timeStadium = el.getElementsByClass("status").get(0).text();
			int hour = 00, min = 00;
			try{				
				if(!timeStadium.toLowerCase().contains("final")){
					hour = Integer.valueOf(timeStadium.substring(0, 2));
					min = Integer.valueOf(timeStadium.substring(3, 5));
				}
			}catch(NumberFormatException e) {}
			String stadium = timeStadium.substring(timeStadium.indexOf(" - ") + " - ".length());

			int day = Integer.valueOf(date.substring(0, date.indexOf("/")));
			date = date.substring(date.indexOf("/")+1);
			int month = Integer.valueOf(date.substring(0, date.indexOf("/"))) + 1;
			date = date.substring(date.indexOf("/")+1);
			int year = Integer.valueOf(date);
			
			//TODO
			if(home.equals("Campeão Taça Guanabara")) home = "Fluminense";
			if(away.equals("Campeão Taça Guanabara")) away = "Fluminense";
			if(home.equals("Campeão Taça Rio")) home = "Botafogo";
			if(away.equals("Campeão Taça Rio")) away = "Botafogo";
			
			al.add(new FutureGame(home, away, score, stadium, day, month, year, hour, min));
		}
		
		return al;
	}
	
	public static TreeMap<Integer, StatClub> getRankingCarioca(int group) throws IOException{
		/*File f = new File(Environment.getExternalStorageDirectory()+"/carioca12b.htm"); //para debug, eh melhor ter o html no sd
		Document document = Jsoup.parse(f, "iso-8859-1");*/
		
		Document document = null;
		/* Taça Guanabara */
		if(group == RankingCariocaTabActivity.CARIOCA_GRUPO_B) document = Jsoup.connect("http://esporte.uol.com.br/futebol/campeonatos/estadual-do-rio/2013/classificacao/taca-guanabara/grupo-b/").get();
		else document = Jsoup.connect("http://esporte.uol.com.br/futebol/campeonatos/estadual-do-rio/2013/classificacao/taca-guanabara/grupo-a/").get();
		
		/* Taça Rio */
		/*if(group == RankingCariocaTabActivity.CARIOCA_GRUPO_B) document = Jsoup.connect("http://esporte.uol.com.br/futebol/campeonatos/estadual-do-rio/2013/classificacao/taca-rio/grupo-b/").get();
		else document = Jsoup.connect("http://esporte.uol.com.br/futebol/campeonatos/estadual-do-rio/2013/classificacao/taca-rio/grupo-a/").get();*/
				
		TreeMap<Integer, StatClub> tm = new TreeMap<Integer, StatClub>();

		Element table = document.getElementById("classificationTable");
		Elements tablerows = table.getElementsByTag("tr");
		tablerows.remove(0); //Headers da tabela

		for (Element e : tablerows) {
			StatClub sc = new StatClub();
			
			Elements tabledata = e.getElementsByTag("td");
			
			for (Element element : tabledata) {
				if(element.className().equals("tc-team")){
					sc.setPosition(Integer.parseInt(element.text().substring(0, 1)));
					Elements els = element.getElementsByTag("a");
					if(els.size()>0) sc.setName(els.get(0).text());//element.text().substring(2));
					else{
						els = element.getElementsByTag("p");
						if(els.size()>0) sc.setName(els.get(0).text());
					}
				}
				else if(element.className().equals("tc-pg")) sc.setPoints(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-j")) sc.setPlayed(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-v")) sc.setWin(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-e")) sc.setDraw(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-d")) sc.setLose(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-gp")) sc.setGoalsPro(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-gc")) sc.setGoalsAgainst(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-sg")) sc.setBalance(Integer.parseInt(element.text()));
				else if(element.className().equals("tc-pc")) sc.setAproveit(Float.parseFloat(element.text()));
			}
			
			while(tm.containsKey(sc.getPosition())){
				sc.setPosition(sc.getPosition()+1);
			}
			
			tm.put(sc.getPosition(), sc);
		}		
		
		return tm;
	}

	public static TreeMap<String, String> getInfoBarData() throws IOException{
		Document document = Jsoup.connect("http://www.fluminense.com.br/site/futebol/o-time/tabela-de-jogos/").get();
		/*File f = new File(Environment.getExternalStorageDirectory()+"/html_test.html"); //para debug, eh melhor ter o html no sd
		Document document = Jsoup.parse(f, "iso-8859-1");*/
		
		TreeMap<String, String> values = new TreeMap<String, String>();
		
		Elements ng_div = document.getElementsByClass("placar-proximo");
		
		Document dc_ng = Jsoup.parse(ng_div.html());
		values.put("NEXT_CHAMPIONSHIP", dc_ng.getElementsByClass("descricao").get(0).text());
		values.put("NEXT_DATE", dc_ng.getElementsByClass("horario").get(0).text());
		
		Document placar_ng_escudo = Jsoup.parse(dc_ng.getElementsByClass("placar-escudo").html());
		Elements els = placar_ng_escudo.getElementsByTag("img");

		values.put("NEXT_T1_BDGURL", els.get(0).attr("src").toString());
		values.put("NEXT_T1_TITLE", els.get(0).attr("title").toString());
		values.put("NEXT_T2_BDGURL", els.get(1).attr("src").toString());
		values.put("NEXT_T2_TITLE", els.get(1).attr("title").toString());		
		
		
		Elements lg_div = document.getElementsByClass("placar-anterior");
		Document dc_lg = Jsoup.parse(lg_div.html());
		
		Element lg_t1 = Jsoup.parse(dc_lg.getElementsByClass("escudo-casa").get(0).html()).getElementsByTag("img").get(0);
		values.put("LAST_T1_BDGURL", lg_t1.attr("src").toString());
		values.put("LAST_T1_TITLE", lg_t1.attr("title").toString());
		
		Element lg_placar = Jsoup.parse(dc_lg.getElementsByClass("placar-confronto").get(0).html());
		Element lg_placar_t1 = lg_placar.getElementsByClass("gol1").get(0);
		Element lg_placar_t2 = lg_placar.getElementsByClass("gol2").get(0);
		values.put("LAST_T1_SCORE", lg_placar_t1.text());
		values.put("LAST_T2_SCORE", lg_placar_t2.text());
		
		Element lg_t2 = Jsoup.parse(dc_lg.getElementsByClass("escudo-visitante").get(0).html()).getElementsByTag("img").get(0);
		values.put("LAST_T2_BDGURL", lg_t2.attr("src").toString());
		values.put("LAST_T2_TITLE", lg_t2.attr("title").toString());
		
		return values;
	}

	public static TreeMap<Integer, TreeMap<Integer, StatClub>> getRankingLibertadores() throws IOException{
		/*File f = new File(Environment.getExternalStorageDirectory()+"/liberta12.htm"); //para debug, eh melhor ter o html no sd
		Document document = Jsoup.parse(f, "iso-8859-1");*/
		
		TreeMap<Integer, TreeMap<Integer, StatClub>> tmRanking = new TreeMap<Integer, TreeMap<Integer, StatClub>>();

		for (int i = 1; i <= 8; i++) {
			TreeMap<Integer, StatClub> tmGroup = new TreeMap<Integer, StatClub>();
			
			Document document = Jsoup.connect("http://esporte.uol.com.br/futebol/campeonatos/libertadores/2013/classificacao/segunda-fase/grupo-" + i + ".htm").get();
			
			Element table = document.getElementById("classificationTable");
			Elements tablerows = table.getElementsByTag("tr");
			tablerows.remove(0); //Headers da tabela
			
			for (Element e : tablerows) {
				StatClub sc = new StatClub();
				
				Elements tabledata = e.getElementsByTag("td");
				
				for (Element element : tabledata) {
					if(element.className().equals("tc-team")){
						sc.setPosition(Integer.parseInt(element.text().substring(0, 1)));
						Elements els = element.getElementsByTag("a");
						if(els.size()>0) sc.setName(els.get(0).text());//element.text().substring(2));
						else{
							els = element.getElementsByTag("p");
							if(els.size()>0) sc.setName(els.get(0).text());
						}
					}
					else if(element.className().equals("tc-pg")) sc.setPoints(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-j")) sc.setPlayed(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-v")) sc.setWin(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-e")) sc.setDraw(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-d")) sc.setLose(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-gp")) sc.setGoalsPro(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-gc")) sc.setGoalsAgainst(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-sg")) sc.setBalance(Integer.parseInt(element.text()));
					else if(element.className().equals("tc-pc")) sc.setAproveit(Float.parseFloat(element.text()));
				}
				
				while(tmGroup.containsKey(sc.getPosition())){
					sc.setPosition(sc.getPosition()+1);
				}
				
				tmGroup.put(sc.getPosition(), sc);
			}
			
			tmRanking.put(i, tmGroup);
		}
		
		return tmRanking;
	}
	
	public static String getVersionAtMarket() throws IOException{
		Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=br.com.zynger.influ").get();
		
		Elements els = document.getElementsByAttributeValue("itemprop", "softwareVersion");
		
		return els.get(0).text();
	}
	
	public static ArrayList<String> getStatisticsDataInfo() throws IOException{
		ArrayList<String> al = new ArrayList<String>();
		
		Document document = Jsoup.connect("http://www.torcidatricolor.com.br/php/estatisticas2012.php").get();
		Element big_table = document.getElementsByTag("table").get(0);
		
		Element table_championships = big_table.getElementsByTag("table").get(0);
		Element tr_champs = table_championships.getElementsByTag("tr").get(2);
		Elements td_champs = tr_champs.getElementsByTag("td");
		for (Element el : td_champs) {
			al.add(el.html());
		}
		
		Element table_goals = big_table.getElementsByTag("table").get(2);
		Elements td_goals = table_goals.getElementsByTag("td");
		for (Element el : td_goals) {
			al.add(el.html());
		}
		
		Element table_team = big_table.getElementsByTag("table").get(5);
		al.add(table_team.getElementsByTag("td").get(0).html());
		
		return al;
	}
	
}
