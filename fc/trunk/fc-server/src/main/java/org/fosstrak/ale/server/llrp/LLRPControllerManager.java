package org.fosstrak.ale.server.llrp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import org.fosstrak.ale.server.readers.LogicalReaderManager;

import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.llrp.ltk.exceptions.InvalidLLRPMessageException;
import org.llrp.ltk.generated.LLRPMessageFactory;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.DELETE_ROSPEC;
import org.llrp.ltk.generated.messages.DISABLE_ROSPEC;
import org.llrp.ltk.generated.messages.ENABLE_ROSPEC;
import org.llrp.ltk.generated.messages.START_ROSPEC;
import org.llrp.ltk.generated.messages.STOP_ROSPEC;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.types.LLRPMessage;
import org.llrp.ltk.types.UnsignedInteger;

import org.fosstrak.ale.server.persistence.Config;
import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;

/**
 * ORANGE: This class manages the ROSPEC.
 * 
 * @author wafa.soubra@orange.com
 */
public class LLRPControllerManager  {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(LLRPControllerManager.class.getName());
	
	/** key = logical reader name, value = RoSpec */
	private static HashMap<String, ROSpec> lrROSpecMap = new HashMap<String, ROSpec>();
	
	/** key = logical reader name, value = physical reader*/
	// TODO change the value to a list in case of a Composite Reader
	private static HashMap<String, String> lrPhysicalMap = new HashMap<String, String>();
	
	/** key = physical reader name, value = logical reader name*/
	private static HashMap<String, String> physicalLRMap = new HashMap<String, String>();
	
	/** key = logical reader name, value = the LLRP thread */
	// TODO change the value to a list in case of a Composite Reader
	private static HashMap <String, LLRPChecking> lrLLRPCheckMap= new HashMap<String, LLRPChecking>();
	
	/** key = logical reader name, value = boolean, true if the LLRP connection is established */
	private static HashMap <String, Boolean> physicalConnectedMap= new HashMap<String, Boolean>();

	/** file containing the launching context of LLRP. */
	private static final String LLRP_CONFIG_PROP_FILE="llrpConfig.properties";
	
	/** the properties read from file. */
	private static Properties props = null; 
	
	/** flag to indicate if we wait for the acknowledge of the connection. */
	private static boolean toWaitForConnection = true;
	
	/**
	 * Add an ROSpec to a declared logical reader in the ALE and enable it.
	 * @param lrSpecName the name of the logical reader
	 * @param pathFile a file containing the description of the ADD_ROSPEC
	 * Note : if we define this method as a Web Method, JAXB fails at runtime
	 * because ADD_ROSPEC contains interfaces and JAXB cannot deserialize them. 
	 * In our client, we call directly the "define(String lrSpecName, ADD_ROSPEC addRoSpec)" 
	 * We can call the "define" webmethod in the LLRPControllerImpl which will call 
	 * the "define" function below ==> MUST BE TESTED. 
	 */
	
	public void define (String lrSpecName, String pathFile) throws DuplicateNameExceptionResponse, NoSuchNameExceptionResponse {
		ADD_ROSPEC addRoSpec = null;
		try {
			LOG.debug("pathfile of add_rospec is " + pathFile);
			addRoSpec = org.fosstrak.ale.util.DeserializerUtil.deserializeAddROSpec(pathFile);
			LOG.debug("ID of the deserialized add_rospec = " + addRoSpec.getROSpec().getROSpecID());
		} catch (FileNotFoundException e) {
			LOG.error("add_rospec file not found " + pathFile, e);				
		} catch (Exception e) {
			LOG.error("error to read add_rospec file " + pathFile, e);
		}	
		define(lrSpecName, addRoSpec);
	}
	
	
	/**
	 * Add a new RoSpec, enable it, launch the thread and persist the ADD_ROSPEC
	 * @param lrSpecName the logical reader name
	 * @param addRoSpec the ADD_ROSPEC object
	 */
	
