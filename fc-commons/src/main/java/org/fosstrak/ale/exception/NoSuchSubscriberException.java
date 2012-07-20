/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE no such subscriber exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class NoSuchSubscriberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public NoSuchSubscriberException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public NoSuchSubscriberException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public NoSuchSubscriberException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public NoSuchSubscriberException(String message, Throwable cause) {
		super(message, cause);
	}

}
