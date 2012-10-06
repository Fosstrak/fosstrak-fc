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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.server.util.ECReportsHelper;
import org.fosstrak.ale.server.util.ECSpecValidator;
import org.fosstrak.ale.util.ECTimeUnit;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports.Reports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;

import com.rits.cloning.Cloner;

/**
 * This class generates ec reports.
 * It validates the ec specifications, starts and stops the event cycles and manages the subscribers.  
 * 
 * @author regli
 * @author swieland
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
	private final Map<String, Subscriber> subscribers = new ConcurrentHashMap<String, Subscriber>();
	
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
	private boolean polling = false;
	
	/** ec report for the poller */
	private ECReports pollReport = null;
	
	
	private EventCycle eventCycle = null;

	private ECReportsHelper reportsHelper;
	
	/**
	 * Constructor validates the ec specification and sets some parameters.
	 * 
	 * @param name of this reports generator
	 * @param spec which defines how the reports of this generator should be build
	 * @throws ECSpecValidationException if the ec specification is invalid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public ReportsGenerator(String name, ECSpec spec) throws ECSpecValidationException, ImplementationException {
		this(name, spec, ALEApplicationContext.getBean(ECSpecValidator.class), ALEApplicationContext.getBean(ECReportsHelper.class));
		
	}
	/**
	 * Constructor validates the ec specification and sets some parameters.
	 * 
	 * @param name of this reports generator
	 * @param spec which defines how the reports of this generator should be build
	 * @param validator the ECSpec validator to use for the validation of the ECSpec.
	 * @throws ECSpecValidationException if the ec specification is invalid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public ReportsGenerator(String name, ECSpec spec, ECSpecValidator validator, ECReportsHelper reportsHelper) throws ECSpecValidationException, ImplementationException {

		LOG.debug("Try to create new ReportGenerator '" + name + "'.");
				
		// set name
		this.name = name;
		
		this.reportsHelper = reportsHelper;
		
		// set spec
		try {
			validator.validateSpec(spec);
		} catch (ECSpecValidationException e) {
			LOG.error(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
			throw e;
		} catch (ImplementationException e) {
			LOG.error(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
			throw e;
		}
		this.spec = spec;
		
		// init boundary spec values
		startTriggerValue = getStartTriggerValue();
		stopTriggerValue = getStopTriggerValue();
		repeatPeriodValue = getRepeatPeriodValue();
		stableSetInterval = getStableSetInterval();
		
		LOG.debug(String.format("[startTriggerValue: %s, stopTriggerValue: %s, repeatPeriodValue: %s, stableSetInterval: %s]",
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
	 * This method subscribes a notification uri of a subscriber to this 
	 * report generator. 
	 * @param notificationURI to subscribe
	 * @throws DuplicateSubscriptionException if the specified notification uri 
	 * is already subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public void subscribe(String notificationURI) throws DuplicateSubscriptionException, InvalidURIException {
		
		Subscriber uri = new Subscriber(notificationURI);
		if (subscribers.containsKey(notificationURI)) {
			throw new DuplicateSubscriptionException(String.format("the URI is already subscribed on this specification %s, %s", name, uri));
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
	public void unsubscribe(String notificationURI) throws NoSuchSubscriberException, InvalidURIException {
		// validate the URI:
		new Subscriber(notificationURI);
		
		if (subscribers.containsKey(notificationURI)) {
			subscribers.remove(notificationURI);
			LOG.debug("NotificationURI '" + notificationURI	+ "' unsubscribed from spec '" + name + "'.");
			
			if (subscribers.isEmpty() && !isPolling()) {
				setState(ReportsGeneratorState.UNREQUESTED);
			}
		} else {
			throw new NoSuchSubscriberException("there is no subscriber on the given notification URI: " + notificationURI);
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
	 * This method notifies all subscribers of this report generator about the 
	 * specified ec reports.
	 * @param reports to notify the subscribers about
	 */
	public void notifySubscribers(ECReports reports, EventCycle ec) {		
		
//		according the ALE 1.1 standard:
//		When the processing of reportIfEmpty and reportOnlyOnChange
//		results in all ECReport instances being omitted from an 
//		ECReports for an event cycle, then the delivery of results
//		to subscribers SHALL be suppressed altogether. [...] poll 
//		and immediate SHALL always be returned [...] even if that 
//		ECReports instance contains zero ECReport instances.
		
//		An ECReports instance SHALL include an ECReport instance corresponding to each
//		ECReportSpec in the governing ECSpec, in the same order specified in the ECSpec,
//		except that an ECReport instance SHALL be omitted under the following circumstances:
//		- If an ECReportSpec has reportIfEmpty set to false, then the corresponding
//		  ECReport instance SHALL be omitted from the ECReports for this event cycle if
//		  the final, filtered set of Tags is empty (i.e., if the final Tag list would be empty, or if
//		  the final count would be zero).
//		- If an ECReportSpec has reportOnlyOnChange set to true, then the
//		  corresponding ECReport instance SHALL be omitted from the ECReports for
//		  this event cycle if the filtered set of Tags is identical to the filtered prior set of Tags,
//		  where equality is tested by considering the primaryKeyFields as specified in the
//		  ECSpec (see Section 8.2), and where the phrase 'the prior set of Tags' is as defined
//		  in Section 8.2.6. This comparison takes place before the filtered set has been modified
//		  based on reportSet or output parameters. The comparison also disregards
//		  whether the previous ECReports was actually sent due to the effect of this
//		  parameter, or the reportIfEmpty parameter.
//		When the processing of reportIfEmpty and reportOnlyOnChange results in all
//		ECReport instances being omitted from an ECReports for an event cycle, then the
//		delivery of results to subscribers SHALL be suppressed altogether. That is, a result
//		consisting of an ECReports having zero contained ECReport instances SHALL NOT
//		be sent to a subscriber. (Because an ECSpec must contain at least one
//		ECReportSpec, this can only arise as a result of reportIfEmpty or
//		reportOnlyOnChange processing.) This rule only applies to subscribers (event cycle
//		requestors that were registered by use of the subscribe method); an ECReports
//		instance SHALL always be returned to the caller of immediate or poll at the end of
//		an event cycle, even if that ECReports instance contains zero ECReport instances.
		
		
		Cloner cloner  = new Cloner();
		// deep clone the original input in order to keep it as the 
		// next event cycles last cycle reports. 
		ECReports originalInput = cloner.deepClone(reports);
		
		// we deep clone (clone not sufficient) for the pollers
		// in order to deliver them the correct set of reports.
		if (isPolling()) {
			// deep clone for the pollers (poll and immediate)
			pollReport = cloner.deepClone(reports);
		}

		// we remove the reports that are equal to the ones in the 
		// last event cycle. then we send the subscribers.
		List<ECReport> equalReps = new LinkedList<ECReport> ();
		List<ECReport> reportsToNotify = new LinkedList<ECReport> ();
		try {
		for (ECReport r : reports.getReports().getReport()) {
			final ECReportSpec reportSpec = ec.getReportSpecByName(r.getReportName());
			
			boolean tagsInReport = hasTags(r);
			// case no tags in report but report if empty
			if (!tagsInReport && reportSpec.isReportIfEmpty()) {
				LOG.debug("requesting empty for report: " + r.getReportName());
				reportsToNotify.add(r);
			} else if (tagsInReport) {
				reportsToNotify.add(r);
			}
			// check for equal reports since last notification.
			if (reportSpec.isReportOnlyOnChange()) {
				// report from the previous EventCycle run.
				ECReport oldR = ec.getLastReports().get(r.getReportName());
				
				// compare the new report with the old one.
				if (reportsHelper.areReportsEqual(reportSpec, r, oldR)) {
					equalReps.add(r);
				}
			}
		}
		} catch (Exception e) {
			LOG.error("caught exception while processing reports: ", e);
		}
		
		// check if the intersection of all reports to notify (including empty ones) and the equal ones is empty
		// -> if so, do not notify at all.
		reportsToNotify.removeAll(equalReps);
		
		// remove the equal reports
		Reports re = reports.getReports();
		if (null != re) re.getReport().removeAll(equalReps);
		LOG.debug("reports size: " + reports.getReports().getReport().size());

		// next step is to check, if the total report is empty (even if requestIfEmpty but when all reports are equal, do not deliver) 
		if (reportsToNotify.size() > 0) {
			// notify the ECReports
			notifySubscribersWithFilteredReports(reports);
		}		
		// store the new reports as old reports
		ec.getLastReports().clear();
		if (null != originalInput.getReports()) {
			for (ECReport r : originalInput.getReports().getReport()) {
				ec.getLastReports().put(r.getReportName(), r);
			}
		}
		
		// notify pollers
		// pollers always receive reports (even when empty).
		if (isPolling()) {
			polling = false;
			if (subscribers.isEmpty()) {
				setState(ReportsGeneratorState.UNREQUESTED);
			}
			synchronized (this) {
				this.notifyAll();
			}
		}	
	}
	
	/**
	 * check if a given ECReport contains at least one tag in its data structures.
	 * @param r the report to check.
	 * @return true if tags contained, false otherwise.
	 */
	private boolean hasTags(ECReport r) {
		try {
			for (ECReportGroup g : r.getGroup()) {
				if (g.getGroupList().getMember().size() > 0) {
					return true;
				}
			}
		} catch (Exception ex) {
			LOG.debug("could not check for tag occurence - report considered not to containing tags", ex);
		}
		return false;
	}
	/**
	 * once all the filtering is done eventually notify the subscribers with the reports.
	 * @param reports the filtered reports.
	 */
	protected void notifySubscribersWithFilteredReports(ECReports reports) {
		// notify subscribers 
		for (Subscriber listener : subscribers.values()) {
			try {
				listener.notify(reports);
			} catch (Exception e) {
				LOG.error("Could not notify subscriber '" + listener.toString(), e);
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
		polling = true;
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
	protected void start() {
		
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
		} catch (ImplementationException e) {
			LOG.error("could not create a new EventCycle: ", e);
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
					synchronized (state) {
						while (state != ReportsGeneratorState.REQUESTED) {
							try {
									state.wait();
							} catch (InterruptedException e) {
								LOG.debug("caught interrupted exception", e);
								return;
							}
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
	private long getRepeatPeriodValue() throws ImplementationException {
		
		ECTime repeatPeriod = spec.getBoundarySpec().getRepeatPeriod();
		if (repeatPeriod != null) {
			if (repeatPeriod.getUnit().compareToIgnoreCase(ECTimeUnit.MS) != 0) {
				throw new ImplementationException("The only ECTimeUnit allowed is milliseconds (MS).");
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
	 * is the reports generator in polling state???
	 * @return true if polling, false otherwise.
	 */
	protected boolean isPolling() {
		return polling;
	}
	
	/**
	 * the poll reports - <strong>Attention></strong> not null safe.
	 * @return the poll reports - <strong>Attention></strong> not null safe.
	 */
	protected ECReports getPollReport() {
		return pollReport;
	}
	
}