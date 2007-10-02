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
 * ECFilterSpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;


/**
 * A ECFilterSpec specifies what EPCs are to be included
 *                 in the final report. The ECFilterSpec implements a
 * flexible filtering scheme based
 *                 on pattern lists for inclusion and exclusion.
 */
public class ECFilterSpec  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private java.lang.String[] includePatterns;

    private java.lang.String[] excludePatterns;

    private org.accada.ale.xsd.ale.epcglobal.ECFilterSpecExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    public ECFilterSpec() {
    }

    public ECFilterSpec(
           java.lang.String[] includePatterns,
           java.lang.String[] excludePatterns,
           org.accada.ale.xsd.ale.epcglobal.ECFilterSpecExtension extension,
           org.apache.axis.message.MessageElement [] _any) {
           this.includePatterns = includePatterns;
           this.excludePatterns = excludePatterns;
           this.extension = extension;
           this._any = _any;
    }


    /**
     * Gets the includePatterns value for this ECFilterSpec.
     * 
     * @return includePatterns
     */
    public java.lang.String[] getIncludePatterns() {
        return includePatterns;
    }


    /**
     * Sets the includePatterns value for this ECFilterSpec.
     * 
     * @param includePatterns
     */
    public void setIncludePatterns(java.lang.String[] includePatterns) {
        this.includePatterns = includePatterns;
    }


    /**
     * Gets the excludePatterns value for this ECFilterSpec.
     * 
     * @return excludePatterns
     */
    public java.lang.String[] getExcludePatterns() {
        return excludePatterns;
    }


    /**
     * Sets the excludePatterns value for this ECFilterSpec.
     * 
     * @param excludePatterns
     */
    public void setExcludePatterns(java.lang.String[] excludePatterns) {
        this.excludePatterns = excludePatterns;
    }


    /**
     * Gets the extension value for this ECFilterSpec.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECFilterSpecExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECFilterSpec.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECFilterSpecExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECFilterSpec.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECFilterSpec.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECFilterSpec)) return false;
        ECFilterSpec other = (ECFilterSpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.includePatterns==null && other.getIncludePatterns()==null) || 
             (this.includePatterns!=null &&
              java.util.Arrays.equals(this.includePatterns, other.getIncludePatterns()))) &&
            ((this.excludePatterns==null && other.getExcludePatterns()==null) || 
             (this.excludePatterns!=null &&
              java.util.Arrays.equals(this.excludePatterns, other.getExcludePatterns()))) &&
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
        int _hashCode = 1;
        if (getIncludePatterns() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIncludePatterns());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIncludePatterns(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExcludePatterns() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExcludePatterns());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExcludePatterns(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        new org.apache.axis.description.TypeDesc(ECFilterSpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECFilterSpec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includePatterns");
        elemField.setXmlName(new javax.xml.namespace.QName("", "includePatterns"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "includePattern"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludePatterns");
        elemField.setXmlName(new javax.xml.namespace.QName("", "excludePatterns"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "excludePattern"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECFilterSpecExtension"));
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
