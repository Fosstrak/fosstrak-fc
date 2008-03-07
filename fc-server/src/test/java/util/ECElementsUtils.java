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

package util;

import java.util.ArrayList;
import java.util.List;

import org.accada.ale.util.ECReportSetEnum;
import org.accada.ale.util.ECTimeUnit;
import org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.accada.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.accada.ale.xsd.ale.epcglobal.ECGroupSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReport;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroup;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroupCount;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroupList;
import org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.ECTime;
import org.accada.ale.xsd.epcglobal.EPC;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

public class ECElementsUtils extends Assert {

	// default spec parameters
	private static final String REPORT_NAME = "TestReport";
	private static final List<String> LOGICAL_READER_NAMES;
	private static final Boolean INCLUDE_SPEC_IN_REPORTS = true;
	private static final long DURATION = 3000;
	private static final long REPEAT_PERIOD = 5000;
	private static final long STABLE_SET_INTERVAL = 0;
	private static final String START_TRIGGER = null;
	private static final String STOP_TRIGGER = null;
	private static final boolean REPORT_ONLY_ON_CHANGE = false;
	private static final boolean REPORT_IF_EMPTY = true;
	private static final String SET_SPEC = ECReportSetEnum.CURRENT;
	private static final List<String> INCLUDE_PATTERNS;
	private static final List<String> EXCLUDE_PATTERNS;
	private static final List<String> GROUP_PATTERNS;
	private static final boolean INCLUDE_COUNT = true;
	private static final boolean INCLUDE_EPC = true;
	private static final boolean INCLUDE_RAW_DECIMAL = false;
	private static final boolean INCLUDE_RAW_HEX = true;
	private static final boolean INCLUDE_TAG = true;
	private static final String ALEID = "TestAleId";
	private static final String SPEC_NAME = "TestSpecName";
	private static final String REPORT_GROUP_NAME = "TestReportGroupName";
	private static final String[] EPC = new String[] {"epc1", "epc2", "epc3"};
	private static final String[] RAW_DEC = new String[] {"dec1", "dec2", "dec3"};
	private static final String[] RAW_HEX = new String[] {"hex1", "hex2", "hex3"};
	private static final String[] TAGS = new String[] {"tag1", "tag2", "tag3"};
	
	static {
		LOGICAL_READER_NAMES = new ArrayList<String>();
		LOGICAL_READER_NAMES.add("LogicalReader1");
		LOGICAL_READER_NAMES.add("LogicalReader2");
		
		INCLUDE_PATTERNS = new ArrayList<String>();
		INCLUDE_PATTERNS.add("urn:epc:pat:gid-96:1.2.3");
		INCLUDE_PATTERNS.add("urn:epc:pat:gid-96:1.2.*");
		
		EXCLUDE_PATTERNS = new ArrayList<String>();
		EXCLUDE_PATTERNS.add("urn:epc:pat:sgtin-64:1.2.3.[1-10]");
		EXCLUDE_PATTERNS.add("urn:epc:pat:sgtin-64:1.2.*.*");
		
		GROUP_PATTERNS = new ArrayList<String>();
		GROUP_PATTERNS.add("urn:epc:pat:sscc-64:1.[0-99].X");
		GROUP_PATTERNS.add("urn:epc:pat:sscc-64:1.[100-1000].X");
	}
	
	public static ECSpec createECSpec() {
		
		// create spec
		ECSpec spec = new ECSpec();
		
		// set parameters
		spec.setBoundarySpec(createECBoundarySpec());
		spec.getLogicalReaders().getLogicalReader().addAll(LOGICAL_READER_NAMES);
		spec.setReportSpecs(new ECSpec.ReportSpecs());
		spec.getReportSpecs().getReportSpec().addAll(createECReportSpecs());
		spec.setIncludeSpecInReports(INCLUDE_SPEC_IN_REPORTS);
		
		return spec;
		
	}
	
