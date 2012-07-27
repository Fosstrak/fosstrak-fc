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
package org.fosstrak.ale.server.util.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.server.util.ECSpecValidator;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECGroupSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.LogicalReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * test the ECSpecValidator utilities.
 * @author sbw
 *
 */
public class ECSpecValidatorTest {
	
	private ECSpecValidator validator;
	
	@Before
	public void beforeTests() {
		validator = new ECSpecValidator();
	}
	
	/**
	 * verify that null as input for the readers list is not allowed.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckReadersAvailableNullAsInput() throws ECSpecValidationException {
		validator.checkReadersAvailable(null, null);
	}
	
	/**
	 * verify that at least one reader has to be specified.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckReadersAvailableNoReaderSpecified() throws ECSpecValidationException {
		validator.checkReadersAvailable(new LogicalReaders(), null);		
	}
	
	/**
	 * no reader specified or null as input.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckReadersAvailableNoReaderInALE() throws ECSpecValidationException {
		LogicalReaderManager manager = EasyMock.createMock(LogicalReaderManager.class);
		EasyMock.expect(manager.contains("logicalReader1")).andReturn(true);
		EasyMock.expect(manager.contains("logicalReader2")).andReturn(false);
		EasyMock.replay(manager);
		LogicalReaders logicalReaders = new LogicalReaders();
		logicalReaders.getLogicalReader().add("logicalReader1");
		logicalReaders.getLogicalReader().add("logicalReader2");
		
		validator.checkReadersAvailable(logicalReaders, manager);		
	}
	
	/**
	 * readers must be all set and present in the ALE.
	 * @throws ECSpecValidationException test failure.
	 */
	@Test
	public void testCheckReadersAvailable() throws ECSpecValidationException {
		LogicalReaderManager manager = EasyMock.createMock(LogicalReaderManager.class);
		EasyMock.expect(manager.contains("logicalReader1")).andReturn(true);
		EasyMock.expect(manager.contains("logicalReader2")).andReturn(true);
		EasyMock.replay(manager);
		LogicalReaders logicalReaders = new LogicalReaders();
		logicalReaders.getLogicalReader().add("logicalReader1");
		logicalReaders.getLogicalReader().add("logicalReader2");
		
		Assert.assertTrue(validator.checkReadersAvailable(logicalReaders, manager));
		
		EasyMock.verify(manager);
	}
	
	/**
	 * verify the check on the boundary spec.
	 * @throws ECSpecValidationException test failure.
	 */
	@Test
	public void testCheckBoundarySpec() throws ECSpecValidationException {
		ECBoundarySpec ecBoundarySpec = new ECBoundarySpec();
		ECTime time = new ECTime();
		time.setValue(1000);
		ecBoundarySpec.setDuration(time);
		Assert.assertTrue(validator.checkBoundarySpec(ecBoundarySpec));
	}
	
