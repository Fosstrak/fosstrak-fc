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

package org.accada.ale.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.accada.ale.util.DeserializerUtil;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.reader.proxy.RPProxyException;
import org.apache.log4j.Logger;

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
	public ReportHandler(int port) throws ImplementationException {
		
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
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
		
		try {
			while (true) {
				Socket s = ss.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				StringBuffer data = new StringBuffer();
				String line = null;
				while (!reader.ready()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				while (!"".equals(line) && reader.ready()) {
					line = reader.readLine();
					data.append(line);
				};
				log.debug("Incoming ecReports: " + data);
				notifyListeners(data);
				s.close();
			}
		} catch (Exception e) {}
		
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
	public static void main(String[] args) throws ImplementationException {
		
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
	 * This method parses the data to a ec reports and notifies all subscribers about the newly received ec reports.
	 * 
	 * @param data string buffer with ec reports as string
	 * @throws Exception 
	 */
	private void notifyListeners(StringBuffer data) throws Exception {
		
		ECReports ecReports = null;
		ecReports = DeserializerUtil.deserializeECReports(new ByteArrayInputStream(data.toString().getBytes()));
			
		Iterator listenerIt = listeners.iterator();
		while (listenerIt.hasNext()) {
			((ReportHandlerListener)listenerIt.next()).dataReceived(ecReports);
		}
	
	}

}