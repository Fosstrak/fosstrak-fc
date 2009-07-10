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

package org.fosstrak.ale.server;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;
import org.fosstrak.ale.util.ECReportSetEnum;
import org.fosstrak.ale.util.ECTimeUnit;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECGroupSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSetSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;
import org.fosstrak.tdt.TDTEngine;

/**
 * @author regli
 */
public class ReportTest extends TestCase {

	// default report spec parameters
	private static final String REPORT_NAME = "TestReport";
	private static final boolean REPORT_ONLY_ON_CHANGE = false;
	private static final boolean REPORT_IF_EMPTY = true;
	
	// default set spec parameters
	private static final String SET_SPEC = ECReportSetEnum.CURRENT;
	
	// default filter spec parameters
	private static final List<String> INCLUDE_PATTERNS;
	private static final List<String> EXCLUDE_PATTERNS;
	
	// default group spec parameters
	private static final List<String> GROUP_PATTERNS;
	
	// default output sepc parameters
	private static final boolean INCLUDE_COUNT = true;
	private static final boolean INCLUDE_EPC = true;
	private static final boolean INCLUDE_RAW_DECIMAL = true;
	private static final boolean INCLUDE_RAW_HEX = true;
	private static final boolean INCLUDE_TAG = true;
	
	// default tag parameters
	private static final String TAG_TAG_URI = "urn:epc:tag:gid-96:123.456.789";
	private static final String TAG_RAW_DECIMAL = "urn:epc:raw:96.16402705662340665045136966421";
	private static final String TAG_RAW_HEX = "urn:epc:raw:96.x35000007B0001C8000000315";
	private static final String TAG_HEX = "35000007B0001C8000000315";
	
	// default group tag parameters
	private static final String DEFAULT_GROUP_TAG_TAG_URI = "urn:epc:tag:sgtin-96:3.237392.5270385.259425767589";
	private static final String DEFAULT_GROUP_TAG_RAW_DECIMAL = "urn:epc:raw:96.15001446348594317971238359205";
	private static final String DEFAULT_GROUP_TAG_RAW_HEX = "urn:epc:raw:96.x3078E7D4141ADC7C66FB10A5";
	private static final String DEFAULT_GROUP_TAG_HEX = "3078E7D4141ADC7C66FB10A5";
	
	// last event cycle default tag parameters
	private static final String LAST_CYCLE_TAG_TAG_URI = "urn:epc:pat:gid-96:1.3.3";
	private static final String LAST_CYCLE_TAG_RAW_DECIMAL = "urn:epc:raw:17.66307";
	private static final String LAST_CYCLE_TAG_RAW_HEX = "urn:epc:raw:17.x10303";
	
	// last event cycle default group tag parameters
	private static final String LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI = "urn:epc:pat:gid-96:1.12.3";
	private static final String LAST_CYCLE_DEFAULT_GROUP_RAW_DECIMAL = "urn:epc:raw:17.68611";
	private static final String LAST_CYCLE_DEFAULT_GROUP_RAW_HEX = "urn:epc:raw:17.x10c03";
	
	// not included tag parameters
	private static final String EXCLUDED_TAG_TAG_URI = "urn:epc:pat:gid-96:5.2.3";
	
	private static final String GROUP_NAME = "urn:epc:pat:gid-96:1.[0-10].3";
	
	private ECReportSpec reportSpec;
	private EventCycle eventCycle;
	
	static {
		GROUP_PATTERNS = new ArrayList<String>();
		GROUP_PATTERNS.add("urn:epc:pat:gid-96:1.[0-10].X");
		
		INCLUDE_PATTERNS = new ArrayList<String>();
		INCLUDE_PATTERNS.add("urn:epc:pat:gid-96:1.*.*");
		EXCLUDE_PATTERNS = new ArrayList<String>(); 
		EXCLUDE_PATTERNS.add("urn:epc:pat:gid-96:5.*.*");
	}
	
	protected void setUp() throws Exception {
		
		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
		// create ReportSpec
		reportSpec = createECReportSpec();
		
		// create EventCycle
		eventCycle = createEventCycle();
		
	}
	
