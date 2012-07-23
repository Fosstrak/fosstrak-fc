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

package org.fosstrak.ale.server;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.server.type.SubscriberOutputChannel;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.junit.Test;

import util.ECElementsUtils;


/**
 * @author regli
 */
public class NotificationListenerTest {
	

	@Test
	public void testHttpURIs() throws Exception {		
		Subscriber listener = new Subscriber("http://localhost:123456");
		Assert.assertTrue(listener.isHttp());
	}

	@Test
	public void testTcpURIs() throws Exception {		
		Subscriber listener = new Subscriber("tcp://localhost:123456");
		Assert.assertTrue(listener.isTcp());	
	}

	@Test
	public void testFileURIs() throws Exception {		
		Subscriber listener = new Subscriber("file:///dir");
		Assert.assertTrue(listener.isFile());
	}

	@Test(expected = InvalidURIException.class)
	public void testInvalidURI_invalidProtocol() throws InvalidURIException {
		new Subscriber("htt://myhost.com");
	}

	@Test
	public void testNotify() throws InvalidURIException, ImplementationException {
		ECReports reports = ECElementsUtils.createECReports();
		SubscriberOutputChannel mock = EasyMock.createMock(SubscriberOutputChannel.class);
		EasyMock.expect(mock.notify(reports)).andReturn(true);
		EasyMock.replay(mock);
		
		Subscriber subscriber = new Subscriber("file:///dir");
		subscriber.setSubscriberOutputChannel(mock);
		subscriber.notify(reports);
		
		EasyMock.verify(mock);
	}
	
}