	/**
	 * verify the check on the boundary spec.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckBoundarySpecNullNotAllowed() throws ECSpecValidationException {
		validator.checkBoundarySpec(null);
	}

	/**
	 * verify illegal time values (negative ones).
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckTimeNotNegative() throws ECSpecValidationException {
		// first if we input null, all must be OK.
		Assert.assertTrue(validator.checkTimeNotNegative(null, null));
		
		ECTime time = new ECTime();
		time.setValue(-1);
		validator.checkTimeNotNegative(time, "successful test failure");
	}
	
	/**
	 * verify illegal stopping condition in the boundary spec.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckStartTriggerConstraintsOnRepeatPeriodIllegalInput() throws ECSpecValidationException {
		ECBoundarySpec ecBoundarySpec = new ECBoundarySpec();
		ecBoundarySpec.setStartTrigger("startTrigger");
		ECTime ecTime = new ECTime();
		ecTime.setValue(1000);
		ecBoundarySpec.setRepeatPeriod(ecTime);
		validator.checkStartTriggerConstraintsOnRepeatPeriod(ecBoundarySpec);	
	}
	
	/**
	 * verify illegal stopping condition in the boundary spec.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test
	public void testCheckStartTriggerConstraintsOnRepeatPeriod() throws ECSpecValidationException {
		ECBoundarySpec ecBoundarySpec = new ECBoundarySpec();
		ecBoundarySpec.setStartTrigger("startTrigger");
		ecBoundarySpec.setRepeatPeriod(new ECTime());
		validator.checkStartTriggerConstraintsOnRepeatPeriod(ecBoundarySpec);		
	}
	
	/**
	 * verify illegal stopping condition in the boundary spec.
	 * @throws ECSpecValidationException expected test result.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckBoundarySpecStoppingConditionIllegalInput() throws ECSpecValidationException {
		validator.checkBoundarySpecStoppingCondition(new ECBoundarySpec());		
	}
	
	/**
	 * test the report spec validation.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckReportSpecsNullIsIllegal() throws ECSpecValidationException {
		validator.checkReportSpecs(null);		
	}
	
	/**
	 * test the report spec validation.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckReportSpecsEmptyIllegal() throws ECSpecValidationException {
		validator.checkReportSpecs(new ECSpec.ReportSpecs());
	}
	
	/**
	 * test the report spec validation.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testCheckReportSpecs() throws ECSpecValidationException {
		ECSpec.ReportSpecs reportSpecs = new ECSpec.ReportSpecs();
		
		ECReportSpec spec1 = new ECReportSpec();
		spec1.setReportName("spec1");
		ECReportSpec spec2 = new ECReportSpec();
		spec2.setReportName("spec2");
				
		ECReportOutputSpec outputSpec1 = new ECReportOutputSpec();
		outputSpec1.setIncludeCount(true);
		spec1.setOutput(outputSpec1);

		ECReportOutputSpec outputSpec2 = new ECReportOutputSpec();
		outputSpec2.setIncludeEPC(true);
		spec2.setOutput(outputSpec2);
		
		reportSpecs.getReportSpec().add(spec1);
		reportSpecs.getReportSpec().add(spec2);
		Assert.assertTrue(validator.checkReportSpecs(reportSpecs));
	}
	
	/**
	 * test the report spec validation - specifically test that no two report specs can have the same name.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testCheckReportSpecNoDuplicateReportSpecNames() throws ECSpecValidationException {
		ECReportSpec spec1 = new ECReportSpec();
		spec1.setReportName("spec1");
		ECReportSpec spec2 = new ECReportSpec();
		spec2.setReportName("spec2");
		
		List<ECReportSpec> reportSpecList = new LinkedList<ECReportSpec> ();
		reportSpecList.add(spec1);
		reportSpecList.add(spec2);
		
		Set<String> names = validator.checkReportSpecNoDuplicateReportSpecNames(reportSpecList);
		Assert.assertNotNull(names);
		Assert.assertTrue(names.contains("spec1"));
		Assert.assertTrue(names.contains("spec2"));
	}
	
	/**
	 * test the report spec validation - specifically test that no two report specs can have the same name - give illegal input.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testCheckReportSpecNoDuplicateReportSpecNamesIllegalInput() throws ECSpecValidationException {
		ECReportSpec spec1 = new ECReportSpec();
		spec1.setReportName("spec1");
		ECReportSpec spec2 = new ECReportSpec();
		spec2.setReportName("spec1");
		
		List<ECReportSpec> reportSpecList = new LinkedList<ECReportSpec> ();
		reportSpecList.add(spec1);
		reportSpecList.add(spec2);
		
		validator.checkReportSpecNoDuplicateReportSpecNames(reportSpecList);
	}
	
	/**
	 * test the report output spec validation - must throw exception here as all report specs must have an output spec.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testReportOutputSpecNoNullSpecAllowed() throws ECSpecValidationException {
		validator.checkReportOutputSpec(null, null);
	}

	/**
	 * test the report output spec validation - must throw exception here as no output definition is given.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testReportOutputSpecNoOutputFormatGiven() throws ECSpecValidationException {
		final String reportName = "reportName";
		final ECReportOutputSpec outputSpec = new ECReportOutputSpec();
		validator.checkReportOutputSpec(reportName, outputSpec);
	}

	/**
	 * test the report output spec validation
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testReportOutputSpec() throws ECSpecValidationException {
		final String reportName = "reportName";
		ECReportOutputSpec outputSpec;
		
		outputSpec = new ECReportOutputSpec();
		outputSpec.setIncludeCount(true);
		Assert.assertTrue(validator.checkReportOutputSpec(reportName, outputSpec));
		
		outputSpec = new ECReportOutputSpec();
		outputSpec.setIncludeEPC(true);
		Assert.assertTrue(validator.checkReportOutputSpec(reportName, outputSpec));
		
		outputSpec = new ECReportOutputSpec();
		outputSpec.setIncludeRawDecimal(true);
		Assert.assertTrue(validator.checkReportOutputSpec(reportName, outputSpec));
		
		outputSpec = new ECReportOutputSpec();
		outputSpec.setIncludeRawHex(true);
		Assert.assertTrue(validator.checkReportOutputSpec(reportName, outputSpec));
		
		outputSpec = new ECReportOutputSpec();
		outputSpec.setIncludeTag(true);
		Assert.assertTrue(validator.checkReportOutputSpec(reportName, outputSpec));
	}
	
	/**
	 * the method must not stumble over null value as input.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testGroupSpecNull() throws ECSpecValidationException {
		Assert.assertTrue(validator.checkGroupSpec(null));
	}
	
	/**
	 * test the group spec validation.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testGroupSpec() throws ECSpecValidationException {
		ECGroupSpec groupSpec = new ECGroupSpec();
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[1-2].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[3-4].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[5-6].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[7-8].*.*");
		Assert.assertTrue(validator.checkGroupSpec(groupSpec));
	}
	
	/**
	 * test the group spec validation - must throw exception here
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testGroupSpecInvalidInput() throws ECSpecValidationException {
		ECGroupSpec groupSpec = new ECGroupSpec();
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[1-2].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[1-2].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[5-6].*.*");
		validator.checkGroupSpec(groupSpec);
	}
	
	/**
	 * test the group spec validation - must throw exception here
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testGroupSpecInvalidInput2() throws ECSpecValidationException {
		ECGroupSpec groupSpec = new ECGroupSpec();
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[1-2].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[3-4].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[5-6].*.*");
		groupSpec.getPattern().add("urn:epc:pat:sgtin-64:1.[5-6].*.*");
		validator.checkGroupSpec(groupSpec);
	}
	
	/**
	 * the method must not stumble over null value as input.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testFilterSpecNull() throws ECSpecValidationException {
		Assert.assertTrue(validator.checkFilterSpec(null));
	}
	
	/**
	 * test the filter spec validation.
	 * @throws ECSpecValidationException violation against the specification.
	 */
	@Test
	public void testFilterSpec() throws ECSpecValidationException {
		ECFilterSpec.IncludePatterns includePatterns = new ECFilterSpec.IncludePatterns();
		includePatterns.getIncludePattern().add("urn:epc:pat:sgtin-64:1.[1-2].*.*");
		ECFilterSpec.ExcludePatterns excludePatterns = new ECFilterSpec.ExcludePatterns();
		excludePatterns.getExcludePattern().add("urn:epc:pat:sgtin-64:1.[1-2].*.*");
		
		ECFilterSpec filterSpec = new ECFilterSpec();

		// test without pattern.
		Assert.assertTrue(validator.checkFilterSpec(filterSpec));
		
		filterSpec.setIncludePatterns(includePatterns);
		filterSpec.setExcludePatterns(excludePatterns);
		
		// test with patterns.
		Assert.assertTrue(validator.checkFilterSpec(filterSpec));
	}
	
	/**
	 * test that an exception is thrown when pattern are equal.
	 * @throws ECSpecValidationException test failure.
	 */
	@Test
	public void testPatternDisjoint() throws ECSpecValidationException {
		String p1 = "urn:epc:pat:sgtin-64:1.1.X.*";
		String p2 = "urn:epc:pat:sgtin-64:1.2.X.*";
		String p3 = "urn:epc:pat:sgtin-64:1.3.X.*";
		Assert.assertFalse(validator.patternDisjoint(p1, p1));
		Assert.assertTrue(validator.patternDisjoint(p1, p2));
		Assert.assertTrue(validator.patternDisjoint(p2, p3));
	}
	
	/**
	 * test that an exception is thrown when pattern are equal.
	 * @throws ECSpecValidationException test expectation.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testPatternDisjointIllegalInput() throws ECSpecValidationException {
		String p1 = "urn:epc:pat:sgtin-64:1.1.X.*";
		String p2 = "fdsfdsfdsfds";
		// must throw an exception
		validator.patternDisjoint(p1, p2);
	}
}
