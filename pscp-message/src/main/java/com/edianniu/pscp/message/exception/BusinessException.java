/**
 * 
 */
package com.edianniu.pscp.message.exception;

/**
 * @author cyl
 *
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