	public void define(String lrSpecName, ADD_ROSPEC addRoSpec) 
		throws DuplicateNameExceptionResponse, NoSuchNameExceptionResponse {
		if (addRoSpec != null) {
			LOG.debug("Define an ADD_ROSPEC for " + lrSpecName);
			// init the Connection and the LLRP context
			AdaptorMgmt.initializeLLRPContext();
			String readerName= retrievePhysicalReader (lrSpecName);
			getLLRPConfiguration();
			initClientConnection(readerName);
			// add ROSpec
			AdaptorMgmt.sendLLRPMessage(readerName, addRoSpec);
			// enable the ROSpec
			ENABLE_ROSPEC enableROSpec = new ENABLE_ROSPEC();
			UnsignedInteger roSpecId = addRoSpec.getROSpec().getROSpecID();
			enableROSpec.setROSpecID(roSpecId);
			AdaptorMgmt.sendLLRPMessage(readerName, enableROSpec);
			// init the internal data
			lrROSpecMap.put(lrSpecName, addRoSpec.getROSpec());
			physicalLRMap.put(readerName, lrSpecName);
			//TODO: case of composite reader
			lrPhysicalMap.put(lrSpecName, readerName);
			//TODO: case of composite reader
			lrLLRPCheckMap.put(lrSpecName, new LLRPChecking(readerName));
			// persistence
			WriteConfig.writeAddROSpec(lrSpecName, addRoSpec);
			LOG.debug("End Define an ADD_ROSPEC for " + lrSpecName);
		} else {
			LOG.error("ERROR !!!! ADD_ROSPEC is null for " + lrSpecName);
		}
	}
	
	/**
	 * Delete the ROSpec defined on the logical reader, stop the thread and 
	 * remove the persisted file. 
	 * @param lrSpecName the name of the logical reader
	 */
	
	public void undefine(String lrSpecName) throws NoSuchNameExceptionResponse {
		LOG.debug("Undefine ROSPEC for " + lrSpecName);
		if (!lrROSpecMap.containsKey(lrSpecName)) {
			throw new NoSuchNameExceptionResponse("this logical reader doesn't exist");
		}
		ROSpec roSpec = lrROSpecMap.get(lrSpecName);
		if (roSpec != null) {
			// stop the thread and remove it
			LLRPChecking llrpCheck = lrLLRPCheckMap.get(lrSpecName);
			llrpCheck.stop();
			lrLLRPCheckMap.remove(lrSpecName);
			// delete the defined ROSpec and remove it
			 DELETE_ROSPEC deleteRoSpec = new DELETE_ROSPEC();
			 deleteRoSpec.setROSpecID(roSpec.getROSpecID());
			 AdaptorMgmt.sendLLRPMessage(llrpCheck.getReaderName(), deleteRoSpec);
			 // remove the lrSpecName from the HashMap
			 lrROSpecMap.remove(lrSpecName); 
			 //persistence
			 RemoveConfig.removeROSpec(lrSpecName);
		}
		LOG.debug("End Undefine ROSPEC for " + lrSpecName);
	}
	
	
	/**
	 * Starts the RoSpec defined on the logical reader
	 * @param lrSpecName the logical reader name
	 */
	
	public void start (String lrSpecName) throws NoSuchNameExceptionResponse {
		LOG.debug("Start ROSPEC for " + lrSpecName);
		if (!lrROSpecMap.containsKey(lrSpecName)) {
			throw new NoSuchNameExceptionResponse("this logical reader doesn't exist");
		}
		ROSpec roSpec = lrROSpecMap.get(lrSpecName);
		String readerName = lrPhysicalMap.get(lrSpecName);
		if (roSpec != null && readerName != null) {	
			START_ROSPEC startROSpec = new START_ROSPEC();
			startROSpec.setROSpecID(roSpec.getROSpecID());
			AdaptorMgmt.sendLLRPMessage(readerName, startROSpec);
		}
		LOG.debug("End Start ROSPEC for " + lrSpecName);
	}
	
