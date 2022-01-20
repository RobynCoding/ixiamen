package com.ixiamen.activity.util;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * Bean Util转换异常，一般由IllegalAccessException, InvocationTargetException, NoSuchMethodException等引起
 * @author luoyongbin
 * @version 1.0 
 */
public class BeanUtilException extends NestableRuntimeException {
	private static final long serialVersionUID = 1L;

	public BeanUtilException() {
		super();
	}

	public BeanUtilException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public BeanUtilException(String msg) {
		super(msg);
	}

	public BeanUtilException(Throwable cause) {
		super(cause);
	}

}
