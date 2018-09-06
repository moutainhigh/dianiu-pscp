package com.edianniu.pscp.sps.bean.workorder.evaluate;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: WorkOrderEvaluateInfo
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:29
 */
public class WorkOrderEvaluateInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 评价文本内容
     */
    private String content;
    /**
     * 服务质量
     */
    private Long serviceQuality;
    /**
     * 服务速度
     */
    private Long serviceSpeed;
    /**
     * 评价时间
     */
    private String createTime;
    /**
     * 附件信息
     */
    private List<Map<String, Object>> attachment;
    /**
     * 附件信息(PC)
     */
    @JSONField(serialize = false)
    private List<Map<String, Object>> attachmentList;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Long serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public Long getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(Long serviceSpeed) {
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

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
