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
package org.fosstrak.ale.server.readers.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.ImmutableReaderException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InUseException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.exception.NonCompositeReaderException;
import org.fosstrak.ale.exception.ReaderLoopException;
import org.fosstrak.ale.exception.SecurityException;
import org.fosstrak.ale.exception.ValidationException;
import org.fosstrak.ale.server.ALE;
import org.fosstrak.ale.server.ALESettings;
import org.fosstrak.ale.server.persistence.RemoveConfig;
import org.fosstrak.ale.server.persistence.WriteConfig;
import org.fosstrak.ale.server.readers.BaseReader;
import org.fosstrak.ale.server.readers.CompositeReader;
import org.fosstrak.ale.server.readers.LogicalReader;
import org.fosstrak.ale.server.readers.LogicalReaderManager;
import org.fosstrak.ale.server.readers.gen.LogicalReaders;
import org.fosstrak.ale.server.readers.gen.ObjectFactory;
import org.fosstrak.ale.server.readers.impl.type.PersistenceProvider;
import org.fosstrak.ale.server.readers.impl.type.ReaderProvider;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * standard implementation of the LogicalReaderManager.
 * 
 * @author swieland
 *
 */
@Service("logicalReaderManager")
public class LogicalReaderManagerImpl implements LogicalReaderManager {
	
	/** logger. */
	private static final Logger LOG = Logger.getLogger(LogicalReaderManager.class);
	
	/** package containing the generated jaxb classes. */
	private static final String JAXB_CONTEXT = "org.fosstrak.ale.server.readers.gen";
	
	/** default path to file which contains the initial logical reader configuration. */
	private static final String LOAD_FILEPATH = "/LogicalReaders.xml";
	
	/** default path to file which contains the current setting of logical readers. */
	private static final String STORE_FILEPATH = "/StoreLogicalReaders.xml";
	
	/** logical reader configuration loaded from file. */
	private LogicalReaders logicalReadersConfiguration;

	/** a map of all LogicalReaders. the readers are mapped against their name.	 */
	private java.util.Map<String, LogicalReader> logicalReaders = new ConcurrentHashMap<String, LogicalReader>();

	private final Schema SCHEMA_FILEPATH = null; 

	/** indicates if the manager is initialized or not. */
	private static boolean initialized = false;
	
	// autowired
	private PersistenceProvider persistenceProvider;

	// autowired
	private RemoveConfig persistenceRemoveAPI;
	
	// autowired
	private WriteConfig persistenceWriteAPI;
	
	// autowired
	private ReaderProvider readerProvider;
	
	// autowired
    private ALESettings aleSettings;

	@Autowired
	private ALE ale;
	

	@Override
	public String getVendorVersion() throws ImplementationException {
		return aleSettings.getAleVendorVersion();
	}

	@Override
	public String getStandardVersion() throws ImplementationException {
		return aleSettings.getAleStandardVersion();
	}

	@Override
	public String getPropertyValue(String name, String propertyName) throws NoSuchNameException, SecurityException, ImplementationException {
				
		LogicalReader logRd = logicalReaders.get(name);
		throwNoSuchNameExceptionIfReaderNull(logRd, name);
		
		List<LRProperty> propList = logRd.getProperties();
		for (LRProperty prop : propList) {
			if (prop.getName().equalsIgnoreCase(propertyName)) {
				return prop.getValue();
			}
		}
		return null;
	}

	@Override
	public void setProperties(String name, List<LRProperty> properties)	throws NoSuchNameException, ValidationException, InUseException, ImmutableReaderException, SecurityException, ImplementationException {

		throwValidationExceptionOnNullInput(properties);
		
		LogicalReader logRd = logicalReaders.get(name);

		throwNoSuchNameExceptionIfReaderNull(logRd, name);
		
		LRSpec spec = logRd.getLRSpec();
		if (spec.getProperties() == null) {
			spec.setProperties(new LRSpec.Properties());
		}
		// we need to replace the properties, not just add to the old ones.
		spec.getProperties().getProperty().clear();
		spec.getProperties().getProperty().addAll(properties);
		LOG.debug("set the properties");
		try {
			update(name, spec);
		} catch (ReaderLoopException e) {
			String errMsg = "ReaderLoopException during update.";
			LOG.debug(errMsg, e);
			throw new ImplementationException(errMsg, e);			
		}		
	}

