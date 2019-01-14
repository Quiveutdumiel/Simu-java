/**
* Classe Environment.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base.models.environment;
import java.time.temporal.Temporal;

import javax.measure.quantity.Acceleration;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.jscience.physics.amount.Constants;

import enstabretagne.base.time.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Environment.
 */
public class Environment {
	
	/** The Simulated world origin date time. */
	public static Temporal SimulatedWorldOriginDateTime;
	
	/** The Constant DefaultTimeUnit. */
	public final static TimeUnits DefaultTimeUnit = TimeUnits.Minute;
	
	/** The Constant g. */
	public final static Vector3D g=new Vector3D(0,0,-Constants.g.doubleValue(Acceleration.UNIT));
	
	/** The Constant rho_air_25deg. */
	public final static double rho_air_25deg=1.293;//masse volumique de l'air à 25°C
}

