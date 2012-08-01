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

package org.fosstrak.ale.server.util.test;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.server.util.ECReportsHelper;
import org.fosstrak.ale.util.DeserializerUtil;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReport;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroup;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.fosstrak.ale.xsd.epcglobal.EPC;
import org.junit.Test;

/**
 * test the reports helper.
 * 
 * @author swieland
 *
 */
public class ECReportsHelperTest {
	
	/**
	 * ec reports with the group with name 'null' and with tags contained in it.
	 */
	public static final String ECREPORTS_NULLGROUP_TWOTAGS = 	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:ECReports terminationCondition=\"DURATION\" totalMilliseconds=\"9500\" ALEID=\"ETHZ-ALE1215934431\" date=\"2012-07-28T17:36:40.599+02:00\" specName=\"current\" xmlns:ns2=\"urn:epcglobal:ale:xsd:1\"><reports><report><group><groupList><member><epc>urn:epc:id:sgtin:138650.3276101.140295059055</epc><tag>urn:epc:tag:sgtin-96:2.138650.3276101.140295059055</tag><rawHex>urn:epc:raw:96.x305887668C7F5160AA3CB66F</rawHex><rawDecimal>urn:epc:raw:96.14962305354717633549608728175</rawDecimal></member><member><epc>urn:epc:id:sgtin:747136.3229748.161154676025</epc><tag>urn:epc:tag:sgtin-96:3.747136.3229748.161154676025</tag><rawHex>urn:epc:raw:96.x307AD9A00C520D2585913539</rawHex><rawDecimal>urn:epc:raw:96.15003797127311169095994520889</rawDecimal></member></groupList></group></report></reports></ns2:ECReports>";

	/**
	 * ec reports with the group with name 'null' and with only one tag contained in it.
	 */
	public static final String ECREPORTS_NULLGROUP_ONETAG = 	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:ECReports terminationCondition=\"DURATION\" totalMilliseconds=\"9500\" ALEID=\"ETHZ-ALE1215934431\" date=\"2012-07-28T17:36:40.599+02:00\" specName=\"current\" xmlns:ns2=\"urn:epcglobal:ale:xsd:1\"><reports><report><group><groupList><member><epc>urn:epc:id:sgtin:747136.3229748.161154676025</epc><tag>urn:epc:tag:sgtin-96:3.747136.3229748.161154676025</tag><rawHex>urn:epc:raw:96.x307AD9A00C520D2585913539</rawHex><rawDecimal>urn:epc:raw:96.15003797127311169095994520889</rawDecimal></member></groupList></group></report></reports></ns2:ECReports>";
	
	/**
	 * ec reports with the group name 'null' and with no tags contained.
	 */
	public static final String ECREPORTS_NULLGROUP_NOTAGSINGROUP = 	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:ECReports terminationCondition=\"DURATION\" totalMilliseconds=\"9501\" ALEID=\"ETHZ-ALE1215934431\" date=\"2012-07-28T17:41:44.097+02:00\" specName=\"current\" xmlns:ns2=\"urn:epcglobal:ale:xsd:1\"><reports><report><group><groupList></groupList></group></report></reports></ns2:ECReports>";

