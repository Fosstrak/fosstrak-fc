package org.fosstrak.ale.server.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.exception.SecurityException;
import org.fosstrak.ale.exception.ValidationException;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.ALEApplicationContext;
import org.fosstrak.ale.server.llrp.LLRPControllerManager;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;

/**
 * This class is called at the startup of tomcat and load all configuration on the the ALE.
 * @author benoit plomion Orange
 * 
 * FIXME: I need some serious refactoring...
 */
public class ReadConfig {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(ReadConfig.class.getName());
	
	/**
	 * this method is called at the startup of the application server by the PersistenceServlet and set the real path of the webapp
	 * @param realPathWebapp
	 */
	public static void initialize() {
				
		//ORANGE: persistence init load of configuration
		try {
			ReadConfig.readLRSpecs();
		} catch (Exception e) {
			LOG.error("readLRSpecs error", e);
		}
		
		try {
			ReadConfig.readECSpecs();
		} catch (Exception e) {
			LOG.error("readECSpecs error", e);
		}
			
		try {
			ReadConfig.readECSpecsSubscriber();
		} catch (Exception e) {
			LOG.error("& error", e);
		}
		
		try {
			ReadConfig.readAddROSpecs();
		} catch (Exception e) {
			LOG.error("readAddROSpecs error", e);
		}
		
		try {
			ReadConfig.readAddAccessSpecs();
		} catch (Exception e) {
			LOG.error("readAddACCESSSpecs error", e);
		}	
	}
	
	private static void readECSpecs() {
		
		LOG.info("start read and load all ecspec");
		
		ArrayList<String> filesNameList = Config.getFilesName(Config.getRealPathECSpecDir(), Config.FILE_ENDING_XML);
		
		for (String fileName : filesNameList){
			
			String specName = fileName.substring(0,fileName.length()-4); // remove ".xml"
			ECSpec ecSpec = null;
			
			try {
				LOG.debug("try to read ecspec " + fileName);
				ecSpec = DeserializerUtil.deserializeECSpec(Config.getRealPathECSpecDir() + fileName);
				LOG.debug("read ecspec " + fileName);
			} catch (FileNotFoundException e) {
				LOG.error("not found ecspec xml file " + fileName, e);				
			} catch (Exception e) {
				LOG.error("error to reade ecspec xml file " + fileName, e);
			}			
			
			try {
				LOG.debug("try to load ecspec " + fileName + " with specName = " + specName);
				ALEApplicationContext.getBean(ALE.class).define(specName, ecSpec);
				LOG.debug("load ecspec " + fileName);
			} catch (DuplicateNameException e) {
				LOG.error("error loading ecspec xml file " + fileName, e);	
			} catch (ECSpecValidationException e) {
				LOG.error("error ecspec validation for xml file " + fileName, e);	
			} catch (ImplementationException e) {
				LOG.error("error loading ecspec xml file " + fileName, e);	
			}
			
		}
		
		LOG.info("end read and load all ecspec");
			
	}
	
	private static void readECSpecsSubscriber() {
		
		LOG.info("start read and load all ecspec subscriber");
		
		ArrayList<String> filesNameList = Config.getFilesName(Config.getRealPathECSpecSubscriberDir(), Config.FILE_ENDING_PROPERTES);
		
		for (String fileName : filesNameList){
			
			String specName = fileName.substring(0,fileName.length()-11); // remove ".properties"
			Properties properties = new Properties();
			
			try {
				LOG.debug("try to read ecspec subscriber " + fileName);
				FileInputStream fileInputStream = new FileInputStream(Config.getRealPathECSpecSubscriberDir() + fileName);	
				properties.load(fileInputStream);				
				LOG.debug("read ecspec subscriber " + fileName);
			} catch (FileNotFoundException e) {
				LOG.error("not found ecspec subscriber properties file " + fileName, e);
				break;
			} catch (Exception e) {
				LOG.error("error to reade ecspec subscriber properties file " + fileName, e);
				break;
			}			
						
			LOG.debug("try to load ecspec subscriber " + fileName + " with specName = " + specName);
				
			String notificationURI = "";
			
			try {
				
				Iterator<Object> it = properties.keySet().iterator();
				
				while (it.hasNext()) {
					
					String propertyName = (String)it.next();
					notificationURI = properties.getProperty(propertyName);
					
					ALEApplicationContext.getBean(ALE.class).subscribe(specName, notificationURI);
					
					LOG.debug("add properties uri = " + notificationURI);					
					
				}
								
			} catch (NoSuchNameException e) {
				LOG.error("error loading ecspec subscriber properties file " + fileName + " with uri=" + notificationURI, e);
			} catch (InvalidURIException e) {
				LOG.error("error uri (" + notificationURI + ") subscriber properties file " + fileName, e);
			} catch (DuplicateSubscriptionException e) {
				LOG.error("error loading ecspec subscriber properties file " + fileName + " with uri=" + notificationURI, e);
			}
			
			LOG.debug("load ecspec subscriber " + fileName);
			
			
		}
		
		LOG.info("end read and load all ecspec subscriber");
			
	}
	
