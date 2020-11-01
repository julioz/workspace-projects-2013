package br.com.site.view;

import br.com.site.model.*;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CustomOverlayItem extends OverlayItem {
	
	private PDI pdi;
	private GeoPoint geopoint;
	
	public CustomOverlayItem(PDI pdi, GeoPoint geopoint) {
		super(geopoint, "", "");
		this.setPdi(pdi);
		this.geopoint = geopoint;
	}

	public GeoPoint getGeopoint() {
		return geopoint;
	}

	public void setGeopoint(GeoPoint geopoint) {
		this.geopoint = geopoint;
	}

	public String getTitle() {
		return pdi.getTituloBalao();
	}

	public String getSnippet() {
		if (pdi instanceof Veiculo) ((Veiculo) pdi).atualizaTextoBalao();
		else if (pdi instanceof Estrutura) ((Estrutura) pdi).atualizaTextoBalao();
		else if (pdi instanceof Equipamento) ((Equipamento) pdi).atualizaTextoBalao();
		else if (pdi instanceof Equipe) ((Equipe) pdi).atualizaTextoBalao();
		else if (pdi instanceof Perigo) ((Perigo) pdi).atualizaTextoBalao();
		else if (pdi instanceof Vitima) ((Vitima) pdi).atualizaTextoBalao();
		return pdi.getTextoBalao();
	}

	public void setPdi(PDI pdi) {
		this.pdi = pdi;
	}

	public PDI getPdi() {
		return pdi;
	}

}
