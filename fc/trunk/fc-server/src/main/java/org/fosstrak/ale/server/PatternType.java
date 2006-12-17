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

package org.accada.ale.server;

import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;

/**
 * This enumeration defines the possible pattern types.
 * 
 * @author regli
 */
public enum PatternType {

	GID_96,
	SGTIN_64,
	SSCC_64;
	
	private static final String GID_96_STRING = "gid-96";
	private static final String SGTIN_64_STRING = "sgtin-64";
	private static final String SSCC_64_STRING = "sscc-64";
	
	private static final int GID_96_DATAFIELDS = 3;
	private static final int SGTIN_64_DATAFIELDS = 4;
	private static final int SSCC_64_DATAFIELDS = 3;
	
	/**
	 * This method returns the pattern type of a types string represenation.
	 * 
	 * @param type string representation of the type
	 * @return pattern type
	 * @throws ECSpecValidationException if the string representation is invalid
	 */
	public static PatternType getType(String type) throws ECSpecValidationException {

		if (GID_96_STRING.equals(type)) {
			return GID_96;
		} else if (SGTIN_64_STRING.equals(type)) {
			return SGTIN_64;
		} else if (SSCC_64_STRING.equals(type)) {
			return SSCC_64;
		} else {
			throw new ECSpecValidationException("Unknown Tag Format '" + type + "'. Known formats are" +
					" '" + GID_96_STRING + "', '" + SGTIN_64_STRING + "' and '" + SSCC_64_STRING + "'.");
		}
		
	}
	
	/**
	 * This method returns the number of data fields the pattern type has.
	 * 
	 * @return number of data fields
	 */
	public int getNumberOfDatafields() {
		
		if (this == GID_96) {
			return GID_96_DATAFIELDS;
		} else if (this == SGTIN_64) {
			return SGTIN_64_DATAFIELDS;
		} else if (this == SSCC_64) {
			return SSCC_64_DATAFIELDS;
		} else {
			return -1;
		}
		
	}
	
	/**
	 * This method returns a string representation of the pattern type.
	 * 
	 * @return string representation of the pattern type
	 */
	public String toSring() {
		
		if (this == GID_96) {
			return GID_96_STRING;
		} else if (this == SGTIN_64) {
			return SGTIN_64_STRING;
		} else if (this == SSCC_64) {
			return SSCC_64_STRING;
		} else {
			return null;
		}
		
	}
	
}