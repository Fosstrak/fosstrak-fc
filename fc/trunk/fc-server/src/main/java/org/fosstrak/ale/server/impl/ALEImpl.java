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
package org.fosstrak.ale.server.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.ALESettings;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.impl.type.InputGeneratorProvider;
import org.fosstrak.ale.server.impl.type.ReportsGeneratorsProvider;
import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.server.readers.rp.InputGenerator;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class represents the application level events interface.
 * All ale operations are executed by this class.
 * 
 * @author regli
 * @author swieland
 * @author haennimi
 * @author benoit.plomion@orange.com
 */
@Service("ale")
public class ALEImpl implements ALE {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(ALEImpl.class);
	
	/** set of input generators which deliver the tag event inputs. */
	private InputGeneratorProvider inputGenerators = new InputGeneratorProvider();
	
	private ReportsGeneratorsProvider reportGeneratorsProvider = new ReportsGeneratorsProvider();

	/** indicates if the ale is ready or not. */
	private boolean isReady = false;
	
	/** prefix for name of report generators which are created by immediate command. */
	private static final String REPORT_GENERATOR_NAME_PREFIX = "ReportGenerator_";
	
	/** index for name of report generator which are created by immediate command. */
	private long nameCounter = 0;

	@Autowired
    private LogicalReaderManager logicalReaderManager;

	// autowired
	private RemoveConfig persistenceRemoveAPI;
	
	// autowired
	private WriteConfig persistenceWriteAPI;
    
    // autowired
    private ALESettings aleSettings;

    /**
     * setup the ALE directly after the construction of the bean.<br/>
     * <strong>NOTICE:</strong> Do not depend on initializer methods of other autowired components (like logical reader manager) 
     * as the order of the initializer methods on the injected beans is not preset -> thus, the 
     * injected bean might already be present in the ale, but not initialized (call postconstruct) yet.
     */
	@PostConstruct
	public void initialize() {		
		LOG.debug("initializing ALE.");
		if (isReady) {
			LOG.debug("ALE already initialized - abort instruction.");
			return;
		}
		isReady = false;
		reportGeneratorsProvider.clear();
		inputGenerators.clear();
		isReady = true;
		LOG.info("ALE initialized");	
	}

	@Override
	public boolean isReady() {
		return isReady;
	}	

	@Override
	public void define(String specName, ECSpec spec) throws DuplicateNameException, ECSpecValidationException, ImplementationException {
		if (reportGeneratorsProvider.containsKey(specName)) {
			LOG.debug("spec already defined: " + specName);
			throw new DuplicateNameException("ECSpec already defined with name: " + specName);
		}
		reportGeneratorsProvider.put(specName, reportGeneratorsProvider.createNewReportGenerator(specName, spec));			
		persistenceWriteAPI.writeECSpec(specName, spec);
	}
	
	@Override
	public void undefine(String specName) throws NoSuchNameException {		

		throwNoSuchNameExceptionIfNoSuchSpec(specName);
		
		reportGeneratorsProvider.remove(specName);
		persistenceRemoveAPI.removeECSpec(specName);
	}
	
	@Override
	public ECSpec getECSpec(String specName) throws NoSuchNameException {		

		throwNoSuchNameExceptionIfNoSuchSpec(specName);
		
		return reportGeneratorsProvider.get(specName).getSpec();
	}
	
	@Override
	public String[] getECSpecNames() {		
		return reportGeneratorsProvider.keySet().toArray(new String[0]);
	}
	
	@Override
	public void subscribe(String specName, String notificationURI) throws NoSuchNameException, InvalidURIException, DuplicateSubscriptionException {

		throwNoSuchNameExceptionIfNoSuchSpec(specName);
		
		reportGeneratorsProvider.get(specName).subscribe(notificationURI);
		persistenceWriteAPI.writeECSpecSubscriber(specName, notificationURI);		
	}

