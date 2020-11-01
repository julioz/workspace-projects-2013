package br.com.site;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import br.com.site.MainActivity.CustomItemizedOverlay;
import br.com.site.model.Equipamento;
import br.com.site.model.Equipe;
import br.com.site.model.Estrutura;
import br.com.site.model.PDI;
import br.com.site.model.Perigo;
import br.com.site.model.Veiculo;
import br.com.site.model.Vitima;
import br.com.site.web.ClientThread;

public class EditarPDI {

	public static void atualizarPDI(PDI pdi){
		int idDoIcone = MainActivity.getIconeCerto(pdi);

		if(pdi instanceof Veiculo){
			atualizarMarker(idDoIcone, MainActivity.veiculoOverlays, pdi);
			if(!MainActivity.listaVeiculos.contains(pdi)) MainActivity.listaVeiculos.add((Veiculo) pdi);
		}else if(pdi instanceof Estrutura){
			atualizarMarker(idDoIcone, MainActivity.estruturaOverlays, pdi);
			if(!MainActivity.listaEstruturas.contains(pdi)) MainActivity.listaEstruturas.add((Estrutura) pdi);
		}else if(pdi instanceof Equipamento){
			atualizarMarker(idDoIcone, MainActivity.equipamentoOverlays, pdi);
			if(!MainActivity.listaEquipamentos.contains(pdi)) MainActivity.listaEquipamentos.add((Equipamento) pdi);
		}else if(pdi instanceof Equipe){
			atualizarMarker(idDoIcone, MainActivity.equipeOverlays, pdi);
			if(!MainActivity.listaEquipes.contains(pdi)) MainActivity.listaEquipes.add((Equipe) pdi);
		}else if(pdi instanceof Perigo){
			atualizarMarker(idDoIcone, MainActivity.perigoOverlays, pdi);
			if(!MainActivity.listaPerigos.contains(pdi)) MainActivity.listaPerigos.add((Perigo) pdi);
		}else if(pdi instanceof Vitima){
			atualizarMarker(idDoIcone, MainActivity.vitimaOverlays, pdi);
			if(!MainActivity.listaVitimas.contains(pdi)) MainActivity.listaVitimas.add((Vitima) pdi);
		}
	}

	private static void atualizarMarker(int idDoIcone, CustomItemizedOverlay cio, PDI pdi){
		if (idDoIcone != 0){
			Drawable icone = MainActivity.getMapView().getContext().getResources().getDrawable(idDoIcone);
			icone.setBounds(cio.getMarker().getBounds());
			pdi.getOverlayItem().setMarker(icone);
		}
	}
	
