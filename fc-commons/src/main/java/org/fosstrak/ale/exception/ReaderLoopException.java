/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE reader loop exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class ReaderLoopException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public ReaderLoopException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public ReaderLoopException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public ReaderLoopException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public ReaderLoopException(String message, Throwable cause) {
		super(message, cause);
	}

}
