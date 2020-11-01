package br.com.site;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import br.com.site.model.PDI;
import br.com.site.web.ClientThread;


public class Global {

	public static boolean novoPdi = false, desPoligono = false, editarPDI = false;
	
	private static boolean online = false;
	
	public static String[] strVei = {"Ambulância","Veículo Aéreo",
		"Veículo de Socorro Terrestre","Veículo de Socorro Aquático"};
	public static String[] strEst = {"Casa","Edifício","Edifício Comercial","Estádio",
		"Hospital","Indústria","Shopping Center"};
	public static String[] strTeam = {"Aeronáutica","Bombeiros","CEDAE","CEG","Cet-Rio",
		"CNEN","Comlurb","Def. Civil Estad.","Def. Civil Mun.","Exército",
		"Geo-Rio","IBAMA","IML","INEA","Light","Marinha","Polícia Civil",
		"Polícia Militar","Voluntários"};
	public static String[] strPer = {"Colapso de Edifício","Deslizamento de Terra",
		"Gases Tóxicos","Líquidos Tóxicos","Exposição Radioativa",
		"Ratos","Substâncias Tóxicas"};
	
	public static PDI pdiEdicao, pdiWeb;
	
	public final static int QUIT = 183;
	
	public static LinkedHashMap<String, Socket> clientSockets = new LinkedHashMap<String, Socket>();
	public static String serverIp = "192.168.2.103"; //TODO
	public static boolean souServer = true;
	
	private static int numEmerg = 0;
	private static int numPDI = 0;
	
	public static void zeraNumPDI(){ //TODO debug
		Global.numPDI = 0;
	}
	
	public static String getLocalIpAddress(Context c) {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("SiTE", ex.toString());
	    }
	    return null;
	}
	
	public static String getExternalIpAdress () {
        try {
        	HttpClient httpclient = new DefaultHttpClient();
        	HttpGet httpget = new HttpGet("http://wiki.iti-lab.org/ip.php");
        	// HttpGet httpget = new HttpGet("http://whatismyip.everdot.org/ip");
        	// HttpGet httpget = new HttpGet("http://whatismyip.com.au/");
        	// HttpGet httpget = new HttpGet("http://www.whatismyip.org/");
        	HttpResponse response;
        	
        	response = httpclient.execute(httpget);
        	
        	HttpEntity entity = response.getEntity();
        	if (entity != null) {
        		long len = entity.getContentLength();
        		if (len != -1 && len < 1024) {
        			String str=EntityUtils.toString(entity);
                    return str;
        		} else return "Resposta demorou muito ou ocorreu um erro";
        	} else {
        		//return "Null:"+response.getStatusLine().toString();
        		return null;
        	}
            
        }catch (ClientProtocolException e){
        	return null;
        }catch (IOException e){
        	return null;
        }

    }
	
	public static void editarPDI(String xml){
		if(isOnline()){
			if(!Global.souServer){ //sou cliente
				ClientThread ct = ClientThread.getInstance(Global.serverIp);
				ct.send(xml, ClientThread.EDITARPDI);
			}else{
				//TODO TELLEVERYONE
				Global.tellEveryone(ClientThread.EDITARPDI, xml);
			}
		}
	}
	
	public static void tellEveryone(int funcao, String message){
		Iterator<String> it = Global.clientSockets.keySet().iterator();
		while(it.hasNext()){
			try{
				String ip = it.next();
				ClientThread ct = ClientThread.getInstance(ip);
				ct.send(message, funcao);
				ClientThread.setClientThreadNull();
			}catch(Exception ex){
				Log.e("SiTE", ex.getMessage());
				ex.printStackTrace(); //TODO
			}
		}
	}
	
	public static void tellEveryoneExcept(int funcao, String message, String except){
		Iterator<String> it = Global.clientSockets.keySet().iterator();
		while(it.hasNext()){
			try{
				String ip = it.next();
				if(!ip.equals(except)){					
					ClientThread ct = ClientThread.getInstance(ip);
					ct.send(message, funcao);
					ClientThread.setClientThreadNull();
				}
			}catch(Exception ex){
				Log.e("SiTE", ex.getMessage());
				ex.printStackTrace(); //TODO
			}
		}

	}

	public static int getNumEmerg() {
		Global.numEmerg++;
		return numEmerg;
	}

	public static int getNumPDI() {
		Global.numPDI++;
		return numPDI;
	}

	public static void setOnline(boolean online) {
		Global.online = online;
	}

	public static boolean isOnline() {
		return online;
	}
	
}
