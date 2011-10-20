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

package org.fosstrak.ale.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.apache.log4j.Logger;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.llrp.ltk.generated.LLRPMessageFactory;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.llrp.ltk.types.LLRPMessage;

import org.fosstrak.ale.wsdl.alelr.epcglobal.AddReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetProperties;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

/**
 * This class provides some methods to deserialize ec specifications and reports.
 * 
 * @author sawielan
 * @author regli
 * @author julian roche
 * @author wafa.soubra@orange.com
 */
public class DeserializerUtil {

	// hash-map for JAXB context.
	private static final Map<String, JAXBContext> s_context = new ConcurrentHashMap<String, JAXBContext> ();
	
	/**	logger. */
	private static final Logger LOG = Logger.getLogger(DeserializerUtil.class);
	
	/**
	 * This method deserializes an ec specification from an input stream.
	 * 
	 * @param inputStream to deserialize
	 * @throws Exception upon error.
	 * @return ec specification
	 */
	public static ECSpec deserializeECSpec(InputStream inputStream) throws Exception {
		return (ECSpec) unmarshall("org.fosstrak.ale.xsd.ale.epcglobal", inputStream, null, ECSpec.class);
	}
	
	/**
	 * This method deserializes an ec specification from a file.
	 * 
	 * @param pathName of the file containing the ec specification
	 * @return ec specification
	 * @throws Exception upon error.
	 */
	public static ECSpec deserializeECSpec(String pathName) throws Exception {
		return deserializeECSpec(new FileInputStream(new File(pathName)));
	}	
	
	/**
	 * This method deserializes a LRSpec from an input stream.
	 * @param inputStream to deserialize
	 * @throws Exception upon error.
	 * @return LRSpec
	 */
	public static LRSpec deserializeLRSpec(InputStream inputStream) throws Exception {
		return (LRSpec) unmarshall("org.fosstrak.ale.wsdl.alelr.epcglobal", inputStream, null, LRSpec.class);
	}

	/**
	 * This method deserializes a LRSpec from an file path.
	 * @param pathName to deserialize
	 * @return LRSpec
	 * @throws Exception upon error.
	 */
	public static LRSpec deserializeLRSpec(String pathName) throws Exception {
		return deserializeLRSpec(new FileInputStream(new File(pathName)));
	}

	
	/**
	 * 
	 * This method deserializes a LRProperty from an input stream.
	 * @param inputStream to deserialize
	 * @throws Exception upon error.
	 * @return LRProperty
	 */
	public static LRProperty deserializeLRProperty(InputStream inputStream) throws Exception {
		return (LRProperty) unmarshall("org.fosstrak.ale.wsdl.alelr.epcglobal", inputStream, null, LRProperty.class);
	}
	
	/**
	 * This method deserializes a SetProperties from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return SetProperties.
	 * @throws Exception upon error.
	 */
	public static SetProperties deserializeSetProperties(String pathName) throws Exception {
		return (SetProperties) unmarshall("org.fosstrak.ale.wsdl.alelr.epcglobal", pathName, null, SetProperties.class);
	}
	
	/**
	 * This method deserializes a RemoveReaders from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return RemoveReaders.
	 * @throws Exception upon error.
	 */
	public static RemoveReaders deserializeRemoveReaders(String pathName) throws Exception {
		return (RemoveReaders) unmarshall("org.fosstrak.ale.wsdl.alelr.epcglobal", pathName, null, RemoveReaders.class);
	}
	
	/**
	 * This method deserializes a SetReaders from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return SetReaders.
	 * @throws Exception upon error.
	 */
	public static SetReaders deserializeSetReaders(String pathName) throws Exception {
		return (SetReaders) unmarshall("org.fosstrak.ale.wsdl.alelr.epcglobal", pathName, null, SetReaders.class);
	}
	
	/**
	 * This method deserializes a AddReaders from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return AddReaders.
	 * @throws Exception upon error.
	 */
	public static AddReaders deserializeAddReaders(String pathName) throws Exception {
		return (AddReaders) unmarshall("org.fosstrak.ale.wsdl.alelr.epcglobal", pathName, null, AddReaders.class);
	}
	
