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

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import org.fosstrak.ale.wsdl.alelr.epcglobal.AddReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.RemoveReaders;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetProperties;
import org.fosstrak.ale.wsdl.alelr.epcglobal.SetReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECGroupSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSetSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec.ExcludePatterns;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec.IncludePatterns;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports.Reports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.LogicalReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.ReportSpecs;

/**
 * @author swieland 
 * Test the serializer and deserializer utils. The tests only test the serializer and 
 * the deserializer algorithm (writing to and reading from JAXB) but not the JAXB serializer 
 * itself (we safely assume it to work correctly :-) ).
 */
public class SerializerAndDeserializerUtilsTest extends TestCase {

	private static final long DEFAULT_ECTIME_VALUE = 1000L;
	private static final long ECTIME_REPEAT_VALUE = 2000L;
	private static final long ECTIME_STABLESET_VALUE = 3000L;

	private static final String START_TRIGGER = "startTrigger";
	private static final String STOP_TRIGGER = "stopTrigger";
	
	private static final String FILTER_SPEC_EXCLUDE_PATTERN = "filterSpecExcludePattern";
	private static final String FILTER_SPEC_INCLUDE_PATTERN = "filterSpecIncludePattern";
	private static final String GROUP_SPEC_PATTERN = "groupSpecPattern";
	private static final String REPORT_NAME = "reportName";
	private static final String REPORT_SET = "reportSet";
	
	private static final String READER_NAME = "readerName";
	private static final String ADD_READER_NAME = "addReaderName";
	private static final String SET_READER_NAME = "setReaderName";
	private static final String REMOVE_READER_NAME = "setReaderName";
	private static final String SET_PROPERTY_NAME = "setPropertyName";

	private static final String SET_PROPERTY_NAMEVALUE_NAME = "setPropertyName";
	private static final String SET_PROPERTY_NAMEVALUE_VALUE = "setPropertyValue";
	
	private static final String ALE_ID = "aleID";
	
	private static final long TOTAL_MS = 2000L; 
	private static final String TERMINATION_TRIGGER = "terminationTrigger";
	private static final String TERMINATION_CONDITION = "terminationCondition";
	private static final String INITIATION_TRIGGER = "initiationTrigger";
	private static final String INITIATION_CONDITION = "initiationCondition";
	private static final String REPORT_SPEC_NAME = "reportSpecName";
	
	public void testECSpec() throws Exception {

		ECSpec ecSpec = createDummyECSpec();
		CharArrayWriter writer = new CharArrayWriter();
		SerializerUtil.serializeECSpec(ecSpec, writer);
		String str = writer.toString();
		ECSpec ecSpec2 = DeserializerUtil.deserializeECSpec(new ByteArrayInputStream(str.getBytes()));
		ensureSame(ecSpec, ecSpec2);
	}

	public void testSerializeECReports() throws Exception {
		ECReports ecReports = createDummyECReports();
		CharArrayWriter writer = new CharArrayWriter();
		SerializerUtil.serializeECReports(ecReports, writer);
		String str = writer.toString();
		ECReports ecReports2 = DeserializerUtil.deserializeECReports(new ByteArrayInputStream(str.getBytes()));
		ensureSame(ecReports, ecReports2);
	}

	public void testSerializeLRSpec() throws Exception {
		LRSpec lrSpec = createDummyLRSpec();
		CharArrayWriter writer = new CharArrayWriter();
		SerializerUtil.serializeLRSpec(lrSpec, writer);
		String str = writer.toString();
		LRSpec lrSpec2 = DeserializerUtil.deserializeLRSpec(new ByteArrayInputStream(str.getBytes()));
		ensureSame(lrSpec, lrSpec2);
	}

	public void testSerializeSetProperties() throws Exception {
		String fn = createTemporaryFileName();
		SetProperties setProperties = createDummySetProperties();
		SerializerUtil.serializeSetProperties(setProperties, fn);
		SetProperties setProperties2 = DeserializerUtil.deserializeSetProperties(fn);
		ensureSame(setProperties, setProperties2);
	}

	public void testSerializeRemoveReaders() throws Exception {
		String fn = createTemporaryFileName();
		RemoveReaders removeReaders = createDummyRemoveReaders();
		SerializerUtil.serializeRemoveReaders(removeReaders, fn);
		RemoveReaders removeReaders2 = DeserializerUtil.deserializeRemoveReaders(fn);
		ensureSame(removeReaders, removeReaders2);
	}

	public void testSerializeSetReaders() throws Exception {
		String fn = createTemporaryFileName();
		SetReaders setReaders = createDummySetReaders();
		SerializerUtil.serializeSetReaders(setReaders, fn);
		SetReaders setReaders2 = DeserializerUtil.deserializeSetReaders(fn);
		ensureSame(setReaders, setReaders2);
	}

