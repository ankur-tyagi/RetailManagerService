package com.db.retailmanager.api.error;

public class RetailManagerException extends Exception {

	private static final long serialVersionUID = -308764990317218635L;

	private int returnCode;

	public static int RETURN_CODE_OK = 0;
	public static int RETURN_CODE_INVALID_REQUEST = 1;
	//TODO: create separate enum for error codes and add mor error codes

	public RetailManagerException(int returnCode, String message) {
		super(message);
		this.returnCode = returnCode;
	}

	public int getReturnCode() {
		return returnCode;
	}

}
