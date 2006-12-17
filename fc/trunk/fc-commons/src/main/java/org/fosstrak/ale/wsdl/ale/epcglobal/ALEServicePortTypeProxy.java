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

package org.accada.ale.wsdl.ale.epcglobal;

public class ALEServicePortTypeProxy implements org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType {
  private String _endpoint = null;
  private org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType aLEServicePortType = null;
  
  public ALEServicePortTypeProxy() {
    _initALEServicePortTypeProxy();
  }
  
  private void _initALEServicePortTypeProxy() {
    try {
      aLEServicePortType = (new org.accada.ale.wsdl.ale.epcglobal.ALEServiceLocator()).getALEServicePort();
      if (aLEServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)aLEServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)aLEServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (aLEServicePortType != null)
      ((javax.xml.rpc.Stub)aLEServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType getALEServicePortType() {
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType;
  }
  
  public org.accada.ale.wsdl.ale.epcglobal.VoidHolder define(org.accada.ale.wsdl.ale.epcglobal.Define parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.define(parms);
  }
  
  public org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine(org.accada.ale.wsdl.ale.epcglobal.Undefine parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.undefine(parms);
  }
  
  public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec(org.accada.ale.wsdl.ale.epcglobal.GetECSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.getECSpec(parms);
  }
  
  public java.lang.String[] getECSpecNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.getECSpecNames(parms);
  }
  
  public org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe(org.accada.ale.wsdl.ale.epcglobal.Subscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.subscribe(parms);
  }
  
  public org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(org.accada.ale.wsdl.ale.epcglobal.Unsubscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.unsubscribe(parms);
  }
  
  public org.accada.ale.xsd.ale.epcglobal.ECReports poll(org.accada.ale.wsdl.ale.epcglobal.Poll parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.poll(parms);
  }
  
  public org.accada.ale.xsd.ale.epcglobal.ECReports immediate(org.accada.ale.wsdl.ale.epcglobal.Immediate parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.immediate(parms);
  }
  
  public java.lang.String[] getSubscribers(org.accada.ale.wsdl.ale.epcglobal.GetSubscribers parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.getSubscribers(parms);
  }
  
  public java.lang.String getStandardVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.getStandardVersion(parms);
  }
  
  public java.lang.String getVendorVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException{
    if (aLEServicePortType == null)
      _initALEServicePortTypeProxy();
    return aLEServicePortType.getVendorVersion(parms);
  }
  
  
}