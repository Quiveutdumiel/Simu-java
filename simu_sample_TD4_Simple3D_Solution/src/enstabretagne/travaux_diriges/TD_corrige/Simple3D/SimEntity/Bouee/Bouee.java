package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.simulation.core.implementation.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Representation3D.IBoueeRepresentation3D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

@ToRecord(name="Bouee")
public class Bouee extends SimEntity implements IMovable,IBoueeRepresentation3D{
	
	BoueeInit bIni;
	BoueeFeatures bFeat;
	
	List<Bouee> filles;
	
	BoueeFeatures bFilles;
	
	Point3D dir;//direction du mouvement
	double speed;
	Rotate r;
	public Bouee(String name, SimFeatures features) {
		super(name, features);
		filles = new ArrayList<>();
		bFeat = (BoueeFeatures) features;

		r = new Rotate();
		r.setAxis(Rotate.Z_AXIS);
		r.setAngle(45);
		
		speed = bFeat.getVitesseMax();

		bFilles = new BoueeFeatures(name+"B2",5,1,0.5,0);
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		bIni = (BoueeInit) getInitParameters();
		maPosition= bIni.getPosInit();
		
		computeDir();
		
		
	}

	protected void computeDir() {

		double x=RandomGenerator().nextDouble();
		double y=RandomGenerator().nextDouble();
		double z=0;
		dir = new Point3D(x-0.5, y-0.5, z);
		dir = dir.normalize();
	}
	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		//Logger.Detail(this, "AfterActivate", "Activation de la bouée %s","test");
		if(bFeat.getType()==5) {
			Post(new Mvt());
			Post(new Echo());
			//Post(new BoueeArrival());
		}
	}
	
	Point3D maPosition;

	@ToRecord(name="Position")
	@Override
	public Point3D getPosition() {
		return maPosition;
	}

	@ToRecord(name="Vitesse")
	@Override
	public Point3D getVitesse() {
		return dir.multiply(speed);
	}

	@ToRecord(name="Acceleration")
	@Override
	public Point3D getAcceleration() {
		return Point3D.ZERO;
	}

	@Override
	public Color getColor() {
		return bIni.getColor();
	}

	@Override
	public double getSize() {
		return bFeat.getTaille();
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		//Logger.Information(this, "BeforeDeactivate", "Sur le point de se désactiver");
		for(Bouee b : filles) {
			b.deactivate();
			b.terminate(starting);
		}

	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		//Logger.Information(this, "AfterDeactivated", "Déactivation");		
	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		//Logger.Information(this, "AfterTerminated", "FIN de la bouee");				
	}

	@Override
	public Point3D getVitesseRotationXYZ() {
		return Point3D.ZERO;
	}

	@Override
	public Point3D getAccelerationRotationXYZ() {
		return Point3D.ZERO;
	}

	@Override
	public Point3D getRotationXYZ() {
		return Point3D.ZERO;
	}
	
	class Echo extends SimEvent{
		public void listerBoueesProches() {
			System.out.println(maPosition);
			boueesProches = null;
			if(boueesProches == null) {
				boueesProches = getEngine().requestSimObject(new SimObjectRequest() {
					
					@Override
					public boolean filter(ISimObject o) {
						if(o == Owner()) return false;
						if(o instanceof Bouee) {
							
						Bouee b = (Bouee) o;
						if (b.getType() != 4){ 
							boolean isClose = b.getPosition().distance(maPosition) < 5;
							return isClose;
						}
						else {
							return false;
						}
						}
						else return false;
					}
				});
				lesbouees="";
				for(ISimObject o:boueesProches)
				{
					lesbouees=lesbouees+o.getName()+";";
				}
			}
				
		}
		
		@Override
		public void Process() {
			listerBoueesProches();
			if(lesbouees=="") {
				Logger.Information(this, "Echo", " Bonjour je suis la bouee drone " + getName() + " de couleur " + getColor() + " et je n ai detecté aucune bouée");
				
			}
			else {
				Logger.Information(this, "Echo", " Bonjour je suis la bouee drone " + getName() + " de couleur " + getColor() + " et j ai détecté les bouées " + lesbouees);
			}
			Post(this,LogicalDuration.ofSeconds(3));
		}
		
	}
		
	class Bonjour extends SimEvent{
		public void listerAutresBouees() {
			if(autresBoueesType1 == null) {
				autresBoueesType1 = getEngine().requestSimObject(new SimObjectRequest() {
					
					@Override
					public boolean filter(ISimObject o) {
						if(o == Owner()) return false;
						if(o instanceof Bouee) {
							
						Bouee b = (Bouee) o;
						return b.getType()==1;
						}
						else return false;
					}
				});
				lesbouees="";
				for(ISimObject o:autresBoueesType1)
				{
					lesbouees=lesbouees+o.getName()+";";
				}
			}
				
		}

		@Override
		public void Process() {
			listerAutresBouees();
			if(lesbouees=="") {
				Logger.Information(this, "Bnjour", " Bonjour je suis la bouee " + getName() + " de couleur " + getColor() + " et je suis seul sniff.....");
				
			}
			else {
				Logger.Information(this, "Bnjour", " Bonjour je suis la bouee " + getName() + " de couleur " + getColor() + " et je parle aux bouees " + lesbouees);
			}
			Post(this,LogicalDuration.ofSeconds(3));
		}
		
	}
	
	class Mvt extends SimEvent{

		@Override
		public void Process() {
			double dt = 0.1;
			if(maPosition.getX()>10||maPosition.getX()<-10||maPosition.getY()>10||maPosition.getY()<-10) {
				dir = dir.multiply(-1);
			}
			else {
				double n = RandomGenerator().nextDouble();
				if(n<0.01)
					computeDir();

			}
			maPosition = maPosition.add(getVitesse().multiply(dt));
			
			
			Owner().Post(this,LogicalDuration.ofSeconds(dt));
		}
		
	}

	@Override
	public int getType() {
		
		return ((BoueeFeatures)getFeatures()).getType();
	}

	List<ISimObject> boueesProches;
	List<ISimObject> autresBoueesType1;
	String lesbouees;

	class BoueeArrival extends SimEvent
	{
		int bnum=0;
		@Override
		public void Process() {
			bnum++;
			BoueeInit i = new BoueeInit("B"+bnum,new Point3D(getPosition().getX(),getPosition().getY(),0),Color.BLUE);
			
			SimEntity b = createChild(Bouee.class,  Owner().getName()+"_"+i.getName() , bFilles);
			b.initialize(i);
			b.activate();
			filles.add((Bouee) b);
			
			
			Owner().Post(this,LogicalDuration.ofSeconds(5));
		}
		
	}

}
