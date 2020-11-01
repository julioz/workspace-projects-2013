package br.com.site.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.site.R;
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Veiculo;
import br.com.site.model.Vitima;

public class ExpansaoPDI extends Dialog {

	protected static final int VEICULO = 1;
	protected static final int ESTRUTURA = 2;
	protected static final int EQUIPAMENTO = 3;
	protected static final int EQUIPE = 4;
	protected static final int PERIGO = 5;
	protected static final int VITIMA = 6;
	
	protected final int CORDOBOTAO = 0xFFFFFF00;
	protected final int CORDABORDA = 0x00D2691E;
	
	private Context context;
	private TableLayout tlDialog;
	private Button bt0, bt1, bt2, bt3;
	private ArrayList<Button> alBotoesTitulo = new ArrayList<Button>();
	private PDI pdi;
	private LinkedHashMap<String, String> map;
	private LinearLayout llSets;
	private RelativeLayout barraSets;
	
	public ExpansaoPDI(Context context, PDI pdi) {
		super(context);
		this.context = context;
		this.pdi = pdi;
		
		map = new LinkedHashMap<String, String>();
		
		getWindow();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.custom_dialog);
		this.setCanceledOnTouchOutside(true);
		
		carregarViews();
		
		povoarBotoes(this.pdi);
		
		this.map = povoarMapa(this.pdi);
		
