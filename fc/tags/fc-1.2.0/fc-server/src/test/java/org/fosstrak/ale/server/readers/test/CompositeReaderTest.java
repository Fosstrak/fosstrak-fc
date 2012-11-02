/**
 * 
 */
package org.fosstrak.ale.server.readers.test;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.CompositeReader;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.junit.Assert;
import org.junit.Test;

/**
 * test the composite reader.
 * @author swieland
 *
 */
public class CompositeReaderTest {
	
	/**
	 * test that a reader can be added.
	 */
	@Test
	public void testAddAndRemoveReader() {
		CompositeReader compositeReader = new CompositeReader();
		
		final String readerName = "logicalReader";
		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(readerName).atLeastOnce();
		// composite reader must add itself as an observer to the logical reader when adding.
		logicalReader.addObserver(compositeReader);
		EasyMock.expectLastCall();
		// composite reader must remove itself as an observer to the logical reader when removing.
		logicalReader.deleteObserver(compositeReader);
		EasyMock.expectLastCall();
		EasyMock.replay(logicalReader);
		
		compositeReader.addReader(logicalReader);
		Assert.assertTrue(compositeReader.containsReader(readerName));
		compositeReader.removeReader(logicalReader);
		Assert.assertFalse(compositeReader.containsReader(readerName));
		
		EasyMock.verify(logicalReader);
	}
	
	/**
	 * test the initialization method.
	 * @throws ImplementationException expected.
	 */
	@Test(expected = ImplementationException.class)
	public void testInitializeNoReaderName() throws ImplementationException {
		CompositeReader compositeReader = new CompositeReader();
		compositeReader.initialize(null, null);
	}
	
	/**
	 * test the initialization method.
	 * @throws ImplementationException expected.
	 */
	@Test(expected = ImplementationException.class)
	public void testInitializeNoSpec() throws ImplementationException {
		CompositeReader compositeReader = new CompositeReader();
		compositeReader.initialize("compositeReader", null);
	}
	
	/**
	 * test the initialization method.
	 * @throws ImplementationException expected.
	 */
	@Test(expected = ImplementationException.class)
	public void testInitializeNoProperties() throws ImplementationException {
		CompositeReader compositeReader = new CompositeReader();
		LRSpec spec = EasyMock.createMock(LRSpec.class);
		EasyMock.expect(spec.getProperties()).andReturn(null);
		EasyMock.replay(spec);
		
		compositeReader.initialize("compositeReader", spec);
	}
	
	/**
	 * test the initialization method.
	 * @throws ImplementationException failure.
	 */
	@Test
	public void testInitializeAddProperties() throws ImplementationException {
		final String propertyName = "propertyName";
		final String propertyValue = "propertyValue";
		
		LRSpec.Properties properties = new LRSpec.Properties();
		LRProperty p = EasyMock.createMock(LRProperty.class);
		EasyMock.expect(p.getName()).andReturn(propertyName);
		EasyMock.expect(p.getValue()).andReturn(propertyValue);
		EasyMock.replay(p);
		
		properties.getProperty().add(p);
		
		CompositeReader compositeReader = new CompositeReader();
		LRSpec spec = EasyMock.createMock(LRSpec.class);
		EasyMock.expect(spec.getProperties()).andReturn(properties).atLeastOnce();
		EasyMock.expect(spec.getReaders()).andReturn(new LRSpec.Readers()).atLeastOnce();
		EasyMock.replay(spec);
		
		compositeReader.initialize("compositeReader", spec);
		
		LRProperty p2 = compositeReader.getProperties().get(0);
		Assert.assertEquals(p, p2);
		
		EasyMock.verify(spec);
		EasyMock.verify(p);
	}
	
