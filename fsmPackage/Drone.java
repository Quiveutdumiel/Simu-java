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
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Bouee;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Representation3D.IDroneRepresentation3D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import fsmPackage.FSM1;

@ToRecord(name="Drone")
public class Drone extends SimEntity implements IMovable,IDroneRepresentation3D{
	
	DroneInit bIni;
	DroneFeatures bFeat;
	
	List<Drone> filles;
	
	DroneFeatures bFilles;
	
	FSM1 fsm;
	
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
		
		fsm = new FSM1(this);
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
		// -------------- Modif: Start -------------
		//Logger.Detail(this, "AfterActivate", "Activation de la bouee %s","test");
		
		fsm.currentState.ActivateState();
		/*
		if(bFeat.getType()==1) {
			Post(new Mvt());
			//Post(new DroneArrival());
			Post(new Bonjour());
		}
		*/
		// -------------- Modif: End ---------------
	}
	// ---------- Modif : Start --------------
	public void PostState1() {
		Post(new Move());
		Post(new Echo());
	}
	
	class Move extends SimEvent{
		int relatedStateId = 1;
		
		@Override
		public void Process() {
			double dt = 0.1;
			System.out.println("State: " + Integer.toString(fsm.currentState.id) + ", " + fsm.currentState.label);
			
			if(maPosition.getX()>50||maPosition.getX()<-50||maPosition.getY()>50||maPosition.getY()<-50) {
				dir = dir.multiply(-1);
			}
			else {
				double n = RandomGenerator().nextDouble();
				if(n<0.01)
					computeDir();

			}
			maPosition = maPosition.add(getVitesse().multiply(dt));
			
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Owner().Post(this,LogicalDuration.ofSeconds(dt));				
			}
		}
		
	}
	
	class Echo extends SimEvent{
		int relatedStateId = 1;
		public void listerBoueesProches() {
			boolean isNull = (boueesProches == null);
			boolean isEmpty = false;
			if (isNull == false) {boueesProches.clear(); isEmpty = boueesProches.isEmpty();}
			if (isNull || isEmpty) {
				boueesProches = getEngine().requestSimObject(new SimObjectRequest() {
					
					@Override
					public boolean filter(ISimObject o) {
						if(o == Owner()) return false;
						if(o instanceof Bouee) {
							
							Bouee b = (Bouee) o;
							double dist = b.getPosition().distance(maPosition);
							if (dist < 35) {
								return true;
							}
							else {
								return false;
							}
						}
						else return false;
					}
				});
				lesBoueesProches="";
				for(ISimObject o:boueesProches)
				{
					lesBoueesProches=lesBoueesProches+o.getName()+";";
				}
			}
				
		}

		@Override
		public void Process() {
			listerBoueesProches();
			if(lesBoueesProches=="") {
				//Logger.Information(this, "Bnjour", " Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je suis seul sniff.....");
				System.out.println(" Echo : je suis le drone " + getName() + " de couleur " + getColor() + " et je suis seul sniff.....");
			}
			else {
				//Logger.Information(this, "Bnjour", " Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je parle aux Drones " + lesDrones);
				System.out.println(" Echo : je suis le drone " + getName() + " de couleur " + getColor() + " et je vois les bouees " + lesBoueesProches);
			}
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Post(this,LogicalDuration.ofSeconds(0.3));
			}
			
		}
		
	}
	
	public void PostState201() {
		Post(new PrevenirNavire());
	}
	
	class PrevenirNavire extends SimEvent{
		int relatedStateId = 201;
		
		@Override
		public void Process() {
			double dt = 0.1;
			System.out.println("State: " + Integer.toString(fsm.currentState.id) + ", " + fsm.currentState.label);
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Owner().Post(this,LogicalDuration.ofSeconds(dt));				
			}
		}
		
	}
	
	public void PostState202() {
		Post(new PlongeantVerticale());
	}
	
	class PlongeantVerticale extends SimEvent{
		int relatedStateId = 202;
		
		@Override
		public void Process() {
			double dt = 0.1;
			System.out.println("State: " + Integer.toString(fsm.currentState.id) + ", " + fsm.currentState.label);
			dir = new Point3D(0, 0, -1); dir = dir.normalize();
			maPosition = maPosition.add(getVitesse().multiply(dt));
			System.out.println(maPosition);
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Owner().Post(this,LogicalDuration.ofSeconds(dt));				
			}
		}
		
	}
	
	public void PostState203() {
		Post(new RapprochantDirectement());
	}
	
	class RapprochantDirectement extends SimEvent{
		int relatedStateId = 203;
		
		@Override
		public void Process() {
			double dt = 0.1;
			System.out.println("State: " + Integer.toString(fsm.currentState.id) + ", " + fsm.currentState.label);
			System.out.println(boueesProches);
			PlusProcheBouee = (Bouee) boueesProches.get(0);
			dir = PlusProcheBouee.getPosition().subtract(maPosition); dir = dir.normalize();
			maPosition = maPosition.add(getVitesse().multiply(dt));
			System.out.println(maPosition.distance(PlusProcheBouee.getPosition()));
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Owner().Post(this,LogicalDuration.ofSeconds(dt));				
			}
		}
		
	}
	
	public void PostState204() {
		Post(new Tour360());
	}
	
	class Tour360 extends SimEvent{
		int relatedStateId = 203;
		
		@Override
		public void Process() {
			double dt = 0.1;
			System.out.println("State: " + Integer.toString(fsm.currentState.id) + ", " + fsm.currentState.label);
			System.out.println(boueesProches);
			PlusProcheBouee = (Bouee) boueesProches.get(0);
			dir = PlusProcheBouee.getPosition().subtract(maPosition); dir = dir.normalize();
			maPosition = maPosition.add(getVitesse().multiply(dt));
			System.out.println(maPosition.distance(PlusProcheBouee.getPosition()));
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Owner().Post(this,LogicalDuration.ofSeconds(dt));				
			}
		}
		
	}
	
	public void PostState8() {
		Post(new AuNavire());
	}
	
	class AuNavire extends SimEvent{
		int relatedStateId = 8;
		
		@Override
		public void Process() {
			double dt = 0.1;
			System.out.println("State: " + Integer.toString(fsm.currentState.id) + ", " + fsm.currentState.label);
			boolean changingState = fsm.setNewState();
			boolean stillSameState = (relatedStateId == fsm.currentState.id);
			if ((changingState == false) && (stillSameState == true)) {
				Owner().Post(this,LogicalDuration.ofSeconds(dt));				
			}
		}
		
	}
	// ---------- Modif: End ---------------
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
		//Logger.Information(this, "BeforeDeactivate", "Sur le point de se desactiver");
		for(Drone b : filles) {
			b.deactivate();
			b.terminate(starting);
		}

	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		//Logger.Information(this, "AfterDeactivated", "Deactivation");		
	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		//Logger.Information(this, "AfterTerminated", "FIN de la Drone");				
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
			boolean isNull = (boueesProches == null);
			if (isNull) {
				System.out.println("marker");
				boueesProches = getEngine().requestSimObject(new SimObjectRequest() {
					
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
				lesBoueesProches="";
				for(ISimObject o:boueesProches)
				{
					lesBoueesProches = lesBoueesProches + o.getName()+";";
				}
			}
				
		}

		@Override
		public void Process() {
			listerAutresDrones();
			if(lesBoueesProches=="") {
				//Logger.Information(this, "Bnjour", " Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je suis seul sniff.....");
				System.out.println(" Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je suis seul sniff.....");
			}
			else {
				//Logger.Information(this, "Bnjour", " Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je parle aux Drones " + lesDrones);
				System.out.println(" Bonjour je suis la Drone " + getName() + " de couleur " + getColor() + " et je parle aux Drones " + lesBoueesProches);
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

	public List<ISimObject> boueesProches;
	String lesBoueesProches;
	public Bouee PlusProcheBouee;
	
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