	public static ArrayList<PDI> getArrayDeLinks(PDI excluido){
		ArrayList<PDI> al = new ArrayList<PDI>();
		
		if(excluido instanceof Veiculo){
			Veiculo veiculo = (Veiculo) excluido;
			for (Equipamento eq : MainActivity.listaEquipamentos) {
				if(eq.getVeiculo() == null) continue;
				if(eq.getVeiculo().equals(veiculo)) al.add(eq);
			}
		}
		else if (excluido instanceof Estrutura){
			Estrutura estrutura = (Estrutura) excluido;
			for (Equipe e : MainActivity.listaEquipes) {
				if(e.getEstrutura() == null) continue;
				if(e.getEstrutura().equals(estrutura)) al.add(e);
			}
			for (Vitima v : MainActivity.listaVitimas) {
				if(v.getEstrutura() == null) continue;
				if(v.getEstrutura().equals(estrutura)) al.add(v);
			}
		}
		else if (excluido instanceof Equipamento){
			Equipamento equipamento = (Equipamento) excluido;
			for (Veiculo v : MainActivity.listaVeiculos) {
				if(v.getEquipamento() == null) continue;
				if(v.getEquipamento().equals(equipamento)) al.add(v);
			}
			for (Equipe e : MainActivity.listaEquipes) {
				if(e.getEquipamento() == null) continue;
				if(e.getEquipamento().equals(equipamento)) al.add(e);
			}
		}
		else if (excluido instanceof Equipe){
			Equipe equipe = (Equipe) excluido;
			for (Veiculo v : MainActivity.listaVeiculos) {
				if(v.getEquipe() == null) continue;
				if(v.getEquipe().equals(equipe)) al.add(v);
			}
			for (Estrutura es : MainActivity.listaEstruturas) {
				if(es.getEquipe() == null) continue;
				if(es.getEquipe().equals(equipe)) al.add(es);
			}
			for (Equipamento eq : MainActivity.listaEquipamentos) {
				if(eq.getEquipe() == null) continue;
				if(eq.getEquipe().equals(equipe)) al.add(eq);
			}
			for (Perigo p : MainActivity.listaPerigos) {
				if(p.getEquipe() == null) continue;
				if(p.getEquipe().equals(equipe)) al.add(p);
			}
			for (Vitima v : MainActivity.listaVitimas) {
				if(v.getEquipe() == null) continue;
				if(v.getEquipe().equals(equipe)) al.add(v);
			}
		}
		else if (excluido instanceof Perigo){
			Perigo perigo = (Perigo) excluido;
			for (Estrutura es : MainActivity.listaEstruturas) {
				if(es.getPerigo() == null) continue;
				if(es.getPerigo().equals(perigo)) al.add(es);
			}
			for (Equipe e : MainActivity.listaEquipes) {
				if(e.getPerigo() == null) continue;
				if(e.getPerigo().equals(perigo)) al.add(e);
			}
			for (Vitima v : MainActivity.listaVitimas) {
				if(v.getPerigo() == null) continue;
				if(v.getPerigo().equals(perigo)) al.add(v);
			}
		}
		else if (excluido instanceof Vitima){
			Vitima vitima = (Vitima) excluido;
			for (Estrutura es : MainActivity.listaEstruturas) {
				if(es.getVitima() == null) continue;
				if(es.getVitima().equals(vitima)) al.add(es);
			}
			for (Perigo p : MainActivity.listaPerigos) {
				if(p.getVitima() == null) continue;
				if(p.getVitima().equals(vitima)) al.add(p);
			}
		}else{
			al = null;
		}
		
		return al;
	}

