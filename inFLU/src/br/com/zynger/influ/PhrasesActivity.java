package br.com.zynger.influ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.com.zynger.influ.model.Phrase;
import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import br.com.zynger.influ.view.ViewFlinger;

public class PhrasesActivity extends Activity {
		
	private RepoPhrases repo;
	private ViewFlinger flinger;
	private ViewGroup layouts[] = new ViewGroup[3];
	private TextView tv_numPhrase1, tv_phrase1, tv_author1, tv_numPhrase2, tv_phrase2, tv_author2, tv_numPhrase3, tv_phrase3, tv_author3;
	private Typeface chopinScript, sweetlyBroken;
	
	private List<String> authors;
	private Dialog dialogSearch, dialogAuthors;
	private AutoCompleteTextView autoComplete;
	private ListView list_search;
	private ImageButton bt_authors, ib_actionbar_search1, ib_actionbar_search2, ib_actionbar_search3;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phrases);
		loadViews();
		updateTheme();
		setViewFlingerSettings();
		setActionbarButtonListener();
		
		repo = new RepoPhrases(this);
		
		if(repo.listPhrases().size()<1) populateDatabase();
		
		loadSearchDialog();
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.phrases.actionbar1).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.phrases.actionbar2).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.phrases.actionbar3).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.phrases.actionbar1).invalidate();
		findViewById(R.phrases.actionbar2).invalidate();
		findViewById(R.phrases.actionbar3).invalidate();
	}

	private void setActionbarButtonListener() {
		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogSearch.show();
			}
		};
		
		ib_actionbar_search1.setOnClickListener(ocl);
		ib_actionbar_search2.setOnClickListener(ocl);
		ib_actionbar_search3.setOnClickListener(ocl);
	}

	private void setViewFlingerSettings() {
		flinger.setOnScreenChangeListener(new ViewFlinger.OnScreenChangeListener(){
	        @Override
	        public void onScreenChanging(View newScreen, int newScreenIndex){}

	        @Override
	        public void onScreenChanged(View newScreen, int newScreenIndex)
	        {
	        	ViewGroup tempLayout = null;
	        	if(newScreenIndex == 2){ // Left to right -->
	        		tempLayout = layouts[0];
	        		
	        		flinger.removeViewFromFront();
	        		flinger.addViewToBack(tempLayout);
	        		layouts[0] = layouts[1];
	        		layouts[1] = layouts[2];
	        		layouts[2] = tempLayout;
	        	}else if(newScreenIndex == 0){ // Right to left <--
	        		tempLayout = layouts[2];
	        		
	        		flinger.removeViewFromBack();
	        		flinger.addViewToFront(tempLayout);
	        		layouts[2] = layouts[1];
	        		layouts[1] = layouts[0];
	        		layouts[0] = tempLayout;
	        	}
	        	
	        	flinger.setCurrentScreenNow(1, false);
	        	
	        	int current = flinger.getCurrentScreen();
	        	
	        	LinearLayout child = (LinearLayout) flinger.getChildAt(current);
	        	TextView tv_id = (TextView) child.findViewWithTag("phrase_id");
	        	try{	        		
	        		String visibleId = tv_id.getText().toString().substring(3);
	        		int id = Integer.parseInt(visibleId);
	        		
	        		if(tv_id.getId() == R.phrases.numPhrase1){
	        			setPhrase3(repo.findPhrase(id-1));
	        			setPhrase2(repo.findPhrase(id+1));
	        		}else if(tv_id.getId() == R.phrases.numPhrase2){
	        			setPhrase1(repo.findPhrase(id-1));
	        			setPhrase3(repo.findPhrase(id+1));
	        		}else if(tv_id.getId() == R.phrases.numPhrase3){
	        			setPhrase2(repo.findPhrase(id-1));
	        			setPhrase1(repo.findPhrase(id+1));
	        		}
	        	}catch(StringIndexOutOfBoundsException e){
	        		//Do nothing...
	        	}catch(NullPointerException e){
	        		//Quando nos dois extremos (primeira e ultima phrases)
	        		int id = Integer.parseInt(tv_id.getText().toString().substring(3));
	        		
	        		if(id == 1){
	        			if(tv_id.getId() == R.phrases.numPhrase1){
		        			setPhrase3(repo.findPhrase(repo.listPhrases().size()));
		        			setPhrase2(repo.findPhrase(id+1));
		        		}else if(tv_id.getId() == R.phrases.numPhrase2){
		        			setPhrase1(repo.findPhrase(repo.listPhrases().size()));
		        			setPhrase3(repo.findPhrase(id+1));
		        		}else if(tv_id.getId() == R.phrases.numPhrase3){
		        			setPhrase2(repo.findPhrase(repo.listPhrases().size()));
		        			setPhrase1(repo.findPhrase(id+1));
		        		}
	        		}else{
	        			if(tv_id.getId() == R.phrases.numPhrase1){
		        			setPhrase3(repo.findPhrase(id-1));
		        			setPhrase2(repo.findPhrase(1));
		        		}else if(tv_id.getId() == R.phrases.numPhrase2){
		        			setPhrase1(repo.findPhrase(id-1));
		        			setPhrase3(repo.findPhrase(1));
		        		}else if(tv_id.getId() == R.phrases.numPhrase3){
		        			setPhrase2(repo.findPhrase(id-1));
		        			setPhrase1(repo.findPhrase(1));
		        		}
	        		}
	        	}
	        }
	    });
	}
	
	private void loadSearchDialog() {
		dialogSearch = new Dialog(this);
		dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogSearch.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		dialogSearch.getWindow().setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL);  
		dialogSearch.setContentView(R.layout.phrases_search_dialog);

		autoComplete = (AutoCompleteTextView) dialogSearch.findViewById(R.phrases_search_dialog.autocomplete_country);
		list_search = (ListView) dialogSearch.findViewById(R.phrases_search_dialog.list_search);
		bt_authors = (ImageButton) dialogSearch.findViewById(R.phrases_search_dialog.bt_authors);
		
		int[] colors = {0, 0xFFFFFFFF, 0};
		list_search.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		list_search.setDividerHeight(1);
		
		authors = repo.getAuthorsList();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.phrases_search_dialog_textview, authors);
	    autoComplete.setAdapter(adapter);
	    
	    autoComplete.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				ArrayList<Phrase> phrases = repo.findPhrasesByAuthor(autoComplete.getText().toString());
				ArrayList<String> phr_str = new ArrayList<String>();
				for (Phrase phrase : phrases) {
					if(phrase.getPhrase().length() > 70) phr_str.add(phrase.getId() + ": " + phrase.getPhrase().substring(0, 70) + "...");
					else phr_str.add(phrase.getId() + ": " + phrase.getPhrase());
					
				}
				ArrayAdapter<String> notes = new ArrayAdapter<String>(v.getContext(), R.layout.phrases_search_dialog_listview, phr_str);
				list_search.setAdapter(notes);
			}
		});
		
		list_search.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
				String item = (String) list_search.getItemAtPosition(position);
				int idPhrase = Integer.parseInt(item.substring(0, item.indexOf(":")));
				
				int current = flinger.getCurrentScreen();
	        	LinearLayout child = (LinearLayout) flinger.getChildAt(current);
	        	TextView tv_id = (TextView) child.findViewWithTag("phrase_id");

	        	if(tv_id.getId() == R.phrases.numPhrase1){
	        		setPhrase1(repo.findPhrase(idPhrase));
	        		try{	        			
	        			setPhrase2(repo.findPhrase(idPhrase+1));
	        		}catch(NullPointerException e){
	        			setPhrase2(repo.findPhrase(1));
	        		}
	        		try{	        			
	        			setPhrase3(repo.findPhrase(idPhrase-1));
	        		}catch(NullPointerException e){
	        			setPhrase3(repo.findPhrase(repo.listPhrases().size()));
	        		}
	        	}else if(tv_id.getId() == R.phrases.numPhrase2){
	        		try{	        			
	        			setPhrase1(repo.findPhrase(idPhrase-1));
	        		}catch(NullPointerException e){
	        			setPhrase1(repo.findPhrase(repo.listPhrases().size()));
	        		}
	        		setPhrase2(repo.findPhrase(idPhrase));
	        		try{	        			
	        			setPhrase3(repo.findPhrase(idPhrase+1));
	        		}catch(NullPointerException e){
	        			setPhrase3(repo.findPhrase(1));
	        		}
	        	}else if(tv_id.getId() == R.phrases.numPhrase3){
	        		try{	        			
	        			setPhrase1(repo.findPhrase(idPhrase+1));
	        		}catch(NullPointerException e){
	        			setPhrase1(repo.findPhrase(1));
	        		}
	        		try{	        			
	        			setPhrase2(repo.findPhrase(idPhrase-1));
	        		}catch(NullPointerException e){
	        			setPhrase2(repo.findPhrase(repo.listPhrases().size()));
	        		}
	        		setPhrase3(repo.findPhrase(idPhrase));
	        	}
        		
				if(dialogSearch.isShowing()) dialogSearch.dismiss();
			}
		});
		
		dialogAuthors = new Dialog(this);
		dialogAuthors.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ListView lv = new ListView(this);
		
		ArrayList<String> sortedAuthors = new ArrayList<String>();
		sortedAuthors.addAll(authors);
		Collections.sort(sortedAuthors);
		
		ArrayAdapter<String> aut = new ArrayAdapter<String>(this, R.layout.phrases_search_dialog_listview, sortedAuthors);
		lv.setAdapter(aut);
		lv.setCacheColorHint(0x00000000);
		dialogAuthors.addContentView(lv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				String author = parent.getItemAtPosition(position).toString();
				autoComplete.setText(author);
				ArrayList<Phrase> phrases = repo.findPhrasesByAuthor(author);
				ArrayList<String> phr_str = new ArrayList<String>();
				for (Phrase phrase : phrases) {
					if(phrase.getPhrase().length() > 70) phr_str.add(phrase.getId() + ": " + phrase.getPhrase().substring(0, 70) + "...");
					else phr_str.add(phrase.getId() + ": " + phrase.getPhrase());
					
				}
				ArrayAdapter<String> notes = new ArrayAdapter<String>(v.getContext(), R.layout.phrases_search_dialog_listview, phr_str);
				list_search.setAdapter(notes);
				dialogAuthors.dismiss();
			}
		});
		
		bt_authors.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogAuthors.show();
			}
		});
	}

	@Override
	protected void onResume() {
		while(true){
			int num = new Random().nextInt(repo.listPhrases().size());
			Phrase p = repo.findPhrase(num);
			try{
				setPhrase2(p);
				setPhrase1(repo.findPhrase(num-1));
				setPhrase3(repo.findPhrase(num+1));
				break;
			}catch(NullPointerException e){}
		}
		super.onResume();
	}
	
	private void setPhrase1(Phrase p) throws NullPointerException{
		tv_numPhrase1.setText("N° " + p.getId());
		tv_phrase1.setText(p.getPhrase());
		tv_phrase1.setTypeface(chopinScript);
		tv_author1.setText(p.getAuthor());
		tv_author1.setTypeface(sweetlyBroken);
	}
	
	private void setPhrase2(Phrase p){
		tv_numPhrase2.setText("N° " + p.getId());
		tv_phrase2.setText(p.getPhrase());
		tv_phrase2.setTypeface(chopinScript);
		tv_author2.setText(p.getAuthor());
		tv_author2.setTypeface(sweetlyBroken);
	}
	
	private void setPhrase3(Phrase p){
		tv_numPhrase3.setText("N° " + p.getId());
		tv_phrase3.setText(p.getPhrase());
		tv_phrase3.setTypeface(chopinScript);
		tv_author3.setText(p.getAuthor());
		tv_author3.setTypeface(sweetlyBroken);
	}
	
	private void loadViews() {
		chopinScript = Typeface.createFromAsset(getAssets(), "fonts/ChopinScript.otf");
		sweetlyBroken = Typeface.createFromAsset(getAssets(), "fonts/SweetlyBroken.ttf");
		
		flinger = (ViewFlinger) findViewById(R.phrases.flinger);
		
		tv_numPhrase1 = (TextView) findViewById(R.phrases.numPhrase1);
		tv_phrase1 = (TextView) findViewById(R.phrases.phrase1);
		tv_author1 = (TextView) findViewById(R.phrases.author1);
		ib_actionbar_search1 = (ImageButton) findViewById(R.phrases.ib_actionbar_search1);
		
		tv_numPhrase2 = (TextView) findViewById(R.phrases.numPhrase2);
		tv_phrase2 = (TextView) findViewById(R.phrases.phrase2);
		tv_author2 = (TextView) findViewById(R.phrases.author2);
		ib_actionbar_search2 = (ImageButton) findViewById(R.phrases.ib_actionbar_search2);
		
		tv_numPhrase3 = (TextView) findViewById(R.phrases.numPhrase3);
		tv_phrase3 = (TextView) findViewById(R.phrases.phrase3);
		tv_author3 = (TextView) findViewById(R.phrases.author3);
		ib_actionbar_search3 = (ImageButton) findViewById(R.phrases.ib_actionbar_search3);
		
		layouts[0] = (ViewGroup) findViewById(R.phrases.phrases_1);
		layouts[1] = (ViewGroup) findViewById(R.phrases.phrases_2);
		layouts[2] = (ViewGroup) findViewById(R.phrases.phrases_3);
	}

	private void populateDatabase() {
		createPhrase("A Grande Guerra seria apenas a paisagem, apenas o fundo das nossas botinadas. Enquanto morria um mundo e começava outro, eu só via o Fluminense.", "Nelson Rodrigues");
		createPhrase("O Fluminense foi a minha vida. Lá eu joguei por toda a minha carreira. Na época, eu jogava com amor.", "Telê Santana");
		createPhrase("Fluminense pra mim é a minha paixão, é a minha vida. Fluminense pra mim é segunda família. Enquanto joguei pelo tricolor, cheguei a seleção brasileira numa idade avançada e consegui ser tricampeão carioca e campeão brasileiro. Eu agradeço a Deus por ter jogado no Fluminense.", "Assis");
		createPhrase("Aonde eu vou digo que a torcida do Fluminense é a melhor do mundo.", "Romerito");
		createPhrase("Eu nem sabia falar direito e o Fluminense já estava dentro de mim, do meu corpo, do meu coração. O Fluminense é a minha vida, uma paixão muito difícil de explicar.", "Preguinho");
		createPhrase("Pode-se identificar um Tricolor entre milhares, entre milhões. Ele se distingue dos demais por uma irradiação específica e deslumbradora.", "Nelson Rodrigues");
		createPhrase("Eu ficava por conta da vida quando perdia jogo. Não brincava, não saia, não tinha nada que me fizesse alegria. O Fluminense era a minha paixão.", "Telê Santana");
		createPhrase("Ao dar a volta olímpica, pensei em todos torcedores tricolores e senti uma emoção cuja intensidade tão grande só pode ser compreendida por quem tem o Fluminense no coração.", "Paulo Victor");
		createPhrase("Eu amo o Fluminense como só amo a minha família. Eu sempre digo que pelo menos 40% do meu tempo dedico ao Fluminense porque sempre estou pensando em algo relacionado ao clube. O Fluminense está sempre presente ao ponto de eu ter feito uma tatuagem com o escudo.", "Francisco Legnani, criador da Fluruguay");
		createPhrase("O Fluminense representa tudo de bom que aconteceu comigo no futebol, aprendi a respeitar o clube e amar a torcida sob todos os aspectos. Onde ando sempre procuro falar sobre o Fluminense e me informar de tudo sobre o clube. Poderia até dizer que sou cinqüenta por cento paraguaio e cinqüenta por cento Fluminense. Aprendi a amar este clube e sua torcida ao longo da história da minha carreira. Me emociono ao lembrar do título e do gol que marquei na decisão contra o Vasco. O gol mais importante da minha passagem pelo Fluminense. Torno a repetir, amo este clube e sua torcida.", "Romerito");
		createPhrase("Ele queria jogar partidas decisivas pelo Fluminense e não teria condição de se recuperar normalmente. Amputando um pedaço do dedo seria mais rápido", "Michel Laurence, sobre Castilho");
		createPhrase("(...) é muito fácil ser rubro-negro. Fácil de mais. É como ser a favor do sol no meio do deserto, ou comemorar o Dia da Árvore no coração da Amazônia. Aliás, nunca existiu um flamenguista. Flamengar é verbo imperfeito que só se conjuga no plural. Por exemplo: E advogo, tu bates o ponto, ele mata mosquito; nós flamengamos, vós flamengais, eles flamengam. Mas torcer pelo Fluminense, modéstia à parte, requer outros talentos. Precisa saber dançar sem batucada. O tricolor chora e ri sem ninguém por perto. Ele merece um campeonato, ele merece.", "Chico Buarque");
		createPhrase("Se quereis saber o futuro do Fluminense, olhai para o seu passado. A história tricolor traduz a predestinação para a glória", "Nelson Rodrigues");
		createPhrase("Meu sentimento clubístico é anterior ao sexo, anterior à memória.", "Nelson Rodrigues");
		createPhrase("O Fluminense não nasceu para ser unanimidade nem massa de manobra do interesse demagógico das elites opressoras. O Fluminense nasceu para atravessar a harmonia do bloco dos contentes. Nasceu para incomodar o senso comum. Essa é a nossa sina.", null);
		createPhrase("A frase é de Nietzsche: \"o que não me mata só me fortalece\". Então, o Fluminense é 'nietzschiano'. Foi parar na Terceira Divisão, não morreu e se fortaleceu. A torcida se mostrou ainda mais fiel. Comecei a ir ao Maracanã com seis anos. Antes disso, não me lembro de nada. O Fluminense é minha primeira referência afetiva. Está acima de nomes. Flamengo é Zico, Botafogo é Garrincha, mas Fluminense é Fluminense. Perco o sono, a fome, não sei me desligar do clube. Em 84, estava cantando com o Hojerizah numa boate, na hora da final com o Flamengo. Tentei adiar o show, em vão. O cara da iluminação era tricolor e ficou na coxia me passando o resultado. Saímos do palco e a platéia, lotada, pedia bis. Estava 1 a 0, faltava pouco. Peguei o rádio, resisti à pressão da banda e só voltei com o Fluminense campeão.", "Tony Platão");
		createPhrase("Não digo \"o Fluminense\". É o \"meu Fluminense\". Nunca vi o clube como propriedade dos torcedores, de associados. Desde jovem, achava que o clube pertencia a mim, morava no meu coração. Na infância, meu tio me levava aos jogos. Depois, tive uma amiga, Helena, que namorava o Tim. Nós saímos com ele. Tim era uma referência em nossas vidas, um ídolo. Freqüentava bailes, carnaval na sede. Não abandonei nem nas fases ruins. Amo o meu clube. Era uma torcedora entusiasmada. Nos gols, gritava \"meu Fluminense!\". Era do bem ser tricolor. Um clube bonito, alinhado. Eu ia aos jogos com uma encharpe tricolor, combinava saia e camisa nas cores do clube. O Fluminense é elegante.", "Bibi Ferreira");
		createPhrase("Para ser um gigante, não fazem falta títulos mirabolantes, equipes inesquecíveis ou milhões de fanáticos torcedores. O Fluminense tem tudo isso, como de resto quase todos os grandes clubes mundo afora. Não é isso que torna o Tricolor diferente dos demais. Para ser um gigante é preciso mostrar valor diante do inimigo invencível e face ao mais profundo dos abismos. Por duas vezes, ao longo de seu primeiro centenário, o Fluminense esteve à beira da aniquilação – e sobreviveu. Foi com tal fidalguia que o clube das três cores que traduzem tradição se tornou uma lenda. Um clube que, quando menor pareceu, aí mesmo foi que provou ser um gigante.", "Marcos Caetano");
		createPhrase("O Fluminense independe de conquistas, o Fluminense está entre o ser e o devir, o Fluminense é o ser. É Fluminense e basta.", "Sidney Garambone");
		createPhrase("Eu praticamente nasci com a camisa do Fluminense. Meu pai conta que foi a primeira coisa que ele me deu no hospital.", "Julio César Romero, filho do Romerito");
		createPhrase("Eu vos digo que o melhor time é o Fluminense. E podem me dizer que os fatos provam o contrário, que eu vos respondo: pior para os fatos.", "Nelson Rodrigues");
		createPhrase("Eu tenho o maior orgulho de dizer que sou tricolor. Sou como o saudoso Mário Lago, tricolor e comunista, e faço questão de mostrar isso. Aliás, o mundo do samba é cheio de tricolores. Paulo da Portela e Cartola eram tricolores de faltar ao samba para ver o Flu. E, graças a Deus, posso dizer que sou um homem feliz. Meu pai, flamenguista, me levou pela primeira vez a um Fla-Flu, em 1938, nas Laranjeiras. Metemos três ou quatro neles (foi 3 a 0) e de lá pra cá só comemoro. Cansei de ver Castilho jogar, a Máquina e a turma do Casal 20 então, nem se fala. Era ir para o Maraca com a certeza da vitória. Eu até hoje me emociono quando vejo o Fluminense jogar.", "Noca da Portela");
		createPhrase("Não se dá um passo em Álvaro Chaves sem tropeçar numa glória.", "Nelson Rodrigues");
		createPhrase("Grandes são os outros, o Fluminense é enorme.", null);
		createPhrase("'Você é químico?' Não, sou Fluminense, respondi de pronto ao ser abordado por um vizinho que me viu brincando com alguns líquidos de diversas cores. Eu tinha apenas três anos de idade, mas com uma convicção clubística anterior ao meu nascimento, e, quem sabe, anterior ao útero materno.", "Nelson Rodrigues Filho");
		createPhrase("Tricolor pela poesia, Tricolor pela beleza das cores, Tricolor pelo verde das matas, pelo branco das nuvens, pelo vinho, a cor do amor! Fluminense é isso, é amor, amor pelo Futebol, amor pela nação! Tricolor sou, Tricolor serei, até o fim da minha vida!", "Gilberto Gil");
		createPhrase("As três cores entram pela retina, resvalam na mente e ficam tatuadas no coração. É para sempre, não há como tirar. Vai-se a infância, torna-se adulto, vêm os amores que, as vezes, se vão. Vêm os filhos, vão-se os amigos, as mulheres, os filhos se casam, parentes se vão, mas estão as três cores no teu coração. Vem a velhice, os filhos mudaram, as mulheres o largaram, Solidão, mas permanece no peito, o Fluzão. É o mais longo amor de toda uma vida, tatuagem grudada, tatuagem sofrida, só solta do peito junto d'alma na tua subida. Tatuada na infância e para além dessa vida. Nunca haverá despedida.", "Joaquim Carlos");
		createPhrase("Já vos disse, somos campeões natos assim como um rei nasce rei. Um rei já o é de berço.", "Nelson Rodrigues");
		createPhrase("Fiquei dois anos e oito meses no Batalhão de Guardas e esse jogo eu ouvi através do rádio do X13, minha cela era X4. Nesse momento, nessa vitória do Fluminense, eu estava completamente livre. Até nisso, quero dizer, até na cadeia o Fluminense foi importantíssimo.", "Nelson Rodrigues Filho");
		createPhrase("Eu não deixo de pensar e sentir o Fluminense todos os dias.", "Fausto Fawcett");
		createPhrase("Quem olha para o nosso passado, quem examina a nossa história, tem que chegar honestamente a uma conclusão fatal: o Fluminense deve ser o primeiro em tudo, particularmente, no futebol.", "Nelson Rodrigues");
		createPhrase("Eu disse para a minha mulher: \"eu vou fugir e vou para o jogo\". Ela disse: \"tá maluco? No dia do seu casamento?\" Mas eu saí pela porta dos fundos durante a festa. Meu sogro nunca me perdoou. E fui ver o jogo do Fluminense.", "Hugo Carvana");
		createPhrase("Antes de Fluminense e América jogarem pelo returno do carioca de 1925, o presidente americano Raul Meirelles Reis faleceu. Em sinal de luto, os rubros decidiram então não comparecer ao jogo, entregando ao Fluminense os pontos pela vitória. O Flu recusou e pediu o adiamento do jogo, o que não foi aceito pela Liga, confirmando a vitória do Flu por W.O. O América, entretanto, sensibilizou-se com o gesto Tricolor e lhe enviou um ofício de agradecimento: \n\"(...)Embora o nobre gesto não nos surpreende-se, nem por isso deixou de sensibilizar extremamente o América(...)\"", "Revista Lance! 1999");
		createPhrase("Essa torcida abnegada, que nos acompanha; que está com o clube no insucesso ou na glória; torcida que tem fogo nas entranhas - essa torcida, dizia eu, explica a imortalidade do Tricolor. Sem uma torcida fiel, plena de amor, um clube envelhece, agoniza e morre. Mas aquela torcida tricolor do Fla-Flu é tão imortal quanto o seu clube. E foi por isso, que, na competição com o Rubro-negro, ganhou, espetacularmente. Não sei se é a maior e talvez não seja a maior. mas eu direi que a torcida do Fluminense é a mais doce, a mais iluminada torcida do Brasil e do mundo.", "Nelson Rodrigues");
		createPhrase("Minha maior alegria é ter jogado no Fluminense, o maior clube que eu conheci.", "Romerito");
		createPhrase("O Tricolor tem aquele toque de classe, o respeito à tradição e a tudo o que o Fluminense representa no futebol brasileiro. E é muito alegre ser parte desta torcida, sentir-se cúmplice dela nas ruas. A torcida do Fluminense não é aquela coisa superpopular. Mas é composta de pessoas interessantes, esta é a diferença. Sou tricolor por causa de meu pai, apesar de ter tios rubro-negros e alvinegros. Como esquecer a época maravilhosa da Máquina Tricolor? Nunca vou esquecer um amistoso no Maracanã, contra o Bayern de Munique. Naquele dia, Cafuringa encarnou Garrincha. Mário Sérgio e Rivelino estavam fazendo mágica em campo. O Fluminense deu olé em Beckenbauer. Ser Fluminense é ser guerreiro para enfrentar as horas difíceis, é ser a favor da paz, da alegria, do craque e da bola na rede.", "Evandro Mesquita");
		createPhrase("Troféus, pode-se ganhar, perder, mas um patrimônio como esse, feito de milhões de anônimos torcedores para quem o time é parte fundamental da vida, não importa se vencedor ou derrotado, se orgulhoso ou humilhado, isso, definitivamente, não se encontra em qualquer clube", "Wevergton Brito Lima, jornalista");
		createPhrase("Por Gerson e Rivelino que, até hoje, querem tão bem ao Fluminense; pelo Flamengo que nasceu de uma costela tricolor; pelo Botafogo, velho co-irmão de rancores e de amores remotos; pelo mais enxuto verso do futebol: Fla-Flu, doce aliteração carioca; pela bandeira tricolor: o branco, símbolo da alma pura, o vermelho, a cor dos nobres e o verde, a luz da criação do mundo!\nE porque o Fluminense não pode morrer, sua divisa, agora, é Viver ou Viver!", "Armando Nogueira");
		createPhrase("O Flamengo tem mais torcida, o Fluminense tem mais gente", "Nelson Rodrigues");
		createPhrase("Não sou tricolor de coração, mas de alma. Pois o coração um dia pára e a alma é eterna.", null);
		createPhrase("Não que as outras torcidas sejam feias mas a do Fluminense é muito linda", "Romário");
		createPhrase("Sem dúvida, a grande desgraça do Flamengo é ter que conviver com um rival tão esnobe, capaz de ridicularizar aquela pobre torcida desiludida, de que outros tantos clubes tinham medo.", null);
		createPhrase("O Maracanã foi varrido por uma tempestade pó-de-arroz. As nossas bandeiras como estandartes de luz. Um turista que lá chegasse, e espiasse a nossa explosão dionisíaca, havia de anotar em seu caderninho: \"O Brasil tem 80 milhões de tricolores!\"", "Nelson Rodrigues");
		createPhrase("Se ainda usássemos chapéu teríamos de tirá-lo toda vez que fossemos falar do Fluminense.", "Nelson Rodrigues");
		createPhrase("Tradição não se compra com títulos e não se perde com decepções. Tradição um clube tem ou não desde sua fundação. E isso, meus caros amigos, o Fluminense tem até de sobra.", null);
		createPhrase("Uma torcida não vale a pena pela sua expressão numérica. Ela vive e influi no destino das batalhas pela força do sentimento. E a torcida tricolor leva um imperecível estandarte de paixão.", null);
		createPhrase("Ser tricolor não é uma questão de gosto ou opção, mas um acontecimento de fundo metafísico, um arranjo cósmico ao qual não se pode - e nem se deseja – fugir.", null);
		createPhrase("...Alguns dizem: vocês ganharam poucos títulos nos últimos anos. Mas imaginem vocês se ganhássemos todos os anos? O que sobraria para os demais? Somente os frios metais dos troféus, porque todos os sentimentos teriam sido consumidos por nós tricolores. Enfim, ninguém é campeão da forma como nós somos!", "Mauro Jácome");
		createPhrase("Lembro à todos que a minha grande paixão pelo Fluminense começou quando tinha 11 anos e o Fluminense havia sido no dia do meu aniversário campeão Estadual de 1995 em cima do Arqui-Rival Flamengo , daí a importância de um clube ser campeão , para alimentar o orgulho e a paixão do torcedor e chamar mais gente e atenção voltada para o querido Tricolor das Laranjeiras.", "Rogério Salarini");
		createPhrase("O Fluminense é um mega clube, uma vez que através dos tempos tem sido sistematicamente profanado, saqueado e vilipendiado, mas mesmo assim resiste a todos os ataques e a todas as intempéries, pois foi forjado com blocos de amor maciço.", "Jamir");
		createPhrase("Sou Fluminense desde antes de nascer. Fui Fluminense ontem, cansado, sofrido, feliz e triste por uma questão de segundos. Sou Fluminense hoje, abatido, traído pelo destino, mas orgulhoso do manto que visto. Serei Fluminense mesmo depois de morrer, como Nelson, como Mario Lago, como Careca. Seria Fluminense mesmo se não existisse, como o Gravatinha.", null);
		createPhrase("A camisa tricolor não é uma veste, que se despe com facilidade e até com indiferença, mas uma outra pele, que adere à própria alma, irreversível, para sempre.", null);
		createPhrase("Deus é carioca, Deus é tricolor. Quando ataca, vence. Sem covardia, na garra, na raça, na luta, na força, na determinação. E o Fluminense chega!", "Luiz Penido");
		createPhrase("...Chega a ser cômico falar nos seus problemas. De todos os clubes, o Tricolor é o que tem melhor saúde econômica, melhor saúde financeira. Se ainda usássemos o chapéu, teríamos que tirá-lo em sentida e obrigatória reverência, sempre que falássemos no seu nome. Os outros, todos os outros, estão vergados ao peso de dívidas como uma árvore ao peso dos frutos.\nO Fluminense, não. O Fluminense nasceu com a vocação da eternidade. Tudo pode passar, só o Tricolor não passará, jamais.\nQuem diz é o óbvio ululante.", "Nelson Rodrigues");
		createPhrase("Há os que são Flamengo doente. Eu sou Fluminense saudável.", "Millôr Fernandes");
		createPhrase("Aos futuros pais tricolores fica o apelo para que cuidem, sempre, que seus filhos escutem o brado de \"nense\" desde o ventre da mãe e não descurem jamais das coisas do Fluminense. A centelha da paixão pelo tricolor é inextinguível. Nelson Rodigues dizia que \"a morte não exime ninguém de suas obrigações clubísticas\"", "Antônio Carlos Teixeira Rocha");
		createPhrase("Então, a mitologia do Tricolor é uma mitologia muito mais rica, muito mais fascinante, muito mais interessante do que a de qualquer outro clube brasileiro, por obra e graça de Nelson Rodrigues", "Sérgio Sá Leitão");
		createPhrase("Certa vez, invoquei o videotape para comprovar um gol irregular do Fluminense. Nelson Rodrigues me jogou pela cara a sentença desconcertante: O videotape é burro!", "Armando Nogueira");
		createPhrase("Mas até hoje há quem diga ter sido Castilho o mais perfeito goleiro já aparecido em nosso futebol. Era notável na colocação, sóbrio nas defesas, impressionantemente seguro e além de tudo, possuidor de muita sorte.", "Luis Mendes");
		createPhrase("O Fluminense é a organização esportiva mais perfeita do mundo.", "Jules Rimet");
		createPhrase("Impossível não é um fato, é uma opinião. Não é uma declaração, é um desafio. É hipotético. Impossível não existe. Não para o Fluminense", null);
		createPhrase("As três cores entram pela retina, resvalam na mente e ficam tatuadas no coração. É para sempre, não há como tirar. Vai-se a infância, torna-se adulto, vêm os amores que, as vezes, se vão. Vêm os filhos, vão-se os amigos, as mulheres, os filhos se casam, parentes se vão, mas estão as três cores no teu coração. Vem a velhice, os filhos mudaram, as mulheres o largaram, Solidão, mas permanece no peito, o Fluzão. É o mais longo amor de toda uma vida, tatuagem grudada, tatuagem sofrida, só solta do peito junto d'alma na tua subida. Tatuada na infância e para além dessa vida. Nunca haverá despedida.", "Joaquim Carlos");
		createPhrase("Hei de torcer por ti, Tricolor, até o fim dos meus dias; com a mesma força e paixão que sinto desde o dia em que, por você, me apaixonei.", null);
		createPhrase("Porque não existe paixão em preto e branco. Paixão de verdade tem que ter cor. Uma só, não. Três.", null);
		createPhrase("Sinto muito carinho e orgulho por defendê-los (os torcedores) em campo. Sou muito grato por tudo o que a torcida tem feito por mim. Sou agradecido a tudo o que o Fluminense tem me dado. Hoje, sem dúvida, é o lugar onde me senti melhor e tudo isso por causa dessa torcida linda. É uma torcida diferenciada, porque no momento mais difícil estava ao nosso lado.", "Dario Conca");
		createPhrase("Esqueça os fatos, a razão, a lógica.O Fluminense desafia todos eles e vence.Essas coisas não combinam com o Tricolor,ele está acima disso,é uma questão mais de amor, paixão, tradição,coragem, esperança, superação e acima de tudo, emoção. Fluminense? Não se explica...", "Júnior Costa");
		createPhrase("É muito dificil falar nesse momento. Eu sei que futebol é importante pra fazer o pé de meia, mas com essa camisa eu jogo por amor.", "Thiago Silva");
		createPhrase("Do lado esquerdo do peito, um pouco abaixo do radinho de pilha, o escudo protege o coração tricolor. E o escudo sorri. Não quero explicar esse sorriso. Não venham me falar de letras e ilusões infantis. Do alto da minha paixão, vivo eternos 11 anos de idade e o escudo me sorri sussurrante. O Fluminense é a minha terra do nunca. Sempre.", "Pedro Bial");
		createPhrase("O Fluminense sempre encarou todas as guerras vestido de branco.", "Pedro Bial");
		createPhrase("Acima de tudo um ídolo tem que se identificar com o clube, gostar, amar o clube. Eu, particularmente, prejudiquei minha vida particular, rejeitando infinitas propostas de clubes de Portugal, Japão, Espanha e de todo Brasil, porque eu adorava o Fluminense.", "Ézio");
	}

	private void createPhrase(String p, String a) {
		Phrase ph = new Phrase(new Long(0), p, a);
		ph.setId(repo.save(ph));
	}

	@Override
	protected void onDestroy() {
		repo.close();
		super.onDestroy();
	}

	
	@SuppressWarnings("unused")
	private class RepoPhrases {
		private static final String NOME_BANCO = "phrases_bd";
		private static final String NOME_TABELA = "phrases_bd_table";
		private static final String DB_CREATE_TABLE_PT = "CREATE TABLE IF NOT EXISTS "
	        + NOME_TABELA + " (" + "id integer primary key autoincrement,"
	        + "phrase text not null," + "author text not null" + " )";
		protected SQLiteDatabase db;
		
		public RepoPhrases(Context c){
			db = c.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(DB_CREATE_TABLE_PT);				
		}
		
		public long save(Phrase phrase){
			long id = phrase.getId();
			
			if(id != 0) update(phrase);
			else id = insert(phrase);
			
			return id;
		}
		
		private long insert(Phrase phrase){
			ContentValues values = new ContentValues();
			values.put("phrase", phrase.getPhrase());
			values.put("author", phrase.getAuthor());
			long id = insert(values);
			return id;
		}
		
		private long insert(ContentValues values){
			long id = db.insert(NOME_TABELA, "", values);
			return id;
		}
		
		private int update(Phrase phrase){
			ContentValues values = new ContentValues();
			values.put("phrase", phrase.getPhrase());
			values.put("author", phrase.getAuthor());
			String _id = String.valueOf(phrase.getId());
			String where = "id=?";
			String[] whereArgs = new String[] { _id };
			int count = update(values, where, whereArgs);
			return count;
		}

		private int update(ContentValues values, String where, String[] whereArgs) {
			int count = db.update(NOME_TABELA, values, where, whereArgs);
			//Log.i("inFLU", "Atualizou ["+count+"] registros");
			return count;
		}
		
		public int delete(long id){
			String where = "id=?";
			String _id = String.valueOf(id);
			String[] whereArgs = new String[] { _id };
			int count = delete(where, whereArgs);
			return count;
		}

		private int delete(String where, String[] whereArgs) {
			int count = db.delete(NOME_TABELA, where, whereArgs);
			//Log.i("inFLU", "Deletou ["+count+"] registros");
			return count;
		}
		
		public Phrase findPhrase(long id){
			Cursor c = db.query(true, NOME_TABELA, Phrase.COLUNAS, "id=" + id, null, null, null, null, null);
			startManagingCursor(c);
			if(c.getCount() > 0){
				c.moveToFirst();
				Phrase phrase = new Phrase();
				phrase.setId(c.getLong(0));
				phrase.setPhrase(c.getString(1));
				phrase.setAuthor(c.getString(2));
				return phrase;
			}
			return null;
		}
		
		private Cursor getCursor() {
			try{
				return db.query(NOME_TABELA, Phrase.COLUNAS, null, null, null, null, null, null);
			}catch(SQLException e){
				Log.e("inFLU", "Erro ao buscar os registros: " + e.toString());
				return null;
			}
		}
		
		public ArrayList<String> getAuthorsList(){
			ArrayList<String> authors = new ArrayList<String>();
			for (Phrase p : repo.listPhrases()) {
				if(!authors.contains(p.getAuthor().toString())) authors.add(p.getAuthor().toString());
			}
			return authors;
		}
		
		public List<Phrase> listPhrases(){
			Cursor c = getCursor();
			startManagingCursor(c);
			List<Phrase> phrases = new ArrayList<Phrase>();
			if(c.moveToFirst()){
				int idxId = c.getColumnIndex("id");
				int idxPhrase = c.getColumnIndex("phrase");
				int idxAuthor = c.getColumnIndex("author");
				do{
					Phrase phrase = new Phrase();
					phrases.add(phrase);
					phrase.setId(c.getLong(idxId));
					phrase.setPhrase(c.getString(idxPhrase));
					phrase.setAuthor(c.getString(idxAuthor));
				}while (c.moveToNext());
			}
			return phrases;
		}
		
		public Phrase findPhraseByAuthor(String author){
			Phrase phrase = null;
			try{
				Cursor c = db.query(NOME_TABELA, Phrase.COLUNAS, "author='"+author+"'", null, null, null, null, null);
				startManagingCursor(c);
				if(c.moveToNext()){
					phrase = new Phrase();
					phrase.setId(c.getLong(0));
					phrase.setPhrase(c.getString(1));
					phrase.setAuthor(c.getString(2));
				}
			}catch (SQLException e){
				Log.e("inFLU", "Erro ao buscar os registros por autor: " + e.toString());
				return null;
			}
			return phrase;
		}
		
		public ArrayList<Phrase> findPhrasesByAuthor(String author){
			ArrayList<Phrase> alPhrases = new ArrayList<Phrase>();
			try{
				Cursor c = db.query(NOME_TABELA, Phrase.COLUNAS, "author='"+author+"'", null, null, null, null, null);
				startManagingCursor(c);
				while(c.moveToNext()){
					Phrase phrase = new Phrase();
					phrase.setId(c.getLong(0));
					phrase.setPhrase(c.getString(1));
					phrase.setAuthor(c.getString(2));
					alPhrases.add(phrase);
				}
			}catch (SQLException e){
				Log.e("inFLU", "Erro ao buscar os registros por autor: " + e.toString());
				return null;
			}
			return alPhrases;
		}
		
		public void close(){
			if(db != null) db.close();
		}
	}
}
