package org.fosstrak.ale.server.llrp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.ALEApplicationContext;
import org.fosstrak.ale.server.readers.llrp.LLRPManager;
import org.fosstrak.llrp.adaptor.Adaptor;
import org.fosstrak.llrp.adaptor.AdaptorManagement;
import org.fosstrak.llrp.adaptor.exception.LLRPRuntimeException;
import org.fosstrak.llrp.client.LLRPExceptionHandler;
import org.fosstrak.llrp.client.LLRPExceptionHandlerTypeMap;
import org.fosstrak.llrp.client.LLRPMessageItem;
import org.fosstrak.llrp.client.MessageHandler;
import org.llrp.ltk.exceptions.InvalidLLRPMessageException;
import org.llrp.ltk.generated.enumerations.ConnectionAttemptStatusType;
import org.llrp.ltk.generated.enumerations.StatusCode;
import org.llrp.ltk.generated.messages.READER_EVENT_NOTIFICATION;
import org.llrp.ltk.generated.parameters.ConnectionAttemptEvent;
import org.llrp.ltk.generated.parameters.LLRPStatus;
import org.llrp.ltk.generated.parameters.ROSpec;
import org.llrp.ltk.generated.parameters.AccessSpec;
import org.llrp.ltk.types.LLRPMessage;

/**
 * ORANGE: This class initializes the llrp context and manage the LLRP messages.
 * 
 * @author wafa.soubra@orange.com
 */
public class AdaptorMgmt {
	
	/**	logger. */
	private static final Logger LOG = Logger.getLogger(AdaptorMgmt.class.getName());
	
	/** flag whether the adaptor management has been initialized or not. */
	private static boolean adaptorMgmtInitialized = false;
	
	/** the unique instance of the LLRP Adaptor Management. */
	private static AdaptorManagement llrpAdaptorMgmt = null; 
		
	/** the remote llrp adaptor. */
	private static Adaptor llrpRemoteAdaptor = null;
	
	/** enable or disable the redefine function to avoid adding several times the ROSpec. */
	private static boolean redefineStatus = false;
	
	/** a boolean to indicate if it is the first time we add an rospec. */
	private static boolean addFirstTime = true;
	
	/**
	 * Initialize the LLRP Context.
	 * Initialize & set the unique instance of the Adaptor Management.
	 * Initialize & set the Remote Adaptor.
	 */
	public static void  initializeLLRPContext () {
		LOG.debug("InitializeLLRPContext ...");
		String readConfig = null;
		String writeConfig = null;
		boolean commitChanges = false;
		if (!getAdaptorMgmtInitialized()) {
			llrpAdaptorMgmt = AdaptorManagement.getInstance();
			if (!llrpAdaptorMgmt.isInitialized()) {
				//musn't happen when we launch the fc-server in fosstrak
				try {
					llrpAdaptorMgmt.initialize(readConfig, writeConfig, commitChanges, null, null);
				} catch (LLRPRuntimeException e) {
					LOG.error("Error when trying to initialize LLRP Adaptor Management", e);
				}
			}
			setAdaptorMgmtInitialized(true);
			//llrpRemoteAdaptor = llrpAdaptorMgmt.getAdaptorNames().get(0);
			llrpRemoteAdaptor = ALEApplicationContext.getBean(LLRPManager.class).getAdaptor();
			// Register the handlers, so the adaptor management will
			// distribute the LLRP messages automatically and asynchronously. 
			llrpAdaptorMgmt.registerFullHandler(defineMessageHandler());
			llrpAdaptorMgmt.setExceptionHandler(defineExceptionHandler());
		}
		LOG.debug("End initializeLLRPContext.");
	}
	
	
	/**
	 * Send asynchronously the LLRP messages
	 * @param readerName the name of the reader
	 * @param msg the LLRP message to send to the reader
	 */
	public static void sendLLRPMessage(String readerName, LLRPMessage msg) {
		try {
			LOG.debug("Asynchrone sending of LLRP message " + msg.getName() + " to the reader " + readerName);
			llrpAdaptorMgmt.enqueueLLRPMessage(llrpRemoteAdaptor.getAdaptorName(), readerName, msg);
		} catch (LLRPRuntimeException e) {
			LOG.error("Couldn't send command " + msg.getName(), e);
		} catch (RemoteException e) {	
			LOG.error("Error occurs during LLRP transmission " + msg.getName(), e);
		}
	}
	
