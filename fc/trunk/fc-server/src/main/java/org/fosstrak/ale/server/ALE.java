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

package org.fosstrak.ale.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;
import org.fosstrak.ale.server.readers.LogicalReaderManagerFactory;
import org.fosstrak.ale.server.readers.rp.InputGenerator;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameException;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateSubscriptionExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameException;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchSubscriberException;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchSubscriberExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;

/**
 * This class represents the application level events interface.
 * All ale operations are executed by this class.
 * 
 * @author regli
 * @author sawielan
 * @author haennimi
 * @author benoit.plomion@orange.com
 */
public class ALE {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(ALE.class);

	/** path to default property file. */
	private static final String DEFAULT_PROPERTIES_PATH = "/ale.properties";
	
	/** map of report generators which create the ec reports. */
	private static final HashMap<String, ReportsGenerator> reportGenerators = new HashMap<String, ReportsGenerator>();
	/** set of input generators which deliver the tag event inputs. */
	private static Set<InputGenerator> inputGenerators = new HashSet<InputGenerator>();
	
	/** indicates if the ale is ready or not. */
	private static boolean isReady = false;
	
	/** prefix for name of report generators which are created by immediate command. */
	private static final String REPORT_GENERATOR_NAME_PREFIX = "ReportGenerator_";
	/** index for name of report generaator which are created by immediate command. */
	private static long nameCounter = 0;

	// contains the application properties
	private static Properties aleProperties;	
	/**
	 * This method initalizes the ALE by loading properties from file and creating input generators.
	 * 
	 * @param propertiesFilePath the filepath to the properties file
	 * @throws ImplementationException if properties could not be loaded or input generator could not be created
	 */
	public static void initialize(String propertiesFilePath) throws ImplementationExceptionResponse {
		LOG.debug("initializing ALE.");
		if (isReady) {
			LOG.debug("ALE already initialized - abort instruction.");
			return;
		}
		reportGenerators.clear();
		inputGenerators.clear();
		isReady = false;
		
		// configure Logger with properties file
		// PropertyConfigurator.configure(ALE.class.getResource("/log4j.properties"));
		
		// load properties file
		aleProperties = getAleProperties();
		if (null == aleProperties) {
			throw new ImplementationExceptionResponse("Error loading properties from ALE properties file '" + propertiesFilePath + "'");
		}
		
		try {
			if (!LogicalReaderManagerFactory.getLRM().isInitialized()) {
				throw new IllegalStateException("logical reader manager is not initialized.");
			}
			
		} catch (Exception e) {
			throw new ImplementationExceptionResponse("ALE cannot be continued");
		} 

		isReady = true;
		LOG.info("ALE initialized");
	}

	/**
	 * This method initializes the ALE by loading properties from default properties file and creating input generators.
	 * 
	 * @throws ImplementationException if properties could not be loaded or input generator could not be created
	 */
	public static void initialize() throws ImplementationExceptionResponse {
		
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
	public static void define(String specName, ECSpec spec) throws DuplicateNameExceptionResponse, ECSpecValidationExceptionResponse, ImplementationExceptionResponse {
		if (reportGenerators.containsKey(specName)) {
			LOG.debug("spec already defined: " + specName);
			throw new DuplicateNameExceptionResponse();
		}
		reportGenerators.put(specName, new ReportsGenerator(specName, spec));		
			
		//ORANGE: persistence define ECSpec
		WriteConfig.writeECSpec(specName, spec);
	
	}
	
	/**
	 * With this method an ec specification can be undefined.
	 * 
	 * @param specName of the ec specification to undefine
	 * @throws NoSuchNameException if there is no ec specification with this name defined
	 */
	public static void undefine(String specName) throws NoSuchNameExceptionResponse {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameExceptionResponse();
		}
		reportGenerators.remove(specName);
		
		//ORANGE: persistence undefine ECSpec
		RemoveConfig.removeECSpec(specName);
		
	}
	
