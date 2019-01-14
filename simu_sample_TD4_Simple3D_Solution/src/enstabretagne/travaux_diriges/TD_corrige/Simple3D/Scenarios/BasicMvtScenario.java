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
		//Logger.Detail(this, "afteractivate", "taille liste bouees = %s", feature.getBouees().size());

		for(Map.Entry<BoueeFeatures, BoueeInit> e : feature.getBouees().entrySet())
		{
			//Logger.Detail(this, "afteractivate", "bouee à créer = %s , %s", e.getValue(),e.getKey());
			Post(new BoueeArrival(e.getValue(),e.getKey()));
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
				//Logger.Detail(this, "BoueeArrival.Process", "Création de la baie " + i);
				SimEntity b = createChild(Bouee.class, i.getName() , f);
				b.initialize(getI());
				b.activate();
			}
			
		}

}
