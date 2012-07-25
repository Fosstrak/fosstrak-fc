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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.fosstrak.ale.wsdl.alelr.epcglobal.AddReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetProperties;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.llrp.ltk.exceptions.InvalidLLRPMessageException;
import org.llrp.ltk.generated.messages.ADD_ACCESSSPEC;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;

/**
 * This class provides some methods to serialize ec specifications and reports.
 * 
 * @author swieland
 * @author regli
 * @author julian roche
 * @author wafa.soubra@orange.com
 */
public class SerializerUtil {
	
	// object factory for ALE
	private static final org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory objectFactoryALE = new org.fosstrak.ale.xsd.ale.epcglobal.ObjectFactory();
	
	// object factory for ALELR
	private static final org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory objectFactoryALELR = new org.fosstrak.ale.wsdl.alelr.epcglobal.ObjectFactory();
	
	// hash-map for JAXB context.
	private static final Map<String, JAXBContext> contexts = new ConcurrentHashMap<String, JAXBContext> ();
	
	// logger
	private static final Logger log = Logger.getLogger(SerializerUtil.class);
	
	/**
	 * This method serializes an ec specification to an xml and writes it into a writer.
	 * 
	 * @param ecSpec to serialize
	 * @param writer containing the xml
	 * @throws Exception upon error.
	 */
	public static void serializeECSpec(ECSpec ecSpec, OutputStream writer) throws Exception {	
		serializeECSpec(ecSpec, writer, false);		
	}
	
	/**
	 * This method serializes an ec specification to a well formed xml and writes it into a writer.
	 * 
	 * @param ecSpec to serialize
	 * @param writer to write the well formed xml into
	 * @throws Exception upon error.
	 */
	public static void serializeECSpecPretty(ECSpec ecSpec, OutputStream writer) throws Exception {		
		serializeECSpec(ecSpec, writer, true);		
	}
	
	/**
	 * This method serializes ec reports to an xml and writes it into a writer.
	 *  
	 * @param ecReports to serialize
	 * @param writer to write the xml into
	 * @throws Exception upon error.
	 */
	public static void serializeECReports(ECReports ecReports, Writer writer) throws Exception {		
		serializeECReports(ecReports, writer, false);		
	}
	
	/**
	 * This method serializes ec reports to a well formed xml and writes it into a writer.
	 *  
	 * @param ecReports to serialize
	 * @param writer to write the well formed xml into
	 * @throws Exception upon error.
	 */
	public static void serializeECReportsPretty(ECReports ecReports, Writer writer) throws Exception {		
		serializeECReports(ecReports, writer, true);		
	}
		
	/**
	 * This method serializes an LRSpec to an xml and writes it into a file.
	 * @param spec the LRSpec to be written into a file
	 * @param pathName the file where to store
	 * @param pretty flag whether well-formed xml or not
	 * @throws Exception upon error.
	 */
	public static void serializeLRSpec(LRSpec spec, String pathName, boolean pretty) throws Exception {
		marshall("org.fosstrak.ale.wsdl.ale.epcglobal", objectFactoryALE.createLRSpec(spec), pathName, pretty);
	}
	
	/**
	 * This method serializes an LRSpec to an xml and writes it into a file.
	 * @param spec the LRSpec to be written into a file
	 * @param pathName the file where to store
	 * @param pretty flag whether well-formed xml or not
	 * @throws Exception upon error.
	 */
	public static void serializeLRSpec(LRSpec spec, Writer writer) throws Exception {
		marshall("org.fosstrak.ale.wsdl.ale.epcglobal", objectFactoryALE.createLRSpec(spec), writer, true);
	}
	
	/**
	 * Serializes an SetProperties to xml and stores this xml into a file.
	 * @param props the SetProperties to be serialized.
	 * @param pathName the path to the file where to store the xml.
	 * @throws Exception upon error.
	 */
	public static void serializeSetProperties(SetProperties props, String pathName) throws Exception {
		marshall("org.fosstrak.ale.wsdl.alelr.epcglobal", objectFactoryALELR.createSetProperties(props), pathName, true);
	}
	
	/**
	 * Serializes a RemoveReaders to xml and stores this xml into a file.
	 * @param readers the RemoveReaders to be serialized.
	 * @param pathName the path to the file where to store the xml.
	 * @throws Exception upon error.
	 */
	public static void serializeRemoveReaders(RemoveReaders readers, String pathName) throws Exception {
		marshall("org.fosstrak.ale.wsdl.alelr.epcglobal", objectFactoryALELR.createRemoveReaders(readers), pathName, true);
	}
	
	/**
	 * Serializes a SetReaders to xml and stores this xml into a file.
	 * @param readers the SetReaders to be serialized.
	 * @param pathName the path to the file where to store the xml.
	 * @throws Exception upon error.
	 */
	public static void serializeSetReaders(SetReaders readers, String pathName) throws Exception {
		marshall("org.fosstrak.ale.wsdl.alelr.epcglobal", objectFactoryALELR.createSetReaders(readers), pathName, true);
	}
	
	/**
	 * Serializes an AddReaders to xml and stores this xml into a file.
	 * @param readers the AddReaders to be serialized.
	 * @param pathName the path to the file where to store the xml.
	 * @throws Exception upon error.
	 */
	public static void serializeAddReaders(AddReaders readers, String pathName) throws Exception {
		marshall("org.fosstrak.ale.wsdl.alelr.epcglobal", objectFactoryALELR.createAddReaders(readers), pathName, true);
	}
	
	
	//
	// private methods
	//
	
