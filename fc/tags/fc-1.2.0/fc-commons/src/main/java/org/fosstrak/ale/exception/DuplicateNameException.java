/**
 * 
 */
package org.fosstrak.ale.exception;

/**
 * business exception for the ALE duplicate name exception. this exception is not used on WSDL interfaces - only ALE internal.
 * 
 * @author swieland
 *
 */
public class DuplicateNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * empty implementation exception.
	 */
	public DuplicateNameException() {
		super();
	}

	/**
	 * @param message exception string.
	 */
	public DuplicateNameException(String message) {
		super(message);
	}

	/**
	 * @param cause exception cause.
	 */
	public DuplicateNameException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message exception string.
	 * @param cause exception cause.
	 */
	public DuplicateNameException(String message, Throwable cause) {
		super(message, cause);
	}

}
