package com.edianniu.pscp.cs.bean.needs.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.edianniu.pscp.cs.bean.engineeringproject.QuotedInfo;
import com.edianniu.pscp.cs.bean.needs.Attachment;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhoujianjian
 * 2017年9月14日上午10:31:33
 */
public class NeedsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /* 客户公司名称.*/
    private String companyName;
    /* 客户公司ID.*/
    @JSONField(serialize = false)
    private Long companyId;

    private String orderId;

    private Integer status;

    private String name;

    private String publishTime;

    private String describe;

    private List<Attachment> attachmentList;

    private String publishCutoffTime;

    private String workingStartTime;

    private String workingEndTime;

    private String contactPerson;

    private String contactNumber;

    private String contactAddr;

    private String distributionRooms;

    private List<Attachment> cooperationInfo;

    private QuotedInfo quotedInfo;
    /* 关键词.*/
    @JSONField(serialize = false)
    private String keyword;
    
    private String failReason;
    /**
     * 需求保证金
     */
    @JSONField(serialize = false)
    private Double cautionMoney;

    public QuotedInfo getQuotedInfo() {
        return quotedInfo;
    }

    public void setQuotedInfo(QuotedInfo quotedInfo) {
        this.quotedInfo = quotedInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getWorkingStartTime() {
        return workingStartTime;
    }

    public void setWorkingStartTime(String workingStartTime) {
        this.workingStartTime = workingStartTime;
    }

    public String getWorkingEndTime() {
        return workingEndTime;
    }

    public void setWorkingEndTime(String workingEndTime) {
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

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public String getDistributionRooms() {
        return distributionRooms;
    }

    public void setDistributionRooms(String distributionRooms) {
        this.distributionRooms = distributionRooms;
    }

    public List<Attachment> getCooperationInfo() {
        return cooperationInfo;
    }

    public void setCooperationInfo(List<Attachment> cooperationInfo) {
        this.cooperationInfo = cooperationInfo;
    }

    public String getPublishCutoffTime() {
        return publishCutoffTime;
    }

    public void setPublishCutoffTime(String publishCutoffTime) {
        this.publishCutoffTime = publishCutoffTime;
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
