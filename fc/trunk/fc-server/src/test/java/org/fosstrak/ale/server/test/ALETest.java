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

import java.util.Arrays;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.easymock.EasyMock;
import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.ALESettings;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.impl.ALEImpl;
import org.fosstrak.ale.server.impl.type.InputGeneratorProvider;
import org.fosstrak.ale.server.impl.type.ReportsGeneratorsProvider;
import org.fosstrak.ale.server.readers.impl.type.PersistenceProvider;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.junit.Before;
import org.junit.Test;

/**
 * test the functionality of the default ALE Implementation.
 * 
 * @author swieland
 *
 */
public class ALETest {

	private ALE ale;
	
	@Before
	public void beforeEachTest() {
		ale = new ALEImpl();
	}
	
	/**
	 * vendor version shall never return null.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetVendorVersion() throws Exception {
		ALESettings aleSettings = EasyMock.createMock(ALESettings.class);
		((ALEImpl) ale).setAleSettings(aleSettings);
		EasyMock.expect(aleSettings.getAleVendorVersion()).andReturn("1.1");
		EasyMock.replay(aleSettings);
		Assert.assertEquals("1.1", ale.getVendorVersion());
		
		EasyMock.verify(aleSettings);
	}
	
	/**
	 * standard version shall never return null.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetStandardVersion() throws Exception {
		ALESettings aleSettings = EasyMock.createMock(ALESettings.class);
		((ALEImpl) ale).setAleSettings(aleSettings);
		EasyMock.expect(aleSettings.getAleStandardVersion()).andReturn("1.1.1");
		EasyMock.replay(aleSettings);
		Assert.assertEquals("1.1.1", ale.getStandardVersion());
		
		EasyMock.verify(aleSettings);
	}
	
	/**
	 * tests the initialize function
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testInitialize() throws Exception {		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		rgenProvider.clear();
		EasyMock.expectLastCall();
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		InputGeneratorProvider iProvider = EasyMock.createMock(InputGeneratorProvider.class);
		iProvider.clear();
		EasyMock.expectLastCall();
		EasyMock.replay(iProvider);
		((ALEImpl) ale).setInputGenerators(iProvider);
		
		((ALEImpl)ale).initialize();
		Assert.assertTrue(ale.isReady());
		
		// now try to invoke once more (must not initialize once more). easymock will throw an exception if mocks are hit twice.
		((ALEImpl)ale).initialize();
		Assert.assertTrue(ale.isReady());

		EasyMock.verify(rgenProvider);
		EasyMock.verify(iProvider);
	}
	
	/**
	 * tests that exception is thrown when invoking define on an existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = DuplicateNameException.class)
	public void testDefineDuplicateNameException() throws Exception {
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		ale.define("spec", null);
	}
	
	/**
	 * tests the define method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testDefine() throws Exception {		
		ECSpec ecSpec = new ECSpec();
		
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.writeECSpec("spec", ecSpec);
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceMock);
		((ALEImpl) ale).setPersistenceProvider(persistenceMock);
		
		ReportsGenerator rg = EasyMock.createMock(ReportsGenerator.class);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.createNewReportGenerator("spec", ecSpec)).andReturn(rg);
		EasyMock.expect(rgenProvider.put("spec", rg)).andReturn(rg);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(false);
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		ale.define("spec", ecSpec);
		
		EasyMock.verify(rgenProvider);
		EasyMock.verify(persistenceMock);
	}
	
	/**
	 * tests that exception is thrown when invoking undefine on not existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testUndefineThrowNoSuchNameException() throws Exception {
		ale.undefine("noSuchSpec");
	}
	
	/**
	 * 
	 * tests the undefine method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testUndefine() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.removeECSpec("spec");
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceMock);
		((ALEImpl) ale).setPersistenceProvider(persistenceMock);

		ReportsGenerator reportGenerator = EasyMock.createMock(ReportsGenerator.class);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.remove("spec")).andReturn(reportGenerator);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		ale.undefine("spec");
		
		EasyMock.verify(rgenProvider);
		EasyMock.verify(persistenceMock);
	}
	
	/**
	 * tests that exception is thrown when invoking getECSpec on not existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testGetECSpecThrowNoSuchNameException() throws Exception {
		ale.getECSpec("noSuchSpec");
	}
	
	/**
	 * tests the getECSpec method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetECSpec() throws Exception {
		ReportsGenerator reportGenerator = EasyMock.createMock(ReportsGenerator.class);
		ECSpec spec = new ECSpec();
		EasyMock.expect(reportGenerator.getSpec()).andReturn(spec);
		EasyMock.replay(reportGenerator);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.expect(rgenProvider.get("spec")).andReturn(reportGenerator);
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		ECSpec result = ale.getECSpec("spec");
		Assert.assertNotNull(result);
		Assert.assertEquals(spec, result);

		EasyMock.verify(reportGenerator);
		EasyMock.verify(rgenProvider);
	}

	/**
	 * test that the specification names are correctly returned.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	public void testGetECSpecNames() throws Exception {
		String[] names = ale.getECSpecNames();
		Assert.assertNotNull(names);
		Assert.assertEquals(0, names.length);
	}
	
	/**
	 * tests that exception is thrown when invoking subscribe on not existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testSubscribeThrowNoSuchNameException() throws Exception {
		ale.subscribe("noSuchSpec", "");
	}
	
	/**
	 * tests the subscribe method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testSubscribe() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.writeECSpecSubscriber(EasyMock.isA(String.class), EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceMock);
		((ALEImpl) ale).setPersistenceProvider(persistenceMock);
		
		final String notificationURI = "test";
		ReportsGenerator reportGenerator = EasyMock.createMock(ReportsGenerator.class);
		reportGenerator.subscribe(notificationURI);
		EasyMock.expectLastCall();
		EasyMock.replay(reportGenerator);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.expect(rgenProvider.get("spec")).andReturn(reportGenerator);
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		ale.subscribe("spec", notificationURI);

		EasyMock.verify(persistenceMock);
		EasyMock.verify(reportGenerator);
		EasyMock.verify(rgenProvider);
	}
	
	/**
	 * tests that exception is thrown when invoking unsubscribe on not existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testUnsubscribeThrowNoSuchNameException() throws Exception {
		ale.unsubscribe("noSuchSpec", "");
	}
	
	/**
	 * tests the unsubscribe method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testUnsubscribe() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.removeECSpecSubscriber(EasyMock.isA(String.class), EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceMock);
		((ALEImpl) ale).setPersistenceProvider(persistenceMock);
		
		final String notificationURI = "test";
		ReportsGenerator reportGenerator = EasyMock.createMock(ReportsGenerator.class);
		reportGenerator.unsubscribe(notificationURI);
		EasyMock.expectLastCall();
		EasyMock.replay(reportGenerator);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.expect(rgenProvider.get("spec")).andReturn(reportGenerator);
		EasyMock.replay(rgenProvider);		
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		ale.unsubscribe("spec", notificationURI);

		EasyMock.verify(persistenceMock);
		EasyMock.verify(reportGenerator);
		EasyMock.verify(rgenProvider);
	}
	
	/**
	 * test the close method.
	 */
	@Test
	public void testClose() {
		// TODO: elaborate the test.
		ale.close();
		Assert.assertEquals(0, ale.getECSpecNames().length);
	}
	
