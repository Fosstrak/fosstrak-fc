/**
 * ALEServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.wsdl.ale.epcglobal;

public class ALEServiceLocator extends org.apache.axis.client.Service implements org.accada.ale.wsdl.ale.epcglobal.ALEService {

    public ALEServiceLocator() {
    }


    public ALEServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ALEServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ALEServicePort
    private java.lang.String ALEServicePort_address = "http://localhost:8080/ALEWebService/services/ALEServicePort";

    public java.lang.String getALEServicePortAddress() {
        return ALEServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ALEServicePortWSDDServiceName = "ALEServicePort";

    public java.lang.String getALEServicePortWSDDServiceName() {
        return ALEServicePortWSDDServiceName;
    }

    public void setALEServicePortWSDDServiceName(java.lang.String name) {
        ALEServicePortWSDDServiceName = name;
    }

    public org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType getALEServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ALEServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getALEServicePort(endpoint);
    }

    public org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType getALEServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.accada.ale.wsdl.ale.epcglobal.ALEServiceBindingStub _stub = new org.accada.ale.wsdl.ale.epcglobal.ALEServiceBindingStub(portAddress, this);
            _stub.setPortName(getALEServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setALEServicePortEndpointAddress(java.lang.String address) {
        ALEServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.accada.ale.wsdl.ale.epcglobal.ALEServiceBindingStub _stub = new org.accada.ale.wsdl.ale.epcglobal.ALEServiceBindingStub(new java.net.URL(ALEServicePort_address), this);
                _stub.setPortName(getALEServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ALEServicePort".equals(inputPortName)) {
            return getALEServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ALEService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ALEServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ALEServicePort".equals(portName)) {
            setALEServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
