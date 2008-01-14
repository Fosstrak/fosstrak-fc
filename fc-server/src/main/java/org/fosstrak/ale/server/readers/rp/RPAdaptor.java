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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import org.accada.ale.server.Tag;
import org.accada.ale.server.readers.BaseReader;
import org.accada.ale.server.readers.IdentifyThread;
import org.accada.ale.server.readers.LRSpec;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity;
import org.accada.hal.HardwareException;
import org.accada.hal.Observation;
import org.accada.reader.rp.proxy.RPProxyException;

/**
 * represents an adaptor to the RP reader.
 * @author sawielan
 *
 */
public class RPAdaptor extends BaseReader {
	
	/** logger. */
	private static final Logger LOG = Logger.getLogger(RPAdaptor.class);
	
	/** default readTimeInterval. */
	public static final int DEFAULT_READTIME_INTERVALL = 2000;
		
	/** input generator for the RP that establishes connection and receives tags. */
	private InputGenerator inputGenerator = null;
		
	/** the host where commands shall be sent. */
	private String commandChannelHost = null;
	
	/** the port where to connect to. */
	private int commandChannelPort = -1;
	
	/** the host where notifications shall be sent. */
	private String notificationChannelHost = null;
	
	/** the corresponding port. */
	private int notificationChannelPort = -1;
	
	/** the interval in which shall be read from the reader. */
	private int readTimeInterval = -1;
	
	/** the physical sources where tags are read in a String (eg shelf1, shelf2). */
	private String sourcesString = null;
	
	/** the physical sources. */
	private Set<String> sources = new HashSet<String>();
	
