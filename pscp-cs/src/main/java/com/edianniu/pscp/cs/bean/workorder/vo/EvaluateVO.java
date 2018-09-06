package com.edianniu.pscp.cs.bean.workorder.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: EvaluateVO
 * Author: tandingbo
 * CreateTime: 2017-08-16 09:22
 */
public class EvaluateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 评价信息ID.*/
    private Long evaluateId;
    /* 评价内容.*/
    private String content;
    /* 服务质量.*/
    private Integer serviceQuality;
    /* 服务速度.*/
    private Integer serviceSpeed;
    /* 评价时间（yyyy-mm-dd）.*/
    private String createTime;
    /* 附件信息（yyyy-mm-dd）.*/
    private List<Map<String, Object>> attachment;

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Integer serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public Integer getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(Integer serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Map<String, Object>> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Map<String, Object>> attachment) {
        this.attachment = attachment;
    }
}
