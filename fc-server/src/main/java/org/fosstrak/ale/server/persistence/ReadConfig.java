package org.fosstrak.ale.server.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateSubscriptionExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ValidationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;


import com.orange.api.rfid.ale.server.gpio.GPIOControllerImpl;
import com.orange.api.rfid.ale.server.llrp.LLRPControllerManager;
import com.orange.api.rfid.ale.server.mobile.MobileDeviceImpl;
import com.orange.api.rfid.ale.server.subscriber.CaptureAppGatewayImpl;

/**
 * This class is called at the startup of tomcat and load all configuration on the the ALE.
 * @author benoit plomion Orange
 */
public class ReadConfig extends Config {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(ReadConfig.class.getName());
	
	/**
	 * this method is called at the startup tomcat by the PersistenceServlet and set the real path of the webapp
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
		
				
	}
	
	private static void readECSpecs() {
		
		LOG.info("start read and load all ecspec");
		
		ArrayList<String> filesNameList = ReadConfig.getFilesName(Config.getRealPathECSpecDir());
		
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
				ALE.define(specName, ecSpec);
				LOG.debug("load ecspec " + fileName);
			} catch (DuplicateNameExceptionResponse e) {
				LOG.error("error loading ecspec xml file " + fileName, e);	
			} catch (ECSpecValidationExceptionResponse e) {
				LOG.error("error ecspec validation for xml file " + fileName, e);	
			} catch (ImplementationExceptionResponse e) {
				LOG.error("error loading ecspec xml file " + fileName, e);	
			}
			
		}
		
		LOG.info("end read and load all ecspec");
			
	}
	
	private static void readECSpecsSubscriber() {
		
		LOG.info("start read and load all ecspec subscriber");
		
		ArrayList<String> filesNameList = ReadConfig.getFilesName(Config.getRealPathECSpecSubscriberDir());
		
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
					
					ALE.subscribe(specName, notificationURI);
					
					LOG.debug("add properties uri = " + notificationURI);					
					
				}
								
			} catch (NoSuchNameExceptionResponse e) {
				LOG.error("error loading ecspec subscriber properties file " + fileName + " with uri=" + notificationURI, e);
			} catch (InvalidURIExceptionResponse e) {
				LOG.error("error uri (" + notificationURI + ") subscriber properties file " + fileName, e);
			} catch (DuplicateSubscriptionExceptionResponse e) {
				LOG.error("error loading ecspec subscriber properties file " + fileName + " with uri=" + notificationURI, e);
			}
			
			LOG.debug("load ecspec subscriber " + fileName);
			
			
		}
		
		LOG.info("end read and load all ecspec subscriber");
			
	}
	
	private static void readLRSpecs() {
		
		LOG.info("start read and load all lrspec");
		
		ArrayList<String> filesNameList = ReadConfig.getFilesName(Config.getRealPathLRSpecDir());
		
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
					LogicalReaderManager.define(specName, lrSpec);
					LOG.debug("load lrspec " + fileName);
				} catch (DuplicateNameExceptionResponse e) {
					LOG.error("error loading lrspec xml file " + fileName, e);	
				} catch (ImplementationExceptionResponse e) {
					LOG.error("error loading lrspec xml file " + fileName, e);	
				} catch (ValidationExceptionResponse e) {
					LOG.error("error lrspec validation for xml file " + fileName, e);
				} catch (SecurityExceptionResponse e) {
					LOG.error("error lrspec security for xml file " + fileName, e);	
				}
				
		}
		
		LOG.info("end read and load all lrspec");
			
	}
	
	private static void readAddROSpecs() {
		
		LOG.info("start read and load all rospecs");
		
		ArrayList<String> filesNameList = ReadConfig.getFilesName(Config.getRealPathROSpecDir());
		
		for (String fileName : filesNameList) {
			
			String specName = fileName.substring(0,fileName.length()-5); // remove ".llrp"
			ADD_ROSPEC addRoSpec = null;
			
			try {
				
				String pathFile = Config.getRealPathROSpecDir() + fileName;
				LOG.debug("pathfile of add_rospec is " + pathFile);
				addRoSpec = com.orange.api.rfid.ale.util.DeserializerUtil.deserializeAddROSpec(pathFile);
				LOG.debug("ID of the deserialized add_rospec = " + addRoSpec.getROSpec().getROSpecID());
				
			} catch (FileNotFoundException e) {
				LOG.error("add_rospec file not found " + fileName, e);				
			} catch (Exception e) {
				LOG.error("error to read add_rospec file " + fileName, e);
			}
			
			LLRPControllerManager llrpControllerImpl = new LLRPControllerManager();
			LOG.debug("try to define add_rospec " + fileName + " with specName = " + specName);
			
			try {
				llrpControllerImpl.define(specName, addRoSpec);
			} catch (org.fosstrak.ale.wsdl.alelr.epcglobal.NoSuchNameExceptionResponse e) {
				LOG.error("error when trying to define add_rospec ", e);
			} catch(com.orange.api.rfid.ale.server.llrp.DuplicateNameExceptionResponse e) {
				LOG.error("error when trying to define add_rospec ", e);
			}
			
			LOG.debug("add_rospec defined  " + fileName);
			
		}
		
		LOG.info("end read and load all rospec");
	}
		
}
