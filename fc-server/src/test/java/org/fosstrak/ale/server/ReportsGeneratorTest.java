/*
 * Copyright (C) 2007 ETH Zurich
 * 
 * This file is part of Accada (www.accada.org).
 *
 * Accada is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 * 
 * Accada is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Accada; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.accada.ale.server;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.accada.ale.util.DeserializerUtil;
import org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException;
import org.accada.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException;
import org.accada.ale.xsd.ale.epcglobal.ECReports;
import org.accada.ale.xsd.ale.epcglobal.ECSpec;
import org.apache.log4j.PropertyConfigurator;

import util.ECElementsUtils;

/**
 * @author regli
 */
public class ReportsGeneratorTest extends TestCase {
	
	private static final String SPEC_NAME = "TestSpec";
	private static final String HTTP_NOTIFICATION_URI = "http://localhost:1234/dir/subdir";
	private static final String TCP_NOTIFICATION_URI = "tcp://localhost:1234";
	private static final String FILE_NOTIFICATION_URI = "file:///dir/subdir/file";
	
	private static final String FILE_NOTIFICATION_URL_PREFIX = "file:///";
	private static final String NOTIFICATION_FILE_PREFIX = "ReportsGeneratorTest_";
	private static final String NOTIFICATION_FILE_SUFFIX = "tmp";
		
	private ECSpec spec;

	protected void setUp() throws Exception {
		
		super.setUp();
		
		// configure Logger with properties file
		URL url = this.getClass().getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		
		spec = ECElementsUtils.createECSpec();
		
	}
	
	public void testCreateReportsGenerator() throws Exception {
		
		new ReportsGenerator(SPEC_NAME, spec);
		
	}

	public void testGetSpec() throws Exception {

		assertEquals(spec, new ReportsGenerator(SPEC_NAME, spec).getSpec());
		
	}
	
	public void testSubscribeWithHttpNotificationURI() throws Exception {
		
		new ReportsGenerator(SPEC_NAME, spec).subscribe(HTTP_NOTIFICATION_URI);
		
	}
	
	public void testSubscribeWithTcpNotificationURI() throws Exception {
		
		new ReportsGenerator(SPEC_NAME, spec).subscribe(TCP_NOTIFICATION_URI);
		
	}
	
	public void testSubscribeWithFileNotificationURI() throws Exception {
		
		new ReportsGenerator(SPEC_NAME, spec).subscribe(FILE_NOTIFICATION_URI);
		
	}
	
	public void testSubscribeWithInvalidURI() throws Exception {
		
		try {
			new ReportsGenerator(SPEC_NAME, spec).subscribe("InvalidNotificationURI");
		} catch (InvalidURIException e) {
			assertEquals("A valid URI must have one of the following forms: (http://host[:port]/remainder-of-URL | tcp://host:port | file://[host]/path)", e.getReason());
			return;
		}
		
		fail("Should throw new InvalidURIException. Because 'InvalidNotificationURI' is not a valid URI.");
		
	}
	
	public void testSubscribeWithDuplicateSubscription() throws Exception {
	
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.subscribe(HTTP_NOTIFICATION_URI);
		
		try {
			reportsGenerator.subscribe(HTTP_NOTIFICATION_URI);
		} catch (DuplicateSubscriptionException e) {
			return;
		}
		
		fail("Should throw new DuplicateSubscriptionException. Because the same notification uri is already subscribed " +
				"to this ReportsGenerator.");
		
	}
	
	public void testUnsubscribe() throws Exception {
		
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.subscribe(HTTP_NOTIFICATION_URI);
		
		reportsGenerator.unsubscribe(HTTP_NOTIFICATION_URI);
			
	}
	
	public void testUnsubscribeWithUnknownSubscriber() throws Exception {
		
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.subscribe(HTTP_NOTIFICATION_URI);
		
		try {
			reportsGenerator.unsubscribe(TCP_NOTIFICATION_URI);
		} catch (NoSuchSubscriberException e) {
			return;
		}
		
		fail("Should throw new NoSuchSubscriberException. Because this subscriber is not subscribed.");
		
	}
	
	public void testUnsubscribeWithInvalidURI() throws Exception {
		
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.subscribe(HTTP_NOTIFICATION_URI);
		
		try {
			reportsGenerator.unsubscribe("InvalidNotificationURI");
		} catch (InvalidURIException e) {
			assertEquals("A valid URI must have one of the following forms: (http://host[:port]/remainder-of-URL | tcp://host:port | file://[host]/path)", e.getReason());
			return;
		}
		
		fail("Should throw new InvalidURIException. Because 'InvalidNotificationURI' is not a valid URI.");
		
	}
	
	public void testGetSubscribers() throws Exception {
	
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.subscribe(HTTP_NOTIFICATION_URI);
		reportsGenerator.subscribe(TCP_NOTIFICATION_URI);
		reportsGenerator.subscribe(FILE_NOTIFICATION_URI);
		
		List<String> subscribers = reportsGenerator.getSubscribers();
		
		assertEquals(3, subscribers.size());
		assertTrue(subscribers.contains(HTTP_NOTIFICATION_URI));
		assertTrue(subscribers.contains(TCP_NOTIFICATION_URI));
		assertTrue(subscribers.contains(FILE_NOTIFICATION_URI));
		
	}
	
	public void testNotifySubscribers() throws Exception {

		File tmpFile = File.createTempFile(NOTIFICATION_FILE_PREFIX, NOTIFICATION_FILE_SUFFIX);
		
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.subscribe(FILE_NOTIFICATION_URL_PREFIX + tmpFile.getAbsolutePath());
		
		ECReports reports = ECElementsUtils.createECReports();
		reportsGenerator.notifySubscribers(reports);
		
		ECReports resultReports = DeserializerUtil.deserializeECReports(new FileInputStream(tmpFile));
		
		ECElementsUtils.assertEquals(reports, resultReports);
		
	}
	
	public void testPoll() throws Exception {
		
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		reportsGenerator.poll();
		
	}
	
	public void testGetName() throws Exception {
		
		ReportsGenerator reportsGenerator = new ReportsGenerator(SPEC_NAME, spec);
		
		assertEquals(SPEC_NAME, reportsGenerator.getName());
		
	}
	
}