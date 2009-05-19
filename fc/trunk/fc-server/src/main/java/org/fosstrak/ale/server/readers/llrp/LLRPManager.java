package org.fosstrak.ale.server.readers.llrp;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.llrp.adaptor.Adaptor;
import org.fosstrak.llrp.adaptor.AdaptorManagement;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;
import org.fosstrak.llrp.client.LLRPExceptionHandler;
import org.fosstrak.llrp.client.LLRPExceptionHandlerTypeMap;
import org.fosstrak.llrp.client.LLRPMessageItem;
import org.fosstrak.llrp.client.MessageHandler;
import org.llrp.ltk.types.LLRPMessage;


/**
 * the LLRPManager is a singleton providing access to the llrp-client-adaptor. the 
 * manager starts an instance of the llrp-client-adaptor and tries to register 
 * this adaptor into rmi. the adaptor is run on a thread to allow the application 
 * to proceed. if there occurs an error that does not allow the manager to proceed 
 * the error condition is signaled by the static error flag.
 * @author sawielan
 *
 */
public class LLRPManager implements LLRPExceptionHandler, MessageHandler {
	
	/** the path to the configuration file. */
	public static final String CONFIGURATION_FILE = "/llrpAdaptorConfiguration.properties";
	
	/** the singleton of the LLRPManager. */
	private static LLRPManager instance = null;
	
	/** logger. */
	private static final Logger log = Logger.getLogger(LLRPManager.class);
	
	/** the adaptor from the llrp-gui client managing the connections to the llrp-readers. */
	private static Adaptor adaptor = null;
	
	/** if there has been an error during setup we flag this. */
	private static boolean error = false;
	
	public static final String ADAPTOR_NAME_PREFIX = "fc adaptor - ";
	
	/** get a handle on the adaptor management. */
	private final AdaptorManagement mgmt = AdaptorManagement.getInstance();
	
	/** 
	 * a link counter counting the references onto a physical reader. 
	 * the link counter is needed as several logical readers from 
	 * fc can point to the same physical reader in the reader module. 
	 * */
	private Map<String, Integer> linkCounter = new HashMap<String, Integer>();
	
	/**
	 * private constructor as we need the LLRPManager to act as a singleton.
	 * @throws ImplementationExceptionResponse whenever the LLRPManager cannot be created.
	 */
	private LLRPManager() throws Exception {
		try {
			initialize();
			log.info("llrp adaptor is bound");	

		} catch (LLRPRuntimeException e) {
			log.error("there has been an error when creating the reader management");
			throw new Exception("there has been an error when creating the reader management");
		}
	}
	
	/**
	 * try to initialize the adaptor holding the llrp-readers. <br/>
	 * try to acquire the rmi registry from the localhost. if this fails create a 
	 * new rmi registry and also create a new llrp-adaptor. if everything is ok 
	 * then (registry exists and can be contacted) try to acquire the existing 
	 * adaptor from the registry. if this fails try to create a new adaptor as well. 
	 * @throws LLRPRuntimeException 
	*/
	private void initialize() throws LLRPRuntimeException {
		
		String resolvedConfig = null;
		try {
			URL url1 = LLRPManager.class.getResource(CONFIGURATION_FILE);			
			resolvedConfig = url1.getFile();
			
			// try to read from it ...
			File f = new File(resolvedConfig);
			if (f.exists() && f.canRead() && f.canWrite()) {
				log.debug("found config file: " + resolvedConfig);
			} else {
				throw new Exception("config file not found");
			}
		} catch (Exception e) {
			log.error("no config file - therefore using defaults...");
		}
		
		
		// tell the adaptor management to export the adaptor with RMI (last true).
		mgmt.initialize(resolvedConfig, resolvedConfig, false, this, this, true);
		
		// get the first exported adaptor.
		if (mgmt.getAdaptorNames().size()>0) {
			adaptor = mgmt.getAdaptor(mgmt.getAdaptorNames().get(0));
		} else {
			throw new LLRPRuntimeException("no adaptor was found!!!");
		}
	}
	
	/**
	 * return a reference to the llrp gui client adaptor management. 
	 * @return an instance of the llrp gui client adaptor management.
	*/
	public Adaptor getAdaptor() {
		return adaptor;
	}
	
	/**
	 * increments the link counter on a reader. the link counter is needed as several 
	 * logical readers from fc can point to the same physical reader in the reader 
	 * module.
	 * @param readerName the name of the reader.
	 */
	public void incReference(String readerName) {
		if (!linkCounter.containsKey(readerName)) {
			linkCounter.put(readerName, 0);
		}
		
		linkCounter.put(readerName, linkCounter.get(readerName).intValue() + 1);
	}
	
	/** 
	 * decrements the link counter of the reader. the link counter is needed as several 
	 * logical readers from fc can point to the same physical reader in the reader 
	 * module. if no logical reader of the fc points to the physical reader, this reader 
	 * is considered to be used no more therefore if counter is zero the reader gets undefined.
	 * @param readerName the name of the reader.
	 * @throws LLRPRuntimeException when there is an exception in the reader module.
	 * @throws RemoteException when there is an rmi exception.
	 */
	public void decReferenceCount(String readerName) throws RemoteException, LLRPRuntimeException {
		if (!linkCounter.containsKey(readerName)) {
			return;
		}
		
		if (linkCounter.get(readerName).intValue() > 0) {
			int newVal = linkCounter.get(readerName) - 1;
			if (newVal == 0) {
				// undefine the reader from the adaptor.
				getAdaptor().undefine(readerName);
			}
			
			linkCounter.put(readerName, newVal);
		}
	}

	/**
	 * @return true if the manager was not started correctly or crashed.
	 */
	public static boolean isErroneous() {
		return error;
	}
		
	/**
	 * create the singleton instance of the LLRPManager.
	 * @return an instance of the singleton LLRPManager.
	 * @throws ImplementationExceptionResponse 
	 */
	public static synchronized LLRPManager getInstance() {
		if (LLRPManager.instance == null) {
			try {
				LLRPManager.instance = new LLRPManager();
				log.debug("successfully initiated the llrp manager");
			} catch (Exception e) {
				log.error("severe error - the llrp adaptor could not be initialized");
				LLRPManager.error = true;
				LLRPManager.instance = null;
			}
		}
		return LLRPManager.instance;
	}
	
	
	
	
	
	
	/**
	 * receives an asynchronous exception and posts this one. (comes from ExceptionHandler).
	 */
	public void postExceptionToGUI(LLRPExceptionHandlerTypeMap eTypeMap,
			LLRPRuntimeException e, String adaptorName, String readerName) {
		log.error("An exception occured - printing stack-trace:");
		e.printStackTrace();
	}

	public void handle(String adaptorName, String readerName,
			LLRPMessage message) {
		log.debug("placeholder. NEVER IMPLEMENTED.");		
	}


}
