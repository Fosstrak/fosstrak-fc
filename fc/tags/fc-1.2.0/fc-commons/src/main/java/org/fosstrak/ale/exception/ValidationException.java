/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE validation exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public ValidationException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
