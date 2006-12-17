/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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