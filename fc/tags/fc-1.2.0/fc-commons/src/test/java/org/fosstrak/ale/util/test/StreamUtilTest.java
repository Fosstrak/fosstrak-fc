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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.fosstrak.ale.util.StreamUtil;
import org.junit.Test;

/**
 * test to assert the stream utils.
 * @author swieland
 *
 */
public class StreamUtilTest {
	
	@Test
	public void testStream2StringNull() throws Exception {
		Assert.assertNull(StreamUtil.inputStream2String(null));
		
		InputStream in = EasyMock.createMock(InputStream.class);
		EasyMock.expect(in.available()).andThrow(new IOException("Mock exception."));
		EasyMock.replay(in);
		
		Assert.assertNull(StreamUtil.inputStream2String(in));
		EasyMock.verify(in);
	}
	
	@Test
	public void testStream2String() throws Exception {
		ByteArrayInputStream bin = new ByteArrayInputStream("hello".getBytes());
		Assert.assertEquals("hello", StreamUtil.inputStream2String(bin));
	}
}
