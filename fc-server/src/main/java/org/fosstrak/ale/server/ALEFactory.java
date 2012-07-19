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
package org.fosstrak.ale.server;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Static factory giving access to the ALE. this factory is provided for backwards compatibility to 
 * components that do not use the spring bean wiring.<br/>
 * <br/>
 * <strong>NOTICE: </strong> This component will be removed in future releases.
 * <br/>
 * <br/>
 * Instead define your services/beans as spring beans. then you can automatically profit from nice features like 
 * spring autowiring and dependency injection.
 * 
 * @author swieland
 *
 * @deprecated please use the logical reader manager as an autowired bean.
 */
@Component("aleFactory")
public class ALEFactory {

	private static final Logger LOG = Logger.getLogger(ALEFactory.class);
	
	private static ALE ale;
	
	/**
	 * private utility constructor.
	 */
	private ALEFactory() {
		
	}
	
	/**
	 * little trick to inject the autowired bean ALE into the static factory class.
	 * @param theAle
	 */
	@Autowired
	public void setALE(ALE theAle) {
		ale = theAle;
	}
	
	/**
	 * @deprecated please use the ALE as an autowired bean.
	 * gives a handle onto the currently parameterized ALE.
	 * @return the currently parameterized ALE.
	 */
	public static ALE getALE() {
		LOG.info("do not use this method anymore - deprecated.");
		return ale;
	}
}
