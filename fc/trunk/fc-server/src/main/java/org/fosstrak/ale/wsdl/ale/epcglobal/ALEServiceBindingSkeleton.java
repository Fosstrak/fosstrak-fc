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

/**
 * ALEServiceBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
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
        _fault.setName("DuplicateNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"));
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
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
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
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
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
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("InvalidURIExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.InvalidURIException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("DuplicateSubscriptionExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
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
        _fault.setName("ImplementationExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.ImplementationException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"));
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
        _fault.setName("SecurityExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.SecurityException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"));
        _oper.addFault(_fault);
        _fault = new org.apache.axis.description.FaultDesc();
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
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
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
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
        _fault.setName("NoSuchNameExceptionFault");
        _fault.setQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
        _fault.setClassName("org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException");
        _fault.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"));
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
    }

    public ALEServiceBindingSkeleton() {
        this.impl = new org.accada.ale.wsdl.ale.epcglobal.ALEServiceBindingImpl();
    }

    public ALEServiceBindingSkeleton(org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType impl) {
        this.impl = impl;
    }
    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder define(org.accada.ale.wsdl.ale.epcglobal.Define parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.define(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine(org.accada.ale.wsdl.ale.epcglobal.Undefine parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.undefine(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec(org.accada.ale.wsdl.ale.epcglobal.GetECSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException
    {
        org.accada.ale.xsd.ale.epcglobal.ECSpec ret = impl.getECSpec(parms);
        return ret;
    }

    public java.lang.String[] getECSpecNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        java.lang.String[] ret = impl.getECSpecNames(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe(org.accada.ale.wsdl.ale.epcglobal.Subscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.subscribe(parms);
        return ret;
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(org.accada.ale.wsdl.ale.epcglobal.Unsubscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException
    {
        org.accada.ale.wsdl.ale.epcglobal.VoidHolder ret = impl.unsubscribe(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.ECReports poll(org.accada.ale.wsdl.ale.epcglobal.Poll parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException
    {
        org.accada.ale.xsd.ale.epcglobal.ECReports ret = impl.poll(parms);
        return ret;
    }

    public org.accada.ale.xsd.ale.epcglobal.ECReports immediate(org.accada.ale.wsdl.ale.epcglobal.Immediate parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException
    {
        org.accada.ale.xsd.ale.epcglobal.ECReports ret = impl.immediate(parms);
        return ret;
    }

    public java.lang.String[] getSubscribers(org.accada.ale.wsdl.ale.epcglobal.GetSubscribers parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException
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

}
