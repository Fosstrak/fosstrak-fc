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
 * Define.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.accada.ale.wsdl.ale.epcglobal;

public class Define  implements java.io.Serializable {
    private java.lang.String specName;

    private org.accada.ale.xsd.ale.epcglobal.ECSpec spec;

    public Define() {
    }

    public Define(
           java.lang.String specName,
           org.accada.ale.xsd.ale.epcglobal.ECSpec spec) {
           this.specName = specName;
           this.spec = spec;
    }


    /**
     * Gets the specName value for this Define.
     * 
     * @return specName
     */
    public java.lang.String getSpecName() {
        return specName;
    }


    /**
     * Sets the specName value for this Define.
     * 
     * @param specName
     */
    public void setSpecName(java.lang.String specName) {
        this.specName = specName;
    }


    /**
     * Gets the spec value for this Define.
     * 
     * @return spec
     */
    public org.accada.ale.xsd.ale.epcglobal.ECSpec getSpec() {
        return spec;
    }


    /**
     * Sets the spec value for this Define.
     * 
     * @param spec
     */
    public void setSpec(org.accada.ale.xsd.ale.epcglobal.ECSpec spec) {
        this.spec = spec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Define)) return false;
        Define other = (Define) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.specName==null && other.getSpecName()==null) || 
             (this.specName!=null &&
              this.specName.equals(other.getSpecName()))) &&
            ((this.spec==null && other.getSpec()==null) || 
             (this.spec!=null &&
              this.spec.equals(other.getSpec())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSpecName() != null) {
            _hashCode += getSpecName().hashCode();
        }
        if (getSpec() != null) {
            _hashCode += getSpec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Define.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Define"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spec"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpec"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
