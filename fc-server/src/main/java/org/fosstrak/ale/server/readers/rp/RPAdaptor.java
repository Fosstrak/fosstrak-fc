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

package org.accada.ale.server.readers.rp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import org.accada.ale.server.Tag;
import org.accada.ale.server.readers.BaseReader;
import org.accada.ale.server.readers.LRSpec;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.reader.hal.HardwareException;
import org.accada.reader.hal.Observation;

/**
 * represents an adaptor to the RP reader.
 * @author sawielan
 *
 */
public class RPAdaptor extends BaseReader {
	
	/** logger. */
	private static final Logger LOG = Logger.getLogger(RPAdaptor.class);
		
	/** input generator for the RP that establishes connection and receives tags. */
	private InputGenerator inputGenerator = null;
	
	/** physicalSourceStub for the current reader. */
	private Map<String, PhysicalSourceStub> physicalSourceStubs = new HashMap<String, PhysicalSourceStub>();
	
	/** the host where commands shall be sent. */
	private String commandChannelHost = null;
	
	/** the port where to connect to. */
	private int commandChannelPort = -1;
	
	/** the host where notifications shall be sent. */
	private String notificationChannelHost = null;
	
	/** the corresponding port. */
	private int notificationChannelPort = -1;
	
	/** the intervall in which shall be read from the reader. */
	private int readTimeIntervall = -1;
	
	/**
	 * constructor for the RP adaptor.
	 */
	public RPAdaptor() {
		super();
	}

	/**
	 * initializes a RPAdaptor. this method must be called befor the Adaptor can
	 * be used.
 	 * @param name the name for the reader encapsulated by this adaptor.
	 * @param spec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.

	 */
	public void initialize(String name, LRSpec spec) throws ImplementationException {
		try {
			super.initialize(name, spec);
		} catch (ImplementationException ie) {
			LOG.error("error in initialize of superclass");
			throw ie;
		}
		
		try {
			extractConnectionSettings();
		} catch (ImplementationException ie) {
			ie.printStackTrace();
			LOG.error("could not extract connection settings from LRSpec");
			throw new ImplementationException();
		}
	}
	
	/**
	 * this method extracts the connection settings from the LRProperty.
	 * if necessary the reader will be restarted
	 * @throws ImplementationException whenever an error occurs
	 */
	private void extractConnectionSettings() throws ImplementationException {
		String connectionPoint = logicalReaderProperties.get("ConnectionPoint");
		String notificationPoint = logicalReaderProperties.get("NotificationPoint");
		String intervall = logicalReaderProperties.get("ReadTimeIntervall");
		
		String [] commandChannelParts = null;
		try {
			// parse the uri from the form http://localhost:8080
			commandChannelParts = connectionPoint.split("[:]");
		} catch (NullPointerException ne) {
			LOG.error("could not extract connectionPoint from LRPropery");
			throw new ImplementationException("could not extract connectionPoint from LRPropery", ImplementationExceptionSeverity.ERROR);
		}
		
		String [] notificationChannelParts = null;		
		try {
			// parse the uri from the form http://localhost:8080
			notificationChannelParts = notificationPoint.split("[:]");
		} catch (NullPointerException ne) {
			LOG.error("could not extract notificationPoint from LRPropery");
			throw new ImplementationException("could not extract notificationPoint from LRPropery", ImplementationExceptionSeverity.ERROR);
		}

		
		String ncommandChannelHost = commandChannelParts[1].replaceAll("/", "");
		int ncommandChannelPort = Integer.parseInt(commandChannelParts[2]);
		
		String nnotificationChannelHost = notificationChannelParts[1].replaceAll("/", "");
		int nnotificationChannelPort = Integer.parseInt(notificationChannelParts[2]);

		int nreadTimeIntervall = -1;
		try {
			nreadTimeIntervall = Integer.parseInt(intervall);
		} catch (Exception ne) {
			LOG.error("could not extract readTimeIntervall from LRPropery");
			throw new ImplementationException("could not extract notificationPoint from LRPropery", ImplementationExceptionSeverity.ERROR);
		}
		
		
		
		boolean disconn = false;
		if (!ncommandChannelHost.equals(commandChannelHost)) {
			disconn = true;
		}
		if (!nnotificationChannelHost.equals(notificationChannelHost)) {
			disconn = true;
		}
		if (ncommandChannelPort != commandChannelPort) {
			disconn = true; 
		}
		if (nnotificationChannelPort != notificationChannelPort) {
			disconn = true;
		}
		
		LOG.debug("readTimeIntervall " + nreadTimeIntervall);
		LOG.debug(String.format("commandChannelHost %s on port %d", ncommandChannelHost, ncommandChannelPort));
		LOG.debug(String.format("notificationChannelHost %s on port %d", nnotificationChannelHost, nnotificationChannelPort));

		readTimeIntervall = nreadTimeIntervall;
		
		if (disconn) {
			disconnectReader();
			
			commandChannelHost = ncommandChannelHost;
			notificationChannelHost = nnotificationChannelHost;
			commandChannelPort = ncommandChannelPort;
			notificationChannelPort = nnotificationChannelPort;
			
			connectReader();
		}
	}
	
	
	/**
	 * sets up a reader.
	 * @throws ImplementationException whenever an internal error occured
	 */
	@Override
	public void connectReader() throws ImplementationException {
		try {
			inputGenerator = new InputGenerator(this, commandChannelHost, commandChannelPort, 
					notificationChannelHost, notificationChannelPort, readTimeIntervall);
		} catch (ImplementationException e) {
			setDisconnected();
			throw e;
		}
		
		setConnected();
	}

