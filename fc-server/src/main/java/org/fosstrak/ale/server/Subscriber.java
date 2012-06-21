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

import java.io.CharArrayWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.util.SerializerUtil;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIException;
import org.fosstrak.ale.wsdl.ale.epcglobal.InvalidURIExceptionResponse;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports;
import org.apache.log4j.Logger;

/**
 * This class represents a subscriber of an ec specification.
 * There are three types of such subscribers: http, tcp, file
 * 
 * This class parses and validates the notification url, formats the ec reports and notifies the subscriber.
 * 
 * @author regli
 */
public class Subscriber {

	/** logger */
	private static final Logger LOG = Logger.getLogger(Subscriber.class);
	
	/** number representing a http uri */
	private static final int HTTP = 0;
	/** number representing a tcp uri */
	private static final int TCP = 1;
	/** number representing a file uri */
	private static final int FILE = 2;
	
	/** http uri prefix */
	private static final String HTTP_PREFIX = "http";
	/** tcp uri prefix */
	private static final String TCP_PREFIX = "tcp";
	/** file uri prefix */
	private static final String FILE_PREFIX = "file";
	
	/** message for invalid uri exception */
	private static final String INVALID_URI_EXCEPTION_TEXT = "A valid URI must have one of the following forms: (http://host[:port]/remainder-of-URL | tcp://host:port | file://[host]/path)";

	/** localhost */
	private static final String LOCALHOST = "localhost";
	/** default port */
	private static final int DEFAULT_PORT = 80;

	/** notification nurl */
	private final String uri;
	
	/** number representing the protocol of this subscriber */
	private int protocol;
	/** host name of this subscriber */
	private String host;
	/** port number of this subsriber */
	private int port;
	/** path of this subscriber */
	private String path;

	/**
	 * Constructor parses and validates the notification uri and creates the corresponding subscriber.
	 * 
	 * @param notificationURI of the subscriber 
	 * @throws InvalidURIException if the notification uri is invalid
	 */
	public Subscriber(String notificationURI) throws InvalidURIExceptionResponse {
		
		this.uri = notificationURI;
		
		String[] parts = notificationURI.split(":");
		
		if (parts.length < 2 || parts.length > 3 || parts[1].length() < 3 || !parts[1].startsWith("//")) {
			throw new InvalidURIExceptionResponse(INVALID_URI_EXCEPTION_TEXT);
		}
		
		if (HTTP_PREFIX.equals(parts[0])) {
			
			// http url
			protocol = HTTP;
			
			if (parts.length == 2) {
				
				// default port
				port = DEFAULT_PORT;
				int slashPos = parts[1].indexOf("/", 2);
				host = slashPos < 2 ? parts[1].substring(2) : parts[1].substring(2, slashPos);
				path = slashPos < 2 ? "" : parts[1].substring(slashPos + 1);
				
			} else {
				
				// explicit port
				host = parts[1].substring(2);
				int slashPos = parts[2].indexOf("/");
				try {
					port = Integer.parseInt(slashPos < 0 ? parts[2] : parts[2].substring(0, slashPos));
				} catch (NumberFormatException e) {
					throw new InvalidURIExceptionResponse("Invalid port. " + INVALID_URI_EXCEPTION_TEXT);
				}
				path = slashPos < 0 ? "" : parts[2].substring(slashPos + 1);
				
			}
		} else if (TCP_PREFIX.equals(parts[0])) {
			
			// tcp url
			protocol = TCP;
			
			if (parts.length != 3) {
				throw new InvalidURIExceptionResponse(INVALID_URI_EXCEPTION_TEXT);
			}
			
			host = parts[1].substring(2);
			try {
				port = Integer.parseInt(parts[2]);
			} catch (NumberFormatException e) {
				throw new InvalidURIExceptionResponse("Invalid port. " + INVALID_URI_EXCEPTION_TEXT);
			}
			
			
		} else if (FILE_PREFIX.equals(parts[0])) {
			
			// file url
			protocol = FILE;
			
			if (parts.length == 3) {
				if (parts[1].startsWith("//") && parts[1].length() < 5) {
					parts[1] = parts[1] + ":" + parts[2];
				} else {
					throw new InvalidURIExceptionResponse(INVALID_URI_EXCEPTION_TEXT);
				}
			}
			
			int slashPos = parts[1].indexOf("/", 2);
			
			if (slashPos < 0) {
				throw new InvalidURIExceptionResponse("Invalid path. " + INVALID_URI_EXCEPTION_TEXT);
			}
			
			if (slashPos == 2) {
				host = LOCALHOST;
			} else {
				host = parts[1].substring(2, slashPos);
			}
			
			path = slashPos == parts[1].length() ? parts[1] : parts[1].substring(slashPos + 1);
			
		} else {
			
			// invalid url
			throw new InvalidURIExceptionResponse("Invalid protocol. " + INVALID_URI_EXCEPTION_TEXT);
			
		}
		
	}
	
	/**
	 * This metod returns the notification uri of this subscriber.
	 * 
	 * @return notification uri
	 */
	public String getURI() {
	
		return uri;
		
	}
	
	/**
	 * This method indicates if this subscriber uses the http protocol.
	 * 
	 * @return true if this subscriber uses the http protocol and false otherwise
	 */
	public boolean isHttp() {
		
		return protocol == HTTP;
		
	}
	
	/**
	 * This method indicates if this subscriber uses the tcp protocol.
	 * 
	 * @return true if this subscriber uses the tcp protocol and false otherwise
	 */
	public boolean isTcp() {
		
		return protocol == TCP;
		
	}
	
