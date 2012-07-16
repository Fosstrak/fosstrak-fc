package org.fosstrak.ale.server.readers.impl.type;

import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderFactory;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * little helper class allowing us to detach the Logical Reader Manager from static methods.
 * @author swieland
 *
 */
@Component("readerProvider")
public class ReaderProvider {
	
	@Autowired
	private LogicalReaderManager logicalReaderManager;

	/**
	 * factory method to create a new reader. this can be a CompositeReader or a BaseReader.
	 * @param name name of the reader
	 * @param spec the specificationFile for a Reader
	 * @return a logical reader
	 * @throws ImplementationException when the LogicalReader could not be built by reflection
	 */
	public LogicalReader createReader(String name, LRSpec spec)  throws ImplementationExceptionResponse {
		return LogicalReaderFactory.createReader(name, spec, logicalReaderManager);
	}
}
