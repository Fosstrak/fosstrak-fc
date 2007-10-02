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
 * Subscribe.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.wsdl.ale.epcglobal;

public class Subscribe  implements java.io.Serializable {
    private java.lang.String specName;

    private java.lang.String notificationURI;

    public Subscribe() {
    }

    public Subscribe(
           java.lang.String specName,
           java.lang.String notificationURI) {
           this.specName = specName;
           this.notificationURI = notificationURI;
    }


    /**
     * Gets the specName value for this Subscribe.
     * 
     * @return specName
     */
    public java.lang.String getSpecName() {
        return specName;
    }


    /**
     * Sets the specName value for this Subscribe.
     * 
     * @param specName
     */
    public void setSpecName(java.lang.String specName) {
        this.specName = specName;
    }


    /**
     * Gets the notificationURI value for this Subscribe.
     * 
     * @return notificationURI
     */
    public java.lang.String getNotificationURI() {
        return notificationURI;
    }


    /**
     * Sets the notificationURI value for this Subscribe.
     * 
     * @param notificationURI
     */
    public void setNotificationURI(java.lang.String notificationURI) {
        this.notificationURI = notificationURI;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Subscribe)) return false;
        Subscribe other = (Subscribe) obj;
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
            ((this.notificationURI==null && other.getNotificationURI()==null) || 
             (this.notificationURI!=null &&
              this.notificationURI.equals(other.getNotificationURI())));
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
        if (getNotificationURI() != null) {
            _hashCode += getNotificationURI().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Subscribe.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:wsdl:1", "Subscribe"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificationURI");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notificationURI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
