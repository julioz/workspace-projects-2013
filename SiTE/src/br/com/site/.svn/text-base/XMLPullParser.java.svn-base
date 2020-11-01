package br.com.site;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Veiculo;
import br.com.site.model.Vitima;

import com.google.android.maps.GeoPoint;

public class XMLPullParser{
	
	private String xml;
	private XmlPullParser parser;
	public static final int STRING = 0;
	public static final int URL = 1;
	private boolean modo = false;
	
	private final String TAG = "SiTE";
	
    public XMLPullParser(String xml, int modo) throws IOException, XmlPullParserException{
    	setXmlUrl(xml);
    	
    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
        XmlPullParser parser = factory.newPullParser();
        this.parser = parser;
        
        if(modo == XMLPullParser.STRING) this.modo = false;
        if(modo == XMLPullParser.URL) this.modo = true;
	}
    
    public PDI parse() throws IOException, XmlPullParserException{
    	if(this.modo){
    		URL urlObj = new URL(xml);
    		URLConnection ucon = urlObj.openConnection();
    		InputStream is = ucon.getInputStream();
    		
    		parser.setInput(is, null);
    	}else{
    		parser.setInput(new StringReader(this.xml));
    	}
    	
    	return parseFromUrl();
    }
	
	private PDI parseFromUrl(){
		PDI retorno = null;
		
		try{
			parser.nextTag();
	        if(parser.getName().equals("veiculo")) retorno = parseVeiculo();
	        if(parser.getName().equals("estrutura")) retorno = parseEstrutura();
	        if(parser.getName().equals("equipe")) retorno = parseEquipe();
	        if(parser.getName().equals("equipamento")) retorno = parseEquipamento();
	        if(parser.getName().equals("perigo")) retorno = parsePerigo();
	        if(parser.getName().equals("vitima")) retorno = parseVitima();
		}catch (XmlPullParserException e) {
			Log.d(TAG, "XmlPullParserException");
		}catch (IOException e) {
			Log.d(TAG, "IOException");
		}
		
		return retorno;
	}

	private Veiculo parseVeiculo() throws XmlPullParserException, IOException {
		int id = parseId();
		GeoPoint p = parseGeoPoint();
		
		String ident = getNextText();
		String tipoVei = getNextText();
		String subtipo = getNextText();
		
		Veiculo v = new Veiculo(id, p, ident, null, null, ident, tipoVei, subtipo, null);
		v.atualizaTextoBalao();
		return v;
	}
	
	private Estrutura parseEstrutura() throws XmlPullParserException, IOException {
		int id = parseId();
		GeoPoint p = parseGeoPoint();
		
		String ident = getNextText();
		String tipoEst = getNextText();
		int aflPub = getNextInt();
		String estRev = getNextText();
		String instab = getNextText();
		String material = getNextText();
		String tamanhoAcesso = getNextText();
		String difEntrada = getNextText();
		int numAndares = getNextInt();
		int numSubsolos = getNextInt();
		String tipoSubsolo = getNextText();
		String evolucaoTrab = getNextText();
		int qtdPessoasPiso = getNextInt();
		int resistenciaPiso = getNextInt();
		int tempoEstAcesso = getNextInt();
		
		int estadoRev;
    	if (estRev.equals("Não revisado")) estadoRev = 1;
    	else if (estRev.equals("Em revisão")) estadoRev = 2;
    	else if (estRev.equals("Revisado")) estadoRev = 3;
    	else estadoRev = 0;
    	
    	int instabil;
    	if (instab.equals("Estável")) instabil = 1;
    	else if (instab.equals("Instável")) instabil = 2;
    	else if (instab.equals("Completamente Instável")) instabil = 3;
    	else instabil = 0;
    	
    	int dific;
    	if (difEntrada.equals("Leve")) dific = 1;
    	else if (difEntrada.equals("Média")) dific = 2;
    	else if (difEntrada.equals("Difícil")) dific = 3;
    	else if(difEntrada.equals("Muito Difícil")) dific = 4;
    	else dific = 0;
    	
    	int evTr;
    	if (evolucaoTrab.equals("Não começou"))evTr = 1;
    	else if (evolucaoTrab.equals("Em andamento")) evTr = 2;
    	else if (evolucaoTrab.equals("Finalizado")) evTr = 3;
    	else if(evolucaoTrab.equals("Em pausa")) evTr = 4;
    	else evTr = 0;
		
		Estrutura est = new Estrutura(id, p, ident, null, null, null, ident, tipoEst, aflPub, estadoRev, instabil, material, tamanhoAcesso, dific, numAndares, numSubsolos, tipoSubsolo, evTr, qtdPessoasPiso, resistenciaPiso, tempoEstAcesso);
		est.atualizaTextoBalao();
		return est;
	}
	
