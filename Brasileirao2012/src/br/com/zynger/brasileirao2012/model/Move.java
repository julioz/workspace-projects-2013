package br.com.zynger.brasileirao2012.model;

import java.io.Serializable;

import android.text.Html;
import br.com.zynger.brasileirao2012.R;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Operacao {
		INCLUSAO, ALTERACAO, EXCLUSAO
	}
	
	public enum Tipo {
		LANCE, LANCE_IMPORTANTE, LANCE_CARTAO, LANCE_GOL, LANCE_SUBSTITUICAO, ATUALIZACAO_ESCALACAO;
		
		public int getImageResource() {
			if (this == LANCE_IMPORTANTE) {
				return R.drawable.img_squad_important;
			} else if (this == LANCE_GOL) {
				return R.drawable.img_squad_goal_pro;
			} else if (this == LANCE_SUBSTITUICAO) {
				return R.drawable.img_squad_subst;
			}
			return -1;
		}
	}
	
	public enum Cartao {
		A, V;
		
		public int getImageResource() {
			if (this == A){				
				return R.drawable.img_squad_cards_yellow;
			}else if (this == V){				
				return R.drawable.img_squad_cards_red;
			}
			return -1;
		}
	}
	
	public class Card implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("jogador_id") Integer jogadorId;
		@JsonProperty("cartao_id") Integer cartaoId;
		@JsonProperty("nome_jogador") String nomeJogador;
		@JsonProperty("atuacao_id") Integer atuacaoId;
		@JsonProperty Cartao tipo;
		
		public Card() { }
		
		public Integer getJogadorId() {
			return jogadorId;
		}
		
		public Integer getCartaoId() {
			return cartaoId;
		}
		
		public String getNomeJogador() {
			return nomeJogador;
		}
		
		public Integer getAtuacaoId() {
			return atuacaoId;
		}
		
		public Cartao getTipo() {
			return tipo;
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Video implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@JsonProperty Videos[] videos;
		
		public Video() { }
		
		public Videos[] getVideos() {
			return videos;
		}
		
		public String getUrl() {
			try{
				for (Resource resource : getVideos()[0].getResources()) {
					if(resource.getKind().equals("M4")){
						return resource.getUrl();
					}
				}
				return null;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Videos implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@JsonProperty private Integer id;
		@JsonProperty private String title;
		@JsonProperty private String type;
		@JsonProperty private String program;
		@JsonProperty private Integer programId;
		@JsonProperty("site_page") private String sitePage;
		@JsonProperty private Integer zoneId;
		@JsonProperty("default_video_id") private Integer defaultVideoId;
		@JsonProperty private String channel;
		@JsonProperty private String category;
		@JsonProperty private Integer duration;
		@JsonProperty Resource[] resources;
		
		public Videos() { }
		
		public Resource[] getResources() {
			return resources;
		}

		public Integer getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getType() {
			return type;
		}

		public String getProgram() {
			return program;
		}

		public Integer getProgramId() {
			return programId;
		}

		public String getSitePage() {
			return sitePage;
		}

		public Integer getZoneId() {
			return zoneId;
		}

		public Integer getDefaultVideoId() {
			return defaultVideoId;
		}

		public String getChannel() {
			return channel;
		}

		public String getCategory() {
			return category;
		}

		public Integer getDuration() {
			return duration;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Resource implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("_id") String id;
        @JsonProperty Integer bitrate;
        @JsonProperty String definition;
        @JsonProperty Integer height;
		@JsonProperty String kind;
		@JsonProperty String url;
		@JsonProperty Integer duration;
		
		public Resource() { }
		
		public String getUrl() {
			return url;
		}

		public String getId() {
			return id;
		}

		public Integer getBitrate() {
			return bitrate;
		}

		public String getDefinition() {
			return definition;
		}

		public Integer getHeight() {
			return height;
		}

		public String getKind() {
			return kind;
		}

		public Integer getDuration() {
			return duration;
		}
	}
	
	@JsonProperty private Operacao operacao;
	@JsonProperty("url_thumb_playlist") private String urlThumbPlaylist;
	@JsonProperty private Tipo tipo;
	@JsonProperty("nome_time") private String nomeTime;
	@JsonProperty private String periodo;
	@JsonProperty("video_id") private Integer videoId;
	@JsonProperty("periodo_id") private Integer periodoId;
	@JsonProperty private String texto;
	@JsonProperty("url_thumb_playlist_aovivo") private String urlThumbPlaylistAoVivo;
	@JsonProperty private Integer momento;
	@JsonProperty private Integer time;
	@JsonProperty("url_thumb_feed") private String urlThumbFeed;
	@JsonProperty("cartao") private Card card;
	@JsonProperty private Integer id;
	private String videoUrl;
	
	public Move() { }
	
	public Operacao getOperacao() {
		return operacao;
	}

	public String getUrlThumbPlaylist() {
		return urlThumbPlaylist;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getNomeTime() {
		return nomeTime;
	}

	public String getPeriodo() {
		return periodo;
	}

	public Integer getVideoId() {
		return videoId;
	}
	
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public Integer getPeriodoId() {
		return periodoId;
	}

	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getUrlThumbPlaylistAoVivo() {
		return urlThumbPlaylistAoVivo;
	}

	public Integer getMomento() {
		return momento;
	}

	public Integer getTime() {
		return time;
	}

	public String getUrlThumbFeed() {
		return urlThumbFeed;
	}

	public Integer getId() {
		return id;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}
	
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public Card getCard() {
		return card;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((momento == null) ? 0 : momento.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result
				+ ((periodoId == null) ? 0 : periodoId.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		if (momento == null) {
			if (other.getMomento() != null)
				return false;
		} else if (!momento.equals(other.getMomento()))
			return false;
		if (periodo == null) {
			if (other.getPeriodo() != null)
				return false;
		} else if (!periodo.equals(other.getPeriodo()))
			return false;
		if (periodoId == null) {
			if (other.getPeriodoId() != null)
				return false;
		} else if (!periodoId.equals(other.getPeriodoId()))
			return false;
		if (texto == null) {
			if (other.getTexto() != null)
				return false;
		} else if (!texto.contains(other.getTexto()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Move [operacao=" + operacao + ", urlThumbPlaylist="
				+ urlThumbPlaylist + ", tipo=" + tipo + ", nomeTime="
				+ nomeTime + ", periodo=" + periodo + ", videoId=" + videoId
				+ ", periodoId=" + periodoId
				+ ", urlThumbPlaylistAoVivo=" + urlThumbPlaylistAoVivo
				+ ", momento=" + momento + ", time=" + time + ", urlThumbFeed="
				+ urlThumbFeed + ", id=" + id + "]";
	}
	
	public String getShareableContent() {
		String message = (getMomento() != null ? getMomento()
				+ " - " : new String());
		message = message + getPeriodo() + "\n"
				+ Html.fromHtml(getTexto()).toString();
		return message;
	}
}
