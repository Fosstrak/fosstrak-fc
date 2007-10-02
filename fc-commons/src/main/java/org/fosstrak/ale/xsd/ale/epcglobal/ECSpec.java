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
 * ECSpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;


/**
 * An ECSpec describes an event cycle and one or more
 *                 reports that are to be generated from it. It contains
 * a list of logical readers
 *                 whose reader cycles are to be included in the event
 * cycle, a specification of read
 *                 cycle timing, a specification of how the boundaries
 * of event cycles are to be
 *                 determined, and list of specifications each of which
 * describes a report to be
 *                 generated from this event cycle.
 */
public class ECSpec  extends org.accada.ale.xsd.epcglobal.Document  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private java.lang.String[] logicalReaders;

    private org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec boundarySpec;

    private org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] reportSpecs;

    private org.accada.ale.xsd.ale.epcglobal.ECSpecExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    private boolean includeSpecInReports;  // attribute

    public ECSpec() {
    }

    public ECSpec(
           java.math.BigDecimal schemaVersion,
           java.util.Calendar creationDate,
           boolean includeSpecInReports,
           java.lang.String[] logicalReaders,
           org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec boundarySpec,
           org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] reportSpecs,
           org.accada.ale.xsd.ale.epcglobal.ECSpecExtension extension,
           org.apache.axis.message.MessageElement [] _any) {
        super(
            schemaVersion,
            creationDate);
        this.includeSpecInReports = includeSpecInReports;
        this.logicalReaders = logicalReaders;
        this.boundarySpec = boundarySpec;
        this.reportSpecs = reportSpecs;
        this.extension = extension;
        this._any = _any;
    }


    /**
     * Gets the logicalReaders value for this ECSpec.
     * 
     * @return logicalReaders
     */
    public java.lang.String[] getLogicalReaders() {
        return logicalReaders;
    }


    /**
     * Sets the logicalReaders value for this ECSpec.
     * 
     * @param logicalReaders
     */
    public void setLogicalReaders(java.lang.String[] logicalReaders) {
        this.logicalReaders = logicalReaders;
    }


    /**
     * Gets the boundarySpec value for this ECSpec.
     * 
     * @return boundarySpec
     */
    public org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec getBoundarySpec() {
        return boundarySpec;
    }


    /**
     * Sets the boundarySpec value for this ECSpec.
     * 
     * @param boundarySpec
     */
    public void setBoundarySpec(org.accada.ale.xsd.ale.epcglobal.ECBoundarySpec boundarySpec) {
        this.boundarySpec = boundarySpec;
    }


    /**
     * Gets the reportSpecs value for this ECSpec.
     * 
     * @return reportSpecs
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] getReportSpecs() {
        return reportSpecs;
    }


    /**
     * Sets the reportSpecs value for this ECSpec.
     * 
     * @param reportSpecs
     */
    public void setReportSpecs(org.accada.ale.xsd.ale.epcglobal.ECReportSpec[] reportSpecs) {
        this.reportSpecs = reportSpecs;
    }


    /**
     * Gets the extension value for this ECSpec.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECSpecExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECSpec.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECSpecExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECSpec.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECSpec.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the includeSpecInReports value for this ECSpec.
     * 
     * @return includeSpecInReports
     */
    public boolean isIncludeSpecInReports() {
        return includeSpecInReports;
    }


    /**
     * Sets the includeSpecInReports value for this ECSpec.
     * 
     * @param includeSpecInReports
     */
    public void setIncludeSpecInReports(boolean includeSpecInReports) {
        this.includeSpecInReports = includeSpecInReports;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECSpec)) return false;
        ECSpec other = (ECSpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.logicalReaders==null && other.getLogicalReaders()==null) || 
             (this.logicalReaders!=null &&
              java.util.Arrays.equals(this.logicalReaders, other.getLogicalReaders()))) &&
            ((this.boundarySpec==null && other.getBoundarySpec()==null) || 
             (this.boundarySpec!=null &&
              this.boundarySpec.equals(other.getBoundarySpec()))) &&
            ((this.reportSpecs==null && other.getReportSpecs()==null) || 
             (this.reportSpecs!=null &&
              java.util.Arrays.equals(this.reportSpecs, other.getReportSpecs()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            this.includeSpecInReports == other.isIncludeSpecInReports();
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
        if (getLogicalReaders() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLogicalReaders());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLogicalReaders(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBoundarySpec() != null) {
            _hashCode += getBoundarySpec().hashCode();
        }
        if (getReportSpecs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReportSpecs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReportSpecs(), i);
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
        _hashCode += (isIncludeSpecInReports() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ECSpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpec"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("includeSpecInReports");
        attrField.setXmlName(new javax.xml.namespace.QName("", "includeSpecInReports"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logicalReaders");
        elemField.setXmlName(new javax.xml.namespace.QName("", "logicalReaders"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "logicalReader"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("boundarySpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "boundarySpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECBoundarySpec"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportSpecs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportSpecs"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportSpec"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "reportSpec"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpecExtension"));
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
