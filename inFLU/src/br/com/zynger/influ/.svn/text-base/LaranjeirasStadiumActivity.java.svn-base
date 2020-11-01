package br.com.zynger.influ;

import br.com.zynger.influ.model.Theme;
import br.com.zynger.influ.util.FileHandler;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class LaranjeirasStadiumActivity extends Activity {
	
	private TextView tv_info, tv1, tv2, tv3;
	private ImageView iv1, iv2, iv3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_stadium);
		
		tv_info = (TextView) findViewById(R.laranj.tv_info);
		tv1 = (TextView) findViewById(R.laranj.tv1);
		tv2 = (TextView) findViewById(R.laranj.tv2);
		tv3 = (TextView) findViewById(R.laranj.tv3);
		
		iv1 = (ImageView) findViewById(R.laranj.iv1);
		iv2 = (ImageView) findViewById(R.laranj.iv2);
		iv3 = (ImageView) findViewById(R.laranj.iv3);
		
		updateTheme();
		
		tv_info.setText(Html.fromHtml("<b>Nome:</b> Estádio Manoel Schwartz <i>(Estádio das Laranjeiras, Estádio da Rua Álvaro Chaves)</i><br /><b>Dimensões:</b> 104m x 70m<br /><b>Capacidade:</b> 18.000 <i>(1919)</i>, 25.000 <i>(1922)</i>, 4.300 <i>(2010)</i><br /><b>Construção:</b> 21 de janeiro de 1919<br /><b>Inauguração:</b> 11 de maio de 1919<br /><b>1° Jogo:</b> Brasil 6x0 Chile<br /><b>1° Gol:</b> Arthur Friedenreich <i>(Brasil)</i><br /><b>Público Recorde:</b> 14 de junho de 1925, 25.718 pagantes para Fluminense 3x1 Flamengo"));
		tv1.setText(Html.fromHtml("No dia 21 de janeiro de 1919, para socorrer a Confederação Brasileira de Desportos que queria sediar o Campeonato Sul Americano de Futebol (vencido pela primeira vez pelo Brasil), o tricolor deu mais uma demonstração de força e prestígio. Em tempo recorde, ergueu o Estádio das Laranjeiras com capacidade para 18 mil pessoas."));
		tv2.setText(Html.fromHtml("No centenário da Independência, realizou-se novo Sul Americano e o Fluminense, mais uma vez não decepcionou. Ao ser acionado, construiu mais um lance de arquibancadas aumentando sua capacidade em 5 mil lugares para 25000 assentos.<br /><br />Segundo depoimento do grande benemérito e ex-presidente tricolor Marcos Carneiro de Mendonça, a construção do segundo lance de arquibancadas do Estádio foi feita a pedido do Presidente Epitácio Pessoa apesar da diretoria ser contra, tendo o Fluminense mais uma vez arcado com todos os custos.<br /><br />Em alguns jogos este estádio teve públicos estimados maiores que a sua capacidade, mas aparentemente o recorde de público pagante deste estádio foi na partida Fluminense 3 a 1 Flamengo, em 14 de junho de 1925, quando 25.718 espectadores pagaram ingressos, embora nos dias de hoje se desconheça o público da partida do Fluminense contra o Sporting Clube de Portugal, realizado em 15 de julho de 1928 na disputa da Taça Vulcain, com o estádio lotado e mais 2.000 cadeiras sendo colocadas na pista de atletismo para comportar o público presente.<br /><br />A Seleção Brasileira jogou 18 jogos nesta sua primeira casa, ganhando 13 e empatando 5, entre 11 de Maio de 1919 e 6 de Setembro de 1931 e incluindo o primeiro jogo da história da Seleção Brasileira contra o Exeter City, antes da construção das novas arquibancadas. Assim como o jogador do Fluminense e capitão da Seleção, Preguinho, viria a fazer o primeiro gol do Brasil em Copas do Mundo,o também jogador tricolor Oswaldo Gomes, veio a fazer neste estádio o primeiro gol das História da Seleção Brasileira, na vitória por 2 a 0 sobre o Exeter City F. C. da Inglaterra aos 28 minutos de jogo, em 21 de Julho de 1914, aniversário de 12 anos do Fluminense Football Club.<br /><br />Hoje em dia, a capacidade do estádio apresenta-se reduzida para 4.300 torcedores e o campo mede 70 x 104 metros. Tal redução deveu-se a uma desapropriação para a duplicação da Rua Pinheiro Machado, necessária para o escoamento do trânsito do Túnel Santa Bárbara e o crescimento do bairro de Laranjeiras, além de momentâneas questões de segurança, pois algumas áreas do estádio requerem reformas para que ele possa comportar cerca de 8.000 pessoas."));
		tv3.setText(Html.fromHtml("O Estádio é anexo ao Palácio Guanabara, sede do Governo do Estado do Rio de Janeiro e antiga sede da República do Brasil. Apesar de toda a tradição de Laranjeiras, entretanto, foi no Maracanã que o Fluminense conquistou suas maiores glórias nas últimas décadas, dada a diferença de capacidade entre os dois estádios e a redução ocorrida em Laranjeiras a partir de 1961.<br /><br />O Fluminense não joga mais partidas oficiais no Estádio das Laranjeiras, onde disputou 839 partidas, com 531 vitórias, 158 empates, 150 derrotas, 2.206 gols pró e 1049 gols contra, até o último jogo disputado, em 26 de fevereiro de 2003, empate de 3 a 3 contra o Americano Futebol Clube, pelo Campeonato Carioca."));
		
		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				switch(id){
				case R.laranj.iv1:
					showDialogWithImageView(R.drawable.laranj_1);
					break;
				case R.laranj.iv2:
					showDialogWithImageView(R.drawable.laranj_2);
					break;
				case R.laranj.iv3:
					showDialogWithImageView(R.drawable.laranj_3);
					break;
				default:
					break;
				}
			}
		};
		
		iv1.setOnClickListener(ocl);
		iv2.setOnClickListener(ocl);
		iv3.setOnClickListener(ocl);
	}
	
	private void updateTheme() {
		Theme t = (Theme) FileHandler.openBackup(this, ThemeChooserActivity.ARQUIVO);
		findViewById(R.laranj.ll_actbg).setBackgroundColor(t.getAct_background());
		findViewById(R.laranj.ll_info).setBackgroundColor(t.getContent_background());
		findViewById(R.laranj.ll_tv1).setBackgroundColor(t.getContent_background());
		findViewById(R.laranj.ll_tv2).setBackgroundColor(t.getContent_background());
		findViewById(R.laranj.ll_tv3).setBackgroundColor(t.getContent_background());
		findViewById(R.laranj.ll_tv4).setBackgroundColor(t.getContent_background());
		int colors[] = { t.getAbgradstart() , t.getAbgradend() };
		findViewById(R.laranj.actionbar).setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
		findViewById(R.laranj.ll_actbg).invalidate();
	}

	private void showDialogWithImageView(int resource){
		Dialog d = new Dialog(this); 
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.history_stadium_images);
		ImageView iv = (ImageView) d.findViewById(R.history_stadium_images.iv);
		iv.setImageResource(resource);
		d.setCanceledOnTouchOutside(true);
		d.show();
	}
}
