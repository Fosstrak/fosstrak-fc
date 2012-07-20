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
 * @author swieland
 *
 * exception to denote when a service is down or not reachable.
 */
public class FosstrakAleClientServiceDownException extends
		FosstrakAleClientException {

	/**
	 * serial version.
	 */
	private static final long serialVersionUID = 2117214580604962751L;

	/**
	 * exception without description.
	 */
	public FosstrakAleClientServiceDownException() {
	}

	/**
	 * @param message the exception message.
	 */
	public FosstrakAleClientServiceDownException(String message) {
		super(message);
	}

	/**
	 * @param cause the throwable cause.
	 */
	public FosstrakAleClientServiceDownException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message the exception description as a string.
	 * @param cause the cause of the exception.
	 */
	public FosstrakAleClientServiceDownException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param e the child exception.
	 */
	public FosstrakAleClientServiceDownException(Exception e) {
		super(e);
	}
}
