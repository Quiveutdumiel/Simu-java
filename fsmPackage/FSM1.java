package fsmPackage;

import java.util.ArrayList;
import java.util.List;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Drone;

public class FSM1 {
	public State currentState;
	Drone owner;
	
	boolean canStartMission = false;
	
	public FSM1(Drone drone) {
		State s1 = new State(1, "Recherchant une cible", new ArrayList<Link>(), this);
		//State s2 = new State(2, "Analysant la cible", new ArrayList<Link>(), this);
		State s201 = new State(201, "Prévenir le navire", new ArrayList<Link>(), this);
		State s202 = new State(202, "Plongeant verticale", new ArrayList<Link>(), this);
		State s203 = new State(203, "Se rapprochant directement", new ArrayList<Link>(), this);
		State s204 = new State(204, "Tour 360", new ArrayList<Link>(), this);
		State s3 = new State(3, "Remontant vers la surface", new ArrayList<Link>(), this);
		State s4 = new State(4, "Envoyant les clichés", new ArrayList<Link>(), this);
		State s5 = new State(5, "Attendant la réponse", new ArrayList<Link>(), this);
		State s6 = new State(6, "En attente (batterie faible)", new ArrayList<Link>(), this);
		State s7 = new State(7, "Retournant vers le navire", new ArrayList<Link>(), this);
		State s8 = new State(8, "Au navire", new ArrayList<Link>(), this);
		
		Link l1 = new Link(1, "Cible détectée", s1, s201);
		Link l17 = new Link(17, "Message envoyé", s201, s202);
		Link l18 = new Link(18, "Profondeur atteinte", s202, s203);
		Link l19 = new Link(19, "Distance de proximité atteinte", s203, s204);
		Link l20 = new Link(20, "Analyse finie", s204, s3);
		Link l3 = new Link(3, "Surface atteinte", s3, s4);
		Link l4 = new Link(4, "Clichés envoyés", s4, s5);
		Link l5 = new Link(5, "Cible sans intérêt", s5, s1);
		Link l6 = new Link(6, "Besoin d'une seconde analyse", s5, s201);
		Link l7 = new Link(7, "Cible identifiée", s1, s7);
		Link l8 = new Link(8, "Cible identifiée", s4, s7);
		Link l9 = new Link(9, "Cible identifiée", s5, s7);
		Link l10 = new Link(10, "Batterie faible", s1, s6);
		Link l11 = new Link(11, "Batterie faible", s4, s6);
		Link l12 = new Link(12, "Batterie faible", s5, s6);
		Link l13 = new Link(13, "Batterie faible", s7, s6);
		Link l141 = new Link(141, "Batterie faible", s201, s3);
		Link l142 = new Link(142, "Batterie faible", s202, s3);
		Link l143 = new Link(143, "Batterie faible", s203, s3);
		Link l144 = new Link(144, "Batterie faible", s204, s3);
		Link l15 = new Link(15, "Envoi mission", s8, s1);
		Link l16 = new Link(16, "Arrivé au bateau", s7, s8);
		
		owner = drone;
		currentState = s8;
	}
	
	List<Link> ActivatedLinks() {
		List<Link> activatedLinks = new ArrayList<Link>();
		for (Link link : currentState.exitingConnectedLinks) {
			link.CheckTransitionCondition();
			if (link.transitionCondition) {
				activatedLinks.add(link);
			}
		}
		return activatedLinks;
	}
	
	public boolean setNewState() {
		boolean out = false;
		List<Link> activatedLinks = ActivatedLinks();
		int nActivatedLinks = activatedLinks.size();
		if (nActivatedLinks != 1) {
			System.out.println("Number of activated links = " + Integer.toString(nActivatedLinks));
			System.out.println("    ==> Current state unchanged");
		}
		else {
			System.out.println("Changing state");
			out = true;
			State newState = activatedLinks.get(0).destination;
			currentState = newState;
			currentState.ActivateState();
		}
		return out;
	}
	
}
