package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone;

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
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Representation3D.IDroneRepresentation3D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

@ToRecord(name="Drone")
public class Drone extends SimEntity implements IMovable,IDroneRepresentation3D{
	
	DroneInit bIni;
	DroneFeatures bFeat;
	
	List<Drone> filles;
	
	DroneFeatures bFilles;
	
	Point3D dir;//direction du mouvement
	double speed;
	Rotate r;
	public Drone(String name, SimFeatures features) {
		super(name, features);
		filles = new ArrayList<>();
		bFeat = (DroneFeatures) features;

		r = new Rotate();
		r.setAxis(Rotate.Z_AXIS);
		r.setAngle(45);
		
		speed = bFeat.getVitesseMax();

		bFilles = new DroneFeatures(name+"B2",5,1,0.5,0);
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		bIni = (DroneInit) getInitParameters();
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
		Logger.Detail(this, "AfterActivate", "Activation de la bou�e %s","test");
		if(bFeat.getType()==1) {
			Post(new Mvt());
			//Post(new DroneArrival());
			Post(new Bonjour());
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
		Logger.Information(this, "BeforeDeactivate", "Sur le point de se d�sactiver");
		for(Drone b : filles) {
			b.deactivate();
			b.terminate(starting);
		}

	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterDeactivated", "D�activation");		
	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		Logger.Information(this, "AfterTerminated", "FIN de la Drone");				
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
	
	class Bonjour extends SimEvent{
		public void listerAutresDrones() {
			if(autresDronesType1 == null) {
				autresDronesType1 = getEngine().requestSimObject(new SimObjectRequest() {
					
					@Override
					public boolean filter(ISimObject o) {
						if(o == Owner()) return false;
						if(o instanceof Drone) {
							
						Drone b = (Drone) o;
						return b.getType()==1;
						}
						else return false;
					}
				});
				lesDrones="";
				for(ISimObject o:autresDronesType1)
				{
					lesDrones=lesDrones+o.getName()+";";
				}
			}
				
		}

		@Override
		public void Process() {
			listerAutresDrones();
			if(lesDrones=="") {
				Logger.Information(this, "Bnjour", " Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je suis seul sniff.....");
				
			}
			else {
				Logger.Information(this, "Bnjour", " Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je parle aux Drones " + lesDrones);
			}
			Post(this,LogicalDuration.ofSeconds(3));
		}
		
	}
	
	class Mvt extends SimEvent{

		@Override
		public void Process() {
			double dt = 0.1;
			if(maPosition.getX()>50||maPosition.getX()<-50||maPosition.getY()>50||maPosition.getY()<-50) {
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
		
		return ((DroneFeatures)getFeatures()).getType();
	}

	List<ISimObject> autresDronesType1;
	String lesDrones;

	class DroneArrival extends SimEvent
	{
		int bnum=0;
		@Override
		public void Process() {
			bnum++;
			DroneInit i = new DroneInit("B"+bnum,new Point3D(getPosition().getX(),getPosition().getY(),0),Color.BLUE);
			
			SimEntity b = createChild(Drone.class,  Owner().getName()+"_"+i.getName() , bFilles);
			b.initialize(i);
			b.activate();
			filles.add((Drone) b);
			
			
			Owner().Post(this,LogicalDuration.ofSeconds(5));
		}
		
	}

}
