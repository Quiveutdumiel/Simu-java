package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Representation3D;


import java.util.Map;

import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import enstabretagne.monitor.implementation.YXZRotator;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

@Contrat3D(contrat = IBoueeRepresentation3D.class)
public class Bouee3DRepresentation2 extends Representation3D {
	
	public Bouee3DRepresentation2(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	IBoueeRepresentation3D bouee3D;
	Group maBouee;
	@Override
	public void init(Group world, Object obj) {
		bouee3D = (IBoueeRepresentation3D) obj;
		maBouee = new Group();
	    
	    PhongMaterial material; 
	    //material = new PhongMaterial(bouee3D.getColor());
	    

	    if(bouee3D.getType()==1) {
		    Sphere s = new Sphere(bouee3D.getSize());
		    material = new PhongMaterial(Color.RED);
		    s.setMaterial(material);
		    maBouee.getChildren().add(s);
		    /*
		    material = new PhongMaterial(Color.DARKGREEN);
		    
		    Sphere s1 = new Sphere(bouee3D.getSize()/2);
		    s1.setMaterial(material);
		    maBouee.getChildren().add(s1);
		    s1.setTranslateX(bouee3D.getSize());
	
		    material = new PhongMaterial(Color.AQUA);
	
	//	    Sphere s2 = new Sphere(bouee3D.getSize()/2);
	//	    s2.setMaterial(material);
	//	    maBouee.getChildren().add(s2);
	//	    s2.setTranslateX(-bouee3D.getSize());
	
		    Sphere s3 = new Sphere(bouee3D.getSize()/2);
		    s3.setMaterial(material);
		    maBouee.getChildren().add(s3);
		    s3.setTranslateY(bouee3D.getSize());
	
	//	    Sphere s4 = new Sphere(bouee3D.getSize()/2);
	//	    s4.setMaterial(material);
	//	    maBouee.getChildren().add(s4);
	//	    s4.setTranslateY(-bouee3D.getSize());
	
		    material = new PhongMaterial(Color.BLUEVIOLET);
	
		    Cylinder cy = new Cylinder(bouee3D.getSize()/2,bouee3D.getSize()/2*4);
		    cy.setMaterial(material);
		    cy.setRotationAxis(Rotate.X_AXIS);
		    cy.setRotate(90);
		    maBouee.getChildren().add(cy);
		    cy.setTranslateZ(bouee3D.getSize()*3/2);
		    */
	    }
	    
	    if(bouee3D.getType()==2) {
		    Cylinder c = new Cylinder(bouee3D.getSize(), bouee3D.getSize());
		    material = new PhongMaterial(Color.ORANGE);
		    c.setMaterial(material);
		    maBouee.getChildren().add(c);
	    }
	    
	    if(bouee3D.getType()==3) {
		    Box b = new Box(bouee3D.getSize(), bouee3D.getSize(), bouee3D.getSize());
		    material = new PhongMaterial(Color.DARKGREEN);
		    b.setMaterial(material);
		    maBouee.getChildren().add(b);
	    }
	    
	    if(bouee3D.getType()==4) {
		    Box b = new Box(20, 0.1, 20);
		    Color seaColor = new Color(0.4, 0.4, 1.0, 0.5);
		    material = new PhongMaterial(seaColor);
		    b.setMaterial(material);
		    maBouee.getChildren().add(b);
	    }
	    
	    if(bouee3D.getType()==5) {
		    Box b = new Box(2*bouee3D.getSize(), 2*bouee3D.getSize(), 2*bouee3D.getSize());
		    material = new PhongMaterial(Color.BLUEVIOLET);
		    b.setMaterial(material);
		    maBouee.getChildren().add(b);
	    }
	    
	    world.getChildren().add(maBouee);
	    
	}
	
	public void setRotation(Rotate r,double angle, Point3D pivot, Point3D axe) {
		r.setAngle(angle);
		r.setAxis(axe);
		r.setPivotX(pivot.getX());
		r.setPivotY(pivot.getY());
		r.setPivotZ(pivot.getZ());
	}
	
	int i =0;
	@Override
	public void update() {
		Point3D p = bouee3D.getPosition();

		maBouee.setTranslateX(p.getX());
		maBouee.setTranslateY(p.getY());
		maBouee.setTranslateZ(p.getZ());

//		{
		
//			XYZRotator rot = new XYZRotator();
//		    Point3D dir = new Point3D(1,0,0);
//	
//		    Point3D xyz =  XYZRotator.computeRotationXYZ_Zaxis2(p, dir);
//		    rot.rotate(maBouee, xyz);
//		    Logger.Detail(null, "update", "Vecteur angle = %s",xyz);
//		}
		
			Rotate rx= new Rotate();
			Rotate ry= new Rotate();
			Rotate rz= new Rotate();

			setRotation(rz, 90, Point3D.ZERO, Rotate.Z_AXIS);
			setRotation(ry, 90, Point3D.ZERO, rz.transform(Rotate.Y_AXIS));
			setRotation(rx, 90, Point3D.ZERO, rz.transform(ry.transform(Rotate.X_AXIS)));

			if(bouee3D.getType()>=2 && bouee3D.getType()<5) {			
			maBouee.getTransforms().add(rz);
			}
			
			if(bouee3D.getType()>=3 && bouee3D.getType()<5) {			
			maBouee.getTransforms().add(ry);
			}

			if(bouee3D.getType()>=4 && bouee3D.getType()<5) {
				maBouee.getTransforms().add(rx);
			}
//			if(bouee3D.getType()>=5) {			
//				Affine a = new Affine();
//				a.prepend(rx);
//				a.prepend(ry);
//				a.prepend(rz);
//				maBouee.getTransforms().add(a);
//			}


			Map.Entry<Affine, Point3D> atest = YXZRotator.computeRotationYXZ_Zaxis(new Point3D(-1,1,1));
			if(bouee3D.getType()>=5) {			
			maBouee.getTransforms().add(atest.getKey());
		}

			
//			rx= new Rotate();
//			ry= new Rotate();
//			rz= new Rotate();
//
//			Point3D dire = new Point3D(-1,-1,-1);
//			Point3D dirZX = new Point3D(dire.getX(),0,dire.getZ());
//			double angley = Rotate.Z_AXIS.angle(dirZX);
//			double sy = Math.signum(Rotate.Z_AXIS.crossProduct(dirZX).dotProduct(Rotate.Y_AXIS));
//			angley = sy*angley;
//			
//			setRotation(ry,angley,Point3D.ZERO,Rotate.Y_AXIS);
//			double anglex = dirZX.angle(dire);
//			double sx = Math.signum(dirZX.crossProduct(dire).dotProduct(ry.transform(Rotate.X_AXIS)));
//			anglex = sx*anglex;
//
//			System.out.println("ax="+anglex+ " ay="+angley);
//			Point3D di ;
//			
//			setRotation(rx,anglex,Point3D.ZERO,ry.transform(Rotate.X_AXIS));
//			
//			
//			double anglez;
//			
//			Affine af2;
//			af2= new Affine();
//			af2.prepend(ry);
//			af2.prepend(rx);
//
//			Point3D newX = af2.transform(Rotate.X_AXIS);
//			Point3D newZ = af2.transform(Rotate.Z_AXIS);
//			Point3D ref = newZ.crossProduct(Rotate.Z_AXIS);
//			
//			anglez = ref.angle(newX);
//			double sz = Math.signum(newX.crossProduct(ref).dotProduct(newZ));
//			anglez=anglez*sz;
//			setRotation(rz,anglez,Point3D.ZERO,af2.transform(Rotate.Z_AXIS));
//			
//			
//			Point3D nX,nY,nZ;
//			nZ=af2.transform(Rotate.Z_AXIS);
//			nX=af2.transform(Rotate.X_AXIS);
//			nY=af2.transform(Rotate.Y_AXIS);
//			
//			System.out.println(dire.normalize() +" =? " );
//			System.out.println(af2.transform(Rotate.Z_AXIS));
//			System.out.println(af2.transform(Rotate.Y_AXIS));
//			System.out.println(af2.transform(Rotate.X_AXIS));
//			System.out.println(">>"+rz.transform(nX));
//			af2.prepend(rz);
//			System.out.println(">>"+af2.transform(Rotate.X_AXIS));
//			System.out.println(nX.dotProduct(nZ));
//			System.out.println(nZ.dotProduct(nY));
//
//			if(bouee3D.getType()>=5) {			
//				maBouee.getTransforms().setAll(af2);
//			}
			
			
	}


}
	