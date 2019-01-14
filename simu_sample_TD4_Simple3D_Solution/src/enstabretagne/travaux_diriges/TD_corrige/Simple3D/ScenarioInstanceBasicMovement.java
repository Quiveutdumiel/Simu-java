package enstabretagne.travaux_diriges.TD_corrige.Simple3D;

import java.util.HashMap;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.IScenarioInstance;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenario;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenarioFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeInit;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public class ScenarioInstanceBasicMovement implements IScenarioInstance {

	@Override
	public IScenario getScenarioInstance() {
		BasicMvtScenarioFeatures bsf = new BasicMvtScenarioFeatures("BSF");
		
		//Création du navire et des points de passage
		HashMap<String,Point3D> positionsCles = new HashMap<String, Point3D>();
		positionsCles.put("start", new Point3D(0,0,0));
		positionsCles.put("PointCible1", new Point3D(10,10,0));
		positionsCles.put("PointCible2", new Point3D(30,-10,0));
		positionsCles.put("PointCible3", new Point3D(20,0,0));
		positionsCles.put("PointDirection", new Point3D(20,20,0));
		positionsCles.put("PointSousEau", new Point3D(0,0,-10));
		positionsCles.put("ObservationMine", new Point3D(20,20,-20));

		

		//Création de bouees
		//
		int i=0;
		positionsCles = new HashMap<String, Point3D>();
		MoreRandom mr = new MoreRandom(1);
		int nArtefact=30;
		int nA1 = 18;
		int nA2 = 9;
		int nA3 = 3;
		double vArtefact = 0.0;
		double size = 20;
		double depth = 3;
		int type;
		double objSize = 0.25;
		double vSea = 0.0;
		double vDrone = 1.5;

		for(i=0;i<=nArtefact;i++) {
			double x = mr.nextDouble() * size;
			double y = mr.nextDouble() * size;
			double z = - mr.nextDouble() * depth;
			if (i < nA1) {
				type = 1;
				bsf.getBouees().put(new BoueeFeatures("B"+i,vArtefact,1,objSize,type), new BoueeInit("B"+i,new Point3D(x-size/2,y-size/2,z),Color.RED));
			}
			
			else if ((nA1 <= i) && (i < nA1 + nA2)) {
				type = 2;
				bsf.getBouees().put(new BoueeFeatures("B"+i,vArtefact,1,objSize,type), new BoueeInit("B"+i,new Point3D(x-size/2,y-size/2,z),Color.RED));
			}
			
			else if ((nA1 + nA2 <= i) && (i < nA1 + nA2 + nA3)) {
				type = 3;
				bsf.getBouees().put(new BoueeFeatures("B"+i,vArtefact,1,objSize,type), new BoueeInit("B"+i,new Point3D(x-size/2,y-size/2,z),Color.RED));
			}
			
		}
		// Surface de mer
		type = 4;
		//bsf.getBouees().put(new BoueeFeatures("sea",vSea,1,objSize,type), new BoueeInit("sea",new Point3D(0, 0, 0),Color.RED));
		type = 5;
		bsf.getBouees().put(new BoueeFeatures("drone",vDrone,1,objSize,type), new BoueeInit("drone",new Point3D(0.0, 0.0, 0.0),Color.RED));

		//Zone destinée à des tests
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,5), new BoueeInit("B"+2,new Point3D(30,10,20),Color.BROWN));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,4), new BoueeInit("B"+i,new Point3D(20,10,20),Color.RED));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,3), new BoueeInit("B"+3,new Point3D(10,10,20),Color.BLACK));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,2), new BoueeInit("B"+4,new Point3D(0,10,20),Color.BEIGE));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,1), new BoueeInit("B"+5,new Point3D(-10,10,20),Color.BISQUE));
//
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("O",new Point3D(0,0,0),Color.BLACK));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("X",new Point3D(10,0,0),Color.BLUEVIOLET));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Y",new Point3D(0,10,0),Color.GREEN));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Z",new Point3D(0,0,10),Color.BLUE));
		
		LogicalDateTime start = new LogicalDateTime("05/12/2018 06:00");
		LogicalDateTime end = start.add(LogicalDuration.ofMinutes(2));
		BasicMvtScenario bms = new BasicMvtScenario(new ScenarioId("S1"), bsf, start, end);
		
		return bms;
	}

}
