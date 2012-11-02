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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.server.EventCycle;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.impl.EventCycleImpl;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.tdt.TDTEngine;
import org.junit.Test;

/**
 * small test suite in order to test a minimum of the eventcycles behavior.<br/>
 * basically this means:<br/>
 * <ol>
 * <li>start an event cycle</li>
 * <li>deliver some tags into the cycle</li>
 * <li>wait for the cycle to go through and obtain the reports</li>
 * <li>check the report contains the given tags</li>
 * </ol>
 * @author swieland
 *
 */
public class EventCycleTest {

	
	private final String TAG1_BINARY = "001100000110100011100101110101100011000011001101000011110010100100011011111001011110100011011100";
	private final String TAG1_PURE_URI = "urn:epc:tag:sgtin-96:3.3856019661.060.176561711324";	
	
	private final String TAG2_BINARY = "001100000010001110010110110100010010101001000111010100001001010010100000100000001010110100111011";
	private final String TAG2_PURE_URI = "urn:epc:tag:sgtin-96:1.986572296660.2.88592133435";
	
	private final String TAG3_BINARY = "001100000011100110110100111011000100110010011100001110000111001000001011010101011110101100111111";
	private final String TAG3_PURE_URI = "urn:epc:tag:sgtin-96:1.447409.3305697.214938544959";
	
	@Test
	public void testPrintHex() throws Exception {		
		TDTEngine tdt = new TDTEngine();
		Map<String, String> extraparms = new HashMap<String, String> ();
		extraparms.put("taglength", "96");
		extraparms.put("filter", "3");
		extraparms.put("gs1companyprefixlength", "7");
		
		//String hex = tdt.convert(line, extraparms, LevelTypeList.TAG_ENCODING);
		System.out.println(tdt.bin2hex(TAG1_BINARY));
		System.out.println(tdt.bin2hex(TAG2_BINARY));
		System.out.println(tdt.bin2hex(TAG3_BINARY));
	}
	
	
	@Test
	public void testCycleWithOneRound() throws Exception {
		ECSpec ecspec = DeserializerUtil.deserializeECSpec(EventCycleTest.class.getResourceAsStream("/ecspecs/eventCycle-testOneRound.xml"));
		
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
		
		// need a handle onto the spec delivered for the reader creation.
		final AtomicReference<ECReports> ref = new AtomicReference<ECReports>(); 
		EasyMock.expectLastCall().andDelegateTo(new ReportsGenerator() {
			@Override
			public void unsubscribe(String notificationURI) throws NoSuchSubscriberException, InvalidURIException { }
			@Override
			public void subscribe(String notificationURI) throws DuplicateSubscriptionException, InvalidURIException { }		
			@Override
			public void poll() { }
			@Override
			public void notifySubscribers(ECReports reports, EventCycle ec) {
				// store the reports for later test analysis
				ref.set(reports);
			}
			@Override
			public List<String> getSubscribers() { return null; }
			@Override
			public boolean isStateRequested() { return true; }
			@Override
			public ECSpec getSpec() { return null; }			
			@Override
			public ECReports getPollReports() { return null; }			
			@Override
			public String getName() { return null; }
			@Override
			public void setStateRequested() { }
			@Override
			public void setStateUnRequested() { }
			@Override
			public boolean isStateUnRequested() { return false;	}
		});
		
		EasyMock.replay(reportsGenerator);
		
		final EventCycle cycle = new EventCycleImpl(reportsGenerator, manager);
		final Tag t1 = new Tag();
		t1.setTagAsBinary(TAG1_BINARY);
		t1.setTagIDAsPureURI(TAG1_PURE_URI);
		final Tag t2 = new Tag();
		t2.setTagAsBinary(TAG2_BINARY);
		t2.setTagIDAsPureURI(TAG2_PURE_URI);
		final Tag t3 = new Tag();
		t3.setTagAsBinary(TAG3_BINARY);
		t3.setTagIDAsPureURI(TAG3_PURE_URI);
		
		final List<Tag> tags = new LinkedList<Tag> ();
		tags.add(t2);
		tags.add(t3);
		
		Assert.assertFalse(((EventCycleImpl) cycle).isRoundOver());
		
		Thread sender = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					while (true) {
						cycle.update(null, t1);
						cycle.update(null, tags);
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					System.out.println("got interrupted - quitting.");
				}
			}
			
		});
		sender.start();
		
		// execute the eventcycle once and check the result...
		cycle.launch();
		
		Thread.sleep(2000L);
		sender.interrupt();

		EasyMock.verify(lr1);
		EasyMock.verify(manager);
		EasyMock.verify(reportsGenerator);
		
		ECReports reports = ref.get();
		Assert.assertNotNull(reports);
		Assert.assertEquals("DURATION", reports.getTerminationCondition());
		List<ECReport> ecReports = reports.getReports().getReport();
		Assert.assertNotNull(ecReports);
		Assert.assertEquals(1, ecReports.size());
		ECReport report = ecReports.get(0);
		Assert.assertNotNull(report);
		List<ECReportGroupListMember> gmember = report.getGroup().get(0).getGroupList().getMember();
		Assert.assertEquals(3, gmember.size());
		boolean t1Contained = false;
		boolean t2Contained = false;
		boolean t3Contained = false;
		for (ECReportGroupListMember member : gmember) {
			if (TAG1_PURE_URI.equalsIgnoreCase(member.getTag().getValue())) {
				t1Contained = true;
			}
			if (TAG2_PURE_URI.equalsIgnoreCase(member.getTag().getValue())) {
				t2Contained = true;
			}
			if (TAG3_PURE_URI.equalsIgnoreCase(member.getTag().getValue())) {
				t3Contained = true;
			}
		}
		Assert.assertTrue(t1Contained);
		Assert.assertTrue(t2Contained);
		Assert.assertTrue(t3Contained);
		
		Assert.assertNotNull(cycle.getLastReports());

		Assert.assertTrue(((EventCycleImpl) cycle).isRoundOver());
		Assert.assertEquals(1, cycle.getRounds());
	}
}
