package br.com.site.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;
import br.com.site.view.CustomOverlayItem;

import com.google.android.maps.GeoPoint;

public class PDI extends com.google.android.maps.Overlay implements Comparable<PDI> {
	
	private String latitude;
	private String longitude;
	private String titulo;
	private String texto;
	private int id;
	
	CustomOverlayItem overlayitem = null;

	private GeoPoint ponto;
	private int tipo = 0;
	
	public PDI(int id, String latitude, String longitude, String titulo){
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.titulo = titulo;
		
		double lat = Double.parseDouble(this.latitude);
		double lng = Double.parseDouble(this.longitude);
		this.ponto = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)); 
	}
	
	public PDI(int id, GeoPoint p, String titulo){
		this.id = id;
		this.ponto = p;
		this.titulo = titulo;
	}
	
	public GeoPoint getGeoPoint(){
			return ponto;
	}
	public void setGeoPoint(GeoPoint ponto) {
		this.ponto = ponto;
	}

	public CustomOverlayItem getOverlayItem(){
		if(this.overlayitem == null){
			this.overlayitem = new CustomOverlayItem(this, getGeoPoint());
			return overlayitem;
		}else{
			return overlayitem;
		}
	}

	public int getTipo() {
		return tipo;
	}
	
	public void setTextoBalao(String texto) {
		this.texto = texto;
	}
	
	public String getTextoBalao(){
		return texto;
	}
	
	public void setTituloBalao(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTituloBalao(){
		return titulo;
	}
	
	public int getId(){
		return id;
	}
	
	protected String getXML(String tipoPDI, LinkedHashMap<String, String> mapaRelacionamentos){
		XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
        try {
			serializer.setOutput(writer);
			serializer.startDocument("ISO-8859-1", true);
			/*se quiser indentar o xml, mas causa problema na hora do parsing:
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);*/
	        serializer.startTag("", tipoPDI);
	        
	        serializer.startTag("", "id");
	        serializer.text(String.valueOf(this.getId()));
	        serializer.endTag("", "id");
	        
	        serializer.startTag("", "geopoint");
		        serializer.startTag("", "latitudeE6");
		        serializer.text(String.valueOf(this.getGeoPoint().getLatitudeE6()));
		        serializer.endTag("", "latitudeE6");
		        serializer.startTag("", "longitudeE6");
		        serializer.text(String.valueOf(this.getGeoPoint().getLongitudeE6()));
		        serializer.endTag("", "longitudeE6");
			serializer.endTag("", "geopoint");
			
	        for (String tag : mapaRelacionamentos.keySet()) {
				serializer.startTag("", tag);
				serializer.text(mapaRelacionamentos.get(tag));
				serializer.endTag("", tag);
			}
	        
	        serializer.endTag("", tipoPDI);
	        serializer.endDocument();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return writer.toString();
	}

	@Override
	public int compareTo(PDI pdiComparacao) {
		if(this.toString().equals(pdiComparacao.toString())) return 0;
		return 1;
	}
	
}