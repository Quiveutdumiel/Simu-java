package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee;

import enstabretagne.simulation.components.data.SimInitParameters;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public class BoueeInit extends SimInitParameters {
	private String name;
	private Color couleur;
	private Point3D posInit;
	
	public BoueeInit(String nom,Point3D posInit,Color c)
	{
		this.name = nom;
		this.couleur=c;
		this.posInit = posInit;
	}
		
	public Point3D getPosInit() {
		return posInit;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return couleur;
	}
	
	

}
