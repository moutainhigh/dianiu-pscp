package com.edianniu.pscp.portal.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-31 19:16:24
 */
public class MemberWalletEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long uid;
    //$column.comments
    private Double amount;
    //$column.comments
    private Double freezingAmount;
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
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置：${column.comments}
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置：${column.comments}
     */
    public void setFreezingAmount(Double freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    /**
     * 获取：${column.comments}
     */
    public Double getFreezingAmount() {
        return freezingAmount;
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
