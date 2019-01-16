package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone;

import enstabretagne.simulation.components.data.SimFeatures;

public class DroneFeatures extends SimFeatures {

	private double vitesseMax;
	private double accelerationMax;
	private double taille;
	
	private int type;
	public int getType() {
		return type;
	}
	
	public double getVitesseMax() {
		return vitesseMax;
	}
	
	public double getAccelerationMax() {
		return accelerationMax;
	}
	
	public DroneFeatures(String id,double vitesseMax,double accelerationMax,double taille,int type) {
		super(id);
		this.vitesseMax = vitesseMax;
		this.accelerationMax = accelerationMax;
		this.taille = taille;
		this.type=type;
	}

	public double getTaille() {
		return taille;
	}

}
