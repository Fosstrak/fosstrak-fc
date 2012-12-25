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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * backwards compatibility to provide ECReportSetEnum values.
 * @author swieland
 *
 */
public enum ECReportSetEnum {
	
	/** all the current tags. */
	CURRENT,
	
	/** all the new tags. */
	ADDITIONS,
	
	/** all the tags that left the previous cycle. */
	DELETIONS;
	
	/** logger. */
	private static final Logger LOG = Logger.getLogger(ECReportSetEnum.class);
	
	/**
	 * compare a given input string to the report set enumerations values. 
	 * the method is save for illegal input arguments (eg. null or not existing report set type).
	 * further the method is case insensitive
	 * @param setEnum the enumeration value to compare to.
	 * @param name the value to check from the enumeration.
	 * @return true if same enumeration value, false otherwise.
	 */
	public static boolean isSameECReportSet(ECReportSetEnum setEnum, String name) {
		try {
			return setEnum.equals(ECReportSetEnum.valueOf(StringUtils.upperCase(name)));
		} catch (Exception ex) {
			LOG.error("you provided an illegal report set enum value: " + name, ex);
		}
		return false;
	}
}
