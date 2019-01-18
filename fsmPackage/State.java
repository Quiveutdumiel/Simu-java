package fsmPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {
	public int id;
	public String label;
	FSM1 fsm;
	List<Link> exitingConnectedLinks = new ArrayList<Link>();
	
	State(int id, String label, List<Link> existingConnectedLinks, FSM1 fsm){
		this.id = id;
		this.label = label;
		this.fsm = fsm;
		this.exitingConnectedLinks = existingConnectedLinks;
	}
	
	public void ActivateState() {
		if (id == 1) {
			fsm.owner.PostState1();
		}
		else if (id == 201) {
			fsm.owner.PostState201();
		}
		else if (id == 202) {
			fsm.owner.PostState202();
		}
		else if (id == 203) {
			fsm.owner.PostState203();
		}
		else if (id == 8) {
			fsm.owner.PostState8();
		}
		// ...
	}
}
