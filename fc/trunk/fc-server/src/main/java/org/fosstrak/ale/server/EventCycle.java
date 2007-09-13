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
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

import org.accada.ale.server.readers.LogicalReader;
import org.accada.ale.server.readers.LogicalReaderManager;
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
import org.accada.reader.rp.proxy.RPProxyException;
import org.accada.reader.rprm.core.msg.notification.TagType;
import org.apache.log4j.Logger;


/**
 * This class represents an event cycle. It collects the tags and manages the reports.
 * 
 * @author regli
 * @author sawielan
 */
public class EventCycle implements Runnable, Observer {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(EventCycle.class);

	/** random numbers generator. */
	private static final Random rand = new Random(System.currentTimeMillis());
	/** ale id. */
	private static final String ALEID = "ETHZ-ALE" + rand.nextInt();
	/** number of this event cycle. */
	private static int number = 0;
	
	/** name of this event cycle. */
	private final String name;
	/** report generator which contains this event cycle. */
	private final ReportsGenerator generator;
	/** last event cycle of the same ec specification. */
	//private final EventCycle lastEventCycle;
	/** thread. */
	private final Thread thread;
	
	/** ec specfication for this event cycle. */
	private final ECSpec spec;
	
	/** set of logical readers which deliver tags for this event cycle. */
	private final Set<LogicalReader> logicalReaders = new HashSet<LogicalReader>();
	
	/** set of reports for this event cycle. */
	private final Set<Report> reports = new HashSet<Report>();
	
	/** set of tags for this event cycle. */
	private  Set<Tag> tags = new HashSet<Tag>();
	
	/** this set stores the tags from the previous EventCycle run. */
	private Set<Tag> lastEventCycleTags = null;
	
	/** indicates if this event cycle is terminated or not .*/
	private boolean isTerminated = false;
	/** the duration of collecting tags for this event cycle in milliseconds. */
	private long durationValue;
	/** the total time this event cycle runs in milliseconds. */
	private long totalTime;
	/** the termination condition of this event cycle. */
	private ECTerminationCondition terminationCondition = null;

	/** flags the eventCycle whether it shall run several times or not.	 */
	private boolean running = false;
	
	/** flags whether the EventCycle is currently not accepting tags. */
	private boolean acceptTags = false;
	
	/**
	 * Constructor sets parameter and starts thread.
	 * 
	 * @param generator to which this event cycle belongs to
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public EventCycle(ReportsGenerator generator) throws ImplementationException {
		
		// set name
		name = generator.getName() + "_" + number++;
		
		// set ReportGenerator
		this.generator = generator;
		
		// set spec
		spec = generator.getSpec();
		
		// get report specs and create a report for each spec
		for (ECReportSpec reportSpec : spec.getReportSpecs()) {
			
			// add report spec and report to reports
			reports.add(new Report(reportSpec, this));
			
		}
		
		// init BoundarySpec values
		durationValue = getDurationValue();
		
		setAcceptTags(false);
		
		// get LogicalReaderStubs
		if (spec.getLogicalReaders() != null) {
			String[] logicalReaderNames = spec.getLogicalReaders();
			for (String logicalReaderName : logicalReaderNames) {
				LogicalReader logicalReader = LogicalReaderManager.getLogicalReader(logicalReaderName);
				
				if (logicalReader != null) {
					logicalReaders.add(logicalReader);
				}
			}
		}
		
		for (LogicalReader logicalReader : logicalReaders) {
			
			// subscribe this event cycle to the logical readers
			logicalReader.addObserver(this);
		}
		
		// create and start Thread
		thread = new Thread(this);
		thread.start();
		
		LOG.debug("New EventCycle  '" + name + "' created.");

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
	public synchronized Set<Tag> getTags() {

		return tags;
		
	}

	/**
	 * This method adds a tag to this event cycle.
	 * 
	 * @param tag to add
	 * @throws ImplementationException if an implementation exception occures
	 * @throws ECSpecValidationException if the tag is not valid
	 */
	public synchronized void addTag(Tag tag) throws ImplementationException, ECSpecValidationException {
		if (!isAcceptingTags()) {
			return;
		}
		
		// add event only if EventCycle is still running
		if (thread.isAlive()) {
			LOG.debug("EventCycle '" + name + "' add Tag '" + tag.getTagID() + "'.");
			
			for (Tag atag : tags) {
				// do not add the tag it is already in the list
				if (atag.equals(tag)) {
					return;
				}
			}
			
			// add tag to tags
			tags.add(tag);
			
			// iterate over reports and add event
			for (Report report : reports) {
				report.addTag(tag);
			}
			
		}
	}
	
