/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * ALEServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.accada.ale.wsdl.ale.epcglobal;

public interface ALEServicePortType extends java.rmi.Remote {
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder define(org.accada.ale.wsdl.ale.epcglobal.Define parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException;
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine(org.accada.ale.wsdl.ale.epcglobal.Undefine parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
    public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec(org.accada.ale.wsdl.ale.epcglobal.GetECSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
    public java.lang.String[] getECSpecNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException;
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe(org.accada.ale.wsdl.ale.epcglobal.Subscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(org.accada.ale.wsdl.ale.epcglobal.Unsubscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
    public org.accada.ale.xsd.ale.epcglobal.ECReports poll(org.accada.ale.wsdl.ale.epcglobal.Poll parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
    public org.accada.ale.xsd.ale.epcglobal.ECReports immediate(org.accada.ale.wsdl.ale.epcglobal.Immediate parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException;
    public java.lang.String[] getSubscribers(org.accada.ale.wsdl.ale.epcglobal.GetSubscribers parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException;
    public java.lang.String getStandardVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
    public java.lang.String getVendorVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException;
}
