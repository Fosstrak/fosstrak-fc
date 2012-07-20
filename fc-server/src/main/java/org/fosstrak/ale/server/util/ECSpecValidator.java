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
package org.fosstrak.ale.server.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.Pattern;
import org.fosstrak.ale.server.PatternUsage;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ECSpecValidationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECGroupSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.LogicalReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.ReportSpecs;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * helper utility validating an ECSpec.
 * @author sbw
 *
 */
@Component("ecSpecValidator")
public class ECSpecValidator {

	/**
	 * private handle onto the logical reader manager.
	 */
	private static LogicalReaderManager logicalReaderManager;

	/** logger */
	private static final Logger LOG = Logger.getLogger(ECSpecValidator.class);
	
	/**
	 * private constructor for utility classes.
	 */
	private ECSpecValidator() {
		
	}	
	
	/**
	 * little trick to inject the autowired bean logical reader manager into the static utility class.
	 * @param lrm
	 */
	@Autowired
	public void setLogicalReaderManager(LogicalReaderManager lrm) {
		logicalReaderManager = lrm;
	}
	
	/**
	 * This method validates an ec specification under criterias of chapter 
	 * 8.2.11 of ALE specification version 1.0.
	 * @param spec to validate
	 * @throws ECSpecValidationException if the specification is invalid
	 * @throws ImplementationException if an implementation exception occurs
	 */
	public static void validateSpec(ECSpec spec) throws ECSpecValidationExceptionResponse, ImplementationExceptionResponse {
				
		// check if the logical readers are known to the implementation
		checkReadersAvailable(spec.getLogicalReaders(), logicalReaderManager);
		
		checkBoundarySpec(spec.getBoundarySpec());
		checkReportSpecs(spec.getReportSpecs());
	}

	/**
	 * verifies that the spec contains some readers and that those readers are available in the ALE.
	 * @param logicalReaders the logical reader definition from the ECSpec.
	 * @param readerManager handle to the logical reader manager.
	 * @return true if OK, throws exception otherwise.
	 * @throws ECSpecValidationExceptionResponse if no reader specified or the specified readers do not exist in the ALE.
	 */
	public static boolean checkReadersAvailable(LogicalReaders logicalReaders, LogicalReaderManager readerManager) throws ECSpecValidationExceptionResponse {
		if ((null == logicalReaders) || (logicalReaders.getLogicalReader().size() == 0)) {
			throw logAndCreateECSpecValidationException("ECSpec does not specify at least one reader"); 
		}
		for (String logicalReaderName : logicalReaders.getLogicalReader()) {
			if (!readerManager.contains(logicalReaderName)) {
				throw logAndCreateECSpecValidationException("LogicalReader '" + logicalReaderName + "' is unknown.");
			}
		}
		return true;
	}

	/**
	 * verifies the boundary spec of an ECSpec.
	 * @param boundarySpec the boundary spec to verify.
	 * @throws ECSpecValidationExceptionResponse if the specification does not meet the specification.
	 * @return true if OK, throws exception otherwise.
	 */
	public static boolean checkBoundarySpec(ECBoundarySpec boundarySpec) throws ECSpecValidationExceptionResponse {

		// boundaries parameter of ECSpec is null or omitted
		if (boundarySpec == null) {
			throw logAndCreateECSpecValidationException("The boundaries parameter of ECSpec is null.");
		}
		
		// start and stop tiggers
		checkTrigger(boundarySpec.getStartTrigger());
		checkTrigger(boundarySpec.getStopTrigger());
		
		// check if duration, stableSetInterval or repeatPeriod is negative
		checkTimeNotNegative(boundarySpec.getDuration(), "The duration field of ECBoundarySpec is negative.");
		checkTimeNotNegative(boundarySpec.getStableSetInterval(), "The stableSetInterval field of ECBoundarySpec is negative.");
		checkTimeNotNegative(boundarySpec.getRepeatPeriod(), "The repeatPeriod field of ECBoundarySpec is negative.");
		
		// check if start trigger is non-empty and repeatPeriod is non-zero
		checkStartTriggerConstraintsOnRepeatPeriod(boundarySpec);
		
		// check if a stopping condition is specified
		checkBoundarySpecStoppingCondition(boundarySpec);
		
		return true;
	}
	
