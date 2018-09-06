package com.edianniu.pscp.mis.commons;

public interface Result {
	public boolean isSuccess();

	public void setSuccess(boolean paramBoolean);

	public int getCode();

	public void setCode(int paramInt);

	public String getMessage();

	public void setMessage(String paramString);
}
