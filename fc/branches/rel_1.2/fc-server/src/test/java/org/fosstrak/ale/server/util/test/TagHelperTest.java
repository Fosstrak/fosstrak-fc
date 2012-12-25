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

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.epcglobalinc.tdt.LevelTypeList;
import org.fosstrak.ale.server.Tag;
import org.fosstrak.ale.server.util.TagHelper;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMember;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpec;
import org.fosstrak.ale.xsd.epcglobal.EPC;
import org.fosstrak.tdt.TDTEngine;
import org.fosstrak.tdt.TDTException;
import org.junit.Before;
import org.junit.Test;

/**
 * verify the methods of the tag helper.
 * @author swieland
 *
 */
public class TagHelperTest {
	
	@Before
	public void beforeEachTest() throws IOException, JAXBException {
		TagHelper.setTDTEngine(new TDTEngine());
	}
	
	/**
	 * test if to add the tag in raw decimal - exception case.
	 */
	@Test
	public void testIsReportOutputSpecIncludeRawDecimalExceptionReturnsFalse() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeRawDecimal()).andThrow(new IllegalArgumentException("MOCK EXCEPTION FOR TEST"));
		EasyMock.replay(outputSpec);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeRawDecimal(outputSpec));
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test if to add the tag in raw decimal - normal cases.
	 */
	@Test
	public void testIsReportOutputSpecIncludeRawDecimal() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeRawDecimal()).andReturn(false);
		EasyMock.replay(outputSpec);
		ECReportOutputSpec outputSpec2 = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec2.isIncludeRawDecimal()).andReturn(true);
		EasyMock.replay(outputSpec2);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeRawDecimal(outputSpec));
		Assert.assertTrue(TagHelper.isReportOutputSpecIncludeRawDecimal(outputSpec2));
		EasyMock.verify(outputSpec);
		EasyMock.verify(outputSpec2);
	}
	
	/**
	 * test adding a tag as raw decimal - test the standard case - no error.
	 */
	@Test
	public void testAddTagAsRawDecimal() {		
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.bin2dec(original)).andReturn(expected);
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		groupMember.setRawDecimal(EasyMock.isA(EPC.class));
		EasyMock.expectLastCall();
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertEquals("urn:epc:raw:8.converted", TagHelper.addTagAsRawDecimal(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test adding a tag as raw decimal - test the case where TDT has a problem.
	 */
	@Test
	public void testAddTagAsRawDecimalErrorInTDT() {		
		final String original = "original";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.bin2dec(original)).andThrow(new TDTException("MOCK EXCEPTION"));
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertNull(TagHelper.addTagAsRawDecimal(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test if to add the tag in tag encoding - exception case.
	 */
	@Test
	public void testIsReportOutputSpecIncludeTagEncodingExceptionReturnsFalse() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeTag()).andThrow(new IllegalArgumentException("MOCK EXCEPTION FOR TEST"));
		EasyMock.replay(outputSpec);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeTagEncoding(outputSpec));
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test if to add the tag in tag encoding - normal cases.
	 */
	@Test
	public void testIsReportOutputSpecIncludeTagEncoding() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeTag()).andReturn(false);
		EasyMock.replay(outputSpec);
		ECReportOutputSpec outputSpec2 = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec2.isIncludeTag()).andReturn(true);
		EasyMock.replay(outputSpec2);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeTagEncoding(outputSpec));
		Assert.assertTrue(TagHelper.isReportOutputSpecIncludeTagEncoding(outputSpec2));
		EasyMock.verify(outputSpec);
		EasyMock.verify(outputSpec2);
	}
	
	/**
	 * test adding a tag as tag encoding - test the standard case - no error.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddTagAsTagEncoding() {		
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.TAG_ENCODING))).andReturn(expected);
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		groupMember.setTag(EasyMock.isA(EPC.class));
		EasyMock.expectLastCall();
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.expect(tag.getTagLength()).andReturn("tagLength").atLeastOnce();
		EasyMock.expect(tag.getFilter()).andReturn("tagFilter").atLeastOnce();
		EasyMock.expect(tag.getCompanyPrefixLength()).andReturn("companyPrefixLength").atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertEquals(expected, TagHelper.addTagAsTagEncoding(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test adding a tag as tag encoding - test the case where TDT has a problem.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddTagAsTagEncodingErrorInTDT() {		
		final String original = "original";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.TAG_ENCODING))).andThrow(new TDTException("MOCK EXCEPTION"));
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.expect(tag.getTagLength()).andReturn("tagLength").atLeastOnce();
		EasyMock.expect(tag.getFilter()).andReturn("tagFilter").atLeastOnce();
		EasyMock.expect(tag.getCompanyPrefixLength()).andReturn("companyPrefixLength").atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertNull(TagHelper.addTagAsTagEncoding(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test if to add the tag in raw hex - exception case.
	 */
	@Test
	public void testIsReportOutputSpecIncludeRawHexExceptionReturnsFalse() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeRawHex()).andThrow(new IllegalArgumentException("MOCK EXCEPTION FOR TEST"));
		EasyMock.replay(outputSpec);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeRawHex(outputSpec));
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test if to add the tag in raw hex - normal cases.
	 */
	@Test
	public void testIsReportOutputSpecIncludeRawHex() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeRawHex()).andReturn(false);
		EasyMock.replay(outputSpec);
		ECReportOutputSpec outputSpec2 = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec2.isIncludeRawHex()).andReturn(true);
		EasyMock.replay(outputSpec2);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeRawHex(outputSpec));
		Assert.assertTrue(TagHelper.isReportOutputSpecIncludeRawHex(outputSpec2));
		EasyMock.verify(outputSpec);
		EasyMock.verify(outputSpec2);
	}
	
	/**
	 * test adding a tag as raw hex - test the standard case - no error.
	 */
	@Test
	public void testAddTagAsRawHex() {		
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.bin2hex(original)).andReturn(expected);
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		groupMember.setRawHex(EasyMock.isA(EPC.class));
		EasyMock.expectLastCall();
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertEquals("urn:epc:raw:8.xconverted", TagHelper.addTagAsRawHex(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test adding a tag as raw hex - test the case where TDT has a problem.
	 */
	@Test
	public void testAddTagAsRawHexErrorInTDT() {		
		final String original = "original";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.bin2hex(original)).andThrow(new TDTException("MOCK EXCEPTION"));
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertNull(TagHelper.addTagAsRawHex(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test if to add the tag in EPC format - exception case.
	 */
	@Test
	public void testIsReportOutputSpecIncludeEPCExceptionReturnsFalse() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeEPC()).andThrow(new IllegalArgumentException("MOCK EXCEPTION FOR TEST"));
		EasyMock.replay(outputSpec);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeEPC(outputSpec));
		EasyMock.verify(outputSpec);
	}
	
	/**
	 * test if to add the tag in EPC format - normal cases.
	 */
	@Test
	public void testIsReportOutputSpecIncludeEPC() {
		ECReportOutputSpec outputSpec = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec.isIncludeEPC()).andReturn(false);
		EasyMock.replay(outputSpec);
		ECReportOutputSpec outputSpec2 = EasyMock.createMock(ECReportOutputSpec.class);
		EasyMock.expect(outputSpec2.isIncludeEPC()).andReturn(true);
		EasyMock.replay(outputSpec2);
		
		Assert.assertFalse(TagHelper.isReportOutputSpecIncludeEPC(outputSpec));
		Assert.assertTrue(TagHelper.isReportOutputSpecIncludeEPC(outputSpec2));
		EasyMock.verify(outputSpec);
		EasyMock.verify(outputSpec2);
	}
	
	/**
	 * test adding a tag as EPC - test the standard case - no error.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddTagAsEPC() {		
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.PURE_IDENTITY))).andReturn(expected);
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		groupMember.setEpc(EasyMock.isA(EPC.class));
		EasyMock.expectLastCall();
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.expect(tag.getTagLength()).andReturn("tagLength").atLeastOnce();
		EasyMock.expect(tag.getFilter()).andReturn("tagFilter").atLeastOnce();
		EasyMock.expect(tag.getCompanyPrefixLength()).andReturn("companyPrefixLength").atLeastOnce();
		EasyMock.replay(tag);
		Assert.assertEquals(expected, TagHelper.addTagAsEPC(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}
	
	/**
	 * test adding a tag as EPC - test the case where TDT has a problem.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddTagAsEPCErrorInTDT() {	
		final String original = "original";
		final String pureURI = "pureURI";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.PURE_IDENTITY))).andThrow(new TDTException("MOCK EXCEPTION"));
		EasyMock.replay(tdt);		
		TagHelper.setTDTEngine(null);
		
		ECReportGroupListMember groupMember = EasyMock.createMock(ECReportGroupListMember.class);
		groupMember.setEpc(EasyMock.isA(EPC.class));
		EasyMock.expectLastCall();
		EasyMock.replay(groupMember);
		
		Tag tag = EasyMock.createMock(Tag.class);
		EasyMock.expect(tag.getTagAsBinary()).andReturn(original).atLeastOnce();
		EasyMock.expect(tag.getTagLength()).andReturn("tagLength").atLeastOnce();
		EasyMock.expect(tag.getFilter()).andReturn("tagFilter").atLeastOnce();
		EasyMock.expect(tag.getCompanyPrefixLength()).andReturn("companyPrefixLength").atLeastOnce();
		EasyMock.expect(tag.getTagIDAsPureURI()).andReturn(pureURI).atLeastOnce();
		EasyMock.replay(tag);
		
		Assert.assertEquals(pureURI, TagHelper.addTagAsEPC(tdt, groupMember, tag));
		EasyMock.verify(tdt);
		EasyMock.verify(tag);
		EasyMock.verify(groupMember);
	}

	
	/**
	 * test the retrieval of the tag in binary but with binary set (error case).
	 */
	@Test
	public void testGetBinaryRepresentationFailureCase() {
		Assert.assertNull(TagHelper.getBinaryRepresentation(null));
		Assert.assertNull(TagHelper.getBinaryRepresentation(new Tag()));
	}
	
	// ----------------------------------------- TAG CONVERSIONS --------------------------------------
	/**
	 * TagHelper must not throw NullPointerException - only TDTException is allowed. 
	 */
	@Test(expected = TDTException.class)
	public void testTDTNotThrowingNullPointerException() {
		TagHelper.setTDTEngine(null);
		TagHelper.convert(null, null, null, null);
	}
	
	/**
	 * test the TDT conversion to LEGACY.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvert_to_LEGACY() {	
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.LEGACY))).andReturn(expected);
		EasyMock.replay(tdt);
		
		TagHelper.setTDTEngine(tdt);
		String actual = TagHelper.convert_to_LEGACY("tagLength", "filter", "companyPrefixLength", original);
		Assert.assertEquals(expected, actual);
		EasyMock.verify(tdt);
	}
	
	/**
	 * test the TDT conversion to PURE_IDENTITY.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvert_to_PURE_IDENTITY() {		
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.PURE_IDENTITY))).andReturn(expected);
		EasyMock.replay(tdt);
		
		TagHelper.setTDTEngine(tdt);
		String actual = TagHelper.convert_to_PURE_IDENTITY("tagLength", "filter", "companyPrefixLength", original);
		Assert.assertEquals(expected, actual);
		EasyMock.verify(tdt);
	}
	
	/**
	 * test the TDT conversion to TAG_ENCODING.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvert_to_TAG_ENCODING() {		
		final String original = "original";
		final String expected = "converted";
		
		TDTEngine tdt = EasyMock.createMock(TDTEngine.class);
		EasyMock.expect(tdt.convert(EasyMock.eq(original), EasyMock.isA(Map.class), EasyMock.eq(LevelTypeList.TAG_ENCODING))).andReturn(expected);
		EasyMock.replay(tdt);
		
		TagHelper.setTDTEngine(tdt);
		String actual = TagHelper.convert_to_TAG_ENCODING("tagLength", "filter", "companyPrefixLength", original);
		Assert.assertEquals(expected, actual);
		EasyMock.verify(tdt);
	}
	
	/**
	 * test the creation of the extra parameters map.
	 */
	@Test
	public void testCreateExtraParameters() {
		Map<String, String> pMap1 = TagHelper.createExtraParams(null, null, null);
		Assert.assertNotNull(pMap1);
		Assert.assertNull(pMap1.get(TagHelper.EXTRA_PARAMS_COMPANYPREFIXLENGTH));
		Assert.assertNull(pMap1.get(TagHelper.EXTRA_PARAMS_FILTER));
		Assert.assertNull(pMap1.get(TagHelper.EXTRA_PARAMS_TAGLENGTH));
		
		Map<String, String> pMap2 = TagHelper.createExtraParams("a", "b", "c");
		Assert.assertNotNull(pMap2);
		Assert.assertEquals("a", pMap2.get(TagHelper.EXTRA_PARAMS_TAGLENGTH));
		Assert.assertEquals("b", pMap2.get(TagHelper.EXTRA_PARAMS_FILTER));
		Assert.assertEquals("c", pMap2.get(TagHelper.EXTRA_PARAMS_COMPANYPREFIXLENGTH));
	}

}