	@Override
	public void removeReaders(String name, java.util.List<String> readers) throws NoSuchNameException, InUseException, ImmutableReaderException, NonCompositeReaderException, SecurityException, ImplementationException {
		LogicalReader lgRd = logicalReaders.get(name);

		throwNoSuchNameExceptionIfReaderNull(lgRd, name);
		throwNonCompositeReaderExceptionIfReaderNotComposite(lgRd, name);
		
		// get the readers that are still in the spec
		LRSpec spec = lgRd.getLRSpec();
		List<String> res = new ArrayList<String>();
		if ((spec.getReaders() != null) && (spec.getReaders().getReader().size() > 0)) {
			for (String reader : spec.getReaders().getReader()) {
				if (!readers.contains(reader)) {
					res.add(reader);
				}
			}
		}
		
		// add the resulting readers
		spec.setReaders(new LRSpec.Readers());
		spec.getReaders().getReader().addAll(res);
		try {
			update(name, spec);
		} catch (ValidationException e) {
			throw new ImplementationException(e.getMessage());
		} catch (ReaderLoopException e) {
			throw new ImplementationException(e.getMessage());
		}
	}

	@Override
	public void setReaders(String name, java.util.List<String> readers)  throws NoSuchNameException, ValidationException, InUseException, ImmutableReaderException, NonCompositeReaderException, ReaderLoopException, SecurityException, ImplementationException {
		LogicalReader logRd = logicalReaders.get(name);
		
		throwNoSuchNameExceptionIfReaderNull(logRd, name);
		throwNonCompositeReaderExceptionIfReaderNotComposite(logRd, name);
		throwValidationExceptionIfNotAllReadersAvailable(readers);
		
		LRSpec spec = logRd.getLRSpec();
		spec.setReaders(new LRSpec.Readers());
		spec.getReaders().getReader().addAll(readers);
		update(name, spec);
	}

	@Override
	public void addReaders(String name, java.util.List<String> readers) throws NoSuchNameException, ValidationException, InUseException, ImmutableReaderException, ReaderLoopException, SecurityException, ImplementationException, NonCompositeReaderException {
		LogicalReader logRd = logicalReaders.get(name);

		throwNoSuchNameExceptionIfReaderNull(logRd, name);
		throwNonCompositeReaderExceptionIfReaderNotComposite(logRd, name);
		throwValidationExceptionIfNotAllReadersAvailable(readers);
		
		LRSpec spec = logRd.getLRSpec();
		if (spec.getReaders() == null) {
			spec.setReaders(new LRSpec.Readers());
		}
		for (String reader : readers) {
			if (!spec.getReaders().getReader().contains(reader)) {
				spec.getReaders().getReader().add(reader);
			}
		}
		update(name, spec);
	}

	@Override
	public LRSpec getLRSpec(String name) throws NoSuchNameException, SecurityException, ImplementationException {
		LogicalReader logRd = logicalReaders.get(name);

		throwNoSuchNameExceptionIfReaderNull(logRd, name);
		
		return logRd.getLRSpec();		
	}

	@Override
	public java.util.List<String> getLogicalReaderNames() throws SecurityException, ImplementationException {
		List<String> rdNames = new ArrayList<String>();
		Iterable<String> it = logicalReaders.keySet();
		for (String reader : it) {
			rdNames.add(reader);
		}
		return rdNames;
	}

	@Override
	public void undefine(String name) throws NoSuchNameException, InUseException, SecurityException, ImmutableReaderException, ImplementationException {
		// the logicalReader must delete himself from its observables
		LOG.debug("undefining reader " + name);
		LogicalReader reader = getLogicalReader(name);
		
		throwNoSuchNameExceptionIfReaderNull(reader, name);
		
		// according to the EPC standard a reader cannot be undefined when there is 
		// an active CC or EC pointing to the reader
		// this raises an InUseException
		if (reader.countObservers() > 0) {
			throw new InUseException(name + "is still in use.");
		}
		
		if (reader instanceof CompositeReader) {
			CompositeReader composite = (CompositeReader) reader;
			composite.unregisterAsObserver();
		} else if (reader instanceof BaseReader) {
			BaseReader basereader = (BaseReader) reader;
			basereader.disconnectReader();
			basereader.cleanup();
		} else {
			throw new ImplementationException("try to undefine unknown reader type - ALE knows BaseReader and CompositeReader - atomic readers must subclass BaseReader, composite readers (collections of readers) must subclass CompositeReader - this is a serious problem!!! reader-name: " + name);
		}
		
		persistenceRemoveAPI.removeLRSpec(name);
		
		logicalReaders.remove(name);
	}

