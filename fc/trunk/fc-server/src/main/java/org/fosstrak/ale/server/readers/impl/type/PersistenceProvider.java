package org.fosstrak.ale.server.readers.impl.type;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;
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
