package com.edianniu.pscp.message.push.domain;

import com.edianniu.pscp.message.commons.DefaultResult;

public class PushResult extends DefaultResult {
    private static final long serialVersionUID = 1L;

    private String result;
    private String contentId;
    private String taskId;
    private String status;
    private String response;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
