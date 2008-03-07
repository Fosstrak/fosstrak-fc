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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.List;

import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.accada.ale.xsd.ale.epcglobal.LRProperty;
import org.accada.ale.xsd.ale.epcglobal.LRSpec;
import org.apache.log4j.Logger;

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
	private static final Logger LOG = Logger.getLogger(LogicalReader.class);
	
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
		return	logicalReaderSpec;	
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
	protected boolean isStarted() {
		return started;
	}
	
	/**
	 * factory method to create a new reader. this can be a CompositeReader or a BaseReader.
	 * @param name name of the reader
	 * @param spec the specificationFile for a Reader
	 * @return a logical reader
	 * @throws ImplementationException when the LogicalReader could not be built by reflection
	 */
	public static LogicalReader createReader(String name, LRSpec spec)  throws ImplementationExceptionResponse {
		// determine whether composite or basereader
		if (spec.isIsComposite()) {
			CompositeReader compositeReader = new CompositeReader();
			// initialize the reader
			compositeReader.initialize(name, spec);
			return compositeReader;
		} 
		
		// first test if reader is already in the LogicalReaderManager
		LogicalReader logicalReader = LogicalReaderManager.getLogicalReader(name);
		if (logicalReader != null) {
			return logicalReader;
		}
		
		try {
			// get the reader type of the reader
			String readerType = null;
			for (LRProperty property : spec.getProperties().getProperty()) {
				if (property.getName().equalsIgnoreCase("ReaderType")) {
					readerType = property.getValue();
				}
			}
			
			if (readerType == null) {
				throw new ImplementationExceptionResponse("Property ReaderType not defined");
			}
			
			Class cls = Class.forName(readerType);  
			Object result = cls.newInstance();
			
			if (result instanceof LogicalReader) {
				logicalReader = (LogicalReader) result;
				// initialize the reader
				logicalReader.initialize(name, spec);
				
				// store the reader in the logical reader manager
				LogicalReaderManager.setLogicalReader(logicalReader);
			} else {
				throw new ClassCastException("constructor resulted in wrong type");
			}
			
		} catch (Throwable e) {
			System.out.println("could not dynamically reflect the reader type");
			e.printStackTrace();
			throw new ImplementationExceptionResponse();
		}
		
		return logicalReader;
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
