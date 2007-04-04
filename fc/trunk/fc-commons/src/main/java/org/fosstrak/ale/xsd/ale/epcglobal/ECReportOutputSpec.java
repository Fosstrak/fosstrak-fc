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
 * ECReportOutputSpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;


/**
 * ECReportOutputSpec specifies how the final set of EPCs
 *                 is to be reported with respect to type.
 */
public class ECReportOutputSpec  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    private boolean includeEPC;  // attribute

    private boolean includeTag;  // attribute

    private boolean includeRawHex;  // attribute

    private boolean includeRawDecimal;  // attribute

    private boolean includeCount;  // attribute

    public ECReportOutputSpec() {
    }

    public ECReportOutputSpec(
           org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension extension,
           org.apache.axis.message.MessageElement [] _any,
           boolean includeEPC,
           boolean includeTag,
           boolean includeRawHex,
           boolean includeRawDecimal,
           boolean includeCount) {
           this.extension = extension;
           this._any = _any;
           this.includeEPC = includeEPC;
           this.includeTag = includeTag;
           this.includeRawHex = includeRawHex;
           this.includeRawDecimal = includeRawDecimal;
           this.includeCount = includeCount;
    }


    /**
     * Gets the extension value for this ECReportOutputSpec.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECReportOutputSpec.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECReportOutputSpec.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECReportOutputSpec.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the includeEPC value for this ECReportOutputSpec.
     * 
     * @return includeEPC
     */
    public boolean isIncludeEPC() {
        return includeEPC;
    }


    /**
     * Sets the includeEPC value for this ECReportOutputSpec.
     * 
     * @param includeEPC
     */
    public void setIncludeEPC(boolean includeEPC) {
        this.includeEPC = includeEPC;
    }


    /**
     * Gets the includeTag value for this ECReportOutputSpec.
     * 
     * @return includeTag
     */
    public boolean isIncludeTag() {
        return includeTag;
    }


    /**
     * Sets the includeTag value for this ECReportOutputSpec.
     * 
     * @param includeTag
     */
    public void setIncludeTag(boolean includeTag) {
        this.includeTag = includeTag;
    }


    /**
     * Gets the includeRawHex value for this ECReportOutputSpec.
     * 
     * @return includeRawHex
     */
    public boolean isIncludeRawHex() {
        return includeRawHex;
    }


    /**
     * Sets the includeRawHex value for this ECReportOutputSpec.
     * 
     * @param includeRawHex
     */
    public void setIncludeRawHex(boolean includeRawHex) {
        this.includeRawHex = includeRawHex;
    }


    /**
     * Gets the includeRawDecimal value for this ECReportOutputSpec.
     * 
     * @return includeRawDecimal
     */
    public boolean isIncludeRawDecimal() {
        return includeRawDecimal;
    }


    /**
     * Sets the includeRawDecimal value for this ECReportOutputSpec.
     * 
     * @param includeRawDecimal
     */
    public void setIncludeRawDecimal(boolean includeRawDecimal) {
        this.includeRawDecimal = includeRawDecimal;
    }


    /**
     * Gets the includeCount value for this ECReportOutputSpec.
     * 
     * @return includeCount
     */
    public boolean isIncludeCount() {
        return includeCount;
    }


    /**
     * Sets the includeCount value for this ECReportOutputSpec.
     * 
     * @param includeCount
     */
    public void setIncludeCount(boolean includeCount) {
        this.includeCount = includeCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECReportOutputSpec)) return false;
        ECReportOutputSpec other = (ECReportOutputSpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            this.includeEPC == other.isIncludeEPC() &&
            this.includeTag == other.isIncludeTag() &&
            this.includeRawHex == other.isIncludeRawHex() &&
            this.includeRawDecimal == other.isIncludeRawDecimal() &&
            this.includeCount == other.isIncludeCount();
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
        _hashCode += (isIncludeEPC() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIncludeTag() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIncludeRawHex() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIncludeRawDecimal() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIncludeCount() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ECReportOutputSpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportOutputSpec"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("includeEPC");
        attrField.setXmlName(new javax.xml.namespace.QName("", "includeEPC"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("includeTag");
        attrField.setXmlName(new javax.xml.namespace.QName("", "includeTag"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("includeRawHex");
        attrField.setXmlName(new javax.xml.namespace.QName("", "includeRawHex"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("includeRawDecimal");
        attrField.setXmlName(new javax.xml.namespace.QName("", "includeRawDecimal"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("includeCount");
        attrField.setXmlName(new javax.xml.namespace.QName("", "includeCount"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportOutputSpecExtension"));
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