	/**
	 * compatibility reasons.
	 * @param tag to add
	 * @throws ImplementationException if an implementation exception occures
	 * @throws ECSpecValidationException if the tag is not valid
	 */
	public void addTag(TagType tag) throws ImplementationException, ECSpecValidationException {
		if (!isAcceptingTags()) {
			return;
		}
		
		Tag newTag = new Tag();
		newTag.setTagID(tag.getTagIDAsPureURI());
		addTag(newTag);
	}


	/**
	 * implementation of the observer interface for tags.
	 * @param o an observable object that triggered the update
	 * @param arg the arguments passed by the observable
	 */
	public synchronized void update(Observable o, Object arg) {
		if (!isAcceptingTags()) {
			return;
		}
		
		if (arg instanceof Tag) {
			Tag tag = (Tag) arg;
			LOG.debug("EventCycle: received tag :");
			//tag.prettyPrint(LOG);
			try {
				addTag(tag);
			} catch (ImplementationException ie) {
				ie.printStackTrace();
			} catch (ECSpecValidationException ive) {
				ive.printStackTrace();
			}
		}
	}
	
	/**
	 * This method stops the thread.
	 */
	public void stop() {

		// unsubscribe this event cycle from logical readers
		for (LogicalReader logicalReader : logicalReaders) {
			//logicalReader.unsubscribeEventCycle(this);
			logicalReader.deleteObserver(this);
		}

		
		if (thread.isAlive()) {
			thread.interrupt();
			
			// stop EventCycle
			LOG.debug("EventCycle '" + name + "' stopped.");
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
		
		// wait for the start
		// running will be set by the ReportsGenerator when the EventCycle
		// has a subscriber
		if (!running) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		while (running) {
			LOG.debug("starting EventCycle");
			
			// set start time
			long startTime = System.currentTimeMillis();
	
			// accept tags
			setAcceptTags(true);
			
			//------------------------------ run for the specified time
			try {
				
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
			
			} catch (InterruptedException e) {
				
				// if Thread is stopped with method stop(), then return without notify subscribers.
				return;
				
			}
			
			// dont accept tags anymore
			setAcceptTags(false);
			//-------------------------------------------------- generate the reports
			
			// get reports
			try {
				
				ECReports ecReports = getECReports();
				
				// notifySubscribers
				generator.notifySubscribers(ecReports);
				
				// store the current tags into the old tags
				lastEventCycleTags = tags;
				tags = new HashSet<Tag>();
				
			} catch (ECSpecValidationException e) {
				LOG.error("Could not create ECReports (" + e.getMessage() + ")");
			} catch (ImplementationException e) {
				LOG.error("Could not create ECReports (" + e.getMessage() + ")");
			}
			
			// compute total time
			totalTime = System.currentTimeMillis() - startTime;
		
			LOG.debug("EventCycle finished");
			try {
				synchronized (this) {
					this.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			
		// stop EventCycle
		stop();
		
	}
	
	/**
	 * starts this EventCycle.
	 */
	public void launch() {
		this.running = true;
		synchronized (this) {
			this.notifyAll();
		}
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
	 * @throws ImplementationException if an implementation exception occurs
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

	/**
	 * returns the set of tags from the previous EventCycle run.
	 * @return a set of tags from the previous EventCycle run
	 */
	public Set<Tag> getLastEventCycleTags() {
		return lastEventCycleTags;
	}

	/** 
	 * tells whether the ec accepts tags.
	 * @return boolean telling whether the ec accepts tags
	 */
	private boolean isAcceptingTags() {
		return acceptTags;
	}

	/**
	 * sets the flag acceptTags to the passed boolean value. 
	 * @param acceptTags sets the flag acceptTags to the passed boolean value.
	 */
	private void setAcceptTags(boolean acceptTags) {
		this.acceptTags = acceptTags;
	}

}