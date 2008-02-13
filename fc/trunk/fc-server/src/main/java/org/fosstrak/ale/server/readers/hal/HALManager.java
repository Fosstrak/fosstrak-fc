package org.accada.ale.server.readers.hal;

import java.util.HashMap;
import java.util.Map;

import org.accada.hal.HardwareAbstraction;
import org.accada.hal.impl.sim.SimulatorController;
import org.apache.log4j.Logger;

/**
 * the HALManager creates and maintains instances of the HAL readers. 
 * @author sawielan
 *
 */
public class HALManager {
	
	/** 
	 * internal helper class to store HardwareAbstraction readers with 
	 * a reference counter.
	 * @author sawielan
	 *
	 */
	private class HALManagerEntry {
		
		/** the HAL reader itself. */
		private HardwareAbstraction hal = null;
		/** how many references onto this hal. */
		private int referenced = 0;
		
		/**
		 * creates a helper.
		 * @param hal the HAL reader.
		 */
		public HALManagerEntry(HardwareAbstraction hal) {
			this.hal = hal;
			referenced = 1;
		}
		
		/**
		 * increases the link counter on the stored HAL object.
		 */
		public void lease() {
			referenced++;
		}
		
		/**
		 * decreases the link counter on the stored HAL object. 
		 * @return the number of links on the stored HAL object.
		 */
		public int unlease() {
			referenced--;
			return referenced;
		}
		
		/**
		 * returns the stored HAL object.
		 * @return returns the stored HAL object.
		 */
		public HardwareAbstraction get() {
			return hal;
		}
	}
	
	/** the singleton of the HALManager. */
	private static HALManager instance = null;
		
	/** a hash map that stores the instances of the HAL readers. */
	private Map<String, HALManagerEntry> hals = null;
	
	/** logger. */
	private static final Logger log = Logger.getLogger(HALAdaptor.class);
	
	/** private constructor. we want only a singleton. */
	private HALManager() {
		hals = new HashMap<String, HALManagerEntry>();
	};
	
	/**
	 * define a new HardwareAbstraction reader. if the reader already exists 
	 * a reference to the reader is returned. otherwise a new reader is created.
	 * @param halName the name of the HAL reader that shall be created.
	 * @param propFile the properties file for the reader.
	 * @return an instance of a HardwareAbstraction reader.
	 */
	public synchronized HardwareAbstraction define(String halName, String propFile) {
		HardwareAbstraction hal = null;
		
		if (hals.containsKey(halName)) {
			HALManagerEntry entry = hals.get(halName);
			hal = entry.get();
			entry.lease();
			
			log.debug("reusing running HAL instance: " + halName);
		} else {
			// create the HAL device
			hal = new SimulatorController(halName, propFile);
			hals.put(halName, new HALManagerEntry(hal));
			
			log.debug("creating new HAL instance: " + halName);
		}
		
		return hal;
	}
	
	/**
	 * removes a HAL reader. if there is no other adaptor using the instance 
	 * of this HAL reader, the HAL will be destroyed.
	 * @param halName the name of the HAL reader that shall be undefined.
	 */
	public synchronized void undefine(String halName) {
		if (hals.containsKey(halName)) {
			
			int leases = hals.get(halName).unlease();
			if (leases < 1) {
				// need to undefine the hal
				hals.remove(halName);
				
				log.debug("there are no other instances using : " + halName + ". therefor destroy it.");
			} else {
				log.debug("there are other instances still using : " + halName + ". therefor do not destroy it.");
			}
		}
	}
	
	/**
	 * create the singleton instance of the HALManager.
	 * @return an instance of the singleton HALManager.
	 */
	public static synchronized HALManager getInstance() {
		if (HALManager.instance == null) {
			HALManager.instance = new HALManager();
		}
		return HALManager.instance;
	}
}
