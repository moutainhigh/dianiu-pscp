package com.edianniu.pscp.message.sms.domain;

import com.edianniu.pscp.message.commons.BaseDo;

import java.util.Date;

/**
 * ClassName: SmsSendLog
 * Author: tandingbo
 * CreateTime: 2017-05-02 14:38
 */
public class SmsSendLog extends BaseDo {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String mobile;
    private String content;
    private String msgId;
    private String msgStatus;
    private Integer status;
    private Integer channelType;
    private String err;
    private String failDesc;
    private Integer subSeq;
    private Date reportTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    public Integer getSubSeq() {
        return subSeq;
    }

    public void setSubSeq(Integer subSeq) {
        this.subSeq = subSeq;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}