	/**
	 * destroys a reader.
	 * @throws ImplementationException whenever an internal error occured
	 */
	@Override
	public void disconnectReader() throws ImplementationException {
		physicalSourceStubs.clear();
		
		commandChannelHost = null;
		notificationChannelHost = null;
		commandChannelPort = -1;
		notificationChannelPort = -1;
		readTimeIntervall = -1;
		
		inputGenerator = null;
		
		setDisconnected();
		setStopped();
	}

	/**
	 * whenever a new Tag is read a notification is sent to the observers.
	 * @param tag a tag read on the reader
	 */
	@Override
	public void addTag(Tag tag) {
		tag.addTrace(getName());
		
		setChanged();
		notifyObservers(tag);
	}

	/**
	 * starts a base reader to read tags.
	 *
	 */
	@Override
	public synchronized void start() {
		if (!isConnected()) {
			try {
				connectReader();
			} catch (ImplementationException e) {
				e.printStackTrace();
				
				setDisconnected();
			}
		}
		
		if (!isConnected()) {
			setStopped();
		} else {
			setStarted();
		}
	}

	/**
	 * stops a reader from reading tags.
	 *
	 */
	@Override
	public synchronized void stop() {
		setStopped();
	}

	/**
	 * updates a reader according the specified LRSpec.
	 * @param spec LRSpec for the reader
	 * @throws ImplementationException whenever an internal error occurs
	 */
	@Override
	public synchronized void update(LRSpec spec) throws ImplementationException {
		stop();
		setLRSpec(spec);
		extractConnectionSettings();
		start();
	}

	/**
	 * adds a physicalSourceStub for a source to the current reader.
	 * @param sourceStub a valid sourceStub containing a source
	 */
	public void addPhysicalSourceStub(PhysicalSourceStub sourceStub) {
		physicalSourceStubs.put(sourceStub.getName(), sourceStub);
	}
	
	/**
	 * returns one of the source-stubs from the reader.
	 * @param name name of the sourceStub to be returned
	 * @return PhysicalSourceStub for the requested source
	 */
	public PhysicalSourceStub getPhysicalSourceStub(String name) {
		return physicalSourceStubs.get(name);
	}
	
	/**
	 * Triggers the identification of all tags that are currently available 
	 * on the reader. this method is used when the IdentifyThread is used to poll the adaptor.
	 * @param readPointNames the readers/sources that have to be polled
	 * @return a set of Observations
	 * @throws HardwareException whenever an internal hardware error occurs (as reader not available...)
	 */
	@Override
	public Observation[] identify(String[] readPointNames)
			throws HardwareException {
		
		// how can we poll a ReaderProtocol device?
		
		// TODO Auto-generated method stub
		return null;
	}
}
