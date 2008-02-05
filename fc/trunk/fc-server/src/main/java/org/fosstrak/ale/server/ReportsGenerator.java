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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.accada.ale.server.readers.LogicalReaderManager;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException;
import org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.accada.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroup;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.ECTime;
import org.accada.ale.xsd.ale.epcglobal.ECTimeUnit;
import org.accada.ale.xsd.ale.epcglobal.ECTrigger;
import org.apache.log4j.Logger;

/**
 * This class generates ec reports.
 * It validates the ec specifications, starts and stops the event cycles and manages the subscribers.  
 * 
 * @author regli
 */
public class ReportsGenerator implements Runnable {

	/** logger */
	private static final Logger LOG = Logger.getLogger(ReportsGenerator.class);
	
	/** name of the report generator */
	private final String name;
	/** ec specification which defines how the report should be generated */
	private final ECSpec spec;
	/** map of subscribers of this report generator */
	private final Map<String, Subscriber> subscribers = new HashMap<String, Subscriber>();
	

	/** set of currently running event cycles of this report generator */
//	private final Set<EventCycle> runningEventCycles = new HashSet<EventCycle>();
	
	// boundary spec values
	/** start trigger */
	private final String startTriggerValue;
	/** stop trigger */
	private final String stopTriggerValue;
	/** time between one and the following event cycle in milliseconds */
	private final long repeatPeriodValue;
	/**
	 * The stable set interval in milliseconds.
	 * If there are no new tags detected for this time, the reports generation should stop.
	 */
	private final long stableSetInterval;
	
	/** thread to run the main loop */
	private Thread thread;
	