	private Equipe parseEquipe() throws XmlPullParserException, IOException {
		int id = parseId();
		GeoPoint p = parseGeoPoint();
		
		String ident = getNextText();
		String chefe = getNextText();
		String tipoFunc = getNextText();
		int qtdMem = getNextInt();
		int qtdMemFer = getNextInt();
		String tarefaAt = getNextText();
		
		int tipoDeFuncao = 7;
		if (tipoFunc.equals("Desconhecido")) tipoDeFuncao = 0;
		else if (tipoFunc.equals("Resgate")) tipoDeFuncao = 1;
		else if (tipoFunc.equals("Administração")) tipoDeFuncao = 2;
		else if (tipoFunc.equals("Médica")) tipoDeFuncao = 3;
		else if (tipoFunc.equals("Segurança")) tipoDeFuncao = 4;
		else if (tipoFunc.equals("Voluntários")) tipoDeFuncao = 5;
		else if (tipoFunc.equals("Serviços Públicos")) tipoDeFuncao = 6;
		else tipoDeFuncao = 7;
		
		Equipe te = new Equipe(id, p, ident, null, null, null, ident, chefe, null, null, tipoDeFuncao, qtdMem, qtdMemFer, tarefaAt);
		te.atualizaTextoBalao();
		return te;
	}
	
	private Equipamento parseEquipamento() throws XmlPullParserException, IOException {
		int id = parseId();
		GeoPoint p = parseGeoPoint();
		
		String ident = getNextText();
		String tipoEquip = getNextText();
		int quant = getNextInt();
		int disp = getNextInt();
		
		Equipamento eqp = new Equipamento(id, p, ident, null, null, ident, tipoEquip, quant, disp);
		eqp.atualizaTextoBalao();
		return eqp;
	}
	
	private Perigo parsePerigo() throws XmlPullParserException, IOException {
		int id = parseId();
		GeoPoint p = parseGeoPoint();
		
		String ident = getNextText();
		String tipoPer = getNextText();
		String risco = getNextText();
		String devoIr = getNextText();
		
		int riscoAssociado = 5;
		if (risco.equals("Desconhecido")) riscoAssociado = 0;
		else if (risco.equals("Nulo")) riscoAssociado = 1;
		else if (risco.equals("Baixo")) riscoAssociado = 2;
		else if (risco.equals("Médio")) riscoAssociado = 3;
		else if (risco.equals("Alto")) riscoAssociado = 4;
		else riscoAssociado = 5;
		
		Perigo per = new Perigo(id, p, ident, null, null, tipoPer, riscoAssociado, devoIr, ident);
		per.atualizaTextoBalao();
		return per;
	}
	
	private Vitima parseVitima() throws XmlPullParserException, IOException {
		int id = parseId();
		GeoPoint p = parseGeoPoint();
		
		String iden = getNextText();
		int numPosVit = getNextInt();
		int qtdViv = getNextInt();
		int qtdMor = getNextInt();
		int qtdVResg = getNextInt();
		int qtdMRem = getNextInt();
		
		Vitima vit = new Vitima(id, p, iden, null, null, null, iden, numPosVit, qtdViv, qtdMor, qtdVResg, qtdMRem);
		vit.atualizaTextoBalao();
		return vit;
	}

	private GeoPoint parseGeoPoint() throws XmlPullParserException, IOException{
		parser.nextTag();
		parser.nextTag();
		int lat = Integer.valueOf(parser.nextText());
		parser.nextTag();
		int lng = Integer.valueOf(parser.nextText());
		parser.nextTag();
		GeoPoint p = new GeoPoint(lat, lng);
		parser.nextTag();
		return p;
	}
	
	private int parseId() throws XmlPullParserException, IOException{
		parser.nextTag();
		return Integer.valueOf(parser.nextText());
	}
	
	private String getNextText() throws XmlPullParserException, IOException{
		String ret = parser.nextText();
		parser.nextTag();
		return ret;
	}
	
	private int getNextInt() throws XmlPullParserException, IOException{
		int ret = Integer.parseInt(parser.nextText());
		parser.nextTag();
		return ret;
	}

	public void setXmlUrl(String xmlUrl) {
		this.xml = xmlUrl;
	}
}