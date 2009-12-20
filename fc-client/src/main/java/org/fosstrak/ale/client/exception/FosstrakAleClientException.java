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

package org.fosstrak.ale.client.exception;


/**
 * @author sawielan
 * exception class for the fosstrak ale client.
 */
public class FosstrakAleClientException extends Exception {

	// serial version uid.
	private static final long serialVersionUID = -3319768609041318326L;

	/**
	 * default exception constructor.
	 */
	public FosstrakAleClientException() {
	}

	/**
	 * @param message exception string.
	 */
	public FosstrakAleClientException(String message) {
		super(message);
	}

	/**
	 * @param cause throwable for the exception.
	 */
	public FosstrakAleClientException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message the exception message as string.
	 * @param cause the cause of the exception.
	 */
	public FosstrakAleClientException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param e a prototype exception.
	 */
	public FosstrakAleClientException(Exception e) {
		super(e);
	}

}