	/**
	 * This method serializes an ec specification to an xml and writes it into a writer.
	 * 
	 * @param ecSpec to serialize
	 * @param writer to write the xml into
	 * @param pretty indicates if the xml should be well formed or not
	 * @throws Exception upon error.
	 */
	private static void serializeECSpec(ECSpec ecSpec, OutputStream writer, boolean pretty) throws Exception {
		marshall("org.fosstrak.ale.xsd.ale.epcglobal", objectFactoryALE.createECSpec(ecSpec), writer, pretty);
	}
	
	
	/**
	 * This method serializes en ECSpec to an xml and writes it into a writer.
	 * @param ecSpec spec to be serialized.
	 * @param writer to writer where to store.
	 * @throws Exception upon error.
	 */
	public static void serializeECSpec(ECSpec ecSpec, Writer writer) throws Exception {
		marshall("org.fosstrak.ale.xsd.ale.epcglobal", objectFactoryALE.createECSpec(ecSpec), writer, true);		
	}	
	
	
	/**
	 * This method serializes ec reports to an xml and writes it into a writer.
	 * 
	 * @param ecReports to serialize
	 * @param writer to write the xml into
	 * @param pretty indicates if the xml should be well formed or not
	 * @throws Exception upon error.
	 * @throws IOException if deserialization fails
	 */
	private static void serializeECReports(ECReports ecReports, Writer writer, boolean pretty) throws Exception {
		marshall("org.fosstrak.ale.xsd.ale.epcglobal", objectFactoryALE.createECReports(ecReports), writer, pretty);	
	}
	
	/**
	 * marshalles the object into the stream. if errors occur, they are written to the log.
	 * @param marshaller the marshaller.
	 * @param pretty if formatted or not.
	 * @param o the object to write.
	 * @param of the output stream.
	 * @throws Exception upon error.
	 */
	private static void marshall(String jaxbContext, Object o, Object of, boolean pretty) throws Exception {
		try {
			Marshaller marshaller = getMarshaller(jaxbContext, pretty);
			if (of instanceof Writer) {
				marshaller.marshal(o, (Writer) of);
			} else {
				OutputStream fof = null;
				if (of instanceof String) {
					fof = new FileOutputStream((String) of);
				} else if (of instanceof OutputStream) {
					fof = (OutputStream) of;
				} else {
					throw new Exception("Wrong writer provided.");
				}
				marshaller.marshal(o, fof);
			}
				
		} catch (Exception e) {
			log.error(String.format("Caught exception during marshalling:\n%s", e));
			throw e;
		}
	}
	
	/**
	 * creates a marshaller on pooled JAXBContext instances.
	 * @param jaxbContext the context on which to create a marshaller.
	 * @param pretty if formatted or not.
	 * @return the marshaller.
	 * @throws JAXBException when unable to create the marshaller.
	 */
	private static Marshaller getMarshaller(String jaxbContext, boolean pretty) throws JAXBException {
		JAXBContext context = null;
		synchronized (contexts) {
			context = contexts.get(jaxbContext);
			if (null == context) {
				context = JAXBContext.newInstance(jaxbContext);
				contexts.put(jaxbContext, context);
			}
		}
		Marshaller marshaller = null;
		synchronized (context) {
			marshaller = context.createMarshaller();
		}			
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(pretty));
		return marshaller;
	}
	
	/**
	 * ORANGE: This method serializes a ADD_ROSPEC to an xml and writes it into a file.
	 * @param addRoSpec containing the ADD_ROSPEC to be written into a file
	 * @param pathName the file where to store
	 * @throws IOException whenever an io problem occurs
	 */
	public static void serializeAddROSpec(ADD_ROSPEC addRoSpec, String pathName) throws IOException {
		try {
			Document document = addRoSpec.encodeXML();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(document, new FileOutputStream(pathName));
		} catch (InvalidLLRPMessageException e) {
			log.error("could not serialize AddROSpec:", e);
		}
	}
	
	/**
	 * ORANGE: This method serializes a ADD_ROSPEC to an xml and writes it into a file.
	 * @param roSpec containing the ADD_ROSPEC to be written into a file
	 * @param writer to write the xml into
	 * @throws IOException whenever an io problem occurs
	 */
	public static void serializeAddROSpec(ADD_ROSPEC addRoSpec, Writer writer) throws IOException {
		try {
			Document document = addRoSpec.encodeXML();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(document, writer);
		} catch (InvalidLLRPMessageException e) {
			log.error("could not serialize AddROSpec:", e);
		}
	}
	
	
	/**
	 * ORANGE: This method serializes an ADD_ACCESSSPEC to an xml and writes it into a file.
	 * @param addAccessSpec containing the ADD_ACCESSSPEC to be written into a file
	 * @param pathName the file where to store
	 * @throws IOException whenever an io problem occurs
	 */
	public static void serializeAddAccessSpec(ADD_ACCESSSPEC addAccessSpec, String pathName) throws IOException {
		try {
			Document document = addAccessSpec.encodeXML();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(document, new FileOutputStream(pathName));
		} catch (InvalidLLRPMessageException e) {
			log.error("could not serialize AddAccessSpec:", e);
		}
	}
	
	/**
	 * ORANGE: This method serializes an ADD_ACCESSSPEC to an xml and writes it into a file.
	 * @param addAccessSpec containing the ADD_ACCESSSPEC to be written into a file
	 * @param writer to write the xml into
	 * @throws IOException whenever an io problem occurs
	 */
	public static void serializeAddAccessSpec(ADD_ACCESSSPEC addAccessSpec, Writer writer) throws IOException {
		try {
			Document document = addAccessSpec.encodeXML();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(document, writer);
		} catch (InvalidLLRPMessageException e) {
			log.error("could not serialize AddAccessSpec:", e);
		}
	}
	
}