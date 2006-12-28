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