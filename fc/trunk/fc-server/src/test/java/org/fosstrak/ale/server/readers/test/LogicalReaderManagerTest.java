package org.fosstrak.ale.server.readers.test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.server.readers.CompositeReader;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.server.readers.impl.LogicalReaderManagerImpl;
import org.fosstrak.ale.server.readers.impl.type.PersistenceProvider;
import org.fosstrak.ale.server.readers.impl.type.ReaderProvider;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.InUseExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.NonCompositeReaderExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ValidationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.junit.Before;
import org.junit.Test;

/**
 * unit tests for the logical reader management.
 * 
 * @author sawielan
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
		Assert.assertNotNull(manager.getVendorVersion());
	}
	
	/**
	 * standard version shall never return null.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testGetStandardVersion() throws Exception {
		Assert.assertNotNull(manager.getStandardVersion());
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
		// start is invoked only once.
		logicalReader2.start();
		EasyMock.expectLastCall();
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
	}
	
	/**
	 * verify that the setting a reader twice is not allowed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ImplementationExceptionResponse.class)
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
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
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
		
		// start is invoked only once.
		logicalReader.start();
		EasyMock.expectLastCall();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		
		String value = manager.getPropertyValue(readerName, "name2");
		Assert.assertEquals("value2", value);
		Assert.assertNull(manager.getPropertyValue(readerName, "unknown"));
	}

	/**
	 * test property setter when illegal null input.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationExceptionResponse.class)
	public void testSetPropertyIllegalNullInput() throws Exception {
		manager.setProperties("notExistingReader", null);
	}
	
	/**
	 * test property setter on non existing reader
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
	public void testSetPropertyNotExistingReader() throws Exception {
		manager.setProperties("notExistingReader", new LinkedList<LRProperty> ());
	}	
	
	/**
	 * test property setter
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testSetProperty() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.removeLRSpec(EasyMock.isA(String.class));
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		
		final String logicalReaderName = "logicalReader";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(logicalReaderName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();

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
	}
	
	/**
	 * test that the LR specification of a reader can be queried.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
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
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		
		LRSpec lrSpec = new LRSpec();
		EasyMock.expect(logicalReader.getLRSpec()).andReturn(lrSpec);
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		LRSpec result = manager.getLRSpec(readerName);
		Assert.assertEquals(lrSpec, result);
	}
	
	/**
	 * test no unknown reader can be undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
	public void testUndefineNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.undefine("virtualReader");
	}
	
	/**
	 * test that readers in use are not undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = InUseExceptionResponse.class)
	public void testUndefineReaderInUse() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.expect(logicalReader.countObservers()).andReturn(1);
		
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
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
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(new PersistenceProvider());
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
		Assert.assertEquals(0, manager.getLogicalReaders().size());
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

		((LogicalReaderManagerImpl) manager).setPersistenceProvider(new PersistenceProvider());
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
		Assert.assertEquals(0, manager.getLogicalReaders().size());
	}	
	
	/**
	 * test that composite readers are undefined.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ImplementationExceptionResponse.class)
	public void testUndefineUnsupportedReaderType() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(true).atLeastOnce();
		EasyMock.expect(logicalReader.countObservers()).andReturn(0);
		
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.undefine(readerName);
	}	

	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationExceptionResponse.class)
	public void testDefineIllegalInputName() throws Exception {
		manager.define(null, (org.fosstrak.ale.server.readers.gen.LRSpec) null);
	}

	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationExceptionResponse.class)
	public void testDefineIllegalInputSpec() throws Exception {
		manager.define("name", (org.fosstrak.ale.server.readers.gen.LRSpec) null);
	}
	
	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationExceptionResponse.class)
	public void testDefine2IllegalInputName() throws Exception {
		manager.define(null, (LRSpec) null);
	} 
	
	/**
	 * test that define is not allowing null as input
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = ValidationExceptionResponse.class)
	public void testDefine2IllegalInputSpec() throws Exception {
		manager.define("name", (LRSpec) null);
	}
	
	/**
	 * test the definition of a reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testDefine() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		
		final BaseReader baseReader = EasyMock.createMock(BaseReader.class);
		baseReader.connectReader();
		EasyMock.expectLastCall();
		EasyMock.replay(baseReader);
		
		// need a handle onto the spec delivered for the reader creation.
		final AtomicReference<org.fosstrak.ale.xsd.ale.epcglobal.LRSpec> ref = new AtomicReference<org.fosstrak.ale.xsd.ale.epcglobal.LRSpec>(); 
		
		ReaderProvider readerProvider = EasyMock.createMock(ReaderProvider.class);
		EasyMock.expect(readerProvider.createReader(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class))).andDelegateTo(new ReaderProvider() {
			@Override
			public LogicalReader createReader(String name, org.fosstrak.ale.xsd.ale.epcglobal.LRSpec theSpec)  throws ImplementationExceptionResponse {
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
	}
	
	/**
	 * test that the readers of a composite reader can be set.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
	public void testSetReadersNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.setReaders("virtualReader", null);
	}
	
	/**
	 * test that the readers of a composite reader can be set.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NonCompositeReaderExceptionResponse.class)
	public void testSetReadersNonCompositeReader() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		
		// test on some not-existing reader
		manager.setReaders(readerName, null);
	}
	
	
	/**
	 * test that the readers of a composite reader can be set.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testSetReader() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.removeLRSpec(EasyMock.isA(String.class));
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		
		final String compositeReaderName = "compositeReader";
		LogicalReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn(compositeReaderName).atLeastOnce();
		EasyMock.expect(compositeReader.isStarted()).andReturn(false).atLeastOnce();

		LRSpec lrSpec = new LRSpec();
		LRSpec.Readers readers = new LRSpec.Readers();
		readers.getReader().add("reader2");
		lrSpec.setReaders(readers);
		
		EasyMock.expect(compositeReader.getLRSpec()).andReturn(lrSpec);
		compositeReader.update(lrSpec);
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(compositeReader);

		manager.setLogicalReader(compositeReader);
		
		manager.setReaders(compositeReaderName, Arrays.asList(new String[] { "reader1" } ));
		Assert.assertEquals(1, lrSpec.getReaders().getReader().size());
		Assert.assertEquals("reader1", lrSpec.getReaders().getReader().get(0));
	}
	
	/**
	 * test that the readers of a composite reader can be removed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
	public void testRemoveReadersNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.removeReaders("virtualReader", null);
	}
	
	/**
	 * test that the readers of a composite reader can be removed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NonCompositeReaderExceptionResponse.class)
	public void testRemoveReadersNonCompositeReader() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.removeReaders(readerName, null);
	}
	
	/**
	 * test that the readers of a composite reader can be removed.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testRemoveReader() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.removeLRSpec(EasyMock.isA(String.class));
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		
		final String compositeReaderName = "compositeReader";
		LogicalReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn(compositeReaderName).atLeastOnce();
		EasyMock.expect(compositeReader.isStarted()).andReturn(false).atLeastOnce();

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
	}
	
	/**
	 * test that new readers can be added to a composite reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
	public void testAddReadersNoSuchReader() throws Exception {
		// test on some not-existing reader
		manager.addReaders("virtualReader", null);
	}
	
	/**
	 * test that new readers can be added to a composite reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test(expected = NonCompositeReaderExceptionResponse.class)
	public void testAddReaderNonCompositeReader() throws Exception {
		final String readerName = "readerName";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		EasyMock.expect(logicalReader.isStarted()).andReturn(false).atLeastOnce();
		EasyMock.replay(logicalReader);
		
		manager.setLogicalReader(logicalReader);
		manager.addReaders(readerName, null);
	}
	
	/**
	 * test that new readers can be added to a composite reader.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testAddReader() throws Exception {
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.removeLRSpec(EasyMock.isA(String.class));
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		
		final String compositeReaderName = "compositeReader";
		LogicalReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn(compositeReaderName).atLeastOnce();
		EasyMock.expect(compositeReader.isStarted()).andReturn(false).atLeastOnce();

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
		
		manager.addReaders(compositeReaderName, Arrays.asList(new String[] { logicalReaderName } ));
		Assert.assertEquals(1, lrSpecReaders.getReader().size());
		Assert.assertEquals(logicalReaderName, lrSpecReaders.getReader().get(0));
	}
	
	/**
	 * test the initializer method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testInitializeAndContains() throws Exception {		
		String cfg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		cfg += "<LogicalReaders xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"/LogicalReaders.xsd\">";
			cfg += "<LogicalReader name=\"LogicalReader1\">";
				cfg += "<LRSpec isComposite=\"false\"";; 
					cfg += " readerType=\"org.fosstrak.ale.server.readers.llrp.LLRPAdaptor\">";
						cfg += " <LRProperty name=\"ImplClass\" value=\"org.fosstrak.ale.server.readers.llrp.LLRPAdaptor\"/>";
						cfg += " <LRProperty name=\"clientInitiated\" value=\"true\"/>";
				cfg += "</LRSpec>";
			cfg += "</LogicalReader>";
		cfg += "</LogicalReaders>";
		
		ByteArrayInputStream bis = new ByteArrayInputStream(cfg.getBytes());
		
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));		
		EasyMock.expect(persistenceMock.getInitializeInputStream(EasyMock.isA(String.class))).andReturn(bis);
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		
		final BaseReader baseReader = EasyMock.createMock(BaseReader.class);
		baseReader.connectReader();
		EasyMock.expectLastCall();
		baseReader.start();
		EasyMock.expectLastCall();
		EasyMock.replay(baseReader);
		ReaderProvider readerProvider = EasyMock.createMock(ReaderProvider.class);
		
		// need a handle onto the spec delivered for the reader creation.
		final AtomicReference<org.fosstrak.ale.xsd.ale.epcglobal.LRSpec> ref = new AtomicReference<org.fosstrak.ale.xsd.ale.epcglobal.LRSpec>(); 
		EasyMock.expect(readerProvider.createReader(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class))).andDelegateTo(new ReaderProvider() {
			@Override
			public LogicalReader createReader(String name, org.fosstrak.ale.xsd.ale.epcglobal.LRSpec theSpec)  throws ImplementationExceptionResponse {
				ref.set(theSpec);
				return baseReader;
			}
		});
		EasyMock.replay(readerProvider);
		((LogicalReaderManagerImpl) manager).setReaderProvider(readerProvider);

		Assert.assertFalse(manager.isInitialized());
		// contains initializes the Logical Reader Manager
		Assert.assertTrue(manager.contains("LogicalReader1"));
		((LogicalReaderManagerImpl) manager).initialize();
		
		Assert.assertEquals(1, manager.getLogicalReaders().size());
		
		org.fosstrak.ale.xsd.ale.epcglobal.LRSpec theNewSpec = ref.get();
		Assert.assertNotNull(theNewSpec);
		Assert.assertFalse(theNewSpec.isIsComposite());
		Assert.assertEquals(3, theNewSpec.getProperties().getProperty().size());
		Map<String, String> keyValue = new HashMap<String, String> ();
		for (LRProperty pr : theNewSpec.getProperties().getProperty()) {
			keyValue.put(pr.getName(), pr.getValue());
		}
		Assert.assertEquals("org.fosstrak.ale.server.readers.llrp.LLRPAdaptor",keyValue.get(LogicalReader.PROPERTY_READER_TYPE));
		Assert.assertEquals("org.fosstrak.ale.server.readers.llrp.LLRPAdaptor", keyValue.get("ImplClass"));
		Assert.assertEquals("true", keyValue.get("clientInitiated"));
		Assert.assertTrue(manager.isInitialized());
		Assert.assertTrue(manager.contains("LogicalReader1"));
		// assert that initialize is not called twice...
		Assert.assertEquals(1, manager.getLogicalReaders().size());
	}
	
	/**
	 * test the store to file method.
	 * @throws Exception test failure (or see what is expected by the test).
	 */
	@Test
	public void testStoreToFile() throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		PersistenceProvider persistenceMock = EasyMock.createMock(PersistenceProvider.class);
		persistenceMock.writeLRSpec(EasyMock.isA(String.class), EasyMock.isA(LRSpec.class));		
		EasyMock.expect(persistenceMock.getStreamToWhereToStoreWholeManager(EasyMock.isA(String.class))).andReturn(bout);
		EasyMock.replay(persistenceMock);
		((LogicalReaderManagerImpl) manager).setPersistenceProvider(persistenceMock);
		((LogicalReaderManagerImpl) manager).setReaderProvider(new ReaderProvider());		
		
		LRSpec lrSpec = new LRSpec();
		lrSpec.setIsComposite(true);
		LRSpec.Readers readers = new LRSpec.Readers();
		readers.getReader().add("treader1");	readers.getReader().add("treader2");
		lrSpec.setReaders(readers);
		
		final CompositeReader compositeReader = EasyMock.createMock(CompositeReader.class);
		EasyMock.expect(compositeReader.getName()).andReturn("compositeReader1").anyTimes();
		EasyMock.expect(compositeReader.getLRSpec()).andReturn(lrSpec);
		EasyMock.replay(compositeReader);
		manager.setLogicalReader(compositeReader);
		
		LRSpec lrSpec2 = new LRSpec();
		lrSpec2.setIsComposite(false);
		lrSpec2.setReaders(readers);
		
		LRProperty p0 = new LRProperty();
		p0.setName("ImplClass");
		p0.setValue("org.fosstrak.ale.server.readers.llrp.LLRPAdaptor");
		
		final BaseReader baseReader = EasyMock.createMock(BaseReader.class);
		EasyMock.expect(baseReader.getName()).andReturn("baseReader1").anyTimes();
		EasyMock.expect(baseReader.getLRSpec()).andReturn(lrSpec2);
		EasyMock.expect(baseReader.getProperties()).andReturn(Arrays.asList(new LRProperty[] { p0 } ));
		EasyMock.replay(baseReader);
		manager.setLogicalReader(baseReader);
		
		((LogicalReaderManagerImpl) manager).storeToFile(null);
		
		String cfg = bout.toString();
		
		// generated cfg is nicely formatted with newlines etc -> remove these
		cfg = cfg.replaceAll(">[\\s|\r\n|\n|\t]*<", "><").trim();
		// resulting string has some mock-specific characters contained in the implementing class -> replace them
		cfg = cfg.replaceAll("\\$\\$EnhancerByCGLIB\\$\\$.{8}", "");
		Assert.assertEquals(524, cfg.length());
	}
}