	/**
	 * Stop the RoSpec defined on the logical reader
	 * @param lrSpecName the logical reader name
	 */
	
	public void stop(String lrSpecName) throws NoSuchNameExceptionResponse {
		LOG.debug("Stop ROSPEC for " + lrSpecName);
		if (!lrROSpecMap.containsKey(lrSpecName)) {
			throw new NoSuchNameExceptionResponse("this logical reader doesn't exist");
		}
		ROSpec roSpec = lrROSpecMap.get(lrSpecName);
		String readerName = lrPhysicalMap.get(lrSpecName);
		if (roSpec != null && readerName != null) {	
			STOP_ROSPEC stopROSpec = new STOP_ROSPEC();
			stopROSpec.setROSpecID(roSpec.getROSpecID());
			AdaptorMgmt.sendLLRPMessage(readerName, stopROSpec);
		}
		LOG.debug("End Stop ROSPEC for " + lrSpecName);
	}
	
	/**
	 * Enable the RoSpec defined on the logical reader
	 * @param lrSpecName the logical reader name
	 */
	
	public void enable(String lrSpecName) throws NoSuchNameExceptionResponse {
		LOG.debug("Enable ROSPEC for " + lrSpecName);
		if (!lrROSpecMap.containsKey(lrSpecName)) {
			throw new NoSuchNameExceptionResponse("this logical reader doesn't exist");
		}
		ROSpec roSpec = lrROSpecMap.get(lrSpecName);
		String readerName = lrPhysicalMap.get(lrSpecName);
		if (roSpec != null && readerName != null) {	
			ENABLE_ROSPEC enableROSpec = new ENABLE_ROSPEC();
			enableROSpec.setROSpecID(roSpec.getROSpecID());
			AdaptorMgmt.sendLLRPMessage(readerName, enableROSpec);
		}
		LOG.debug("End Enable ROSPEC for " + lrSpecName);
	}
	
	/**
	 * Disable the RoSpec defined on the logical reader
	 * @param lrSpecName the logical reader name
	 */
	
	public void disable(String lrSpecName) throws NoSuchNameExceptionResponse {
		LOG.debug("Disable ROSPEC for " + lrSpecName);
		if (!lrROSpecMap.containsKey(lrSpecName)) {
			throw new NoSuchNameExceptionResponse("this logical reader doesn't exist");
		}
		ROSpec roSpec = lrROSpecMap.get(lrSpecName);
		String readerName = lrPhysicalMap.get(lrSpecName);
		if (roSpec != null && readerName != null) {	
			DISABLE_ROSPEC disableROSpec = new DISABLE_ROSPEC();
			disableROSpec.setROSpecID(roSpec.getROSpecID());
			AdaptorMgmt.sendLLRPMessage(readerName, disableROSpec);
		}
		LOG.debug("End Disable ROSPEC for " + lrSpecName);
	}
	
	/**
	 * Disable all defined RoSpec.
	 */
	
	public void disableAll() {
		LOG.debug("DisableAll ROSPEC on LLRP Readers");
		for (String lrSpecName : lrROSpecMap.keySet()) {			
			try {
				disable(lrSpecName);
			} catch (NoSuchNameExceptionResponse e) {
				LOG.error("try to stop lrSpec " + lrSpecName, e);
			}			
		}
		LOG.debug("End DisableAll ROSPEC on LLRP Readers");
	}
	
	/**
	 * Add again the ROSpec and enable it. 
	 * @param readerName the physical reader name
	 */
	public static void redefine (String readerName) {
		LOG.debug("Start Redefine for " + readerName);
		ROSpec roSpec = null;
		String logicalName = physicalLRMap.get(readerName);
		if (logicalName != null) {
			roSpec = lrROSpecMap.get(logicalName);
			if (roSpec != null) {	
				ADD_ROSPEC addRoSpec = new ADD_ROSPEC();
				addRoSpec.setROSpec(roSpec);
				AdaptorMgmt.sendLLRPMessage(readerName, addRoSpec);	
				ENABLE_ROSPEC enableROSpec = new ENABLE_ROSPEC();
				enableROSpec.setROSpecID(roSpec.getROSpecID());
				AdaptorMgmt.sendLLRPMessage(readerName, enableROSpec);
			} else {
				if (roSpec == null) {
					LOG.error("Undefined ROSpec for this physical reader " + readerName);	
				}
			}
		} else {
			LOG.error("Undefined logical reader for this physical reader " + readerName);
		} 	
		LOG.debug("End Redefine for " + readerName);
	}
	
