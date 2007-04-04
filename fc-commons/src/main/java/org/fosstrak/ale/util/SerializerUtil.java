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

import java.io.IOException;
import java.io.Writer;

import javax.xml.namespace.QName;

import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;

/**
 * This class provides some methods to serialize ec specifications and reports.
 * 
 * @author regli
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
	
	//
	// private methods
	//
	
	/**
	 * This method serializes an ec specification to an xml and writes it into a writer.
	 * 
	 * @param ecSpec to serialize
	 * @param writer to write the xml into
	 * @param pretty indicates if the xml should be well formed or not
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
	 * @param ecReprt to serialize
	 * @param writer to write the xml into
	 * @param pretty indicates if the xml should be well formed or not
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
	 */
	private static void serializeECReports(ECReports ecReports, Writer writer, boolean pretty) throws IOException {
		
		QName qName = new QName("urn:epcglobal:ale:xsd:1", "ECReports");
		
		SerializationContext context = new SerializationContext(writer);
		context.setPretty(pretty);
		Serializer serializer = ECReports.getSerializer(null, ECReports.class, qName);
		serializer.serialize(qName, null, ecReports, context);
		
	}
	
}