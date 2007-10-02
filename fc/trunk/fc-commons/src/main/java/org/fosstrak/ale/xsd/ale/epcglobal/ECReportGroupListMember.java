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
 * ECReportGroupListMember.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;

public class ECReportGroupListMember  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.accada.ale.xsd.epcglobal.EPC epc;

    private org.accada.ale.xsd.epcglobal.EPC tag;

    private org.accada.ale.xsd.epcglobal.EPC rawHex;

    private org.accada.ale.xsd.epcglobal.EPC rawDecimal;

    private org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    public ECReportGroupListMember() {
    }

    public ECReportGroupListMember(
           org.accada.ale.xsd.epcglobal.EPC epc,
           org.accada.ale.xsd.epcglobal.EPC tag,
           org.accada.ale.xsd.epcglobal.EPC rawHex,
           org.accada.ale.xsd.epcglobal.EPC rawDecimal,
           org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension extension,
           org.apache.axis.message.MessageElement [] _any) {
           this.epc = epc;
           this.tag = tag;
           this.rawHex = rawHex;
           this.rawDecimal = rawDecimal;
           this.extension = extension;
           this._any = _any;
    }


    /**
     * Gets the epc value for this ECReportGroupListMember.
     * 
     * @return epc
     */
    public org.accada.ale.xsd.epcglobal.EPC getEpc() {
        return epc;
    }


    /**
     * Sets the epc value for this ECReportGroupListMember.
     * 
     * @param epc
     */
    public void setEpc(org.accada.ale.xsd.epcglobal.EPC epc) {
        this.epc = epc;
    }


    /**
     * Gets the tag value for this ECReportGroupListMember.
     * 
     * @return tag
     */
    public org.accada.ale.xsd.epcglobal.EPC getTag() {
        return tag;
    }


    /**
     * Sets the tag value for this ECReportGroupListMember.
     * 
     * @param tag
     */
    public void setTag(org.accada.ale.xsd.epcglobal.EPC tag) {
        this.tag = tag;
    }


    /**
     * Gets the rawHex value for this ECReportGroupListMember.
     * 
     * @return rawHex
     */
    public org.accada.ale.xsd.epcglobal.EPC getRawHex() {
        return rawHex;
    }


    /**
     * Sets the rawHex value for this ECReportGroupListMember.
     * 
     * @param rawHex
     */
    public void setRawHex(org.accada.ale.xsd.epcglobal.EPC rawHex) {
        this.rawHex = rawHex;
    }


    /**
     * Gets the rawDecimal value for this ECReportGroupListMember.
     * 
     * @return rawDecimal
     */
    public org.accada.ale.xsd.epcglobal.EPC getRawDecimal() {
        return rawDecimal;
    }


    /**
     * Sets the rawDecimal value for this ECReportGroupListMember.
     * 
     * @param rawDecimal
     */
    public void setRawDecimal(org.accada.ale.xsd.epcglobal.EPC rawDecimal) {
        this.rawDecimal = rawDecimal;
    }


    /**
     * Gets the extension value for this ECReportGroupListMember.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECReportGroupListMember.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECReportGroupListMember.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECReportGroupListMember.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECReportGroupListMember)) return false;
        ECReportGroupListMember other = (ECReportGroupListMember) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.epc==null && other.getEpc()==null) || 
             (this.epc!=null &&
              this.epc.equals(other.getEpc()))) &&
            ((this.tag==null && other.getTag()==null) || 
             (this.tag!=null &&
              this.tag.equals(other.getTag()))) &&
            ((this.rawHex==null && other.getRawHex()==null) || 
             (this.rawHex!=null &&
              this.rawHex.equals(other.getRawHex()))) &&
            ((this.rawDecimal==null && other.getRawDecimal()==null) || 
             (this.rawDecimal!=null &&
              this.rawDecimal.equals(other.getRawDecimal()))) &&
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
        if (getEpc() != null) {
            _hashCode += getEpc().hashCode();
        }
        if (getTag() != null) {
            _hashCode += getTag().hashCode();
        }
        if (getRawHex() != null) {
            _hashCode += getRawHex().hashCode();
        }
        if (getRawDecimal() != null) {
            _hashCode += getRawDecimal().hashCode();
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
        new org.apache.axis.description.TypeDesc(ECReportGroupListMember.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupListMember"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("epc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "epc"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:xsd:1", "EPC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tag"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:xsd:1", "EPC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rawHex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rawHex"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:xsd:1", "EPC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rawDecimal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rawDecimal"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:xsd:1", "EPC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupListMemberExtension"));
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
