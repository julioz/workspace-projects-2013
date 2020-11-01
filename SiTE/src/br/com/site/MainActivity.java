package br.com.site;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ZoomButtonsController;
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.Instituicao;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Unidade;
import br.com.site.model.Veiculo;
import br.com.site.model.Vitima;
import br.com.site.view.ActionItem;
import br.com.site.view.CustomOverlayItem;
import br.com.site.view.ExpansaoPDI;
import br.com.site.view.QuickAction;
import br.com.site.web.ClientThread;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity {

	///////////////////////////////////////////////////
	private Toast toast;
	private long lastBackPressTime = 0;
	///////////////////////////////////////////////////
	private WiFiBroadcastReceiver wifibroadcastreceiver = new WiFiBroadcastReceiver();
	///////////////////////////////////////////////////
	private static MapView mapView;
	private int numEmerg;
	private static Handler handler = new Handler();
	///////////////////////////////////////////////////
	protected static final int VEICULO = 1;
	protected static final int ESTRUTURA = 2;
	protected static final int EQUIPAMENTO = 3;
	protected static final int EQUIPE = 4;
	protected static final int PERIGO = 5;
	protected static final int VITIMA = 6;
	///////////////////////////////////////////////////
	private RelativeLayout actionBar;
	private List<LinearLayout> actionBarBtns;
	private QuickAction quickaction;
	///////////////////////////////////////////////////
	public static CustomItemizedOverlay veiculoOverlays, estruturaOverlays, equipamentoOverlays, equipeOverlays, vitimaOverlays, perigoOverlays;
	private static List<CustomItemizedOverlay> listaDeOverlays;
	///////////////////////////////////////////////////
	public static LinkedHashMap<Integer, PDI> mapaPDI;
	public static List<Veiculo> listaVeiculos;
	public static List<Estrutura> listaEstruturas;
	public static List<Equipamento> listaEquipamentos;
	public static List<Equipe> listaEquipes;
	public static List<Perigo> listaPerigos;
	public static List<Vitima> listaVitimas;
	
	public static List<Instituicao> listaInstituicoes;
	public static List<Unidade> listaUnidades;
	///////////////////////////////////////////////////
	public static PDI objetoPassado;
	///////////////////////////////////////////////////
	private RelativeLayout barraDesenho;
	private TableRow iconesDesenhoBarra, textosDesenhoBarra, botoesPoligonoBarra;
	private ImageButton desenhoLivre, desenhoRegiao, desenhoPoligono, desenhoCores, desenhoBorracha, fecharBarraDesenho;
	private Button novoTriangulo, novoQuadrilatero, novoPentagono;
	
	private int cor;
	private int poligono;
	private int corDefinida;
	private boolean apagar = false, dLivre = false;
	private Desenho desLiv;
	///////////////////////////////////////////////////
	
	
    // Called when the activity is first created.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.numEmerg = Global.getNumEmerg(); //TODO pensar uma forma de salvar uma emergencia para ser carregada depois
        
        Global.zeraNumPDI();
        
        carregaNovoMapa();
        carregaActionBar();
        initViewsDesenho();
        exibirActionBar();
        
        if(Global.isOnline()){
        	Intent it = new Intent("MESSAGELISTENER");
        	startService(it);        	
        }
    }

    @Override
	protected void onResume() {
		registerReceiver(this.wifibroadcastreceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(this.wifibroadcastreceiver);
		super.onPause();
	}
    
    private void carregaActionBar(){
    	actionBar = (RelativeLayout) findViewById(R.id.actionbar);
    	
    	actionBarBtns = new ArrayList<LinearLayout>();
    	
    	//LinearLayout mao = (LinearLayout) findViewById(R.id.cj_mao);
    	LinearLayout servidor = (LinearLayout) findViewById(R.id.cj_server);
    	LinearLayout desenho = (LinearLayout) findViewById(R.id.cj_desenho);
    	LinearLayout novo = (LinearLayout) findViewById(R.id.cj_novo);
    	LinearLayout exibicao = (LinearLayout) findViewById(R.id.cj_exibicao);
    	
    	//actionBarBtns.add(mao);
    	actionBarBtns.add(servidor);
    	actionBarBtns.add(desenho);
    	actionBarBtns.add(novo);
    	actionBarBtns.add(exibicao);
    	
    	final RelativeLayout barraDesenho = (RelativeLayout) findViewById(R.id.desenho_layout);
    	
    	/*mao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				barraDesenho.setVisibility(View.GONE);
				esconderActionBar();
			}
		});*/
    	
    	final String ip = Global.getLocalIpAddress(this);
    	
    	servidor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final QuickAction qa = new QuickAction(v);
				qa.setAnimStyle(QuickAction.ANIM_REFLECT);
				quickaction = qa;
				
				String stat = "Desconectado";
				if(Global.isOnline()) stat = "Conectado";
				qa.addActionItem(new ActionItem("Status", stat, null));
				if(Global.isOnline()){
					qa.addActionItem(new ActionItem("IP do dispositivo", ip, null));
					
					if(Global.serverIp != null){
						String sIp = Global.serverIp;
						if(Global.serverIp.equals("")) sIp = "Não definido";
						qa.addActionItem(new ActionItem("IP do servidor", sIp, new OnClickListener() {
							@Override
							public void onClick(View v) {
								final Dialog d = new Dialog(MainActivity.this);
								d.requestWindowFeature(Window.FEATURE_NO_TITLE);
								d.setContentView(R.layout.client);
								
								final EditText etIp = (EditText) d.findViewById(R.id.server_ip);
								etIp.setText(Global.serverIp);
								
								Button con = (Button) d.findViewById(R.id.connect_phones);
								
								con.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										String texto = etIp.getText().toString();
										Global.souServer = false;
										
										if(texto.equals("")){
											Global.serverIp = texto;
											Toast.makeText(MainActivity.this, "IP do servidor não foi definido", Toast.LENGTH_SHORT).show();
											d.dismiss();
											qa.dismiss();
										}else{
											Matcher matcher = ServerConfig.IP_ADDRESS.matcher(texto);
											if(matcher.matches()){
												Global.serverIp = texto;
												
												Toast.makeText(MainActivity.this, "IP do servidor setado para " + Global.serverIp, Toast.LENGTH_SHORT).show();
												
												ClientThread ct = ClientThread.getInstance(Global.serverIp);
												ct.send("", ClientThread.CONEXAO);
												
												d.dismiss();
												qa.dismiss();
											}else{
												etIp.setError("O IP não é válido");
											}								
										}
									}
								});
								
								d.show();
							}
						}));
					}
					
				}

				ActionItem configs = setActionItem("Configurar", R.drawable.bt_edit, new Intent(MainActivity.this, ServerConfig.class));
				qa.addActionItem(configs);
				
				qa.show();
			}
		});
    	
    	desenho.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carregaMenusDesenho(barraDesenho);
				esconderActionBar();
			}
		});
    	
    	final ActionItem veiculo = setActionItem("Veículo", R.drawable.car_icon, new Intent(MainActivity.this, NovoVeiculo.class));
    	final ActionItem estrutura = setActionItem("Estrutura", R.drawable.casa_icon, new Intent(MainActivity.this, NovoEstrutura.class));
    	final ActionItem equipamento = setActionItem("Equipamento", R.drawable.equipment_icon, new Intent(MainActivity.this, NovoEquipamento.class));
    	final ActionItem equipe = setActionItem("Equipe", R.drawable.team_icon, new Intent(MainActivity.this, NovoEquipe.class));
    	final ActionItem perigo = setActionItem("Perigo", R.drawable.danger_icon, new Intent(MainActivity.this, NovoPerigo.class));
    	final ActionItem vitima = setActionItem("Vítima", R.drawable.victim_icon, new Intent(MainActivity.this, NovoVitima.class));
    	final ActionItem instituicao = setActionItem("Instituição", R.drawable.ic_instituicao, new Intent(MainActivity.this, NovoInstituicao.class));
    	
    	final ActionItem unidade = new ActionItem();
		unidade.setTitle("Unidade");
		unidade.setIcon(getResources().getDrawable(R.drawable.ic_unidade));
		unidade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog criaUnidade = new Dialog(MainActivity.this);
				criaUnidade.requestWindowFeature(Window.FEATURE_NO_TITLE);
				criaUnidade.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	        	criaUnidade.setContentView(R.layout.novo_unidade);
	        	criaUnidade.setTitle("Nova Unidade");
	        	carregaCriacaoUnidade(criaUnidade);
	        	quickaction.dismiss();
	        	criaUnidade.show();
			}
		});
    	
    	novo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QuickAction qa = new QuickAction(v);
				qa.setAnimStyle(QuickAction.ANIM_REFLECT);
				quickaction = qa;
				
				qa.addActionItem(veiculo);
				qa.addActionItem(estrutura);
				qa.addActionItem(equipamento);
				qa.addActionItem(equipe);
				qa.addActionItem(perigo);
				qa.addActionItem(vitima);
				qa.addActionItem(instituicao);
				qa.addActionItem(unidade);
				
				qa.show();
			}
		});
    	
    	exibicao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Exibicao.class));
				if(quickaction != null){
					if(quickaction.isShown()){
						quickaction.dismiss();
					}
				}
			}
		});
    }

    private void initViewsDesenho(){
    	iconesDesenhoBarra = (TableRow) findViewById(R.id.desenho_icones_barra);
    	textosDesenhoBarra = (TableRow) findViewById(R.id.desenho_texto_barra);
    	botoesPoligonoBarra = (TableRow) findViewById(R.id.desenho_botoes_poligono_barra);
    	
    	desenhoLivre = (ImageButton) findViewById(R.id.bt_desenho);
    	desenhoRegiao = (ImageButton) findViewById(R.id.bt_fill);
    	desenhoPoligono = (ImageButton) findViewById(R.id.bt_poligono);
    	desenhoCores = (ImageButton) findViewById(R.id.bt_cores);
    	desenhoBorracha = (ImageButton) findViewById(R.id.bt_borracha);
    	
    	fecharBarraDesenho = (ImageButton) findViewById(R.id.bt_fechar_desenho);
    	
    	novoTriangulo = (Button) findViewById(R.id.bt_triangulo);
    	novoQuadrilatero = (Button) findViewById(R.id.bt_quadrilatero);
    	novoPentagono = (Button) findViewById(R.id.bt_pentagono);
    }
    
    private ActionItem setActionItem(String title, int icon, final Intent intent){
    	ActionItem ai = new ActionItem();
    	ai.setTitle(title);
    	ai.setIcon(getResources().getDrawable(icon));
    	
    	ai.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);
				quickaction.dismiss();
			}
		});
    	
    	return ai;
    }
    
    /** Novo mapa e seus recursos */
    private void carregaNovoMapa(){
    	getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.mapa);
    	mapView = (MapView) findViewById(R.id.mapView);
    	
    	MapController mc = mapView.getController();
    	mc.setZoom(15);
    	mc.animateTo(new GeoPoint(-22862767,-43236342));
    	
        /** controles do zoom setados a direita */
        mapView.setBuiltInZoomControls(true);
    	ZoomButtonsController zbc = mapView.getZoomButtonsController();
    	View zc = zbc.getZoomControls();
    	FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) zc.getLayoutParams();
    	lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
	    zc.requestLayout();
        
        /** icone padrao dos tipos de pdis */
        Drawable iconeVeiculo = getResources().getDrawable(R.drawable.car_icon);
    	Drawable iconeEstrutura = getResources().getDrawable(R.drawable.casa_icon);
    	Drawable iconeEquipamento = getResources().getDrawable(R.drawable.equipment_icon);
    	Drawable iconeEquipe = getResources().getDrawable(R.drawable.team_icon);
    	Drawable iconePerigo = getResources().getDrawable(R.drawable.danger_icon);
    	Drawable iconeVitima = getResources().getDrawable(R.drawable.victim_icon);
        
        /** cria os Objetos Personalizados que carregarao os overlays */
		veiculoOverlays = new CustomItemizedOverlay(iconeVeiculo, mapView);
		estruturaOverlays = new CustomItemizedOverlay(iconeEstrutura, mapView);
		equipamentoOverlays = new CustomItemizedOverlay(iconeEquipamento, mapView);
		equipeOverlays = new CustomItemizedOverlay(iconeEquipe, mapView);
		perigoOverlays = new CustomItemizedOverlay(iconePerigo, mapView);
		vitimaOverlays = new CustomItemizedOverlay(iconeVitima, mapView);
		
		listaDeOverlays = new ArrayList<CustomItemizedOverlay>();
		listaDeOverlays.add(veiculoOverlays);
		listaDeOverlays.add(estruturaOverlays);
		listaDeOverlays.add(equipamentoOverlays);
		listaDeOverlays.add(equipeOverlays);
		listaDeOverlays.add(perigoOverlays);
		listaDeOverlays.add(vitimaOverlays);
		
		mapaPDI = new LinkedHashMap<Integer, PDI>();
		
		// Carregarei as listas de PDIs
		listaVeiculos = new ArrayList<Veiculo>();
		listaEstruturas = new ArrayList<Estrutura>();
		listaEquipamentos = new ArrayList<Equipamento>();
		listaEquipes = new ArrayList<Equipe>();
		listaPerigos = new ArrayList<Perigo>();
		listaVitimas = new ArrayList<Vitima>();
		
		listaInstituicoes = new ArrayList<Instituicao>();
		listaUnidades = new ArrayList<Unidade>();
		
		//TODO ja fez tudo? Pode me tirar daqui! :)
		PDIsFicticios();
		
		
		// TODO Que tal implementar reverse Geocoding?
		
		
		/////////////////////////////////////////
		// vou realmente desenhar tudo no mapa //
		/////////////////////////////////////////
		
		getMapOverlays();
    }
    
    private void PDIsFicticios(){
    	/** PDIs ficticios */
		Instituicao bombeiros = new Instituicao("Bombeiros", "Tenente Jonas da Silva");
		listaInstituicoes.add(bombeiros);
		Instituicao policia = new Instituicao("Polícia Militar", "Sargento Carlos de Andrade");
		listaInstituicoes.add(policia);
		Instituicao comlurb = new Instituicao("Comlurb", "Márcio Chaves");
		listaInstituicoes.add(comlurb);
		Instituicao exc = new Instituicao("Exército", "Darío Alves");
		listaInstituicoes.add(exc);
		
		Unidade un = new Unidade("UA25", "Francisco Santos");
		Unidade un2 = new Unidade("PO07", "Delegado Mauro César");
		Unidade un3 = new Unidade("BO13", "Cabo Diego Lima");
		
		listaUnidades.add(un);
		listaUnidades.add(un2);
		listaUnidades.add(un3);
		
		GeoPoint p1 = new GeoPoint(-22867037, -43238003);
		GeoPoint p2 = new GeoPoint(-22856044, -43244784);
		PDI carro2 = new Veiculo(Global.getNumPDI(), p1, "Q11", new TreeSet<Equipamento>(), new TreeSet<Equipe>(), "Q11", "Ambulância","Ambulância Comum", bombeiros);
		PDI casa2 = new Estrutura(Global.getNumPDI(), p2, "HOSPITAL01", new TreeSet<Equipe>(), new TreeSet<Perigo>(), new TreeSet<Vitima>(), "D'Or", "Hospital", 600, 0, 2, "Concreto", "Grande", 2, 4, 1, "Estacionamento", 2, 70, 45000, 12);
		
		Veiculo carro = new Veiculo(Global.getNumPDI(), new GeoPoint(-22864981, -43221525), "AIR01", new TreeSet<Equipamento>(), new TreeSet<Equipe>(), "A12", "Veículo Aéreo", "Avião Tanque", policia);
		
		Estrutura casa = new Estrutura(Global.getNumPDI(), new GeoPoint(-22854778, -43231995), "EDF01", new TreeSet<Equipe>(), new TreeSet<Perigo>(), new TreeSet<Vitima>(), "EDC314", "Edifício Comercial", 300, 1, 4, "Concreto", "Pequeno", 2, 4, 1, "Garagem", 2, 220, 15000, 25);
		
		TreeSet<Veiculo> setVeic = new TreeSet<Veiculo>();
		setVeic.add((Veiculo) carro2);
		Equipamento equip = new Equipamento(Global.getNumPDI(), new GeoPoint(-22864982, -43221525), "Equipamento", setVeic, new TreeSet<Equipe>(), "E01", "Marreta", 3, 1);
		
		Equipamento eq2 = new Equipamento(Global.getNumPDI(), new GeoPoint(-22850680, -43232910), "Eq2", new TreeSet<Veiculo>(), new TreeSet<Equipe>(), "Equipamento2", "Cordas", 5, 2);
		
		Equipe team = new Equipe(Global.getNumPDI(), new GeoPoint(-22869409, -43243153), "EX02", new TreeSet<Estrutura>(), new TreeSet<Equipamento>(), new TreeSet<Perigo>(), "E33", "Dr. Ivan", exc, un, 3, 12, 4, "Fiscalização");
		
		Perigo danger = new Perigo(Global.getNumPDI(), new GeoPoint(-22868777, -43218349), "SUBST01", new TreeSet<Equipe>(), new TreeSet<Vitima>(), "Substâncias Tóxicas", 3, "Sim", "PER01");
		
		Vitima victim = new Vitima(Global.getNumPDI(), new GeoPoint(-22862529, -43242037), "VIT04", new TreeSet<Estrutura>(), new TreeSet<Equipe>(), new TreeSet<Perigo>(), "V04", 25, 12, 13, 4, 1);
		
		criarOverlay(carro);
		criarOverlay(carro2);
		criarOverlay(casa);
		criarOverlay(casa2);
		criarOverlay(equip);
		criarOverlay(eq2);
		criarOverlay(team);
		criarOverlay(danger);
		criarOverlay(victim);
		
		TreeSet<Equipamento> set = new TreeSet<Equipamento>();
		set.add(equip);
		set.add(eq2);
		((Veiculo) carro2).setEquipamento(set);
    }

	private void getMapOverlays(){
    	List<Overlay> mapOverlays = mapView.getOverlays();
    	
    	if (!mapOverlays.contains(veiculoOverlays)) mapOverlays.add(veiculoOverlays);
    	if (!mapOverlays.contains(estruturaOverlays)) mapOverlays.add(estruturaOverlays);
    	if (!mapOverlays.contains(equipamentoOverlays)) mapOverlays.add(equipamentoOverlays);
    	if (!mapOverlays.contains(equipeOverlays)) mapOverlays.add(equipeOverlays);
    	if (!mapOverlays.contains(perigoOverlays)) mapOverlays.add(perigoOverlays);
    	if (!mapOverlays.contains(vitimaOverlays)) mapOverlays.add(vitimaOverlays);
    	
    	//redesenhar o mapa
    	mapView.postInvalidate();
    }
    
    public static void criarOverlay(PDI objeto){
    	switch (objeto.getTipo()){
    	case 0: break;
    	case VEICULO:
    		Veiculo vei = (Veiculo) objeto;
    		listaVeiculos.add(vei);
    		veiculoOverlays.addOverlay(vei.getOverlayItem(), vei);
    		break;
    	case ESTRUTURA:
    		Estrutura est = (Estrutura) objeto;
    		listaEstruturas.add(est);
    		estruturaOverlays.addOverlay(est.getOverlayItem(), est);
    		break;
    	case EQUIPAMENTO:
    		Equipamento eqp = (Equipamento) objeto;
    		listaEquipamentos.add(eqp);
    		equipamentoOverlays.addOverlay(eqp.getOverlayItem(), eqp);
    		break;
    	case EQUIPE:
    		Equipe team = (Equipe) objeto;
    		listaEquipes.add(team);
    		equipeOverlays.addOverlay(team.getOverlayItem(), team);
    		break;
    	case PERIGO:
    		Perigo prg = (Perigo) objeto;
    		listaPerigos.add(prg);
    		perigoOverlays.addOverlay(prg.getOverlayItem(), prg);
    		break;
    	case VITIMA:
    		Vitima vit = (Vitima) objeto;
    		listaVitimas.add(vit);
    		vitimaOverlays.addOverlay(vit.getOverlayItem(), vit);
    		break;
    	default: break;
    	}
    	mapaPDI.put(objeto.getId(), objeto);
    }
    
    /*TODO fazer validação dos campos de cada funcao a seguir
     * como id nao-vazio, ou nao poder ter dois IDs iguais, etc
     */
    
    private void carregaCriacaoUnidade(final Dialog criaUnidade){
    	final EditText etIdUnidade = (EditText) criaUnidade.findViewById(R.novo_unidade.etIdUnidade);
    	final EditText etChefeUnidade = (EditText) criaUnidade.findViewById(R.novo_unidade.etChefeUnidade);
    	final Button salvar = (Button) criaUnidade.findViewById(R.novo_unidade.btCriaUnid);
    	
    	salvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String nomeChefe = etChefeUnidade.getText().toString();
            	String identificacao = etIdUnidade.getText().toString();
            	
            	if(!identificacao.trim().equals("")){
            		Unidade unid = new Unidade(identificacao, nomeChefe);
                	listaUnidades.add(unid);
                	criaUnidade.dismiss();
                	exibeToast(getApplicationContext(), "Unidade \"" + identificacao + "\" criada com sucesso");
            	}else{
            		etIdUnidade.setError("Um ID deve ser definido para esta unidade");
            	}
			}
		});	
    }
    
    private void carregaMenusDesenho(RelativeLayout barraPrincipal){
    	barraDesenho = barraPrincipal;
    	
    	barraDesenho.setVisibility(View.VISIBLE);
    	iconesDesenhoBarra.setVisibility(View.VISIBLE);
    	textosDesenhoBarra.setVisibility(View.VISIBLE);
    	botoesPoligonoBarra.setVisibility(View.GONE);
        
    	desenhoLivre.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
            	exibeToast(getApplicationContext(), "Modo livre:\nToque na tela para traçar");
            	dLivre = true;
            	poligono = Desenho.LIVRE;
            }  
        });
    	
    	desenhoRegiao.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
            	exibeToast(getApplicationContext(), "Modo região:\nToque na tela para traçar e solte para fechar a região");
            	dLivre = true;
            	poligono = Desenho.REGIAO;
            }  
        });

        desenhoPoligono.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
            	iconesDesenhoBarra.setVisibility(View.GONE);
            	textosDesenhoBarra.setVisibility(View.GONE);
            	botoesPoligonoBarra.setVisibility(View.VISIBLE);
            }  
        });
        
        desenhoCores.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {            	
            	MainActivity.this.corDefinida = MainActivity.this.cor;
            	//colorpicker(); FIXME colorpicker nao está sendo inicializado corretamente
            	Toast.makeText(v.getContext(), "Consertar bug no colorpicker", Toast.LENGTH_SHORT).show(); //TODO retirar
            }  
        });

        desenhoBorracha.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {
            	apagar = true;
    	    	for (Overlay o : mapView.getOverlays()) {
    	    		if (o instanceof Desenho) ((Desenho) o).setApagar(apagar);
    			}
    	    	exibeToast(getApplicationContext(), "Ferramenta \"borracha\" selecionada\nClique em alguma região para apagá-la");
            }  
        });
        ////////////////////////////////////////////////////////////
        novoTriangulo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				poligono = Desenho.TRIANGULO;
				exibeToast(getApplicationContext(), "Posicione os vértices do triângulo");
				//barraDesenho.setVisibility(View.GONE);
				Global.desPoligono = true;
			}
		});
        
        novoQuadrilatero.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				poligono = Desenho.RETANGULO;
				exibeToast(getApplicationContext(), "Posicione os vértices do quadrilátero");
				//barraDesenho.setVisibility(View.GONE);
				Global.desPoligono = true;
			}
		});

        novoPentagono.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				poligono = Desenho.PENTAGONO;
				exibeToast(getApplicationContext(), "Posicione os vértices do pentágono");
				//barraDesenho.setVisibility(View.GONE);
				Global.desPoligono = true;
			}
		});
        
        ////////////////////////////////////////////////////////////
        fecharBarraDesenho.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iconesDesenhoBarra.setVisibility(View.GONE);
            	textosDesenhoBarra.setVisibility(View.GONE);
            	barraDesenho.setVisibility(View.GONE);
            	dLivre = false;
            	apagar = false;
            	exibeToast(MainActivity.this, "Modo \"mão\":\nMovimento do mapa habilitado");
			}
		});
    
    }
    
    private void colorpicker()
    {
    	br.com.site.AmbilWarnaDialog.OnAmbilWarnaListener listener = new br.com.site.AmbilWarnaDialog.OnAmbilWarnaListener() {
			
			@Override
			public void onOk(br.com.site.AmbilWarnaDialog dialog, int color) {
				String s = Integer.toHexString(color);
    			s = "33" + s.substring(2);
    			int corTransp = Integer.valueOf(s, 16).intValue();
    			corDefinida = cor = corTransp;
			}
			
			@Override
			public void onCancel(br.com.site.AmbilWarnaDialog dialog) {}
		};
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, this.corDefinida, listener);

        dialog.show();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	if(actionBar.getVisibility() == View.VISIBLE){
	    		esconderActionBar();
	    	}else{
	    		exibirActionBar();
	    	}
	    }
	    return super.onKeyDown(keyCode, event);
    }
       
    /**Definirei as funcoes do botao 'back' para evitar sair da aplicacao sem querer */
    @Override
    public void onBackPressed() {
    	if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
    		toast = Toast.makeText(this, "Toque em voltar novamente para sair da aplicação", 2000);
    		toast.show();
    		this.lastBackPressTime = System.currentTimeMillis();
    	}else{
    		if (toast != null) {
    			toast.cancel();
    		}
    		super.onBackPressed();
    	}
    }
    
    /**Exibe um toast simples com o texto */
    public static void exibeToast(Context context, String texto){
    	CharSequence textoToast = texto;
    	Toast toast = Toast.makeText(context, textoToast, Toast.LENGTH_SHORT);
    	toast.show();
    }
    
    private void exibirActionBar(){
    	actionBar.setVisibility(View.VISIBLE);
		findViewById(R.id.actionbar_princ).setVisibility(View.VISIBLE);
    	TranslateAnimation abEntrada = new TranslateAnimation(0, 0, -(actionBar.getHeight()), 0);
    	abEntrada.setDuration(650);
    	abEntrada.setFillEnabled(true);
    	abEntrada.setFillAfter(true);
    	actionBar.startAnimation(abEntrada);
    	
    	for (LinearLayout ll : actionBarBtns) {
			ll.setClickable(true);
		}
    }
    
    private void esconderActionBar(){
    	TranslateAnimation abSaida = new TranslateAnimation(0, 0, 0, -(actionBar.getHeight()));
    	abSaida.setDuration(650);
    	abSaida.setFillEnabled(true);
    	abSaida.setFillAfter(true);
    	actionBar.startAnimation(abSaida);
    	actionBar.setVisibility(View.GONE);
    	
    	for (LinearLayout ll : actionBarBtns) {
			ll.setClickable(false);
		}
    }
    
    /**metodo obrigatorio...avisa ao google que nao ha rota exibida */
    @Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	    
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
    	boolean permiteCriarPDI = Global.novoPdi;
    	
    	
    	//quando o usuario levantar o dedo, e quando novoPdi for true, desenharei o pdi
    	if (event.getAction() == MotionEvent.ACTION_DOWN){
    		if(permiteCriarPDI == true){
    			PDI objeto = objetoPassado;
    			GeoPoint projecao = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
	    		
	    		objeto.setGeoPoint(projecao);
	    		criarOverlay(objeto);
	    		mapView.postInvalidate();
	    		Global.novoPdi = false;

	    		String xml = "";
	    		if (objeto instanceof Veiculo) xml = ((Veiculo) objeto).getXML();
	    		else if (objeto instanceof Estrutura) xml = ((Estrutura) objeto).getXML();
	    		else if (objeto instanceof Equipamento) xml = ((Equipamento) objeto).getXML();
	    		else if (objeto instanceof Equipe) xml = ((Equipe) objeto).getXML();
	    		else if (objeto instanceof Perigo) xml = ((Perigo) objeto).getXML();
	    		else if (objeto instanceof Vitima) xml = ((Vitima) objeto).getXML();
	    		
	    		if(!Global.souServer){ //sou cliente
	    			ClientThread ct = ClientThread.getInstance(Global.serverIp);
					ct.send(xml, ClientThread.NOVOPDI);
	    		}else{ //sou o servidor
	    			/*ArrayList<String> al = new ArrayList<String>();
		    		al.add(xml);*///TODO deleteme
	    			Global.tellEveryone(ClientThread.NOVOPDI, xml); //TODO testar
	    		}
	    		
    		}else if (Global.desPoligono){
    			GeoPoint inicial = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
    			
    			if (cor == 0) cor = Desenho.VERMELHO; //vermelho padrão
    			if (poligono == 0) poligono = Desenho.RETANGULO; //retangulo padrão
    			Desenho des = new Desenho(mapView, inicial, cor, poligono);
    			mapView.getOverlays().add(des);
    			Global.desPoligono = false;
    		}
    	}
    	if(dLivre){
    		if(event.getAction() == MotionEvent.ACTION_DOWN){ //TODO codigo muito feio, arrumar isso aqui
    			if(isViewContains(desenhoLivre, (int) event.getX(), (int) event.getY())){
    				exibeToast(getApplicationContext(), "Modo livre:\nToque na tela para traçar");
                	dLivre = true;
                	poligono = Desenho.LIVRE;
                	return true;
    			}else if(isViewContains(desenhoRegiao, (int) event.getX(), (int) event.getY())){
    				exibeToast(getApplicationContext(), "Modo região:\nToque na tela para traçar e solte para fechar a região");
                	dLivre = true;
                	poligono = Desenho.REGIAO;
                	return true;
    			}else if(isViewContains(desenhoPoligono, (int) event.getX(), (int) event.getY())){
    				dLivre = false;
    				iconesDesenhoBarra.setVisibility(View.GONE);
                	textosDesenhoBarra.setVisibility(View.GONE);
                	botoesPoligonoBarra.setVisibility(View.VISIBLE);
                	return true;
    			}else if(isViewContains(desenhoCores, (int) event.getX(), (int) event.getY())){
    				Log.d("SiTE","clique");
    				MainActivity.this.corDefinida = MainActivity.this.cor;
                	colorpicker();
                	return true;
    			}else if(isViewContains(desenhoBorracha, (int) event.getX(), (int) event.getY())){
    				dLivre = false;
    				apagar = true;
        	    	for (Overlay o : mapView.getOverlays()) {
        	    		if (o instanceof Desenho) ((Desenho) o).setApagar(apagar);
        			}
        	    	exibeToast(getApplicationContext(), "Ferramenta \"borracha\" selecionada\nClique em alguma região para apagá-la");
        	    	return true;
    			}else if(isViewContains(fecharBarraDesenho, (int) event.getX(), (int) event.getY())){
    				dLivre = false;
    				apagar = false;
    				iconesDesenhoBarra.setVisibility(View.GONE);
                	textosDesenhoBarra.setVisibility(View.GONE);
                	barraDesenho.setVisibility(View.GONE);
        			return true;
    			}else{
    				if (cor == 0) cor = Desenho.VERMELHO;
    				GeoPoint gp = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
        			desLiv = new Desenho(mapView, gp, cor, poligono);
        			mapView.getOverlays().add(desLiv);
        			return true; //nao posso deixar o mapa se mover
    			}
    		}
    		if(event.getAction() == MotionEvent.ACTION_MOVE){
    			GeoPoint gp = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
    			desLiv.getList().add(gp);
    			mapView.invalidate();
    			return true;
    		}
    		if(event.getAction() == MotionEvent.ACTION_UP){
    			desLiv.fecharPath();
    			mapView.invalidate();
    			return true;
    		}
    	}
    	return super.dispatchTouchEvent(event);
    }
    
    private boolean isViewContains(View view, int rx, int ry) {
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        if (rx < x || rx > x + w || ry < y || ry > y + h) {
            return false;
        }
        return true;
    }
    
	public class CustomItemizedOverlay extends BalloonItemizedOverlay<CustomOverlayItem>{

		private ArrayList<CustomOverlayItem> m_overlays = new ArrayList<CustomOverlayItem>();
		private Context c;
		private ArrayList<Overlay> l_overlays = new ArrayList<Overlay>();

		private Drawable marker;
		
		public CustomItemizedOverlay(Drawable defaultMarker, MapView mapView) {
			super(boundCenter(defaultMarker), mapView);
			c = mapView.getContext();
			this.marker = defaultMarker;
			populate();
		}
		
		public void remove(CustomOverlayItem overlay, PDI objeto) {
			m_overlays.remove(overlay);
			populate();
			l_overlays.remove(objeto);
			setLastFocusedIndex(-1);
		}

		public void addOverlay(CustomOverlayItem overlay, PDI objeto) {
			int idDoIcone = getIconeCerto(objeto);
			if (idDoIcone != 0){
				Drawable icone = getResources().getDrawable(idDoIcone);
				icone.setBounds(this.marker.getBounds());
				overlay.setMarker(icone);
			}
			
			m_overlays.add(overlay);
		    populate();
		    l_overlays.add(objeto);
		    //pro caso de a lista ter sido limpa recentemente, vai evitar probs com o index
		    setLastFocusedIndex(-1); 
		}
		
		public ArrayList<Overlay> getL_overlays() {
			return l_overlays;
		}
		
		public Drawable getMarker() {
			return marker;
		}

		@Override
		protected OverlayItem createItem(int i) {
			return m_overlays.get(i);
		}
		
		public void clear() {
	        m_overlays.clear();
	        populate();
	        l_overlays.clear();
	    }

		@Override
		public int size() {
			return m_overlays.size();
		}
		
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, false); //retirar a sombra
		}
		
		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			if (this.m_overlays.size()>0){
				return super.onTap(p, mapView);
			}
			return false;
			
		}
		
		@Override
		protected boolean onBalloonTap(int index) {
			PDI pdi = (PDI) l_overlays.get(index);
			ExpansaoPDI dialog = new ExpansaoPDI(c, pdi);
			exibeToast(c, "ID do PDI = " + pdi.getId()); //TODO debug
			dialog.show();
			return true;
		}
	}
	
	public static int getIconeCerto(PDI objeto){
		int idDoIcone = 0; //padrao: se for 0 usara o icone padrao do pdi
		
		switch(objeto.getTipo()){
		case 0:
			break;
		case VEICULO:
			Veiculo vei = (Veiculo) objeto;
			if(vei.getTipoDeVeiculo().equals("Ambulância")){
				idDoIcone = R.drawable.ic_ambulancia;
			}else if (vei.getTipoDeVeiculo().equals("Veículo Aéreo")){
				idDoIcone = R.drawable.ic_veiculoaereo;
			}else if (vei.getTipoDeVeiculo().equals("Veículo de Socorro Terrestre")){
				idDoIcone = R.drawable.ic_veiculoterrestre;
			}else if (vei.getTipoDeVeiculo().equals("Veículo de Socorro Aquático")){
				idDoIcone = R.drawable.ic_veiculoaquatico;
			}
			break;
		case ESTRUTURA:
			Estrutura est = (Estrutura) objeto;
			if(est.getTipoDeEstrutura().equals("Hospital")){
				idDoIcone = R.drawable.ic_hospital;
			}else if (est.getTipoDeEstrutura().equals("Edifício")){
				idDoIcone = R.drawable.ic_predio;
			}else if (est.getTipoDeEstrutura().equals("Estádio")){
				idDoIcone = R.drawable.ic_estadio;
			}else if (est.getTipoDeEstrutura().equals("Casa")){
				idDoIcone = R.drawable.ic_casa;
			}else if (est.getTipoDeEstrutura().equals("Edifício Comercial")){
				idDoIcone = R.drawable.ic_prediocomercial;
			}else if (est.getTipoDeEstrutura().equals("Indústria")){
				idDoIcone = R.drawable.ic_industria;
			}else if (est.getTipoDeEstrutura().equals("Shopping Center")){
				idDoIcone = R.drawable.ic_shopping;
			}
			break;
		case EQUIPAMENTO:
			break; //nao tem subdivisao
		case EQUIPE:
			Equipe team = (Equipe) objeto;
			try{
				Instituicao i = team.getInst();
				if(i.getTipoDeInstituicao().equals("Bombeiros")){
					idDoIcone = R.drawable.ic_bombeiros;
				}else if(i.getTipoDeInstituicao().equals("Voluntário")){
					idDoIcone = R.drawable.ic_voluntario;
				}else if(i.getTipoDeInstituicao().equals("Def. Civil Mun.")
						|| i.getTipoDeInstituicao().equals("Def. Civil Estad.")){
					idDoIcone = R.drawable.ic_defesacivil;
				}else if(i.getTipoDeInstituicao().equals("Comlurb")){
					idDoIcone = R.drawable.ic_comlurb;
				}else if(i.getTipoDeInstituicao().equals("IBAMA")){
					idDoIcone = R.drawable.ic_ibama;
				}else if(i.getTipoDeInstituicao().equals("CET-Rio")
						|| i.getTipoDeInstituicao().equals("CEG")
						|| i.getTipoDeInstituicao().equals("IML")
						|| i.getTipoDeInstituicao().equals("Light")
						|| i.getTipoDeInstituicao().equals("CEDAE")
						|| i.getTipoDeInstituicao().equals("Geo-Rio")
						|| i.getTipoDeInstituicao().equals("CNEN")){
					idDoIcone = R.drawable.ic_public;
				}else if(i.getTipoDeInstituicao().equals("INEA")){
					idDoIcone = R.drawable.ic_inea;
				}else if (i.getTipoDeInstituicao().equals("Polícia Militar")
						|| i.getTipoDeInstituicao().equals("Polícia Civil")){
					idDoIcone = R.drawable.ic_policia;
				}else if (i.getTipoDeInstituicao().equals("Engenheiros Civis")){
					idDoIcone = R.drawable.ic_engenheiro;
				}else if (i.getTipoDeInstituicao().equals("Exército")
						|| i.getTipoDeInstituicao().equals("Marinha")
						|| i.getTipoDeInstituicao().equals("Aeronáutica")){
					idDoIcone = R.drawable.ic_militares;
				}
			}catch (NullPointerException e){
				
			}
			break;
		case PERIGO:
			Perigo per = (Perigo) objeto;
			if(per.getTipoDePerigo().equals("Gases Tóxicos")){
				idDoIcone = R.drawable.ic_gastoxico;
			}else if (per.getTipoDePerigo().equals("Líquidos Tóxicos")){
				idDoIcone = R.drawable.ic_liquido;
			}else if (per.getTipoDePerigo().equals("Substâncias Tóxicas")){
				idDoIcone = R.drawable.ic_quimica;
			}else if (per.getTipoDePerigo().equals("Exposição Radioativa")){
				idDoIcone = R.drawable.ic_radioativo;
			}else if (per.getTipoDePerigo().equals("Deslizamento de Terra")){
				idDoIcone = R.drawable.ic_pedra;
			}else if (per.getTipoDePerigo().equals("Colapso de Edifício")){
				idDoIcone = R.drawable.ic_prediocolapso;
			}else if (per.getTipoDePerigo().equals("Ratos")){
				idDoIcone = R.drawable.ic_rato;
			}
			break;
		case VITIMA:
			break; //nao tem subdivisao
		default: break;
		}
		
		return idDoIcone;
	}

	public static void editarPDI(Context c, OverlayItem overlayitem){
		CustomOverlayItem coi = (CustomOverlayItem) overlayitem;
		PDI pdi = coi.getPdi();
		
		Intent intent;
		
		Veiculo ve;
		Estrutura es;
		Equipe te;
		Equipamento eq;
		Perigo pe;
		Vitima vi;
		
		Global.editarPDI=true;
		
		if(pdi instanceof Veiculo){
			ve = (Veiculo) pdi;
			Global.pdiEdicao = ve;
			intent = new Intent(c, NovoVeiculo.class);
	    	((Activity) c).startActivity(intent);
		}else if(pdi instanceof Estrutura){
			es = (Estrutura) pdi;
			Global.pdiEdicao = es;
			intent = new Intent(c, NovoEstrutura.class);
	    	((Activity) c).startActivity(intent);
		}else if(pdi instanceof Equipamento){
			eq = (Equipamento) pdi;
			Global.pdiEdicao = eq;
			intent = new Intent(c, NovoEquipamento.class);
	    	((Activity) c).startActivity(intent);
		}else if(pdi instanceof Equipe){
			te = (Equipe) pdi;
			Global.pdiEdicao = te;
			intent = new Intent(c, NovoEquipe.class);
	    	((Activity) c).startActivity(intent);
		}else if(pdi instanceof Perigo){
			pe = (Perigo) pdi;
			Global.pdiEdicao = pe;
			intent = new Intent(c, NovoPerigo.class);
	    	((Activity) c).startActivity(intent);
		}else if(pdi instanceof Vitima){
			vi = (Vitima) pdi;
			Global.pdiEdicao = vi;
			intent = new Intent(c, NovoVitima.class);
	    	((Activity) c).startActivity(intent);
		}
	}
	
	public static void moverPDI(Context c, OverlayItem overlayitem){
		//TODO funcao mover pdi que vai modificar o geopoint onde o pdi esta
		exibeToast(c, "Ainda vai ser implementado :)");
	}
	
	public static void hideBalloons(){
		handler.post(new Runnable() {
            @Override
            public void run() {
            	for (CustomItemizedOverlay cio : listaDeOverlays) {
        			cio.hideBalloon();
        		}
            }
        });
	}
	
	public static MapView getMapView() {
		return mapView;
	}
	
}