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

import org.accada.ale.server.Pattern;
import org.accada.ale.server.PatternUsage;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.apache.log4j.PropertyConfigurator;


import junit.framework.TestCase;

/**
 * @author regli
 */
public class PatternTest extends TestCase {

	private static final String TAG_PATTERN = "urn:epc:pat:sgtin-64:1.2.3.4";
	private static final String FILTER_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].*.*";
	private static final String GROUP_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].X.*";
	private static final String GROUP_MEMBER_PATTERN = "urn:epc:pat:sgtin-64:1.2.3.4";
	private static final String GROUP_NAME_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].3.*";
	private static final String GROUP_NOT_MEMBER_PATTERN = "urn:epc:pat:sgtin-64:1.0.3.4";
	private static final String DISJOINT_GROUP_PATTERN = "urn:epc:pat:sgtin-64:1.[3-4].X.*";
	private static final String INVALID_PATTERN = "urn:epc:pat:sgtin-64:a.b.c.d";

	protected void setUp() throws Exception {
		
		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testCreateTagPattern() throws Exception {
		
		new Pattern(TAG_PATTERN, PatternUsage.TAG);
		
	}

	public void testCreateTagPatternWithInvalidStringRepresentation() throws Exception {
		
		try {
			new Pattern(FILTER_PATTERN, PatternUsage.TAG);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid data field '[1-2]'. Only 'int' is allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the tag pattern contains a '*'.");
		
	}
	
	public void testCreateFilterPattern() throws Exception {
		
		new Pattern(TAG_PATTERN, PatternUsage.FILTER);
		new Pattern(FILTER_PATTERN, PatternUsage.FILTER);
		
	}
	
	public void testCreateFilterPatternWithInvalidStringRepresentation() throws Exception {
		
		try {
			new Pattern(GROUP_PATTERN, PatternUsage.FILTER);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid data field 'X'. Only '*', '[lo-hi]' or 'int' are allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the filter pattern contains a 'X'.");
		
	}
	
	public void testCreateGroupPattern() throws Exception {
	
		new Pattern(TAG_PATTERN, PatternUsage.GROUP);
		new Pattern(FILTER_PATTERN, PatternUsage.GROUP);
		new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		
	}
	
	public void testCreateGroupPatternWithInvalidStringRepresentation() throws Exception {
		
		try {
			new Pattern(INVALID_PATTERN, PatternUsage.GROUP);
		} catch (ECSpecValidationException e) {
			assertEquals("Invalid data field 'a'. Only '*', 'X', '[lo-hi]' or 'int' are allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the group pattern is invalid.");
		
	}
	
	public void testIsDisjointString() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		
		assertTrue(groupPattern.isDisjoint(DISJOINT_GROUP_PATTERN));
		assertFalse(groupPattern.isDisjoint(GROUP_PATTERN));
		
	}

	public void testIsDisjointPattern() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		Pattern disjointGroupPattern = new Pattern(DISJOINT_GROUP_PATTERN, PatternUsage.GROUP);
		
		assertTrue(groupPattern.isDisjoint(disjointGroupPattern));
		assertFalse(groupPattern.isDisjoint(groupPattern));
		
	}

	public void testIsMember() throws Exception {

		Pattern filterPattern = new Pattern(FILTER_PATTERN, PatternUsage.FILTER);
		
		assertTrue(filterPattern.isMember(GROUP_MEMBER_PATTERN));
		assertFalse(filterPattern.isMember(GROUP_NOT_MEMBER_PATTERN));
		
	}

	public void testGetGroupName() throws Exception {
		
		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		
		assertEquals(GROUP_NAME_PATTERN, groupPattern.getGroupName(GROUP_MEMBER_PATTERN));
		assertNull(groupPattern.getGroupName(GROUP_NOT_MEMBER_PATTERN));

	}

	public void testToString() throws Exception {

		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		
		assertEquals(GROUP_PATTERN, groupPattern.toString());
		
	}

}
