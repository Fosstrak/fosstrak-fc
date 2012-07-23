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
package org.fosstrak.ale.server.type;

import java.net.URI;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

/**
 * send message using a standard TCP socket.
 * @author swieland
 */
public class TCPSubscriberOutputChannel extends AbstractSocketSubscriberOutputChannel {

	/** logger */
	private static final Logger LOG = Logger.getLogger(TCPSubscriberOutputChannel.class);
	
	private URI uri;
	private final String host;
	private final int port;	
	
	public TCPSubscriberOutputChannel(String notificationURI) throws InvalidURIException {
		super(notificationURI);
		try {
			uri = new URI(notificationURI);
			host = uri.getHost();
			port = uri.getPort();
			if (!("TCP".equalsIgnoreCase(uri.getScheme()))) {
				LOG.error("invalid scheme: " + uri.getScheme());
				throw new InvalidURIException("invalid scheme: " + uri.getScheme());
			}
		} catch (Exception e) {
			LOG.error("malformed URI");
			throw new InvalidURIException("malformed URI: ", e);
		} 
	}
	
	@Override
	public boolean notify(ECReports reports) throws ImplementationException {
		LOG.debug("Write reports '" + reports.getSpecName() + "' as xml to tcp socket '" + getHost() + ":" + getPort() + "'.");
		writeToSocket(getXml(reports));
		return true;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return uri.toString();
	}
	
}

