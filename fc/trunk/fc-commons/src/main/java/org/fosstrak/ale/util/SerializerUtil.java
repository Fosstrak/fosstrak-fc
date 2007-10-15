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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.namespace.QName;

import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.LRProperties;
import org.accada.ale.xsd.ale.epcglobal.LRSpec;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;

/**
 * This class provides some methods to serialize ec specifications and reports.
 * 
 * @author regli
 * @author sawielan
 */
public class SerializerUtil {

	/**
	 * This method serializes an ec specification to an xml and writes it into a writer.
	 * 
	 * @param ecSpec to serialize
	 * @param writer containing the xml
	 * @throws IOException if serialization fails
	 */
	public static void serializeECSpec(ECSpec ecSpec, Writer writer) throws IOException {
	
		serializeECSpec(ecSpec, writer, false);
		
	}
	
	/**
	 * This method serializes an ec specification to a well formed xml and writes it into a writer.
	 * 
	 * @param ecSpec to serialize
	 * @param writer to write the well formed xml into
	 * @throws IOException if serialization fails
	 */
	public static void serializeECSpecPretty(ECSpec ecSpec, Writer writer) throws IOException {
		
		serializeECSpec(ecSpec, writer, true);
		
	}
	
	/**
	 * This method serializes an ec report to an xml and writes it into a writer.
	 * 
	 * @param ecReport to serialize
	 * @param writer to write the xml into
	 * @throws IOException if serialization fails
	 */
	public static void serializeECReport(ECReport ecReport, Writer writer) throws IOException {
		
		serializeECReport(ecReport, writer, false);
		
	}
	
	/**
	 * This method serializes an ec report to a well formed xml and writes it into a writer.
	 *  
	 * @param ecReport to serialize
	 * @param writer to write the well formed xml into
	 * @throws IOException if serialization fails
	 */
	public static void serializeECReportPretty(ECReport ecReport, Writer writer) throws IOException {
		
		serializeECReport(ecReport, writer, true);
		
	}
	
	/**
	 * This method serializes ec reports to an xml and writes it into a writer.
	 *  
	 * @param ecReports to serialize
	 * @param writer to write the xml into
	 * @throws IOException if serialization fails
	 */
	public static void serializeECReports(ECReports ecReports, Writer writer) throws IOException {
		
		serializeECReports(ecReports, writer, false);
		
	}
	
	/**
	 * This method serializes ec reports to a well formed xml and writes it into a writer.
	 *  
	 * @param ecReports to serialize
	 * @param writer to write the well formed xml into
	 * @throws IOException if serialization fails
	 */
	public static void serializeECReportsPretty(ECReports ecReports, Writer writer) throws IOException {
		
		serializeECReports(ecReports, writer, true);
		
	}
		
	/**
	 * This method serializes an LRSpec to an xml and writes it into a file.
	 * @param spec the LRSpec to be written into a file
	 * @param pathName the file where to store
	 * @param pretty flag whether well-formed xml or not
	 * @throws IOException whenever an io problem occurs
	 */
	public static void serializeLRSpec(LRSpec spec, String pathName, boolean pretty) throws IOException {
		QName qName = new QName("urn:epcglobal:ale:xsd:1", "LRSpec");
		
		FileWriter writer = new FileWriter(new File(pathName));
		SerializationContext context = new SerializationContext(writer);
		context.setPretty(pretty);
		Serializer serializer = LRSpec.getSerializer(null, LRSpec.class, qName);
		serializer.serialize(qName, null, spec, context);
		writer.flush();
		writer.close();		
	}
	
	/**
	 * This method serializes LRProperties into a file in xml format.
	 * @param prop the Properties to be serialized
	 * @param pathName the path to the file where to store the serialized xml.
	 * @param pretty flag whether well-formed xml or not
	 * @throws IOException if deserialization fails
	 */
	public static void serializeLRProperties(LRProperties prop, String pathName, boolean pretty) throws IOException {
		QName qName = new QName("urn:epcglobal:ale:xsd:1", "LRProperties");
		
		FileWriter writer = new FileWriter(new File(pathName));
		SerializationContext context = new SerializationContext(writer);
		context.setPretty(pretty);
		Serializer serializer = LRProperties.getSerializer(null, LRProperties.class, qName);
		serializer.serialize(qName, null, prop, context);
		writer.flush();
		writer.close();
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
	 * @throws IOException if deserialization fails
	 */
	private static void serializeECSpec(ECSpec ecSpec, Writer writer, boolean pretty) throws IOException {
		
		QName qName = new QName("urn:epcglobal:ale:xsd:1", "ECSpec");
		
		SerializationContext context = new SerializationContext(writer);
		context.setPretty(pretty);
		Serializer serializer = ECSpec.getSerializer(null, ECSpec.class, qName);
		serializer.serialize(qName, null, ecSpec, context);
		
	}
	
	/**
	 * This method serializes an ec report to an xml and writes it into a writer.
	 * 
	 * @param ecReport to serialize
	 * @param writer to write the xml into
	 * @param pretty indicates if the xml should be well formed or not
	 * @throws IOException if deserialization fails
	 */
	private static void serializeECReport(ECReport ecReport, Writer writer, boolean pretty) throws IOException {
		
		QName qName = new QName("urn:epcglobal:ale:xsd:1", "ECReport");
		
		SerializationContext context = new SerializationContext(writer);
		context.setPretty(pretty);
		Serializer serializer = ECReport.getSerializer(null, ECReport.class, qName);
		serializer.serialize(qName, null, ecReport, context);
		
	}
	
	/**
	 * This method serializes ec reports to an xml and writes it into a writer.
	 * 
	 * @param ecReports to serialize
	 * @param writer to write the xml into
	 * @param pretty indicates if the xml should be well formed or not
	 * @throws IOException if deserialization fails
	 */
	private static void serializeECReports(ECReports ecReports, Writer writer, boolean pretty) throws IOException {
		
		QName qName = new QName("urn:epcglobal:ale:xsd:1", "ECReports");
		
		SerializationContext context = new SerializationContext(writer);
		context.setPretty(pretty);
		Serializer serializer = ECReports.getSerializer(null, ECReports.class, qName);
		serializer.serialize(qName, null, ecReports, context);
	}
}