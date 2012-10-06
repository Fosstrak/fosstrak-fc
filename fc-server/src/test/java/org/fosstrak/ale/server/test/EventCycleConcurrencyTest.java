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

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.fosstrak.ale.server.EventCycle;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.server.readers.test.TestAdaptor;
import org.fosstrak.ale.server.util.test.ECSpecValidatorTest;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.junit.Ignore;
import org.junit.Test;

/**
 * test some concurrency issues of an event cycle. please note that this is a very very basic and stupid brute force test... may need to enhance it for future use.
 * <h1>this is an integration test for now - do not run it as a standard unit test!!!</h1>
 * @author swieland
 *
 */
@Ignore
public class EventCycleConcurrencyTest {

	/** logger. */
	private static final Logger LOG = Logger.getLogger(EventCycleConcurrencyTest.class);

	private Tag[] tagsAsArray;
	private int tagSize;
	
	/**
	 * setup: 
	 * <ul>
	 * <li>one reports generator</li>
	 * <li>one eventcycle</li>
	 * <li>N receivers</li>
	 * <li>M senders delivering tags on random intervals</li>
	 * </ul>
	 * then let the whole setup run for a while at full speed. fail upon synchronization issues.
	 * @throws Exception
	 */
	@Test
	public void testConcurrency() throws Exception {
		loadTags();
		
		ECSpec ecspec = DeserializerUtil.deserializeECSpec(ECSpecValidatorTest.class.getResourceAsStream("/ecspecs/ecSpecForConcurrencyTests.xml"));
		
		final String logicalReaderName1 = "LogicalReader1";
		LogicalReader lr1 = EasyMock.createMock(LogicalReader.class);
		EasyMock.expect(lr1.getName()).andReturn(logicalReaderName1).anyTimes();
		lr1.addObserver(EasyMock.isA(EventCycle.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(lr1);
		
		LogicalReaderManager manager = EasyMock.createMock(LogicalReaderManager.class);
		EasyMock.expect(manager.contains(logicalReaderName1)).andReturn(true).anyTimes();
		EasyMock.expect(manager.getLogicalReader(logicalReaderName1)).andReturn(lr1).anyTimes();
		EasyMock.replay(manager);
		
		ReportsGenerator reportsGenerator = EasyMock.createMock(ReportsGenerator.class);
		EasyMock.expect(reportsGenerator.getName()).andReturn("generator").anyTimes();
		EasyMock.expect(reportsGenerator.getSpec()).andReturn(ecspec).anyTimes();
		reportsGenerator.notifySubscribers(EasyMock.isA(ECReports.class), EasyMock.isA(EventCycle.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(reportsGenerator);
		
		EventCycle cycle = new EventCycle(reportsGenerator, manager);
		// execute the event cycle scheduler (invoking indirectly the ec processing)
		Thread launcher = new Thread(createEventCycleRescheduler(cycle));
		launcher.start();
		
		List<Thread> producers = new LinkedList<Thread> ();
		for (int i=0; i<50; i++) {
			Thread producer = new Thread(addProducer(cycle, i));
			producer.start();
			producers.add(producer);
		}
		
		// now wait... :-)
		Thread.sleep(120000L);
		launcher.interrupt();
		for (Thread producer : producers) {
			producer.interrupt();
		}
		
		EasyMock.verify(manager);
		EasyMock.verify(reportsGenerator);
		EasyMock.verify(lr1);
	}
	
	private void loadTags() throws Exception {
		TestAdaptor ta = new TestAdaptor();
		ta.loadTags();
		tagsAsArray = ta.getTags();
		tagSize = tagsAsArray.length;
	}

	private Runnable addProducer(final EventCycle ec, final long delay) {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(delay + 1);
						Tag tag = randomTag();
						ec.update(null, tag);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};
	}

	private Tag randomTag() {
		int pos = ((int) (Math.random() * tagSize * 1000))%tagSize;
		return tagsAsArray[pos];
	}

	private Runnable createEventCycleRescheduler(final EventCycle ec) {
		return new Runnable() {

			@Override
			public void run() {
				try {
					ec.launch();
					while (true) {
						Thread.sleep(307L);
						if (ec.isRoundOver()) {
							LOG.debug("reschedule EventCycle.");
							ec.launch();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
	}
}