	private static void readLRSpecs() {
		
		LOG.info("start read and load all lrspec");
		
		ArrayList<String> filesNameList = Config.getFilesName(Config.getRealPathLRSpecDir(), Config.FILE_ENDING_XML);
		
		for (String fileName : filesNameList){
			
			String specName = fileName.substring(0,fileName.length()-4); // remove ".xml"
			LRSpec lrSpec = null;
			
				try {
					LOG.debug("try to read lrspec " + fileName);
					lrSpec = DeserializerUtil.deserializeLRSpec(Config.getRealPathLRSpecDir() + fileName);
					LOG.debug("read lrspec " + fileName);
				} catch (FileNotFoundException e) {
					LOG.error("not found lrspec xml file " + fileName, e);				
				} catch (Exception e) {
					LOG.error("error to reade lrspec xml file " + fileName, e);
				}
				
				try {
					LOG.debug("try to load lrspec " + fileName + " with specName = " + specName);
					ALEApplicationContext.getBean(LogicalReaderManager.class).define(specName, lrSpec);
					LOG.debug("load lrspec " + fileName);
				} catch (DuplicateNameException e) {
					LOG.error("error loading lrspec xml file " + fileName, e);	
				} catch (ImplementationException e) {
					LOG.error("error loading lrspec xml file " + fileName, e);	
				} catch (ValidationException e) {
					LOG.error("error lrspec validation for xml file " + fileName, e);
				} catch (SecurityException e) {
					LOG.error("error lrspec security for xml file " + fileName, e);	
				}
				
		}
		
		LOG.info("end read and load all lrspec");
			
	}
	
	private static void readAddROSpecs() {
		
		LOG.info("start read and load all rospecs");
		
		ArrayList<String> filesNameList = Config.getFilesName(Config.getRealPathROSpecDir(), Config.FILE_ENDING_LLRP);
		
		for (String fileName : filesNameList) {
			
			String specName = fileName.substring(0,fileName.length()-5); // remove ".llrp"
			ADD_ROSPEC addRoSpec = null;
			
			try {
				
				String pathFile = Config.getRealPathROSpecDir() + fileName;
				LOG.debug("pathfile of add_rospec is " + pathFile);
				addRoSpec = org.fosstrak.ale.util.DeserializerUtil.deserializeAddROSpec(pathFile);
				LOG.debug("ID of the deserialized add_rospec = " + addRoSpec.getROSpec().getROSpecID());
				
			} catch (FileNotFoundException e) {
				LOG.error("add_rospec file not found " + fileName, e);				
			} catch (Exception e) {
				LOG.error("error to read add_rospec file " + fileName, e);
			}
			
			LLRPControllerManager llrpControllerManager = new LLRPControllerManager();
			LOG.debug("try to define add_rospec " + fileName + " with specName = " + specName);
			
			try {
				llrpControllerManager.define(specName, addRoSpec);
			} catch (org.fosstrak.ale.exception.NoSuchNameException e) {
				LOG.error("error when trying to define add_rospec ", e);
			} catch(DuplicateNameException e) {
				LOG.error("error when trying to define add_rospec ", e);
			}
			
			LOG.debug("add_rospec defined  " + fileName);
			
		}
		
		LOG.info("end read and load all rospec");
	}
		
	
	
	private static void readAddAccessSpecs() {
		LOG.info("start read and load all accessspecs");
		ArrayList<String> filesNameList = Config.getFilesName(Config.getRealPathAccessSpecDir(), Config.FILE_ENDING_LLRP);
		for (String fileName : filesNameList) {
			String specName = fileName.substring(0,fileName.length()-5); // remove ".llrp"
			ADD_ACCESSSPEC addAccessSpec = null;
			try {
				String pathFile = Config.getRealPathAccessSpecDir() + fileName;
				LOG.debug("pathfile of add_accessspec is " + pathFile);
				addAccessSpec = org.fosstrak.ale.util.DeserializerUtil.deserializeAddAccessSpec(pathFile);
				LOG.debug("ID of the deserialized add_accessspec = " + addAccessSpec.getAccessSpec().getAccessSpecID());
				} catch (FileNotFoundException e) {
					LOG.error("add_accessspec file not found " + fileName, e);				
				} catch (Exception e) {
					LOG.error("error to read add_accessspec file " + fileName, e);
				}
				
				LLRPControllerManager llrpControllerManager = new LLRPControllerManager();
				LOG.debug("try to define add_accessspec " + fileName + " with specName = " + specName);
				try {
					// FIXME: there seems to be a mess between static parts and non static parts in the controller manager.
					llrpControllerManager.defineAccessSpec(specName, addAccessSpec);
				} catch (NoSuchNameException e) {
					LOG.error("error when trying to define add_accessspec ", e);
				} catch (DuplicateNameException e) {
					LOG.error("error when trying to define add_acccessspec ", e);
				}
				LOG.debug("add_acccessspec defined  " + fileName);
		}
		LOG.info("end read and load all acccessspec");
	}

	
}