	@Override
	public void unsubscribe(String specName, String notificationURI) throws NoSuchNameException, NoSuchSubscriberException, InvalidURIException {
		
		throwNoSuchNameExceptionIfNoSuchSpec(specName);
		
		reportGeneratorsProvider.get(specName).unsubscribe(notificationURI);
		persistenceRemoveAPI.removeECSpecSubscriber(specName, notificationURI);
	}

	@Override
	public ECReports poll(String specName) throws NoSuchNameException {

		throwNoSuchNameExceptionIfNoSuchSpec(specName);
		return poll(reportGeneratorsProvider.get(specName));		
	}
	
	@Override
	public ECReports immediate(ECSpec spec) throws ECSpecValidationException, ImplementationException {		
		try {
			return poll(new ReportsGenerator(getNextReportGeneratorName(), spec));
		} catch (NoSuchNameException e) {
			throw new ImplementationException("immediate failed");
		}
	}
	
	@Override
	public String[] getSubscribers(String specName) throws NoSuchNameException {
		
		throwNoSuchNameExceptionIfNoSuchSpec(specName);
		return reportGeneratorsProvider.get(specName).getSubscribers().toArray(new String[0]);
	}
	
	@Override
	public final String getStandardVersion() {	
		LOG.debug("getStandardVersion");
		return aleSettings.getAleStandardVersion();
		
	}
	
	@Override
	public final String getVendorVersion() {
		LOG.debug("getVendorVersion");
		return aleSettings.getAleVendorVersion();
	}
	
	@Override
	public void close() {		
		LOG.info("Close ALE.");
		
		// remove input generators
		for (InputGenerator inputGenerator : inputGenerators) {
			inputGenerator.remove();
		}		
	}
	
	private ECReports poll(ReportsGenerator reportGenerator) throws NoSuchNameException {
		
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
		} catch (InterruptedException e) {
			LOG.debug("got interrupted.");
		}		
		return reports;
	}
	
	/**
	 * This method returns a name for a report generator which is created by a immediate command.
	 * 
	 * @return name for input generator
	 */
	private String getNextReportGeneratorName() {
		return REPORT_GENERATOR_NAME_PREFIX + (nameCounter++);
	}
	
	@Override
	public Map<String, ReportsGenerator> getReportGenerators() {
		return reportGeneratorsProvider;
	}

	/**
	 * throws an exception if the given specification name is not existing.
	 * @param specName the name of the specification to verify.
	 * @throws NoSuchNameException when name not existing.
	 */
	protected void throwNoSuchNameExceptionIfNoSuchSpec(String specName) throws NoSuchNameException {
		if (!reportGeneratorsProvider.containsKey(specName)) {
			throw new NoSuchNameException("No ECSpec with such name defined: " + specName);
		}
	}
	
	/**
	 * allow to inject a new ALESettings.
	 * @param aleSettings the new ALESettings to be used.
	 */
    @Autowired
	public void setAleSettings(ALESettings aleSettings) {
		this.aleSettings = aleSettings;
	}
	
	/**
	 * allow to inject a new report generator provider.
	 * @param reportGeneratorsProvider the new report generator provider.
	 */
	public void setReportGeneratorsProvider(ReportsGeneratorsProvider reportGeneratorsProvider) {
		this.reportGeneratorsProvider = reportGeneratorsProvider;
	}
	
	/**
	 * allow to inject a new input generator provider.
	 * @param inputGenerators the new input generator provider.
	 */
	public void setInputGenerators(InputGeneratorProvider inputGenerators) {
		this.inputGenerators = inputGenerators;
	}

	/**
	 * allow to inject the persistence delete API.
	 * @param persistenceRemoveAPI the persistence delete API.
	 */
    @Autowired
	public void setPersistenceRemoveAPI(RemoveConfig persistenceRemoveAPI) {
		this.persistenceRemoveAPI = persistenceRemoveAPI;
	}

    /**
     * allow to inject the persistence write API.
     * @param persistenceWriteAPI the persistence write API.
     */
    @Autowired
	public void setPersistenceWriteAPI(WriteConfig persistenceWriteAPI) {
		this.persistenceWriteAPI = persistenceWriteAPI;
	}
}