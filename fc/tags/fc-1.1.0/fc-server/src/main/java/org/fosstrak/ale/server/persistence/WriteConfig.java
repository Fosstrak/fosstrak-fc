package org.fosstrak.ale.server.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC;

/**
 * This class write all configuration file on the real path of the webapp
 * @author benoit.plomion@orange.com
 */
public class WriteConfig extends Config {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(WriteConfig.class.getName());
		
	/**
	 * this method write on the path of the webapp each ECSpec loaded in the ALE.
	 * @param specName
	 * @param spec
	 */
	public static void writeECSpec(String specName, ECSpec spec) {
		
		WriteConfig.writeECSpec(specName, spec, Config.getRealPathECSpecDir());
	}
	
	private static void writeECSpec(String name, ECSpec spec, String path) {
		
		String fileName = name + ".xml";
			 
		if (!WriteConfig.fileExist(fileName, path)) {
			
			try {
				
				boolean dirCreated = new File(path).mkdirs();
				
				if (!dirCreated) {
					LOG.info("cannot create directories or directories already exist : " + path);
				}		
				
				LOG.debug("try to create file for ecspec: " + fileName);
				FileOutputStream fileOutputStream = new FileOutputStream(path + fileName);
				SerializerUtil.serializeECSpec(spec, fileOutputStream);
				LOG.info("ecspec file " + fileName + " created on path: " + path);	
								
			} catch (FileNotFoundException e) {
				LOG.error("error create ecspec file: " + path + fileName, e);		
			} catch (IOException e) {			
				LOG.error("error serialize ecspec file: " + path + fileName, e);
			} catch (Exception e) {
				LOG.error("error ecspec file: " + path + fileName, e);
			}
		
		} else {			
			LOG.info("ecspec file " + fileName + " already exist on path: " + path);			
		}
		
	}
	
	/**
	 *  this method write on the path of the webapp each ECSpec subscriber loaded in the ALE.
	 * @param specName
	 * @param notificationURI
	 */
	public static void writeECSpecSubscriber(String specName, String notificationURI) {
		
		LOG.info("start create file for ecspec subscriber: " + specName + ".properties");
		
		String path = Config.getRealPathECSpecSubscriberDir();
		String fileName = specName + ".properties";
		
		// create file and directory
		if (!WriteConfig.fileExist(fileName, path)) {
			
			boolean dirCreated = new File(path).mkdirs();
			
			if (!dirCreated) {
				LOG.info("cannot create directories or directories already exist : " + path);
			}	
			
			try {
				
				LOG.debug("create properties file for ecspec subscriber: " + specName + ".properties:" + path);
				
				OutputStream outputStream = new FileOutputStream(path + fileName);
				Properties properties = new Properties();				
				properties.store(outputStream, "");
				outputStream.flush();
				outputStream.close();
				
			} catch (IOException e) {
				LOG.error("error create ecspec subscriber file: " + path + fileName, e);		
			}
			
			
		}
				
		// write in the properties file
		try {			
			
			LOG.debug("load properties file for ecspec subscriber: " + specName + ".properties: " + path);
						
			Properties properties = new Properties();	
			FileInputStream fileInputStream = new FileInputStream(path + fileName);	
			properties.load(fileInputStream);
			
			Iterator<Object> it = properties.keySet().iterator();
			int i = 1;
			
			// properties file is empty
			if (!it.hasNext()) {
							
				LOG.debug("properties file empty => add properties uri_" + i + " = " + notificationURI);					
				properties.setProperty("uri_" + i, notificationURI);
				
			// properties file is not empty
			} else {
			
				boolean uriExist = false;
				
				while (it.hasNext()) {
					
					String propertyName = (String)it.next();
					String propertyValue = properties.getProperty(propertyName);
					
					if (propertyValue.equalsIgnoreCase(notificationURI.trim())) {
						LOG.debug("uri already exist => don t add properties uri_" + i + " = " + notificationURI);
						uriExist = true;
						break;
					}
					
					i++;				
					
				}
				
				if (!uriExist) {
					LOG.debug("add properties uri_" + i + " = " + notificationURI);					
					properties.setProperty("uri_" + i, notificationURI);
				}
				
			}
			LOG.debug("save properties file for ecspec subscriber: " + specName + ".properties: " + path);
			
			OutputStream outputStream = new FileOutputStream(path + fileName);				
			properties.store(outputStream, "");
			outputStream.flush();
			outputStream.close();			
			
		} catch (FileNotFoundException e) {						
			LOG.error("error read ecspec subscriber file: " + path + fileName, e);				
		} catch (IOException e) {
			LOG.error("error read ecspec subscriber file: " + path + fileName, e);			
		}	
			
	}
	
