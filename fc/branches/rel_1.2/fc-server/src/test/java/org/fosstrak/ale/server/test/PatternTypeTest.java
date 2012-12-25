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

package org.fosstrak.ale.server.test;

import java.net.URL;

import junit.framework.Assert;

import org.apache.log4j.PropertyConfigurator;
import org.fosstrak.ale.server.PatternType;
import org.junit.Before;
import org.junit.Test;

/**
 * @author regli
 */
public class PatternTypeTest {

	private static final String GID_96 = "gid-96";
	private static final String SGTIN_64 = "sgtin-64";
	private static final String SSCC_64 = "sscc-64";
	
	private static final int GID_96_DATAFIELDS = 3;
	private static final int SGTIN_64_DATAFIELDS = 4;
	private static final int SSCC_64_DATAFIELDS = 3;
	
	@Before
	public void setUp() throws Exception {		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);	
	}
	
	@Test
	public void testGetType() throws Exception {
		Assert.assertEquals(PatternType.GID_96, PatternType.getType(GID_96));
		Assert.assertEquals(PatternType.SGTIN_64, PatternType.getType(SGTIN_64));
		Assert.assertEquals(PatternType.SSCC_64, PatternType.getType(SSCC_64));
	}

	@Test
	public void testGetNumberOfDataFields() throws Exception {
		Assert.assertEquals(GID_96_DATAFIELDS, PatternType.GID_96.getNumberOfDatafields());
		Assert.assertEquals(SGTIN_64_DATAFIELDS, PatternType.SGTIN_64.getNumberOfDatafields());
		Assert.assertEquals(SSCC_64_DATAFIELDS, PatternType.SSCC_64.getNumberOfDatafields());
	}

	@Test
	public void testToString() throws Exception {
		Assert.assertEquals(GID_96, PatternType.GID_96.toSring());
		Assert.assertEquals(SGTIN_64, PatternType.SGTIN_64.toSring());
		Assert.assertEquals(SSCC_64, PatternType.SSCC_64.toSring());
	}
	
}