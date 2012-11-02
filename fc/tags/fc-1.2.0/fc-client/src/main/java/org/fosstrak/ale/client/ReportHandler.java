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

package org.fosstrak.ale.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.reader.rp.proxy.RPProxyException;

/**
 * This class listen to a specified port for ec reports and notifies his subscribers about new ec reports.
 * 
 * @author regli
 */
public class ReportHandler implements Runnable {

	/** the logger */
	private static Logger log = Logger.getLogger(ReportHandler.class);
	
	/** the thread */
	private final Thread thread;
	
	/** contains the subscribers of this ReportHandler */
	private final List<ReportHandlerListener> listeners = new Vector<ReportHandlerListener>();
	
	/** server socket to communicate with the ALE */
	private final ServerSocket ss;

	/**
	 * Constructor opens the server socket and starts the thread.
	 * 
	 * @param port on which the ALE notifies
	 * @throws ImplementationException if server socket could not be created on specified port.
	 */
	public ReportHandler(int port) throws ImplementationExceptionResponse {
		
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			throw new ImplementationExceptionResponse(e.getMessage());
		}
		
		thread = new Thread(this);
		thread.start();
		
	}
	
	/**
	 * This mehtod adds a new subscriber to the list of listeners.
	 * 
	 * @param listener to add to this ReportHandler
	 */
	public void addListener(ReportHandlerListener listener) {
		
		listeners.add(listener);
		
	}
	
	/**
	 * This method removes a subscriber from the list of listeners.
	 * 
	 * @param listener to remove from this ReportHandler
	 */
	public void removeListener(ReportHandlerListener listener) {
		
		listeners.remove(listener);
		
	}
	
	/**
	 * This method contains the main loop of the thread, in which data is read from the socket
	 * and forwarded to the method notifyListeners().
	 */
	public void run() {
		while (true) {
			try {
				Socket s = ss.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
				String data = in.readLine();
				// ignore the HTTP header
				data = in.readLine();
				data = in.readLine();
				data = in.readLine();
				data = in.readLine();

				StringBuffer buffer = new StringBuffer();
				while (null != data) {
					buffer.append(data);
					data = in.readLine();
				}
				log.debug(buffer.toString());

				// create a stream from the buffer
				InputStream parseStream = new ByteArrayInputStream(
						buffer.toString().getBytes());

				// parse the string
				ECReports reports = DeserializerUtil.deserializeECReports(parseStream);
				if (null != reports) {
					notifyListeners(reports);
				}
				s.close();
			} catch (Exception e) {
				log.error(String.format("Could not receive report: %s", 
						e.getMessage()));
			}
		}

	}
	
	/**
	 * This method stops the thread and closes the socket
	 */
	public void stop() {
		
		// stop thread
		if (thread.isAlive()) {
			thread.interrupt();
		}
		
		// close socket
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method starts the ReportHandler.
	 * 
	 * @param args command line arguments, which can contain the port number
	 * @throws RPProxyException if something goes wrong while creating the ReportHandler
	 */
	public static void main(String[] args) throws ImplementationExceptionResponse {
		
		int port = 9000;
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {}
		}
		new ReportHandler(port);
		
	}
	
	//
	// private
	//
	
	/**
	 * dispatch the reports.
	 * 
	 * @param ecReports the reports.
	 * @throws Exception 
	 */
	private void notifyListeners(ECReports ecReports) throws Exception {
			
		Iterator<ReportHandlerListener> listenerIt = listeners.iterator();
		while (listenerIt.hasNext()) {
			listenerIt.next().dataReceived(ecReports);
		}
	
	}

}