	/**
	 * Send synchronously the LLRP message.  
	 * @param readerName the name of the reader
	 * @param msg the LLRP message to send to the reader
	 */
	public static void sendSynchroneLLRPMessage(String readerName, LLRPMessage msg) {
		try {
			byte[] binaryEncodedMessage = msg.encodeBinary();
			LOG.debug("Synchrone sending of LLRP message " + msg.getName() + " to the reader " + readerName);
			llrpRemoteAdaptor.sendLLRPMessage(readerName, binaryEncodedMessage);
		} catch (InvalidLLRPMessageException e) {
			LOG.error("Invalid message ", e);
		} catch (LLRPRuntimeException e) {
			LOG.error("Couldn't send command " + msg.getName(), e);
		} catch (RemoteException e) {
			LOG.error("Error occurs during LLRP transmission " + msg.getName(), e);
		}
	}
		
	/**
	 *  Disconnect all readers & shutdown the Adaptor Management
	 *  We must call that function when we exit the platform.
	 */
	public static void shutdownLLRPContext() {
		LOG.debug("ShutdownLLRPContext and disconnecting all readers ...");
		if (adaptorMgmtInitialized) {
			llrpAdaptorMgmt.disconnectReaders();
			llrpAdaptorMgmt.shutdown();
			setAdaptorMgmtInitialized(false);
		}
		LOG.debug("End shutdownLLRPContext.");
	}
	
	/**
	 *  Test if the adaptor contains a physical reader
	 *  @param readerName the name of the reader
	 *  @return boolean true if the adaptor contains the reader. 
	 */
	public static boolean containsReader(String readerName) {
		boolean readerExist = false;
		try {
			readerExist = llrpRemoteAdaptor.containsReader(readerName);
		} catch (RemoteException e) {
			LOG.error("Error when testing if the remote adaptor contains the reader " + readerName, e);
		} 
		return readerExist;	
	}
		
	/**
	 *  Sets the value of the redefineStatus 
	 *  @param status if true enable the redefine function.
	 */
	public static void setRedefineStatus (boolean status) {
		redefineStatus= status;
	}
	
	/**
	 *  @return boolean true if the adaptor management is initialized.
	 */
	private static synchronized boolean getAdaptorMgmtInitialized() {
		return adaptorMgmtInitialized;
	}
	
	/**
	 *  @param status if true means the adaptor management is initialized
	 *  else shutdown the adaptor management.
	 */
	private static synchronized void setAdaptorMgmtInitialized(boolean status) {
		adaptorMgmtInitialized = status; 
	}
	
