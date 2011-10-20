package org.fosstrak.ale.server.llrp;

/**
 * ORANGE: This class defines the duplicate name exception for LLRP Reader.
 * 
 * @author wafa.soubra@orange.com
 */
public class DuplicateNameExceptionResponse extends Exception {
	
	private static final long serialVersionUID = 1L;

	public static final String DUPLICATE_NAME_EXCEPTION = "This reader is already used";
	
	public String message;

	public DuplicateNameExceptionResponse() {
		super();
		this.message = DUPLICATE_NAME_EXCEPTION;
	}
	
	public DuplicateNameExceptionResponse(String message) {
		super(message);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