	public static ECBoundarySpec createECBoundarySpec() {
		
		// create spec
		ECBoundarySpec spec = new ECBoundarySpec();

		// set duration
		spec.setDuration(getECTimeInMS(DURATION));
		
		// set repeat period
		spec.setRepeatPeriod(getECTimeInMS(REPEAT_PERIOD));
		
		// set stabel set interval
		spec.setStableSetInterval(getECTimeInMS(STABLE_SET_INTERVAL));
		
		// set start trigger
		spec.setStartTrigger(START_TRIGGER);
		
		// set stop trigger
		spec.setStopTrigger(STOP_TRIGGER);
		
		return spec;
		
	}
	
	public static List<ECReportSpec> createECReportSpecs() {
		
		// create specs
		List<ECReportSpec> specs = new ArrayList<ECReportSpec>();
		
		// add spec
		specs.add(createECReportSpec());
		
		return specs;
		
	}
	
	public static ECReportSpec createECReportSpec() {
		
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

	public static ECReportSetSpec createECReportSetSpec() {
		
		// create setSpec
		ECReportSetSpec setSpec = new ECReportSetSpec();
		
		// set parameters
		setSpec.setSet(SET_SPEC);
		
		return setSpec; 
		
	}
	
	public static ECFilterSpec createECFilterSpec() {
		
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

	public static ECReportOutputSpec createECReportOutputSpec() {
		
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
	
	public static ECReports createECReports() {
		
		// create reports
		ECReports reports = new ECReports();
		
		// set parameters
		reports.setALEID(ALEID);
		reports.setDate(null);
		reports.setECSpec(null);
		reports.setReports(new ECReports.Reports());
		reports.getReports().getReport().addAll(createECReportList());
		reports.setSchemaURL("");
		reports.setSpecName(SPEC_NAME);
		reports.setTerminationCondition(null);
		reports.setTotalMilliseconds(1000);
		
		return reports;
		
	}
	
	public static List<ECReport> createECReportList() {
		
		// create report list
		List<ECReport> ecReports = new ArrayList<ECReport>();
		
		// set reports
		ecReports.add(createECReport());
		
		return ecReports;
		
	}
	
	public static ECReport createECReport() {
		
		// create report
		ECReport report = new ECReport();
		
		// set name and group
		report.setReportName(REPORT_NAME);
		report.getGroup().add(createECReportGroup());
		
		return report;
		
	}
	
	public static ECReportGroup createECReportGroup() {
		
		// create report group
		ECReportGroup group = new ECReportGroup();
		
		// set name and members
		group.setGroupName(REPORT_GROUP_NAME);
		group.setGroupCount(createECReportGroupCount());
		group.setGroupList(createECReportGroupList());
		
		return group;
		
	}
	
	public static ECReportGroupCount createECReportGroupCount() {
		
		// create report group count
		ECReportGroupCount groupCount = new ECReportGroupCount();
		
		// set parameter
		groupCount.setCount(EPC.length);
		
		return groupCount;
		
	}
	
	public static ECReportGroupList createECReportGroupList() {
		
		// create report group list
		ECReportGroupList groupList = new ECReportGroupList();
		
		// add members
		List<ECReportGroupListMember> members = new ArrayList<ECReportGroupListMember>();
		for (int i = 0; i < EPC.length; i++) {
			members.add(createECReportGroupListMember(EPC[i], RAW_DEC[i], RAW_HEX[i], TAGS[i]));
		}
		groupList.getMember().addAll(members);
		
		return groupList;
		
	}
	
	public static ECReportGroupListMember createECReportGroupListMember(String epc, String rawDec, String rawHex, String tag) {
		
		// create report group member
		ECReportGroupListMember member = new ECReportGroupListMember();
		
		// set parameter
		EPC nepc = new EPC();
		nepc.setValue(epc);
		member.setEpc(new EPC());
		member.getEpc().setValue(epc);
		member.setRawDecimal(new EPC());
		member.getRawDecimal().setValue(rawDec);
		member.setRawHex(new EPC());
		member.getRawHex().setValue(rawHex);
		member.setTag(new EPC());
		member.getTag().setValue(tag);
	
		return member;
		
	}

	public static void assertEquals(ECSpec expected, ECSpec actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getBoundarySpec(), actual.getBoundarySpec());
		assertEquals(expected.getCreationDate(), actual.getCreationDate());
		assertEquals(expected.getExtension(), actual.getExtension());
		assertEquals(expected.getLogicalReaders(), actual.getLogicalReaders());
		assertEquals(expected.getReportSpecs(), actual.getReportSpecs());
		assertEquals(expected.getSchemaVersion(), actual.getSchemaVersion());
		
	}
	
	public static void assertEquals(ECBoundarySpec expected, ECBoundarySpec actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getDuration(), actual.getDuration());
		assertEquals(expected.getExtension(), actual.getExtension());
		assertEquals(expected.getRepeatPeriod(), actual.getRepeatPeriod());
		assertEquals(expected.getStableSetInterval(), actual.getStableSetInterval());
		assertEquals(expected.getStartTrigger(), actual.getStartTrigger());
		assertEquals(expected.getStopTrigger(), actual.getStopTrigger());
		
	}
	
	public static void assertEquals(ECTime expected, ECTime actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getValue(), actual.getValue());
		assertEquals(expected.getUnit(), actual.getUnit());
		
	}
	/*
	public static void assertEquals(ECTimeUnit expected, ECTimeUnit actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getValue(), actual.getValue());
			
	}*/
	