	/**
	 * This method returns an ec specification depending on a given name.
	 * 
	 * @param specName of the ec specification to return
	 * @return ec specification with the specified name
	 * @throws NoSuchNameException if no such ec specification exists
	 */
	public static ECSpec getECSpec(String specName) throws NoSuchNameExceptionResponse {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameExceptionResponse();
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
	public static void subscribe(String specName, String notificationURI) throws NoSuchNameExceptionResponse, InvalidURIExceptionResponse, DuplicateSubscriptionExceptionResponse {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameExceptionResponse();
		}
		reportGenerators.get(specName).subscribe(notificationURI);
		
		//ORANGE: persistence add ECSpec subscriber
		WriteConfig.writeECSpecSubscriber(specName, notificationURI);
		
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
	public static void unsubscribe(String specName, String notificationURI) throws NoSuchNameExceptionResponse, NoSuchSubscriberExceptionResponse, InvalidURIExceptionResponse {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameExceptionResponse();
		}
		reportGenerators.get(specName).unsubscribe(notificationURI);
		
		//ORANGE: persistence remove ECSpec subscriber
		RemoveConfig.removeECSpecSubscriber(specName, notificationURI);
		
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
	public static ECReports poll(String specName) throws NoSuchNameExceptionResponse {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameExceptionResponse();
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
	public static ECReports immediate(ECSpec spec) throws ECSpecValidationExceptionResponse, ImplementationExceptionResponse {
		
		try {
			return poll(new ReportsGenerator(getNextReportGeneratorName(), spec));
		} catch (NoSuchNameExceptionResponse e) {
			throw new ImplementationExceptionResponse("immediate failed");
		}
		
	}
	
	/**
	 * This method returns all subscribers to a given ec specification name.
	 * 
	 * @param specName of which the subscribers should be returned
	 * @return array of string with notification uris
	 * @throws NoSuchNameException if there is no ec specification with the given name is defined
	 */
	public static String[] getSubscribers(String specName) throws NoSuchNameExceptionResponse {
		
		if (!reportGenerators.containsKey(specName)) {
			throw new NoSuchNameExceptionResponse();
		}
		return reportGenerators.get(specName).getSubscribers().toArray(new String[0]);
		
	}
	

	
	/**
	 * load the application properties if not loaded yet.
	 * @return the ALE application properties.
	 */
	public static synchronized Properties getAleProperties() {
		if (null == aleProperties) {
			try {
				// load and set the ALE vendor and standard version.
				aleProperties = new Properties();
				aleProperties.load(ALE.class.getResourceAsStream(DEFAULT_PROPERTIES_PATH));
			} catch (Exception ex) {
				LOG.error("could not read ALE properties: ", ex);
			}
		}
		return aleProperties;
	}
	
	/**
	 * extract a given property from the ALE versions. if the versions cannot be loaded, then "default" is returned.
	 * @param property the property to extract.
	 * @return the property or "undefined"
	 */
	private static String getVersionProperty(String property) {
		Properties p = getAleProperties();
		final String defaultValue = "undefined";
		if (null == p) {
			return defaultValue;
		}
		return p.getProperty(property, defaultValue);
		
	}
	
	/**
	 * This method returns the standard version to which this implementation is compatible.
	 * 
	 * @return standard version
	 */
	public static final String getStandardVersion() {	
		LOG.debug("getStandardVersion");	
		return getVersionProperty("ale.standard.version");
		
	}
	
	/**
	 * This method returns the vendor version of this implementation.
	 * 
	 * @return vendor version
	 */
	public static final String getVendorVersion() {
		LOG.debug("getVendorVersion");
		return getVersionProperty("ale.vendor.version");
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
	public static void main(String[] args) throws ImplementationExceptionResponse {
		
		if (args.length == 0) {
			ALE.initialize();
		} else if (args.length == 1) {
			ALE.initialize(args[0]);
		}
		
	}
	
	//
	// private methods
	//
	
	public static ECReports poll(ReportsGenerator reportGenerator) throws NoSuchNameExceptionResponse {
		
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
	
	//ORANGE: add for ALEController ws
	public static HashMap<String, ReportsGenerator> getReportGenerators() {
		return reportGenerators;
	}
	
}