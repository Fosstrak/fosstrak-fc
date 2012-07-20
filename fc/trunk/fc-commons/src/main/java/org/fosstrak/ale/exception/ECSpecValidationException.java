/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE ec spec validation exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class ECSpecValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public ECSpecValidationException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public ECSpecValidationException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public ECSpecValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public ECSpecValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
