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

package org.fosstrak.ale.server.persistence.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.easymock.EasyMock;
import org.fosstrak.ale.server.persistence.PersistenceInit;
import org.fosstrak.ale.server.persistence.ReadConfig;
import org.fosstrak.ale.server.persistence.impl.PersistenceInitImpl;
import org.fosstrak.ale.server.persistence.type.PersistenceConfig;
import org.junit.Test;

/**
 * tests the functionality of the persistence servlet.
 * @author swieland
 *
 */
public class PersistenceInitTest {
	
	/**
	 * verify that the servlets context settings are correctly passed to the persistence API.
	 * @throws ServletException test failure.
	 */
	@Test
	public void testInit() throws ServletException {
		final String realPath = "realPath";
		PersistenceConfig pConfig = EasyMock.createMock(PersistenceConfig.class);
		pConfig.setRealPathWebapp(realPath);
		EasyMock.expectLastCall();
		EasyMock.replay(pConfig);
		
		ReadConfig readConfig = EasyMock.createMock(ReadConfig.class);
		readConfig.init();
		EasyMock.expectLastCall();
		EasyMock.replay(readConfig);
		
		ServletContext servletContext = EasyMock.createMock(ServletContext.class);
		EasyMock.expect(servletContext.getRealPath("/")).andReturn(realPath);
		EasyMock.replay(servletContext);
		
		PersistenceInitImpl persistenceInit = new PersistenceInitImpl();
		persistenceInit.setPersistenceConfig(pConfig);
		persistenceInit.setPersistenceReadConfig(readConfig);
		
		persistenceInit.init(servletContext);
		
		EasyMock.verify(pConfig);
		EasyMock.verify(readConfig);
		EasyMock.verify(servletContext);
	}
	
	/**
	 * verify that the servlets context settings are correctly passed to the persistence API.
	 * @throws ServletException test failure.
	 */
	@Test(expected = ServletException.class)
	public void testInitErrorCase() throws ServletException {		
		ServletContext servletContext = EasyMock.createMock(ServletContext.class);
		EasyMock.expect(servletContext.getRealPath("/")).andThrow(new IllegalStateException("Mock exception"));
		EasyMock.replay(servletContext);
		
		PersistenceInit persistenceInit = new PersistenceInitImpl();
		
		persistenceInit.init(servletContext);
	}
}