	/** state of this report generator */
	private ReportsGeneratorState state = ReportsGeneratorState.UNREQUESTED;
	/** indicates if this report generator is running or not */
	private boolean isRunning = false;
	/** indicates if somebody is polling this input generator at the moment or not */
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
	public ReportsGenerator(String name, ECSpec spec) throws ECSpecValidationException, ImplementationException {

		LOG.debug("Try to create new ReportGenerator '" + name + "'.");
		
		// set name
		this.name = name;
		
		// set spec
		try {
			validateSpec(spec);
		} catch (ECSpecValidationException e) {
			LOG.error(e.getClass().getSimpleName() + ": " + e.getReason());
			throw e;
		} catch (ImplementationException e) {
			LOG.error(e.getClass().getSimpleName() + ": " + e.getReason());
			throw e;
		}
		this.spec = spec;
		
		// init boundary spec values
		startTriggerValue = getStartTriggerValue();
		stopTriggerValue = getStopTriggerValue();
		repeatPeriodValue = getRepeatPeriodValue();
		stableSetInterval = getStableSetInterval();
		
		LOG.debug(String.format("startTriggerValue: %s\nstopTriggerValue: %s\nrepeatPeriodValue: %s\nstableSetInterval: %s\n",
				startTriggerValue, stopTriggerValue, repeatPeriodValue, stableSetInterval));
		
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
	 * If the state changes from UNREQUESTED to REQUESTED, the report generators main loop will be started.
	 * If the state changes from REQUESTED to UNREQUESTED, the report generatots main loop will be stoped.
	 * 
	 * @param state to set
	 */
	public void setState(ReportsGeneratorState state) {
		
		ReportsGeneratorState oldState = this.state;
		this.state = state;
		synchronized (this.state) {
			this.state.notifyAll();
		}
		LOG.debug("ReportGenerator '" + name + "' change state from '" + oldState + "' to '" + state + "'");
		
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
	 * This method subscribes a notification uri of a subscriber to this report generator.
	 * 
	 * @param notificationURI to subscribe
	 * @throws DuplicateSubscriptionException if the specified notifiction uri is already subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public void subscribe(String notificationURI) throws DuplicateSubscriptionException, InvalidURIException {
		
		Subscriber uri = new Subscriber(notificationURI);
		if (subscribers.containsKey(notificationURI)) {
			throw new DuplicateSubscriptionException();
		} else {
			subscribers.put(notificationURI, uri);
			LOG.debug("NotificationURI '" + notificationURI + "' subscribed to spec '" + name + "'.");
			if (state == ReportsGeneratorState.UNREQUESTED) {
				setState(ReportsGeneratorState.REQUESTED);
			}
		}
		
	}
	
	/**
	 * This method unsubscribes a notification uri of a subscriber from this report generator.
	 * 
	 * @param notificationURI to unsubscribe
	 * @throws NoSuchSubscriberException if the specified notification uri is not yet subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public void unsubscribe(String notificationURI) throws NoSuchSubscriberException, InvalidURIException {
		
		new Subscriber(notificationURI);
		if (subscribers.containsKey(notificationURI)) {
			subscribers.remove(notificationURI);
			LOG.debug("NotificationURI '" + notificationURI + "' unsubscribed from spec '" + name + "'.");
			if (subscribers.isEmpty() && !isPolling) {
				setState(ReportsGeneratorState.UNREQUESTED);
			}
		} else {
			throw new NoSuchSubscriberException();
		}
		
	}
	
	/**
	 * This method return the notification uris of all the subscribers of this report generator.
	 * 
	 * @return list of notification uris
	 */
	public List<String> getSubscribers() {
		
		return new ArrayList<String>(subscribers.keySet());
		
	}
	
	/**
	 * This method notifies all subscribers of this report generator about the specified ec reports.
	 * 
	 * @param reports to notify the subscribers about
	 */
	public void notifySubscribers(ECReports reports) {
		
		LOG.debug("Notify subscribers of Spec '" + name + "' with ECReports '" + reports.getSpecName() + "'.");
		if (reports.getReports() != null){
			ECReport[] ecReports = reports.getReports();
			for (ECReport ecReport : ecReports) {
				LOG.debug("  Report: " + ecReport.getReportName());
				if (ecReport.getGroup() != null) {
					for (ECReportGroup group : ecReport.getGroup()) {
						LOG.debug("    Group: " + group.getGroupName() + "(" + group.getGroupCount() + " members)");
						if (group.getGroupList().getMember() != null) {
							for (ECReportGroupListMember member : group.getGroupList().getMember()) {
								LOG.debug("      " + member.getTag());
							}
						}	
					}
				}
			}	
		}
		
		// notifiy subscribers
		for (Subscriber listener : subscribers.values()) {
			try {
				listener.notify(reports);
			} catch (ImplementationException e) {
				LOG.error("Could not notifiy subscriber '" + listener.getURI() + "' (" + e.getMessage() + ")");
			}
		}
		
		// notify pollers
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
	 * This method delivers the ec reports which have been generated because of a poll. 
	 * 
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
		/*
		// stop running EventCycles
		for (EventCycle eventCycle : runningEventCycles) {
			eventCycle.stop();
		}
		*/
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
		} catch (ImplementationException e) {
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
					
					// while state is REQUESTED start every repeatPeriod a new EventCycle
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
						} catch (InterruptedException e) {
							return;
						}
					}
					
				}
			} else {
				
				// neither startTrigger nor repeatPeriod are specified
				// eventCycle is started when:
				// state transitions from UNREQUESTED to REQUESTED or
				// immediately after the previous event cycle, if the state is still REQUESTED
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
					
					// while state is REQUESTED start one EventCycle after the other
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
	 * This method returns the repeat period value on the basis of the ec specification.
	 * 
	 * @return repeat period value
	 * @throws ImplementationException if the time unit in use is unknown
	 */
	private long getRepeatPeriodValue() throws ImplementationException {
		
		ECTime repeatPeriod = spec.getBoundarySpec().getRepeatPeriod();
		if (repeatPeriod != null) {
			if (repeatPeriod.getUnit() == ECTimeUnit.MS) {
				return repeatPeriod.get_value();
			} else {
				throw new ImplementationException("The only ECTimeUnit allowed is milliseconds (MS).",
						ImplementationExceptionSeverity.ERROR);
			}
		}
		return -1;
		
	}

	/**
	 * This method returns the start trigger value on the basis of the ec specification.
	 * 
	 * @return start trigger value
	 */
	private String getStartTriggerValue() {
		
		ECTrigger startTrigger = spec.getBoundarySpec().getStartTrigger();
		if (startTrigger != null) {
			return startTrigger.get_value();
		}
		return null;
		
	}
	
	/**
	 * This method returns the stop trigger value on the basis of the ec specification.
	 * 
	 * @return stop trigger value
	 */
	private String getStopTriggerValue() {
		
		ECTrigger stopTrigger = spec.getBoundarySpec().getStopTrigger();
		if (stopTrigger != null) {
			return stopTrigger.get_value();
		}
		return null;
		
	}
	
	/**
	 * This method returns the stabel set interval on the basis of the ec specification.
	 * 
	 * @return stable set interval
	 */
	private long getStableSetInterval() {
		
		ECTime stableSetInterval = spec.getBoundarySpec().getStableSetInterval();
		if (stableSetInterval != null) {
			return stableSetInterval.get_value();
		}
		return -1;
		
	}

	/**
	 * This method validates an ec specification under criterias of chapter 8.2.11 of ALE specification version 1.0.
	 * 
	 * @param spec to validate
	 * @throws ECSpecValidationException if the specification is invalid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	private void validateSpec(ECSpec spec) throws ECSpecValidationException, ImplementationException {
				
		// check if the logical readers are known to the implementation
		String[] logicalReaders = spec.getLogicalReaders();
		if (logicalReaders != null) {
			for (String logicalReaderName : logicalReaders) {
				if (!LogicalReaderManager.contains(logicalReaderName)) {
					throw new ECSpecValidationException("LogicalReader '" + logicalReaderName + "' is unknown.");
				}
			}
		}
		
		// boundaries parameter of ECSpec is null or omitted
		ECBoundarySpec boundarySpec = spec.getBoundarySpec();
		if (boundarySpec == null) {
			throw new ECSpecValidationException("The boundaries parameter of ECSpec is null.");
		}
		
		// start and stop tiggers
		checkTrigger(boundarySpec.getStartTrigger());
		checkTrigger(boundarySpec.getStopTrigger());
		
		// check if duration, stableSetInterval or repeatPeriod is negative
		ECTime time;
		if ((time = boundarySpec.getDuration()) != null) {
			if (time.get_value() < 0) {
				throw new ECSpecValidationException("The duration field of ECBoundarySpec is negative.");
			}
		}
		if ((time = boundarySpec.getStableSetInterval()) != null) {
			if (time.get_value() < 0) {
				throw new ECSpecValidationException("The stableSetInterval field of ECBoundarySpec is negative.");
			}
		}
		if ((time = boundarySpec.getRepeatPeriod()) != null) {
			if (time.get_value() < 0) {
				throw new ECSpecValidationException("The repeatPeriod field of ECBoundarySpec is negative.");
			}
		}
		
		// check if start trigger is non-empty and repeatPeriod is non-zero
		if (boundarySpec.getStartTrigger() != null && boundarySpec.getRepeatPeriod().get_value() != 0) {
			throw new ECSpecValidationException("The startTrigger field of ECBoundarySpec is non-empty and " +
					"the repeatPeriod field of ECBoundarySpec is non-zero.");
		}
		
		// check if a stopping condition is specified
		if (boundarySpec.getStopTrigger() == null && boundarySpec.getDuration() == null &&
				boundarySpec.getStableSetInterval() == null) {
			throw new ECSpecValidationException("No stopping condition is specified in ECBoundarySpec.");
		}
		
		// check if there is a ECReportSpec instance
		if (spec.getReportSpecs() == null || spec.getReportSpecs() == null ||
				spec.getReportSpecs().length == 0) {
			throw new ECSpecValidationException("List of ECReportSpec instances is empty.");
		}
		
		// check if two ECReportSpec instances have identical names
		Set<String> reportSpecNames = new HashSet<String>();
		for (ECReportSpec reportSpec : spec.getReportSpecs()) {
			if (!reportSpecNames.add(reportSpec.getReportName())) {
				throw new ECSpecValidationException("Two ReportSpecs instances have identical names '" +
						reportSpec.getReportName() + "'.");
			}
		}
		
		// check filters
		for (ECReportSpec reportSpec : spec.getReportSpecs()) {
			ECFilterSpec filterSpec = reportSpec.getFilterSpec();
			
			if (filterSpec != null) {
				// check include patterns
				if (filterSpec.getIncludePatterns() != null) {
					for (String pattern : filterSpec.getIncludePatterns()) {
						new Pattern(pattern, PatternUsage.FILTER);
					}
				}
				
				// check exclude patterns
				if (filterSpec.getExcludePatterns() != null) {
					for (String pattern : filterSpec.getExcludePatterns()) {
						new Pattern(pattern, PatternUsage.FILTER);
					}
				}
			}
			
		}
		
		// check grouping patterns
		for (ECReportSpec reportSpec : spec.getReportSpecs()) {
			if (reportSpec.getGroupSpec() != null) {
				for (String pattern1 : reportSpec.getGroupSpec()) {
					Pattern pattern = new Pattern(pattern1, PatternUsage.GROUP);
					for (String pattern2 : reportSpec.getGroupSpec()) {
						if (pattern1 != pattern2 && !pattern.isDisjoint(pattern2)) {
							throw new ECSpecValidationException("The two grouping patterns '" + pattern1 +
									"' and '" + pattern2 + "' are not disjoint.");
						}
					}
				}
			}
		}
		
		// check if there is a output type specified for each ECReportSpec
		for (ECReportSpec reportSpec : spec.getReportSpecs()) {
			ECReportOutputSpec outputSpec = reportSpec.getOutput();
			if (!outputSpec.isIncludeEPC() && !outputSpec.isIncludeTag() && !outputSpec.isIncludeRawHex() &&
					!outputSpec.isIncludeRawDecimal() && !outputSpec.isIncludeCount()) {
				throw new ECSpecValidationException("The ECReportOutputSpec of ReportSpec '" +
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
	private void checkTrigger(ECTrigger trigger) throws ECSpecValidationException {
		
		// TODO: implement checkTrigger
		
	}
	
}