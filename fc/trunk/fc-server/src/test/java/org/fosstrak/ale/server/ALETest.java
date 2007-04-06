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

import junit.framework.TestCase;

import org.accada.ale.server.ALE;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException;
import org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReportSpec;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.accada.ale.xsd.ale.epcglobal.ECTime;
import org.accada.ale.xsd.ale.epcglobal.ECTimeUnit;
import org.accada.ale.xsd.ale.epcglobal.ECTrigger;
import org.apache.log4j.PropertyConfigurator;

import util.ECElementsUtils;


/**
 * @author regli 
 */
public class ALETest extends TestCase {
	
	// ale properties
	private static final String ALE_PROPERTIES = "/ALE_without_a_inputgenerator.properties";
	
	// default spec parameters
	private static final String SPEC_NAME = "TestSpec";
	private static final String HTTP_NOTIFICATION_URI = "http://localhost:1234/dir/subdir";
	private static final String TCP_NOTIFICATION_URI = "tcp://localhost:1234";
	private static final String FILE_NOTIFICATION_URI = "file:///dir/subdir/file";
	private static final String STANDARD_VERSION = "1.0";
	private static final String VENDOR_VERSION = "0.1";
	
	private ECSpec spec;

	protected void setUp() throws Exception {
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
		super.setUp();
		ALE.initialize(ALE_PROPERTIES);
		spec = ECElementsUtils.createECSpec();
		
	}
	
	protected void tearDown() throws Exception {
		
		super.tearDown();
		ALE.close();
		
	}
	
