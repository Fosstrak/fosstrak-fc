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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

	/** a map of all LogicalReaders. the readers are mapped against their name.	 */
	private java.util.Map<String, LogicalReader> logicalReaders = new ConcurrentHashMap<String, LogicalReader>();

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
		return aleSettings.getVendorVersion();
	}

	@Override
	public String getStandardVersion() throws ImplementationException {
		return aleSettings.getLrStandardVersion();
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

	@Override
	public boolean contains(String logicalReaderName) {
		return logicalReaders.containsKey(logicalReaderName);
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
			if (!contains(readerName)) {
				throw new ValidationException("the requested reader is not defined: " + readerName);
			}
		}
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
