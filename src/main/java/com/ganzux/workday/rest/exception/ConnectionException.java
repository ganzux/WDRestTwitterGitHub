package com.ganzux.workday.rest.exception;

public class ConnectionException extends Exception{

	private static final long serialVersionUID = 1L;

	public ConnectionException(String string, Exception ex) {
		super(string, ex);
	}

}
