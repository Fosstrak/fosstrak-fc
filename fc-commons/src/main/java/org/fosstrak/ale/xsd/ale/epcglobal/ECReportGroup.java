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
 * ECReportGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;

public class ECReportGroup  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.accada.ale.xsd.ale.epcglobal.ECReportGroupList groupList;

    private org.accada.ale.xsd.ale.epcglobal.ECReportGroupCount groupCount;

    private org.accada.ale.xsd.ale.epcglobal.ECReportGroupExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    private java.lang.String groupName;  // attribute

    public ECReportGroup() {
    }

    public ECReportGroup(
           org.accada.ale.xsd.ale.epcglobal.ECReportGroupList groupList,
           org.accada.ale.xsd.ale.epcglobal.ECReportGroupCount groupCount,
           org.accada.ale.xsd.ale.epcglobal.ECReportGroupExtension extension,
           org.apache.axis.message.MessageElement [] _any,
           java.lang.String groupName) {
           this.groupList = groupList;
           this.groupCount = groupCount;
           this.extension = extension;
           this._any = _any;
           this.groupName = groupName;
    }


    /**
     * Gets the groupList value for this ECReportGroup.
     * 
     * @return groupList
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportGroupList getGroupList() {
        return groupList;
    }


    /**
     * Sets the groupList value for this ECReportGroup.
     * 
     * @param groupList
     */
    public void setGroupList(org.accada.ale.xsd.ale.epcglobal.ECReportGroupList groupList) {
        this.groupList = groupList;
    }


    /**
     * Gets the groupCount value for this ECReportGroup.
     * 
     * @return groupCount
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportGroupCount getGroupCount() {
        return groupCount;
    }


    /**
     * Sets the groupCount value for this ECReportGroup.
     * 
     * @param groupCount
     */
    public void setGroupCount(org.accada.ale.xsd.ale.epcglobal.ECReportGroupCount groupCount) {
        this.groupCount = groupCount;
    }


    /**
     * Gets the extension value for this ECReportGroup.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportGroupExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECReportGroup.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECReportGroupExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECReportGroup.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECReportGroup.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the groupName value for this ECReportGroup.
     * 
     * @return groupName
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this ECReportGroup.
     * 
     * @param groupName
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECReportGroup)) return false;
        ECReportGroup other = (ECReportGroup) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groupList==null && other.getGroupList()==null) || 
             (this.groupList!=null &&
              this.groupList.equals(other.getGroupList()))) &&
            ((this.groupCount==null && other.getGroupCount()==null) || 
             (this.groupCount!=null &&
              this.groupCount.equals(other.getGroupCount()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this.groupName==null && other.getGroupName()==null) || 
             (this.groupName!=null &&
              this.groupName.equals(other.getGroupName())));
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
        if (getGroupList() != null) {
            _hashCode += getGroupList().hashCode();
        }
        if (getGroupCount() != null) {
            _hashCode += getGroupCount().hashCode();
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
        if (getGroupName() != null) {
            _hashCode += getGroupName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ECReportGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroup"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("groupName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "groupName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupList"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupList"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupCount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportGroupExtension"));
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
