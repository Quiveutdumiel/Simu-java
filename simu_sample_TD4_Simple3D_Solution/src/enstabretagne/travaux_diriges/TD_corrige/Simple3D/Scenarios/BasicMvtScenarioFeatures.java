package enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeInit;

public class BasicMvtScenarioFeatures extends SimFeatures {

	private HashMap<BoueeFeatures, BoueeInit> bouees;
	
	public HashMap<BoueeFeatures, BoueeInit> getBouees() {
		return bouees;
	}
	public BasicMvtScenarioFeatures(String id) {
		super(id);
		bouees = new HashMap<>();
	}	

}
