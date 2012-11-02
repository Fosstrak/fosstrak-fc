/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Fosstrak (www.fosstrak.org).
 *
 * Fosstrak is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Fosstrak is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.server.persistence.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.fosstrak.ale.server.llrp.LLRPControllerManager;
import org.fosstrak.ale.server.persistence.ReadConfig;
import org.fosstrak.ale.server.persistence.type.PersistenceConfig;
import org.fosstrak.ale.server.persistence.util.FileUtils;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * reference implementation of the persistence read API.
 * @author swieland
 * @author benoit.plomion@orange.com
 *
 */
@Repository("readConfigImpl")
public class ReadConfigImpl implements ReadConfig {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(ReadConfigImpl.class.getName());

	// autowired
	private FileUtils fileUtils;

	// autowired
	private PersistenceConfig persistenceConfig;

	// autowired
	private ALE ale;
	
	// autowired
	private LogicalReaderManager logicalReaderManager;

	// autowired
	private LLRPControllerManager llrpControllerManager;

	@Override
	public void init() {			
		//ORANGE: persistence init load of configuration
		try {
			readLRSpecs();
		} catch (Exception e) {
			LOG.error("readLRSpecs error", e);
		}
		
		try {
			readECSpecs();
		} catch (Exception e) {
			LOG.error("readECSpecs error", e);
		}
			
		try {
			readECSpecsSubscriber();
		} catch (Exception e) {
			LOG.error("& error", e);
		}
		
		try {
			readAddROSpecs();
		} catch (Exception e) {
			LOG.error("readAddROSpecs error", e);
		}
		
		try {
			readAddAccessSpecs();
		} catch (Exception e) {
			LOG.error("readAddACCESSSpecs error", e);
		}
	}

	@Override
	public void readECSpecs() {
		LOG.debug("start read and load all ecspec");
		
		List<String> fileNames = fileUtils.getFilesName(persistenceConfig.getRealPathECSpecDir(), FileUtils.FILE_ENDING_XML);
		Map<String, ECSpec> ecSpecs = deserializeECSpecs(fileNames);
		defineECSpecs(ecSpecs);
		
		LOG.debug("end read and load all ecspec");
	}

	/**
	 * deserialize the persistent ECSpecs from the given file names.
	 * @param fileNames the filenames where to get the ECSpecs from.
	 * @return a Map of ECSpecs hashed by their specification name.
	 */
	private Map<String, ECSpec> deserializeECSpecs(List<String> fileNames) {
		Map<String, ECSpec> ecSpecs = new HashMap<String, ECSpec> ();
		for (String fileName : fileNames) {
			final String specName = fileName.substring(0, fileName.length() - 4); // remove ".xml"
			try {
				LOG.debug("try to read ecspec " + fileName);
				ECSpec ecSpec = DeserializerUtil.deserializeECSpec(persistenceConfig.getRealPathECSpecDir() + fileName);
				ecSpecs.put(specName, ecSpec);
				LOG.debug("read ecspec " + fileName);
			} catch (FileNotFoundException e) {
				LOG.error("ecspec xml file not found:" + fileName, e);				
			} catch (Exception e) {
				LOG.error("error while reading ecspec xml file " + fileName, e);
			}
			
		}
		return ecSpecs;
	}
	
	/**
	 * define the ECSpecs via the ALE.
	 * @param ecSpecs the ECSpecs as a Map hashed by their specification name.
	 */
	private void defineECSpecs(Map<String, ECSpec> ecSpecs) {
		for (Map.Entry<String, ECSpec> entry : ecSpecs.entrySet()) {
			try {
				LOG.debug(String.format("Loading ECSpec %s ...", entry.getKey()));
				ale.define(entry.getKey(), entry.getValue());
				LOG.debug(String.format(" ... loading ECSpec %s done", entry.getKey()));
			} catch (DuplicateNameException e) {
				LOG.error(String.format("ECSpec %s already defined.", entry.getKey()), e);
			} catch (ECSpecValidationException e) {
				LOG.error(String.format("ECSpec %s is not valid.", entry.getKey()), e);
			} catch (ImplementationException e) {
				LOG.error("caught an implementation exception - continuing", e);
			}
		}
	}

	@Override
	public void readECSpecsSubscriber() {		
		LOG.debug("start read and load all ecspec subscriber");
		
		List<String> fileNames = fileUtils.getFilesName(persistenceConfig.getRealPathECSpecSubscriberDir(), FileUtils.FILE_ENDING_PROPERTES);
		Map<String, Properties> properties = getProperties(fileNames);
		subscribeSubscribers(properties);		
		LOG.debug("end read and load all ecspec subscriber");
	}

