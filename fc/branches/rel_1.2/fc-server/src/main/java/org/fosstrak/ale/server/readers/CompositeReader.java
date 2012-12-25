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

package org.fosstrak.ale.server.readers;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

/**
 * represents a compositeReader that is a composition of different logicalreaders.
 * @author swieland
 *
 */
public class CompositeReader extends LogicalReader implements Observer  {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(CompositeReader.class);
	
	/** logical readers within the composite reader. */
	private final java.util.Map<String, LogicalReader> logicalReaders = new HashMap<String, LogicalReader>();;

	/**
	 * constructor for the composite reader.
	 */
	public CompositeReader() {
		super();
	}
	
	/**
	 * initializes a Composite Reader. this method must be called befor the Reader can
	 * be used.
 	 * @param name the name for the reader encapsulated by this reader.
	 * @param aspec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.
	 */
	public void initialize(String name, LRSpec aspec) throws ImplementationException {
		super.initialize(name, aspec);

		// create the sub parts by calling the factory method
		List<String> readers = aspec.getReaders().getReader();
		for (String reader : readers) {
			
			LOG.debug(String.format("retrieving reader part %s", reader));
			
			// just retrieve the reader from the LogicalReaderManager
			LogicalReader logicalReader = logicalReaderManager.getLogicalReader(reader);
			
//			 add the reader to the observable
			logicalReader.addObserver(this);
			logicalReaders.put(logicalReader.getName(), logicalReader);
		}
	}
	
	/**
	 * unregister this compositeReader from its observables.
	 */
	public void unregisterAsObserver() {
		for (LogicalReader reader : logicalReaders.values()) {
			LOG.debug("undefining observer " + readerName + " on reader " + reader.getName());
			reader.deleteObserver(this);
		}
	}
		
	/**
	 * implements the update-method for the observer-pattern for events.
	 * @param o the observed object
	 * @param arg the arguments passed by the observable
	 */
	public void update(Observable o, Object arg) {
		// deliver tags only if the reader is not suspended
		if (isStarted()) {
			if (arg instanceof Tag) {
				setChanged();
				Tag tag = (Tag) arg;
				tag.setReader(getName());
				tag.addTrace(getName());
				
				notifyObservers(tag);
			} else if (arg instanceof List) {
				LOG.debug("processing multiple tags");
				
				// process multiple tags at once
				@SuppressWarnings("unchecked")				
				List<Tag> tagList = (List<Tag>) arg;				
				for (Tag tag : tagList) {
					tag.setReader(getName());
					tag.addTrace(getName());
				}
				setChanged();
				notifyObservers(tagList);
			}
		}
	}

	/**
	 * add a logicalReader to the composite.
	 * @param reader a logicalReader (baseReader or CompositeReader)
	 */
	public void addReader(LogicalReader reader) {
		reader.addObserver(this);
		
		logicalReaders.put(reader.getName(), reader);
		return;
	}

	/**
	 * removes a given reader from the composite.
	 * @param reader a logicalReader (baseReader or CompositeReader).
	 */
	public void removeReader(LogicalReader reader) {
		reader.deleteObserver(this);
		
		logicalReaders.remove(reader.getName());
		return;
	}
	
	/**
	 * checks if the composite reader contains the given reader.
	 * @param readerName the name of the reader to check.
	 * @return true if contained, false otherwise.
	 */
	public boolean containsReader(String readerName) {
		return logicalReaders.containsKey(readerName);
	}
	
	/**
	 * this method changes the specification of a reader.
	 * @param aspec an LRSpec containing the changes for the reader
	 * @throws ImplementationException whenever an internal error occurs
	 */
	@Override
	public  synchronized void update(LRSpec aspec) throws ImplementationException {
		// get a lock on the logicalReaders
	 	synchronized (logicalReaders) {
	 		// test whether we need to update the reader or just the properties
	 		// this can be done by comparing the readers with the readers in the new LRSpec
	 		List<String> readers  = aspec.getReaders().getReader();

	 		// set the new spec
			setLRSpec(aspec);
	 		
	 		if (!CollectionUtils.isEqualCollection(readers, logicalReaders.keySet())) {
	 			
	 			// as there are changes in the readers, we
	 			// need to stop this compositeReader, update the 
	 			// components and then restart again
	 			LOG.debug("updating readers in CompositeReader " + readerName);
	 			
				// stop the reader
				stop();
				
				// remove all readers first
				for (LogicalReader reader : logicalReaders.values()) {
					reader.deleteObserver(this);
				}
				logicalReaders.clear();
				
				// fill in the new readers
				for (String reader : readers) {
					LogicalReader logicalReader = logicalReaderManager.getLogicalReader(reader);
					logicalReader.addObserver(this);
					logicalReaders.put(reader, logicalReader);
				}
				
				start();
	 		}
	 		
 			// update the LRProperties
 			LOG.debug("updating LRProperties in CompositeReader " + readerName);	 		
 				 		
			notifyAll();
		}
	}

	/**
	 * starts all components of a composite reader. 
	 */
	@Override
	public synchronized void start() {
		setStarted();
	}

	/**
	 * stops all components of a composite reader. 
	 */
	@Override
	public synchronized void stop() {
		setStopped();
	}

}
