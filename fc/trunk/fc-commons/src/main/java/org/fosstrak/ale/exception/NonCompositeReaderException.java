/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE non composite reader exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class NonCompositeReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public NonCompositeReaderException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public NonCompositeReaderException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public NonCompositeReaderException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public NonCompositeReaderException(String message, Throwable cause) {
		super(message, cause);
	}

}