	/**
	 * Create a message handler to let the client receive all the incoming LLRP messages
	 * @return the message handler.
	 */
	private static MessageHandler defineMessageHandler () {
		MessageHandler msgHandler = new MessageHandler() {
		@SuppressWarnings("unchecked")
			public void handle(String adaptorName, String readerName, LLRPMessage msg) {
				LOG.debug (String.format("Received LLRP message msg name: %s from adapter: %s and  reader: %s",
							msg.getName(), adaptorName, readerName));	
				StatusCode statusCode = null;
				String statusStr ="";
				try {
					Method getLLRPStatusMethod = msg.getClass().getMethod("getLLRPStatus", new Class[0]);
					LLRPStatus status = (LLRPStatus) getLLRPStatusMethod.invoke(msg, new Object[0]);
					statusCode = status.getStatusCode();
					statusStr = statusCode.toString();
				} catch (Exception e) {
						//do Nothing 
				}
				// store the item in the repository which is the LOG file
				LLRPMessageItem item = new LLRPMessageItem();
				item.setAdapter(adaptorName);
				item.setReader(readerName);
				String msgName = msg.getName();
				item.setMessageType(msgName);
				item.setStatusCode(statusStr);
				try {
					item.setContent(msg.toXMLString());
					LOG.trace(item.prettyPrint());
					LOG.trace(item.getContent()); 
				} catch (InvalidLLRPMessageException e) {
					LOG.error("error when converting a LLRP msg into XML", e);
				}
				
				// connection between the client and the reader is established 
				if (item.getMessageType().equals("READER_EVENT_NOTIFICATION")) {
					READER_EVENT_NOTIFICATION msgEvent = (READER_EVENT_NOTIFICATION)msg;
					ConnectionAttemptEvent connectionEvent = msgEvent.getReaderEventNotificationData().getConnectionAttemptEvent();
					if (connectionEvent != null){
						ConnectionAttemptStatusType connectionStatus = connectionEvent.getStatus();
						String connectionStatusStr = connectionStatus.toString();
						if (connectionStatus.getValue(connectionStatusStr) == ConnectionAttemptStatusType.Success) {
							LOG.debug("Connection is established");
							ALEApplicationContext.getBean(LLRPControllerManager.class).setReaderConnected(readerName, true);
						}
					}
				}
			
				// enable the Redefine Status when the add_rospec is done
				if (item.getMessageType().equals("ADD_ROSPEC_RESPONSE") && (statusStr != "") &&
					(statusCode.getValue(statusStr) == StatusCode.M_Success)) {
						LOG.debug("Redefine is enabled");
						addFirstTime=false;
						setRedefineStatus(true);
					}
				
				// added to test sending commands 
				if (item.getMessageType().equals("ADD_ACCESSSPEC_RESPONSE") && (statusStr != "")) {
					LOG.debug("Status of AccesSpec Response = " + statusCode.getValue(statusStr));
					}		
				
				if (item.getMessageType().equals("ENABLE_ACCESSSPEC_RESPONSE") && (statusStr != "")) {
					LOG.debug("Status of Enable AccesSpec Response = " + statusCode.getValue(statusStr));
					}		
				
				if (item.getMessageType().equals("ERROR_MESSAGE") && (statusStr != "")) {
					LOG.debug("Status of Error Message = " + statusCode.getValue(statusStr));
					}	
				
				try {
					if (item.getMessageType().equals("GET_ACCESSSPECS_RESPONSE")) { 					
						Method getAccessSpecList = msg.getClass().getMethod("getAccessSpecList", new Class[0]);
						List<AccessSpec> listAccessSpec = (List<AccessSpec>) getAccessSpecList.invoke(msg, new Object[0]);	
						for (AccessSpec access : listAccessSpec) {
							LOG.debug ("length of acccesspecList = " + listAccessSpec.size());
							LOG.debug ("access id is = " + access.getAccessSpecID());
						}
					}
				} catch (NoSuchMethodException e) {
					LOG.error ("NoSuchMethodException in GET_ACCESSSPECS_RESPONSE " + e);
				} catch (InvocationTargetException e) {
					LOG.error ("InvocationTargetException in GET_ACCESSSPECS_RESPONSE " + e);
				} catch (IllegalAccessException e) {
					LOG.error ("IllegalAccessException in GET_ACCESSSPECS_RESPONSE " + e);
				}
				
	
				// if reader was deconnected, redefine the ROSpec and disable the Redefine Status
				try {
					if (item.getMessageType().equals("GET_ROSPECS_RESPONSE")) { 					
						Method getROSpecList = msg.getClass().getMethod("getROSpecList", new Class[0]);
						List<ROSpec> listRoSpec = (List<ROSpec>) getROSpecList.invoke(msg, new Object[0]);	
						if (listRoSpec == null || listRoSpec.isEmpty()) {
							if (redefineStatus) {
								ALEApplicationContext.getBean(LLRPControllerManager.class).redefine (readerName);
								setRedefineStatus (false);
							} else {
								// This is the case where we are in the mode waitForConnection = false.
								// So we send several GET_ROSPECS messages. We do the ADD_ROSPEC one time and just the first time.
								if (addFirstTime) {
									ALEApplicationContext.getBean(LLRPControllerManager.class).redefine (readerName);	
									addFirstTime = false;
								} else {
									// This is the case where : 
									// - we never get the "add_rospec_response" or we are still waiting for the 
									//   "add_rospec_response", so the redefineSatus is false
									// - and the reader has been disconnected meanwhile, so the rospec list is empty.
									// If we never get the response, the redefine must do it manually.
									LOG.warn ("Wait to redefine the ROSpec automatically ... If not, do it manually.");
								}
							}
						}
					}
				} catch (NoSuchMethodException e) {
					LOG.error ("NoSuchMethodException in GET_ROSPECS_RESPONSE " + e);
				} catch (InvocationTargetException e) {
					LOG.error ("InvocationTargetException in GET_ROSPECS_RESPONSE " + e);
				} catch (IllegalAccessException e) {
					LOG.error ("IllegalAccessException in GET_ROSPECS_RESPONSE " + e);
				}
				// TODO : 1- Check if an ROSpec is already defined and disabled on the reader 
				// TODO: 2- Check if an ROSpec is already defined and enabled on the reader 
				// when launching the platform, we receive RO_ACCESS_REPORT
				// if (item.getMessageType().equals("RO_ACCESS_REPORT")) { 
				//		LOG.debug(" ROSpec already defined and running in the reader.");
				//	}
			}
		};
		return msgHandler;
	}
	
	/**
	 * Create an exception handler to receive asynchronous exceptions from the adaptors and the readers.
	 * @return the exception handler.
	 */
	private static LLRPExceptionHandler defineExceptionHandler () {
		LLRPExceptionHandler exceptionHandler = new LLRPExceptionHandler() {
			public void postExceptionToGUI (LLRPExceptionHandlerTypeMap exceptionType, LLRPRuntimeException e, 
											String adaptorName, String readerName) {
				LOG.warn(String.format("Received LLRP Exception %s from adapter: %s and reader: %s",
					 	exceptionType.toString(), adaptorName, readerName));
			}	
		};
		return exceptionHandler;
	}	
	
}
