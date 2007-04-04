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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.accada.ale.util.StreamUtil;


import junit.framework.TestCase;

public class StreamUtilTest extends TestCase {

	private static final String EXPECTED_STRING = "TestString";
	private static final InputStream INPUT_STREAM = new ByteArrayInputStream("TestString".getBytes());

	public void testInputStream2String() throws Exception {

		assertEquals(EXPECTED_STRING, StreamUtil.inputStream2String(INPUT_STREAM));
		
	}
}