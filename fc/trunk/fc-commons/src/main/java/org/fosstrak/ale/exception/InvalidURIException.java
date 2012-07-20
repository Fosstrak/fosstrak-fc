/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE invalid URI exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class InvalidURIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public InvalidURIException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public InvalidURIException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public InvalidURIException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public InvalidURIException(String message, Throwable cause) {
		super(message, cause);
	}

}
