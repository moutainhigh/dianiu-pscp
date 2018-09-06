package com.edianniu.pscp.sps.bean.evaluate.electrician.VO;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: EvaluateVO
 * Author: tandingbo
 * CreateTime: 2017-05-26 11:41
 */
public class EvaluateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评价信息ID
     */
    private Long evaluateId;
    /**
     * 服务质量
     */
    private Integer serviceQuality;
    /**
     * 服务速度
     */
    private Integer serviceSpeed;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价图片信息
     */
    @JSONField(serialize = false)
    private String[] evaluateImageArray;
    /**
     * 评价图片信息
     */
    private List<EvaluateAttachmentVO> attachment;
    /**
     * 评价标识(0:未评价;1:已评价)
     */
    @JSONField(serialize = false)
    private Integer processed;
    /**
     * 删除评价图片信息ID
     */
    @JSONField(serialize = false)
    private String attachmentIds;
    /**
     * 评价时间
     */
    private String createTime;

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getEvaluateImageArray() {
        return evaluateImageArray;
    }

    public void setEvaluateImageArray(String[] evaluateImageArray) {
        this.evaluateImageArray = evaluateImageArray;
    }

    public List<EvaluateAttachmentVO> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<EvaluateAttachmentVO> attachment) {
        this.attachment = attachment;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
