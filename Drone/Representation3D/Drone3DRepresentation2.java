package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Representation3D;

import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import enstabretagne.monitor.implementation.XYZRotator2;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Drone.Representation3D.IDroneRepresentation3D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
//import javafx.scene.shape.Sphere;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

@Contrat3D(contrat = IDroneRepresentation3D.class)
public class Drone3DRepresentation2 extends Representation3D {
	
	public Drone3DRepresentation2(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	IDroneRepresentation3D Drone3D;
	Group bateau;
	int r1=1;
	int h=2;
	
	//ici mettre les objets3D repr�sentant l'entit� 
	//Sphere sph;

	@Override
	public void init(Group world, Object obj) {
		Drone3D = (IDroneRepresentation3D) obj;
	    bateau = new Group();
	    
	    
	    PhongMaterial material = new PhongMaterial(Drone3D.getColor());

	    Cylinder cy = new Cylinder(r1, h*2);
	    cy.setMaterial(material);
	    cy.setRotationAxis(Rotate.Z_AXIS);
	    cy.setRotate(90.0);
	    cy.setTranslateX(-h/2);
	    bateau.getChildren().add(cy);
/*
	    Sphere s = new Sphere(r1);
	    s.setMaterial(material);
	    s.setTranslateX(h/2);
	    bateau.getChildren().add(s);
	    
	    double c = r1;
	    Box b = new Box(c,c,c);
	    material = new PhongMaterial(Color.BLUEVIOLET);
	    b.setMaterial(material);
	    b.setTranslateZ(r1);
	    b.setTranslateX(h/2-c);
	    bateau.getChildren().add(b);
	    */
		world.getChildren().add(bateau);

	}

	@Override
	public void update() {
		Point3D p = Drone3D.getPosition();

		bateau.setTranslateX(p.getX());
		bateau.setTranslateY(p.getY());
		bateau.setTranslateZ(p.getZ());
		
		Point3D rot = Drone3D.getRotationXYZ();
		
		Affine a = XYZRotator2.getTransformByAngle(rot);
		bateau.getTransforms().setAll(a);

	}


}