	public void testDefineWithValidSpec() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
	}
	
	public void testDefineWithDuplicateName() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (DuplicateNameException e) {
			return;
		}
		
		fail("Should throw DuplicateNameException. Because there is already a spec with the same name.");
		
	}
	
	public void testDefineWithUnknownReaderName() throws Exception {
		
		String[] logicalReaders = spec.getLogicalReaders();
		String[] newLogicalReaders = new String[logicalReaders.length + 1];
		for (int i = 0; i < logicalReaders.length; i++) {
			newLogicalReaders[i] = logicalReaders[i];
		}
		newLogicalReaders[logicalReaders.length] = "UnknownReader";
		spec.setLogicalReaders(newLogicalReaders);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("LogicalReader 'UnknownReader' is unknown.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because of unknown reader name.");
		
	}
	
	public void testDefineWithInvalidStartTrigger() throws Exception {
		
		fail("Not yet implemented.");
		
	}
	
	public void testDefineWithInvalidStopTrigger() throws Exception {
		
		fail("Not yet implemented.");
		
	}
	
	public void testDefineWithNegativeDurationValue() throws Exception {
		
		ECTime duration = new ECTime();
		duration.setUnit(ECTimeUnit.MS);
		duration.set_value(-1);
		spec.getBoundarySpec().setDuration(duration);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The duration field of ECBoundarySpec is negative.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because the duration value is negative.");
		
	}
	
	public void testDefineWithNegativeStableSetIntervalValue() throws Exception {
		
		ECTime stableSetInterval = new ECTime();
		stableSetInterval.setUnit(ECTimeUnit.MS);
		stableSetInterval.set_value(-1);
		spec.getBoundarySpec().setStableSetInterval(stableSetInterval);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The stableSetInterval field of ECBoundarySpec is negative.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because the stable set interval value is negative.");
		
	}
	
	public void testDefineWithNegativeRepeatPeriodValue() throws Exception {
		
		spec.getBoundarySpec().setRepeatPeriod(ECElementsUtils.getECTimeInMS(-1));
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The repeatPeriod field of ECBoundarySpec is negative.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because the repeat period value is negative.");
		
	}
	
	public void testDefineWithSpecWithStartTriggerIsNonEmptyAndRepeatPeriodIsNonZero() throws Exception {
		
		ECBoundarySpec boundarySpec = spec.getBoundarySpec();
		boundarySpec.setStartTrigger(new ECTrigger());
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The startTrigger field of ECBoundarySpec is non-empty and the repeatPeriod " +
					"field of ECBoundarySpec is non-zero.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because the start trigger is non empty and " +
				"the repeat period is non zero.");
		
	}
	
	public void testDefineWithNoStoppingConditionSpecified() throws Exception {
		
		ECBoundarySpec boundarySpec = spec.getBoundarySpec();
		boundarySpec.setStopTrigger(null);
		boundarySpec.setDuration(null);
		boundarySpec.setStableSetInterval(null);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("No stopping condition is specified in ECBoundarySpec.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because no stopping condition is specified.");
		
	}
	
	public void testDefineWithEmptyListOfECReportSpecs() throws Exception {
		
		spec.setReportSpecs(new ECReportSpec[0]);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("List of ECReportSpec instances is empty.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because the list of ECReportSpecs is empty.");
		
	}
	
	public void testDefineWithTwoECReportSpecHavingTheSameName() throws Exception {
		
		ECReportSpec[] reportSpecs = spec.getReportSpecs();
		ECReportSpec[] newReportSpecs = new ECReportSpec[reportSpecs.length + 2];
		for (int i = 0; i < reportSpecs.length; i++) {
			newReportSpecs[i] = reportSpecs[i];
		}
		
		// add spec one
		ECReportSpec spec1 = new ECReportSpec();
		spec1.setReportName("SameName");
		newReportSpecs[reportSpecs.length - 1] = spec1;
		
		// add spec two
		ECReportSpec spec2 = new ECReportSpec();
		spec2.setReportName("SameName");
		newReportSpecs[reportSpecs.length] = spec2;
		
		spec.setReportSpecs(newReportSpecs);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Two ReportSpecs instances have identical names 'SameName'.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because two ECReportSpecs have identical names.");
		
	}
	
	public void testDefineWithoutBoundaryParameterSetInSpec() throws Exception {
		
		spec.setBoundarySpec(null);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The boundaries parameter of ECSpec is null.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there are no boundary parameters defined.");
		
	}
	
	public void testDefineWithInvalidFilterPatternWithWrongFormat() throws Exception {
		
		String[] includePatterns = spec.getReportSpecs()[0].getFilterSpec().getIncludePatterns();
		String[] newIncludePatterns = new String[includePatterns.length + 1];
		for (int i = 0; i < includePatterns.length; i++) {
			newIncludePatterns[i] = includePatterns[i];
		}
		newIncludePatterns[includePatterns.length] = "InvalidPattern";
		spec.getReportSpecs()[0].getFilterSpec().setIncludePatterns(newIncludePatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid Pattern 'InvalidPattern'. Pattern must have the form 'urn:epc:(urn | tag | pat | id | idpat | raw):tag-type:data-fields'.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid filter pattern in ECSpec.");
		
	}

	public void testDefineWithInvalidFilterPatternWithUnknownTagFormat() throws Exception {
		
		String[] includePatterns = spec.getReportSpecs()[0].getFilterSpec().getIncludePatterns();
		String[] newIncludePatterns = new String[includePatterns.length + 1];
		for (int i = 0; i < includePatterns.length; i++) {
			newIncludePatterns[i] = includePatterns[i];
		}
		newIncludePatterns[includePatterns.length] = "urn:epc:pat:abc-123:1.2.3.4";
		spec.getReportSpecs()[0].getFilterSpec().setIncludePatterns(newIncludePatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Unknown Tag Format 'abc-123'. Known formats are 'gid-96', 'sgtin-64' and 'sscc-64'.",
					e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid filter pattern in ECSpec.");
		
	}
	
	public void testDefineWithInvalidFilterPatternWithTooLessDataFields() throws Exception {
		
		String[] includePatterns = spec.getReportSpecs()[0].getFilterSpec().getIncludePatterns();
		String[] newIncludePatterns = new String[includePatterns.length + 1];
		for (int i = 0; i < includePatterns.length; i++) {
			newIncludePatterns[i] = includePatterns[i];
		}
		newIncludePatterns[includePatterns.length] = "urn:epc:pat:gid-96:1.2";
		spec.getReportSpecs()[0].getFilterSpec().setIncludePatterns(newIncludePatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Too less data fields '1.2' in pattern 'urn:epc:pat:gid-96:1.2'. " +
					"Pattern Format 'GID_96' expects 3 data fields.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid filter pattern in ECSpec.");
		
	}

	public void testDefineWithInvalidFilterPatternWithTooManyDataFields() throws Exception {
		
		String[] includePatterns = spec.getReportSpecs()[0].getFilterSpec().getIncludePatterns();
		String[] newIncludePatterns = new String[includePatterns.length + 1];
		for (int i = 0; i < includePatterns.length; i++) {
			newIncludePatterns[i] = includePatterns[i];
		}
		newIncludePatterns[includePatterns.length] = "urn:epc:pat:gid-96:1.2.3.4";
		spec.getReportSpecs()[0].getFilterSpec().setIncludePatterns(newIncludePatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Too many data fields '1.2.3.4' in pattern 'urn:epc:pat:gid-96:1.2.3.4'. " +
					"Pattern Format 'GID_96' expects 3 data fields.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid filter pattern in ECSpec.");
		
	}
	
	public void testDefineWithInvalidFilterPatternWithInvalidRange() throws Exception {
		
		String[] includePatterns = spec.getReportSpecs()[0].getFilterSpec().getIncludePatterns();
		String[] newIncludePatterns = new String[includePatterns.length + 1];
		for (int i = 0; i < includePatterns.length; i++) {
			newIncludePatterns[i] = includePatterns[i];
		}
		newIncludePatterns[includePatterns.length] = "urn:epc:pat:gid-96:1.2.[100-0]";
		spec.getReportSpecs()[0].getFilterSpec().setIncludePatterns(newIncludePatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid range '[100-0]'. Range must have the form '[lo-hi]' with lo <= hi.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid filter pattern in ECSpec.");
		
	}
	
	public void testDefineWithInvalidFilterPatternWithX() throws Exception {
		
		String[] includePatterns = spec.getReportSpecs()[0].getFilterSpec().getIncludePatterns();
		String[] newIncludePatterns = new String[includePatterns.length + 1];
		for (int i = 0; i < includePatterns.length; i++) {
			newIncludePatterns[i] = includePatterns[i];
		}
		newIncludePatterns[includePatterns.length] = "urn:epc:pat:gid-96:1.2.X";
		spec.getReportSpecs()[0].getFilterSpec().setIncludePatterns(newIncludePatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid data field 'X'. Only '*', '[lo-hi]' or 'int' are allowed.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid filter pattern in ECSpec.");
		
	}
	
	public void testDefineWithInvalidGroupPattern() throws Exception {
		
		String[] groupPatterns = spec.getReportSpecs()[0].getGroupSpec();
		String[] newGroupPatterns = new String[groupPatterns.length + 1];
		for (int i = 0; i < groupPatterns.length; i++) {
			newGroupPatterns[i] = groupPatterns[i];
		}
		newGroupPatterns[groupPatterns.length] = "InvalidPattern";
		spec.getReportSpecs()[0].setGroupSpec(newGroupPatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid Pattern 'InvalidPattern'. Pattern must have the form 'urn:epc:(urn | tag | pat | id | idpat | raw):tag-type:data-fields'.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is an invalid group pattern in ECSpec.");
		
	}
	
	public void testDefineWithNonDisjointGroupPatternsWithAsteriksAndX() throws Exception {
		
		String[] groupPatterns = spec.getReportSpecs()[0].getGroupSpec();
		String[] newGroupPatterns = new String[groupPatterns.length + 2];
		for (int i = 0; i < groupPatterns.length; i++) {
			newGroupPatterns[i] = groupPatterns[i];
		}
		newGroupPatterns[groupPatterns.length] = "urn:epc:pat:gid-96:1.X.*";
		newGroupPatterns[groupPatterns.length + 1] = "urn:epc:pat:gid-96:1.X.X";
		spec.getReportSpecs()[0].setGroupSpec(newGroupPatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The two grouping patterns 'urn:epc:pat:gid-96:1.X.*' and " +
					"'urn:epc:pat:gid-96:1.X.X' are not disjoint.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there are non disjoint group patterns in ECSpec.");
		
	}
	
	public void testDefineWithNonDisjointGroupPatternsWithValueInRange1() throws Exception {
		
		String[] groupPatterns = spec.getReportSpecs()[0].getGroupSpec();
		String[] newGroupPatterns = new String[groupPatterns.length + 2];
		for (int i = 0; i < groupPatterns.length; i++) {
			newGroupPatterns[i] = groupPatterns[i];			
		}
		newGroupPatterns[groupPatterns.length] = "urn:epc:pat:gid-96:1.100.X";
		newGroupPatterns[groupPatterns.length + 1] = "urn:epc:pat:gid-96:1.[0-200].X";
		spec.getReportSpecs()[0].setGroupSpec(newGroupPatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The two grouping patterns 'urn:epc:pat:gid-96:1.100.X' and " +
					"'urn:epc:pat:gid-96:1.[0-200].X' are not disjoint.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there are non disjoint group patterns in ECSpec.");
		
	}
	
	public void testDefineWithNonDisjointGroupPatternsWithValueInRange2() throws Exception {
		
		String[] groupPatterns = spec.getReportSpecs()[0].getGroupSpec();
		String[] newGroupPatterns = new String[groupPatterns.length + 2];
		for (int i = 0; i < groupPatterns.length; i++) {
			newGroupPatterns[i] = groupPatterns[i];
		}
		newGroupPatterns[groupPatterns.length] = "urn:epc:pat:gid-96:1.[0-200].X";
		newGroupPatterns[groupPatterns.length + 1] = "urn:epc:pat:gid-96:1.100.X";
		spec.getReportSpecs()[0].setGroupSpec(newGroupPatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The two grouping patterns 'urn:epc:pat:gid-96:1.[0-200].X' and " +
					"'urn:epc:pat:gid-96:1.100.X' are not disjoint.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there are non disjoint group patterns in ECSpec.");
		
	}
	
	public void testDefineWithNonDisjointGroupPatternsWithOverlappingRanges() throws Exception {
		
		String[] groupPatterns = spec.getReportSpecs()[0].getGroupSpec();
		String[] newGroupPatterns = new String[groupPatterns.length + 2];
		for (int i = 0; i < groupPatterns.length; i++) {
			newGroupPatterns[i] = groupPatterns[i];
		}
		newGroupPatterns[groupPatterns.length] = "urn:epc:pat:gid-96:1.[0-100].X";
		newGroupPatterns[groupPatterns.length + 1] = "urn:epc:pat:gid-96:1.[100-200].X";
		spec.getReportSpecs()[0].setGroupSpec(newGroupPatterns);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The two grouping patterns 'urn:epc:pat:gid-96:1.[0-100].X' and " +
					"'urn:epc:pat:gid-96:1.[100-200].X' are not disjoint.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there are non disjoint group patterns in ECSpec.");
		
	}

	public void testDefineWithoutAnyOutputTypeSpecified() throws Exception {
		
		ECReportOutputSpec output = spec.getReportSpecs()[0].getOutput();
		output.setIncludeCount(false);
		output.setIncludeEPC(false);
		output.setIncludeRawDecimal(false);
		output.setIncludeRawHex(false);
		output.setIncludeTag(false);
		
		try {
			ALE.define(SPEC_NAME, spec);
		} catch (ECSpecValidationException e) {
			assertEquals("The ECReportOutputSpec of ReportSpec 'TestReport' has no output type specified.", e.getReason());
			return;
		}
		
		fail("Should throw ECSpecValidationException. Because there is no output type specified.");
		
	}
	
	public void testUndefine() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		ALE.undefine(SPEC_NAME);
		
	}
	
	public void testUndefineWithUnknownSpecName() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		try {
			ALE.undefine("UnkownSpecName");
		} catch (NoSuchNameException e) {
			return;
		}
		
		fail("Should throw new NoSuchNameException. Because there is no spec defined with name 'UnknownSpecName'.");
		
	}
	
	public void testGetECSpec() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		ECSpec newSpec = ALE.getECSpec(SPEC_NAME);
		
		assertEquals(spec, newSpec);
		
	}
	
	public void testGetECSpecWithUnknownSpecName() throws Exception {

		ALE.define(SPEC_NAME, spec);
		
		try {
			ALE.getECSpec("UnknownSpecName");
		} catch (NoSuchNameException e) {
			return;
		}
		
		fail("Should throw new NoSuchNameException. Because there is no spec defined with name 'UnknownSpecName'.");
		
	}
	
	public void testGetECSpecNames() throws Exception {
	
		ALE.define(SPEC_NAME, spec);
		
		String[] names = ALE.getECSpecNames();
		
		assertEquals(1, names.length);
		assertEquals(SPEC_NAME, names[0]);
		
	}
	
	public void testSubscribeWithHttpNotificationURI() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
	}
	
	public void testSubscribeWithTcpNotificationURI() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		ALE.subscribe(SPEC_NAME, TCP_NOTIFICATION_URI);
		
	}
	
	public void testSubscribeWithFileNotificationURI() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		ALE.subscribe(SPEC_NAME, FILE_NOTIFICATION_URI);
		
	}
	
	public void testSubscribeWithUnknownSpecName() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		try {
			ALE.subscribe("UnknownSpecName", HTTP_NOTIFICATION_URI);
		} catch (NoSuchNameException e) {
			return;
		}
		
		fail("Should throw new NoSuchNameException. Because there is no spec defined with name 'UnknownSpecName'.");
		
	}
	
	public void testSubscribeWithInvalidURI() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		
		try {
			ALE.subscribe(SPEC_NAME, "InvalidNotificationURI");
		} catch (InvalidURIException e) {
			assertEquals("A valid URI must have one of the following forms: (http://host[:port]/remainder-of-URL | tcp://host:port | file://[host]/path)", e.getReason());
			return;
		}
		
		fail("Should throw new InvalidURIException. Because 'InvalidNotificationURI' is not a valid URI.");
		
	}
	
	public void testSubscribeWithDuplicateSubscription() throws Exception {
	
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		try {
			ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		} catch (DuplicateSubscriptionException e) {
			return;
		}
		
		fail("Should throw new DuplicateSubscriptionException. Because the same notification uri is already subscribed " +
				"at this spec.");
		
	}
	
	public void testUnsubscribe() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		ALE.unsubscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
			
	}
	
	public void testUnsubscribeWithUnknownSpecName() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		try {
			ALE.unsubscribe("UnknownSpecName", HTTP_NOTIFICATION_URI);
		} catch (NoSuchNameException e) {
			return;
		}
		
		fail("Should throw new NoSuchNameException. Because there is no spec defined with name 'UnknownSpecName'.");
		
	}
	
	public void testUnsubscribeWithUnknownSubscriber() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		try {
			ALE.unsubscribe(SPEC_NAME, TCP_NOTIFICATION_URI);
		} catch (NoSuchSubscriberException e) {
			return;
		}
		
		fail("Should throw new NoSuchSubscriberException. Because there this subscriber is not subscribed.");
		
	}
	
	public void testUnsubscribeWithInvalidURI() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		try {
			ALE.unsubscribe(SPEC_NAME, "InvalidNotificationURI");
		} catch (InvalidURIException e) {
			assertEquals("A valid URI must have one of the following forms: (http://host[:port]/remainder-of-URL | tcp://host:port | file://[host]/path)", e.getReason());
			return;
		}
		
		fail("Should throw new InvalidURIException. Because 'InvalidNotificationURI' is not a valid URI.");
		
	}
	
	public void testPoll() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ECReports reports = ALE.poll(SPEC_NAME);
		
		assertNotNull(reports);
		
	}
	
	public void testImmediate() throws Exception {
		
		ECReports reports = ALE.immediate(spec);
		
		assertNotNull(reports);
		
	}
	
	public void testGetSubscribers() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		String[] subscribers = ALE.getSubscribers(SPEC_NAME);
		
		assertNotNull(subscribers);
		assertEquals(1, subscribers.length);
		assertEquals(HTTP_NOTIFICATION_URI, subscribers[0]);
		
		ALE.subscribe(SPEC_NAME, TCP_NOTIFICATION_URI);
		ALE.subscribe(SPEC_NAME, FILE_NOTIFICATION_URI);
		
		subscribers = ALE.getSubscribers(SPEC_NAME);
		
		assertEquals(3, subscribers.length);
		
		boolean contains = false;
		for (String subscriber : subscribers) {
			if (HTTP_NOTIFICATION_URI.equals(subscriber)) {
				contains = true;
			}
		}
		if (!contains) {
			fail();
		}
		
		contains = false;
		for (String subscriber : subscribers) {
			if (TCP_NOTIFICATION_URI.equals(subscriber)) {
				contains = true;
			}
		}
		if (!contains) {
			fail();
		}
		
		contains = false;
		for (String subscriber : subscribers) {
			if (FILE_NOTIFICATION_URI.equals(subscriber)) {
				contains = true;
			}
		}
		if (!contains) {
			fail();
		}
		
	}
	
	public void testGetSubscribersWithUnknownSpecName() throws Exception {
		
		ALE.define(SPEC_NAME, spec);
		ALE.subscribe(SPEC_NAME, HTTP_NOTIFICATION_URI);
		
		try {
			ALE.getSubscribers("UnknownSpecName");
		} catch (NoSuchNameException e) {
			return;
		}
		
		fail("Should throw new NoSuchNameException. Because there is no spec defined with name 'UnknownSpecName'.");
		
	}
	
	public void testGetStandadVersion() throws Exception {
		
		assertEquals(STANDARD_VERSION, ALE.getStandardVersion());
		
	}
	
	public void testGetVendorVersion() throws Exception {
	
		assertEquals(VENDOR_VERSION, ALE.getVendorVersion());
		
	}
	
}