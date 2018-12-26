package com.wolf.core.exception;

public class ExceptionUtil {

	public static void throwException(Errors code, Object... args) {
		if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
			throw new AppException((Throwable) args[args.length - 1], code, args);
		} else {
			throw new AppException(code, args);
		}
	}

	public static void throwException(Throwable e) {
		if (e instanceof AppException) {
			throw (AppException) e;
		} else {
			throw new AppException(e, Errors.ERROR, e);
		}
	}

	public static AppException wrap(Throwable e) {
		if (e instanceof AppException) {
			return (AppException) e;
		} else {
			return new AppException(e, Errors.ERROR, e);
		}
	}

}
