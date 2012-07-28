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

package org.fosstrak.ale.server.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.ReportsGeneratorState;
import org.fosstrak.ale.server.util.ECSpecValidator;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.junit.Assert;
import org.junit.Test;

import util.ECElementsUtils;

/**
 * test the reports generator.
 * 
 * TODO: test the thread behavior of the generator and the reports notification...
 * 
 * @author swieland
 *
 */
public class ReportsGeneratorTest {

	/** logger */
	private static final Logger LOG = Logger.getLogger(ReportsGeneratorTest.class);
	
	/**
	 * test the correct behavior of the constructor in the spec validation case.
	 */
	@Test(expected = ECSpecValidationException.class)
	public void testConstructorValidationException() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall().andThrow(new ECSpecValidationException("Mock exception"));
		EasyMock.replay(validator);
		new ReportsGenerator("theName", spec, validator);
	}
	
	/**
	 * test the correct behavior of the constructor in the spec validation case.
	 */
	@Test(expected = ImplementationException.class)
	public void testConstructorImplementationException() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall().andThrow(new ImplementationException("Mock exception"));
		EasyMock.replay(validator);
		new ReportsGenerator("theName", spec, validator);
	}
	
	/**
	 * test getter.
	 * @throws Exception test failure.
	 */
	@Test
	public void testGetSpecAndStateAndGetSubscribersAndGetName() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		ReportsGenerator generator = new ReportsGenerator("theName", spec, validator);
		Assert.assertEquals(spec, generator.getSpec());
		Assert.assertEquals(ReportsGeneratorState.UNREQUESTED, generator.getState());
		Assert.assertNotNull(generator.getSubscribers());
		Assert.assertEquals(0, generator.getSubscribers().size());
		Assert.assertEquals("theName", generator.getName());
		
		EasyMock.verify(validator);
	}
	
	/**
	 * test the correct behavior of the constructor in the spec validation case.
	 */
	@Test(expected = InvalidURIException.class)
	public void testSubscribeInvalidURI() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		ReportsGenerator generator = new ReportsGenerator("theName", spec, validator);
		generator.subscribe(null);
	}
	
	/**
	 * test duplicate subscription not allowed.
	 */
	@Test(expected = DuplicateSubscriptionException.class)
	public void testSubscribeDuplicateSubscriptionException() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		ReportsGenerator generator = new NonRunnableReportsGenerator("theName", spec, validator);
		
		final String uri ="http://localhost:9999"; 
		generator.subscribe(uri);
		generator.subscribe(uri);
	}
	
	/**
	 * test the correct behavior of the constructor in the spec validation case.
	 */
	@Test(expected = NoSuchSubscriberException.class)
	public void testUnsubscribeNoSuchSubscriber() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		ReportsGenerator generator = new ReportsGenerator("theName", spec, validator);
		generator.unsubscribe("http://localhost:9999");
	}
	
	/**
	 * test the correct behavior of the constructor in the spec validation case.
	 */
	@Test(expected = InvalidURIException.class)
	public void testUnsubscribeInvalidURI() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		ReportsGenerator generator = new ReportsGenerator("theName", spec, validator);
		generator.unsubscribe(null);
	}

	@Test
	public void testSubscribeUnSubscribe() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		ReportsGenerator generator = new NonRunnableReportsGenerator("theName", spec, validator);
		
		final String uri ="http://localhost:9999"; 
		final String uri2 ="http://localhost:8888"; 
		
		Assert.assertEquals(ReportsGeneratorState.UNREQUESTED, generator.getState());
		generator.subscribe(uri);
		Assert.assertEquals(1, generator.getSubscribers().size());
		Assert.assertEquals(ReportsGeneratorState.REQUESTED, generator.getState());
		generator.subscribe(uri2);
		
		Assert.assertEquals(2, generator.getSubscribers().size());
		List<String> subscribers = generator.getSubscribers();
		for (String str : new String[] {uri, uri2 }) {
			Assert.assertTrue(subscribers.contains(str));
		}
		
		generator.unsubscribe(uri);
		Assert.assertEquals(1, generator.getSubscribers().size());
		Assert.assertEquals(ReportsGeneratorState.REQUESTED, generator.getState());
		generator.unsubscribe(uri2);
		Assert.assertEquals(0, generator.getSubscribers().size());
		Assert.assertEquals(ReportsGeneratorState.UNREQUESTED, generator.getState());	
		
		EasyMock.verify(validator);
	}
	
	/**
	 * little helper class overriding the thread parts of the reports generator -> allows the testing.
	 * @author swieland
	 *
	 */
	private class NonRunnableReportsGenerator extends ReportsGenerator {
		
		public NonRunnableReportsGenerator(String name, ECSpec spec, ECSpecValidator validator)	throws ECSpecValidationException, ImplementationException {
			super(name, spec, validator);
		}

		@Override
		public void start() {
			LOG.debug("Mock start");
		}

		@Override
		public void stop() {
			LOG.debug("Mock stop");
		}
		
		
	}
}
