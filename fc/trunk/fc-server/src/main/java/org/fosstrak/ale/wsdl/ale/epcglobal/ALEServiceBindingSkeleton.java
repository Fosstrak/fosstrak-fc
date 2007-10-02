/**
 * ALEServiceBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.wsdl.ale.epcglobal;

public class ALEServiceBindingSkeleton implements org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType, org.apache.axis.wsdl.Skeleton {
    private org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Define"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Define"), org.accada.ale.wsdl.ale.epcglobal.Define.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("define", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "define"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("define") == null) {
            _myOperations.put("define", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("define")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ECSpecValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("DuplicateNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Undefine"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Undefine"), org.accada.ale.wsdl.ale.epcglobal.Undefine.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("undefine", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "undefine"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("undefine") == null) {
            _myOperations.put("undefine", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("undefine")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpec"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpec"), org.accada.ale.wsdl.ale.epcglobal.GetECSpec.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getECSpec", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpecResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpec"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getECSpec"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getECSpec") == null) {
            _myOperations.put("getECSpec", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getECSpec")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpecNames"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getECSpecNames", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpecNamesResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getECSpecNames"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getECSpecNames") == null) {
            _myOperations.put("getECSpecNames", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getECSpecNames")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Subscribe"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Subscribe"), org.accada.ale.wsdl.ale.epcglobal.Subscribe.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("subscribe", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "subscribe"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("subscribe") == null) {
            _myOperations.put("subscribe", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("subscribe")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("DuplicateSubscriptionExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InvalidURIExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InvalidURIException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Unsubscribe"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Unsubscribe"), org.accada.ale.wsdl.ale.epcglobal.Unsubscribe.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("unsubscribe", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "unsubscribe"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("unsubscribe") == null) {
            _myOperations.put("unsubscribe", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("unsubscribe")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InvalidURIExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InvalidURIException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchSubscriberExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchSubscriberException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchSubscriberException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Poll"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Poll"), org.accada.ale.wsdl.ale.epcglobal.Poll.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("poll", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "PollResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReports"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "poll"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("poll") == null) {
            _myOperations.put("poll", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("poll")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Immediate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Immediate"), org.accada.ale.wsdl.ale.epcglobal.Immediate.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("immediate", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmediateResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReports"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "immediate"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("immediate") == null) {
            _myOperations.put("immediate", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("immediate")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ECSpecValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribers"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribers"), org.accada.ale.wsdl.ale.epcglobal.GetSubscribers.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getSubscribers", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribersResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getSubscribers"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSubscribers") == null) {
            _myOperations.put("getSubscribers", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSubscribers")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetStandardVersion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getStandardVersion", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetStandardVersionResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getStandardVersion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getStandardVersion") == null) {
            _myOperations.put("getStandardVersion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getStandardVersion")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetVendorVersion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getVendorVersion", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetVendorVersionResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getVendorVersion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVendorVersion") == null) {
            _myOperations.put("getVendorVersion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVendorVersion")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReader"), org.accada.ale.wsdl.ale.epcglobal.DefineReader.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("defineReader", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReaderResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">DefineReaderResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "defineReader"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("defineReader") == null) {
            _myOperations.put("defineReader", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("defineReader")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("DuplicateNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReader"), org.accada.ale.wsdl.ale.epcglobal.UpdateReader.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("updateReader", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReaderResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">UpdateReaderResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "updateReader"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("updateReader") == null) {
            _myOperations.put("updateReader", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("updateReader")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImmutableReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InUseExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InUseException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ReaderLoopExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReader"), org.accada.ale.wsdl.ale.epcglobal.UndefineReader.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("undefineReader", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReaderResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">UndefineReaderResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "undefineReader"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("undefineReader") == null) {
            _myOperations.put("undefineReader", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("undefineReader")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImmutableReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InUseExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InUseException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLogicalReaderNames"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getLogicalReaderNames", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLogicalReaderNamesResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getLogicalReaderNames"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getLogicalReaderNames") == null) {
            _myOperations.put("getLogicalReaderNames", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getLogicalReaderNames")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpec"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpec"), org.accada.ale.wsdl.ale.epcglobal.GetLRSpec.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getLRSpec", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpecResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRSpec"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getLRSpec"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getLRSpec") == null) {
            _myOperations.put("getLRSpec", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getLRSpec")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReaders"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReaders"), org.accada.ale.wsdl.ale.epcglobal.AddReaders.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("addReaders", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReadersResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">AddReadersResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "addReaders"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("addReaders") == null) {
            _myOperations.put("addReaders", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("addReaders")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImmutableReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InUseExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InUseException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NonCompositeReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ReaderLoopExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReaders"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReaders"), org.accada.ale.wsdl.ale.epcglobal.SetReaders.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setReaders", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReadersResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetReadersResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "setReaders"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setReaders") == null) {
            _myOperations.put("setReaders", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setReaders")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImmutableReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InUseExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InUseException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NonCompositeReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ReaderLoopExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReaders"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReaders"), org.accada.ale.wsdl.ale.epcglobal.RemoveReaders.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("removeReaders", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReadersResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">RemoveReadersResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "removeReaders"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("removeReaders") == null) {
            _myOperations.put("removeReaders", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("removeReaders")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImmutableReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InUseExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InUseException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NonCompositeReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetProperties"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetProperties"), org.accada.ale.wsdl.ale.epcglobal.SetProperties.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setProperties", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetPropertiesResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetPropertiesResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "setProperties"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setProperties") == null) {
            _myOperations.put("setProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setProperties")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImmutableReaderExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InUseExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InUseException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ValidationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ValidationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"));
        _oper.addFault(_fault);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValue"), org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getPropertyValue", _params, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValueResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("", "getPropertyValue"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPropertyValue") == null) {
            _myOperations.put("getPropertyValue", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPropertyValue")).add(_oper);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
    }

    public ALEServiceBindingSkeleton() {
        this.impl = new org.accada.ale.wsdl.ale.epcglobal.ALEServiceBindingImpl();
    }

    public ALEServiceBindingSkeleton(org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType impl) {
        this.impl = impl;
    }
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder define(org.accada.ale.wsdl.ale.epcglobal.Define parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.define(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine(org.accada.ale.wsdl.ale.epcglobal.Undefine parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.undefine(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec(org.accada.ale.wsdl.ale.epcglobal.GetECSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.xsd.ale.epcglobal.ECSpec ret = impl.getECSpec(parms);
        return ret;
    }

    public java.lang.String[] getECSpecNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        java.lang.String[] ret = impl.getECSpecNames(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe(org.accada.ale.wsdl.ale.epcglobal.Subscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.subscribe(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(org.accada.ale.wsdl.ale.epcglobal.Unsubscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.unsubscribe(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.ECReports poll(org.accada.ale.wsdl.ale.epcglobal.Poll parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.xsd.ale.epcglobal.ECReports ret = impl.poll(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.ECReports immediate(org.accada.ale.wsdl.ale.epcglobal.Immediate parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.xsd.ale.epcglobal.ECReports ret = impl.immediate(parms);
        return ret;
    }

    public java.lang.String[] getSubscribers(org.accada.ale.wsdl.ale.epcglobal.GetSubscribers parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        java.lang.String[] ret = impl.getSubscribers(parms);
        return ret;
    }

    public java.lang.String getStandardVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException
    {
        java.lang.String ret = impl.getStandardVersion(parms);
        return ret;
    }

    public java.lang.String getVendorVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException
    {
        java.lang.String ret = impl.getVendorVersion(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult defineReader(org.accada.ale.wsdl.ale.epcglobal.DefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ValidationException
    {
        org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult ret = impl.defineReader(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult updateReader(org.accada.ale.wsdl.ale.epcglobal.UpdateReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException
    {
        org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult ret = impl.updateReader(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult undefineReader(org.accada.ale.wsdl.ale.epcglobal.UndefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult ret = impl.undefineReader(parms);
        return ret;
    }

    public java.lang.String[] getLogicalReaderNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        java.lang.String[] ret = impl.getLogicalReaderNames(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.LRSpec getLRSpec(org.accada.ale.wsdl.ale.epcglobal.GetLRSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.xsd.ale.epcglobal.LRSpec ret = impl.getLRSpec(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.AddReadersResult addReaders(org.accada.ale.wsdl.ale.epcglobal.AddReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException
    {
        org.accada.ale.wsdl.ale.epcglobal.AddReadersResult ret = impl.addReaders(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.SetReadersResult setReaders(org.accada.ale.wsdl.ale.epcglobal.SetReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException
    {
        org.accada.ale.wsdl.ale.epcglobal.SetReadersResult ret = impl.setReaders(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult removeReaders(org.accada.ale.wsdl.ale.epcglobal.RemoveReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException
    {
        org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult ret = impl.removeReaders(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult setProperties(org.accada.ale.wsdl.ale.epcglobal.SetProperties parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ValidationException
    {
        org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult ret = impl.setProperties(parms);
        return ret;
    }

    public java.lang.String getPropertyValue(org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        java.lang.String ret = impl.getPropertyValue(parms);
        return ret;
    }

}
