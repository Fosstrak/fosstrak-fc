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
import java.net.URL;

import junit.framework.TestCase;

import org.accada.ale.util.EnveloperUtil;
import org.accada.ale.util.StreamUtil;
import org.apache.log4j.PropertyConfigurator;


public class EnveloperUtilTest extends TestCase {

	private static final String INPUT_STRING1 = "<?xml ?><Test/>";
	private static final String EXPECTED_STRING1 = "<?xml ?><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Body><Test/></soap:Body></soap:Envelope>";
	private static final String INPUT_STRING2 = "<Test/>";
	private static final String EXPECTED_STRING2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Body><Test/></soap:Body></soap:Envelope>";

	private static final InputStream INPUT_STREAM2 = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Test/>".getBytes());
	
	protected void setUp() throws Exception {

		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testEnvelopeString() throws Exception {
		
		assertEquals(EXPECTED_STRING1, EnveloperUtil.envelope(INPUT_STRING1));
		
		assertEquals(EXPECTED_STRING2, EnveloperUtil.envelope(INPUT_STRING2));
		
	}
	
	public void testEnvelopeStream() throws Exception {
		
		InputStream resultInputStream = EnveloperUtil.envelope(INPUT_STREAM2);
		
		String result = StreamUtil.inputStream2String(resultInputStream);
				
		assertEquals(EXPECTED_STRING2, result);
		
	}
	
}