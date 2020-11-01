package br.com.zynger.libertadores.model;

public class Move {

	public static final int TYPE_MOVE = 0;
	public static final int TYPE_IMPORTANTMOVE = 1;
	public static final int TYPE_GOAL = 2;
	public static final int TYPE_CARD = 3;
	public static final int TYPE_SUBSTITUTION = 4;
	
	public static final int CARD_NULL = -1;
	public static final int CARD_YELLOW = 6;
	public static final int CARD_RED = 7;
	
	private int id;
	private String operacao;
	private String nomeTime;
	private String periodo;
	private String videoId;
	private String texto;
	private String momento;
	private String videoUrl;
	private String urlThumb;
	private int tipo;
	private int periodoId;
	private int cartao;
	
	
	
	public Move(int id, String operacao, String nomeTime, String periodo,
			String videoId, String texto, String momento, String videoUrl,
			String urlThumb, int tipo, int periodoId, int cartao) {
		this.id = id;
		this.operacao = operacao;
		this.nomeTime = nomeTime;
		this.periodo = periodo;
		this.videoId = videoId;
		this.texto = texto;
		this.momento = momento;
		this.videoUrl = videoUrl;
		this.urlThumb = urlThumb;
		this.tipo = tipo;
		this.periodoId = periodoId;
		this.cartao = cartao;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOperacao() {
		return operacao;
	}
	
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	
	public String getNomeTime() {
		return nomeTime;
	}
	
	public void setNomeTime(String nomeTime) {
		this.nomeTime = nomeTime;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getVideoId() {
		return videoId;
	}
	
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getMomento() {
		return momento;
	}
	
	public void setMomento(String momento) {
		this.momento = momento;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}
	
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public String getUrlThumb() {
		return urlThumb;
	}
	
	public void setUrlThumb(String urlThumb) {
		this.urlThumb = urlThumb;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getPeriodoId() {
		return periodoId;
	}
	
	public void setPeriodoId(int periodoId) {
		this.periodoId = periodoId;
	}
	
	public int getCartao() {
		return cartao;
	}
	
	public void setCartao(int cartao) {
		this.cartao = cartao;
	}
	
	@Override
	public String toString() {
		return getMomento() + " - " + getTexto();
	}
	
}
