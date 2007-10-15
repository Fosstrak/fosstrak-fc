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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;

import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.LRLogicalReaders;
import org.accada.ale.xsd.ale.epcglobal.LRProperties;
import org.accada.ale.xsd.ale.epcglobal.LRProperty;
import org.accada.ale.xsd.ale.epcglobal.LRSpec;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.RPCElement;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.accada.ale.xsd.ale.epcglobal.LRSpecExtension;

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
		
		QName ecSpecQName = new QName("urn:epcglobal:ale:xsd:1", "ECSpec");

		return (ECSpec) deserialize(inputStream, ECSpec.class, ecSpecQName);
		
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
		
		QName ecSpecQName = new QName("urn:epcglobal:ale:xsd:1", "ECSpec");
		
		return (ECSpec) deserialize(new FileInputStream(pathName), ECSpec.class, ecSpecQName);
		
	}
	
	/**
	 * This method deserializes an ec report from an input stream.
	 * 
	 * @param inputStream to deserialize
	 * @return ec report
	 * @throws Exception if deserialization fails
	 */
	public static ECReport deserializeECReport(InputStream inputStream) throws Exception {
		
		QName ecReportQName = new QName("urn:epcglobal:ale:xsd:1", "ECReport");
		
		return (ECReport) deserialize(inputStream, ECReport.class, ecReportQName);
		
	}
	
	/**
	 * This method deserializes ec reports from an input stream.
	 * 
	 * @param inputStream to deserialize
	 * @return ec reports
	 * @throws Exception if deserialization fails
	 */
	public static ECReports deserializeECReports(InputStream inputStream) throws Exception {
	
		QName ecReportsQName = new QName("urn:epcglobal:ale:xsd:1", "ECReports");
		
		return (ECReports) deserialize(inputStream, ECReports.class, ecReportsQName);
		
	}
	
	
	/**
	 * This method deserializes a LRSpec from an input stream.
	 * @param inputStream to deserialize
	 * @return LRSpec
	 * @throws Exception if deserialization fails
	 */
	public static LRSpec deserializeLRSpec(InputStream inputStream) throws Exception {
		QName lrSpecQName  = new QName("urn:epcglobal:ale:xsd:1", "LRSpec");
		return (LRSpec) deserialize(inputStream, LRSpec.class, lrSpecQName);
	}

	/**
	 * This method deserializes a LRSpec from an file path.
	 * @param pathName to deserialize
	 * @return LRSpec
	 * @throws Exception if deserialization fails
	 */
	public static LRSpec deserializeLRSpec(String pathName) throws FileNotFoundException, Exception {
		QName lrSpecQName = new QName("urn:epcglobal:ale:xsd:1", "LRSpec");
		LRSpec spec;
		try {
			spec = (LRSpec) deserialize(new FileInputStream(pathName), LRSpec.class, lrSpecQName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return spec;
	}

	
	/**
	 * 
	 * This method deserializes a LRProperty from an input stream.
	 * @param inputStream to deserialize
	 * @return LRProperty
	 * @throws Exception if deserialization fails
	 */
	public static LRProperty deserializeLRProperty(InputStream inputStream) throws Exception {
		QName lrPropertyQName = new QName("urn:epcglobal:ale:xsd:1", "LRProperty");
		return (LRProperty) deserialize(inputStream, LRProperty.class, lrPropertyQName);
	}
	
	/**
	 * 
	 * This method deserializes a LRProperty from an string.
	 * @param pathName path to the file to be deserialized
	 * @return LRProperty
	 * @throws Exception if deserialization fails
	 */
	public static LRProperty deserializeLRProperty(String pathName) throws Exception {
		QName lrPropertyQName = new QName("urn:epcglobal:ale:xsd:1", "LRProperty");
		return (LRProperty) deserialize(new FileInputStream(pathName), LRProperty.class, lrPropertyQName);
	}

	/**
	 * This method deserializes LRProperties from a string.
	 * @param pathName the path to the properties file
	 * @return LRProperties
	 * @throws Exception if deserialization fails
	 */
	public static LRProperties deserializeLRProperties(String pathName) throws Exception {
		return deserializeLRProperties(new FileInputStream(pathName));
	}

	/**
	 * This method deserializes LRProperties from an InputStream.
	 * @param inputStream the inputStream to deserialize
	 * @return LRProperties
	 * @throws Exception if deserialization fails
	 */
	public static LRProperties deserializeLRProperties(InputStream inputStream) throws Exception {
		QName lrp = new QName("urn:epcglobal:ale.xsd:1", "LRProperties");
		return (LRProperties) deserialize(inputStream, LRProperties.class, lrp);
	}

	/**
	 * This method deserializes LRLogicalReaders.
	 * @param pathName path to the file to be deserialized.
	 * @return LRLogicalReaders
	 * @throws Exception if deserialization fails
	 */
	public static LRLogicalReaders deserializeLRLogicalReaders(String pathName) throws Exception {
		QName ra = new QName("urn:epcglobal:ale:xsd:1", "LRLogicalReaders");
		LRLogicalReaders lr = (LRLogicalReaders) deserialize(new FileInputStream(pathName), LRLogicalReaders.class , ra);
		return lr;
	}

	/**
	 * This method deserializes an LRSpecExtension from a file input stream.
	 * @param inputStream the inputstream to be deserialized
	 * @return LRSpecExtension
	 * @throws Exception if deserialization fails
	 */
	public static LRSpecExtension deserializeLRSpecExtension(InputStream inputStream) throws Exception {
		QName lrs = new QName("urn:epcglobal:ale:xsd:1", "LRSpecExtension");
		return (LRSpecExtension) deserialize(inputStream, LRSpecExtension.class, lrs);
	}
	
	//
	// private methods
	//
	
	/**
	 * This method deserializes an object of type type from an input stream.
	 * 
	 * @param inputStream to deserialize.
	 * @param type of the resulting object
	 * @param qName matching to the reuslting object
	 * @return Object
	 * @throws Exception if deserialization fails
	 */
	private static Object deserialize(InputStream inputStream, Class type, QName qName) throws Exception {
		
		// create deserialization context
		MessageContext msgContext = new MessageContext(new Service().getEngine());
		InputSource inputSource = new InputSource(EnveloperUtil.envelope(inputStream));
		DeserializationContext context = new DeserializationContext(inputSource, msgContext, "");
		
		//TODO: [regli] add type mapping for ECTimeUnit remove class org.apache.axis.encoding.ser.SimpleSerializer
		
		// add type mapping for ECTimeUnit
		//TODO: [mlampe] check code below since it was commented by Remo Egli before
//		QName timeUnitQName = new QName("urn:epcglobal:ale:xsd:1", "ECTimeUnit");
//		TypeMapping typeMapping = context.getTypeMapping();
//		typeMapping.register(ECTimeUnit.class, timeUnitQName, null, new ECTimeUnitDeserializerFactory(ECTimeUnit.class, timeUnitQName));
//		typeMapping.isRegistered(ECTimeUnit.class, timeUnitQName);
//		typeMapping.getTypeQName(ECTimeUnit.class);
//		typeMapping.setSupportedEncodings(new String[] {"test"});
//		for (String s : typeMapping.getSupportedEncodings()) {
//			LOG.debug(s);
//		}
		
		// parse
		try {
			context.parse();
		} catch (SAXException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		// get ECSpec object from soap envelope
		SOAPBody body = context.getEnvelope().getBody(); 
		RPCElement specElement = (RPCElement)body.getChildElements(new PrefixedQName(qName)).next();
		return specElement.getValueAsType(qName, type);
		
	}
	
}