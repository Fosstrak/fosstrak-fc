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

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.server.type.FileSubscriberOutputChannel;
import org.fosstrak.ale.server.type.HTTPSubscriberOutputChannel;
import org.fosstrak.ale.server.type.SubscriberOutputChannel;
import org.fosstrak.ale.server.type.TCPSubscriberOutputChannel;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
//import org.fosstrak.ale.util.SerializerUtil;

/**
 * This class represents a subscriber of an ec specification.
 * There are three types of such subscribers: http, tcp, file
 * 
 * This class parses and validates the notification url, formats the ec reports and notifies the subscriber.
 * 
 * @author regli
 */
public class Subscriber {

	/** logger */
	private static final Logger LOG = Logger.getLogger(Subscriber.class);
	
	/** http uri prefix */
	private static final String HTTP_PREFIX = "http";
	/** tcp uri prefix */
	private static final String TCP_PREFIX = "tcp";
	/** file uri prefix */
	private static final String FILE_PREFIX = "file";

	/** number representing the protocol of this subscriber */
	private final String protocol;
	/** handle on the helper delivering the report */
	private SubscriberOutputChannel subscriberOutputChannel;

	/**
	 * Constructor parses and validates the notification uri and creates the corresponding subscriber.
	 * 
	 * @param notificationURI of the subscriber 
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public Subscriber(String notificationURI) throws InvalidURIException {
		try {
			String[] parts = notificationURI.split(":");
			protocol = parts[0];
		} catch (Exception ex) {
			throw new InvalidURIException("illegal notification URI: " + notificationURI, ex);
		}

		if (HTTP_PREFIX.equals(protocol)) {
			LOG.debug("using http subscriber output channel: " + notificationURI);
			subscriberOutputChannel = new HTTPSubscriberOutputChannel(notificationURI);
		} else if (TCP_PREFIX.equals(protocol)) {
			LOG.debug("using tcp subscriber output channel: " + notificationURI);
			subscriberOutputChannel = new TCPSubscriberOutputChannel(notificationURI);
		} else if (FILE_PREFIX.equals(protocol)) {
			LOG.debug("using file subscriber output channel: " + notificationURI);
			subscriberOutputChannel = new FileSubscriberOutputChannel(notificationURI);
		} else {
			// invalid url
			throw new InvalidURIException("Invalid protocol.");
		}
	}
	
	/**
	 * This method indicates if this subscriber uses the http protocol.
	 * 
	 * @return true if this subscriber uses the http protocol and false otherwise
	 */
	public boolean isHttp() {
		return HTTP_PREFIX.equalsIgnoreCase(protocol);		
	}
	
	/**
	 * This method indicates if this subscriber uses the tcp protocol.
	 * 
	 * @return true if this subscriber uses the tcp protocol and false otherwise
	 */
	public boolean isTcp() {
		return TCP_PREFIX.equalsIgnoreCase(protocol);
	}
	
	/**
	 * This method indicates if this subscriber uses the file protocol.
	 * 
	 * @return true if this subscriber uses the file protocol and false otherwise
	 */
	public boolean isFile() {
		return FILE_PREFIX.equalsIgnoreCase(protocol);
	}
	
	@Override
	public String toString() {
		return "[" + this.getClass().getName() + ", output channel: " + subscriberOutputChannel.toString() + "]"; 
	}
	
	/**
	 * This method notifies the subscriber about the ec reports.
	 * 
	 * @param reports to notify the subscriber about
	 * @throws ImplementationException if an implementation exception occures
	 */
	public void notify(ECReports reports) throws ImplementationException {
		subscriberOutputChannel.notify(reports);
	}

	/**
	 * allow to inject a new output channel for this subscriber.
	 * @param subscriberOutputChannel the new output channel that shall be used for this subscriber.
	 */
	public void setSubscriberOutputChannel(SubscriberOutputChannel subscriberOutputChannel) {
		this.subscriberOutputChannel = subscriberOutputChannel;
	}
}