	/**
	 * Set the connection of a reader
	 * @param readerName the name of the physical reader
	 * @param connected boolean if true the connection is established
	 */
	public static void setReaderConnected (String readerName, boolean connected) {
		physicalConnectedMap.put(readerName, connected);
	}
	
	
	/**
	 * Retrieves the name of the physical reader associated to the LR.
	 * @param readerName the name of the physical reader
	 * @return the physical reader name
	 */
	private static String retrievePhysicalReader (String lrSpecName) 
		throws DuplicateNameExceptionResponse, NoSuchNameExceptionResponse {
		if (!LogicalReaderManager.contains(lrSpecName)) {
			throw new NoSuchNameExceptionResponse("this logical reader doesn't exist");
		}
		// Test if a LR with the given name already exists or 
		// if we are adding another ROSpec to the same reader.
		if (lrROSpecMap.containsKey(lrSpecName) ) { 
			throw new DuplicateNameExceptionResponse
				(DuplicateNameExceptionResponse.DUPLICATE_NAME_EXCEPTION);
		} 
		String readerName = null;
		try {
			readerName = LogicalReaderManager.getPropertyValue(lrSpecName, "PhysicalReaderName");
		} catch(org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse e) {
			LOG.error("Missing property PhysicalReaderName", e);
		} catch(ImplementationExceptionResponse e) {
			LOG.error("Error when trying to get property PhysicalReaderName", e);
		} catch(SecurityExceptionResponse e) {
			LOG.error("Error when trying to get property PhysicalReaderName", e);
		}
		return readerName;
	}
	
	/**
	 * Get the launching configuration of LLRP.
	 * 1) rifidiEmulator = true, when we test with the Rifidi Emulator.
	 *    The acknowledge of the connection "reader_event_connection" 
	 *    is never sent to messageHandler in Fosstrak.
	 * 2) waitConnection = false, if we don't want to wait for ACK of the connection.
	 */
	private static void getLLRPConfiguration () {
		if (props == null) {
			props = new Properties();
			// try to load the properties file
			try {
				FileInputStream fileInputStream = 
					new FileInputStream(Config.getRealPathLLRPSpecDir() + LLRP_CONFIG_PROP_FILE);	
				props.load(fileInputStream);
				Boolean rifidi = new Boolean(props.getProperty("rifidiEmulator"));
				Boolean wait = new Boolean (props.getProperty("waitConnection"));
				LOG.debug("rifidiEmulator " + rifidi);
				LOG.debug("waitConnection " + wait);
				if (rifidi || !wait) {
					toWaitForConnection=false;
					LOG.debug("toWaitForConnection " + toWaitForConnection);
				}
			} catch (FileNotFoundException e) {
				LOG.error("Config. file " + LLRP_CONFIG_PROP_FILE + " was not found: ", e);
			} catch (IOException e) {
				LOG.error("IO Exception when reading the config. file " + LLRP_CONFIG_PROP_FILE, e);
			}	
		}
	}

	/**
	 * Wait until the LLRP connection between the client and the reader 
	 * is really established, and that to avoid sending several get_rospecs 
	 * messages from the LLRPChecking thread. 
	 * @param readerName the name of the physical reader
	 */
	private static void initClientConnection (String readerName) {
		if (toWaitForConnection) {
			while (physicalConnectedMap.get(readerName)== null || !physicalConnectedMap.get(readerName)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
		 			LOG.error("Error when init or waiting for the client connection ", e );
		 		}
			}
		}
	}

}