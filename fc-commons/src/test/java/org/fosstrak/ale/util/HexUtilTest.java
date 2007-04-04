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

package org.accada.ale.util;

import java.net.URL;

import org.accada.ale.util.HexUtil;
import org.apache.log4j.PropertyConfigurator;

import junit.framework.TestCase;

/**
 * @author regli
 */
public class HexUtilTest extends TestCase {

	private static final byte[] byteArray = new byte[] {16, 17, 34, 51, 68, 0, 1, 2, 3, 4, 10, 15, -96, -16, -86, -81, -6, -1};
	private static final String hexString = "101122334400010203040A0FA0F0AAAFFAFF";
	
	protected void setUp() throws Exception {
		
		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testByteArrayToHexString() throws Exception {
		
		assertEquals(hexString, HexUtil.byteArrayToHexString(byteArray));
		
	}
	
	public void testHexStringToByteArray() throws Exception {
		
		assertEquals(byteArray, HexUtil.hexStringToByteArray(hexString));
		
	}
	
	private void assertEquals(byte[] expected, byte[] actual) {
		
		assertEquals(expected.length, actual.length);
		
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
		
	}
	
}