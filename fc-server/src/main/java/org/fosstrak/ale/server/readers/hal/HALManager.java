package org.fosstrak.ale.server.readers.hal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.fosstrak.hal.HardwareAbstraction;
import org.fosstrak.hal.impl.sim.SimulatorController;
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
	 * @param the class to invoke that implements the hal (eg. org.fosstrak.hal.impl.sim.SimulatorController).
	 * @return an instance of a HardwareAbstraction reader.
	 */
	public synchronized HardwareAbstraction define(String halName, String propFile, String ImplementingClass) {
		HardwareAbstraction hal = null;
		
		if (hals.containsKey(halName)) {
			HALManagerEntry entry = hals.get(halName);
			hal = entry.get();
			entry.lease();
			
			log.debug("reusing running HAL instance: " + halName);
		} else {
			
			try {
				Class cls = Class.forName(ImplementingClass);
				// get the constructor
				Constructor ctor = cls.getConstructor(String.class, String.class);
				
				// invoke the constructor
				Object instnc = ctor.newInstance(halName, propFile);

				if (instnc instanceof HardwareAbstraction) {
					hal = (HardwareAbstraction) instnc;
					
					hals.put(halName, new HALManagerEntry(hal));
					
					log.debug("creating new HAL instance: " + halName);
					return hal;
				}
				
				return null;
		
			} catch (ClassNotFoundException e) {
				log.error(String.format("implementing class '%s' not found!", ImplementingClass));
				
			}	catch (SecurityException e) {
				log.error(String.format("security exception when creating instance of '%s'!", ImplementingClass));
				
			} catch (NoSuchMethodException e) {
				log.error(String.format("class '%s' has no constructor of form (String halName, String configFile)!", ImplementingClass));
			
			} catch (IllegalArgumentException e) {
				log.error(String.format("illegal arguments when invoking constructor on class '%s'!", ImplementingClass));
				
			} catch (InstantiationException e) {
				log.error(String.format("could not create instance of class '%s'!", ImplementingClass));
				
			} catch (IllegalAccessException e) {
				log.error(String.format("illegal class exception when creating instance of '%s'!", ImplementingClass));
				
			} catch (InvocationTargetException e) {
				log.error(String.format("could not invoke constructor on class '%s'!", ImplementingClass));
				
			}

			return null;
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
