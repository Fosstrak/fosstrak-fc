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
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.server.Pattern;
import org.fosstrak.ale.server.PatternUsage;
import org.junit.Before;
import org.junit.Test;

/**
 * @author regli
 */
public class PatternTest {

	private static final String TAG_PATTERN = "urn:epc:pat:sgtin-64:1.2.3.4";
	private static final String FILTER_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].*.*";
	private static final String GROUP_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].X.*";
	private static final String GROUP_MEMBER_PATTERN = "urn:epc:pat:sgtin-64:1.2.3.4";
	private static final String GROUP_NAME_PATTERN = "urn:epc:pat:sgtin-64:1.[1-2].3.*";
	private static final String GROUP_NOT_MEMBER_PATTERN = "urn:epc:pat:sgtin-64:1.0.3.4";
	private static final String DISJOINT_GROUP_PATTERN = "urn:epc:pat:sgtin-64:1.[3-4].X.*";
	private static final String INVALID_PATTERN = "urn:epc:pat:sgtin-64:a.b.c.d";

	@Before
	public void setUp() throws Exception {
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	@Test
	public void testCreateTagPattern() throws Exception {
		new Pattern(TAG_PATTERN, PatternUsage.TAG);
	}

	@Test
	public void testCreateTagPatternWithInvalidStringRepresentation() throws Exception {
		try {
			new Pattern(FILTER_PATTERN, PatternUsage.TAG);
		} catch (ECSpecValidationException e) {
			Assert.assertEquals("Invalid data field '[1-2]'. Only 'int' is allowed.", e.getMessage());
			return;
		}
		
		Assert.fail("Should throw an ECSpecValidationException because the string representation of the tag pattern contains a '*'.");
	}

	@Test
	public void testCreateFilterPattern() throws Exception {
		new Pattern(TAG_PATTERN, PatternUsage.FILTER);
		new Pattern(FILTER_PATTERN, PatternUsage.FILTER);
	}

	@Test
	public void testCreateFilterPatternWithInvalidStringRepresentation() throws Exception {
		try {
			new Pattern(GROUP_PATTERN, PatternUsage.FILTER);
		} catch (ECSpecValidationException e) {
			Assert.assertEquals("Invalid data field 'X'. Only '*', '[lo-hi]' or 'int' are allowed.", e.getMessage());
			return;
		}
		
		Assert.fail("Should throw an ECSpecValidationException because the string representation of the filter pattern contains a 'X'.");	
	}
	
	@Test
	public void testCreateGroupPattern() throws Exception {
		new Pattern(TAG_PATTERN, PatternUsage.GROUP);
		new Pattern(FILTER_PATTERN, PatternUsage.GROUP);
		new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
	}

	@Test
	public void testCreateGroupPatternWithInvalidStringRepresentation() throws Exception {
		try {
			new Pattern(INVALID_PATTERN, PatternUsage.GROUP);
		} catch (ECSpecValidationException e) {
			Assert.assertEquals("Invalid data field 'a'. Only '*', 'X', '[lo-hi]' or 'int' are allowed.", e.getMessage());
			return;
		}
		
		Assert.fail("Should throw an ECSpecValidationException because the string representation of the group pattern is invalid.");		
	}

	@Test
	public void testIsDisjointString() throws Exception {
		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		
		Assert.assertTrue(groupPattern.isDisjoint(DISJOINT_GROUP_PATTERN));
		Assert.assertFalse(groupPattern.isDisjoint(GROUP_PATTERN));
	}

	@Test
	public void testIsDisjointPattern() throws Exception {
		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		Pattern disjointGroupPattern = new Pattern(DISJOINT_GROUP_PATTERN, PatternUsage.GROUP);
		
		Assert.assertTrue(groupPattern.isDisjoint(disjointGroupPattern));
		Assert.assertFalse(groupPattern.isDisjoint(groupPattern));
	}

	@Test
	public void testIsMember() throws Exception {
		Pattern filterPattern = new Pattern(FILTER_PATTERN, PatternUsage.FILTER);
		
		Assert.assertTrue(filterPattern.isMember(GROUP_MEMBER_PATTERN));
		Assert.assertFalse(filterPattern.isMember(GROUP_NOT_MEMBER_PATTERN));
	}

	@Test
	public void testGetGroupName() throws Exception {
		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		Assert.assertEquals(GROUP_NAME_PATTERN, groupPattern.getGroupName(GROUP_MEMBER_PATTERN));
		Assert.assertNull(groupPattern.getGroupName(GROUP_NOT_MEMBER_PATTERN));
	}


	@Test
	public void testToString() throws Exception {
		Pattern groupPattern = new Pattern(GROUP_PATTERN, PatternUsage.GROUP);
		Assert.assertEquals(GROUP_PATTERN, groupPattern.toString());
	}

}
