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
package org.fosstrak.ale.util.test;

import junit.framework.Assert;

import org.fosstrak.ale.util.ECReportSetEnum;
import org.junit.Test;

/**
 * test the correct behaviour of the enumeration.
 * @author swieland
 *
 */
public class ECReportSetEnumTest {
	
	@Test
	public void isSameECReportSet() {
		Assert.assertTrue(ECReportSetEnum.ADDITIONS.equals(ECReportSetEnum.valueOf("ADDITIONS")));
		Assert.assertTrue(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.ADDITIONS, "ADDITIONS"));
		Assert.assertTrue(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.ADDITIONS, "additions"));
		
		Assert.assertTrue(ECReportSetEnum.CURRENT.equals(ECReportSetEnum.valueOf("CURRENT")));
		Assert.assertTrue(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.CURRENT, "CURRENT"));
		Assert.assertTrue(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.CURRENT, "current"));
		
		Assert.assertTrue(ECReportSetEnum.DELETIONS.equals(ECReportSetEnum.valueOf("DELETIONS")));
		Assert.assertTrue(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.DELETIONS, "DELETIONS"));
		Assert.assertTrue(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.DELETIONS, "deletions"));
		
		Assert.assertFalse(ECReportSetEnum.DELETIONS.equals(ECReportSetEnum.valueOf("ADDITIONS")));
	}
	
	@Test
	public void testNullValue() {
		Assert.assertFalse(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.DELETIONS, null));
	}
	
	@Test
	public void testUnknow() {
		Assert.assertFalse(ECReportSetEnum.isSameECReportSet(ECReportSetEnum.DELETIONS, "test"));
	}
}
