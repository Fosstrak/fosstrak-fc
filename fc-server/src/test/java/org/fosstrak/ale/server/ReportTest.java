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

package org.accada.ale.server;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.accada.ale.server.EventCycle;
import org.accada.ale.server.Report;
import org.accada.ale.server.ReportsGenerator;
import org.accada.ale.server.readers.gen.LogicalReader;
import org.accada.ale.util.ECReportSetEnum;
import org.accada.ale.util.ECTimeUnit;
import org.accada.ale.util.HexUtil;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.accada.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.accada.ale.xsd.ale.epcglobal.ECGroupSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroup;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportSpec;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.ECTime;
import org.accada.reader.rprm.core.msg.notification.TagType;
import org.apache.log4j.PropertyConfigurator;

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
	private static final byte[] TAG_ID = new byte[] {1, 2, 3};
	private static final String TAG_PURE_URI = "urn:epc:pat:gid-96:1.2.3";
	private static final String TAG_TAG_URI = "urn:epc:pat:gid-96:1.2.3";
	
	// default group tag parameters
	private static final byte[] DEFAULT_GROUP_TAG_ID = new byte[] {1, 11, 3};
	private static final String DEFAULT_GROUP_TAG_PURE_URI = "urn:epc:pat:gid-96:1.11.3";
	private static final String DEFAULT_GROUP_TAG_TAG_URI = "urn:epc:pat:gid-96:1.11.3";
	
	// last event cycle default tag parameters
	private static final byte[] LAST_CYCLE_TAG_ID = new byte[] {1, 3, 3};
	private static final String LAST_CYCLE_TAG_PURE_URI = "urn:epc:pat:gid-96:1.3.3";
	private static final String LAST_CYCLE_TAG_TAG_URI = "urn:epc:pat:gid-96:1.3.3";
	
	// last event cycle default group tag parameters
	private static final byte[] LAST_CYCLE_DEFAULT_GROUP_TAG_ID = new byte[] {1, 12, 3};
	private static final String LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI = "urn:epc:pat:gid-96:1.12.3";
	private static final String LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI = "urn:epc:pat:gid-96:1.12.3";
	
	// not included tag parameters
	private static final byte[] EXCLUDED_TAG_ID = new byte[] {5, 2, 3};
	private static final String EXCLUDED_TAG_PURE_URI = "urn:epc:pat:gid-96:5.2.3";
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
		report.addTag(createTag(TAG_ID, TAG_PURE_URI, TAG_TAG_URI));
		report.addTag(createTag(DEFAULT_GROUP_TAG_ID, DEFAULT_GROUP_TAG_PURE_URI, DEFAULT_GROUP_TAG_TAG_URI));
		report.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		
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
				assertEquals(DEFAULT_GROUP_TAG_PURE_URI, member.getRawDecimal().getValue());
				assertEquals(HexUtil.byteArrayToHexString(DEFAULT_GROUP_TAG_ID), member.getRawHex().getValue());
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
				assertEquals(TAG_PURE_URI, member.getRawDecimal().getValue());
				assertEquals(HexUtil.byteArrayToHexString(TAG_ID), member.getRawHex().getValue());
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
		report.addTag(createTag(TAG_ID, TAG_PURE_URI, TAG_TAG_URI));
		report.addTag(createTag(DEFAULT_GROUP_TAG_ID, DEFAULT_GROUP_TAG_PURE_URI, DEFAULT_GROUP_TAG_TAG_URI));
		report.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		

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
				assertEquals(DEFAULT_GROUP_TAG_PURE_URI, member.getRawDecimal().getValue());
				assertEquals(HexUtil.byteArrayToHexString(DEFAULT_GROUP_TAG_ID), member.getRawHex().getValue());
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
				assertEquals(TAG_PURE_URI, member.getRawDecimal().getValue());
				assertEquals(HexUtil.byteArrayToHexString(TAG_ID), member.getRawHex().getValue());
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
		eventCycle.addTag(createTag(LAST_CYCLE_TAG_ID, LAST_CYCLE_TAG_PURE_URI, LAST_CYCLE_TAG_TAG_URI));
		eventCycle.addTag(createTag(LAST_CYCLE_DEFAULT_GROUP_TAG_ID, LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI,
				LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI));
		eventCycle.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		
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
		eventCycle.addTag(createTag(TAG_ID, TAG_PURE_URI, TAG_TAG_URI));
		eventCycle.addTag(createTag(DEFAULT_GROUP_TAG_ID, DEFAULT_GROUP_TAG_PURE_URI, DEFAULT_GROUP_TAG_TAG_URI));
		eventCycle.addTag(createTag(EXCLUDED_TAG_ID, EXCLUDED_TAG_PURE_URI, EXCLUDED_TAG_TAG_URI));
		
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
				assertEquals(LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI, member.getRawDecimal().getValue());
				assertEquals(HexUtil.byteArrayToHexString(LAST_CYCLE_DEFAULT_GROUP_TAG_ID), member.getRawHex().getValue());
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
				assertEquals(LAST_CYCLE_TAG_PURE_URI, member.getRawDecimal().getValue());
				assertEquals(HexUtil.byteArrayToHexString(LAST_CYCLE_TAG_ID), member.getRawHex().getValue());
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
		eventCycle.addTag(createTag(LAST_CYCLE_TAG_ID, LAST_CYCLE_TAG_PURE_URI, LAST_CYCLE_TAG_TAG_URI));
		eventCycle.addTag(createTag(LAST_CYCLE_DEFAULT_GROUP_TAG_ID, LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI,
				LAST_CYCLE_DEFAULT_GROUP_TAG_TAG_URI));
		
		// get ECReport
		ECReport ecReport = report.getECReport();

		// test report name
		assertEquals(REPORT_NAME, ecReport.getReportName());

		// test groups
		assertEquals(ecReport.getGroup().size(), 0);
		
	}
	
	private TagType createTag(byte[] tag_id, String tag_pure_uri, String tag_tag_uri) {
		
		TagType tag = new TagType();
		tag.setTagID(tag_id);
		tag.setTagIDAsPureURI(tag_pure_uri);
		tag.setTagIDAsTagURI(tag_tag_uri);
		
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
		tag1.setTagIDAsPureURI(LAST_CYCLE_TAG_PURE_URI);
		tag1.setTagID(LAST_CYCLE_TAG_ID);
		tags.add(tag1);
		Tag tag2 = new Tag();
		tag2.setTagIDAsPureURI(LAST_CYCLE_DEFAULT_GROUP_TAG_PURE_URI);
		tag2.setTagID(LAST_CYCLE_DEFAULT_GROUP_TAG_ID);
		tags.add(tag2);
		Tag tag3 = new Tag();
		tag3.setTagIDAsPureURI(EXCLUDED_TAG_PURE_URI);
		tag3.setTagID(EXCLUDED_TAG_ID);
		tags.add(tag3);
		
		
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