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

import java.util.ArrayList;
import java.util.List;

/**
 * An LRSpec describes the configuration of a Logical Reader.
 * @author sawielan
 * @author haennimi
 *
 */
public class LRSpec {
	
	/** List of readers. */
	private List<String> readers;
	
	/** A Logical Reader isComposite, if it consists of more than one reader. */
	private boolean isComposite;
	
	/** List of properties for the specified logicalreader. */
	private List <LRProperty> properties;
	
	/** AdaptorClass if baseReader, null if composite. */
	private String readerType;

	/** Constructor without values.*/
	public LRSpec() {
		readers = null;
		isComposite = false;
		properties = null;		
	}

	/** Constructor with values.
	 * @param readers a list containing the readers
	 * @param isComposite tells whether this reader is composite or not
	 * @param propList the list of properties
	 * @param readerType the type of the reader. see comment of setReaderType 
	 */
	public LRSpec(List<String> readers, boolean isComposite, List <org.accada.ale.server.readers.gen.LRProperty> propList, String readerType) {
		super();
		if (isComposite) {
			setComposite();
		}
		
		setReaderType(readerType);
		
		if (isComposite()) {
			setReaders(readers);
		} else {
			setReaders(null);
		}
		
		// extract the properties
		List<LRProperty> extracted = new ArrayList<LRProperty>();
		for (org.accada.ale.server.readers.gen.LRProperty property : propList) {
			extracted.add(new LRProperty(property.getName(), property.getValue()));
		}
		// store the properties
		setProperties(extracted);
	}
	
	/** 
	 * If true, this Logical Reader is a composite reader that
	 *  is an alias for the logical reader or readers specified
	 *  in the readers field.  If false, this Logical Reader is
	 *  a base reader. 
	 * @return see the comment.  
	 */
	public boolean isComposite() {
		return isComposite;
	}
	
	/**
	 * sets an LRSpec to compositeReader.
	 */
	public void setComposite() {
		isComposite = true;
	}
	
	/**
	 *  (Optional)  If isComposite is true, a list of zero or more
	 *  names of logical readers that collectively provide the 
	 *  channel to access Tags represented by this Logical Reader.
	 *  Specifying the name of this Logical Reader in an ECSpec or
	 *  CCSpec is equivalent to specifying the names in readers,
	 *   except that different properties may apply. 
	 *   Omitted if isComposite is false.
	 *   @return returns a list containing the readers 
	 */
	public List<String> getReaders() {
		if (isComposite) {
			return readers;
		}
		return null;
	}
	
	/** 
	 * A list of properties (key/value pairs) that control how Tags
	 *  are accessed using this Logical Reader.
	 *  @return returns a list containing the LRProperties 
	 */
	public List<LRProperty> getProperties() {
		return properties;
	}
	
	/**
	 * sets the readers in this LRSPec.
	 * @param readers a list containing the readers.
	 */
	public void setReaders(List<String> readers) {
		this.readers = readers;
	}
	
	/** 
	 * Sets a list of properties for the logical reader.
	 * @param propList List of properties for logicalreader.
	 */
	public void setProperties(List<LRProperty> propList) {
		properties = propList;
	}
	
	/**
	 * sets the readertype.
	 * @param readerType the full qualified type of the reader (eg. org.accada.ale.server.readers.hal.HALAdaptor)
	 */
	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}
	
	/**
	 * returns the full qualified type of this reader.
	 * @return a string holding the fqt.
	 */
	public String getReaderType() {
		return readerType;
	}

	/**
	 * returns all the LRProperties of this reader.
	 * @return List of LRProperty
	 */
	public List<LRProperty> getLRProperty() {
		return properties;
	}
}
