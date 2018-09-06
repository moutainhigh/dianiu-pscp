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
public class WorkOrderEvaluateAttachment extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long workOrderEvaluateId;
    //$column.comments
    private Long type;
    //$column.comments
    private String fid;
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
    public void setWorkOrderEvaluateId(Long workOrderEvaluateId) {
        this.workOrderEvaluateId = workOrderEvaluateId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderEvaluateId() {
        return workOrderEvaluateId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getType() {
        return type;
    }

    /**
     * 设置：${column.comments}
     */
    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * 获取：${column.comments}
     */
    public String getFid() {
        return fid;
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
