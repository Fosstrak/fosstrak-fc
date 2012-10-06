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

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.ECTerminationCondition;
import org.fosstrak.ale.util.ECTimeUnit;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports.Reports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;


/**
 * This class represents an event cycle. It collects the tags and manages the 
 * reports.
 * 
 * @author regli
 * @author swieland
 * @author benoit.plomion@orange.com
 * @author nkef@ait.edu.gr
 */
public final class EventCycle implements Runnable, Observer {

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
	
	/** thread. */
	private final Thread thread;
	
	/** event cycle specification for this event cycle. */
	private final ECSpec spec;
	
	/** set of logical readers which deliver tags for this event cycle. */
	private final Set<LogicalReader> logicalReaders = 
		new HashSet<LogicalReader>();
	
	/** set of reports for this event cycle. */
	private final Set<Report> reports = new HashSet<Report>();
	
	/** a hash map with all the reports generated in the last round. */
	private final Map<String, ECReport> lastReports = new HashMap<String, ECReport> ();
	
	/** contains all the ec report specs hashed by their report name. */
	private final Map<String, ECReportSpec> reportSpecByName = 
		new HashMap<String, ECReportSpec> ();
	
	/** set of tags for this event cycle. */
	private  Set<Tag> tags = Collections.synchronizedSet(new HashSet<Tag>());
	
	/** this set stores the tags from the previous EventCycle run. */
	private Set<Tag> lastEventCycleTags = null;
	
	/** this set stores the tags between two event cycle in the case of rejectTagsBetweenCycle is false */
	private Set<Tag> betweenEventsCycleTags =  Collections.synchronizedSet(new HashSet<Tag>());	

	/** flags to know if the event cycle haven t to reject tags in the case than duration and repeatPeriod is same */
	private boolean rejectTagsBetweenCycle = true;

	/** indicates if this event cycle is terminated or not .*/
	private boolean isTerminated = false;
	
	/** 
	 * lock for thread synchronization between reports generator and this.
	 * swieland 2012-09-29: do not use primitive type as int or Integer as autoboxing can result in new thread object for the lock -> non-threadsafe... 
	 */
	private final EventCycleLock lock = new EventCycleLock();
	
	/** flag whether the event cycle has passed through or not. */ 
	private boolean roundOver = false;
	
	/** the duration of collecting tags for this event cycle in milliseconds. */
	private long durationValue;
	
	/** the total time this event cycle runs in milliseconds. */
	private long totalTime;
	
	/** the termination condition of this event cycle. */
	private String terminationCondition = null;

	/** flags the eventCycle whether it shall run several times or not.	 */
	private boolean running = false;
	
	/** flags whether the EventCycle is currently not accepting tags. */
	private boolean acceptTags = false;
	
	/** tells how many times this EventCycle has been scheduled. */
	private int rounds = 0;
	
	// TODO: check if we can use this instead of the dummy class.
	private final class EventCycleLock {
		
	}

