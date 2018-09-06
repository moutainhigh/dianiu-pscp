package com.edianniu.pscp.mis.bean.attachment.common;

import java.io.Serializable;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-19 11:04:25
 */
public class CommonAttachment implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long foreignKey;
    //$column.comments
    private Long companyId;
    //$column.comments
    private Long memberId;
    //$column.comments
    private Integer businessType;
    //$column.comments
    private Integer type;
    //$column.comments
    private String fid;
    //$column.comments
    private Long orderNum;
    // 0:是(默认)；1:否(满足条件开放)
    /* 是否开放.*/
    private Integer isOpen;

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
    public void setForeignKey(Long foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getForeignKey() {
        return foreignKey;
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
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 设置：${column.comments}
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getType() {
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
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getOrderNum() {
        return orderNum;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
