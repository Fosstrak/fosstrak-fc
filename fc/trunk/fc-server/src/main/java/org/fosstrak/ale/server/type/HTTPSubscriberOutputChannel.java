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

import java.net.URL;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;

import com.mysql.jdbc.StringUtils;

/**
 * send message as HTTP POST request using a standard socket.
 * @author swieland
 */
public class HTTPSubscriberOutputChannel extends AbstractSocketSubscriberOutputChannel {

	/** logger */
	private static final Logger LOG = Logger.getLogger(HTTPSubscriberOutputChannel.class);

	/** default port */
	private static final int DEFAULT_PORT = 80;
	
	private final URL url;
	private final String host;
	private final int port;	
	private final String path;
	
	public HTTPSubscriberOutputChannel(String notificationURI) throws InvalidURIException {
		super(notificationURI);
		try {
			url = new URL(notificationURI);
			host = url.getHost();
			port = (url.getPort() == -1) ? DEFAULT_PORT : url.getPort();
			path = StringUtils.startsWithIgnoreCase(url.getPath(), "/") ? url.getPath().substring(1) : url.getPath();
		} catch (Exception e) {
			LOG.error("malformed URL: ", e);
			throw new InvalidURIException("malformed URL: ", e);
		}
	}
	
	@Override
	public boolean notify(ECReports reports) throws ImplementationException {			
		LOG.debug("Write reports '" + reports.getSpecName() + "' as post request to http socket '" + getHost() + ":" + getPort() + "'.");
		writeToSocket(getPostRequest(reports));
		return true;
	}
	
	
	/**
	 * This method creates a post request from ec reports containing an xml representation of the reports.
	 * 
	 * @param reports to transform into a post request
	 * @return post request containing an xml representation of the reports
	 * @throws ImplementationException if an implementation exception occurs
	 */
	private String getPostRequest(ECReports reports) throws ImplementationException {
		
		LOG.debug("Create POST request with reports '" + reports.getSpecName() + "'.");
		
		// create body
		String body = getXml(reports);
		
		// create header
		StringBuffer header = new StringBuffer();
		
		// append request line
		header.append("POST ");
		// add the trimmed / again together with a white space
		String p = "/" + getPath() + " ";
		header.append(p);
			
		header.append("HTTP/1.1");
		header.append("\n");
		
		// append host
		header.append("Host: ");
		// append port 
		// patch by Gianrico D'Angelis <gianrico.dangelis@gmail.com>  
		header.append(getHost() + ":" + getPort());  
		header.append("\n");
		
    
      
		// append content type
		header.append("Content-Type: ");
		header.append("text/xml; charset=\"utf-8\"");
		header.append("\n");
		
		// append content length
		header.append("Content-Length: ");
		header.append(body.length());
		header.append("\n");
		
		// terminate body
		header.append("\n");

		return header + body;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return url.toString();
	}
	
	
}