	/**
	 * Constructor sets parameter and starts thread.
	 * 
	 * @param generator to which this event cycle belongs to
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public EventCycle(ReportsGenerator generator) throws ImplementationException {
		this(generator, ALEApplicationContext.getBean(LogicalReaderManager.class));
	}
	
	/**
	 * Constructor sets parameter and starts thread.
	 * 
	 * @param generator to which this event cycle belongs to
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public EventCycle(ReportsGenerator generator, LogicalReaderManager logicalReaderManager) throws ImplementationException {	
		
		// set name
		name = generator.getName() + "_" + number++;
		
		// set ReportGenerator
		this.generator = generator;
		
		// set spec
		spec = generator.getSpec();
		
		// get report specs and create a report for each spec
		for (ECReportSpec reportSpec : spec.getReportSpecs().getReportSpec()) {
			
			// add report spec and report to reports
			reports.add(new Report(reportSpec, this));
			
			// hash into the report spec structure
			reportSpecByName.put(reportSpec.getReportName(), reportSpec);
			
		}
		
		// init BoundarySpec values
		durationValue = getDurationValue();
		
		long repeatPeriod = getRepeatPeriodValue();
		if (durationValue == repeatPeriod) {
			setRejectTagsBetweenCycle(false);
		}
		
		LOG.debug(String.format("durationValue: %s\n",
				durationValue));
		
		setAcceptTags(false);
		
		LOG.debug("adding logicalReaders to EventCycle");
		// get LogicalReaderStubs
		if (spec.getLogicalReaders() != null) {
			List<String> logicalReaderNames = 
				spec.getLogicalReaders().getLogicalReader();
			for (String logicalReaderName : logicalReaderNames) {
				LOG.debug("retrieving logicalReader " + logicalReaderName);
				LogicalReader logicalReader = logicalReaderManager.getLogicalReader(logicalReaderName);
				
				if (logicalReader != null) {
					LOG.debug("adding logicalReader " + 
							logicalReader.getName() + " to EventCycle " + name);
					logicalReaders.add(logicalReader);
				}
			}
		} else {
			LOG.error("ECSpec contains no readers");
		}
		
		for (LogicalReader logicalReader : logicalReaders) {
			
			// subscribe this event cycle to the logical readers
			LOG.debug(
					"registering EventCycle " + name + " on reader " + 
					logicalReader.getName());
			
			logicalReader.addObserver(this);
		}
		
		rounds = 0;
		
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
	 * @throws ImplementationException if an implementation exception occurs.
	 */
	private ECReports getECReports() 
		throws ECSpecValidationException, 
		ImplementationException {
		
		// create ECReports
		ECReports reports = new ECReports();

		// set spec name
		reports.setSpecName(generator.getName());
		
		// set date
		try {
			reports.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
		} catch (DatatypeConfigurationException e) {
			LOG.error("Could not create date: " + e.getMessage());
		}

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
		reports.setReports(new Reports());
		reports.getReports().getReport().addAll(getReportList());
		
		return reports;
	}

	/**
	 * This method adds a tag to this event cycle.
	 * 
	 * @param tag to add
	 * @throws ImplementationException if an implementation exception occurs
	 * @throws ECSpecValidationException if the tag is not valid
	 */
	public void addTag(Tag tag) {
		
		if (!isAcceptingTags()) {
			return;
		}
		
		// add event only if EventCycle is still running
		if (isEventCycleActive()) {
			logTagOnDebugEnabled(tag);
			
			// add tag to tags
			addTagAndLogOnNotAdded(tags, tag);
		}
	}

	/**
	 * This method adds a tag between 2 event cycle.
	 * 
	 * @param tag to add
	 * @throws ImplementationException if an implementation exception occurs
	 * @throws ECSpecValidationException if the tag is not valid
	 */
	private void addTagBetweenEventsCycle(Tag tag) {
		
		if (isRejectTagsBetweenCycle()) {
			return;
		}
		
		// add event only if EventCycle is still running
		if (isEventCycleActive()) {
			logTagOnDebugEnabled(tag);
			
			// add tag to tags
			addTagAndLogOnNotAdded(betweenEventsCycleTags, tag);			
		}
	}
	
	/**
	 * determine if this event cycle is active (running) or not.
	 * @return true if the event cycle is active, false if not.
	 */
	private boolean isEventCycleActive() {
		return thread.isAlive();
	}

