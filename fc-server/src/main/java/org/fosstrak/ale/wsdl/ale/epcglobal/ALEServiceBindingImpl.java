/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Accada (www.accada.org).
 *
 * Accada is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Accada is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Accada; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.accada.ale.wsdl.ale.epcglobal;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.accada.ale.server.ALE;
import org.accada.ale.server.readers.LogicalReaderManager;
import org.accada.ale.xsd.ale.epcglobal.LRLogicalReaders;
import org.accada.ale.xsd.ale.epcglobal.LRProperties;
import org.accada.ale.xsd.ale.epcglobal.LRSpecExtension;
import org.apache.log4j.Logger;

/**
 * binding for the ALE to the binding stub.
 * @author regli
 * @author sawielan
 */
public class ALEServiceBindingImpl implements org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType {

	private static Logger log = Logger.getLogger(ALEServiceBindingImpl.class);
	
	/**
	 * constructor that initializes the ale if not already done.
	 */
	public ALEServiceBindingImpl() {
		
		if (!ALE.isReady()) {
			try {
				ALE.initialize();
			} catch (ImplementationException e) {
				e.printStackTrace();
			}
		}
		
	}
	
    @Override
	public org.accada.ale.wsdl.ale.epcglobal.VoidHolder define(org.accada.ale.wsdl.ale.epcglobal.Define parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
    	
		ALE.define(parms.getSpecName(), parms.getSpec());
		return null;
    	
    }

