/**
* Classe Randomizer.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base.math;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Randomizer.
 *
 * @author Audoli
 */
public class Randomizer {

    /** The Constant ProbMin. */
    static final double ProbMin = 1.0E-12;
    
    /** The Constant ProbMax. */
    static final double ProbMax = 1.0 - ProbMin;

    /**
     * Instantiates a new randomizer.
     *
     * @param r the r
     */
    public Randomizer(Random r){
		
	}

    
	/**
	 * Gets the seed.
	 *
	 * @param replicaSeed the replica seed
	 * @param mixer the mixer
	 * @return the int
	 */
	public static int GetSeed(int replicaSeed, String mixer) {
	      int seed = replicaSeed;
	      if (seed == 0)
	        seed = (int) System.currentTimeMillis();

	      if (mixer == null)
	        return seed;

	      boolean b = false;
	      for(int i=0;i<mixer.length(); i++) // Use a specific (but portable) HashCode 
	      {
	    	int n = mixer.codePointAt(i);
	        b = !b;
	        if (b)
	          seed += n;
	        else
	          seed *= n;
	      }
	      return seed;   
	}

	/**
	 * Random generator type.
	 *
	 * @return the string
	 */
	public String RandomGeneratorType() {
		// TODO Auto-generated method stub
		return "RandomADeterminer";
	}
}

