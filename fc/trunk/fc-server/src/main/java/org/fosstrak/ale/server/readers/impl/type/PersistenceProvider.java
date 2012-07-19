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

import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.llrp.ltk.generated.messages.ADD_ROSPEC;
import org.springframework.stereotype.Component;

/**
 * Little wrapper for the persistence provider allowing to detach the Logical Reader Manager from static methods.
 * 
 * @author swieland
 *
 */
@Component("persistenceProvider")
public class PersistenceProvider {
	
	/**
	 * this method write on the path of the webapp each ECSpec loaded in the ALE.
	 * @param specName
	 * @param spec
	 */
	public void writeECSpec(String specName, ECSpec spec) {
		WriteConfig.writeECSpec(specName, spec);
	}

	/**
	 * this method remove on the path of the webapp the ECSpec file which is undefine
	 * @param specName
	 */
	public void removeECSpec(String specName) {
		RemoveConfig.removeECSpec(specName);
		
	}
	
	/**
	 *  this method write on the path of the webapp each ECSpec subscriber loaded in the ALE.
	 * @param specName
	 * @param notificationURI
	 */
	public void writeECSpecSubscriber(String specName, String notificationURI) {
		WriteConfig.writeECSpecSubscriber(specName, notificationURI);
	}
	
	/**
	 *  this method remove on the path of the webapp the ECSpec subscriber wich is unsubscribe. if the file is empty
	 * @param specName
	 */
	public void removeECSpecSubscriber(String specName, String notificationURI) {
		RemoveConfig.removeECSpecSubscriber(specName, notificationURI);
	}
	
		/**
	 * persist the given LR specification.
	 * @param specName the name of specification.
	 * @param spec the specification itself.
	 */
	public void writeLRSpec(String specName, LRSpec spec) {
		WriteConfig.writeLRSpec(specName, spec);
	}

	/**
	 * persist the given RO specification.
	 * @param specName the name of the specification.
	 * @param addRoSpec the specification itself.
	 */
	public void writeAddROSpec(String specName, ADD_ROSPEC addRoSpec) {
		WriteConfig.writeAddROSpec(specName, addRoSpec);
	}

	/**
	 * remove the given specification from the persistence.
	 * @param specName the specification to be removed.
	 */
	public void removeLRSpec(String specName) {
		RemoveConfig.removeLRSpec(specName);		
	}
	
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
