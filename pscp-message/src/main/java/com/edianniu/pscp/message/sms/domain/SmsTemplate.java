package com.edianniu.pscp.message.sms.domain;

import com.edianniu.pscp.message.commons.BaseDo;

/**
 * ClassName: SmsTemplate
 * Author: tandingbo
 * CreateTime: 2017-05-02 14:39
 */
public class SmsTemplate extends BaseDo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String msgId;
    private String context;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