	/**
	 * tests that exception is thrown when invoking poll on not existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testPollThrowNoSuchNameException() throws Exception {
		ale.poll("noSuchSpec");
	}
	
	/**
	 * test the poll method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testPoll() throws Exception {
		ReportsGenerator reportGenerator = EasyMock.createMock(ReportsGenerator.class);
		reportGenerator.poll();
		EasyMock.expectLastCall();
		ECReports reports = new ECReports();
		EasyMock.expect(reportGenerator.getPollReports()).andReturn(reports);
		EasyMock.replay(reportGenerator);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.expect(rgenProvider.get("spec")).andReturn(reportGenerator);
		EasyMock.replay(rgenProvider);
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		
		ECReports result = ale.poll("spec");
		Assert.assertNotNull(result);
		Assert.assertEquals(reports, result);

		EasyMock.verify(reportGenerator);
		EasyMock.verify(rgenProvider);
	}
	
	/**
	 * tests that exception is thrown when invoking getSubscribers on not existing specification.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testGetSubscribersThrowNoSuchNameException() throws Exception {
		ale.getSubscribers("noSuchSpec");
	}
	
	/**
	 * test the getSubscribers method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetSubscribers() throws Exception {
		String[] subscribers = new String[] { "sub1", "sub2" };
		ReportsGenerator reportGenerator = EasyMock.createMock(ReportsGenerator.class);
		EasyMock.expect(reportGenerator.getSubscribers()).andReturn(Arrays.asList(subscribers));
		EasyMock.replay(reportGenerator);
		
		ReportsGeneratorsProvider rgenProvider = EasyMock.createMock(ReportsGeneratorsProvider.class);
		EasyMock.expect(rgenProvider.containsKey("spec")).andReturn(true);
		EasyMock.expect(rgenProvider.get("spec")).andReturn(reportGenerator);
		EasyMock.replay(rgenProvider);
		
		((ALEImpl) ale).setReportGeneratorsProvider(rgenProvider);
		Assert.assertTrue(ArrayUtils.isEquals(subscribers, ale.getSubscribers("spec")));

		EasyMock.verify(reportGenerator);
		EasyMock.verify(rgenProvider);
	}
	
	/**
	 * test that the ale is not returning null for the reports generator.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetReportGenerators() throws Exception {
		Assert.assertNotNull(ale.getReportGenerators());
	}
	
}