	public void test_DefaultReportSpec() throws Exception {
		
		// create report
		Report report = new Report(reportSpec, eventCycle);
		
		// add event
		report.addTag(createTag(TAG_HEX, TAG_TAG_URI));
		report.addTag(createTag(DEFAULT_GROUP_TAG_HEX, DEFAULT_GROUP_TAG_TAG_URI));
		// FIXME report.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		
		// get ECReport
		ECReport ecReport = report.getECReport();
		
		// test report name
		assertEquals(REPORT_NAME, ecReport.getReportName());

		// test groups
		List<ECReportGroup> groups = ecReport.getGroup();
		assertEquals(2, groups.size());
		
		// test group
		for (ECReportGroup group : groups) {
			if (group.getGroupName() == null) {
				
				// test default group
				assertEquals(null, group.getGroupName());
				assertEquals(1, group.getGroupCount().getCount());
				
				// test members
				List<ECReportGroupListMember> members = group.getGroupList().getMember();
				assertEquals(1, members.size());
				
				// test member
				ECReportGroupListMember member = members.get(0);
				assertEquals(DEFAULT_GROUP_TAG_RAW_DECIMAL, member.getRawDecimal().getValue());
				assertEquals(DEFAULT_GROUP_TAG_RAW_HEX, member.getRawHex().getValue());
				assertEquals(DEFAULT_GROUP_TAG_TAG_URI, member.getTag().getValue());
				// TODO: test epc
				//assertEquals("unkown", member.getEpc());
				
			} else {
				
				// test group 'urn:epc:pat:gid-96:1.[0-10].3'
				assertEquals(GROUP_NAME, group.getGroupName());
				assertEquals(1, group.getGroupCount().getCount());
				
				// test members
				List<ECReportGroupListMember> members = group.getGroupList().getMember();
				assertEquals(1, members.size());
				
				// test member
				ECReportGroupListMember member = members.get(0);
				assertEquals(TAG_RAW_DECIMAL, member.getRawDecimal().getValue());
				assertEquals(TAG_RAW_HEX, member.getRawHex().getValue());
				assertEquals(TAG_TAG_URI, member.getTag().getValue());
				// TODO: test epc
				// assertEquals("unkown", member.getEpc());
				
			}
		}
		
	}
	
	public void test_AdditionsReportSpec() throws Exception {
		
		// modify spec
		reportSpec.getReportSet().setSet(ECReportSetEnum.ADDITIONS);
		
		// create report
		Report report = new Report(reportSpec, eventCycle);
		
		// add event
		report.addTag(createTag(TAG_HEX, TAG_TAG_URI));
		report.addTag(createTag(DEFAULT_GROUP_TAG_HEX, DEFAULT_GROUP_TAG_TAG_URI));
		// FIXME report.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		

		// get ECReport
		ECReport ecReport = report.getECReport();

		// test report name
		assertEquals(REPORT_NAME, ecReport.getReportName());

		// test groups
		List<ECReportGroup> groups = ecReport.getGroup();
		assertEquals(2, groups.size());
		
		// test group
		for (ECReportGroup group : groups) {
			if (group.getGroupName() == null) {
				
				// test default group
				assertEquals(null, group.getGroupName());
				assertEquals(1, group.getGroupCount().getCount());
				
				// test members
				List<ECReportGroupListMember> members = group.getGroupList().getMember();
				assertEquals(1, members.size());
				
				// test member
				ECReportGroupListMember member = members.get(0);

				assertEquals(DEFAULT_GROUP_TAG_RAW_DECIMAL, member.getRawDecimal().getValue());
				assertEquals(DEFAULT_GROUP_TAG_RAW_HEX, member.getRawHex().getValue());
				assertEquals(DEFAULT_GROUP_TAG_TAG_URI, member.getTag().getValue());
				// TODO: test epc
				//assertEquals("unkown", member.getEpc());
				
			} else {
				
				// test group 'urn:epc:pat:gid-96:1.[0-10].3'
				assertEquals(GROUP_NAME, group.getGroupName());
				assertEquals(1, group.getGroupCount().getCount());
				
				// test members
				List<ECReportGroupListMember> members = group.getGroupList().getMember();
				assertEquals(1, members.size());
				
				// test member
				ECReportGroupListMember member = members.get(0);
				
				assertEquals(TAG_RAW_DECIMAL, member.getRawDecimal().getValue());
				assertEquals(TAG_RAW_HEX, member.getRawHex().getValue());
				assertEquals(TAG_TAG_URI, member.getTag().getValue());
				// TODO: test epc
				// assertEquals("unkown", member.getEpc());
				
			}
		}
		
	}
	
	public void test_AdditionsReportWithoutAdditionSpec() throws Exception {
		
		// modify spec
		reportSpec.getReportSet().setSet(ECReportSetEnum.ADDITIONS);
		
		// create report
		Report report = new Report(reportSpec, eventCycle);
		
		// add event
		// FIXME eventCycle.addTag(createTag(LAST_CYCLE_TAG_ID, LAST_CYCLE_TAG_PURE_URI, LAST_CYCLE_TAG_TAG_URI));
		// FIXME eventCycle.addTag(createTag(LAST_CYCLE_DEFAULT_GROUP_TAG_ID, LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI,
		// FIXME 		LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI));
				// FIXME eventCycle.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		
		// get ECReport
		ECReport ecReport = report.getECReport();

		// test report name
		assertEquals(REPORT_NAME, ecReport.getReportName());

		// test groups
		assertEquals(ecReport.getGroup().size(), 0);
		
	}
	
