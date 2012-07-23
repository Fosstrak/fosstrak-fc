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
package org.fosstrak.ale.server.type;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InvalidURIException;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.springframework.util.StringUtils;

/**
 * deliver a message using a file.
 * @author swieland
 */
public class FileSubscriberOutputChannel extends AbstractSubscriberOutputChannel {
	
	/** logger */
	private static final Logger LOG = Logger.getLogger(FileSubscriberOutputChannel.class);

	/** localhost */
	private static final String LOCALHOST = "localhost";

	private URI uri;
	private final String host;
	private final String path;
	
	public FileSubscriberOutputChannel(String notificationURI) throws InvalidURIException {
		super(notificationURI);
		try {
			uri = new URI(notificationURI);
			path = StringUtils.startsWithIgnoreCase(uri.getPath(), "/") ? uri.getPath().substring(1) : uri.getPath();
			if ("".equalsIgnoreCase(path) || StringUtils.endsWithIgnoreCase(path, "/")) {
				throw new InvalidURIException("missing filename");				
			}
			host = (uri.getHost() == null) ? LOCALHOST.toLowerCase() : uri.getHost();
			if (!(LOCALHOST.equalsIgnoreCase(getHost()))) {
				throw new InvalidURIException("This implementation can not write reports to a remote file.");
			}
			if (!("FILE".equalsIgnoreCase(uri.getScheme()))) {
				LOG.error("invalid scheme: " + uri.getScheme());
				throw new InvalidURIException("invalid scheme: " + uri.getScheme());
			}
		} catch (Exception e) {
			LOG.error("malformed URI");
			throw new InvalidURIException("malformed URI: ", e);
		} 
	}
	
	@Override
	public boolean notify(ECReports reports) throws ImplementationException {
		writeNotificationToFile(reports);
		return true;
	}
	
	/**
	 * This method writes ec reports to a file.
	 * 
	 * @param reports to write to the file
	 * @throws ImplementationException if an implementation exception occures
	 */
	private void writeNotificationToFile(ECReports reports) throws ImplementationException {		
		// append reports as xml to file
		LOG.debug("Append reports '" + reports.getSpecName() + "' as xml to file '" + getPath() + "'.");

		File file = getFile();
		
		// create file if it does not already exists
		if (!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new ImplementationException("Could not create new file '" + getPath() + "'.", e);
			}
		}
		
		try {
			
			// open streams
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

			// append reports as xml to file
			dataOutputStream.writeBytes(getPrettyXml(reports));
			dataOutputStream.writeBytes("\n\n");
			dataOutputStream.flush();
			
			// close streams
			dataOutputStream.close();
			fileOutputStream.close();
			
		} catch (IOException e) {
			throw new ImplementationException("Could not write to file '" + getPath() + "'.", e);
		}		
	}

	protected File getFile() {
		return new File(getPath());
	}

	public String getHost() {
		return host;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return uri.toString();
	}
}

