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

package org.accada.ale.util;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.ser.BaseDeserializerFactory;

/**
 * This class is a deserializer factory for ECTimeUnit.
 * 
 * @author regli
 */
public class ECTimeUnitDeserializerFactory extends BaseDeserializerFactory {

	/**
	 * Constructor.
	 * 
	 * @param javaType
	 * @param xmlType
	 */
    public ECTimeUnitDeserializerFactory(Class javaType, QName xmlType) {
        super(DeserializerImpl.class, xmlType, javaType);
    }
    
}