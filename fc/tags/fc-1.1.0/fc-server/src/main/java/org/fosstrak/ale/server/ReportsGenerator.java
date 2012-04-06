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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.ECTimeUnit;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException;
import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateSubscriptionExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchSubscriberException;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchSubscriberExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports.Reports;
import org.fosstrak.ale.xsd.epcglobal.EPC;

/**
 * This class generates ec reports.
 * It validates the ec specifications, starts and stops the event cycles and manages the subscribers.  
 * 
 * @author regli
 * @author sawielan
 * @author benoit.plomion@orange.com
 */
public class ReportsGenerator implements Runnable {

	/** logger */
	private static final Logger LOG = Logger.getLogger(ReportsGenerator.class);
	
	/** name of the report generator */
	private final String name;
	
	/** ec specification which defines how the report should be generated */
	private final ECSpec spec;
	
	/** map of subscribers of this report generator */
	private final Map<String, Subscriber> subscribers = 
		new HashMap<String, Subscriber>();
	
	// boundary spec values
	
	/** start trigger */
	private final String startTriggerValue;
	
	/** stop trigger */
	private final String stopTriggerValue;
	
	/** time between one and the following event cycle in milliseconds */
	private final long repeatPeriodValue;
	
	/**
	 * The stable set interval in milliseconds. If there are no new tags 
	 * detected for this time, the reports generation should stop.
	 */
	private final long stableSetInterval;
	
	/** thread to run the main loop */
	private Thread thread;
	
	/** state of this report generator */
	private ReportsGeneratorState state = ReportsGeneratorState.UNREQUESTED;
	
	/** indicates if this report generator is running or not */
	private boolean isRunning = false;
	
	/** indicates if somebody is polling this input generator at the moment. */
	private boolean isPolling = false;
	
	/** ec report for the poller */
	private ECReports pollReport = null;
	
	
	private EventCycle eventCycle = null;
	
	/**
	 * Constructor validates the ec specification and sets some parameters.
	 * 
	 * @param name of this reports generator
	 * @param spec which defines how the reports of this generator should be build
	 * @throws ECSpecValidationException if the ec specification is invalid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public ReportsGenerator(String name, ECSpec spec) 
		throws ECSpecValidationExceptionResponse, 
		ImplementationExceptionResponse {

		LOG.debug("Try to create new ReportGenerator '" + name + "'.");
		
		// set name
		this.name = name;
		
		// set spec
		try {
			validateSpec(spec);
		} catch (ECSpecValidationExceptionResponse e) {
			LOG.error(e.getClass().getSimpleName() + ": " + e.getMessage());
			throw e;
		} catch (ImplementationExceptionResponse e) {
			LOG.error(e.getClass().getSimpleName() + ": " + e.getMessage());
			throw e;
		}
		this.spec = spec;
		
		// init boundary spec values
		startTriggerValue = getStartTriggerValue();
		stopTriggerValue = getStopTriggerValue();
		repeatPeriodValue = getRepeatPeriodValue();
		stableSetInterval = getStableSetInterval();
		
		LOG.debug(
				String.format("startTriggerValue: %s\nstopTriggerValue: " +
						"%s\nrepeatPeriodValue: %s\nstableSetInterval: %s\n",
						startTriggerValue, 
						stopTriggerValue, 
						repeatPeriodValue, 
						stableSetInterval));
		
		LOG.debug("ReportGenerator '" + name + "' successfully created.");
	}
	
	/**
	 * This method returns the ec specification of this generator.
	 * 
	 * @return ec specification
	 */
	public ECSpec getSpec() {
		return spec;
	}
	
	/**
	 * This method sets the state of this report generator.
	 * If the state changes from UNREQUESTED to REQUESTED, the report generators 
	 * main loop will be started.
	 * If the state changes from REQUESTED to UNREQUESTED, the report generators 
	 * main loop will be stoped.
	 * 
	 * @param state to set
	 */
	public void setState(ReportsGeneratorState state) {
		
		ReportsGeneratorState oldState = this.state;
		this.state = state;
		synchronized (this.state) {
			this.state.notifyAll();
		}
		LOG.debug("ReportGenerator '" + name + "' change state from '" 
				+ oldState + "' to '" + state + "'");
		
		if (state == ReportsGeneratorState.REQUESTED && !isRunning) {
			start();
		} else if (state == ReportsGeneratorState.UNREQUESTED && isRunning) {
			stop();
		}
	}
	