	/**
	 * log a tag to the logger if debug is enabled. the event cycles name and the tag as pure URI is written to the log on one line.
	 * @param tagToLog the tag to be logged.
	 */
	private void logTagOnDebugEnabled(Tag tagToLog) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("EventCycle '" + name + "' add Tag '" + tagToLog.getTagIDAsPureURI() + "'."); 
		}
	}

	/**
	 * little helper method adding a tag to a given set. if the tag is not added (as already contained) log it.
	 * @param whereToAddTheTag the set where to add the tag to.
	 * @param theTagToAdd the tag which is meant to be added.
	 */
	private void addTagAndLogOnNotAdded(Set<Tag> whereToAddTheTag, Tag theTagToAdd) {
		if (!whereToAddTheTag.add(theTagToAdd) && LOG.isDebugEnabled()) {
			LOG.debug("tag already contained, therefore not adding.");
		}
	}

	/**
	 * implementation of the observer interface for tags.
	 * @param o an observable object that triggered the update
	 * @param arg the arguments passed by the observable
	 */
	public void update(Observable o, Object arg) {
		LOG.debug("EventCycle "+ getName() + ": Update notification received. ");
		List<Tag> tags = new LinkedList<Tag> ();
		// process the new tag.
		if (arg instanceof Tag) {
			LOG.debug("processing one tag");
			// process one tag
			tags.add((Tag) arg);
		} else if (arg instanceof List) {
			LOG.debug("processing a list of tags");
			for (Object entry : (List<?>) arg) {
				if (entry instanceof Tag) {
					tags.add((Tag) entry);					
				}
			}
		}
		
		if (tags.size() > 0) {
			handleTags(tags);
		} else {
			LOG.debug("EventCycle "+ getName() + ": Update notification received - but not with any tags - ignoring. ");			
		}
	}
	
	private void handleTags(List<Tag> tags) {
		if (!isAcceptingTags()) {
			handleTagsWhileNotAccepting(tags);
		} else {
			handleTagsWhileAccepting(tags);
		}		
	}
	
	/**
	 * deal with new tags.
	 * @param tags
	 */
	private void handleTagsWhileAccepting(List<Tag> tags) {	
		// process all the tags we did not process between two eventcycles (or while we did not accept any tags).
		if (!isRejectTagsBetweenCycle()) {			
			for (Tag tag : betweenEventsCycleTags) {
				addTag(tag);			
			}
			
			betweenEventsCycleTags.clear();			
		}
		LOG.debug("EventCycle "+ getName() + ": Received list of tags :");
		for (Tag tag : tags) {
			addTag(tag);
		}
	}

	/**
	 * deal with tags while the event cycle is not accepting tags. (eg. between two event cycles).
	 * @param arg the update we received.
	 */
	private void handleTagsWhileNotAccepting(List<Tag> tags) {
		if (!isRejectTagsBetweenCycle()) {
			for (Tag tag : tags) {	
				LOG.debug("received tag between eventcycles: " + tag.getTagIDAsPureURI());					
				addTagBetweenEventsCycle(tag);	
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
		
		if (isEventCycleActive()) {
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
		
		lastEventCycleTags = new HashSet<Tag>();
		
		// wait for the start
		// running will be set by the ReportsGenerator when the EventCycle
		// has a subscriber
		if (!running) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					LOG.debug("caught interrupted exception");
				}
			}
		}
		
		while (running) {
			rounds ++;
			synchronized (lock) {
				roundOver = false;
			}
			LOG.info("EventCycle "+ getName() + ": Starting (Round " + rounds + ").");
			
			// set start time
			long startTime = System.currentTimeMillis();
	
			// accept tags
			setAcceptTags(true);
			
			//------------------------------ run for the specified time
			try {
				
				if (durationValue > 0) {
					
					// if durationValue is specified and larger than zero, 
					// wait for notify or durationValue elapsed.
					synchronized (this) {
						long dt = (System.currentTimeMillis() - startTime);
						this.wait(Math.max(1, durationValue - dt));
						terminationCondition = ECTerminationCondition.DURATION;
					}
				} else {
					
					// if durationValue is not specified or smaller than zero, 
					// wait for notify.
					synchronized (this) {
						this.wait();
					}
				}
			
			} catch (InterruptedException e) {
				
				// if Thread is stopped with method stop(), 
				// then return without notify subscribers.
				return;
			}
			
			// don't accept tags anymore
			setAcceptTags(false);
			//------------------------ generate the reports
			
			// get reports
			try {
				// compute total time
				totalTime = System.currentTimeMillis() - startTime;
				
				LOG.info("EventCycle "+ getName() + 
						": Number of Tags read in the current EventCyle.java: " 
						+ tags.size());
				
				ECReports ecReports = getECReports();
				
				// notifySubscribers
				generator.notifySubscribers(ecReports, this);
				
				// store the current tags into the old tags
				// explicitly clear the tags
				if (lastEventCycleTags != null) {
					lastEventCycleTags.clear();
				}
				if (null != tags) {
					lastEventCycleTags.addAll(tags);
				}
				
				tags = Collections.synchronizedSet(new HashSet<Tag>());
				
			} catch (Exception e) {
				LOG.error("EventCycle "+ getName() + ": Could not create ECReports", e);
			}
			
			
			LOG.info("EventCycle "+ getName() +  ": EventCycle finished (Round " + rounds + ").");
			try {
				// inform possibly waiting workers about the finish
				synchronized (lock) {
					roundOver = true;
					lock.notifyAll();
				}
				// wait until reschedule.
				synchronized (this) {	
					this.wait();
				}
				LOG.debug("eventcycle continues");
			} catch (InterruptedException e) {
				LOG.error("eventcycle got interrupted");
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
		LOG.debug("launching eventCycle" + getName());
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	/**
	 * This method returns all reports of this event cycle as event cycle 
	 * reports. 
	 * @return array of ec reports
	 * @throws ECSpecValidationException if a tag of this report is not valid
	 * @throws ImplementationException if an implementation exception occurs.
	 */
	private List<ECReport> getReportList() throws ECSpecValidationException, ImplementationException {
		ArrayList<ECReport> ecReports = new ArrayList<ECReport>();
		for (Report report : reports) {
			ECReport r = report.getECReport();
			if (null != r) ecReports.add(r);
		}
		return ecReports;		
	}
	
	/**
	 * This method returns the duration value extracted from the event cycle 
	 * specification. 
	 * @return duration value in milliseconds
	 * @throws ImplementationException if an implementation exception occurs
	 */
	private long getDurationValue() throws ImplementationException {
		if (spec.getBoundarySpec() != null) {
			ECTime duration = spec.getBoundarySpec().getDuration();
			if (duration != null) {
				if (duration.getUnit().compareToIgnoreCase(ECTimeUnit.MS) == 0) {
					return duration.getValue();
				} else {
					throw new ImplementationException(
							"The only ECTimeUnit allowed is milliseconds (MS).");
				}
			}
		}
		return -1;
		
	}
	
	/**	
	 * This method returns the repeat period value on the basis of the event 
	 * cycle specification.
	 * @return repeat period value
	 * @throws ImplementationException if the time unit in use is unknown
	 */
	private long getRepeatPeriodValue() throws ImplementationException {
		if (spec.getBoundarySpec() != null) {		
			ECTime repeatPeriod = spec.getBoundarySpec().getRepeatPeriod();
			if (repeatPeriod != null) {
				if (repeatPeriod.getUnit().compareToIgnoreCase(ECTimeUnit.MS) != 0) {
					throw new ImplementationException(
							"The only ECTimeUnit allowed is milliseconds (MS).");
				} else {
					return repeatPeriod.getValue();
				}
			}
		}
		return -1;
	}

	/**
	 * returns the set of tags from the previous EventCycle run.
	 * @return a set of tags from the previous EventCycle run
	 */
	public Set<Tag> getLastEventCycleTags() {
		return copyContentToNewDatastructure(lastEventCycleTags);
	}

	/**
	 * This method return all tags of this event cycle.
	 * 
	 * @return set of tags
	 */
	public Set<Tag> getTags() {
		return copyContentToNewDatastructure(tags);		
	}
	
	/**
	 * create a copy of the content of the given data structure -> we use synchronized sets -> make sure not to leak them.<br/>
	 * this method synchronizes the original data structure during to copy process.
	 * <br/>
	 * <strong>Notice that the content is NOT cloned, simply referenced!</strong>
	 * 
	 * @param contentToCopy the data structure to copy.
	 * @return a copy of the data structure with the content of the input.
	 */
	private Set<Tag> copyContentToNewDatastructure(Set<Tag> contentToCopy) {
		Set<Tag> copy = new HashSet<Tag> ();
		synchronized (contentToCopy) {
			for (Tag tag : contentToCopy) {
				copy.add(tag);
			}
		}
		return copy;
	}
	
	private boolean isRejectTagsBetweenCycle() {
		return rejectTagsBetweenCycle;
	}

	private void setRejectTagsBetweenCycle(boolean rejectTagsBetweenCycle) {
		this.rejectTagsBetweenCycle = rejectTagsBetweenCycle;
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

	/**
	 * @return the number of rounds this event cycle has already run through.
	 */
	public int getRounds() {
		return rounds;
	}
	
	/**
	 * thread synchronizer for the end of this event cycle. if the event cycle 
	 * has already finished, then the method returns immediately. otherwise the 
	 * thread waits for the finish.
	 * @throws InterruptedException
	 */
	public void join() throws InterruptedException {
		synchronized (lock) {
			while (!isRoundOver()) {
				lock.wait();
			}
		}
	}

	/**
	 * whether the event cycle round is over.
	 * @return true if over, false otherwise
	 */
	public boolean isRoundOver() {
		return roundOver;
	}

	public ECReportSpec getReportSpecByName(String name) {
		return reportSpecByName.get(name);
	}

	public Map<String, ECReportSpec> getReportSpecByName() {
		return reportSpecByName;
	}

	/**
	 * @return the lastReports
	 */
	public Map<String, ECReport> getLastReports() {
		return lastReports;
	}

}