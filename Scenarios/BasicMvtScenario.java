package enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios;

import java.util.Map;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.components.implementation.SimScenario;
import enstabretagne.simulation.core.implementation.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenarioFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Drone;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.DroneFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.DroneInit;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Navire.EntityNavire;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Navire.EntityNavireFeature;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Navire.EntityNavireInit;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Ocean.EntityOcean;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Ocean.EntityOceanFeature;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Ocean.EntityOceanInit;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Bouee;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeInit;

public class BasicMvtScenario extends SimScenario{

	public BasicMvtScenario(ScenarioId id, SimFeatures features, LogicalDateTime start, LogicalDateTime end) {
		super(id, features, start, end);
		
	}
	
	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		super.initializeSimEntity(init);
	}
	
	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		super.AfterActivate(sender, starting);
		
		BasicMvtScenarioFeatures feature = (BasicMvtScenarioFeatures) getFeatures();
		Logger.Detail(this, "afteractivate", "taille liste bouees = %s", feature.getBouees().size());

		for(Map.Entry<BoueeFeatures, BoueeInit> e : feature.getBouees().entrySet())
		{
			Logger.Detail(this, "afteractivate", "bouee � cr�er = %s , %s", e.getValue(),e.getKey());
			Post(new BoueeArrival(e.getValue(),e.getKey()));
		}
		for(Map.Entry<EntityNavireFeature, EntityNavireInit> e : feature.getNavires().entrySet())
		{
			Logger.Detail(this, "afteractivate", "navire � cr�er = %s , %s", e.getValue(),e.getKey());
			Post(new NavireArrival(e.getValue(),e.getKey()));
		}
		for(Map.Entry<DroneFeatures, DroneInit> e : feature.getDrones().entrySet())
		{
			Logger.Detail(this, "afteractivate", "drone � cr�er = %s , %s", e.getValue(),e.getKey());
			Post(new DroneArrival(e.getValue(),e.getKey()));
		}
		for(Map.Entry<EntityOceanFeature, EntityOceanInit> e : feature.getOcean().entrySet())
		{
			Logger.Detail(this, "afteractivate", "navire � cr�er = %s , %s", e.getValue(),e.getKey());
			Post(new OceanArrival(e.getValue(),e.getKey()));
		}
	}
	
	class BoueeArrival extends SimEvent
	{
		private BoueeInit i;
		private BoueeFeatures f;
		public BoueeInit getI() {
			return i;
		}
		
		public BoueeFeatures getF() {
			return f;
		}
		
		
		public BoueeArrival(BoueeInit i, BoueeFeatures f) {
			this.i=i;
			this.f=f;
		}

		@Override
		public void Process() {
			Logger.Detail(this, "BoueeArrival.Process", "Cr�ation de la baie " + i);
			SimEntity b = createChild(Bouee.class, i.getName() , f);
			b.initialize(getI());
			b.activate();
		}
		
	}

	class NavireArrival extends SimEvent
	{
		private EntityNavireInit i;
		private EntityNavireFeature f;
		
		public EntityNavireInit getI() {
			return i;
		}
		
		public EntityNavireFeature getF() {
			return f;
		}
		
		
		public NavireArrival(EntityNavireInit i, EntityNavireFeature f) {
			this.i=i;
			this.f=f;
		}

		@Override
		public void Process() {
			Logger.Detail(this, "NavireArrival.Process", "Cr�ation du Navire" + i);
			SimEntity b = createChild(EntityNavire.class, i.getName() , f);
			b.initialize(getI());
			b.activate();
		}
		
	}

	

	class DroneArrival extends SimEvent
	{
		private DroneInit i;
		private DroneFeatures f;
		
		public DroneInit getI() {
			return i;
		}
		
		public DroneFeatures getF() {
			return f;
		}
		
		
		public DroneArrival(DroneInit i, DroneFeatures f) {
			this.i=i;
			this.f=f;
		}

		@Override
		public void Process() {
			Logger.Detail(this, "DroneArrival.Process", "Cr�ation du Drone" + i);
			SimEntity b = createChild(Drone.class, i.getName() , f);
			b.initialize(getI());
			b.activate();
		}
		
	}
	
	
	class OceanArrival extends SimEvent
	{
		private EntityOceanInit i;
		private EntityOceanFeature f;
		
		public EntityOceanInit getI() {
			return i;
		}
		
		public EntityOceanFeature getF() {
			return f;
		}
		
		
		public OceanArrival(EntityOceanInit i, EntityOceanFeature f) {
			this.i=i;
			this.f=f;
		}

		@Override
		public void Process() {
			Logger.Detail(this, "OceanArrival.Process", "Cr�ation de l'oc�an" + i);
			SimEntity b = createChild(EntityOcean.class, i.getName() , f);
			b.initialize(getI());
			b.activate();
		}
		
	}
}
