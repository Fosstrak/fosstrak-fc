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
	
	@Value(value = "${ale.vendor.version}")
	private String aleVendorVersion;
	
	@Value(value = "${ale.lr.readerAPI}")
	private String aleLRReaderAPI;

	public String getAleStandardVersion() {
		return aleStandardVersion;
	}

	public void setAleStandardVersion(String aleStandardVersion) {
		this.aleStandardVersion = aleStandardVersion;
	}

	public String getAleVendorVersion() {
		return aleVendorVersion;
	}

	public void setAleVendorVersion(String aleVendorVersion) {
		this.aleVendorVersion = aleVendorVersion;
	}

	public String getAleLRReaderAPI() {
		return aleLRReaderAPI;
	}

	public void setAleLRReaderAPI(String aleLRReaderAPI) {
		this.aleLRReaderAPI = aleLRReaderAPI;
	}
}
