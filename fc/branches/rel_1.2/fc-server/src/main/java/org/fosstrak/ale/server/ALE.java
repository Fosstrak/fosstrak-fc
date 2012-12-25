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
 * Lesser General License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.server;

import java.util.Map;

import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;

/**
 * This class represents the application level events interface.
 * All ale operations are executed by this class.
 * 
 * @author regli
 * @author swieland
 * @author haennimi
 * @author benoit.plomion@orange.com
 */
public interface ALE {

	/**
	 * This method indicates if the ALE is ready or not.
	 * 
	 * @return true if the ALE is ready and false otherwise
	 */
	boolean isReady();
	
	/**
	 * With this method an ec specification can be defined.
	 * 
	 * @param specName of the ec specification
	 * @param spec to define
	 * @throws DuplicateNameException if a ec specification with the same name is already defined
	 * @throws ECSpecValidationException if the ec specification is not valid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	void define(String specName, ECSpec spec) throws DuplicateNameException, ECSpecValidationException, ImplementationException;
	
	/**
	 * With this method an ec specification can be undefined.
	 * 
	 * @param specName of the ec specification to undefine
	 * @throws NoSuchNameException if there is no ec specification with this name defined
	 */
	void undefine(String specName) throws NoSuchNameException;
	
	/**
	 * This method returns an ec specification depending on a given name.
	 * 
	 * @param specName of the ec specification to return
	 * @return ec specification with the specified name
	 * @throws NoSuchNameException if no such ec specification exists
	 */
	ECSpec getECSpec(String specName) throws NoSuchNameException;
	
	/**
	 * This method returns the names of all defined ec specifications.
	 * 
	 * @return string array with names
	 */
	String[] getECSpecNames();
	
	/**
	 * With this method a notification uri can be subscribed to a defined ec specification.
	 * 
	 * @param specName of the ec specification
	 * @param notificationURI to subscribe
	 * @throws NoSuchNameException if there is no ec specification with the given name defined
	 * @throws InvalidURIException if the specified notification uri is invalid
	 * @throws DuplicateSubscriptionException if the same subscription is already done
	 */
	void subscribe(String specName, String notificationURI) throws NoSuchNameException, InvalidURIException, DuplicateSubscriptionException;

	/**
	 * With this method a notification uri can be unsubscribed from a defined ec specification.
	 * 
	 * @param specName of the ec specification
	 * @param notificationURI to unsubscribe
	 * @throws NoSuchNameException if there is no ec specification with the given name defined
	 * @throws NoSuchSubscriberException if the specified notification uri is not subscribed to the ec specification.
	 * @throws InvalidURIException if the specified notification uri is invalid
	 */
	void unsubscribe(String specName, String notificationURI) throws NoSuchNameException, NoSuchSubscriberException, InvalidURIException;

	/**
	 * With this method a defined ec specification can be polled.
	 * Polling is the same as subscribe to a ec specification, waiting for one event cycle and then unsubscribe
	 * with the difference that the report is the result of the method instead of sending it to an uri.
	 * 
	 * @param specName of the ec specification which schould be polled
	 * @return ec report of the next event cycle
	 * @throws NoSuchNameException if there is no ec specification with the given name defined
	 */
	ECReports poll(String specName) throws NoSuchNameException;
	
	/**
	 * With this method a undefined ec specifcation can be executed.
	 * It's the same as defining the ec specification, polling and undefining it afterwards.
	 * 
	 * @param spec ec specification to execute
	 * @return ec report of the next event cycle
	 * @throws ECSpecValidationException if the ec specification is not valid
	 * @throws ImplementationException if an implementation exception occures
	 */
	ECReports immediate(ECSpec spec) throws ECSpecValidationException, ImplementationException;
	
	/**
	 * This method returns all subscribers to a given ec specification name.
	 * 
	 * @param specName of which the subscribers should be returned
	 * @return array of string with notification uris
	 * @throws NoSuchNameException if there is no ec specification with the given name is defined
	 */
	String[] getSubscribers(String specName) throws NoSuchNameException;
		
	/**
	 * This method returns the standard version to which this implementation is compatible.
	 * 
	 * @return standard version
	 */
	String getStandardVersion();
	
	/**
	 * This method returns the vendor version of this implementation.
	 * 
	 * @return vendor version
	 */
	String getVendorVersion();
	
	/**
	 * This method closes the ale and remove all input generators and there objects on the reader devices.
	 */
	void close();
	
	/**
	 * returns a handle on the report generators in the ALE.<br/>this method never returns null. 
	 * @return a handle on the report generators in the ALE.
	 */
	//ORANGE: add for ALEController ws
	Map<String, ReportsGenerator> getReportGenerators();
	
}