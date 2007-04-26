/*
 * Copyright (c) 2006, ETH Zurich
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the ETH Zurich nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis.encoding.ser;

import org.accada.ale.util.ECTimeUnitDeserializerFactory;
import org.accada.ale.xsd.ale.epcglobal.ECTimeUnit;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.SimpleType;
import org.apache.axis.encoding.TypeMapping;
import org.apache.axis.message.SOAPHandler;
import org.apache.axis.utils.BeanPropertyDescriptor;
import org.apache.axis.utils.BeanUtils;
import org.apache.axis.utils.Messages;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


import javax.xml.namespace.QName;
import java.io.CharArrayWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A deserializer for any simple type with a (String) constructor.  Note:
 * this class is designed so that subclasses need only override the makeValue 
 * method in order to construct objects of their own type.
 *
 * @author Glen Daniels (gdaniels@apache.org)
 * @author Sam Ruby (rubys@us.ibm.com)
 * Modified for JAX-RPC @author Rich Scheuerle (scheu@us.ibm.com)
 * 
 * Modified for accada fc-client:
 * The class has been integrated into the ale client and modified 
 * as a workaround in order to be able to parse the ECSpec Files.
 * The workaround will be removed in the next patch.
 */
public class SimpleDeserializer extends DeserializerImpl {
    
    private static final Class[] STRING_STRING_CLASS = 
        new Class [] {String.class, String.class};

    public static final Class[] STRING_CLASS = 
        new Class [] {String.class};

    private final CharArrayWriter val = new CharArrayWriter();
    private Constructor constructor = null;
    private Map propertyMap = null;
    private HashMap attributeMap = null;
    
    public QName xmlType;
    public Class javaType;
    
    private TypeDesc typeDesc = null;

    protected DeserializationContext context = null;
    protected SimpleDeserializer cacheStringDSer = null;
    protected QName cacheXMLType = null;
    /**
     * The Deserializer is constructed with the xmlType and 
     * javaType (which could be a java primitive like int.class)
     */
    public SimpleDeserializer(Class javaType, QName xmlType) {
         this.xmlType = xmlType;
        this.javaType = javaType;
        
        init();
    }

    public SimpleDeserializer(Class javaType, QName xmlType, TypeDesc typeDesc) {
        this.xmlType = xmlType;
        this.javaType = javaType;
        this.typeDesc = typeDesc;
        
        init();
    }    

    /**
     * Initialize the typeDesc, property descriptors and propertyMap.
     */
    private void init() {
        // The typeDesc and map array are only necessary
        // if this class extends SimpleType.
        if (SimpleType.class.isAssignableFrom(javaType)) {
            // Set the typeDesc if not already set
            if (typeDesc == null) {
                typeDesc = TypeDesc.getTypeDescForClass(javaType);
            }
        }

        // Get the cached propertyDescriptor from the type or
        // generate a fresh one.
        if (typeDesc != null) {
            propertyMap = typeDesc.getPropertyDescriptorMap();
        } else {
            BeanPropertyDescriptor[] pd = BeanUtils.getPd(javaType, null);
            propertyMap = new HashMap();
            for (int i = 0; i < pd.length; i++) {
                BeanPropertyDescriptor descriptor = pd[i];
                propertyMap.put(descriptor.getName(), descriptor);
            }
        }
    }
    
    /**
     * Reset deserializer for re-use
     */
    public void reset() {
        val.reset();
        attributeMap = null; // Remove attribute map
        isNil = false; // Don't know if nil
        isEnded = false; // Indicate the end of element not yet called
    }
    
    /** 
     * The Factory calls setConstructor.
     */
    public void setConstructor(Constructor c) 
    {
        constructor = c;
    }
    
