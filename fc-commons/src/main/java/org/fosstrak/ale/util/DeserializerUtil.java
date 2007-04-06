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
import org.apache.axis.MessageContext;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.RPCElement;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class provides some methods to deserialize ec specifications and reports.
 * 
 * @author regli
 */
public class DeserializerUtil {

	/**	logger */
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

		return (ECSpec)deserialize(inputStream, ECSpec.class, ecSpecQName);
		
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
		
		return (ECSpec)deserialize(new FileInputStream(pathName), ECSpec.class, ecSpecQName);
		
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
		
		return (ECReport)deserialize(inputStream, ECReport.class, ecReportQName);
		
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
		
		return (ECReports)deserialize(inputStream, ECReports.class, ecReportsQName);
		
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
		
		//TODO: add type mapping for ECTimeUnit remove class org.apache.axis.encoding.ser.SimpleSerializer
		
		// add type mapping for ECTimeUnit
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