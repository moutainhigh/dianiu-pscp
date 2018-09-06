package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;

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
    private Long serviceQuality;
    //$column.comments
    private Long serviceSpeed;
    //$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Date modifiedTime;
    //$column.comments
    private String modifiedUser;

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

    /**
     * 设置：${column.comments}
     */
    public void setServiceQuality(Long serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getServiceQuality() {
        return serviceQuality;
    }

    /**
     * 设置：${column.comments}
     */
    public void setServiceSpeed(Long serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getServiceSpeed() {
        return serviceSpeed;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置：${column.comments}
     */
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * 获取：${column.comments}
     */
    public String getModifiedUser() {
        return modifiedUser;
    }

}
