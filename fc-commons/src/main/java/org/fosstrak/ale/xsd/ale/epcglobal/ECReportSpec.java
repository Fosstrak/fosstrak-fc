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
 * ECReportSpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;


/**
 * A ReportSpec specifies one report to be returned from
 *                 executing an event cycle. An ECSpec may contain one
 * or more ECReportSpec
 *             instances.
 */
public class ECReportSpec  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec reportSet;

    private org.accada.ale.xsd.ale.epcglobal.ECFilterSpec filterSpec;

    private java.lang.String[] groupSpec;

    private org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec output;

    private org.accada.ale.xsd.ale.epcglobal.ECReportSpecExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    private java.lang.String reportName;  // attribute

    private boolean reportIfEmpty;  // attribute

    private boolean reportOnlyOnChange;  // attribute

    public ECReportSpec() {
    }

    public ECReportSpec(
           org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec reportSet,
           org.accada.ale.xsd.ale.epcglobal.ECFilterSpec filterSpec,
           java.lang.String[] groupSpec,
           org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec output,
           org.accada.ale.xsd.ale.epcglobal.ECReportSpecExtension extension,
           org.apache.axis.message.MessageElement [] _any,
           java.lang.String reportName,
           boolean reportIfEmpty,
           boolean reportOnlyOnChange) {
           this.reportSet = reportSet;
           this.filterSpec = filterSpec;
           this.groupSpec = groupSpec;
           this.output = output;
           this.extension = extension;
           this._any = _any;
           this.reportName = reportName;
           this.reportIfEmpty = reportIfEmpty;
           this.reportOnlyOnChange = reportOnlyOnChange;
    }


    /**
     * Gets the reportSet value for this ECReportSpec.
     * 
     * @return reportSet
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec getReportSet() {
        return reportSet;
    }


    /**
     * Sets the reportSet value for this ECReportSpec.
     * 
     * @param reportSet
     */
    public void setReportSet(org.accada.ale.xsd.ale.epcglobal.ECReportSetSpec reportSet) {
        this.reportSet = reportSet;
    }


    /**
     * Gets the filterSpec value for this ECReportSpec.
     * 
     * @return filterSpec
     */
    public org.accada.ale.xsd.ale.epcglobal.ECFilterSpec getFilterSpec() {
        return filterSpec;
    }


    /**
     * Sets the filterSpec value for this ECReportSpec.
     * 
     * @param filterSpec
     */
    public void setFilterSpec(org.accada.ale.xsd.ale.epcglobal.ECFilterSpec filterSpec) {
        this.filterSpec = filterSpec;
    }


    /**
     * Gets the groupSpec value for this ECReportSpec.
     * 
     * @return groupSpec
     */
    public java.lang.String[] getGroupSpec() {
        return groupSpec;
    }


    /**
     * Sets the groupSpec value for this ECReportSpec.
     * 
     * @param groupSpec
     */
    public void setGroupSpec(java.lang.String[] groupSpec) {
        this.groupSpec = groupSpec;
    }


    /**
     * Gets the output value for this ECReportSpec.
     * 
     * @return output
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec getOutput() {
        return output;
    }


    /**
     * Sets the output value for this ECReportSpec.
     * 
     * @param output
     */
    public void setOutput(org.accada.ale.xsd.ale.epcglobal.ECReportOutputSpec output) {
        this.output = output;
    }


    /**
     * Gets the extension value for this ECReportSpec.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportSpecExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECReportSpec.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECReportSpecExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECReportSpec.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECReportSpec.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the reportName value for this ECReportSpec.
     * 
     * @return reportName
     */
    public java.lang.String getReportName() {
        return reportName;
    }


    /**
     * Sets the reportName value for this ECReportSpec.
     * 
     * @param reportName
     */
    public void setReportName(java.lang.String reportName) {
        this.reportName = reportName;
    }


    /**
     * Gets the reportIfEmpty value for this ECReportSpec.
     * 
     * @return reportIfEmpty
     */
    public boolean isReportIfEmpty() {
        return reportIfEmpty;
    }


    /**
     * Sets the reportIfEmpty value for this ECReportSpec.
     * 
     * @param reportIfEmpty
     */
    public void setReportIfEmpty(boolean reportIfEmpty) {
        this.reportIfEmpty = reportIfEmpty;
    }


    /**
     * Gets the reportOnlyOnChange value for this ECReportSpec.
     * 
     * @return reportOnlyOnChange
     */
    public boolean isReportOnlyOnChange() {
        return reportOnlyOnChange;
    }


    /**
     * Sets the reportOnlyOnChange value for this ECReportSpec.
     * 
     * @param reportOnlyOnChange
     */
    public void setReportOnlyOnChange(boolean reportOnlyOnChange) {
        this.reportOnlyOnChange = reportOnlyOnChange;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECReportSpec)) return false;
        ECReportSpec other = (ECReportSpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportSet==null && other.getReportSet()==null) || 
             (this.reportSet!=null &&
              this.reportSet.equals(other.getReportSet()))) &&
            ((this.filterSpec==null && other.getFilterSpec()==null) || 
             (this.filterSpec!=null &&
              this.filterSpec.equals(other.getFilterSpec()))) &&
            ((this.groupSpec==null && other.getGroupSpec()==null) || 
             (this.groupSpec!=null &&
              java.util.Arrays.equals(this.groupSpec, other.getGroupSpec()))) &&
            ((this.output==null && other.getOutput()==null) || 
             (this.output!=null &&
              this.output.equals(other.getOutput()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this.reportName==null && other.getReportName()==null) || 
             (this.reportName!=null &&
              this.reportName.equals(other.getReportName()))) &&
            this.reportIfEmpty == other.isReportIfEmpty() &&
            this.reportOnlyOnChange == other.isReportOnlyOnChange();
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
        if (getReportSet() != null) {
            _hashCode += getReportSet().hashCode();
        }
        if (getFilterSpec() != null) {
            _hashCode += getFilterSpec().hashCode();
        }
        if (getGroupSpec() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGroupSpec());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGroupSpec(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOutput() != null) {
            _hashCode += getOutput().hashCode();
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
        if (getReportName() != null) {
            _hashCode += getReportName().hashCode();
        }
        _hashCode += (isReportIfEmpty() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isReportOnlyOnChange() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ECReportSpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpec"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("reportName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "reportName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("reportIfEmpty");
        attrField.setXmlName(new javax.xml.namespace.QName("", "reportIfEmpty"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("reportOnlyOnChange");
        attrField.setXmlName(new javax.xml.namespace.QName("", "reportOnlyOnChange"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportSet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportSet"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSetSpec"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filterSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filterSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECFilterSpec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "pattern"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("output");
        elemField.setXmlName(new javax.xml.namespace.QName("", "output"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportOutputSpec"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpecExtension"));
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