	/** to get all the tags we need a polling thread.  */
	private IdentifyThread identifyThread = null;
	
	
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
			// extract from LRSpec how to connect to the reader
			extractConnectionSettings();
		} catch (ImplementationException ie) {
			ie.printStackTrace();
			LOG.error("could not extract connection settings from LRSpec");
			throw new ImplementationException();
		}
		
		// connect to the reader
		connectReader();
	}
	
	/**
	 * this method calls the URL constructor with a string.
	 * @param urlString the URL to be converted into a java.net.URL
	 * @param comment "NotificationPoint" or " ConnectionPoint" 
	 * @return a URL created from urlString
	 * @throws ImplementationException when a MalformedURLException is thrown
	 */
	private URL toURL(String urlString, String comment) throws ImplementationException {
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new ImplementationException("Could not extract " + comment + " from LRProperty",
				ImplementationExceptionSeverity.ERROR);
		}
		return url;
	}
	
	/**
	 * this method extracts the connection settings from the LRProperty. when there 
	 * is the need to disconnect the reader (when something in the connection to the 
	 * reader has been changed in the LRSpec), true will be returned false otherwise.
	 * if necessary the reader will be restarted
	 * @throws ImplementationException whenever an error occurs
	 * @return returns true if the connection to the reader needs to be reconnected
	 */
	private boolean extractConnectionSettings() throws ImplementationException {
		URL connectionPoint = toURL(logicalReaderProperties.get("ConnectionPoint"), "ConnectionPoint");
		URL notificationPoint = toURL(logicalReaderProperties.get("NotificationPoint"), "NotificationPoint");
		String interval = logicalReaderProperties.get("ReadTimeInterval");
	
		setReadTimeInterval(-1);
		try {
			setReadTimeInterval(Integer.parseInt(interval));
		} catch (Exception ne) {
			LOG.error("could not extract readTimeIntervall from LRPropery");
			throw new ImplementationException("could not extract notificationPoint from LRPropery", 
					ImplementationExceptionSeverity.ERROR);
		}
		
		// assert that the readTimeInterval is not -1
		if (readTimeInterval == -1) {
			LOG.error("ReadTimeInterval not set - assuming 2000ms");
			setReadTimeInterval(DEFAULT_READTIME_INTERVALL);
		}
		
		boolean disconnect = false;
		
		// compare the new sources string to the old sources string 
		String nsources = logicalReaderProperties.get("PhysicalReaderSource");
		if (!nsources.equalsIgnoreCase(sourcesString)) {
			disconnect = true;
		}		
		
		// disconnect if command channel host has changed
		if (!connectionPoint.getHost().equalsIgnoreCase(commandChannelHost)) {
			disconnect = true;
		}
		
		// disconnect if notificationChannelHost has changed
		if (!notificationPoint.getHost().equalsIgnoreCase(notificationChannelHost)) {
			disconnect = true;
		}
		
		// disconnect when the port has changed
		if (connectionPoint.getPort() != commandChannelPort) {
			disconnect = true;
		}
			
		// disconnect when the port has changed
		if (notificationPoint.getPort() != notificationChannelPort) {
			disconnect = true;
		}
		// disconnect when the reader was not connected before
		if (inputGenerator != null && !inputGenerator.isReady()) {
			disconnect = true;
		}
		
		LOG.debug("readTimeInterval " + readTimeInterval);
		LOG.debug(String.format("commandChannelHost %s on port %d", 
				connectionPoint.getHost(), 
				connectionPoint.getPort()));
		LOG.debug(String.format("notificationChannelHost %s on port %d", 
				notificationPoint.getHost(), 
				notificationPoint.getPort()));
		
		// set the new commandChannelSettings
		setCommandChannelHost(connectionPoint.getHost());
		setCommandChannelPort(connectionPoint.getPort());
		
		// set the new notificationChannelSettings
		setNotificationChannelHost(notificationPoint.getHost());
		setNotificationChannelPort(notificationPoint.getPort());
		
		// set the new sources
		sourcesString = nsources;
		setSourcesFromArray(sourcesString.split("[,]"));
		
		return disconnect;
	}
	
	
	/**
	 * sets up a reader. if the adaptor can be connected to the rp-proxy 
	 * the adaptor will be set to connected.
	 * @throws ImplementationException whenever an internal error occured
	 */
	@Override
	public void connectReader() throws ImplementationException {
		if (isConnected()) {
			return;
		}
		
		try {
			inputGenerator = new InputGenerator(this);
			
		} catch (ImplementationException e) {
			setDisconnected();
			throw e;
		}
		
		LOG.debug("setup identifyThread on RPAdaptor " + getName());
		// setup the polling thread
		identifyThread = new IdentifyThread(this);
		identifyThread.setPollingFrequency(readTimeInterval);
		identifyThread.start();
		
		// suspend the polling thread to the beginning
		identifyThread.suspendIdentify();		
		
		setConnected();
	}

	/**
	 * destroys a reader.
	 * @throws ImplementationException whenever an internal error occured
	 */
	@Override
	public void disconnectReader() throws ImplementationException {
		
		setCommandChannelHost(null);
		setNotificationChannelHost(null);
		setCommandChannelPort(-1);
		setNotificationChannelPort(-1);
		setReadTimeInterval(-1);
		
		if (inputGenerator != null) {
			inputGenerator.remove();
			
			inputGenerator = null;
		}
		
		
		if (identifyThread != null) {
			identifyThread.stopIdentify();
			identifyThread = null;
		}
		setDisconnected();
		setStopped();
	}

	/**
	 * whenever a new Tag is read a notification is sent to the observers.
	 * @param tag a tag read on the reader
	 */
	@Override
	public void addTag(Tag tag) {
		setChanged();
		tag.addTrace(getName());
		//LOG.debug("calling observers");
		notifyObservers(tag);
	}
	
	/**
	 * whenever new Tags are read a notification is sent to the observers.
	 * @param tags a list of tags to be added to the reader
	 */
	@Override
	public void addTags(List<Tag> tags) {
		setChanged();
		for (Tag tag : tags) {
			tag.addTrace(getName());
		}
		notifyObservers(tags);
	}

	/**
	 * starts a base reader to read tags. if the reader 
	 * is not yet connected this command will connect the reader 
	 * immediately and then start it.
	 * If the reader cannot be connected then the reader is 
	 * not started as well.
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
		
		// reader is not connected so it is not started as well
		if (!isConnected()) {
			setStopped();
		} else {
			LOG.debug("identifyThread starting to identify");
			identifyThread.resumeIdentify();
			setStarted();
		}
	}

	/**
	 * stops a reader from reading tags.
	 *
	 */
	@Override
	public synchronized void stop() {
		LOG.debug("identifyThread suspend to identify");
		identifyThread.suspendIdentify();
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
		if (extractConnectionSettings()) {
			LOG.debug("restarting reader " + getName());
			disconnectReader();
			// set the connection settings again and then start the reader
			extractConnectionSettings();
			connectReader();
		}
		start();
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
		
		//LOG.debug("identify called an RPAdaptor " + getName());
		
		if ((inputGenerator != null) && (countObservers() > 0)) {
			
			if (inputGenerator.isReady()) {
			
				try {
					inputGenerator.poll();
					
				} catch (RPProxyException e) {
					e.printStackTrace();
					throw new HardwareException("Could not poll the adaptor " + getName());
				}
			} else {
				LOG.debug("rp-proxy not ready (yet)");
			}
		}
		
		return null;
	}

	/**
	 * returns the commandChannelHost of the rp-proxy.
	 * @return the commandChannelHost.
	 */
	public String getCommandChannelHost() {
		return commandChannelHost;
	}

	/**
	 * sets the commandChannelHost of the rp-proxy.
	 * @param commandChannelHost new commandChannelHost.
	 */
	private void setCommandChannelHost(String commandChannelHost) {
		this.commandChannelHost = commandChannelHost;
	}

	/**
	 * returns the commandChannelPort of the rp-proxy.
	 * @return the commandChannelPort.
	 */
	public int getCommandChannelPort() {
		return commandChannelPort;
	}

	/**
	 * sets the commandChannelPort of the rp-proxy.
	 * @param commandChannelPort the new commandChannelPort
	 */
	private void setCommandChannelPort(int commandChannelPort) {
		this.commandChannelPort = commandChannelPort;
	}

	/**
	 * returns the notificationChannelHost of the rp-proxy.
	 * @return the notificationChannelHost
	 */
	public String getNotificationChannelHost() {
		return notificationChannelHost;
	}

	/**
	 * sets the notificationChannelHost of the rp-proxy.
	 * @param notificationChannelHost the new notificationChannelHost
	 */
	private void setNotificationChannelHost(String notificationChannelHost) {
		this.notificationChannelHost = notificationChannelHost;
	}

	/**
	 * returns the notificationChannelPort of the rp-proxy.
	 * @return the notificationChannelPort.
	 */
	public int getNotificationChannelPort() {
		return notificationChannelPort;
	}

	/**
	 * sets the notificationChannelPort of the rp-proxy.
	 * @param notificationChannelPort the new notificationChannelPort
	 */
	private void setNotificationChannelPort(int notificationChannelPort) {
		this.notificationChannelPort = notificationChannelPort;
	}

	/**
	 * returns the readTimeInterval.
	 * @return the readTimeInterval
	 */
	public int getReadTimeInterval() {
		return readTimeInterval;
	}

	/**
	 * sets the readTimeInterval.
	 * @param readTimeInterval the readTimeInterval
	 */
	private void setReadTimeInterval(int readTimeInterval) {
		this.readTimeInterval = readTimeInterval;
	}

	/**
	 * returns the sources from where the reader reads tags. 
	 * @return the sources from where the reader reads tags.
	 */
	public Set<String> getSources() {
		return sources;
	}

	/**
	 * sets the sources from where the reader reads tags. the 
	 * sources are copied into the HashSet sources 
	 * @param sources an array of strings containing the sources.
	 */
	private void setSourcesFromArray(String[] sources) {
		for (String source : sources) {
			this.sources.add(source);
		}
	}
}