	public static void promptDeletaObj(final Context c, final PDI pdi, final String xml){
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage("Tem certeza?")
		.setCancelable(false)
		.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				excluirObj(pdi);
				((Activity) c).finish();
				
				if(Global.isOnline()){
					if(!Global.souServer){ //sou cliente
						ClientThread ct = ClientThread.getInstance(Global.serverIp);
						ct.send(xml, ClientThread.EXCLUIRPDI);
					}else{
						//TODO TELLEVERYONE
						Global.tellEveryone(ClientThread.EXCLUIRPDI, xml);
					}
				}
			}
		})
		.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		if(pdi instanceof Veiculo) builder.setTitle("Excluir Veículo");
		if(pdi instanceof Estrutura) builder.setTitle("Excluir Estrutura");
		if(pdi instanceof Equipamento) builder.setTitle("Excluir Equipamento");
		if(pdi instanceof Equipe) builder.setTitle("Excluir Equipe");
		if(pdi instanceof Perigo) builder.setTitle("Excluir Perigo");
		if(pdi instanceof Vitima) builder.setTitle("Excluir Vítima");

		builder.show();
	}
	
	public static void excluirObj(PDI pdi){
		if(pdi instanceof Veiculo) excluirVeiculo((Veiculo) pdi);
		if(pdi instanceof Estrutura) excluirEstrutura((Estrutura) pdi);
		if(pdi instanceof Equipamento) excluirEquipamento((Equipamento) pdi);
		if(pdi instanceof Equipe) excluirEquipe((Equipe) pdi);
		if(pdi instanceof Perigo) excluirPerigo((Perigo) pdi);
		if(pdi instanceof Vitima) excluirVitima((Vitima) pdi);
		if(MainActivity.mapaPDI.containsKey(pdi.getId())) MainActivity.mapaPDI.remove(pdi.getId());
		MainActivity.hideBalloons();
		Global.editarPDI=false;
	}
	
	public static void substituiObj(PDI antigo, PDI pdi){
		if(pdi instanceof Veiculo) substituiVeiculo((Veiculo) antigo, (Veiculo) pdi);
		if(pdi instanceof Estrutura) substituiEstrutura((Estrutura) antigo, (Estrutura) pdi);
		if(pdi instanceof Equipamento) substituiEquipamento((Equipamento) antigo, (Equipamento) pdi);
		if(pdi instanceof Equipe) substituiEquipe((Equipe) antigo, (Equipe) pdi);
		if(pdi instanceof Perigo) substituiPerigo((Perigo) antigo, (Perigo) pdi);
		if(pdi instanceof Vitima) substituiVitima((Vitima) antigo, (Vitima) pdi);
		MainActivity.hideBalloons();
		atualizarPDI(antigo);
	}
	
	private static void substituiVeiculo(Veiculo antigo, Veiculo pdi) {
		antigo.setIdentificacao(pdi.getIdentificacao());
		antigo.setTipoDeVeiculo(pdi.getTipoDeVeiculo());
		antigo.setSubtipo(pdi.getSubtipo());
		antigo.setEquipamento(pdi.getEquipamento());
		antigo.setEquipe(pdi.getEquipe());
		antigo.setInst(pdi.getInst());
		antigo.setTituloBalao(pdi.getTituloBalao());
		antigo.atualizaTextoBalao();
	}
	
	private static void substituiEstrutura(Estrutura antigo, Estrutura pdi) {
		antigo.setIdentificacao(pdi.getIdentificacao());
		antigo.setTipoDeEstrutura(pdi.getTipoDeEstrutura());
		antigo.setAfluenciaPublico(pdi.getAfluenciaPublico());
		antigo.setDificuldadeDeEntrada(pdi.getDificuldadeDeEntradaInt());
		antigo.setEstadoDaRevisao(pdi.getEstadoDaRevisaoInt());
		antigo.setEvolucaoDoTrabalho(pdi.getEvolucaoDoTrabalhoInt());
		antigo.setEstabilidade(pdi.getEstabilidadeInt());
		antigo.setNumAndares(pdi.getNumAndares());
		antigo.setNumSubsolos(pdi.getNumSubsolos());
		antigo.setQtdPessoasPiso(pdi.getQtdPessoasPiso());
		antigo.setResistenciaPiso(pdi.getResistenciaPiso());
		antigo.setTamanhoDoAcesso(pdi.getTamanhoDoAcesso());
		antigo.setTempoAcesso(pdi.getTempoAcesso());
		antigo.setTipoDeMaterial(pdi.getTipoDeMaterial());
		antigo.setTipoDeSubsolo(pdi.getTipoDeSubsolo());
		antigo.setEquipe(pdi.getEquipe());
		antigo.setPerigo(pdi.getPerigo());
		antigo.setVitima(pdi.getVitima());
		antigo.setTituloBalao(pdi.getTituloBalao());
		antigo.atualizaTextoBalao();
	}
	
	private static void substituiEquipamento(Equipamento antigo, Equipamento pdi){
		antigo.setIdentificacao(pdi.getIdentificacao());
		antigo.setTipoDeEquipamento(pdi.getTipoDeEquipamento());
		antigo.setQuantidade(pdi.getQuantidade());
		antigo.setDisponiveis(pdi.getDisponiveis());
		antigo.setVeiculo(pdi.getVeiculo());
		antigo.setEquipe(pdi.getEquipe());
		antigo.setTituloBalao(pdi.getTituloBalao());
		antigo.atualizaTextoBalao();		
	}
	
	private static void substituiEquipe(Equipe antigo, Equipe pdi){
		antigo.setIdentificacao(pdi.getIdentificacao());
		antigo.setChefe(pdi.getChefe());
		antigo.setTipoDeFuncao(pdi.getTipoDeFuncaoInt());
		antigo.setQtdMembros(pdi.getQtdMembros());
		antigo.setQtdMembrosFeridos(pdi.getQtdMembrosFeridos());
		antigo.setTarefaAtual(pdi.getTarefaAtual());
		antigo.setEquipamento(pdi.getEquipamento());
		antigo.setEstrutura(pdi.getEstrutura());
		antigo.setInst(pdi.getInst());
		antigo.setPerigo(pdi.getPerigo());
		antigo.setUnid(pdi.getUnid());
		antigo.setTituloBalao(pdi.getTituloBalao());
		antigo.atualizaTextoBalao();
	}
	
	private static void substituiPerigo(Perigo antigo, Perigo pdi){
		antigo.setIdentificacao(pdi.getIdentificacao());
		antigo.setTipoDePerigo(pdi.getTipoDePerigo());
		antigo.setDevoIr(pdi.getDevoIr());
		antigo.setRiscoAssociado(pdi.getRiscoAssociadoInt());
		antigo.setEquipe(pdi.getEquipe());
		antigo.setVitima(pdi.getVitima());
		antigo.setTituloBalao(pdi.getTituloBalao());
		antigo.atualizaTextoBalao();
	}
	
	private static void substituiVitima(Vitima antigo, Vitima pdi){
		antigo.setIdentificacao(pdi.getIdentificacao());
		antigo.setNumeroPossiveisVitimas(pdi.getNumeroPossiveisVitimas());
		antigo.setQtdVivos(pdi.getQtdVivos());
		antigo.setQtdVivosResgatados(pdi.getQtdVivosResgatados());
		antigo.setQtdMortos(pdi.getQtdMortos());
		antigo.setQtdMortosRemovidos(pdi.getQtdMortosRemovidos());
		antigo.setEquipe(pdi.getEquipe());
		antigo.setEstrutura(pdi.getEstrutura());
		antigo.setPerigo(pdi.getPerigo());
		antigo.setTituloBalao(pdi.getTituloBalao());
		antigo.atualizaTextoBalao();
	}

	private static void excluirVeiculo(Veiculo v){
		ArrayList<PDI> links = getArrayDeLinks(v);
		Log.d("SiTE", "ja peguei o array de relacionamentos do veiculo");

		if(!links.equals(null) && links.size()>0){
			for (PDI pdi : links) {
				if(pdi instanceof Equipamento){
					Equipamento e = (Equipamento) pdi;
					e.setVeiculo(null);
					e.atualizaTextoBalao();
					EditarPDI.atualizarPDI(e);
				}
			}
		}
		MainActivity.listaVeiculos.remove(v);
		MainActivity.veiculoOverlays.remove(v.getOverlayItem(), v);
	}
	
	

	private static void excluirEstrutura(Estrutura e){
		ArrayList<PDI> links = getArrayDeLinks(e);
		if(!links.equals(null) && links.size()>0){
			for (PDI pdi : links) {
				if(pdi instanceof Equipe){
					Equipe es = (Equipe) pdi;
					es.setEstrutura(null);
					es.atualizaTextoBalao();
					EditarPDI.atualizarPDI(es);
				}
				if(pdi instanceof Vitima){
					Vitima vi = (Vitima) pdi;
					vi.setEstrutura(null);
					vi.atualizaTextoBalao();
					EditarPDI.atualizarPDI(vi);
				}
			}
		}

		MainActivity.listaEstruturas.remove(e);
		MainActivity.estruturaOverlays.remove(e.getOverlayItem(), e);
	}

	private static void excluirEquipamento(Equipamento e){
		ArrayList<PDI> links = getArrayDeLinks(e);
		if(!links.equals(null) && links.size()>0){
			for (PDI pdi : links) {
				if(pdi instanceof Veiculo){
					Veiculo v = (Veiculo) pdi;
					v.setEquipamento(null);
					v.atualizaTextoBalao();
					EditarPDI.atualizarPDI(v);
				}
				if(pdi instanceof Equipe){
					Equipe es = (Equipe) pdi;
					es.setEquipamento(null);
					es.atualizaTextoBalao();
					EditarPDI.atualizarPDI(es);
				}
			}
		}

		MainActivity.listaEquipamentos.remove(e);
		MainActivity.equipamentoOverlays.remove(e.getOverlayItem(), e);
	}

	private static void excluirEquipe(Equipe eq){
		ArrayList<PDI> links = getArrayDeLinks(eq);
		if(!links.equals(null) && links.size()>0){
			for (PDI pdi : links) {
				if(pdi instanceof Veiculo){
					Veiculo v = (Veiculo) pdi;
					v.setEquipe(null);
					v.atualizaTextoBalao();
					EditarPDI.atualizarPDI(v);
				}
				if(pdi instanceof Estrutura){
					Estrutura es = (Estrutura) pdi;
					es.setEquipe(null);
					es.atualizaTextoBalao();
					EditarPDI.atualizarPDI(es);
				}
				if(pdi instanceof Equipamento){
					Equipamento eqp = (Equipamento) pdi;
					eqp.setEquipe(null);
					eqp.atualizaTextoBalao();
					EditarPDI.atualizarPDI(eqp);
				}
				if(pdi instanceof Perigo){
					Perigo p = (Perigo) pdi;
					p.setEquipe(null);
					p.atualizaTextoBalao();
					EditarPDI.atualizarPDI(p);
				}
				if(pdi instanceof Vitima){
					Vitima vi = (Vitima) pdi;
					vi.setEquipe(null);
					vi.atualizaTextoBalao();
					EditarPDI.atualizarPDI(vi);
				}
			}
		}

		MainActivity.listaEquipes.remove(eq);
		MainActivity.equipeOverlays.remove(eq.getOverlayItem(), eq);
	}

	private static void excluirPerigo(Perigo p){
		ArrayList<PDI> links = getArrayDeLinks(p);
		if(!links.equals(null) && links.size()>0){
			for (PDI pdi : links) {
				if(pdi instanceof Estrutura){
					Estrutura es = (Estrutura) pdi;
					es.setPerigo(null);
					es.atualizaTextoBalao();
					EditarPDI.atualizarPDI(es);
				}
				if(pdi instanceof Equipe){
					Equipe e = (Equipe) pdi;
					e.setPerigo(null);
					e.atualizaTextoBalao();
					EditarPDI.atualizarPDI(e);
				}
				if(pdi instanceof Vitima){
					Vitima vi = (Vitima) pdi;
					vi.setPerigo(null);
					vi.atualizaTextoBalao();
					EditarPDI.atualizarPDI(vi);
				}
			}
		}		        	   

		MainActivity.listaPerigos.remove(p);
		MainActivity.perigoOverlays.remove(p.getOverlayItem(), p);
	}

	private static void excluirVitima(Vitima vi){
		ArrayList<PDI> links = getArrayDeLinks(vi);
		if(!links.equals(null) && links.size()>0){
			for (PDI pdi : links) {
				if(pdi instanceof Estrutura){
					Estrutura es = (Estrutura) pdi;
					es.setVitima(null);
					es.atualizaTextoBalao();
					EditarPDI.atualizarPDI(es);
				}
				if(pdi instanceof Perigo){
					Perigo p = (Perigo) pdi;
					p.setVitima(null);
					p.atualizaTextoBalao();
					EditarPDI.atualizarPDI(p);
				}
			}
		}

		MainActivity.listaVitimas.remove(vi);
		MainActivity.vitimaOverlays.remove(vi.getOverlayItem(), vi);
	}

}