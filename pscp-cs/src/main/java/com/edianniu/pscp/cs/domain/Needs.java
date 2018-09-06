package com.edianniu.pscp.cs.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoujianjian
 * 2017年9月13日下午3:39:13
 */
public class Needs extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //需求ID
    private Long id;
    //公司ID
    private Long companyId;
    //需求编号
    private String orderId;
    //需求名称
    private String name;
    //需求描述
    private String describe;
    //发布截止时间
    private Date publishCutoffTime;
    //工作结束时间
    private Date workingStartTime;
    //工作开始时间
    private Date workingEndTime;
    //联迪人
    private String contactPerson;
    //联系方式
    private String contactNumber;
    //联系地址
    private String contactDddr;
    //状态
    //-3：已超时；-2：已取消；-1：审核不通过；
    //0：审核中；1：响应中(审核通过)；
    //2：报价中； 3：已报价； 4：已合作
    private int status;
    //如果有多个配电房，逗号隔开
    private String distributionRoomIds;
    //备注
    private String remark;
    //审核时间
    private Date auditTime;
    //审核失败原因
    private String failReason;
    // 关键词
    private String keyword;
    /**需求保证金*/
    private Double cautionMoney;
    /**上下架状态 0:未上架（下架）  1:上架*/
    private Integer onShelves;

    public Integer getOnShelves() {
		return onShelves;
	}

	public void setOnShelves(Integer onShelves) {
		this.onShelves = onShelves;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getPublishCutoffTime() {
        return publishCutoffTime;
    }

    public void setPublishCutoffTime(Date publishCutoffTime) {
        this.publishCutoffTime = publishCutoffTime;
    }

    public Date getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(Date workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public Date getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(Date workingEndTime) {
        this.workingEndTime = workingEndTime;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactDddr() {
        return contactDddr;
    }

    public void setContactDddr(String contactDddr) {
        this.contactDddr = contactDddr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDistributionRoomIds() {
        return distributionRoomIds;
    }

    public void setDistributionRoomIds(String distributionRoomIds) {
        this.distributionRoomIds = distributionRoomIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Double getCautionMoney() {
        return cautionMoney;
    }

    public void setCautionMoney(Double cautionMoney) {
        this.cautionMoney = cautionMoney;
    }
}
