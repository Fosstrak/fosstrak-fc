/*
 * Copyright (C) 2007 ETH Zurich
 *
 * This file is part of Fosstrak (www.fosstrak.org).
 *
 * Fosstrak is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 *
 * Fosstrak is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Fosstrak; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.fosstrak.ale.util;

import java.math.BigInteger;

/**
 * This method provides some methods to convert byte arrays into hexadecimal strings and vice versa.
 * 
 * @author regli
 */
public class HexUtil {

	/**
	 * This method converts a byte array into a hexadecimal string.
	 * 
	 * @param byteArray to convert
	 * @return hexadecimal string
	 */
	public static String byteArrayToHexString(byte[] byteArray) {
		if (null == byteArray) {
			return "";
		}
		return new BigInteger(byteArray).toString(16).toUpperCase();
				
	}
	
	/**
	 * This method converts a hexadecimal string into a byte array.
	 * 
	 * @param hexString to convert
	 * @return byte array
	 */
	public static byte[] hexStringToByteArray(String hexString) {
		
		return new BigInteger(hexString, 16).toByteArray();
		
	}
	
}