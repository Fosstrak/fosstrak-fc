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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.log4j.Logger;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

/**
 * 
 * Represents the abstract interface for a reader. a reader can be either a composite of 
 * different readers or a basereader
 * 
 * @author sawielan
 *
 */
public abstract class LogicalReader extends Observable{

	/** logger. */
	private static final Logger log = Logger.getLogger(LogicalReader.class);
	
	/**
	 * all logical readers must define this property in the properties for the automatic reader creation.<br/>
	 * the property contains the FQN of the readers implementing class. 
	 */
	public static final String PROPERTY_READER_TYPE = "ReaderType";
	
	/** name of the reader. */
	protected String readerName;
	
	/** property value pair for the reader configuration. */
	protected Map<String, String> logicalReaderProperties = new HashMap<String, String>();

	/** LRSpec for the reader. */
	protected LRSpec logicalReaderSpec;
	
	/** LRpoperties for the reader. */
	protected List<LRProperty> properties = new LinkedList<LRProperty>();
	
	/**
	 * constructor for the logical reader.
	 */
	public LogicalReader() {
	}
	
	/**
	 * initializes a Logical Reader. this method must be called befor the Reader can
	 * be used.
 	 * @param name the name for the reader encapsulated by this reader.
	 * @param spec the specification that describes the current reader.
	 * @throws ImplementationException whenever an internal error occurs.
	 */
	public void initialize(String name, LRSpec spec) throws ImplementationExceptionResponse {

		this.readerName = name;
		this.logicalReaderSpec = spec;
		
		if (spec.getProperties() == null) {
			log.debug("no properties specified - aborting.");
			throw new ImplementationExceptionResponse("no properties specified");
		}
		// store the properties
		for (LRProperty prop : spec.getProperties().getProperty()) {
			logicalReaderProperties.put(prop.getName(), prop.getValue());
			properties.add(prop);
		}	
	}
	
	/**
	 * This method sets the LRSpec.
	 * 
	 * @param spec The spec. 
	 */
	public void setLRSpec(LRSpec spec) {
		logicalReaderSpec = spec;
	}
	
	/**
	 * This method returns the spec.
	 * 
	 * @return This is the spec of the logical reader.
	 */
	public LRSpec getLRSpec() {
		return logicalReaderSpec;	
	}
	
	/**
	 * This method sets the LRProperty property to the logical reader named name.
	 * @param name name of the property
	 * @param property The property. 
	 */
	public void setProperties(String name, LRProperty property) {
		properties.add(property);
	}
	
	/**
	 * This method returns the properties.
	 * @return returns a list of LRProperty  
	 */
	public List<LRProperty> getProperties() {
		return properties;
	}
		
	/** indicates whether the reader is started or not. */
	protected boolean started = false;
		
	/**
	 * flags the reader as started.
	 */
	protected void setStarted() {
		started = true;
	}
	
	/**
	 * flags the reader as stopped.
	 */
	protected void setStopped() {
		started = false;
	}
	
	/**
	 * tells whether the reader is started or not.
	 * @return boolean true or false
	 */
	public boolean isStarted() {
		return started;
	}
	
	/**
	 * This method returns the name of the logical reader.
	 * 
	 * @return name of this logical reader
	 */
	public String getName() {
		return readerName;
	}

	/**
	 * This method sets the name of the logical reader.
	 * 
	 * @param name name of this logical reader
	 */
	public void setName(String name) {
		readerName = name;
	}
	
	/**
	 * updates the specification of a logical reader.
	 * @param spec an LRSpec containing the changes of the reader
	 * @throws ImplementationException whenever an internal error occurs
	 */
	public abstract void update(LRSpec spec) throws ImplementationExceptionResponse;
	
	/**
	 * stops a reader from reading tags.
	 *
	 */
	public abstract void stop();
	
	/**
	 * starts a basereader to read tags.
	 *
	 */
	public abstract void start();

}
