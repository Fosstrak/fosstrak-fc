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

package org.accada.ale.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.accada.ale.util.DeserializerUtil;
import org.accada.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.apache.log4j.PropertyConfigurator;

import util.ECElementsUtils;
import util.SocketListener;

/**
 * @author regli
 */
public class NotificationListenerTest extends TestCase {

	private static final String INVALID_URI_EXCEPTION_TEXT = "A valid URI must have one of the following forms: (http://host[:port]/remainder-of-URL | tcp://host:port | file://[host]/path)";
	
	private static final int NOTIFICATION_PORT = 6543;
	
	protected void setUp() throws Exception {
		
		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
	}
	
	public void testHttpURIs_withExplicitPort() throws Exception {
		
		Subscriber listener = new Subscriber("http://localhost:123456");
		assertTrue(listener.isHttp());
		assertEquals("localhost", listener.getHost());
		assertEquals(123456, listener.getPort());
		assertEquals("", listener.getPath());
		
		listener = new Subscriber("http://localhost:123456/");
		assertTrue(listener.isHttp());
		assertEquals("localhost", listener.getHost());
		assertEquals(123456, listener.getPort());
		assertEquals("", listener.getPath());
		
		listener = new Subscriber("http://myhost.com:123456/abc");
		assertTrue(listener.isHttp());
		assertEquals("myhost.com", listener.getHost());
		assertEquals(123456, listener.getPort());
		assertEquals("abc", listener.getPath());

		listener = new Subscriber("http://192.168.1.1:123456/abc/def/ghi/");
		assertTrue(listener.isHttp());
		assertEquals("192.168.1.1", listener.getHost());
		assertEquals(123456, listener.getPort());
		assertEquals("abc/def/ghi/", listener.getPath());
		
	}
	
	public void testHttpURIs_withDefaultPort80() throws Exception {
		
		Subscriber listener = new Subscriber("http://myhost.com");
		assertTrue(listener.isHttp());
		assertEquals("myhost.com", listener.getHost());
		assertEquals(80, listener.getPort());
		assertEquals("", listener.getPath());
		
		listener = new Subscriber("http://myhost.com/");
		assertTrue(listener.isHttp());
		assertEquals("myhost.com", listener.getHost());
		assertEquals(80, listener.getPort());
		assertEquals("", listener.getPath());
		
		listener = new Subscriber("http://localhost/abc");
		assertTrue(listener.isHttp());
		assertEquals("localhost", listener.getHost());
		assertEquals(80, listener.getPort());
		assertEquals("abc", listener.getPath());
		
		listener = new Subscriber("http://192.168.1.1/abc/def/ghi/");
		assertTrue(listener.isHttp());
		assertEquals("192.168.1.1", listener.getHost());
		assertEquals(80, listener.getPort());
		assertEquals("abc/def/ghi/", listener.getPath());
		
	}
	
	public void testInvalidHttpURI_invalidHost() throws Exception {
		
		try {
			new Subscriber("http://::");
		} catch(InvalidURIException e) {
			assertEquals(INVALID_URI_EXCEPTION_TEXT, e.getReason());
			return;
		}
		fail("Invalid host. Should throw InvalidURIException");
		
	}
	
	public void testInvalidHttpURI_invalidPort() throws Exception {
		
		try {
			new Subscriber("http://myhost.com:achttausend/abc");
		} catch(InvalidURIException e) {
			assertEquals("Invalid port. " + INVALID_URI_EXCEPTION_TEXT, e.getReason());
			return;
		}
		fail("Invalid port. Should throw InvalidURIException");
		
	}
	
	public void testTcpURIs() throws Exception {
		
		Subscriber listener = new Subscriber("tcp://localhost:123456");
		assertTrue(listener.isTcp());
		assertEquals("localhost", listener.getHost());
		assertEquals(123456, listener.getPort());
		
	}
	
	public void testInvalidTcpURIs_invalidPort() throws Exception {
		
		try {
			new Subscriber("tcp://localhost:123456/");
		} catch (InvalidURIException e) {
			assertEquals("Invalid port. " + INVALID_URI_EXCEPTION_TEXT, e.getReason());
			return;
		}
		fail("Invalid port. Should throw InvalidURIException.");
		
	}

