package com.edianniu.pscp.portal.bean.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: EvaluateVO
 * Author: tandingbo
 * CreateTime: 2017-10-26 14:43
 */
public class EvaluateVO implements Serializable {
    private static final long serialVersionUID = -66396934573642442L;

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
     * 附件信息(PC)
     */
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

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