	public void test_DeletionsReportSpec() throws Exception {
		
		// modify spec
		reportSpec.getReportSet().setSet(ECReportSetEnum.DELETIONS);
		
		// create report
		Report report = new Report(reportSpec, eventCycle);
		
		// add event
		eventCycle.addTag(createTag(TAG_HEX, TAG_TAG_URI));
		eventCycle.addTag(createTag(DEFAULT_GROUP_TAG_HEX, DEFAULT_GROUP_TAG_TAG_URI));
		// FIXME eventCycle.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		
		// get ECReport
		ECReport ecReport = report.getECReport();

		// test report name
		assertEquals(REPORT_NAME, ecReport.getReportName());

		// test groups
		List<ECReportGroup> groups = ecReport.getGroup();
		assertEquals(2, groups.size());
		
		// test group
		for (ECReportGroup group : groups) {
			if (group.getGroupName() == null) {
				
				// test default group
				assertEquals(null, group.getGroupName());
				assertEquals(1, group.getGroupCount().getCount());
				
				// test members
				List<ECReportGroupListMember> members = group.getGroupList().getMember();
				assertEquals(1, members.size());
				
				// test member
				ECReportGroupListMember member = members.get(0);
				assertEquals(LAST_CYCLE_DEFAULT_GROUP_RAW_DECIMAL, member.getRawDecimal().getValue());
				assertEquals(LAST_CYCLE_DEFAULT_GROUP_RAW_HEX, member.getRawHex().getValue());
				assertEquals(LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI, member.getTag().getValue());
				// TODO: test epc
				//assertEquals("unkown", member.getEpc());
				
			} else {
				
				// test group 'urn:epc:pat:gid-96:1.[0-10].3'
				assertEquals(GROUP_NAME, group.getGroupName());
				assertEquals(1, group.getGroupCount().getCount());
				
				// test members
				List<ECReportGroupListMember> members = group.getGroupList().getMember();
				assertEquals(1, members.size());
				
				// test member
				ECReportGroupListMember member = members.get(0);
				assertEquals(LAST_CYCLE_TAG_RAW_DECIMAL, member.getRawDecimal().getValue());
				assertEquals(LAST_CYCLE_TAG_RAW_HEX, member.getRawHex().getValue());
				assertEquals(LAST_CYCLE_TAG_TAG_URI, member.getTag().getValue());
				// TODO: test epc
				// assertEquals("unkown", member.getEpc());
				
			}
		}
		
	}
	
	public void test_DeletionsReportSpecWithoutDeletions() throws Exception {
		
		// modify spec
		reportSpec.getReportSet().setSet(ECReportSetEnum.DELETIONS);
		
		// create report
		Report report = new Report(reportSpec, eventCycle);
		
		// add event
		// FIXME eventCycle.addTag(createTag(LAST_CYCLE_TAG_ID, LAST_CYCLE_TAG_PURE_URI, LAST_CYCLE_TAG_TAG_URI));
		// FIXME eventCycle.addTag(createTag(LAST_CYCLE_DEFAULT_GROUP_TAG_ID, LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI,
		// FIXME 		LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI));
		
		// get ECReport
		ECReport ecReport = report.getECReport();

		// test report name
		assertEquals(REPORT_NAME, ecReport.getReportName());		
	}
	
	private Tag createTag(String hexValue, String pure) {
		TDTEngine tdt = Tag.getTDTEngine();
		Tag tag = new Tag();
		tag.setTagAsBinary(tdt.hex2bin(hexValue));
		tag.setTagIDAsPureURI(pure);
		
		return tag;		
	}

	private ECReportSpec createECReportSpec() {
		
		// create spec
		ECReportSpec spec = new ECReportSpec();
		
		// set paramteters
		spec.setReportName(REPORT_NAME);
		spec.setReportOnlyOnChange(REPORT_ONLY_ON_CHANGE);
		spec.setReportIfEmpty(REPORT_IF_EMPTY);
		spec.setReportSet(createECReportSetSpec());
		spec.setFilterSpec(createECFilterSpec());
		spec.setGroupSpec(new ECGroupSpec());
		spec.getGroupSpec().getPattern().addAll(GROUP_PATTERNS);
		spec.setOutput(createECReportOutputSpec());
		
		return spec;
		
	}

	private ECReportSetSpec createECReportSetSpec() {
		
		// create setSpec
		ECReportSetSpec setSpec = new ECReportSetSpec();
		
		// set parameters
		setSpec.setSet(SET_SPEC);
		
		return setSpec; 
		
	}
	
