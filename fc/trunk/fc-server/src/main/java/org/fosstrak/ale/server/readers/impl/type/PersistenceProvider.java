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
package org.fosstrak.ale.server.readers.impl.type;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

/**
 * Little wrapper for the persistence provider allowing to detach the Logical Reader Manager from static methods.
 * 
 * @deprecated
 * 
 * @author swieland
 *
 */
@Component("persistenceProvider")
public class PersistenceProvider {
	
	/**
	 * return a stream for the given load file path. 
	 * @param loadFilePath the load file path where the initial configuration is loaded from.
	 * @return
	 */
	public InputStream getInitializeInputStream(String loadFilePath) {
		return PersistenceProvider.class.getResourceAsStream(loadFilePath);
	}
	
	/**
	 * return a stream where to persist the whole logical reader setup.
	 * @param storeFilePath the file path where to store to.
	 * @return
	 */
	public OutputStream getStreamToWhereToStoreWholeManager(String storeFilePath) throws FileNotFoundException {
		return new FileOutputStream(storeFilePath);
	}
}
