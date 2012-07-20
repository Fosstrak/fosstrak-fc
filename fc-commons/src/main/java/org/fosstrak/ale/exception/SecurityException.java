/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE security exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class SecurityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public SecurityException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public SecurityException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public SecurityException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

}
