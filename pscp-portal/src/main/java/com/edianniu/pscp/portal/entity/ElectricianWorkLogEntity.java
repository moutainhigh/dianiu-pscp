package com.edianniu.pscp.portal.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 11:16:43
 */
public class ElectricianWorkLogEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long electricianWorkOrderId;
    //$column.comments
    private Long workOrderId;
    //$column.comments
    private Long electricianId;
    //$column.comments
    private String content;
    //$column.comments
    private Long companyId;
    //$column.comments
    private Date createTime;
    //$column.comments
    private String createUser;
    //$column.comments
    private Date modifiedTime;
    //$column.comments
    private String modifiedUser;
    //$column.comments
    private Integer deleted;

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
    public void setElectricianWorkOrderId(Long electricianWorkOrderId) {
        this.electricianWorkOrderId = electricianWorkOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getElectricianWorkOrderId() {
        return electricianWorkOrderId;
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
    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getElectricianId() {
        return electricianId;
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
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getCompanyId() {
        return companyId;
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

    /**
     * 设置：${column.comments}
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getDeleted() {
        return deleted;
    }
}
