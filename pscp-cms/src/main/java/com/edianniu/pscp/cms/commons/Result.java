package com.edianniu.pscp.cms.commons;

public interface Result {
	public boolean isSuccess();
	public int getCode();
	public String getMessage();
	public void setCode(int code);
	public void setMessage(String message);
	public void set(int code,String message);
}
