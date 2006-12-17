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

/**
 * This class provides some methods to packaging a message into a soap envelope.
 * 
 * @author regli
 */
public class EnveloperUtil {

	/** xml version tag */
	private static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	/** opening soap envelope and body tag */
	private static final String ENVELOPE_OPEN = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Body>";
	/** closing soap body and envelope tag */
	private static final String ENVELOPE_CLOSE = "</soap:Body></soap:Envelope>";

	/**
	 * This method packaging a message from an input stream into a soap envelope and returns it as an input stream.
	 * 
	 * @param in input stream with the message to envelope
	 * @return input stream with the soap message
	 */
	public static InputStream envelope(InputStream in) {
		
		return new ByteArrayInputStream(envelope(StreamUtil.inputStream2String(in)).getBytes());
		
	}
	
	/**
	 * This method packaging a message from a string into a soap envelope and returns it as a string.
	 * 
	 * @param s string with the message to envelope
	 * @return string with the soap message
	 */
	public static String envelope(String s) {
		
		String xmlVersion;
		String content;
		int index;
		if ((index = s.indexOf("?>") + 2) > 2) {
			xmlVersion = s.substring(0, index);
			content = s.substring(index);
		} else {
			xmlVersion = XML_VERSION;
			content = s;
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append(xmlVersion);
		buf.append(ENVELOPE_OPEN);
		buf.append(content);
		buf.append(ENVELOPE_CLOSE);
		
		return buf.toString();
		
	}
	
}