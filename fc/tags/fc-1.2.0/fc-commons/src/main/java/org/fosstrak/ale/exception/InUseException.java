/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE in use exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class InUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public InUseException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public InUseException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public InUseException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public InUseException(String message, Throwable cause) {
		super(message, cause);
	}

}
