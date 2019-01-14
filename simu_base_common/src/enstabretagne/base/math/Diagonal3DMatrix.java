/**
* Classe Diagonal3DMatrix.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class Diagonal3DMatrix.
 */
public class Diagonal3DMatrix {

	/** The z. */
	private double x,y,z;
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Instantiates a new diagonal 3 D matrix.
	 */
	private Diagonal3DMatrix(){}
	
	/**
	 * Instantiates a new diagonal 3 D matrix.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Diagonal3DMatrix(double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	/**
	 * Dot.
	 *
	 * @param v the v
	 * @return the vector 3 D
	 */
	public Vector3D dot(Vector3D v) {
		return new Vector3D(v.getX()*x,v.getY()*y,v.getZ()*z);
	}

	/**
	 * Inverse.
	 *
	 * @return the diagonal 3 D matrix
	 */
	public Diagonal3DMatrix inverse() {
		
		Diagonal3DMatrix m = new Diagonal3DMatrix();
		if(x!=0) m.x=1/x;
		if(y!=0) m.y=1/y;
		if(z!=0) m.z=1/z;
		
		return m;
	}
}

