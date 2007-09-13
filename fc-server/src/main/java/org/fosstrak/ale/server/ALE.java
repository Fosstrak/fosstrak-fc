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

package org.accada.ale.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.accada.ale.server.readers.LogicalReaderManager;
import org.accada.ale.server.readers.rp.InputGenerator;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException;
import org.accada.ale.wsdl.ale.epcglobal.SecurityException;
import org.accada.ale.wsdl.ale.epcglobal.ValidationException;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class represents the application level events interface.
 * All ale operations are executed by this class.
 * 
 * @author regli
 * @author sawielan
 * @author haennimi
 */
public class ALE {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(ALE.class);

	/** path to default property file. */
	private static final String DEFAULT_PROPERTIES_PATH = "/InputGenerators.properties";
	
	/** map of report generators which create the ec reports. */
	private static final HashMap<String, ReportsGenerator> reportGenerators = new HashMap<String, ReportsGenerator>();
	/** set of input generators which deliver the tag event inputs. */
	private static Set<InputGenerator> inputGenerators = new HashSet<InputGenerator>();
	
	/** indicates if the ale is ready or not. */
	private static boolean isReady;
	
	/** prefix for name of report generators which are created by immediate command. */
	private static final String REPORT_GENERATOR_NAME_PREFIX = "ReportGenerator_";
	/** index for name of report generaator which are created by immediate command. */
	private static long nameCounter = 0;
	
	/**
	 * This method initalizes the ALE by loading properties from file and creating input generators.
	 * 
	 * @param propertiesFilePath the filepath to the properties file
	 * @throws ImplementationException if properties could not be loaded or input generator could not be created
	 */
	public static void initialize(String propertiesFilePath) throws ImplementationException {

		reportGenerators.clear();
		inputGenerators.clear();
		isReady = false;
		
		// configure Logger with properties file
		PropertyConfigurator.configure(ALE.class.getResource("/log4j.properties"));
		
		// load properties file
		Properties props = new Properties();
		try {
			props.load(ALE.class.getResourceAsStream(propertiesFilePath));
		} catch (Exception e) {
			throw new ImplementationException("Error loading properties from ALE properties file '" + propertiesFilePath + "'",
					ImplementationExceptionSeverity.ERROR);
		}
		
		// we need to initialize the LogicalReaderManager
		String configFile = props.getProperty("readerAPI");
		try {
			LogicalReaderManager.initializeFromFile(configFile);
			
		} catch (Exception e) {
			if (e instanceof ImplementationException) {
				LOG.error("ImplementationException thrown: ");
				
			} else if (e instanceof DuplicateNameException) {
				LOG.error("DuplicateNameException thrown: ");
				
			} else if (e instanceof SecurityException) {
				LOG.error(" SecurityException thrown: ");
				
			} else if (e instanceof ValidationException) {
				LOG.error("ValidationException thrown: ");
				
			}
			
			e.printStackTrace();
			throw new ImplementationException("ALE cannot be continued", 
					ImplementationExceptionSeverity.SEVERE);
		} 

		isReady = true;
		
	}

	/**
	 * This method initializes the ALE by loading properties from default properties file and creating input generators.
	 * 
	 * @throws ImplementationException if properties could not be loaded or input generator could not be created
	 */
	public static void initialize() throws ImplementationException {
		
		initialize(DEFAULT_PROPERTIES_PATH);
		
	}

	/**
	 * This method indicates if the ALE is ready or not.
	 * 
	 * @return true if the ALE is ready and false otherwise
	 */
	public static boolean isReady() {
		
		return isReady;

	}
	