	/**
	 * load all the properties files from the given file names.
	 * @param fileNames the filenames where to get the properties from.
	 * @return a Map of Properties hashed by the name of the ec spec where to subscribe to.
	 */
	private Map<String, Properties> getProperties(List<String> fileNames) {
		Map<String, Properties> properties = new HashMap<String, Properties> ();
		for (String fileName : fileNames) {
			try {
				LOG.debug("try to read ecspec subscriber " + fileName);
				final String specName = fileName.substring(0,fileName.length()-11); // remove ".properties"
				
				Properties pFile = new Properties();
				InputStream ioStream = new FileInputStream(persistenceConfig.getRealPathECSpecSubscriberDir() + fileName);
				pFile.load(ioStream);
				ioStream.close();
				
				properties.put(specName, pFile);
				LOG.debug("read ecspec subscribers " + fileName);
			} catch (FileNotFoundException e) {
				LOG.error("ecspec subscriber properties file not found:" + fileName, e);
			} catch (Exception e) {
				LOG.error("error to read ecspec subscriber properties file " + fileName, e);
			}
		}
		return properties;
	}

	/**
	 * subscribe the subscribers on the ec spec.
	 * @param properties the properties holding the subscribers urls. the hash key of the input encodes the ec spec where to subscribe to.
	 */
	private void subscribeSubscribers(Map<String, Properties> properties) {
		for (Map.Entry<String, Properties> entry : properties.entrySet()) {
			try {
				final String specName = entry.getKey();
				final Properties props = entry.getValue();
				
				LOG.debug("try to define subscribers for specName " + specName);
				for (Map.Entry<Object, Object> propsEntries : props.entrySet()) {
					try {
						final String notificationURI = (String) propsEntries.getValue();
						LOG.debug("defining notification URI: " + notificationURI);					
						ale.subscribe(specName, notificationURI); 
					} catch (InvalidURIException e) {
						LOG.error("Illegal Notification URI", e);
					} catch (DuplicateSubscriptionException e) {
						LOG.error("Notification URI is already defined.", e);
					}
				}								
			} catch (NoSuchNameException e) {
				LOG.error("ECSpec does not exist", e);
			}
		}
	}

	@Override
	public void readLRSpecs() {		
		LOG.debug("start read and load all lrspec");		
		List<String> fileNames = fileUtils.getFilesName(persistenceConfig.getRealPathLRSpecDir(), FileUtils.FILE_ENDING_XML);
		Map<String, LRSpec> lrSpecs = deserializeLRSpecs(fileNames); 
		defineLRSpecs(lrSpecs);
		LOG.debug("end read and load all lrspec");
	}

	/**
	 * deserialize the persistent LRSpecs from the given file names.
	 * @param fileNames the filenames where to get the LRSpecs from.
	 * @return a Map of LRSpecs hashed by their reader name.
	 */
	private Map<String, LRSpec> deserializeLRSpecs(List<String> fileNames) {
		Map<String, LRSpec> lrSpecs = new HashMap<String, LRSpec> ();
		for (String fileName : fileNames) {
			final String readerName = fileName.substring(0, fileName.length() - 4); // remove ".xml"
			try {
				LOG.debug("try to read lrspec " + fileName);
				LRSpec lrSpec = DeserializerUtil.deserializeLRSpec(persistenceConfig.getRealPathLRSpecDir() + fileName);
				lrSpecs.put(readerName, lrSpec);
				LOG.debug("read lrspec " + fileName);
			} catch (FileNotFoundException e) {
				LOG.error("lrspec xml file not found:" + fileName, e);				
			} catch (Exception e) {
				LOG.error("error while reading lrspec xml file " + fileName, e);
			}
			
		}
		return lrSpecs;
	}
	