	/** 
	 * This method returns the state of this report generator.
	 * 
	 * @return state
	 */
	public ReportsGeneratorState getState() {
		return state;
	}
	
	/**
	 * This method subscribes a notification uri of a subscriber to this 
	 * report generator. 
	 * @param notificationURI to subscribe
	 * @throws DuplicateSubscriptionException if the specified notification uri 
	 * is already subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public void subscribe(String notificationURI) 
		throws DuplicateSubscriptionExceptionResponse, 
		InvalidURIExceptionResponse {
		
		Subscriber uri = new Subscriber(notificationURI);
		
		//ORANGE: persistence can only add one URI
		/* Original code
		if (subscribers.containsKey(notificationURI)) {
			throw new DuplicateSubscriptionExceptionResponse();
		} else {
			subscribers.put(notificationURI, uri);
			LOG.debug("NotificationURI '" + notificationURI + "' subscribed to spec '" + name + "'.");
			if (state == ReportsGeneratorState.UNREQUESTED) {
				setState(ReportsGeneratorState.REQUESTED);
			}
		}
		*/
		if (!subscribers.isEmpty()) {
			throw new DuplicateSubscriptionExceptionResponse();
		} else {
			subscribers.put(notificationURI, uri);
			LOG.debug("NotificationURI '" + notificationURI + "' subscribed to spec '" + name + "'.");
			if (state == ReportsGeneratorState.UNREQUESTED) {
				setState(ReportsGeneratorState.REQUESTED);
			}
		}
	}
	
	/**
	 * This method unsubscribes a notification uri of a subscriber from this 
	 * report generator.
	 * @param notificationURI to unsubscribe
	 * @throws NoSuchSubscriberException if the specified notification uri is 
	 * not yet subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public void unsubscribe(String notificationURI) 
		throws NoSuchSubscriberExceptionResponse, InvalidURIExceptionResponse {
		
		new Subscriber(notificationURI);
		if (subscribers.containsKey(notificationURI)) {
			subscribers.remove(notificationURI);
			LOG.debug("NotificationURI '" + notificationURI 
					+ "' unsubscribed from spec '" + name + "'.");
			if (subscribers.isEmpty() && !isPolling) {
				setState(ReportsGeneratorState.UNREQUESTED);
			}
		} else {
			throw new NoSuchSubscriberExceptionResponse();
		}
	}
	
	/**
	 * This method return the notification uris of all the subscribers of this 
	 * report generator.
	 * @return list of notification uris
	 */
	public List<String> getSubscribers() {
		return new ArrayList<String>(subscribers.keySet());
	}
	
	/**
	 * adds an epc value to the hashset. 
	 * @param set the set where to add.
	 * @param epc the epc to get the value from.
	 * @return true if value could be obtained, false otherwise.
	 */
	private boolean addEPC(HashSet<String> set, EPC epc)
	{
		if ((null == set) || (null == epc) || (null == epc.getValue())) return false;
		set.add(epc.getValue());
		return true;
	}
	
	/**
	 * This method notifies all subscribers of this report generator about the 
	 * specified ec reports.
	 * @param reports to notify the subscribers about
	 */
	public void notifySubscribers(ECReports reports, EventCycle ec) {
		
		// according the ALE 1.1 standard:
		// When the processing of reportIfEmpty and reportOnlyOnChange
		// results in all ECReport instances being omitted from an 
		// ECReports for an event cycle, then the delivery of results
		// to subscribers SHALL be suppressed altogether. [...] poll 
		// and immediate SHALL always be returned [...] even if that 
		// ECReports instance contains zero ECReport instances.

		// we remove the reports that are equal to the ones in the 
		// last event cycle. then we send the subscribers and add 
		// them back for the pollers... .
		List<ECReport> equalReps = new LinkedList<ECReport> ();
		Map<String, ECReportGroup> newGroupByName = new HashMap<String, ECReportGroup> ();
		Map<String, ECReportGroup> oldGroupByName = new HashMap<String, ECReportGroup> ();
		boolean isOneReportRequestingEmpty = false;	// count if someone wants empty reports...
		System.out.println("reports size: " + reports.getReports().getReport().size());
		try {
		for (ECReport r : reports.getReports().getReport()) {
			// if we only want reports that have changed, we need to compare the 
			// old report with the current...
			if (ec.getReportSpecByName().get(r.getReportName()).isReportIfEmpty()) {
				isOneReportRequestingEmpty = true;
				System.out.println("requesting empty: " + r.getReportName());
			}
			if (ec.getReportSpecByName().get(r.getReportName()).isReportOnlyOnChange()) {
				
				ECReport oldR = ec.getLastReports().get(r.getReportName());
	
				boolean equality = false;
				// compare the tags...
				if (null != oldR) {
					List<ECReportGroup> oldGroup = oldR.getGroup();
					List<ECReportGroup> newGroup = r.getGroup();
					if (oldGroup.size() == newGroup.size()) {
						// equal amount of groups, so need to compare the groups...
						for (ECReportGroup g : oldGroup) oldGroupByName.put(g.getGroupName(), g);
						for (ECReportGroup g : newGroup) newGroupByName.put(g.getGroupName(), g);
						
						for (String gName : oldGroupByName.keySet()) {
							ECReportGroup og = oldGroupByName.get(gName);
							ECReportGroup ng = newGroupByName.get(gName);
							
							// now compare the two groups...
							if ((null != og.getGroupList()) && (null != ng.getGroupList())) {
								// need to check
								
								boolean useEPC = ec.getReportSpecByName().get(r.getReportName()).getOutput().isIncludeEPC();
								boolean useTag = ec.getReportSpecByName().get(r.getReportName()).getOutput().isIncludeTag();
								boolean useHex = ec.getReportSpecByName().get(r.getReportName()).getOutput().isIncludeRawHex();
								HashSet<String> hs = new HashSet<String>();
								HashSet<String> hs2 = new HashSet<String>();
								for (ECReportGroupListMember oMember : og.getGroupList().getMember()) {
									
									boolean error = false;
									// compare according the epc field
									if (useEPC) error = addEPC(hs, oMember.getEpc());
									if (!error && useTag) error = addEPC(hs, oMember.getTag());
									if (!error && useHex) error = addEPC(hs, oMember.getRawHex());
									if (!error) error = addEPC(hs, oMember.getRawDecimal());
								}
								for (ECReportGroupListMember oMember : ng.getGroupList().getMember()) {
									
									boolean error = false;
									// compare according the epc field
									if (useEPC) error = addEPC(hs2, oMember.getEpc());
									if (!error && useTag) error = addEPC(hs2, oMember.getTag());
									if (!error && useHex) error = addEPC(hs2, oMember.getRawHex());
									if (!error) error = addEPC(hs2, oMember.getRawDecimal());
								}
								// if intersection is not empty, the sets are not equal
								if (hs.containsAll(hs2) && hs2.containsAll(hs)) {
									// equal
									equality = true;
								} else {
									equality = false;
									break;
								}
								
							} else if ((null == og.getGroupList()) && (null == ng.getGroupList())) {
								// the groups are equal
								equality = true;
							} else {
								// not equal.
								equality = false;
								break;
							}
						}
					}
				}
			
				if (equality) {
					equalReps.add(r);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// remove the equal reports
		Reports re = reports.getReports();
		if (null != re) re.getReport().removeAll(equalReps);
		System.out.println("reports size2: " + reports.getReports().getReport().size());
		// next step is to check, if the total report is empty
		if (
				((null != re) && (re.getReport().size() > 0)) 
				|| 
				(isOneReportRequestingEmpty)) {		
			// notify subscribers 
			for (Subscriber listener : subscribers.values()) {
				try {
					listener.notify(reports);
				} catch (Exception e) {
					LOG.error("Could not notifiy subscriber '" + 
							listener.getURI() 
							+ "' (" + e.getMessage() + ")");
				}
			}
		}
		
		// add the equal reports back (as pollers need to get those).
		if (null != re) re.getReport().addAll(equalReps);
		
		// store the new reports as old reports
		ec.getLastReports().clear();
		if (null != re) {
			for (ECReport r : re.getReport()) {
				ec.getLastReports().put(r.getReportName(), r);
			}
		}
		
		// notify pollers
		// pollers always receive reports (even when empty).
		if (isPolling) {
			pollReport = reports;
			isPolling = false;
			if (subscribers.isEmpty()) {
				setState(ReportsGeneratorState.UNREQUESTED);
			}
			synchronized (this) {
				this.notifyAll();
			}
		}	
	}

	/**
	 * This method is invoked if somebody polls this report generator.
	 * The result of the polling can be picked up by the method getPollReports.
	 */
	public void poll() {
		
		LOG.debug("Spec '" + name + "' polled.");
		pollReport = null;
		isPolling = true;
		if (state == ReportsGeneratorState.UNREQUESTED) {
			setState(ReportsGeneratorState.REQUESTED);
		}
	}
	
	/**
	 * This method delivers the ec reports which have been generated because 
	 * of a poll.
	 * @return ec reports
	 */
	public ECReports getPollReports() {
		return pollReport;
	}
	
	/**
	 * This method starts the main loop of the report generator.
	 */
	public void start() {
		
		thread = new Thread(this, name);
		thread.start();
		isRunning = true;
		LOG.debug("Thread of spec '" + name + "' started.");		
	}
	
	/**
	 * This method stops the main loop of the report generator.
	 */
	public void stop() {
		eventCycle.stop();
		
		// stop Thread
		if (thread.isAlive()) {
			thread.interrupt();
		}
		
		isRunning = false;
		
		LOG.debug("Thread of spec '" + name + "' stopped.");
	}
	
	/**
	 * This method returns the name of this reports generator.
	 * 
	 * @return name of reports generator
	 */
	public String getName() {
		return name;
		
	}
	
	/**
	 * This method contains the main loop of the reports generator.
	 * Here the event cycles will be generated and started.
	 */
	public void run() {
		
		try {
			eventCycle = new EventCycle(this);
		} catch (ImplementationExceptionResponse e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (startTriggerValue != null) {
			if (repeatPeriodValue == -1) {
				// startTrigger is specified and repeatPeriod is not specified
				// eventCycle is started when:
				// state is REQUESTED and startTrigger is received
				
			}
		} else {
			if (repeatPeriodValue != -1) {
						
				// startTrigger is not specified and repeatPeriod is specified
				// eventCycle is started when:
				// state transitions from UNREQUESTED to REQUESTED or
				// repeatPeriod has elapsed from start of the last eventCycle and
				// in that interval the state was never UNREQUESTED
				while (isRunning) {
					
					// wait until state is REQUESTED
					while (state != ReportsGeneratorState.REQUESTED) {
						try {
							synchronized (state) {
								state.wait();
							}
						} catch (InterruptedException e) {
							return;
						}
					}
					
					// while state is REQUESTED start every repeatPeriod a 
					// new EventCycle
					while (state == ReportsGeneratorState.REQUESTED) {
						if (eventCycle == null) {
							LOG.error("eventCycle is null");
						} else {
							eventCycle.launch();
						}

						try {
							synchronized (state) {
								state.wait(repeatPeriodValue);
							}
							// wait for the event cycle to finish...
							eventCycle.join();
							
						} catch (InterruptedException e) {
							return;
						}
					}
					
				}
			} else {
				
				// neither startTrigger nor repeatPeriod are specified
				// eventCycle is started when:
				// state transitions from UNREQUESTED to REQUESTED or
				// immediately after the previous event cycle, if the state 
				// is still REQUESTED
				while (isRunning) {
					
					// wait until state is REQUESTED
					while (state != ReportsGeneratorState.REQUESTED) {
						try {
							synchronized (state) {
								state.wait();
							}
						} catch (InterruptedException e) {
							return;
						}
					}
					
					// while state is REQUESTED start one EventCycle 
					// after the other
					while (state == ReportsGeneratorState.REQUESTED) {
						eventCycle.launch();
						
						while (!eventCycle.isTerminated()) {
							try {
								synchronized (eventCycle) {
									eventCycle.wait();
								}
							} catch (InterruptedException e) {
								return;
							}
						}
					}
				}
			}
		}
		
	}
	
	//
	// private methods
	//
	
	/**
	 * This method returns the repeat period value on the basis of the event 
	 * cycle specification.
	 * @return repeat period value
	 * @throws ImplementationException if the time unit in use is unknown
	 */
	private long getRepeatPeriodValue() throws ImplementationExceptionResponse {
		
		ECTime repeatPeriod = spec.getBoundarySpec().getRepeatPeriod();
		if (repeatPeriod != null) {
			if (repeatPeriod.getUnit().compareToIgnoreCase(ECTimeUnit.MS) != 0) {
				throw new ImplementationExceptionResponse(
						"The only ECTimeUnit allowed is milliseconds (MS).");
			} else {
				return repeatPeriod.getValue();
			}
		}
		return -1;
		
	}

	/**
	 * This method returns the start trigger value on the basis of the event 
	 * cycle specification.
	 * @return start trigger value
	 */
	private String getStartTriggerValue() {
		
		String startTrigger = spec.getBoundarySpec().getStartTrigger();
		if (startTrigger != null) {
			return startTrigger;
		}
		return null;
		
	}
	
	/**
	 * This method returns the stop trigger value on the basis of the event 
	 * cycle specification.
	 * @return stop trigger value
	 */
	private String getStopTriggerValue() {
		
		String stopTrigger = spec.getBoundarySpec().getStopTrigger();
		if (stopTrigger != null) {
			return stopTrigger;
		}
		return null;
		
	}
	
	/**
	 * This method returns the stabel set interval on the basis of the event 
	 * cycle specification.
	 * @return stable set interval
	 */
	private long getStableSetInterval() {
		
		ECTime stableSetInterval = spec.getBoundarySpec().getStableSetInterval();
		if (stableSetInterval != null) {
			return stableSetInterval.getValue();
		}
		return -1;
		
	}

	/**
	 * This method validates an ec specification under criterias of chapter 
	 * 8.2.11 of ALE specification version 1.0.
	 * @param spec to validate
	 * @throws ECSpecValidationException if the specification is invalid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	private void validateSpec(ECSpec spec) 
		throws ECSpecValidationExceptionResponse, 
		ImplementationExceptionResponse {
				
		// check if the logical readers are known to the implementation
		List<String> logicalReaders = 
			spec.getLogicalReaders().getLogicalReader();
		if (logicalReaders != null) {
			for (String logicalReaderName : logicalReaders) {
				if (!LogicalReaderManager.contains(logicalReaderName)) {
					throw new ECSpecValidationExceptionResponse(
							"LogicalReader '" + logicalReaderName 
							+ "' is unknown.");
				}
			}
		}
		
		// boundaries parameter of ECSpec is null or omitted
		ECBoundarySpec boundarySpec = spec.getBoundarySpec();
		if (boundarySpec == null) {
			throw new ECSpecValidationExceptionResponse(
					"The boundaries parameter of ECSpec is null.");
		}
		
		// start and stop tiggers
		checkTrigger(boundarySpec.getStartTrigger());
		checkTrigger(boundarySpec.getStopTrigger());
		
		// check if duration, stableSetInterval or repeatPeriod is negative
		ECTime time;
		if ((time = boundarySpec.getDuration()) != null) {
			if (time.getValue() < 0) {
				throw new ECSpecValidationExceptionResponse(
						"The duration field of ECBoundarySpec is negative.");
			}
		}
		if ((time = boundarySpec.getStableSetInterval()) != null) {
			if (time.getValue() < 0) {
				throw new ECSpecValidationExceptionResponse(
						"The stableSetInterval field of ECBoundarySpec is negative.");
			}
		}
		if ((time = boundarySpec.getRepeatPeriod()) != null) {
			if (time.getValue() < 0) {
				throw new ECSpecValidationExceptionResponse(
						"The repeatPeriod field of ECBoundarySpec is negative.");
			}
		}
		
		// check if start trigger is non-empty and repeatPeriod is non-zero
		if ((boundarySpec.getStartTrigger() != null) && (boundarySpec.getRepeatPeriod().getValue() != 0)) {
			throw new ECSpecValidationExceptionResponse(
					"The startTrigger field of ECBoundarySpec is non-empty and " +
					"the repeatPeriod field of ECBoundarySpec is non-zero.");
		}
		
		// check if a stopping condition is specified
		if ((boundarySpec.getStopTrigger() == null) 
				&& (boundarySpec.getDuration() == null) &&
				(boundarySpec.getStableSetInterval() == null)) {
			throw new ECSpecValidationExceptionResponse(
					"No stopping condition is specified in ECBoundarySpec.");
		}
		
		// check if there is a ECReportSpec instance
		if ((spec.getReportSpecs() == null) ||
				(spec.getReportSpecs().getReportSpec().size() == 0)) {
			throw new ECSpecValidationExceptionResponse(
					"List of ECReportSpec instances is empty.");
		}
		
		// check if two ECReportSpec instances have identical names
		Set<String> reportSpecNames = new HashSet<String>();
		for (ECReportSpec reportSpec : spec.getReportSpecs().getReportSpec()) {
			if (reportSpecNames.contains(reportSpec.getReportName())) {
				throw new ECSpecValidationExceptionResponse(
						"Two ReportSpecs instances have identical names '" +
						reportSpec.getReportName() + "'.");
			} else {
				reportSpecNames.add(reportSpec.getReportName());
			}
		}
		
		// check filters
		for (ECReportSpec reportSpec : spec.getReportSpecs().getReportSpec()) {
			ECFilterSpec filterSpec = reportSpec.getFilterSpec();
			
			if (filterSpec != null) {
				// check include patterns
				if (filterSpec.getIncludePatterns() != null) {
					for (String pattern : filterSpec.getIncludePatterns().
							getIncludePattern()) {
						
						new Pattern(pattern, PatternUsage.FILTER);
					}
				}
				
				// check exclude patterns
				if (filterSpec.getExcludePatterns() != null) {
					for (String pattern : filterSpec.getExcludePatterns().
							getExcludePattern()) {
						
						new Pattern(pattern, PatternUsage.FILTER);
					}
				}
			}
			
		}
		
		// check grouping patterns
		for (ECReportSpec reportSpec : spec.getReportSpecs().getReportSpec()) {
			if (reportSpec.getGroupSpec() != null) {
				for (String pattern1 : reportSpec.getGroupSpec().getPattern()) {
					Pattern pattern = new Pattern(pattern1, PatternUsage.GROUP);
					for (String pattern2 : reportSpec.getGroupSpec().getPattern()) {
						if (pattern1 != pattern2 && !pattern.isDisjoint(pattern2)) {
							throw new ECSpecValidationExceptionResponse(
									"The two grouping patterns '" + pattern1 +
									"' and '" + pattern2 + "' are not disjoint.");
						}
					}
				}
			}
		}
		
		// check if there is a output type specified for each ECReportSpec
		for (ECReportSpec reportSpec : spec.getReportSpecs().getReportSpec()) {
			ECReportOutputSpec outputSpec = reportSpec.getOutput();
			if (!outputSpec.isIncludeEPC() && !outputSpec.isIncludeTag() && !outputSpec.isIncludeRawHex() &&
					!outputSpec.isIncludeRawDecimal() && !outputSpec.isIncludeCount()) {
				throw new ECSpecValidationExceptionResponse("The ECReportOutputSpec of ReportSpec '" +
						reportSpec.getReportName() + "' has no output type specified.");
			}
		}
		
	}
	
	/**
	 * This method checks if the trigger is valid or not.
	 * 
	 * @param trigger to check
	 * @throws ECSpecValidationException if the trigger is invalid.
	 */
	private void checkTrigger(String trigger) throws ECSpecValidationExceptionResponse {
		
		// TODO: implement checkTrigger
		
	}
	
}