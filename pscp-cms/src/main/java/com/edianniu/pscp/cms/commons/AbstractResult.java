package com.edianniu.pscp.cms.commons;

public abstract class AbstractResult implements Result{
	protected int code=ResultCode.SUCCESS;
	protected String message;
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public boolean isSuccess() {
		if(code==ResultCode.SUCCESS){
			return true;
		}
		return false;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public void set(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
