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
 * ECReports.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;


/**
 * ECReports is the output from an event cycle. The
 *                 "meat" of an ECReports instance is the lists of count
 * report instances and list
 *                 report instances, each corresponding to an ECReportSpec
 * instance in the event
 *                 cycle's ECSpec. In addition to the reports themselves,
 * ECReports contains a number
 *                 of "header" fields that provide useful information
 * about the event
 *             cycle.
 */
public class ECReports  extends org.accada.ale.xsd.epcglobal.Document  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.accada.ale.xsd.ale.epcglobal.ECReport[] reports;

    private org.accada.ale.xsd.ale.epcglobal.ECReportsExtension extension;

    private org.accada.ale.xsd.ale.epcglobal.ECSpec ECSpec;

    private org.apache.axis.message.MessageElement [] _any;

    private java.lang.String specName;  // attribute

    private java.util.Calendar date;  // attribute

    private java.lang.String ALEID;  // attribute

    private long totalMilliseconds;  // attribute

    private org.accada.ale.xsd.ale.epcglobal.ECTerminationCondition terminationCondition;  // attribute

    private java.lang.String schemaURL;  // attribute

    public ECReports() {
    }

    public ECReports(
           java.math.BigDecimal schemaVersion,
           java.util.Calendar creationDate,
           java.lang.String specName,
           java.util.Calendar date,
           java.lang.String ALEID,
           long totalMilliseconds,
           org.accada.ale.xsd.ale.epcglobal.ECTerminationCondition terminationCondition,
           java.lang.String schemaURL,
           org.accada.ale.xsd.ale.epcglobal.ECReport[] reports,
           org.accada.ale.xsd.ale.epcglobal.ECReportsExtension extension,
           org.accada.ale.xsd.ale.epcglobal.ECSpec ECSpec,
           org.apache.axis.message.MessageElement [] _any) {
        super(
            schemaVersion,
            creationDate);
        this.specName = specName;
        this.date = date;
        this.ALEID = ALEID;
        this.totalMilliseconds = totalMilliseconds;
        this.terminationCondition = terminationCondition;
        this.schemaURL = schemaURL;
        this.reports = reports;
        this.extension = extension;
        this.ECSpec = ECSpec;
        this._any = _any;
    }


    /**
     * Gets the reports value for this ECReports.
     * 
     * @return reports
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReport[] getReports() {
        return reports;
    }


    /**
     * Sets the reports value for this ECReports.
     * 
     * @param reports
     */
    public void setReports(org.accada.ale.xsd.ale.epcglobal.ECReport[] reports) {
        this.reports = reports;
    }


    /**
     * Gets the extension value for this ECReports.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECReportsExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECReports.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECReportsExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the ECSpec value for this ECReports.
     * 
     * @return ECSpec
     */
    public org.accada.ale.xsd.ale.epcglobal.ECSpec getECSpec() {
        return ECSpec;
    }


    /**
     * Sets the ECSpec value for this ECReports.
     * 
     * @param ECSpec
     */
    public void setECSpec(org.accada.ale.xsd.ale.epcglobal.ECSpec ECSpec) {
        this.ECSpec = ECSpec;
    }


    /**
     * Gets the _any value for this ECReports.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECReports.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the specName value for this ECReports.
     * 
     * @return specName
     */
    public java.lang.String getSpecName() {
        return specName;
    }


    /**
     * Sets the specName value for this ECReports.
     * 
     * @param specName
     */
    public void setSpecName(java.lang.String specName) {
        this.specName = specName;
    }


    /**
     * Gets the date value for this ECReports.
     * 
     * @return date
     */
    public java.util.Calendar getDate() {
        return date;
    }


    /**
     * Sets the date value for this ECReports.
     * 
     * @param date
     */
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }


    /**
     * Gets the ALEID value for this ECReports.
     * 
     * @return ALEID
     */
    public java.lang.String getALEID() {
        return ALEID;
    }


    /**
     * Sets the ALEID value for this ECReports.
     * 
     * @param ALEID
     */
    public void setALEID(java.lang.String ALEID) {
        this.ALEID = ALEID;
    }


    /**
     * Gets the totalMilliseconds value for this ECReports.
     * 
     * @return totalMilliseconds
     */
    public long getTotalMilliseconds() {
        return totalMilliseconds;
    }


    /**
     * Sets the totalMilliseconds value for this ECReports.
     * 
     * @param totalMilliseconds
     */
    public void setTotalMilliseconds(long totalMilliseconds) {
        this.totalMilliseconds = totalMilliseconds;
    }


    /**
     * Gets the terminationCondition value for this ECReports.
     * 
     * @return terminationCondition
     */
    public org.accada.ale.xsd.ale.epcglobal.ECTerminationCondition getTerminationCondition() {
        return terminationCondition;
    }


    /**
     * Sets the terminationCondition value for this ECReports.
     * 
     * @param terminationCondition
     */
    public void setTerminationCondition(org.accada.ale.xsd.ale.epcglobal.ECTerminationCondition terminationCondition) {
        this.terminationCondition = terminationCondition;
    }


    /**
     * Gets the schemaURL value for this ECReports.
     * 
     * @return schemaURL
     */
    public java.lang.String getSchemaURL() {
        return schemaURL;
    }


    /**
     * Sets the schemaURL value for this ECReports.
     * 
     * @param schemaURL
     */
    public void setSchemaURL(java.lang.String schemaURL) {
        this.schemaURL = schemaURL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECReports)) return false;
        ECReports other = (ECReports) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.reports==null && other.getReports()==null) || 
             (this.reports!=null &&
              java.util.Arrays.equals(this.reports, other.getReports()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.ECSpec==null && other.getECSpec()==null) || 
             (this.ECSpec!=null &&
              this.ECSpec.equals(other.getECSpec()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this.specName==null && other.getSpecName()==null) || 
             (this.specName!=null &&
              this.specName.equals(other.getSpecName()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.ALEID==null && other.getALEID()==null) || 
             (this.ALEID!=null &&
              this.ALEID.equals(other.getALEID()))) &&
            this.totalMilliseconds == other.getTotalMilliseconds() &&
            ((this.terminationCondition==null && other.getTerminationCondition()==null) || 
             (this.terminationCondition!=null &&
              this.terminationCondition.equals(other.getTerminationCondition()))) &&
            ((this.schemaURL==null && other.getSchemaURL()==null) || 
             (this.schemaURL!=null &&
              this.schemaURL.equals(other.getSchemaURL())));
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
        if (getReports() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReports());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReports(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (getECSpec() != null) {
            _hashCode += getECSpec().hashCode();
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
        if (getSpecName() != null) {
            _hashCode += getSpecName().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getALEID() != null) {
            _hashCode += getALEID().hashCode();
        }
        _hashCode += new Long(getTotalMilliseconds()).hashCode();
        if (getTerminationCondition() != null) {
            _hashCode += getTerminationCondition().hashCode();
        }
        if (getSchemaURL() != null) {
            _hashCode += getSchemaURL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ECReports.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReports"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("specName");
        attrField.setXmlName(new javax.xml.namespace.QName("", "specName"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("date");
        attrField.setXmlName(new javax.xml.namespace.QName("", "date"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("ALEID");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ALEID"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("totalMilliseconds");
        attrField.setXmlName(new javax.xml.namespace.QName("", "totalMilliseconds"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("terminationCondition");
        attrField.setXmlName(new javax.xml.namespace.QName("", "terminationCondition"));
        attrField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTerminationCondition"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("schemaURL");
        attrField.setXmlName(new javax.xml.namespace.QName("", "schemaURL"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reports"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReport"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "report"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECReportsExtension"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ECSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ECSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECSpec"));
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
