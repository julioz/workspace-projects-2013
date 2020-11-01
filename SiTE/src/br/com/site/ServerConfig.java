package br.com.site;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.site.web.ClientThread;

public class ServerConfig extends Activity implements View.OnClickListener, OnItemLongClickListener{
	
	public static final int PORTAPADRAO = 7777;
	
	public static final Pattern IP_ADDRESS = Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\." +
			"(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\." +
			"(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\." +
			"(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))");
	
	private CheckBox cbServer;
	private ListView lvConexoes;
	private LinearLayout llIp;
	private TextView tvIp;
	private Dialog d;
	private Button connect;
	private EditText etIp;
	private ArrayList<InetAddress> al;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serverconfig);
		
		initViews();

		al = new ArrayList<InetAddress>();
		
		cbServer.setChecked(Global.souServer);
		
		if(cbServer.isChecked()){
			modoServer();
		}else{
			if(!Global.serverIp.equals("") && Global.serverIp != null){
				al.add(new InetSocketAddress(Global.serverIp, PORTAPADRAO).getAddress());
			}
			lvConexoes.setOnItemLongClickListener(this);
			lvConexoes.setAdapter(new SocketAdapter(this, R.layout.serverconfiglistviewrow, al));
		}
		
		cbServer.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//TODO aqui precisaria de um dialog de confirmacao?
				Global.souServer = isChecked;
				
				if(isChecked){
					modoServer();
				}else{
					llIp.setVisibility(View.GONE);
					lvConexoes.setOnItemLongClickListener(ServerConfig.this);
					al.clear();
					d.show();
				}
			}
		});
	
	}
	
	/** Limpa o ArrayList de InetAddress e adiciona todos os que pertencem ao Global.clientSockets*/
	private void modoServer(){
		Global.serverIp = null;
		llIp.setVisibility(View.VISIBLE);
		
		al.clear();
		for (Socket socket : Global.clientSockets.values()) {
			al.add(socket.getInetAddress());
		}
		/*al.add(new InetSocketAddress("192.168.2.1", PORTAPADRAO).getAddress());
		al.add(new InetSocketAddress("188.210.68.21", PORTAPADRAO).getAddress());
		al.add(new InetSocketAddress("98.100.57.128", PORTAPADRAO).getAddress());*/
		
		lvConexoes.setAdapter(new SocketAdapter(ServerConfig.this, R.layout.serverconfiglistviewrow, al));
		lvConexoes.setOnItemLongClickListener(this);
	}
	
	private void initViews(){
		cbServer = (CheckBox) findViewById(R.server_config.cbServer);
		lvConexoes = (ListView) findViewById(R.server_config.lvConexoes);
		llIp = (LinearLayout) findViewById(R.server_config.llIp);
		tvIp = (TextView) findViewById(R.server_config.tvIp);
		
		tvIp.setText(Global.getLocalIpAddress(this));
		
		d = new Dialog(this);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.client);
		d.setCancelable(false);
		d.setCanceledOnTouchOutside(false);
		
		etIp = (EditText) d.findViewById(R.id.server_ip);
		etIp.setText(Global.serverIp);
		
		connect = (Button) d.findViewById(R.id.connect_phones);
		
		connect.setOnClickListener(this);
	}
	
	private class SocketAdapter extends ArrayAdapter<InetAddress> {

		private ArrayList<InetAddress> items;

		public SocketAdapter(Context  context, int textViewResourceId, ArrayList<InetAddress> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}
		
		@Override
		public View getView(int position, View  convertView, ViewGroup parent) {
			View  v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.serverconfiglistviewrow, null);
			}
			
			InetAddress s = items.get(position);
			
			if (s != null) {
				ImageView iv = (ImageView) v.findViewById(R.serverconfiglvrow.icone);
				TextView tt = (TextView) v.findViewById(R.serverconfiglvrow.toptext);
				TextView bt = (TextView) v.findViewById(R.serverconfiglvrow.bottomtext);
				if (iv != null){
					if(Global.souServer) iv.setImageResource(R.drawable.ic_smartphone);
					else iv.setImageResource(R.drawable.ic_tablet);
				}
				if (tt != null){
					if(cbServer.isChecked()){						
						tt.setText("Cliente " + (position+1));
					}else{
						tt.setText("Servidor");
					}
				}
				if(bt != null) bt.setText("IP: "+ s.toString().substring(1));
			}
			
			return v;
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.connect_phones){
			String texto = etIp.getText().toString();
			
			if(texto.equals("")){
				Global.serverIp = texto;
				Toast.makeText(this, "IP do servidor não foi definido", Toast.LENGTH_SHORT).show();
				d.dismiss();
			}else{
				Matcher matcher = IP_ADDRESS.matcher(texto);
				if(matcher.matches()){
					Global.serverIp = texto;
					
					al.add(new InetSocketAddress(Global.serverIp, PORTAPADRAO).getAddress());
					lvConexoes.setAdapter(new SocketAdapter(this, R.layout.serverconfiglistviewrow, al));
					
					Toast.makeText(this, "IP do servidor setado para " + Global.serverIp, Toast.LENGTH_SHORT).show();
					
					ClientThread ct = ClientThread.getInstance(Global.serverIp);
					ct.send("", ClientThread.CONEXAO);
					
					d.dismiss();
				}else{
					etIp.setError("O IP não é válido");
				}								
			}
		}
	}

	@Override
	public boolean onItemLongClick(final AdapterView<?> parent, View view,
			final int position, long id) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		if(Global.souServer){			
			alerta.setMessage("Deseja fechar a conexão com " + parent.getItemAtPosition(position).toString().substring(1) + "?");
			alerta.setIcon(R.drawable.ic_smartphone);
			alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Socket socket = null;
					for (Socket s : Global.clientSockets.values()) {
						if(s.getInetAddress().equals(parent.getItemAtPosition(position))){
							socket = s;
						}
					}
					
					if(socket != null){
						Global.clientSockets.remove(socket);
						Toast.makeText(ServerConfig.this, "Conexão fechada", Toast.LENGTH_SHORT).show();					
					}
				}
			});
			alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}else{
			alerta.setMessage("Deseja testar a conexão com o servidor?");
			alerta.setIcon(R.drawable.ic_tablet);
			alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ClientThread ct = ClientThread.getInstance(Global.serverIp);
					ct.send("", ClientThread.CONEXAO);
					//TODO preciso de alguma forma de saber se o ack foi recebido, para entao exibir o toast de confirmacao da conexao ativa
					Toast.makeText(ServerConfig.this, "Conexão ativa", Toast.LENGTH_SHORT).show();
				}
			});
			alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}

		alerta.show();
		return true;
	}
}