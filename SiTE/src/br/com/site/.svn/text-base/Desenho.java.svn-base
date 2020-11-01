package br.com.site;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import br.com.site.view.MarcadorPonto;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class Desenho extends Overlay{
	
	private ArrayList<GeoPoint> list = new ArrayList<GeoPoint>();
	private MarcadorPonto marcaClique;
	private MapView mapView;
	private Path path = new Path();
	private Paint myPaint = new Paint();
	private int tipo;
	private boolean fechado = false;
	private boolean apagar = false;
	
	public static final int AZUL = 0x330000FF; 
	public static final int VERDE = 0x3300FF00;
	public static final int VERMELHO = 0x33FF0000;
	public static final int AMARELO = 0x33FFFF00;
	public static final int ROSA = 0x33FF00FF;
	public static final int AZUL_MARINHO = 0x3300FFFF;
	public static final int BRANCO = 0x33FFFFFF;
	public static final int PRETO = 0x33000000;
	
	public static final int LIVRE = 1;
	public static final int REGIAO = 2;
	public static final int RETANGULO = 3;
	public static final int TRIANGULO = 4;
	public static final int PENTAGONO = 5;
	
	public Desenho(MapView mapView, GeoPoint inicial, int cor, int poligono){
		this.list.add(inicial);
		this.tipo = poligono;
		this.mapView = mapView;
		
	    myPaint.setColor(cor);
	    myPaint.setAntiAlias(true);
	    if(this.tipo == Desenho.LIVRE) myPaint.setStrokeWidth(6);
	    else myPaint.setStrokeWidth(1);
	    myPaint.setStyle(Paint.Style.STROKE);
	    
	    if(poligono != Desenho.LIVRE && poligono != Desenho.REGIAO){
	    	Drawable marker = mapView.getResources().getDrawable(R.drawable.map_blue);
	    	int markerWidth = marker.getIntrinsicWidth();
	        int markerHeight = marker.getIntrinsicHeight();
	        marker.setBounds(0, markerHeight, markerWidth, 0);
	    	marcaClique = new MarcadorPonto(marker, inicial);
	    	this.mapView.getOverlays().add(marcaClique);
	    	marcaClique.addItem(inicial, "myPoint1", "myPoint1");
	    	this.mapView.invalidate();
	    }
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		path.reset();
		
		Projection projection = mapView.getProjection();
		
		Point atual = new Point();
		projection.toPixels(list.get(0),atual);
		path.moveTo(atual.x, atual.y);
		
		for (GeoPoint gp : list) {
			Point prox = new Point();
			projection.toPixels(gp, prox);
			path.lineTo(prox.x, prox.y);
		}
		
		if ( (this.tipo == RETANGULO && list.size() == 5) ||
				(this.tipo == TRIANGULO && list.size() == 4) ||
				(this.tipo == PENTAGONO && list.size() == 6) ){
			mapView.getOverlays().remove(this.marcaClique);
			fecharPath();
			mapView.invalidate();
		}
		
		canvas.drawPath(path, myPaint);
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		if (this.tipo == RETANGULO && list.size() < 5 && !apagar && !fechado
				|| this.tipo == TRIANGULO && list.size() < 4 && !apagar && !fechado
				|| this.tipo == PENTAGONO && list.size() < 6 && !apagar && !fechado){
			list.add(p);
			this.marcaClique.addItem(p, "", "");
			mapView.invalidate();
			return true;
		}
		if (apagar){
			Point ptIntersecao = new Point();
			mapView.getProjection().toPixels(p, ptIntersecao);
			
			Region intersecao = new Region();
			Region clip = new Region(0,0,mapView.getWidth(),mapView.getHeight());
			intersecao.setPath(path, clip);
			if(intersecao.contains(ptIntersecao.x, ptIntersecao.y)){
				mapView.getOverlays().remove(this);
				for (Overlay o : mapView.getOverlays()) {
    				if (o instanceof Desenho){
    					((Desenho) o).setApagar(false);
    				}
    			}
			}
		}
		return false;
	}

	public void setMyPaint(Paint myPaint) {
		this.myPaint = myPaint;
	}

	public Paint getMyPaint() {
		return myPaint;
	}
	
	public void fecharPath(){
		path.close();
		if(this.tipo == LIVRE) myPaint.setStyle(Paint.Style.STROKE);
		else myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		setFechado(true);
	}

	
	public void setApagar(boolean apagar) {
		this.apagar = apagar;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}

	public boolean isFechado() {
		return fechado;
	}
	
	public ArrayList<GeoPoint> getList() {
		return list;
	}
}
