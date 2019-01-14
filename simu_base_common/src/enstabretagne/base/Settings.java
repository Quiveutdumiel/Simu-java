/**
* Classe Settings.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class Settings.
 */
public class Settings {
	
	/** The Constant jsonb. */
	public static final Jsonb jsonb;
	static {
		JsonbConfig config = new JsonbConfig().withFormatting(true);
		jsonb= JsonbBuilder.create(config);
	}

	/** The Constant configuration_dir. */
	public static final String configuration_dir="conf";
	
	/** The Constant controleurSuffixe. */
	public static final String controleurSuffixe = "_controler";
	
	/** The Constant date_time_separator. */
	public static final String date_time_separator = " ";

	
	/**
	 * Sep.
	 *
	 * @return the char
	 */
	public static char sep(){
		return ';';
	}
	
	/**
	 * Use portable random generator.
	 *
	 * @return true, if successful
	 */
	public static boolean UsePortableRandomGenerator() {
		return false;
	}

	/**
	 * Use one random generator per agent.
	 *
	 * @return true, if successful
	 */
	public static boolean UseOneRandomGeneratorPerAgent() {
		return false;
	}

	
	/**
	 * Use binary tree for event list.
	 *
	 * @return true, if successful
	 */
	public static boolean UseBinaryTreeForEventList() {
		return false;
	}

	/**
	 * Checks if is engine integrity checked.
	 *
	 * @return true, if successful
	 */
	public static boolean IsEngineIntegrityChecked() {
		return false;
	}

	/**
	 * Default synchro order.
	 *
	 * @return the int
	 */
	public static int DefaultSynchroOrder() {
		return 4;
	}
	
	/**
	 * Tick value.
	 *
	 * @return the double
	 */
	public static double TickValue(){
		return 0.0001;
	}
	
	/**
	 * Time origin.
	 *
	 * @return the local date time
	 */
	public static LocalDateTime timeOrigin(){
		return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
	}
	
	/**
	 * Filter engine logs.
	 *
	 * @return true, if successful
	 */
	public static boolean filterEngineLogs() {
		return true;
	}
}

