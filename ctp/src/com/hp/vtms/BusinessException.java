package com.hp.vtms;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2882783418036414472L;

	private String errorMsg;

	public BusinessException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public BusinessException(String message, Throwable cause, String errorMsg) {
		super(message, cause);
		this.errorMsg = errorMsg;
	}

	public BusinessException(String errorMsg, String message) {
		super(message);
		this.errorMsg = errorMsg;
	}

	public BusinessException(String errorMsg, Throwable cause) {
		super(cause);
		this.errorMsg = errorMsg;
	}

	public String geterrorMsg() {
		return errorMsg;
	}

	public void seterrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
