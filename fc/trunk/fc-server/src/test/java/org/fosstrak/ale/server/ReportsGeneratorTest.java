/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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