	/**
	 * This method deserializes ECReports from a file.
	 * @param pathName the path to the file to be deserialized.
	 * @return ECReports.
	 * @throws Exception upon error.
	 */
	public static ECReports deserializeECReports(InputStream in) throws Exception {
		return (ECReports) unmarshall("org.fosstrak.ale.xsd.ale.epcglobal", in, null, ECReports.class);
	}
	

	/**
	 * unmarshalles the object from the stream. if errors occur, they are written to the log.
	 * @param unmarshaller the unmarshaller.
	 * @param validationEventHandler validation event handler. if null, the default validation handler is added.
	 * @param of the output stream.
	 * @return the unmarshalled object.
	 * @throws Exception upon error.
	 * @throws Exception upon error.
	 */
	private static Object unmarshall(String jaxbContext, Object istr, ValidationEventHandler validationEventHandler, Class<?> object) throws Exception {
		try {
			ValidationEventHandler handler = validationEventHandler;
			if (null == handler) handler = new DefaultValidationEventHandler();
			Unmarshaller unmarshaller = getUnmarshaller(jaxbContext, handler);
			InputStream fif = null;
			if (istr instanceof String) {
				fif = new FileInputStream((String) istr);
			} else if (istr instanceof InputStream) {
				fif = (InputStream) istr;
			} else {
				throw new Exception("Wrong writer provided.");
			}
			return ((JAXBElement<?>) unmarshaller.unmarshal(fif)).getValue();
				
		} catch (Exception e) {
			LOG.error(String.format("Caught exception during unmarshalling:\n%s", e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * creates a unmarshaller on pooled JAXBContext instances.
	 * @param jaxbContext the context on which to create a unmarshaller.
	 * @param validationEventHandler validation event handler.
	 * @return the unmarshaller.
	 * @throws JAXBException when unable to create the unmarshaller.
	 */
	private static Unmarshaller getUnmarshaller(String jaxbContext, ValidationEventHandler validationEventHandler) throws JAXBException {
		JAXBContext context = null;
		synchronized (s_context) {
			context = s_context.get(jaxbContext);
			if (null == context) {
				context = JAXBContext.newInstance(jaxbContext);
				s_context.put(jaxbContext, context);
			}
		}
		Unmarshaller unmarshaller = null;
		synchronized (context) {
			unmarshaller = context.createUnmarshaller();
		}			
		unmarshaller.setEventHandler(validationEventHandler);
		return unmarshaller;
	}
	
	
	/**
	 * ORANGE: This method deserializes an ADD_ROSPEC from an input stream.
	 * TODO :  Must be tested very well because when we put these methods
	 * into the class org.fosstrak.ale.util.DeserializerUtil in fc-commons,
	 * we had the error "NoSuchMethodError" in the localhost log file when we launch Fosstrak. 
	 * @param inputStream to deserialize
	 * @return ADD_ROSPEC
	 * @throws Exception if deserialization fails
	 */
	public static ADD_ROSPEC deserializeAddROSpec(InputStream inputStream) throws Exception {
		LOG.debug("Start deserializeAddROSpec .... ");
		Document document = new SAXBuilder().build(inputStream);
		LOG.debug("Get jdom Document with SAXBuilder");
		LLRPMessage message = LLRPMessageFactory.createLLRPMessage(document);
		if (message != null ) {
			LOG.debug("LLRP Message created");
		}
		else {
			LOG.debug("LLRP Message is null !!!!!");
		}
		ADD_ROSPEC addRoSpec = (ADD_ROSPEC)message;
		LOG.debug("End of deserializeAddROSpec");
		return addRoSpec;
	}

	/**
	 * ORANGE: This method deserializes an ADD_ROSPEC from an file path.
	 * @param pathName to deserialize
	 * @return ADD_ROSPEC
	 * @throws Exception if deserialization fails
	 */
	public static ADD_ROSPEC deserializeAddROSpec(String pathName) throws FileNotFoundException, Exception {
		return  deserializeAddROSpec(new FileInputStream(new File(pathName)));
	}
	
}