package com.wolf.core.exception;

import java.text.MessageFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppException extends RuntimeException {

	private static final long serialVersionUID = -5284980920186855151L;
	private Errors code;
	// private Object[] args;
	private Throwable error;

	AppException(Errors code, Object... args) {
		super(MessageFormat.format(code.toString(), args));
		this.code = code;
	}

	AppException(Throwable e, Errors code, Object... args) {
		super(MessageFormat.format(code.toString(), args), e);
		this.error = e;
	}

	public int getCode() {
		return code.code();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return error == null ? null : error.getStackTrace();
	}

	@Override
	public void printStackTrace() {
		if (error != null) {
			error.printStackTrace();
		} else {
			log.warn(this.getMessage());
		}
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String toString() {
		return getMessage();
	}

}
