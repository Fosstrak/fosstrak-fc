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

package org.accada.ale.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.accada.ale.wsdl.alelr.epcglobal.AddReaders;
import org.accada.ale.wsdl.alelr.epcglobal.RemoveReaders;
import org.accada.ale.wsdl.alelr.epcglobal.SetProperties;
import org.accada.ale.wsdl.alelr.epcglobal.SetReaders;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.LRProperty;
import org.accada.ale.xsd.ale.epcglobal.LRSpec;
import org.apache.log4j.Logger;

/**
 * This class provides some methods to deserialize ec specifications and reports.
 * 
 * @author regli
 * @author sawielan
 */
public class DeserializerUtil {

	/**	logger. */
	public static final Logger LOG = Logger.getLogger(DeserializerUtil.class);
	
	/**
	 * This method deserializes an ec specification from an input stream.
	 * 
	 * @param inputStream to deserialize
	 * @return ec specification
	 * @throws Exception if deserialization fails
	 */
	public static ECSpec deserializeECSpec(InputStream inputStream) throws Exception {
		ECSpec spec = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.xsd.ale.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			spec = ((JAXBElement<ECSpec>) unmarshaller.unmarshal(inputStream)).getValue();
						
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return spec;
	}
	
	/**
	 * This method deserializes an ec specification from a file.
	 * 
	 * @param pathName of the file containing the ec specification
	 * @return ec specification
	 * @throws FileNotFoundException if the file could not be found
	 * @throws Exception if deserialization fails
	 */
	public static ECSpec deserializeECSpec(String pathName) throws FileNotFoundException, Exception {
		return deserializeECSpec(new FileInputStream(new File(pathName)));
	}	
	
	/**
	 * This method deserializes a LRSpec from an input stream.
	 * @param inputStream to deserialize
	 * @return LRSpec
	 * @throws Exception if deserialization fails
	 */
	public static LRSpec deserializeLRSpec(InputStream inputStream) throws Exception {
		LRSpec spec = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.wsdl.alelr.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			spec = ((JAXBElement<LRSpec>) unmarshaller.unmarshal(inputStream)).getValue();
						
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return spec;
	}

	/**
	 * This method deserializes a LRSpec from an file path.
	 * @param pathName to deserialize
	 * @return LRSpec
	 * @throws Exception if deserialization fails
	 */
	public static LRSpec deserializeLRSpec(String pathName) throws FileNotFoundException, Exception {
		return deserializeLRSpec(new FileInputStream(new File(pathName)));
	}

	
	/**
	 * 
	 * This method deserializes a LRProperty from an input stream.
	 * @param inputStream to deserialize
	 * @return LRProperty
	 * @throws Exception if deserialization fails
	 */
	public static LRProperty deserializeLRProperty(InputStream inputStream) throws Exception {
		// FIXME : throws Exception
		LRProperty prop = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.wsdl.alelr.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			prop = (LRProperty) unmarshaller.unmarshal(inputStream);
						
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * This method deserializes a SetProperties from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return SetProperties.
	 * @throws IOException if file is not found.
	 */
	public static SetProperties deserializeSetProperties(String pathName) throws IOException {
		SetProperties props = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.wsdl.alelr.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			props = ((JAXBElement<SetProperties>) unmarshaller.unmarshal(new FileInputStream(new File(pathName)))).getValue();
						
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	/**
	 * This method deserializes a RemoveReaders from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return RemoveReaders.
	 * @throws IOException if file is not found.
	 */
	public static RemoveReaders deserializeRemoveReaders(String pathName) throws IOException {
		RemoveReaders readers = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.wsdl.alelr.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			readers = ((JAXBElement<RemoveReaders>) unmarshaller.unmarshal(new FileInputStream(new File(pathName)))).getValue();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return readers;
	}
	
	/**
	 * This method deserializes a SetReaders from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return SetReaders.
	 * @throws IOException if file is not found.
	 */
	public static SetReaders deserializeSetReaders(String pathName) throws IOException {
		SetReaders readers = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.wsdl.alelr.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			readers = ((JAXBElement<SetReaders>) unmarshaller.unmarshal(new FileInputStream(new File(pathName)))).getValue();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return readers;		
	}
	
	/**
	 * This method deserializes a AddReaders from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return AddReaders.
	 * @throws IOException if file is not found.
	 */
	public static AddReaders deserializeAddReaders(String pathName) throws IOException {
		AddReaders readers = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.wsdl.alelr.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			readers = ((JAXBElement<AddReaders>) unmarshaller.unmarshal(new FileInputStream(new File(pathName)))).getValue();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return readers;
	}
	
	/**
	 * This method deserializes ECReports from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return ECReports.
	 * @throws IOException if file is not found.
	 */
	public static ECReports deserializeECReports(InputStream in) throws IOException {
		ECReports reports = null;
		try {
			String JAXB_CONTEXT = "org.accada.ale.xsd.ale.epcglobal";
			
			// initialize jaxb context and unmarshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			
			reports = ((JAXBElement<ECReports>) unmarshaller.unmarshal(in)).getValue();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return reports;
	}
}