	/**
	 * define the Logical readers via the Logical Reader manager.
	 * @param lrSpecs the LRSpecs as a Map hashed by their reader name.
	 */
	private void defineLRSpecs(Map<String, LRSpec> lrSpecs) {
		for (Map.Entry<String, LRSpec> entry : lrSpecs.entrySet()) {
			LOG.debug(String.format("Loading LRSpec %s ...", entry.getKey()));
			try {
				logicalReaderManager.define(entry.getKey(), entry.getValue());
			} catch (DuplicateNameException e) {
				LOG.error(String.format("LogicalReader %s already defined.", entry.getKey()), e);
			} catch (ValidationException e) {
				LOG.error(String.format("LRSpec %s is not valid.", entry.getKey()), e);
			} catch (SecurityException e) {
				LOG.error("Security exception.", e);
			} catch (ImplementationException e) {
				LOG.error("Implementation exception.", e);
			}
			LOG.debug(String.format(" ... loading LRSpec %s done", entry.getKey()));
		}
	}

	@Override
	public void readAddROSpecs() {		
		LOG.debug("start read and load all rospecs");
		List<String> fileNames = fileUtils.getFilesName(persistenceConfig.getRealPathROSpecDir(), FileUtils.FILE_ENDING_LLRP);		
		for (String fileName : fileNames) {			
			String specName = fileName.substring(0, fileName.length() - 5); // remove ".llrp"
			ADD_ROSPEC addRoSpec = null;
			
			try {				
				String pathFile = persistenceConfig.getRealPathROSpecDir() + fileName;
				LOG.debug("pathfile of add_rospec is " + pathFile);
				addRoSpec = org.fosstrak.ale.util.DeserializerUtil.deserializeAddROSpec(pathFile);
				LOG.debug("ID of the deserialized add_rospec = " + addRoSpec.getROSpec().getROSpecID());				
			} catch (FileNotFoundException e) {
				LOG.error("add_rospec file not found " + fileName, e);				
			} catch (Exception e) {
				LOG.error("error to read add_rospec file " + fileName, e);
			}
			
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
		LOG.debug("end read and load all rospec");
	}

	@Override
	public void readAddAccessSpecs() {
		LOG.debug("start read and load all accessspecs");
		List<String> filesNameList = fileUtils.getFilesName(persistenceConfig.getRealPathAccessSpecDir(), FileUtils.FILE_ENDING_LLRP);
		for (String fileName : filesNameList) {
			String specName = fileName.substring(0,fileName.length()-5); // remove ".llrp"
			ADD_ACCESSSPEC addAccessSpec = null;
			try {
				String pathFile = persistenceConfig.getRealPathAccessSpecDir() + fileName;
				LOG.debug("pathfile of add_accessspec is " + pathFile);
				addAccessSpec = org.fosstrak.ale.util.DeserializerUtil.deserializeAddAccessSpec(pathFile);
				LOG.debug("ID of the deserialized add_accessspec = " + addAccessSpec.getAccessSpec().getAccessSpecID());
				} catch (FileNotFoundException e) {
					LOG.error("add_accessspec file not found " + fileName, e);				
				} catch (Exception e) {
					LOG.error("error to read add_accessspec file " + fileName, e);
				}
				
				LOG.debug("try to define add_accessspec " + fileName + " with specName = " + specName);
				try {
					llrpControllerManager.defineAccessSpec(specName, addAccessSpec);
				} catch (NoSuchNameException e) {
					LOG.error("error when trying to define add_accessspec ", e);
				} catch (DuplicateNameException e) {
					LOG.error("error when trying to define add_acccessspec ", e);
				}
				LOG.debug("add_acccessspec defined  " + fileName);
		}
		LOG.debug("end read and load all acccessspec");
	}

	/**
	 * inject a handle to the file utils. 
	 * @param fileUtils the file utils to use.
	 */
	@Autowired
	public void setFileUtils(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}

	/**
	 * inject a handle to the persistence config.
	 * @param persistenceConfig the persistence config to use.
	 */
	@Autowired
	public void setPersistenceConfig(PersistenceConfig persistenceConfig) {
		this.persistenceConfig = persistenceConfig;
	}

	/**
	 * inject a handle to the ALE.
	 * @param ale the ALE to use.
	 */
	@Autowired
	public void setAle(ALE ale) {
		this.ale = ale;
	}

	/**
	 * inject a handle to the logical reader manager.
	 * @param ale the logical reader manager to use.
	 */
	@Autowired
	public void setLogicalReaderManager(LogicalReaderManager logicalReaderManager) {
		this.logicalReaderManager = logicalReaderManager;
	}


	/**
	 * inject a handle to the llrp controller manager.
	 * @param llrpControllerManager the  llrp controller manager to use.
	 */
	@Autowired
	public void setLlrpControllerManager(LLRPControllerManager llrpControllerManager) {
		this.llrpControllerManager = llrpControllerManager;
	}

}
