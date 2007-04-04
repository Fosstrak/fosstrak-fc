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