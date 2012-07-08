package org.fosstrak.ale.server.readers.test;


import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.junit.Test;

/**
 * unit test for the logical reader management.
 * 
 * @author sawielan
 *
 */
public class LogicalReaderManagerTest {
	
	/**
	 * vendor version shall never return null.
	 * @throws ImplementationExceptionResponse test failure.
	 */
	@Test
	public void testGetVendorVersion() throws ImplementationExceptionResponse {
		Assert.assertNotNull(LogicalReaderManager.getVendorVersion());
	}
	
	/**
	 * standard version shall never return null.
	 * @throws ImplementationExceptionResponse test failure.
	 */
	@Test
	public void testGetStandardVersion() throws ImplementationExceptionResponse {
		Assert.assertNotNull(LogicalReaderManager.getStandardVersion());
	}
	
	/**
	 * verify that the setting/getting of readers is correctly working.
	 * @throws ImplementationExceptionResponse test failure.
	 * @throws SecurityExceptionResponse test failure.
	 */
	@Test
	public void testReaderSettersAndGetters() throws ImplementationExceptionResponse, SecurityExceptionResponse {
		LogicalReaderHelper logicalReader1 = EasyMock.createMock(LogicalReaderHelper.class);
		EasyMock.expect(logicalReader1.getName()).andReturn("reader1").atLeastOnce();
		EasyMock.expect(logicalReader1.isStarted()).andReturn(false).atLeastOnce();
		// start is invoked at least once (as mock is always returning not-started).
		logicalReader1.start();
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(logicalReader1);
		
		LogicalReaderHelper logicalReader2 = EasyMock.createMock(LogicalReaderHelper.class);
		EasyMock.expect(logicalReader2.getName()).andReturn("reader2").atLeastOnce();
		EasyMock.expect(logicalReader2.isStarted()).andReturn(true).atLeastOnce();
		// start is invoked only once.
		logicalReader2.start();
		EasyMock.expectLastCall();
		EasyMock.replay(logicalReader2);
		
		// test setters and getters -----------------
		
		LogicalReaderManager.setLogicalReader(logicalReader1);
		LogicalReaderManager.setLogicalReader(logicalReader2);
		Assert.assertNotNull(LogicalReaderManager.getLogicalReader("reader1"));
		Assert.assertEquals("reader1", LogicalReaderManager.getLogicalReader("reader1").getName());
		Assert.assertNotNull(LogicalReaderManager.getLogicalReader("reader2"));
		Assert.assertEquals("reader2", LogicalReaderManager.getLogicalReader("reader2").getName());
		// non existing reader.
		Assert.assertNull(LogicalReaderManager.getLogicalReader("reader3"));
		
		Assert.assertEquals(2, LogicalReaderManager.getLogicalReaders().size());
		List<String> readerNames = LogicalReaderManager.getLogicalReaderNames();
		Assert.assertEquals(2, readerNames.size());
		Assert.assertTrue(readerNames.contains("reader1"));
		Assert.assertTrue(readerNames.contains("reader2"));
	}
	
	/**
	 * verify that the setting a reader twice is not allowed.
	 * @throws ImplementationExceptionResponse test OK.
	 */
	@Test(expected = ImplementationExceptionResponse.class)
	public void testSetLogicalReadersNotSetTwice() throws ImplementationExceptionResponse {
		LogicalReaderHelper logicalReader1 = EasyMock.createMock(LogicalReaderHelper.class);
		EasyMock.expect(logicalReader1.getName()).andReturn("reader1").atLeastOnce();
		EasyMock.expect(logicalReader1.isStarted()).andReturn(false).atLeastOnce();
		// start is invoked only once.
		logicalReader1.start();
		EasyMock.expectLastCall();
		EasyMock.replay(logicalReader1);
		
		LogicalReaderManager.setLogicalReader(logicalReader1);
		Assert.assertNotNull(LogicalReaderManager.getLogicalReader("reader1"));
		
		// add once more must trigger an exception
		LogicalReaderManager.setLogicalReader(logicalReader1);
	}
	
	/**
	 * tests the property setter/getters
	 * @throws ImplementationExceptionResponse test failure.
	 * @throws SecurityExceptionResponse test failure.
	 * @throws NoSuchNameExceptionResponse test failure.
	 */
	@Test
	public void testPropertyMutators() throws ImplementationExceptionResponse, NoSuchNameExceptionResponse, SecurityExceptionResponse {
		final String readerName = "testPropertyMutators";
		LogicalReaderHelper logicalReader = EasyMock.createMock(LogicalReaderHelper.class);
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
		
		LogicalReaderManager.setLogicalReader(logicalReader);
		
		String value = LogicalReaderManager.getPropertyValue(readerName, "name2");
		Assert.assertEquals("value2", value);
		Assert.assertNull(LogicalReaderManager.getPropertyValue(readerName, "unknown"));
	}

	/**
	 * test mutator on non existing reader
	 */
	@Test(expected = NoSuchNameExceptionResponse.class)
	public void testPropertyMutatorsNotExistingReader() throws Exception {
		LogicalReaderManager.setProperties("notExistingReader", null);
	}
	
	/**
	 * little helper class for the tests (giving access to some internals of the reader).
	 * @author swieland
	 *
	 */
	private abstract class LogicalReaderHelper extends LogicalReader {
		
		/**
		 * make the method public for tests.
		 */
		@Override
		public abstract boolean isStarted();
		
	}
}
