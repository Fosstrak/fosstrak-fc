package org.fosstrak.ale.server.readers.llrp;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.fosstrak.llrp.adaptor.Adaptor;
import org.fosstrak.llrp.adaptor.AdaptorManagement;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;
import org.fosstrak.llrp.client.LLRPExceptionHandler;
import org.fosstrak.llrp.client.LLRPExceptionHandlerTypeMap;
import org.fosstrak.llrp.client.LLRPMessageItem;
import org.fosstrak.llrp.client.MessageHandler;
import org.fosstrak.llrp.client.ROAccessReportsRepository;
import org.fosstrak.llrp.client.Repository;
import org.fosstrak.llrp.client.RepositoryFactory;
import org.llrp.ltk.exceptions.InvalidLLRPMessageException;
import org.llrp.ltk.generated.messages.RO_ACCESS_REPORT;
import org.llrp.ltk.generated.parameters.LLRPStatus;
import org.llrp.ltk.types.LLRPMessage;


/**
 * the LLRPManager is a singleton providing access to the llrp-client-adaptor. the 
 * manager starts an instance of the llrp-client-adaptor and tries to register 
 * this adaptor into rmi. the adaptor is run on a thread to allow the application 
 * to proceed. if there occurs an error that does not allow the manager to proceed 
 * the error condition is signaled by the static error flag.
 * @author swieland
 *
 */
public class LLRPManager implements LLRPExceptionHandler, MessageHandler {
	
	/** the path to the configuration file. */
	public static final String CONFIGURATION_FILE = "/llrpAdaptorConfiguration.properties";
	
	/** the name of the property of the adapter management configuration file.*/
	public static final String PROP_MGMT_CFG = "mgmt";
	
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
	
	/** if defined, a handle to the LLRP message repository. */
	private Repository repository = null;
	
	/** 
	 * a link counter counting the references onto a physical reader. 
	 * the link counter is needed as several logical readers from 
	 * fc can point to the same physical reader in the reader module. 
	 * */
	private Map<String, Integer> linkCounter = new HashMap<String, Integer>();
	
	/**
	 * private constructor as we need the LLRPManager to act as a singleton.
	 * @throws ImplementationException whenever the LLRPManager cannot be created.
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
		String mgmtConfigFile = null;
		Properties props = null;
		try {
			URL url1 = LLRPManager.class.getResource(CONFIGURATION_FILE);
			String resolvedConfig = url1.getFile();
			
			props = new Properties();
			props.load(new FileInputStream(new File(resolvedConfig)));
			
			// try to read the configuration file for the adapter management.
			mgmtConfigFile = props.getProperty(PROP_MGMT_CFG, null);			
			
			// try to read from it ...
			File f = new File(mgmtConfigFile);
			if (f.exists() && f.canRead() && f.canWrite()) {
				log.debug("found config file: " + mgmtConfigFile);
			} else {
				throw new Exception("config file not found");
			}
		} catch (Exception e) {
			log.error("no config file - therefore using defaults...");
		}
		
		
		// tell the adaptor management to export the adaptor with RMI (last true).
		mgmt.initialize(mgmtConfigFile, mgmtConfigFile, false, this, this, true);
		
		// get the first exported adaptor.
		if (mgmt.getAdaptorNames().size() > 0) {
			adaptor = mgmt.getAdaptor(mgmt.getAdaptorNames().get(0));
		} else {
			throw new LLRPRuntimeException("no adaptor was found!!!");
		}
		try {
			// if everything is fine, try to open the repository (if defined).
			registerRepository(props);
		} catch (Exception e) {
			log.error("Could not initialize the repository - disabling it.");
		}
	}
	
	/**
	 * try to create a new repository and register it as a new message 
	 * handler. if the RO_ACCESS_REPORT is logged, register this repository 
	 * as well.
	 * @param props the properties file of the adapter.
	 * @throws Exception when something goes wrong.
	 */
	private void registerRepository(Properties props) throws Exception {
		if (null == props) throw new Exception("Empty properties.");
		repository = RepositoryFactory.create(props);
		
		if (null == repository) throw new Exception("Repository is null.");
		// create our message handler
		mgmt.registerFullHandler(new MessageHandler() {

			public void handle(String adapter, String reader, 
					LLRPMessage msg) {
				
				LLRPMessageItem item = new LLRPMessageItem();
				item.setAdapter(adapter);
				item.setReader(reader);
				
				String msgName = msg.getName();
				item.setMessageType(msgName);
				
				// if the message contains a "LLRPStatus" parameter, 
				//set the status code (otherwise use empty string)
				String statusCode = null;
				try {
					Method getLLRPStatusMethod = 
						msg.getClass().getMethod("getLLRPStatus", 
								new Class[0]);
					LLRPStatus status = 
						(LLRPStatus) getLLRPStatusMethod.invoke(
								msg, new Object[0]);
					statusCode = status.getStatusCode().toString();
				} catch (Exception e) {
					statusCode = "";
				} 
				item.setStatusCode(statusCode);
				
				// store the xml string to the repository
				try {
					item.setContent(msg.toXMLString());
				} catch (InvalidLLRPMessageException e) {
					log.debug("caught exception", e);
				}
				
				try {
					repository.put(item);
				} catch (Exception e) {
					// repository might be null
					log.debug("caught exception", e);
				}
			}
		});
		ROAccessReportsRepository roAcc = repository.getROAccessRepository();
		if ((null != roAcc) && (roAcc instanceof MessageHandler)) {
			// register the RO_ACCESS_REPORTS handler
			mgmt.registerPartialHandler(
					roAcc, RO_ACCESS_REPORT.class);
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
	 * @throws ImplementationException 
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
		log.error("An exception occured:", e);
	}

	public void handle(String adaptorName, String readerName,
			LLRPMessage message) {
		log.debug("placeholder. NEVER IMPLEMENTED.");		
	}


}
