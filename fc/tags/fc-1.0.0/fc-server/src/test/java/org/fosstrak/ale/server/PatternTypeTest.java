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

import java.net.URL;

import org.fosstrak.ale.server.PatternType;
import org.apache.log4j.PropertyConfigurator;



import junit.framework.TestCase;

/**
 * @author regli
 */
public class PatternTypeTest extends TestCase {

	private static final String GID_96 = "gid-96";
	private static final String SGTIN_64 = "sgtin-64";
	private static final String SSCC_64 = "sscc-64";
	
	private static final int GID_96_DATAFIELDS = 3;
	private static final int SGTIN_64_DATAFIELDS = 4;
	private static final int SSCC_64_DATAFIELDS = 3;
	
	protected void setUp() throws Exception {

		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testGetType() throws Exception {

		assertEquals(PatternType.GID_96, PatternType.getType(GID_96));
		assertEquals(PatternType.SGTIN_64, PatternType.getType(SGTIN_64));
		assertEquals(PatternType.SSCC_64, PatternType.getType(SSCC_64));
		
	}
	
	public void testGetNumberOfDataFields() throws Exception {
		
		assertEquals(GID_96_DATAFIELDS, PatternType.GID_96.getNumberOfDatafields());
		assertEquals(SGTIN_64_DATAFIELDS, PatternType.SGTIN_64.getNumberOfDatafields());
		assertEquals(SSCC_64_DATAFIELDS, PatternType.SSCC_64.getNumberOfDatafields());
		
	}
	
	public void testToString() throws Exception {
		
		assertEquals(GID_96, PatternType.GID_96.toSring());
		assertEquals(SGTIN_64, PatternType.SGTIN_64.toSring());
		assertEquals(SSCC_64, PatternType.SSCC_64.toSring());
		
	}
	
}