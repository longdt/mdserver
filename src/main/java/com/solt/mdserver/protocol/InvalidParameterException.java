package com.solt.mdserver.protocol;

public class InvalidParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidParameterException() {
	}
	
	public InvalidParameterException(String msg) {
		super(msg);
	}

}
