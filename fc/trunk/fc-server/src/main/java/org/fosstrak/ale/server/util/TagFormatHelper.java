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
package org.fosstrak.ale.server.util;

/**
 * Format the content of an EPC tag into String<br/>
 * <br/>
 * <pre>
 * 	Chapter 4.3.9 Raw Tag URI - TDS_1_1
 * 	RawURI ::= "urn:epc:raw:" RawURIBody( DecimalRawURIBody |
 * 	HexRawURIBody )
 * 	DecimalRawURIBody ::= NonZeroComponent "." NumericComponent
 * 	HexRawURIBody ::= NonZeroComponent ".x" HexComponent
 * </pre>
 * 
 * @author swieland
 *
 */
public final class TagFormatHelper {

	/**
	 * private utility class.
	 */
	private TagFormatHelper() {
	}

	/**
	 * creates a raw epc-decimal representation of a given tag.
	 * @param bitStringLength the length of the bit-string (binary representation).
	 * @param dec the decimal representation of the tag.
	 * @return the formatted epc-decimal representation.
	 */
	public static String formatAsRawDecimal(int bitStringLength, String dec) {
		return String.format("urn:epc:raw:%d.%s", bitStringLength, dec);
	}
	
	/**
	 * creates a epc-hex representation of a given tag.
	 * @param bitStringLength the length of the bit-string (binary representation).
	 * @param hex the hex representation of the tag.
	 * @return the formatted epc-hex representation.
	 */
	public static String formatAsRawHex(int bitStringLength, String hex) {
		return String.format("urn:epc:raw:%d.x%s", bitStringLength, hex);
	}

}
