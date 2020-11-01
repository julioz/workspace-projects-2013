package br.com.site.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.util.Log;

public class ClientThread {
	//private Handler handler = new Handler();
	
	private static ClientThread clientThread = null;
	private static Socket sock = null;
	private static PrintWriter writer = null;	
	private static BufferedReader reader = null;
	private static String ipAddress;
	private static Thread incomingThread;
    
    public static final int CONEXAO = 0;
    public static final int NOVOPDI = 1;
    public static final int EDITARPDI = 2;
    public static final int EXCLUIRPDI = 3;
    
	private ClientThread(){
		this.start();
	}
	
	public static ClientThread getInstance(String ip){
		ipAddress = ip;
		if (clientThread == null) clientThread = new ClientThread();
		return clientThread;
	}
	
	public void closeConnection(){
		try {
			Log.e("SiTE", "closeConnection()");
			setClientThreadNull();
			sock.close();
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void start(){
		this.setUpNetworking();
		incomingThread = new Thread(new IncomingReader());
		incomingThread.start();
	}
	
	private void setUpNetworking(){
		try{
			sock = new Socket(ipAddress, MessageListener.SERVERPORT);
			writer = new PrintWriter(sock.getOutputStream());
			Log.e("SiTE", "Setei o writer como " + writer.toString());
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void send(String message, int modo){
		try{
			writer.println("FUNCAO: " + modo);
			writer.println(message);
			writer.flush();
			Log.e("SiTE", "FLUSHEI" + message);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private class IncomingReader implements Runnable {

		@Override
		public void run() {
			try {
				String message;
				while((message = reader.readLine()) != null){
					Log.e("SiTE", "incoming - " + message); //vou receber o ack ou nack
					//showMessage("incoming - " + message);
					if(message.equals(MessageListener.ACK)) closeConnection();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    public static ArrayList<String> getFormattedArrayList(int tipoComunicacao, ArrayList<String> messageList){
    	ArrayList<String> mensagens = new ArrayList<String>();
    	mensagens.add("FUNCAO: " + tipoComunicacao);
    	mensagens.addAll(messageList);
    	return mensagens;
    }
    
    public static String getIpAddress() {
		return ipAddress;
	}
    
    public static void setClientThreadNull() {
		ClientThread.clientThread = null;
	}
    
    /*private void showMessage(final String string) {
		handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.getMapView().getContext(), string, Toast.LENGTH_SHORT).show();
            }
        });
	}*/

}