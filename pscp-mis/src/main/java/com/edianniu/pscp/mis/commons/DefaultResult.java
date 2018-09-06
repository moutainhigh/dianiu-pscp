package com.edianniu.pscp.mis.commons;

public class DefaultResult extends AbstractResult implements Result {
	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
