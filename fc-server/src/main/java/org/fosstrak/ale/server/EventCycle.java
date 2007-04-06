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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.accada.ale.server.readers.LogicalReaderManager;
import org.accada.ale.server.readers.LogicalReaderStub;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReportSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.ECTerminationCondition;
import org.accada.ale.xsd.ale.epcglobal.ECTime;
import org.accada.ale.xsd.ale.epcglobal.ECTimeUnit;
import org.accada.reader.msg.notification.TagType;
import org.accada.reader.proxy.RPProxyException;
import org.apache.log4j.Logger;


/**
 * This class represents an event cycle. It collects the tags and manages the reports.
 * 
 * @author regli
 */
public class EventCycle implements Runnable {

	/** logger */
	private static final Logger LOG = Logger.getLogger(EventCycle.class);

	/** random numbers generator */
	private static final Random rand = new Random(System.currentTimeMillis());
	/** ale id */
	private static final String ALEID = "ETHZ-ALE" + rand.nextInt();
	/** number of this event cycle */
	private static int number = 0;
	
	/** name of this event cycle */
	private final String name;
	/** report generator which contains this event cycle */
	private final ReportsGenerator generator;
	/** last event cycle of the same ec specification */
	private final EventCycle lastEventCycle;
	/** thread */
	private final Thread thread;
	/** ec specfication for this event cycle */
	private final ECSpec spec;
	
	/** set of logical reader stubs which deliver tags for this event cycle */
	private final Set<LogicalReaderStub> logicalReaderStubs = new HashSet<LogicalReaderStub>();
	
	/** set of reports for this event cycle */
	private final Set<Report> reports = new HashSet<Report>();
	/** set of tags for this event cycle */
	private final Set<TagType> tags = new HashSet<TagType>();
	
	/** indicates if this event cycle is terminated or not */
	private boolean isTerminated = false;
	/** the duration of collecting tags for this event cycle in milliseconds */
	private long durationValue;
	/** the total time this event cycle runs in milliseconds */
	private long totalTime;
	/** the termination condition of this event cycle */
	private ECTerminationCondition terminationCondition = null;

	/**
	 * Constructor sets parameter and starts thread.
	 * 
	 * @param generator to which this event cycle belongs to
	 * @param lastEventCycle with the same ec specification
	 * @throws ImplementationException if an implementation exception occures
	 */
	public EventCycle(ReportsGenerator generator, EventCycle lastEventCycle) throws ImplementationException {
		
		// set name
		name = generator.getName() + "_" + number++;
		
		// set ReportGenerator
		this.generator = generator;
		
		// set last event Cycle
		this.lastEventCycle = lastEventCycle;
		
		// set spec
		spec = generator.getSpec();
		
		// get report specs and create a report for each spec
		for (ECReportSpec reportSpec : spec.getReportSpecs()) {
			
			// add report spec and report to reports
			reports.add(new Report(reportSpec, this));
			
		}
		
		// init BoundarySpec values
		durationValue = getDurationValue();
		
		// get LogicalReaderStubs
		if (spec.getLogicalReaders() != null) {
			String[] logicalReaderNames = spec.getLogicalReaders();
			for (String logicalReaderName : logicalReaderNames) {
				LogicalReaderStub logicalReader = LogicalReaderManager.getLogicalReaderStub(logicalReaderName);
				if (logicalReader != null) {
					logicalReaderStubs.add(logicalReader);
				}
			}
		}

		// add this EventCycle to runningEventCycles of the corresponding ReportGenerator
		generator.addRunningEventCycle(this);
		
		// create and start Thread
		thread = new Thread(this);
		thread.start();
		
		LOG.debug("New EventCycle '" + name + "' started.");

	}
	
	/**
	 * This method returns the ec reports.
	 * 
	 * @return ec reports
	 * @throws ECSpecValidationException if the tags of the report are not valid
	 * @throws ImplementationException if an implementation exception occures
	 */
	public ECReports getECReports() throws ECSpecValidationException, ImplementationException {
		
		// create ECReports
		ECReports reports = new ECReports();

		// set spec name
		reports.setSpecName(generator.getName());
		
		// set date
		reports.setDate(new GregorianCalendar());

		// set ale id
		reports.setALEID(ALEID);
		
		// set total time in milliseconds
		reports.setTotalMilliseconds(totalTime);
		
		// set termination condition
		reports.setTerminationCondition(terminationCondition);
		
		// set spec
		if (spec.isIncludeSpecInReports()) {
			reports.setECSpec(spec);
		}
		
		// set reports
		reports.setReports(getReportList());
		
		return reports;
		
	}

	/**
	 * This method return all tags of this event cylce.
	 * 
	 * @return set of tags
	 */
	public Set<TagType> getTags() {

		return tags;
		
	}