	@Override
	public void update(String name, LRSpec spec)  throws NoSuchNameException, ValidationException, InUseException,  ImmutableReaderException, ReaderLoopException, SecurityException, ImplementationException {
		LogicalReader logRd = logicalReaders.get(name);
		throwNoSuchNameExceptionIfReaderNull(logRd, name);
		
		logRd.update(spec);
		
		persistenceRemoveAPI.removeLRSpec(name);
		persistenceWriteAPI.writeLRSpec(name, spec);
	}

	@Override
	public void define(String name, org.fosstrak.ale.server.readers.gen.LRSpec spec) throws DuplicateNameException, ValidationException, SecurityException, ImplementationException {
		throwValidationExceptionOnNullInput(name, "parameter name is null");
		throwValidationExceptionOnNullInput(spec, "parameter spec is null");
		
		LRSpec thespec = new LRSpec();
		
		// add the readers
		thespec.setReaders(new LRSpec.Readers());
		if (spec.getReaders() != null) {
			thespec.getReaders().getReader().addAll(spec.getReaders());
		}
		
		// set if composite reader or basereader
		thespec.setIsComposite(spec.isIsComposite());
		
		// set the properties 
		thespec.setProperties(new LRSpec.Properties());
		for (org.fosstrak.ale.server.readers.gen.LRProperty prop : spec.getLRProperty()) {
			LRProperty property = new LRProperty();
			property.setName(prop.getName());
			property.setValue(prop.getValue());
			thespec.getProperties().getProperty().add(property);
		}
		
		// at the ReaderType property
		LRProperty property = new LRProperty();
		property.setName(LogicalReader.PROPERTY_READER_TYPE);
		property.setValue(spec.getReaderType());
		thespec.getProperties().getProperty().add(property);
		
		define(name, thespec);
	}

	@Override
	public void define(String name, LRSpec spec) throws DuplicateNameException, ValidationException, SecurityException, ImplementationException {
		LOG.debug("define");

		throwValidationExceptionOnNullInput(name, "parameter name is null");
		throwValidationExceptionOnNullInput(spec, "parameter spec is null");
		
		LogicalReader logRead = getReaderProvider().createReader(name, spec);
		// establish connection when basereader
		if (logRead instanceof BaseReader) {
			((BaseReader)logRead).connectReader();
		}
		
		persistenceWriteAPI.writeLRSpec(name, spec);
		
		LOG.debug("saving reader: " + name + " " + logRead.getClass().getCanonicalName());
		logicalReaders.put(name, logRead);

		LOG.debug("successfully executed define");
	}

	/**
	 * initialize the Logical Reader Manager. <br/>
     * <strong>NOTICE:</strong> Do not depend on initializer methods of other autowired components (like ALE) 
     * as the order of the initializer methods on the injected beans is not preset -> thus, the 
     * injected bean might already be present in the manager, but not initialized (call postconstruct) yet.
	 */
	@PostConstruct
	public void initialize() {		
		LOG.debug("initialize");
		initializeFromFile(LOAD_FILEPATH);
	}
	
	public void initializeFromFile(String loadFilePath) {
		
		if (isInitialized()) {
			LOG.debug("already initialized - no need to reinitialize again - aborting");
			return;
		}
		
		LOG.debug("Initialize LogicalReaderManager");
		try {			
			// if configuration file path is not set, set it to default value
			if (loadFilePath == null) {
				loadFilePath = LOAD_FILEPATH;
			}
			
			// try to parse reader configuration file
			LOG.debug("Parse configuration file :" + loadFilePath);
			List<org.fosstrak.ale.server.readers.gen.LogicalReader> genLogicalReaders;
			try {
				// initialize jaxb context and unmarshaller
				JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
				
				// unmarshal logical reader configuration file
				logicalReadersConfiguration = (LogicalReaders) unmarshaller.unmarshal(persistenceProvider.getInitializeInputStream(loadFilePath));
				// trying to validate schema
				
				unmarshaller.setSchema(SCHEMA_FILEPATH);
				boolean isValidating = unmarshaller.getSchema() != null;
				LOG.debug("is schema validated: " + isValidating);
				
				genLogicalReaders = logicalReadersConfiguration.getLogicalReader();
			} catch (JAXBException e) {
				LOG.error("could not initialize the logical reader manager from file: ", e);
				return;
			}
			
			// iterate over logical readers
			for (org.fosstrak.ale.server.readers.gen.LogicalReader logicalReader : genLogicalReaders) {
				// get logical reader name
				String logName = logicalReader.getName();
				org.fosstrak.ale.server.readers.gen.LRSpec spec = logicalReader.getLRSpec();
				define(logName, spec);	
			}
				
			// set initialized to true
			initialized = true;
			LOG.debug("LogicalReaderManager successfully initialized");
	
			LOG.debug("starting the readers");
			for (LogicalReader reader : getLogicalReaders()) {
				reader.start();
			}
		} catch (Exception ex) {
			LOG.error("could not setup the logical reader manager - tear down the application.", ex);
			throw new IllegalStateException("could not setup the logical reader manager - tear down the application.", ex);
		}
	}
	