	public static void assertEquals(String expected, String actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected, actual);
		
	}
	
	public static void assertEquals(ECReportSpec[] expected, ECReportSpec[] actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.length, actual.length);
		for (ECReportSpec expectedReportSpec : expected) {
			boolean contains = false;
			for (ECReportSpec actualReportSpec : actual) {
				try {
					assertEquals(expectedReportSpec, actualReportSpec);
				} catch (Error e) {
					continue;
				}
				contains = true;
			}
			if (!contains) {
				throw new AssertionFailedError();
			}
		}
		
	}
	
	public static void assertEquals(ECReportSpec expected, ECReportSpec actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getGroupSpec(), actual.getGroupSpec());
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		assertEquals(expected.getFilterSpec(), actual.getFilterSpec());
		assertEquals(expected.getOutput(), actual.getOutput());
		assertEquals(expected.getReportName(), actual.getReportName());
		assertEquals(expected.getReportSet(), actual.getReportSet());
		
	}
	
	public static void assertEquals(ECFilterSpec expected, ECFilterSpec actual) {
	
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getExcludePatterns(), actual.getExcludePatterns());
		assertEquals(expected.getIncludePatterns(), actual.getIncludePatterns());
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		
	}
	
	public static void assertEquals(ECReportOutputSpec expected, ECReportOutputSpec actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.isIncludeCount(), actual.isIncludeCount());
		assertEquals(expected.isIncludeEPC(), actual.isIncludeEPC());
		assertEquals(expected.isIncludeRawDecimal(), actual.isIncludeRawDecimal());
		assertEquals(expected.isIncludeRawHex(), actual.isIncludeRawHex());
		assertEquals(expected.isIncludeTag(), actual.isIncludeTag());
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		
	}
	
	public static void assertEquals(ECReportSetSpec expected, ECReportSetSpec actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getSet(), actual.getSet());
		
	}
	/*
	public static void assertEquals(ECReportSetEnum expected, ECReportSetEnum actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getValue(), actual.getValue());
		
	}*/
	
	public static void assertEquals(ECReports expected, ECReports actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getALEID(), actual.getALEID());
		assertEquals(expected.getSchemaURL(), actual.getSchemaURL());
		assertEquals(expected.getSpecName(), actual.getSpecName());
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getDate(), actual.getDate());
		assertEquals(expected.getECSpec(), actual.getECSpec());
		assertEquals(expected.getExtension(), actual.getExtension());
		assertEquals(expected.getReports().getReport(), actual.getReports().getReport());
		assertEquals(expected.getTerminationCondition(), actual.getTerminationCondition());
		assertEquals(expected.getTotalMilliseconds(), actual.getTotalMilliseconds());

	}
	
	public static void assertEqualsReports(List<ECReport> expected, List<ECReport> actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.size(), actual.size());
		for (ECReport expectedReport : expected) {
			boolean contains = false;
			for (ECReport actualReport : actual) {
				try {
					assertEquals(expectedReport, actualReport);
				} catch (Error e) {
					continue;
				}
				contains = true;
			}
			if (!contains) {
				throw new AssertionFailedError();
			}
		}
		
	}
	
	public static void assertEquals(ECReport expected, ECReport actual) {

		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getReportName(), actual.getReportName());
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		
		List<ECReportGroup> expectedGroups = expected.getGroup();
		List<ECReportGroup> actualGroups = actual.getGroup();
		
		assertEquals(expectedGroups.size(), actualGroups.size());
		
		for (ECReportGroup expectedGroup : expectedGroups) {
			boolean contains = false;
			for (ECReportGroup actualGroup : actualGroups) {
				try {
					assertEquals(expectedGroup, actualGroup);
				} catch (Error e) {
					continue;
				}
				contains = true;
			}
			if (!contains) {
				throw new AssertionFailedError();
			}
		}
		
	}
	
	public static void assertEquals(ECReportGroup expected, ECReportGroup actual) {

		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		assertEquals(expected.getGroupCount(), actual.getGroupCount());
		assertEquals(expected.getGroupList(), actual.getGroupList());
		assertEquals(expected.getGroupName(), actual.getGroupName());
		
	}
	
	public static void assertEquals(ECReportGroupCount expected, ECReportGroupCount actual) {
	
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getCount(), actual.getCount());
		assertEquals(expected.getExtension(), actual.getExtension());
		
	}
	
	public static void assertEquals(ECReportGroupList expected, ECReportGroupList actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		
		List<ECReportGroupListMember> expectedMembers = expected.getMember();
		List<ECReportGroupListMember> actualMembers = actual.getMember();
		
		assertEquals(expectedMembers.size(), actualMembers.size());
		
		for (ECReportGroupListMember expectedMember : expectedMembers) {
			boolean contains = false;
			for (ECReportGroupListMember actualMember : actualMembers) {
				try {
					assertEquals(expectedMember, actualMember);
				} catch (Error e) {
					continue;
				}
				contains = true;
			}
			if (!contains) {
				throw new AssertionFailedError();
			}
		}
		
	}
	
	public static void assertEquals(ECReportGroupListMember expected, ECReportGroupListMember actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.getEpc(), actual.getEpc());
		assertEquals(expected.getRawDecimal(), actual.getRawDecimal());
		assertEquals(expected.getRawHex(), actual.getRawHex());
		assertEquals(expected.getTag(), actual.getTag());
		assertEquals(expected.getAny(), actual.getAny());
		assertEquals(expected.getExtension(), actual.getExtension());
		
	}
	
	public static void assertEqualsString(List<String> expected, List<String> actual) {
		
		if (expected == null || actual == null) {
			if (expected == null && actual == null) {
				return;
			} else {
				throw new AssertionFailedError();
			}
		}
		assertEquals(expected.size(), actual.size());
		for (String expectedString : expected) {
			boolean contains = false;
			for (String actualString : actual) {
				try {
					assertEquals(expectedString, actualString);
				} catch (Error e) {
					continue;
				}
				contains = true;
			}
			if (!contains) {
				throw new AssertionFailedError();
			}
		}
		
	}
	
	public static ECTime getECTimeInMS(long value) {
		
		ECTime time = new ECTime();
		time.setUnit(ECTimeUnit.MS);
		time.setValue(value);
		
		return time;
		
	}
	
}