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
 * LRLogicalReaders.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;

public class LRLogicalReaders  extends org.accada.ale.xsd.epcglobal.Document  implements java.io.Serializable {
    private java.lang.String[] logicalReader;

    public LRLogicalReaders() {
    }

    public LRLogicalReaders(
           java.math.BigDecimal schemaVersion,
           java.util.Calendar creationDate,
           java.lang.String[] logicalReader) {
        super(
            schemaVersion,
            creationDate);
        this.logicalReader = logicalReader;
    }


    /**
     * Gets the logicalReader value for this LRLogicalReaders.
     * 
     * @return logicalReader
     */
    public java.lang.String[] getLogicalReader() {
        return logicalReader;
    }


    /**
     * Sets the logicalReader value for this LRLogicalReaders.
     * 
     * @param logicalReader
     */
    public void setLogicalReader(java.lang.String[] logicalReader) {
        this.logicalReader = logicalReader;
    }

    public java.lang.String getLogicalReader(int i) {
        return this.logicalReader[i];
    }

    public void setLogicalReader(int i, java.lang.String _value) {
        this.logicalReader[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LRLogicalReaders)) return false;
        LRLogicalReaders other = (LRLogicalReaders) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.logicalReader==null && other.getLogicalReader()==null) || 
             (this.logicalReader!=null &&
              java.util.Arrays.equals(this.logicalReader, other.getLogicalReader())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getLogicalReader() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLogicalReader());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLogicalReader(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LRLogicalReaders.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRLogicalReaders"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logicalReader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "logicalReader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