	/**
	 * test the initialization method complete with all steps.
	 * @throws ImplementationException failure.
	 */
	@Test
	public void testInitializeAddReaders() throws ImplementationException {
		final String propertyName = "propertyName";
		final String propertyValue = "propertyValue";
		
		LRSpec.Properties properties = new LRSpec.Properties();
		LRProperty p = EasyMock.createMock(LRProperty.class);
		EasyMock.expect(p.getName()).andReturn(propertyName);
		EasyMock.expect(p.getValue()).andReturn(propertyValue);
		EasyMock.replay(p);
		
		properties.getProperty().add(p);
		
		final String logicalReaderName1 = "logicalReader1";
		LRSpec.Readers readers = new LRSpec.Readers();
		readers.getReader().add(logicalReaderName1);
		
		CompositeReader compositeReader = new CompositeReader();
		LRSpec spec = EasyMock.createMock(LRSpec.class);
		EasyMock.expect(spec.getProperties()).andReturn(properties).atLeastOnce();
		EasyMock.expect(spec.getReaders()).andReturn(readers).atLeastOnce();
		EasyMock.replay(spec);

		LogicalReader logicalReader = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(logicalReader.getName()).andReturn(logicalReaderName1).atLeastOnce();
		logicalReader.addObserver(compositeReader);
		EasyMock.expectLastCall();
		logicalReader.deleteObserver(compositeReader);
		EasyMock.expectLastCall();
		EasyMock.replay(logicalReader);		
		
		LogicalReaderManager lrm = EasyMock.createMock(LogicalReaderManager.class);
		EasyMock.expect(lrm.getLogicalReader(logicalReaderName1)).andReturn(logicalReader);
		EasyMock.replay(lrm);		
		
		compositeReader.setLogicalReaderManager(lrm);
		compositeReader.initialize("compositeReader", spec);
		
		compositeReader.unregisterAsObserver();
		
		LRProperty p2 = compositeReader.getProperties().get(0);
		Assert.assertEquals(p, p2);
		
		EasyMock.verify(spec);
		EasyMock.verify(p);
		EasyMock.verify(lrm);
		EasyMock.verify(logicalReader);
	}
	
	/**
	 * test that incoming tags are notified
	 * @throws ImplementationException failure.
	 */
	@Test
	public void testUpdateFromTag() throws ImplementationException {
		Tag tag = new Tag();
		tag.setTagID("hello".getBytes());
		TestUpdateHelper reader = new TestUpdateHelper();
		reader.setName("reader");
		reader.stop();
		
		reader.update(reader, tag);
		Assert.assertFalse(reader.changed);

		reader.start();
		reader.update(reader, this);
		Assert.assertFalse(reader.changed);
		
		reader.update(reader, tag);
		Assert.assertTrue(reader.changed);
		Assert.assertEquals("hello", new String(((Tag) reader.object).getTagID()));
		Assert.assertEquals("reader", ((Tag) reader.object).getReader());
	}
	
	/**
	 * test that incoming tags are notified
	 * @throws ImplementationException failure.
	 */
	@Test
	public void testUpdateFromTags() throws ImplementationException {
		Tag tag = new Tag();
		tag.setTagID("hello".getBytes());
		List<Tag> tags = new LinkedList<Tag> ();
		tags.add(tag);
		
		TestUpdateHelper reader = new TestUpdateHelper();
		reader.setName("reader");
		reader.stop();
		reader.update(reader, tags);
		Assert.assertFalse(reader.changed);
		
		reader.start();
		reader.update(reader, this);
		Assert.assertFalse(reader.changed);
		
		reader.update(reader, tags);
		Assert.assertTrue(reader.changed);
		@SuppressWarnings("unchecked")
		List<Tag> res = (List<Tag>) reader.object;
		Assert.assertEquals(1, res.size());
		Tag resTag = res.get(0);
		Assert.assertEquals("hello", new String(resTag.getTagID()));
		Assert.assertEquals("reader", resTag.getReader());
	}
	
	/**
	 * helper class we  need for testing the notify mechanisms.
	 * @author swieland
	 *
	 */
	private class TestUpdateHelper extends CompositeReader {
		
		public Object object;
		public boolean changed;

		@Override
		public void notifyObservers(Object arg) {
			object = arg;
		}

		@Override
		public synchronized void setChanged() {
			changed = true;
		}
	}
}