	/**
	 * deserialize an EC Reports structure from a string.
	 * @param reportString the spec.
	 * @return the reports.
	 * @throws Exception error.
	 */
	public static ECReports getECReports(String reportString) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(reportString.getBytes());	
		ECReports ecReports = DeserializerUtil.deserializeECReports(bis);
		bis.close();
		return ecReports;
	}
	
	private boolean invokeHelper(ECReportsHelper helper, ECReports report1, ECReports report2, ECReportSpec spec) {
		return helper.areReportsEqual(
				spec, 
				(report1 == null) ? null : report1.getReports().getReport().get(0), 
				(report2 == null) ? null : report2.getReports().getReport().get(0));
	}
	
	private boolean invokeHelper(ECReportsHelper helper, ECReport report1, ECReport report2, ECReportSpec spec) {
		return helper.areReportsEqual(
				spec, 
				(report1 == null) ? null : report1, 
				(report2 == null) ? null : report2);
	}
	
	/**
	 * test equal reports.
	 * @throws Exception test failure.
	 */
	@Test
	public void testEqualWithTags() throws Exception {
		ECReports reportTwoTags = getECReports(ECREPORTS_NULLGROUP_TWOTAGS);
		
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeEPC()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeTag()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeRawHex()).andReturn(true);
		EasyMock.replay(outputSpec);
		
		ECReportSpec spec = EasyMock.createMock(ECReportSpec.class);
		EasyMock.expect(spec.getOutput()).andReturn(outputSpec).atLeastOnce();
		EasyMock.replay(spec);
		
		ECReportsHelper helper = new ECReportsHelper();
		
		Assert.assertTrue(invokeHelper(helper, reportTwoTags, reportTwoTags, spec));
		
		EasyMock.verify(spec);
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test equal reports.
	 * @throws Exception test failure.
	 */
	@Test
	public void testEqualWithoutTags() throws Exception {
		ECReports reportNoTags = getECReports(ECREPORTS_NULLGROUP_NOTAGSINGROUP);
		
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeEPC()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeTag()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeRawHex()).andReturn(true);
		EasyMock.replay(outputSpec);
		
		ECReportSpec spec = EasyMock.createMock(ECReportSpec.class);
		EasyMock.expect(spec.getOutput()).andReturn(outputSpec).atLeastOnce();
		EasyMock.replay(spec);
		
		ECReportsHelper helper = new ECReportsHelper();
		
		Assert.assertTrue(invokeHelper(helper, reportNoTags, reportNoTags, spec));
		
		EasyMock.verify(spec);
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test equal reports - old report null.
	 * @throws Exception test failure.
	 */
	@Test
	public void testNotEqualOldNull() throws Exception {
		ECReports reportTwoTags = getECReports(ECREPORTS_NULLGROUP_TWOTAGS);
		
		ECReportSpec spec = EasyMock.createMock(ECReportSpec.class);
		EasyMock.replay(spec);
		
		ECReportsHelper helper = new ECReportsHelper();
		
		Assert.assertFalse(invokeHelper(helper, reportTwoTags, null, spec));
		EasyMock.verify(spec);
	}
	
	/**
	 * test equal reports - not the same members.
	 * @throws Exception test failure.
	 */
	@Test
	public void testNotEqualNotTheSameMembersFirstMore() throws Exception {
		ECReports reportTwoTags = getECReports(ECREPORTS_NULLGROUP_TWOTAGS);
		ECReports reportOneTag = getECReports(ECREPORTS_NULLGROUP_ONETAG);
				
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeEPC()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeTag()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeRawHex()).andReturn(true);
		EasyMock.replay(outputSpec);
		
		ECReportSpec spec = EasyMock.createMock(ECReportSpec.class);
		EasyMock.expect(spec.getOutput()).andReturn(outputSpec).atLeastOnce();
		EasyMock.replay(spec);
		
		ECReportsHelper helper = new ECReportsHelper();
		
		Assert.assertFalse(invokeHelper(helper, reportTwoTags, reportOneTag, spec));
		EasyMock.verify(spec);
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test equal reports - not the same members.
	 * @throws Exception test failure.
	 */
	@Test
	public void testNotEqualNotTheSameMembersSecondMore() throws Exception {
		ECReports reportTwoTags = getECReports(ECREPORTS_NULLGROUP_TWOTAGS);
		ECReports reportOneTag = getECReports(ECREPORTS_NULLGROUP_ONETAG);
				
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeEPC()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeTag()).andReturn(true);
		EasyMock.expect(outputSpec.isIncludeRawHex()).andReturn(true);
		EasyMock.replay(outputSpec);
		
		ECReportSpec spec = EasyMock.createMock(ECReportSpec.class);
		EasyMock.expect(spec.getOutput()).andReturn(outputSpec).atLeastOnce();
		EasyMock.replay(spec);
		
		ECReportsHelper helper = new ECReportsHelper();
		
		Assert.assertFalse(invokeHelper(helper, reportOneTag, reportTwoTags, spec));
		EasyMock.verify(spec);
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * 
	 * @throws Exception test failure
	 */
	@Test
	public void testNotEqualGroupSizeDiffering() throws Exception {
		
		@SuppressWarnings("unchecked")
		List<ECReportGroup> group1 = EasyMock.createMock(List.class);
		EasyMock.expect(group1.size()).andReturn(1);
		EasyMock.replay(group1);
		
		ECReport report1 = EasyMock.createMock(ECReport.class);
		EasyMock.expect(report1.getGroup()).andReturn(group1);
		EasyMock.replay(report1);


		@SuppressWarnings("unchecked")
		List<ECReportGroup> group2 = EasyMock.createMock(List.class);
		EasyMock.expect(group2.size()).andReturn(2);
		EasyMock.replay(group2);
		
		ECReport report2 = EasyMock.createMock(ECReport.class);
		EasyMock.expect(report2.getGroup()).andReturn(group2);
		EasyMock.replay(report2);
		
		ECReportsHelper helper = new ECReportsHelper();
		
		Assert.assertFalse(invokeHelper(helper, report1, report2, null));
		EasyMock.verify(report1);
		EasyMock.verify(report2);
		EasyMock.verify(group1);
		EasyMock.verify(group2);
	}
	
	/**
	 * test the add epc method.
	 */
	@Test
	public void testAddEPC() {
		ECReportsHelper helper = new ECReportsHelper();
		Assert.assertFalse(helper.addEPC(null, null));
		Assert.assertFalse(helper.addEPC(new HashSet<String>(), null));
		Assert.assertFalse(helper.addEPC(new HashSet<String>(), new EPC()));
		EPC epc = new EPC();
		epc.setValue("theValue");
		Set<String> set = new HashSet<String>();
		Assert.assertTrue(helper.addEPC(set, epc));
		Assert.assertEquals("theValue", set.toArray()[0]);
		
	}
}
