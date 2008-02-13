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

package org.accada.ale.server.readers;

import java.util.List;

import org.accada.ale.server.Tag;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.hal.HardwareException;
import org.accada.hal.Observation;

/**
 * represents an abstract superclass for basereaders.
 * @author sawielan
 *
 */
public abstract class BaseReader extends LogicalReader {
	
	/**
	 * constructor for a BaseReader.
	*/
	public BaseReader() {
		super();
	}

	/**
	 * initializes a BaseReader. this method must be called befor the Reader can
	 * be used.
 	 * @param name the name for the reader encapsulated by this reader.
	 * @param spec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.

	 */
	public void initialize(String name, LRSpec spec) throws ImplementationException {
		super.initialize(name, spec);
	}
	
	/**
	 *  add a tag to a reader.
	 * @param tag tag to be added to the reader
	 */
	public abstract void addTag(Tag tag);
	
	/**
	 * add a List of tags to a reader.
	 * @param tags a list of tags to be added to the reader
	 */
	public abstract void addTags(List<Tag> tags);
	
	/**
	 * starts a basereader to read tags.
	 *
	 */
	public abstract void start();

	/**
	 * stops a reader from reading tags.
	 *
	 */
	public abstract void stop();

	/**
	 * sets up a reader.
	 * @throws ImplementationException whenever an internal error occured
	 *
	 */
	public abstract void connectReader() throws ImplementationException;

	/**
	 * destroys a reader.
	 * @throws ImplementationException whenever an internal error occured
	 *
	 */
	public abstract void disconnectReader() throws ImplementationException;

	/**
	 * updates a reader according the specified LRSpec.
	 * @param spec LRSpec for the reader
	 * @throws ImplementationException whenever an internal error occurs
	 */
	public abstract void update(LRSpec spec) throws ImplementationException;
	
	/**
	 * Triggers the identification of all tags that are currently available 
	 * on the reader.
	 * @param readPointNames the readers/sources that have to be polled
	 * @return a set of Observations
	 * @throws HardwareException whenever an internal hardware error occurs (as reader not available...)
	 */
	public abstract Observation[] identify(String[] readPointNames) throws HardwareException;
	
	/** indicates whether the reader is connected or not. */
	protected boolean connected = false;
	
	/**
	 * flags the reader as connected.
	 */
	protected void setConnected() {
		connected = true;
	}
	
	/**
	 * flags the reader as disconnected.
	 */
	protected void setDisconnected() {
		connected = false;
	}
	
	/**
	 * tells whether the reader is connected or not.
	 * @return boolean true or false
	 */
	protected boolean isConnected() {
		return connected;
	}

	/** 
	 * this method is called whenever a reader is undefined. a basereader 
	 * can override this method with its own cleanup routine.
	 */
	public void cleanup() {
	}
}
