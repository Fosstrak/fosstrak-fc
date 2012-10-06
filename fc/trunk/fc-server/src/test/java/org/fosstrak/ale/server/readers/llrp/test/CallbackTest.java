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

package org.fosstrak.ale.server.readers.llrp.test;

import org.easymock.EasyMock;
import org.fosstrak.ale.server.readers.llrp.Callback;
import org.fosstrak.ale.server.readers.llrp.LLRPAdaptor;
import org.junit.Test;

/**
 * test the LLRP callback handler.
 * @author swieland
 *
 */
public class CallbackTest {
	
	@Test
	public void testNotify() throws Exception {
		LLRPAdaptor adaptor = EasyMock.createMock(LLRPAdaptor.class);
		
		final byte [] message = "message".getBytes();
		final String readerName = "readerName";
		adaptor.notify(message, readerName);
		EasyMock.expectLastCall();
		
		EasyMock.replay(adaptor);
		
		Callback cb = new Callback(adaptor);
		cb.notify(message, readerName);
		
		EasyMock.verify(adaptor);
	}
}
