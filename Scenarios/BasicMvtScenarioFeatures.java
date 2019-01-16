package enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.DroneFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.DroneInit;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Navire.EntityNavireFeature;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Navire.EntityNavireInit;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Ocean.EntityOceanFeature;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Ocean.EntityOceanInit;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeInit;

public class BasicMvtScenarioFeatures extends SimFeatures {

	private HashMap<BoueeFeatures, BoueeInit> bouees;
	private HashMap<EntityNavireFeature, EntityNavireInit> navires;
	private HashMap<DroneFeatures, DroneInit> drones;
	private HashMap<EntityOceanFeature,EntityOceanInit> ocean;
	
	public HashMap<EntityOceanFeature, EntityOceanInit> getOcean() {
		return ocean;
	}
	public HashMap<EntityNavireFeature, EntityNavireInit> getNavires() {
		return navires;
	}
	
	public HashMap<DroneFeatures, DroneInit> getDrones() {
		return drones;
	}
	
	public HashMap<BoueeFeatures, BoueeInit> getBouees() {
		return bouees;
	}
	public BasicMvtScenarioFeatures(String id) {
		super(id);
		bouees = new HashMap<>();
		navires = new HashMap<>();
		drones = new HashMap<>();
		ocean = new HashMap<>();
	}	

}
