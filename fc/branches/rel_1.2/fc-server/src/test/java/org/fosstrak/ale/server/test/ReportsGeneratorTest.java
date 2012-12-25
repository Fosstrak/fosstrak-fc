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

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.fosstrak.ale.exception.DuplicateSubscriptionException;
import org.fosstrak.ale.exception.ECSpecValidationException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.exception.NoSuchSubscriberException;
import org.fosstrak.ale.server.EventCycle;
import org.fosstrak.ale.server.ReportsGenerator;
import org.fosstrak.ale.server.ReportsGeneratorState;
import org.fosstrak.ale.server.impl.ReportsGeneratorImpl;
import org.fosstrak.ale.server.util.ECReportsHelper;
import org.fosstrak.ale.server.util.ECSpecValidator;
import org.fosstrak.ale.server.util.test.ECReportsHelperTest;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import util.ECElementsUtils;

import com.rits.cloning.Cloner;

/**
 * test the reports generator.
 * 
 * TODO: test the thread behavior of the generator and the reports notification...
 * 
 * @author swieland
 *
 */
@Ignore
public class ReportsGeneratorTest {
	
	/** logger */
	private static final Logger LOG = Logger.getLogger(ReportsGeneratorTest.class);

	/**
	 * ec spec with report spec for group 'null' and requesting report if report does not contain tags. 
	 */
	public final static String ECSPEC_CURRENT_REPORTSPECNULL_EMPTYREPORT = 	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:ECSpec xmlns:ns2=\"urn:epcglobal:ale:xsd:1\"><logicalReaders><logicalReader>LogicalReader1</logicalReader></logicalReaders><boundarySpec><repeatPeriod unit=\"MS\">10000</repeatPeriod><duration unit=\"MS\">9500</duration><stableSetInterval unit=\"MS\">0</stableSetInterval></boundarySpec><reportSpecs><reportSpec reportIfEmpty=\"true\"><reportSet set=\"CURRENT\"/><output includeRawHex=\"true\" includeRawDecimal=\"true\" includeEPC=\"true\" includeTag=\"true\"/></reportSpec></reportSpecs></ns2:ECSpec>";
	
	/**
	 * ec spec with report spec for group 'null' and request report only on change.
	 */
	public final static String ECSPEC_CURRENT_REPORTSPECNULL_ONLYONCHANGE = 	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:ECSpec xmlns:ns2=\"urn:epcglobal:ale:xsd:1\"><logicalReaders><logicalReader>LogicalReader1</logicalReader></logicalReaders><boundarySpec><repeatPeriod unit=\"MS\">10000</repeatPeriod><duration unit=\"MS\">9500</duration><stableSetInterval unit=\"MS\">0</stableSetInterval></boundarySpec><reportSpecs><reportSpec reportOnlyOnChange=\"true\"><reportSet set=\"CURRENT\"/><output includeRawHex=\"true\" includeRawDecimal=\"true\" includeEPC=\"true\" includeTag=\"true\"/></reportSpec></reportSpecs></ns2:ECSpec>";
	
	/**
	 * ec spec with report spec for group 'null' and requesting no report if report does not contain tags. 
	 */
	public static final String ECSPEC_CURRENT_REPORTSPECNULL_NOEMPTYREPORT = 	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:ECSpec xmlns:ns2=\"urn:epcglobal:ale:xsd:1\"><logicalReaders><logicalReader>LogicalReader1</logicalReader></logicalReaders><boundarySpec><repeatPeriod unit=\"MS\">10000</repeatPeriod><duration unit=\"MS\">9500</duration><stableSetInterval unit=\"MS\">0</stableSetInterval></boundarySpec><reportSpecs><reportSpec><reportSet set=\"CURRENT\"/><output includeRawHex=\"true\" includeRawDecimal=\"true\" includeEPC=\"true\" includeTag=\"true\"/></reportSpec></reportSpecs></ns2:ECSpec>";
		