	public void testSerializeAddReaders() throws Exception {
		String fn = createTemporaryFileName();
		AddReaders addReaders = createDummyAddReaders();
		SerializerUtil.serializeAddReaders(addReaders, fn);
		AddReaders addReaders2 = DeserializerUtil.deserializeAddReaders(fn);
		ensureSame(addReaders, addReaders2);
	}
	
	private String createTemporaryFileName() {
		String tempdir = System.getProperty("java.io.tmpdir");

		if (!(tempdir.endsWith("/") || tempdir.endsWith("\\"))) {
		   tempdir = tempdir + System.getProperty("file.separator");
		}
		
		return tempdir + String.format("aleSerializerTest_%d.temp", System.currentTimeMillis());
	}

	public SetProperties createDummySetProperties() {
		SetProperties setProperties = new SetProperties();
		SetProperties.Properties properties = new SetProperties.Properties();
		LRProperty property = new LRProperty();
		property.setName(SET_PROPERTY_NAMEVALUE_NAME);
		property.setValue(SET_PROPERTY_NAMEVALUE_VALUE);
		properties.getProperty().add(property);
		setProperties.setName(SET_PROPERTY_NAME);
		setProperties.setProperties(properties);
		return setProperties;
	}

	public RemoveReaders createDummyRemoveReaders() {
		RemoveReaders removeReaders = new RemoveReaders();
		RemoveReaders.Readers readers = new RemoveReaders.Readers();
		readers.getReader().add(READER_NAME);
		removeReaders.setName(REMOVE_READER_NAME);
		removeReaders.setReaders(readers);		
		return removeReaders;
	}

	public SetReaders createDummySetReaders() {
		SetReaders setReaders = new SetReaders();
		SetReaders.Readers readers = new SetReaders.Readers();
		readers.getReader().add(READER_NAME);
		setReaders.setName(SET_READER_NAME);
		setReaders.setReaders(readers);		
		return setReaders;
	}

	public AddReaders createDummyAddReaders() {
		AddReaders addReaders = new AddReaders();
		AddReaders.Readers readers = new AddReaders.Readers();
		readers.getReader().add(READER_NAME);
		addReaders.setName(ADD_READER_NAME);
		addReaders.setReaders(readers);
		return addReaders;
	}

	public ECSpec createDummyECSpec() throws Exception {
		ECSpec spec = new ECSpec();
		spec.setBoundarySpec(createDummyECBoundarySpec());
		spec.setCreationDate(createDummyCalendar());
		spec.setIncludeSpecInReports(true);
		spec.setLogicalReaders(createDummyLogicalReaders(new String[] {
				"LogicalReader1", "LogicalReader2" }));
		spec.setReportSpecs(createDummyReportSpecs());
		return spec;
	}

	public ECBoundarySpec createDummyECBoundarySpec() {
		ECBoundarySpec spec = new ECBoundarySpec();
		spec.setDuration(createDummyECTime(DEFAULT_ECTIME_VALUE));
		spec.setRepeatPeriod(createDummyECTime(ECTIME_REPEAT_VALUE));
		spec.setStableSetInterval(createDummyECTime(ECTIME_STABLESET_VALUE));
		spec.setStartTrigger(START_TRIGGER);
		spec.setStopTrigger(STOP_TRIGGER);
		return spec;
	}

	public ECTime createDummyECTime(long t) {
		ECTime time = new ECTime();
		time.setUnit(ECTimeUnit.MS);
		time.setValue(t);
		return time;
	}

