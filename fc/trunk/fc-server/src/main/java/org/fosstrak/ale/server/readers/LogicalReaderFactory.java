package org.fosstrak.ale.server.readers;

import org.apache.log4j.Logger;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

/**
 * Factory creating logical readers.
 * 
 * @author swieland
 *
 */
public class LogicalReaderFactory {
	
	/** logger. */
	private static final Logger log = Logger.getLogger(LogicalReaderFactory.class);
	
	private LogicalReaderFactory() {
	}
	
	private static LogicalReader createCompositeReader(String name, LRSpec spec) throws ImplementationExceptionResponse {
		CompositeReader compositeReader = new CompositeReader();
		// initialize the reader
		compositeReader.initialize(name, spec);
		return compositeReader;
	}
	
	private static LogicalReader createBaseReader(String name, LRSpec spec) throws ImplementationExceptionResponse {
		LogicalReader logicalReader;
		try {
			// get the reader type of the reader
			String readerType = null;
			for (LRProperty property : spec.getProperties().getProperty()) {
				if (LogicalReader.PROPERTY_READER_TYPE.equalsIgnoreCase(property.getName())) {
					readerType = property.getValue();
				}
			}
			
			if (readerType == null) {
				log.debug("Property " + LogicalReader.PROPERTY_READER_TYPE + " not defined - aborting");
				throw new ImplementationExceptionResponse("Property " + LogicalReader.PROPERTY_READER_TYPE + " not defined");
			}
			
			Class<?> cls = Class.forName(readerType);  
			Object result = cls.newInstance();
			
			if (result instanceof LogicalReader) {
				logicalReader = (LogicalReader) result;
				// initialize the reader
				logicalReader.initialize(name, spec);
			} else {
				log.debug("constructor resulted in wrong type - aborting: " + result.getClass().getCanonicalName());
				throw new ClassCastException("constructor resulted in wrong type");
			}
			
		} catch (Throwable e) {
			log.error("could not dynamically reflect the reader type", e);
			throw new ImplementationExceptionResponse();
		}
		
		return logicalReader;
	}
	
	private static boolean isCompositeReader(LRSpec spec) throws ImplementationExceptionResponse {
		if (null == spec) {
			log.debug("spec is null - aborting");
			throw new ImplementationExceptionResponse("spec is null");
		}
		return spec.isIsComposite();
	}
	
	
	/**
	 * factory method to create a new reader. this can be a CompositeReader or a BaseReader.
	 * @param name name of the reader
	 * @param spec the specificationFile for a Reader
	 * @param logicalReaderManager handle to the logical reader manager
	 * @return a logical reader
	 * @throws ImplementationException when the LogicalReader could not be built by reflection
	 */
	public static LogicalReader createReader(String name, LRSpec spec, LogicalReaderManager logicalReaderManager) throws ImplementationExceptionResponse {
		// first test if reader is already in the LogicalReaderManager
		LogicalReader logicalReader = logicalReaderManager.getLogicalReader(name);
		if (logicalReader != null) {
			log.debug("using already defined reader.");
			return logicalReader;
		}
		
		// determine whether composite or basereader
		if (isCompositeReader(spec)) {
			log.debug("creating composite reader");
			return createCompositeReader(name, spec);
		}
		log.debug("creating base reader");
		return createBaseReader(name, spec);
	}
}
