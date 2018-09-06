package com.edianniu.pscp.cs.domain;

import java.io.Serializable;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 10:11:05
 */
public class WorkOrderEvaluate extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long workOrderId;
    //$column.comments
    private Long customerId;
    //$column.comments
    private String content;
    //$column.comments
    private Integer serviceQuality;
    //$column.comments
    private Integer serviceSpeed;

    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderId() {
        return workOrderId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：${column.comments}
     */
    public String getContent() {
        return content;
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

}
