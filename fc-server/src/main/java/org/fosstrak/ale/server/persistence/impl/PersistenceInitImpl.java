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

package org.fosstrak.ale.server.persistence.impl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.fosstrak.ale.server.persistence.PersistenceInit;
import org.fosstrak.ale.server.persistence.ReadConfig;
import org.fosstrak.ale.server.persistence.type.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * standard implementation of the persistence init.
 * 
 * @author benoit.plomion@orange.com
 */
@Repository("persistenceInit")
public class PersistenceInitImpl implements PersistenceInit {

	private static final Logger LOG = Logger.getLogger(PersistenceInitImpl.class.getName());
	// autowired
	private PersistenceConfig persistenceConfig;

	// autowired
	private ReadConfig persistenceReadConfig;

	@Override
	public void init(ServletContext servletContext) throws ServletException {
		try {
			LOG.info("ALE Persistence => start");
			String path = servletContext.getRealPath("/");
			LOG.debug("ALE Persistence real path of the webapp: " + path);
			persistenceConfig.setRealPathWebapp(path);

			LOG.info("ALE Persistence initialize configuration");
			persistenceReadConfig.init();

			LOG.info("ALE Persistence => end");
		} catch (Exception ex) {
			LOG.error("could not initialize the persistence API.", ex);
			throw new ServletException(ex);
		}
	}

	/**
	 * allow to inject the persistence config API.
	 * 
	 * @param persistenceRemoveAPI
	 *            the persistence config API.
	 */
	@Autowired
	public void setPersistenceConfig(PersistenceConfig persistenceConfig) {
		this.persistenceConfig = persistenceConfig;
	}

	/**
	 * allow to inject the persistence write API.
	 * 
	 * @param persistenceWriteAPI
	 *            the persistence write API.
	 */
	@Autowired
	public void setPersistenceReadConfig(ReadConfig persistenceReadConfig) {
		this.persistenceReadConfig = persistenceReadConfig;
	}

}