	/**
	 * This method stores the current setting of logicalreaders to a .xml file.
	 * 
	 * @param storeFilePath configurationFilePath to initialize
	 * @throws ImplementationException whenever something goes wrong inside the implementation 
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws DuplicateNameException when a reader name is already defined
	 * @throws ValidationException the provided LRSpec is invalid
	 * @throws FileNotFoundException the provided file was not found
	 */
	public void storeToFile(String storeFilePath) throws ImplementationException, SecurityException, DuplicateNameException, ValidationException, FileNotFoundException {
		
		LOG.debug("Store LogicalReaderManager");
		
		// if store file path is not set, set it to default value
		if (storeFilePath == null) {
			storeFilePath = STORE_FILEPATH;
		}
		
		// try to generate store file
		LOG.debug("Generate store file");
		
		try {
			// initialize jaxb context and marshaller
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT);
			Marshaller marshaller = context.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			ObjectFactory objFactory = new ObjectFactory();
			
			LogicalReaders genLogReaders = (LogicalReaders) objFactory.createLogicalReaders();
			Iterable<String> names = logicalReaders.keySet();
			for (String name : names){
				LogicalReader logRd = logicalReaders.get(name);
				LRSpec spec = logRd.getLRSpec();
				org.fosstrak.ale.server.readers.gen.LogicalReader genLogRd = objFactory.createLogicalReader();
				genLogRd.setName(logRd.getName());
				genLogRd.setName(name);
				org.fosstrak.ale.server.readers.gen.LRSpec genSpec = objFactory.createLRSpec();
				genSpec.setIsComposite(spec.isIsComposite());
				if (genSpec.isIsComposite()) {
					if (spec.getReaders() != null) {
						for (String readerName : spec.getReaders().getReader()) {
							genSpec.getReaders().add(readerName);
						}
					}
				} else {
					for (LRProperty property : logRd.getProperties()) {
						if (LogicalReader.PROPERTY_READER_TYPE .equals(property.getName())) {
							// skip this property as set further down on the attribute
						} else {
							org.fosstrak.ale.server.readers.gen.LRProperty genProp = objFactory.createLRProperty();
							genProp.setName(property.getName());
							genProp.setValue(property.getValue());
							genSpec.getLRProperty().add(genProp);
						}
					}
				}
				genLogRd.setLRSpec(genSpec);
				genSpec.setReaderType(logRd.getClass().getCanonicalName());
				genLogReaders.getLogicalReader().add(genLogRd);
			}
			// store the file to the file path
			marshaller.marshal(genLogReaders, persistenceProvider.getStreamToWhereToStoreWholeManager(storeFilePath));			

		} catch (JAXBException e) {
			LOG.error("could not store the logical reader manager to file: ", e);
		}
		
