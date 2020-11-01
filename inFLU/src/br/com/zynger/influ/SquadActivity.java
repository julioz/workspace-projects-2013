package br.com.zynger.influ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import br.com.zynger.influ.model.Player;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;

public class SquadActivity extends Activity {

	private ArrayList<Player> alGoleiros = new ArrayList<Player>();
	private ArrayList<Player> alZagueiros = new ArrayList<Player>();
	private ArrayList<Player> alLaterais = new ArrayList<Player>();
	private ArrayList<Player> alVolantes = new ArrayList<Player>();
	private ArrayList<Player> alMeias = new ArrayList<Player>();
	private ArrayList<Player> alAtacantes = new ArrayList<Player>();
	
	private Theme theme;
	
	private ExpandableListView elv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.squad);
		elv = (ExpandableListView) findViewById(R.squad.expandablelv);
		elv.setCacheColorHint(0x00000000);
		updateTheme();
		loadGoalkeepers();
		loadDefenders();
		loadSidePlayers();
		loadVolants();
		loadMidfielders();
		loadForwards();

		elv.setAdapter(new MyExpandableListAdapter(this, createGroupList(), R.layout.squad_group, new String[] {"Posicao"}, new int[] {R.exlvgroup.row_name},
				createChildList(), R.layout.squad_child,
				new String[] {"Foto", "Nome", "Naturalidade", "AlturaPeso", "Numero"},
				new int[] {R.exlvchild.child_photo, R.exlvchild.child_name, R.exlvchild.child_naturality, R.exlvchild.child_heightweight, R.exlvchild.child_number}));
		elv.setOnChildClickListener(getOnChildClickListener());
	}
	
	private void updateTheme() {
		theme = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		findViewById(R.squad.ll_actbg).setBackgroundColor(theme.getAct_background());
		int colors[] = { theme.getAbgradstart() , theme.getAbgradend() };
		findViewById(R.squad.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.squad.ll_actbg).invalidate();
		elv.setCacheColorHint(0x00000000);
	}

	/* Creating the Hashmap for the row */
	private List<HashMap<String, String>> createGroupList() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> hmGoleiro = new HashMap<String, String>();
		HashMap<String, String> hmZagueiro = new HashMap<String, String>();
		HashMap<String, String> hmLateral = new HashMap<String, String>();
		HashMap<String, String> hmVolante = new HashMap<String, String>();
		HashMap<String, String> hmMeia = new HashMap<String, String>();
		HashMap<String, String> hmAtacante = new HashMap<String, String>();

		hmGoleiro.put("Posicao", "GOLEIROS");
		hmZagueiro.put("Posicao", "ZAGUEIROS");
		hmLateral.put("Posicao", "LATERAIS");
		hmVolante.put("Posicao", "VOLANTES");
		hmMeia.put("Posicao", "MEIAS");
		hmAtacante.put("Posicao", "ATACANTES");

		result.add(hmGoleiro);
		result.add(hmZagueiro);
		result.add(hmLateral);
		result.add(hmVolante);
		result.add(hmMeia);
		result.add(hmAtacante);
		return result;
	}

	private List<ArrayList<HashMap<String, String>>> createChildList() {
		ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
		result.add(populateHashMap(alGoleiros));
		result.add(populateHashMap(alZagueiros));
		result.add(populateHashMap(alLaterais));
		result.add(populateHashMap(alVolantes));
		result.add(populateHashMap(alMeias));
		result.add(populateHashMap(alAtacantes));
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<HashMap<String, String>> populateHashMap(ArrayList<Player> al){
		ArrayList<HashMap<String, String>> hashmap = new ArrayList<HashMap<String, String>>();		
		for (Player player : al) {
			HashMap hm = new HashMap();
			hm.put("Foto", player.getImage());
			hm.put("Nome", player.getName());
			hm.put("Naturalidade", player.getNaturality());
			hm.put("AlturaPeso", player.getHeightWeight());
			hm.put("Numero", player.getNumber());
			hashmap.add(hm);
		}
		return hashmap;
	}
	
	public OnChildClickListener getOnChildClickListener() {
		OnChildClickListener occl = new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
					int childPosition, long id) {
				Player s = null;
				if(groupPosition == 0) s = alGoleiros.get(childPosition);
				else if(groupPosition == 1) s = alZagueiros.get(childPosition);
				else if(groupPosition == 2) s = alLaterais.get(childPosition);
				else if(groupPosition == 3) s = alVolantes.get(childPosition);
				else if(groupPosition == 4) s = alMeias.get(childPosition);
				else if(groupPosition == 5) s = alAtacantes.get(childPosition);
				
				if(s != null){			
					Intent it = new Intent(v.getContext(), PlayerInformationActivity.class);
					it.putExtra("NAME", s.getName());
					it.putExtra("COMPLETENAME", s.getCompleteName());
					it.putExtra("ARRIVAL", s.getArrival());
					it.putExtra("BORN", s.getBorn());
					it.putExtra("HW", s.getHeightWeight());
					it.putExtra("IMAGE", ((BitmapDrawable) s.getImage()).getBitmap());
					it.putExtra("LASTCLUB", s.getLastClub());
					it.putExtra("NATURALITY", s.getNaturality());
					it.putExtra("NUMBER", s.getNumber());
					it.putExtra("POSITION", s.getPosition());
					it.putExtra("DESCRIPTION", s.getDescription());
					startActivity(it);
					return true;
				}else{
					Toast.makeText(v.getContext(), "Erro SA01", Toast.LENGTH_SHORT).show();
					Log.e("inFLU", "Erro SA01");
					return false;
				}
			}
		};
		return occl;
	}

	public void onGroupExpand (int groupPosition){
		System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
	}
	
	private void loadGoalkeepers(){
		alGoleiros.add(new Player("Diego Cavalieri",
				getResources().getDrawable(R.drawable.elenco_cavalieri), "Diego Cavalieri",
				0, 12, "São Paulo (SP)", "01/12/1982", "1,91m / 86Kg", "Dezembro de 2010", "Cesena (ITA)",
				"Frieza e habilidade debaixo das traves. Diego Cavalieri foi destaque nas categorias de base do Palmeiras e companhia constante do pentacampeão do mundo, Marcos.<br /><br />As boas defesas, visão de jogo e habilidade em pegar pênaltis fazem de Cavalieri o terror dos atacantes na hora da cobrança. Este ano, pegou pênalti decisivo cobrado por Andreas D’Alessandro na partida que o Tricolor vencia por 1 a 0 o Internacional no Engenhão. O jogo terminou 2 a 0 para o Flu.<br /><br />Outra característica do goleiro é que ele sempre tem boas atuações em partidas decisivas.<br /><br />Em 2007, Cavalieri foi eleito um dos melhores goleiros do país ainda quando defendia o Palmeiras. No ano seguinte, foi para o Liverpool da Inglaterra e depois de dois anos, foi transferido para o Cesena da Itália.<br /><br />Chegou ao Flu no fim de dezembro de 2010."));
		
		alGoleiros.add(new Player("Ricardo Berna",
				getResources().getDrawable(R.drawable.elenco_berna), "Ricardo Ferreira Berna",
				0, 1, "São Paulo (SP)", "11/06/1979", "1,88m / 83Kg", "Janeiro de 2005", "América-MG",
				"Perseverança. Esta é palavra que pode resumir a vida de Ricardo Berna no Fluminense.<br /><br />Dedicado, rápido e com excelente reflexo, Berna é o atleta com mais tempo de casa. Está no Fluminense desde 2005.<br /><br />Reserva de Fernando Henrique até o meados de 2010, o arqueiro nunca deixou de acreditar no seu potencial. Assumiu a titularidade na reta final da campanha do tricampeonato brasileiro.<br /><br />O empate sem gols contra o Internacional no Beira Rio, na fase decisiva da competição, foi determinante para sua titularidade no gol tricolor. Aliás, aquele jogo ficou conhecido como o “Milagre de Berna”. Ao ser perguntado no final, se o jogo foi para provar que ele podia ser titular do gol do Flu, Berna não titubeou e respondeu: “Isso vai ajudar o Fluminense a conquistar o título? Estou aqui para ajudar o Fluminense a ser campeão brasileiro”.<br /><br />E foi e com Berna de titular."));

		alGoleiros.add(new Player("Kléver",
				getResources().getDrawable(R.drawable.elenco_klever), "Klever Gomes Rufino",
				0, 33, "Mirassol (SP)", "20/6/1989", "1,88m / 82Kg", "Janeiro de 2009", "Divisão de base",
				"Cria de Xerém, Klever seguiu o caminho natural de todo jogador profissional com raízes no Fluminense. Realizou um belo trabalho e foi chamado para integrar o time profissional.<br /><br />Antes de subir definitivamente para os profissionais, Kléver foi emprestado ao Volta Redonda. Sua agilidade e rapidez em baixo da baliza chamam a atenção.<br /><br />Sempre elogiado pelos treinadores de goleiros que passaram pelo Flu, o goleiro tem tudo para ser um dos grandes nomes da posição no Fluminense."));
		
	}

	private void loadDefenders(){
		alZagueiros.add(new Player("Anderson",
				getResources().getDrawable(R.drawable.elenco_anderson), "Anderson Grasiane de Mattos Silva",
				1, 15, "Itaperuna (RJ)", "26/08/1982", "1,89m / 82kg", "Janeiro de 2012", "Atlético-GO",
				"Anderson nasceu em Itaperuna, Rio de Janeiro, e se profissionalizou no Americano, em 2006.<br /><br />O zagueiro, que se destacou jogando o Campeonato Brasileiro de 2011 pelo Atlético-GO, já jogou pelo Gama e pelo Ceará.<br /><br />Anderson chega ao Fluminense entusiasmado para jogar sua primeira Libertadores da América e disse em entrevista que essa oportunidade chegou em uma hora certa, com 29 anos de idade e com certa experiência na bagagem adquirida nos últimos anos."));
		
		alZagueiros.add(new Player("Digão",
				getResources().getDrawable(R.drawable.elenco_digao), "Rodrigo Junior Paula Silva",
				1, 13, "Duque de Caxias (RJ)", "07/05/1988", "1,87m / 85Kg", "Profissional desde 2008", "Divisão de base",
				"A torcida tricolor tem um carinho especial com os jogadores que cresceram nas divisões de base do clube. Com Digão não tem sido diferente.<br /><br />Com apenas um ano de profissional, o jovem zagueiro foi um dos destaques da arrancada do Fluminense em 2009, quando o tricolor fugiu do rebaixamento em uma série inacreditável de jogos. Digão tornou-se querido dos torcedores e tem tudo para se tornar mais um ídolo de Laranjeiras.<br /><br />Após um ano difícil em 2010, quando ficou de fora de muitas partidas por problemas médicos, em 2011 vai retomando o melhor de seu futebol. Elogiado pelo técnico Abel Braga, Digão é um zagueiro vigoroso, que costuma levar vantagem em todas as divididas."));
		
		alZagueiros.add(new Player("Elivélton",
				getResources().getDrawable(R.drawable.elenco_elivelton), "Elivélton Viana dos Santos",
				1, 14, "Bom Jardim (RJ)", "10/05/1992", "1,79m / 81Kg", "Profissional desde 2011", "Divisão de base",
				"O zagueiro Elivélton, de apenas 19 anos, encantou o técnico Abel Braga, assim como já havia feito com Enderson Moreira.<br /><br />Rápido e eficaz nos desarmes, fez o atual técnico e ex-zagueiro, dizer que o Fluminense está bem servido na posição por uns bons anos.<br /><br />Perguntado sobre de onde vem a inspiração para jogar futebol, Elivélton respondeu: Thiago Silva.<br /><br />Elivélton tem sido relacionado algumas vezes como opção no banco de reservas, mas já atuou como titular. A torcida tricolor tem grandes expectativas com relação a esta jovem promessa."));
		
		alZagueiros.add(new Player("Gum",
				getResources().getDrawable(R.drawable.elenco_gum), "Welington Pereira Rodrigues",
				1, 3, "São Paulo (SP)", "04/01/1986", "1,89m / 86Kg", "Agosto de 2009", "Ponte Preta",
				"Quando o \"Time de Guerreiros\" é citado, o zagueiro Gum tem um lugar especial na memória da torcida. Seu empenho em campo o coloca como um dos símbolos de luta da equipe, sinônimo de raça quando veste a camisa do Fluminense.<br /><br />Em 2009, na semifinal da Sul-Americana, recebeu uma cotovelada de um jogador do Cerro Porteño que causou uma ferida no supercílio. Cotado para sair do jogo, o guerreiro pediu para permanecer em campo, enfaixado, e acabou fazendo o gol decisivo que levou a equipe para a grande final.<br /><br />Em 2010, Gum formou a zaga menos vazada do Campeonato Brasileiro ao lado de Leandro Euzébio. A dupla de zagueiros tem como um dos pontos fortes o ataque com bolas altas na área do adversário."));
		
		alZagueiros.add(new Player("Leandro Euzébio",
				getResources().getDrawable(R.drawable.elenco_leandro_euzebio), "Leandro da Fonseca Euzébio",
				1, 4, "Cabo Frio (RJ)", "18/08/1981", "1,87m / 81Kg", "Janeiro de 2010", "Goiás",
				"Leandro Euzébio chegou ao Fluminense depois de fazer um bom campeonato pelo Goiás em 2009. Por causa da oportunidade, a emoção tomou conta do grande zagueiro de 1,87m. Ao se apresentar, disse estar realizando um sonho do já falecido pai.<br /><br />Porém, esta não foi a primeira vez que o zagueiro vestiu a camisa tricolor. Formado nas divisões de base, Leandro Euzébio foi transferido do clube em 2000.<br /><br />Ao retornar dez anos depois, formou ao lado de Gum a zaga menos vazada do Campeonato Brasileiro de 2010.<br /><br />Forte na bola aérea, o zagueiro marcou cinco gols importantes na campanha do Tricampeonato Brasileiro."));
		
		alZagueiros.add(new Player("Wellington Carvalho",
				getResources().getDrawable(R.drawable.elenco_wellingtoncarvalho), "Wellington Carvalho de Souza",
				1, 34, "Duque de Caxias (RJ)", "15/02/1993", "1,85m / 84kg", "Profissional desde 2012", "Divisão de base",
				"Força e técnica com o DNA dos guerreiros tricolores. Wellington Carvalho chegou ao Fluminense em 2007, para fazer um teste, e acabou no time infantil. Conquistou títulos e a confiança de todos os treinadores que o comandaram em Xerém. Com seriedade e humildade, chamou a atenção do técnico Abel Braga e estreou no time principal no Brasileiro de 2012, na vitória por 1 a 0 sobre o Corinthians, no Pacaembu, em São Paulo. Convocado para a Seleção Brasileira sub-20 que disputou o Torneio Oito Nações, na África do Sul, Wellington Carvalho foi titular na conquista da competição pelo Brasil."));
		
	}
	
	private void loadSidePlayers() {
		alLaterais.add(new Player("Carlinhos",
				getResources().getDrawable(R.drawable.elenco_carlinhos), "Carlos Andrade Souza",
				2, 6, "Vitória da Conquista (BA)", "23/01/1987", "1,75m / 68Kg", "Maio de 2010", "Santo André (SP)",
				"Carlinhos tem como principal característica o apoio ao ataque e o drible. Veloz, é o típico ala do futebol moderno. Apoia e ataca com a mesma eficiência.<br /><br />Foi uma jogada dele, pela ponta, que resultou o gol do título brasileiro de 2010. Também andou marcando gols com a perna direita naquela campanha vitoriosa. Foram dois tentos importantes: um contra o Atlético-MG na goleada por 5 a 0 no Engenhão e outro na decisiva vitória por 2 a 1 na penúltima rodada do campeonato diante do Palmeiras, em Barueri (SP).<br /><br />Começou a carreira no Santos. Atuou pelas seleções de base do Brasil e também pela seleção principal. Foi convocado em 2006 pelo técnico Dunga para disputar o amistoso contra a Suíça. Depois foi para o Cruzeiro, mas teve poucas oportunidades e se transferiu para o Santo André-SP, onde viveu um grande momento.<br /><br />No time do ABC, foi vice-campeão paulista em 2010 e o melhor lateral-esquerdo da competição. Despertou o interesse de vários clubes do Brasil, entre eles o Fluminense. Acabou optando por Laranjeiras e hoje ele  é um dos principais nomes do time."));
		
		alLaterais.add(new Player("Bruno",
				getResources().getDrawable(R.drawable.elenco_bruno), "Bruno Vieira do Nascimento",
				2, 2, "Campo Grande (MS)", "30/08/1985", "1,75m / 74kg", "Dezembro de 2011", "Figueirense",
				"Contratado do Figueirense para a temporada 2012 para substituir Mariano, que transferiu-se para o futebol francês, Bruno Vieira se disse muito feliz em vestir a camisa de um grande clube como o Fluminense pela primeira vez na vida, com 26 anos de idade.<br /><br />Bruno foi eleito o terceiro melhor lateral-direito do Campeonato Brasileiro de 2011 e já chega ao Tricolor com a missão de assumir a camisa 2 na Libertadores da América.<br /><br />O lateral também sonha em vestir a camisa da Seleção Brasileira e espera chegar lá com a ajuda dos seus companheiros no Fluminense."));
		
		alLaterais.add(new Player("Wellington Silva",
				getResources().getDrawable(R.drawable.elenco_wellingtonsilva), "Wellington Nascimento Silva",
				2, 0, "Rio de Janeiro (RJ)", "06/03/1988", "1,77m / 64kg", "Janeiro de 2013", "Flamengo",
				"Velocidade. Essa é principal característica de Wellington Silva. Com fôlego impressionante, ele tem intimidade com a linha de fundo. Seu jogo é vertical, não burocratiza a jogada. Visa sempre o lance o agudo. Wellington Silva se destaca pela personalidade e o atrevimento em campo. Assim que chegou em janeiro de 2013, o jogador já trouxe seu cartão de visitas. Foi um dos destaques nos treinamentos da pré-temporada em Atibaia-SP. Torcida tricolor já sabe que o Fluzão está bem servido na lateral-direita."));
		
		alLaterais.add(new Player("Wallace",
				getResources().getDrawable(R.drawable.elenco_wallace), "Wallace Oliveira dos Santos",
				2, 16, "Rio de Janeiro (RJ)", "01/05/1994", "1,75m / 72Kg", "Profissional desde 2011", "Divisão de Base",
				"O jovem lateral direito do Fluminense é uma das promessas do Tricolor. Chegou às divisões de base do clube em 2005 e foi destaque quando competiu o Sul-Americano sub-17 de 2011 pela Seleção Brasileira da categoria.<br /><br />As comparações com o lateral esquerdo Marcelo, hoje no Real Madrid, são inevitáveis. Wallace chama a atenção de todos pela sua grande qualidade técnica - dribles e velocidade no ataque e o vigor físico.<br /><br />Depois da passagem na seleção brasileira, Wallace foi integrado à equipe principal do Fluminense a pedido do técnico Abel."));
	}
	
	private void loadVolants(){
		alVolantes.add(new Player("Diguinho",
				getResources().getDrawable(R.drawable.elenco_diguinho), "Rodrigo Oliveira de Bittencourt",
				3, 8, "Canoas (RS)", "20/03/1983", "1,71m / 70Kg", "Fevereiro de 2009", "Botafogo",
				"Diguinho veio para o Fluminense em 2009 com o desafio de conquistar a torcida tricolor, já que havia defendido as cores do rival Botafogo. Bastou a bola rolar para o meia entrar para a história do Fluzão e viver a melhor fase de sua carreira.<br /><br />Dono de um excelente passe, Diguinho tornou-se peça-chave no planejamento tático do Fluminense, independentemente de quem estiver no comando do time.<br /><br />No Flu, ele participou da campanha inesquecível que manteve o clube na 1ª Divisão, em 2009, quando também chegou à final da Copa Sul-Americana, marcando, inclusive, um gol no último jogo.<br /><br />Em 2010, foi um dos pilares do Tricampeonato Brasileiro."));
		
		alVolantes.add(new Player("Jean",
				getResources().getDrawable(R.drawable.elenco_jean), "Jean Raphael Vanderlei Moreira",
				3, 25, "Campo Grande (MS)", "24/06/1986", "1,70m / 70Kg", "Janeiro de 2012", "São Paulo",
				"Natural de Campo Grande (MS), pode-se dizer que Jean é um daqueles vencedores da bola e da vida. Por ter nascido longe dos grandes centros do futebol, teve que lutar com seus pés habilidosos para chamar a atenção e chegar a principal capital do país.<br /><br />Em 2002, lá estava ele nas categorias de base do São Paulo. Profissionalizou-se pelo clube paulista em 2005, mas ainda assim teve que ralar até ganhar reconhecimento. Antes de se tornar titular da equipe que se tornou campeã brasileira em 2008, único título de sua carreira, jogou por empréstimo no América de Rio Preto (SP), no Marília (SP) e no Penafiel, de Portugal.<br /><br />Versátil, atua tanto como volante, sua posição de origem, quanto lateral-direito. A pedido do técnico Abel Braga, chegou ao Fluminense como um dos grandes reforços para a temporada 2012.<br /><br />É um jogador que se caracteriza por um futebol vistoso, de bom toque de bola. E ainda cobra falta como poucos."));
		
		alVolantes.add(new Player("Edinho",
				getResources().getDrawable(R.drawable.elenco_edinho), "Edimo Ferreira Campos",
				3, 5, "Niterói (RJ)", "15/01/1983", "1,83m / 83Kg", "Janeiro de 2011", "Palmeiras",
				"O niteroiense Edinho foi revelado pelo Boavista, de Saquarema, mas ganhou notoriedade e o respeito do mundo futebolístico atuando pelo Internacional (RS). No clube gaúcho, conquistou sete títulos. Três estaduais (2004, 2005 e 2008), Copa Libertadores da América (2006), Mundial de Clubes (2006), Recopa Sul-Americana (2007) e Copa Sul-Americana (2008). Em 2010, foi para o Palmeiras sob a chancela do técnico pentacampeão do mundo Luiz Felipe Scolari.<br /><br />No início de 2011, chegou ao Fluminense para jogar como volante, sua posição de origem. Porém, devido à necessidade da comissão técnica, foi escalado na defesa e conseguiu destaque, retornando, posteriormente, ao meio-campo.<br /><br />Edinho é um volante versátil, pois tem passes precisos, é eficiente no desarme e comete poucas faltas."));
		
		alVolantes.add(new Player("Valencia",
				getResources().getDrawable(R.drawable.elenco_valencia), "Edwin Armando Valencia",
				3, 17, "Florida (COL)", "20/03/1983", "1,85m / 80Kg", "Junho de 2010", "Atlético-PR",
				"O colombiano Valencia foi revelado pelo América de Cali, um dos principais clubes do futebol de seu país. Ganhou destaque, no cenário internacional, ao vencer o Campeonato Sul-Americano Sub-20, em 2005, com a Seleção Colombiana.<br /><br />Seu desempenho chamou a atenção de alguns clubes brasileiros e ele foi contratado pelo Atlético Paranaense, pelo qual atuou entre 2007 e 2010.<br /><br />No segundo semestre deste último ano, despertou o interesse do Fluminense e acertou sua transferência para o tricolor.<br /><br />Assumiu a condição de titular, no setor defensivo do meio-campo do Fluzão e teve papel importante na campanha do Tricampeonato Brasileiro.<br /><br />Volante firme, Valencia tem como principais marcas o bom desarme e a segurança no transporte da bola até os meias ofensivos.<br /><br />O desempenho com a camisa do Fluminense rendeu a ele uma convocação para representar a Colômbia em um amistoso contra a Espanha, em janeiro de 2011."));
		
		alVolantes.add(new Player("Fábio",
				getResources().getDrawable(R.drawable.elenco_fabio), "Fábio Farroco Braga",
				3, 32, "Rio de Janeiro (RJ)", "06/09/1992", "1,84m / 84Kg", "Janeiro de 2012", "Internacional (RS)",
				"Fábio chegou ao Fluminense em 2009. O volante venceu o Campeonato Carioca Juvenil, em 2009, e a Taça Guanabara de Juniores, em 2010.<br /><br />Fábio se destacou no Campeonato Brasileiro sub-20 de 2011, onde o Tricolor chegou à final."));
		
		alVolantes.add(new Player("Rafinha",
				getResources().getDrawable(R.drawable.elenco_rafinha), "Rafael Gimenes da Silva",
				3, 37, "Rio de Janeiro (RJ)", "05/08/1993", "1,77m / 74kg", "Profissional desde 2012", "Divisão de base",
				"Está no Fluminense desde os sete anos, quando chegou ao clube para jogar futsal. Natural do subúrbio do Rio de Janeiro, Rafinha começou no Pré-Mirim e não parou mais. Nos Juniores, ganhou destaque após atuação de gala na Copa São Paulo de 2012. Volante dinâmico, ataca com eficiência e tem um chute mortal. Na defesa, Rafinha desarma como ninguém. Estreou no time principal na vitória do Fluminense sobre o Corinthians, por 1 a 0 no Pacembu, na primeira rodada do Campeonato Brasileiro de 2012."));
	}
	
	private void loadMidfielders(){
		alMeias.add(new Player("Thiago Neves",
				getResources().getDrawable(R.drawable.elenco_thiagoneves), "Thiago Neves Augusto",
				4, 10, "Curitiba (PR)", "27/02/1985", "1,82m / 70Kg", "Janeiro de 2012", "Flamengo",
				"Promovido para a equipe principal do Paraná em 2004, foi destaque do campeonato estadual daquele ano. Devido a problemas disciplinares, foi vendido para o Vegalta Sendai, do Japão e treinado por Joel Santana. No início de 2007, transferiu-se para o Fluminense para ser uma peça de reposição.<br /><br />Destacou-se na campanha do título da Copa do Brasil, e tomou a vaga de Carlos Alberto na equipe principal. Recebeu a bola de ouro da revista Placar naquele ano, como o melhor jogador do campeonato brasileiro de 2007.<br /><br />Na Libertadores de 2008, Thiago teve excelentes atuações e participações decisivas. Na final do campeonato, foi autor de três gols, e tornou-se o primeiro jogador da história com o feito.<br /><br />Foi vendido ao Hamburgo da Alemanha, mas não se firmou, e foi negociado com o Al-Hilal da Arábia Saudita. Por cinco meses, foi emprestado pelo clube árabe ao Fluminense, e disputou o Campeonato Carioca e a Copa do Brasil em 2009. Apesar de não repetir as atuações brilhantes, realizou bons jogos. A partir da metade daquele ano, retornou ao Al-Hilal e foi artilheiro do campeonato árabe.<br /><br />Em janeiro de 2011, assinou com o Flamengo e venceu o Campeonato Carioca, tendo sido o craque do torneio e artilheiro de sua equipe.<br /><br />Jogador rápido, inteligente, driblador e finalizador, jogando tanto na criação quanto no ataque voltou ao Fluminense em janeiro de 2012 com contrato por quatro temporadas, e espera terminar o que deixou na épica campanha da América em 2008."));
		
		alMeias.add(new Player("Deco",
				getResources().getDrawable(R.drawable.elenco_deco), "Anderson Luís de Souza",
				4, 20, "São Bernardo do Campo (SP)", "27/08/1977", "1,74m / 73Kg", "Agosto de 2010", "Chelsea (ING)",
				"Nascido em São Bernardo do Campo (SP), o meia Deco apareceu para o futebol mundial em 2003 atuando pelo Porto, onde conquistou uma Taça UEFA, uma Liga dos Campeões e uma Taça de Portugal. Em terras portuguesas desde 1997, quando foi do CSA para o Benfica e acabou emprestado primeiramente ao Alverca e posteriormente ao Salgueiros, o jogador conseguiu sua dupla cidadania e acabou por ser convocado para a Seleção Portuguesa após seis anos no futebol local.<br /><br />Os títulos pelo Porto chamaram a atenção e o jogador recebeu propostas de grandes clubes europeus, escolhendo defender o Barcelona, equipe onde se consagrou ao atuar ao lado de Ronaldinho Gaúcho e Eto'o, conquistando dois Campeonato Espanhóis, duas Supercopas da Espanha e uma Champions League.<br /><br />Contratado pelo Chelsea em meados de 2008, foi negociado com o Fluminense após a Copa do Mundo de 2010. Apresentado com grande festa à torcida tricolor em um Maracanã lotado para a partida contra o Internacional, o atleta esteve em 16 partidas na campanha do título do Brasileirão 2010 e marcou um gol no empate em 2 a 2 com o São Paulo.<br /><br />Em 2011, viveu seu ponto alto ao marcar o gol da vitória do Fluminense sobre o América-MEX pela Libertadores."));

		alMeias.add(new Player("Wagner",
				getResources().getDrawable(R.drawable.elenco_wagner), "Wagner Ferreira dos Santos",
				4, 19, "Sete Lagoas (MG)", "29/01/1985", "1,75m / 65kg", "Dezembro de 2011", "Gaziantepspor (TUR)",
				"No início da carreira, Wagner seguiu passos semelhantes aos de Fred, seu amigo pessoal. Foi revelado pelo América (MG), mas apareceu para o mundo com a camisa do Cruzeiro, pelo qual conquistou todos os títulos da carreira até o momento: Campeonato Mineiro (2006, 2008 e 2009) e Campeonato Internacional de Verão (2009).<br /><br />Seu ótimo desempenho nos campeonatos brasileiros de 2006 e 2008 rendeu-lhe a Bola da Prata da Revista Placar, como o melhor da posição, nestes respectivos anos. Em 2009, chegou à final da Copa Libertadores da América, também com o Cruzeiro.<br /><br />Meia de estilo clássico e exímio cobrador de faltas, Wagner chamou a atenção do futebol europeu e foi vendido ao Lokomotiv Moscou, da Rússia. No ano seguinte, transferiu-se ao Gaziantepspor, da Turquia, e, no fim de 2011, foi repatriado pelo Fluminense."));

	}
	
	private void loadForwards(){
		alAtacantes.add(new Player("Fred",
				getResources().getDrawable(R.drawable.elenco_fred), "Frederico Chaves Guedes",
				5, 9, "Teófilo Otoni (MG)", "03/10/1983", "1,85m / 84Kg", "Março de 2009", "Olympique Lyon (FRA)",
				"Fred chegou ao Fluminense em março de 2009 com direito a recepção de gala no Salão Nobre do clube. Logo na sua estreia, no dia 15 do mesmo mês, já mostrou contra o Macaé que seria mesmo a grande aposta da diretoria. Dois gols na vitória por 3 a 1 e a resposta imediata da torcida. Nascia um novo ídolo nas Laranjeiras.<br /><br />Em seu primeiro Brasileiro com a camisa tricolor, Fred foi figura decisiva na reta final para ajudar a tirar a equipe da crise, quando os matemáticos já davam o time como rebaixado. Nos 11 últimos jogos foram sete vitórias, quatro empates e oito gols de Fred. Nascia ali o Time de Guerreiros.<br /><br />Mas um ídolo, para marcar sua passagem por um clube, precisa de títulos. Se escapou na final da Sul-Americana de 2009, ele veio de forma triunfal na no Brasileiro de 2010. Fred foi a liderança tanto dentro quanto fora de campo.<br /><br />Em 2011, o atacante voltou a marcar seu nome na história ao terminar o Campeonato Carioca como artilheiro da competição, com dez gols. E, no Brasileiro, ainda superou a marca de Washington (21 gols em 2008) como o maior artilheiro do Fluminense em uma única edição do campeonato, com 22 gols."));
		
		alAtacantes.add(new Player("Wellington Nem",
				getResources().getDrawable(R.drawable.elenco_nem), "Wellington Silva Sanches Aguiar",
				5, 18, "Rio de Janeiro (RJ)", "06/02/1992", "1,65m / 65Kg", "Dezembro de 2011", "Figueirense",
				"É um dos raros talentos formados em Xerém, apesar de ter dado os primeiros passos no futebol com a camisa do América (RJ). Ao chegar ao profissional do Fluminense, e com tantos bons valores na sua posição, acabou emprestado para o Figueirense, com o intuito de ganhar mais rodagem e maturidade. E foi justamente o que aconteceu. Aliás, o pequeno Wellington Nem, de apenas 1,65m, se superou.<br /><br />Originalmente um meia de criação, atuou pela equipe catarinense como atacante. E impressionou o Brasil com sua habilidade. Resultado: foi eleito a revelação do Campeonato Brasileiro de 2011.<br /><br />Bom para o Fluminense, que recebeu um jogador ainda mais reconhecido pelo seu talento, louco para mostrar o que sabe com a camisa do seu clube do coração."));
		
		alAtacantes.add(new Player("Marcos Jr",
				getResources().getDrawable(R.drawable.elenco_marcosjr), "Marcos Junio Lima",
				5, 21, "Gama (DF)", "19/01/1993", "1,67m / 63 kg", "Profissional desde Abril/2012", "Divisão de Base",
				"Faro de gol. Essa é a definição certa para esse centroavante hábil e letal dentro da área. Artilheiro das Divisões de Base do Fluminense, Marcos Junio se destacou facilmente pela quantidade de gols marcados nas diversas competições que participou com a camisa do Fluzão. Além de goleador, Marcos Junio tem estrela, em sua primeira como profissional, o atacante entrou no segundo tempo e no seu primeiro toque na bola, ele marcou o gol da vitória por 2 a 1 sobre o Macaé, na semifinal da Taça Luiz Penido, em Moça Bonita, em Bangu, zona oeste do Rio de Janeiro. Marcos Junio ainda meteu uma bola no travessão após linda jogada individual. Sua atuação destacada fez o técnico Abel Braga convocá-lo para os profissionais para o confronto seguinte contra o Internacional, em jogo válido pelas oitavas de final da Copa Libertadores 2012."));
		
		alAtacantes.add(new Player("Rhayner",
				getResources().getDrawable(R.drawable.elenco_rhayner), "Rhayner Santos Nascimento",
				5, 0, "Serra (ES)", "05/09/1990", "1,79m / 75kg", "Janeiro de 2013", "Náutico (PE)",
				"Atacante veloz e de muita versatilidade, que atua pelos dois lados do campo, o atacante de 22 anos foi um dos destaques do Campeonato Brasileiro de 2012 pelo Náutico e já jogou por Grêmio Barueri e Figueirense. Com uma boa colocação, que confunde os adversários, Rhayner disse que jogar pelo Fluminense será especial, pois ele terá a companhia de atletas experientes, como Fred e Rafael Sobis, com quem o jovem jogador terá a oportunidade de aprender muito na carreira."));
		
		alAtacantes.add(new Player("Matheus Carvalho",
				getResources().getDrawable(R.drawable.elenco_matheus_carvalho), "Matheus Thiago de Carvalho",
				5, 35, "Niterói (RJ)", "11/03/1992", "1,77m / 69Kg", "Profissional desde 2010", "Divisão de Base",
				"Nascido e criado em Niterói (RJ), Matheus Carvalho é visto como uma grande promessa no Fluminense. Cria da casa, começou no futsal. Na quadra, já mostrava faro para o gol. No campo, foi uma das grandes revelações da equipe de juniores em 2010 e acabou integrado ao elenco profissional na campanha do título brasileiro.<br /><br />Chegou a ser relacionado pelo técnico Muricy Ramalho para a partida contra o Grêmio, no Engenhão (2 a 0, dois gols de Conca).<br /><br />Seu primeiro jogo como profissional se deu na vitória de 1 a 0 sobre o Atlético-GO, no Serra Dourada, já no Brasileiro de 2011. E o primeiro gol do atacante não demoraria a sair. Foi contra o Coritiba (1×3), no Couto Pereira.<br /><br />Em meio a  nomes consagrados para a posição (Fred, Rafael Moura, Rafael Sobis, Araújo e Martinuccio), Matheus só tem a crescer com a experiência do elenco."));
		
		alAtacantes.add(new Player("Rafael Sóbis",
				getResources().getDrawable(R.drawable.elenco_rafael_sobis), "Rafael Augusto Sóbis",
				5, 23, "Erechim (RS)", "17/06/1985", "1,72m / 73Kg", "Julho de 2011", "Internacional (RS)",
				"Indicado pelo técnico Abel Braga, Rafael Sobis chegou ao Fluminense em julho de 2011 para tentar reeditar com o treinador a parceria de sucesso que fizeram no Inter de Porto Alegre. Juntos, sagraram-se campeões da Libertadores em 2006. Por ter se transferido logo depois para o futebol europeu, mais precisamente para o Betis, da Espanha, o atacante acabou não fazendo parte do elenco colorado campeão do mundo no fim daquele ano. Porém, quando retornou ao Beira-Rio, em 2010, sua estrela voltou a brilhar: ele conquistou o bicampeonato da principal competição sul-americana.<br /><br />Com a camisa tricolor, estreou na vitória por 1 a 0 sobre o Palmeiras, em Volta Redonda, e marcou seu primeiro gol na goleada de 4 a 0 sobre o Ceará, no Engenhão.<br /><br />O jogador está emprestado pelo Al Jazira (EAU) até julho de 2012."));
		
		alAtacantes.add(new Player("Samuel",
				getResources().getDrawable(R.drawable.elenco_samuel), "Samuel Rosa Gonçalves",
				5, 31, "São Borja (RS)", "25/02/1991", "1,85m / 83Kg", "Janeiro de 2012", "São José (RS)",
				"Samuel se transferiu para o Fluminense em abril de 2011, quando deixou o Internacional de Porto Alegre. Lá, o jovem atacante colecionou títulos.<br /><br />Com apenas vinte anos, Samuel conquistou o Campeonato Gaúcho de Júnior, em 2009. No ano seguinte, venceu, novamente o Estadual de Júnior com o time B do Internacional, e também o sub-19 do Gaúchão.<br /><br />A sua maior conquista ficou por conta do Campeonato Brasileiro sub-23, em 2010, a primeira edição do torneio nessa categoria."));
	}
	
	public class MyExpandableListAdapter extends SimpleExpandableListAdapter {
		private List<? extends List<? extends Map<String, ?>>> mChildData;
		private String[] mChildFrom;
		private int[] mChildTo;

		public MyExpandableListAdapter(Context context, List<? extends Map<String, ?>> groupData, int groupLayout,
				String[] groupFrom, int[] groupTo, List<? extends List<? extends Map<String, ?>>> childData, int childLayout, String[] childFrom, int[] childTo) {
			super(context, groupData, groupLayout, groupFrom, groupTo,
					childData, childLayout, childFrom, childTo);

			mChildData = childData;
			mChildFrom = childFrom;
			mChildTo = childTo;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, 
				ViewGroup parent) {

			View v;
			if (convertView == null) v = newChildView(isLastChild, parent);
			else v = convertView;
			bindView(v, mChildData.get(groupPosition).get(childPosition), mChildFrom, mChildTo, groupPosition, childPosition);
			
			((TextView) v.findViewById(R.exlvchild.text_naturality)).setTextColor(theme.getSec_text());
			((TextView) v.findViewById(R.exlvchild.text_heightweight)).setTextColor(theme.getSec_text());
			((LinearLayout) v.findViewById(R.exlvchild.numberbg)).setBackgroundColor(theme.getContent_background());
			((TextView) v.findViewById(R.exlvchild.child_number)).setTextColor(theme.getAbout_text());
			return v;
		}

		// This method binds my data to the Views specified in the child xml layout
		private void bindView(View view, Map<String, ?> data,
				String[] from, int[] to, int groupPosition, int  childPosition) {
			
			// Apply ImageView
			ImageView imgV = (ImageView) view.findViewById(to[0]);
			if (imgV != null) imgV.setImageDrawable((Drawable) data.get(from[0]));
			
			// Apply TextViews
			for (int i = 1; i < to.length; i++) {
				TextView v = (TextView) view.findViewById(to[i]);
				if (v != null) v.setText((String) data.get(from[i]));

			}
		}
	}
}