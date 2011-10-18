package org.fosstrak.ale.server.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class is used to removed configuration persistence depending of action done from the ale
 * @author benoit.plomion@orange.com
 */
public class RemoveConfig extends Config {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(RemoveConfig.class.getName());
	
	/**
	 * this method remove on the path of the webapp the ECSpec file which is undefine
	 * @param specName
	 */
	public static void removeECSpec(String specName) {
		
		LOG.info("start remove file for ecspec: " + specName + ".xml");
		
		String path = Config.getRealPathECSpecDir();
		String fileName = specName + ".xml";
			 
		if (RemoveConfig.fileExist(fileName, path)) {
			
				LOG.debug("try to remove file for ecspec: " + fileName);
				
				RemoveConfig.removeFile(path, fileName);				
				
				LOG.info("ecspec file " + fileName + " removed on path: " + path);	
					
		} else {			
			LOG.info("ecspec file " + fileName + " does not exist on path: " + path);			
		}
		
	}
	
	/**
	 *  this method remove on the path of the webapp the ECSpec subscriber wich is unsubscribe. if the file is empty
	 * @param specName
	 */
	public static void removeECSpecSubscriber(String specName, String notificationURI) {
		
		LOG.info("start remove ecspec subscriber from file: " + specName + ".properties");
		
		String path = Config.getRealPathECSpecSubscriberDir();
		String fileName = specName + ".properties";
		
		if (fileExist(fileName, path)) {
			
			LOG.debug("file " + specName + ".properties exist");
			
			FileInputStream fileInputStream = null;
			Properties properties = new Properties();	
			
			// load properties subscriber file
			try {			
				fileInputStream  = new FileInputStream(path + fileName);	
				properties.load(fileInputStream);
				LOG.debug("load file " + specName + ".properties exist in properties object");
			} catch (IOException e) {
				LOG.error("error loading ecspec subscriber properties file " + fileName, e);
			}
			
			// properties subscriber file are loaded
			if (fileInputStream != null) {
			
				Iterator<Object> it = properties.keySet().iterator();
							
				// properties file is empty => remove file
				if (!it.hasNext()) {
								
					LOG.debug("properties file empty => remove file = " + fileName);					
					try {
						fileInputStream.close();
					} catch (IOException e) {
						LOG.error("error to close ecspec subscriber properties empty file " + fileName, e);
					}
					removeFile(path, fileName);					
						
				// properties file is not empty => delete uri corresponding
				} else {
				
					boolean uriExist = false;
				
					// get list of uri of the properties file and check if the uri to delete exist
					
					ArrayList<String> listURI = new ArrayList<String>();
					
					// get list of uri
					while (it.hasNext()) {
						
						String propertyName = (String)it.next();
						String propertyValue = properties.getProperty(propertyName);
											
						if (!propertyValue.equalsIgnoreCase(notificationURI.trim())) {						
							listURI.add(propertyValue);						
						} else {											
							LOG.debug("uri to be deleted exist in the properties file : " + propertyValue);
							uriExist = true;
						}
							
					}
						
					LOG.debug("uri list contain in the properties file " + fileName + " is: " + listURI);
					
					// uri to be deleted exist -> then modify file (remove -> create)
					if (uriExist) {
						
						// delete file always and create after									
						try {
							fileInputStream.close();
						} catch (IOException e) {
							LOG.error("error to close ecspec subscriber properties file " + fileName, e);
						}
						removeFile(path, fileName);					
						
						// create again file if other uri than the uri deleted
						if (listURI.size() > 0) {
														
							try {
								
								LOG.debug("create subscriber properties file " + fileName + ":");
								
								// create the file
								OutputStream outputStream = new FileOutputStream(path + fileName);					
								properties = new Properties();
								int i = 1;
								
								// add uri list
								for (String uri : listURI) {						
									properties.setProperty("uri_" + i, uri);
									LOG.debug("add uri_" + i + " = " + uri);
									i++;									
								}
								
								// close file
								properties.store(outputStream, "");
								outputStream.flush();
								outputStream.close();
								
							} catch (FileNotFoundException e) {
								LOG.error("error when create again ecspec subcriber properties  file " + fileName, e);
							}  catch (IOException e) {
								LOG.error("error when create again ecspec subcriber properties  file " + fileName, e);
							}
							
						} else {
							LOG.debug("subscriber properties file " + fileName + " contained only the uri deleted => delete file");
						}
						
					} else {
						LOG.debug("subscriber properties file does not contain the uri " + notificationURI + " than to be deleted");
					}
					
				}
							
			}
			
		}
		
		LOG.info("end remove ecspec subscriber from file: " + specName + ".properties");
		
	}
	
	/**
	 * this method remove on the path of the webapp each LRSpec which is undefine
	 * @param specName
	 */
	public static void removeLRSpec(String specName) {
		
		LOG.info("start remove file for lrspec: " + specName + ".xml");
		
		String path = Config.getRealPathLRSpecDir();
		String fileName = specName + ".xml";
			 
		if (RemoveConfig.fileExist(fileName, path)) {
			
				LOG.debug("try to remove file for lrspec: " + fileName);
				
				RemoveConfig.removeFile(path, fileName);				
				
				LOG.info("lrspec file " + fileName + " removed on path: " + path);	
					
		} else {			
			LOG.info("lrspec file " + fileName + " does not exist on path: " + path);			
		}
		
	}
		
	/**
	 * this method remove on the path of the webapp each ROSpec which is undefine
	 * @param specName
	 */
	public static void removeROSpec(String lrSpecName) {
		
		LOG.info("start remove file for rospec: " + lrSpecName + ".llrp");
		
		String path = Config.getRealPathROSpecDir();
		String fileName = lrSpecName + ".llrp";
			 
		if (RemoveConfig.fileExist(fileName, path)) {
			
				LOG.debug("try to remove file for rospec: " + fileName);
				
				RemoveConfig.removeFile(path, fileName);				
				
				LOG.info("rospec file " + fileName + " removed on path: " + path);	
					
		} else {			
			LOG.info("rospec file " + fileName + " does not exist on path: " + path);			
		}
		
	}
		
}
