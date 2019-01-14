/*
 * 
 */
package enstabretagne.base.logger.loggerimpl;

import java.util.HashMap;

import enstabretagne.base.logger.LoggerParamsNames;
import enstabretagne.base.messages.MessagesLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class LoggerConf.
 */
public class LoggerConf {
	
	/** The parametres. */
	public HashMap<String, String> parametres;
	
	/**
	 * Instantiates a new logger conf.
	 */
	public LoggerConf() {
		parametres = new HashMap<String, String>();
	}

	/**
	 * Check logger conf.
	 *
	 * @return the string
	 */
	public String checkLoggerConf() {
		String result = "";
		if(!parametres.containsKey(LoggerParamsNames.LoggerKind.toString()))
				result = MessagesLogger.LoggerKindNotProvided;
		else {
			String classToFind = parametres.get(LoggerParamsNames.LoggerKind.toString());
			try {
				Class.forName(classToFind);
			} catch (ClassNotFoundException e) {
				result = MessagesLogger.LoggerKindNotFound + " : "+classToFind;
			}
		}
		return result;
	}

}
