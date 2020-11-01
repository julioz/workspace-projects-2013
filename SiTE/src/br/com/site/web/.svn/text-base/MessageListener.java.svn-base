package br.com.site.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import br.com.site.EditarPDI;
import br.com.site.Global;
import br.com.site.MainActivity;
import br.com.site.XMLPullParser;
import br.com.site.model.PDI;

public class MessageListener extends Service implements Runnable{
	
    private String strBuffer;

    public static String SERVERIP = "julioz92.dyndns.org";//"10.0.2.15";
    public static final int SERVERPORT = 20777;
    public static final String ACK = "ack";
    public static final String NACK = "nak";
    private Handler handler = new Handler();
    private ServerSocket serverSocket;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
        SERVERIP = Global.getLocalIpAddress(getApplicationContext()); //TODO eh o ip local o que eu tenho que usar?

        Thread t = new Thread(this);
        t.start();
	}

	@Override
	public void run() {
		try {
            if (SERVERIP != null) {
            	serverSocket = new ServerSocket(SERVERPORT);
                
                while (true) {
                	Socket client = serverSocket.accept();

                    showMessage("CONECTOU!");
                	Log.d("SiTE", "CONECTOU!");

                	if(Global.souServer){                		
                		String clientInetAddress = client.getInetAddress().toString().substring(1);
                		if(!Global.clientSockets.containsKey(clientInetAddress)){
                			Global.clientSockets.put(clientInetAddress, client);
                		}
                	}
                    
                    PrintWriter writer = new PrintWriter(client.getOutputStream(), true); /**/
                    Thread t = new Thread(new ClientHandler(client, writer));
                    t.start();
                }
            } else { //SERVERIP == NULL
                showMessage("Não pude detectar a conexão com a internet");
                stopSelf();
            }
        } catch (IOException e) {
            showMessage("Erro - IOException");
            stopSelf();
        }
	}

	private void novoPDI(Socket client, PrintWriter writer) throws IOException {
		Log.e("SiTE", "entrei em novoPDI()");
		XMLPullParser xmlpp;
		try {
			xmlpp = new XMLPullParser(strBuffer, XMLPullParser.STRING);
			Log.v("SiTE", strBuffer);
			Log.e("SiTE", "[NOVO] instanciei o xmlpullparser");
			PDI pdi = xmlpp.parse();
			Log.e("SiTE", "[NOVO] parseei o pdi " + pdi.toString());
			MainActivity.criarOverlay(pdi);
			MainActivity.getMapView().postInvalidate();
		} catch (XmlPullParserException e) {
			enviarNACK(writer);
			e.printStackTrace();
		}
		
		enviarACK(writer);
		
		if(Global.souServer){
			Global.tellEveryoneExcept(ClientThread.NOVOPDI, strBuffer, client.getInetAddress().toString().substring(1));
		}
	}
	
	private void editarPDI(Socket client, PrintWriter writer) throws IOException {
		XMLPullParser xmlpp;
		try {
			xmlpp = new XMLPullParser(strBuffer, XMLPullParser.STRING);
			PDI pdi = xmlpp.parse();
			Log.e("SiTE", "[EDITAR] recebi o pdi  " + pdi.toString());
			if(MainActivity.mapaPDI.containsKey(pdi.getId())){
				Log.e("SiTE", "[EDITAR] contem a chave");
				EditarPDI.substituiObj(MainActivity.mapaPDI.get(pdi.getId()), pdi);
				Log.e("SiTE", "[EDITAR] substituiObj()");
			}else{
				MainActivity.criarOverlay(pdi);
			}
			
			MainActivity.getMapView().postInvalidate();
		} catch (XmlPullParserException e) {
			enviarNACK(writer);
			e.printStackTrace();
		}
		
		enviarACK(writer);
		
		if(Global.souServer){
			Global.tellEveryoneExcept(ClientThread.EDITARPDI, strBuffer, client.getInetAddress().toString().substring(1));
		}
	}
	
	private void excluirPDI(Socket client, PrintWriter writer) throws IOException{
		XMLPullParser xmlpp;
		try {
			xmlpp = new XMLPullParser(strBuffer, XMLPullParser.STRING);
			PDI pdi = xmlpp.parse();
			if(MainActivity.mapaPDI.containsKey(pdi.getId())){
				EditarPDI.excluirObj(MainActivity.mapaPDI.get(pdi.getId()));
			}
			
			MainActivity.getMapView().postInvalidate();
		} catch (XmlPullParserException e) {
			enviarNACK(writer);
			e.printStackTrace();
		}
		
		enviarACK(writer);
		
		if(Global.souServer){
			Global.tellEveryoneExcept(ClientThread.EXCLUIRPDI, strBuffer, client.getInetAddress().toString().substring(1));
		}
	}
	
	private void enviarACK(PrintWriter writer){
		writer.println(MessageListener.ACK);
	}
	
	private void enviarNACK(PrintWriter writer){
		writer.println(MessageListener.NACK);
	}

	private void showMessage(final String string) {
		handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	@Override
	public void onDestroy() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	public class ClientHandler implements Runnable {

		private BufferedReader reader;
		private Socket sock;
		private PrintWriter writer;

		public ClientHandler(Socket clientSocket, PrintWriter cliWriter){
			try {
				this.sock = clientSocket;
				this.reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
				this.writer = cliWriter;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			try {
				String message = "";
				strBuffer = "";
				int funcao = -1;
				while ((message = reader.readLine()) != null){
					if(message.contains("FUNCAO: ")){
                    	try{
                    		funcao = Integer.valueOf(message.substring(8));
                    	}catch (NumberFormatException e){
                    		Log.e("SiTE", "numberformatexception - FUNCAO");
                    	}
                    }else{
                    	strBuffer += message + "\n";
                    	break;
                    }
				}
				if(strBuffer != null) executarInstrucao(funcao);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void executarInstrucao(int funcao) throws IOException{
			strBuffer = strBuffer.substring(0, strBuffer.length()-1);
            
            //break; //Se só quiser aceitar uma conexão por vez, use break pra sair do while infinito
            
            if(funcao == -1) Log.e("SiTE", "erro durante comunicacao - nenhuma funcao chamada");
            else if(funcao == ClientThread.CONEXAO) enviarACK(writer);
            else if(funcao == ClientThread.NOVOPDI) novoPDI(sock, this.writer);
            else if(funcao == ClientThread.EDITARPDI) editarPDI(sock, this.writer);
            else if(funcao == ClientThread.EXCLUIRPDI) excluirPDI(sock, this.writer);
            else{
            	Log.e("SiTE", "Mandei o else pra funcao");
            	enviarACK(writer);
            }
		}
	}
}