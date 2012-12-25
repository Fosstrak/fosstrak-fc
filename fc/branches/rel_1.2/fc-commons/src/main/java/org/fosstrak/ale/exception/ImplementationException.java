/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE implementation exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class ImplementationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public ImplementationException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public ImplementationException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public ImplementationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public ImplementationException(String message, Throwable cause) {
		super(message, cause);
	}

}
