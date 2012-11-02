package org.fosstrak.ale.server.readers;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
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
	
	private static LogicalReader createCompositeReader(String name, LRSpec spec) throws ImplementationException {
		return new CompositeReader();
	}
	
	private static LogicalReader createBaseReader(String name, LRSpec spec) throws ImplementationException {
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
				throw new ImplementationException("Property " + LogicalReader.PROPERTY_READER_TYPE + " not defined");
			}
			
			Class<?> cls = Class.forName(readerType);  
			Object result = cls.newInstance();
			
			if (result instanceof LogicalReader) {
				logicalReader = (LogicalReader) result;				
				return logicalReader;
			} else {
				log.debug("constructor resulted in wrong type - aborting: " + result.getClass().getCanonicalName());
				throw new ClassCastException("constructor resulted in wrong type");
			}
			
		} catch (Throwable e) {
			log.error("could not dynamically reflect the reader type", e);
			throw new ImplementationException("could not dynamically reflect the reader type", e);
		}
	}
	
	private static boolean isCompositeReader(LRSpec spec) throws ImplementationException {
		if (null == spec) {
			log.debug("spec is null - aborting");
			throw new ImplementationException("spec is null");
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
	public static LogicalReader createReader(String name, LRSpec spec, LogicalReaderManager logicalReaderManager) throws ImplementationException {
		// first test if reader is already in the LogicalReaderManager
		LogicalReader logicalReader = logicalReaderManager.getLogicalReader(name);
		if (logicalReader != null) {
			log.debug("using already defined reader.");
			return logicalReader;
		}
		
		// determine whether composite or basereader
		if (isCompositeReader(spec)) {
			log.debug("creating composite reader");
			logicalReader = createCompositeReader(name, spec);
		}
		log.debug("creating base reader");
		logicalReader = createBaseReader(name, spec);
		logicalReader.setLogicalReaderManager(logicalReaderManager);
		logicalReader.initialize(name, spec);
		return logicalReader;
	}
}
