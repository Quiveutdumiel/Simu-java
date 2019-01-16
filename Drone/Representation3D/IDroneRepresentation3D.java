package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Representation3D;

import enstabretagne.monitor.interfaces.IMovable;
import javafx.scene.paint.Color;

public interface IDroneRepresentation3D extends IMovable{
	Color getColor();
	double getSize();
	int getType();	
}