    @Override
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine(org.accada.ale.wsdl.ale.epcglobal.Undefine parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException {
    	
    	ALE.undefine(parms.getSpecName());
    	return null;
        
    }

    @Override
    public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec(org.accada.ale.wsdl.ale.epcglobal.GetECSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException {
    	
    	return ALE.getECSpec(parms.getSpecName());
    	
    }

    @Override
    public java.lang.String[] getECSpecNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {

    	return ALE.getECSpecNames();
    	
    }

    @Override
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe(org.accada.ale.wsdl.ale.epcglobal.Subscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException {
    	
    	ALE.subscribe(parms.getSpecName(), parms.getNotificationURI());
    	return null;
        
    }

    @Override
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(org.accada.ale.wsdl.ale.epcglobal.Unsubscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException {
    	
    	ALE.unsubscribe(parms.getSpecName(), parms.getNotificationURI());
        return null;
        
    }

    @Override
    public org.accada.ale.xsd.ale.epcglobal.ECReports poll(org.accada.ale.wsdl.ale.epcglobal.Poll parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException {
    	
    	return ALE.poll(parms.getSpecName());

    }

    @Override
    public org.accada.ale.xsd.ale.epcglobal.ECReports immediate(org.accada.ale.wsdl.ale.epcglobal.Immediate parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {

    	return ALE.immediate(parms.getSpec());
    	
    }

    @Override
    public java.lang.String[] getSubscribers(org.accada.ale.wsdl.ale.epcglobal.GetSubscribers parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException {

    	return ALE.getSubscribers(parms.getSpecName());
    
    }

    @Override
    public java.lang.String getStandardVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException {

    	return ALE.getStandardVersion();
    	
    }

    @Override
    public java.lang.String getVendorVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException {
        
    	return ALE.getVendorVersion();
    	
    }
	
	public org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult defineReader(org.accada.ale.wsdl.ale.epcglobal.DefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		log.debug("defineReader " + parms.getName());
		
		if (parms.getSpec() == null) {
			log.error("no specification!!!");
			throw new ImplementationException("no specification - reader not created", ImplementationExceptionSeverity.ERROR);
		}
			
		// create the LRSpec
		org.accada.ale.server.readers.LRSpec spec = new org.accada.ale.server.readers.LRSpec();
		if (parms.getSpec().getIsComposite()) {
			spec.setComposite();
		}
		
		// generate the properties
		List<org.accada.ale.server.readers.LRProperty> props = new LinkedList<org.accada.ale.server.readers.LRProperty>();
		if (parms.getSpec().getProperties() != null) {
			if (parms.getSpec().getProperties().getProperty() != null) {
				for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : parms.getSpec().getProperties().getProperty()) {
					props.add(new org.accada.ale.server.readers.LRProperty(prop.getName(), prop.getValue()));
				}
			}
		}
		spec.setProperties(props);
		
		// generate the readers
		List<String> readers = new LinkedList<String>();
		if (parms.getSpec().getReaders() != null) {
			if (parms.getSpec().getReaders().getLogicalReader() != null) {
				for (String reader : parms.getSpec().getReaders().getLogicalReader()) {
					readers.add(reader);
				}
			}
		}
		spec.setReaders(readers);
		
		// set the reader type
		if (parms.getSpec().getExtension() == null) {
			log.error("spec extension empty");
			throw new ImplementationException("spec extension empty - reader not defined", ImplementationExceptionSeverity.ERROR);
		} 
		if (parms.getSpec().getExtension().getReaderType() == null) {
			log.error("readertype in specExtension not set");
			throw new ImplementationException("readertype in specExtension not set - reader not defined", ImplementationExceptionSeverity.ERROR);
		}
		spec.setReaderType(parms.getSpec().getExtension().getReaderType());
	
		LogicalReaderManager.define(parms.getName(), spec);
		log.debug("defineReader done");
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult updateReader(org.accada.ale.wsdl.ale.epcglobal.UpdateReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		log.debug("updateReader " + parms.getName());
		
		if (parms.getSpec() == null) {
			log.error("no specification!!!");
			throw new ImplementationException("no specification - reader not updated", ImplementationExceptionSeverity.ERROR);
		}
			
		// create the LRSpec
		org.accada.ale.server.readers.LRSpec spec = new org.accada.ale.server.readers.LRSpec();
		if (parms.getSpec().getIsComposite()) {
			spec.setComposite();
		}
		
		// generate the properties
		List<org.accada.ale.server.readers.LRProperty> props = new LinkedList<org.accada.ale.server.readers.LRProperty>();
		if (parms.getSpec().getProperties() != null) {
			if (parms.getSpec().getProperties().getProperty() != null) {
				for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : parms.getSpec().getProperties().getProperty()) {
					props.add(new org.accada.ale.server.readers.LRProperty(prop.getName(), prop.getValue()));
				}
			}
		}
		spec.setProperties(props);
		
		// generate the readers
		List<String> readers = new LinkedList<String>();
		if (parms.getSpec().getReaders() != null) {
			if (parms.getSpec().getReaders().getLogicalReader() != null) {
				for (String reader : parms.getSpec().getReaders().getLogicalReader()) {
					readers.add(reader);
				}
			}
		}
		spec.setReaders(readers);
		
		// set the reader type
		if (parms.getSpec().getExtension() == null) {
			log.error("spec extension empty");
			throw new ImplementationException("spec extension empty - reader not defined", ImplementationExceptionSeverity.ERROR);
		} 
		if (parms.getSpec().getExtension().getReaderType() == null) {
			log.error("readertype in specExtension not set");
			throw new ImplementationException("readertype in specExtension not set - reader not defined", ImplementationExceptionSeverity.ERROR);
		}
		spec.setReaderType(parms.getSpec().getExtension().getReaderType());
		
		LogicalReaderManager.update(parms.getName(), spec);
		log.debug("updateReader done");
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult undefineReader(org.accada.ale.wsdl.ale.epcglobal.UndefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
		LogicalReaderManager.undefine(parms.getName());    	

		return null;
	}

	/**
	 * returns a list containing all logical reader names in the LogicalReaderManager.
	 * @param parms holder for void
	 * @return an array of String holding the logical reader names
	 */
	public java.lang.String[] getLogicalReaderNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
    	List<String> lrmReaders = LogicalReaderManager.getLogicalReaderNames();
    	String[] readers = new String[lrmReaders.size()];
    	for (int i = 0; i < lrmReaders.size(); i++) {
    		readers[i] = lrmReaders.get(i);
    	}
    	
    	return readers;
	}

	/**
	 * returns an LRSpec of the reader specified.
	 * @param parms Serialization for the name of the reader 
	 * @return an LRSpec for the specified reader
	 */
	public org.accada.ale.xsd.ale.epcglobal.LRSpec getLRSpec(org.accada.ale.wsdl.ale.epcglobal.GetLRSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
		org.accada.ale.server.readers.LRSpec lrspec = LogicalReaderManager.getLRSpec(parms.getName());
		org.accada.ale.xsd.ale.epcglobal.LRSpec spec = null;
		
		// create a new LRSpec for the return-type
		if (lrspec != null) {
			spec = new org.accada.ale.xsd.ale.epcglobal.LRSpec();
			spec.setIsComposite(lrspec.isComposite());
			
			LRLogicalReaders lr = new LRLogicalReaders();
			lr.setLogicalReader(stringListToStringArray(lrspec.getReaders()));
			spec.setReaders(lr);
			
			LRProperties lrprops = new LRProperties();
			lrprops.setProperty(propertiesListToLRPropertiesArray(lrspec.getProperties()));
			spec.setProperties(lrprops);
			
			// create a new LRSpecExtension and store the reader type in this extension
			LRSpecExtension extension = new LRSpecExtension();
			extension.setReaderType(lrspec.getReaderType());
			spec.setExtension(extension);
		}
		return spec;		
	}

	public org.accada.ale.wsdl.ale.epcglobal.AddReadersResult addReaders(org.accada.ale.wsdl.ale.epcglobal.AddReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		LogicalReaderManager.addReaders(parms.getName(), Arrays.asList(parms.getReaders().getLogicalReader()));
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.SetReadersResult setReaders(org.accada.ale.wsdl.ale.epcglobal.SetReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		LogicalReaderManager.setReaders(parms.getName(), Arrays.asList(parms.getReaders().getLogicalReader()));
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult removeReaders(org.accada.ale.wsdl.ale.epcglobal.RemoveReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException 
	{
		LogicalReaderManager.removeReaders(parms.getName(), Arrays.asList(parms.getReaders().getLogicalReader()));
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult setProperties(org.accada.ale.wsdl.ale.epcglobal.SetProperties parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		List<org.accada.ale.server.readers.LRProperty> props = new ArrayList<org.accada.ale.server.readers.LRProperty>();
		if (parms.getProperties().getProperty() == null) {
			throw new ImplementationException("no properties defined! - not updating reader", ImplementationExceptionSeverity.ERROR);
		}
		
		for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : parms.getProperties().getProperty()) {
			props.add(new org.accada.ale.server.readers.LRProperty(prop.getName(), prop.getValue()));
		}
		
		LogicalReaderManager.setProperties(parms.getName(), props);
		return null;
	}

	public java.lang.String getPropertyValue(org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
		return LogicalReaderManager.getPropertyValue(parms.getName(), parms.getPropertyName());
	}

	/**
	 * converts a List&gt;String&lt; into a string array.
	 * @param list List&gt;String&lt; to be converted
	 * @return string array
	 */
	private java.lang.String[] stringListToStringArray(List<String> list) {
		if (list == null) {
			return null;
		}
		
		Object[] arr = list.toArray();
		if (arr == null) {
			return null;
		}
		
		java.lang.String[] narr = new java.lang.String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			narr[i] = (String) arr[i];
		}
		return narr;
		
	}
	
	/**
	 * converts List containing LRProperties into a org.accada.ale.xsd.ale.epcglobal.LRPropertys array.
	 * @param list List&gt;org.accada.ale.server.readers.LRProperty&lt; to be converted into an array 
	 * @return array of org.accada.ale.xsd.ale.epcglobal.LRProperty
	 */
	private org.accada.ale.xsd.ale.epcglobal.LRProperty[] propertiesListToLRPropertiesArray(List<org.accada.ale.server.readers.LRProperty> list) {
		if (list == null) {
			return null;
		}
		
		Object[] arr = list.toArray();
		if (arr == null) {
			return null;
		}
		
		org.accada.ale.xsd.ale.epcglobal.LRProperty[] narr = new org.accada.ale.xsd.ale.epcglobal.LRProperty[arr.length];
		for (int i = 0; i < arr.length; i++) {
			org.accada.ale.server.readers.LRProperty p = (org.accada.ale.server.readers.LRProperty) arr[i];
			narr[i] = new org.accada.ale.xsd.ale.epcglobal.LRProperty(p.getName(), p.getValue());
		}
		return narr;
	}
}