	private ECFilterSpec createECFilterSpec() {
		
		// create spec
		ECFilterSpec spec = new ECFilterSpec();
		
		// set include patterns
		spec.setIncludePatterns(new ECFilterSpec.IncludePatterns());
		spec.getIncludePatterns().getIncludePattern().addAll(INCLUDE_PATTERNS);
		
		// set exclude patterns
		spec.setExcludePatterns(new ECFilterSpec.ExcludePatterns());
		spec.getExcludePatterns().getExcludePattern().addAll(EXCLUDE_PATTERNS);
		
		return spec;
		
	}
	
	private ECReportOutputSpec createECReportOutputSpec() {
		
		// create spec
		ECReportOutputSpec spec = new ECReportOutputSpec();
		
		// set parameters
		spec.setIncludeCount(INCLUDE_COUNT);
		spec.setIncludeEPC(INCLUDE_EPC);
		spec.setIncludeRawDecimal(INCLUDE_RAW_DECIMAL);
		spec.setIncludeRawHex(INCLUDE_RAW_HEX);
		spec.setIncludeTag(INCLUDE_TAG);
		
		return spec;
		
	}
	
	private EventCycle createEventCycle() throws ImplementationExceptionResponse, ECSpecValidationExceptionResponse {
	
		// create EventCycle
		EventCycle eventCycle = new EventCycle(createReportsGenerator());
		eventCycle.setLastEventCycleTags(getLastEventCycleTags());
		
		return eventCycle;
		
	}
	
	private Set<Tag> getLastEventCycleTags() throws ImplementationExceptionResponse, ECSpecValidationExceptionResponse {
		
		// create EventCycle
		EventCycle eventCycle = new EventCycle(createReportsGenerator());
		Set<Tag> tags = new HashSet<Tag>();
		Tag tag1 = new Tag();
//// FIXME 		tag1.setTagIDAsPureURI(LAST_CYCLE_TAG_PURE_URI);
//		tag1.setTagAsBinary(new BigInteger(LAST_CYCLE_TAG_ID).toString(2));
//		tag1.setTagID(LAST_CYCLE_TAG_ID);
//		tags.add(tag1);
//		Tag tag2 = new Tag();
//		tag2.setTagIDAsPureURI(LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI);
//		tag2.setTagID(LAST_CYCLE_DEFAULT_GROUP_TAG_ID);
//		tag2.setTagAsBinary(new BigInteger(LAST_CYCLE_DEFAULT_GROUP_TAG_ID).toString(2));
//		tags.add(tag2);
//		Tag tag3 = new Tag();
//		tag3.setTagIDAsPureURI(EXCLUDED_TAG_PURE_URI);
//		tag3.setTagID(EXCLUDED_TAG_ID);
//		tag3.setTagAsBinary(new BigInteger(EXCLUDED_TAG_ID).toString(2));
//		tags.add(tag3);
		
		
		eventCycle.stop();
		
		return tags;
		
	}
	
	private ReportsGenerator createReportsGenerator() throws ECSpecValidationExceptionResponse, ImplementationExceptionResponse {
		
		// create ReportsGenerator
		ReportsGenerator reportsGenerator = new ReportsGenerator(REPORT_NAME, createECSpec());
		
		return reportsGenerator;
		
	}
	
	private ECSpec createECSpec() {
		
		// create ECSpec
		ECSpec ecSpec = new ECSpec();
		
		// set parameter
		ecSpec.setBoundarySpec(createECBoundarySpec());
		ecSpec.setIncludeSpecInReports(false);
		ecSpec.setLogicalReaders(new ECSpec.LogicalReaders());
		ecSpec.setReportSpecs(new ECSpec.ReportSpecs());
		ecSpec.getReportSpecs().getReportSpec().addAll(createECReportSpecs());
		
		return ecSpec;
		
	}
	
	private List<ECReportSpec> createECReportSpecs() {
		
		// create ECReportSpecs
		List<ECReportSpec> ecReportSpecs = new ArrayList<ECReportSpec>();
		
		// add report spec
		ecReportSpecs.add(reportSpec);
		
		return ecReportSpecs;
		
	}
	
	private ECBoundarySpec createECBoundarySpec() {
		
		// create spec
		ECBoundarySpec spec = new ECBoundarySpec();

		// set duration
		spec.setDuration(getECTimeInMS(0));
		
		// set repeat period
		spec.setRepeatPeriod(getECTimeInMS(0));
		
		// set stabel set interval
		spec.setStableSetInterval(getECTimeInMS(0));
		
		// set start trigger
		spec.setStartTrigger(null);
		
		// set stop trigger
		spec.setStopTrigger(null);
		
		return spec;
		
	}
	
	private ECTime getECTimeInMS(long value) {
		
		ECTime time = new ECTime();
		time.setUnit(ECTimeUnit.MS);
		time.setValue(value);
		
		return time;
		
	}
	
}