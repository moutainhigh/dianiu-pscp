package com.edianniu.pscp.message.commons;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public abstract class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private int resultCode = ResultCode.SUCCESS;
	private String resultMessage = "成功";

	public boolean isSuccess(){
		if(resultCode==ResultCode.SUCCESS){
			return true;
		}
		return false;
	}
	public int getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
    public void set(int resultCode,String resultMessage){
    	this.resultCode=resultCode;
    	this.resultMessage=resultMessage;
    }
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