	/**
	 * check that the provided time value is not negative.
	 * @param duration the time value to check.
	 * @param string a message string to show in the exception.
	 * @return true if OK, throws Exception otherwise.
	 * @throws ECSpecValidationExceptionResponse if the time value is negative.
	 */
	public static boolean checkTimeNotNegative(ECTime duration, String string) throws ECSpecValidationExceptionResponse {		
		if (duration != null) {
			if (duration.getValue() < 0) {
				throw logAndCreateECSpecValidationException("The duration field of ECBoundarySpec is negative.");
			}
		}
		return true;
	}

	/**
	 * if a start trigger is specified, then the repeat period must be 0.
	 * @param boundarySpec the boundary spec to test.
	 * @return true if OK, throws exception otherwise.
	 * @throws ECSpecValidationExceptionResponse if a start trigger is specified, then the repeat period must be 0. if not, throw an exception.
	 */
	public static boolean checkStartTriggerConstraintsOnRepeatPeriod(ECBoundarySpec boundarySpec) throws ECSpecValidationExceptionResponse {
		if ((boundarySpec.getStartTrigger() != null) && (boundarySpec.getRepeatPeriod().getValue() != 0)) {
			throw logAndCreateECSpecValidationException("The startTrigger field of ECBoundarySpec is non-empty and the repeatPeriod field of ECBoundarySpec is non-zero.");
		}
		return true;
	}

	/**
	 * check the stopping condition of the EC boundary spec:<br/>
	 * if there is no stop trigger or no duration value or no stableSetInterval, throw an exception.
	 * @param boundarySpec the boundary spec to test.
	 * @return true if OK, throws exception otherwise.
	 * @throws ECSpecValidationExceptionResponse if there is no stop trigger or no duration value or no stableSetInterval, throw an exception.
	 */
	public static boolean checkBoundarySpecStoppingCondition(ECBoundarySpec boundarySpec) throws ECSpecValidationExceptionResponse {
		if ((boundarySpec.getStopTrigger() == null) && (boundarySpec.getDuration() == null) && (boundarySpec.getStableSetInterval() == null)) {
			throw logAndCreateECSpecValidationException("No stopping condition is specified in ECBoundarySpec.");
		}
		return true;		
	}
	
	/**
	 * checks the report specs.
	 * @param reportSpecs the report specs to verify.
	 * @throws ECSpecValidationExceptionResponse when the specifications do not meet the requirements.
	 * @return true if the specification is OK, throws exception otherwise.
	 */
	public static boolean checkReportSpecs(ReportSpecs reportSpecs) throws ECSpecValidationExceptionResponse {
		// check if there is a ECReportSpec instance
		if ((reportSpecs == null) || (reportSpecs.getReportSpec().size() == 0)) {
			throw logAndCreateECSpecValidationException("List of ECReportSpec is empty or null.");
		}
		final List<ECReportSpec> reportSpecList = reportSpecs.getReportSpec();
		
		// check that no two ECReportSpec instances have identical names
		checkReportSpecNoDuplicateReportSpecNames(reportSpecList);
		
		// check filters
		for (ECReportSpec reportSpec : reportSpecList) {
			checkFilterSpec(reportSpec.getFilterSpec());
		}
		
		// check grouping patterns
		for (ECReportSpec reportSpec : reportSpecList) {
			checkGroupSpec(reportSpec.getGroupSpec());
		}
		
		// check if there is a output type specified for each ECReportSpec
		for (ECReportSpec reportSpec : reportSpecList) {
			checkReportOutputSpec(reportSpec.getReportName(), reportSpec.getOutput());
		}
		return true;
	}
	
	/**
	 * verify that no two report specs have the same name.
	 * @param reportSpecList the list of report specs to check.
	 * @return a list containing all the names of the different report specs.
	 * @throws ECSpecValidationExceptionResponse when there are two report specs with the same name.
	 */
	public static Set<String> checkReportSpecNoDuplicateReportSpecNames(List<ECReportSpec> reportSpecList) throws ECSpecValidationExceptionResponse {
		Set<String> reportSpecNames = new HashSet<String>();
		for (ECReportSpec reportSpec : reportSpecList) {
			if (reportSpecNames.contains(reportSpec.getReportName())) {
				throw logAndCreateECSpecValidationException("Two ReportSpecs instances have identical names '" + reportSpec.getReportName() + "'.");
			} else {
				reportSpecNames.add(reportSpec.getReportName());
			}
		}
		return reportSpecNames;
	}

