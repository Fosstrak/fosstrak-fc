/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE immutable reader exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class ImmutableReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public ImmutableReaderException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public ImmutableReaderException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public ImmutableReaderException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public ImmutableReaderException(String message, Throwable cause) {
		super(message, cause);
	}

}
