/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE no such name exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class NoSuchNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public NoSuchNameException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public NoSuchNameException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public NoSuchNameException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public NoSuchNameException(String message, Throwable cause) {
		super(message, cause);
	}

}
