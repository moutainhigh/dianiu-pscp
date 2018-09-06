package com.edianniu.pscp.cs.bean;

import com.edianniu.pscp.cs.commons.ResultCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public abstract class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resultCode = ResultCode.SUCCESS;
    private String resultMessage = "成功";
    private Object object;

    public boolean isSuccess() {
        if (resultCode == ResultCode.SUCCESS) {
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

    public void set(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