	public XMLGregorianCalendar createDummyCalendar() throws Exception {
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2000, 10, 10));
	}

	public LogicalReaders createDummyLogicalReaders(String[] readers) {
		LogicalReaders spec = new LogicalReaders();
		for (String reader : readers) {
			spec.getLogicalReader().add(reader);
		}
		return spec;
	}

	public ReportSpecs createDummyReportSpecs() {
		ExcludePatterns excludePatterns = new ExcludePatterns();
		excludePatterns.getExcludePattern().add(FILTER_SPEC_EXCLUDE_PATTERN);
		IncludePatterns includePatterns = new IncludePatterns();
		includePatterns.getIncludePattern().add(FILTER_SPEC_INCLUDE_PATTERN);
		ECFilterSpec filterSpec = new ECFilterSpec();
		filterSpec.setExcludePatterns(excludePatterns);
		filterSpec.setIncludePatterns(includePatterns);
		
		ECGroupSpec groupSpec = new ECGroupSpec();
		groupSpec.getPattern().add(GROUP_SPEC_PATTERN);
		
		ECReportOutputSpec outputSpec = new ECReportOutputSpec();
		outputSpec.setIncludeCount(true);
		outputSpec.setIncludeEPC(true);
		outputSpec.setIncludeRawDecimal(true);
		outputSpec.setIncludeRawHex(true);
		outputSpec.setIncludeTag(true);
		
		ECReportSetSpec reportSet = new ECReportSetSpec();
		reportSet.setSet(REPORT_SET);
		
		ECReportSpec reportSpec = new ECReportSpec();
		reportSpec.setFilterSpec(filterSpec);
		reportSpec.setGroupSpec(groupSpec);
		reportSpec.setOutput(outputSpec);
		reportSpec.setReportIfEmpty(true);
		reportSpec.setReportName(REPORT_NAME);
		reportSpec.setReportOnlyOnChange(true);
		reportSpec.setReportSet(reportSet);
		
		ReportSpecs specs = new ReportSpecs();		
		specs.getReportSpec().add(reportSpec);
		return specs;
	}
	
	public ECReports createDummyECReports() throws Exception  {
		ECReports reports = new ECReports();
		reports.setALEID(ALE_ID);
		reports.setCreationDate(createDummyCalendar());
		reports.setDate(createDummyCalendar());
		reports.setECSpec(createDummyECSpec());
		reports.setInitiationCondition(INITIATION_CONDITION);
		reports.setInitiationTrigger(INITIATION_TRIGGER);
		reports.setReports(createDummyReports());
		reports.setSpecName(REPORT_SPEC_NAME);
		reports.setTerminationCondition(TERMINATION_CONDITION);
		reports.setTerminationTrigger(TERMINATION_TRIGGER);
		reports.setTotalMilliseconds(TOTAL_MS);
		return reports;
	}
	
	public Reports createDummyReports() {
		Reports reports = new Reports();
		reports.getReport().add(createDummyECReport());
		return reports;
	}

	public ECReport createDummyECReport() {
		ECReport report = new ECReport();
		report.setReportName(REPORT_NAME);
		report.getGroup().add(createDummyECReportGroup());
		return report;
	}
	
	public ECReportGroup createDummyECReportGroup() {
		ECReportGroup group = new ECReportGroup();
		return group;
	}

	public LRSpec createDummyLRSpec() throws Exception  {
		LRSpec lrSpec = new LRSpec();
		lrSpec.setCreationDate(createDummyCalendar());
		lrSpec.setIsComposite(false);
		LRSpec.Properties properties = new LRSpec.Properties();
		LRProperty property = new LRProperty();
		property.setName(SET_PROPERTY_NAMEVALUE_NAME);
		property.setValue(SET_PROPERTY_NAMEVALUE_VALUE);
		properties.getProperty().add(property);
		lrSpec.setProperties(properties);

		LRSpec.Readers readers = new LRSpec.Readers();
		readers.getReader().add(READER_NAME);
		lrSpec.setReaders(readers);
		return lrSpec;
	}
	


	


	

	private void ensureSame(SetProperties expected, SetProperties actual) {
		assertNotNull(actual);
		assertEquals(expected.getName(), actual.getName());
		assertNotNull(actual.getProperties());
		assertEquals(expected.getProperties().getProperty().size(), actual.getProperties().getProperty().size());
	}

	private void ensureSame(RemoveReaders expected, RemoveReaders actual) {
		assertNotNull(actual);
		assertEquals(expected.getName(), actual.getName());
		assertNotNull(actual.getReaders());
		assertEquals(expected.getReaders().getReader().size(), actual.getReaders().getReader().size());
	}

	private void ensureSame(SetReaders expected, SetReaders actual) {
		assertNotNull(actual);
		assertEquals(expected.getName(), actual.getName());
		assertNotNull(actual.getReaders());
		assertEquals(expected.getReaders().getReader().size(), actual.getReaders().getReader().size());
	}

	private void ensureSame(AddReaders expected, AddReaders actual) {
		assertNotNull(actual);
		assertEquals(expected.getName(), actual.getName());
		assertNotNull(actual.getReaders());
		assertEquals(expected.getReaders().getReader().size(), actual.getReaders().getReader().size());
	}

	private void ensureSame(ECSpec expected, ECSpec actual) {
		assertNotNull(actual);
		assertNotNull(actual.getCreationDate());
		assertEquals(expected.getCreationDate().getDay(), actual.getCreationDate().getDay());
		assertEquals(expected.getCreationDate().getMonth(), actual.getCreationDate().getMonth());
		assertEquals(expected.getCreationDate().getYear(), actual.getCreationDate().getYear());		
	}

	private void ensureSame(ECReports expected, ECReports actual) {
		assertNotNull(actual);
	}

	private void ensureSame(LRSpec expected, LRSpec actual) {
		assertNotNull(actual);		
	}
}
