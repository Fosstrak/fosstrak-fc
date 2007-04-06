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
 * ECBoundarySpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.accada.ale.xsd.ale.epcglobal;


/**
 * A ECBoundarySpec specifies how the beginning and end
 *                 of event cycles are to be determined. The startTrigger
 * and repeatPeriod elements are
 *                 mutually exclusive. One may, however, specify a ECBoundarySpec
 * with neither event
 *                 cycle start condition (i.e., startTrigger nor repeatPeriod)
 * present. At least one
 *                 event cycle stopping condition (stopTrigger, duration,
 * and/or stableSetInterval)
 *                 must be present.
 */
public class ECBoundarySpec  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.accada.ale.xsd.ale.epcglobal.ECTrigger startTrigger;

    private org.accada.ale.xsd.ale.epcglobal.ECTime repeatPeriod;

    private org.accada.ale.xsd.ale.epcglobal.ECTrigger stopTrigger;

    private org.accada.ale.xsd.ale.epcglobal.ECTime duration;

    private org.accada.ale.xsd.ale.epcglobal.ECTime stableSetInterval;

    private org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension extension;

    private org.apache.axis.message.MessageElement [] _any;

    public ECBoundarySpec() {
    }

    public ECBoundarySpec(
           org.accada.ale.xsd.ale.epcglobal.ECTrigger startTrigger,
           org.accada.ale.xsd.ale.epcglobal.ECTime repeatPeriod,
           org.accada.ale.xsd.ale.epcglobal.ECTrigger stopTrigger,
           org.accada.ale.xsd.ale.epcglobal.ECTime duration,
           org.accada.ale.xsd.ale.epcglobal.ECTime stableSetInterval,
           org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension extension,
           org.apache.axis.message.MessageElement [] _any) {
           this.startTrigger = startTrigger;
           this.repeatPeriod = repeatPeriod;
           this.stopTrigger = stopTrigger;
           this.duration = duration;
           this.stableSetInterval = stableSetInterval;
           this.extension = extension;
           this._any = _any;
    }


    /**
     * Gets the startTrigger value for this ECBoundarySpec.
     * 
     * @return startTrigger
     */
    public org.accada.ale.xsd.ale.epcglobal.ECTrigger getStartTrigger() {
        return startTrigger;
    }


    /**
     * Sets the startTrigger value for this ECBoundarySpec.
     * 
     * @param startTrigger
     */
    public void setStartTrigger(org.accada.ale.xsd.ale.epcglobal.ECTrigger startTrigger) {
        this.startTrigger = startTrigger;
    }


    /**
     * Gets the repeatPeriod value for this ECBoundarySpec.
     * 
     * @return repeatPeriod
     */
    public org.accada.ale.xsd.ale.epcglobal.ECTime getRepeatPeriod() {
        return repeatPeriod;
    }


    /**
     * Sets the repeatPeriod value for this ECBoundarySpec.
     * 
     * @param repeatPeriod
     */
    public void setRepeatPeriod(org.accada.ale.xsd.ale.epcglobal.ECTime repeatPeriod) {
        this.repeatPeriod = repeatPeriod;
    }


    /**
     * Gets the stopTrigger value for this ECBoundarySpec.
     * 
     * @return stopTrigger
     */
    public org.accada.ale.xsd.ale.epcglobal.ECTrigger getStopTrigger() {
        return stopTrigger;
    }


    /**
     * Sets the stopTrigger value for this ECBoundarySpec.
     * 
     * @param stopTrigger
     */
    public void setStopTrigger(org.accada.ale.xsd.ale.epcglobal.ECTrigger stopTrigger) {
        this.stopTrigger = stopTrigger;
    }


    /**
     * Gets the duration value for this ECBoundarySpec.
     * 
     * @return duration
     */
    public org.accada.ale.xsd.ale.epcglobal.ECTime getDuration() {
        return duration;
    }


    /**
     * Sets the duration value for this ECBoundarySpec.
     * 
     * @param duration
     */
    public void setDuration(org.accada.ale.xsd.ale.epcglobal.ECTime duration) {
        this.duration = duration;
    }


    /**
     * Gets the stableSetInterval value for this ECBoundarySpec.
     * 
     * @return stableSetInterval
     */
    public org.accada.ale.xsd.ale.epcglobal.ECTime getStableSetInterval() {
        return stableSetInterval;
    }


    /**
     * Sets the stableSetInterval value for this ECBoundarySpec.
     * 
     * @param stableSetInterval
     */
    public void setStableSetInterval(org.accada.ale.xsd.ale.epcglobal.ECTime stableSetInterval) {
        this.stableSetInterval = stableSetInterval;
    }


    /**
     * Gets the extension value for this ECBoundarySpec.
     * 
     * @return extension
     */
    public org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ECBoundarySpec.
     * 
     * @param extension
     */
    public void setExtension(org.accada.ale.xsd.ale.epcglobal.ECBoundarySpecExtension extension) {
        this.extension = extension;
    }


    /**
     * Gets the _any value for this ECBoundarySpec.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this ECBoundarySpec.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ECBoundarySpec)) return false;
        ECBoundarySpec other = (ECBoundarySpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.startTrigger==null && other.getStartTrigger()==null) || 
             (this.startTrigger!=null &&
              this.startTrigger.equals(other.getStartTrigger()))) &&
            ((this.repeatPeriod==null && other.getRepeatPeriod()==null) || 
             (this.repeatPeriod!=null &&
              this.repeatPeriod.equals(other.getRepeatPeriod()))) &&
            ((this.stopTrigger==null && other.getStopTrigger()==null) || 
             (this.stopTrigger!=null &&
              this.stopTrigger.equals(other.getStopTrigger()))) &&
            ((this.duration==null && other.getDuration()==null) || 
             (this.duration!=null &&
              this.duration.equals(other.getDuration()))) &&
            ((this.stableSetInterval==null && other.getStableSetInterval()==null) || 
             (this.stableSetInterval!=null &&
              this.stableSetInterval.equals(other.getStableSetInterval()))) &&
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
        if (getStartTrigger() != null) {
            _hashCode += getStartTrigger().hashCode();
        }
        if (getRepeatPeriod() != null) {
            _hashCode += getRepeatPeriod().hashCode();
        }
        if (getStopTrigger() != null) {
            _hashCode += getStopTrigger().hashCode();
        }
        if (getDuration() != null) {
            _hashCode += getDuration().hashCode();
        }
        if (getStableSetInterval() != null) {
            _hashCode += getStableSetInterval().hashCode();
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
        new org.apache.axis.description.TypeDesc(ECBoundarySpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECBoundarySpec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTrigger");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startTrigger"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTrigger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repeatPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "repeatPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stopTrigger");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stopTrigger"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTrigger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("", "duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stableSetInterval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stableSetInterval"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:epcglobal:ale:xsd:1", "ECBoundarySpecExtension"));
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
