/**
 * ALEServiceBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.wsdl.ale.epcglobal;

public class ALEServiceBindingStub extends org.apache.axis.client.Stub implements org.accada.ale.wsdl.ale.epcglobal.ALEServicePortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[21];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("define");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Define"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Define"), org.accada.ale.wsdl.ale.epcglobal.Define.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("undefine");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Undefine"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Undefine"), org.accada.ale.wsdl.ale.epcglobal.Undefine.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getECSpec");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpec"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpec"), org.accada.ale.wsdl.ale.epcglobal.GetECSpec.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpec"));
        oper.setReturnClass(org.accada.ale.xsd.ale.epcglobal.ECSpec.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpecResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getECSpecNames");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpecNames"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpecNamesResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "string"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("subscribe");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Subscribe"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Subscribe"), org.accada.ale.wsdl.ale.epcglobal.Subscribe.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException"),
                      "org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InvalidURIException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unsubscribe");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Unsubscribe"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Unsubscribe"), org.accada.ale.wsdl.ale.epcglobal.Unsubscribe.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InvalidURIException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchSubscriberException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchSubscriberException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("poll");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Poll"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Poll"), org.accada.ale.wsdl.ale.epcglobal.Poll.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReports"));
        oper.setReturnClass(org.accada.ale.xsd.ale.epcglobal.ECReports.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "PollResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("immediate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Immediate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Immediate"), org.accada.ale.wsdl.ale.epcglobal.Immediate.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReports"));
        oper.setReturnClass(org.accada.ale.xsd.ale.epcglobal.ECReports.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmediateResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSubscribers");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribers"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribers"), org.accada.ale.wsdl.ale.epcglobal.GetSubscribers.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribersResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "string"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getStandardVersion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetStandardVersion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetStandardVersionResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVendorVersion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetVendorVersion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetVendorVersionResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("defineReader");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReader"), org.accada.ale.wsdl.ale.epcglobal.DefineReader.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">DefineReaderResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReaderResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"), 
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateReader");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReader"), org.accada.ale.wsdl.ale.epcglobal.UpdateReader.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">UpdateReaderResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReaderResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InUseException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"), 
                      true
                     ));
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("undefineReader");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReader"), org.accada.ale.wsdl.ale.epcglobal.UndefineReader.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">UndefineReaderResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReaderResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InUseException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLogicalReaderNames");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLogicalReaderNames"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms"), org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLogicalReaderNamesResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "string"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLRSpec");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpec"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpec"), org.accada.ale.wsdl.ale.epcglobal.GetLRSpec.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRSpec"));
        oper.setReturnClass(org.accada.ale.xsd.ale.epcglobal.LRSpec.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpecResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addReaders");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReaders"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReaders"), org.accada.ale.wsdl.ale.epcglobal.AddReaders.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">AddReadersResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.AddReadersResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReadersResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InUseException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"), 
                      true
                     ));
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setReaders");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReaders"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReaders"), org.accada.ale.wsdl.ale.epcglobal.SetReaders.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetReadersResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.SetReadersResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReadersResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InUseException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"), 
                      true
                     ));
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("removeReaders");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReaders"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReaders"), org.accada.ale.wsdl.ale.epcglobal.RemoveReaders.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">RemoveReadersResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReadersResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InUseException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException"), 
                      true
                     ));
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setProperties");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetProperties"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetProperties"), org.accada.ale.wsdl.ale.epcglobal.SetProperties.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetPropertiesResult"));
        oper.setReturnClass(org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetPropertiesResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"),
                      "org.accada.ale.wsdl.ale.epcglobal.InUseException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ValidationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException"), 
                      true
                     ));
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPropertyValue");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValue"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValue"), org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValueResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"),
                      "org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"),
                      "org.accada.ale.wsdl.ale.epcglobal.ImplementationException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"),
                      "org.accada.ale.wsdl.ale.epcglobal.SecurityException",
                      new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException"), 
                      true
                     ));
        _operations[20] = oper;

    }

    public ALEServiceBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ALEServiceBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ALEServiceBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">AddReaders>readers");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "reader");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">AddReadersResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.AddReadersResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">DefineReaderResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">RemoveReaders>readers");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "reader");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">RemoveReadersResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetProperties>properties");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.LRProperty[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRProperty");
            qName2 = new javax.xml.namespace.QName("", "property");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetPropertiesResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetReaders>readers");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "reader");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">SetReadersResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.SetReadersResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">UndefineReaderResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", ">UpdateReaderResult");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "AddReaders");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.AddReaders.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ALEException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ALEException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ArrayOfString");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Define");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.Define.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DefineReader");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.DefineReader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateNameException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "DuplicateSubscriptionException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ECSpecValidationException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "EmptyParms");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.EmptyParms.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetECSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.GetECSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetLRSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.GetLRSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetPropertyValue");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "GetSubscribers");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.GetSubscribers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Immediate");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.Immediate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImmutableReaderException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ImplementationException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ImplementationExceptionSeverity");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ImplementationExceptionSeverity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InUseException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.InUseException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "InvalidURIException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.InvalidURIException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NonCompositeReaderException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchNameException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "NoSuchSubscriberException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Poll");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.Poll.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ReaderLoopException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "RemoveReaders");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.RemoveReaders.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SecurityException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.SecurityException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetProperties");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.SetProperties.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "SetReaders");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.SetReaders.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Subscribe");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.Subscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Undefine");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.Undefine.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UndefineReader");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.UndefineReader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Unsubscribe");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.Unsubscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "UpdateReader");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.UpdateReader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "ValidationException");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.ValidationException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "VoidHolder");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", ">LRSpec>properties");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.LRProperty[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRProperty");
            qName2 = new javax.xml.namespace.QName("", "property");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", ">LRSpec>readers");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "reader");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECBoundarySpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECBoundarySpecExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECExcludePatterns");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "excludePattern");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECFilterSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECFilterSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECFilterSpecExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECFilterSpecExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECGroupSpec");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "pattern");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECIncludePatterns");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "includePattern");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECLogicalReaders");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "logicalReader");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReport");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroup");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupCount");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupCount.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupCountExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupCountExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupList");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupListExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupListExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupListMember");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupListMemberExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportList");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReport[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReport");
            qName2 = new javax.xml.namespace.QName("", "report");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportOutputSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportOutputSpecExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReports");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReports.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSetEnum");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportSetEnum.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSetSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportsExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportsExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpecExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportSpecExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpecs");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECReportSpec[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpec");
            qName2 = new javax.xml.namespace.QName("", "reportSpec");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpecExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECSpecExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTerminationCondition");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECTerminationCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTime");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTimeUnit");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECTimeUnit.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTrigger");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.ECTrigger.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRProperty");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.LRProperty.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRSpec");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.LRSpec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRSpecExtension");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.ale.epcglobal.LRSpecExtension.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:xsd:1", "Document");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.epcglobal.Document.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:epcglobal:xsd:1", "EPC");
            cachedSerQNames.add(qName);
            cls = org.accada.ale.xsd.epcglobal.EPC.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder define(org.accada.ale.wsdl.ale.epcglobal.Define parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "define"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder undefine(org.accada.ale.wsdl.ale.epcglobal.Undefine parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "undefine"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec(org.accada.ale.wsdl.ale.epcglobal.GetECSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getECSpec"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.xsd.ale.epcglobal.ECSpec) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.xsd.ale.epcglobal.ECSpec) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.xsd.ale.epcglobal.ECSpec.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String[] getECSpecNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getECSpecNames"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder subscribe(org.accada.ale.wsdl.ale.epcglobal.Subscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "subscribe"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.DuplicateSubscriptionException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InvalidURIException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InvalidURIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.VoidHolder unsubscribe(org.accada.ale.wsdl.ale.epcglobal.Unsubscribe parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.InvalidURIException, org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "unsubscribe"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.VoidHolder) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.VoidHolder.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InvalidURIException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InvalidURIException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchSubscriberException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.xsd.ale.epcglobal.ECReports poll(org.accada.ale.wsdl.ale.epcglobal.Poll parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "poll"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.xsd.ale.epcglobal.ECReports) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.xsd.ale.epcglobal.ECReports) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.xsd.ale.epcglobal.ECReports.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.xsd.ale.epcglobal.ECReports immediate(org.accada.ale.wsdl.ale.epcglobal.Immediate parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "immediate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.xsd.ale.epcglobal.ECReports) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.xsd.ale.epcglobal.ECReports) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.xsd.ale.epcglobal.ECReports.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ECSpecValidationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String[] getSubscribers(org.accada.ale.wsdl.ale.epcglobal.GetSubscribers parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getSubscribers"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String getStandardVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getStandardVersion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String getVendorVersion(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getVendorVersion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult defineReader(org.accada.ale.wsdl.ale.epcglobal.DefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException, org.accada.ale.wsdl.ale.epcglobal.ValidationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "defineReader"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.DefineReaderResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.DuplicateNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ValidationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult updateReader(org.accada.ale.wsdl.ale.epcglobal.UpdateReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "updateReader"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.UpdateReaderResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InUseException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InUseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ValidationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult undefineReader(org.accada.ale.wsdl.ale.epcglobal.UndefineReader parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "undefineReader"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.UndefineReaderResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InUseException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InUseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String[] getLogicalReaderNames(org.accada.ale.wsdl.ale.epcglobal.EmptyParms parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getLogicalReaderNames"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.xsd.ale.epcglobal.LRSpec getLRSpec(org.accada.ale.wsdl.ale.epcglobal.GetLRSpec parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getLRSpec"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.xsd.ale.epcglobal.LRSpec) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.xsd.ale.epcglobal.LRSpec) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.xsd.ale.epcglobal.LRSpec.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.AddReadersResult addReaders(org.accada.ale.wsdl.ale.epcglobal.AddReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "addReaders"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.AddReadersResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.AddReadersResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.AddReadersResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InUseException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InUseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ValidationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.SetReadersResult setReaders(org.accada.ale.wsdl.ale.epcglobal.SetReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException, org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException, org.accada.ale.wsdl.ale.epcglobal.ValidationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "setReaders"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.SetReadersResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.SetReadersResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.SetReadersResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InUseException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InUseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ReaderLoopException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ValidationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult removeReaders(org.accada.ale.wsdl.ale.epcglobal.RemoveReaders parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "removeReaders"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.RemoveReadersResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InUseException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InUseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NonCompositeReaderException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult setProperties(org.accada.ale.wsdl.ale.epcglobal.SetProperties parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException, org.accada.ale.wsdl.ale.epcglobal.InUseException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException, org.accada.ale.wsdl.ale.epcglobal.ValidationException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "setProperties"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult) org.apache.axis.utils.JavaUtils.convert(_resp, org.accada.ale.wsdl.ale.epcglobal.SetPropertiesResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImmutableReaderException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.InUseException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.InUseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ValidationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ValidationException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String getPropertyValue(org.accada.ale.wsdl.ale.epcglobal.GetPropertyValue parms) throws java.rmi.RemoteException, org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException, org.accada.ale.wsdl.ale.epcglobal.ImplementationException, org.accada.ale.wsdl.ale.epcglobal.SecurityException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getPropertyValue"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parms});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.NoSuchNameException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.ImplementationException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.ImplementationException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof org.accada.ale.wsdl.ale.epcglobal.SecurityException) {
              throw (org.accada.ale.wsdl.ale.epcglobal.SecurityException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
