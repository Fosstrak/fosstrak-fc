/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Accada (www.accada.org).
 *
 * Accada is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Accada is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Accada; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.accada.ale.server.readers;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.accada.ale.server.readers.generated.LogicalReader;
import org.accada.ale.server.readers.generated.LogicalReaders;
import org.accada.ale.server.readers.generated.PhysicalReader;
import org.apache.log4j.Logger;


/**
 * This class manages the mapping between logical and physical readers.
 * The definition is loaded from the file LogicalReader.xml.
 * 
 * @author regli
 */
public class LogicalReaderManager {

	/** logger */
	private static final Logger LOG = Logger.getLogger(LogicalReaderManager.class);
	
	/** package containing the generated jaxb classes */
	private static final String JAXB_CONTEXT = "org.accada.ale.server.readers.generated";
	
	/** default path to file which contains the mapping definitions */
	private static final String CONFIGURATION_FILEPATH = "/LogicalReaders.xml";
	
	/** logical reader configuration loaded from file */
	private static LogicalReaders logicalReadersConfiguration;
	/** map containing the logical reader stubs */
	private static final Map<String, LogicalReaderStub> logicalReaderStubs = new HashMap<String, LogicalReaderStub>();
	/** map containing the physical reader stubs */
	private static final Map<String, PhysicalReaderStub> physicalReaderStubs = new HashMap<String, PhysicalReaderStub>();

	/** indicates if the manager is initialized or not */
	private static boolean initialized = false;
	
	/**
	 * This method initializes the manager by loading the default definition from file.
	 */
	public static void initialize() {
		
		initialize(CONFIGURATION_FILEPATH); 
		
	}
	
	/**
	 * This method initializes the manager by loading the definition from the specified file
	 * and creating corresponding logical reader stubs.
	 * 
	 * @param configurationFilePath to initialize
	 */
	public static void initialize(String configurationFilePath) {
		
		LOG.debug("Initialize LogicalReaderManager");
		
		// if configuration file path is not set, set it to default value
		if (configurationFilePath == null) {
			configurationFilePath = CONFIGURATION_FILEPATH;
		}
		
		// try to parse reader configuration file
		LOG.debug("Parse configuration file");
		List<LogicalReader> logicalReaders;
		try {
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// unmarshal logical reader configuration file
			InputStream inputStream = LogicalReaderManager.class.getResourceAsStream(configurationFilePath);
			logicalReadersConfiguration = (LogicalReaders)unmarshaller.unmarshal(inputStream);
			logicalReaders = logicalReadersConfiguration.getLogicalReader();
		} catch (JAXBException e) {
			e.printStackTrace();
			return;
		}
		
		// iterate over logical readers
		for (LogicalReader logicalReader : logicalReaders) {
			
			// get logical reader name
			String logicalReaderName = logicalReader.getName();
			
			// create logical reader stubs and add it to logical reader stubs
			logicalReaderStubs.put(logicalReaderName, new LogicalReaderStub(logicalReaderName));
			
		}
			
		// set initialized to true
		initialized = true;
		
		LOG.debug("LogicalReaderManager successfully initialized");
		
	}
	
	/**
	 * This method indicates if the manager contains a logical reader with specified name.
	 * 
	 * @param logicalReaderName to search
	 * @return true if the logical reader exists and false otherwise
	 */
	public static boolean contains(String logicalReaderName) {
		
		// initialize if necessary
		if (!initialized) initialize();
		
		return logicalReaderStubs.containsKey(logicalReaderName);
		
	}
	
	/**
	 * This method returns the logical reader stub which belongs to the logical reader name.
	 * 
	 * @param logicalReaderName
	 * @return logical reader stub
	 */
	public static LogicalReaderStub getLogicalReaderStub(String logicalReaderName) {
		
		// initialize if necessary
		if (!initialized) initialize();
		
		return logicalReaderStubs.get(logicalReaderName);
		
	}
	
