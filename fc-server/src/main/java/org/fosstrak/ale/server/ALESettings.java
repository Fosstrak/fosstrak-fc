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
package org.fosstrak.ale.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * settings class with all application specific settings. <br/>
 * <br/>
 * the content of the settings variables is auto-loaded from the properties file ale.properties
 * @author swieland
 *
 */
@Component("aleSettings")
public class ALESettings {
	
	@Value(value = "${ale.standard.version}")
	private String aleStandardVersion;
	
	@Value(value = "${lr.standard.version}")
	private String lrStandardVersion;
	
	@Value(value = "${vendor.version}")
	private String vendorVersion;

	/**
	 * return the current standard version of the ALE.
	 * @return current standard version.
	 */
	public String getAleStandardVersion() {
		return aleStandardVersion;
	}

	public void setAleStandardVersion(String aleStandardVersion) {
		this.aleStandardVersion = aleStandardVersion;
	}

	/**
	 * return the current standard version of the logical reader management.
	 * @return current standard version.
	 */
	public String getLrStandardVersion() {
		return lrStandardVersion;
	}
	
	public void setLrStandardVersion(String lrStandardVersion) {
		this.lrStandardVersion = lrStandardVersion;
	}

	/**
	 * encodes the current vendor version of this filtering and collection - the current build.
	 * @return current vendor version.
	 */
	public String getVendorVersion() {
		return vendorVersion;
	}

	public void setVendorVersion(String vendorVersion) {
		this.vendorVersion = vendorVersion;
	}
}
