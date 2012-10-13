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

import java.util.List;

import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;

/**
 * This interface generates ec reports.
 * It validates the ec specifications, starts and stops the event cycles and manages the subscribers.  
 * 
 * @author regli
 * @author swieland
 * @author benoit.plomion@orange.com
 */
public interface ReportsGenerator {
		
	/**
	 * This method returns the ec specification of this generator.
	 * 
	 * @return ec specification
	 */
	ECSpec getSpec();
	
	/**
	 * sets the reports generator to state requested.
	 */
	public void setStateRequested();
	
	/**
	 * sets the reports generator to state unrequested.
	 */
	public void setStateUnRequested();
	
	/**
	 * whether the report generators state is requested or not.
	 * @return true if requested, false in any other state.
	 */
	public boolean isStateRequested();

	/**
	 * whether the report generators state is unrequested or not.
	 * @return true if unrequested, false in any other state.
	 */
	public boolean isStateUnRequested();
	
	/**
	 * This method subscribes a notification uri of a subscriber to this 
	 * report generator. 
	 * @param notificationURI to subscribe
	 * @throws DuplicateSubscriptionException if the specified notification uri 
	 * is already subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	void subscribe(String notificationURI) throws DuplicateSubscriptionException, InvalidURIException;
	
	/**
	 * This method unsubscribes a notification uri of a subscriber from this 
	 * report generator.
	 * @param notificationURI to unsubscribe
	 * @throws NoSuchSubscriberException if the specified notification uri is 
	 * not yet subscribed
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	void unsubscribe(String notificationURI) throws NoSuchSubscriberException, InvalidURIException;
	
	/**
	 * This method return the notification uris of all the subscribers of this 
	 * report generator.
	 * @return list of notification uris
	 */
	List<String> getSubscribers();
	
	/**
	 * This method notifies all subscribers of this report generator about the 
	 * specified ec reports.
	 * @param reports to notify the subscribers about
	 */
	void notifySubscribers(ECReports reports, EventCycle ec);
	
	/**
	 * This method is invoked if somebody polls this report generator.
	 * The result of the polling can be picked up by the method getPollReports.
	 */
	void poll();
	
	/**
	 * This method delivers the ec reports which have been generated because 
	 * of a poll.
	 * @return ec reports
	 */
	ECReports getPollReports();
	
	/**
	 * This method returns the name of this reports generator.
	 * 
	 * @return name of reports generator
	 */
	String getName();
	
}