	/**
	 * This method return all logical reader stubs which contains the specified source.
	 * 
	 * @param physicalReaderName of the reader which contains the specified source
	 * @param sourceName 
	 * @return set of logical reader stubs
	 */
	public static Set<LogicalReaderStub> getLogicalReaderStubs(String physicalReaderName, String sourceName) {
		
		// initialize if necessary
		if (!initialized) initialize();
		
		try {
			PhysicalReaderStub physicalReaderStub = physicalReaderStubs.get(physicalReaderName);
			if (sourceName == null) {
				Set<LogicalReaderStub> resultSet = new HashSet<LogicalReaderStub>();
				for (PhysicalSourceStub sourceStub : physicalReaderStub.getSourceStubs()) {
					resultSet.addAll(sourceStub.getLogicalReaderStubs());
				}
				return resultSet;
			} else {
				return physicalReaderStub.getSourceStub(sourceName).getLogicalReaderStubs();
			}
		} catch (NullPointerException e) {
			return null;
		}
		
	}
	
	/**
	 * This method adds a physical reader stub to the manager.
	 * 
	 * @param physicalReaderStub to add
	 */
	public static void addPhysicalReaderStub(PhysicalReaderStub physicalReaderStub) {
		
		// initialize if necessary
		if (!initialized) initialize();
		
		LOG.debug("Add PhysicalReaderStub '" + physicalReaderStub.getName() + "' to LogicalReaderManager.");
		
		// add reader stub to reader stubs
		physicalReaderStubs.put(physicalReaderStub.getName(), physicalReaderStub);
		
		// add physical source stubs to corresponding logical reader stubs and other way round
		for (PhysicalSourceStub physicalSourceStub : physicalReaderStub.getSourceStubs()) {
			for (LogicalReaderStub logicalReaderStub : getLogicalReaderStubs(physicalReaderStub, physicalSourceStub)) {
				physicalSourceStub.addLogicalReaderStub(logicalReaderStub);
				logicalReaderStub.addPhysicalSourceStub(physicalSourceStub);
			}
		}

	}

	/**
	 * This method returns all logical readers which contains the specified physical source stub.
	 * 
	 * @param physicalReaderStub containing the physical source stub
	 * @param physicalSourceStub
	 * @return set of logical reader stubs
	 */
	private static Set<LogicalReaderStub> getLogicalReaderStubs(PhysicalReaderStub physicalReaderStub, PhysicalSourceStub physicalSourceStub) {
		
		// create result set
		Set<LogicalReaderStub> resultSet = new HashSet<LogicalReaderStub>();
		
		// get stub names
		String readerStubName = physicalReaderStub.getName();
		String sourceStubName = physicalSourceStub.getName();
		
		// iterate over logical readers in configuration
		List<LogicalReader> logicalReaders = logicalReadersConfiguration.getLogicalReader();
		for (LogicalReader logicalReader : logicalReaders) {
			for (PhysicalReader physicalReader : logicalReader.getPhysicalReader()) {
				if (readerStubName.equals(physicalReader.getName())) {
					
					// test excludes
					boolean excluded = false;
					if (physicalReader.getExclude() != null && physicalReader.getExclude().getSource() != null) {
						for (String sourceName : physicalReader.getExclude().getSource()) {
							if (sourceStubName.equals(sourceName)) {
								excluded = true;
								break;
							}
						}
					}
					
					if (!excluded) {
						// test included
						if (physicalReader.getInclude() != null && physicalReader.getInclude().getSource() != null) {
							for (String sourceName : physicalReader.getInclude().getSource()) {
								if (sourceStubName.equals(sourceName)) {
									resultSet.add(getLogicalReaderStub(logicalReader.getName()));
									break;
								}
							}
						} else {
							resultSet.add(getLogicalReaderStub(logicalReader.getName()));
						}
					}
				}
			}
		}
		
		return resultSet;
		
	}
	
}