	/**
	 * This method adds a tag to this event cycle.
	 * 
	 * @param tag to add
	 * @throws ImplementationException if an implementation exception occures
	 * @throws ECSpecValidationException if the tag is not valid
	 */
	public void addTag(TagType tag) throws ImplementationException, ECSpecValidationException {
		
		// add event only if EventCycle is still running
		if (thread.isAlive()) {
			LOG.debug("EventCycle '" + name + "' add Tag '" + tag.getTagIDAsPureURI() + "'.");
			
			// add tag to tags
			tags.add(tag);
			
			// iterate over reports and add event
			for (Report report : reports) {
				report.addTag(tag);
			}
			
		}
		
	}
	
	/**
	 * This method stops the thread.
	 */
	public void stop() {
		
		if (thread.isAlive()) {
			thread.interrupt();
			
			// stop EventCycle
			LOG.debug("EventCycle '" + name + "' stopped.");
			
			// remove EventCycle from runningEventCycles of the corresponding ReportGenerator
			generator.removeRunningEventCycle(this);
		}
		
		isTerminated = true;
		
		synchronized (this) {
			this.notifyAll();
		}
		
	}
	
	/**
	 * This method returns the name of this event cycle.
	 * 
	 * @return name of event cycle
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * This method indicates if this event cycle is terminated or not.
	 * 
	 * @return true if this event cycle is terminated and false otherwise
	 */
	public boolean isTerminated() {
		
		return isTerminated;
		
	}
	
	/**
	 * This method is the main loop of the event cycle in which the tags will be collected.
	 * At the end the reports will be generated and the subscribers will be notified.
	 */
	public void run() {
		
		// set start time
		long startTime = System.currentTimeMillis();
		
		try {
			
			for (LogicalReaderStub logicalReader : logicalReaderStubs) {
				
				// subscribe this event cycle to the logical readers
				logicalReader.subscribeEventCycle(this);
				
				// get all tags from logical readers at this moment
				for (TagType tag : logicalReader.getAllTags()) {
					addTag(tag);
				}
			}
			
			if (durationValue > 0) {
				
				// if durationValue is specified and larger than zero, wait for notify or durationValue elapsed.
				synchronized (this) {
					this.wait(Math.max(1, durationValue - (System.currentTimeMillis() - startTime)));
					terminationCondition = ECTerminationCondition.DURATION;
				}
			} else {
				
				// if durationValue is not specified or smaller than zero, wait for notify.
				synchronized (this) {
					this.wait();
				}
			}

			// unsubscribe this event cycle from logical readers
			for (LogicalReaderStub logicalReader : logicalReaderStubs) {
				logicalReader.unsubscribeEventCycle(this);
			}
			
		} catch (InterruptedException e) {
			
			// if Thread is stopped with method stop(), then return without notify subscribers.
			return;
			
		} catch (ImplementationException e) {
			e.printStackTrace();
		} catch (ECSpecValidationException e) {
			e.printStackTrace();
		} catch (RPProxyException e) {
			e.printStackTrace();
		}
		
		// get reports
		try {
			
			ECReports ecReports = getECReports();
			
			// notifySubscribers
			generator.notifySubscribers(ecReports);
			
		} catch (ECSpecValidationException e) {
			LOG.error("Could not create ECReports (" + e.getMessage() + ")");
		} catch (ImplementationException e) {
			LOG.error("Could not create ECReports (" + e.getMessage() + ")");
		}
		
		// compute total time
		totalTime = System.currentTimeMillis() - startTime;
		
		// stop EventCycle
		stop();
		
	}

	/**
	 * This method returns the last event cycle.
	 * 
	 * @return last event cycle
	 */
	public EventCycle getLastEventCycle() {
		
		return lastEventCycle;
		
	}
	
	//
	// private methods
	//
	
	/**
	 * This method returns all reports of this event cycle as ec reports.
	 * 
	 * @return array of ec reports
	 * @throws ECSpecValidationException if a tag of this report is not valid
	 * @throws ImplementationException if an implementation exception occures
	 */
	private ECReport[] getReportList() throws ECSpecValidationException, ImplementationException {

		ArrayList<ECReport> ecReports = new ArrayList<ECReport>();
		for (Report report : reports) {
			ecReports.add(report.getECReport());
		}
		return ecReports.toArray(new ECReport[reports.size()]);
		
	}
	
	/**
	 * This method returns the duration value extracted from the ec specification.
	 * 
	 * @return duration value in milliseconds
	 * @throws ImplementationException if an implementation excpetion occures
	 */
	private long getDurationValue() throws ImplementationException {
		
		ECTime duration = spec.getBoundarySpec().getDuration();
		if (duration != null) {
			if (duration.getUnit() == ECTimeUnit.MS) {
				return duration.get_value();
			} else {
				throw new ImplementationException("The only ECTimeUnit allowed is milliseconds (MS).",
						ImplementationExceptionSeverity.ERROR);
			}
		}
		return -1;
		
	}

}