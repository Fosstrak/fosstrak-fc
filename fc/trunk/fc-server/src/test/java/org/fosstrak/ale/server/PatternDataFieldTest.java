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

import org.accada.ale.server.PatternDataField;
import org.accada.ale.server.PatternUsage;
import org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException;
import org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
import org.apache.log4j.PropertyConfigurator;


import junit.framework.TestCase;

/**
 * @author regli
 */
public class PatternDataFieldTest extends TestCase {

	private static final String INT = "50";
	private static final int LOW = 0;
	private static final int HIGH = 100;
	private static final String RANGE = "[" + LOW + "-" + HIGH + "]";
	private static final String ASTERISK = "*";
	private static final String X = "X";
	private static final String ABC = "abc";

	protected void setUp() throws Exception {

		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testCreateTagDataField() throws Exception {
		
		new PatternDataField(INT, PatternUsage.TAG);
		
	}
	
	public void testCreateTagDataFieldWithInvalidStringRepresentation_Range() throws Exception {
		
		try {
			new PatternDataField(RANGE, PatternUsage.TAG);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + RANGE + "'. Only 'int' is allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + RANGE + "'.");
		
	}
	
	public void testCreateTagDataFieldWithInvalidStringRepresentation_Asterisk() throws Exception {
		
		try {
			new PatternDataField(ASTERISK, PatternUsage.TAG);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + ASTERISK + "'. Only 'int' is allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + ASTERISK + "'.");
		
	}
	
	public void testCreateTagDataFieldWithInvalidStringRepresentation_X() throws Exception {
		
		try {
			new PatternDataField(X, PatternUsage.TAG);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + X + "'. Only 'int' is allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + X + "'.");
		
	}
	
	public void testCreateTagDataFieldWithInvalidStringRepresentation_Abc() throws Exception {
		
		try {
			new PatternDataField(ABC, PatternUsage.TAG);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + ABC + "'. Only 'int' is allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + ABC + "'.");
		
	}
	
	public void testCreateFilterDataField() throws Exception {
		
		new PatternDataField(INT, PatternUsage.FILTER);
		new PatternDataField(RANGE, PatternUsage.FILTER);
		new PatternDataField(ASTERISK, PatternUsage.FILTER);
		
	}
	
	public void testCreateFilterDataFieldWithInvalidStringRepresentation_X() throws Exception {
		
		try {
			new PatternDataField(X, PatternUsage.FILTER);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + X + "'. Only '*', '[lo-hi]' or 'int' are allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + X + "'.");
		
	}
	
	public void testCreateFilterDataFieldWithInvalidStringRepresentation_Abc() throws Exception {
		
		try {
			new PatternDataField(ABC, PatternUsage.FILTER);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + ABC + "'. Only '*', '[lo-hi]' or 'int' are allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + ABC + "'.");
		
	}
	
	public void testCreateGroupDataField() throws Exception {
		
		new PatternDataField(INT, PatternUsage.GROUP);
		new PatternDataField(RANGE, PatternUsage.GROUP);
		new PatternDataField(ASTERISK, PatternUsage.GROUP);
		
	}
	
	public void testCreateGroupDataFieldWithInvalidStringRepresentation_Abc() throws Exception {
		
		try {
			new PatternDataField(ABC, PatternUsage.GROUP);
		} catch(ECSpecValidationException e) {
			assertEquals("Invalid data field '" + ABC + "'. Only '*', 'X', '[lo-hi]' or 'int' are allowed.", e.getReason());
			return;
		}
		
		fail("Should throw an ECSpecValidationException because the string representation of the data field contains a '" + ABC + "'.");
		
	}
	
	public void testTypes() throws Exception {

		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		PatternDataField asteriskField = new PatternDataField(ASTERISK, PatternUsage.GROUP);
		PatternDataField xField = new PatternDataField(X, PatternUsage.GROUP);
		
		assertTrue(intField.isInt());
		assertFalse(intField.isRange());
		assertFalse(intField.isAsterisk());
		assertFalse(intField.isX());
		
		assertFalse(rangeField.isInt());
		assertTrue(rangeField.isRange());
		assertFalse(rangeField.isAsterisk());
		assertFalse(rangeField.isX());
		
		assertFalse(asteriskField.isInt());
		assertFalse(asteriskField.isRange());
		assertTrue(asteriskField.isAsterisk());
		assertFalse(asteriskField.isX());
		
		assertFalse(xField.isInt());
		assertFalse(xField.isRange());
		assertFalse(xField.isAsterisk());
		assertTrue(xField.isX());
		
	}

	public void testGetValue() throws Exception {

		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		
		assertEquals(Integer.parseInt(INT), intField.getValue());
		
	}

	public void testGetValueWithNonIntDataField() throws Exception {
	
		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		
		try {
			rangeField.getValue();
		} catch(ImplementationException e) {
			assertEquals("Data field is not an int.", e.getReason());
			return;
		}
		
		fail("Should throw an ImplementationException because data field is not an of type int.");
		
	}
	
	public void testGetLow() throws Exception {

		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		
		assertEquals(LOW, rangeField.getLow());
		
	}
	
	public void testGetLowWithNonRangeDataField() throws Exception {
		
		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		
		try {
			intField.getLow();
		} catch(ImplementationException e) {
			assertEquals("Data field is not a range.", e.getReason());
			return;
		}
		
		fail("Should throw an ImplementationException because data field is not of type range.");
		
	}

	public void testGetHigh() throws Exception {

		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		
		assertEquals(HIGH, rangeField.getHigh());
		
	}
	
	public void testGetHighWithNonRangeDataField() throws Exception {
		
		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		
		try {
			intField.getHigh();
		} catch(ImplementationException e) {
			assertEquals("Data field is not a range.", e.getReason());
			return;
		}
		
		fail("Should throw an ImplementationException because data field is not of type range.");
		
	}

	public void testIsDisjoint() throws Exception {

		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		PatternDataField asteriskField = new PatternDataField(ASTERISK, PatternUsage.GROUP);
		PatternDataField xField = new PatternDataField(X, PatternUsage.GROUP);
		
		PatternDataField disjointIntField = new PatternDataField(new Integer(HIGH + 1).toString(), PatternUsage.GROUP);
		PatternDataField disjointRangeField = new PatternDataField("[" + new Integer(HIGH + 100).toString() + "-" +
				new Integer(HIGH + 200).toString() + "]", PatternUsage.GROUP);
		
		// test intField
		assertFalse(intField.isDisjoint(xField));
		assertFalse(intField.isDisjoint(asteriskField));
		assertFalse(intField.isDisjoint(intField));
		assertFalse(intField.isDisjoint(rangeField));
		assertTrue(intField.isDisjoint(disjointIntField));
		assertTrue(intField.isDisjoint(disjointRangeField));
		
		// test rangeField
		assertFalse(rangeField.isDisjoint(xField));
		assertFalse(rangeField.isDisjoint(asteriskField));
		assertFalse(rangeField.isDisjoint(intField));
		assertFalse(rangeField.isDisjoint(rangeField));
		assertTrue(rangeField.isDisjoint(disjointIntField));
		assertTrue(rangeField.isDisjoint(disjointRangeField));
		
		// test asteriskField
		assertFalse(asteriskField.isDisjoint(xField));
		assertFalse(asteriskField.isDisjoint(asteriskField));
		assertFalse(asteriskField.isDisjoint(intField));
		assertFalse(asteriskField.isDisjoint(rangeField));
		assertFalse(asteriskField.isDisjoint(disjointIntField));
		assertFalse(asteriskField.isDisjoint(disjointRangeField));
		
		// test xField
		assertFalse(xField.isDisjoint(xField));
		assertFalse(xField.isDisjoint(asteriskField));
		assertFalse(xField.isDisjoint(intField));
		assertFalse(xField.isDisjoint(rangeField));
		assertFalse(xField.isDisjoint(disjointIntField));
		assertFalse(xField.isDisjoint(disjointRangeField));
		
	}

	public void testIsMember() throws Exception {
		
		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		PatternDataField asteriskField = new PatternDataField(ASTERISK, PatternUsage.GROUP);
		PatternDataField xField = new PatternDataField(X, PatternUsage.GROUP);
		
		int member = Integer.parseInt(INT);
		int nonMember = HIGH + 100;
		
		// test with member
		assertTrue(intField.isMember(member));
		assertTrue(rangeField.isMember(member));
		assertTrue(asteriskField.isMember(member));
		assertTrue(xField.isMember(member));
		
		// test with non member
		assertFalse(intField.isMember(nonMember));
		assertFalse(rangeField.isMember(nonMember));
		assertTrue(asteriskField.isMember(nonMember));
		assertTrue(xField.isMember(nonMember));

	}

	public void testToString() throws Exception {

		PatternDataField intField = new PatternDataField(INT, PatternUsage.GROUP);
		PatternDataField rangeField = new PatternDataField(RANGE, PatternUsage.GROUP);
		PatternDataField asteriskField = new PatternDataField(ASTERISK, PatternUsage.GROUP);
		PatternDataField xField = new PatternDataField(X, PatternUsage.GROUP);
		
		assertEquals(INT, intField.toString());
		assertEquals(RANGE, rangeField.toString());
		assertEquals(ASTERISK, asteriskField.toString());
		assertEquals(X, xField.toString());
		
	}

}
