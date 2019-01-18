package fsmPackage;

public class Link {
	int id;
	String label;
	State source;
	State destination;
	boolean transitionCondition;
	
	Link(int id, String label, State source, State destination) {
		this.id = id;
		this.label = label;
		this.source = source;
		this.destination = destination;
		
		source.exitingConnectedLinks.add(this);
	}
	
	void CheckTransitionCondition() {
		boolean bool = false;
		if (id == 1) {
			// Procedure pour verifier si la condition de transition est vraie
			bool = isAnyArtefactClose();
		}
		else if (id == 2) {
			bool = isAnalysisDone();
		}
		else if (id == 15) {
			bool = isStartMissionOn();
		}
		else if (id == 17) {
			bool = isBoatInformed();
		}
		else if (id == 18) {
			bool = isDepthReached();
		}
		else if (id == 19) {
			bool = isCloseEnough();
		}
		// ...
		// Conclure par transitionCondition = bool
		transitionCondition = bool;
	}
	
	boolean isAnyArtefactClose() {
		boolean out = false;
		boolean isNull = (source.fsm.owner.boueesProches == null);
		if (isNull == false) {
			if (source.fsm.owner.boueesProches.isEmpty() == false) {
				out = true;
			}
		}
		return out;
	}
	
	boolean isAnalysisDone() {
		boolean out = false;
		double rand = Math.random();
		if (rand > 0.9) {
			out = true;
		}
		return out;
	}
	
	boolean isStartMissionOn() {
		boolean out = false;
		double rand = Math.random();
		if (rand > 0.5) {
			out = true;
		}
		return out;
	}
	
	boolean isBoatInformed() {
		boolean out = false;
		out = true;
		return out;
	}
	
	boolean isDepthReached() {
		boolean out = false;
		double eps = 0.5;
		if (Math.abs(source.fsm.owner.getPosition().getZ() + 10.0) < eps) {
			out = true;
		}
		return out;
	}
	
	boolean isCloseEnough() {
		boolean out = false;
		double eps = 10.0;
		
		if (Math.abs(source.fsm.owner.getPosition().distance(source.fsm.owner.PlusProcheBouee.getPosition())) < eps) {
			out = true;
		}
		return out;
	}
}