	public void testFileURIs() throws Exception {
		
		Subscriber listener = new Subscriber("file:///dir");
		assertTrue(listener.isFile());
		assertEquals("localhost", listener.getHost());
		assertEquals("dir", listener.getPath());
		
		listener = new Subscriber("file://localhost/dir/dir");
		assertTrue(listener.isFile());
		assertEquals("localhost", listener.getHost());
		assertEquals("dir/dir", listener.getPath());
		
		listener = new Subscriber("file://localhost/dir/dir/");
		assertTrue(listener.isFile());
		assertEquals("localhost", listener.getHost());
		assertEquals("dir/dir/", listener.getPath());
		
		listener = new Subscriber("file://localhost/dir/dir");
		assertTrue(listener.isFile());
		assertEquals("localhost", listener.getHost());
		assertEquals("dir/dir", listener.getPath());
		
		listener = new Subscriber("file://myhost.com/dir/dir");
		assertTrue(listener.isFile());
		assertEquals("myhost.com", listener.getHost());
		assertEquals("dir/dir", listener.getPath());
		
		listener = new Subscriber("file://192.168.1.1/dir/dir");
		assertTrue(listener.isFile());
		assertEquals("192.168.1.1", listener.getHost());
		assertEquals("dir/dir", listener.getPath());
		
	}
	
	public void testInvalidFileURIs_noPath() throws Exception {
		
		try {
			new Subscriber("file://localhost");
		} catch (InvalidURIException e) {
			assertEquals("Invalid path. " + INVALID_URI_EXCEPTION_TEXT, e.getReason());
			return;
		}
		fail("Invalid port. Should throw InvalidURIException.");
		
	}
	
	public void testInvalidURI_invalidProtocol() throws Exception {
		
		try {
			new Subscriber("htt://myhost.com");
		} catch(InvalidURIException e) {
			assertEquals("Invalid protocol. " + INVALID_URI_EXCEPTION_TEXT, e.getReason());
			return;
		}
		fail("Invalid protocol. Should throw InvalidURIException");
		
	}
	
	public void testNotify_File() throws Exception {
		
		// create file
		File notificationFile = File.createTempFile("NotifiactionListenerTest", null);
		
		// create notification listener
		Subscriber notificationListener = new Subscriber("file:///" + notificationFile.getAbsolutePath());
		
		// create reports
		ECReports reports = ECElementsUtils.createECReports();
		
		// notify listener about reports
		notificationListener.notify(reports);
		
		// read file
		ECReports resultReports = DeserializerUtil.deserializeECReports(new FileInputStream(notificationFile));
		
		// check result
		ECElementsUtils.assertEquals(reports, resultReports);
				
	}
	
	public void testNotify_Tcp() throws Exception {

		// create socket listener
		SocketListener socketListener = new SocketListener(NOTIFICATION_PORT);
		
		// create notification listener
		Subscriber notificationListener = new Subscriber("tcp://localhost:" + NOTIFICATION_PORT);
		
		// create reports
		ECReports reports = ECElementsUtils.createECReports();
		
		// notify listener about reports
		notificationListener.notify(reports);
		
		// get input stream
		InputStream inputStream = socketListener.getInputStream();
		
		// read from input stream
		ECReports resultReports = DeserializerUtil.deserializeECReports(inputStream);
		
		// close socket listener
		socketListener.stop();
		
		// check result
		ECElementsUtils.assertEquals(reports, resultReports);
		
	}
	
	public void testNotify_Http() throws Exception {
		
		// create socket listener
		SocketListener socketListener = new SocketListener(NOTIFICATION_PORT);
		
		// create notification listener
		Subscriber notificationListener = new Subscriber("http://localhost:" + NOTIFICATION_PORT);
		
		// create reports
		ECReports reports = ECElementsUtils.createECReports();
		
		// notify listener about reports
		notificationListener.notify(reports);
		
		// get input stream
		InputStream inputStream = socketListener.getInputStream();
		
		// read from input stream
		readHtmlHeaderFromInputStream(inputStream);
		ECReports resultReports = DeserializerUtil.deserializeECReports(inputStream);
		
		// close socket listener
		socketListener.stop();
		
		// check result
		ECElementsUtils.assertEquals(reports, resultReports);
		
	}
	
	private String readHtmlHeaderFromInputStream(InputStream inputStream) throws IOException {
		
		boolean newLine = false;
		byte[] buf = new byte[1];
		StringBuffer header = new StringBuffer();
		while (inputStream.read(buf) > 0) {
			String newString = new String(buf);
			if (buf[0] == 10) {
				if (newLine) {
					break;
				} else {
					newLine = true;
				}
			} else if (newLine = true) {
				newLine = false;
			}
			header.append(newString);
		}
		return header.toString();
		
	}
	
}