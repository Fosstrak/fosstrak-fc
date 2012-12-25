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

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;

/**
 * abstract class that can be used in order to send a given message using a socket.
 * @author swieland
 */
public abstract class AbstractSocketSubscriberOutputChannel extends AbstractSubscriberOutputChannel {
	
	
	public AbstractSocketSubscriberOutputChannel(String notificationURI) {
		super(notificationURI);
	}

	/** logger */
	private static final Logger LOG = Logger.getLogger(AbstractSocketSubscriberOutputChannel.class);

	/**
	 * This method writes data to a socket with host name and port number of this subscriber.
	 * 
	 * @param data to write to the socket
	 * @throws ImplementationException if an implementation exception occures
	 */
	protected void writeToSocket(String data) throws ImplementationException {		
		Socket socket;
		try {
			
			// open socket and stream
			socket = getSocket();
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			// write reports
			dataOutputStream.writeBytes(data);
			dataOutputStream.write("\n".getBytes());
			dataOutputStream.flush();
			
			// close socket and stream
			dataOutputStream.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			LOG.error("unknown host: ", e);
			throw new ImplementationException("Host '" + getHost() + "' not found.", e);
		} catch (IOException e) {
			LOG.error("io exception: ", e);
			throw new ImplementationException("Could not write data to socket at '" + getHost() + ":" + getPort() + "'.", e);
		}		
	}
	
	/**
	 * @return get a socket for the connection.
	 * @throws IOException some other io issue.
	 * @throws UnknownHostException unknown host. 
	 */
	protected Socket getSocket() throws UnknownHostException, IOException {
		return new Socket(getHost(), getPort());
	}
	
	/**
	 * @return the host to be use in order to establish the connection.
	 */
	public abstract String getHost();
	
	/**
	 * @return the port to use in order to establish the connection.
	 */
	public abstract int getPort();
	
}

