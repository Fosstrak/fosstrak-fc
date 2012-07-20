/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE duplicate subscription exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class DuplicateSubscriptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public DuplicateSubscriptionException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public DuplicateSubscriptionException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public DuplicateSubscriptionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public DuplicateSubscriptionException(String message, Throwable cause) {
		super(message, cause);
	}

}