	/**
	 * obtain the EC Spec from the given input string.
	 * @param specString the specification.
	 * @return the ECSpec.
	 * @throws Exception error
	 */
	public static ECSpec getECSpec(String specString) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(specString.getBytes());	
		ECSpec ecReports = DeserializerUtil.deserializeECSpec(bis);
		bis.close();
		return ecReports;
	}
	
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
		new ReportsGeneratorImpl("theName", spec, validator, new ECReportsHelper());
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
		new ReportsGeneratorImpl("theName", spec, validator, new ECReportsHelper());
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
		ReportsGenerator generator = new ReportsGeneratorImpl("theName", spec, validator, new ECReportsHelper());
		Assert.assertEquals(spec, generator.getSpec());
		Assert.assertEquals(ReportsGeneratorState.UNREQUESTED, ((ReportsGeneratorImpl) generator).getState());
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
		ReportsGenerator generator = new ReportsGeneratorImpl("theName", spec, validator, new ECReportsHelper());
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
		ReportsGenerator generator = new ReportsGeneratorImpl("theName", spec, validator, new ECReportsHelper());
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
		ReportsGenerator generator = new ReportsGeneratorImpl("theName", spec, validator, new ECReportsHelper());
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
		
		Assert.assertEquals(ReportsGeneratorState.UNREQUESTED, ((ReportsGeneratorImpl) generator).getState());
		generator.subscribe(uri);
		Assert.assertEquals(1, generator.getSubscribers().size());
		Assert.assertEquals(ReportsGeneratorState.REQUESTED, ((ReportsGeneratorImpl) generator).getState());
		generator.subscribe(uri2);
		
		Assert.assertEquals(2, generator.getSubscribers().size());
		List<String> subscribers = generator.getSubscribers();
		for (String str : new String[] {uri, uri2 }) {
			Assert.assertTrue(subscribers.contains(str));
		}
		
		generator.unsubscribe(uri);
		Assert.assertEquals(1, generator.getSubscribers().size());
		Assert.assertEquals(ReportsGeneratorState.REQUESTED, ((ReportsGeneratorImpl) generator).getState());
		generator.unsubscribe(uri2);
		Assert.assertEquals(0, generator.getSubscribers().size());
		Assert.assertEquals(ReportsGeneratorState.UNREQUESTED, ((ReportsGeneratorImpl) generator).getState());	
		
		EasyMock.verify(validator);
	}
	
	/**
	 * test the notifications for poll and immediate - they should always receive all the reports (even empty).
	 * @throws Exception test failure. 
	 */
	@Test
	public void testNotifyPollers() throws Exception {
		ECSpec spec = ECElementsUtils.createECSpec();
		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		
		final String reportSpecName = "TestReport";
		ECReportSpec reportSpec = EasyMock.createMock(ECReportSpec.class);
		EasyMock.expect(reportSpec.isReportOnlyOnChange()).andReturn(false);
		EasyMock.replay(reportSpec);
		
		Map<String, ECReport> lastReports = new HashMap<String, ECReport> ();
		EventCycle ec = EasyMock.createMock(EventCycle.class);
		EasyMock.expect(ec.getReportSpecByName(reportSpecName)).andReturn(reportSpec).atLeastOnce();
		EasyMock.expect(ec.getLastReports()).andReturn(lastReports).atLeastOnce();
		EasyMock.replay(ec);
		
		NonRunnablePollableReportsGenerator generator = new NonRunnablePollableReportsGenerator("theSpec", spec, validator);
		
		ECReports reports = ECElementsUtils.createECReports();
		
		generator.notifySubscribers(reports, ec);
		ECReports result = generator.getPollReport();
		
		Assert.assertEquals(reports.getALEID(), result.getALEID());
		Assert.assertEquals(reports.getReports().getReport().get(0).getReportName(), result.getReports().getReport().get(0).getReportName());
		
		EasyMock.verify(validator);
		EasyMock.verify(ec);
		EasyMock.verify(reportSpec);
	}
	
	@Test
	public void testNotifyAllwaysButNotEmpty() throws Exception {
		ECReports ecReportsNotEmpty = ECReportsHelperTest.getECReports(ECReportsHelperTest.ECREPORTS_NULLGROUP_TWOTAGS);
		ECReports ecReportsEmpty = ECReportsHelperTest.getECReports(ECReportsHelperTest.ECREPORTS_NULLGROUP_NOTAGSINGROUP);
		
		ECSpec spec = getECSpec(ECSPEC_CURRENT_REPORTSPECNULL_NOEMPTYREPORT);

		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		
		Map<String, ECReport> lastReports = new HashMap<String, ECReport> ();
		EventCycle ec = EasyMock.createMock(EventCycle.class);
		EasyMock.expect(ec.getReportSpecByName(null)).andReturn(spec.getReportSpecs().getReportSpec().get(0)).atLeastOnce();
		EasyMock.expect(ec.getLastReports()).andReturn(lastReports).atLeastOnce();
		EasyMock.replay(ec);
		
		NonRunnableNotifyableReportsGenerator generator = new NonRunnableNotifyableReportsGenerator("current", spec, validator);				
		// first run
		generator.notifySubscribers(new Cloner().deepClone(ecReportsNotEmpty), ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), true);
		generator.setNotifiedReportsToNull();
		
		// second run
		generator.notifySubscribers(new Cloner().deepClone(ecReportsNotEmpty), ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), true);
		generator.setNotifiedReportsToNull();
		
		// empty report (must not be delivered)
		generator.notifySubscribers(new Cloner().deepClone(ecReportsEmpty), ec);
		Assert.assertNull(generator.getNotifiedReports());		
		
		EasyMock.verify(validator);
		EasyMock.verify(ec);
	}

	/**
	 * the report spec defines to return even empty reports.
	 * @throws Exception
	 */
	@Test
	public void testNotifyReportIfEmpty() throws Exception {
		ECReports ecReportsNotEmpty = ECReportsHelperTest.getECReports(ECReportsHelperTest.ECREPORTS_NULLGROUP_TWOTAGS);
		ECReports ecReportsEmpty = ECReportsHelperTest.getECReports(ECReportsHelperTest.ECREPORTS_NULLGROUP_NOTAGSINGROUP);
		
		ECSpec spec = getECSpec(ECSPEC_CURRENT_REPORTSPECNULL_EMPTYREPORT);

		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		
		Map<String, ECReport> lastReports = new HashMap<String, ECReport> ();
		EventCycle ec = EasyMock.createMock(EventCycle.class);
		EasyMock.expect(ec.getReportSpecByName(null)).andReturn(spec.getReportSpecs().getReportSpec().get(0)).atLeastOnce();
		EasyMock.expect(ec.getLastReports()).andReturn(lastReports).atLeastOnce();
		EasyMock.replay(ec);
		
		NonRunnableNotifyableReportsGenerator generator = new NonRunnableNotifyableReportsGenerator("current", spec, validator);				
		// first run
		generator.notifySubscribers(new Cloner().deepClone(ecReportsNotEmpty), ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), true);
		generator.setNotifiedReportsToNull();
		
		// second run
		generator.notifySubscribers(new Cloner().deepClone(ecReportsNotEmpty), ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), true);
		generator.setNotifiedReportsToNull();
		
		// empty report (must be delivered)
		generator.notifySubscribers(new Cloner().deepClone(ecReportsEmpty), ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), false);
		
		EasyMock.verify(validator);
		EasyMock.verify(ec);
	}

	/**
	 * the report spec defines to return even empty reports.
	 * @throws Exception test failure.
	 */
	@Test
	public void testNotifyReportOnlyOnChange() throws Exception {
		ECReports ecReportsNotEmpty = ECReportsHelperTest.getECReports(ECReportsHelperTest.ECREPORTS_NULLGROUP_TWOTAGS);

		ECSpec spec = getECSpec(ECSPEC_CURRENT_REPORTSPECNULL_ONLYONCHANGE);

		ECSpecValidator validator = EasyMock.createMock(ECSpecValidator.class);
		validator.validateSpec(spec);
		EasyMock.expectLastCall();
		EasyMock.replay(validator);
		
		Map<String, ECReport> lastReports = new HashMap<String, ECReport> ();
		EventCycle ec = EasyMock.createMock(EventCycle.class);
		EasyMock.expect(ec.getReportSpecByName(null)).andReturn(spec.getReportSpecs().getReportSpec().get(0)).atLeastOnce();
		EasyMock.expect(ec.getLastReports()).andReturn(lastReports).atLeastOnce();
		EasyMock.replay(ec);
		
		NonRunnableNotifyableReportsGenerator generator = new NonRunnableNotifyableReportsGenerator("current", spec, validator);				
		// first run
		generator.notifySubscribers(new Cloner().deepClone(ecReportsNotEmpty), ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), true);
		generator.setNotifiedReportsToNull();
		
		// second run
		generator.notifySubscribers(new Cloner().deepClone(ecReportsNotEmpty), ec);
		Assert.assertNull(generator.getNotifiedReports());
		generator.setNotifiedReportsToNull();
		
		// remove one tag and thus the report must be delivered again.
		ECReports r2 = new Cloner().deepClone(ecReportsNotEmpty);
		r2.getReports().getReport().get(0).getGroup().get(0).getGroupList().getMember().remove(0);
		generator.notifySubscribers(r2, ec);
		Assert.assertNotNull(generator.getNotifiedReports());
		assertTagsContained(generator.getNotifiedReports(), true);
		
		EasyMock.verify(validator);
		EasyMock.verify(ec);
	}
	
	/**
	 * verify that either tags are contained or not at all.
	 * @param notifiedReports the reports to verify.
	 * @param b if true, then tags must be contained, if false then tags shall not be contained.
	 */
	private void assertTagsContained(ECReports notifiedReports, boolean b) {
		for (ECReport ecReport : notifiedReports.getReports().getReport()) {
			for (ECReportGroup group : ecReport.getGroup()) {
				if (b) {
					// must contain tags
					Assert.assertTrue(group.getGroupList().getMember().size() > 0);
				} else {
					// shall not contain tags
					Assert.assertTrue(group.getGroupList().getMember().size() == 0);
				}
			}
		}
	}
	
	/**
	 * little helper class overriding the thread parts of the reports generator -> allows the testing.
	 * @author swieland
	 *
	 */
	private class NonRunnableReportsGenerator extends ReportsGeneratorImpl {
		
		public NonRunnableReportsGenerator(String name, ECSpec spec, ECSpecValidator validator)	throws ECSpecValidationException, ImplementationException {
			super(name, spec, validator, new ECReportsHelper());
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
	
	/**
	 * helper class for tests
	 * @author swieland
	 *
	 */
	private class NonRunnableNotifyableReportsGenerator extends NonRunnableReportsGenerator {
		
		private ECReports notifiedReports;

		public NonRunnableNotifyableReportsGenerator(String name, ECSpec spec, ECSpecValidator validator)	throws ECSpecValidationException, ImplementationException {
			super(name, spec, validator);
		}
		
		@Override
		protected void notifySubscribersWithFilteredReports(ECReports reports) {
			notifiedReports = reports;
		}
		
		public ECReports getNotifiedReports() {
			return notifiedReports;
		}
		
		public void setNotifiedReportsToNull() {
			notifiedReports = null;
		}
		
	}
	
	/**
	 * little helper class overriding the thread parts and polling of the reports generator -> allows the testing.
	 * @author swieland
	 *
	 */
	private class NonRunnablePollableReportsGenerator extends ReportsGeneratorImpl {
		
		public NonRunnablePollableReportsGenerator(String name, ECSpec spec, ECSpecValidator validator)	throws ECSpecValidationException, ImplementationException {
			super(name, spec, validator, new ECReportsHelper());
		}

		@Override
		public void start() {
			LOG.debug("Mock start");
		}

		@Override
		public void stop() {
			LOG.debug("Mock stop");
		}

		@Override
		public boolean isPolling() {
			return true;
		}

		@Override
		public ECReports getPollReport() {
			return super.getPollReport();
		}
		
	}
}
