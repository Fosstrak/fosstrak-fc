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

import java.net.URL;

import org.accada.ale.server.PatternType;
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