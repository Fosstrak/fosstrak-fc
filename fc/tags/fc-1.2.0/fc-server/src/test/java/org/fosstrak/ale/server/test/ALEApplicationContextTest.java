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

import org.fosstrak.ale.server.ALEApplicationContext;
import org.junit.Test;

/**
 * test the application context and its methods - notice that autowiring is not tested t
 * 
 * @author swieland
 *
 */
public class ALEApplicationContextTest {
	
	@Test(expected = IllegalStateException.class)
	public void testGetBeanByName() {
		ALEApplicationContext.getBeanByName(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetBeanByNameWithClass() {
		ALEApplicationContext.getBeanByName(null, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetBean() {
		ALEApplicationContext.getBean(null);
	}
}