	/**
	 * With this method an ec specification can be defined.
	 * 
	 * @param specName of the ec specification
	 * @param spec to define
	 * @throws DuplicateNameException if a ec specification with the same name is already defined
	 * @throws ECSpecValidationException if the ec specification is not valid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public static void define(String specName, ECSpec spec) throws DuplicateNameException, ECSpecValidationException, ImplementationException {
		
		if (reportGenerators.containsKey(specName)) {
			throw new DuplicateNameException();
		}
		reportGenerators.put(specName, new ReportsGenerator(specName, spec));
		
	}
	
	/**
	 * With this method an ec specification can be undefined.
	 * 
	 * @param specName of the ec specification to undefine
	 * @throws NoSuchNameException if there is no ec specification with this name defined
	 */
	public static void undefine(String specName) throws NoSuchNameException {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameException();
		}
		reportGenerators.remove(specName);
		
	}
	
	/**
	 * This method returns an ec specification depending on a given name.
	 * 
	 * @param specName of the ec specification to return
	 * @return ec specification with the specified name
	 * @throws NoSuchNameException if no such ec specification exists
	 */
	public static ECSpec getECSpec(String specName) throws NoSuchNameException {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameException();
		}
		return reportGenerators.get(specName).getSpec();
		
	}
	
	/**
	 * This method returns the names of all defined ec specifications.
	 * 
	 * @return string array with names
	 */
	public static String[] getECSpecNames() {
		
		return reportGenerators.keySet().toArray(new String[0]);
		
	}
	
	/**
	 * With this method a notification uri can be subscribed to a defined ec specification.
	 * 
	 * @param specName of the ec specification
	 * @param notificationURI to subscribe
	 * @throws NoSuchNameException if there is no ec specification with the given name defined
	 * @throws InvalidURIException if the specified notification uri is invalid
	 * @throws DuplicateSubscriptionException if the same subscription is already done
	 */
	public static void subscribe(String specName, String notificationURI) throws NoSuchNameException, InvalidURIException, DuplicateSubscriptionException {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameException();
		}
		reportGenerators.get(specName).subscribe(notificationURI);
		
	}

	/**
	 * With this method a notification uri can be unsubscribed from a defined ec specification.
	 * 
	 * @param specName of the ec specification
	 * @param notificationURI to unsubscribe
	 * @throws NoSuchNameException if there is no ec specification with the given name defined
	 * @throws NoSuchSubscriberException if the specified notification uri is not subscribed to the ec specification.
	 * @throws InvalidURIException if the specified notification uri is invalid
	 */
	public static void unsubscribe(String specName, String notificationURI) throws NoSuchNameException, NoSuchSubscriberException, InvalidURIException {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameException();
		}
		reportGenerators.get(specName).unsubscribe(notificationURI);
		
	}

	/**
	 * With this method a defined ec specification can be polled.
	 * Polling is the same as subscribe to a ec specification, waiting for one event cycle and then unsubscribe
	 * with the difference that the report is the result of the method instead of sending it to an uri.
	 * 
	 * @param specName of the ec specification which schould be polled
	 * @return ec report of the next event cycle
	 * @throws NoSuchNameException if there is no ec specification with the given name defined
	 */
	public static ECReports poll(String specName) throws NoSuchNameException {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameException();
		}
		return poll(reportGenerators.get(specName));
		
	}
	
	/**
	 * With this method a undefined ec specifcation can be executed.
	 * It's the same as defining the ec specification, polling and undefining it afterwards.
	 * 
	 * @param spec ec specification to execute
	 * @return ec report of the next event cycle
	 * @throws ECSpecValidationException if the ec specification is not valid
	 * @throws ImplementationException if an implementation exception occures
	 */
	public static ECReports immediate(ECSpec spec) throws ECSpecValidationException, ImplementationException {
		
		try {
			return poll(new ReportsGenerator(getNextReportGeneratorName(), spec));
		} catch (NoSuchNameException e) {
			throw new ImplementationException("immediate failed", ImplementationExceptionSeverity.ERROR);
		}
		
	}
	
	/**
	 * This method returns all subscribers to a given ec specification name.
	 * 
	 * @param specName of which the subscribers should be returned
	 * @return array of string with notification uris
	 * @throws NoSuchNameException if there is no ec specification with the given name is defined
	 */
	public static String[] getSubscribers(String specName) throws NoSuchNameException {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameException();
		}
		return reportGenerators.get(specName).getSubscribers().toArray(new String[0]);
		
	}
	
	/**
	 * This method returns the standard version to which this implementation is compatible.
	 * 
	 * @return standard version
	 */
	public static String getStandardVersion() {
		
		return "1.0";
		
	}
	
	/**
	 * This method returns the vendor version of this implementation.
	 * 
	 * @return vendor version
	 */
	public static String getVendorVersion() {
		
		return "0.1";
		
	}
	
	/**
	 * This method closes the ale and remove all input generators and there objects on the reader devices.
	 */
	public static void close() {
		
		LOG.info("Close ALE.");
		
		// remove input generators
		for (InputGenerator inputGenerator : inputGenerators) {
			inputGenerator.remove();
		}
		
	}
	
	/**
	 * With this method the ALE can be started.
	 * 
	 * @param args can contain the properties file path
	 * @throws ImplementationException if an implementation exception occures
	 */
	public static void main(String[] args) throws ImplementationException {
		
		if (args.length == 0) {
			ALE.initialize();
		} else if (args.length == 1) {
			ALE.initialize(args[0]);
		}
		
	}
	
	//
	// private methods
	//
	
	public static ECReports poll(ReportsGenerator reportGenerator) throws NoSuchNameException {
		
		ECReports reports = null;
		reportGenerator.poll();
		try {
			synchronized (reportGenerator) {
				reports = reportGenerator.getPollReports();
				while (reports == null) {
					reportGenerator.wait();
					reports = reportGenerator.getPollReports();
				}
			}
		} catch (InterruptedException e) {}
		
		return reports;
		
	}
	
	/**
	 * This method returns a name for a report generator which is created by a immediate command.
	 * 
	 * @return name for input generator
	 */
	private static String getNextReportGeneratorName() {
		
		return REPORT_GENERATOR_NAME_PREFIX + (nameCounter++);
		
	}
	
}