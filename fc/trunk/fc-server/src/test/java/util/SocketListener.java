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

package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * helper class to test classes/processes that have to send data through a socket connection.
 * @author regli
 * @author swieland
 *
 */
public class SocketListener implements Runnable {

	/** logger */
	private static final Logger LOG = Logger.getLogger(SocketListener.class);
	
	private final Thread thread;	
	private ServerSocket serverSocket;	
	private InputStream inputStream;
	private boolean keepRunning;
	
	/**
	 * create a server socket on the given port and start a new thread with this runnable as input.
	 * @param port the port where to start the server socket on.
	 * @throws IOException when the socket cannot be created.
	 */
	public SocketListener(int port) throws IOException {
		keepRunning = true;
		// create tcp server socket
		serverSocket = new ServerSocket(port);

		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * stop the server socket and stop the processing thread.
	 */
	public void stop() {
		keepRunning = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			LOG.error("caught IOException: ", e);
		}
	}
	
	/**
	 * returns the input stream of the last client socket.
	 * @return returns the input stream of the last client socket.
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void run() {
		while (!serverSocket.isClosed() && keepRunning) {
			try {
				// wait for connection				
				Socket socket = serverSocket.accept();
				
				// get inputstream
				inputStream = socket.getInputStream();
			} catch (IOException e) {
				LOG.debug("caught IOException: ", e);
			}
		}
	}	
}