	/**
	 * this method write on the path of the webapp each LRSpec loaded in the ALE.
	 * @param specName
	 * @param spec
	 */
	public static void writeLRSpec(String specName, LRSpec spec) {
		
		LOG.info("start create file for lrspec: " + specName + ".xml");
		
		String path = Config.getRealPathLRSpecDir();
		String fileName = specName + ".xml";
			 
		if (!WriteConfig.fileExist(fileName, path)) {
			
			try {
				
				boolean dirCreated = new File(path).mkdirs();
				
				if (!dirCreated) {
					LOG.info("cannot create directories or directories already exist : " + path);
				}		
				
				LOG.debug("try to create file for lrspec: " + fileName);
				SerializerUtil.serializeLRSpec(spec, path + fileName, false);
				LOG.info("lrspec file " + fileName + " created on path: " + path);	
								
			} catch (FileNotFoundException e) {
				LOG.error("error create lrspec file: " + path + fileName, e);		
			} catch (IOException e) {			
				LOG.error("error serialize lrspec file: " + path + fileName, e);
			}  catch (Exception e) {
				LOG.error("error lrspec file: " + path + fileName, e);
			}
		
		} else {			
			LOG.info("lrspec file " + fileName + " already exist on path: " + path);			
		}
		
	}
	
	/**
	 * this method write on the path of the webapp the ADD_ROSPEC loaded in the ALE.
	 * @param specName the logical reader name
	 * @param addRoSpec the ADD_ROSPEC to save
	 */
	public static void writeAddROSpec(String specName, ADD_ROSPEC addRoSpec) {
		LOG.info("start write file for add_rospec: " + specName + ".llrp");
		
		String path = Config.getRealPathROSpecDir();
		String fileName = specName + ".llrp";
		if (!WriteConfig.fileExist(fileName, path)) {
			try {
				boolean dirCreated = new File(path).mkdirs();
				if (!dirCreated) {
					LOG.info("cannot create directories or directories already exist : " + path);
				}		
				LOG.debug("try to create file for add_rospec: " + fileName);
				SerializerUtil.serializeAddROSpec(addRoSpec, path + fileName);
				LOG.info("add_rospec file " + fileName + " created on path: " + path);	
								
			} catch (FileNotFoundException e) {
				LOG.error("error create rospec file: " + path + fileName, e);		
			} catch (IOException e) {			
				LOG.error("error serialize rospec file: " + path + fileName, e);
			}
		
		} else {			
			LOG.info("rospec file " + fileName + " already exist on path: " + path);			
		}
		
	}
	
	
	/**
	 * this method write on the path of the webapp the ADD_ROSPEC loaded in the ALE.
	 * @param specName the logical reader name
	 * @param addAccessSpec the ADD_ACCESSSPEC to save
	 */
	public static void writeAddAccessSpec(String specName, ADD_ACCESSSPEC addAccessSpec) {
		LOG.info("start write file for add_accessspec: " + specName + ".llrp");
		
		String path = Config.getRealPathAccessSpecDir();
		String fileName = specName + ".llrp";
		if (!WriteConfig.fileExist(fileName, path)) {
			try {
				boolean dirCreated = new File(path).mkdirs();
				if (!dirCreated) {
					LOG.info("cannot create directories or directories already exist : " + path);
				}		
				LOG.debug("try to create file for add_accessspec: " + fileName);
				SerializerUtil.serializeAddAccessSpec(addAccessSpec, path + fileName);
				LOG.info("add_accessspec file " + fileName + " created on path: " + path);	
								
			} catch (FileNotFoundException e) {
				LOG.error("error create accessspec file: " + path + fileName, e);		
			} catch (IOException e) {			
				LOG.error("error serialize accessspec file: " + path + fileName, e);
			}
		
		} else {			
			LOG.info("accessspec file " + fileName + " already exist on path: " + path);			
		}
		
	}
}