		LOG.info("LogicalReaderManager successfully stored");
	}

	@Override
	public LogicalReader getLogicalReader(String readerName) {
		LogicalReader reader = null;
		if (logicalReaders.containsKey(readerName)) {
			reader = logicalReaders.get(readerName);
			
			if (!reader.isStarted()) {
				reader.start();
			}
		}
		return reader;
	}

	@Override
	public Collection<LogicalReader> getLogicalReaders() {
		return logicalReaders.values();
	}

	@Override
	public void setLogicalReader(LogicalReader reader) throws ImplementationException {
		if (logicalReaders.containsKey(reader.getName())) {
			throw new ImplementationException("reader duplicated");
		}
		
		logicalReaders.put(reader.getName(), reader);
	}
	
	private boolean containsNoInitialize(String readerName) {
		return logicalReaders.containsKey(readerName);
	}

	@Override
	public boolean contains(String logicalReaderName) {
		
		// initialize if necessary
		try {
			if (!initialized) {
				initialize();
			}
		} catch (Exception e) {
			LOG.error("could not initialize Logical Reader Manager", e);
		}
		return containsNoInitialize(logicalReaderName);
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}


	/**
	 * assert that the given reader is not null. if so, throws Exception.
	 * @param logRd the logical reader to test.
	 * @param name the name of the reader.
	 * @throws NoSuchNameException if the given reader is null.
	 */
	protected void throwNoSuchNameExceptionIfReaderNull(LogicalReader logRd, String name) throws NoSuchNameException {
		if (null == logRd) {
			String errMsg = String.format("There is no such reader %s", name);
			LOG.debug(errMsg);
			throw new NoSuchNameException(errMsg);
		}
	}

	/**
	 * assert that the given reader is a composite reader. if not, throws exception.
	 * @param logRd the logical reader that is to be tested.
	 * @param name the name of the reader.
	 * @throws NonCompositeReaderException if the given reader is not composite.
	 */
	protected void throwNonCompositeReaderExceptionIfReaderNotComposite(LogicalReader logRd, String name) throws NonCompositeReaderException {
		if (! (logRd instanceof CompositeReader)) {
			String errMsg = "reader " + name + " is not composite";
			LOG.debug(errMsg);
			throw new NonCompositeReaderException(errMsg);
		}
	}

	/**
	 * assert that the given input parameter is not null. if so, throws exception.
	 * @param inputParameter the input parameter to test on null.
	 * @throws ValidationException if the given input parameter is null.
	 */
	protected void throwValidationExceptionOnNullInput(Object inputParameter, String... parameters) throws ValidationException {
		if (null == inputParameter) {
			String errMsg = "given input parameter is null. " + StringUtils.join(parameters);
			LOG.debug(errMsg);
			throw new ValidationException(errMsg);
		}
	}
	
	/**
	 * assert that the given list of readers are all present/defined.
	 * @param readers the readers to test.
	 * @throws ValidationException if at least one of the requested readers is not existing.
	 */
	protected void throwValidationExceptionIfNotAllReadersAvailable(List<String> readers) throws ValidationException {
		for (String readerName : readers) {
			if (!containsNoInitialize(readerName)) {
				throw new ValidationException("the requested reader is not defined: " + readerName);
			}
		}
	}

	/**
	 * a handle to the currently used persistence provider.
	 * @return the currently used persistence provider.
	 */
	public PersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	/**
	 * allow to inject a new persistence provider.
	 * @param persistenceProvider the new persistence provider to be used.
	 */
	@Autowired
	public void setPersistenceProvider(PersistenceProvider persistenceProvider) {
		this.persistenceProvider = persistenceProvider;
	}

	/**
	 * a handle to the currently used reader provider.
	 * @return the currently used reader provider.
	 */
	public ReaderProvider getReaderProvider() {
		return readerProvider;
	}

	/**
	 * allow to inject a new reader provider.
	 * @param readerProvider the new reader provider to be used.
	 */
	@Autowired
	public void setReaderProvider(ReaderProvider readerProvider) {
		this.readerProvider = readerProvider;
	}
	
	/**
	 * allow to inject a new ALESettings.
	 * @param aleSettings the new ALESettings to be used.
	 */
    @Autowired
	public void setAleSettings(ALESettings aleSettings) {
		this.aleSettings = aleSettings;
	}

	/**
	 * allow to inject the persistence delete API.
	 * @param persistenceRemoveAPI the persistence delete API.
	 */
    @Autowired
	public void setPersistenceRemoveAPI(RemoveConfig persistenceRemoveAPI) {
		this.persistenceRemoveAPI = persistenceRemoveAPI;
	}

    /**
     * allow to inject the persistence write API.
     * @param persistenceWriteAPI the persistence write API.
     */
    @Autowired
	public void setPersistenceWriteAPI(WriteConfig persistenceWriteAPI) {
		this.persistenceWriteAPI = persistenceWriteAPI;
	}
}