		completarTabela(this.map);
	}

	private void carregarViews() {
		tlDialog = (TableLayout) findViewById(R.customdialog.tlDialog);
		bt0 = (Button) findViewById(R.customdialog.bt0);
		bt1 = (Button) findViewById(R.customdialog.bt1);
		bt2 = (Button) findViewById(R.customdialog.bt2);
		bt3 = (Button) findViewById(R.customdialog.bt3);
		
		alBotoesTitulo.add(bt0);
		alBotoesTitulo.add(bt1);
		alBotoesTitulo.add(bt2);
		alBotoesTitulo.add(bt3);		
		
		llSets = (LinearLayout) findViewById(R.customdialog.linearLayoutSets);
		barraSets = (RelativeLayout) findViewById(R.customdialog.dialogBarraSets);
		
		for (Button bt : alBotoesTitulo) bt.setPadding(bt.getPaddingLeft()+6, bt.getPaddingTop(), bt.getPaddingRight()+6, bt.getPaddingBottom());
	}
	
	private void povoarBotoes(PDI pdi) {
		if(pdi instanceof Veiculo){
			Veiculo v = (Veiculo) pdi;
			setBotoes("Veículo", "Equipam.", "Equipe", null, v.getEquipamento(), v.getEquipe(), null);
		}
		else if(pdi instanceof Estrutura){
			Estrutura e = (Estrutura) pdi;
			setBotoes("Estrut.", "Equipe", "Perigo", "Vitima", e.getEquipe(), e.getPerigo(), e.getVitima());
		}
		else if(pdi instanceof Equipamento){
			Equipamento e = (Equipamento) pdi;
			setBotoes("Equipam.", "Veículo", "Equipe", null, e.getVeiculo(), e.getEquipe(), null);
		}
		else if(pdi instanceof Equipe){
			Equipe e = (Equipe) pdi;
			setBotoes("Equipe", "Estrut.", "Equipam.", "Perigo", e.getEstrutura(), e.getEquipamento(), e.getPerigo());
		}
		else if(pdi instanceof Perigo){
			Perigo p = (Perigo) pdi;
			setBotoes("Perigo", "Equipe", "Vítima", null, p.getEquipe(), p.getVitima(), null);
		}
		else if(pdi instanceof Vitima){
			Vitima v = (Vitima) pdi;
			setBotoes("Vítima", "Estrut.", "Equipe", "Perigo", v.getEstrutura(), v.getEquipe(), v.getPerigo());
		}
	}

	private LinkedHashMap<String, String> povoarMapa(PDI pdi){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if(pdi instanceof Veiculo){
			Veiculo v = (Veiculo) pdi;
			map.put("Identificação:", v.getIdentificacao());
			map.put("Tipo de Veículo:", v.getTipoDeVeiculo());
			map.put("Subtipo:", v.getSubtipo());
			if(v.getInst() != null){
				map.put("Instituição:", v.getInst().getTipoDeInstituicao());
				map.put("Chefe da Inst.:", v.getInst().getChefeDaInstituicao());
			}
		}
		else if(pdi instanceof Estrutura){
			Estrutura e = (Estrutura) pdi;
			map.put("Identificação:", e.getIdentificacao());
			map.put("Tipo de Estrutura:", e.getTipoDeEstrutura());
			map.put("Afluência de Público:", String.valueOf(e.getAfluenciaPublico()));
			map.put("Estado da Revisão:", e.getEstadoDaRevisao());
			map.put("Estabilidade:", e.getEstabilidade());
			map.put("Tipo de Material:", e.getTipoDeMaterial());
			map.put("Tamanho do Acesso:", e.getTamanhoDoAcesso());
			map.put("Dificuldade de Acesso:", e.getDificuldadeDeEntrada());
			map.put("Número de Andares:", String.valueOf(e.getNumAndares()));
			map.put("Média de Pessoas\npor Andar:", String.valueOf(e.getQtdPessoasPiso()));
			map.put("Resistência do Piso:\n(em kg)", String.valueOf(e.getResistenciaPiso()));
			map.put("Tipo de Subterrâneo:", e.getTipoDeSubsolo());
			map.put("Número de Subterrâneos:", String.valueOf(e.getNumSubsolos()));
			map.put("Tempo Estimado\nde Acesso: (em min)", String.valueOf(e.getTempoAcesso()));
			map.put("Evolução do Trabalho:", e.getEvolucaoDoTrabalho());
		}
		else if(pdi instanceof Equipamento){
			Equipamento e = (Equipamento) pdi;
			map.put("Identificação:", e.getIdentificacao());
			map.put("Tipo de Equipamento:", e.getTipoDeEquipamento());
			map.put("Quantidade:", String.valueOf(e.getQuantidade()));
			map.put("Disponíveis:", String.valueOf(e.getDisponiveis()));
		}
		else if(pdi instanceof Equipe){
			Equipe e = (Equipe) pdi;
			map.put("Identificação:", e.getIdentificacao());
			map.put("Chefe da Equipe:", e.getChefe());
			map.put("Quantidade de Membros:", String.valueOf(e.getQtdMembros()));
			map.put("Quantidade de Membros Feridos:", String.valueOf(e.getQtdMembrosFeridos()));
			map.put("Tarefa Atual:", e.getTarefaAtual());
			map.put("Tipo de Função:", e.getTipoDeFuncao());
			if(e.getInst() != null){
				map.put("Instituição:", e.getInst().getTipoDeInstituicao());
				map.put("Chefe da Inst.:", e.getInst().getChefeDaInstituicao());
			}
			if(e.getUnid() != null){
				map.put("Unidade:", e.getUnid().getUnidade());
				map.put("Chefe da Unid.:", e.getUnid().getChefeDaUnidade());
			}
		}
		else if(pdi instanceof Perigo){
			Perigo p = (Perigo) pdi;
			map.put("Identificação:", p.getIdentificacao());
			map.put("Tipo de Perigo:", p.getTipoDePerigo());
			map.put("Risco Associado:", p.getRiscoAssociado());
			map.put("Entrada Segura:", p.getDevoIr());
		}
		else if(pdi instanceof Vitima){
			Vitima v = (Vitima) pdi;
			map.put("Identificação:", v.getIdentificacao());
			map.put("Número de Possíveis Vítimas:", String.valueOf(v.getNumeroPossiveisVitimas()));
			map.put("Quantidade de Vivos:", String.valueOf(v.getQtdVivos()));
			map.put("Quantidade de Mortos:", String.valueOf(v.getQtdMortos()));
			map.put("Vivos Resgatados:", String.valueOf(v.getQtdVivosResgatados()));
			map.put("Mortos Removidos:", String.valueOf(v.getQtdMortosRemovidos()));
		}
		
		return map;
	}
	
	private void completarTabela(LinkedHashMap<String, String> map){
		Set<Map.Entry<String, String>> set = map.entrySet();
	    Iterator<Map.Entry<String, String>> i = set.iterator();
	    
	    tlDialog.removeAllViews(); //TODO o redimensionamento do dialog é bizarro!
	    
	    while(i.hasNext()){
	      Map.Entry<String, String> entrada = (Map.Entry<String, String>) i.next();
	      
	      TableRow tr = new TableRow(context);
	      
	      TextView atributo = getTextView(entrada.getKey());
	      atributo.setPadding(atributo.getPaddingLeft(), atributo.getPaddingTop(), atributo.getPaddingRight()+15, atributo.getPaddingBottom());
	      atributo.setGravity(Gravity.LEFT);
	      
	      if(atributo.getText().toString().equals("Instituição:") || atributo.getText().toString().equals("Unidade:")){
	    	  atributo.setTypeface(null, Typeface.BOLD);
	    	  atributo.setPadding(atributo.getPaddingLeft(), atributo.getPaddingTop()+10, atributo.getPaddingRight(), atributo.getPaddingBottom());
	      }
	      
	      TextView valor = getTextView(entrada.getValue());
	      valor.setGravity(Gravity.RIGHT);
	      
	      tr.addView(atributo);
	      tr.addView(valor);
	      
	      tlDialog.addView(tr);
	    }
	}
	
	private void setBotoes(String s0, String s1, String s2, String s3,
			final PDI p1, final PDI p2, final PDI p3) {
		
		colorirBt(bt0, alBotoesTitulo);
		
		//se a string for igual a null, o atributo nao existe
		//entao posso sumir com o botao
		
		if(s0 == null) this.bt0.setVisibility(View.GONE);
		else this.bt0.setText(s0);
		
		if(s1 == null) this.bt1.setVisibility(View.GONE);
		else this.bt1.setText(s1);
		
		if(s2 == null) this.bt2.setVisibility(View.GONE);
		else this.bt2.setText(s2);
		
		if(s3 == null) this.bt3.setVisibility(View.GONE);
		else this.bt3.setText(s3);
		
		bt0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				completarTabela(povoarMapa(ExpansaoPDI.this.pdi));
				colorirBt((Button) v, alBotoesTitulo);
			}
		});
		
		
		//se o pdi atrelado for igual a null, preciso indicar ao user que
		//nao ha nenhum link, mas que ele PODE ser feito, entao, so irei
		//desativar o botao
		
		if(p1 == null) bt1.setEnabled(false);
		else{
			bt1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					completarTabela(povoarMapa(p1));
					colorirBt((Button) v, alBotoesTitulo);
				}
			});
		}
		
		if(p2 == null) bt2.setEnabled(false);
		else{
			bt2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					completarTabela(povoarMapa(p2));
					colorirBt((Button) v, alBotoesTitulo);
				}
			});
		}
		
		if(p3 == null) bt3.setEnabled(false);
		else{
			bt3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					completarTabela(povoarMapa(p3));
					colorirBt((Button) v, alBotoesTitulo);
				}
			});
		}
	}
	
	private void setBotoes(String s0, String s1, String s2, String s3,
			final Set<? extends PDI> set1,
			final Set<? extends PDI> set2, final Set<? extends PDI> set3) {
		
		colorirBt(bt0, alBotoesTitulo);
		
		//se a string for igual a null, o atributo nao existe
		//entao posso sumir com o botao
		
		if(s0 == null) this.bt0.setVisibility(View.GONE);
		else this.bt0.setText(s0);
		
		if(s1 == null) this.bt1.setVisibility(View.GONE);
		else this.bt1.setText(s1);
		
		if(s2 == null) this.bt2.setVisibility(View.GONE);
		else this.bt2.setText(s2);
		
		if(s3 == null) this.bt3.setVisibility(View.GONE);
		else this.bt3.setText(s3);
		
		bt0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				barraSets.setVisibility(View.GONE);
				completarTabela(povoarMapa(ExpansaoPDI.this.pdi));
				colorirBt((Button) v, alBotoesTitulo);
			}
		});
		
		setButtonBarra(bt1, set1);
		setButtonBarra(bt2, set2);
		setButtonBarra(bt3, set3);
	}
	
	private void setButtonBarra(final Button bt,  final Set<? extends PDI> set){//, final int numBotao){
		/*se o pdi atrelado for igual a null, preciso indicar ao user que
		nao ha nenhum link, mas que ele PODE ser feito, entao, so irei
		desativar o botao*/
		if(set == null || set.isEmpty()){
			barraSets.setVisibility(View.GONE);
			bt.setEnabled(false);
		}
		else{
			bt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int i = 0;
					
					llSets.removeAllViews();
					completarTabela(povoarMapa(set.iterator().next()));
					if(set.size() > 1){
						barraSets.setVisibility(View.VISIBLE);
						final ArrayList<Button> al = new ArrayList<Button>();
						for (Iterator<? extends PDI> it = set.iterator(); it.hasNext();) {
							final PDI pdi = (PDI) it.next();
							i++;
							Button bt = new Button(context);
							bt.setText(String.valueOf(i));
							bt.setGravity(Gravity.CENTER);
							bt.setBackgroundColor(Color.TRANSPARENT);
							bt.setTextSize(12);
							bt.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							al.add(bt);
							
							View barraVertical = new View(v.getContext());
							barraVertical.setLayoutParams(new LayoutParams(1, LayoutParams.FILL_PARENT));
							barraVertical.setBackgroundColor(Color.BLACK);
							
							bt.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									colorirBt((Button) v, al);
									completarTabela(povoarMapa(pdi));
								}
							});
							
							colorirBt(al.get(0), al);
							llSets.addView(barraVertical);
							llSets.addView(bt);
						}
						
						View barraVertical = new View(v.getContext());
						barraVertical.setLayoutParams(new LayoutParams(1, LayoutParams.FILL_PARENT));
						barraVertical.setBackgroundColor(Color.BLACK);
						llSets.addView(barraVertical);
					}else{
						barraSets.setVisibility(View.GONE);
					}
					colorirBt(bt, alBotoesTitulo);
				}
			});
		}
	}
	
	private void colorirBt(Button clicado, ArrayList<Button> al){
		for (Button button : al) {
			button.getBackground().setColorFilter(null);
			button.setTypeface(null, Typeface.NORMAL);
		}
		clicado.getBackground().setColorFilter(new LightingColorFilter(this.CORDOBOTAO, this.CORDABORDA));
		clicado.setTypeface(null, Typeface.BOLD);
	}

	private TextView getTextView(String valor){
		TextView tv = new TextView(context);
		tv.setTextColor(Color.BLACK);
		tv.setText(valor);
        tv.setTextSize(14);
        
        return tv;
	}
}