    /**
     * There should not be nested elements, so thow and exception if this occurs.
     */
    public SOAPHandler onStartChild(String namespace,
                                    String localName,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
            throws SAXException
    {
        throw new SAXException(
                Messages.getMessage("cantHandle00", "SimpleDeserializer"));
    }
    
    /**
     * Append any characters received to the value.  This method is defined 
     * by Deserializer.
     */
    public void characters(char [] chars, int start, int end)
            throws SAXException
    {
        val.write(chars,start,end);
    }
    
    /**
     * Append any characters to the value.  This method is defined by 
     * Deserializer.
     */
    public void onEndElement(String namespace, String localName,
                             DeserializationContext context)
            throws SAXException
    {
        if (isNil) {
            value = null;
            return;
        }
        try {
            value = makeValue(val.toString());
        } catch (InvocationTargetException ite) {
            Throwable realException = ite.getTargetException();
            if (realException instanceof Exception)
                throw new SAXException((Exception)realException);
            else
                throw new SAXException(ite.getMessage());
        } catch (Exception e) {
            throw new SAXException(e);
        }
        
        // If this is a SimpleType, set attributes we have stashed away
        setSimpleTypeAttributes();
    }
    
    /**
     * Convert the string that has been accumulated into an Object.  Subclasses
     * may override this.  Note that if the javaType is a primitive, the returned
     * object is a wrapper class.
     * @param source the serialized value to be deserialized
     * @throws Exception any exception thrown by this method will be wrapped
     */
    public Object makeValue(String source) throws Exception
    {
        if (javaType == java.lang.String.class) {
            return source;
        }

        // Trim whitespace if non-String
        source = source.trim();

        if (source.length() == 0 && typeDesc == null) {
            return null;
        }
        
        // if constructor is set skip all basic java type checks
        if (this.constructor == null) {
            Object value = makeBasicValue(source);
            if (value != null) {
                return value;
            }
        }
            
        Object [] args = null;
        
        boolean isQNameSubclass = QName.class.isAssignableFrom(javaType);

        if (isQNameSubclass) {
            int colon = source.lastIndexOf(":");
            String namespace = colon < 0 ? "" :
                context.getNamespaceURI(source.substring(0, colon));
            String localPart = colon < 0 ? source : source.substring(colon + 1);
            args = new Object [] {namespace, localPart};
        } 

        if (constructor == null) {
            try {
                if (isQNameSubclass) {
                    constructor = 
                        javaType.getDeclaredConstructor(STRING_STRING_CLASS);
                } else {
                    constructor = 
                        javaType.getDeclaredConstructor(STRING_CLASS);
                }
            } catch (Exception e) {
                return null;
            }
        }
        
        if(constructor.getParameterTypes().length==0){
            try {
                Object obj = constructor.newInstance(new Object[]{});
                obj.getClass().getMethod("set_value", new Class[]{String.class})
                        .invoke(obj, new Object[]{source});
                return obj;
            } catch (Exception e){
                //Ignore exception
            }
        }
        if (args == null) {
            args = new Object[]{source};
        }
        return constructor.newInstance(args);
    }

    private Object makeBasicValue(String source) throws Exception {
        // If the javaType is a boolean, except a number of different sources
        if (javaType == boolean.class || 
            javaType == Boolean.class) {
            // This is a pretty lame test, but it is what the previous code did.
            switch (source.charAt(0)) {
                case '0': case 'f': case 'F':
                    return Boolean.FALSE;
                    
                case '1': case 't': case 'T': 
                    return Boolean.TRUE; 
                    
                default:
                    throw new NumberFormatException(
                            Messages.getMessage("badBool00"));
                }
            
        }
        
        // If expecting a Float or a Double, need to accept some special cases.
        if (javaType == float.class ||
            javaType == java.lang.Float.class) {
            if (source.equals("NaN")) {
                return new Float(Float.NaN);
            } else if (source.equals("INF")) {
                return new Float(Float.POSITIVE_INFINITY);
            } else if (source.equals("-INF")) {
                return new Float(Float.NEGATIVE_INFINITY);
            } else {
                return new Float(source);
            }
        }
        
        if (javaType == double.class ||
            javaType == java.lang.Double.class) {
            if (source.equals("NaN")) {
                return new Double(Double.NaN);
            } else if (source.equals("INF")) {
                return new Double(Double.POSITIVE_INFINITY);
            } else if (source.equals("-INF")) {
                return new Double(Double.NEGATIVE_INFINITY);
            } else {
                return new Double(source);
            }
        }    
        
        if (javaType == int.class ||
            javaType == java.lang.Integer.class) {
            return new Integer(source);
        }
        
        if (javaType == short.class ||
            javaType == java.lang.Short.class) {
            return new Short(source);
        }
        
        if (javaType == long.class ||
            javaType == java.lang.Long.class) {
            return new Long(source);
        }
        
        if (javaType == byte.class ||
            javaType == java.lang.Byte.class) {
            return new Byte(source);
        }
        
        if (javaType == org.apache.axis.types.URI.class) {
            return new org.apache.axis.types.URI(source);
        }

        return null;
    }
        
    /**
     * Set the bean properties that correspond to element attributes.
     * 
     * This method is invoked after startElement when the element requires
     * deserialization (i.e. the element is not an href and the value is not nil.)
     * @param namespace is the namespace of the element
     * @param localName is the name of the element
     * @param prefix is the prefix of the element
     * @param attributes are the attributes on the element...used to get the type
     * @param context is the DeserializationContext
     */
    public void onStartElement(String namespace, String localName,
                               String prefix, Attributes attributes,
                               DeserializationContext context)
            throws SAXException 
    {
        
        this.context = context;

        // loop through the attributes and set bean properties that
        // correspond to attributes
        for (int i=0; i < attributes.getLength(); i++) {
            QName attrQName = new QName(attributes.getURI(i),
                                        attributes.getLocalName(i));
            
            String fieldName = attributes.getLocalName(i);
            
            if(typeDesc != null) {
                fieldName = typeDesc.getFieldNameForAttribute(attrQName);
                if (fieldName == null)
                    continue;
            }

            if (propertyMap == null)
                continue;
            
            // look for the attribute property
            BeanPropertyDescriptor bpd =
                    (BeanPropertyDescriptor) propertyMap.get(fieldName);
            if (bpd != null) {
                if (!bpd.isWriteable() || bpd.isIndexed() ) continue ;
                
                // determine the QName for this child element
                TypeMapping tm = context.getTypeMapping();
                
                
                
                
                
                //-------------- MODIFIED by regli --------------
                QName timeUnitQName = new QName("urn:epcglobal:ale:xsd:1", "ECTimeUnit");
                tm.register(ECTimeUnit.class, timeUnitQName, null, new ECTimeUnitDeserializerFactory(ECTimeUnit.class, timeUnitQName));
                //-----------------------------------------------
                
                
                
                
                
                Class type = bpd.getType();
                QName qn = tm.getTypeQName(type);
                if (qn == null)
                    throw new SAXException(
                            Messages.getMessage("unregistered00", type.toString()));
                
                // get the deserializer
                Deserializer dSer = context.getDeserializerForType(qn);
                if (dSer == null)
                    throw new SAXException(
                            Messages.getMessage("noDeser00", type.toString()));
                if (! (dSer instanceof SimpleDeserializer))
                    throw new SAXException(
                            Messages.getMessage("AttrNotSimpleType00",
                                                bpd.getName(),
                                                type.toString()));
                
                // Success!  Create an object from the string and save
                // it in our attribute map for later.
                if (attributeMap == null) {
                    attributeMap = new HashMap();
                }
                try {
                    Object val = ((SimpleDeserializer)dSer).
                            makeValue(attributes.getValue(i));
                    attributeMap.put(fieldName, val);
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            } // if
        } // attribute loop
    } // onStartElement
    
    /**
     * Process any attributes we may have encountered (in onStartElement)
     */ 
    private void setSimpleTypeAttributes() throws SAXException {
        if (attributeMap == null)
            return;
        
        // loop through map
        Set entries = attributeMap.entrySet();
        for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            Object val = entry.getValue();
            
            BeanPropertyDescriptor bpd =
                    (BeanPropertyDescriptor) propertyMap.get(name);
            if (!bpd.isWriteable() || bpd.isIndexed()) continue;
            try {
                bpd.set(value, val );
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
}