	/**
	 * verify the report output specification.
	 * @param outputSpec the output specification.
	 * @return true if the specification is OK, otherwise throws exception.
	 * @throws ECSpecValidationExceptionResponse violates the specification.
	 */
	public static boolean checkReportOutputSpec(String reportName, ECReportOutputSpec outputSpec) throws ECSpecValidationExceptionResponse {
		if (null == outputSpec) {
			throw logAndCreateECSpecValidationException("there is no output spec for report spec: " + reportName);
		}
		if (!outputSpec.isIncludeEPC() && !outputSpec.isIncludeTag() && !outputSpec.isIncludeRawHex() && !outputSpec.isIncludeRawDecimal() && !outputSpec.isIncludeCount()) {
			throw logAndCreateECSpecValidationException("The ECReportOutputSpec of ReportSpec '" + reportName + "' has no output type specified.");
		}
		return true;
	}

	/**
	 * check the group spec patterns do not have intersecting groups -> all group patterns have to be disjoint:<br/>
	 * <ul>
	 * 	<li>the same pattern is not allowed to occur twice</li>
	 * 	<li>two different pattern with intersecting selectors are not allowed</li>
	 * 	<li>no pattern at all is allowed</li>
	 * </ul>
	 * @param groupSpec the group spec to tested.
	 * @throws ECSpecValidationExceptionResponse upon violation.
	 * @return true if filter group spec is valid. exception otherwise.
	 */
	public static boolean checkGroupSpec(ECGroupSpec groupSpec) throws ECSpecValidationExceptionResponse {
		if (groupSpec != null) {
			String[] patterns = groupSpec.getPattern().toArray(new String[] {});
			for (int i=0; i<patterns.length-1; i++) {
				final String pattern1 = patterns[i];
				for (int j=i+1; j<patterns.length; j++) {
					final String pattern2 = patterns[j];
					if (!patternDisjoint(pattern1, pattern2)) {
						throw logAndCreateECSpecValidationException("The two grouping patterns '" + pattern1 + "' and '" + pattern2 + "' are not disjoint.");
					}
				}
			}
		}
		return true;
	}

	/**
	 * check the filter spec. if the filter spec is null, it is ignored.
	 * @param filterSpec the filter spec to verify.
	 * @throws ECSpecValidationExceptionResponse upon violation of the filter pattern.
	 * @return true if filter spec is valid. exception otherwise.
	 */
	public static boolean checkFilterSpec(ECFilterSpec filterSpec) throws ECSpecValidationExceptionResponse {			
		if (filterSpec != null) {
			// check include patterns
			if (filterSpec.getIncludePatterns() != null) {
				for (String pattern : filterSpec.getIncludePatterns().getIncludePattern()) {						
					new Pattern(pattern, PatternUsage.FILTER);
				}
			}
			
			// check exclude patterns
			if (filterSpec.getExcludePatterns() != null) {
				for (String pattern : filterSpec.getExcludePatterns().getExcludePattern()) {						
					new Pattern(pattern, PatternUsage.FILTER);
				}
			}
		}
		return true;
	}

	/**
	 * test if two pattern are disjoint.
	 * @param pattern1 the first and reference pattern.
	 * @param pattern2 the second pattern.
	 * @return true if pattern not disjoint, false otherwise.
	 */
	public static boolean patternDisjoint(String pattern1, String pattern2) throws ECSpecValidationExceptionResponse {
		Pattern pattern = new Pattern(pattern1, PatternUsage.GROUP);
		return pattern.isDisjoint(pattern2);
	}

	/**
	 * log the given string and then create from the string an ECSpecValidationException.
	 * @param string the log and exception string.
	 * @return the ECSpecValidationException created from the input string.
	 */
	private static ECSpecValidationExceptionResponse logAndCreateECSpecValidationException(String string) {
		LOG.debug(string);
		return new ECSpecValidationExceptionResponse(string);
	}

	/**
	 * This method checks if the trigger is valid or not.
	 * 
	 * @param trigger to check
	 * @throws ECSpecValidationException if the trigger is invalid.
	 */
	private static void checkTrigger(String trigger) throws ECSpecValidationExceptionResponse {		
		// TODO: implement checkTrigger
		LOG.debug("CHECK TRIGGER not implemented");
	}
}