	/**
	 * This method indicates if this subscriber uses the file protocol.
	 * 
	 * @return true if this subscriber uses the file protocol and false otherwise
	 */
	public boolean isFile() {
		
		return protocol == FILE;
		
	}
	
	/**
	 * This method returns the host name of this subscriber.
	 * 
	 * @return host name
	 */
	public String getHost() {
		
		return host;
		
	}
	
	/**
	 * This method returns the port number of this subscriber.
	 * 
	 * @return port number
	 */
	public int getPort() {
		
		return port;
		
	}
	
	/**
	 * This method returns the path of this subscriber.
	 * 
	 * @return path
	 */
	public String getPath() {
		
		return path;
		
	}

	/**
	 * This method notifies the subscriber about the ec reports.
	 * 
	 * @param reports to notify the subscriber about
	 * @throws ImplementationException if an implementation exception occures
	 */
	public void notify(ECReports reports) throws ImplementationExceptionResponse {
		
		if (isHttp()) {
			
			// write reports as post request to http socket
			LOG.debug("Write reports '" + reports.getSpecName() + "' as post request to http socket '" + host + ":" + port + "'.");
			writeToSocket(getPostRequest(reports));
			
		} else if (isTcp()) {
			
			// write reports as xml to tcp socket
			LOG.debug("Write reports '" + reports.getSpecName() + "' as xml to tcp socket '" + host + ":" + port + "'.");
			writeToSocket(getXml(reports));
			
			
		} else if (isFile()) {
			
			writeNotificationToFile(reports);
			
		}
		
	}
	
	//
	// private methods
	//
	
	/**
	 * This method writes data to a socket with host name and port number of this subscriber.
	 * 
	 * @param data to write to the socket
	 * @throws ImplementationException if an implementation exception occures
	 */
	private void writeToSocket(String data) throws ImplementationExceptionResponse {
	
		Socket socket;
		try {
			
			// open socket and stream
			socket = new Socket(host, port);
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			// write reports
			dataOutputStream.writeBytes(data);
			dataOutputStream.write("\n".getBytes());
			dataOutputStream.flush();
			
			// close socket and stream
			dataOutputStream.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			throw new ImplementationExceptionResponse("Host '" + host + "' not found.");
		} catch (IOException e) {
			throw new ImplementationExceptionResponse("Could not write data to socket at '" + host + ":" + port + "'.");
		}
		
	}
	
	/**
	 * This method writes ec reports to a file.
	 * 
	 * @param reports to write to the file
	 * @throws ImplementationException if an implementation exception occures
	 */
	private void writeNotificationToFile(ECReports reports) throws ImplementationExceptionResponse {
		
		// append reports as xml to file
		LOG.debug("Append reports '" + reports.getSpecName() + "' as xml to file '" + path + "'.");

		// can only write to local files
		if (LOCALHOST.equals(host)) {
			
			File file = new File(path);
			
			// create file if it does not already exists
			if (!file.exists() || !file.isFile()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw new ImplementationExceptionResponse("Could not create new file '" + path + "'.");
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
				throw new ImplementationExceptionResponse("Could not write to file '" + path + "'.");
			}
		
		} else {
			throw new ImplementationExceptionResponse("This implementation can not write reports to a remote file.");
		}
		
	}
	
	/**
	 * This method serializes ec reports into a xml representation.
	 * 
	 * @param reports to serialize
	 * @return xml representation of the ec reports
	 * @throws ImplementationException if a implementation exception occurs
	 */
	private String getXml(ECReports reports) throws ImplementationExceptionResponse {
	
		CharArrayWriter writer = new CharArrayWriter();
		try {			
			SerializerUtil.serializeECReports(reports, writer);
		} catch (Exception e) {
			throw new ImplementationExceptionResponse("Unable to serialze reports. (" + e.getMessage() + ")");
		}
		return writer.toString();
		
	}
	
	/**
	 * This method serializes ec reports into a well formed xml representation. 
	 * 
	 * @param reports to serialize
	 * @return well formed xml representation of the ec reports
	 * @throws ImplementationException if a implementation exception occurs
	 */
	private String getPrettyXml(ECReports reports) throws ImplementationExceptionResponse {
		
		CharArrayWriter writer = new CharArrayWriter();
		try {
			SerializerUtil.serializeECReports(reports, writer);
		} catch (Exception e) {
			throw new ImplementationExceptionResponse("Unable to serialze reports. (" + e.getMessage() + ")");
		}
		return writer.toString();
		
	}	
	
	/**
	 * This method creates a post request from ec reports containing an xml representation of the reports.
	 * 
	 * @param reports to transform into a post request
	 * @return post request containing an xml representation of the reports
	 * @throws ImplementationException if an implementation exception occurs
	 */
	private String getPostRequest(ECReports reports) throws ImplementationExceptionResponse {
		
		LOG.debug("Create POST request with reports '" + reports.getSpecName() + "'.");
		
		// create body
		String body = getXml(reports);
		
		// create header
		StringBuffer header = new StringBuffer();
		
		// append request line
		header.append("POST ");
		// add the trimmed / again together with a white space
		String p = "/" + path + " ";
		header.append(p);
			
		header.append("HTTP/1.1");
		header.append("\n");
		
		// append host
		header.append("Host: ");
		// append port 
    // patch by Gianrico D'Angelis <gianrico.dangelis@gmail.com>  
    header.append(host+":"+port);  
		header.append("\n");
		
    
      
		// append content type
		header.append("Content-Type: ");
		header.append("text/xml; charset=\"utf-8\"");
		header.append("\n");
		
		// append content length
		header.append("Content-Length: ");
		header.append(body.length());
		header.append("\n");
		
		// terminate body
		header.append("\n");

		return header + body;
		
	}
	
}