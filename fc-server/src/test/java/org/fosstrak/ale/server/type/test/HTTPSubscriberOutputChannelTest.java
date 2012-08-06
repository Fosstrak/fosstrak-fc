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

package org.fosstrak.ale.server.type.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.server.type.HTTPSubscriberOutputChannel;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.junit.Test;

import util.ECElementsUtils;

/**
 * test the http socket subscriber output channel.
 * @author swieland
 *
 */
public class HTTPSubscriberOutputChannelTest {
	
	@Test
	public void testHttpURIs_withExplicitPort() throws InvalidURIException {
		
		HTTPSubscriberOutputChannel listener = new HTTPSubscriberOutputChannel("http://localhost:123456");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals(123456, listener.getPort());
		Assert.assertEquals("", listener.getPath());
		
		listener = new HTTPSubscriberOutputChannel("http://localhost:123456/");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals(123456, listener.getPort());
		Assert.assertEquals("", listener.getPath());
		
		listener = new HTTPSubscriberOutputChannel("http://myhost.com:123456/abc");
		Assert.assertEquals("myhost.com", listener.getHost());
		Assert.assertEquals(123456, listener.getPort());
		Assert.assertEquals("abc", listener.getPath());

		listener = new HTTPSubscriberOutputChannel("http://192.168.1.1:123456/abc/def/ghi/");
		Assert.assertEquals("192.168.1.1", listener.getHost());
		Assert.assertEquals(123456, listener.getPort());
		Assert.assertEquals("abc/def/ghi/", listener.getPath());		
	}

	@Test
	public void testHttpURIs_withDefaultPort80() throws InvalidURIException {
		
		HTTPSubscriberOutputChannel listener = new HTTPSubscriberOutputChannel("http://myhost.com");
		Assert.assertEquals("myhost.com", listener.getHost());
		Assert.assertEquals(80, listener.getPort());
		Assert.assertEquals("", listener.getPath());
		
		listener = new HTTPSubscriberOutputChannel("http://myhost.com/");
		Assert.assertEquals("myhost.com", listener.getHost());
		Assert.assertEquals(80, listener.getPort());
		Assert.assertEquals("", listener.getPath());
		
		listener = new HTTPSubscriberOutputChannel("http://localhost/abc");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals(80, listener.getPort());
		Assert.assertEquals("abc", listener.getPath());
		
		listener = new HTTPSubscriberOutputChannel("http://192.168.1.1/abc/def/ghi/");
		Assert.assertEquals("192.168.1.1", listener.getHost());
		Assert.assertEquals(80, listener.getPort());
		Assert.assertEquals("abc/def/ghi/", listener.getPath());		
	}

	@Test(expected = InvalidURIException.class)
	public void testInvalidHttpURI_invalidHost() throws InvalidURIException {		
		new HTTPSubscriberOutputChannel("http://::");		
	}

	@Test(expected = InvalidURIException.class)
	public void testHttpInvalidHttpURI_invalidPort() throws InvalidURIException {
		new HTTPSubscriberOutputChannel("http://myhost.com:achttausend/abc");		
	}
	
	@Test(expected = InvalidURIException.class)
	public void testHttpInvalidHttpURI_null() throws InvalidURIException {
		new HTTPSubscriberOutputChannel(null);		
	}

	@Test
	public void testNotify_Http() throws Exception {
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		Socket mock = EasyMock.createMock(Socket.class);
		EasyMock.expect(mock.getOutputStream()).andReturn(bout);
		mock.close();
		EasyMock.expectLastCall();
		EasyMock.replay(mock);
		
		HTTPSubscriberOutputChannel tcp = new NotifyHTTP("http://localhost:" + 9999, mock);
		
		// create reports
		ECReports reports = ECElementsUtils.createECReports();
		
		// notify listener about reports
		tcp.notify(reports);
		
		String res = bout.toString();
		ByteArrayInputStream bin = new ByteArrayInputStream(res.getBytes());
		readHtmlHeaderFromInputStream(bin);
		ECReports resultReports = DeserializerUtil.deserializeECReports(bin);
		
		// check result
		EasyMock.verify(mock);
		ECElementsUtils.assertEquals(reports, resultReports);
	}
	
	private String readHtmlHeaderFromInputStream(InputStream inputStream) throws IOException {
		
		boolean newLine = false;
		byte[] buf = new byte[1];
		StringBuffer header = new StringBuffer();
		Assert.assertNotNull(inputStream);
		while (inputStream.read(buf) > 0) {
			String newString = new String(buf);
			if (buf[0] == 10) {
				if (newLine) {
					break;
				} else {
					newLine = true;
				}
			} else if (newLine == true) {
				newLine = false;
			}
			header.append(newString);
		}
		return header.toString();		
	}
	
	/**
	 * little helper class allowing us to nicely test the HTTP subscriber without the need of a real socket.
	 * @author swieland
	 *
	 */
	private class NotifyHTTP extends HTTPSubscriberOutputChannel {

		private Socket mock;
		
		public NotifyHTTP(String notificationURI, Socket mock) throws InvalidURIException {
			super(notificationURI);
			this.mock = mock;
		}

		@Override
		protected Socket getSocket() throws UnknownHostException, IOException {
			return mock;
		}
	}
}
