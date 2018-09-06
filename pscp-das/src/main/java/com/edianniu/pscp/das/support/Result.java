package com.edianniu.pscp.das.support;

import java.io.Serializable;

/**
 * ClassName: Result
 * Author: tandingbo
 * CreateTime: 2017-10-11 10:57
 */
public abstract class Result implements Serializable {
    private static final long serialVersionUID = 8180755321811187815L;

    private static final int SUCCESS = 200;

    private int resultCode = SUCCESS;
    private String resultMessage = "成功";

    public boolean isSuccess() {
        if (resultCode == SUCCESS) {
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
}
