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
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

/**
 * binding for the ALE to the binding stub.
 * @author regli
 * @author sawielan
 */
public class ALEServiceBindingImpl implements org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType {

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
		
		Logger log = Logger.getLogger(ALEServiceBindingImpl.class);
		log.debug("got define message");
		
		// create the LRSpec
		org.accada.ale.server.readers.LRSpec spec = new org.accada.ale.server.readers.LRSpec();
		if (parms.getSpec().getIsComposite()) {
			spec.setComposite();
		}
		List<org.accada.ale.server.readers.LRProperty> props = new LinkedList<org.accada.ale.server.readers.LRProperty>();
		for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : parms.getSpec().getProperties()) {
			props.add(new org.accada.ale.server.readers.LRProperty(prop.getName(), prop.getValue()));
		}
		spec.setProperties(props);
		
		List<String> readers = new LinkedList<String>();
		for (String reader : parms.getSpec().getReaders()) {
			readers.add(reader);
		}
		spec.setReaders(readers);
		
		// FIXME determine reader-type from vendor-extension
		//spec.setReaderType(parms.getSpec().getReaderType());
	
		LogicalReaderManager.define(parms.getName(), spec);
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult updateReader(org.accada.ale.wsdl.ale.epcglobal.UpdateReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		// create the LRSpec
		org.accada.ale.server.readers.LRSpec spec = new org.accada.ale.server.readers.LRSpec();
		if (parms.getSpec().getIsComposite()) {
			spec.setComposite();
		}
		List<org.accada.ale.server.readers.LRProperty> props = new LinkedList<org.accada.ale.server.readers.LRProperty>();
		for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : parms.getSpec().getProperties()) {
			props.add(new org.accada.ale.server.readers.LRProperty(prop.getName(), prop.getValue()));
		}
		spec.setProperties(props);
		
		List<String> readers = new LinkedList<String>();
		for (String reader : parms.getSpec().getReaders()) {
			readers.add(reader);
		}
		spec.setReaders(readers);
		// FIXME determine readerType from vendor extension
		//spec.setReaderType(parms.getSpec().getReaderType());
	
		LogicalReaderManager.update(parms.getName(), spec);
		return null;

	}

	public org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult undefineReader(org.accada.ale.wsdl.ale.epcglobal.UndefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
		LogicalReaderManager.undefine(parms.getName());    	

		return null;
	}

	public java.lang.String[] getLogicalReaderNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
    	System.out.println("calling LogicalReaderManager to get the logicalReaderNames");
    	List<String> lrmReaders = LogicalReaderManager.getLogicalReaderNames();
    	String[] readers = new String[lrmReaders.size()];
    	for (int i=0; i<lrmReaders.size(); i++) {
    		readers[i] = lrmReaders.get(i);
    	}
    	
    	return readers;
	}

	public org.accada.ale.xsd.ale.epcglobal.LRSpec getLRSpec(org.accada.ale.wsdl.ale.epcglobal.GetLRSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
		org.accada.ale.server.readers.LRSpec lrspec = LogicalReaderManager.getLRSpec(parms.getName());
		org.accada.ale.xsd.ale.epcglobal.LRSpec spec = new org.accada.ale.xsd.ale.epcglobal.LRSpec();
		spec.setIsComposite(lrspec.isComposite());
		spec.setProperties(objectArrayToLRPropertiesArray(lrspec.getProperties().toArray()));
		spec.setReaders(objectArrayToStringArray(lrspec.getReaders().toArray()));
		// FIXME set readerType in vendor extension
		
		return spec;		
	}

	public org.accada.ale.wsdl.ale.epcglobal.AddReadersResult addReaders(org.accada.ale.wsdl.ale.epcglobal.AddReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		LogicalReaderManager.addReaders(parms.getName(), Arrays.asList(parms.getReaders()));
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.SetReadersResult setReaders(org.accada.ale.wsdl.ale.epcglobal.SetReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		LogicalReaderManager.setReaders(parms.getName(), Arrays.asList(parms.getReaders()));
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult removeReaders(org.accada.ale.wsdl.ale.epcglobal.RemoveReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException 
	{
		LogicalReaderManager.removeReaders(parms.getName(), Arrays.asList(parms.getReaders()));
		return null;
	}

	public org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult setProperties(org.accada.ale.wsdl.ale.epcglobal.SetProperties parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ValidationException 
	{
		List<org.accada.ale.server.readers.LRProperty> props = new ArrayList<org.accada.ale.server.readers.LRProperty>();
		for (org.accada.ale.xsd.ale.epcglobal.LRProperty prop : parms.getProperties()) {
			props.add(new org.accada.ale.server.readers.LRProperty(prop.getName(), prop.getValue()));
		}
		
		LogicalReaderManager.setProperties(parms.getName(), props);
		return null;
	}

	public java.lang.String getPropertyValue(org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException 
	{
		return LogicalReaderManager.getPropertyValue(parms.getName(), parms.getPropertyName());
	}

	
	private java.lang.String[] objectArrayToStringArray(Object[] arr) {
		java.lang.String[] narr = new java.lang.String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			narr[i] = (String) arr[i];
		}
		return narr;
	}
	
	private org.accada.ale.xsd.ale.epcglobal.LRProperty[] objectArrayToLRPropertiesArray(Object[] arr) {
		org.accada.ale.xsd.ale.epcglobal.LRProperty[] narr = new org.accada.ale.xsd.ale.epcglobal.LRProperty[arr.length];
		for (int i = 0; i < arr.length; i++) {
			narr[i] = (org.accada.ale.xsd.ale.epcglobal.LRProperty) arr[i];
		}
		return narr;
	}
}

