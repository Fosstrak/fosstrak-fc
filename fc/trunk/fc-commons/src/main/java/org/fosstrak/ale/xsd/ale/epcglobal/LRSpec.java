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
 * LRSpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;

public class LRSpec  extends org.accada.ale.xsd.epcglobal.Document  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private java.lang.Boolean isComposite;

    private org.accada.ale.xsd.ale.epcglobal.LRLogicalReaders readers;

    private org.accada.ale.xsd.ale.epcglobal.LRProperties properties;

    private org.accada.ale.xsd.ale.epcglobal.LRSpecExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    public LRSpec() {
    }

    public LRSpec(
           java.math.BigDecimal schemaVersion,
           java.util.Calendar creationDate,
           java.lang.Boolean isComposite,
           org.accada.ale.xsd.ale.epcglobal.LRLogicalReaders readers,
           org.accada.ale.xsd.ale.epcglobal.LRProperties properties,
           org.accada.ale.xsd.ale.epcglobal.LRSpecExtension extension,
           org.apache.axis.message.MessageElement [] _any) {
        super(
            schemaVersion,
            creationDate);
        this.isComposite = isComposite;
        this.readers = readers;
        this.properties = properties;
        this.extension = extension;
        this._any = _any;
    }


    /**
     * Gets the isComposite value for this LRSpec.
     * 
     * @return isComposite
     */
    public java.lang.Boolean getIsComposite() {
        return isComposite;
    }


    /**
     * Sets the isComposite value for this LRSpec.
     * 
     * @param isComposite
     */
    public void setIsComposite(java.lang.Boolean isComposite) {
        this.isComposite = isComposite;
    }


    /**
     * Gets the readers value for this LRSpec.
     * 
     * @return readers
     */
    public org.accada.ale.xsd.ale.epcglobal.LRLogicalReaders getReaders() {
        return readers;
    }


    /**
     * Sets the readers value for this LRSpec.
     * 
     * @param readers
     */
    public void setReaders(org.accada.ale.xsd.ale.epcglobal.LRLogicalReaders readers) {
        this.readers = readers;
    }


    /**
     * Gets the properties value for this LRSpec.
     * 
     * @return properties
     */
    public org.accada.ale.xsd.ale.epcglobal.LRProperties getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this LRSpec.
     * 
     * @param properties
     */
    public void setProperties(org.accada.ale.xsd.ale.epcglobal.LRProperties properties) {
        this.properties = properties;
    }


    /**
     * Gets the extension value for this LRSpec.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.LRSpecExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this LRSpec.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.LRSpecExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this LRSpec.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this LRSpec.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LRSpec)) return false;
        LRSpec other = (LRSpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.isComposite==null && other.getIsComposite()==null) || 
             (this.isComposite!=null &&
              this.isComposite.equals(other.getIsComposite()))) &&
            ((this.readers==null && other.getReaders()==null) || 
             (this.readers!=null &&
              this.readers.equals(other.getReaders()))) &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              this.properties.equals(other.getProperties()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any())));
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
        if (getIsComposite() != null) {
            _hashCode += getIsComposite().hashCode();
        }
        if (getReaders() != null) {
            _hashCode += getReaders().hashCode();
        }
        if (getProperties() != null) {
            _hashCode += getProperties().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
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
        new org.apache.axis.description.TypeDesc(LRSpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRSpec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isComposite");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isComposite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("readers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "readers"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRLogicalReaders"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRProperties"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "LRSpecExtension"));
        elemField.setMinOccurs(0);
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
