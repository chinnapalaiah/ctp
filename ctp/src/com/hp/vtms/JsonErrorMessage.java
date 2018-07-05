package com.hp.vtms;

public class JsonErrorMessage {

	/**
	 * param error message
	 */
	private String message;
	/**
	 * param error code
	 */
	private String code;
	/**
	 * param error stacktrace
	 */
	private String stacktrace;

	public final String getStacktrace() {
		return stacktrace;
	}

	public final void setStacktrace(final String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final String getCode() {
		return code;
	}

	public final void setCode(final String code) {
		this.code = code;
	}

}
