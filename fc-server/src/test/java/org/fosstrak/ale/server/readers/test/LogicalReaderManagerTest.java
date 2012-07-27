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
package org.fosstrak.ale.server.readers.test;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InUseException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.exception.NonCompositeReaderException;
import org.fosstrak.ale.exception.ValidationException;
import org.fosstrak.ale.server.ALESettings;
import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.server.readers.CompositeReader;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.server.readers.impl.LogicalReaderManagerImpl;
import org.fosstrak.ale.server.readers.impl.type.ReaderProvider;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.junit.Before;
import org.junit.Test;

/**
 * unit tests for the logical reader management.
 * 
 * @author swieland
 *
 */
public class LogicalReaderManagerTest {
	
	private LogicalReaderManager manager;
	
	@Before
	public void beforeEachTest() {
		manager = new LogicalReaderManagerImpl();
	}
	
	/**
	 * vendor version shall never return null.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetVendorVersion() throws Exception {
		ALESettings aleSettings = EasyMock.createMock(ALESettings.class);
		((LogicalReaderManagerImpl) manager).setAleSettings(aleSettings);
		EasyMock.expect(aleSettings.getVendorVersion()).andReturn("1.1");
		EasyMock.replay(aleSettings);
		Assert.assertEquals("1.1", manager.getVendorVersion());
		
		EasyMock.verify(aleSettings);
	}
	
	/**
	 * standard version shall never return null.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetStandardVersion() throws Exception {
		ALESettings aleSettings = EasyMock.createMock(ALESettings.class);
		((LogicalReaderManagerImpl) manager).setAleSettings(aleSettings);
		EasyMock.expect(aleSettings.getLrStandardVersion()).andReturn("1.1.1");
		EasyMock.replay(aleSettings);
		Assert.assertEquals("1.1.1",manager.getStandardVersion());
		
		EasyMock.verify(aleSettings);
	}
	
	/**
	 * verify that the setting/getting of readers is correctly working.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testReaderSettersAndGetters() throws Exception {
		
		LogicalReader logicalReader1 = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader1.getName()).andReturn("reader1").atLeastOnce();
		EasyMock.expect(logicalReader1.isStarted()).andReturn(false).atLeastOnce();
		// start is invoked at least once (as mock is always returning not-started).
		logicalReader1.start();
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(logicalReader1);
		
		LogicalReader logicalReader2 = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader2.getName()).andReturn("reader2").atLeastOnce();
		EasyMock.expect(logicalReader2.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.replay(logicalReader2);
		
		// test setters and getters -----------------
		
		manager.setLogicalReader(logicalReader1);
		manager.setLogicalReader(logicalReader2);
		Assert.assertNotNull(manager.getLogicalReader("reader1"));
		Assert.assertEquals("reader1", manager.getLogicalReader("reader1").getName());
		Assert.assertNotNull(manager.getLogicalReader("reader2"));
		Assert.assertEquals("reader2", manager.getLogicalReader("reader2").getName());
		// non existing reader.
		Assert.assertNull(manager.getLogicalReader("reader3"));
		
		Assert.assertEquals(2, manager.getLogicalReaders().size());
		List<String> readerNames = manager.getLogicalReaderNames();
		Assert.assertEquals(2, readerNames.size());
		Assert.assertTrue(readerNames.contains("reader1"));
		Assert.assertTrue(readerNames.contains("reader2"));
		
		EasyMock.verify(logicalReader1);
		EasyMock.verify(logicalReader2);
	}
	
	/**
	 * verify that the setting a reader twice is not allowed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ImplementationException.class)
	public void testSetLogicalReadersNotSetTwice() throws Exception {
		LogicalReader logicalReader1 = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader1.getName()).andReturn("reader1").atLeastOnce();
		EasyMock.expect(logicalReader1.isStarted()).andReturn(false).atLeastOnce();
		// start is invoked only once.
		logicalReader1.start();
		EasyMock.expectLastCall();
		EasyMock.replay(logicalReader1);
		
		manager.setLogicalReader(logicalReader1);
		Assert.assertNotNull(manager.getLogicalReader("reader1"));
		
		// add once more must trigger an exception
		manager.setLogicalReader(logicalReader1);
	}
	
	/**
	 * tests the property getter
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetPropertyValue() throws Exception {
		final String readerName = "testPropertyMutators";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		List<LRProperty> props = new LinkedList<LRProperty> ();
		LRProperty p1 = new LRProperty();
		p1.setName("name1");
		p1.setValue("value1");
		
		LRProperty p2 = new LRProperty();
		p2.setName("name2");
		p2.setValue("value2");
		
		props.add(p1);
		props.add(p2);
		
		EasyMock.expect(logicalReader.getProperties()).andReturn(props).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		
		String value = manager.getPropertyValue(readerName, "name2");
		Assert.assertEquals("value2", value);
		Assert.assertNull(manager.getPropertyValue(readerName, "unknown"));
		
		EasyMock.verify(logicalReader);
	}

	/**
	 * test property setter when illegal null input.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationException.class)
	public void testSetPropertyIllegalNullInput() throws Exception {
		manager.setProperties("notExistingReader", null);
	}
	
	/**
	 * test property setter on non existing reader
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testSetPropertyNotExistingReader() throws Exception {
		manager.setProperties("notExistingReader", new LinkedList<LRProperty> ());
	}	
	
	/**
	 * test property setter
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testSetProperty() throws Exception {

		WriteConfig persistenceWriteMock = EasyMock.createMock(WriteConfig.class);
		persistenceWriteMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceWriteMock);
		((LogicalReaderManagerImpl) manager).setPersistenceWriteAPI(persistenceWriteMock);
		
		RemoveConfig persistenceRemoveMock = EasyMock.createMock(RemoveConfig.class);
		persistenceRemoveMock.removeLRSpec(EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceRemoveMock);
		((LogicalReaderManagerImpl) manager).setPersistenceRemoveAPI(persistenceRemoveMock);
		
		final String logicalReaderName = "logicalReader";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(logicalReaderName).atLeastOnce();

		LRSpec.Properties lrSpecProperties = new LRSpec.Properties();
		LRProperty p0 = new LRProperty();
		p0.setName("name0");
		p0.setValue("value0");
		lrSpecProperties.getProperty().add(p0);
		LRSpec lrSpec = EasyMock.createMock(LRSpec.class);
		EasyMock.expect(lrSpec.getProperties()).andReturn(lrSpecProperties).atLeastOnce();
		EasyMock.replay(lrSpec);
		
		EasyMock.expect(logicalReader.getLRSpec()).andReturn(lrSpec);
		logicalReader.update(lrSpec);
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(logicalReader);
		

		manager.setLogicalReader(logicalReader);
		
		LRProperty p1 = new LRProperty();
		p1.setName("name1");
		p1.setValue("value1");
		LRProperty p2 = new LRProperty();
		p2.setName("name2");
		p2.setValue("value2");	
		
		manager.setProperties(logicalReaderName, Arrays.asList(new LRProperty[] { p1, p2 } ));
		Assert.assertEquals(2, lrSpecProperties.getProperty().size());
		// original property must be removed.
		for (LRProperty p : lrSpecProperties.getProperty()) {
			Assert.assertNotSame("name0", p.getName());
		}
		
		EasyMock.verify(logicalReader);
		EasyMock.verify(persistenceWriteMock);
		EasyMock.verify(persistenceRemoveMock);
		EasyMock.verify(lrSpec);
	}
	
	/**
	 * test that the LR specification of a reader can be queried.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testGetLRSpecNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.getLRSpec("virtualReader");
	}
	
	/**
	 * test that the LR specification of a reader can be queried.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetLRSpec() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		
		LRSpec lrSpec = new LRSpec();
		EasyMock.expect(logicalReader.getLRSpec()).andReturn(lrSpec);
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		LRSpec result = manager.getLRSpec(readerName);
		Assert.assertEquals(lrSpec, result);
		
		EasyMock.verify(logicalReader);
	}
	
	/**
	 * test no unknown reader can be undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testUndefineNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.undefine("virtualReader");
	}
	
	/**
	 * test that readers in use are not undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = InUseException.class)
	public void testUndefineReaderInUse() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.expect(logicalReader.countObservers()).andReturn(1);
		
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
		
		EasyMock.verify(logicalReader);
	}
	
	/**
	 * test that base readers are undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testUndefineBaseReader() throws Exception {
		final String readerName = "readerName";
		BaseReader logicalReader = EasyMock.createMock(BaseReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.expect(logicalReader.countObservers()).andReturn(0);
		logicalReader.disconnectReader();
		EasyMock.expectLastCall();
		logicalReader.cleanup();
		EasyMock.expectLastCall();
		
		EasyMock.replay(logicalReader);
		
		RemoveConfig persistenceRemoveMock = EasyMock.createMock(RemoveConfig.class);
		persistenceRemoveMock.removeLRSpec(EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceRemoveMock);
		((LogicalReaderManagerImpl) manager).setPersistenceRemoveAPI(persistenceRemoveMock);
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
		Assert.assertEquals(0, manager.getLogicalReaders().size());
		
		EasyMock.verify(logicalReader);
		EasyMock.verify(persistenceRemoveMock);
	}	
	
	/**
	 * test that composite readers are undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testUndefineCompositeReader() throws Exception {
		final String readerName = "readerName";
		CompositeReader logicalReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.expect(logicalReader.countObservers()).andReturn(0);
		logicalReader.unregisterAsObserver();
		EasyMock.expectLastCall();
		
		EasyMock.replay(logicalReader);
		
		RemoveConfig persistenceRemoveMock = EasyMock.createMock(RemoveConfig.class);
		persistenceRemoveMock.removeLRSpec(EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceRemoveMock);
		((LogicalReaderManagerImpl) manager).setPersistenceRemoveAPI(persistenceRemoveMock);
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
		Assert.assertEquals(0, manager.getLogicalReaders().size());
		
		EasyMock.verify(logicalReader);
		EasyMock.verify(persistenceRemoveMock);
	}	
	
	/**
	 * test that composite readers are undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ImplementationException.class)
	public void testUndefineUnsupportedReaderType() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.expect(logicalReader.countObservers()).andReturn(0);
		
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
		
		EasyMock.verify(logicalReader);
	}	

	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationException.class)
	public void testDefineIllegalInputName() throws Exception {
		manager.define(null, (org.fosstrak.ale.server.readers.gen.LRSpec) null);
	}

	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationException.class)
	public void testDefineIllegalInputSpec() throws Exception {
		manager.define("name", (org.fosstrak.ale.server.readers.gen.LRSpec) null);
	}
	
	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationException.class)
	public void testDefine2IllegalInputName() throws Exception {
		manager.define(null, (LRSpec) null);
	} 
	
	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationException.class)
	public void testDefine2IllegalInputSpec() throws Exception {
		manager.define("name", (LRSpec) null);
	}
	
	/**
	 * test the definition of a reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testDefine() throws Exception {
		WriteConfig persistenceWriteMock = EasyMock.createMock(WriteConfig.class);
		persistenceWriteMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.replay(persistenceWriteMock);
		((LogicalReaderManagerImpl) manager).setPersistenceWriteAPI(persistenceWriteMock);
		
		final BaseReader baseReader = EasyMock.createMock(BaseReader.class);
		baseReader.connectReader();
		EasyMock.expectLastCall();
		EasyMock.replay(baseReader);
		
		// need a handle onto the spec delivered for the reader creation.
		final AtomicReference<org.fosstrak.ale.xsd.ale.epcglobal.LRSpec> ref = new AtomicReference<org.fosstrak.ale.xsd.ale.epcglobal.LRSpec>(); 
		
		ReaderProvider readerProvider = EasyMock.createMock(ReaderProvider.class);
		EasyMock.expect(readerProvider.createReader(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class))).andDelegateTo(new ReaderProvider() {
			@Override
			public LogicalReader createReader(String name, org.fosstrak.ale.xsd.ale.epcglobal.LRSpec theSpec)  throws ImplementationException {
				ref.set(theSpec);
				return baseReader;
			}
		});
		EasyMock.replay(readerProvider);
		((LogicalReaderManagerImpl) manager).setReaderProvider(readerProvider);
		
		org.fosstrak.ale.server.readers.gen.LRSpec spec = new org.fosstrak.ale.server.readers.gen.LRSpec();
		spec.setIsComposite(true);
		spec.setReaderType("theNiceTestReaderType");
		org.fosstrak.ale.server.readers.gen.LRProperty p = new org.fosstrak.ale.server.readers.gen.LRProperty();
		p.setName("name");
		p.setValue("value");
		spec.getLRProperty().add(p);
		spec.getReaders().addAll(Arrays.asList(new String[] { "reader1", "reader2", "reader3" }));

		final String readerName = "readerName";
		manager.define(readerName, spec);
		
		Assert.assertTrue(manager.contains(readerName));
		org.fosstrak.ale.xsd.ale.epcglobal.LRSpec theNewSpec = ref.get();
		Assert.assertNotNull(theNewSpec);
		Assert.assertTrue(theNewSpec.isIsComposite());
		Assert.assertEquals(2, theNewSpec.getProperties().getProperty().size());
		Map<String, String> keyValue = new HashMap<String, String> ();
		for (LRProperty pr : theNewSpec.getProperties().getProperty()) {
			keyValue.put(pr.getName(), pr.getValue());
		}
		Assert.assertEquals("theNiceTestReaderType",keyValue.get("ReaderType"));
		Assert.assertEquals("value", keyValue.get("name"));		
		
		EasyMock.verify(persistenceWriteMock);
		EasyMock.verify(baseReader);
	}
	
	/**
	 * test that the readers of a composite reader can be set.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testSetReadersNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.setReaders("virtualReader", null);
	}
	
	/**
	 * test that the readers of a composite reader can be set.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NonCompositeReaderException.class)
	public void testSetReadersNonCompositeReader() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		
		// test on some not-existing reader
		manager.setReaders(readerName, null);
		
		EasyMock.verify(logicalReader);	
	}
	
	
	/**
	 * test that the readers of a composite reader can be set.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testSetReader() throws Exception {

		WriteConfig persistenceWriteMock = EasyMock.createMock(WriteConfig.class);
		persistenceWriteMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceWriteMock);
		((LogicalReaderManagerImpl) manager).setPersistenceWriteAPI(persistenceWriteMock);
		
		RemoveConfig persistenceRemoveMock = EasyMock.createMock(RemoveConfig.class);
		persistenceRemoveMock.removeLRSpec(EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceRemoveMock);
		((LogicalReaderManagerImpl) manager).setPersistenceRemoveAPI(persistenceRemoveMock);
				
		final String compositeReaderName = "compositeReader";
		LogicalReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn(compositeReaderName).atLeastOnce();

		LRSpec lrSpec = new LRSpec();
		LRSpec.Readers readers = new LRSpec.Readers();
		readers.getReader().add("reader2");
		lrSpec.setReaders(readers);
		
		EasyMock.expect(compositeReader.getLRSpec()).andReturn(lrSpec);
		compositeReader.update(lrSpec);
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(compositeReader);
		
		manager.setLogicalReader(compositeReader);
		
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn("reader1").atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
	
		manager.setReaders(compositeReaderName, Arrays.asList(new String[] { "reader1" } ));
		Assert.assertEquals(1, lrSpec.getReaders().getReader().size());
		Assert.assertEquals("reader1", lrSpec.getReaders().getReader().get(0));

		EasyMock.verify(persistenceWriteMock);
		EasyMock.verify(persistenceRemoveMock);	
		EasyMock.verify(compositeReader);	
		EasyMock.verify(logicalReader);	
	}
	
	/**
	 * test that the readers of a composite reader can be removed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testRemoveReadersNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.removeReaders("virtualReader", null);
	}
	
	/**
	 * test that the readers of a composite reader can be removed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NonCompositeReaderException.class)
	public void testRemoveReadersNonCompositeReader() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.removeReaders(readerName, null);
		
		EasyMock.verify(logicalReader);	
	}
	
	/**
	 * test that the readers of a composite reader can be removed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testRemoveReader() throws Exception {

		WriteConfig persistenceWriteMock = EasyMock.createMock(WriteConfig.class);
		persistenceWriteMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceWriteMock);
		((LogicalReaderManagerImpl) manager).setPersistenceWriteAPI(persistenceWriteMock);
		
		RemoveConfig persistenceRemoveMock = EasyMock.createMock(RemoveConfig.class);
		persistenceRemoveMock.removeLRSpec(EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceRemoveMock);
		((LogicalReaderManagerImpl) manager).setPersistenceRemoveAPI(persistenceRemoveMock);
		
		final String compositeReaderName = "compositeReader";
		LogicalReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn(compositeReaderName).atLeastOnce();

		LRSpec lrSpec = new LRSpec();
		LRSpec.Readers readers = new LRSpec.Readers();
		readers.getReader().add("reader1");
		readers.getReader().add("reader2");
		lrSpec.setReaders(readers);
		
		EasyMock.expect(compositeReader.getLRSpec()).andReturn(lrSpec);
		compositeReader.update(lrSpec);
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(compositeReader);

		manager.setLogicalReader(compositeReader);
		
		manager.removeReaders(compositeReaderName, Arrays.asList(new String[] { "reader1" } ));
		Assert.assertEquals(1, lrSpec.getReaders().getReader().size());
		Assert.assertEquals("reader2", lrSpec.getReaders().getReader().get(0));

		EasyMock.verify(persistenceWriteMock);
		EasyMock.verify(persistenceRemoveMock);		
		EasyMock.verify(compositeReader);
	}
	
	/**
	 * test that new readers can be added to a composite reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameException.class)
	public void testAddReadersNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.addReaders("virtualReader", null);
	}
	
	/**
	 * test that new readers can be added to a composite reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NonCompositeReaderException.class)
	public void testAddReaderNonCompositeReader() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.addReaders(readerName, null);
		
		EasyMock.verify(logicalReader);
	}
	
	/**
	 * test that new readers can be added to a composite reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testAddReader() throws Exception {

		WriteConfig persistenceWriteMock = EasyMock.createMock(WriteConfig.class);
		persistenceWriteMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceWriteMock);
		((LogicalReaderManagerImpl) manager).setPersistenceWriteAPI(persistenceWriteMock);
		
		RemoveConfig persistenceRemoveMock = EasyMock.createMock(RemoveConfig.class);
		persistenceRemoveMock.removeLRSpec(EasyMock.isA(String.class));
		EasyMock.expectLastCall();
		EasyMock.replay(persistenceRemoveMock);
		((LogicalReaderManagerImpl) manager).setPersistenceRemoveAPI(persistenceRemoveMock);
		
		final String compositeReaderName = "compositeReader";
		LogicalReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn(compositeReaderName).atLeastOnce();

		LRSpec.Readers lrSpecReaders = new LRSpec.Readers();
		LRSpec lrSpec = EasyMock.createMock(LRSpec.class);
		EasyMock.expect(lrSpec.getReaders()).andReturn(lrSpecReaders).atLeastOnce();
		EasyMock.replay(lrSpec);
		
		EasyMock.expect(compositeReader.getLRSpec()).andReturn(lrSpec);
		compositeReader.update(lrSpec);
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(compositeReader);
		
		final String logicalReaderName = "logicalReader";

		manager.setLogicalReader(compositeReader);
		
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(logicalReaderName).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		
		manager.addReaders(compositeReaderName, Arrays.asList(new String[] { logicalReaderName } ));
		Assert.assertEquals(1, lrSpecReaders.getReader().size());
		Assert.assertEquals(logicalReaderName, lrSpecReaders.getReader().get(0));

		EasyMock.verify(persistenceWriteMock);
		EasyMock.verify(persistenceRemoveMock);
		EasyMock.verify(compositeReader);	
		EasyMock.verify(logicalReader);	
	}
}
