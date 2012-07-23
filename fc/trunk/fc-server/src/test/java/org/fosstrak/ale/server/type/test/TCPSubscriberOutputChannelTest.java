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
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.server.type.TCPSubscriberOutputChannel;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.junit.Test;

import util.ECElementsUtils;

/**
 * test the tcp socket subscriber output channel.
 * @author swieland
 *
 */
public class TCPSubscriberOutputChannelTest {

	@Test
	public void testTcpURIs() throws InvalidURIException {		
		TCPSubscriberOutputChannel listener = new TCPSubscriberOutputChannel("tcp://localhost:12345");
		Assert.assertEquals("localhost", listener.getHost());
		Assert.assertEquals(12345, listener.getPort());		
	}

	@Test(expected = InvalidURIException.class)
	public void testTcpWrongScheme() throws InvalidURIException {		
		new TCPSubscriberOutputChannel("ftp://localhost:12345");
	}
	
	@Test(expected = InvalidURIException.class)
	public void testTcpNull() throws InvalidURIException {		
		new TCPSubscriberOutputChannel(null);
	}
	
	/**
	 * test handling when the socket is throwing an illegal host exception.
	 */
	@Test(expected = ImplementationException.class)
	public void testNotify_TcpSocketException() throws Exception {
		Socket mock = EasyMock.createMock(Socket.class);
		EasyMock.expect(mock.getOutputStream()).andThrow(new UnknownHostException());
		EasyMock.replay(mock);
		TCPSubscriberOutputChannel tcp = new NotifyTcp("tcp://localhost:" + 9999, mock);
		tcp.notify(ECElementsUtils.createECReports());
	}
	
	/**
	 * test handling when the socket is throwing an illegal host exception.
	 */
	@Test(expected = ImplementationException.class)
	public void testNotify_TcpSocketIOException() throws Exception {
		Socket mock = EasyMock.createMock(Socket.class);
		EasyMock.expect(mock.getOutputStream()).andThrow(new IOException());
		EasyMock.replay(mock);
		TCPSubscriberOutputChannel tcp = new NotifyTcp("tcp://localhost:" + 9999, mock);
		tcp.notify(ECElementsUtils.createECReports());
	}

	@Test
	public void testNotify_Tcp() throws Exception {
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		Socket mock = EasyMock.createMock(Socket.class);
		EasyMock.expect(mock.getOutputStream()).andReturn(bout);
		mock.close();
		EasyMock.expectLastCall();
		EasyMock.replay(mock);
		
		TCPSubscriberOutputChannel tcp = new NotifyTcp("tcp://localhost:" + 9999, mock);
		
		// create reports
		ECReports reports = ECElementsUtils.createECReports();
		
		// notify listener about reports
		tcp.notify(reports);
		
		String res = bout.toString();
		ByteArrayInputStream bin = new ByteArrayInputStream(res.getBytes());
		ECReports resultReports = DeserializerUtil.deserializeECReports(bin);
		
		// check result
		EasyMock.verify(mock);
		ECElementsUtils.assertEquals(reports, resultReports);
	}
	
	/**
	 * little helper class allowing us to nicely test the TCP subscriber without the need of a real socket.
	 * @author swieland
	 *
	 */
	private class NotifyTcp extends TCPSubscriberOutputChannel {

		private Socket mock;
		
		public NotifyTcp(String notificationURI, Socket mock) throws InvalidURIException {
			super(notificationURI);
			this.mock = mock;
		}

		@Override
		protected Socket getSocket() throws UnknownHostException, IOException {
			return mock;
		}
	}
}
